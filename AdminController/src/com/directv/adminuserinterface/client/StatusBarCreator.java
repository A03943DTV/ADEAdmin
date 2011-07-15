/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client;

import com.directv.adminuserinterface.shared.LoginInfo;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class StatusBarCreator.
 */
public class StatusBarCreator {

	/**
	 * Creates the status bar.
	 *
	 * @param loginInfo the login info
	 * @return the decorator panel
	 */
	public static DecoratorPanel createStatusBar(LoginInfo loginInfo) {

		Grid formGrid = new Grid(1, 10);

		formGrid.setWidget(0, 0, new Label("UserId : "));
		formGrid.setWidget(0, 1, new Label(loginInfo.getEmailAddress() + " | "));
		formGrid.setWidget(0, 2, new Label("Role : "));
		formGrid.setWidget(0, 3, new Label(loginInfo.getUser().getRole() + " | "));
		formGrid.setWidget(0, 4, new Label("Privilege : "));
		formGrid.setWidget(0, 5, new Label(loginInfo.getUser().getCredential() + " | "));
		formGrid.setWidget(0, 6, new Label("Location : "));
		formGrid.setWidget(0, 7, new Label(loginInfo.getUser().getLocation() + " | "));
		formGrid.setWidget(0, 8, new Label("Group : "));
		formGrid.setWidget(0, 9, new Label(loginInfo.getUser().getGroup() + " | "));

		formGrid.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(0, 4, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(0, 6, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(0, 8, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);

		formGrid.setCellSpacing(10);
		formGrid.setWidth("940px");

		DecoratorPanel decPanel = new DecoratorPanel();
		decPanel.setWidget(formGrid);

		return decPanel;
	}
}
