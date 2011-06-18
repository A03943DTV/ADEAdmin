/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.server.export;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.FormCSVData;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDownloadServlet.
 */
public class UserDownloadServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4179241484616232579L;

	/** The Constant FILE_NAME. */
	private static final String FILE_NAME = "UsersInfo";

	/** The Constant HEADER_DATA. */
	private static final String HEADER_DATA[] = new String[] { "First Name", "Last Name", "User Id", "Group", "Location", "Manager's Id", "Role",
			"Campaign", "Privilege" };

	/**
	 * Overridden Method
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overridden Method
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		StringBuffer stringBuilder = FormCSVData.formData(HEADER_DATA, (ArrayList<User>) request.getSession().getAttribute(
				AdminConstants.SESSION_DATA_STORE_ATTRIBUTE), false);

		ServletOutputStream out = null;
		try {

			out = response.getOutputStream();

			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME + ".csv");
			response.setContentLength(stringBuilder.toString().getBytes().length);

			out.write(stringBuilder.toString().getBytes());
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}