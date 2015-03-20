package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.View;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;

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

		/*
		 * Alternative
		 */
		//Get access to the panel
		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("serviceParametersPanel");
		panel.setTitle("Service Parameters");
		panel.setStyleName("serviceParametersPanel");
		//Get the parameters to request.

		//Create the widgets with parameters.
		Label cloudNodeL = new Label();
		cloudNodeL.setText("Cloud Node");		
		ListBox listBox = new ListBox();
		listBox.addItem("item");
		listBox.addItem("item2");
		listBox.addItem("item3");
		
		//Add widget to the panel.
		panel.add(cloudNodeL);
		panel.add(listBox);

		//Create the widgets with parameters.
		Label urlDomainL = new Label();
		urlDomainL.setText("URL/Domain:");
		TextBox urlDomainTB = new TextBox();
		
		//Add widget to the panel.
		panel.add(urlDomainL);
		panel.add(urlDomainTB);
		
		HTMLPanel newPanel = new HTMLPanel("Service Flavour");
		newPanel.setStyleName("serviceFlavour");
		
		panel.add(newPanel);
		
		//Create the widgets with parameters.
		Label operatinSystemL = new Label();
		operatinSystemL.setText("Operating System");
		ListBox operatinSystemLB = new ListBox();
		operatinSystemLB.addItem("operting1");
		operatinSystemLB.addItem("operting2");
		operatinSystemLB.addItem("operting3");

		//Add widget to the panel.
		newPanel.add(operatinSystemL);
		newPanel.add(operatinSystemLB);
		
		//Create the widgets with parameters.
		Label cssSkinL = new Label();
		cssSkinL.setText("CSS Skin");
		ListBox cssSkinLB = new ListBox();
		cssSkinLB.addItem("css1");
		cssSkinLB.addItem("css2");
		cssSkinLB.addItem("css3");

		//Add widget to the panel.
		newPanel.add(cssSkinL);
		newPanel.add(cssSkinLB);
		
		//Create the widgets with parameters.
		Label numberCPUsL = new Label();
		numberCPUsL.setText("Number CPU's");
		ListBox numberCPUsLB = new ListBox();
		numberCPUsLB.addItem("1");
		numberCPUsLB.addItem("2");
		numberCPUsLB.addItem("4");
		
		//Add widget to the panel.
		newPanel.add(numberCPUsL);
		newPanel.add(numberCPUsLB);
		
		//Create the widgets with parameters.
		Label bandwithL = new Label();
		bandwithL.setText("Bandwith");
		TextBox bandwithTB = new TextBox();
		
		//Add widget to the panel.
		newPanel.add(bandwithL);
		newPanel.add(bandwithTB);
		
		//Create the widgets with parameters.
		Label diskSpaceL = new Label();
		diskSpaceL.setText("Disk Space");
		TextBox diskSpaceTB = new TextBox();
		
		//Add widget to the panel.
		newPanel.add(diskSpaceL);
		newPanel.add(diskSpaceTB);

		//Create the widgets with parameters.
		Label memoryRAML = new Label();
		memoryRAML.setText("Memory RAM");
		TextBox memoryRAMTB = new TextBox();
		
		//Add widget to the panel.
		newPanel.add(memoryRAML);
		newPanel.add(memoryRAMTB);

		//Add widgets to the view.
		
		
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
}