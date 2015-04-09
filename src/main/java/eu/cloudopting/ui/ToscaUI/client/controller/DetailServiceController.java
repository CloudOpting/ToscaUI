package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialogcontainer.DialogViewContainer;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

import eu.cloudopting.ui.ToscaUI.client.utils.Navigate;
import eu.cloudopting.ui.ToscaUI.client.utils.ViewConstants;
import eu.cloudopting.ui.ToscaUI.server.model.Application;

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
		Integer id = (Integer) getContext().get(ViewConstants.INT_APPLICATION_ID_CURRENT_INSTANCE);
		Application app = (Application) getContext().get(ViewConstants.APPLICATION_INSTANCE + id);
		view.productName().setText("Service: " + app.getApplicationName() );
		view.productDescription().setText( app.getApplicationDescription() + " " + view.productDescription().getText());
	}
	
	@Expose
	public void subscribe() {
		//Close myself
		((DialogViewContainer) view.getBoundCruxView().getContainer()).closeDialog();
		//Navigate
		Navigate.to(Navigate.SUBSCIRBE_SERVICE_TAYLOR_FORM);		
	}

	@BindView("detailService")
	public static interface DetailServiceView extends WidgetAccessor
	{
		HTMLPanel panelScreen();
		HTML productName();
		HTML productDescription();
	}
}