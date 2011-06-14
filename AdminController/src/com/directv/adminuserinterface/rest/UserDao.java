/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.rest;

import java.util.List;

import com.directv.adminuserinterface.shared.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserDao.
 */
public interface UserDao {

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
	 * @return the list
	 */
	public List<User> listUsers();
}
