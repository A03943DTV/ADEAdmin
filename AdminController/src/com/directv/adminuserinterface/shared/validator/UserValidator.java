/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.shared.validator;

import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.util.AdminException;

// TODO: Auto-generated Javadoc
/**
 * The Class UserValidator.
 */
public class UserValidator {

	/**
	 * Validate.
	 *
	 * @param user the user
	 * @throws AdminException the admin exception
	 */
	public static void validate(User user) throws AdminException {

		if (user.getFirstName() == null || user.getFirstName().equals("")) {
			throw new AdminException("FirstName is required");
		}
		if (user.getLastName() == null || user.getLastName().equals("")) {
			throw new AdminException("LastName is required");
		}
		if (user.getUserId() == null || user.getUserId().equals("")) {
			throw new AdminException("UserId is required");
		}
		if (user.getOrganization() == null || user.getOrganization().equals("")) {
			throw new AdminException("Organization is required");
		}
		if (user.getSubOrganization() == null || user.getSubOrganization().equals("")) {
			throw new AdminException("Vendor is required");
		}
		if (user.getLocation() == null || user.getLocation().equals("")) {
			throw new AdminException("Location is required");
		}
	}
}
