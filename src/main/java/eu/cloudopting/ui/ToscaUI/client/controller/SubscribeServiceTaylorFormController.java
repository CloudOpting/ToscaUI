package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.View;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("subscribeServiceTaylorFormController")
public class SubscribeServiceTaylorFormController 
{
	private final Map<String, String> map = new HashMap<String, String>();
	
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
		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("serviceParametersPanel");
		panel.setTitle("Service Parameters");
		panel.setStyleName("serviceParametersPanel");
		
		addLabelListBoxPair(panel, "Cloud Node:", listItems, "taylor-Label", "taylor-ListBox", "cloudNode");
		addLabelTextBoxPair(panel, "URL/Domain:", "taylor-Label", "taylor-TextBox", "urlDomain");
		
		//Create a new panel 
		HTMLPanel newPanel = new HTMLPanel("");//new HTMLPanel("Service Flavour");
		newPanel.setStyleName("serviceFlavour");
		panel.add(newPanel);

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
				
				FlatMessageBox.show("Subscribe Service DONE!! " + map, MessageType.SUCCESS);
				
			}
		});		
		
		panel.add(subscribeServiceB);
	}


	@Expose
	public void subscribeService()
	{
		
		FlatMessageBox.show("Subscribe!!!", MessageType.INFO);
	}

	@BindView("subscribeServiceTaylorFormView")
	public static interface SubscribeServiceTaylorFormView extends WidgetAccessor
	{
		HTMLPanel serviceParametersPanel();
	}
	
	/*
	 * PRIVATE METHODS
	 *
	 */
	private void addLabelTextBoxPair(HTMLPanel panel, String labelText, 
			String labelStyle, String textBoxStyle, final String id) {
		//Create the widgets with parameters.
		Label label = new Label();
		label.setText(labelText);
		label.setStyleName(labelStyle);
		final TextBox textBox = new TextBox();
		textBox.setStyleName(textBoxStyle);
		
		//Add widget to the panel.
		panel.add(label);
		panel.add(textBox);
		
		//Add handler
		map.put(id, "");
		textBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				map.put(id, textBox.getValue());
			}
		});
	}

	private void addLabelListBoxPair(HTMLPanel panel, String labelText, List<String> listItems,
			String labelStyle, String listBoxStyle, final String id) {
		//Create the widgets with parameters.
		Label label = new Label();
		label.setText(labelText);
		label.setStyleName(labelStyle);
		final ListBox listBox = new ListBox();
		listBox.setStyleName(listBoxStyle);
		for(String item : listItems){
			listBox.addItem(item);
		}
		
		//Add widget to the panel.
		panel.add(label);
		panel.add(listBox);
		
		//Add handler
		map.put(id, listBox.getItemText(0));
		listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				map.put(id, listBox.getItemText(listBox.getSelectedIndex()));
			}
		});
	}

}