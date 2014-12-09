package output;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class XmlOutput {

	public static PrintWriter xmlSimpleWriter;

	public static void createsXMLHeader() throws IOException {

		// TODO: Add Folder Browser Dialog

		xmlSimpleWriter = new PrintWriter(new FileWriter("C:\\Users\\Friese Yvonne\\Desktop\\Computer Science\\XML und Style\\xmltest.xml"));

		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		// TODO: Choose Stylesheet from folder FileBrowseDialog
		String xmlStylesheet = "TiffTagStyle.xsl";

		xmlSimpleWriter.println("<=?" + xmlVersion + " " + xmlEncoding + "?>");
		xmlSimpleWriter.println("<?xml-stylesheet type=\"test/xsl\" href =\"" + xmlStylesheet + "\"?>");

	}

	

}
