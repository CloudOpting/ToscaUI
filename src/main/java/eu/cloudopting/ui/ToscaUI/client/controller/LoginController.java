package eu.cloudopting.ui.ToscaUI.client.controller;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.screen.Screen;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.SingleViewContainer;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.simplecontainer.SimpleViewContainer;
import org.cruxframework.crux.widgets.client.styledpanel.StyledPanel;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;

import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;

/**
 * 
 * @author xeviscc
 *
 */
@Controller("loginController")
public class LoginController  extends AbstractController implements KeyPressHandler
{
	@Inject
	public LoginView view;

	@Inject
	public IProxyAPIService connectApi;

	@Expose
	public void onLoad() {

		connectApi.connected(
				new Callback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							((SimpleViewContainer) Screen.get("loginView")).setVisible(false);
							((SimpleViewContainer) Screen.get("views")).showView("menu");
						} else {
						}
					}
					@Override
					public void onError(Exception e) {
					}
				});
		
//		WaitBox.hideAllDialogs();

		//Add handler for Enter key pressed.
		view.passwordTextBox().addKeyPressHandler(this);
		view.nameTextBox().addKeyPressHandler(this);

		//Set focus to the name text box
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			public void execute () {
				view.nameTextBox().setFocus(true);
			}
		});
	}

	@Expose   
	public void login()
	{
		Callback<Boolean> callback = new Callback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result!=null) {
					((SingleViewContainer) Screen.get("views")).showView("menu");
				} else {
					FlatMessageBox.show("Try again!! Authentication ERROR: No SESSION ID recived.", MessageType.INFO);
				}
			}
			@Override
			public void onError(Exception e) {
				FlatMessageBox.show("Try again!! Authentication ERROR: " + e.getMessage(), MessageType.INFO);
			}
		};

		connectApi.connect(view.nameTextBox().getValue(), view.passwordTextBox().getValue(), callback);

	}  

	@BindView("login")
	public static interface LoginView extends WidgetAccessor
	{
		TextBox nameTextBox();
		TextBox passwordTextBox();
		Button okButton();
		StyledPanel panelScreen();
	}

	@Override
	public void onKeyPress(KeyPressEvent event_)
	{
		if (KeyCodes.KEY_ENTER == event_.getNativeEvent().getKeyCode())
		{
			login();
		}
	}

}