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

/**
 * 
 * @author xeviscc
 *
 */
@Controller("serviceSubscriberOperateController")
public class ServiceSubscriberOperateController extends AbstractController
{
	@Inject
	public ServiceSubscriberOperateView serviceSubscriberOperateView;

	@Expose
	public void onLoad() {
		
		//GET VALUES FROM THE DATABASE
		
		String name = "Clearò";
		
		/*
		 * END MOCK VARIABLES AND LISTS.
		 */

		//Get access to the panel
		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("mainPanel");
//		panel.setTitle("Manage "+name);
//		panel.setStyleName("serviceParametersPanel");

		HTMLPanel innerPanel = new HTMLPanel("");
		innerPanel.setStyleName("innerPanel");
		panel.add(innerPanel);
		
		Label label = new Label();
		label.setText("Manage "+name);
		label.setStyleName("header-Label");
		innerPanel.add(label);
		
//		Label label2 = new Label();
//		label2.setText("Manage Clearo Operations");
//		panel.add(label2);
		
		//Create a new panel 
		HTMLPanel manageOperations = new HTMLPanel("<span class=\"mo_text\">Manage "+name+" Operations</span>");
		manageOperations.setStyleName("manageOperations");
		innerPanel.add(manageOperations);
		
		addLabelButtonPair(manageOperations, "Handle resource request:", "taylor-Label-Big", "crux-Button taylor-Button", "cloudNode", "Update "+name);
		addLabelButtonPair(manageOperations, "Decomission "+name+" Instance:", "taylor-Label-Big", "crux-Button taylor-Button", "urlDomain", "Decomission");
		
//		Label label3 = new Label();
//		label3.setText("Follow Clearo KPI's");
//		panel.add(label3);
		
		//Create a new panel 
		HTMLPanel followKPIs = new HTMLPanel("<span class=\"mo_text\">Follow "+name+" KPI's</span>");
		followKPIs.setStyleName("followKPIs");
		innerPanel.add(followKPIs);

		addLabelTextBoxPair(followKPIs, "CPU Usage:", "taylor-Label", "taylor-TextBox", "numberCPUs", "2");
		addLabelTextBoxPair(followKPIs, "Bandwith Usage:", "taylor-Label", "taylor-TextBox", "bandwith", "200Gb/month");
		addLabelTextBoxPair(followKPIs, "Disk Space Usage:", "taylor-Label", "taylor-TextBox", "diskSpace", "256GB");
		addLabelTextBoxPair(followKPIs, "RAM Memory Usage:", "taylor-Label", "taylor-TextBox", "memoryRAM", "2048");

		Label label4 = new Label();
		label4.setText("Check your Terms and Conditions");
		innerPanel.add(label4);
	}


	@Expose
	public void subscribeService()
	{
		
		FlatMessageBox.show("Subscribe!!!", MessageType.INFO);
	}

	@BindView("serviceSubscriberOperateView")
	public static interface ServiceSubscriberOperateView extends WidgetAccessor
	{
		HTMLPanel mainPanel();
	}
	
}