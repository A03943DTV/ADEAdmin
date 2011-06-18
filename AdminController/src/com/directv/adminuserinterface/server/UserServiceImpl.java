/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.server;

import java.util.List;

import com.directv.adminuserinterface.client.user.UserService;
import com.directv.adminuserinterface.rest.UserDao;
import com.directv.adminuserinterface.rest.UserDaoImpl;
import com.directv.adminuserinterface.server.domain.GoogleUserManager;
import com.directv.adminuserinterface.shared.ManagersId;
import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.shared.validator.UserValidator;
import com.directv.adminuserinterface.util.AdminConstants;
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
	 * Gets the code table service impl.
	 *
	 * @return the code table service impl
	 */
	public CodeTableServiceImpl getCodeTableServiceImpl() {

		return new CodeTableServiceImpl();
	}

	/**
	 * Overridden Method
	 * @param user
	 * @return
	 * @throws AdminException 
	 */
	@Override
	public User addUser(String hostPageBaseURL, User user) throws AdminException {

		//Validating user for Bulkupload
		UserValidator.validate(user);

		//Creating a new user in the domain
		user = new GoogleUserManager().createDomainUser(user);

		//Creating a new user in the DB
		if (user.getCredential() == null || user.getCredential().equals("")) {
			user.setCredential(AdminConstants.CREDENTIAL_USER);//By default newly created user will have user credential
		}
		user = getUserDao().addUser(user);

		verifyIfRoleIsManager(true, user);
		return user;
	}

	/**
	 * Verify if role is manager.
	 *
	 * @param isAddUser the is add user
	 * @param user the user
	 */
	private void verifyIfRoleIsManager(boolean isAddUser, User user) {

		//If the role Manager has been assigned to user then the location and userId has to be entered in ManagersId code table
		if (user.getRole().equals(AdminConstants.MANAGER_ROLE_CONSTANT)) {

			CodeTableServiceImpl codeTableService = getCodeTableServiceImpl();
			List<ManagersId> managersIdList = codeTableService.getManagersIdsList(null);
			if (isAddUser) {//New user add so it won't be in the code table

				codeTableService.addManagersId(new ManagersId(new Long((managersIdList.size() + 1)), user.getUserId(), user.getLocation()));
			} else {//Editing existing user so there might be a possibility

				boolean managerIsIdInLocationNotExist = true;
				for (ManagersId managersId : managersIdList) {
					if (managersId.getLocation() != null && managersId.getDescription() != null
							&& managersId.getLocation().equals(user.getLocation()) && managersId.getDescription().equals(user.getUserId())) {
						managerIsIdInLocationNotExist = false;
					}
				}
				if (managerIsIdInLocationNotExist) {
					codeTableService.addManagersId(new ManagersId(new Long((managersIdList.size() + 1)), user.getUserId(), user.getLocation()));
				}
			}
		}
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

		//While deleting the user deleting the managersId from code table
		if (user.getRole().equals(AdminConstants.MANAGER_ROLE_CONSTANT)) {
			getCodeTableServiceImpl().deleteManagersIdData(user.getUserId());
		}

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

		verifyIfRoleIsManager(false, user);
		return user;
	}

	/**
	 * Overridden Method
	 * @param location
	 * @param hostPageBaseURL
	 * @return
	 * @throws AdminException
	 */
	@Override
	public List<User> listUsers(String location, String hostPageBaseURL) throws AdminException {

		//Retrieving all the users in a particular location from the DB/Domain[DB Domain both count will be the same]
		return new UserDaoImpl().listUsers(User.LOCATION_PARAM, location);
	}
}
