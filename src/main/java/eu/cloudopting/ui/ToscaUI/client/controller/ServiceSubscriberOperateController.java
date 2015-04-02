package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
import eu.cloudopting.ui.ToscaUI.server.model.Application;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("serviceSubscriberOperateController")
public class ServiceSubscriberOperateController extends AbstractController
{
	@Inject
	public ServiceSubscriberOperateView view;
	
	@Inject
	public IProxyAPIService connectApi;
	
	private final static String PAGE_NAME = "Service Subscriber Operate Controller";
	
	/*
	 * CALLBACKS
	 */
	private Callback<Application> callbackView = new Callback<Application>() {
		@Override
		public void onSuccess(Application result) {
			buildView(result);
		}
		@Override
		public void onError(Exception e) {
			
		}
	};

	@Expose
	public void onLoad() {
		connectApi.application("1", callbackView);
	}

	@Expose
	public void subscribeService()
	{
		FlatMessageBox.show("Subscribe!!!", MessageType.INFO);
	}

	@BindView("serviceSubscriberOperate")
	public static interface ServiceSubscriberOperateView extends WidgetAccessor
	{
		HTMLPanel panelScreen();
	}
	
	/*
	 * PRIVATE METHODS
	 */
	private void buildView(Application application) {
		
		setScreenHeader(view.panelScreen(), PAGE_NAME);
		
		//String name = "Clearò";
		String name = application.getApplicationName();
		
		HTMLPanel innerPanel = new HTMLPanel("");
		innerPanel.setStyleName("innerPanel");
		view.panelScreen().add(innerPanel);
		
		Label label = new Label();
		label.setText("Manage "+name);
		label.setStyleName("header-Label");
		innerPanel.add(label);
		
		innerPanel.add(buildManagePanel(name));

		innerPanel.add(buildFollowPanel(name));
		
		//Add terms and conditions
		Label label4 = new Label();
		label4.setText("Check your Terms and Conditions");
		innerPanel.add(label4);
	}

	private HTMLPanel buildFollowPanel(String name) {
		//Create a new panel 
		HTMLPanel followKPIs = new HTMLPanel("<span class=\"mo_text\">Follow "+name+" KPI's</span>");
		followKPIs.setStyleName("followKPIs");

		addLabelTextBoxPair(followKPIs, "CPU Usage:", "taylor-Label", "taylor-TextBox", "numberCPUs", "2");
		addLabelTextBoxPair(followKPIs, "Bandwith Usage:", "taylor-Label", "taylor-TextBox", "bandwith", "200Gb/month");
		addLabelTextBoxPair(followKPIs, "Disk Space Usage:", "taylor-Label", "taylor-TextBox", "diskSpace", "256GB");
		addLabelTextBoxPair(followKPIs, "RAM Memory Usage:", "taylor-Label", "taylor-TextBox", "memoryRAM", "2048");
		
		return followKPIs;
	}

	private HTMLPanel buildManagePanel(String name) {
		//Create a new panel 
		HTMLPanel manageOperations = new HTMLPanel("<span class=\"mo_text\">Manage "+name+" Operations</span>");
		manageOperations.setStyleName("manageOperations");
		
		addLabelButtonPair(manageOperations, "Handle resource request:", "taylor-Label-Big", "crux-Button taylor-Button", "cloudNode", "Update "+name);
		addLabelButtonPair(manageOperations, "Decomission "+name+" Instance:", "taylor-Label-Big", "crux-Button taylor-Button", "urlDomain", "Decomission");
		
		return manageOperations;
	}
	
}