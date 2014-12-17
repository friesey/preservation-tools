package output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import externalToolAnalysis.JhoveValidator;
import filetools.gif.GifChecker;

public class XslStyleSheets {
	public static void JhoveCustomizedXsl() throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter(JhoveValidator.folder + "//" + "JhoveCustomized.xsl"));
		xslStyle.println("<?xml version=\"1.0\"?>");
		xslStyle.println("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">");
		xslStyle.println("<xsl:template match=\"JhoveFindingsSummary\">");
		xslStyle.println("<html>");
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
		xslStyle.println("<body>");

		xslStyle.println("<h1>PDF files examined by JHOVE</h1>");

		xslStyle.println("<xsl:value-of select=\"FileName\"/><br/>");
		xslStyle.println("<br/>");
		xslStyle.println("<h4>JHOVE Examination per File</h4>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr>");
		xslStyle.println("<th>FileName</th>");
		xslStyle.println("<th>Status</th>");
		xslStyle.println("<th>JhoveMessages</th>");
		xslStyle.println("<th>Message1</th>");
		xslStyle.println("<th>Message2</th>");
		xslStyle.println("<th>Message3</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"GifFile\">");
		xslStyle.println("<tr>");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Status\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"JhoveMessages\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message1\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message2\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message3\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");
		
		xslStyle.println("</body>");
		xslStyle.println("</html>");
		xslStyle.println("</xsl:template>");
		xslStyle.println("</xsl:stylesheet>");
		xslStyle.close();
	}

	public static void GifJhoveCustomizedXsl()  throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter((GifChecker.giffolder + "//" + "GifJhoveCustomized.xsl")));
		xslStyle.println("<?xml version=\"1.0\"?>");
		xslStyle.println("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">");
		xslStyle.println("<xsl:template match=\"JhoveFindingsSummary\">");
		xslStyle.println("<html>");
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
		xslStyle.println("<body>");

		xslStyle.println("<h1>Gif files examined by JHOVE</h1>");

		xslStyle.println("<xsl:value-of select=\"FileName\"/><br/>");
		xslStyle.println("<br/>");
		xslStyle.println("<h4>JHOVE Examination per File</h4>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr>");
		xslStyle.println("<th>FileName</th>");
		xslStyle.println("<th>Status</th>");
		xslStyle.println("<th>JhoveMessages</th>");
		xslStyle.println("<th>Message1</th>");
		xslStyle.println("<th>Message2</th>");
		xslStyle.println("<th>Message3</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"GifFile\">");
		xslStyle.println("<tr>");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Status\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"JhoveMessages\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message1\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message2\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message3\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");
		
		xslStyle.println("</body>");
		xslStyle.println("</html>");
		xslStyle.println("</xsl:template>");
		xslStyle.println("</xsl:stylesheet>");
		xslStyle.close();
	}
	
	


}