/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.client.user;

import java.util.List;

import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.util.AdminException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserService.
 */
@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

	/**
	 * Adds the user.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param user the user
	 * @return the user
	 * @throws AdminException the admin exception
	 */
	User addUser(String hostPageBaseURL, User user) throws AdminException;

	/**
	 * Update user.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param user the user
	 * @return the user
	 * @throws AdminException the admin exception
	 */
	User updateUser(String hostPageBaseURL, User user) throws AdminException;

	/**
	 * List users.
	 *
	 * @param location the location
	 * @param hostPageBaseURL the host page base url
	 * @return the list
	 * @throws AdminException the admin exception
	 */
	List<User> listUsers(String location, String hostPageBaseURL) throws AdminException;

	/**
	 * Removes the user.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param user the user
	 * @return the user
	 * @throws AdminException the admin exception
	 */
	User removeUser(String hostPageBaseURL, User user) throws AdminException;
}
