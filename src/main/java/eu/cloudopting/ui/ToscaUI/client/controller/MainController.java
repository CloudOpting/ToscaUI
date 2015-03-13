package eu.cloudopting.ui.ToscaUI.client.controller;

import java.util.Date;
import java.util.Map;

import org.cruxframework.crux.core.client.controller.Controller;
import org.cruxframework.crux.core.client.controller.Expose;
import org.cruxframework.crux.core.client.ioc.Inject;
import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.screen.Screen;
import org.cruxframework.crux.core.client.screen.views.BindView;
import org.cruxframework.crux.core.client.screen.views.WidgetAccessor;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox;
import org.cruxframework.crux.widgets.client.dialog.FlatMessageBox.MessageType;
import org.cruxframework.crux.widgets.client.formdisplay.FormDisplay;
import org.cruxframework.crux.widgets.client.swapcontainer.HorizontalSwapContainer;

import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

import eu.cloudopting.ui.ToscaUI.client.remote.IApi;
import eu.cloudopting.ui.ToscaUI.client.remote.IProxyAPIService;
import eu.cloudopting.ui.ToscaUI.client.remote.IUserInfo;

@Controller("mainController")
public class MainController 
{

	@Inject
	public MainView mainView;

	@Inject
	public IApi api;

	@Inject
	public IProxyAPIService connectApi;

	@Inject
	public IUserInfo userInfo;

	@Expose
	public void onLoad() {}

	@Expose   
	public void getInfoUser()
	{
		
		final Callback<Map<String,String>> callbackUserInfo = new Callback<Map<String,String>>() {
			@Override
			public void onSuccess(Map<String,String> result) {
				mainView.createdBy().setValue(result.get("createdBy"));
				mainView.createdDate().setValue(new Date(Long.parseLong(result.get("createdDate"))).toString());
				mainView.lastModifiedBy().setValue(result.get("lastModifiedBy"));
				mainView.lastModifiedDate().setValue(new Date(Long.parseLong(result.get("lastModifiedDate"))).toString());
				mainView.id().setValue(result.get("id"));
				mainView.login().setValue(result.get("login"));
				mainView.firstName().setValue(result.get("firstName"));
				mainView.lastName().setValue(result.get("lastName"));
				mainView.email().setValue(result.get("email"));
				mainView.activated().setValue(result.get("activated"));
				mainView.langKey().setValue(result.get("langKey"));
				mainView.activationKey().setValue(result.get("activationKey"));
			}
			@Override
			public void onError(Exception e) {}
		};

		final Callback<String> callback = new Callback<String>() {
			@Override
			public void onSuccess(String json) {
				userInfo.getLastModifiedBy(json, callbackUserInfo);
			}
			@Override
			public void onError(Exception e) {
				FlatMessageBox.show("The user [" + mainView.nameTextBox().getValue() + "] does not exist!!", MessageType.INFO);
			}
		};

		connectApi.users(mainView.nameTextBox().getValue(), callback);

	}  
	@Expose   
	public void createField()
	{
		FormDisplay fd = new FormDisplay();
		
		TextBox tb = new TextBox();
		tb.setValue("TESTING");
		
		fd.addEntry("TESTING1", tb, HorizontalAlignmentConstant.startOf(Direction.DEFAULT));
		fd.addEntry("TESTING2", tb, HorizontalAlignmentConstant.startOf(Direction.DEFAULT));
		fd.addEntry("TESTING3", tb, HorizontalAlignmentConstant.startOf(Direction.DEFAULT));
		fd.addEntry("TESTING4", tb, HorizontalAlignmentConstant.startOf(Direction.DEFAULT));
		fd.addEntry("TESTING5", tb, HorizontalAlignmentConstant.startOf(Direction.DEFAULT));
		mainView.gwtFormPanel().add(fd);
		
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
	//	@Expose   
	//	public void login()
	//	{
	//		api.login("admin", new Callback<String>() {
	//
	//			@Override
	//			public void onSuccess(String result) {
	//				FlatMessageBox.show("login SUCCESS: " + result, MessageType.INFO);
	//			}
	//
	//			@Override
	//			public void onError(Exception e) {
	//				FlatMessageBox.show("login ERROR: " + e.getMessage(), MessageType.INFO);
	//			}
	//		});
	//	}  
	//
	//	@Expose   
	//	public void getAccount()
	//	{
	//		//TODO: Change nulls
	//		api.getAccount(null, null, new Callback<String>() {
	//
	//			@Override
	//			public void onSuccess(String result) {
	//				FlatMessageBox.show("getAccount SUCCESS: " + result, MessageType.INFO);
	//			}
	//
	//			@Override
	//			public void onError(Exception e) {
	//				FlatMessageBox.show("getAccount ERROR: " + e.getMessage(), MessageType.INFO);
	//			}
	//		});
	//	}  


	@Expose   
	public void changeView()
	{

		HorizontalSwapContainer views =  (HorizontalSwapContainer) Screen.get("views");
		//HorizontalSwapContainer
		views.showView("toscalist");

	}  

	@BindView("main")
	public static interface MainView extends WidgetAccessor
	{
		Panel panel();
		FormPanel gwtFormPanel(); 
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