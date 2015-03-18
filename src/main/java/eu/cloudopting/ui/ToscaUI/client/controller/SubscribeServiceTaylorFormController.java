package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.View;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

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
		//Get the parameters to request.

		//Create the widgets with parameters.
		final Label label = new Label();
		label.setText("Cloud Node");		
		label.setWidth("33%");
		ListBox listBox = new ListBox();
		listBox.addItem("item");
		listBox.addItem("item2");
		listBox.addItem("item3");
		listBox.setWidth("67%");

		//Add widgets to the view.
		/*
		 * Not working. Why?
		 */
//		subscribeServiceTaylorFormView.serviceParametersPanel().add(label, "label");
//		subscribeServiceTaylorFormView.serviceParametersPanel().add(listBox, "listbox");
		
		/*
		 * Alternative
		 */
		HTMLPanel panel = (HTMLPanel)View.of(this).getWidget("serviceParametersPanel");
		panel.add(label);
		panel.add(listBox);
		
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