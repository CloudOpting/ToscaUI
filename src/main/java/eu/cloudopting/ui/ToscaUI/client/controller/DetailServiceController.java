package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("detailServiceController")
public class DetailServiceController 
{
	@Inject
	public DetailServiceView detailServiceView;
	
	@Expose
	public void onLoad(){}

	@BindView("detailServiceView")
	public static interface DetailServiceView extends WidgetAccessor
	{
	}

}