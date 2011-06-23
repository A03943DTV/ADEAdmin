/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.AdminException;
import com.directv.adminuserinterface.util.EMailIdUtil;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainErrorCode;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.appsforyourdomain.provisioning.UserEntry;
import com.google.gdata.data.appsforyourdomain.provisioning.UserFeed;
import com.google.gdata.util.ServiceException;

// TODO: Auto-generated Javadoc
/**
 * The Class GoogleUserManager.
 */

public class GoogleUserManager {

	/**
	 * Gets the client.
	 *
	 * @return the client
	 * @throws AdminException the admin exception
	 */
	public AppsForYourDomainClient getClient() throws AdminException {

		try {
			return new AppsForYourDomainClient(AdminConstants.DOMAIN_ADMIN_USER_ID, AdminConstants.DOMAIN_ADMIN_USER_PASSWORD,
					AdminConstants.DOMAIN_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException("Exception occured while authenticating using admin login credentials in directv domain.");
		}
	}

	/**
	 * Creates the domain user.
	 *
	 * @param user the user
	 * @return the user
	 * @throws AdminException the admin exception
	 */
	public User createDomainUser(User user) throws AdminException {

		AppsForYourDomainClient client = getClient();

		try {
			//Creating a new user in domain
			client.createUser(user.getUserId(), user.getFirstName(), user.getLastName(), AdminConstants.NEW_USER_DEFAULT_PASSWORD);
		} catch (AppsForYourDomainException e) {
			processAppsForYourDomainException(e);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new AdminException("Service exception occured while creating user in directv domain.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdminException("IOException occured while creating user in directv domain.");
		} catch (Exception e) {
			processOtherException(e);
		}

		try {
			//Making the created user to change password on first time login
			client.forceUserToChangePassword(user.getUserId());
		} catch (AppsForYourDomainException e) {
			processAppsForYourDomainException(e);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new AdminException("Service exception occured while making created user to change password in directv domain.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdminException("IOException occured while making created user to change password in directv domain.");
		} catch (Exception e) {
			processOtherException(e);
		}

		user.setUserId(EMailIdUtil.getEmailIdFromName(user.getUserId()));
		return user;
	}

	/**
	 * Update domain user.
	 *
	 * @param user the user
	 * @return the user
	 * @throws AdminException the admin exception
	 */
	public User updateDomainUser(User user) throws AdminException {

		AppsForYourDomainClient client = getClient();

		UserEntry userEntry = null;

		try {
			//Retrieving the user from domain and updating new information
			userEntry = client.retrieveUser(user.getUserId());
			userEntry.getName().setGivenName(user.getFirstName());
			userEntry.getName().setFamilyName(user.getLastName());
		} catch (AppsForYourDomainException e) {
			processAppsForYourDomainException(e);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new AdminException("Service exception occured while retreiving user to update in directv domain.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdminException("IOException occured while retreiving user to update in directv domain.");
		} catch (Exception e) {
			processOtherException(e);
		}

		try {
			client.updateUser(user.getUserId(), userEntry);
		} catch (AppsForYourDomainException e) {
			processAppsForYourDomainException(e);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new AdminException("Service exception occured while updating user in directv domain.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdminException("IOException occured while updating user in directv domain.");
		} catch (Exception e) {
			processOtherException(e);
		}

		user.setUserId(EMailIdUtil.getEmailIdFromName(user.getUserId()));

		return user;
	}

	/**
	 * Retrieve all users in domain.
	 *
	 * @return the list
	 * @throws AdminException the admin exception
	 */
	public List<User> retrieveAllUsersInDomain() throws AdminException {

		List<User> userList = new ArrayList<User>();

		UserFeed resultFeed = null;
		try {
			resultFeed = getClient().retrieveAllUsers();
		} catch (AppsForYourDomainException e) {
			processAppsForYourDomainException(e);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new AdminException("Service exception occured while retreving all users from directv domain.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdminException("IOException occured while retreving all users from directv domain.");
		}

		List<UserEntry> entriesList = resultFeed.getEntries();

		if (entriesList != null && entriesList.size() > 0) {
			for (UserEntry entry : entriesList) {

				User user = new User();
				user.setUserId(EMailIdUtil.getEmailIdFromName(entry.getTitle().getPlainText()));
				user.setFirstName(entry.getName().getGivenName());
				user.setLastName(entry.getName().getFamilyName());
				userList.add(user);
			}
		}
		return userList;
	}

	/**
	 * Retrieve user.
	 *
	 * @param userId the user id
	 * @return the user
	 * @throws AdminException the admin exception
	 */
	public User retrieveUser(String userId) throws AdminException {

		UserEntry userEntry = null;
		try {
			userEntry = getClient().retrieveUser(EMailIdUtil.getNameFromEmailId(userId));
		} catch (AppsForYourDomainException e) {
			processAppsForYourDomainException(e);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new AdminException("Service exception occured while retreving user from directv domain.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdminException("IOException occured while retreving user from directv domain.");
		}

		User user = new User();
		user.setUserId(EMailIdUtil.getEmailIdFromName(userEntry.getTitle().getPlainText()));
		user.setFirstName(userEntry.getName().getGivenName());
		user.setLastName(userEntry.getName().getFamilyName());
		user.setAdmin(userEntry.getLogin().getAdmin());
		return user;
	}

	/**
	 * Delete domain user.
	 *
	 * @param user the user
	 * @return the user
	 * @throws AdminException the admin exception
	 */
	public User deleteDomainUser(User user) throws AdminException {

		try {
			getClient().deleteUser(EMailIdUtil.getNameFromEmailId(user.getUserId()));//Deleting the user
		} catch (AppsForYourDomainException e) {
			processAppsForYourDomainException(e);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new AdminException("Service exception occured while deleting user from directv domain.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdminException("IOException occured while deleting user from directv domain.");
		} catch (Exception e) {
			processOtherException(e);
		}
		return user;
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
			throw new AdminException("Exception occured while add/update/delete user from directv domain.");
		}
	}

	/**
	 * Process apps for your domain exception.
	 *
	 * @param e the e
	 * @throws AdminException the admin exception
	 */
	private void processAppsForYourDomainException(AppsForYourDomainException e) throws AdminException {

		if (e.getErrorCode() == AppsForYourDomainErrorCode.UnknownError) {

			throw new AdminException("The request failed for an unknown reason.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.ServerBusy) {

			throw new AdminException("The server is busy and it could not complete the request.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.UserDeletedRecently) {

			throw new AdminException(
					"The request instructs Google to create a new user but uses the username of an account that was deleted in the previous five days.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.UserSuspended) {

			throw new AdminException("The user identified in the request is suspended.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.DomainUserLimitExceeded) {

			throw new AdminException("The specified domain has already reached its quota of user accounts.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.DomainAliasLimitExceeded) {

			throw new AdminException("The specified domain has already reached its quota of aliases. Aliases include nicknames and email lists.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.DomainSuspended) {

			throw new AdminException("Google has suspended the specified domain's access to Google Apps. ");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.DomainFeatureUnavailable) {

			throw new AdminException("This particular feature is not available for the specified domain.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.EntityExists) {

			throw new AdminException("The request instructs Google to create an entity that already exists.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.EntityDoesNotExist) {

			throw new AdminException("The request asks Google to retrieve an entity that does not exist.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.EntityNameIsReserved) {

			throw new AdminException("The request instructs Google to create an entity with a reserved name, such as 'abuse' or 'postmaster'.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.EntityNameNotValid) {

			throw new AdminException("The request provides an invalid name for a requested resource.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidGivenName) {

			throw new AdminException("The value in the API request for the user's first name, or given name, contains unaccepted characters.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidFamilyName) {

			throw new AdminException("The value in the API request for the user's surname, or family name, contains unaccepted characters.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidPassword) {

			throw new AdminException(
					"The value in the API request for the user's password contains an invalid number of characters or unaccepted characters.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidUsername) {

			throw new AdminException("The value in the API request for the user's username contains unaccepted characters.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidHashFunctionName) {

			throw new AdminException("The specified query parameter value is not valid.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidHashDigestLength) {

			throw new AdminException("The specified password does not comply with the specified hash function.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidEmailAddress) {

			throw new AdminException("The specified email address is not valid.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidQueryParameterValue) {

			throw new AdminException("The specified query parameter value is not valid.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.TooManyRecipientsOnEmailList) {

			throw new AdminException(
					"The request instructs Google to add users to an email list, but that list has already reached the maximum number of subscribers (1000).");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidSsoSigningKey) {

			throw new AdminException("The specified signing key is not valid.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.TooManyNicknamesForUser) {

			throw new AdminException(
					"The request instructs Google to add a nickname to an email address, but that email address has already reached the maximum number of nicknames.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.DuplicateDestinations) {

			throw new AdminException("The destination specified has already been used.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.TooManyDestinations) {

			throw new AdminException("The maximum number of destinations has been reached.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidRouteAddress) {

			throw new AdminException("The routing address specified is invalid.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.GroupCannotContainCycle) {

			throw new AdminException("A group cannot contain a cycle.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidDomainEdition) {

			throw new AdminException("The domain product version is not valid.");
		} else if (e.getErrorCode() == AppsForYourDomainErrorCode.InvalidValue) {

			throw new AdminException("An value specified is not valid.");
		}
	}
}
