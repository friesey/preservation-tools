package output;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class XmlWriter {

	public PrintWriter out;

	public XmlWriter(String folder, String fileName) throws IOException {

		out = new PrintWriter(new FileWriter(folder + "\\" + fileName));
		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		out.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
	}

	public void printXml(String text) {
		out.println(text);
	}

	public void closeXmlWriter() {
		out.close();
	}
}
