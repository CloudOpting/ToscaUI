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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;

import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
import eu.cloudopting.ui.ToscaUI.client.remote.IToscaManagerService;
import eu.cloudopting.ui.ToscaUI.client.utils.Navigate;
import eu.cloudopting.ui.ToscaUI.client.utils.ViewConstants;
import eu.cloudopting.ui.ToscaUI.server.model.Application;
import eu.cloudopting.ui.ToscaUI.server.model.Customizations;
import eu.cloudopting.ui.ToscaUI.server.model.SLA;
import eu.cloudopting.ui.ToscaUI.server.model.SubscribeServicesView;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("serviceAddDeployFormController")
public class ServiceAddDeployFormController extends AbstractController
{
	
	@Inject
	public ServiceAddDeployFormView view;
	
	@Inject
	public IProxyAPIService api;
	
	@Inject
	public IToscaManagerService toscaManager;
	
	/*
	 * CONSTANTS
	 */
	private static final String PAGE_NAME = "Service Add Deploy Form";

	/*
	 * CALLBACKS
	 */
	private Callback<SLA> callbackSLA = new Callback<SLA>() {
		@Override
		public void onSuccess(SLA result) {
			//getContext().put(ViewConstants.SLA_CURRENT_INSTANCE, result);
			//GET VALUES FROM THE DATABASE

			List<String> listItems = new ArrayList<String>(); 
			listItems.add(0, "Turin");
			listItems.add(1, "Barcelona");
			listItems.add(2, "Bucharest");

			List<String> listOSs = new ArrayList<String>(); 
			listOSs.add(0, "Ubuntu 14.04");
			listOSs.add(1, "CentOS 7");
			listOSs.add(2, "CoreOS 1.6");

			List<String> listCSSs = new ArrayList<String>(); 
			listCSSs.add(0, "BlueSky");
			listCSSs.add(1, "Lemonade");
			listCSSs.add(2, "Violette");		

			buildView(listItems, listOSs, listCSSs, result);
			
		}
		@Override
		public void onError(Exception e) {
		}
	};
	
	private Callback<String> setToscaCallback = new Callback<String>() {
		@Override
		public void onSuccess(String result) {
			String name = (String) getContext().get(ViewConstants.STRING_CUSTOMIZATION_NAME_CURRENT_INSTANCE);
			toscaManager.getChosenSLA(name, "VMhost", callbackSLA);
		}
		@Override
		public void onError(Exception e) {
			//TODO: On error, try again or go back to the catalog.
			FlatMessageBox.show("We couldn't get the instance requested, try again in a few minutes.", MessageType.ERROR);
		}
	};
	
	private Callback<Customizations> customCallback = new Callback<Customizations>() {
		@Override
		public void onSuccess(Customizations result) {
			getContext().put(ViewConstants.CUSTOMIZATION_CURRENT_INSTANCE, result);
			toscaManager.setTosca(result.getCustomizationToscaFile(), setToscaCallback);
		}
		@Override
		public void onError(Exception e) {
			//TODO: On error, try again or go back to the catalog.
			FlatMessageBox.show("We couldn't get the instance requested, try again in a few minutes.", MessageType.ERROR);
		}
	};
	
	
	
	
	
	private Callback<String> setToscaCallbackApp = new Callback<String>() {
		@Override
		public void onSuccess(String result) {
			Integer id = (Integer) getContext().get(ViewConstants.INT_APPLICATION_ID_CURRENT_INSTANCE);
			Application app = (Application) getContext().get(ViewConstants.APPLICATION_INSTANCE+id);
			toscaManager.getChosenSLA(app.getApplicationName(), "VMhost", callbackSLA);
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
			toscaManager.setTosca(result.getApplicationToscaTemplate(), setToscaCallbackApp);
		}
		@Override
		public void onError(Exception e) {
			//TODO: On error, try again or go back to the catalog.
			FlatMessageBox.show("We couldn't get the service requested, try again in a few minutes.", MessageType.ERROR);
		}
	};
	
	
	
	
	@Expose
	public void onLoad() {
		Integer id = (Integer) getContext().get(ViewConstants.INT_CUSTOMIZATION_ID_CURRENT_INSTANCE);
		
		if(id!=null) {
			api.customization(String.valueOf(id), customCallback);
		} else {
			id = (Integer) getContext().get(ViewConstants.INT_APPLICATION_ID_CURRENT_INSTANCE);
			if(id!=null) {
				api.application(String.valueOf(id), appCallback);
			} else {
				Confirm.show("Question", "No Instance Selected. Go to Instances Service Catalog - Manager?", new OkHandler() 
				{
					@Override
					public void onOk(OkEvent event) 
					{			
						Navigate.to(Navigate.INSTANCES_SERVICE_CATALOG);
					}
				}, null);
			}
		}
		
		
	}

	@Expose
	public void subscribeService()
	{
		FlatMessageBox.show("Subscribe!!!", MessageType.INFO);
	}

	@BindView("serviceAddDeployForm")
	public static interface ServiceAddDeployFormView extends WidgetAccessor
	{
		HTMLPanel panelScreen();
	}

	private final void buildView(List<String> listItems, List<String> listOSs,
			List<String> listCSSs, SLA sla) {

		setScreenHeader(view.panelScreen(), PAGE_NAME);
		//Get access to the panel
		HTMLPanel panel = view.panelScreen();

		buildServiceDeployPanel(panel);

		buildServiceParametersPanel(listItems, listOSs, listCSSs, sla,
				panel);
	}

