package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;

@Controller("subscribeServiceTaylorFormController")
public class SubscribeServiceTaylorFormController 
{
	@Inject
	public SubscribeServiceTaylorFormView subscribeServiceTaylorFormView;

	@Expose
	public void onLoad() {
		
	}

	@Expose
	public void subscribeService()
	{
		FlatMessageBox.show("Subscribe!!!", MessageType.INFO);
	}

	@BindView("subscribeServiceTaylorFormView")
	public static interface SubscribeServiceTaylorFormView extends WidgetAccessor
	{
				
	}

}
