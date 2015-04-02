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
import org.cruxframework.crux.core.client.screen.Screen;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.smartfaces.client.dialog.Confirm;
import org.cruxframework.crux.widgets.client.deviceadaptivegrid.DeviceAdaptiveGrid;
import org.cruxframework.crux.widgets.client.grid.DataRow;
import org.cruxframework.crux.widgets.client.simplecontainer.SimpleViewContainer;
import org.cruxframework.crux.widgets.client.swapcontainer.HorizontalSwapContainer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.cloudopting.ui.ToscaUI.client.controller.datasource.RowDataSource;
import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
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
	public IProxyAPIService connectApi;
	
	/*
	 * CALLBACKS
	 */
	private Callback<ApplicationList> callbackView = new Callback<ApplicationList>() {
		@Override
		public void onSuccess(ApplicationList result) {
			buildView(result);
		}
		@Override
		public void onError(Exception e) {
			
		}
	};
	
	private Callback<ApplicationList> callbackGrid = new Callback<ApplicationList>() {
		@Override
		public void onSuccess(ApplicationList result) {
			buildGrid(result);
		}
		@Override
		public void onError(Exception e) {
			
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
		//get the applications from the database
		//connectApi.applicationList(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, DEFAULT_SORT_ORDER, DEFAULT_FILTER, callbackView);
		//FIXME: FOR THE DEMO WE ARE GOING TO USE AN UNPAGINATED LIST OF APPLICATIONS
		connectApi.applicationListUnpaginated(callbackView);
	}
	
	@Expose
	public void go(SelectEvent event){
		DataRow row = view.grid().getRow((Widget) event.getSource());
		RowDTO dto = (RowDTO) row.getBoundObject();
		Confirm.show("Question", "Do you want to edit the instance of " + dto.getInstance() +" with ID \"" + dto.getId() + "\"?", new OkHandler() 
		{
			@Override
			public void onOk(OkEvent event) 
			{					
				//Change of location
				//Change full screen
				((SimpleViewContainer) Screen.get("views")).showView("subscribeServiceTaylorForm");
				
				//Navigate through the top menu. (Not working)
				//TopMenuDisposal tmd = (TopMenuDisposal)View.of(this).getWidget("menu");
				//tmd.loadView("subscribeServiceTaylorForm", true);
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
		setScreenHeader(view.panelScreen(), "Instances Service Catalog");
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
					connectApi.applicationListUnpaginated(callbackGrid);
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
			List<Customizations> custList = app.getCustomizationss();
			for (Customizations customizations : custList) {
				//Add the customization to the list.
				rowsList.add(new RowDTO(app.getId().toString(), "S: " +  app.getApplicationName() + " U: " + customizations.getUsername(), customizations.getStatusId().getStatus()));
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