	/*
	 * PRIVATE METHODS
	 */
	private void buildServiceParametersPanel(List<String> listItems,
			List<String> listOSs, List<String> listCSSs, SLA sla,
			HTMLPanel panel) {
		//Create a new panel 
		HTMLPanel serviceParametersPanel = new HTMLPanel("<span class=\"mo_text\">Service Parameters</span>");
		serviceParametersPanel.setStyleName("serviceParameters");
		panel.add(serviceParametersPanel);

		//Create the left panel.
		HTMLPanel leftPanel = new HTMLPanel("");
		leftPanel.setStyleName("leftPanel");
		serviceParametersPanel.add(leftPanel);

		addLabelListBoxPair(leftPanel, "Cloud Node:", listItems, "taylor-Label", "taylor-ListBox", "cloudNode");
		addLabelListBoxPair(leftPanel, "Operating System:", listOSs, "taylor-Label", "taylor-ListBox", "operatingSystem");
		
		//addLabelListBoxPair(leftPanel, "Number CPU's:", listCPUs, "taylor-Label", "taylor-ListBox", "numberCPUs");
		
		addLabelTextBoxPair(leftPanel, "Number CPU's:", "taylor-Label", "taylor-TextBox", "numberCPUs", sla.getNumCpus());
		addLabelTextBoxPair(leftPanel, "Disk Space:", "taylor-Label", "taylor-TextBox", "diskSpace", sla.getDisk());
		addLabelTextBoxPair(leftPanel, "Memory RAM:", "taylor-Label", "taylor-TextBox", "memoryRAM", sla.getMemory());
		addLabelTextBoxPair(leftPanel, "Bandwith:", "taylor-Label", "taylor-TextBox", "bandwith", sla.getPrice());		
		addLabelTextBoxPair(leftPanel, "URL/Domain:", "taylor-Label", "taylor-TextBox", "urlDomain");
		addLabelListBoxPair(leftPanel, "CSS Skin:", listCSSs, "taylor-Label", "taylor-ListBox", "cssSkin");

		buildPuppetFilesPanel(serviceParametersPanel);

		buildBottomButtons(serviceParametersPanel);
	}

	private void buildBottomButtons(HTMLPanel panel) {
		//Create the button to save the results.
		Button saveTemplateB = new Button();
		saveTemplateB.setText("Save Template");
		saveTemplateB.setStyleName("crux-Button");
		saveTemplateB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FlatMessageBox.show("Template Saved correctly!! ", MessageType.SUCCESS);
			}
		});		
		panel.add(saveTemplateB);

		//Create the button to uplad a new template.
		Button uploadTemplateB = new Button();
		uploadTemplateB.setText("Upload Template");
		uploadTemplateB.setStyleName("crux-Button");
		uploadTemplateB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FlatMessageBox.show("Template Uploaded correctly!! ", MessageType.SUCCESS);
			}
		});	

		panel.add(uploadTemplateB);
	}

	private void buildServiceDeployPanel(HTMLPanel panel) {
		//Create a new panel 
		HTMLPanel serviceDeployPanel = new HTMLPanel("<span class=\"mo_text\">Service Deploy</span>");
		serviceDeployPanel.setStyleName("serviceDeploy");
		panel.add(serviceDeployPanel);

		//Create the button to save the results.
		Button deployServiceB = new Button();
		deployServiceB.setText("Deploy Service");
		deployServiceB.setStyleName("crux-Button");
		deployServiceB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FlatMessageBox.show("Serice Deployed correctly!! ", MessageType.SUCCESS);
			}
		});		
		serviceDeployPanel.add(deployServiceB);

		//Create the button to save the results.
		Button testServiceB = new Button();
		testServiceB.setText("Test Service");
		testServiceB.setStyleName("crux-Button");
		testServiceB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FlatMessageBox.show("Go to test!! ", MessageType.INFO);
			}
		});		
		serviceDeployPanel.add(testServiceB);
	}

	private void buildPuppetFilesPanel(HTMLPanel panel) {
		//Create the right panel
		HTMLPanel rightPanel = new HTMLPanel("<span class=\"mo_text\">Puppet Files</span>");
		rightPanel.setStyleName("rightPanel");
		panel.add(rightPanel);

		//Create the button to save the results.
		final String id = "uploadPuppet";
		addButtonTextBoxPair(rightPanel, "Upload Puppet", "crux-Button", "taylor-TextBox", id, 
			new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					FlatMessageBox.show("Puppet file uploaded", MessageType.INFO);
				}
			}
		);

		CheckBox cb1 = new CheckBox("Puppet File 1");
		cb1.setStyleName("puppet-checkBox");
		rightPanel.add(cb1);
		CheckBox cb2 = new CheckBox("Puppet File 2");
		cb2.setStyleName("puppet-checkBox");
		rightPanel.add(cb2);
		CheckBox cb3 = new CheckBox("Puppet File 3");
		cb3.setStyleName("puppet-checkBox");
		rightPanel.add(cb3);

		//Create the button to save the results.
		Button removePuppetB = new Button();
		removePuppetB.setText("Remove Puppet");
		removePuppetB.setStyleName("crux-Button");
		removePuppetB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FlatMessageBox.show("Puppet removed!! ", MessageType.ERROR);
			}
		});		
		rightPanel.add(removePuppetB);
	}
}