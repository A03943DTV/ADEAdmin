/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.directv.adminuserinterface.client.StatusBarCreator;
import com.directv.adminuserinterface.client.codetable.CodeTableService;
import com.directv.adminuserinterface.client.codetable.CodeTableServiceAsync;
import com.directv.adminuserinterface.client.dialog.ChooseDialogBox;
import com.directv.adminuserinterface.client.dialog.ConfirmDialogBox;
import com.directv.adminuserinterface.client.dialog.LoadingDialogBox;
import com.directv.adminuserinterface.client.dialog.NormalDialogBox;
import com.directv.adminuserinterface.client.table.CustomizedImageCell;
import com.directv.adminuserinterface.login.LoginService;
import com.directv.adminuserinterface.login.LoginServiceAsync;
import com.directv.adminuserinterface.shared.Campaign;
import com.directv.adminuserinterface.shared.Group;
import com.directv.adminuserinterface.shared.Location;
import com.directv.adminuserinterface.shared.LoginInfo;
import com.directv.adminuserinterface.shared.ManagersId;
import com.directv.adminuserinterface.shared.Role;
import com.directv.adminuserinterface.shared.SubOrganization;
import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.shared.UserRemovalDto;
import com.directv.adminuserinterface.shared.validator.UserValidator;
import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.AdminException;
import com.directv.adminuserinterface.util.EMailIdUtil;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserAdminScreen.
 */
public class UserAdminScreen extends Composite {

	/** The logger. */
	public static Logger logger = Logger.getLogger(UserAdminScreen.class.getName());

	/** The login service. */
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	/** The user service. */
	private final UserServiceAsync userService = GWT.create(UserService.class);

	/** The code table service. */
	private final CodeTableServiceAsync codeTableService = GWT.create(CodeTableService.class);

	/** The login info. */
	private LoginInfo loginInfo;

	/** The group drop down array. */
	private String groupDropDownArray[];

	/** The role drop down array. */
	private String roleDropDownArray[];

	/** The location drop down array. */
	private String locationDropDownArray[];

	/** The managers id drop down array. */
	private String managersIdDropDownArray[];

	/** The sub organization drop down array. */
	private String subOrganizationDropDownArray[];

	/** The campaign drop down array. */
	private String campaignDropDownArray[];

	/** The organization label. */
	private Label organizationLabel = new Label("Organization");

	/** The organization text field. */
	private TextBox organizationTextField = new TextBox();

	/** The sub organization label. */
	private Label subOrganizationLabel = new Label("Vendor");

	/** The sub organization drop box. */
	private ListBox subOrganizationDropBox = new ListBox(false);

	/** The credential drop down array. */
	private String credentialDropDownArray[] = { "", AdminConstants.CREDENTIAL_USER, AdminConstants.CREDENTIAL_ADMIN_USER,
			AdminConstants.CREDENTIAL_SUPER_ADMIN_USER };

	/** The first name label. */
	private Label firstNameLabel = new Label("First Name");

	/** The first name text field. */
	private TextBox firstNameTextField = new TextBox();

	/** The last name label. */
	private Label lastNameLabel = new Label("Last Name");

	/** The last name text field. */
	private TextBox lastNameTextField = new TextBox();

	/** The user id label. */
	private Label userIdLabel = new Label("User Id");

	/** The user id text field. */
	private TextBox userIdTextField = new TextBox();

	/** The group label. */
	private Label groupLabel = new Label("Group");

	/** The group drop box. */
	private ListBox groupDropBox = new ListBox(false);

	/** The location label. */
	private Label locationLabel = new Label("Location");

	/** The location drop box. */
	private ListBox locationDropBox = new ListBox(false);

	/** The managers id label. */
	private Label managersIdLabel = new Label("Manager's Id");

	/** The managers id drop box. */
	private ListBox managersIdDropBox = new ListBox(false);

	/** The role label. */
	private Label roleLabel = new Label("Role");

	/** The role drop box. */
	private ListBox roleDropBox = new ListBox(false);

	/** The campaign label. */
	private Label campaignLabel = new Label("Campaign");

	/** The campaign drop box. */
	private ListBox campaignDropBox = new ListBox(false);

	/** The search button. */
	private Button searchButton = new Button("Search");

	/** The save or update button. */
	private Button saveOrUpdateButton = new Button("Save/Update");

	/** The clear button. */
	private Button clearButton = new Button("Clear");

	/** The list all users button. */
	private Button listAllUsersButton = new Button("List All Users");

	/** The delete users button. */
	private Button deleteUsersButton = new Button("Delete Users");

	/** The user table. */
	private CellTable<User> userTable = new CellTable<User>();

	/** The data provider. */
	private ListDataProvider<User> dataProvider = new ListDataProvider<User>();

	/** The list users main from web service. */
	private List<User> listUsersMainFromWebService = new ArrayList<User>();

	/** The edit user index. */
	private Integer editUserIndex;

	/** The old user id for edit processing. */
	private String oldUserIdForEditProcessing;

	/** The loading dialog box. */
	private LoadingDialogBox loadingDialogBox;

	/** The host page base url. */
	private String hostPageBaseURL;

	/** The csv download image. */
	private Image csvDownloadImage = new Image();

	/** The credential lable. */
	private Label credentialLable = new Label("Privilege");

	/** The credential drop box. */
	private ListBox credentialDropBox = new ListBox();

	/** The indexes of users to be removed. */
	private List<Integer> indexesOfUsersToBeRemoved = new ArrayList<Integer>();

	/** The add new user. */
	protected boolean addNewUser;

	/**
	 * Instantiates a new user admin screen.
	 */
	public UserAdminScreen() {

	}

