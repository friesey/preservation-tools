package output;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import externalToolAnalysis.RunJhoveApp;

public class XslStyleSheetJhoveCustomized {
	public static void JhoveCustomizedXsl() throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter((RunJhoveApp.folder + "//" + "JhoveCustomized.xsl")));
		xslStyle.println("<?xml version=\"1.0\"?>");
		xslStyle.println("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">");
		xslStyle.println("<xsl:template match=\"JhoveFindingsSummary\">");
		xslStyle.println("<html>");
		xslStyle.println("<head>");
		xslStyle.println("<style>");
		xslStyle.println("body {font-family: Verdana, Geneva, sans-serif; font-size: 10pt; }");
		xslStyle.println("table {font-family: Verdana, Geneva, sans-serif; font-size: 10pt; }");
		xslStyle.println("h1 {font-family: Verdana, Geneva, sans-serif; font-weight:bold; font-size: 18pt; color: #000000; }");
		xslStyle.println("h2 {font-family: Verdana, Geneva, sans-serif; font-weight:bold; font-size: 14pt; color: #000000; }");
		xslStyle.println("tr {background-color: #f0f0f0;}");
		xslStyle.println("tr.caption {background-color: #eeafaf; font-weight:bold}");
		xslStyle.println("tr.captionm {background-color: #f8dfdf}");
		xslStyle.println("tr.captionio {background-color: #afeeaf; font-weight:bold}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");

		xslStyle.println("<h1>PDF files examined by JHOVE</h1>");



		xslStyle.println("<h2>Well-formed and valid</h2>");
		


		xslStyle.println("<table border =\"1\">");

		xslStyle.println("</table>");


	
		xslStyle.println("</body>");
		xslStyle.println("</html>");
		xslStyle.println("</xsl:template>");
		xslStyle.println("</xsl:stylesheet>");
		xslStyle.close();
	}
}