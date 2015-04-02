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
public class DetailServiceController extends AbstractController
{
	@Inject
	public DetailServiceView view;
	
	@Expose
	public void onLoad(){
	}

	@BindView("detailServiceView")
	public static interface DetailServiceView extends WidgetAccessor
	{

	}

}