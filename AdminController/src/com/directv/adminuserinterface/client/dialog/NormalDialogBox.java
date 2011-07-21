/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class NormalDialogBox.
 */
public class NormalDialogBox {

	/** The dialog box. */
	private DialogBox dialogBox = new DialogBox();

	/** The close button. */
	private Button closeButton = new Button("Close");

	/** The dialog content. */
	private HTML dialogContent = new HTML();

	/**
	 * Instantiates a new normal dialog box.
	 *
	 * @param title the title
	 * @param message the message
	 */
	public NormalDialogBox(String title, String message) {
		initializeNormalDialog(title, message);
	}

	/**
	 * Initialize normal dialog.
	 *
	 * @param title the title
	 * @param message the message
	 */
	private void initializeNormalDialog(String title, String message) {

		closeButton.getElement().setId("closeButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
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
		dialogContent.setText(message);
		dialogBox.center();
		closeButton.setFocus(true);
	}
}
