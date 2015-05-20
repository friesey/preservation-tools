package output;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class XslStyleSheets {

	public static void fileAnalysis(String xsltLocation) throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter(xsltLocation));

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
		xslStyle.println("tr.captionkhaki {background-color: #BDB76B}");
		xslStyle.println("tr.captionolive {background-color: #808000}");
		xslStyle.println("tr.captionwheat {background-color: #F5DEB3}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");

		xslStyle.println("<h2>Overview Analyzed Files</h2>");

		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captionolive\">");
		xslStyle.println("<th> FileName</th> ");
	//	xslStyle.println("<th> MD5Checksum</th>");
		xslStyle.println("<th> FileSizeKB</th>");
		xslStyle.println("<th> Mimetype</th>");
		xslStyle.println("<th> FileExtension</th>");
		xslStyle.println("<th> PdfEncryption</th>");
		xslStyle.println("<th> PdfA</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"FileAnalysisSummary/File\">");
		xslStyle.println("<tr class=\"captionolive\">");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		// xslStyle.println("<td><xsl:value-of select=\"MD5Checksum\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"FileSizeKB\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Mimetype\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"FileExtension\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"PdfEncryption\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"PdfA\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");
		xslStyle.println("</body>");
		xslStyle.println("</html>");
		xslStyle.println("</xsl:template>");
		xslStyle.println("</xsl:stylesheet>");

		xslStyle.close();

	}

	public static void pdfAnalysis(String xsltLocation) throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter(xsltLocation));
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
		xslStyle.println("tr.captionkhaki {background-color: #BDB76B}");
		xslStyle.println("tr.captionolive {background-color: #808000}");
		xslStyle.println("tr.captionwheat {background-color: #F5DEB3}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");

		xslStyle.println("<h2>Pdf Analysis</h2>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th>ID</th>");
		xslStyle.println("<th>Name</th>");
		xslStyle.println("<th>MD5</th>");
		xslStyle.println("<th>Encrypted</th>");
		xslStyle.println("<th>PdfA</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"PdfAnalysis/Pdf\">");

		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<td><xsl:value-of select=\"ObjectId\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"MD5Checksum\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"PdfEncrypted\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"PdfA\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("</body>");
		xslStyle.println("</html>");
		xslStyle.println("</xsl:template>");
		xslStyle.println("</xsl:stylesheet>");

		xslStyle.close();

	}

	public static void PdfBoxSummaryCustomizedXsl(String xsltLocation) throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter(xsltLocation));

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
		xslStyle.println("tr.captionkhaki {background-color: #BDB76B}");
		xslStyle.println("tr.captionolive {background-color: #808000}");
		xslStyle.println("tr.captionwheat {background-color: #F5DEB3}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");

		xslStyle.println("<h2>PDF/A Validation with PDFBox Short Summary</h2>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th>FileName</th>");
		xslStyle.println("<th>Creation Year</th>");
		xslStyle.println("<th>Creation Software</th>");
		xslStyle.println("<th>Status</th>");
		xslStyle.println("<th>ErrorsCount</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"PdfBoxValidationSummary/PdfAFile\">");
		xslStyle.println("<xsl:sort select=\"Status\" />");
		xslStyle.println("<xsl:if test=\"Status[contains(text(),'Valid')]\">");
		xslStyle.println("<tr class=\"captiongreen\">");
		xslStyle.println("<td><i><xsl:value-of select=\"FileName\"/></i></td>");
		xslStyle.println("<td><xsl:value-of select=\"CreationYear\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"CreationSoftware\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Status\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ErrorsCount\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:if>	");
		xslStyle.println("<xsl:if test=\"Status[contains(text(),'Invalid')]\">");
		xslStyle.println("<tr class=\"captionred\">");
		xslStyle.println("<td><i><xsl:value-of select=\"FileName\"/></i></td>");
		xslStyle.println("<td><xsl:value-of select=\"CreationYear\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"CreationSoftware\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Status\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ErrorsCount\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:if>		");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("<h2>Examined PDF/A Files</h2>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th>Examined PDF/A</th>");
		xslStyle.println("<th>Valid</th>");
		xslStyle.println("<th>Invalid</th>");
		xslStyle.println("<th>Broken</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"PdfBoxValidationSummary/Summary\">");

		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<td><xsl:value-of select=\"ExaminedPdfAFiles\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ValidPdfAFiles\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"InvalidPdfAFiles\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"CausedErrorPdfAFiles\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("</body>");
		xslStyle.println("</html>");
		xslStyle.println("</xsl:template>");
		xslStyle.println("</xsl:stylesheet>");

		xslStyle.close();

	}

	public static void PdfBoxCustomizedXsl(String xsltLocation) throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter(xsltLocation));

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
		xslStyle.println("tr.captionkhaki {background-color: #BDB76B}");
		xslStyle.println("tr.captionolive {background-color: #808000}");
		xslStyle.println("tr.captionwheat {background-color: #F5DEB3}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");

		xslStyle.println("<h2>Overview on invalid PDF/A Files</h2>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th>FileName</th>");
		xslStyle.println("<th>Creation Year</th>");
		xslStyle.println("<th>Creation Software</th>");
		xslStyle.println("<th>Syntax Errors</th>");
		xslStyle.println("<th>Graphic Errors</th>");
		xslStyle.println("<th>Font Errors</th>");
		xslStyle.println("<th>Transparency Errors</th>");
		xslStyle.println("<th>Annotation Errors</th>");
		xslStyle.println("<th>Action Errors</th>");
		xslStyle.println("<th>Metadata Errors</th>");
		xslStyle.println("<th>Runtime Errors</th>");

		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"PdfBoxValidation/PdfAFile\">");
		xslStyle.println("<tr class=\"captionred\">");
		xslStyle.println("<td><i><xsl:value-of select=\"FileName\"/></i></td>");
		xslStyle.println("<td><xsl:value-of select=\"CreationYear\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"CreationSoftware\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"SyntaxErrors\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"GraphicErrors\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"FontErrors\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"TransparencyErrors\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"AnnotationErrors\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ActionErrors\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"MetadataErrors\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"RuntimeErrors\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("</body>");
		xslStyle.println("</html>");
		xslStyle.println("</xsl:template>");
		xslStyle.println("</xsl:stylesheet>");

		xslStyle.close();

	}

	public static void TextSucheCustomizedXsl(String xsltLocation) throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter(xsltLocation));

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
		xslStyle.println("tr.captionkhaki {background-color: #BDB76B}");
		xslStyle.println("tr.captionolive {background-color: #808000}");
		xslStyle.println("tr.captionwheat {background-color: #F5DEB3}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");

		xslStyle.println("<h2>Textsuche</h2>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th>Durchsuchter Ordner oder Datei</th>");
		xslStyle.println("<th>Art der Suche</th>");
		xslStyle.println("<th>Anzahl Dateien im Suchordner</th>");
		xslStyle.println("<th>Gesuchter Text</th>");
		xslStyle.println("<th>Text in durchsuchtem Ordner/Datei gefunden</th>");

		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"Textsuche\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<td><xsl:value-of select=\"Durchsucht\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Art\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"AnzahlDateien\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"GesuchterText\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"OccuranceSearchedTest\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("<h2>Durchsuchte Dateien</h2>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th>Dateiname</th>");
		xslStyle.println("<th>Gesuchten Text gefunden</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"Textsuche/Datei\">");

		xslStyle.println("<xsl:sort select=\"Suchergebnis\" />");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<td><xsl:value-of select=\"Dateiname\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Suchergebnis\"/></td>");
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
		xslStyle.println("tr.captionkhaki {background-color: #BDB76B}");
		xslStyle.println("tr.captionolive {background-color: #808000}");
		xslStyle.println("tr.captionwheat {background-color: #F5DEB3}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");

		xslStyle.println("<h4>Newfound Tiff Tags</h4>");
		xslStyle.println("<xsl:value-of select=\"DifferentTiffTagsInSample\"/>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th> TiffTag</th>");
		xslStyle.println("<th> Content</th>");
		xslStyle.println("<th> Dec</th>");
		xslStyle.println("<th> Hex</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/TiffFile/TiffTags/NewTag\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<td><xsl:value-of select=\"Tag\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Content\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Dec\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Hex\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");
		
		xslStyle.println("<h2>Examination of Tiff Tags</h2>");
		xslStyle.println("<h3>Summary of Tiff Tags</h3>");
		xslStyle.println("<xsl:value-of select=\"DifferentTiffTagsInSample\"/>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th> TiffTag</th>");
		xslStyle.println("<th> Occurance</th>");
		xslStyle.println("<th> Description</th>");
		xslStyle.println("<th> SourceOfTag</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/AnalysisSummary/DifferentTiffTags\">");
		xslStyle.println("<xsl:sort select=\"SourceOfTag\" />");
		// TODO: sorts in alphabetic order
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<td><xsl:value-of select=\"TiffTag\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Occurance\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"Description\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"SourceOfTag\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("<h3>Tiff Statistic</h3>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiondred\">");
		xslStyle.println("<th>All Tiffs in Examined Folder</th>");
		xslStyle.println("<th>Problematic Tiffs that were not examined</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/AnalysisSummary\">");
		xslStyle.println("<tr class=\"captiondred\">");
		xslStyle.println("<td><xsl:value-of select=\"ExaminedTiffs\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ProblematicTiffs\"/></td>");
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
		xslStyle.println("<tr class=\"captionkhaki\">");
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
		xslStyle.println("<tr class=\"captionkhaki\">");
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
		xslStyle.println("<tr class=\"captionwheat\">");
		xslStyle.println("<th> FileName</th> ");
		xslStyle.println("<th> ExifIfd</th>");
		xslStyle.println("<th> ExtraSamples</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/TiffFile/TiffTags\">");
		xslStyle.println("<tr class=\"captionwheat\">");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ExifIfd\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"ExtraSamples\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("<h3>Other known Tiff Tags</h3>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captionolive\">");
		xslStyle.println("<th> FileName</th> ");
		xslStyle.println("<th> T4Option</th>");
		xslStyle.println("<th> T6Option</th>");
		xslStyle.println("<th> PageNumber</th>");
		xslStyle.println("<th> FillOrder</th>");
		xslStyle.println("<th> Orientation</th>");
		xslStyle.println("<th> PlanarConfiguration</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"TiffTagAnalysis/TiffFile/TiffTags\">");
		xslStyle.println("<tr class=\"captionolive\">");
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

	public static void PdfMetadataCustomizedXsl(String xsltLocation) throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter(xsltLocation));

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
		xslStyle.println("tr.captionkhaki {background-color: #BDB76B}");
		xslStyle.println("tr.captionolive {background-color: #808000}");
		xslStyle.println("tr.captionwheat {background-color: #F5DEB3}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");

		xslStyle.println("<h2>Pdf Metadata Extraction</h2>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<th>File Name</th> ");
		/* xslStyle.println("<th>No. of Metadata Entries</th> "); */
		xslStyle.println("<th>Year of Creation</th> ");
		xslStyle.println("<th>Pdf Version</th> ");
		/*
		 * xslStyle.println("<th>Creation Date</th> ");
		 * xslStyle.println("<th>Modification Date</th> ");
		 */
		/*
		 * xslStyle.println("<th>Title</th> ");
		 * xslStyle.println("<th>Author</th> ");
		 */
		xslStyle.println("<th>Software</th> ");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"PdfMetadata/File\">");
		xslStyle.println("<xsl:sort select=\"CreationYear\" />");
		xslStyle.println("<tr class=\"captiontan\">");
		xslStyle.println("<td><xsl:value-of select=\"FileName\"/></td>");
		/*
		 * xslStyle.println("<td><xsl:value-of select=\"MetadataEntries\"/></td>"
		 * );
		 */
		xslStyle.println("<td><xsl:value-of select=\"CreationYear\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"PdfVersion\"/></td>");
		/*
		 * xslStyle.println("<td><xsl:value-of select=\"CreationDate\"/></td>");
		 * xslStyle
		 * .println("<td><xsl:value-of select=\"ModificationDate\"/></td>");
		 */
		/*
		 * xslStyle.println("<td><xsl:value-of select=\"Title\"/></td>");
		 * xslStyle.println("<td><xsl:value-of select=\"Author\"/></td>");
		 */
		xslStyle.println("<td><xsl:value-of select=\"Producer\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("</body>");
		xslStyle.println("</html>");
		xslStyle.println("</xsl:template>");
		xslStyle.println("</xsl:stylesheet>");

		xslStyle.close();

	}

	public static void PdfTwinTestXsl(String xsltLocation) throws IOException {

		PrintWriter xslStyle = new PrintWriter(new FileWriter(xsltLocation));

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
		xslStyle.println("tr.captionkhaki {background-color: #BDB76B}");
		xslStyle.println("tr.captionolive {background-color: #808000}");
		xslStyle.println("tr.captionwheat {background-color: #F5DEB3}");
		xslStyle.println("</style>");
		xslStyle.println("</head>");
		xslStyle.println("<body>");

		xslStyle.println("<h2>Compared PDF Files</h2>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captionkhaki\">");
		xslStyle.println("<th>Original File</th>");
		xslStyle.println("<th>Number of Lines Original</th>");
		xslStyle.println("<th>Migrated File</th>");
		xslStyle.println("<th>Number of Lines Original Migration</th>");
		xslStyle.println("<th>PDF Twins</th>");
		xslStyle.println("<th>Different Lines</th>");
		xslStyle.println("<th>Levenshtein Distance Full PDF</th>");
		xslStyle.println("<th>Line Irregulaties (lines shifting etc.)</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"PdfTwinTest/ComparedItem\">");
		xslStyle.println("<tr class=\"captionwheat\">");
		xslStyle.println("<td><xsl:value-of select=\"FileOrg\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"LinesLengthOrg\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"FileMig\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"LinesLengthMig\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"PdfTwins\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"DifferentLines\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"LevenshteinPdf\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"LineIrregularities\"/></td>");
		xslStyle.println("</tr>");
		xslStyle.println("</xsl:for-each>");
		xslStyle.println("</table>");

		xslStyle.println("<h2>Found differences in PDF Files</h2>");
		xslStyle.println("<table border =\"1\">");
		xslStyle.println("<tr class=\"captionkhaki\">");
		xslStyle.println("<th>Difference in Line Number</th>");
		xslStyle.println("<th>Original Line</th>");
		xslStyle.println("<th>Migrated Line</th>");
		xslStyle.println("<th>Different Word (Original)</th>"); // TODO: could
																// be more than
																// one
		xslStyle.println("<th>Different Word (Migration)</th>");
		xslStyle.println("<th>Levenshtein Distance</th>");
		xslStyle.println("</tr>");
		xslStyle.println("<xsl:for-each select=\"PdfTwinTest/Details\">");
		xslStyle.println("<tr class=\"captionwheat\">");
		xslStyle.println("<td><xsl:value-of select=\"DifferentLineNumber\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"OriginalLine\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"MigrationLine\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"DifferentWordOrg\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"DifferentWordMig\"/></td>");
		xslStyle.println("<td><xsl:value-of select=\"LevenshteinDistance\"/></td>");
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
