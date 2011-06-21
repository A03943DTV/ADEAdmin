/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.directv.adminuserinterface.server.domain.ReportException;
import com.directv.adminuserinterface.server.domain.ReportRunner;
import com.directv.adminuserinterface.shared.ReportBean;
import com.directv.adminuserinterface.util.AdminConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportServiceImpl.
 */
public class ReportServiceImpl implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2667730991357881742L;

	/** The Constant ACCOUNTS. */
	private static final String ACCOUNTS = "accounts";//Don't change this name. If you change this report won't work.

	/**
	 * Gets the reports.
	 *
	 * @return the reports
	 */
	public Map<String, ReportBean> getReports() {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String date = null;

		ReportRunner runner = new ReportRunner();
		runner.setAdminEmail(AdminConstants.DOMAIN_ADMIN_USER_ID);
		runner.setAdminPassword(AdminConstants.DOMAIN_ADMIN_USER_PASSWORD);
		runner.setDomain(AdminConstants.DOMAIN_NAME);
		try {
			runner.runReport(ACCOUNTS, date, out);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ReportException e) {
			e.printStackTrace();
		}

		return processOutput(out);
	}

	/**
	 * Process output.
	 *
	 * @param out the out
	 * @return the map
	 */
	private Map<String, ReportBean> processOutput(ByteArrayOutputStream out) {

		Map<String, ReportBean> reportBeanMap = new HashMap<String, ReportBean>();

		InputStream is = new ByteArrayInputStream(out.toByteArray());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;

		try {
			while ((line = br.readLine()) != null) {
				String report[] = line.split(",");
				ReportBean reportBean = new ReportBean(report[0], report[1], report[2], report[3], report[4], report[5], report[6], report[7],
						report[8], report[9], report[10], report[11], report[12], report[13], report[14], report[15], report[16], report[17],
						report[18], report[19], report[20]);
				reportBeanMap.put(report[2], reportBean);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reportBeanMap;
	}
}
