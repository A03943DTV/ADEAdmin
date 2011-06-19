/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.rest;

import java.util.List;

import com.directv.adminuserinterface.shared.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserDao.
 */
public interface UserDao extends GenericDao {

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User addUser(User user);

	/**
	 * Removes the user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User removeUser(User user);

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User updateUser(User user);

	/**
	 * List users.
	 *
	 * @param locationParam the location param
	 * @param locationValue the location value
	 * @return the list
	 */
	public List<User> listUsers(String locationParam, String locationValue);

	/**
	 * Gets the user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User getUser(String userId);
}
