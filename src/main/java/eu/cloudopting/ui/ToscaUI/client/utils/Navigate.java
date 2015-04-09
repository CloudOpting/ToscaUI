package eu.cloudopting.ui.ToscaUI.client.utils;

import org.cruxframework.crux.core.client.screen.Screen;
import org.cruxframework.crux.widgets.client.simplecontainer.SimpleViewContainer;

public class Navigate {

	public static final String SUBSCIRBE_SERVICE_TAYLOR_FORM = "subscribeServiceTaylorForm";
	public static final String SERVICE_CATALOG_LIST = "serviceCatalogList";
	public static final String SERVICE_ADD_DEPLOY_FORM = "serviceAddDeployForm";
	public static final String SERVICE_SUBSCRIBER_OPERATE = "serviceSubscriberOperate";
	public static final String PUBLISH_SERVICE = "publishService";
	public static final String INSTANCES_SERVICE_CATALOG = "instancesServiceCatalog";
	public static final String SERVICES_CATALOG = "servicesCatalog";

	public static void to(String to){
		((SimpleViewContainer) Screen.get("views")).showView(to);
	}
}
