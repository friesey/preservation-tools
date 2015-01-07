package output;

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
		xslStyle.println("<xsl:template match=\"/\">");
		xslStyle.println("<html>");
		xslStyle.println("<head>");
		xslStyle.println("<style>");
		xslStyle.println("tr.captiondred {background-color: #FF0000}");
		xslStyle.println("tr.captionred {background-color: #CD5C5C}");
		xslStyle.println("tr.captiongreen {background-color: #006400}");
		xslStyle.println("	tr.captiontan {background-color: #FFDEAD}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");

		xslStyle.println("<body>");

		// xslStyle.println("<h1>PDF files examined by JHOVE</h1>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");

		// xslStyle.println("<h4>JHOVE Examination per File</h4>");

		xslStyle.println("<th>FileName</th>");
		xslStyle.println("<th>Status</th>");
		xslStyle.println("<th>JhoveMessages</th>");
		xslStyle.println("<th>Message1</th>");
		xslStyle.println("<th>Message2</th>");
		xslStyle.println("<th>Message3</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"JhoveFindingsSummary/PdfFile\">");
		xslStyle.println("<xsl:sort select=\"Status\" />");
		xslStyle.println("<xsl:if test=\"Status[contains(text(),'Not')]\">");
		xslStyle.println("<tr class=\"captiondred\">");
		xslStyle.println("<td><i><xsl:value-of select=\"FileName\"/></i></td>");
		xslStyle.println("<td><xsl:value-of select=\"Status\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"JhoveMessages\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message1\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message2\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message3\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:if>	");
		xslStyle.println("<xsl:if test=\"Status[contains(text(),'not')]\">");
		xslStyle.println("<tr class=\"captionred\">");
		xslStyle.println("<td><i><xsl:value-of select=\"FileName\" /></i></td>");
		xslStyle.println("<td><xsl:value-of select=\"Status\" /></td>");
		xslStyle.println("<td><xsl:value-of select=\"JhoveMessages\" /></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message1\" /></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message2\" /></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message3\" /></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:if>		");
		xslStyle.println("<xsl:if test=\"Status[contains(text(),'and')]\">");
		xslStyle.println("<tr class=\"captiongreen\">");
		xslStyle.println("<td><i><xsl:value-of select=\"FileName\" /></i></td>");
		xslStyle.println("<td><xsl:value-of select=\"Status\" /></td>");
		xslStyle.println("<td><xsl:value-of select=\"JhoveMessages\" /></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message1\" /></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message2\" /></td>");
		xslStyle.println("<td><xsl:value-of select=\"Message3\" /></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:if>	");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");
		xslStyle.println("<table border=\"1\">");
		xslStyle.println("<tr class=\"captiontan\">	");
		xslStyle.println("<th>MessageText</th>");
		xslStyle.println("<th>Occurance</th>");
		xslStyle.println("</tr> ");
		
		xslStyle.println("<xsl:for-each select=\"JhoveFindingsSummary/SampleSummary/JhoveMessage\">  ");
		xslStyle.println("<tr class=\"captiontan\">	");
		xslStyle.println("<xsl:value-of select=\"JhoveMessage\" />");
		xslStyle.println("<td><xsl:value-of select=\"MessageText\" /></td>");
		xslStyle.println("<td><xsl:value-of select=\"Occurance\" />	</td>");

		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");	
		xslStyle.println("</table>");
		xslStyle.println("</body>");
		xslStyle.println("</html>");
		xslStyle.println("</xsl:template>");
		xslStyle.println("</xsl:stylesheet>");
		xslStyle.close();
	}

	public static void GifJhoveCustomizedXsl() throws IOException {

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