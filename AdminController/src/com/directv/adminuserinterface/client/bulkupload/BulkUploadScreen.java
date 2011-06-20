/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.bulkupload;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.directv.adminuserinterface.client.dialog.NormalDialogBox;
import com.directv.adminuserinterface.client.table.CustomizedImageCell;
import com.directv.adminuserinterface.shared.BulkUploadDto;
import com.directv.adminuserinterface.shared.LoginInfo;
import com.directv.adminuserinterface.util.ErrorMessageUtil;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

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

	/** The clear button. */
	private Button clearButton = new Button("Clear");

	/** The refresh grid button. */
	private Button refreshGridButton = new Button("Refresh Grid");

	/** The description text field. */
	private TextBox descriptionTextField = new TextBox();

	/** The file upload. */
	private FileUpload fileUpload = new FileUpload();

	/** The file lable. */
	private Label fileLable = new Label("File");

	/** The description lable. */
	private Label descriptionLable = new Label("Description");

	/** The bu table. */
	private CellTable<BulkUploadDto> buTable = new CellTable<BulkUploadDto>();

	/** The data provider. */
	private ListDataProvider<BulkUploadDto> dataProvider = new ListDataProvider<BulkUploadDto>();

	/** The list bu main from web service. */
	private List<BulkUploadDto> listBuMainFromWebService = new ArrayList<BulkUploadDto>();

	/** The login info. */
	private LoginInfo loginInfo;

	/** The download lable. */
	Label downloadLable = new Label("If you don't have the bulk upload template please");

	/** The anchor template. */
	private Anchor anchorTemplate = new Anchor("clickhere");

	/**
	 * Instantiates a new bulk upload screen.
	 *
	 * @param loginInfo the login info
	 */
	public BulkUploadScreen(final LoginInfo loginInfo) {

		this.loginInfo = loginInfo;

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(5);

		anchorTemplate.setHref("templates/UsersInfo.csv");

		HorizontalPanel hDownloadPanel = new HorizontalPanel();
		hDownloadPanel.add(downloadLable);
		hDownloadPanel.add(anchorTemplate);
		hDownloadPanel.setSpacing(5);

		form.setAction("/userBulkUpload");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.setWidget(vPanel);

		fileUpload.setName("uploadFormField");
		descriptionTextField.setName("description");

		Grid grid = getFormGrid();

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(submitButton);
		hPanel.add(clearButton);
		hPanel.add(refreshGridButton);
		hPanel.setSpacing(5);

		//Loading DB data and Setting the Columns,Values and Pagination for BU Table
		VerticalPanel vTablePanel = loadDataAndSetBuTableValues();

		vPanel.add(grid);
		vPanel.add(hDownloadPanel);
		vPanel.add(hPanel);
		vPanel.add(vTablePanel);

		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.submit();
			}
		});

		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clearFormFields();
			}
		});

		refreshGridButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				listBuInfo();
			}
		});

		// Add an event handler to the form.
		form.addFormHandler(new FormHandler() {

			public void onSubmit(FormSubmitEvent event) {
				// This event is fired just before the form is submitted. We can take
				// this opportunity to perform validation.
			}

			public void onSubmitComplete(FormSubmitCompleteEvent event) {

				String errorMessage = ErrorMessageUtil.getErrorMessage(event.getResults());
				if (errorMessage != null) {
					new NormalDialogBox("Validation Error", errorMessage);
					return;
				}

				clearFormFields();
				listBuInfo();

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

	/**
	 * Load data and set bu table values.
	 *
	 * @return the vertical panel
	 */
	private VerticalPanel loadDataAndSetBuTableValues() {

		//Load the BulkUpload data's from DB/WebService
		listBuInfo();

		return setBuTableValues();
	}

	/**
	 * List bu info.
	 */
	private void listBuInfo() {

		bulkUploadService.getBulkUploadResults(loginInfo.getUser().getUserId(), new AsyncCallback<List<BulkUploadDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Bulk Upload List Fetching Error : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<BulkUploadDto> result) {
				System.out.println("Bulk Upload List Fetching Successfull : " + result.size());
				listBuMainFromWebService = result;
				dataProvider.getList().clear();
				List<BulkUploadDto> dataProviderList = dataProvider.getList();
				for (BulkUploadDto buDto : listBuMainFromWebService) {
					dataProviderList.add(buDto);
				}
				dataProvider.refresh();
			}
		});
	}

	/**
	 * Sets the bu table values.
	 *
	 * @return the vertical panel
	 */
	private VerticalPanel setBuTableValues() {

		buTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		//Adding the columns to buTable
		Column<BulkUploadDto, String> downloadColumn = generateDownloadColumn();
		Column<BulkUploadDto, String> processStatusColumn = generateProcessStatusColumn();
		Column<BulkUploadDto, String> idColumn = generateIdColumn();
		Column<BulkUploadDto, String> descriptionColumn = generateDescriptionColumn();
		Column<BulkUploadDto, String> submittedTimeColumn = generateSubmittedTimeColumn();
		Column<BulkUploadDto, String> processStartTimeColumn = generateProcessStartTimeColumn();
		Column<BulkUploadDto, String> processEndTimeColumn = generateProcessEndTimeColumn();
		Column<BulkUploadDto, String> noOfFailureRecordsColumn = generateNoOfFailureRecordsColumn();
		Column<BulkUploadDto, String> noOfSuccessRecordsColumn = generateNoOfSuccessRecordsColumn();
		Column<BulkUploadDto, String> statusColumn = generateStatusColumn();

		// Set the width of the buTable and put the buTable in fixed width mode.
		buTable.setWidth("1385px", true);

		// Set the width of each column.
		buTable.setColumnWidth(downloadColumn, 7.0, Unit.PCT);
		buTable.setColumnWidth(processStatusColumn, 11.0, Unit.PCT);
		buTable.setColumnWidth(idColumn, 4.0, Unit.PCT);
		buTable.setColumnWidth(descriptionColumn, 9.0, Unit.PCT);
		buTable.setColumnWidth(submittedTimeColumn, 13.0, Unit.PCT);
		buTable.setColumnWidth(processStartTimeColumn, 13.0, Unit.PCT);
		buTable.setColumnWidth(processEndTimeColumn, 13.0, Unit.PCT);
		buTable.setColumnWidth(noOfFailureRecordsColumn, 11.0, Unit.PCT);
		buTable.setColumnWidth(noOfSuccessRecordsColumn, 11.0, Unit.PCT);
		buTable.setColumnWidth(statusColumn, 8.0, Unit.PCT);

		dataProvider.addDataDisplay(buTable);
		List<BulkUploadDto> dataProviderList = dataProvider.getList();
		for (BulkUploadDto buDto : listBuMainFromWebService) {
			dataProviderList.add(buDto);
		}

		//Adding sort handler for header
		addSortHandlersForHeader(dataProviderList, idColumn, descriptionColumn, submittedTimeColumn, processStartTimeColumn, processEndTimeColumn,
				noOfFailureRecordsColumn, noOfSuccessRecordsColumn, processStatusColumn, statusColumn);

		//Adding scroller to the table
		ScrollPanel scrollerPanel = new ScrollPanel(buTable);
		scrollerPanel.setWidth("950px");
		scrollerPanel.setHeight("260px");

		VerticalPanel vTablePanel = new VerticalPanel();
		vTablePanel.add(scrollerPanel);
		vTablePanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		return vTablePanel;
	}

	/**
	 * Download bu result template.
	 *
	 * @param index the index
	 * @param object the object
	 */
	protected void downloadBuResultTemplate(int index, BulkUploadDto object) {

		try {
			Window.open(GWT.getModuleBaseURL() + "buDownloadServlet?id=" + object.getId(), "", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the sort handlers for header.
	 *
	 * @param dataProviderList the data provider list
	 * @param idColumn the id column
	 * @param descriptionColumn the description column
	 * @param submittedTimeColumn the submitted time column
	 * @param processStartTimeColumn the process start time column
	 * @param processEndTimeColumn the process end time column
	 * @param noOfFailureRecordsColumn the no of failure records column
	 * @param noOfSuccessRecordsColumn the no of success records column
	 * @param processStatusColumn the process status column
	 * @param statusColumn the status column
	 */
	private void addSortHandlersForHeader(List<BulkUploadDto> dataProviderList, Column<BulkUploadDto, String> idColumn,
			Column<BulkUploadDto, String> descriptionColumn, Column<BulkUploadDto, String> submittedTimeColumn,
			Column<BulkUploadDto, String> processStartTimeColumn, Column<BulkUploadDto, String> processEndTimeColumn,
			Column<BulkUploadDto, String> noOfFailureRecordsColumn, Column<BulkUploadDto, String> noOfSuccessRecordsColumn,
			Column<BulkUploadDto, String> processStatusColumn, Column<BulkUploadDto, String> statusColumn) {

		ListHandler<BulkUploadDto> columnSortHandler = new ListHandler<BulkUploadDto>(dataProviderList);
		columnSortHandler.setComparator(idColumn, new Comparator<BulkUploadDto>() {
			public int compare(BulkUploadDto o1, BulkUploadDto o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getId().compareTo(o2.getId()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(descriptionColumn, new Comparator<BulkUploadDto>() {
			public int compare(BulkUploadDto o1, BulkUploadDto o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getDescription().compareTo(o2.getDescription()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(submittedTimeColumn, new Comparator<BulkUploadDto>() {
			public int compare(BulkUploadDto o1, BulkUploadDto o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getSubmittedTime().compareTo(o2.getSubmittedTime()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(processStartTimeColumn, new Comparator<BulkUploadDto>() {
			public int compare(BulkUploadDto o1, BulkUploadDto o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getProcessStratTime().compareTo(o2.getProcessStratTime()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(processEndTimeColumn, new Comparator<BulkUploadDto>() {
			public int compare(BulkUploadDto o1, BulkUploadDto o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getProcessEndTime().compareTo(o2.getProcessEndTime()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(noOfFailureRecordsColumn, new Comparator<BulkUploadDto>() {
			public int compare(BulkUploadDto o1, BulkUploadDto o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getNoOfFailureRecords().compareTo(o2.getNoOfFailureRecords()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(noOfSuccessRecordsColumn, new Comparator<BulkUploadDto>() {
			public int compare(BulkUploadDto o1, BulkUploadDto o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getNoOfSuccessRecords().compareTo(o2.getNoOfSuccessRecords()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(processStatusColumn, new Comparator<BulkUploadDto>() {
			public int compare(BulkUploadDto o1, BulkUploadDto o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getProcessStatus().compareTo(o2.getProcessStatus()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(statusColumn, new Comparator<BulkUploadDto>() {
			public int compare(BulkUploadDto o1, BulkUploadDto o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 != null) {
					return (o2 != null) ? o1.getStatus().compareTo(o2.getStatus()) : 1;
				}
				return -1;
			}
		});
		buTable.addColumnSortHandler(columnSortHandler);

		// We know that the data is sorted alphabetically by default.
		buTable.getColumnSortList().push(idColumn);
	}

	/**
	 * Clear form fields.
	 */
	protected void clearFormFields() {

		form.reset();
	}

	/**
	 * Gets the form grid.
	 *
	 * @return the form grid
	 */
	private Grid getFormGrid() {

		Grid formGrid = new Grid(2, 2);

		fileUpload.setWidth("305px");
		fileUpload.setHeight("25px");
		descriptionTextField.setWidth("215px");

		formGrid.setWidget(0, 0, fileLable);
		formGrid.setWidget(0, 1, fileUpload);
		formGrid.setWidget(1, 0, descriptionLable);
		formGrid.setWidget(1, 1, descriptionTextField);

		formGrid.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);

		formGrid.setCellSpacing(5);

		return formGrid;
	}

	/**
	 * Generate status column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateStatusColumn() {

		Column<BulkUploadDto, String> statusColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getStatus();
			}
		};
		statusColumn.setSortable(true);//For sorting
		buTable.addColumn(statusColumn, "Result");
		return statusColumn;
	}

	/**
	 * Generate process status column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateProcessStatusColumn() {

		Column<BulkUploadDto, String> processStatusColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getProcessStatus();
			}
		};
		processStatusColumn.setSortable(true);//For sorting
		buTable.addColumn(processStatusColumn, "Process Status");
		return processStatusColumn;
	}

	/**
	 * Generate no of success records column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateNoOfSuccessRecordsColumn() {

		Column<BulkUploadDto, String> noOfSuccessRecordsColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getNoOfSuccessRecords() != null ? object.getNoOfSuccessRecords().toString() : String.valueOf(0);
			}
		};
		noOfSuccessRecordsColumn.setSortable(true);//For sorting
		buTable.addColumn(noOfSuccessRecordsColumn, "Success Count");
		return noOfSuccessRecordsColumn;
	}

	/**
	 * Generate no of failure records column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateNoOfFailureRecordsColumn() {

		Column<BulkUploadDto, String> noOfFailureRecordsColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getNoOfFailureRecords() != null ? object.getNoOfFailureRecords().toString() : String.valueOf(0);
			}
		};
		noOfFailureRecordsColumn.setSortable(true);//For sorting
		buTable.addColumn(noOfFailureRecordsColumn, "Failure Count");
		return noOfFailureRecordsColumn;
	}

	/**
	 * Generate process end time column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateProcessEndTimeColumn() {

		Column<BulkUploadDto, String> processEndTimeColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getProcessEndTime();
			}
		};
		processEndTimeColumn.setSortable(true);//For sorting
		buTable.addColumn(processEndTimeColumn, "Process EndTime");
		return processEndTimeColumn;
	}

	/**
	 * Generate process start time column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateProcessStartTimeColumn() {

		Column<BulkUploadDto, String> processStartTimeColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getProcessStratTime();
			}
		};
		processStartTimeColumn.setSortable(true);//For sorting
		buTable.addColumn(processStartTimeColumn, "Process StartTime");
		return processStartTimeColumn;
	}

	/**
	 * Generate submitted time column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateSubmittedTimeColumn() {

		Column<BulkUploadDto, String> submittedTimeColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getSubmittedTime();
			}
		};
		submittedTimeColumn.setSortable(true);//For sorting
		buTable.addColumn(submittedTimeColumn, "Submitted Time");
		return submittedTimeColumn;
	}

	/**
	 * Generate description column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateDescriptionColumn() {

		Column<BulkUploadDto, String> descriptionColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getDescription();
			}
		};
		descriptionColumn.setSortable(true);//For sorting
		buTable.addColumn(descriptionColumn, "Description");
		return descriptionColumn;
	}

	/**
	 * Generate id column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateIdColumn() {

		Column<BulkUploadDto, String> idColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getId().toString();
			}
		};
		idColumn.setSortable(true);//For sorting
		buTable.addColumn(idColumn, "Id");
		return idColumn;
	}

	/**
	 * Generate download column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateDownloadColumn() {

		Column<BulkUploadDto, String> downloadButtonColumn = new Column<BulkUploadDto, String>(new CustomizedImageCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return "images/csvsmall.png";
			}
		};
		downloadButtonColumn.setFieldUpdater(new FieldUpdater<BulkUploadDto, String>() {
			@Override
			public void update(int index, BulkUploadDto object, String value) {
				if (object.getProcessStatus().equals(BulkUploadDto.PROCESS_STATUS_COMPLETED)) {
					downloadBuResultTemplate(index, object);
				} else {
					new NormalDialogBox("In Process",
							"Your Template data in process. Click on Refresh Grid button to know the updated Process Status.");
				}
			}
		});
		buTable.addColumn(downloadButtonColumn, "Download");
		return downloadButtonColumn;
	}
}
