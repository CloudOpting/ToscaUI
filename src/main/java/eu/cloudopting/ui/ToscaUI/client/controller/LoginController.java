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
	public IProxyAPIService api;

	/*
	 * CALLBACKS
	 */
	private Callback<Boolean> callbackLogin = new Callback<Boolean>() {
		@Override
		public void onSuccess(Boolean result) {
			if(result) {
				((SimpleViewContainer) Screen.get("loginView")).setVisible(false);
				((SimpleViewContainer) Screen.get("menu")).setVisible(true);
				((SingleViewContainer) Screen.get("views")).showView("serviceCatalogList");
			} else {
				FlatMessageBox.show("Try again!! Authentication ERROR: No SESSION ID recived.", MessageType.INFO);
			}
		}
		@Override
		public void onError(Exception e) {
			FlatMessageBox.show("Try again!! Authentication ERROR: " + e.getMessage(), MessageType.INFO);
		}
	};
	
	private Callback<Boolean> callbackOnload = new Callback<Boolean>() {
		@Override
		public void onSuccess(Boolean result) {
			if(result) {
				((SimpleViewContainer) Screen.get("loginView")).setVisible(false);
				((SimpleViewContainer) Screen.get("menu")).setVisible(true);
				((SingleViewContainer) Screen.get("views")).showView("serviceCatalogList");
			} else {
			}
		}
		@Override
		public void onError(Exception e) {
		}
	};
	
	@Expose
	public void onLoad() {
		api.connected(callbackOnload);
		
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
		api.connect(view.nameTextBox().getValue(), view.passwordTextBox().getValue(), callbackLogin);
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