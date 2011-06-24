/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.user;

import java.util.List;

import com.directv.adminuserinterface.shared.User;
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
	 * Removes the user.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param user the user
	 * @param callback the callback
	 */
	void removeUser(String hostPageBaseURL, User user, AsyncCallback<User> callback);

	/**
	 * List users.
	 *
	 * @param location the location
	 * @param subOrganization the sub organization
	 * @param hostPageBaseURL the host page base url
	 * @param callback the callback
	 */
	void listUsers(String location, String subOrganization, String hostPageBaseURL, AsyncCallback<List<User>> callback);
}
