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
	@Inject
	public SubscribeServiceTaylorFormView subscribeServiceTaylorFormView;

	@Expose
	public void onLoad() {
		/*
		 * Not working. Why?
		 */
//		subscribeServiceTaylorFormView.serviceParametersPanel().add(label, "label");
//		subscribeServiceTaylorFormView.serviceParametersPanel().add(listBox, "listbox");
		
		//GET VALUES FROM THE DATABASE
		
		//MOCK VARIABLES AND LISTS.

		List<String> listItems = new ArrayList<String>(); 
		listItems.add("item");
		listItems.add("item2");
		listItems.add("item3");
		
		List<String> listOSs = new ArrayList<String>(); 
		listOSs.add("operting1");
		listOSs.add("operting2");
		listOSs.add("operting3");
		
		List<String> listCSSs = new ArrayList<String>(); 
		listCSSs.add("css1");
		listCSSs.add("css2");
		listCSSs.add("css3");

		List<String> listCPUs = new ArrayList<String>(); 
		listCPUs.add("1");
		listCPUs.add("2");
		listCPUs.add("4");

		/*
		 * Alternative
		 */
		//Get access to the panel
		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("serviceParametersPanel");
		panel.setTitle("Service Parameters");
		panel.setStyleName("serviceParametersPanel");
		
		addLabelListBoxPair(panel, "Cloud Node:", listItems, "taylor-Label", "taylor-ListBox");
		addLabelTextBoxPair(panel, "URL/Domain:", "taylor-Label", "taylor-TextBox");
		
		//Create a new panel 
		HTMLPanel newPanel = new HTMLPanel("");;//new HTMLPanel("Service Flavour");
		newPanel.setStyleName("serviceFlavour");
		panel.add(newPanel);

		addLabelListBoxPair(newPanel, "Operating System:", listOSs, "taylor-Label", "taylor-ListBox");
		addLabelListBoxPair(newPanel, "CSS Skin:", listCSSs, "taylor-Label", "taylor-ListBox");
		addLabelListBoxPair(newPanel, "Number CPU's:", listCPUs, "taylor-Label", "taylor-ListBox");
		addLabelTextBoxPair(newPanel, "Bandwith:", "taylor-Label", "taylor-TextBox");
		addLabelTextBoxPair(newPanel, "Disk Space:", "taylor-Label", "taylor-TextBox");
		addLabelTextBoxPair(newPanel, "Memory RAM:", "taylor-Label", "taylor-TextBox");

		//Create the button
		Button subscribeServiceB = new Button();
		subscribeServiceB.setText("Subscribe Service");
		subscribeServiceB.setStyleName("crux-Button");
		subscribeServiceB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				FlatMessageBox.show("Subscribe Service DONE!!", MessageType.SUCCESS);
				
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
		//		DivElement serviceParametersBox();
		//		DivElement serviceFlavourBox();
		HTMLPanel serviceParametersPanel();
		//		Table tableServiceParametersBox();
		//		Table tableServiceFlavourBox();

	}
	
	/*
	 * PRIVATE METHODS
	 *
	 */
	private void addLabelTextBoxPair(HTMLPanel panel, String labelText, 
			String labelStyle, String textBoxStyle) {
		//Create the widgets with parameters.
		Label label = new Label();
		label.setText(labelText);
		label.setStyleName(labelStyle);
		TextBox textBox = new TextBox();
		textBox.setStyleName(textBoxStyle);
		
		//Add widget to the panel.
		panel.add(label);
		panel.add(textBox);
	}

	private void addLabelListBoxPair(HTMLPanel panel, String labelText, List<String> listItems,
			String labelStyle, String listBoxStyle) {
		//Create the widgets with parameters.
		Label label = new Label();
		label.setText(labelText);
		label.setStyleName(labelStyle);
		ListBox listBox = new ListBox();
		listBox.setStyleName(listBoxStyle);
		for(String item : listItems){
			listBox.addItem(item);
		}
		
		//Add widget to the panel.
		panel.add(label);
		panel.add(listBox);
	}

}