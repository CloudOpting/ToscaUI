package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.NotFoundException;

import eu.cloudopting.ui.ToscaUI.client.remote.impl.ProxyAPIService;
import eu.cloudopting.ui.ToscaUI.server.utils.IOUtils;

/**
 * 
 * @author xeviscc
 *
 */
public class ProxyAPITest {
	
	/*
	 * VARIABLES
	 */
	String user = "admin";
	String password = "admin";
	String xmlFile;
	String toscaTemplate;
	int idXmlFile = RandomUtils.nextInt();
	int idFile = RandomUtils.nextInt();
	String applicationName = "Clearo";
	
	@Before
	public void initFiles() throws IOException {
		/*
		 * VARIABLES
		 */
		xmlFile = StringEscapeUtils.escapeJavaScript(
				IOUtils.readFile("src/test/resources/TOSCA_ClearoExample.xml", Charset.defaultCharset())
				);
		toscaTemplate = StringEscapeUtils.escapeJavaScript(
				IOUtils.readFile("src/test/resources/TOSCA_ClearoExample.xml", Charset.defaultCharset())
				);
	}

//	@Test
	public void testCreateApplicationWithExample() throws IOException, JSONException, NotFoundException {
		initFiles();
		
		//CREATE INSTANCE
		ProxyAPIService c = new ProxyAPIService();
		//CONNECT
		c.connect(user, password);
		//GET THE USER
		String userJson = c.users(user);

		//CREATE THE JSON TO CREATE AN APPLICAITON
		JSONObject jsonObject = new JSONObject(userJson);
		String json = "{"
				+" \"id\": "+idFile+","
				+" \"applicationName\": \""+applicationName+"\","
				+" \"applicationDescription\": \"\","
				+" \"applicationToscaTemplate\": {"+toscaTemplate+"},"
				+" \"applicationVersion\": \"\","
				+" \"customizations\": {"
				+"  \"id\": "+idXmlFile+","
				+"  \"customizationToscaFile\": {"+xmlFile+"},"
				+"  \"customizationCreation\": \"\","
				+"  \"customizationActivation\": \"\","
				+"  \"customizationDecommission\": \"\","
				+"  \"username\": \""+jsonObject.getString("login")+"\","
				+"  \"applicationId\": \"Applications\","
				+"  \"statusId\": {"
				+"   \"id\": 0,"
				+"   \"status\": \"\","
				+"   \"customizations\": \"Customizations\","
				+"   \"applications\": \"Applications\""
				+"  }"
				+" },"
				+" \"applicationMedia\": {"
				+"  \"id\": 0,"
				+"  \"mediaContent\": ["
				+"  \"\""
				+"  ],"
				+"  \"applicationId\": \"Applications\""
				+" },"
				+" \"statusId\": {"
				+"  \"id\": 0,"
				+"  \"status\": \"\","
				+"  \"customizations\": {"
				+"   \"id\": "+idXmlFile+","
				+"   \"customizationToscaFile\": {"+xmlFile+"},"
				+"   \"customizationCreation\": \"\","
				+"   \"customizationActivation\": \"\","
				+"   \"customizationDecommission\": \"\","
				+"   \"username\": \""+jsonObject.getString("login")+"\","
				+"   \"applicationId\": \"Applications\","
				+"   \"statusId\": \"Status\""
				+"  },"
				+"  \"applications\": \"Applications\""
				+" },"
				+" \"userId\": {"
				+"  \"createdBy\": \""+jsonObject.getString("createdBy")+"\","
				+"  \"createdDate\": \""+jsonObject.getString("createdDate")+"\","
				+"  \"lastModifiedBy\": \""+jsonObject.getString("lastModifiedBy")+"\","
				+"  \"lastModifiedDate\": \""+jsonObject.getString("lastModifiedDate")+"\","
				+"  \"id\": "+jsonObject.getString("id")+","
				+"  \"login\": \""+jsonObject.getString("login")+"\","
				+"  \"firstName\": \""+jsonObject.getString("firstName")+"\","
				+"  \"lastName\": \""+jsonObject.getString("lastName")+"\","
				+"  \"email\": \""+jsonObject.getString("email")+"\","
				+"  \"activated\": "+jsonObject.getString("activated")+","
				+"  \"langKey\": \""+jsonObject.getString("langKey")+"\","
				+"  \"activationKey\": \""+jsonObject.getString("activationKey")+"\""
				+" }"
				+"}";
		
		System.out.println(json);
		
		//CREATE APPLICAITON
		c.applicationCreate(json);
	}
	
