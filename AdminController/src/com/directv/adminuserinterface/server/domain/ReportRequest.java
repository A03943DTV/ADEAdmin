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

/**
 * This encapsulates the attributes of a report request, and contains a method
 * for generating the XML request.
 *
 * The XML elements are described at:
 *
 * http://code.google.com/apis/apps/reporting/google_apps_reporting_api.html
 */
public class ReportRequest {

	private static final String REQUEST_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<rest xmlns=\"google:accounts:rest:protocol\">"
			+ "<type>!TYPE!</type>\n" + "<token>!TOKEN!</token>\n" + "<page>!PAGE!</page>\n" + "<domain>!DOMAIN!</domain>\n"
			+ "<date>!DATE!</date>\n" + "<reportType>!REPORT_TYPE!</reportType>\n" + "<reportName>!REPORT_NAME!</reportName>\n" + "</rest>\n";

	private String type = "Report";
	private String token = null;
	private String domain = null;
	private String date = null;
	private String reportType = "daily";
	private String reportName = null;
	private int page = 0;

	/**
	 * Default constructor.
	 */
	public ReportRequest() {
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportName() {
		return reportName;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * Override Object.toString().
	 *
	 * @return string representation of the report request
	 */
	public String toString() {
		return "ReportRequest:type=" + type + ",token=" + token + ",domain=" + domain + ",date=" + date + ",reportType=" + reportType
				+ ",reportName=" + reportName + " ,page=" + page;
	}

	/**
	 * Construct XML request.
	 *
	 * @return XML representation of the report request
	 */
	public String toXmlString() {
		return REQUEST_TEMPLATE.replaceFirst("!TYPE!", type == null ? "" : type).replaceFirst("!TOKEN!", token == null ? "" : token).replaceFirst(
				"!DOMAIN!", domain == null ? "" : domain).replaceFirst("!DATE!", date == null ? "" : date).replaceFirst("!REPORT_TYPE!",
				reportType == null ? "" : reportType).replaceFirst("!REPORT_NAME!", reportName == null ? "" : reportName).replaceFirst("!PAGE!",
				page == 0 ? "1" : String.valueOf(page));
	}
}
