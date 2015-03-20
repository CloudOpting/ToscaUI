package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.NodeTemplateType;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.NodeTypeName;
import eu.cloudopting.ui.ToscaUI.client.remote.impl.ToscaManager.OperationName;
import eu.cloudopting.ui.ToscaUI.server.model.SLA;
import eu.cloudopting.ui.ToscaUI.server.utils.IOUtils;

/**
 * 
 * @author xeviscc
 *
 */
public class ToscaManagerTest {
	
	private static ToscaManager toscaManager = new ToscaManager();
	private static String toscaFilePath = "src/test/resources/TOSCA_ClearoExample.xml";
	

	private static String definitionId = "Clearo";
	private static String serviceTemplate = "Clearo";
	private static NodeTypeName nodeTypeName = NodeTypeName.VMhost;
	private static String interfaceName = "http://tempuri.org";
	private static OperationName operationName = OperationName.Install;
	private static String inputParameterType = "co:SLA";
	private static String slaID = "BigCity";
	
	@Before
	public void init() throws IOException, JSONException {

		String jsonTemplate = "{ \"tosca\" : \"" + StringEscapeUtils.escapeJavaScript(
				IOUtils.readFile(toscaFilePath, Charset.defaultCharset())
				) + "\"}";
		
		//GET AN OBJECT JSON THROUGHT THE JSON STRING
		JSONObject jsonObject = new JSONObject(jsonTemplate);
		
		//UNESCAPE IT FROM JAVASCRIPT
		String toscaFileFromJson = StringEscapeUtils.unescapeJavaScript(jsonObject.getString("tosca"));
		toscaManager.setTosca(toscaFileFromJson);
	}
	
	@After
	public void finish() {
		System.out.println("------");
//		System.out.println("Template: \n" + toscaManager.getTosca(false) );
//		System.out.println("Customized: \n" +  toscaManager.getTosca(false) );
	}

	public static void main(String[] args) throws Exception
	{
//		init();
		toscaManager.getOperationDocumentation(definitionId, NodeTypeName.VMhost, interfaceName, OperationName.Install);
//				toscaUtil.getOperationDocumentation(definition, NodeType.Apache, interfaceName, Operation.Install);
//				toscaUtil.getOperationDocumentation(definition, NodeType.DockerContainer, interfaceName, Operation.Install);
//				toscaUtil.getOperationDocumentation(definition, NodeType.ApacheVirtualHost, interfaceName, Operation.Install);

//				toscaUtil.getHostName(definition, serviceTemplate, NodeTemplate.VMhost);
//				toscaUtil.getHostName(definition, serviceTemplate, NodeTemplate.Apache);
//				toscaUtil.getHostName(definition, serviceTemplate, NodeTemplate.DockerContainer);
		toscaManager.getVHostName(definitionId, serviceTemplate, NodeTemplateType.ApacheVirtualHost, "ClearoApacheVH");
		toscaManager.setVHostName("HOSTNAME_TEST", definitionId, serviceTemplate, NodeTemplateType.ApacheVirtualHost, "ClearoApacheVH");
		toscaManager.getVHostName(definitionId, serviceTemplate, NodeTemplateType.ApacheVirtualHost, "ClearoApacheVH");
//		toscaManager.getInputParametersType(definitionId, NodeTypeName.VMhost, OperationName.Install);
		toscaManager.getSlaAvaliable(definitionId, serviceTemplate, NodeTemplateType.VMhost);
		
		System.out.println("-------------");

		toscaManager.getInputParameter(definitionId, NodeTypeName.VMhost, interfaceName, OperationName.Install, "co:SLA");
		toscaManager.setInputParameter(definitionId, NodeTypeName.VMhost, interfaceName, OperationName.Install, "co:SLA", "HOLA");
		toscaManager.getInputParameter(definitionId, NodeTypeName.VMhost, interfaceName, OperationName.Install, "co:SLA");
		

		SLA sla = toscaManager.getSLA(definitionId, serviceTemplate, NodeTemplateType.VMhost, slaID);
//				toscaUtil.getSLA(definition, serviceTemplate, NodeTemplate.Apache, slaID);
//				toscaUtil.getSLA(definition, serviceTemplate, NodeTemplate.ApacheVirtualHost, slaID);
//				toscaUtil.getSLA(definition, serviceTemplate, NodeTemplate.DockerContainer, slaID);
		System.out.println(sla);
		
		////
		
		
		
	}
	
	
	
