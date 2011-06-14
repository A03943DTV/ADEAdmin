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
	private static final String HEADER_DATA[] = new String[] { "User Id", "First Name", "Last Name", "Group", "Location", "Manager's Id", "Role",
			"Campaign" };

	/**
	 * Overridden Method
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * Overridden Method
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		StringBuffer stringBuilder = new StringBuffer();
		for (String headerData : HEADER_DATA) {
			stringBuilder.append(headerData);
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1); // remove last ","
		stringBuilder.append("\n");

		for (User user : (ArrayList<User>) request.getSession().getAttribute(AdminConstants.SESSION_DATA_STORE_ATTRIBUTE)) {
			stringBuilder.append(user.getUserId());
			stringBuilder.append(",");
			stringBuilder.append(user.getFirstName());
			stringBuilder.append(",");
			stringBuilder.append(user.getLastName());
			stringBuilder.append(",");
			stringBuilder.append(user.getGroup());
			stringBuilder.append(",");
			stringBuilder.append(user.getLocation());
			stringBuilder.append(",");
			stringBuilder.append(user.getManagersId());
			stringBuilder.append(",");
			stringBuilder.append(user.getRole());
			stringBuilder.append(",");
			stringBuilder.append(user.getCampaign());
			stringBuilder.append("\n");
		}

		ServletOutputStream out = response.getOutputStream();

		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME + ".csv");
		response.setContentLength(stringBuilder.toString().getBytes().length);

		out.write(stringBuilder.toString().getBytes());
		out.flush();
		out.close();
	}
}