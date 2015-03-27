package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.screen.Screen;
import org.cruxframework.crux.widgets.client.swapcontainer.HorizontalSwapContainer;

import com.google.gwt.user.client.Window;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("toscaListController")
public class ToscaListController extends AbstractController
{

	@Expose
	public void onModuleLoad() {

	}
	
	@Expose
	public void sayOK() {
		Window.alert("OK");
	}
	
	@Expose
	public void changeView() {
		HorizontalSwapContainer views =  (HorizontalSwapContainer) Screen.get("views");
		views.showView("main");
	}

}