	@Test
	public void testCreateNewApplication() throws MalformedURLException, IOException {
		@SuppressWarnings("unused")
		String json = 
		"{"
			+"\"statusId\": {"
			+"\"id\": 1"
			+"},"
			+"\"userId\": {"
				+"\"id\": 3"
			+"},"
			+"\"applicationName\": \"test app xml\","
			+"\"applicationDescription\": \"test description xml\","
			+"\"applicationToscaTemplate\": \"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Definitions id=\"Clearo\" name=\"\"targetNamespace=\"http://tempuri.org\"\r\n\txmlns=\"http://docs.oasis-open.org/tosca/ns/2011/12\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\r\n\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n\txmlns:co=\"http://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes\"\r\n\txsi:schemaLocation=\"http://docs.oasis-open.org/tosca/ns/2011/12 TOSCA-v1.0.xsd \r\n\thttp://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes ./Types/CloudOptingTypes.xsd\">\r\n\t<NodeType name=\"VMhost\">\r\n\t\t<documentation>This is the VM description, we need to collect the SLA\r\n\t\t\tinformation (that is the set of CPU+RAM+DISK) that the VM need to\r\n\t\t\thave for the service (this information is just a label for the end\r\n\t\t\tuser but translate to data for the system)\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\"co:VMhostProperties\"\r\n\t\t\ttype=\"co:tVMhostProperties\" />\r\n\t\t<Interfaces>\r\n\t\t\t<Interface name=\"http://tempuri.org\">\r\n\t\t\t\t<Operation name=\"Install\">\r\n\t\t\t\t\t<documentation>The parameters to ask to the end user to execute the\r\n\t\t\t\t\t\t\"install\" operation of this node\r\n\t\t\t\t\t</documentation>\r\n\t\t\t\t\t<InputParameters>\r\n\t\t\t\t\t\t<InputParameter name=\"co:SLA.co:Chosen\" type=\"co:SLA\" />\r\n\t\t\t\t\t</InputParameters>\r\n\t\t\t\t</Operation>\r\n\t\t\t</Interface>\r\n\t\t</Interfaces>\r\n\t</NodeType>\r\n\t<NodeType name=\"DockerContainer\">\r\n\t\t<documentation>This is the Docker Container (the Docker host is\r\n\t\t\talready installed in the VM image)\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\"\"type=\"\" />\r\n\t</NodeType>\r\n\t<NodeType name=\"Apache\">\r\n\t\t<documentation>This is the Apache server (we should not ask anything\r\n\t\t\tto the end user about apache, but we need to set the properties)\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\"\"type=\"\" />\r\n\t</NodeType>\r\n\t<NodeType name=\"ApacheVirtualHost\">\r\n\t\t<documentation>This is the Apache Virtual Host and here we have things\r\n\t\t\tto ask to the user\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\"\"type=\"\" />\r\n\t\t<Interfaces>\r\n\t\t\t<Interface name=\"http://tempuri.org\">\r\n\t\t\t\t<Operation name=\"Install\">\r\n\t\t\t\t\t<InputParameters>\r\n\t\t\t\t\t\t<InputParameter name=\"VHostName\" type=\"xs:string\" />\r\n\t\t\t\t\t</InputParameters>\r\n\t\t\t\t</Operation>\r\n\t\t\t</Interface>\r\n\t\t</Interfaces>\r\n\t</NodeType>\r\n\t<ServiceTemplate id=\"Clearo\" name=\"\"\r\n\t\tsubstitutableNodeType=\"QName\" targetNamespace=\"http://tempuri.org\">\r\n\r\n\t\t<TopologyTemplate>\r\n\t\t\t<documentation xml:lang=\"\"source=\"http://tempuri.org\" />\r\n\t\t\t<NodeTemplate id=\"ClearoVM\" maxInstances=\"1\"\r\n\t\t\t\tminInstances=\"1\" name=\"\"type=\"VMhost\">\r\n\t\t\t\t<documentation xml:lang=\"\"source=\"http://tempuri.org\" />\r\n\t\t\t\t<Properties>\r\n\t\t\t\t\t<documentation xml:lang=\"\"source=\"http://tempuri.org\" />\r\n\t\t\t\t\t<co:VMhostProperties>\r\n\t\t\t\t\t\t<co:SLAsProperties>\r\n\t\t\t\t\t\t\t<co:SLA Name=\"Big City\" id=\"BigCity\">\r\n\t\t\t\t\t\t\t\t<co:NumCpus>2</co:NumCpus>\r\n\t\t\t\t\t\t\t\t<co:Memory>2</co:Memory>\r\n\t\t\t\t\t\t\t\t<co:Price>10000</co:Price>\r\n\t\t\t\t\t\t\t\t<co:Disk>10</co:Disk>\r\n\t\t\t\t\t\t\t\t<co:Chosen>false</co:Chosen>\r\n\t\t\t\t\t\t\t</co:SLA>\r\n\t\t\t\t\t\t\t<co:SLA Name=\"Small City\" id=\"SmallCity\">\r\n\t\t\t\t\t\t\t\t<co:NumCpus>1</co:NumCpus>\r\n\t\t\t\t\t\t\t\t<co:Memory>1</co:Memory>\r\n\t\t\t\t\t\t\t\t<co:Price>5000</co:Price>\r\n\t\t\t\t\t\t\t\t<co:Disk>5</co:Disk>\r\n\t\t\t\t\t\t\t\t<co:Chosen>false</co:Chosen>\r\n\t\t\t\t\t\t\t</co:SLA>\r\n\t\t\t\t\t\t\t<co:SLA Name=\"Region\" id=\"Region\">\r\n\t\t\t\t\t\t\t\t<co:NumCpus>4</co:NumCpus>\r\n\t\t\t\t\t\t\t\t<co:Memory>4</co:Memory>\r\n\t\t\t\t\t\t\t\t<co:Price>20000</co:Price>\r\n\t\t\t\t\t\t\t\t<co:Disk>20</co:Disk>\r\n\t\t\t\t\t\t\t\t<co:Chosen>false</co:Chosen>\r\n\t\t\t\t\t\t\t</co:SLA>\r\n\t\t\t\t\t\t\t<co:vmImage></co:vmImage>\r\n\t\t\t\t\t\t</co:SLAsProperties>\r\n\t\t\t\t\t</co:VMhostProperties>\r\n\t\t\t\t</Properties>\r\n\t\t\t</NodeTemplate>\r\n\t\t\t<NodeTemplate type=\"DockerContainer\" id=\"ClearoApacheDC\"></NodeTemplate>\r\n\t\t\t<NodeTemplate type=\"Apache\" id=\"ClearoApache\"></NodeTemplate>\r\n\t\t\t<NodeTemplate type=\"ApacheVirtualHost\" id=\"ClearoApacheVH\">\r\n\t\t\t\t<Properties>\r\n\t\t\t\t\t<co:ApacheVirtualHostproperties>\r\n\t\t\t\t\t\t<co:VHostName><?getInput VHostName?></co:VHostName>\r\n\t\t\t\t\t</co:ApacheVirtualHostproperties>\r\n\t\t\t\t</Properties>\r\n\t\t\t</NodeTemplate>\r\n\t\t</TopologyTemplate>\r\n\t\t<Plans targetNamespace=\"http://tempuri.org\">\r\n\t\t\t<Plan id=\"idvalue5\" name=\"\"planLanguage=\"http://tempuri.org\"\r\n\t\t\t\tplanType=\"http://tempuri.org\">\r\n\t\t\t\t<documentation xml:lang=\"\"source=\"http://tempuri.org\" />\r\n\t\t\t\t<Precondition expressionLanguage=\"http://tempuri.org\" />\r\n\t\t\t\t<InputParameters>\r\n\t\t\t\t\t<InputParameter name=\"\"required=\"yes\" type=\"\"/>\r\n\t\t\t\t</InputParameters>\r\n\t\t\t\t<OutputParameters>\r\n\t\t\t\t\t<OutputParametername=\"\" required=\"yes\" type=\"\"/>\r\n\t\t\t\t</OutputParameters>\r\n\t\t\t\t<PlanModel>\r\n\t\t\t\t\t<documentationxml: lang=\"\" source=\"http://tempuri.org\" />\r\n\t\t\t\t</PlanModel>\r\n\t\t\t</Plan>\r\n\t\t</Plans>\r\n\t</ServiceTemplate>\r\n\t<NodeTypeImplementation nodeType=\"QName\" name=\"NCName\"></NodeTypeImplementation>\r\n</Definitions>\","
			+"\"applicationVersion\": \"1\""
		+"}";
		
	/*	@SuppressWarnings("unused")
	    String createNewApplicationJson = "{\n" +
	            "\t\"statusId\": {\n" +
	            "\t\t\"id\": 1\n" +
	            "\t},\n" +
	            "\t\"userId\": {\n" +
	            "\t\t\"id\": 3\n" +
	            "\t},\n" +
	            "\t\"applicationName\": \"test app xml\",\n" +
	            "\t\"applicationDescription\": \"test description xml\",\n" +
	            "\t\"applicationToscaTemplate\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\r\\n<Definitions id=\\\"Clearo\\\" name=\\\"\\\"targetNamespace=\\\"http://tempuri.org\\\"\\r\\n\\txmlns=\\\"http://docs.oasis-open.org/tosca/ns/2011/12\\\" xmlns:xml=\\\"http://www.w3.org/XML/1998/namespace\\\"\\r\\n\\txmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"\\r\\n\\txmlns:co=\\\"http://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes\\\"\\r\\n\\txsi:schemaLocation=\\\"http://docs.oasis-open.org/tosca/ns/2011/12 TOSCA-v1.0.xsd \\r\\n\\thttp://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes ./Types/CloudOptingTypes.xsd\\\">\\r\\n\\t<NodeType name=\\\"VMhost\\\">\\r\\n\\t\\t<documentation>This is the VM description, we need to collect the SLA\\r\\n\\t\\t\\tinformation (that is the set of CPU+RAM+DISK) that the VM need to\\r\\n\\t\\t\\thave for the service (this information is just a label for the end\\r\\n\\t\\t\\tuser but translate to data for the system)\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"co:VMhostProperties\\\"\\r\\n\\t\\t\\ttype=\\\"co:tVMhostProperties\\\" />\\r\\n\\t\\t<Interfaces>\\r\\n\\t\\t\\t<Interface name=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t\\t<Operation name=\\\"Install\\\">\\r\\n\\t\\t\\t\\t\\t<documentation>The parameters to ask to the end user to execute the\\r\\n\\t\\t\\t\\t\\t\\t\\\"install\\\" operation of this node\\r\\n\\t\\t\\t\\t\\t</documentation>\\r\\n\\t\\t\\t\\t\\t<InputParameters>\\r\\n\\t\\t\\t\\t\\t\\t<InputParameter name=\\\"co:SLA.co:Chosen\\\" type=\\\"co:SLA\\\" />\\r\\n\\t\\t\\t\\t\\t</InputParameters>\\r\\n\\t\\t\\t\\t</Operation>\\r\\n\\t\\t\\t</Interface>\\r\\n\\t\\t</Interfaces>\\r\\n\\t</NodeType>\\r\\n\\t<NodeType name=\\\"DockerContainer\\\">\\r\\n\\t\\t<documentation>This is the Docker Container (the Docker host is\\r\\n\\t\\t\\talready installed in the VM image)\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\\r\\n\\t</NodeType>\\r\\n\\t<NodeType name=\\\"Apache\\\">\\r\\n\\t\\t<documentation>This is the Apache server (we should not ask anything\\r\\n\\t\\t\\tto the end user about apache, but we need to set the properties)\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\\r\\n\\t</NodeType>\\r\\n\\t<NodeType name=\\\"ApacheVirtualHost\\\">\\r\\n\\t\\t<documentation>This is the Apache Virtual Host and here we have things\\r\\n\\t\\t\\tto ask to the user\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\\r\\n\\t\\t<Interfaces>\\r\\n\\t\\t\\t<Interface name=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t\\t<Operation name=\\\"Install\\\">\\r\\n\\t\\t\\t\\t\\t<InputParameters>\\r\\n\\t\\t\\t\\t\\t\\t<InputParameter name=\\\"VHostName\\\" type=\\\"xs:string\\\" />\\r\\n\\t\\t\\t\\t\\t</InputParameters>\\r\\n\\t\\t\\t\\t</Operation>\\r\\n\\t\\t\\t</Interface>\\r\\n\\t\\t</Interfaces>\\r\\n\\t</NodeType>\\r\\n\\t<ServiceTemplate id=\\\"Clearo\\\" name=\\\"\\\"\\r\\n\\t\\tsubstitutableNodeType=\\\"QName\\\" targetNamespace=\\\"http://tempuri.org\\\">\\r\\n\\r\\n\\t\\t<TopologyTemplate>\\r\\n\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t<NodeTemplate id=\\\"ClearoVM\\\" maxInstances=\\\"1\\\"\\r\\n\\t\\t\\t\\tminInstances=\\\"1\\\" name=\\\"\\\"type=\\\"VMhost\\\">\\r\\n\\t\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t<Properties>\\r\\n\\t\\t\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t\\t<co:VMhostProperties>\\r\\n\\t\\t\\t\\t\\t\\t<co:SLAsProperties>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:SLA Name=\\\"Big City\\\" id=\\\"BigCity\\\">\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:NumCpus>2</co:NumCpus>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Memory>2</co:Memory>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Price>10000</co:Price>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Disk>10</co:Disk>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Chosen>false</co:Chosen>\\r\\n\\t\\t\\t\\t\\t\\t\\t</co:SLA>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:SLA Name=\\\"Small City\\\" id=\\\"SmallCity\\\">\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:NumCpus>1</co:NumCpus>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Memory>1</co:Memory>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Price>5000</co:Price>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Disk>5</co:Disk>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Chosen>false</co:Chosen>\\r\\n\\t\\t\\t\\t\\t\\t\\t</co:SLA>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:SLA Name=\\\"Region\\\" id=\\\"Region\\\">\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:NumCpus>4</co:NumCpus>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Memory>4</co:Memory>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Price>20000</co:Price>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Disk>20</co:Disk>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Chosen>false</co:Chosen>\\r\\n\\t\\t\\t\\t\\t\\t\\t</co:SLA>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:vmImage></co:vmImage>\\r\\n\\t\\t\\t\\t\\t\\t</co:SLAsProperties>\\r\\n\\t\\t\\t\\t\\t</co:VMhostProperties>\\r\\n\\t\\t\\t\\t</Properties>\\r\\n\\t\\t\\t</NodeTemplate>\\r\\n\\t\\t\\t<NodeTemplate type=\\\"DockerContainer\\\" id=\\\"ClearoApacheDC\\\"></NodeTemplate>\\r\\n\\t\\t\\t<NodeTemplate type=\\\"Apache\\\" id=\\\"ClearoApache\\\"></NodeTemplate>\\r\\n\\t\\t\\t<NodeTemplate type=\\\"ApacheVirtualHost\\\" id=\\\"ClearoApacheVH\\\">\\r\\n\\t\\t\\t\\t<Properties>\\r\\n\\t\\t\\t\\t\\t<co:ApacheVirtualHostproperties>\\r\\n\\t\\t\\t\\t\\t\\t<co:VHostName><?getInput VHostName?></co:VHostName>\\r\\n\\t\\t\\t\\t\\t</co:ApacheVirtualHostproperties>\\r\\n\\t\\t\\t\\t</Properties>\\r\\n\\t\\t\\t</NodeTemplate>\\r\\n\\t\\t</TopologyTemplate>\\r\\n\\t\\t<Plans targetNamespace=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t<Plan id=\\\"idvalue5\\\" name=\\\"\\\"planLanguage=\\\"http://tempuri.org\\\"\\r\\n\\t\\t\\t\\tplanType=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t<Precondition expressionLanguage=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t<InputParameters>\\r\\n\\t\\t\\t\\t\\t<InputParameter name=\\\"\\\"required=\\\"yes\\\" type=\\\"\\\"/>\\r\\n\\t\\t\\t\\t</InputParameters>\\r\\n\\t\\t\\t\\t<OutputParameters>\\r\\n\\t\\t\\t\\t\\t<OutputParametername=\\\"\\\" required=\\\"yes\\\" type=\\\"\\\"/>\\r\\n\\t\\t\\t\\t</OutputParameters>\\r\\n\\t\\t\\t\\t<PlanModel>\\r\\n\\t\\t\\t\\t\\t<documentationxml: lang=\\\"\\\" source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t</PlanModel>\\r\\n\\t\\t\\t</Plan>\\r\\n\\t\\t</Plans>\\r\\n\\t</ServiceTemplate>\\r\\n\\t<NodeTypeImplementation nodeType=\\\"QName\\\" name=\\\"NCName\\\"></NodeTypeImplementation>\\r\\n</Definitions>\",\n" +
	            "\t\"applicationVersion\": \"1\"\n" +
	            "}";
	    */
	    String lastJSON="{"
	    		+"\"statusId\": {"
	    	+"\"id\": 1"
	    	+"},"
                +"\"userId\": {"
	    		+"\"id\": 3"
	    		+"},"
                +"\"applicationName\": \"test app xml 990000999\","
                +"\"applicationDescription\": \"test description xml\","
                +"\"applicationToscaTemplate\": "
                + "\"" + StringEscapeUtils.escapeJavaScript(xmlFile) + "\","
                //+ "\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\r\n<Definitions id=\\\"Clearo\\\" name=\\\"\\\"targetNamespace=\\\"http://tempuri.org\\\"\r\n\txmlns=\\\"http://docs.oasis-open.org/tosca/ns/2011/12\\\" xmlns:xml=\\\"http://www.w3.org/XML/1998/namespace\\\"\r\n\txmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"\r\n\txmlns:co=\\\"http://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes\\\"\r\n\txsi:schemaLocation=\\\"http://docs.oasis-open.org/tosca/ns/2011/12 TOSCA-v1.0.xsd \r\n\thttp://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes ./Types/CloudOptingTypes.xsd\\\">\r\n\t<NodeType name=\\\"VMhost\\\">\r\n\t\t<documentation>This is the VM description, we need to collect the SLA\r\n\t\t\tinformation (that is the set of CPU+RAM+DISK) that the VM need to\r\n\t\t\thave for the service (this information is just a label for the end\r\n\t\t\tuser but translate to data for the system)\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\\\"co:VMhostProperties\\\"\r\n\t\t\ttype=\\\"co:tVMhostProperties\\\" />\r\n\t\t<Interfaces>\r\n\t\t\t<Interface name=\\\"http://tempuri.org\\\">\r\n\t\t\t\t<Operation name=\\\"Install\\\">\r\n\t\t\t\t\t<documentation>The parameters to ask to the end user to execute the\r\n\t\t\t\t\t\t\\\"install\\\" operation of this node\r\n\t\t\t\t\t</documentation>\r\n\t\t\t\t\t<InputParameters>\r\n\t\t\t\t\t\t<InputParameter name=\\\"co:SLA.co:Chosen\\\" type=\\\"co:SLA\\\" />\r\n\t\t\t\t\t</InputParameters>\r\n\t\t\t\t</Operation>\r\n\t\t\t</Interface>\r\n\t\t</Interfaces>\r\n\t</NodeType>\r\n\t<NodeType name=\\\"DockerContainer\\\">\r\n\t\t<documentation>This is the Docker Container (the Docker host is\r\n\t\t\talready installed in the VM image)\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\r\n\t</NodeType>\r\n\t<NodeType name=\\\"Apache\\\">\r\n\t\t<documentation>This is the Apache server (we should not ask anything\r\n\t\t\tto the end user about apache, but we need to set the properties)\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\r\n\t</NodeType>\r\n\t<NodeType name=\\\"ApacheVirtualHost\\\">\r\n\t\t<documentation>This is the Apache Virtual Host and here we have things\r\n\t\t\tto ask to the user\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\r\n\t\t<Interfaces>\r\n\t\t\t<Interface name=\\\"http://tempuri.org\\\">\r\n\t\t\t\t<Operation name=\\\"Install\\\">\r\n\t\t\t\t\t<InputParameters>\r\n\t\t\t\t\t\t<InputParameter name=\\\"VHostName\\\" type=\\\"xs:string\\\" />\r\n\t\t\t\t\t</InputParameters>\r\n\t\t\t\t</Operation>\r\n\t\t\t</Interface>\r\n\t\t</Interfaces>\r\n\t</NodeType>\r\n\t<ServiceTemplate id=\\\"Clearo\\\" name=\\\"\\\"\r\n\t\tsubstitutableNodeType=\\\"QName\\\" targetNamespace=\\\"http://tempuri.org\\\">\r\n\r\n\t\t<TopologyTemplate>\r\n\t\t\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\r\n\t\t\t<NodeTemplate id=\\\"ClearoVM\\\" maxInstances=\\\"1\\\"\r\n\t\t\t\tminInstances=\\\"1\\\" name=\\\"\\\"type=\\\"VMhost\\\">\r\n\t\t\t\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\r\n\t\t\t\t<Properties>\r\n\t\t\t\t\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\r\n\t\t\t\t\t<co:VMhostProperties>\r\n\t\t\t\t\t\t<co:SLAsProperties>\r\n\t\t\t\t\t\t\t<co:SLA Name=\\\"Big City\\\" id=\\\"BigCity\\\">\r\n\t\t\t\t\t\t\t\t<co:NumCpus>2</co:NumCpus>\r\n\t\t\t\t\t\t\t\t<co:Memory>2</co:Memory>\r\n\t\t\t\t\t\t\t\t<co:Price>10000</co:Price>\r\n\t\t\t\t\t\t\t\t<co:Disk>10</co:Disk>\r\n\t\t\t\t\t\t\t\t<co:Chosen>false</co:Chosen>\r\n\t\t\t\t\t\t\t</co:SLA>\r\n\t\t\t\t\t\t\t<co:SLA Name=\\\"Small City\\\" id=\\\"SmallCity\\\">\r\n\t\t\t\t\t\t\t\t<co:NumCpus>1</co:NumCpus>\r\n\t\t\t\t\t\t\t\t<co:Memory>1</co:Memory>\r\n\t\t\t\t\t\t\t\t<co:Price>5000</co:Price>\r\n\t\t\t\t\t\t\t\t<co:Disk>5</co:Disk>\r\n\t\t\t\t\t\t\t\t<co:Chosen>false</co:Chosen>\r\n\t\t\t\t\t\t\t</co:SLA>\r\n\t\t\t\t\t\t\t<co:SLA Name=\\\"Region\\\" id=\\\"Region\\\">\r\n\t\t\t\t\t\t\t\t<co:NumCpus>4</co:NumCpus>\r\n\t\t\t\t\t\t\t\t<co:Memory>4</co:Memory>\r\n\t\t\t\t\t\t\t\t<co:Price>20000</co:Price>\r\n\t\t\t\t\t\t\t\t<co:Disk>20</co:Disk>\r\n\t\t\t\t\t\t\t\t<co:Chosen>false</co:Chosen>\r\n\t\t\t\t\t\t\t</co:SLA>\r\n\t\t\t\t\t\t\t<co:vmImage></co:vmImage>\r\n\t\t\t\t\t\t</co:SLAsProperties>\r\n\t\t\t\t\t</co:VMhostProperties>\r\n\t\t\t\t</Properties>\r\n\t\t\t</NodeTemplate>\r\n\t\t\t<NodeTemplate type=\\\"DockerContainer\\\" id=\\\"ClearoApacheDC\\\"></NodeTemplate>\r\n\t\t\t<NodeTemplate type=\\\"Apache\\\" id=\\\"ClearoApache\\\"></NodeTemplate>\r\n\t\t\t<NodeTemplate type=\\\"ApacheVirtualHost\\\" id=\\\"ClearoApacheVH\\\">\r\n\t\t\t\t<Properties>\r\n\t\t\t\t\t<co:ApacheVirtualHostproperties>\r\n\t\t\t\t\t\t<co:VHostName><?getInput VHostName?></co:VHostName>\r\n\t\t\t\t\t</co:ApacheVirtualHostproperties>\r\n\t\t\t\t</Properties>\r\n\t\t\t</NodeTemplate>\r\n\t\t</TopologyTemplate>\r\n\t\t<Plans targetNamespace=\\\"http://tempuri.org\\\">\r\n\t\t\t<Plan id=\\\"idvalue5\\\" name=\\\"\\\"planLanguage=\\\"http://tempuri.org\\\"\r\n\t\t\t\tplanType=\\\"http://tempuri.org\\\">\r\n\t\t\t\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\r\n\t\t\t\t<Precondition expressionLanguage=\\\"http://tempuri.org\\\" />\r\n\t\t\t\t<InputParameters>\r\n\t\t\t\t\t<InputParameter name=\\\"\\\"required=\\\"yes\\\" type=\\\"\\\"/>\r\n\t\t\t\t</InputParameters>\r\n\t\t\t\t<OutputParameters>\r\n\t\t\t\t\t<OutputParametername=\\\"\\\" required=\\\"yes\\\" type=\\\"\\\"/>\r\n\t\t\t\t</OutputParameters>\r\n\t\t\t\t<PlanModel>\r\n\t\t\t\t\t<documentationxml: lang=\\\"\\\" source=\\\"http://tempuri.org\\\" />\r\n\t\t\t\t</PlanModel>\r\n\t\t\t</Plan>\r\n\t\t</Plans>\r\n\t</ServiceTemplate>\r\n\t<NodeTypeImplementation nodeType=\\\"QName\\\" name=\\\"NCName\\\"></NodeTypeImplementation>\r\n</Definitions>\","
                + "\"applicationVersion\": \"1\""
 +"}";

		
		//CREATE INSTANCE
		ProxyAPIService c = new ProxyAPIService();
		//CONNECT
		c.connect(user, password);
		//CREATE THE APPLICATION
		c.applicationCreate(lastJSON);

	}
	
