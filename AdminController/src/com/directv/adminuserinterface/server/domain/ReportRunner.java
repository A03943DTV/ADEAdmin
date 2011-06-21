// Copyright 2007 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.directv.adminuserinterface.server.domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This contains the logic for constructing and submitting a report request
 * to the Google Apps reporting service and reading the response.
 *
 * A description of the web service protocol can be found at:
 *
 * http://code.google.com/apis/apps/reporting/google_apps_reporting_api.html
 *
 * <p>
 * Example usage:
 * <pre>
 * ReportRunner runner = new ReportRunner();
 * runner.setAdminEmail("admin@example.com");
 * runner.setAdminPassword("passwd");
 * runner.setDomain("example.com");
 * // Run the latest accounts report to standard output.
 * runner.runReport("accounts", null, System.out);
 * // Run the accounts report for May 15, 2007 and save it to out.txt.
 * runner.runReport("accounts", "2007-05-15", new FileOutputStream("out.txt"));
 * </pre>
 * </p>
 */
public class ReportRunner {

	private static final String AUTH_URL = "https://www.google.com/accounts/ClientLogin";
	private static final String REPORTING_URL = "https://www.google.com/hosted/services/v1.0/reports/ReportingData";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("PST8PDT");
	private static final int PUBLISH_HOUR_OF_DAY = 12;

	private String adminEmail = null;
	private String adminPassword = null;
	private String domain = null;
	private String token = null;
	private int page = 1;
	private boolean reachedEOR = false;
	private boolean hasUserRequestedSinglePage = false;

	/**
	 * Default constructor.
	 */
	public ReportRunner() {
	}

	public boolean isHasUserRequestedSinglePage() {
		return hasUserRequestedSinglePage;
	}

	public void setHasUserRequestedSinglePage(boolean bParam) {
		this.hasUserRequestedSinglePage = bParam;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	/**
	 * Get the domain of the admin's email address.
	 *
	 * This gets the portion of the admin's email address after the final at-sign
	 * ('@').  In most cases, this is the same as the domain for reporting.  This
	 * is used when no domain is set.
	 *
	 * @return  admin email address domain if the admin email address has a valid
	 *          domain, otherwise return <code>null</code>
	 */
	public String getAdminEmailDomain() {
		if (adminEmail != null) {
			int atIndex = adminEmail.lastIndexOf('@');
			if (atIndex >= 0) {
				return adminEmail.substring(atIndex + 1);
			}
		}
		return null;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}

	/**
	 * Set the authentication token to be used to access the reporting service.
	 *
	 * This token is set in the login() method, however if the user of this class
	 * has obtained an authentication token through other means it can be set
	 * with this method.
	 *
	 * @param token  authentication service token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Get the current authentication token used to access the reporting service.
	 *
	 * @return  the current authentication token, or null if the token has not
	 *          not been set
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Get an authorization token from username and password for use with the
	 * report service.
	 *
	 * This authorization token is cached in the ReportRunner instance.  If a new
	 * token is needed, for example if the token is 24 hours old, then call this
	 * method again to get a new token.
	 *
	 * @throws IOException  if error occurs in the HTTPS connection or if the
	 *                      credentials are incorrect
	 */
	public void login() throws IOException {
		String postContent = "accountType=HOSTED&Email=" + urlEncode(adminEmail) + "&Passwd=" + urlEncode(adminPassword);
		HttpURLConnection connection = (HttpURLConnection) new URL(AUTH_URL).openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Length", Integer.toString(postContent.getBytes().length));
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(true);
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(postContent);
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = reader.readLine();
		while (line != null) {
			if (line.startsWith("SID=")) {
				reader.close();
				token = line.substring("SID=".length());
				break;
			} else {
				line = reader.readLine();
			}
		}
		reader.close();
	}

	/**
	 * Run named report for the specified date.
	 *
	 * @param  reportName                report name as listed in Reporting API
	 *                                   docs
	 * @param  dateStr                   date to run the report for.  If the date
	 *                                   is <code>null</code>, then the report is
	 *                                   run for the latest available report date.
	 * @param  out                       output stream to write to.  If the output
	 *                                   stream is <code>null</code>, then the
	 *                                   report is written to standard output.
	 * @throws IOException               if an error occurs in the HTTPS
	 *                                   connection or in writing to the output
	 *                                   stream
	 * @throws ReportException           if an error response is returned from
	 *                                   report service
	 * @throws IllegalArgumentException  if reportName is <code>null</code> or if
	 *                                   the date string is incorrectly formatted
	 */
	public void runReport(String reportName, String dateStr, OutputStream out) throws IOException, ReportException, IllegalArgumentException {
		Date date = null;
		// Check dateStr argument for correct format.
		if (dateStr != null) {
			try {
				date = DATE_FORMAT.parse(dateStr);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Date is not in the format " + "YYYY-MM-DD");
			}
		}
		runReport(reportName, date, out);
	}

	/**
	 * Run named report for the specified date.
	 *
	 * @param  reportName                report name as listed in Reporting API
	 *                                   docs
	 * @param  date                      date to run the report for.  If the date
	 *                                   is <code>null</code>, then the report is
	 *                                   run for the latest available report date.
	 * @param  out                       output stream to write to.  If the output
	 *                                   stream is <code>null</code>, the report
	 *                                   is written to standard output.
	 * @throws IOException               if an error occurs in the HTTPS
	 *                                   connection or in writing to the output
	 *                                   stream
	 * @throws ReportException           if an error response returned from the
	 *                                   report service
	 * @throws IllegalArgumentException  if reportName is <code>null</code>
	 */
	public void runReport(String reportName, Date date, OutputStream out) throws IOException, ReportException, IllegalArgumentException {
		// Check for non-null reportName argument.
		if (reportName == null) {
			throw new IllegalArgumentException("Report name is null.");
		}
		if (getToken() == null) {
			login();
		}
		ReportRequest request = new ReportRequest();
		request.setToken(getToken());
		request.setPage(getPage());
		if (getDomain() != null) {
			request.setDomain(getDomain());
		} else if (getAdminEmailDomain() != null) {
			request.setDomain(getAdminEmailDomain());
		}
		if (date == null) {
			date = getLatestReportDate();
		}
		request.setReportName(reportName);
		request.setDate(DATE_FORMAT.format(date));
		if (out == null) {
			out = System.out;
		}
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, ReportResponse.CHARSET));

