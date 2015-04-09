package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.dialogcontainer.DialogViewContainer;
import org.cruxframework.crux.widgets.client.storyboard.Storyboard;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLPanel;

import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
import eu.cloudopting.ui.ToscaUI.client.utils.ViewConstants;
import eu.cloudopting.ui.ToscaUI.server.model.Application;
import eu.cloudopting.ui.ToscaUI.server.model.ApplicationList;
import eu.cloudopting.ui.ToscaUI.server.model.StoryboardItem;
import eu.cloudopting.ui.ToscaUI.server.model.StoryboardItemView;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("serviceCatalogListController")
public class ServiceCatalogListController extends AbstractController
{
	private static final String PAGE_NAME = "Service Catalog";

	@Inject
	public ServiceCatalogListView view;

	@Inject
	public IProxyAPIService api;
	
	private final static Integer RETRY_MAX = 3;
	private Integer RETRY_COUNT = 0;

	private Callback<ApplicationList> callbackView = new Callback<ApplicationList>() {
		@Override
		public void onSuccess(ApplicationList result) {
			setAppliactionsToContext(result);
			buildView(result);
			RETRY_COUNT = 0;
			hideProgress();
		}

		@Override
		public void onError(Exception e) {
			if(RETRY_COUNT<RETRY_MAX) {
				RETRY_COUNT++;
				api.applicationListUnpaginated(callbackView);
			} else {
				RETRY_COUNT = 0;
				hideProgress();
				FlatMessageBox.show("A problem occurred with the network, please try in a few minutes.", MessageType.WARN);
			}
		}
	};

	@Expose
	public void onLoad() {
		showProgress("Retriving data...");
		api.applicationListUnpaginated(callbackView);
	}

	@Expose
	public void openDetail()
	{
		view.dialogViewContainer().loadView("detailService", true);
		view.dialogViewContainer().openDialog();
		view.dialogViewContainer().center();
	}

	@BindView("serviceCatalogList")
	public static interface ServiceCatalogListView extends WidgetAccessor
	{
		Storyboard storyboard();
		HTMLPanel panelScreen();
		DialogViewContainer dialogViewContainer();
	}
	
	/*
	 * PRIVATE METHODS
	 */
	private void buildView(ApplicationList result) {
		setScreenHeader(view.panelScreen(), PAGE_NAME);
		
		List<StoryboardItemView> listItem = new ArrayList<StoryboardItemView>();
		for (Application app : result.getContent()) {
			String name = app.getApplicationName();
			String desc = app.getApplicationDescription();
			addItem(listItem, name + " CHOOSEN", app.getId(), name, desc, "CHOOSE THIS SERVICE");
		}

		for(StoryboardItemView w : listItem){
			view.storyboard().add(w);
		}
		
		//Set The Storyboard at the end of the panel.
		view.panelScreen().add(view.storyboard());
	}

	private void addItem(List<StoryboardItemView> listItem,
			final String clickedMsg, final Integer id, final String name, final String price, 
			final String textButton) {
		
		final StoryboardItem storyboardItem = new StoryboardItem(clickedMsg, id, name, price, textButton);
		
		ClickHandler handler = new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				getContext().put(ViewConstants.INT_APPLICATION_ID_CURRENT_INSTANCE, storyboardItem.getId());
				openDetail();
			}
		};
		
		listItem.add(new StoryboardItemView(storyboardItem, handler));
	}

	private void setAppliactionsToContext(ApplicationList result) {
		for (Application app : result.getContent()) {
			getContext().put(ViewConstants.APPLICATION_INSTANCE + app.getId(), app);
		}
	}
}
