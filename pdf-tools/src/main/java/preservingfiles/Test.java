package preservingfiles;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.pdf.PdfReader;

public class Test {

	static String examinedFolder;
	String pathwriter;
	static PrintWriter xmlSimpleWriter;

	public static void main(String args[]) throws IOException {

		changecolor();

		JOptionPane.showMessageDialog(null, "Please choose the folder with files to analyse.", "File Analysation", JOptionPane.QUESTION_MESSAGE);
		examinedFolder = utilities.BrowserDialogs.chooseFolder();

		if (examinedFolder != null) {

			xmlSimpleWriter = new PrintWriter(new FileWriter(examinedFolder + "\\FileAnalysis.xml"));

			String xsltLocation = (examinedFolder + "\\Analyst.xsl");
			output.XslStyleSheets.fileAnalysis(xsltLocation);
			startingXmlOutput();

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
			ArrayList<ZbwFile> findings = new ArrayList<ZbwFile>();

			// TODO: neue arraylist aus jhovefileobjekten

			xmlSimpleWriter.println("<FileAnalysisSummary>");

			// To handle one file after the other
			for (int i = 0; i < files.size(); i++) {

				ZbwFile testfile = new ZbwFile();
				testfile.fileName = testfile.getName(files.get(i).toString());

				testfile.path = files.get(i).toString();
				testfile.zbwFile = testfile.toFile(testfile.path);
				testfile.mimetype = testfile.getFileMimeType(testfile.zbwFile);
				testfile.checksumMD5 = testfile.getMD5Checksum(testfile.zbwFile);
				testfile.size = testfile.getSizeinKB(testfile.toFile(testfile.path));
				testfile.mimetype = testfile.getFileMimeType(testfile.toFile(testfile.path));
				testfile.fileExtension = testfile.getFileExtension(testfile.path);

				findings.add(testfile);
			}

			for (int i = 0; i < findings.size(); i++) {
				xmlSimpleWriter.println("<File>");

				xmlSimpleWriter.println("<FileName><![CDATA[" + findings.get(i).fileName + "]]></FileName>");
				xmlSimpleWriter.println("<MD5Checksum>" + findings.get(i).checksumMD5 + "</MD5Checksum>");
				xmlSimpleWriter.println("<FileSizeKB>" + findings.get(i).size + "</FileSizeKB>");
				xmlSimpleWriter.println("<Mimetype>" + findings.get(i).mimetype + "</Mimetype>");
				xmlSimpleWriter.println("<FileExtension>" + findings.get(i).fileExtension + "</FileExtension>");

				if (findings.get(i).mimetype.equals("application/pdf")) {
					ZbwFilePdf testfilePdf = new ZbwFilePdf();
					testfilePdf.pdfFile = ZbwFilePdf.toPDDocument(findings.get(i).zbwFile);
					testfilePdf.isEncrypted = ZbwFilePdf.isEncrypted(testfilePdf.pdfFile);
					testfilePdf.isPdfA = ZbwFilePdf.isPdfA(findings.get(i).zbwFile.toString());
					xmlSimpleWriter.println("<PdfEncryption>" + testfilePdf.isEncrypted + "</PdfEncryption>");
					xmlSimpleWriter.println("<PdfA>" + testfilePdf.isPdfA + "</PdfA>");
				} else {
					xmlSimpleWriter.println("<PdfEncryption>" + "No Pdf File" + "</PdfEncryption>");
					xmlSimpleWriter.println("<PdfA>" + "No Pdf File" + "</PdfA>");

				}

				xmlSimpleWriter.println("</File>");
			}

			xmlSimpleWriter.println("</FileAnalysisSummary>");
			xmlSimpleWriter.close();
		}
	}

	private static void startingXmlOutput() {
		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		xmlSimpleWriter.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		xmlSimpleWriter.println("<?xml-stylesheet type=\"text/xsl\" href=\"Analyst.xsl\"?>");
	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}

}
