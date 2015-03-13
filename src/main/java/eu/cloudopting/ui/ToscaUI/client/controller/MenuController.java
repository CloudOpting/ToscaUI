package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.disposal.topmenudisposal.TopMenuDisposal;

import com.google.gwt.user.client.ui.HTML;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("menuController")
public class MenuController 
{
	@Inject
	public MenuView menuView;

	@Expose
	public void onLoad() {}

	@BindView("menuView")
	public static interface MenuView extends WidgetAccessor
	{
		TopMenuDisposal menu();
		HTML componentDescription();
	}

}