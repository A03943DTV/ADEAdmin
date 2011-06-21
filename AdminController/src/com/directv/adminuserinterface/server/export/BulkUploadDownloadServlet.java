/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server.export;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.directv.adminuserinterface.rest.BulkUploadDaoImpl;
import com.directv.adminuserinterface.server.dao.BulkUpload;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkUploadDownloadServlet.
 */
public class BulkUploadDownloadServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7553214876648040781L;

	/** The Constant FILE_NAME. */
	private static final String FILE_NAME = "BulkUploadResult";

	/** The Constant ID. */
	private static final String ID = "id";

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		ServletOutputStream out = null;
		try {

			BulkUpload bulkUpload = new BulkUploadDaoImpl().getBulkUploadById(Long.parseLong(request.getParameter(ID)));

			out = response.getOutputStream();

			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME + ".csv");
			response.setContentLength(bulkUpload.getBlob().getBytes().length);

			out.write(bulkUpload.getBlob().getBytes());
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