	String createNewApplicationJson = "{\n" +
            "\t\"statusId\": {\n" +
            "\t\t\"id\": 1\n" +
            "\t},\n" +
            "\t\"userId\": {\n" +
            "\t\t\"id\": 3\n" +
            "\t},\n" +
            "\t\"applicationName\": \"test app xml 888888\",\n" +
            "\t\"applicationDescription\": \"test description xml\",\n" +
            "\t\"applicationToscaTemplate\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\r\\n<Definitions id=\\\"Clearo\\\" name=\\\"\\\"targetNamespace=\\\"http://tempuri.org\\\"\\r\\n\\txmlns=\\\"http://docs.oasis-open.org/tosca/ns/2011/12\\\" xmlns:xml=\\\"http://www.w3.org/XML/1998/namespace\\\"\\r\\n\\txmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"\\r\\n\\txmlns:co=\\\"http://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes\\\"\\r\\n\\txsi:schemaLocation=\\\"http://docs.oasis-open.org/tosca/ns/2011/12 TOSCA-v1.0.xsd \\r\\n\\thttp://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes ./Types/CloudOptingTypes.xsd\\\">\\r\\n\\t<NodeType name=\\\"VMhost\\\">\\r\\n\\t\\t<documentation>This is the VM description, we need to collect the SLA\\r\\n\\t\\t\\tinformation (that is the set of CPU+RAM+DISK) that the VM need to\\r\\n\\t\\t\\thave for the service (this information is just a label for the end\\r\\n\\t\\t\\tuser but translate to data for the system)\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"co:VMhostProperties\\\"\\r\\n\\t\\t\\ttype=\\\"co:tVMhostProperties\\\" />\\r\\n\\t\\t<Interfaces>\\r\\n\\t\\t\\t<Interface name=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t\\t<Operation name=\\\"Install\\\">\\r\\n\\t\\t\\t\\t\\t<documentation>The parameters to ask to the end user to execute the\\r\\n\\t\\t\\t\\t\\t\\t\\\"install\\\" operation of this node\\r\\n\\t\\t\\t\\t\\t</documentation>\\r\\n\\t\\t\\t\\t\\t<InputParameters>\\r\\n\\t\\t\\t\\t\\t\\t<InputParameter name=\\\"co:SLA.co:Chosen\\\" type=\\\"co:SLA\\\" />\\r\\n\\t\\t\\t\\t\\t</InputParameters>\\r\\n\\t\\t\\t\\t</Operation>\\r\\n\\t\\t\\t</Interface>\\r\\n\\t\\t</Interfaces>\\r\\n\\t</NodeType>\\r\\n\\t<NodeType name=\\\"DockerContainer\\\">\\r\\n\\t\\t<documentation>This is the Docker Container (the Docker host is\\r\\n\\t\\t\\talready installed in the VM image)\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\\r\\n\\t</NodeType>\\r\\n\\t<NodeType name=\\\"Apache\\\">\\r\\n\\t\\t<documentation>This is the Apache server (we should not ask anything\\r\\n\\t\\t\\tto the end user about apache, but we need to set the properties)\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\\r\\n\\t</NodeType>\\r\\n\\t<NodeType name=\\\"ApacheVirtualHost\\\">\\r\\n\\t\\t<documentation>This is the Apache Virtual Host and here we have things\\r\\n\\t\\t\\tto ask to the user\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\\r\\n\\t\\t<Interfaces>\\r\\n\\t\\t\\t<Interface name=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t\\t<Operation name=\\\"Install\\\">\\r\\n\\t\\t\\t\\t\\t<InputParameters>\\r\\n\\t\\t\\t\\t\\t\\t<InputParameter name=\\\"VHostName\\\" type=\\\"xs:string\\\" />\\r\\n\\t\\t\\t\\t\\t</InputParameters>\\r\\n\\t\\t\\t\\t</Operation>\\r\\n\\t\\t\\t</Interface>\\r\\n\\t\\t</Interfaces>\\r\\n\\t</NodeType>\\r\\n\\t<ServiceTemplate id=\\\"Clearo\\\" name=\\\"\\\"\\r\\n\\t\\tsubstitutableNodeType=\\\"QName\\\" targetNamespace=\\\"http://tempuri.org\\\">\\r\\n\\r\\n\\t\\t<TopologyTemplate>\\r\\n\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t<NodeTemplate id=\\\"ClearoVM\\\" maxInstances=\\\"1\\\"\\r\\n\\t\\t\\t\\tminInstances=\\\"1\\\" name=\\\"\\\"type=\\\"VMhost\\\">\\r\\n\\t\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t<Properties>\\r\\n\\t\\t\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t\\t<co:VMhostProperties>\\r\\n\\t\\t\\t\\t\\t\\t<co:SLAsProperties>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:SLA Name=\\\"Big City\\\" id=\\\"BigCity\\\">\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:NumCpus>2</co:NumCpus>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Memory>2</co:Memory>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Price>10000</co:Price>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Disk>10</co:Disk>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Chosen>false</co:Chosen>\\r\\n\\t\\t\\t\\t\\t\\t\\t</co:SLA>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:SLA Name=\\\"Small City\\\" id=\\\"SmallCity\\\">\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:NumCpus>1</co:NumCpus>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Memory>1</co:Memory>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Price>5000</co:Price>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Disk>5</co:Disk>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Chosen>false</co:Chosen>\\r\\n\\t\\t\\t\\t\\t\\t\\t</co:SLA>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:SLA Name=\\\"Region\\\" id=\\\"Region\\\">\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:NumCpus>4</co:NumCpus>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Memory>4</co:Memory>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Price>20000</co:Price>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Disk>20</co:Disk>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Chosen>false</co:Chosen>\\r\\n\\t\\t\\t\\t\\t\\t\\t</co:SLA>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:vmImage></co:vmImage>\\r\\n\\t\\t\\t\\t\\t\\t</co:SLAsProperties>\\r\\n\\t\\t\\t\\t\\t</co:VMhostProperties>\\r\\n\\t\\t\\t\\t</Properties>\\r\\n\\t\\t\\t</NodeTemplate>\\r\\n\\t\\t\\t<NodeTemplate type=\\\"DockerContainer\\\" id=\\\"ClearoApacheDC\\\"></NodeTemplate>\\r\\n\\t\\t\\t<NodeTemplate type=\\\"Apache\\\" id=\\\"ClearoApache\\\"></NodeTemplate>\\r\\n\\t\\t\\t<NodeTemplate type=\\\"ApacheVirtualHost\\\" id=\\\"ClearoApacheVH\\\">\\r\\n\\t\\t\\t\\t<Properties>\\r\\n\\t\\t\\t\\t\\t<co:ApacheVirtualHostproperties>\\r\\n\\t\\t\\t\\t\\t\\t<co:VHostName><?getInput VHostName?></co:VHostName>\\r\\n\\t\\t\\t\\t\\t</co:ApacheVirtualHostproperties>\\r\\n\\t\\t\\t\\t</Properties>\\r\\n\\t\\t\\t</NodeTemplate>\\r\\n\\t\\t</TopologyTemplate>\\r\\n\\t\\t<Plans targetNamespace=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t<Plan id=\\\"idvalue5\\\" name=\\\"\\\"planLanguage=\\\"http://tempuri.org\\\"\\r\\n\\t\\t\\t\\tplanType=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t<Precondition expressionLanguage=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t<InputParameters>\\r\\n\\t\\t\\t\\t\\t<InputParameter name=\\\"\\\"required=\\\"yes\\\" type=\\\"\\\"/>\\r\\n\\t\\t\\t\\t</InputParameters>\\r\\n\\t\\t\\t\\t<OutputParameters>\\r\\n\\t\\t\\t\\t\\t<OutputParametername=\\\"\\\" required=\\\"yes\\\" type=\\\"\\\"/>\\r\\n\\t\\t\\t\\t</OutputParameters>\\r\\n\\t\\t\\t\\t<PlanModel>\\r\\n\\t\\t\\t\\t\\t<documentationxml: lang=\\\"\\\" source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t</PlanModel>\\r\\n\\t\\t\\t</Plan>\\r\\n\\t\\t</Plans>\\r\\n\\t</ServiceTemplate>\\r\\n\\t<NodeTypeImplementation nodeType=\\\"QName\\\" name=\\\"NCName\\\"></NodeTypeImplementation>\\r\\n</Definitions>\",\n" +
            "\t\"applicationVersion\": \"1\"\n" +
            "}";
	
//	@Test
	public void testCreateNewCustomization() throws MalformedURLException, IOException {
		String json = 
		"{"
			+"\"statusId\": {"
			+"\"id\": 1"
			+"},"
			+"\"applicationId\":{"
				+"\"id\": 1"
			+"},"
			+"\"customizationToscaFile\": \"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Definitions id=\"Clearo\" name=\"\"targetNamespace=\"http://tempuri.org\"\r\n\txmlns=\"http://docs.oasis-open.org/tosca/ns/2011/12\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\r\n\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n\txmlns:co=\"http://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes\"\r\n\txsi:schemaLocation=\"http://docs.oasis-open.org/tosca/ns/2011/12 TOSCA-v1.0.xsd \r\n\thttp://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes ./Types/CloudOptingTypes.xsd\">\r\n\t<NodeType name=\"VMhost\">\r\n\t\t<documentation>This is the VM description, we need to collect the SLA\r\n\t\t\tinformation (that is the set of CPU+RAM+DISK) that the VM need to\r\n\t\t\thave for the service (this information is just a label for the end\r\n\t\t\tuser but translate to data for the system)\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\"co:VMhostProperties\"\r\n\t\t\ttype=\"co:tVMhostProperties\" />\r\n\t\t<Interfaces>\r\n\t\t\t<Interface name=\"http://tempuri.org\">\r\n\t\t\t\t<Operation name=\"Install\">\r\n\t\t\t\t\t<documentation>The parameters to ask to the end user to execute the\r\n\t\t\t\t\t\t\"install\" operation of this node\r\n\t\t\t\t\t</documentation>\r\n\t\t\t\t\t<InputParameters>\r\n\t\t\t\t\t\t<InputParameter name=\"co:SLA.co:Chosen\" type=\"co:SLA\" />\r\n\t\t\t\t\t</InputParameters>\r\n\t\t\t\t</Operation>\r\n\t\t\t</Interface>\r\n\t\t</Interfaces>\r\n\t</NodeType>\r\n\t<NodeType name=\"DockerContainer\">\r\n\t\t<documentation>This is the Docker Container (the Docker host is\r\n\t\t\talready installed in the VM image)\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\"\"type=\"\" />\r\n\t</NodeType>\r\n\t<NodeType name=\"Apache\">\r\n\t\t<documentation>This is the Apache server (we should not ask anything\r\n\t\t\tto the end user about apache, but we need to set the properties)\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\"\"type=\"\" />\r\n\t</NodeType>\r\n\t<NodeType name=\"ApacheVirtualHost\">\r\n\t\t<documentation>This is the Apache Virtual Host and here we have things\r\n\t\t\tto ask to the user\r\n\t\t</documentation>\r\n\t\t<PropertiesDefinition element=\"\"type=\"\" />\r\n\t\t<Interfaces>\r\n\t\t\t<Interface name=\"http://tempuri.org\">\r\n\t\t\t\t<Operation name=\"Install\">\r\n\t\t\t\t\t<InputParameters>\r\n\t\t\t\t\t\t<InputParameter name=\"VHostName\" type=\"xs:string\" />\r\n\t\t\t\t\t</InputParameters>\r\n\t\t\t\t</Operation>\r\n\t\t\t</Interface>\r\n\t\t</Interfaces>\r\n\t</NodeType>\r\n\t<ServiceTemplate id=\"Clearo\" name=\"\"\r\n\t\tsubstitutableNodeType=\"QName\" targetNamespace=\"http://tempuri.org\">\r\n\r\n\t\t<TopologyTemplate>\r\n\t\t\t<documentation xml:lang=\"\"source=\"http://tempuri.org\" />\r\n\t\t\t<NodeTemplate id=\"ClearoVM\" maxInstances=\"1\"\r\n\t\t\t\tminInstances=\"1\" name=\"\"type=\"VMhost\">\r\n\t\t\t\t<documentation xml:lang=\"\"source=\"http://tempuri.org\" />\r\n\t\t\t\t<Properties>\r\n\t\t\t\t\t<documentation xml:lang=\"\"source=\"http://tempuri.org\" />\r\n\t\t\t\t\t<co:VMhostProperties>\r\n\t\t\t\t\t\t<co:SLAsProperties>\r\n\t\t\t\t\t\t\t<co:SLA Name=\"Big City\" id=\"BigCity\">\r\n\t\t\t\t\t\t\t\t<co:NumCpus>2</co:NumCpus>\r\n\t\t\t\t\t\t\t\t<co:Memory>2</co:Memory>\r\n\t\t\t\t\t\t\t\t<co:Price>10000</co:Price>\r\n\t\t\t\t\t\t\t\t<co:Disk>10</co:Disk>\r\n\t\t\t\t\t\t\t\t<co:Chosen>false</co:Chosen>\r\n\t\t\t\t\t\t\t</co:SLA>\r\n\t\t\t\t\t\t\t<co:SLA Name=\"Small City\" id=\"SmallCity\">\r\n\t\t\t\t\t\t\t\t<co:NumCpus>1</co:NumCpus>\r\n\t\t\t\t\t\t\t\t<co:Memory>1</co:Memory>\r\n\t\t\t\t\t\t\t\t<co:Price>5000</co:Price>\r\n\t\t\t\t\t\t\t\t<co:Disk>5</co:Disk>\r\n\t\t\t\t\t\t\t\t<co:Chosen>false</co:Chosen>\r\n\t\t\t\t\t\t\t</co:SLA>\r\n\t\t\t\t\t\t\t<co:SLA Name=\"Region\" id=\"Region\">\r\n\t\t\t\t\t\t\t\t<co:NumCpus>4</co:NumCpus>\r\n\t\t\t\t\t\t\t\t<co:Memory>4</co:Memory>\r\n\t\t\t\t\t\t\t\t<co:Price>20000</co:Price>\r\n\t\t\t\t\t\t\t\t<co:Disk>20</co:Disk>\r\n\t\t\t\t\t\t\t\t<co:Chosen>false</co:Chosen>\r\n\t\t\t\t\t\t\t</co:SLA>\r\n\t\t\t\t\t\t\t<co:vmImage></co:vmImage>\r\n\t\t\t\t\t\t</co:SLAsProperties>\r\n\t\t\t\t\t</co:VMhostProperties>\r\n\t\t\t\t</Properties>\r\n\t\t\t</NodeTemplate>\r\n\t\t\t<NodeTemplate type=\"DockerContainer\" id=\"ClearoApacheDC\"></NodeTemplate>\r\n\t\t\t<NodeTemplate type=\"Apache\" id=\"ClearoApache\"></NodeTemplate>\r\n\t\t\t<NodeTemplate type=\"ApacheVirtualHost\" id=\"ClearoApacheVH\">\r\n\t\t\t\t<Properties>\r\n\t\t\t\t\t<co:ApacheVirtualHostproperties>\r\n\t\t\t\t\t\t<co:VHostName><?getInput VHostName?></co:VHostName>\r\n\t\t\t\t\t</co:ApacheVirtualHostproperties>\r\n\t\t\t\t</Properties>\r\n\t\t\t</NodeTemplate>\r\n\t\t</TopologyTemplate>\r\n\t\t<Plans targetNamespace=\"http://tempuri.org\">\r\n\t\t\t<Plan id=\"idvalue5\" name=\"\"planLanguage=\"http://tempuri.org\"\r\n\t\t\t\tplanType=\"http://tempuri.org\">\r\n\t\t\t\t<documentation xml:lang=\"\"source=\"http://tempuri.org\" />\r\n\t\t\t\t<Precondition expressionLanguage=\"http://tempuri.org\" />\r\n\t\t\t\t<InputParameters>\r\n\t\t\t\t\t<InputParameter name=\"\"required=\"yes\" type=\"\"/>\r\n\t\t\t\t</InputParameters>\r\n\t\t\t\t<OutputParameters>\r\n\t\t\t\t\t<OutputParametername=\"\" required=\"yes\" type=\"\"/>\r\n\t\t\t\t</OutputParameters>\r\n\t\t\t\t<PlanModel>\r\n\t\t\t\t\t<documentationxml: lang=\"\" source=\"http://tempuri.org\" />\r\n\t\t\t\t</PlanModel>\r\n\t\t\t</Plan>\r\n\t\t</Plans>\r\n\t</ServiceTemplate>\r\n\t<NodeTypeImplementation nodeType=\"QName\" name=\"NCName\"></NodeTypeImplementation>\r\n</Definitions>\","
			+"\"customizationCreation\": \"2015-02-15\","
			+"\"customizationActivation\": \"2015-02-15\","
			+"\"customizationDecommission\": \"2015-02-15\","
			+"\"username\": \"1\""
		+"}";


	    String createNewCustomizationJson = "{\n" +
	            "\t\"statusId\": {\n" +
	            "\t\t\"id\": 1\n" +
	            "\t},\n" +
	            "\t\"applicationId\":{\n" +
	            "\t\t\"id\": 181\n" +
	            "\t},\n" +
	            "\t\"customizationToscaFile\": " +
	            "\"" + xmlFile + "\"," +
	            //+ "\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\r\\n<Definitions id=\\\"Clearo\\\" name=\\\"\\\"targetNamespace=\\\"http://tempuri.org\\\"\\r\\n\\txmlns=\\\"http://docs.oasis-open.org/tosca/ns/2011/12\\\" xmlns:xml=\\\"http://www.w3.org/XML/1998/namespace\\\"\\r\\n\\txmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"\\r\\n\\txmlns:co=\\\"http://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes\\\"\\r\\n\\txsi:schemaLocation=\\\"http://docs.oasis-open.org/tosca/ns/2011/12 TOSCA-v1.0.xsd \\r\\n\\thttp://docs.oasis-open.org/tosca/ns/2011/12/CloudOptingTypes ./Types/CloudOptingTypes.xsd\\\">\\r\\n\\t<NodeType name=\\\"VMhost\\\">\\r\\n\\t\\t<documentation>This is the VM description, we need to collect the SLA\\r\\n\\t\\t\\tinformation (that is the set of CPU+RAM+DISK) that the VM need to\\r\\n\\t\\t\\thave for the service (this information is just a label for the end\\r\\n\\t\\t\\tuser but translate to data for the system)\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"co:VMhostProperties\\\"\\r\\n\\t\\t\\ttype=\\\"co:tVMhostProperties\\\" />\\r\\n\\t\\t<Interfaces>\\r\\n\\t\\t\\t<Interface name=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t\\t<Operation name=\\\"Install\\\">\\r\\n\\t\\t\\t\\t\\t<documentation>The parameters to ask to the end user to execute the\\r\\n\\t\\t\\t\\t\\t\\t\\\"install\\\" operation of this node\\r\\n\\t\\t\\t\\t\\t</documentation>\\r\\n\\t\\t\\t\\t\\t<InputParameters>\\r\\n\\t\\t\\t\\t\\t\\t<InputParameter name=\\\"co:SLA.co:Chosen\\\" type=\\\"co:SLA\\\" />\\r\\n\\t\\t\\t\\t\\t</InputParameters>\\r\\n\\t\\t\\t\\t</Operation>\\r\\n\\t\\t\\t</Interface>\\r\\n\\t\\t</Interfaces>\\r\\n\\t</NodeType>\\r\\n\\t<NodeType name=\\\"DockerContainer\\\">\\r\\n\\t\\t<documentation>This is the Docker Container (the Docker host is\\r\\n\\t\\t\\talready installed in the VM image)\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\\r\\n\\t</NodeType>\\r\\n\\t<NodeType name=\\\"Apache\\\">\\r\\n\\t\\t<documentation>This is the Apache server (we should not ask anything\\r\\n\\t\\t\\tto the end user about apache, but we need to set the properties)\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\\r\\n\\t</NodeType>\\r\\n\\t<NodeType name=\\\"ApacheVirtualHost\\\">\\r\\n\\t\\t<documentation>This is the Apache Virtual Host and here we have things\\r\\n\\t\\t\\tto ask to the user\\r\\n\\t\\t</documentation>\\r\\n\\t\\t<PropertiesDefinition element=\\\"\\\"type=\\\"\\\" />\\r\\n\\t\\t<Interfaces>\\r\\n\\t\\t\\t<Interface name=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t\\t<Operation name=\\\"Install\\\">\\r\\n\\t\\t\\t\\t\\t<InputParameters>\\r\\n\\t\\t\\t\\t\\t\\t<InputParameter name=\\\"VHostName\\\" type=\\\"xs:string\\\" />\\r\\n\\t\\t\\t\\t\\t</InputParameters>\\r\\n\\t\\t\\t\\t</Operation>\\r\\n\\t\\t\\t</Interface>\\r\\n\\t\\t</Interfaces>\\r\\n\\t</NodeType>\\r\\n\\t<ServiceTemplate id=\\\"Clearo\\\" name=\\\"\\\"\\r\\n\\t\\tsubstitutableNodeType=\\\"QName\\\" targetNamespace=\\\"http://tempuri.org\\\">\\r\\n\\r\\n\\t\\t<TopologyTemplate>\\r\\n\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t<NodeTemplate id=\\\"ClearoVM\\\" maxInstances=\\\"1\\\"\\r\\n\\t\\t\\t\\tminInstances=\\\"1\\\" name=\\\"\\\"type=\\\"VMhost\\\">\\r\\n\\t\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t<Properties>\\r\\n\\t\\t\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t\\t<co:VMhostProperties>\\r\\n\\t\\t\\t\\t\\t\\t<co:SLAsProperties>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:SLA Name=\\\"Big City\\\" id=\\\"BigCity\\\">\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:NumCpus>2</co:NumCpus>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Memory>2</co:Memory>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Price>10000</co:Price>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Disk>10</co:Disk>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Chosen>false</co:Chosen>\\r\\n\\t\\t\\t\\t\\t\\t\\t</co:SLA>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:SLA Name=\\\"Small City\\\" id=\\\"SmallCity\\\">\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:NumCpus>1</co:NumCpus>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Memory>1</co:Memory>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Price>5000</co:Price>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Disk>5</co:Disk>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Chosen>false</co:Chosen>\\r\\n\\t\\t\\t\\t\\t\\t\\t</co:SLA>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:SLA Name=\\\"Region\\\" id=\\\"Region\\\">\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:NumCpus>4</co:NumCpus>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Memory>4</co:Memory>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Price>20000</co:Price>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Disk>20</co:Disk>\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t<co:Chosen>false</co:Chosen>\\r\\n\\t\\t\\t\\t\\t\\t\\t</co:SLA>\\r\\n\\t\\t\\t\\t\\t\\t\\t<co:vmImage></co:vmImage>\\r\\n\\t\\t\\t\\t\\t\\t</co:SLAsProperties>\\r\\n\\t\\t\\t\\t\\t</co:VMhostProperties>\\r\\n\\t\\t\\t\\t</Properties>\\r\\n\\t\\t\\t</NodeTemplate>\\r\\n\\t\\t\\t<NodeTemplate type=\\\"DockerContainer\\\" id=\\\"ClearoApacheDC\\\"></NodeTemplate>\\r\\n\\t\\t\\t<NodeTemplate type=\\\"Apache\\\" id=\\\"ClearoApache\\\"></NodeTemplate>\\r\\n\\t\\t\\t<NodeTemplate type=\\\"ApacheVirtualHost\\\" id=\\\"ClearoApacheVH\\\">\\r\\n\\t\\t\\t\\t<Properties>\\r\\n\\t\\t\\t\\t\\t<co:ApacheVirtualHostproperties>\\r\\n\\t\\t\\t\\t\\t\\t<co:VHostName><?getInput VHostName?></co:VHostName>\\r\\n\\t\\t\\t\\t\\t</co:ApacheVirtualHostproperties>\\r\\n\\t\\t\\t\\t</Properties>\\r\\n\\t\\t\\t</NodeTemplate>\\r\\n\\t\\t</TopologyTemplate>\\r\\n\\t\\t<Plans targetNamespace=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t<Plan id=\\\"idvalue5\\\" name=\\\"\\\"planLanguage=\\\"http://tempuri.org\\\"\\r\\n\\t\\t\\t\\tplanType=\\\"http://tempuri.org\\\">\\r\\n\\t\\t\\t\\t<documentation xml:lang=\\\"\\\"source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t<Precondition expressionLanguage=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t<InputParameters>\\r\\n\\t\\t\\t\\t\\t<InputParameter name=\\\"\\\"required=\\\"yes\\\" type=\\\"\\\"/>\\r\\n\\t\\t\\t\\t</InputParameters>\\r\\n\\t\\t\\t\\t<OutputParameters>\\r\\n\\t\\t\\t\\t\\t<OutputParametername=\\\"\\\" required=\\\"yes\\\" type=\\\"\\\"/>\\r\\n\\t\\t\\t\\t</OutputParameters>\\r\\n\\t\\t\\t\\t<PlanModel>\\r\\n\\t\\t\\t\\t\\t<documentationxml: lang=\\\"\\\" source=\\\"http://tempuri.org\\\" />\\r\\n\\t\\t\\t\\t</PlanModel>\\r\\n\\t\\t\\t</Plan>\\r\\n\\t\\t</Plans>\\r\\n\\t</ServiceTemplate>\\r\\n\\t<NodeTypeImplementation nodeType=\\\"QName\\\" name=\\\"NCName\\\"></NodeTypeImplementation>\\r\\n</Definitions>\",\n" +
	            "\t\"customizationCreation\": \"2015-02-15\",\n" +
	            "\t\"customizationActivation\": \"2015-02-15\",\n" +
	            "\t\"customizationDecommission\": \"2015-02-15\",\n" +
	            "\t\"username\": \"1\"\n" +
	            "}";
		
		//CREATE INSTANCE
		ProxyAPIService c = new ProxyAPIService();
		//CONNECT
		c.connect(user, password);
		//CREATE THE APPLICATION
		c.applicationCustomization(createNewCustomizationJson);

	}
}
