/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server;

import java.util.List;

import com.directv.adminuserinterface.client.user.UserService;
import com.directv.adminuserinterface.rest.UserDao;
import com.directv.adminuserinterface.rest.UserDaoImpl;
import com.directv.adminuserinterface.server.domain.GoogleOrgManager;
import com.directv.adminuserinterface.server.domain.GoogleUserManager;
import com.directv.adminuserinterface.shared.Group;
import com.directv.adminuserinterface.shared.Location;
import com.directv.adminuserinterface.shared.ManagersId;
import com.directv.adminuserinterface.shared.Role;
import com.directv.adminuserinterface.shared.SubOrganization;
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

		//Creating a new user in the domain
		user = new GoogleUserManager().createDomainUser(user);

		//Creating a new user in the DB
		if (user.getCredential() == null || user.getCredential().equals("")) {
			user.setCredential(AdminConstants.CREDENTIAL_USER);//By default newly created user will have user credential
		}

		//Managing user into organization
		manageOrganizationPath(user, true);

		user = getUserDao().addUser(user);

		verifyIfRoleIsManager(true, user);
		return user;
	}

	/**
	 * Manage organization path.
	 *
	 * @param user the user
	 * @param isInsert the is insert
	 * @throws AdminException the admin exception
	 */
	private void manageOrganizationPath(User user, boolean isInsert) throws AdminException {

		GoogleOrgManager googleOrgManager = new GoogleOrgManager();
		String oldPath = "/";//By default created users will be here in base "/" while newly created
		String newPath = googleOrgManager.createOrgPathFromOrgUnits(user.getOrganization(), user.getSubOrganization(), user.getLocation(), user
				.getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER));

		if (!isInsert) {
			User dbUser = getUserDao().getUser(user.getUserId());
			oldPath = googleOrgManager.createOrgPathFromOrgUnits(dbUser.getOrganization(), dbUser.getSubOrganization(), dbUser.getLocation(), dbUser
					.getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER));
		}
		googleOrgManager.updateOrganizationUser(user.getUserId(), oldPath, newPath);
	}

	/**
	 * Adds the bulk upload user.
	 *
	 * @param user the user
	 * @return the user
	 * @throws AdminException the admin exception
	 */
	public User addBulkUploadUser(User user) throws AdminException {

		UserValidator.validate(user);
		doValidations(user);
		return addUser(null, user);
	}

	/**
	 * Do validations.
	 *
	 * @param userToBeCreated the user to be created
	 * @throws AdminException the admin exception
	 */
	private void doValidations(User userToBeCreated) throws AdminException {

		CodeTableServiceImpl codeTableService = getCodeTableServiceImpl();

		if (userToBeCreated.getOrganization() != null && !userToBeCreated.getOrganization().equals("")
				&& !(AdminConstants.ORGANIZATION_NAME.equals(userToBeCreated.getOrganization()))) {
			throw new AdminException("Invalid Organization");
		}
		if (userToBeCreated.getSubOrganization() != null && !userToBeCreated.getSubOrganization().equals("")
				&& !(codeTableService.getSubOrganizationsList(SubOrganization.DESCRIPTION_PARAM, userToBeCreated.getSubOrganization()).size() > 0)) {
			throw new AdminException("Invalid Vendor");
		}
		if (userToBeCreated.getGroup() != null && !userToBeCreated.getGroup().equals("")
				&& !(codeTableService.getGroupsList(Group.DESCRIPTION_PARAM, userToBeCreated.getGroup()).size() > 0)) {
			throw new AdminException("Invalid Group");
		}
		if (userToBeCreated.getRole() != null && !userToBeCreated.getRole().equals("")
				&& !(codeTableService.getRolesList(Role.DESCRIPTION_PARAM, userToBeCreated.getRole()).size() > 0)) {
			throw new AdminException("Invalid Role");
		}

		List<Location> locationsList = codeTableService.getLocationsList(Location.DESCRIPTION_PARAM, userToBeCreated.getLocation());
		if (userToBeCreated.getLocation() != null && !userToBeCreated.getLocation().equals("")) {
			if (!(locationsList.size() > 0)) {
				throw new AdminException("Invalid Location");
			} else {
				boolean isAvailable = false;
				for (Location location : locationsList) {
					if (location.getSubOrganization() != null && location.getSubOrganization().equals(userToBeCreated.getSubOrganization())) {
						isAvailable = true;
					}
				}
				if (!isAvailable) {
					throw new AdminException("Invalid Vendor/Location combination");
				}
			}
		}

		List<ManagersId> managersIdList = codeTableService.getManagersIdsList(ManagersId.DESCRIPTION_PARAM, userToBeCreated.getManagersId());
		if (userToBeCreated.getManagersId() != null && !userToBeCreated.getManagersId().equals("")) {
			if (!(managersIdList.size() > 0)) {
				throw new AdminException("Invalid ManagersId");
			} else {
				boolean isAvailable = false;
				for (ManagersId managerId : managersIdList) {
					if (managerId.getLocation() != null && managerId.getLocation().equals(userToBeCreated.getLocation())) {
						isAvailable = true;
					}
				}
				if (!isAvailable) {
					throw new AdminException("Invalid Location/ManagersId combination");
				}
			}
		}

		if (userToBeCreated.getCredential() != null && userToBeCreated.getCredential() != "") {

			if (!userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)
					&& !userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER)
					&& !userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_USER)) {
				throw new AdminException("Invalid Privilege");
			}
			if (userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
				throw new AdminException("SuperAdmin user can't be created through ADELite application");
			}
		}

		User userLoggedIn = new LoginServiceImpl().getUserForBulkUpload();
		if (userLoggedIn.getCredential().equalsIgnoreCase(AdminConstants.CREDENTIAL_ADMIN_USER)) {

			if (!userLoggedIn.getSubOrganization().equals(userToBeCreated.getSubOrganization())) {
				throw new AdminException("You don't have privilege to add user to other Vendor");
			}

			if (!userLoggedIn.getLocation().equals(userToBeCreated.getLocation())) {
				throw new AdminException("You don't have privilege to add user to other Location");
			}

			if (userToBeCreated.getCredential() != null && userToBeCreated.getCredential() != "") {
				if (userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER)) {
					throw new AdminException("You don't have privilege to create an Admin user");
				}
			}
		}
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

		//Managing user into organization
		manageOrganizationPath(user, false);

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
	public List<User> listUsers(String location, String subOrganization, String hostPageBaseURL) throws AdminException {

		//Retrieving all the users in a particular location from the DB/Domain[DB Domain both count will be the same]
		return new UserDaoImpl().listUsers(User.LOCATION_PARAM, location, User.SUB_ORG_PARAM, subOrganization);
	}
}
