/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.server.upload;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.directv.adminuserinterface.rest.BulkUploadDaoImpl;
import com.directv.adminuserinterface.server.dao.BulkUpload;
import com.google.appengine.api.datastore.Blob;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkUploadHandlerServlet.
 */
public class BulkUploadHandlerServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2136257905967406889L;

	/** The Constant DESCRIPTION_FIELD. */
	private static final String DESCRIPTION_FIELD = "description";

	/** The Constant UPLOAD_FORM_FIELD. */
	private static final String UPLOAD_FORM_FIELD = "uploadFormField";

	/**
	 * Overridden Method
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	/**
	 * Overridden Method
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload();

		if (isMultipart) {

			FileItemIterator iter = null;
			try {
				iter = upload.getItemIterator(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}

			try {

				String description = null;
				String uploadedData = null;

				while (iter.hasNext()) {

					FileItemStream item = iter.next();
					String name = item.getFieldName();
					InputStream stream = item.openStream();
					if (item.isFormField()) {

						System.out.println("Form field " + name + " detected.");
						if (name.equals(DESCRIPTION_FIELD)) {
							description = Streams.asString(stream);
						}
					} else {

						System.out.println("File field " + name + " with file name " + item.getName() + " detected.");
						if (name.equals(UPLOAD_FORM_FIELD)) {
							uploadedData = Streams.asString(stream);
						}
					}
				}

				byte byteArray[] = uploadedData.getBytes();
				Blob blob = new Blob(byteArray);

				Long id = new Long(new BulkUploadDaoImpl().getCount() + 1);
				new BulkUploadDaoImpl().add(new BulkUpload(id, description, blob, BulkUpload.PROCESS_STATUS_TO_BE_PROCESSED, (new Timestamp(System
						.currentTimeMillis())).toString()));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
