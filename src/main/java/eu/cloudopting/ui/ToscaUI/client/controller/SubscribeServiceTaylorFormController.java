package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
import eu.cloudopting.ui.ToscaUI.client.remote.IToscaManagerService;
import eu.cloudopting.ui.ToscaUI.client.utils.Navigate;
import eu.cloudopting.ui.ToscaUI.client.utils.ViewConstants;
import eu.cloudopting.ui.ToscaUI.server.model.Application;
import eu.cloudopting.ui.ToscaUI.server.model.SLA;
import eu.cloudopting.ui.ToscaUI.server.model.SubscribeServicesView;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("subscribeServiceTaylorFormController")
public class SubscribeServiceTaylorFormController extends AbstractController
{
	

	@Inject
	public SubscribeServiceTaylorFormView view;

	@Inject
	public IProxyAPIService api;
	
	@Inject
	public IToscaManagerService toscaManager;
	
	/*
	 * CONSTANTS
	 */
	private static final String PAGE_NAME = "Subscribe Service - Taylor Form";
	private static final String SLA_SELECTED = "slaSelected"; 
	
	/*
	 * CALLBACKS
	 */

	private Callback<SubscribeServicesView> getSubscribeServiceListsCallback = new Callback<SubscribeServicesView>() {
		@Override
		public void onSuccess(SubscribeServicesView ssl) {
			/*
			 * INIT MOCK VARIABLES AND LISTS.
			 */
			final List<String> listLocations = new ArrayList<String>(); 
			listLocations.add(0, "Turin");
			listLocations.add(1, "Barcelona");
			listLocations.add(2, "Bucharest");
			
			final List<String> listOSs = new ArrayList<String>(); 
			listOSs.add(0, "Ubuntu 14.04");
			listOSs.add(1, "CentOS 7");
			listOSs.add(2, "CoreOS 1.6");
			
			final List<String> listCSSs = new ArrayList<String>(); 
			listCSSs.add(0, "BlueSky");
			listCSSs.add(1, "Lemonade");
			listCSSs.add(2, "Violette");		

			//Tosca file only have SLA at the moment.
			List<String> listSLAId = new ArrayList<String>();
			for (SLA sla : ssl.getListSLAs()) {
				getContext().put(sla.getId(), sla);
				listSLAId.add(sla.getId());
			}
			buildView(listLocations, listOSs, listCSSs, listSLAId);
		}
		@Override
		public void onError(Exception e) {
			//TODO: On error, try again or go back to the catalog.
			FlatMessageBox.show("We couldn't get the service requested, try again in a few minutes.", MessageType.ERROR);
		}
	};
	
	private Callback<String> setToscaCallback = new Callback<String>() {
		@Override
		public void onSuccess(String result) {
			Integer id = (Integer) getContext().get(ViewConstants.INT_APPLICATION_ID_CURRENT_INSTANCE);
			Application app = (Application) getContext().get(ViewConstants.APPLICATION_INSTANCE+id);
			toscaManager.getSubscribeServiceLists(app.getApplicationName(), getSubscribeServiceListsCallback);
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
			toscaManager.setTosca(result.getApplicationToscaTemplate(), setToscaCallback);
		}
		@Override
		public void onError(Exception e) {
			//TODO: On error, try again or go back to the catalog.
			FlatMessageBox.show("We couldn't get the service requested, try again in a few minutes.", MessageType.ERROR);
		}
	};
	
	/*
	 * PUBLIC
	 */
	
	@Expose
	public void onLoad() {
		//GET VALUES FROM THE DATABASE
		Integer id = (Integer) getContext().get(ViewConstants.INT_APPLICATION_ID_CURRENT_INSTANCE);
		
		if(id!=null) {
			api.application(String.valueOf(id), appCallback);
		} else {
			Confirm.show("Question", "No Service Selected. Go to Service Catalog List?", new OkHandler() 
			{
				@Override
				public void onOk(OkEvent event) 
				{			
					Navigate.to(Navigate.SERVICE_CATALOG_LIST);
				}
			}, null);
		}		
	}

	@Expose
	public void subscribeService()
	{
		FlatMessageBox.show("Subscribe!!!", MessageType.INFO);
	}

	@BindView("subscribeServiceTaylorForm")
	public static interface SubscribeServiceTaylorFormView extends WidgetAccessor
	{
		HTMLPanel panelScreen();
	}

