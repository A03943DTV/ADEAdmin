/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class UserList.
 */
@XmlRootElement(name = "userListBase")
public class UserList implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5719109457895268700L;

	/** The Constant USER_LIST_PROPERTY. */
	public static final String USER_LIST_PROPERTY = "userList";

	/** The user list. */
	private List<User> userList = new ArrayList<User>();

	/**
	 * Gets the user list.
	 *
	 * @return the user list
	 */
	@XmlElement
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * Sets the user list.
	 *
	 * @param userList the new user list
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
