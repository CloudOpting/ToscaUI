package eu.cloudopting.ui.ToscaUI.client.remote.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.xml.dtm.ref.DTMNodeList;
import org.cruxframework.crux.core.server.rest.annotation.RestService;
import org.cruxframework.crux.core.shared.rest.annotation.DefaultValue;
import org.cruxframework.crux.core.shared.rest.annotation.FormParam;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.POST;
import org.cruxframework.crux.core.shared.rest.annotation.Path;
import org.cruxframework.crux.core.shared.rest.annotation.QueryParam;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import eu.cloudopting.ui.ToscaUI.server.model.SLA;
import eu.cloudopting.ui.ToscaUI.server.model.SubscribeServicesView;
import eu.cloudopting.ui.ToscaUI.server.utils.LogsUtil;
import eu.cloudopting.ui.ToscaUI.server.utils.UniversalNamespaceResolver;

/**
 * 
 * @author xeviscc
 *
 */
@RestService("toscaManagerService")
@Path("toscaManagerService")
public class ToscaManagerService {

	//TODO: Edit temp variables
	private String interfaceName = "http://tempuri.org";
	
	private static Document documentOriginal;
	private static Document document;
	private static XPath xpath;

	/**
	 * [VMhost, DockerContainer, Apache, ApacheVirtualHost, MySQL, MySQLDatabase]
	 */
	public enum NodeTypeName {
		VMhost, DockerContainer, Apache, ApacheVirtualHost, MySQL, MySQLDatabase
	}
	/**
	 * [VMhost, DockerContainer, Apache, ApacheVirtualHost, MySQL, MySQLDatabase]
	 */
	public enum NodeTemplateType {
		VMhost, DockerContainer, Apache, ApacheVirtualHost, MySQL, MySQLDatabase
	}
	/**
	 * [Install, Deploy]
	 */
	public enum OperationName {
		Install,
		Deploy
	}

	//** PREPARED QUERIES **//

	/*
	 * NodeType TAG access queries
	 */
	
