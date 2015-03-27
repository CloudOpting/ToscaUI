package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.select.SingleSelect;
import org.cruxframework.crux.widgets.client.textarea.TextArea;
import org.cruxframework.crux.widgets.client.uploader.FileUploader;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("publishServiceController")
public class PublishServiceController extends AbstractController
{
	@Inject
	public PublishServiceView publishServiceView;

	@Expose
	public void onLoad() {}
	
	@Expose
	public void addItem() {}
	
	@Expose
	public void deleteItem() {}
	
	@Expose
	public void saveConfiguration() {}
	
	@Expose
	public void itemSelected() {
	}
	
	@Expose
	public void publishService() {}

	@BindView("publishServiceView")
	public static interface PublishServiceView extends WidgetAccessor
	{
		TextArea serviceDescription();
		SingleSelect singleSelect();
		FileUploader fileUploader();

	}

}