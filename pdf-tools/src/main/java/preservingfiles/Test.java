package preservingfiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.itextpdf.text.pdf.PdfEncryptor;
import com.itextpdf.text.pdf.PdfReader;

import filetools.pdf.PdfAnalysis;
import filetools.tiff.TiffTagZbw;

public class Test {

	static String examinedFolder;
	String pathwriter;
	static PrintWriter xmlSimpleWriter;

	static int MAXIMUM_SIZE_PDF = 15500;

	// TODO: If a PDF is bigger than a certain size, do not do certain tests,
	// that will fail the Heap Space. The same is true for the number of pages.
	// It is too difficult to determine exactly where the borders are, that is
	// why I have chosen something arbitrary which puts the program on the save
	// side.

	public static void main(String args[]) throws IOException {

		changecolor();

		JOptionPane.showMessageDialog(null, "Please choose the folder with files to analyse.", "File Analysis", JOptionPane.QUESTION_MESSAGE);
		examinedFolder = utilities.BrowserDialogs.chooseFolder();

		if (examinedFolder != null) {

			JFrame f = new JFrame();
			JButton but = new JButton("... Program is running ... ");
			f.add(but, BorderLayout.PAGE_END);
			f.pack();
			f.setVisible(true);

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

				if (findings.get(i).mimetype != null) {
					if (findings.get(i).mimetype.equals("application/pdf")) {
						ZbwFilePdf testfilePdf = new ZbwFilePdf();
						System.out.println(findings.get(i).fileName);
						System.out.println(findings.get(i).size);
						if (findings.get(i).size < MAXIMUM_SIZE_PDF) {
							testfilePdf.pdfFile = ZbwFilePdf.toPDDocument(findings.get(i).zbwFile);
							if (testfilePdf.pdfFile != null) {
								testfilePdf.isEncrypted = ZbwFilePdf.isEncrypted(testfilePdf.pdfFile);
								xmlSimpleWriter.println("<PdfEncryption>" + testfilePdf.isEncrypted + "</PdfEncryption>");

								if (testfilePdf.isEncrypted == true) {
								try {

									PdfReader reader = new PdfReader(findings.get(i).path);

									int permissions = reader.getPermissions();

									// TODO
									xmlSimpleWriter.println("<EncryptionDetails>");
									
									if (!PdfEncryptor.isAssemblyAllowed(permissions)) {
										xmlSimpleWriter.println("<isAssemblyAllowed>" + false + "</isAssemblyAllowed>");
									}
									
									else {
										xmlSimpleWriter.println("<isAssemblyAllowed>" + true + "</isAssemblyAllowed>");
									}

									if (!PdfEncryptor.isCopyAllowed(permissions)) {
										xmlSimpleWriter.println("<isCopyAllowed>" + false + "</isCopyAllowed>");
									}

									else {
										xmlSimpleWriter.println("<isCopyAllowed>" + true + "</isCopyAllowed>");
									}
									if (!PdfEncryptor.isDegradedPrintingAllowed(permissions)) {
										xmlSimpleWriter.println("<isDegradedPrintingAllowed>" + false + "</isDegradedPrintingAllowed>");
									}
									else {
										xmlSimpleWriter.println("<isDegradedPrintingAllowed>" + true + "</isDegradedPrintingAllowed>");
									}

									if (!PdfEncryptor.isFillInAllowed(permissions)) {
										xmlSimpleWriter.println("<isFillInAllowed>" + false + "</isFillInAllowed>");
									}
									
									else {
										xmlSimpleWriter.println("<isFillInAllowed>" + true + "</isFillInAllowed>");
									}

									if (!PdfEncryptor.isModifyAnnotationsAllowed(permissions)) {
										xmlSimpleWriter.println("<isModifyAnnotationsAllowed>" + false + "</isModifyAnnotationsAllowed>");
									}
									else {
										xmlSimpleWriter.println("<isModifyAnnotationsAllowed>" + true + "</isModifyAnnotationsAllowed>");
									}

									if (!PdfEncryptor.isModifyContentsAllowed(permissions)) {
										xmlSimpleWriter.println("<isModifyContentsAllowed>" + false + "</isModifyContentsAllowed>");
									}
									else {
										xmlSimpleWriter.println("<isModifyContentsAllowed>" + true + "</isModifyContentsAllowed>");
									}
															

									if (!PdfEncryptor.isPrintingAllowed(permissions)) {
										xmlSimpleWriter.println("<isPrintingAllowed>" + false + "</isPrintingAllowed>");
									}
									
									else {
										xmlSimpleWriter.println("<isPrintingAllowed>" + true + "</isPrintingAllowed>");
									}

									if (!PdfEncryptor.isScreenReadersAllowed(permissions)) {
										xmlSimpleWriter.println("<isScreenReadersAllowed>" + false + "</isScreenReadersAllowed>");
									}
									
									else {
										xmlSimpleWriter.println("<isScreenReadersAllowed>" + true + "</isScreenReadersAllowed>");
									}
									
									xmlSimpleWriter.println("</EncryptionDetails>");

								}

								catch (Exception e) {

									JOptionPane.showMessageDialog(null, findings.get(i).path + " ist defekt", "Information", JOptionPane.INFORMATION_MESSAGE);

								}
								}

								// ZbwFilePdfEncryption.testPermissions
								// (findings.get(i).getPath());
								if (!testfilePdf.isEncrypted) {
									testfilePdf.isPdfA = ZbwFilePdf.isPdfA(findings.get(i).zbwFile.toString());
									xmlSimpleWriter.println("<PdfA>" + testfilePdf.isPdfA + "</PdfA>");
								} else {
									xmlSimpleWriter.println("<PdfA>" + "false" + "</PdfA>");
								}
							}
						} else {
							xmlSimpleWriter.println("<PdfEncryption>" + "Encryption could not be checked" + "</PdfEncryption>");
							xmlSimpleWriter.println("<PdfA>" + "PDF/A could not be checked" + "</PdfA>");
						}

					}

				}
				if (findings.get(i).mimetype.equals("image/tiff")) {

					ZbwFileTiff tiff = new ZbwFileTiff();
					ArrayList<ZbwTiffTag> listTiffTags = new ArrayList<ZbwTiffTag>();
					listTiffTags = tiff.alltifftags;
					xmlSimpleWriter.println("<TiffTags>");

					xmlSimpleWriter.println("</TiffTags>");

				}

				xmlSimpleWriter.println("</File>");
			}

			xmlSimpleWriter.println("</FileAnalysisSummary>");
			xmlSimpleWriter.close();
			f.dispose();
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
