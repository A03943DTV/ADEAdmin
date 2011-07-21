/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.dialog;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class ChooseDialogBox.
 */
public class ChooseDialogBox {

	/** The dialog box. */
	private DialogBox dialogBox = new DialogBox();

	/** The button1. */
	private Button button1 = new Button();

	/** The button2. */
	private Button button2 = new Button();

	/** The cancel. */
	private Button cancel = new Button("Cancel");

	/** The dialog content. */
	private HTML dialogContent = new HTML();

	/**
	 * Instantiates a new choose dialog box.
	 *
	 * @param button1Lable the button1 lable
	 * @param button2Lable the button2 lable
	 */
	public ChooseDialogBox(String button1Lable, String button2Lable) {
		button1.setText(button1Lable);
		button2.setText(button2Lable);
	}

	/**
	 * Initialize confirm dialog.
	 *
	 * @param title the title
	 * @param message the message
	 */
	public void initializeConfirmDialog(String title, String message) {

		button1.getElement().setId("1Button");
		button2.getElement().setId("2Button");
		cancel.getElement().setId("12CancelButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(dialogContent);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(5);
		hPanel.add(button1);
		hPanel.add(button2);
		hPanel.add(cancel);
		dialogVPanel.add(hPanel);

		dialogBox.setWidget(dialogVPanel);
		dialogBox.setText(title);
		dialogContent.setText(message);
		dialogBox.center();
		button1.setFocus(true);

	}

	/**
	 * Gets the button1.
	 *
	 * @return the button1
	 */
	public Button getButton1() {
		return button1;
	}

	/**
	 * Gets the button2.
	 *
	 * @return the button2
	 */
	public Button getButton2() {
		return button2;
	}

	/**
	 * Gets the cancel button.
	 *
	 * @return the cancel button
	 */
	public Button getCancelButton() {
		return cancel;
	}

	/**
	 * Hide dialog box.
	 */
	public void hideDialogBox() {
		dialogBox.hide();
	}
}