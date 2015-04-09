package eu.cloudopting.ui.ToscaUI.server.model;

import com.google.gwt.user.client.ui.Composite;

/**
 * 
 * @author xeviscc
 *
 */
public class StoryboardItem extends Composite {

	private String clickedMsg; 
	private Integer id; 
	private String name; 
	private String description; 
	private String textButton; 
	
	public StoryboardItem()	{}
	
	public void setStoryboardItem(StoryboardItem storyboardItem) {
		this.id = storyboardItem.getId();
		this.clickedMsg = storyboardItem.getClickedMsg();
		this.name = storyboardItem.getName();
		this.description = storyboardItem.getDescription();
		this.textButton = storyboardItem.getTextButton();
	}
	
	public StoryboardItem(final String clickedMsg, Integer id, String name, String description, String textButton)
	{
		this.id = id;
		this.clickedMsg = clickedMsg;
		this.name = name;
		this.description = description;
		this.textButton = textButton;
	}

	public String getClickedMsg() {
		return clickedMsg;
	}

	public void setClickedMsg(String clickedMsg) {
		this.clickedMsg = clickedMsg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTextButton() {
		return textButton;
	}

	public void setTextButton(String textButton) {
		this.textButton = textButton;
	}
}
