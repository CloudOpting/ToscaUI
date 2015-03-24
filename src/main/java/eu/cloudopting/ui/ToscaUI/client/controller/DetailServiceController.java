package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.View;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.HTMLPanel;

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
	public void onLoad(){
		
//		detailServiceView.productName().setInnerText("HOLA");
	}

	@BindView("detailServiceView")
	public static interface DetailServiceView extends WidgetAccessor
	{
//		DivElement productName();
	}

}