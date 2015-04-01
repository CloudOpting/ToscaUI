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
	public LoginView loginView;

	@Inject
	public IProxyAPIService connectApi;
	
	@Expose
	public void onLoad() {
		
		connectApi.connected(
				new Callback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							((HorizontalSwapContainer) Screen.get("views")).showView("menu");
						}
					}
					@Override
					public void onError(Exception e) {
					}
				});
		
		
		//Set custom style to the password text box.
//		loginView.passwordTextBox().setStyleName("gwt-TextBox");

		//Add handler for Enter key pressed.
		loginView.passwordTextBox().addKeyPressHandler(this);
		loginView.nameTextBox().addKeyPressHandler(this);
		
		//Set focus to the name text box
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	        public void execute () {
	        	loginView.nameTextBox().setFocus(true);
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
		Button okButton();
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