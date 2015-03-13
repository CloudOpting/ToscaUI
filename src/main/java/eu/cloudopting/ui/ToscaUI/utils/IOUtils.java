package eu.cloudopting.ui.ToscaUI.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 * @author xeviscc
 *
 */
public class IOUtils {

	/**
	 * Transforms the file in the path to a string taking into account the ecoding charset.
	 * 
	 * @param path The path to the file to be transformed
	 * @param encoding Encoding used to do the transformation.
	 * @return a String with the file encoded with the charset specified.
	 * @throws IOException
	 */
	public static String readFile(String path, Charset encoding) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
