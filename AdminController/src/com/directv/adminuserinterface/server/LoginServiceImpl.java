/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.server;

import java.util.ArrayList;
import java.util.List;

import com.directv.adminuserinterface.login.LoginService;
import com.directv.adminuserinterface.shared.LoginInfo;
import com.directv.adminuserinterface.util.AdminConstants;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginServiceImpl.
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -362777790030279549L;

	/**
	 * Overridden Method
	 * @param requestUri
	 * @return
	 */
	public LoginInfo login(String requestUri) {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();

		if (user != null) {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		}
		return loginInfo;
	}

	/**
	 * Overridden Method
	 * @param userList
	 */
	@Override
	public void storeUserListInSession(List<com.directv.adminuserinterface.shared.User> userList) {

		this.getThreadLocalRequest().getSession().setAttribute(AdminConstants.SESSION_DATA_STORE_ATTRIBUTE, userList);
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<com.directv.adminuserinterface.shared.User> getUserListFromSession() {

		return (List<com.directv.adminuserinterface.shared.User>) this.getThreadLocalRequest().getSession().getAttribute(
				AdminConstants.SESSION_DATA_STORE_ATTRIBUTE);
	}

	/**
	 * Reflect added user in session.
	 *
	 * @param user the user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void reflectAddedUserInSession(com.directv.adminuserinterface.shared.User user) {

		((List<com.directv.adminuserinterface.shared.User>) this.getThreadLocalRequest().getSession().getAttribute(
				AdminConstants.SESSION_DATA_STORE_ATTRIBUTE)).add(0, user);
	}

	/**
	 * Reflect updated user in session.
	 *
	 * @param user the user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void reflectUpdatedUserInSession(com.directv.adminuserinterface.shared.User user) {

		for (com.directv.adminuserinterface.shared.User userSession : (List<com.directv.adminuserinterface.shared.User>) this.getThreadLocalRequest()
				.getSession().getAttribute(AdminConstants.SESSION_DATA_STORE_ATTRIBUTE)) {
			if (userSession.getUserId().equals(user.getUserId())) {
				userSession.setFirstName(user.getFirstName());
				userSession.setLastName(user.getLastName());
				userSession.setGroup(user.getGroup());
				userSession.setLocation(user.getLocation());
				userSession.setManagersId(user.getManagersId());
				userSession.setRole(user.getRole());
				userSession.setCampaign(user.getCampaign());
			}
		}
	}

	/**
	 * Reflect deleted user in session.
	 *
	 * @param user the user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void reflectDeletedUserInSession(com.directv.adminuserinterface.shared.User user) {

		List<com.directv.adminuserinterface.shared.User> userToBeRemoved = new ArrayList<com.directv.adminuserinterface.shared.User>();

		for (com.directv.adminuserinterface.shared.User userSession : (List<com.directv.adminuserinterface.shared.User>) this.getThreadLocalRequest()
				.getSession().getAttribute(AdminConstants.SESSION_DATA_STORE_ATTRIBUTE)) {
			if (userSession.getUserId().equals(user.getUserId())) {
				userToBeRemoved.add(userSession);
			}
		}

		((List<com.directv.adminuserinterface.shared.User>) this.getThreadLocalRequest().getSession().getAttribute(
				AdminConstants.SESSION_DATA_STORE_ATTRIBUTE)).remove(userToBeRemoved.get(0));
	}
}