	/**
	 * Instantiates a new user admin screen.
	 *
	 * @param hostPageBaseURL the host page base url
	 * @param loginInfo the login info
	 */
	public UserAdminScreen(String hostPageBaseURL, final LoginInfo loginInfo) {

		this.hostPageBaseURL = hostPageBaseURL;
		this.loginInfo = loginInfo;
		csvDownloadImage.setUrl("images/csv.png");

		loadingDialogBox = new LoadingDialogBox("Loading.....", "Loading user informations..... Please wait for few seconds.....");

		saveOrUpdateButtonManagement();
		clearButtonManagement();
		searchButtonManagement();
		listAllUserButtonManagement();
		deleteUsersButtonManagement();
		csvDownloadImageManagement();

		scheduleTimerForUserListSessionUpdate();

		subOrganizationDropBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				processSubOrganizationChange(false);
			}
		});

		locationDropBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				processLocationChange(false);
			}
		});

		roleDropBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				processRoleChange();
			}
		});

		//Loading DB data and Setting the Columns,Values and Pagination for User Table
		VerticalPanel vTablePanel = loadDataAndSetUserTableValues();

		//Grid that contain form fields
		Grid formGrid = getFormGrid();

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(5);
		hPanel.add(searchButton);
		hPanel.add(saveOrUpdateButton);
		hPanel.add(clearButton);
		hPanel.add(listAllUsersButton);
		hPanel.add(deleteUsersButton);

		//Export Data Fields
		HorizontalPanel hDownloadPanel = new HorizontalPanel();
		hDownloadPanel.setSpacing(5);
		hDownloadPanel.add(csvDownloadImage);

		DockPanel dockPanel = new DockPanel();
		dockPanel.setStyleName("cw-DockPanel");
		dockPanel.setSpacing(4);
		dockPanel.add(hPanel, DockPanel.WEST);
		dockPanel.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
		dockPanel.add(hDownloadPanel, DockPanel.EAST);
		dockPanel.setWidth("950px");

		//Final Panel
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(5);
		vPanel.add(formGrid);
		vPanel.add(dockPanel);
		vPanel.add(vTablePanel);
		vPanel.add(StatusBarCreator.createStatusBar(loginInfo));

		initWidget(vPanel);
	}

	/**
	 * Process role change.
	 */
	protected void processRoleChange() {

		//TeamLead and Manager role's must be tied to the admin privilege
		managePrivilegeBasedOnRole(roleDropDownArray[roleDropBox.getSelectedIndex()]);
	}

	/**
	 * Process location change.
	 *
	 * @param isEditClicked the is edit clicked
	 */
	protected void processLocationChange(boolean isEditClicked) {

		//Since location and managerId are depended dropdown
		getManagersIdFromServiceAndFillDropDown(subOrganizationDropDownArray[subOrganizationDropBox.getSelectedIndex()],
				locationDropDownArray[locationDropBox.getSelectedIndex()], isEditClicked, null);
	}

	/**
	 * Process sub organization change.
	 *
	 * @param isEditClicked the is edit clicked
	 */
	protected void processSubOrganizationChange(boolean isEditClicked) {

		//Since location and managerIds are dependent dropdown
		//Emptyout managerIds drop down when sub organization changed
		emptyOutManagerIds();
		//Since suborganization and location are depended dropdown
		getLocationsFromServiceAndFillDropDown(subOrganizationDropDownArray[subOrganizationDropBox.getSelectedIndex()], isEditClicked, null);
	}

	/**
	 * Manage privilege based on role.
	 *
	 * @param selectedRole the selected role
	 */
	protected void managePrivilegeBasedOnRole(String selectedRole) {

		if (selectedRole != null
				&& (selectedRole.equals(AdminConstants.MANAGER_ROLE_CONSTANT) || selectedRole.equals(AdminConstants.TEAMLEAD_ROLE_CONSTANT))) {
			if (credentialDropBox != null && credentialDropBox.isAttached()) {
				credentialDropBox.setSelectedIndex(2);
			}
		} else {
			if (credentialDropBox != null && credentialDropBox.isAttached()) {
				credentialDropBox.setSelectedIndex(0);
			}
		}
	}

	/**
	 * Schedule timer for user list session update.
	 */
	private void scheduleTimerForUserListSessionUpdate() {

		Timer t = new Timer() {
			@Override
			public void run() {
				process();
			}

			private void process() {

				//If loggedin user is SuperAdmin then display all user not location specific so he can add/edit/remove all users
				//If loggedin user is Admin then location specific so he can add/edit/remove users in that specific location
				String location = null;
				String subOrganization = null;
				if (loginInfo.getUser().getCredential().equalsIgnoreCase(AdminConstants.CREDENTIAL_ADMIN_USER)) {
					location = loginInfo.getUser().getLocation();
					subOrganization = loginInfo.getUser().getSubOrganization();
				}

				System.out.println("Scheduler called at : " + new Date().toString());
				logger.log(Level.INFO, "Scheduler called at : " + new Date().toString());
				userService.listUsers(location, subOrganization, hostPageBaseURL, new AsyncCallback<List<User>>() {
					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Scheduler User List Error : " + caught.getMessage());
						logger.log(Level.SEVERE, "Scheduler User List Error : " + caught.getStackTrace());
					}

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(List<User> listUsers) {
						System.out.println("Scheduler User List Successfull : " + listUsers.size());
						List<User> listUsersNew = getFilteredUsers(listUsers);
						loginService.storeUserListInSession(listUsersNew, new AsyncCallback() {
							@Override
							public void onFailure(Throwable caught) {
								System.out.println("Scheduler UserList Session Storage Error : " + caught.getMessage());
								logger.log(Level.SEVERE, "Scheduler UserList Session Storage Error : " + caught.getStackTrace());
							}

							@Override
							public void onSuccess(Object result) {
								System.out.println("Scheduler UserList Session Storage Success");
							}
						});
					}
				});
			}
		};
		t.schedule(1000); // Schedule the timer delay.
		t.scheduleRepeating(5000);// Schedule the timer repeat interval.
	}

	/**
	 * Gets the filtered users.
	 *
	 * @param listUsers the list users
	 * @return the filtered users
	 */
	protected List<User> getFilteredUsers(List<User> listUsers) {

		List<User> listUsersNew = new ArrayList<User>();
		for (User user : listUsers) {
			boolean add = true;
			if (loginInfo.getUser().getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER)
					&& user.getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
				add = false;//Preventing SuperAdmin to be displayed in grid/table for Admin users
			} else if (loginInfo.getUser().getCredential().equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
				add = true;//SuperAdmin to be displayed in grid/table for SuperAdmin users
			}
			if (add) {
				listUsersNew.add(user);
			}
		}
		return listUsersNew;
	}

	/**
	 * Csv download image management.
	 */
	private void csvDownloadImageManagement() {

		csvDownloadImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				try {
					Window.open(GWT.getModuleBaseURL() + "userDownloadServlet", "", "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * List all user button management.
	 */
	private void listAllUserButtonManagement() {

		listAllUsersButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				loadingDialogBox = new LoadingDialogBox("Processing.....", "Listing users..... Please wait for few seconds.....");
				fillGridWithUsersFromSession();
			}
		});
	}

	/**
	 * Fill grid with users from session.
	 */
	protected void fillGridWithUsersFromSession() {

		loginService.getUserListFromSession(new AsyncCallback<List<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Session Retreival Error : " + caught.getMessage());
				logger.log(Level.SEVERE, "Session Retreival Error : " + caught.getStackTrace());
				new NormalDialogBox("Error", "Session Retreival Error : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<User> result) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(result);
				dataProvider.refresh();//To replicate the change in table
				userTable.setRowCount(dataProvider.getList().size(), true);// For pagination 
				userTable.redraw();
				clearFormFields();
				loadingDialogBox.hideLoaderDialog();
			}
		});
	}

	/**
	 * Delete users button management.
	 */
	private void deleteUsersButtonManagement() {

		deleteUsersButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (indexesOfUsersToBeRemoved.size() <= 0) {
					new NormalDialogBox("Validation Error", "Please select atlease one user to delete.....");
					return;
				}

				final ConfirmDialogBox confirmDialog = new ConfirmDialogBox();
				Button yesButton = confirmDialog.initializeConfirmDialog("Confirm", "Are you sure you want to delete selected users.....");
				yesButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						confirmDialog.hideDialogBox();
						Map<Integer, User> usersToBeDeletedMap = new HashMap<Integer, User>();
						for (Integer index : indexesOfUsersToBeRemoved) {
							usersToBeDeletedMap.put(index, dataProvider.getList().get(index));
						}
						removeSelectedData(usersToBeDeletedMap);
					}
				});
			}
		});
	}

	/**
	 * Search button management.
	 */
	private void searchButtonManagement() {

		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				//Getting user entered values
				final User user = getUserFromForm();
				String userId = (user.getUserId() != null && user.getUserId() != "") ? EMailIdUtil.getEmailIdFromName(user.getUserId()) : null;
				user.setUserId(userId);

				loginService.getUserListFromSession(new AsyncCallback<List<User>>() {
					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Session Retreival Error : " + caught.getMessage());
						logger.log(Level.SEVERE, "Session Retreival Error : " + caught.getStackTrace());
						new NormalDialogBox("Error", "Session Retreival Error : " + caught.getMessage());
					}

					@Override
					public void onSuccess(List<User> result) {
						dataProvider.getList().clear();
						List<User> listUsersNew = getFilteredUsers(result);
						dataProvider.getList().addAll(UserSearchHelper.searchUser(listUsersNew, user));//Adding only the search matched values
						dataProvider.refresh();//To replicate the change in table
						userTable.setRowCount(dataProvider.getList().size(), true);// For pagination
						userTable.redraw();
					}
				});
			}
		});
	}

	/**
	 * Clear button management.
	 */
	private void clearButtonManagement() {

		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clearFormFields();
			}
		});
	}

	/**
	 * Save or update button management.
	 */
	private void saveOrUpdateButtonManagement() {

		saveOrUpdateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				//Getting user entered values
				User user = getUserFromForm();

				//Validating required fields
				try {
					UserValidator.validate(user);
				} catch (AdminException e) {
					new NormalDialogBox("Validation Error", e.getMessage());
					return;
				}

				loadingDialogBox = new LoadingDialogBox("Processing.....", "Processing save/update..... Please wait for few seconds.....");

				addNewUser = false;
				if (editUserIndex != null) {
					//User Info save/update with userId modification/updation
					if (!user.getOldUserId().equals(user.getUserId())) {
						getConfirmationAboutUserIdUpdate(user);
						return;//So that no proceedings after this. Based on their confirmation save/update will be processed.....
					}
				} else {
					addNewUser = true;
				}

				//Normal User Info save/update without userId modification/updation
				if (addNewUser) {//Adding new user
					addNewUserServiceCaller(user);
				} else {//Updating existing user
					updateUserServiceCaller(user);
				}
			}
		});
	}

	/**
	 * Update user service caller.
	 *
	 * @param user the user
	 */
	protected void updateUserServiceCaller(User user) {

		userService.updateUser(UserAdminScreen.this.hostPageBaseURL, user, new AsyncCallback<User>() {

			public void onFailure(Throwable caught) {

				loadingDialogBox.hideLoaderDialog();
				System.out.println("User Update Error : " + caught.getMessage());
				logger.log(Level.SEVERE, "User Update Error : " + caught.getStackTrace());
				new NormalDialogBox("Error", "User Update Error : " + caught.getMessage());
			}

			public void onSuccess(User userUpdated) {
				updateGridUserData(userUpdated);
			}
		});

	}

	/**
	 * Update user id by creating new user service caller.
	 *
	 * @param user the user
	 */
	protected void updateUserIdByCreatingNewUserServiceCaller(User user) {

		userService.updateUserIdByCreatingNewUser(UserAdminScreen.this.hostPageBaseURL, user, new AsyncCallback<User>() {

			public void onFailure(Throwable caught) {

				loadingDialogBox.hideLoaderDialog();
				System.out.println("User's UserId Update Error : " + caught.getMessage());
				logger.log(Level.SEVERE, "User's UserId Update Error : " + caught.getStackTrace());
				new NormalDialogBox("Error", "User's UserId Update Error : " + caught.getMessage());
			}

			public void onSuccess(User userUpdated) {
				updateGridUserData(userUpdated);
			}
		});

	}

	/**
	 * Update grid user data.
	 *
	 * @param userUpdated the user updated
	 */
	protected void updateGridUserData(User userUpdated) {

		//Updating existing user
		//So update existing user record in the grid
		System.out.println("User Updated Successfully : " + userUpdated.getUserId());
		logger.log(Level.INFO, "User Updated Successfully : " + userUpdated.getUserId());
		//While reflectUpdatedUserInSession is success it will fill the grid with new data from session
		reflectUpdatedUserInSession(userUpdated);
		clearFormFields();
	}

	/**
	 * Adds the new user service caller.
	 *
	 * @param user the user
	 */
	protected void addNewUserServiceCaller(final User user) {

		userService.addUser(UserAdminScreen.this.hostPageBaseURL, user, new AsyncCallback<User>() {

			public void onFailure(Throwable caught) {

				//While creating new user if existing user id has been given then we need to update user without throwing error
				if (caught.getMessage().equals("The request instructs Google to create an entity that already exists.")) {
					user.setOldUserId(user.getUserId());
					updateUserServiceCaller(user);
				} else {
					loadingDialogBox.hideLoaderDialog();
					System.out.println("User Add Error : " + caught.getMessage());
					logger.log(Level.SEVERE, "User Add Error : " + caught.getStackTrace());
					new NormalDialogBox("Error", "User Add Error : " + caught.getMessage());
				}
			}

			public void onSuccess(User userAdded) {
				addGridUserData(userAdded);
			}
		});
	}

	/**
	 * Adds the grid user data.
	 *
	 * @param userAdded the user added
	 */
	protected void addGridUserData(User userAdded) {

		//Adding new user
		//So add that user to the starting of the table
		System.out.println("User Added Successfully : " + userAdded.getUserId());
		logger.log(Level.INFO, "User Added Successfully : " + userAdded.getUserId());
		dataProvider.getList().add(0, userAdded);
		dataProvider.refresh();//To replicate the change in table
		userTable.setRowCount(dataProvider.getList().size(), true);// For pagination 
		userTable.redraw();
		clearFormFields();
		reflectAddedUserInSession(userAdded);
		loadingDialogBox.hideLoaderDialog();
	}

	/**
	 * Gets the confirmation about user id update.
	 *
	 * @param user the user
	 * @return the confirmation about user id update
	 */
	protected void getConfirmationAboutUserIdUpdate(final User user) {

		final ChooseDialogBox chooseDialogBox = new ChooseDialogBox("Create New User", "Update Existing User");
		chooseDialogBox.initializeConfirmDialog("Confirm", "You are trying to update the UserId from "
				+ EMailIdUtil.getEmailIdFromName(user.getOldUserId()) + " to " + EMailIdUtil.getEmailIdFromName(user.getUserId())
				+ " Confirm weather you need to create new user with the entered UserId or update existing user with the entered UserId ?");
		chooseDialogBox.getButton1().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				chooseDialogBox.hideDialogBox();
				addNewUserServiceCaller(user);
			}
		});
		chooseDialogBox.getButton2().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				chooseDialogBox.hideDialogBox();
				updateUserIdByCreatingNewUserServiceCaller(user);
			}
		});
		chooseDialogBox.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				chooseDialogBox.hideDialogBox();
				loadingDialogBox.hideLoaderDialog();
			}
		});
	}

	/**
	 * Store users list in session.
	 */
	@SuppressWarnings("unchecked")
	protected void storeUsersListInSession() {

		List<User> userList = new ArrayList<User>();
		if (dataProvider.getList() != null && dataProvider.getList().size() > 0) {
			userList.addAll(dataProvider.getList());
		}

		loginService.storeUserListInSession(userList, new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("UserList Session Storage Error : " + caught.getMessage());
				logger.log(Level.SEVERE, "UserList Session Storage Error : " + caught.getStackTrace());
				new NormalDialogBox("Error", "UserList Session Storage Error : " + caught.getMessage());
			}

			@Override
			public void onSuccess(Object result) {
				System.out.println("UserList Session Storage Success");
			}
		});
	}

	/**
	 * Gets the user from form.
	 *
	 * @return the user from form
	 */
	protected User getUserFromForm() {

		User user = new User(
				firstNameTextField.getValue(),
				lastNameTextField.getValue(),
				userIdTextField.getValue(),
				groupDropDownArray[groupDropBox.getSelectedIndex()],
				organizationTextField.getValue(),
				subOrganizationDropDownArray[subOrganizationDropBox.getSelectedIndex()],
				(locationDropDownArray != null && locationDropBox.getSelectedIndex() >= 0) ? locationDropDownArray[locationDropBox.getSelectedIndex()]
						: "",
				(managersIdDropDownArray != null && managersIdDropBox.getSelectedIndex() >= 0) ? managersIdDropDownArray[managersIdDropBox
						.getSelectedIndex()] : "", roleDropDownArray[roleDropBox.getSelectedIndex()], campaignDropDownArray[campaignDropBox
						.getSelectedIndex()], credentialDropDownArray[credentialDropBox.getSelectedIndex()]);
		user.setOldUserId(oldUserIdForEditProcessing); //For update user userId update management
		return user;

	}

	/**
	 * Load data and set user table values.
	 *
	 * @return the vertical panel
	 */
	private VerticalPanel loadDataAndSetUserTableValues() {

		//Load the UserList data's from DB/WebService
		listUserInfo();

		return setUserTableValues();
	}

	/**
	 * List user info.
	 */
	private void listUserInfo() {

		//If loggedin user is SuperAdmin then display all user not location specific so he can add/edit/remove all users
		//If loggedin user is Admin then location specific so he can add/edit/remove users in that specific location
		String location = null;
		String subOrganization = null;
		if (loginInfo.getUser().getCredential().equalsIgnoreCase(AdminConstants.CREDENTIAL_ADMIN_USER)) {
			location = loginInfo.getUser().getLocation();
			subOrganization = loginInfo.getUser().getSubOrganization();
		}

		userService.listUsers(location, subOrganization, hostPageBaseURL, new AsyncCallback<List<User>>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("User List Error : " + caught.getMessage());
				logger.log(Level.SEVERE, "User List Error : " + caught.getStackTrace());
				loadingDialogBox.hideLoaderDialog();
				new NormalDialogBox("Error", "User List Error : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<User> listUsers) {
				System.out.println("User List Successfull : " + listUsers.size());
				logger.log(Level.INFO, "User List Successfull : " + listUsers.size());
				listUsersMainFromWebService = listUsers;

				List<User> listUsersNew = getFilteredUsers(listUsers);
				dataProvider.getList().clear();
				dataProvider.getList().addAll(listUsersNew);
				dataProvider.refresh();//To replicate the change in table
				userTable.setRowCount(dataProvider.getList().size(), true);// For pagination 
				userTable.redraw();

				loadingDialogBox.hideLoaderDialog();
				storeUsersListInSession();
			}
		});
	}

	/**
	 * Sets the user table values.
	 *
	 * @return the vertical panel
	 */
	protected VerticalPanel setUserTableValues() {

		userTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		//Adding the columns to userTable
		Column<User, String> editColumn = generateEditColumn();
		Column<User, String> resetPasswordColumn = generateResetPasswordColumn();
		Column<User, Boolean> removeColumn = generateRemoveColumn();
		Column<User, String> userIdColumn = generateUserIdColumn();
		Column<User, String> firstNameColumn = generateFirstNameColumn();
		Column<User, String> lastNameColumn = generateLastNameColumn();
		Column<User, String> groupColumn = generateGroupColumn();
		Column<User, String> organizationColumn = generateOrganizationColumn();
		Column<User, String> subOrganizationColumn = generateSubOrganizationColumn();
		Column<User, String> locationColumn = generateLocationColumn();
		Column<User, String> managersIdColumn = generateManagersIdColumn();
		Column<User, String> roleColumn = generateRoleColumn();
		Column<User, String> campaignColumn = generateCampaignColumn();
		Column<User, String> credentialColumn = generateCredentialColumn();

		// Set the width of the userTable and put the userTable in fixed width mode.
		userTable.setWidth("1675px", true);

		// Set the width of each column.
		userTable.setColumnWidth(editColumn, 3.0, Unit.PCT);
		userTable.setColumnWidth(resetPasswordColumn, 4.0, Unit.PCT);
		userTable.setColumnWidth(removeColumn, 4.0, Unit.PCT);
		userTable.setColumnWidth(userIdColumn, 10, Unit.PCT);
		userTable.setColumnWidth(firstNameColumn, 7.0, Unit.PCT);
		userTable.setColumnWidth(lastNameColumn, 7.0, Unit.PCT);
		userTable.setColumnWidth(groupColumn, 5.0, Unit.PCT);
		userTable.setColumnWidth(organizationColumn, 8.0, Unit.PCT);
		userTable.setColumnWidth(subOrganizationColumn, 6.0, Unit.PCT);
		userTable.setColumnWidth(locationColumn, 9.0, Unit.PCT);
		userTable.setColumnWidth(managersIdColumn, 10.0, Unit.PCT);
		userTable.setColumnWidth(roleColumn, 13.0, Unit.PCT);
		userTable.setColumnWidth(campaignColumn, 7.0, Unit.PCT);
		userTable.setColumnWidth(credentialColumn, 7.0, Unit.PCT);

		dataProvider.addDataDisplay(userTable);
		List<User> dataProviderList = dataProvider.getList();
		for (User user : listUsersMainFromWebService) {
			dataProviderList.add(user);
		}

		//Adding sort handler for header
		addSortHandlersForHeader(dataProviderList, userIdColumn, firstNameColumn, lastNameColumn, groupColumn, organizationColumn,
				subOrganizationColumn, locationColumn, managersIdColumn, roleColumn, campaignColumn, credentialColumn);

		//Adding Pagination
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(userTable);
		userTable.setRowCount(listUsersMainFromWebService.size(), true);// For pagination 
		userTable.setRowData(0, listUsersMainFromWebService);
		userTable.setVisibleRange(0, AdminConstants.NO_OF_DATA_PER_PAGE);// No of data per page

		//Adding scroller to the table
		ScrollPanel scrollerPanel = new ScrollPanel(userTable);
		scrollerPanel.setWidth("950px");
		scrollerPanel.setHeight("260px");

		VerticalPanel vTablePanel = new VerticalPanel();
		vTablePanel.add(scrollerPanel);
		vTablePanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		//TODO : Pager not required, So commented as of now.
		//vTablePanel.add(pager);

		return vTablePanel;
	}

	/**
	 * Gets the form grid.
	 *
	 * @return the form grid
	 */
	private Grid getFormGrid() {

		campaignDropBox.getElement().setId("CampaignDropBox");
		groupDropBox.getElement().setId("GroupDropBox");
		locationDropBox.getElement().setId("LocationDropBox");
		managersIdDropBox.getElement().setId("ManagersIdDropBox");
		roleDropBox.getElement().setId("RoleDropBox");
		credentialDropBox.getElement().setId("CredentialDropBox");
		subOrganizationDropBox.getElement().setId("SubOrganizationDropBox");

		//After setting the Element Id's to the DropDown boxes DropDown values has to be loaded
		//Filling up the drop down values from code table
		loadAndFillDropDownValues();

		firstNameTextField.setWidth("205px");
		lastNameTextField.setWidth("205px");
		userIdTextField.setWidth("140px");
		organizationTextField.setWidth("205px");
		campaignDropBox.setWidth("210px");
		groupDropBox.setWidth("210px");
		locationDropBox.setWidth("210px");
		managersIdDropBox.setWidth("210px");
		roleDropBox.setWidth("210px");
		credentialDropBox.setWidth("210px");
		subOrganizationDropBox.setWidth("210px");

		organizationTextField.setEnabled(false);
		organizationTextField.setText(AdminConstants.ORGANIZATION_NAME);

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(userIdTextField);
		hPanel.add(new Label("@" + AdminConstants.DOMAIN_NAME));

		Grid formGrid = null;

		if (loginInfo.getUser().getCredential().equalsIgnoreCase(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
			formGrid = new Grid(6, 4);
		} else {
			formGrid = new Grid(5, 4);
		}

		formGrid.setWidget(0, 0, firstNameLabel);
		formGrid.setWidget(0, 1, firstNameTextField);
		formGrid.setWidget(0, 2, lastNameLabel);
		formGrid.setWidget(0, 3, lastNameTextField);
		formGrid.setWidget(1, 0, userIdLabel);
		formGrid.setWidget(1, 1, hPanel);
		formGrid.setWidget(1, 2, groupLabel);
		formGrid.setWidget(1, 3, groupDropBox);
		formGrid.setWidget(2, 0, organizationLabel);
		formGrid.setWidget(2, 1, organizationTextField);
		formGrid.setWidget(2, 2, subOrganizationLabel);
		formGrid.setWidget(2, 3, subOrganizationDropBox);
		formGrid.setWidget(3, 0, locationLabel);
		formGrid.setWidget(3, 1, locationDropBox);
		formGrid.setWidget(3, 2, managersIdLabel);
		formGrid.setWidget(3, 3, managersIdDropBox);
		formGrid.setWidget(4, 0, roleLabel);
		formGrid.setWidget(4, 1, roleDropBox);
		formGrid.setWidget(4, 2, campaignLabel);
		formGrid.setWidget(4, 3, campaignDropBox);

		if (loginInfo.getUser().getCredential().equalsIgnoreCase(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER)) {
			formGrid.setWidget(5, 0, credentialLable);
			formGrid.setWidget(5, 1, credentialDropBox);
			formGrid.getCellFormatter().setAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		}

		formGrid.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(2, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(3, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(4, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);

		formGrid.setCellSpacing(5);

		return formGrid;
	}

	/**
	 * Removes the selected data.
	 *
	 * @param usersToBeDeletedMap the users to be deleted map
	 */
	protected void removeSelectedData(final Map<Integer, User> usersToBeDeletedMap) {

		loadingDialogBox = new LoadingDialogBox("Processing.....", "Deleting " + usersToBeDeletedMap.keySet().size()
				+ " user(s)..... Please wait for few seconds.....");

		userService.removeUsers(hostPageBaseURL, usersToBeDeletedMap, new AsyncCallback<UserRemovalDto>() {

			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "User Removal Error : " + caught.getStackTrace());
				System.out.println("User Removal Error : " + caught.getMessage());
				loadingDialogBox.hideLoaderDialog();
				new NormalDialogBox("Error", "User Removal Error : " + caught.getMessage());
			}

			public void onSuccess(UserRemovalDto result) {

				System.out.println("User Removed Successfully");
				logger.log(Level.INFO, "User Removed Successfully");

				for (Integer index : result.getRemovedUserIndex()) {
					reflectDeletedUserInSession(usersToBeDeletedMap.get(index));
				}

				fillGridWithUsersFromSession();//Filling the grid with the new data
				clearFormFields();

				if (result.getErrorMessageList().size() > 0) {
					String errorMessage = "";
					for (String error : result.getErrorMessageList()) {
						errorMessage = errorMessage + error + " / ";
					}
					new NormalDialogBox("Error", "User Removal Errors : " + errorMessage.substring(0, errorMessage.length() - 2));
				}
			}
		});
	}

	/**
	 * Reset password.
	 *
	 * @param index the index
	 * @param user the user
	 */
	protected void resetPassword(final int index, final User user) {

		final ConfirmDialogBox confirmDialog = new ConfirmDialogBox();
		Button yesButton = confirmDialog.initializeConfirmDialog("Confirm", "Are you sure you want to reset password for user : " + user.getUserId());
		yesButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				confirmDialog.hideDialogBox();

				loadingDialogBox = new LoadingDialogBox("Processing.....", "Processing password reset..... Please wait for few seconds.....");

				user.setUserId(EMailIdUtil.getNameFromEmailId(user.getUserId()));
				user.setResetPassword(true);

				userService.updateUser(UserAdminScreen.this.hostPageBaseURL, user, new AsyncCallback<User>() {

					public void onFailure(Throwable caught) {

						dataProvider.getList().get(index).setResetPassword(false);
						dataProvider.refresh();//To replicate the change in table
						userTable.setRowCount(dataProvider.getList().size(), true);// For pagination 
						userTable.redraw();

						loadingDialogBox.hideLoaderDialog();

						System.out.println("User Update Password Reset Error : " + caught.getMessage());
						logger.log(Level.SEVERE, "User Update Password Reset Error : " + caught.getStackTrace());
						new NormalDialogBox("Error", "User Update Password Reset Error : " + caught.getMessage());
					}

					public void onSuccess(User userUpdated) {

						System.out.println("User Updated Password Reset Successfully : " + userUpdated.getUserId());
						logger.log(Level.INFO, "User Updated Password Reset Successfully : " + userUpdated.getUserId());

						dataProvider.getList().get(index).setResetPassword(false);
						dataProvider.refresh();//To replicate the change in table
						userTable.setRowCount(dataProvider.getList().size(), true);// For pagination 
						userTable.redraw();

						clearFormFields();
						loadingDialogBox.hideLoaderDialog();
						new NormalDialogBox("Success", userUpdated.getUserId() + " user password resetted sucessfully");
					}
				});
			}
		});
	}

	/**
	 * Edits the selected data.
	 *
	 * @param index the index
	 * @param user the user
	 */
	protected void editSelectedData(int index, User user) {

		firstNameTextField.setText(user.getFirstName());
		lastNameTextField.setText(user.getLastName());
		userIdTextField.setText(EMailIdUtil.getNameFromEmailId(user.getUserId()));

		if (Arrays.asList(groupDropDownArray).contains(user.getGroup())) {
			groupDropBox.setSelectedIndex(Arrays.asList(groupDropDownArray).indexOf(user.getGroup()));
		} else {
			groupDropBox.setSelectedIndex(Arrays.asList(groupDropDownArray).indexOf(0));
		}
		if (Arrays.asList(subOrganizationDropDownArray).contains(user.getSubOrganization())) {
			subOrganizationDropBox.setSelectedIndex(Arrays.asList(subOrganizationDropDownArray).indexOf(user.getSubOrganization()));
		} else {
			subOrganizationDropBox.setSelectedIndex(Arrays.asList(subOrganizationDropDownArray).indexOf(0));
		}

		//Since suborganization and location are dependent dropdown
		getLocationsFromServiceAndFillDropDown(user.getSubOrganization(), true, user);

		//Since location and managerId are depended dropdown
		getManagersIdFromServiceAndFillDropDown(user.getSubOrganization(), user.getLocation(), true, user);

		if (Arrays.asList(roleDropDownArray).contains(user.getRole())) {
			roleDropBox.setSelectedIndex(Arrays.asList(roleDropDownArray).indexOf(user.getRole()));
		} else {
			roleDropBox.setSelectedIndex(Arrays.asList(roleDropDownArray).indexOf(0));
		}
		if (Arrays.asList(credentialDropDownArray).contains(user.getCredential())) {
			credentialDropBox.setSelectedIndex(Arrays.asList(credentialDropDownArray).indexOf(user.getCredential()));
		} else {
			credentialDropBox.setSelectedIndex(Arrays.asList(credentialDropDownArray).indexOf(0));
		}
		if (Arrays.asList(campaignDropDownArray).contains(user.getCampaign())) {
			campaignDropBox.setSelectedIndex(Arrays.asList(campaignDropDownArray).indexOf(user.getCampaign()));
		} else {
			campaignDropBox.setSelectedIndex(Arrays.asList(campaignDropDownArray).indexOf(0));
		}

		oldUserIdForEditProcessing = user.getOldUserId();

		editUserIndex = new Integer(index);//To identify which item in list to be updated
	}

	/**
	 * Clear form fields.
	 */
	protected void clearFormFields() {

		emptyOutManagerIds();
		emptyOutLocation();

		firstNameTextField.setText("");
		lastNameTextField.setText("");
		userIdTextField.setText("");
		verifyLengthAndSetSelelectedValue(groupDropBox, groupDropDownArray, false);
		verifyLengthAndSetSelelectedValue(subOrganizationDropBox, subOrganizationDropDownArray, false);
		verifyLengthAndSetSelelectedValue(managersIdDropBox, managersIdDropDownArray, false);
		verifyLengthAndSetSelelectedValue(roleDropBox, roleDropDownArray, false);
		verifyLengthAndSetSelelectedValue(campaignDropBox, campaignDropDownArray, false);
		verifyLengthAndSetSelelectedValue(credentialDropBox, credentialDropDownArray, false);
		oldUserIdForEditProcessing = null;
		indexesOfUsersToBeRemoved = new ArrayList<Integer>();
		addNewUser = false;
		editUserIndex = null;//For safety purpose clear editUserIndex
	}

	/**
	 * Empty out location.
	 */
	private void emptyOutLocation() {

		//Since dependent on sub organization
		locationDropDownArray = new String[1];
		locationDropDownArray[0] = "";
		locationDropBox.clear();
		for (int i = 0; i < locationDropDownArray.length; i++) {
			locationDropBox.addItem(locationDropDownArray[i]);
		}
	}

	/**
	 * Empty out manager ids.
	 */
	private void emptyOutManagerIds() {

		//Since dependent on location
		managersIdDropDownArray = new String[1];
		managersIdDropDownArray[0] = "";
		managersIdDropBox.clear();
		for (int i = 0; i < managersIdDropDownArray.length; i++) {
			managersIdDropBox.addItem(managersIdDropDownArray[i]);
		}
	}

	/**
	 * Adds the sort handlers for header.
	 *
	 * @param dataProviderList the data provider list
	 * @param userIdColumn the user id column
	 * @param firstNameColumn the first name column
	 * @param lastNameColumn the last name column
	 * @param groupColumn the group column
	 * @param organizationColumn the organization column
	 * @param subOrganizationColumn the sub organization column
	 * @param locationColumn the location column
	 * @param managersIdColumn the managers id column
	 * @param roleColumn the role column
	 * @param campaignColumn the campaign column
	 * @param credentialColumn the credential column
	 */
	private void addSortHandlersForHeader(List<User> dataProviderList, Column<User, String> userIdColumn, Column<User, String> firstNameColumn,
			Column<User, String> lastNameColumn, Column<User, String> groupColumn, Column<User, String> organizationColumn,
			Column<User, String> subOrganizationColumn, Column<User, String> locationColumn, Column<User, String> managersIdColumn,
			Column<User, String> roleColumn, Column<User, String> campaignColumn, Column<User, String> credentialColumn) {

		ListHandler<User> columnSortHandler = new ListHandler<User>(dataProviderList);
		columnSortHandler.setComparator(userIdColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getUserId().compareTo(o2.getUserId()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(firstNameColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getFirstName().compareTo(o2.getFirstName()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(lastNameColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getLastName().compareTo(o2.getLastName()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(groupColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getGroup().compareTo(o2.getGroup()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(organizationColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getOrganization().compareTo(o2.getOrganization()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(subOrganizationColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getSubOrganization().compareTo(o2.getSubOrganization()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(locationColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getLocation().compareTo(o2.getLocation()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(managersIdColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getManagersId().compareTo(o2.getManagersId()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(roleColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getRole().compareTo(o2.getRole()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(campaignColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getCampaign().compareTo(o2.getCampaign()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(credentialColumn, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getCredential().compareTo(o2.getCredential()) : 1;
				}
				return -1;
			}
		});
		userTable.addColumnSortHandler(columnSortHandler);

		// We know that the data is sorted alphabetically by default.
		userTable.getColumnSortList().push(userIdColumn);
	}

	/**
	 * Generate sub organization column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateSubOrganizationColumn() {

		Column<User, String> subOrganizationColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getSubOrganization();
			}
		};
		subOrganizationColumn.setSortable(true);//For sorting
		userTable.addColumn(subOrganizationColumn, "Vendor");
		return subOrganizationColumn;
	}

	/**
	 * Generate organization column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateOrganizationColumn() {

		Column<User, String> organizationColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getOrganization();
			}
		};
		organizationColumn.setSortable(true);//For sorting
		userTable.addColumn(organizationColumn, "Organization");
		return organizationColumn;
	}

	/**
	 * Generate credential column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateCredentialColumn() {

		Column<User, String> credentialColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getCredential();
			}
		};
		credentialColumn.setSortable(true);//For sorting
		userTable.addColumn(credentialColumn, "Privilege");
		return credentialColumn;
	}

	/**
	 * Generate remove column.
	 *
	 * @return the column
	 */
	private Column<User, Boolean> generateRemoveColumn() {

		Column<User, Boolean> removeButtonColumn = new Column<User, Boolean>(new CheckboxCell()) {
			@Override
			public Boolean getValue(User object) {
				return false;
			}
		};
		removeButtonColumn.setFieldUpdater(new FieldUpdater<User, Boolean>() {
			@Override
			public void update(int index, User object, Boolean value) {
				if (value) {
					indexesOfUsersToBeRemoved.add(new Integer(index));
				} else {
					if (indexesOfUsersToBeRemoved.contains(new Integer(index))) {
						indexesOfUsersToBeRemoved.remove(new Integer(index));
					}
				}
			}
		});
		userTable.addColumn(removeButtonColumn, "Delete");
		return removeButtonColumn;
	}

	/**
	 * Generate reset password column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateResetPasswordColumn() {

		Column<User, String> resetPasswordButtonColumn = new Column<User, String>(new CustomizedImageCell()) {
			@Override
			public String getValue(User object) {
				return "images/password.png";
			}
		};
		resetPasswordButtonColumn.setFieldUpdater(new FieldUpdater<User, String>() {
			@Override
			public void update(int index, User object, String value) {
				resetPassword(index, object);
			}
		});
		userTable.addColumn(resetPasswordButtonColumn, "Reset");
		return resetPasswordButtonColumn;
	}

	/**
	 * Generate edit column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateEditColumn() {

		Column<User, String> editButtonColumn = new Column<User, String>(new CustomizedImageCell()) {
			@Override
			public String getValue(User object) {
				return "images/edit.png";
			}
		};
		editButtonColumn.setFieldUpdater(new FieldUpdater<User, String>() {
			@Override
			public void update(int index, User object, String value) {
				editSelectedData(index, object);
			}
		});
		userTable.addColumn(editButtonColumn, "Edit");
		return editButtonColumn;
	}

	/**
	 * Generate campaign column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateCampaignColumn() {

		Column<User, String> campaignColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getCampaign();
			}
		};
		campaignColumn.setSortable(true);//For sorting
		userTable.addColumn(campaignColumn, "Campaign");
		return campaignColumn;
	}

	/**
	 * Generate role column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateRoleColumn() {

		Column<User, String> roleColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getRole();
			}
		};
		roleColumn.setSortable(true);//For sorting
		userTable.addColumn(roleColumn, "Role");
		return roleColumn;
	}

	/**
	 * Generate managers id column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateManagersIdColumn() {

		Column<User, String> managersIdColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getManagersId();
			}
		};
		managersIdColumn.setSortable(true);//For sorting
		userTable.addColumn(managersIdColumn, "Manager's Id");
		return managersIdColumn;
	}

	/**
	 * Generate location column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateLocationColumn() {

		Column<User, String> locationColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getLocation();
			}
		};
		locationColumn.setSortable(true);//For sorting
		userTable.addColumn(locationColumn, "Location");
		return locationColumn;
	}

	/**
	 * Generate group column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateGroupColumn() {

		Column<User, String> groupColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getGroup();
			}
		};
		groupColumn.setSortable(true);//For sorting
		userTable.addColumn(groupColumn, "Group");
		return groupColumn;
	}

	/**
	 * Generate user id column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateUserIdColumn() {

		Column<User, String> userIdColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getUserId();
			}
		};
		userIdColumn.setSortable(true);//For sorting
		userTable.addColumn(userIdColumn, "User Id");
		return userIdColumn;
	}

	/**
	 * Generate last name column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateLastNameColumn() {
		Column<User, String> lastNameColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getLastName();
			}
		};
		lastNameColumn.setSortable(true);//For sorting
		userTable.addColumn(lastNameColumn, "Last Name");
		return lastNameColumn;
	}

	/**
	 * Generate first name column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateFirstNameColumn() {
		Column<User, String> firstNameColumn = new Column<User, String>(new TextCell()) {
			@Override
			public String getValue(User object) {
				return object.getFirstName();
			}
		};
		firstNameColumn.setSortable(true);//For sorting
		userTable.addColumn(firstNameColumn, "First Name");
		return firstNameColumn;
	}

	/**
	 * Reflect deleted user in session.
	 *
	 * @param user the user
	 */
	public void reflectDeletedUserInSession(User user) {
		loginService.reflectDeletedUserInSession(user, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Reflect Delete user from User List in session Error : " + caught.getMessage());
				logger.log(Level.SEVERE, "Reflect Delete user from User List in session Error : " + caught.getStackTrace());
				new NormalDialogBox("Error", "Reflect Delete user from User List in session Error :" + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				logger.log(Level.INFO, "Reflect Delete user from User List in session Success");
				System.out.println("Reflect Delete user from User List in session Success");
			}
		});
	}

	/**
	 * Reflect updated user in session.
	 *
	 * @param user the user
	 */
	public void reflectUpdatedUserInSession(User user) {
		loginService.reflectUpdatedUserInSession(user, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Reflect Update user in User List in session Error : " + caught.getMessage());
				logger.log(Level.SEVERE, "Reflect Update user in User List in session Error : " + caught.getStackTrace());
				new NormalDialogBox("Error", "Reflect Update user in User List in session Error :" + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Reflect Update user in User List in session Success");
				logger.log(Level.INFO, "Reflect Update user in User List in session Success");
				fillGridWithUsersFromSession();//Filling the grid with the new data
			}
		});
	}

	/**
	 * Reflect added user in session.
	 *
	 * @param user the user
	 */
	public void reflectAddedUserInSession(User user) {
		loginService.reflectAddedUserInSession(user, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Reflect Add user in User List in session Error : " + caught.getMessage());
				logger.log(Level.SEVERE, "Reflect Add user in User List in session Error : " + caught.getStackTrace());
				new NormalDialogBox("Error", "Reflect Add user in User List in session Error :" + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Reflect Add user in User List in session Success");
				logger.log(Level.INFO, "Reflect Add user in User List in session Success");
			}
		});
	}

	/**
	 * Load and fill drop down values.
	 */
	private void loadAndFillDropDownValues() {

		loadGroups();
		loadRoles();
		loadCredentials();
		loadSubOrganizations();
		loadCampaigns();
	}

	/**
	 * Load campaigns.
	 */
	private void loadCampaigns() {

		codeTableService.getCampaignsList(new AsyncCallback<List<com.directv.adminuserinterface.shared.Campaign>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				logger.log(Level.SEVERE, "Error retreving campaign list : " + caught.getStackTrace());
				new NormalDialogBox("Error", "Error retreving campaign list : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<Campaign> result) {

				logger.log(Level.INFO, "Campaign list : " + result.size());
				int idx = 1;
				campaignDropDownArray = new String[result.size() + 1];
				campaignDropDownArray[0] = "";
				if (result.size() > 0) {
					for (Campaign campaign : result) {
						campaignDropDownArray[idx] = campaign.getDescription();
						idx++;
					}
				}
				campaignDropBox.clear();
				for (int i = 0; i < campaignDropDownArray.length; i++) {
					campaignDropBox.addItem(campaignDropDownArray[i]);
				}
				verifyLengthAndSetSelelectedValue(campaignDropBox, campaignDropDownArray, false);
			}
		});
	}

	/**
	 * Verify length and set selelected value.
	 *
	 * @param dropBox the drop box
	 * @param dropDownArray the drop down array
	 * @param isEditClicked the is edit clicked
	 */
	protected void verifyLengthAndSetSelelectedValue(ListBox dropBox, String[] dropDownArray, boolean isEditClicked) {

		if (dropDownArray != null && dropDownArray.length == 2) {
			dropBox.setSelectedIndex(1);
			if (dropBox.getElement().getId().equals("SubOrganizationDropBox")) {
				processSubOrganizationChange(isEditClicked);
			} else if (dropBox.getElement().getId().equals("LocationDropBox")) {
				processLocationChange(isEditClicked);
			} else if (dropBox.getElement().getId().equals("RoleDropBox")) {
				processRoleChange();
			}
		} else {
			dropBox.setSelectedIndex(0);
		}
	}

	/**
	 * Load sub organizations.
	 */
	private void loadSubOrganizations() {

		//If logged in user is Admin then allow that user to create only users in that particular suborganization
		if (loginInfo.getUser().getCredential().equalsIgnoreCase(AdminConstants.CREDENTIAL_ADMIN_USER)) {

			subOrganizationDropDownArray = new String[2];
			subOrganizationDropDownArray[0] = "";
			subOrganizationDropDownArray[1] = loginInfo.getUser().getSubOrganization();
			addSubOrgDropDownValues();

		} else {//If logged in user is SuperAdmin then allow that user to create users in all suborganizations

			codeTableService.getSubOrganizationsList(new AsyncCallback<List<SubOrganization>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					logger.log(Level.SEVERE, "Error retreving suborganization list : " + caught.getStackTrace());
					new NormalDialogBox("Error", "Error retreving suborganization list : " + caught.getMessage());
				}

				@Override
				public void onSuccess(List<SubOrganization> result) {

					logger.log(Level.INFO, "SubOrganization list : " + result.size());
					int idx = 1;
					subOrganizationDropDownArray = new String[result.size() + 1];
					subOrganizationDropDownArray[0] = "";
					if (result.size() > 0) {
						for (SubOrganization subOrganization : result) {
							subOrganizationDropDownArray[idx] = subOrganization.getDescription();
							idx++;
						}
					}
					addSubOrgDropDownValues();
				}
			});
		}
	}

	/**
	 * Adds the sub org drop down values.
	 */
	private void addSubOrgDropDownValues() {

		subOrganizationDropBox.clear();
		for (int i = 0; i < subOrganizationDropDownArray.length; i++) {
			subOrganizationDropBox.addItem(subOrganizationDropDownArray[i]);
		}
		verifyLengthAndSetSelelectedValue(subOrganizationDropBox, subOrganizationDropDownArray, false);
	}

	/**
	 * Load credentials.
	 */
	private void loadCredentials() {

		credentialDropBox.clear();
		for (int i = 0; i < credentialDropDownArray.length; i++) {
			credentialDropBox.addItem(credentialDropDownArray[i]);
		}
		verifyLengthAndSetSelelectedValue(credentialDropBox, credentialDropDownArray, false);
	}

	/**
	 * Load roles.
	 */
	private void loadRoles() {

		codeTableService.getRolesList(new AsyncCallback<List<Role>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				logger.log(Level.SEVERE, "Error retreving role list : " + caught.getStackTrace());
				new NormalDialogBox("Error", "Error retreving role list : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<Role> result) {

				logger.log(Level.INFO, "Role list : " + result.size());
				int idx = 1;
				roleDropDownArray = new String[result.size() + 1];
				roleDropDownArray[0] = "";
				if (result.size() > 0) {
					for (Role role : result) {
						roleDropDownArray[idx] = role.getDescription();
						idx++;
					}
				}
				roleDropBox.clear();
				for (int i = 0; i < roleDropDownArray.length; i++) {
					roleDropBox.addItem(roleDropDownArray[i]);
				}
				verifyLengthAndSetSelelectedValue(roleDropBox, roleDropDownArray, false);
			}
		});
	}

	/**
	 * Load groups.
	 */
	private void loadGroups() {

		codeTableService.getGroupsList(new AsyncCallback<List<com.directv.adminuserinterface.shared.Group>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				logger.log(Level.SEVERE, "Error retreving group list : " + caught.getStackTrace());
				new NormalDialogBox("Error", "Error retreving group list : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<Group> result) {

				logger.log(Level.INFO, "Group list : " + result.size());
				int idx = 1;
				groupDropDownArray = new String[result.size() + 1];
				groupDropDownArray[0] = "";
				if (result.size() > 0) {
					for (Group group : result) {
						groupDropDownArray[idx] = group.getDescription();
						idx++;
					}
				}
				groupDropBox.clear();
				for (int i = 0; i < groupDropDownArray.length; i++) {
					groupDropBox.addItem(groupDropDownArray[i]);
				}
				verifyLengthAndSetSelelectedValue(groupDropBox, groupDropDownArray, false);
			}
		});
	}

	/**
	 * Gets the locations from service and fill drop down.
	 *
	 * @param subOrganization the sub organization
	 * @param isEditClicked the is edit clicked
	 * @param user the user
	 * @return the locations from service and fill drop down
	 */
	private void getLocationsFromServiceAndFillDropDown(String subOrganization, final boolean isEditClicked, final User user) {

		//If logged in user is Admin then allow that user to create only users in that particular location
		if (loginInfo.getUser().getCredential().equalsIgnoreCase(AdminConstants.CREDENTIAL_ADMIN_USER)) {

			locationDropDownArray = new String[2];
			locationDropDownArray[0] = "";
			locationDropDownArray[1] = loginInfo.getUser().getLocation();
			addlocDropDownValues();
			if (isEditClicked) {
				if (Arrays.asList(locationDropDownArray).contains(user.getLocation())) {
					locationDropBox.setSelectedIndex(Arrays.asList(locationDropDownArray).indexOf(user.getLocation()));
				} else {
					locationDropBox.setSelectedIndex(Arrays.asList(locationDropDownArray).indexOf(0));
				}
			}
			if (!isEditClicked) {
				verifyLengthAndSetSelelectedValue(locationDropBox, locationDropDownArray, isEditClicked);
			}

		} else {//If logged in user is SuperAdmin then allow that user to create users in all location

			final LoadingDialogBox loadingDialogBox = new LoadingDialogBox("Loading.....", "Loading locations..... Please wait for few seconds.....");

			codeTableService.getLocationsList(subOrganization, new AsyncCallback<List<Location>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					logger.log(Level.SEVERE, "Error retreving location list : " + caught.getStackTrace());
					loadingDialogBox.hideLoaderDialog();
					new NormalDialogBox("Error", "Error retreving location list : " + caught.getMessage());
				}

				@Override
				public void onSuccess(List<Location> result) {

					logger.log(Level.INFO, "Location list : " + result.size());
					int idx = 1;
					locationDropDownArray = new String[result.size() + 1];
					locationDropDownArray[0] = "";
					if (result.size() > 0) {
						for (Location location : result) {
							locationDropDownArray[idx] = location.getDescription();
							idx++;
						}
					}
					addlocDropDownValues();
					//If user clicked on edit icon in table then for a suborganization loading locations
					//And making corresponding location selected
					if (isEditClicked) {
						if (Arrays.asList(locationDropDownArray).contains(user.getLocation())) {
							locationDropBox.setSelectedIndex(Arrays.asList(locationDropDownArray).indexOf(user.getLocation()));
						} else {
							locationDropBox.setSelectedIndex(Arrays.asList(locationDropDownArray).indexOf(0));
						}
					}
					if (!isEditClicked) {
						verifyLengthAndSetSelelectedValue(locationDropBox, locationDropDownArray, isEditClicked);
					}
					loadingDialogBox.hideLoaderDialog();
				}
			});
		}
	}

	/**
	 * Addloc drop down values.
	 */
	private void addlocDropDownValues() {

		locationDropBox.clear();
		for (int i = 0; i < locationDropDownArray.length; i++) {
			locationDropBox.addItem(locationDropDownArray[i]);
		}
	}

	/**
	 * Gets the managers id from service and fill drop down.
	 *
	 * @param subOrganization the sub organization
	 * @param location the location
	 * @param isEditClicked the is edit clicked
	 * @param user the user
	 * @return the managers id from service and fill drop down
	 */
	private void getManagersIdFromServiceAndFillDropDown(String subOrganization, String location, final boolean isEditClicked, final User user) {

		final LoadingDialogBox loadingDialogBox = new LoadingDialogBox("Loading.....", "Loading managerid's..... Please wait for few seconds.....");

		codeTableService.getManagersIdsList(subOrganization, location, new AsyncCallback<List<ManagersId>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				logger.log(Level.SEVERE, "Error retreving managersId list : " + caught.getStackTrace());
				loadingDialogBox.hideLoaderDialog();
				new NormalDialogBox("Error", "Error retreving managersId list : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<ManagersId> result) {

				logger.log(Level.INFO, "ManagersId list : " + result.size());
				int idx = 1;
				managersIdDropDownArray = new String[result.size() + 1];
				managersIdDropDownArray[0] = "";
				if (result.size() > 0) {
					for (ManagersId managersId : result) {
						managersIdDropDownArray[idx] = managersId.getDescription();
						idx++;
					}
				}
				managersIdDropBox.clear();
				String elementToBeRemoved = null;
				for (int i = 0; i < managersIdDropDownArray.length; i++) {
					//If the SuperAdmin is creating a new user with manager role the user will be added in the grid
					//And userId/Location will be added in the ManagersId code table
					//As soon as he created if he edit that user then in the managers id drop down he will get the 
					//created userid as managers id since there is an entry in the code table so preventing that to be added in dropdown
					//Wile editing there will be a possibility that userId and managersId be the same that should be prevented
					if (isEditClicked) {
						if (!managersIdDropDownArray[i].equals(user.getUserId())) {
							managersIdDropBox.addItem(managersIdDropDownArray[i]);
						} else {
							elementToBeRemoved = managersIdDropDownArray[i];
						}
					} else {
						managersIdDropBox.addItem(managersIdDropDownArray[i]);
					}
				}
				if (elementToBeRemoved != null) {
					managersIdDropDownArray = removeElement(managersIdDropDownArray, elementToBeRemoved);
				}
				//If user clicked on edit icon in table then for a location then loading managersIds for that location
				//And making corresponding managersId selected
				if (isEditClicked) {
					if (Arrays.asList(managersIdDropDownArray).contains(user.getManagersId())) {
						managersIdDropBox.setSelectedIndex(Arrays.asList(managersIdDropDownArray).indexOf(user.getManagersId()));
					} else {
						managersIdDropBox.setSelectedIndex(Arrays.asList(managersIdDropDownArray).indexOf(0));
					}
				}
				if (!isEditClicked) {
					//While editing user however each user will have some managerId so default selection is not necessary
					verifyLengthAndSetSelelectedValue(managersIdDropBox, managersIdDropDownArray, isEditClicked);
					//If loggedin user's role is Manager/TeamLead select that loggedin users managers'Id by default.
					if (managersIdDropBox.getItemCount() == managersIdDropDownArray.length) {
						for (int j = 0; j < managersIdDropDownArray.length; j++) {
							if (managersIdDropDownArray[j].equals(loginInfo.getEmailAddress())) {
								managersIdDropBox.setSelectedIndex(j);
							}
						}
					}
				}
				loadingDialogBox.hideLoaderDialog();
			}
		});
	}

	/**
	 * Removes the element.
	 *
	 * @param input the input
	 * @param deleteMe the delete me
	 * @return the string[]
	 */
	private String[] removeElement(String[] input, String deleteMe) {

		List<String> result = new ArrayList<String>();
		for (String item : input) {
			if (!deleteMe.equals(item)) {
				result.add(item);
			}
		}
		return result.toArray(input);
	}

}
