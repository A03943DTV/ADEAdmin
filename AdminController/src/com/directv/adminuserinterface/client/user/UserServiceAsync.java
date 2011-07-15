/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.user;

import java.util.List;
import java.util.Map;

import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.shared.UserRemovalDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserServiceAsync.
 */
public interface UserServiceAsync {

	/**
	 * Adds the user.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param user the user
	 * @param callback the callback
	 */
	void addUser(String hostPageBaseURL, User user, AsyncCallback<User> callback);

	/**
	 * Update user.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param user the user
	 * @param callback the callback
	 */
	void updateUser(String hostPageBaseURL, User user, AsyncCallback<User> callback);

	/**
	 * Removes the users.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param usersToBeDeletedMap the users to be deleted map
	 * @param callback the callback
	 */
	void removeUsers(String hostPageBaseURL, Map<Integer, User> usersToBeDeletedMap, AsyncCallback<UserRemovalDto> callback);

	/**
	 * List users.
	 *
	 * @param location the location
	 * @param subOrganization the sub organization
	 * @param hostPageBaseURL the host page base url
	 * @param callback the callback
	 */
	void listUsers(String location, String subOrganization, String hostPageBaseURL, AsyncCallback<List<User>> callback);

	/**
	 * Update user id by creating new user.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param user the user
	 * @param callback the callback
	 */
	void updateUserIdByCreatingNewUser(String hostPageBaseURL, User user, AsyncCallback<User> callback);
}
