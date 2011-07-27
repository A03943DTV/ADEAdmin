/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server.domain;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Arrays;

import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.AdminException;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.appsforyourdomain.generic.GenericEntry;
import com.google.gdata.util.ServiceException;

// TODO: Auto-generated Javadoc
/**
 * The Class GoogleOrgManager.
 */
public class GoogleOrgManager {

	/**
	 * Gets the org management client.
	 *
	 * @return the org management client
	 * @throws AdminException the admin exception
	 */
	private OrgManagementClient getOrgManagementClient() throws AdminException {

		return InstanceFactory.getOMCInstance();
	}

	/**
	 * Gets the customer id.
	 *
	 * @param client the client
	 * @return the customer id
	 * @throws AdminException the admin exception
	 */
	private String getCustomerId(OrgManagementClient client) throws AdminException {

		GenericEntry entry = null;
		try {
			entry = client.retrieveCustomerId(AdminConstants.DOMAIN_NAME);
		} catch (AppsForYourDomainException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while getting customer id in directv domain for organization.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while getting customer id in directv domain for organization.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while getting customer id in directv domain for organization.");
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while getting customer id in directv domain for organization.");
		} catch (Exception e) {
			processOtherException(e);
		}
		return entry.getProperty("customerId");
	}

	/**
	 * Update organization user.
	 *
	 * @param userId the user id
	 * @param oldPath the old path
	 * @param newPath the new path
	 * @throws AdminException the admin exception
	 */
	public void updateOrganizationUser(String userId, String oldPath, String newPath) throws AdminException {

		OrgManagementClient client = getOrgManagementClient();
		try {
			client.updateOrganizationUser(getCustomerId(client), userId, oldPath, newPath);
		} catch (AppsForYourDomainException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while updating organization user.");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while updating organization user.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while updating organization user.");
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while updating organization user.");
		} catch (Exception e) {
			processOtherException(e);
		}
	}

	/**
	 * Process other exception.
	 *
	 * @param e the e
	 * @throws AdminException the admin exception
	 */
	private void processOtherException(Exception e) throws AdminException {

		//TODO : Ugly fix but no solution available for this issue other than this 
		//Need to do some research on this
		if (e.getCause() != null && e.getCause().toString().contains("java.net.SocketTimeoutException")) {
			System.out.println("Exception occured : " + e.getMessage());
		} else {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the org path from org units.
	 *
	 * @param organization the organization
	 * @param subOrganization the sub organization
	 * @param location the location
	 * @param isAdmin the is admin
	 * @return the string
	 * @throws AdminException the admin exception
	 */
	public String createOrgPathFromOrgUnits(String organization, String subOrganization, String location, boolean isAdmin) throws AdminException {
		try {
			String administrator = "Administrator";
			if (isAdmin) {
				return getOrgManagementClient().createOrgPathFromOrgUnits((Arrays.asList(organization, subOrganization, location, administrator)));
			} else {
				return getOrgManagementClient().createOrgPathFromOrgUnits((Arrays.asList(organization, subOrganization, location)));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while creating organization path.");
		} catch (AdminException e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while creating organization path.");
		}
	}
}
