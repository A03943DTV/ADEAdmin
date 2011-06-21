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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an exception based on the XML error response returned by the
 * report service.
 *
 * See status codes and descriptions at:
 *
 * http://code.google.com/apis/apps/reporting/google_apps_reporting_api.html
 */
public class ReportException extends Exception {

	private static final long serialVersionUID = 4237768973827064393L;
	private static final Pattern STATUS_PATTERN = Pattern.compile("status>(.*?)<", Pattern.DOTALL);
	private static final Pattern REASON_PATTERN = Pattern.compile("reason>(.*?)<", Pattern.DOTALL);
	private static final Pattern EXTENDED_MESSAGE_PATTERN = Pattern.compile("extendedMessage>(.*?)<", Pattern.DOTALL);
	private static final Pattern RESULT_PATTERN = Pattern.compile("result>(.*?)<", Pattern.DOTALL);
	private static final Pattern TYPE_PATTERN = Pattern.compile("type>(.*?)<", Pattern.DOTALL);

	private String status = null;
	private String reason = null;
	private String extendedMessage = null;
	private String result = null;
	private String type = null;

	/**
	 * Make the default constructor private, since we want users to use the static
	 * methods to instantiate ReportExceptions.
	 */
	private ReportException() {
		super();
	}

	/**
	 * Parse an XML error response returned by the reporting service.
	 *
	 * @param msg XML error response as described in Reporting API docs
	 * @return report exception constructed from error response attributes
	 */
	public static ReportException fromXml(String msg) {
		ReportException e = new ReportException();
		Matcher m = STATUS_PATTERN.matcher(msg);
		if (m.find()) {
			e.status = m.group(1);
		}
		m = REASON_PATTERN.matcher(msg);
		if (m.find()) {
			e.reason = m.group(1);
		}
		m = EXTENDED_MESSAGE_PATTERN.matcher(msg);
		if (m.find()) {
			e.extendedMessage = m.group(1);
		}
		m = RESULT_PATTERN.matcher(msg);
		if (m.find()) {
			e.result = m.group(1);
		}
		m = TYPE_PATTERN.matcher(msg);
		if (m.find()) {
			e.type = m.group(1);
		}
		return e;
	}

	public String getStatus() {
		return status;
	}

	public String getReason() {
		return reason;
	}

	public String getExtendedMessage() {
		return extendedMessage;
	}

	public String getResult() {
		return result;
	}

	public String getType() {
		return type;
	}

	/**
	 * Overrides Object.toString().
	 *
	 * @return text representation of exception containing attributes
	 *         returned in error response XML
	 */
	public String toString() {
		return "ReportException:status=" + status + ",reason=" + reason + ",extendedMessage=" + extendedMessage + ",result=" + result + ",type="
				+ type;
	}
}
