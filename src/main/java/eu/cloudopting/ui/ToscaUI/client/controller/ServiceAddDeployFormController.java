package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("serviceAddDeployFormController")
public class ServiceAddDeployFormController extends AbstractController
{
	@Inject
	public ServiceAddDeployFormView serviceAddDeployFormView;

	@Expose
	public void onLoad() {
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

		List<String> listCPUs = new ArrayList<String>(); 
		listCPUs.add(0, "1");
		listCPUs.add(1, "2");
		listCPUs.add(2, "4");

		buildView(listItems, listOSs, listCSSs, listCPUs);
	}


	@Expose
	public void subscribeService()
	{
		FlatMessageBox.show("Subscribe!!!", MessageType.INFO);
	}

	@BindView("serviceAddDeployForm")
	public static interface ServiceAddDeployFormView extends WidgetAccessor
	{
		HTMLPanel mainPanel();
	}

	private final void buildView(List<String> listItems, List<String> listOSs,
			List<String> listCSSs, List<String> listCPUs) {

		//Get access to the panel
//		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("mainPanel");
		HTMLPanel panel = serviceAddDeployFormView.mainPanel();

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

		//Create a new panel 
		HTMLPanel newPanel = new HTMLPanel("<span class=\"mo_text\">Service Parameters</span>");
		newPanel.setStyleName("serviceParameters");
		panel.add(newPanel);

		//Create the left panel.
		HTMLPanel leftPanel = new HTMLPanel("");
		leftPanel.setStyleName("leftPanel");
		newPanel.add(leftPanel);

		addLabelListBoxPair(leftPanel, "Cloud Node:", listItems, "taylor-Label", "taylor-ListBox", "cloudNode");
		addLabelListBoxPair(leftPanel, "Operating System:", listOSs, "taylor-Label", "taylor-ListBox", "operatingSystem");
		addLabelListBoxPair(leftPanel, "Number CPU's:", listCPUs, "taylor-Label", "taylor-ListBox", "numberCPUs");
		addLabelTextBoxPair(leftPanel, "Disk Space:", "taylor-Label", "taylor-TextBox", "diskSpace");
		addLabelTextBoxPair(leftPanel, "Memory RAM:", "taylor-Label", "taylor-TextBox", "memoryRAM");
		addLabelTextBoxPair(leftPanel, "Bandwith:", "taylor-Label", "taylor-TextBox", "bandwith");		
		addLabelTextBoxPair(leftPanel, "URL/Domain:", "taylor-Label", "taylor-TextBox", "urlDomain");
		addLabelListBoxPair(leftPanel, "CSS Skin:", listCSSs, "taylor-Label", "taylor-ListBox", "cssSkin");

		//Create the right panel
		HTMLPanel rightPanel = new HTMLPanel("<span class=\"mo_text\">Puppet Files</span>");
		rightPanel.setStyleName("rightPanel");
		newPanel.add(rightPanel);

		//Create the button to save the results.
		final String id = "uploadPuppet";

		addButtonTextBoxPair(rightPanel, "Upload Puppet", "crux-Button", "taylor-TextBox", id, 
			new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					FlatMessageBox.show("UPLOAD PUPPET: " + getMap().get(id), MessageType.INFO);
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


		//Create the button to save the results.
		Button saveTemplateB = new Button();
		saveTemplateB.setText("Save Template");
		saveTemplateB.setStyleName("crux-Button");
		saveTemplateB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				FlatMessageBox.show("Template Saved correctly!! " + getMap(), MessageType.SUCCESS);

			}
		});		
		newPanel.add(saveTemplateB);

		//Create the button to uplad a new template.
		Button uploadTemplateB = new Button();
		uploadTemplateB.setText("Upload Template");
		uploadTemplateB.setStyleName("crux-Button");
		uploadTemplateB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				FlatMessageBox.show("Template Uploaded correctly!! " + getMap(), MessageType.SUCCESS);

			}
		});	


		newPanel.add(uploadTemplateB);
	}


}