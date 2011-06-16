/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.directv.adminuserinterface.shared.User;

// TODO: Auto-generated Javadoc
/**
 * The Class RestService.
 */
@Path("/ade/dtvuser")
public class RestService {

	/**
	 * Rest update.
	 *
	 * @param userid the userid
	 * @param group the group
	 * @param location the location
	 * @param managerid the managerid
	 * @param role the role
	 * @param campaign the campaign
	 * @return the user
	 */
	@GET
	@Path("/restupdate")
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User restUpdate(@QueryParam("userid") String userid, @QueryParam("group") String group, @QueryParam("location") String location,
			@QueryParam("managerid") String managerid, @QueryParam("role") String role, @QueryParam("campaign") String campaign) {

		//TODO : firstName/lastName/credential Param null to be changed
		User user = new User(null, null, userid, group, location, managerid, role, campaign, null);
		UserDao userDao = new UserDaoImpl();
		return userDao.addUser(user);
	}

	/**
	 * Rest delete.
	 *
	 * @param userid the userid
	 * @param group the group
	 * @param location the location
	 * @param managerid the managerid
	 * @param role the role
	 * @param campaign the campaign
	 * @return the string
	 */
	@GET
	@Path("/restdelete")
	public String restDelete(@QueryParam("userid") String userid, @QueryParam("group") String group, @QueryParam("location") String location,
			@QueryParam("managerid") String managerid, @QueryParam("role") String role, @QueryParam("campaign") String campaign) {

		//TODO : firstName/lastName/credential Param null to be changed
		User user = new User(null, null, userid, group, location, managerid, role, campaign, null);
		UserDao userDao = new UserDaoImpl();
		userDao.removeUser(user);
		return "User Deleted Successfully";
	}

	/**
	 * Rest get users.
	 *
	 * @return the list
	 */
	@GET
	@Path("/getusers")
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<User> restGetUsers() {

		UserDao userDao = new UserDaoImpl();
		return userDao.listUsers(null, null);
	}
}
