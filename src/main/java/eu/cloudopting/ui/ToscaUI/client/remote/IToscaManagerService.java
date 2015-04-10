package eu.cloudopting.ui.ToscaUI.client.remote;

import org.cruxframework.crux.core.client.rest.Callback;
import org.cruxframework.crux.core.client.rest.RestProxy;
import org.cruxframework.crux.core.client.rest.RestProxy.TargetRestService;

import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManagerService.NodeTypeName;
import eu.cloudopting.ui.ToscaUI.server.model.SLA;
import eu.cloudopting.ui.ToscaUI.server.model.SubscribeServicesView;

/**
 * 
 * @author xeviscc
 *
 */
@TargetRestService("toscaManagerService")
public interface IToscaManagerService extends RestProxy {
	
	void setTosca(String toscaXml, Callback<String> callback);
	
	void getTosca(Boolean original, Callback<String> callback);
	
	void getSubscribeServiceLists(String nameId, Callback<SubscribeServicesView> callback);
	
//	void getVHostName(String definitionId, String serviceTemplate,
//			NodeTemplateType nodeTemplateType, String nodeTemplateId, 
//			Callback<String> callback);
//
//	void setVHostName(String vHostName, String definitionId,
//			String serviceTemplate, NodeTemplateType nodeTemplateType, String nodeTemplateId, 
//			Callback<String> callback);
//	
//	void getSLA(String definitionId, String serviceTemplate,
//			NodeTemplateType nodeTemplateType, String slaID, 
//			Callback<SLA> callback);
	
//	void listSlaAvaliable(String definitionId, String serviceTemplate,
//			NodeTemplateType nodeTemplateType, 
//			Callback<List<String>> callback);
	
	
	void getChosenSLA(String definitionId, String serviceTemplate, String nodeTypeName,  
			Callback<SLA> callback); 
	
	void setSLA(String nameId, String nodeTypeName, SLA sla, Callback<String> callback);
	
/*
	void getDocumentationFromOperation(String definition, NodeTypeName nodeType,
			String interfaceName, OperationName operation, Callback<String> callback);

	void getInputParametersNeeded(String definition, NodeTypeName nodeType,
			OperationName operation, Callback<String> callback);

	void getInputParameter(String definition, NodeTypeName nodeType,
			OperationName operation, String inputParameterId, Callback<String> callback);

	void setInputParameter(String definition, NodeTypeName nodeType,
			OperationName operation, String inputParameterId, String value, Callback<String> callback);
*/
}