		//Fetch all pages untill End-Of-Report or the specified page. 
		do {
			ReportResponse response = new ReportResponse(getReportData(request));
			if (response.getHeaderLine().equalsIgnoreCase("End-Of-Report")) {
				reachedEOR = true;
			} else {
				writeReport(response, writer);
				writer.flush();
				request.setPage(request.getPage() + 1);
			}
		} while (!reachedEOR && !hasUserRequestedSinglePage);

		writer.close();
	}

	/**
	 * Get latest available report date, based on report service time zone.
	 *
	 * Reports for the current date are available after 12:00 PST8PDT the
	 * following day.  We calculate and return the date of the latest available
	 * report based on the current time.
	 *
	 * @return  latest available report date
	 */
	private Date getLatestReportDate() {
		Calendar cal = Calendar.getInstance(TIME_ZONE);
		if (cal.get(Calendar.HOUR_OF_DAY) < PUBLISH_HOUR_OF_DAY) {
			cal.add(Calendar.DATE, -2); // day before yesterday
		} else {
			cal.add(Calendar.DATE, -1); // yesterday
		}
		return cal.getTime();
	}

	/**
	 * URL encode a string.
	 *
	 * @param  s                             string to URL encode
	 * @return                               URL encoded string
	 * @throws UnsupportedEncodingException  if the character set is not supported
	 */
	private String urlEncode(String s) throws UnsupportedEncodingException {
		return URLEncoder.encode(s, ReportResponse.CHARSET);
	}

	/**
	 * Get report response as an input stream.
	 *
	 * @param  request      report request containing request attributes
	 * @throws IOException  if an HTTPS connection error occurs
	 */
	private InputStream getReportData(ReportRequest request) throws IOException {
		String postContent = request.toXmlString();
		HttpURLConnection connection = (HttpURLConnection) new URL(REPORTING_URL).openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Length", Integer.toString(postContent.getBytes().length));
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(true);
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(postContent);
		out.flush();
		out.close();
		return connection.getInputStream();
	}

	/**
	 * Print report response to writer.
	 *
	 * @param  response         report response reader
	 * @param  writer           output writer to write report reponse to
	 * @throws IOException      if an error occurs in the HTTPS connection or in
	 *                          writing to the output stream
	 * @throws ReportException  if an error response is returned from the report
	 *                          service
	 */
	private void writeReport(ReportResponse response, BufferedWriter writer) throws IOException, ReportException {
		String line = response.getNextLine();
		while (line != null) {
			writer.write(line);
			writer.newLine();
			line = response.getNextLine();
		}
	}
}
