package filetools.gif;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GifXmlOutput {

	static String pathtowriter;
	
	public static PrintWriter xmlgifwriter;

	public static void createXmlGifOutput() throws IOException {
		
		pathtowriter = GifChecker.giffolder + "//" + "GifExamination" + ".xml";

		xmlgifwriter = new PrintWriter(new FileWriter(pathtowriter));

		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";

		xmlgifwriter.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		xmlgifwriter.println("<GifFindings>");
	}

	public static void closeXmlGifOutput() {
		xmlgifwriter.println("</GifFindings>");
		xmlgifwriter.close();		
		XmlParserGif.parseXmlFile (pathtowriter);
	}

}
