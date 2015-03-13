package eu.cloudopting.ui.ToscaUI.server.model;

import org.cruxframework.crux.widgets.client.styledpanel.StyledPanel;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * 
 * @author xeviscc
 *
 */
public class StoryboardItem extends Composite {

	private FlowPanel image = new FlowPanel();
	private StyledPanel wrapper = new StyledPanel();
	private Label labelName = new Label();
	private Label labelOther = new Label();
	private Button button = new Button();
	private String url;
	private HTMLPanel htmlPanel = new HTMLPanel(url);
	
	public StoryboardItem(final String clickedMsg, String name, String price, String textButton, ClickHandler handler)
	{
		initWidget(wrapper);
		
		wrapper.add(htmlPanel);
		
		htmlPanel.add(image);
		htmlPanel.add(labelName);
		htmlPanel.add(labelOther);
		htmlPanel.add(button);
		
		this.addStyleNames();
		
		labelName.setText(name);
		labelOther.setText(price);
		button.setText(textButton);
		
		button.addClickHandler(handler);
		
		
	}
	
	private void addStyleNames() {
		wrapper.addStyleName("storeItem");
		htmlPanel.addStyleName("cf");
		image.addStyleName("productImage");
		labelName.removeStyleName("gwt-Label");
		labelName.addStyleName("productName");
		labelOther.removeStyleName("gwt-Label");
		labelOther.addStyleName("productOther");
		button.addStyleName("productButton");
	}
}
