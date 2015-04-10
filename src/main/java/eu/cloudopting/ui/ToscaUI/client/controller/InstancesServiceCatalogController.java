package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.event.OkEvent;
import org.cruxframework.crux.core.client.event.OkHandler;
import org.cruxframework.crux.core.client.event.SelectEvent;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.smartfaces.client.dialog.Confirm;
import org.cruxframework.crux.widgets.client.deviceadaptivegrid.DeviceAdaptiveGrid;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.grid.DataRow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.cloudopting.ui.ToscaUI.client.controller.datasource.RowDataSource;
import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
import eu.cloudopting.ui.ToscaUI.client.utils.Navigate;
import eu.cloudopting.ui.ToscaUI.client.utils.ViewConstants;
import eu.cloudopting.ui.ToscaUI.server.model.Application;
import eu.cloudopting.ui.ToscaUI.server.model.ApplicationList;
import eu.cloudopting.ui.ToscaUI.server.model.Customizations;
import eu.cloudopting.ui.ToscaUI.server.model.RowDTO;

/**
 * 
 * @author xeviscc
 *
 */
@SuppressWarnings("all")
@Controller("instancesServiceCatalogController")
public class InstancesServiceCatalogController extends AbstractController
{

	@Inject
	public InstancesServiceCatalogView view;
	
	@Inject
	public IProxyAPIService api;
	
	/*
	 * CONSTANTS
	 */
	private static final String PAGE_NAME = "Instances Service Catalog";
	private static final Integer RETRY_MAX = 3;
	
	private Integer RETRY_COUNT = 0;
	
	/*
	 * CALLBACKS
	 */
	private Callback<ApplicationList> callbackView = new Callback<ApplicationList>() {
		@Override
		public void onSuccess(ApplicationList result) {
			buildView(result);
			RETRY_COUNT = 0;
			hideProgress();
		}
		@Override
		public void onError(Exception e) {
			if(RETRY_COUNT<RETRY_MAX) {
				RETRY_COUNT++;
				api.applicationListUnpaginated(callbackView);
			} else {
				RETRY_COUNT = 0;
				hideProgress();
				FlatMessageBox.show("A problem occurred with the network, please try in a few minutes.", MessageType.WARN);
			}
		}
	};
	
	private Callback<ApplicationList> callbackGrid = new Callback<ApplicationList>() {
		@Override
		public void onSuccess(ApplicationList result) {
			buildGrid(result);
			RETRY_COUNT = 0;
			hideProgress();
		}
		@Override
		public void onError(Exception e) {
			if(RETRY_COUNT<RETRY_MAX) {
				RETRY_COUNT++;
				api.applicationListUnpaginated(callbackGrid);
			} else {
				RETRY_COUNT = 0;
				hideProgress();
				FlatMessageBox.show("A problem occurred with the network, please try in a few minutes.", MessageType.WARN);
			}
		}
	};
	
	/*
	 * CONSTANTS
	 */
	private static String DEFAULT_PAGE = "1";
	private static String DEFAULT_SIZE = "10";
	private static String DEFAULT_SORT_BY = "applicationName";
	private static String DEFAULT_SORT_ORDER = "asc";
	private static String DEFAULT_FILTER = "";

	@Expose
	public void onLoad() {
		showProgress("Retriving data...");
		//get the applications from the database
		//connectApi.applicationList(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, DEFAULT_SORT_ORDER, DEFAULT_FILTER, callbackView);
		//FIXME: FOR THE DEMO WE ARE GOING TO USE AN UNPAGINATED LIST OF APPLICATIONS
		api.applicationListUnpaginated(callbackView);
	}
	
	@Expose
	public void go(SelectEvent event){
		DataRow row = view.grid().getRow((Widget) event.getSource());
		final RowDTO dto = (RowDTO) row.getBoundObject();
		Confirm.show("Question", "Do you want to edit the instance of " + dto.getInstance() +" with ID \"" + dto.getIdCustomization() + "\"?", new OkHandler() 
		{
			@Override
			public void onOk(OkEvent event) 
			{			
				//Update the current Application ID.
				getContext().put(ViewConstants.INT_COSTUMIZATION_ID_CURRENT_INSTANCE, Integer.valueOf(dto.getIdCustomization()));
				
				//Navigate
				if (dto.getStatus().equals("Requested")) {
					Navigate.to(Navigate.SERVICE_ADD_DEPLOY_FORM);
				} else if (dto.getStatus().equals("Running")) {
					Navigate.to(Navigate.SERVICE_ADD_DEPLOY_FORM);
				} else if (dto.getStatus().equals("Stopped")) {
					Navigate.to(Navigate.SERVICE_ADD_DEPLOY_FORM);
				} else if (dto.getStatus().equals("Deleted")) {
					Navigate.to(Navigate.SERVICE_ADD_DEPLOY_FORM);
				} else if (dto.getStatus().equals("To delete")) {
					Navigate.to(Navigate.SERVICE_ADD_DEPLOY_FORM);
				} else {
					//Do not move!! Show a message?
				}
			}
		}, null);
	}

	@BindView("instancesServiceCatalog")
	public static interface InstancesServiceCatalogView extends WidgetAccessor
	{
		HTMLPanel panelScreen();
		DeviceAdaptiveGrid grid();
	}
	
	private void buildView(ApplicationList result) {
		setScreenHeader(view.panelScreen(), PAGE_NAME);
		buildButtons();
		buildGrid(result);
	}
	
	private void buildButtons() {
		//Create a new panel for the buttons 
		HTMLPanel innerPanel = new HTMLPanel("");
		innerPanel.setStyleName("innerPanel");
		view.panelScreen().add(innerPanel);
		
		final String id = "searchServiceButton";
		addButtonTextBoxPair(innerPanel, "Search Service", "crux-Button innerPanel-Button", "innerPanel-TextBox", id, 
			new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					//get the applications from the database applying the filter.
					//String filter = getMap().get(id);
					//connectApi.applicationList(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, DEFAULT_SORT_ORDER, filter, callbackGrid);
					//FIXME: FOR THE DEMO WE ARE GOING TO USE AN UNPAGINATED LIST OF APPLICATIONS
					api.applicationListUnpaginated(callbackGrid);
				}
			}
		);
	}

	private void buildGrid(ApplicationList list) {
		DeviceAdaptiveGrid grid = view.grid();
		grid.setStyleName("grid");
		
		//Get data source of the grid.
		RowDataSource rowDataSource = (RowDataSource) grid.getDataSource();

		//Populate data to the grid.
		List<RowDTO> rowsList = new ArrayList<RowDTO>();
		for (Application app : list.getContent()) {
			//Instances of services
			List<Customizations> custList = app.getCustomizationss();
			for (Customizations customizations : custList) {
				//Add the customization to the list.
				String username = customizations.getUsername();
				username = "Admin";
				rowsList.add(new RowDTO(app.getId().toString(), customizations.getId().toString(),
						"Instance of: " + app.getApplicationName() + ", created by -" + username + "- (" + customizations.getCustomizationActivation() + ")", 
						customizations.getStatusId().getStatus()));
			}
		}
		
		//Load data to the grid.
		rowDataSource.setRows(rowsList);
		grid.loadData();
		grid.refresh();
		
		//This puts the grid in the position we want.
		view.panelScreen().add(grid);
	}
}