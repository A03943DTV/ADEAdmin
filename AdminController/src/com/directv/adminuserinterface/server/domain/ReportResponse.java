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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;

/**
 * An iterator for the report response lines.
 *
 * A description of the report output can be found at:
 *
 * http://code.google.com/apis/apps/reporting/google_apps_reporting_api.html
 */
public class ReportResponse {

	public static final String CHARSET = "UTF-8";

	private LineNumberReader reportReader = null;
	private String headerLine = null;

	/**
	 * Construct a report response from an input stream.
	 *
	 * @param in input stream for reading the report response from
	 */
	public ReportResponse(InputStream in) {
		reportReader = new LineNumberReader(new InputStreamReader(in, Charset.forName(CHARSET)));
	}

	/**
	 * Get the header line.
	 *
	 * @return header line which is the first line in the report and
	 *         has column names
	 * @throws IOException if HTTP connection errors
	 * @throws ReportException if error response returned from report service
	 */
	public String getHeaderLine() throws IOException, ReportException {
		if (headerLine == null) {
			getNextLine();
		}
		return headerLine;
	}

	/**
	 * Read the next line from the reader.  When the first line is read, save it
	 * as the header line and check if it is an XML error response.
	 *
	 * @return next line in the report response, or null when the end of the
	 *         report response is reached
	 * @throws IOException if HTTP connection errors
	 * @throws ReportException if error response returned from report service
	 */
	public String getNextLine() throws IOException, ReportException {
		if (reportReader == null) {
			return null;
		}
		String nextLine = reportReader.readLine();
		if (nextLine == null) {
			reportReader.close();
			reportReader = null;
		} else {
			if (reportReader.getLineNumber() == 1) {
				// Read the first line.
				headerLine = nextLine;
				checkForError();
			}
		}
		return nextLine;
	}

	/**
	 * Close the reader.
	 *
	 * @throws IOException if HTTP connection errors
	 */
	public void close() throws IOException {
		if (reportReader != null) {
			reportReader.close();
			reportReader = null;
		}
	}

	/**
	 * Check for an error response in the header line.  If header line contains
	 * an XML header, then we treat the entire response as an error response and
	 * throw a report exception with attributes from the XML.
	 *
	 * @throws IOException if HTTP connection errors
	 * @throws ReportException if the header line contains XML
	 */
	private void checkForError() throws IOException, ReportException {
		if (headerLine != null && headerLine.trim().startsWith("<?xml")) {
			StringBuffer xml = new StringBuffer();
			String line = headerLine;
			while (line != null) {
				xml.append(line);
				line = getNextLine();
			}
			throw ReportException.fromXml(xml.toString());
		}
	}
}
