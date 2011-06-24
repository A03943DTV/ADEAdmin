/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.codetable;

import java.util.ArrayList;
import java.util.List;

import com.directv.adminuserinterface.client.dialog.NormalDialogBox;
import com.directv.adminuserinterface.shared.Group;
import com.directv.adminuserinterface.shared.Location;
import com.directv.adminuserinterface.shared.ManagersId;
import com.directv.adminuserinterface.shared.Role;
import com.directv.adminuserinterface.shared.SubOrganization;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class CodeTableScreen.
 */
public class CodeTableScreen extends Composite {

	/** The code table service. */
	private final CodeTableServiceAsync codeTableService = GWT.create(CodeTableService.class);

	/** The insert button. */
	private Button insertButton = new Button("Insert Values");

	/** The delete button. */
	private Button deleteButton = new Button("Delete Values");

	/**
	 * Instantiates a new code table screen.
	 */
	public CodeTableScreen() {

		insertButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				insertCodeTableValues();
			}
		});

		deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteCodeTableValues();
			}
		});

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(5);
		hPanel.add(insertButton);
		hPanel.add(deleteButton);

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(hPanel);

		initWidget(vPanel);
	}

	/**
	 * Delete code table values.
	 */
	protected void deleteCodeTableValues() {

		codeTableService.deleteCodeTableValues(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				new NormalDialogBox("Error", "Code table values deletion error : " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				new NormalDialogBox("Success", "All code table values sucessfully deleted");
			}
		});
	}

	/**
	 * Insert code table values.
	 */
	protected void insertCodeTableValues() {

		insertGroups();
		insertLocations();
		insertManagersIds();
		insertRoles();
		insertSubOrganizations();
	}

	/**
	 * Insert sub organizations.
	 */
	private void insertSubOrganizations() {

		List<SubOrganization> subOrganizationsList = new ArrayList<SubOrganization>();
		subOrganizationsList.add(new SubOrganization(1L, "Directv"));
		subOrganizationsList.add(new SubOrganization(2L, "AT&T"));
		subOrganizationsList.add(new SubOrganization(3L, "Convergys"));
		subOrganizationsList.add(new SubOrganization(4L, "Sitel"));

		for (SubOrganization subOrganization : subOrganizationsList) {

			codeTableService.addSubOrganization(subOrganization, new AsyncCallback<SubOrganization>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					new NormalDialogBox("Error", "SubOrganization add error : " + caught.getMessage());
				}

				@Override
				public void onSuccess(SubOrganization result) {
					System.out.println("SubOrganization add success : " + result.getDescription());
				}
			});
		}
	}

	/**
	 * Insert roles.
	 */
	private void insertRoles() {

		List<Role> roleList = new ArrayList<Role>();
		roleList.add(new Role(1L, "CSR"));
		roleList.add(new Role(2L, "Team Lead"));
		roleList.add(new Role(3L, "Manager"));
		roleList.add(new Role(4L, "QA"));
		roleList.add(new Role(5L, "Coach"));
		roleList.add(new Role(6L, "Trainer/Learning Specalist"));

		for (Role role : roleList) {

			codeTableService.addRole(role, new AsyncCallback<Role>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					new NormalDialogBox("Error", "Role add error : " + caught.getMessage());
				}

				@Override
				public void onSuccess(Role result) {
					System.out.println("Role add success : " + result.getDescription());
				}
			});
		}
	}

	/**
	 * Insert managers ids.
	 */
	private void insertManagersIds() {

		List<ManagersId> managersIdList = new ArrayList<ManagersId>();
		managersIdList.add(new ManagersId(1L, "manager1@dtv.com", "Boise"));
		managersIdList.add(new ManagersId(2L, "manager2@dtv.com", "Huntsville"));
		managersIdList.add(new ManagersId(3L, "manager3@dtv.com", "Huntsville"));
		managersIdList.add(new ManagersId(4L, "manager4@dtv.com", "Huntsville"));
		managersIdList.add(new ManagersId(5L, "manager5@dtv.com", "Chattanoga"));
		managersIdList.add(new ManagersId(6L, "manager6@dtv.com", "Chattanoga"));

		for (ManagersId managersId : managersIdList) {

			codeTableService.addManagersId(managersId, new AsyncCallback<ManagersId>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					new NormalDialogBox("Error", "ManagersId add error : " + caught.getMessage());
				}

				@Override
				public void onSuccess(ManagersId result) {
					System.out.println("ManagersId add success : " + result.getDescription());
				}
			});
		}
	}

	/**
	 * Insert locations.
	 */
	private void insertLocations() {

		List<Location> locationList = new ArrayList<Location>();
		locationList.add(new Location(1L, "Huntsville", "Directv"));
		locationList.add(new Location(2L, "Boise", "Convergys"));
		locationList.add(new Location(3L, "Chattanoga", "Sitel"));
		locationList.add(new Location(4L, "Huntsville", "AT&T"));
		locationList.add(new Location(5L, "Boise", "AT&T"));
		locationList.add(new Location(6L, "Chattanoga", "AT&T"));

		for (Location location : locationList) {

			codeTableService.addLocation(location, new AsyncCallback<Location>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					new NormalDialogBox("Error", "Location add error : " + caught.getMessage());
				}

				@Override
				public void onSuccess(Location result) {
					System.out.println("Location add success : " + result.getDescription());
				}
			});
		}
	}

	/**
	 * Insert groups.
	 */
	private void insertGroups() {

		List<Group> groupList = new ArrayList<Group>();
		groupList.add(new Group(1L, "test1"));
		groupList.add(new Group(2L, "test2"));
		groupList.add(new Group(3L, "test3"));
		groupList.add(new Group(4L, "test4"));
		groupList.add(new Group(5L, "test5"));

		for (Group group : groupList) {

			codeTableService.addGroup(group, new AsyncCallback<Group>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					new NormalDialogBox("Error", "Group add error : " + caught.getMessage());
				}

				@Override
				public void onSuccess(Group result) {
					System.out.println("Group add success : " + result.getDescription());
				}
			});
		}
	}
}
