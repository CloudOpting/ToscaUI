package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.core.rebind.screen.Widget;
import org.cruxframework.crux.widgets.client.dialogcontainer.DialogViewContainer;
import org.cruxframework.crux.widgets.client.storyboard.Storyboard;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import eu.cloudopting.ui.ToscaUI.model.StoryboardItem;

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
		
		ClickHandler handler = new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
//				openDetail((StoryboardItem) event.getSource());
				openDetail();
			}
		};
		
		listItem.add(new StoryboardItem("Clearo CHOOSEN", "Clearo", "Clearo is a service that allows to...", "CHOOSE THIS SERVICE", handler));
		listItem.add(new StoryboardItem("FixThis CHOOSEN", "FixThis", "FixThis is a service that allows to...", "CHOOSE THIS SERVICE", handler));
		listItem.add(new StoryboardItem("Agenda CHOOSEN", "Agenda", "Agenda is a service that allows to...", "CHOOSE THIS SERVICE", handler));
		listItem.add(new StoryboardItem("Next2Me CHOOSEN", "Next2Me", "Next2Me is a service that allows to...", "CHOOSE THIS SERVICE",handler));
		listItem.add(new StoryboardItem("MobileID CHOOSEN", "MobileID", "MobileID is a service that allows to...", "CHOOSE THIS SERVICE", handler));
		listItem.add(new StoryboardItem("ASIA CHOOSEN", "ASIA", "ASIA is a service that allows to...", "CHOOSE THIS SERVICE",handler));
		listItem.add(new StoryboardItem("MIB (Base Information Database) CHOOSEN", "MIB", "MIB (Base Information Database) is a service that allows to...", "CHOOSE THIS SERVICE",handler));
		listItem.add(new StoryboardItem("Energy Consumption - Corby CHOOSEN", "Energy Consumption - Corby", "Energy Consumption - Corby is a service that allows to...", "CHOOSE THIS SERVICE",handler));
		listItem.add(new StoryboardItem("Transportation System - Corby CHOOSEN", "Transportation System - Corby", "Transportation System - Corby is a service that allows to...", "CHOOSE THIS SERVICE", handler));
		listItem.add(new StoryboardItem("BusPortal - Corby CHOOSEN", "BusPortal - Corby", "BusPortal - Corby is a service that allows to...", "CHOOSE THIS SERVICE", handler));
		listItem.add(new StoryboardItem("IndicatorsPortal - Sant Feliu CHOOSEN", "IndicatorsPortal - Sant Feliu", "IndicatorsPortal - Sant Feliu is a service that allows to...", "CHOOSE THIS SERVICE", handler));
		listItem.add(new StoryboardItem("SmartCityCloud - Sant Feliu CHOOSEN", "SmartCityCloud - Sant Feliu", "SmartCityCloud - Sant Feliu is a service that allows to...", "CHOOSE THIS SERVICE", handler));
		listItem.add(new StoryboardItem("Barcelona Open Data CHOOSEN", "Barcelona Open Data", "Barcelona Open Data is a service that allows to...", "CHOOSE THIS SERVICE",handler));
//		listItem.add(new StoryboardItem("Mobile Services - Interoperability CHOOSEN", "Mobile Services", "Mobile Services - Interoperability is a service that allows to...", "CHOOSE THIS SERVICE"));
		
		
		for(StoryboardItem w : listItem){
			serviceCatalogList.storyboard().add(w);
		}
	}
	
	@Expose
//	public void openDetail(StoryboardItem widget)
	public void openDetail()
	{
//		FlatMessageBox.show("DETAIL", MessageType.INFO);
		
//		System.out.println(widget.getTitle());
		
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
