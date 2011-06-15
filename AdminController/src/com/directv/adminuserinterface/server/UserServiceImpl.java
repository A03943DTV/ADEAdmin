/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.server;

import java.util.List;

import com.directv.adminuserinterface.client.user.UserService;
import com.directv.adminuserinterface.rest.UserDao;
import com.directv.adminuserinterface.rest.UserDaoImpl;
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
	 * Gets the user dao.
	 *
	 * @return the user dao
	 */
	public UserDao getUserDao() {
		
		return new UserDaoImpl();
	}

	/**
	 * Overridden Method
	 * @param user
	 * @return
	 * @throws AdminException 
	 */
	@Override
	public User addUser(String hostPageBaseURL, User user) throws AdminException {

		//Creating a new user in the domain
		user = new GoogleUserManager().createDomainUser(user);
		//Creating a new user in the DB
		user = getUserDao().addUser(user);
		return user;
	}

	/**
	 * Overridden Method
	 * @return
	 * @throws AdminException 
	 */
	@Override
	public List<User> listUsers(String hostPageBaseURL) throws AdminException {

		//Retrieving all the users from the DB/Domain[DB Domain both count will be the same]
		return new UserDaoImpl().listUsers();
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
		user = new GoogleUserManager().deleteDomainUser(user);
		//Deleting the user from the DB
		user = getUserDao().removeUser(user);
		return user;
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
		user = new GoogleUserManager().updateDomainUser(user);
		//Updating an existing user in the DB
		user = getUserDao().updateUser(user);
		return user;
	}
}
