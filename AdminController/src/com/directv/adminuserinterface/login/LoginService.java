/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.login;

import java.util.List;

import com.directv.adminuserinterface.shared.LoginInfo;
import com.directv.adminuserinterface.shared.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface LoginService.
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	/**
	 * Login.
	 *
	 * @param requestUri the request uri
	 * @return the login info
	 */
	public LoginInfo login(String requestUri);

	/**
	 * Store user list in session.
	 *
	 * @param userList the user list
	 */
	public void storeUserListInSession(List<User> userList);

	/**
	 * Gets the user list from session.
	 *
	 * @return the user list from session
	 */
	public List<User> getUserListFromSession();

	/**
	 * Reflect deleted user in session.
	 *
	 * @param user the user
	 */
	public void reflectDeletedUserInSession(User user);

	/**
	 * Reflect updated user in session.
	 *
	 * @param user the user
	 */
	public void reflectUpdatedUserInSession(User user);

	/**
	 * Reflect added user in session.
	 *
	 * @param user the user
	 */
	public void reflectAddedUserInSession(User user);
}