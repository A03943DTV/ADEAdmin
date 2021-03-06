/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client;

import com.directv.adminuserinterface.client.bulkupload.BulkUploadScreen;
import com.directv.adminuserinterface.client.dialog.ConfirmDialogBox;
import com.directv.adminuserinterface.client.dialog.NormalDialogBox;
import com.directv.adminuserinterface.client.user.UserAdminScreen;
import com.directv.adminuserinterface.login.LoginService;
import com.directv.adminuserinterface.login.LoginServiceAsync;
import com.directv.adminuserinterface.shared.LoginInfo;
import com.directv.adminuserinterface.util.AdminConstants;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminController.
 */
public class AdminController implements EntryPoint {

	/** The Constant CONTAINER. */
	private static final String CONTAINER = "container";

	/** The login service. */
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	/** The login info. */
	private LoginInfo loginInfo = null;

	/** The logout button. */
	private Button logoutButton = new Button("Logout");

	/**
	 * Overridden Method
	 */
	@Override
	public void onModuleLoad() {

		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {

			/**
			 * Overridden Method
			 * @param error
			 */
			public void onFailure(Throwable error) {
				new NormalDialogBox("Error", "DTVUser Login Error : " + error.getMessage());
			}

			public void onSuccess(LoginInfo result) {

				loginInfo = result;
				if (loginInfo.isLoggedIn()
						&& (loginInfo.getUser().getCredential().equals(AdminConstants.CREDENTIAL_ADMIN_USER) || loginInfo.getUser().getCredential()
								.equals(AdminConstants.CREDENTIAL_SUPER_ADMIN_USER))) {

					setMainScreen();
				} else {

					final ConfirmDialogBox confirmDialog = new ConfirmDialogBox();
					Button yesButton = confirmDialog.initializeConfirmDialog("Credential Error",
							"You don't have permission to access this application contact ADELite admin, Click Yes to logout");
					yesButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							confirmDialog.hideDialogBox();
							Window.Location.replace(loginInfo.getLogoutUrl());
						}
					});
				}
			}
		});
	}

	/**
	 * Sets the main screen.
	 */
	private void setMainScreen() {

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(5);

		//Header Directv Image
		Image headerImage = new Image();
		headerImage.setUrl("images/header.jpg");
		headerImage.setHeight("85px");
		headerImage.setWidth("1000px");

		//Displaying the loggedin userId and logout button
		DockPanel dockPanel = new DockPanel();
		dockPanel.setStyleName("cw-DockPanel");
		dockPanel.setSpacing(4);
		dockPanel.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
		dockPanel.add(logoutButton, DockPanel.EAST);
		dockPanel.add(new Label(loginInfo.getEmailAddress()), DockPanel.WEST);
		dockPanel.setWidth("1000px");
		dockPanel.setHeight("40px");

		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.replace(loginInfo.getLogoutUrl());
			}
		});

		//Decorative Tab Panel
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("1000px");
		tabPanel.setAnimationEnabled(true);

		//User Administration Tab
		tabPanel.add(new UserAdminScreen(GWT.getHostPageBaseURL(), loginInfo), "User Administration");

		//Bulk Upload Tab
		tabPanel.add(new BulkUploadScreen(loginInfo), "Bulk upload");

		//Code Table Tab
		//tabPanel.add(new CodeTableScreen(), "Code Table");

		tabPanel.selectTab(0);

		vPanel.add(headerImage);
		vPanel.add(dockPanel);
		vPanel.add(tabPanel);

		RootPanel.get(CONTAINER).add(vPanel);
	}
}
