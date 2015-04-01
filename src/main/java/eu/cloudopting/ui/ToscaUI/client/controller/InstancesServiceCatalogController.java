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
import org.cruxframework.crux.core.client.screen.views.View;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.smartfaces.client.dialog.Confirm;
import org.cruxframework.crux.smartfaces.client.disposal.menudisposal.TopMenuDisposal;
import org.cruxframework.crux.widgets.client.deviceadaptivegrid.DeviceAdaptiveGrid;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.grid.DataRow;
import org.cruxframework.crux.widgets.client.swapcontainer.HorizontalSwapContainer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.cloudopting.ui.ToscaUI.client.controller.datasource.RowDataSource;
import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
import eu.cloudopting.ui.ToscaUI.server.model.ApplicationList;
import eu.cloudopting.ui.ToscaUI.server.model.Content;
import eu.cloudopting.ui.ToscaUI.server.model.RowDTO;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("instancesServiceCatalogController")
public class InstancesServiceCatalogController extends AbstractController
{
	@Inject
	public InstancesServiceCatalogView instancesServiceCatalogView;
	
	@Inject
	public IProxyAPIService connectApi;
	
	
	private Callback<ApplicationList> callback = new Callback<ApplicationList>() {
		@Override
		public void onSuccess(ApplicationList result) {
			buildView(result);
		}
		@Override
		public void onError(Exception e) {
			
		}
	};
	
	private Callback<ApplicationList> callback2 = new Callback<ApplicationList>() {
		@Override
		public void onSuccess(ApplicationList result) {
			buildGrid(result, instancesServiceCatalogView.mainPanel(), instancesServiceCatalogView.grid());
		}
		@Override
		public void onError(Exception e) {
			
		}
	};
	
	private static String DEFAULT_PAGE = "1";
	private static String DEFAULT_SIZE = "10";
	private static String DEFAULT_SORT_BY = "applicationName";
	private static String DEFAULT_SORT_ORDER = "asc";
	private static String DEFAULT_FILTER = "";
	

	@Expose
	public void onLoad() {
		//GET VALUES FROM THE DATABASE
		connectApi.applicationList(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, DEFAULT_SORT_ORDER, DEFAULT_FILTER, callback);
	}
	
	@Expose
	public void go(SelectEvent event){
		DataRow row = instancesServiceCatalogView.grid().getRow((Widget) event.getSource());
		RowDTO dto = (RowDTO) row.getBoundObject();
		Confirm.show("DIALOG_TITLE", "Do you want to edit the instance of " + dto.getInstance() +" with ID \"" + dto.getId() + "\"?", new OkHandler() 
		{
			@Override
			public void onOk(OkEvent event) 
			{					
				//TODO: Fix location
				((HorizontalSwapContainer) Screen.get("views")).showView("subscribeServiceTaylorForm");
				
//				TopMenuDisposal tmd = (TopMenuDisposal)View.of(this).getWidget("menu");
//				tmd.loadView("subscribeServiceTaylorForm", true);
			}
		}, null);
	}

	@BindView("instancesServiceCatalog")
	public static interface InstancesServiceCatalogView extends WidgetAccessor
	{
		HTMLPanel mainPanel();
		DeviceAdaptiveGrid grid();
	}
	

	private final void buildView(ApplicationList list) {
		//Get access to the panel
//		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("mainPanel");
		HTMLPanel panel = instancesServiceCatalogView.mainPanel();
		
		panel.setTitle("Search Services");

		//Create a new panel 
		HTMLPanel innerPanel = new HTMLPanel("");
		innerPanel.setStyleName("innerPanel");
		panel.add(innerPanel);
		
		final String id = "searchServiceButton";
		addButtonTextBoxPair(innerPanel, "Search Service", "crux-Button innerPanel-Button", "innerPanel-TextBox", id, 
			new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					connectApi.applicationList(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, DEFAULT_SORT_ORDER, getMap().get(id), callback2);
				}
			}
		);

		//Create GRID.
		buildGrid(list, panel, instancesServiceCatalogView.grid());		
	}

	private void buildGrid(ApplicationList list, HTMLPanel panel,
			final DeviceAdaptiveGrid grid) {
		grid.setStyleName("grid");
		
		
		//Create columns of the GRID.
//		DeviceAdaptiveGridColumnDefinitions columns = new DeviceAdaptiveGridColumnDefinitions();
//		ColumnDefinition colDefID = new ColumnDefinition("ID", "100px", true, HasHorizontalAlignment.ALIGN_DEFAULT, HasVerticalAlignment.ALIGN_MIDDLE);
//		columns.add(Size.small, "id", colDefID);
//		ColumnDefinition colDef = new ColumnDefinition("Instance", "300px", true, HasHorizontalAlignment.ALIGN_DEFAULT, HasVerticalAlignment.ALIGN_MIDDLE);
//		columns.add(Size.large, "instance", colDef);
//		ColumnDefinition colDef2 = new ColumnDefinition("Status", "100px", true, HasHorizontalAlignment.ALIGN_DEFAULT, HasVerticalAlignment.ALIGN_MIDDLE);
//		columns.add(Size.large, "status", colDef2);
		
		//Parameters to initialize the GRID.
//		int pageSize = 10;
//		int cellSpacing = 0; 
//		boolean autoLoadData = false;
//		boolean stretchColumns = false;
//		boolean highlightRowOnMouseOver = false;
//		String emptyDataFilling = "";
//		boolean fixedCellSize = true;
//		String defaultSortingColumn = "";
		
		//Initialize the GRID.
//		grid.initGrid(columns, pageSize, RowSelectionModel.unselectable, cellSpacing, 
//				autoLoadData, stretchColumns, highlightRowOnMouseOver, emptyDataFilling, 
//				fixedCellSize, defaultSortingColumn, SortingType.ascending);
		
		//Set Datasource to the GRID.
//		RowDataSource rowDataSource = new RowDataSource();
//		RowDataSource rowDataSource = (RowDataSource) View.of(this).createDataSource("rowDataSource");
		RowDataSource rowDataSource = (RowDataSource) grid.getDataSource();
//		grid.setDataSource(rowDataSource, true);

		//Populate data to the GRID.
		List<RowDTO> rowsList = new ArrayList<RowDTO>();
		
		for (Content content : list.getContent()) {
			rowsList.add(new RowDTO(content.getId().toString(), content.getApplicationName(), content.getStatusId().getStatus()));	
		}
		
		/*
		rowsList.add(new RowDTO("1", "Clearo", "Published"));
		rowsList.add(new RowDTO("2", "Clearo", "REquested"));
		rowsList.add(new RowDTO("3", "OpenData", "Published"));
		rowsList.add(new RowDTO("4", "Corby Portal", "Testing"));
		rowsList.add(new RowDTO("5", "Next2Me", "Decomissioned"));
		*/
		
		//Load Data in the GRID.
		rowDataSource.setRows(rowsList);
		grid.loadData();
		grid.refresh();
		
		//Add grid to the panel.
		panel.add(grid);
	}
	

}




