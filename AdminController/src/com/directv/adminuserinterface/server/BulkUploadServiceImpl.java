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
	private static final String HEADER_DATA[] = new String[] { "Action[I/D]", "First Name", "Last Name", "User Id", "Group", "Organization",
			"Vendor", "Location", "Manager's Id", "Role", "Campaign", "Privilege", "Status", "ErrorMessage" };

	/** The Constant COLUMN_COUNT. */
	private static final int COLUMN_COUNT = (HEADER_DATA.length - 2);//Excluding {"Status", "ErrorMessage"}

	/** The Constant INSERT. */
	private static final String INSERT = "I";

	/** The Constant DELETE. */
	private static final String DELETE = "D";

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

		int noOfSuccessfullRecords = 0;
		int noOfUnSuccessFullRecords = 0;

		List<User> userList = getUsersList(bulkUpload.getBlob().getBytes(), false);

		for (User user : userList) {
			try {
				validateBUAction(user);
				if (user.getBuAction().equals(INSERT)) {
					new UserServiceImpl().addBulkUploadUser(user);
				} else if (user.getBuAction().equals(DELETE)) {
					new UserServiceImpl().deleteBulkUploadUser(user);
				}
				user.setStatus(User.STATUS_SUCCESS);
				noOfSuccessfullRecords++;
			} catch (AdminException e) {
				e.printStackTrace();
				user.setStatus(User.STATUS_ERROR);
				user.setErrorMessage(e.getMessage());
				noOfUnSuccessFullRecords++;
			} catch (Throwable e) {
				e.printStackTrace();
				user.setStatus(User.STATUS_ERROR);
				user.setErrorMessage("Unexpected exception occured");
				noOfUnSuccessFullRecords++;
			}
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
	 * Validate bu action.
	 *
	 * @param user the user
	 * @throws AdminException the admin exception
	 */
	private void validateBUAction(User user) throws AdminException {

		if (user.getBuAction() == null || user.getBuAction().equals("")) {
			throw new AdminException("Action[I/D] field is required");
		} else if (!(user.getBuAction().equals(INSERT) || user.getBuAction().equals(DELETE))) {
			throw new AdminException("Action[I/D] field is value can be either I or D");
		}
	}

	/**
	 * Gets the single user data.
	 *
	 * @param line the line
	 * @param isInfoCheck the is info check
	 * @return the single user data
	 */
	private User getSingleUserData(String line, boolean isInfoCheck) {

		String userInfoArray[] = null;
		if (!isInfoCheck) {
			userInfoArray = new String[COLUMN_COUNT];
		} else {
			userInfoArray = new String[HEADER_DATA.length];
		}
		String userInfoTempArray[] = line.split(",");
		for (int idx = 0; idx < userInfoTempArray.length; idx++) {
			userInfoArray[idx] = userInfoTempArray[idx];
		}

		User user = new User(userInfoArray[1], userInfoArray[2], userInfoArray[3], userInfoArray[4], userInfoArray[5], userInfoArray[6],
				userInfoArray[7], userInfoArray[8], userInfoArray[9], userInfoArray[10], userInfoArray[11]);
		user.setBuAction(userInfoArray[0]);
		if (isInfoCheck) {
			user.setStatus(userInfoArray[12]);
			user.setErrorMessage(userInfoArray[13]);
		}
		return user;
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<BulkUploadDto> getBulkUploadResults(String userId) {

		List<BulkUploadDto> bulkuploadDtoList = new ArrayList<BulkUploadDto>();
		for (BulkUpload bulkUpload : new BulkUploadDaoImpl().getBulkUploadResults(userId)) {
			bulkuploadDtoList.add(getBulkUploadDto(bulkUpload));
		}
		return bulkuploadDtoList;
	}

	/**
	 * Gets the bulk upload dto.
	 *
	 * @param bulkUpload the bulk upload
	 * @return the bulk upload dto
	 */
	private BulkUploadDto getBulkUploadDto(BulkUpload bulkUpload) {

		byte[] byteArray = bulkUpload.getBlob().getBytes();
		return new BulkUploadDto(bulkUpload.getId(), bulkUpload.getDescription(), bulkUpload.getUserId(), byteArray, bulkUpload.getProcessStatus(),
				bulkUpload.getSubmittedTime(), bulkUpload.getProcessStratTime(), bulkUpload.getProcessEndTime(), bulkUpload.getNoOfSuccessRecords(),
				bulkUpload.getNoOfFailureRecords(), bulkUpload.getStatus());
	}

	/**
	 * Overridden Method
	 * @param id
	 * @return
	 */
	@Override
	public BulkUploadDto getBulkUploadById(Long id) {

		BulkUpload bulkUpload = new BulkUploadDaoImpl().getBulkUploadById(id);
		byte[] byteArray = bulkUpload.getBlob().getBytes();

		List<User> userList = getUsersList(byteArray, true);

		BulkUploadDto bulkUploadDto = getBulkUploadDto(bulkUpload);
		bulkUploadDto.setUserList(userList);

		return bulkUploadDto;
	}

	/**
	 * Gets the users list.
	 *
	 * @param byteArray the byte array
	 * @param isInfoCheck the is info check
	 * @return the users list
	 */
	private List<User> getUsersList(byte[] byteArray, boolean isInfoCheck) {

		InputStream is = new ByteArrayInputStream(byteArray);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		int count = 1;
		List<User> userList = new ArrayList<User>();
		try {
			while ((line = br.readLine()) != null) {
				if (count == 1) {
					System.out.println("Ignoring Header Information");
				} else {
					User user = getSingleUserData(line, isInfoCheck);
					userList.add(user);
				}
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userList;
	}

	/**
	 * Overridden Method
	 * @param bulkUploadDto
	 */
	@Override
	public void dummy(BulkUploadDto bulkUploadDto) {

	}
}
