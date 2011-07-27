/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server.domain;

import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.AdminException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Instance objects.
 */
public final class InstanceFactory {

	/** The afydc instance. */
	private static AppsForYourDomainClient afydcInstance = null;

	/** The omc instance. */
	private static OrgManagementClient omcInstance = null;

	static {

		try {
			afydcInstance = new AppsForYourDomainClient(AdminConstants.DOMAIN_ADMIN_USER_ID, AdminConstants.DOMAIN_ADMIN_USER_PASSWORD,
					AdminConstants.DOMAIN_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			omcInstance = new OrgManagementClient(AdminConstants.DOMAIN_ADMIN_USER_ID, AdminConstants.DOMAIN_ADMIN_USER_PASSWORD,
					AdminConstants.DOMAIN_NAME, ("AdminController-" + AdminConstants.DOMAIN_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates a new instance factory.
	 */
	private InstanceFactory() {
	}

	/**
	 * Gets the aFYDC instance.
	 *
	 * @return the aFYDC instance
	 * @throws AdminException the admin exception
	 */
	public static AppsForYourDomainClient getAFYDCInstance() throws AdminException {
		if (afydcInstance == null) {
			throw new AdminException("Exception occured while authenticating using admin login credentials in directv domain.");
		}
		return afydcInstance;
	}

	/**
	 * Gets the oMC instance.
	 *
	 * @return the oMC instance
	 * @throws AdminException the admin exception
	 */
	public static OrgManagementClient getOMCInstance() throws AdminException {
		if (omcInstance == null) {
			throw new AdminException(
					"Exception occured while authenticating using admin login credentials in directv domain for organization management.");
		}
		return omcInstance;
	}
}