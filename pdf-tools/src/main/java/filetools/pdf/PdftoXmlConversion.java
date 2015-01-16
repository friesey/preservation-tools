package filetools.pdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PdftoXmlConversion {

	public static String examinedFolder;
	static String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"PdfToXmlConversionStyleSheet.xsl\"?>";

	public static void main(String args[]) throws IOException {
		try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();
			if (examinedFolder != null) {
				
				output.XslStyleSheets.PdfToXmlConversionStyleSheet(); //using the same XSLT Stylesheet for all of the XML Files
				
				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
				for (int i = 0; i < files.size(); i++) {
					String mimetype = filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
					if (mimetype.equals("application/pdf")) {
						System.out.println(files.get(i).toString());
						extractPdfContenttoXmlFile(files.get(i));
					}
				}
			}
		} catch (Exception e) {
			System.out.println (e);
		}
	}

	public static void extractPdfContenttoXmlFile(File file) throws IOException {
		// TODO Auto-generated method stub				
		
		String filename = file.getName().replace(".pdf", "");		
		System.out.println (filename);
		PrintWriter pdftoXmlWriter = new PrintWriter(new FileWriter(examinedFolder + "//" + filename + ".xml"));
		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";	
		pdftoXmlWriter.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		pdftoXmlWriter.println(xsltStyleSheet);
		
		pdftoXmlWriter.println("<DataSeal>");
		
		pdftoXmlWriter.println("</DataSeal>");
		pdftoXmlWriter.close();
		
		
	}
}
