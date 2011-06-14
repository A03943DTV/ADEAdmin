/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.client.dialog;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadingDialogBox.
 */
public class LoadingDialogBox {

	/** The loader dialog box. */
	private DialogBox loaderDialogBox = new DialogBox();

	/** The loader dialog content. */
	private HTML loaderDialogContent = new HTML();

	/**
	 * Instantiates a new loading dialog box.
	 *
	 * @param title the title
	 * @param message the message
	 */
	public LoadingDialogBox(String title, String message) {

		initializeLoaderDialog(title, message);
	}

	/**
	 * Initialize loader dialog.
	 *
	 * @param title the title
	 * @param message the message
	 */
	private void initializeLoaderDialog(String title, String message) {

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(loaderDialogContent);

		loaderDialogBox.setWidget(dialogVPanel);
		loaderDialogBox.setAnimationEnabled(true);

		loaderDialogBox.setText(title);
		loaderDialogContent.setText(message);
		loaderDialogBox.center();
	}

	/**
	 * Hide loader dialog.
	 */
	public void hideLoaderDialog() {
		loaderDialogBox.hide();
	}
}
