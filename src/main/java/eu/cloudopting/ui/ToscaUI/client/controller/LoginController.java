package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.screen.Screen;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.swapcontainer.HorizontalSwapContainer;

import com.google.gwt.user.client.ui.TextBox;

import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("loginController")
public class LoginController 
{
	@Inject
	public LoginView loginView;

	@Inject
	public IProxyAPIService connectApi;

	@Expose
	public void onLoad() {
//		if(connected) {
//			HorizontalSwapContainer views =  (HorizontalSwapContainer) Screen.get("views");
//			//HorizontalSwapContainer
//			views.showView("main");
//		}
		loginView.passwordTextBox().setStyleName("gwt-TextBox");
	}

	@Expose   
	public void login()
	{
		Callback<String> callback = new Callback<String>() {
			@Override
			public void onSuccess(String result) {
				if(result!=null) {
					HorizontalSwapContainer views =  (HorizontalSwapContainer) Screen.get("views");
					//HorizontalSwapContainer
					views.showView("menu");
				} else {
					FlatMessageBox.show("Try again!! Authentication ERROR: No SESSION ID recived.", MessageType.INFO);
				}
			}
			@Override
			public void onError(Exception e) {
				FlatMessageBox.show("Try again!! Authentication ERROR: " + e.getMessage(), MessageType.INFO);
			}
		};

		connectApi.connect(loginView.nameTextBox().getValue(), loginView.passwordTextBox().getValue(), callback);
		
	}  

	@BindView("login")
	public static interface LoginView extends WidgetAccessor
	{
		TextBox nameTextBox();
		TextBox passwordTextBox();
	}

}