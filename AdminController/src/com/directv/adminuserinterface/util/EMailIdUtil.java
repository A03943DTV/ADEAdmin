/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.util;

// TODO: Auto-generated Javadoc
/**
 * The Class EMailIdUtil.
 */
public class EMailIdUtil {

	/**
	 * Gets the email id from name.
	 *
	 * @param name the name
	 * @return the email id from name
	 */
	public static String getEmailIdFromName(String name) {
		return name + "@" + AdminConstants.DOMAIN_NAME;
	}

	/**
	 * Gets the name from email id.
	 *
	 * @param emailId the email id
	 * @return the name from email id
	 */
	public static String getNameFromEmailId(String emailId) {
		return emailId.substring(0, emailId.indexOf("@" + AdminConstants.DOMAIN_NAME));
	}
}
