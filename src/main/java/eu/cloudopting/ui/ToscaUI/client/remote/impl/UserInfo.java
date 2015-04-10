package eu.cloudopting.ui.ToscaUI.client.remote.impl;

import java.util.HashMap;
import java.util.Map;

import org.cruxframework.crux.core.server.rest.annotation.RestService;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.Path;
import org.cruxframework.crux.core.shared.rest.annotation.QueryParam;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author xeviscc
 *
 */
@RestService("userInfoService")
@Path("userInfo")
public class UserInfo {

//	@GET
//	@Path("/")
//	public Map<String,String> getLastModifiedBy(@QueryParam("user") String json) {
//		Map<String,String> result = new HashMap<String,String>();
//		JSONObject jsonObject;
//		try {
//			jsonObject = new JSONObject(json);
//			result.put("createdBy", jsonObject.getString("createdBy"));
//			result.put("createdDate", jsonObject.getString("createdDate"));
//			result.put("lastModifiedBy", jsonObject.getString("lastModifiedBy"));
//			result.put("lastModifiedDate", jsonObject.getString("lastModifiedDate"));
//			result.put("id", jsonObject.getString("id"));
//			result.put("login", jsonObject.getString("login"));
//			result.put("firstName", jsonObject.getString("firstName"));
//			result.put("lastName", jsonObject.getString("lastName"));
//			result.put("email", jsonObject.getString("email"));
//			result.put("activated", jsonObject.getString("activated"));
//			result.put("langKey", jsonObject.getString("langKey"));
//			result.put("activationKey", jsonObject.getString("activationKey"));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

}
