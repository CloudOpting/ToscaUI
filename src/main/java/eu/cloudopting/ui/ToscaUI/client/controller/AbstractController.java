package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.ProgressBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public abstract class AbstractController {

	private static final Map<String, Object> context = new HashMap<String, Object>();
	
	private ProgressBox progress;
	
	public Map<String, Object> getContext() {
		return context;
	}
	
	protected void showProgress(String message) {
		progress = ProgressBox.show(message);
	}
	
	protected void hideProgress() {
		progress.hide();
	}
	
	protected void addLabelTextBoxPair(HTMLPanel panel, String labelText, 
			String labelStyle, String textBoxStyle, final String id, String value, boolean enabled) {
		//Create the widgets with parameters.
		Label label = new Label();
		label.setText(labelText);
		label.setStyleName(labelStyle);
		final TextBox textBox = new TextBox();
		textBox.setStyleName(textBoxStyle);
		textBox.setValue(value);
		textBox.setEnabled(enabled);
		
		//Add widget to the panel.
		panel.add(label);
		panel.add(textBox);
		
		//Add handler
		context.put(id, textBox);
//		textBox.addChangeHandler(new ChangeHandler() {
//			@Override
//			public void onChange(ChangeEvent event) {
//				map.put(id, textBox);
//			}
//		});
	}
	
	protected void addLabelTextBoxPair(HTMLPanel panel, String labelText, 
			String labelStyle, String textBoxStyle, final String id, String value) {
		addLabelTextBoxPair(panel, labelText, 
				labelStyle, textBoxStyle, id, value, true);
	}


	protected void addLabelTextBoxPair(HTMLPanel panel, String labelText, 
			String labelStyle, String textBoxStyle, final String id) {
		addLabelTextBoxPair(panel, labelText, 
				labelStyle, textBoxStyle, id, "", true);
//		//Create the widgets with parameters.
//		Label label = new Label();
//		label.setText(labelText);
//		label.setStyleName(labelStyle);
//		final TextBox textBox = new TextBox();
//		textBox.setStyleName(textBoxStyle);
//		
//		//Add widget to the panel.
//		panel.add(label);
//		panel.add(textBox);
		

	}
	
	protected void addLabelButtonPair(HTMLPanel panel, String labelText, 
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
	
	
	protected void addButtonLabelPair(HTMLPanel panel, String labelText, 
			String labelStyle, String listButtonStyle, final String id, String buttonText) {
		//Create the widgets with parameters.
		Label label = new Label();
		label.setText(labelText);
		label.setStyleName(labelStyle);
		final Button button = new Button();
		button.setStyleName(listButtonStyle);
		button.setText(buttonText);
		
		//Add widget to the panel.
		panel.add(button);
		panel.add(label);
		
		//Add handler
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FlatMessageBox.show("Button", MessageType.INFO);
			}
		});
	}

	
	protected void addButtonTextBoxPair(HTMLPanel panel, String buttonText, 
			String buttonStyle, String textBoxStyle, final String id, EventHandler buttonHandler) {
		//Create the widgets with parameters.
		final Button button = new Button();
		button.setStyleName(buttonStyle);
		button.setText(buttonText);
		
		final TextBox textBox = new TextBox();
		textBox.setStyleName(textBoxStyle);
		
		//Add widget to the panel.
		panel.add(button);
		panel.add(textBox);
		
		//Add the correct handler.
		if(buttonHandler instanceof ClickHandler){
			button.addClickHandler((ClickHandler)buttonHandler);	
		} else if (buttonHandler instanceof KeyDownHandler) {
			button.addKeyDownHandler((KeyDownHandler)buttonHandler);
		} else if (buttonHandler instanceof BlurHandler) {
			button.addBlurHandler((BlurHandler)buttonHandler);
		} 
		
		else {
			//WHAT DO WE DO? EXCEPTION??
		}
		
		//Add handler
		context.put(id, textBox);
//		textBox.addChangeHandler(new ChangeHandler() {
//			@Override
//			public void onChange(ChangeEvent event) {
//				map.put(id, textBox.getValue());
//			}
//		});
	}

	protected void addLabelListBoxPair(HTMLPanel panel, String labelText, List<String> listItems,
			String labelStyle, String listBoxStyle, final String id, ChangeHandler handler) {
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
		context.put(id, listBox);
		if(handler!=null) {
			listBox.addChangeHandler(handler);	
		}
		
	}
	
	protected void addLabelListBoxPair(HTMLPanel panel, String labelText, List<String> listItems,
			String labelStyle, String listBoxStyle, final String id) {
		
		addLabelListBoxPair(panel, labelText, listItems,
				labelStyle, listBoxStyle, id, null);
//		
//		//Create the widgets with parameters.
//		Label label = new Label();
//		label.setText(labelText);
//		label.setStyleName(labelStyle);
//		final ListBox listBox = new ListBox();
//		listBox.setStyleName(listBoxStyle);
//		for(String item : listItems){
//			listBox.addItem(item);
//		}
//		
//		//Add widget to the panel.
//		panel.add(label);
//		panel.add(listBox);
//		
//		//Add handler

	}

	protected void setScreenHeader(HTMLPanel panel, String title) {
		Label l = new Label();
		l.setStyleName("header-ScreenLabel");
		l.setText(title);
		panel.add(l);
		panel.setStyleName("panelScreen");
	}

}
