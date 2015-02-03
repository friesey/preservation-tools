package filetools.pdf;

import java.awt.Color;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.activation.FileDataSource;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;

public class PdfAValidator {

	static String examinedFolder;
	static PrintWriter outputfile;
	static PrintWriter shortSummary;

	static Logger logger = LoggerFactory.getLogger(PdfAValidator.class);

	public static void main(String args[]) throws IOException {

		try {

			changecolor();
			String path = "D://Eclipse New//PDFBoxLogo.gif";
			String description = "PDFBox Logo";
			ImageIcon icon = new ImageIcon(path, description);

			JOptionPane.showMessageDialog(null, "Please choose the folder with PDF/A files to validate.", "PDFBox Validation", JOptionPane.QUESTION_MESSAGE, icon);
			examinedFolder = utilities.BrowserDialogs.chooseFolder();


			outputfile = new PrintWriter(new FileWriter(examinedFolder + "//" + "PdfAValidation.xml"));
			shortSummary = new PrintWriter(new FileWriter(examinedFolder + "//" + "PdfAValidationShortSummary.xml"));

			String xmlVersion = "xml version='1.0'";
			String xmlEncoding = "encoding='ISO-8859-1'";
			// String xsltStyleSheet =
			// "<?xml-stylesheet type=\"text/xsl\" href=\"PdfBoxValidationStyle.xsl\"?>";
			// String xsltStyleSheetSummary =
			// "<?xml-stylesheet type=\"text/xsl\" href=\"PdfBoxSummaryStyle.xsl\"?>";

			outputfile.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			// outputfile.println(xsltStyleSheet);
			outputfile.println("<PdfBoxValidation>");

			shortSummary.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			// shortSummary.println(xsltStyleSheetSummary);
			shortSummary.println("<PdfBoxValidationSummary>");

			if (examinedFolder != null) {

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

				for (int i = 0; i < files.size(); i++) {
					if (files.get(i) != null) {					

						try {	

							if (PdfAnalysis.testPdfOk(files.get(i)))
							/*
							 * Test if the Pdf File is ok to be examined.
							 * Otherwise gives error in Console
							 */
							{
								String PdfType = PdfAnalysis.checkIfPdfA(files.get(i));
								if (PdfType.contains("PDF/A")) {
									
									outputfile.println("<PdfAFile>");
									shortSummary.println("<PdfAFile>");
									
									outputfile.println("<FileName>" + utilities.fileStringUtilities.getFileName(files.get(i)) + "</FileName>" );
									shortSummary.println("<FileName>" + utilities.fileStringUtilities.getFileName(files.get(i)) + "</FileName>" );
									
									outputfile.println("<Number>" + i + 1 + "</Number>");
									shortSummary.println("<Number>" + i + 1 + "</Number>");
									
									/*
									 * the actual PdfAValidation starts here
									 */
									ValidationResult result = null;
									FileDataSource fd = new FileDataSource(files.get(i).toString());
									PreflightParser parser = new PreflightParser(fd);
									try {
										parser.parse();
										PreflightDocument document = parser.getPreflightDocument();
										try {
											document.validate();

											result = document.getResult();
											document.close();

										} catch (NullPointerException e) {
											/*
											 * TODO: Why can this generate a
											 * NullPointerException ?
											 */
											outputfile.println("<Error>" + e + "</Error>");
											shortSummary.println("<Error>" + e + "</Error>");
											logger.error("Error analyzing " + files.get(i).getAbsolutePath(), e);
										}
									} catch (SyntaxValidationException e) {
										/*
										 * the parse method throws a
										 * SyntaxValidationException if the PDF
										 * file can't be parsed.
										 */
										result = e.getResult();
										logger.error("Error analyzing " + files.get(i).getAbsolutePath(), e);
									}
									if (result != null) {
										if (result.isValid()) {
											outputfile.println("<Status>" + "valid" + "</Status>");
											shortSummary.println("<Status>" + "valid" + "</Status>");
										} else {
											
											outputfile.println("<Status>" + "invalid" + "</Status>");
											shortSummary.println("<Status>" + "invalid" + "</Status>");
											outputfile.println("<Errors>");
											for (ValidationError error : result.getErrorsList()) {												
												outputfile.println("<Code>" + error.getErrorCode() + "</Code>");
												outputfile.println("<Details>" + error.getDetails() + "</Details>");								
											}
											outputfile.println("</Errors>");
										}
									}
									
									outputfile.println("</PdfAFile>");
									shortSummary.println("</PdfAFile>");
								} 
							}
						} catch (IOException e) {
							outputfile.println("<Error>" + e + "</Error>");				
							JOptionPane.showMessageDialog(null, e, "error message", JOptionPane.ERROR_MESSAGE);
						}
					}
					

				}
			}
			outputfile.println("</PdfBoxValidation>");
			shortSummary.println("</PdfBoxValidationSummary>");			
			shortSummary.close();
			outputfile.close();
		} catch (FileNotFoundException e) {
			logger.error("Error analyzing " + e);
			JOptionPane.showMessageDialog(null, e, "error message", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}
}