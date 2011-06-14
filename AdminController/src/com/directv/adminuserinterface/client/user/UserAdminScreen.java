/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.client.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.directv.adminuserinterface.client.dialog.ConfirmDialogBox;
import com.directv.adminuserinterface.client.dialog.LoadingDialogBox;
import com.directv.adminuserinterface.client.dialog.NormalDialogBox;
import com.directv.adminuserinterface.client.table.CustomizedImageCell;
import com.directv.adminuserinterface.login.LoginService;
import com.directv.adminuserinterface.login.LoginServiceAsync;
import com.directv.adminuserinterface.shared.User;
import com.directv.adminuserinterface.util.AdminConstants;
import com.directv.adminuserinterface.util.EMailIdUtil;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
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

	/** The login service. */
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	/** The user service. */
	private final UserServiceAsync userService = GWT.create(UserService.class);

	/** The Constant GROUP_DROP_DOWN_VALUES. */
	private static final String GROUP_DROP_DOWN_VALUES[] = { "", "test" };

	/** The Constant ROLE_DROP_DOWN_VALUES. */
	private static final String ROLE_DROP_DOWN_VALUES[] = { "", "csr", "manager", "teamlead" };

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

	/** The location text field. */
	private TextBox locationTextField = new TextBox();

	/** The managers id label. */
	private Label managersIdLabel = new Label("Manager's Id");

	/** The managers id text field. */
	private TextBox managersIdTextField = new TextBox();

	/** The role label. */
	private Label roleLabel = new Label("Role");

	/** The role drop box. */
	private ListBox roleDropBox = new ListBox(false);

	/** The campaign label. */
	private Label campaignLabel = new Label("Campaign");

	/** The campaign text field. */
	private TextBox campaignTextField = new TextBox();

	/** The search button. */
	private Button searchButton = new Button("Search");

	/** The save or update button. */
	private Button saveOrUpdateButton = new Button("Save/Update");

	/** The clear button. */
	private Button clearButton = new Button("Clear");

	/** The list all users button. */
	private Button listAllUsersButton = new Button("List All Users");

	/** The user table. */
	private CellTable<User> userTable = new CellTable<User>();

	/** The data provider. */
	private ListDataProvider<User> dataProvider = new ListDataProvider<User>();

	/** The list users main from web service. */
	private List<User> listUsersMainFromWebService = new ArrayList<User>();

	/** The edit user index. */
	private Integer editUserIndex;

	/** The loading dialog box. */
	private LoadingDialogBox loadingDialogBox;

	/** The host page base url. */
	private String hostPageBaseURL;

	/** The csv download image. */
	private Image csvDownloadImage = new Image();

	/**
	 * Instantiates a new user admin screen.
	 */
	public UserAdminScreen() {

	}

	/**
	 * Instantiates a new user admin screen.
	 *
	 * @param hostPageBaseURL the host page base url
	 */
	public UserAdminScreen(String hostPageBaseURL) {

		this.hostPageBaseURL = hostPageBaseURL;
		csvDownloadImage.setUrl("images/csv.png");

		loadingDialogBox = new LoadingDialogBox("Loading.....", "Loading user informations..... Please wait for few seconds.....");

		saveOrUpdateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				//Getting user entered values
				User user = getUserFromFrom();

				if (editUserIndex == null) {//Adding new user

					userService.addUser(UserAdminScreen.this.hostPageBaseURL, user, new AsyncCallback<User>() {

						public void onFailure(Throwable caught) {
							System.out.println("User Add Error : " + caught.getMessage());
							new NormalDialogBox("Error", "User Add Error : " + caught.getMessage());
						}

						public void onSuccess(User userAdded) {

							//Adding new user
							//So add that user to the starting of the table
							System.out.println("User Added Successfully : " + userAdded.getUserId());
							dataProvider.getList().add(0, userAdded);
							dataProvider.refresh();//To replicate the change in table
							userTable.setRowCount(dataProvider.getList().size(), true);// For pagination 
							clearFormFields();
							reflectAddedUserInSession(userAdded);
						}
					});
				} else {//Updating existing user

					userService.updateUser(UserAdminScreen.this.hostPageBaseURL, user, new AsyncCallback<User>() {

						public void onFailure(Throwable caught) {
							System.out.println("User Update Error : " + caught.getMessage());
							new NormalDialogBox("Error", "User Update Error : " + caught.getMessage());
						}

						public void onSuccess(User userUpdated) {

							//Updating existing user
							//So update existing user record in the grid
							System.out.println("User Updated Successfully : " + userUpdated.getUserId());
							dataProvider.getList().get(editUserIndex).setFirstName(userUpdated.getFirstName());
							dataProvider.getList().get(editUserIndex).setLastName(userUpdated.getLastName());
							dataProvider.getList().get(editUserIndex).setUserId(userUpdated.getUserId());
							dataProvider.getList().get(editUserIndex).setGroup(userUpdated.getGroup());
							dataProvider.getList().get(editUserIndex).setLocation(userUpdated.getLocation());
							dataProvider.getList().get(editUserIndex).setManagersId(userUpdated.getManagersId());
							dataProvider.getList().get(editUserIndex).setRole(userUpdated.getRole());
							dataProvider.getList().get(editUserIndex).setCampaign(userUpdated.getCampaign());
							dataProvider.refresh();//To replicate the change in table
							userTable.setRowCount(dataProvider.getList().size(), true);// For pagination 
							clearFormFields();
							reflectUpdatedUserInSession(userUpdated);
						}
					});
				}
			}
		});

		clearButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clearFormFields();
			}
		});

		searchButton.addClickHandler(new ClickHandler() {

			/**
			 * Overridden Method
			 * @param event
			 */
			public void onClick(ClickEvent event) {

				//Getting user entered values
				final User user = getUserFromFrom();
				String userId = (user.getUserId() != null && user.getUserId() != "") ? EMailIdUtil.getEmailIdFromName(user.getUserId()) : null;
				user.setUserId(userId);

				loginService.getUserListFromSession(new AsyncCallback<List<User>>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Session Retreival Error : " + caught.getMessage());
						new NormalDialogBox("Error", "Session Retreival Error : " + caught.getMessage());
					}

					@Override
					public void onSuccess(List<User> result) {
						dataProvider.getList().clear();
						dataProvider.getList().addAll(UserSearchHelper.searchUser(result, user));//Adding only the search matched values
						dataProvider.refresh();//To replicate the change in table
						userTable.setRowCount(dataProvider.getList().size(), true);// For pagination 
					}
				});
			}
		});

		listAllUsersButton.addClickHandler(new ClickHandler() {

			/**
			 * Overridden Method
			 * @param event
			 */
			public void onClick(ClickEvent event) {

				loginService.getUserListFromSession(new AsyncCallback<List<User>>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Session Retreival Error : " + caught.getMessage());
						new NormalDialogBox("Error", "Session Retreival Error : " + caught.getMessage());
					}

					@Override
					public void onSuccess(List<User> result) {
						dataProvider.getList().clear();
						dataProvider.getList().addAll(result);
						dataProvider.refresh();//To replicate the change in table
						userTable.setRowCount(dataProvider.getList().size(), true);// For pagination 
						clearFormFields();
					}
				});
			}
		});

		csvDownloadImage.addClickHandler(new ClickHandler() {

			/**
			 * Overridden Method
			 * @param event
			 */
			public void onClick(ClickEvent event) {

				Window.open(GWT.getModuleBaseURL() + "userDownloadServlet", "Download Data", "");
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

		//Export Data Fields
		HorizontalPanel hDownloadPanel = new HorizontalPanel();
		hDownloadPanel.setSpacing(5);
		hDownloadPanel.add(new Label("Export "));
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

		initWidget(vPanel);
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
				new NormalDialogBox("Error", "UserList Session Storage Error : " + caught.getMessage());
			}

			@Override
			public void onSuccess(Object result) {
				System.out.println("UserList Session Storage Success");
			}
		});
	}

	/**
	 * Gets the user from from.
	 *
	 * @return the user from from
	 */
	protected User getUserFromFrom() {

		return new User(firstNameTextField.getValue(), lastNameTextField.getValue(), userIdTextField.getValue(), GROUP_DROP_DOWN_VALUES[groupDropBox
				.getSelectedIndex()], locationTextField.getValue(), managersIdTextField.getValue(), ROLE_DROP_DOWN_VALUES[roleDropBox
				.getSelectedIndex()], campaignTextField.getValue());
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

		userService.listUsers(hostPageBaseURL, new AsyncCallback<List<User>>() {

			public void onFailure(Throwable caught) {
				System.out.println("User List Error : " + caught.getMessage());
				loadingDialogBox.hideLoaderDialog();
				new NormalDialogBox("Error", "User List Error : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<User> listUsers) {
				System.out.println("User List Successfull : " + listUsers.size());
				listUsersMainFromWebService = listUsers;
				List<User> dataProviderList = dataProvider.getList();
				for (User user : listUsersMainFromWebService) {
					dataProviderList.add(user);
				}
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
		Column<User, String> removecolumn = generateRemoveColumn();
		Column<User, String> userIdColumn = generateUserIdColumn();
		Column<User, String> firstNameColumn = generateFirstNameColumn();
		Column<User, String> lastNameColumn = generateLastNameColumn();
		Column<User, String> groupColumn = generateGroupColumn();
		Column<User, String> locationColumn = generateLocationColumn();
		Column<User, String> managersIdColumn = generateManagersIdColumn();
		Column<User, String> roleColumn = generateRoleColumn();
		Column<User, String> campaignColumn = generateCampaignColumn();

		// Set the width of the userTable and put the userTable in fixed width mode.
		userTable.setWidth("1150px", true);

		// Set the width of each column.
		userTable.setColumnWidth(editColumn, 5.0, Unit.PCT);
		userTable.setColumnWidth(removecolumn, 5.0, Unit.PCT);
		userTable.setColumnWidth(userIdColumn, 16, Unit.PCT);
		userTable.setColumnWidth(firstNameColumn, 11.0, Unit.PCT);
		userTable.setColumnWidth(lastNameColumn, 11.0, Unit.PCT);
		userTable.setColumnWidth(groupColumn, 8.0, Unit.PCT);
		userTable.setColumnWidth(locationColumn, 10.0, Unit.PCT);
		userTable.setColumnWidth(managersIdColumn, 16.0, Unit.PCT);
		userTable.setColumnWidth(roleColumn, 9.0, Unit.PCT);
		userTable.setColumnWidth(campaignColumn, 9.0, Unit.PCT);

		dataProvider.addDataDisplay(userTable);
		List<User> dataProviderList = dataProvider.getList();
		for (User user : listUsersMainFromWebService) {
			dataProviderList.add(user);
		}

		//Adding sort handler for header
		addSortHandlersForHeader(dataProviderList, userIdColumn, firstNameColumn, lastNameColumn, groupColumn, locationColumn, managersIdColumn,
				roleColumn, campaignColumn);

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

		addDropDownValues();

		firstNameTextField.setWidth("220px");
		lastNameTextField.setWidth("220px");
		userIdTextField.setWidth("150px");
		locationTextField.setWidth("220px");
		managersIdTextField.setWidth("220px");
		campaignTextField.setWidth("220px");

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(userIdTextField);
		hPanel.add(new Label("@" + AdminConstants.DOMAIN_NAME));

		Grid formGrid = new Grid(4, 4);

		formGrid.setWidget(0, 0, firstNameLabel);
		formGrid.setWidget(0, 1, firstNameTextField);
		formGrid.setWidget(0, 2, lastNameLabel);
		formGrid.setWidget(0, 3, lastNameTextField);
		formGrid.setWidget(1, 0, userIdLabel);
		formGrid.setWidget(1, 1, hPanel);
		formGrid.setWidget(1, 2, groupLabel);
		formGrid.setWidget(1, 3, groupDropBox);
		formGrid.setWidget(2, 0, locationLabel);
		formGrid.setWidget(2, 1, locationTextField);
		formGrid.setWidget(2, 2, managersIdLabel);
		formGrid.setWidget(2, 3, managersIdTextField);
		formGrid.setWidget(3, 0, roleLabel);
		formGrid.setWidget(3, 1, roleDropBox);
		formGrid.setWidget(3, 2, campaignLabel);
		formGrid.setWidget(3, 3, campaignTextField);

		formGrid.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(1, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(2, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(3, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);

		formGrid.setCellSpacing(5);

		return formGrid;
	}

	/**
	 * Adds the drop down values.
	 */
	private void addDropDownValues() {

		for (int i = 0; i < GROUP_DROP_DOWN_VALUES.length; i++) {
			groupDropBox.addItem(GROUP_DROP_DOWN_VALUES[i]);
		}
		for (int i = 0; i < ROLE_DROP_DOWN_VALUES.length; i++) {
			roleDropBox.addItem(ROLE_DROP_DOWN_VALUES[i]);
		}
	}

	/**
	 * Removes the selected data.
	 *
	 * @param index the index
	 * @param user the user
	 */
	protected void removeSelectedData(final int index, final User user) {

		final ConfirmDialogBox confirmDialog = new ConfirmDialogBox();
		Button yesButton = confirmDialog.initializeConfirmDialog("Confirm", "Are you sure you want to delete user : " + user.getUserId());
		yesButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				confirmDialog.hideDialogBox();

				userService.removeUser(hostPageBaseURL, user, new AsyncCallback<User>() {

					public void onFailure(Throwable caught) {
						System.out.println("User Removal Error : " + caught.getMessage());
						new NormalDialogBox("Error", "User Removal Error : " + caught.getMessage());
					}

					public void onSuccess(User userRemoved) {
						System.out.println("User Removed Successfully");
						dataProvider.getList().remove(index);
						dataProvider.refresh();//To replicate the change in table
						userTable.setRowCount(dataProvider.getList().size(), true);
						clearFormFields();
						reflectDeletedUserInSession(user);
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
		if (Arrays.asList(GROUP_DROP_DOWN_VALUES).contains(user.getGroup())) {
			groupDropBox.setSelectedIndex(Arrays.asList(GROUP_DROP_DOWN_VALUES).indexOf(user.getGroup()));
		}
		locationTextField.setText(user.getLocation());
		managersIdTextField.setText(user.getManagersId());
		if (Arrays.asList(ROLE_DROP_DOWN_VALUES).contains(user.getRole())) {
			roleDropBox.setSelectedIndex(Arrays.asList(ROLE_DROP_DOWN_VALUES).indexOf(user.getRole()));
		}
		campaignTextField.setText(user.getCampaign());
		editUserIndex = new Integer(index);//To identify which item in list to be updated
	}

	/**
	 * Clear form fields.
	 */
	protected void clearFormFields() {

		firstNameTextField.setText("");
		lastNameTextField.setText("");
		userIdTextField.setText("");
		groupDropBox.setSelectedIndex(0);
		locationTextField.setText("");
		managersIdTextField.setText("");
		roleDropBox.setSelectedIndex(0);
		campaignTextField.setText("");
		editUserIndex = null;//For safety purpose clear editUserIndex
	}

	/**
	 * Adds the sort handlers for header.
	 *
	 * @param dataProviderList the data provider list
	 * @param userIdColumn the user id column
	 * @param firstNameColumn the first name column
	 * @param lastNameColumn the last name column
	 * @param groupColumn the group column
	 * @param locationColumn the location column
	 * @param managersIdColumn the managers id column
	 * @param roleColumn the role column
	 * @param campaignColumn the campaign column
	 */
	private void addSortHandlersForHeader(List<User> dataProviderList, Column<User, String> userIdColumn, Column<User, String> firstNameColumn,
			Column<User, String> lastNameColumn, Column<User, String> groupColumn, Column<User, String> locationColumn,
			Column<User, String> managersIdColumn, Column<User, String> roleColumn, Column<User, String> campaignColumn) {

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
		userTable.addColumnSortHandler(columnSortHandler);

		// We know that the data is sorted alphabetically by default.
		userTable.getColumnSortList().push(userIdColumn);
	}

	/**
	 * Generate remove column.
	 *
	 * @return the column
	 */
	private Column<User, String> generateRemoveColumn() {

		Column<User, String> removeButtonColumn = new Column<User, String>(new CustomizedImageCell()) {
			@Override
			public String getValue(User object) {
				return "images/remove.png";
			}
		};
		removeButtonColumn.setFieldUpdater(new FieldUpdater<User, String>() {
			@Override
			public void update(int index, User object, String value) {
				removeSelectedData(index, object);
			}
		});
		userTable.addColumn(removeButtonColumn, "Delete");
		return removeButtonColumn;
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
				new NormalDialogBox("Error", "Reflect Delete user from User List in session Error :" + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
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
				new NormalDialogBox("Error", "Reflect Update user in User List in session Error :" + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Reflect Update user in User List in session Success");
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
				new NormalDialogBox("Error", "Reflect Add user in User List in session Error :" + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Reflect Add user in User List in session Success");
			}
		});
	}
}
