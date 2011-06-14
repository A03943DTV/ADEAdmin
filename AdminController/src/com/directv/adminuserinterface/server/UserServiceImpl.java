/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.server;

import java.util.List;

import com.directv.adminuserinterface.client.user.UserService;
import com.directv.adminuserinterface.server.domain.GoogleUserManager;
import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.util.AdminException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class UserServiceImpl.
 */
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2992949632327674836L;

	/**
	 * Overridden Method
	 * @param user
	 * @return
	 * @throws AdminException 
	 */
	@Override
	public User addUser(String hostPageBaseURL, User user) throws AdminException {

		//Creating a new user in the domain
		return new GoogleUserManager().createDomainUser(user);
	}

	/**
	 * Overridden Method
	 * @return
	 * @throws AdminException 
	 */
	@Override
	public List<User> listUsers(String hostPageBaseURL) throws AdminException {

		//Retrieving all the users from the domain
		return new GoogleUserManager().retrieveAllUsersInDomain();
	}

	/**
	 * Overridden Method
	 * @param user
	 * @return
	 * @throws AdminException 
	 */
	@Override
	public User removeUser(String hostPageBaseURL, User user) throws AdminException {

		//Deleting the user from the domain
		return new GoogleUserManager().deleteDomainUser(user);
	}

	/**
	 * Overridden Method
	 * @param hostPageBaseURL
	 * @param user
	 * @return
	 * @throws AdminException
	 */
	@Override
	public User updateUser(String hostPageBaseURL, User user) throws AdminException {
		
		//Updating an existing user in the domain
		return new GoogleUserManager().updateDomainUser(user);
	}
}
