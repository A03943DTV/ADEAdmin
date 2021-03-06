/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.server;

import java.util.List;
import java.util.Map;

import com.directv.adminuserinterface.client.user.UserService;
import com.directv.adminuserinterface.rest.CodeTableDaoImpl;
import com.directv.adminuserinterface.rest.UserDao;
import com.directv.adminuserinterface.rest.UserDaoImpl;
import com.directv.adminuserinterface.server.domain.GoogleOrgManager;
import com.directv.adminuserinterface.server.domain.GoogleUserManager;
import com.directv.adminuserinterface.shared.Campaign;
import com.directv.adminuserinterface.shared.Group;
import com.directv.adminuserinterface.shared.Location;
import com.directv.adminuserinterface.shared.ManagersId;
import com.directv.adminuserinterface.shared.Role;
import com.directv.adminuserinterface.shared.SubOrganization;
import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.shared.UserRemovalDto;
import com.directv.adminuserinterface.shared.validator.UserValidator;
import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.AdminException;
import com.directv.adminuserinterface.util.EMailIdUtil;
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

		verifyIfRoleIsManagerOrTeamLead(true, user);

		user.setOldUserId(EMailIdUtil.getNameFromEmailId(user.getUserId()));//For update user userId update management

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
		String newPath = googleOrgManager.createOrgPathFromOrgUnits(user.getOrganization(), user.getSubOrganization(), user.getLocation(), (user
				.getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER) || user.getCredential().equals(
				AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)));

		if (!isInsert) {
			User dbUser = getUserDao().getUser(user.getUserId());
			if (dbUser.getOrganization() != null && !dbUser.getOrganization().equals("") && dbUser.getSubOrganization() != null
					&& !dbUser.getSubOrganization().equals("") && dbUser.getLocation() != null && !dbUser.getLocation().equals("")) {
				oldPath = googleOrgManager.createOrgPathFromOrgUnits(dbUser.getOrganization(), dbUser.getSubOrganization(), dbUser.getLocation(),
						(dbUser.getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER) || dbUser.getCredential().equals(
								AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)));
			}
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
		doBUInsertValidations(user);
		return addUser(null, user);
	}

	/**
	 * Do bu insert validations.
	 *
	 * @param userToBeCreated the user to be created
	 * @throws AdminException the admin exception
	 */
	private void doBUInsertValidations(User userToBeCreated) throws AdminException {

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
		if (userToBeCreated.getCampaign() != null && !userToBeCreated.getCampaign().equals("")
				&& !(codeTableService.getCampaignsList(Campaign.DESCRIPTION_PARAM, userToBeCreated.getCampaign()).size() > 0)) {
			throw new AdminException("Invalid Campaign");
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

		List<ManagersId> managersIdList = codeTableService.getManagersIdsList(ManagersId.DESCRIPTION_PARAM, userToBeCreated.getManagersId(), null,
				null);
		if (userToBeCreated.getManagersId() != null && !userToBeCreated.getManagersId().equals("")) {
			if (!(managersIdList.size() > 0)) {
				throw new AdminException("Invalid ManagersId");
			} else {
				boolean isAvailable = false;
				for (ManagersId managerId : managersIdList) {
					if (managerId.getSubOrganization() != null && managerId.getSubOrganization().equals(userToBeCreated.getSubOrganization())
							&& managerId.getLocation() != null && managerId.getLocation().equals(userToBeCreated.getLocation())) {
						isAvailable = true;
					}
				}
				if (!isAvailable) {
					throw new AdminException("Invalid Vendor/Location/ManagersId combination");
				}
			}
		}

		if (userToBeCreated.getCredential() != null && userToBeCreated.getCredential() != "") {

			if (!userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)
					&& !userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER)
					&& !userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_USER)) {
				throw new AdminException("Invalid Privilege");
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
				if (userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER)
						|| userToBeCreated.getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
					throw new AdminException("You don't have privilege to create an Admin/SuperAdmin users");
				}
			}
		}
	}

	/**
	 * Verify if role is manager or team lead.
	 *
	 * @param isAddUser the is add user
	 * @param user the user
	 */
	private void verifyIfRoleIsManagerOrTeamLead(boolean isAddUser, User user) {

		//If the role Manager/TeamLead has been assigned to user then the location and userId has to be entered in ManagersId code table
		if (user.getRole().equals(AdminConstants.MANAGER_ROLE_CONSTANT) || user.getRole().equals(AdminConstants.TEAMLEAD_ROLE_CONSTANT)) {

			CodeTableServiceImpl codeTableService = getCodeTableServiceImpl();
			List<ManagersId> managersIdList = codeTableService.getManagersIdsList(null, null);
			if (isAddUser) {//New user add so it won't be in the code table

				codeTableService.addManagersId(new ManagersId(new Long((new CodeTableDaoImpl().getMaxManagersId() + 1)), user.getUserId(), user
						.getSubOrganization(), user.getLocation()));
			} else {//Editing existing user so there might be a possibility

				boolean managerIsIdInLocationNotExist = true;
				for (ManagersId managersId : managersIdList) {
					if (managersId.getSubOrganization() != null && managersId.getLocation() != null && managersId.getDescription() != null
							&& managersId.getSubOrganization().equals(user.getSubOrganization())
							&& managersId.getLocation().equals(user.getLocation()) && managersId.getDescription().equals(user.getUserId())) {
						managerIsIdInLocationNotExist = false;
					}
				}
				if (managerIsIdInLocationNotExist) {
					codeTableService.addManagersId(new ManagersId(new Long((new CodeTableDaoImpl().getMaxManagersId() + 1)), user.getUserId(), user
							.getSubOrganization(), user.getLocation()));
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
	public UserRemovalDto removeUsers(String hostPageBaseURL, Map<Integer, User> usersToBeDeletedMap) throws AdminException {

		UserRemovalDto userRemovalDto = new UserRemovalDto();

		for (Integer index : usersToBeDeletedMap.keySet()) {
			try {
				removeUser(hostPageBaseURL, usersToBeDeletedMap.get(index));
				userRemovalDto.getRemovedUserIndex().add(index);
			} catch (AdminException excep) {
				userRemovalDto.getErrorMessageList().add(excep.getMessage() + " : " + usersToBeDeletedMap.get(index).getUserId());
			}
		}
		return userRemovalDto;
	}

	/**
	 * Removes the user.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param user the user
	 * @throws AdminException the admin exception
	 */
	public void removeUser(String hostPageBaseURL, User user) throws AdminException {

		if (user.getUserId().equals(AdminConstants.DOMAIN_ADMIN_USER_ID)) {
			throw new AdminException("You can't delete this user since it is the base for all super admin users");
		}

		List<User> userList = new UserDaoImpl().listUsers(User.USERID_PARAM, user.getUserId());

		User userLoggedIn = new LoginServiceImpl().getUserForBulkUpload();
		if (userList.get(0).getUserId().equals(userLoggedIn.getUserId())) {
			throw new AdminException("You don't have privilege to delete your account");
		}

		//Deleting the user from the domain
		user = new GoogleUserManager().deleteDomainUser(user);

		//Deleting the user from the DB
		user = getUserDao().removeUser(user);

		//While deleting the user deleting the managersId from code table
		if (user.getRole().equals(AdminConstants.MANAGER_ROLE_CONSTANT) || user.getRole().equals(AdminConstants.TEAMLEAD_ROLE_CONSTANT)) {
			getCodeTableServiceImpl().deleteManagersIdData(user.getUserId());
		}
	}

	/**
	 * Overridden Method
	 * @param hostPageBaseURL
	 * @param user
	 * @return
	 * @throws AdminException
	 */
	@Override
	public User updateUserIdByCreatingNewUser(String hostPageBaseURL, User user) throws AdminException {

		if (EMailIdUtil.getEmailIdFromName(user.getOldUserId()).equals(AdminConstants.DOMAIN_ADMIN_USER_ID)) {
			throw new AdminException("You can't update userid " + EMailIdUtil.getEmailIdFromName(user.getOldUserId())
					+ " since it is the base for all super admin users");
		}

		//If the user is updating the userId the old userId data has to be deleted and 
		//new userId data has to be created
		//UserId update management

		String oldUserIdToBeDeleted = user.getOldUserId();
		addUser(hostPageBaseURL, user);//Adding the new userId data

		User userObjectToBeDeleted = getUserDao().get(User.class, EMailIdUtil.getEmailIdFromName(oldUserIdToBeDeleted));
		removeUser(hostPageBaseURL, userObjectToBeDeleted); //Deleting the old userId data

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

		if (EMailIdUtil.getEmailIdFromName(user.getUserId()).equals(AdminConstants.DOMAIN_ADMIN_USER_ID)) {
			if (!user.getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
				throw new AdminException("You can't update the super admin privilege of the userid "
						+ EMailIdUtil.getEmailIdFromName(user.getOldUserId()) + " since it is the base for all super admin users");
			}
		}
		if (EMailIdUtil.getEmailIdFromName(user.getUserId()).equals(user.getManagersId())) {
			throw new AdminException("You can't set userid and managersid to be the same");
		}
		User userLoggedIn = new LoginServiceImpl().getUserForBulkUpload();
		if (userLoggedIn.getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER)) {
			List<User> userList = new UserDaoImpl().listUsers(User.USERID_PARAM, EMailIdUtil.getEmailIdFromName(user.getUserId()));
			if ((!userList.get(0).getSubOrganization().equals(userLoggedIn.getSubOrganization()))
					|| (!userList.get(0).getLocation().equals(userLoggedIn.getLocation()))) {
				throw new AdminException(
						"You don't have privilege to update information of user who belongs to another vendor/location. UserId already exists");
			}
		}
		if (userLoggedIn.getUserId().equals(EMailIdUtil.getEmailIdFromName(user.getUserId()))) {
			if (userLoggedIn.getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)
					&& !user.getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
				throw new AdminException("You can't depriciate your privilege");
			}
		}

		//Updating an existing user in the domain
		user = new GoogleUserManager().updateDomainUser(user);

		//Managing user into organization
		manageOrganizationPath(user, false);

		//Updating an existing user in the DB
		user = getUserDao().updateUser(user);

		verifyIfRoleIsManagerOrTeamLead(false, user);

		user.setOldUserId(EMailIdUtil.getNameFromEmailId(user.getUserId()));
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
		List<User> userList = new UserDaoImpl().listUsers(User.LOCATION_PARAM, location, User.SUB_ORG_PARAM, subOrganization);
		for (User user : userList) {
			user.setOldUserId(EMailIdUtil.getNameFromEmailId(user.getUserId()));
		}
		return userList;
	}

	/**
	 * Delete bulk upload user.
	 *
	 * @param user the user
	 * @throws AdminException the admin exception
	 */
	public void deleteBulkUploadUser(User user) throws AdminException {

		doBUDeleteValidations(user);
		removeUser(null, user);
	}

	/**
	 * Do bu delete validations.
	 *
	 * @param user the user
	 * @throws AdminException the admin exception
	 */
	private void doBUDeleteValidations(User user) throws AdminException {

		List<User> userList = new UserDaoImpl().listUsers(User.USERID_PARAM, user.getUserId());

		if (userList == null || !(userList.size() > 0)) {
			throw new AdminException("Invalid UserId/UserId not exists");
		}

		User userLoggedIn = new LoginServiceImpl().getUserForBulkUpload();

		if (userList.get(0).getUserId().equals(userLoggedIn.getUserId())) {
			throw new AdminException("You don't have privilege to delete your account");
		}
		if (userLoggedIn.getCredential().equalsIgnoreCase(AdminConstants.CREDENTIAL_ADMIN_USER)) {

			if (!userLoggedIn.getSubOrganization().equals(userList.get(0).getSubOrganization())) {
				throw new AdminException("You don't have privilege to delete user who belongs to another Vendor");
			}
			if (!userLoggedIn.getLocation().equals(userList.get(0).getLocation())) {
				throw new AdminException("You don't have privilege to delete user who belongs to another Location");
			}
			if (userList.get(0).getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
				throw new AdminException("You don't have privilege to delete super admin user");
			}
		}
	}
}
