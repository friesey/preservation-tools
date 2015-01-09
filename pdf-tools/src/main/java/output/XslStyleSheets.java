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

	public static void TiffTagAnalysisCustomizedXsl() throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter(filetools.tiff.TiffFileAnalysis.examinedFolder + "//" + "TiffTagStyle.xsl"));

		xslStyle.println("<?xml version=\"1.0\"?>");
		xslStyle.println("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">");
		xslStyle.println("<xsl:template match=\"/\">");
		xslStyle.println("<html>");
		xslStyle.println("<head>");
		xslStyle.println("<style>");
		xslStyle.println("tr.captiondred {background-color: #FF0000}");
		xslStyle.println("tr.captionred {background-color: #CD5C5C}");
		xslStyle.println("tr.captiongreen {background-color: #006400}");
		xslStyle.println("tr.captiontan {background-color: #FFDEAD}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");
		xslStyle.println("<h2>Examination of Tiff Tags</h2>");
		xslStyle.println("<h3>Summary of Tiff Tags</h3>");
		xslStyle.println("<xsl:value-of select=\"DifferentTiffTagsInSample\"/>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th> TiffTag</th>");
		xslStyle.println("<th> Occurance</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/AnalysisSummary/DifferentTiffTags\">");
		xslStyle.println("<xsl:sort select=\"Occurance\" />");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<td><xsl:value-of select=\"TiffTag\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Occurance\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");
		xslStyle.println("<h3>Examined Files and Quantity of Tiff Tags</h3>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th>File Path</th>");
		xslStyle.println("<th>Amount of Tiff Tags</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/TiffFile\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<td><xsl:value-of select=\"FilePath\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"TiffTagsCount\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("<h3>Examination Failed for following Tiff Files</h3>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiondred\">");
		xslStyle.println("<th> FilePath</th> ");
		xslStyle.println("<th> ErrorMessage</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:if test=\"TiffTagAnalysis/TiffFile/ErrorMessage\">");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/TiffFile\">");
		xslStyle.println("<tr class=\"captiondred\">");
		xslStyle.println("<td><xsl:value-of select=\"FilePath\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ErrorMessage\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</xsl:if>");
		xslStyle.println("</table>");

		xslStyle.println("<h3>12 Mandatory Tiff Tags in Baseline Tiff</h3>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiongreen\">");
		xslStyle.println("<th> FileName</th> ");
		xslStyle.println(" <!-- <th> SubFileType</th>   --> 	");
		xslStyle.println("<th> ImageWidth</th>");
		xslStyle.println("<th> ImageLength</th>");
		xslStyle.println("<th> BitsPerSample</th>");
		xslStyle.println("<th> Compression</th>");
		xslStyle.println("<th> Photometric</th>");
		xslStyle.println("<th> StripOffSets</th>");
		xslStyle.println("<th> SamplesPerPixel</th>");
		xslStyle.println("<th> RowsPerStrip</th>");
		xslStyle.println("<th> StripByteCounts</th>");
		xslStyle.println("<th> XResolution</th>");
		xslStyle.println("<th> YResolution</th>");
		xslStyle.println("<th> ResolutionUnit</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/TiffFile/TiffTags\">");
		xslStyle.println("<tr class=\"captiongreen\">");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		xslStyle.println(" <!--  <td><xsl:value-of select=\"SubFileType\"/></td> --> 		");
		xslStyle.println("<td><xsl:value-of select=\"ImageWidth\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ImageLength\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"BitsPerSample\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Compression\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Photometric\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"StripOffSets\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"SamplesPerPixel\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"RowsPerStrip\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"StripByteCounts\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"XResolution\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"YResolution\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ResolutionUnit\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("<h3>Nice to have Tiff Tags optional in Baseline Tiff</h3>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captionred\">");
		xslStyle.println("<th> FileName</th> ");
		xslStyle.println("<th> MinSampleValue</th>  ");
		xslStyle.println("<th> MaxSampleValue</th>  ");
		xslStyle.println("<th> GrayResponseUnit</th>  ");
		xslStyle.println("<th> GrayResponseCurve</th>  ");
		xslStyle.println("<th> Software</th>  ");
		xslStyle.println("<th> DateTime</th>  ");
		xslStyle.println("<th> Artist</th>  ");
		xslStyle.println("<th> ColorMap</th>  ");
		xslStyle.println("<th> Copyright</th>   ");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/TiffFile/TiffTags\">");
		xslStyle.println("<tr class=\"captionred\">");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"MinSampleValue\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"MaxSampleValue\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"GrayResponseUnit\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"GrayResponseCurve\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Software\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"DateTime\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Artist\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ColorMap\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Copyright\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");
		xslStyle.println("<h3>No Tags for Baseline Tiff but nice to have for Digital Preservation</h3>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiondred\">");
		xslStyle.println("<th> FileName</th> ");
		xslStyle.println("<th> ExifIfd</th>");
		xslStyle.println("<th> ExtraSamples</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/TiffFile/TiffTags\">");
		xslStyle.println("<tr class=\"captiondred\">");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ExifIfd\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ExtraSamples\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("<h3>Other known Tiff Tags</h3>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiondred\">");
		xslStyle.println("<th> FileName</th> ");
		xslStyle.println("<th> T4Option</th>");
		xslStyle.println("<th> T6Option</th>");
		xslStyle.println("<th> PageNumber</th>    ");
		xslStyle.println("<th> FillOrder</th>");
		xslStyle.println("<th> Orientation</th>");
		xslStyle.println("<th> PlanarConfiguration</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/TiffFile/TiffTags\">");
		xslStyle.println("<tr class=\"captiondred\">");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"T4Option\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"T6Option\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"PageNumber\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"FillOrder\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Orientation\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"PlanarConfiguration\"/></td>");
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