package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.Date;
import java.util.Map;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.formdisplay.FormDisplay;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
import eu.cloudopting.ui.ToscaUI.client.remote.IUserInfo;
import eu.cloudopting.ui.ToscaUI.client.utils.Navigate;

@Controller("mainController")
public class MainController extends AbstractController
{

	@Inject
	public MainView mainView;

	@Inject
	public IProxyAPIService connectApi;

//	@Inject
//	public IUserInfo userInfo;

	@Expose
	public void onLoad() {
		Button button = new Button();
		button.setText("START REVIEW");
		button.setStyleName("crux-Button review-Button");
		
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				startReview();
				
			}
		});
		
		mainView.buttonPanel().add(button);
	}

	@Expose   
	public void getInfoUser()
	{
		
//		final Callback<Map<String,String>> callbackUserInfo = new Callback<Map<String,String>>() {
//			@Override
//			public void onSuccess(Map<String,String> result) {
//				mainView.createdBy().setValue(result.get("createdBy"));
//				mainView.createdDate().setValue(new Date(Long.parseLong(result.get("createdDate"))).toString());
//				mainView.lastModifiedBy().setValue(result.get("lastModifiedBy"));
//				mainView.lastModifiedDate().setValue(new Date(Long.parseLong(result.get("lastModifiedDate"))).toString());
//				mainView.id().setValue(result.get("id"));
//				mainView.login().setValue(result.get("login"));
//				mainView.firstName().setValue(result.get("firstName"));
//				mainView.lastName().setValue(result.get("lastName"));
//				mainView.email().setValue(result.get("email"));
//				mainView.activated().setValue(result.get("activated"));
//				mainView.langKey().setValue(result.get("langKey"));
//				mainView.activationKey().setValue(result.get("activationKey"));
//			}
//			@Override
//			public void onError(Exception e) {}
//		};
//
//		final Callback<String> callback = new Callback<String>() {
//			@Override
//			public void onSuccess(String json) {
//				if(json==null || json.isEmpty()) FlatMessageBox.show("The user [" + mainView.nameTextBox().getValue() + "] does not exist!!", MessageType.WARN);
//				else userInfo.getLastModifiedBy(json, callbackUserInfo);
//			}
//			@Override
//			public void onError(Exception e) {
//				FlatMessageBox.show("The user [" + mainView.nameTextBox().getValue() + "] does not exist!!", MessageType.ERROR);
//			}
//		};
//
//		connectApi.users(mainView.nameTextBox().getValue(), callback);

	}  

	@Expose
	public void clean() {
		mainView.createdBy().setValue("");
		mainView.createdDate().setValue("");
		mainView.lastModifiedBy().setValue("");
		mainView.lastModifiedDate().setValue("");
		mainView.id().setValue("");
		mainView.login().setValue("");
		mainView.firstName().setValue("");
		mainView.lastName().setValue("");
		mainView.email().setValue("");
		mainView.activated().setValue("");
		mainView.langKey().setValue("");
		mainView.activationKey().setValue("");
	}

	@Expose   
	public void startReview()
	{
		Navigate.to(Navigate.PUBLISH_SERVICE);
	}  

	@BindView("main")
	public static interface MainView extends WidgetAccessor
	{
		HTMLPanel buttonPanel();
		Panel panel();
		FormDisplay form();
		TextBox nameTextBox();
		TextBox createdBy();
		TextBox createdDate();
		TextBox lastModifiedBy();
		TextBox lastModifiedDate();
		TextBox id();
		TextBox login();
		TextBox firstName();
		TextBox lastName();
		TextBox email();
		TextBox activated();
		TextBox langKey();
		TextBox activationKey();
	}

}