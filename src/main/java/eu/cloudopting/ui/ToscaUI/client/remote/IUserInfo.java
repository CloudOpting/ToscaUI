package eu.cloudopting.ui.ToscaUI.client.remote;

import java.util.Map;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;

/**
 * 
 * @author xeviscc
 *
 */
@TargetRestService("userInfoService")
public interface IUserInfo extends RestProxy {
	
	void getLastModifiedBy(String json, Callback<Map<String,String>> callback);
	
}
