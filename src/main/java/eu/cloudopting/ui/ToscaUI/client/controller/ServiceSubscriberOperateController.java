package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.View;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.styledpanel.StyledPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("serviceSubscriberOperateController")
public class ServiceSubscriberOperateController 
{
	@Inject
	public ServiceSubscriberOperateView serviceSubscriberOperateView;

	@Expose
	public void onLoad() {
		
		//GET VALUES FROM THE DATABASE
		
		
		/*
		 * END MOCK VARIABLES AND LISTS.
		 */

		//Get access to the panel
		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("serviceParametersPanel");
		panel.setTitle("Manage Clearo");
		panel.setStyleName("serviceParametersPanel");

		Label label = new Label();
		label.setText("Manage Clearo");
		panel.add(label);
		
		Label label2 = new Label();
		label2.setText("Manage Clearo Operations");
		panel.add(label2);
		
		//Create a new panel 
		HTMLPanel manageOperations = new HTMLPanel("");
		manageOperations.setStyleName("manageOperations");
		panel.add(manageOperations);
		
		addLabelButtonPair(manageOperations, "Handle resource request:", "taylor-Label-Big", "taylor-Button", "cloudNode", "Update Clearo");
		addLabelButtonPair(manageOperations, "Decomission Clearo INstance:", "taylor-Label-Big", "taylor-Button", "urlDomain", "Decomission");
		
		Label label3 = new Label();
		label3.setText("Follow Clearo KPI's");
		panel.add(label3);
		
		//Create a new panel 
		HTMLPanel followKPIs = new HTMLPanel("");
		followKPIs.setStyleName("followKPIs");
		panel.add(followKPIs);

		addLabelTextBoxPair(followKPIs, "CPU Usage:", "taylor-Label", "taylor-TextBox", "numberCPUs", "2");
		addLabelTextBoxPair(followKPIs, "Bandwith Usage:", "taylor-Label", "taylor-TextBox", "bandwith", "200Gb/month");
		addLabelTextBoxPair(followKPIs, "Disk Space Usage:", "taylor-Label", "taylor-TextBox", "diskSpace", "256GB");
		addLabelTextBoxPair(followKPIs, "RAM Memory Usage:", "taylor-Label", "taylor-TextBox", "memoryRAM", "2048");

		Label label4 = new Label();
		label4.setText("Check your Terms and Conditions");
		panel.add(label4);
	}


	@Expose
	public void subscribeService()
	{
		
		FlatMessageBox.show("Subscribe!!!", MessageType.INFO);
	}

	@BindView("serviceSubscriberOperateView")
	public static interface ServiceSubscriberOperateView extends WidgetAccessor
	{
		HTMLPanel serviceParametersPanel();
	}
	
	/*
	 * PRIVATE METHODS
	 *
	 */
	private void addLabelTextBoxPair(HTMLPanel panel, String labelText, 
			String labelStyle, String textBoxStyle, final String id, String value) {
		//Create the widgets with parameters.
		Label label = new Label();
		label.setText(labelText);
		label.setStyleName(labelStyle);
		final TextBox textBox = new TextBox();
		textBox.setStyleName(textBoxStyle);
		textBox.setValue(value);
		textBox.setEnabled(false);
		
		//Add widget to the panel.
		panel.add(label);
		panel.add(textBox);
	}

	
	private void addLabelButtonPair(HTMLPanel panel, String labelText, 
			String labelStyle, String listButtonStyle, final String id, String buttonText) {
		//Create the widgets with parameters.
		Label label = new Label();
		label.setText(labelText);
		label.setStyleName(labelStyle);
		final Button button = new Button();
		button.setStyleName(listButtonStyle);
		button.setText(buttonText);
		
		//Add widget to the panel.
		panel.add(label);
		panel.add(button);
		
		//Add handler
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FlatMessageBox.show("Button", MessageType.INFO);
			}
		});
	}

}