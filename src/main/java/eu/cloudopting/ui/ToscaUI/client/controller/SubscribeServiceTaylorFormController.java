package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.View;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("subscribeServiceTaylorFormController")
public class SubscribeServiceTaylorFormController extends AbstractController
{
	@Inject
	public SubscribeServiceTaylorFormView subscribeServiceTaylorFormView;

	@Expose
	public void onLoad() {
		
		//GET VALUES FROM THE DATABASE
		
		/*
		 * INIT MOCK VARIABLES AND LISTS.
		 */

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
		
		/*
		 * END MOCK VARIABLES AND LISTS.
		 */

		//Get access to the panel
		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("mainPanel");
		
		//Create a new panel 
		HTMLPanel innerPanel = new HTMLPanel("<span class=\"mo_text\">Service Parameters</span>");
		innerPanel.setStyleName("serviceParametersPanel");
		panel.add(innerPanel);
		
		addLabelListBoxPair(innerPanel, "Cloud Node:", listItems, "taylor-Label", "taylor-ListBox", "cloudNode");
		addLabelTextBoxPair(innerPanel, "URL/Domain:", "taylor-Label", "taylor-TextBox", "urlDomain");
		
		//Create a new panel 
		HTMLPanel newPanel = new HTMLPanel("<span class=\"mo_text\">Service Flavour</span>");
		newPanel.setStyleName("serviceFlavour");
		innerPanel.add(newPanel);

		addLabelListBoxPair(newPanel, "Operating System:", listOSs, "taylor-Label", "taylor-ListBox", "operatingSystem");
		addLabelListBoxPair(newPanel, "CSS Skin:", listCSSs, "taylor-Label", "taylor-ListBox", "cssSkin");
		addLabelListBoxPair(newPanel, "Number CPU's:", listCPUs, "taylor-Label", "taylor-ListBox", "numberCPUs");
		addLabelTextBoxPair(newPanel, "Bandwith:", "taylor-Label", "taylor-TextBox", "bandwith");
		addLabelTextBoxPair(newPanel, "Disk Space:", "taylor-Label", "taylor-TextBox", "diskSpace");
		addLabelTextBoxPair(newPanel, "Memory RAM:", "taylor-Label", "taylor-TextBox", "memoryRAM");

		//Create the button to submit the results.
		Button subscribeServiceB = new Button();
		subscribeServiceB.setText("Subscribe Service");
		subscribeServiceB.setStyleName("crux-Button");
		subscribeServiceB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				FlatMessageBox.show("Subscribe Service DONE!! " + getMap(), MessageType.SUCCESS);
				
			}
		});		
		
		innerPanel.add(subscribeServiceB);
	}


	@Expose
	public void subscribeService()
	{
		
		FlatMessageBox.show("Subscribe!!!", MessageType.INFO);
	}

	@BindView("subscribeServiceTaylorFormView")
	public static interface SubscribeServiceTaylorFormView extends WidgetAccessor
	{
		HTMLPanel mainPanel();
	}

}