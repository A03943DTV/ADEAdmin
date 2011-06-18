/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.client.bulkupload;

import java.util.List;

import com.directv.adminuserinterface.shared.BulkUploadDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkUploadScreen.
 */
@SuppressWarnings("deprecation")
public class BulkUploadScreen extends Composite {

	/** The bulk upload service. */
	private final BulkUploadServiceAsync bulkUploadService = GWT.create(BulkUploadService.class);

	/** The form. */
	private final FormPanel form = new FormPanel();

	/** The submit button. */
	private Button submitButton = new Button("Submit");

	/** The result button. */
	private Button resultButton = new Button("Result");

	/** The description text field. */
	private TextBox descriptionTextField = new TextBox();

	/**
	 * Instantiates a new bulk upload screen.
	 */
	public BulkUploadScreen() {

		VerticalPanel panel = new VerticalPanel();

		form.setAction("/userBulkUpload");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.setWidget(panel);

		FileUpload upload = new FileUpload();
		upload.setName("uploadFormField");

		descriptionTextField.setName("description");

		panel.add(upload);
		panel.add(descriptionTextField);
		panel.add(submitButton);
		panel.add(resultButton);

		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.submit();
			}
		});

		resultButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				bulkUploadService.getBulkUploadResults(new AsyncCallback<List<BulkUploadDto>>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Bulk Upload Result Fetching Error : " + caught.getMessage());
					}

					@Override
					public void onSuccess(List<BulkUploadDto> result) {

						for (BulkUploadDto bulkUpload : result) {

							System.out.println("/////////////////////////////////////////////////////////////////////////////");
							System.out.println("Id                 : " + bulkUpload.getId());
							System.out.println("Description        : " + bulkUpload.getDescription());
							System.out.println("SubmittedTime      : " + bulkUpload.getSubmittedTime());
							System.out.println("ProcessStartTime   : " + bulkUpload.getProcessStratTime());
							System.out.println("ProcessEndtime     : " + bulkUpload.getProcessEndTime());
							System.out.println("NoOfFailureRecords : " + bulkUpload.getNoOfFailureRecords());
							System.out.println("NoOfSuccessRecords : " + bulkUpload.getNoOfSuccessRecords());
							System.out.println("ProcessStatus      : " + bulkUpload.getProcessStatus());
							System.out.println("Status             : " + bulkUpload.getStatus());
						}
					}
				});
			}
		});

		// Add an event handler to the form.
		form.addFormHandler(new FormHandler() {

			public void onSubmit(FormSubmitEvent event) {
				// This event is fired just before the form is submitted. We can take
				// this opportunity to perform validation.
			}

			public void onSubmitComplete(FormSubmitCompleteEvent event) {

				bulkUploadService.processBulkUpload(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Bulk Upload Processing Error : " + caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						System.out.println("Bulk Upload Processing Success");
					}
				});
			}
		});

		initWidget(form);
	}
}
