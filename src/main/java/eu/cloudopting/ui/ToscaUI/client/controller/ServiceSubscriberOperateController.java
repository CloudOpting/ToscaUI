package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.event.OkEvent;
import org.cruxframework.crux.core.client.event.OkHandler;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.smartfaces.client.dialog.Confirm;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
import eu.cloudopting.ui.ToscaUI.client.remote.IToscaManagerService;
import eu.cloudopting.ui.ToscaUI.client.utils.Navigate;
import eu.cloudopting.ui.ToscaUI.client.utils.ViewConstants;
import eu.cloudopting.ui.ToscaUI.server.model.Application;
import eu.cloudopting.ui.ToscaUI.server.model.SLA;

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
	public IProxyAPIService api;
	
	@Inject
	public IToscaManagerService toscaManager;
	
	/*
	 * CONSTANTS
	 */
	private final static String PAGE_NAME = "Service Subscriber Operate Controller";
	
	/*
	 * CALLBACKS
	 */
	private Callback<SLA> callbackSLA = new Callback<SLA>() {
		@Override
		public void onSuccess(SLA result) {
			//getContext().put(ViewConstants.SLA_CURRENT_INSTANCE, result);
			buildView(result);
		}
		@Override
		public void onError(Exception e) {
		}
	};
	
	private Callback<String> setToscaCallback = new Callback<String>() {
		@Override
		public void onSuccess(String result) {
			Application app = (Application) getContext().get(ViewConstants.APPLICATION_CURRENT_INSTANCE);
			toscaManager.getChosenSLA(app.getApplicationName(), app.getApplicationName(), "VMhost", callbackSLA);
		}
		@Override
		public void onError(Exception e) {
			//TODO: On error, try again or go back to the catalog.
			FlatMessageBox.show("We couldn't get the service requested, try again in a few minutes.", MessageType.ERROR);
		}
	};
	
	private Callback<Application> appCallback = new Callback<Application>() {
		@Override
		public void onSuccess(Application result) {
			getContext().put(ViewConstants.APPLICATION_CURRENT_INSTANCE, result);
			getContext().put(ViewConstants.INT_APPLICATION_ID_CURRENT_INSTANCE, result.getId());
			toscaManager.setTosca(result.getApplicationToscaTemplate(), setToscaCallback);
		}
		@Override
		public void onError(Exception e) {
			//TODO: On error, try again or go back to the catalog.
			FlatMessageBox.show("We couldn't get the service requested, try again in a few minutes.", MessageType.ERROR);
		}
	};

	@Expose
	public void onLoad() {
		Integer id = (Integer) getContext().get(ViewConstants.INT_APPLICATION_ID_CURRENT_INSTANCE);
		
		if(id!=null) {
			api.application(String.valueOf(id), appCallback);
		} else {
			Confirm.show("Question", "No Service Selected. Go to Services Catalog - Manager?", new OkHandler() 
			{
				@Override
				public void onOk(OkEvent event) 
				{			
					Navigate.to(Navigate.SERVICES_CATALOG);
				}
			}, null);
		}
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
	private void buildView(SLA sla) {
		Application application = (Application) getContext().get(ViewConstants.APPLICATION_CURRENT_INSTANCE);

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

		innerPanel.add(buildFollowPanel(name, sla));
		
		//Add terms and conditions
		Label label4 = new Label();
		label4.setText("Check your Terms and Conditions");
		innerPanel.add(label4);
	}

	private HTMLPanel buildFollowPanel(String name, SLA sla) {
		//Create a new panel 
		HTMLPanel followKPIs = new HTMLPanel("<span class=\"mo_text\">Follow "+name+" KPI's</span>");
		followKPIs.setStyleName("followKPIs");

		addLabelTextBoxPair(followKPIs, "CPU Usage:", "taylor-Label", "taylor-TextBox", "numberCPUs", sla.getNumCpus(), false);
		addLabelTextBoxPair(followKPIs, "Bandwith Usage:", "taylor-Label", "taylor-TextBox", "bandwith", sla.getPrice(), false);
		addLabelTextBoxPair(followKPIs, "Disk Space Usage:", "taylor-Label", "taylor-TextBox", "diskSpace", sla.getDisk(), false);
		addLabelTextBoxPair(followKPIs, "RAM Memory Usage:", "taylor-Label", "taylor-TextBox", "memoryRAM", sla.getMemory(), false);
		
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