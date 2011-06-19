/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.util;

import java.util.List;

import com.directv.adminuserinterface.shared.User;

// TODO: Auto-generated Javadoc
/**
 * The Class FormCSVData.
 */
public class FormCSVData {

	/**
	 * Form data.
	 *
	 * @param headerDataArray the header data array
	 * @param userList the user list
	 * @param isBulkUploadResultData the is bulk upload result data
	 * @return the string buffer
	 */
	public static StringBuffer formData(String[] headerDataArray, List<User> userList, boolean isBulkUploadResultData) {

		StringBuffer stringBuilder = new StringBuffer();
		for (String headerData : headerDataArray) {

			stringBuilder.append(headerData);
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1); // remove last ","
		stringBuilder.append("\n");

		for (User user : userList) {

			stringBuilder.append(user.getFirstName());
			stringBuilder.append(",");
			stringBuilder.append(user.getLastName());
			stringBuilder.append(",");
			stringBuilder.append(user.getUserId());
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
			stringBuilder.append(",");
			stringBuilder.append(user.getCredential());

			if (isBulkUploadResultData) {

				stringBuilder.append(",");
				stringBuilder.append(user.getStatus());
				stringBuilder.append(",");
				stringBuilder.append(user.getErrorMessage());
			}
			stringBuilder.append("\n");
		}

		return stringBuilder;
	}
}
