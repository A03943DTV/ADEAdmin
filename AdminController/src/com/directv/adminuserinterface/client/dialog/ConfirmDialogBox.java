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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmDialogBox.
 */
public class ConfirmDialogBox {

	/** The dialog box. */
	private DialogBox dialogBox = new DialogBox();

	/** The yes button. */
	private Button yesButton = new Button("Yes");

	/** The no button. */
	private Button noButton = new Button("No");

	/** The dialog content. */
	private HTML dialogContent = new HTML();

	/**
	 * Instantiates a new confirm dialog box.
	 */
	public ConfirmDialogBox() {
	}

	/**
	 * Initialize confirm dialog.
	 *
	 * @param title the title
	 * @param message the message
	 * @return the button
	 */
	public Button initializeConfirmDialog(String title, String message) {

		yesButton.getElement().setId("yesButton");
		noButton.getElement().setId("noButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(dialogContent);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(5);
		hPanel.add(yesButton);
		hPanel.add(noButton);
		dialogVPanel.add(hPanel);

		dialogBox.setWidget(dialogVPanel);

		noButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hideDialogBox();
			}
		});

		dialogBox.setText(title);
		dialogContent.setText(message);
		dialogBox.center();
		yesButton.setFocus(true);

		return yesButton;
	}

	/**
	 * Hide dialog box.
	 */
	public void hideDialogBox() {
		dialogBox.hide();
	}
}
