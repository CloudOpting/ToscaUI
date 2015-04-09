package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.Screen;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.simplecontainer.SimpleViewContainer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

@Controller("topMenuController")
public class TopMenuController {
	
	@Inject
	public TopMenuView view;
	
	@Expose
	public void onLoad() {
		buildView();
	}

	@BindView("topMenu")
	public static interface TopMenuView extends WidgetAccessor
	{
		HTMLPanel panelScreen();
	}
	
	/*
	 * PRIVATE METHODS
	 */
	private void buildView() {
//		addButton("Main", "main");
		addButton("Publish Service", "publishService");
		addButton("Service Catalog List", "serviceCatalogList");
		addButton("Subscribe Service - Taylor Form", "subscribeServiceTaylorForm");
		addButton("Service Subscriber. Operate", "serviceSubscriberOperate");
		addButton("Instances Service Catalog - Manager", "instancesServiceCatalog");
		addButton("Services Catalog - Manager", "servicesCatalog");
		addButton("Service Add Deploy Form", "serviceAddDeployForm");
	}

	private void addButton(String name, final String viewStr) {
		//CREATE THE MENU.
		Button b = new Button();
		b.setText(name);
		b.setStyleName("topMenuEntry-Button");
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				((SimpleViewContainer) Screen.get("views")).showView(viewStr);				
			}
		});
		view.panelScreen().add(b);
	}
}
