/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.client.user;

import java.util.ArrayList;
import java.util.List;

import com.directv.adminuserinterface.shared.User;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSearchHelper.
 */
public class UserSearchHelper {

	/** The Constant NO_OF_SEARCH_FIELDS. */
	private static final int NO_OF_SEARCH_FIELDS = 9;

	/**
	 * Search user.
	 *
	 * @param gridUsersList the grid users list
	 * @param userSearch the user search
	 * @return the list
	 */
	public static List<User> searchUser(List<User> gridUsersList, User userSearch) {

		List<User> filteredList = new ArrayList<User>();

		for (User gridUser : gridUsersList) {

			int count = 0;

			if (userSearch.getUserId() != null && userSearch.getUserId() != "") {
				if (((User) gridUser).getUserId().equals(userSearch.getUserId())) {
					count++;
				}
			} else {
				count++;
			}
			if (userSearch.getFirstName() != null && userSearch.getFirstName() != "") {
				if (((User) gridUser).getFirstName().contains(userSearch.getFirstName())) {
					count++;
				}
			} else {
				count++;
			}
			if (userSearch.getLastName() != null && userSearch.getLastName() != "") {
				if (((User) gridUser).getLastName().contains(userSearch.getLastName())) {
					count++;
				}
			} else {
				count++;
			}
			if (userSearch.getGroup() != null && userSearch.getGroup() != "") {
				if (((User) gridUser).getGroup().equals(userSearch.getGroup())) {
					count++;
				}
			} else {
				count++;
			}
			if (userSearch.getLocation() != null && userSearch.getLocation() != "") {
				if (((User) gridUser).getLocation().equals(userSearch.getLocation())) {
					count++;
				}
			} else {
				count++;
			}
			if (userSearch.getManagersId() != null && userSearch.getManagersId() != "") {
				if (((User) gridUser).getManagersId().equals(userSearch.getManagersId())) {
					count++;
				}
			} else {
				count++;
			}
			if (userSearch.getRole() != null && userSearch.getRole() != "") {
				if (((User) gridUser).getRole().equals(userSearch.getRole())) {
					count++;
				}
			} else {
				count++;
			}
			if (userSearch.getCampaign() != null && userSearch.getCampaign() != "") {
				if (((User) gridUser).getCampaign().equals(userSearch.getCampaign())) {
					count++;
				}
			} else {
				count++;
			}
			if (userSearch.getCredential() != null && userSearch.getCredential() != "") {
				if (((User) gridUser).getCredential().equals(userSearch.getCredential())) {
					count++;
				}
			} else {
				count++;
			}

			if (count == NO_OF_SEARCH_FIELDS) {
				filteredList.add(gridUser);
			}
		}
		return filteredList;
	}
}
