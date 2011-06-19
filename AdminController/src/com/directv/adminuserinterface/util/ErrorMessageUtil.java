/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.util;

// TODO: Auto-generated Javadoc
/**
 * The Class ErrorMessageUtil.
 */
public class ErrorMessageUtil {

	/**
	 * Form error message.
	 *
	 * @param e the e
	 * @return the string
	 */
	public static String formErrorMessage(Exception e) {

		StringBuffer errorMessage = new StringBuffer();
		errorMessage.append(AdminException.class.getName());
		errorMessage.append(":");
		errorMessage.append(e.getMessage());

		return errorMessage.toString();
	}

	/**
	 * Gets the error message.
	 *
	 * @param response the response
	 * @return the error message
	 */
	public static String getErrorMessage(String response) {

		if (response != null && response.contains(AdminException.class.getName())) {
			int startIndex = 5 + AdminException.class.getName().toString().length() + 1; // <pre> + --- + :
			int endIndex = response.length() - 6;//To remove the </pre> tag at the last
			return response.substring(startIndex, endIndex);
		}
		return null;
	}
}
