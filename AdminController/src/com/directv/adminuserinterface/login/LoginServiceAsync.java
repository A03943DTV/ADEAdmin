/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.login;

import java.util.List;

import com.directv.adminuserinterface.shared.LoginInfo;
import com.directv.adminuserinterface.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface LoginServiceAsync.
 */
public interface LoginServiceAsync {

	/**
	 * Login.
	 *
	 * @param requestUri the request uri
	 * @param async the async
	 */
	public void login(String requestUri, AsyncCallback<LoginInfo> async);

	/**
	 * Store user list in session.
	 *
	 * @param userList the user list
	 * @param async the async
	 */
	@SuppressWarnings("unchecked")
	public void storeUserListInSession(List<User> userList, AsyncCallback async);

	/**
	 * Gets the user list from session.
	 *
	 * @param async the async
	 * @return the user list from session
	 */
	public void getUserListFromSession(AsyncCallback<List<User>> async);

	/**
	 * Reflect added user in session.
	 *
	 * @param user the user
	 * @param callback the callback
	 */
	public void reflectAddedUserInSession(User user, AsyncCallback<Void> callback);

	/**
	 * Reflect deleted user in session.
	 *
	 * @param user the user
	 * @param callback the callback
	 */
	public void reflectDeletedUserInSession(User user, AsyncCallback<Void> callback);

	/**
	 * Reflect updated user in session.
	 *
	 * @param user the user
	 * @param callback the callback
	 */
	public void reflectUpdatedUserInSession(User user, AsyncCallback<Void> callback);
}