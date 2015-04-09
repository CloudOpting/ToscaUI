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
import eu.cloudopting.ui.ToscaUI.server.model.StoryboardItem;

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
		
//		String id = (String) getContext().get(ViewConstants.CURRENT_INSTANCE);
//		Application app = (Application) getContext().get(ViewConstants.CURRENT_INSTANCE + id);
//		view.productName().setText("Service: " + app.getApplicationName() );
//		view.productDescription().setText( app.getApplicationDescription() + " " + view.productDescription().getText());

		StoryboardItem storyBoardItem = (StoryboardItem) getContext().get("storyBoardItem");
		view.productName().setText("Service: " + storyBoardItem.getName() );
		view.productDescription().setText( storyBoardItem.getDescription() + " " + view.productDescription().getText());
	}
	
	@Expose
	public void subscribe() {
		//Move to [Subscribe Service - Taylor Form]
		((DialogViewContainer) view.getBoundCruxView().getContainer()).closeDialog();
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