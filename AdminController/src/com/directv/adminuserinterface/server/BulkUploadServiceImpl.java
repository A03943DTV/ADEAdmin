/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.directv.adminuserinterface.client.bulkupload.BulkUploadService;
import com.directv.adminuserinterface.rest.BulkUploadDaoImpl;
import com.directv.adminuserinterface.server.dao.BulkUpload;
import com.directv.adminuserinterface.shared.BulkUploadDto;
import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.AdminException;
import com.directv.adminuserinterface.util.FormCSVData;
import com.google.appengine.api.datastore.Blob;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkUploadServiceImpl.
 */
public class BulkUploadServiceImpl extends RemoteServiceServlet implements BulkUploadService {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6014641590287705576L;

	/** The Constant HEADER_DATA. */
	private static final String HEADER_DATA[] = new String[] { "First Name", "Last Name", "User Id", "Group", "Location", "Manager's Id", "Role",
			"Campaign", "Privilege", "Status", "ErrorMessage" };

	/** The Constant COLUMN_COUNT. */
	private static final int COLUMN_COUNT = (HEADER_DATA.length - 2);//Excluding {"Status", "ErrorMessage"}

	/**
	 * Overridden Method
	 */
	@Override
	public void processBulkUpload() {

		List<BulkUpload> bulkUploadUnProcessedList = new BulkUploadDaoImpl().getUnProcessedBUList();

		for (BulkUpload bulkUpload : bulkUploadUnProcessedList) {

			bulkUpload.setProcessStatus(BulkUpload.PROCESS_STATUS_PROCESSING);
			bulkUpload.setProcessStratTime((new Timestamp(System.currentTimeMillis())).toString());
			new BulkUploadDaoImpl().updateBulkUpload(bulkUpload);

			bulkUpload = processRecord(bulkUpload);

			bulkUpload.setProcessStatus(BulkUpload.PROCESS_STATUS_COMPLETED);
			bulkUpload.setProcessEndTime((new Timestamp(System.currentTimeMillis())).toString());
			new BulkUploadDaoImpl().updateBulkUpload(bulkUpload);
		}
	}

	/**
	 * Process record.
	 *
	 * @param bulkUpload the bulk upload
	 * @return the bulk upload
	 */
	private BulkUpload processRecord(BulkUpload bulkUpload) {

		InputStream is = new ByteArrayInputStream(bulkUpload.getBlob().getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		int noOfSuccessfullRecords = 0;
		int noOfUnSuccessFullRecords = 0;
		int count = 1;
		List<User> userList = new ArrayList<User>();

		try {

			while ((line = br.readLine()) != null) {

				if (count == 1) {
					System.out.println("Ignoring Header Information");
				} else {

					User user = getSingleUserData(line);
					try {

						new UserServiceImpl().addBulkUploadUser(user);
						user.setStatus(User.STATUS_SUCCESS);
						noOfSuccessfullRecords++;
					} catch (AdminException e) {

						e.printStackTrace();
						user.setStatus(User.STATUS_ERROR);
						user.setErrorMessage(e.getMessage());
						noOfUnSuccessFullRecords++;
					}
					userList.add(user);
				}
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		bulkUpload.setNoOfSuccessRecords(new Long(noOfSuccessfullRecords));
		bulkUpload.setNoOfFailureRecords(new Long(noOfUnSuccessFullRecords));

		if (bulkUpload.getNoOfFailureRecords() == 0) {
			bulkUpload.setStatus(BulkUpload.STATUS_SUCCESS);
		} else {
			bulkUpload.setStatus(BulkUpload.STATUS_ERROR);
		}

		StringBuffer stringBuilder = FormCSVData.formData(HEADER_DATA, userList, true);
		bulkUpload.setBlob(new Blob(stringBuilder.toString().getBytes()));

		return bulkUpload;
	}

	/**
	 * Gets the single user data.
	 *
	 * @param line the line
	 * @return the single user data
	 */
	private User getSingleUserData(String line) {

		String userInfoArray[] = new String[COLUMN_COUNT];
		String userInfoTempArray[] = line.split(",");
		for (int idx = 0; idx < userInfoTempArray.length; idx++) {
			userInfoArray[idx] = userInfoTempArray[idx];
		}

		return new User(userInfoArray[0], userInfoArray[1], userInfoArray[2], userInfoArray[3], userInfoArray[4], userInfoArray[5], userInfoArray[6],
				userInfoArray[7], userInfoArray[8]);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<BulkUploadDto> getBulkUploadResults(String userId) {

		List<BulkUploadDto> bulkuploadDtoList = new ArrayList<BulkUploadDto>();
		for (BulkUpload bulkUpload : new BulkUploadDaoImpl().getBulkUploadResults(userId)) {

			byte[] byteArray = bulkUpload.getBlob().getBytes();
			bulkuploadDtoList.add(new BulkUploadDto(bulkUpload.getId(), bulkUpload.getDescription(), bulkUpload.getUserId(), byteArray, bulkUpload
					.getProcessStatus(), bulkUpload.getResultStatus(), bulkUpload.getSubmittedTime(), bulkUpload.getProcessStratTime(), bulkUpload
					.getProcessEndTime(), bulkUpload.getNoOfSuccessRecords(), bulkUpload.getNoOfFailureRecords(), bulkUpload.getStatus()));
		}
		return bulkuploadDtoList;
	}

	/**
	 * Overridden Method
	 * @param bulkUploadDto
	 */
	@Override
	public void dummy(BulkUploadDto bulkUploadDto) {

	}

	/**
	 * Overridden Method
	 * @param id
	 */
	@Override
	public void setDownloadableBulkUploadIdInSession(String id) {

		this.getThreadLocalRequest().getSession().setAttribute(AdminConstants.BU_ID_DATA_STORE_ATTRIBUTE, id);
	}
}
