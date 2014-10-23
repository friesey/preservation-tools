package output;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.tika.metadata.Metadata;

public class XmlOutput {
	
	public static PrintWriter xmlSimpleWriter;
	
	public static void outputsinXML() throws IOException {
	
	xmlSimpleWriter = new PrintWriter(new FileWriter(
			"D:\\MetadataFolder\\audiotest.txt"));
	}
}
