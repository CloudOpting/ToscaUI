package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

import eu.cloudopting.ui.ToscaUI.client.utils.Navigate;

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
		addButton("Publish Service", Navigate.PUBLISH_SERVICE);
		addButton("Service Catalog List", Navigate.SERVICE_CATALOG_LIST);
		addButton("Subscribe Service - Taylor Form", Navigate.SUBSCIRBE_SERVICE_TAYLOR_FORM);
		addButton("Service Subscriber. Operate", Navigate.SERVICE_SUBSCRIBER_OPERATE);
		addButton("Instances Service Catalog - Manager", Navigate.INSTANCES_SERVICE_CATALOG);
		addButton("Services Catalog - Manager", Navigate.SERVICES_CATALOG);
		addButton("Service Add Deploy Form", Navigate.SERVICE_ADD_DEPLOY_FORM);
	}

	private void addButton(String name, final String viewStr) {
		//CREATE THE MENU.
		Button b = new Button();
		b.setText(name);
		b.setStyleName("topMenuEntry-Button");
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Navigate.to(viewStr);
			}
		});
		view.panelScreen().add(b);
	}
}
