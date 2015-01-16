package filetools.pdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.itextpdf.text.pdf.PdfReader;

public class PdftoXmlConversion {

	public static String examinedFolder;
	static String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"PdfToXmlConversionStyleSheet.xsl\"?>";
	static int begin;

	public static void main(String args[]) throws IOException {
		try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();
			if (examinedFolder != null) {

				output.XslStyleSheets.PdfToXmlConversionStyleSheet(); // using
																		// the
																		// same
																		// XSLT
																		// Stylesheet
																		// for
																		// all
																		// of
																		// the
																		// XML
																		// Files

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
				for (int i = 0; i < files.size(); i++) {
					String mimetype = filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
					if (mimetype.equals("application/pdf")) {
						extractPdfContenttoXmlFile(files.get(i));
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void extractPdfContenttoXmlFile(File file) throws IOException {
		// TODO Auto-generated method stub

		String filename = file.getName().replace(".pdf", "");

		PrintWriter pdftoXmlWriter = new PrintWriter(new FileWriter(examinedFolder + "//" + filename + ".xml"));
		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		pdftoXmlWriter.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		pdftoXmlWriter.println(xsltStyleSheet);

		pdftoXmlWriter.println("<DataSeal>");

		// PdfReader reader = new PdfReader(file.toString());

		String[] lines = PdfAnalysis.extractsPdfLines(file.toString());

		for (int i = 0; i < lines.length; i++) {

			if (lines[i].contains("Repository:")) {
				String[] parts = lines[i].split(":");
				pdftoXmlWriter.println("<Repository>" + parts[1] + "</Repository>");

				//
			}
			if (lines[i].contains("Seal Acquiry Date:")) {
				String[] parts = lines[i].split(":");
				pdftoXmlWriter.println("<SealAcquiryDate>" + parts[1] + "</SealAcquiryDate>");
			}

			if (lines[i].equals("Assessment")) {
				begin = i;
			}

			if ((i > begin) && (!lines[i].equals(" ")) && (!lines[i].contains("info@datasealofapproval"))) {

				// do not copy empty lines,
				// do not copy lines in the head or food and
				// TODO do not copy page numbers
				// do not copy the stuff before the chapter 0

				pdftoXmlWriter.println(lines[i]);
			}

		}

		for (int i = 0; ((i > begin) && (i < lines.length)); i++) {
			if ((!lines[i].equals(" ")) && (!lines[i].contains("info@datasealofapproval"))) {

				System.out.println(file.getName() + begin);
				pdftoXmlWriter.println(lines[i]);

			}
		}

		pdftoXmlWriter.println("</DataSeal>");
		pdftoXmlWriter.close();

	}
}
