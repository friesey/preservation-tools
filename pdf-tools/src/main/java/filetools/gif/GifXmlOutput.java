package filetools.gif;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class GifXmlOutput {

	public static PrintWriter xmlgifwriter;

	public static void createXmlGifOutput() throws IOException {
		
		xmlgifwriter = new PrintWriter(new FileWriter(GifChecker.giffolder + "//" + "GifExaminationSummary" + ".xml"));

		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		String xmlxslStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"GifCustomized.xsl\"?>";

		xmlgifwriter.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		xmlgifwriter.println(xmlxslStyleSheet);
		xmlgifwriter.println("<GifFindingsSummary>");

		output.XslStyleSheets.GifCustomizedXsl(); //TODO, has not yet been created
		
	}

	public static void closeXmlGifOutput() {
		xmlgifwriter.println("</GifFindingsSummary>");
		xmlgifwriter.close();
		
	}

}
