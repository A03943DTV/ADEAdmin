/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server;

import java.util.ArrayList;
import java.util.List;

import com.directv.adminuserinterface.login.LoginService;
import com.directv.adminuserinterface.rest.UserDaoImpl;
import com.directv.adminuserinterface.server.domain.GoogleUserManager;
import com.directv.adminuserinterface.shared.LoginInfo;
import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.AdminException;
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
	 * @throws AdminException 
	 */
	public LoginInfo login(String requestUri) throws AdminException {

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

		List<com.directv.adminuserinterface.shared.User> loginUserDBList = new UserDaoImpl().listUsers(
				com.directv.adminuserinterface.shared.User.USERID_PARAM, user.getEmail());
		try {
			com.directv.adminuserinterface.shared.User userGoogle = new GoogleUserManager().retrieveUser(user.getEmail());
			//Super Admin's are only created in the Domain
			//If a SuperAdmin user is been created in Domain and it won't be in the DB
			//So while login verify is it super admin if so put an entry in the user table
			if (!(loginUserDBList.size() > 0)) {
				if (userGoogle.getAdmin()) {
					userGoogle.setCredential(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER);
					new UserDaoImpl().addUser(userGoogle);
				}
			} else {
				//If an existing user in domain is changed to super admin through domain then it has to be reflected in DB
				if (userGoogle.getAdmin()) {
					if (!loginUserDBList.get(0).getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
						loginUserDBList.get(0).setCredential(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER);
						new UserDaoImpl().updateUserCredential(loginUserDBList.get(0));
					}
				}
			}
		} catch (AdminException e) {
			e.printStackTrace();
			throw e;
		}

		loginInfo.setUser(new UserDaoImpl().getUser(user.getEmail()));
		return loginInfo;
	}

	/**
	 * Gets the user for bulk upload.
	 *
	 * @return the user for bulk upload
	 */
	public com.directv.adminuserinterface.shared.User getUserForBulkUpload() {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		return new UserDaoImpl().getUser(user.getEmail());
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
	 * Overridden Method
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void reflectAddedUserInSession(com.directv.adminuserinterface.shared.User user) {

		List<com.directv.adminuserinterface.shared.User> userListSession = (List<com.directv.adminuserinterface.shared.User>) this
				.getThreadLocalRequest().getSession().getAttribute(AdminConstants.SESSION_DATA_STORE_ATTRIBUTE);
		userListSession.add(0, user);
		storeUserListInSession(userListSession);
	}

	/**
	 * Overridden Method
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void reflectUpdatedUserInSession(com.directv.adminuserinterface.shared.User user) {

		List<com.directv.adminuserinterface.shared.User> userListSession = (List<com.directv.adminuserinterface.shared.User>) this
				.getThreadLocalRequest().getSession().getAttribute(AdminConstants.SESSION_DATA_STORE_ATTRIBUTE);
		for (com.directv.adminuserinterface.shared.User userSession : userListSession) {
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
		storeUserListInSession(userListSession);
	}

	/**
	 * Overridden Method
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void reflectDeletedUserInSession(com.directv.adminuserinterface.shared.User user) {

		List<com.directv.adminuserinterface.shared.User> userToBeRemoved = new ArrayList<com.directv.adminuserinterface.shared.User>();
		List<com.directv.adminuserinterface.shared.User> userListSession = (List<com.directv.adminuserinterface.shared.User>) this
				.getThreadLocalRequest().getSession().getAttribute(AdminConstants.SESSION_DATA_STORE_ATTRIBUTE);

		for (com.directv.adminuserinterface.shared.User userSession : userListSession) {
			if (userSession.getUserId().equals(user.getUserId())) {
				userToBeRemoved.add(userSession);
			}
		}

		if (userToBeRemoved != null && userToBeRemoved.size() > 0) {
			userListSession.remove(userToBeRemoved.get(0));
		}
		storeUserListInSession(userListSession);
	}
}