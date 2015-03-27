package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.event.OkEvent;
import org.cruxframework.crux.core.client.event.OkHandler;
import org.cruxframework.crux.core.client.event.SelectEvent;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.View;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.smartfaces.client.dialog.Confirm;
import org.cruxframework.crux.widgets.client.deviceadaptivegrid.DeviceAdaptiveGrid;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.grid.DataRow;

import com.google.gwt.dev.asm.Label;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.cloudopting.ui.ToscaUI.client.controller.datasource.RowDataSource;
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

	@Expose
	public void onLoad() {
		
		//GET VALUES FROM THE DATABASE
		
		
		/*
		 * END MOCK VARIABLES AND LISTS.
		 */

		//Get access to the panel
		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("mainPanel");
		panel.setTitle("Search Services");

		//Create a new panel 
		HTMLPanel innerPanel = new HTMLPanel("");
		innerPanel.setStyleName("innerPanel");
		panel.add(innerPanel);
		
		addButtonTextBoxPair(innerPanel, "Search Service", "crux-Button innerPanel-Button", "innerPanel-TextBox", "searchServiceButton");
		
		//Create GRID.
		final DeviceAdaptiveGrid grid = instancesServiceCatalogView.grid();//new DeviceAdaptiveGrid();
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
		rowsList.add(new RowDTO("1", "Clearo", "Published"));
		rowsList.add(new RowDTO("2", "Clearo", "REquested"));
		rowsList.add(new RowDTO("3", "OpenData", "Published"));
		rowsList.add(new RowDTO("4", "Corby Portal", "Testing"));
		rowsList.add(new RowDTO("5", "Next2Me", "Decomissioned"));
		
		//Load Data in the GRID.
		rowDataSource.setRows(rowsList);
		grid.loadData();
		grid.refresh();
		
		//Add grid to the panel.
		panel.add(grid);
		

	}
	
	@Expose
	public void go(SelectEvent event){
		DataRow row = instancesServiceCatalogView.grid().getRow((Widget) event.getSource());
		RowDTO dto = (RowDTO) row.getBoundObject();
		Confirm.show("DIALOG_TITLE", "GO TO " + dto.getInstance()+"?", new OkHandler() 
		{
			@Override
			public void onOk(OkEvent event) 
			{					
				FlatMessageBox.show("SUCCESS GOOO!!", MessageType.INFO);
			}
		}, null);
	}

	@BindView("instancesServiceCatalog")
	public static interface InstancesServiceCatalogView extends WidgetAccessor
	{
		HTMLPanel mainPanel();
		DeviceAdaptiveGrid grid();
	}
	

}