	@Test
	public void testListInputParametersTypes() throws XPathExpressionException {
		List<String> l = toscaManager.listInputParametersTypes(definitionId, nodeTypeName, interfaceName, operationName);
		assertNotNull(l);
		System.out.println("List: "+ l);
	}

	@Test
	public void testGetInputParametersType() throws XPathExpressionException, IOException {
		String value = "TESTING_STRING";
		toscaManager.setInputParametersType(definitionId, nodeTypeName, interfaceName, operationName, inputParameterType, value);
		String s = toscaManager.getInputParametersType(definitionId, nodeTypeName, interfaceName, operationName, inputParameterType);
		assertEquals(value, s);
		System.out.println("String: " + s);
	}
	
	@Test
	public void testSetInputParametersType() throws XPathExpressionException, IOException {
		String value = "TESTING_STRING";
		String s = toscaManager.setInputParametersType(definitionId, nodeTypeName, interfaceName, operationName, inputParameterType, value);
		assertEquals(value, s);
		System.out.println("String: " + s);
	}	
	
	@Test
	public void testListInputParametersNames() throws XPathExpressionException {
		List<String> l = toscaManager.listInputParametersNames(definitionId, nodeTypeName, interfaceName, operationName);
		assertNotNull(l);
		System.out.println("List: "+ l);
	}
	
	@Test
	public void testGetInputParameterName() throws XPathExpressionException {
		String s = toscaManager.getInputParameterName(definitionId, nodeTypeName, interfaceName, operationName, inputParameterType);
		System.out.println("String: " + s);
	}
		
	@Test
	public void testGetPropertiesDefinitionElement() throws XPathExpressionException {
		String s = toscaManager.getPropertiesDefinitionElement(definitionId, nodeTypeName);
		System.out.println("String: " + s);
	}

	@Test
	public void testGetPropertiesDefinitionType() throws XPathExpressionException {
		String s = toscaManager.getPropertiesDefinitionType(definitionId, nodeTypeName);
		System.out.println("String: " + s);
	}

	@Test
	public void testListNodetypeNames() throws XPathExpressionException {
		List<String> l = toscaManager.listNodetypeNames(definitionId);
		System.out.println("List: "+ l);
	}

	@Test
	public void testGetNodetypeName() throws XPathExpressionException {
		String s = toscaManager.getNodetypeName(definitionId, nodeTypeName);
		System.out.println("String: " + s);
	}

	@Test
	public void testGetNodetypeDocumentation() throws XPathExpressionException {
		String s = toscaManager.getNodetypeDocumentation(definitionId, nodeTypeName);
		assertNotNull(s);
		System.out.println("String: " + s);
	}

	@Test
	public void testGetOperationDocumentation() throws XPathExpressionException {
		String s = toscaManager.getOperationDocumentation(definitionId, nodeTypeName, interfaceName, operationName);
		assertNotNull(s);
		System.out.println("String: " + s);
	}

	@Test
	public void testListInputParameterType() throws XPathExpressionException {
		List<String> l = toscaManager.listInputParameterType(definitionId, nodeTypeName, interfaceName, operationName);
		System.out.println("List: "+ l);
	}

	@Test
	public void testListInputParameterElement() throws XPathExpressionException {
		List<String> l = toscaManager.listInputParameterElement(definitionId, nodeTypeName, interfaceName, operationName);
		System.out.println("List: "+ l);
	}
	
	@Test
	public void testGetInputParameter() throws XPathExpressionException, IOException {
		String value = "TESTING_STRING";
		toscaManager.setInputParameter(definitionId, nodeTypeName, interfaceName, operationName, inputParameterType, value);
		String s = toscaManager.getInputParameter(definitionId, nodeTypeName, interfaceName, operationName, inputParameterType);
		assertEquals(value, s);
		System.out.println("String: " + s);
	}
	
	@Test
	public void testSetInputParameter() throws XPathExpressionException, IOException {
		String value = "TESTING_STRING";
		String s = toscaManager.setInputParameter(definitionId, nodeTypeName, interfaceName, operationName, inputParameterType, value);
		assertEquals(value, s);
		System.out.println("String: " + s);
	}
	
}