	/*
	 * PRIVATE METHODS
	 */
	private void buildView(List<String> listLocations, List<String> listOSs,
			List<String> listCSSs, List<String> listSLAid) {
		setScreenHeader(view.panelScreen(), PAGE_NAME);		
		
		//Get access to the panel
		HTMLPanel panel = view.panelScreen();
		
		//Create a new panel 
		HTMLPanel innerPanel = new HTMLPanel("<span class=\"mo_text\">Service Parameters</span>");
		innerPanel.setStyleName("serviceParametersPanel");
		panel.add(innerPanel);
		
		addLabelListBoxPair(innerPanel, "Cloud Node:", listLocations, "taylor-Label", "taylor-ListBox", "cloudNode");
		addLabelTextBoxPair(innerPanel, "URL/Domain:", "taylor-Label", "taylor-TextBox", "urlDomain");
		
		buildServiceFlavourPanel(listOSs, listCSSs, listSLAid, innerPanel);

		//Create the button to submit the results.
		Button subscribeServiceB = new Button();
		subscribeServiceB.setText("Subscribe Service");
		subscribeServiceB.setStyleName("crux-Button");
		subscribeServiceB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				subsribeService();
			}

		});		
		
		innerPanel.add(subscribeServiceB);
	}

	private void buildServiceFlavourPanel(List<String> listOSs,
			List<String> listCSSs, List<String> listSLAid, HTMLPanel innerPanel) {
		//Create a new panel 
		HTMLPanel newPanel = new HTMLPanel("<span class=\"mo_text\">Service Flavour</span>");
		newPanel.setStyleName("serviceFlavour");
		innerPanel.add(newPanel);

		addLabelListBoxPair(newPanel, "Operating System:", listOSs, "taylor-Label", "taylor-ListBox", "operatingSystem");
		addLabelListBoxPair(newPanel, "CSS Skin:", listCSSs, "taylor-Label", "taylor-ListBox", "cssSkin");

		//Depending on the list we will change the other values.
		ChangeHandler handler = new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				ListBox lb = (ListBox) event.getSource();
				SLA sla = (SLA) getContext().get(lb.getItemText(lb.getSelectedIndex()));
				getContext().put(SLA_SELECTED, sla);
				
				((TextBox) getContext().get("numCPUs")).setValue(sla.getNumCpus());
				((TextBox) getContext().get("bandwith")).setValue(sla.getPrice());
				((TextBox) getContext().get("diskSpace")).setValue(sla.getDisk());
				((TextBox) getContext().get("memoryRAM")).setValue(sla.getMemory());
				
			}
		};
		addLabelListBoxPair(newPanel, "SLAs:", listSLAid, "taylor-Label", "taylor-ListBox", "SLAs", handler);
		
		addLabelTextBoxPair(newPanel, "Number CPU's:", "taylor-Label", "taylor-TextBox", "numCPUs", "", false);
		addLabelTextBoxPair(newPanel, "Bandwith:", "taylor-Label", "taylor-TextBox", "bandwith", "", false);
		addLabelTextBoxPair(newPanel, "Disk Space:", "taylor-Label", "taylor-TextBox", "diskSpace", "", false);
		addLabelTextBoxPair(newPanel, "Memory RAM:", "taylor-Label", "taylor-TextBox", "memoryRAM", "", false);
	}
	
	private void subsribeService() {
		final Integer id = (Integer) getContext().get(ViewConstants.INT_APPLICATION_ID_CURRENT_INSTANCE);
		Application app = (Application) getContext().get(ViewConstants.APPLICATION_INSTANCE+id);
		SLA sla = (SLA) getContext().get(SLA_SELECTED);
		String nodeType = "VMhost";
		
		final Callback<String> changeViewCallback = new Callback<String>() {
			@Override
			public void onSuccess(String result) {
				FlatMessageBox.show("Subscription Requested", MessageType.SUCCESS);
				//TODO: clean Context
				//Navigate.to(Navigate.SERVICE_SUBSCRIBER_OPERATE);
			}
			@Override
			public void onError(Exception e) {
				FlatMessageBox.show("A problem in the service subscription occurred, please, try again.", MessageType.ERROR);
			}
		};
		
		Callback<String> createCustomizationCallback = new Callback<String>() {
			@Override
			public void onSuccess(String result) {
				api.customizationCreate(String.valueOf(id), result, changeViewCallback);
			}
			@Override
			public void onError(Exception e) {
				FlatMessageBox.show("A problem in the service subscription occurred, please, try again.", MessageType.ERROR);
			}
		};

		toscaManager.setSLA(app.getApplicationName(), nodeType, sla, createCustomizationCallback);
	}
}