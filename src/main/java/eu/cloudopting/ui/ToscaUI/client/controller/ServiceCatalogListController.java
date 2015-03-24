package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.DocumentEvent.EventType;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.dialogcontainer.DialogViewContainer;
import org.cruxframework.crux.widgets.client.storyboard.Storyboard;
import org.jgroups.Event;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import eu.cloudopting.ui.ToscaUI.server.model.StoryboardItem;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("serviceCatalogListController")
public class ServiceCatalogListController 
{
	@Inject
	public ServiceCatalogList serviceCatalogList;

	@Expose
	public void onLoad() {
		loadItems();
	}

	private void loadItems() {
		List<StoryboardItem> listItem = new ArrayList<StoryboardItem>();

		
		addItem(listItem, "Clearo CHOOSEN", "Clearo", "Clearo is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "FixThis CHOOSEN", "FixThis", "FixThis is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "Agenda CHOOSEN", "Agenda", "Agenda is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "Next2Me CHOOSEN", "Next2Me", "Next2Me is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "MobileID CHOOSEN", "MobileID", "MobileID is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "ASIA CHOOSEN", "ASIA", "ASIA is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "MIB (Base Information Database) CHOOSEN", "MIB", "MIB (Base Information Database) is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "Energy Consumption - Corby CHOOSEN", "Energy Consumption - Corby", "Energy Consumption - Corby is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "Transportation System - Corby CHOOSEN", "Transportation System - Corby", "Transportation System - Corby is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "BusPortal - Corby CHOOSEN", "BusPortal - Corby", "BusPortal - Corby is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "IndicatorsPortal - Sant Feliu CHOOSEN", "IndicatorsPortal - Sant Feliu", "IndicatorsPortal - Sant Feliu is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "SmartCityCloud - Sant Feliu CHOOSEN", "SmartCityCloud - Sant Feliu", "SmartCityCloud - Sant Feliu is a service that allows to...", "CHOOSE THIS SERVICE");
		addItem(listItem, "Barcelona Open Data CHOOSEN", "Barcelona Open Data", "Barcelona Open Data is a service that allows to...", "CHOOSE THIS SERVICE");
//		addItem(listItem, "Mobile Services - Interoperability CHOOSEN", "Mobile Services", "Mobile Services - Interoperability is a service that allows to...", "CHOOSE THIS SERVICE"));
		
		
		for(StoryboardItem w : listItem){
			serviceCatalogList.storyboard().add(w);
		}
	}

	private void addItem(List<StoryboardItem> listItem,
			final String clickedMsg, String name, String price,
			String textButton) {
		ClickHandler handler = new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
//				openDetail((StoryboardItem) event.getSource());
				openDetail(clickedMsg);
			}
		};

		listItem.add(new StoryboardItem(clickedMsg, name, price, textButton, handler));
	}
	
	//TODO: Check the way to make the dialog know how is the parent, and show consistent information
	@Expose
//	public void openDetail(StoryboardItem widget)
	public void openDetail(String parameter)
	{
		serviceCatalogList.dialogViewContainer().loadView("detailService", true);
		serviceCatalogList.dialogViewContainer().openDialog();
		serviceCatalogList.dialogViewContainer().center();
	}

	@BindView("serviceCatalogList")
	public static interface ServiceCatalogList extends WidgetAccessor
	{
		Storyboard storyboard();
		DialogViewContainer dialogViewContainer();
	}

}
