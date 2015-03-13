package eu.cloudopting.ui.ToscaUI.server.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.cruxframework.crux.core.server.rest.annotation.RestService;
import org.cruxframework.crux.core.shared.rest.annotation.GET;
import org.cruxframework.crux.core.shared.rest.annotation.POST;
import org.cruxframework.crux.core.shared.rest.annotation.Path;

/**
 * 
 * @author xeviscc
 *
 */
@RestService("toscaProviderService")
@Path("/toscaProvider")
public class ToscaProviderServiceImpl {
	
	@GET
	@Path("getToscaFile")
	public Object getToscaFile() {

		try {
			URL url = new URL("http://cloudopting1.cloudapp.net:8888/cloud4cities-rest/rest/facilities");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

//		File file = new File("C:\\file.xml");
//		JAXBContext jaxbContext = JAXBContext.newInstance(Object.class);
//
//		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//		Customer customer = (Customer) jaxbUnmarshaller.unmarshal(file);
//		System.out.println(customer);


		return null;
	}

	@POST
	@Path("setToscaFile")
	public void setToscaFile(Object object) {
//		File file = new File("C:\\file.xml");
//
//		JAXBContext jaxbContext = JAXBContext.newInstance(Object.class);
//		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//		// output pretty printed
//		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//		jaxbMarshaller.marshal(customer, file);
//		jaxbMarshaller.marshal(customer, System.out);
		System.out.println("INSIDE SET TOSCA FILE");
	}

}
