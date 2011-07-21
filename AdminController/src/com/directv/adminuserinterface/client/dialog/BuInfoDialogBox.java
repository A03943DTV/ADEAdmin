/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class BuInfoDialogBox.
 */
public class BuInfoDialogBox {

	/** The dialog box. */
	private DialogBox dialogBox = new DialogBox();

	/** The close button. */
	private Button closeButton = new Button("Close");

	/** The dialog content. */
	private VerticalPanel dialogContent = new VerticalPanel();

	/**
	 * Instantiates a new bu info dialog box.
	 *
	 * @param title the title
	 * @param vTablePanelInfo the v table panel info
	 */
	public BuInfoDialogBox(String title, VerticalPanel vTablePanelInfo) {
		initializeNormalDialog(title, vTablePanelInfo);
	}

	/**
	 * Initialize normal dialog.
	 *
	 * @param title the title
	 * @param vTablePanelInfo the v table panel info
	 */
	private void initializeNormalDialog(String title, VerticalPanel vTablePanelInfo) {

		dialogBox.setWidth("100%");
		closeButton.getElement().setId("closeButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.add(dialogContent);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);

		dialogBox.setWidget(dialogVPanel);

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});

		dialogBox.setText(title);
		dialogContent.add(vTablePanelInfo);
		dialogBox.show();
		dialogBox.setPopupPosition(20, 190);
		closeButton.setFocus(true);
	}
}