	/**
	 * LIST_INPUT_PARAMETERS_TYPES prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 * @param String interfaceName
	 * @param {@link OperationName} operationName
	 */
	private static String LIST_INPUT_PARAMETERS_TYPES = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface[@name=\"%s\"]"
			+ "/Operation[@name=\"%s\"]"
			+ "/InputParameters"
			+ "/InputParameter"
			+ "/@type";
	
	/**
	 * GET_INPUT_PARAMETERS_TYPE prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 * @param String interfaceName
	 * @param {@link OperationName} operationName
	 * @param String inputParameterType
	 */
	private static String GET_INPUT_PARAMETERS_BYTYPE = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface[@name=\"%s\"]"
			+ "/Operation[@name=\"%s\"]"
			+ "/InputParameters"
			+ "/InputParameter[@type=\"%s\"]";
	
	/**
	 * LIST_INPUT_PARAMETERS_NAMES prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 * @param String interfaceName
	 * @param {@link OperationName} operationName
	 */
	private static String LIST_INPUT_PARAMETERS_NAMES = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface[@name=\"%s\"]"
			+ "/Operation[@name=\"%s\"]"
			+ "/InputParameters"
			+ "/InputParameter"
			+ "/@name";

	/**
	 * GET_INPUT_PARAMETERS_NAME prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 * @param String interfaceName
	 * @param {@link OperationName} operationName
	 * @param String inputParameterName
	 */
	private static String GET_INPUT_PARAMETERS_BYNAME = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface[@name=\"%s\"]"
			+ "/Operation[@name=\"%s\"]"
			+ "/InputParameters"
			+ "/InputParameter[@name=\"%s\"]";
	
	/**
	 * GET_PROPERTIES_DEFINITION_ELEMENT prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 */
	private static String GET_PROPERTIES_DEFINITION_ELEMENT = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/PropertiesDefinition"
			+ "/@element";
	//@element extracted is referenced in /Definitions/ServiceTemplate/TopologyTemplate/NodeTemplate/Properties/<element value>
	
	/**
	 * GET_PROPERTIES_DEFINITION_TYPE prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 */
	private static String GET_PROPERTIES_DEFINITION_TYPE = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/PropertiesDefinition"
			+ "/@type";
	//TODO: This type does not has any reference on the TOSCA xml file. 

	/**
	 * LIST_NODETYPE_NAMES prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 */
	private static String LIST_NODETYPE_NAMES = "/Definitions[@id=\"%s\"]"
			+ "/NodeType"
			+ "/@name";
	
	/**
	 * GET_NODETYPE_NAME prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 */
	private static String GET_NODETYPE_BYNAME = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]";
	//TODO: Is this XPath needed? 
	
	/**
	 * Gets the documentation of an NodeType.
	 * 
	 * GET_NODETYPE_DOCUMENTATION prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 */
	private static String GET_NODETYPE_DOCUMENTATION = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/documentation";
	
	/**
	 * Gets the documentation of an Operation.
	 * 
	 * GET_OPERATION_DOCUMENTATION prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 * @param String interfaceName
	 * @param {@link OperationName} operationName
	 */
	private static String GET_OPERATION_DOCUMENTATION = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface[@name=\"%s\"]"
			+ "/Operation[@name=\"%s\"]"
			+ "/documentation";
	
	/**
	 * Gets a list of parameters types.
	 * 
	 * LIST_INPUT_PARAMETER_TYPE prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 * @param String interfaceName
	 * @param {@link OperationName} operationName
	 */
	private static String LIST_INPUT_PARAMETER_TYPE = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface[@name=\"%s\"]"
			+ "/Operation[@name=\"%s\"]"
			+ "/InputParameters"
			+ "/InputParameter"
			+ "/@type";
	
	/**
	 * Gets a list of parameters element.
	 * 
	 * LIST_INPUT_PARAMETER_ELEMENT prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 * @param String interfaceName
	 * @param {@link OperationName} operationName
	 */
	private static String LIST_INPUT_PARAMETER_ELEMENT = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface[@name=\"%s\"]"
			+ "/Operation[@name=\"%s\"]"
			+ "/InputParameters"
			+ "/InputParameter"
			+ "/@element";
	
	/**
	 * Gets the Parameter for a specific id.
	 * 
	 * GET_INPUT_PARAMETER prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param {@link NodeTypeName} nodeTypeName
	 * @param String interfaceName
	 * @param {@link OperationName} operationName
	 * @param String inputParameterId
	 */
	private static String GET_INPUT_PARAMETER_BYTYPE = "/Definitions[@id=\"%s\"]"
			+ "/NodeType[@name=\"%s\"]"
			+ "/Interfaces"
			+ "/Interface[@name=\"%s\"]"
			+ "/Operation[@name=\"%s\"]"
			+ "/InputParameters"
			+ "/InputParameter[@type=\"%s\"]";
	
	/*
	 * NodeTypeImplementation TAG access queries
	 */
	
	/**
	 * LIST_NODETYPE_IMPLEMENTATION_NAME prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 */
	private static String LIST_NODETYPE_IMPLEMENTATION_NAME = "/Definitions[@id=\"%s\"]"
			+ "/NodeTypeImplementation"
			+ "/@name";
	
	/**
	 * GET_NODETYPE_IMPLEMENTATION_NAME prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String name
	 */
	private static String GET_NODETYPE_IMPLEMENTATION_NAME = "/Definitions[@id=\"%s\"]"
			+ "/NodeTypeImplementation[@name=\"%s\"]";
	
	/**
	 * LIST_NODETYPE_IMPLEMENTATION_NODETYPE prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 */
	private static String LIST_NODETYPE_IMPLEMENTATION_NODETYPE = "/Definitions[@id=\"%s\"]"
			+ "/NodeTypeImplementation"
			+ "/@nodeType";
	
	/**
	 * GET_NODETYPE_IMPLEMENTATION_NODETYPE prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String nodeType
	 */
	private static String GET_NODETYPE_IMPLEMENTATION_BYNODETYPE = "/Definitions[@id=\"%s\"]"
			+ "/NodeTypeImplementation[@nodeType=\"%s\"]";
	//TODO: Check if its a nodeTypeName.
	
	/**
	 * LIST_NODETYPE_IMPLEMENTATION_ARTIFACT_TYPES prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String nodeType
	 */
	private static String LIST_NODETYPE_IMPLEMENTATION_ARTIFACT_TYPES = "/Definitions[@id=\"%s\"]"
			+ "/NodeTypeImplementation[@nodeType=\"%s\"]"
			+ "/ImplementationArtifacts"
			+ "/ImplementationArtifact"
			+ "/@artifactType";
	
	/**
	 * GET_NODETYPE_IMPLEMENTATION_ARTIFACT_TYPE prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String nodeType
	 * @param String artifactType
	 */
	private static String GET_NODETYPE_IMPLEMENTATION_ARTIFACT_BYTYPE = "/Definitions[@id=\"%s\"]"
			+ "/NodeTypeImplementation[@nodeType=\"%s\"]"
			+ "/ImplementationArtifacts"
			+ "/ImplementationArtifact[@artifactType=\"%s\"]";
	
	/**
	 * LIST_NODETYPE_IMPLEMENTATION_ARTIFACT_REFS prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String nodeType
	 */
	private static String LIST_NODETYPE_IMPLEMENTATION_ARTIFACT_REFS = "/Definitions[@id=\"%s\"]"
			+ "/NodeTypeImplementation[@nodeType=\"%s\"]"
			+ "/ImplementationArtifacts"
			+ "/ImplementationArtifact"
			+ "/@artifactRef";
	
	/**
	 * GET_NODETYPE_IMPLEMENTATION_ARTIFACT_REF prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String nodeType
	 * @param String artifactRef
	 */
	private static String GET_NODETYPE_IMPLEMENTATION_ARTIFACT_BYREF = "/Definitions[@id=\"%s\"]"
			+ "/NodeTypeImplementation[@nodeType=\"%s\"]"
			+ "/ImplementationArtifacts"
			+ "/ImplementationArtifact[@artifactRef=\"%s\"]";

	/*
	 * ArtifactType TAG access queries
	 */
	
	/**
	 * GET_ARTIFACT_TYPE prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String name
	 */
	private static String GET_ARTIFACT_TYPE_BYNAME = "/Definitions[@id=\"%s\"]"
			+ "/ArtifactType[@name=\"%s\"]";
	
	/*
	 * ArtifactTemplate TAG access queries
	 */
	
	/**
	 * GET_ARTIFACT_TEMPLATE_BYID prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String id
	 */
	private static String GET_ARTIFACT_TEMPLATE_BYID = "/Definitions[@id=\"%s\"]"
			+ "/ArtifactTemplate[@id=\"%s\"]";
	
	/**
	 * GET_ARTIFACT_TEMPLATE_BYTYPE prepared query needs some parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String type
	 */
	private static String GET_ARTIFACT_TEMPLATE_BYTYPE = "/Definitions[@id=\"%s\"]"
			+ "/ArtifactTemplate[@type=\"%s\"]";
	
	
	/* Queries needed
		TODO
	*/
	/*
	 * ServiceTemplate TAG access queries
	 */

	/**
	 * GET_VHOSTNAME prepared query needs 4 parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String serviceTemplateId
	 * @param {@link NodeTemplateType} nodeTemplateType 
	 * @param String nodeTemplateId
	 */
	private static String GET_VHOSTNAME = "/Definitions[@id=\"%s\"]"
			+ "/ServiceTemplate[@id=\"%s\"]"
			+ "/TopologyTemplate"
			+ "/NodeTemplate[@type=\"%s\"][@id=\"%s\"]"
			+ "/Properties"
			+ "/ApacheVirtualHostProperties"
			+ "/VHostName";

	/**
	 * GET_SLA prepared query needs 4 parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String serviceTemplateId
	 * @param {@link NodeTemplateType} nodeTemplateType
	 * @param String SLAid
	 */
	private static String GET_SLA = "/Definitions[@id=\"%s\"]"
			+ "/ServiceTemplate[@id=\"%s\"]"
			+ "/TopologyTemplate"
			+ "/NodeTemplate[@type=\"%s\"]"
			+ "/Properties"
			+ "/VMhostProperties"
			+ "/SLAsProperties"
			+ "/SLA[@id=\"%s\"]";
	
	/**
	 * LIST_SLA_AVALIABLE prepared query needs 3 parameters to work. 
	 * 
	 * @param String definitionId 
	 * @param String serviceTemplate
	 * @param {@link NodeTemplateType} nodeTemplateType
	 */
	private static String LIST_SLA_AVALIABLE = "/Definitions[@id=\"%s\"]"
			+ "/ServiceTemplate[@id=\"%s\"]"
			+ "/TopologyTemplate"
			+ "/NodeTemplate[@type=\"%s\"]"
			+ "/Properties"
			+ "/VMhostProperties"
			+ "/SLAsProperties"
			+ "/SLA"
			+ "/@id";

	@POST
	@Path("/")
	public String setTosca(@FormParam("toscaXmlBase64Encoded") String toscaXmlBase64Encoded) {
		try {
			String toscaXml = new String(Base64.decodeBase64(toscaXmlBase64Encoded));
			//Parse the String to a Document
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = db.parse(new InputSource(new StringReader(toscaXml)));
			//Initialize the xpath
			xpath = XPathFactory.newInstance().newXPath();
			//Set the needed namespaces
			xpath.setNamespaceContext(new UniversalNamespaceResolver(document, false));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "OK";//TODO: CHeck return param
	}
	
	@GET
	@Path("/")
	public String getTosca(@QueryParam("original") @DefaultValue("false") boolean original) {
		String result = "";
		try {
			
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	 
	        StringWriter sw = new StringWriter();
	        if(original){
	        	transformer.transform(new DOMSource(documentOriginal), new StreamResult(sw));
	        } else {
	        	transformer.transform(new DOMSource(document), new StreamResult(sw));
	        }
	        
	        result = Base64.encodeBase64String(sw.toString().getBytes());
	        
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GET
	@Path("/getSubscribeServiceLists")
	public SubscribeServicesView getSubscribeServiceLists(@QueryParam("nameId") String nameId) throws XPathExpressionException {
		SubscribeServicesView ssl = new SubscribeServicesView();
		
		//GET NODE TYPES
		List<String> nodetypes = listNodetypeNames(nameId);
		for (String type : nodetypes) {
			//GET INPUT PARAMS FOR EACH NODE TYPE.
			//List<String> paramNames = listInputParametersNames(nameId, NodeTypeName.valueOf(type), interfaceName, OperationName.Install);
			//SAVE IT in pair <nodetype, paramNames> <String, List>
			
			//List SLA Avaliable
			List<String> listSLA = listSlaAvaliable(nameId, nameId, NodeTemplateType.valueOf(type));
			
			//Get each SLA and add it to the list
			for (String sla : listSLA) {
				ssl.getListSLAs().add(getSLA(nameId, nameId, NodeTemplateType.valueOf(type), sla));
			}
		}
		
//		ssl.setListCPUs(listCPUs);
//		ssl.setListCSSs(listCSSs);
//		ssl.setListItems(listItems);
//		ssl.setListOSs(listOSs);
		return ssl;
		
	}
	
	@POST
	@Path("/setSLA")
	public String setSLA(@QueryParam("nameId") String nameId, 
			@QueryParam("nodeTypeName") String nodeTypeName,
			@QueryParam("sla") SLA sla) throws XPathExpressionException, IOException {
		
		System.out.println(nameId);
		System.out.println(nodeTypeName);
		System.out.println(sla);
		
		String inputParameterType = "co:SLA";
		
		setInputParameter(nameId, NodeTypeName.valueOf(nodeTypeName), interfaceName, OperationName.Install, inputParameterType, "BigCity");
		
		return getTosca(false);
	}

	//** EXPOSED QUERIES **//

	public List<String> listInputParametersTypes(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName) throws XPathExpressionException {
		return evaluateMultipleValues(String.format(LIST_INPUT_PARAMETERS_TYPES, definitionId, nodeTypeName, interfaceName, operationName));
	}

	public String getInputParametersType(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName, String inputParameterType) throws XPathExpressionException {
		return evaluateSingleValue(String.format(GET_INPUT_PARAMETERS_BYTYPE, definitionId, nodeTypeName, interfaceName, operationName, inputParameterType));
	}
	
	public String setInputParametersType(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName, String inputParameterType, String value) throws XPathExpressionException, IOException {
		return insertSingleValue(String.format(GET_INPUT_PARAMETERS_BYTYPE, definitionId, nodeTypeName, interfaceName, operationName, inputParameterType), value);
	}

	public List<String> listInputParametersNames(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName) throws XPathExpressionException {
		return evaluateMultipleValues(String.format(LIST_INPUT_PARAMETERS_NAMES, definitionId, nodeTypeName, interfaceName, operationName));
	}

	public String getInputParameterName(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName, String inputParameterType) throws XPathExpressionException {
		return evaluateSingleValue(String.format(GET_INPUT_PARAMETERS_BYNAME, definitionId, nodeTypeName, interfaceName, operationName, inputParameterType));
	}

	public String getPropertiesDefinitionElement(String definitionId, NodeTypeName nodeTypeName) throws XPathExpressionException {
		return evaluateSingleValue(String.format(GET_PROPERTIES_DEFINITION_ELEMENT, definitionId, nodeTypeName));
	}

	public String getPropertiesDefinitionType(String definitionId, NodeTypeName nodeTypeName) throws XPathExpressionException {
		return evaluateSingleValue(String.format(GET_PROPERTIES_DEFINITION_TYPE, definitionId, nodeTypeName));
	}

	public List<String> listNodetypeNames(String definitionId) throws XPathExpressionException {
		return evaluateMultipleValues(String.format(LIST_NODETYPE_NAMES, definitionId));
	}

	public String getNodetypeName(String definitionId, NodeTypeName nodeTypeName) throws XPathExpressionException {
		return evaluateSingleValue(String.format(GET_NODETYPE_BYNAME, definitionId, nodeTypeName));
	}

	public String getNodetypeDocumentation(String definitionId, NodeTypeName nodeTypeName) throws XPathExpressionException {
		return evaluateSingleValue(String.format(GET_NODETYPE_DOCUMENTATION, definitionId, nodeTypeName));
	}

	public String getOperationDocumentation(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName) throws XPathExpressionException {
		return evaluateSingleValue(String.format(GET_OPERATION_DOCUMENTATION, definitionId, nodeTypeName, interfaceName, operationName));
	}

	public List<String> listInputParameterType(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName) throws XPathExpressionException {
		return evaluateMultipleValues(String.format(LIST_INPUT_PARAMETER_TYPE, definitionId, nodeTypeName, interfaceName, operationName));
	}

	public List<String> listInputParameterElement(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName) throws XPathExpressionException {
		return evaluateMultipleValues(String.format(LIST_INPUT_PARAMETER_ELEMENT, definitionId, nodeTypeName, interfaceName, operationName));
	}

	public String getInputParameter(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName, String inputParameterType) throws XPathExpressionException {
		return evaluateSingleValue(String.format(GET_INPUT_PARAMETER_BYTYPE, definitionId, nodeTypeName, interfaceName, operationName, inputParameterType));
	}

	public String setInputParameter(String definitionId, NodeTypeName nodeTypeName, String interfaceName, OperationName operationName, String inputParameterType, String value) throws XPathExpressionException, IOException {
		return insertSingleValue(String.format(GET_INPUT_PARAMETER_BYTYPE, definitionId, nodeTypeName, interfaceName, operationName, inputParameterType), value);
	}
	
//	@GET
//	@Path("/getDocumentationFromOperation")
//	public String getDocumentationFromOperation(
//			@QueryParam("definition") String definition, 
//			@QueryParam("nodeType") NodeTypeName nodeType, 
//			@QueryParam("interfaceName") String interfaceName, 
//			@QueryParam("operation") OperationName operation) 
//	throws XPathExpressionException
//	{
//		return evaluateSingleValue(String.format(GET_OPERATION_DOCUMENTATION, definition, nodeType, interfaceName, operation));
//	}

//	@GET
//	@Path("/getInputParametersType")
//	public String getInputParametersType(
//			@QueryParam("definitionId") String definitionId, 
//			@QueryParam("interfaceName") String interfaceName,
//			@QueryParam("nodeTypeName") NodeTypeName nodeTypeName, 
//			@QueryParam("operationName") OperationName operationName,
//			@QueryParam("inputParameterType") String inputParameterType) 
//	throws XPathExpressionException 
//	{
//		return evaluateSingleValue(String.format(GET_INPUT_PARAMETERS_BYTYPE, definitionId, nodeTypeName, interfaceName, operationName, inputParameterType));
//	}
	
	@GET
	@Path("/getVHostName")
	public String getVHostName(
			@QueryParam("definitionId") String definitionId, 
			@QueryParam("serviceTemplate") String serviceTemplate, 
			@QueryParam("nodeTemplateType") NodeTemplateType nodeTemplateType,
			@QueryParam("nodeTemplateId") String nodeTemplateId) 
	throws XPathExpressionException 
	{
		String result = evaluateSingleValue(String.format(GET_VHOSTNAME, definitionId, serviceTemplate, nodeTemplateType, nodeTemplateId));
		return result;
	}
	
	@POST
	@Path("/setVHostName")
	public String setVHostName(
			@QueryParam("vHostName") String vHostName,
			@QueryParam("definitionId") String definitionId, 
			@QueryParam("serviceTemplate") String serviceTemplate, 
			@QueryParam("nodeTemplateType") NodeTemplateType nodeTemplateType,
			@QueryParam("nodeTemplateId") String nodeTemplateId) 
	throws XPathExpressionException, IOException 
	{
		insertSingleValue(String.format(GET_VHOSTNAME, definitionId, serviceTemplate, nodeTemplateType, nodeTemplateId), vHostName);
		return "";//TODO: Check the result param
	}

	@GET
	@Path("/getSLA")
	public SLA getSLA(
			@QueryParam("definitionId") String definitionId, 
			@QueryParam("serviceTemplate") String serviceTemplate, 
			@QueryParam("nodeTemplateType") NodeTemplateType nodeTemplateType,
			@QueryParam("slaID") String slaID) 
	throws XPathExpressionException 
	{
		SLA sla = new SLA();
		String expression = String.format(GET_SLA, definitionId, serviceTemplate, nodeTemplateType, slaID);

		sla.setId(slaID);
		sla.setNumCpus(evaluateSingleValue(expression + "/NumCpus"));
		sla.setMemory(evaluateSingleValue(expression + "/Memory"));
		sla.setPrice(evaluateSingleValue(expression + "/Price"));
		sla.setDisk(evaluateSingleValue(expression + "/Disk"));
		sla.setChosen(evaluateSingleValue(expression + "/Chosen"));

		return sla;
	}

	@GET
	@Path("/listSlaAvaliable")
	public List<String> listSlaAvaliable(
			@QueryParam("definitionId") String definitionId, 
			@QueryParam("serviceTemplate") String serviceTemplate, 
			@QueryParam("nodeTemplateType") NodeTemplateType nodeTemplateType) 
	throws XPathExpressionException 
	{
		return evaluateMultipleValues(String.format(LIST_SLA_AVALIABLE, definitionId, serviceTemplate, nodeTemplateType));
	}
	
//	@GET
//	@Path("/getInputParameter")
//	public String getInputParameter(
//			@QueryParam("definition") String definition, 
//			@QueryParam("nodeType") NodeTypeName nodeType, 
//			@QueryParam("operation") OperationName operation,
//			@QueryParam("inputParameterId") String inputParameterId) 
//	throws XPathExpressionException 
//	{
//		return evaluateSingleValue(String.format(GET_INPUT_PARAMETER_BYTYPE, definition, nodeType, operation, inputParameterId));
//	}
	
//	@POST
//	@Path("/setInputParameter")
//	public void setInputParameter(
//			@QueryParam("definition") String definition, 
//			@QueryParam("nodeType") NodeTypeName nodeType, 
//			@QueryParam("operation") OperationName operation,
//			@QueryParam("inputParameterId") String inputParameterId,
//			@QueryParam("value") String value) 
//	throws XPathExpressionException, IOException 
//	{
//		insertSingleValue(String.format(GET_INPUT_PARAMETER_BYTYPE, definition, nodeType, operation, inputParameterId), value);
//	}

	
	/*
	 * TOSCA UTILS
	 */
	
	/**
	 * Evaluates the expression and inserts the value in the Node.
	 * 
	 * @param expression XPath expression where the value has to be inserted.
	 * @param value Value that has to be inserted.
	 * @throws XPathExpressionException
	 */
	private String insertSingleValue(String expression, String value) throws XPathExpressionException, IOException {
		System.out.println("Insert: " + expression);
		if(value != null) {
			Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
			if(node!=null){
				node.setTextContent(value);
			} else {
				throw new IOException("The value could not be inserted, Node not found."); 
			}
		}
		return evaluateSingleValue(expression);
	}

	/**
	 * Evaluates a XPath expression against the embedded document.
	 * @param expression XPath expression to be evaluated against the document
	 * @return String with the result of the evaluation
	 * @throws XPathExpressionException
	 */
	private String evaluateSingleValue(String expression) throws XPathExpressionException {
		String result = (String) xpath.evaluate(expression, document, XPathConstants.STRING);
//		if(result.isEmpty()) {
//			Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
//			result = nodeToString(node);
//		}
		if(LogsUtil.DEBUG_ENABLED){
			System.out.println(result.trim());
		}
		return result.trim();
	}

	/**
	 * Evaluates a XPath expression against the embedded document.
	 * @param expression XPath expression to be evaluated against the document
	 * @return String with the results of the evaluation separated by comma (,)
	 * @throws XPathExpressionException
	 */
	private List<String> evaluateMultipleValues(String expression) throws XPathExpressionException {
		DTMNodeList nodeList = (DTMNodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < nodeList.getLength(); i++) {
			result.add( nodeList.item(i).getNodeValue() );
		}
		if(LogsUtil.DEBUG_ENABLED){
			System.out.println(expression);
			System.out.println(result);
		}
		return result;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	@SuppressWarnings("unused")
	private String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try { 
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.out.println("nodeToString Transformer Exception");
		} 
		return sw.toString();
	} 

}
