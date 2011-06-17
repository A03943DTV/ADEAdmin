/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.rest;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.directv.adminuserinterface.shared.User;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDaoImpl.
 */
public class UserDaoImpl extends GenericDaoImpl implements UserDao {

	/**
	 * Overridden Method
	 * @param user
	 */
	@Override
	public User addUser(User user) {

		return (User) add(user);
	}

	/**
	 * Overridden Method
	 * @param userId
	 * @return
	 */
	@Override
	public User getUser(String userId) {

		return get(User.class, userId);
	}

	/**
	 * Overridden Method
	 * @param location 
	 * @param location 
	 * @return
	 */
	@Override
	public List<User> listUsers(String locationParam, String locationValue) {

		return getList(User.class, locationParam, locationValue);
	}

	/**
	 * Overridden Method
	 * @param user
	 */
	@Override
	public User removeUser(User user) {

		remove(User.class, user.getUserId());
		return user;
	}

	/**
	 * Overridden Method
	 * @param user
	 */
	@Override
	public User updateUser(User user) {

		User userDB = null;
		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		try {

			pm.currentTransaction().begin();
			// We don't have a reference to the selected Product.
			// So we have to look it up first,
			userDB = pm.getObjectById(User.class, user.getUserId());
			userDB.setGroup(user.getGroup());
			userDB.setLocation(user.getLocation());
			userDB.setManagersId(user.getManagersId());
			userDB.setRole(user.getRole());
			userDB.setCampaign(user.getCampaign());
			userDB.setCredential(user.getCredential());

			pm.makePersistent(userDB);
			pm.currentTransaction().commit();

		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}
		return userDB;
	}
}
