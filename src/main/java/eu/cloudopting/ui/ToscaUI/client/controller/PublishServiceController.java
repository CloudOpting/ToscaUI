package eu.cloudopting.ui.ToscaUI.client.controller;


import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.formdisplay.FormDisplay;
import org.cruxframework.crux.widgets.client.select.SingleSelect;
import org.cruxframework.crux.widgets.client.textarea.TextArea;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("publishServiceController")
public class PublishServiceController extends AbstractController
{
	@Inject
	public PublishServiceView view;

	private final static String PAGE_NAME = "Publish Service";
	
	@Expose
	public void onLoad() {
		buildView();
	}

	private void buildView() {
		setScreenHeader(view.panelScreen(), PAGE_NAME);
		
		FormDisplay formDisplay = new FormDisplay();
		formDisplay.setStyleName("publishService-Form");
		view.panelScreen().add(formDisplay);
		
		TextBox tb = new TextBox();
		formDisplay.addEntry("Service Name", tb, HasHorizontalAlignment.ALIGN_DEFAULT);
		TextArea ta = new TextArea();
		ta.setHeight("243px");
		ta.setWidth("630px");
		formDisplay.addEntry("Service Description", ta, HasHorizontalAlignment.ALIGN_DEFAULT);
		
		FileUpload upload = new FileUpload();
		upload.setName("Choose Tosca Template Upload");
		view.panelScreen().add(upload);
		
		Label labelCL = new Label();
		labelCL.setText("Content Library");
		view.panelScreen().add(labelCL);
		
		SingleSelect singleSelect = view.singleSelect();
		singleSelect.addItem("Item One", "value");
		singleSelect.addItem("Item Two", "value");
		singleSelect.addItem("Item Three", "value");
		singleSelect.addItem("Item Four", "value");
		view.panelScreen().add(singleSelect);
		
		Button buttonAdd = new Button();
		buttonAdd.setText("Add Item");
		buttonAdd.setStyleName("crux-Button");
		buttonAdd.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addItem();				
			}
		});
		view.panelScreen().add(buttonAdd);
		
		Button buttonDelete = new Button();
		buttonDelete.setText("Delete Item");
		buttonDelete.setStyleName("crux-Button");
		buttonDelete.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deleteItem();				
			}
		});
		view.panelScreen().add(buttonDelete);
		
		Button buttonSave = new Button();
		buttonSave.setText("Save Configuration");
		buttonSave.setStyleName("crux-Button");
		buttonSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				saveConfiguration();				
			}
		});
		view.panelScreen().add(buttonSave);
		
		
		Button buttonPublish = new Button();
		buttonPublish.setText("Publish Service");
		buttonPublish.setStyleName("crux-Button");
		buttonPublish.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				publishService();				
			}
		});
		view.panelScreen().add(buttonPublish);
	}
	
	private void addItem() {}
	
	private void deleteItem() {}
	
	public void saveConfiguration() {}
	
	@Expose
	public void itemSelected() {
	}
	
	public void publishService() {}

	@BindView("publishService")
	public static interface PublishServiceView extends WidgetAccessor
	{
		HTMLPanel panelScreen();
		SingleSelect singleSelect();
	}

	/*
	 * PRIVATE METHODS
	 */

}