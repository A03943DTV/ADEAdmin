/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
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
	 * @param param1
	 * @param value1
	 * @return
	 */
	@Override
	public List<User> listUsers(String param1, String value1) {

		return getList(User.class, param1, value1);
	}

	/**
	 * Overridden Method
	 * @param param1
	 * @param value1
	 * @param param2
	 * @param value2
	 * @return
	 */
	@Override
	public List<User> listUsers(String param1, String value1, String param2, String value2) {

		return getList(User.class, param1, value1, param2, value2);
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
			userDB = pm.getObjectById(User.class, user.getUserId());

			userDB = user.copyData(userDB);

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

	/**
	 * Overridden Method
	 * @param user
	 */
	@Override
	public User updateUserCredential(User user) {

		User userDB = null;
		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		try {

			pm.currentTransaction().begin();
			userDB = pm.getObjectById(User.class, user.getUserId());
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
