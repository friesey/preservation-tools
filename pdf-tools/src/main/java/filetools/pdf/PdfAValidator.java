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
import java.util.Calendar;
import java.util.Date;

import javax.activation.FileDataSource;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
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
			String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"PdfBoxValidationStyle.xsl\"?>";
			String xsltStyleSheetSummary = "<?xml-stylesheet type=\"text/xsl\" href=\"PdfBoxSummaryStyle.xsl\"?>";

			String xsltLocation = examinedFolder + "//" + "PdfBoxValidationStyle.xsl";
			String xsltLocationSum = examinedFolder + "//" + "PdfBoxSummaryStyle.xsl";

			output.XslStyleSheets.PdfBoxCustomizedXsl(xsltLocation);

			output.XslStyleSheets.PdfBoxSummaryCustomizedXsl(xsltLocationSum);

			outputfile.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			outputfile.println(xsltStyleSheet);
			outputfile.println("<PdfBoxValidation>");

			shortSummary.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			shortSummary.println(xsltStyleSheetSummary);
			shortSummary.println("<PdfBoxValidationSummary>");

			int examinedPdfa = 0;
			int validPdfa = 0;
			int invalidPdfa = 0;
			int causedErrorPdfa = 0;

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

									int syntaxError = 0;
									int graphicError = 0;
									int fontError = 0;
									int transparencyError = 0;
									int annotationError = 0;
									int actionError = 0;
									int metadataError = 0;

									examinedPdfa++;

									outputfile.println("<FileName>" + utilities.fileStringUtilities.getFileName(files.get(i)) + "</FileName>");
									shortSummary.println("<FileName>" + utilities.fileStringUtilities.getFileName(files.get(i)) + "</FileName>");

									PDDocument pd = new PDDocument();
									pd = PDDocument.load(files.get(i));

									PDDocumentInformation info = pd.getDocumentInformation();
									getsomeMetadata(info);
									pd.close();

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

										}

										catch (ArrayIndexOutOfBoundsException out) {
											System.out.println("An Exception occured: " + out);
											System.out.println(files.get(i).toString());
											
											outputfile.println("<Status>" + "Broken" + "</Status>");
											shortSummary.println("<Status>" + "Broken" + "</Status>");
											
											outputfile.println("<Error>" + out + "</Error>");
											shortSummary.println("<Error>" + out + "</Error>");
											
											causedErrorPdfa++;
										}
										
										catch (NullPointerException nullex) {
											System.out.println("An Exception occured: " + nullex);
											System.out.println(files.get(i).toString());
											
											outputfile.println("<Status>" + "Broken" + "</Status>");
											shortSummary.println("<Status>" + "Broken" + "</Status>");
											
											outputfile.println("<Error>" + nullex + "</Error>");
											shortSummary.println("<Error>" + nullex + "</Error>");
											causedErrorPdfa++;
										}

										catch (Exception e) {
											/*
											 * TODO: Why can this generate a
											 * NullPointerException ?
											 */
											outputfile.println("<Error>" + e + "</Error>");
											shortSummary.println("<Error>" + e + "</Error>");
											
											outputfile.println("<Status>" + "Broken" + "</Status>");
											shortSummary.println("<Status>" + "Broken" + "</Status>");
											
											causedErrorPdfa++;											
											
										//	logger.error("Error analyzing " + files.get(i).getAbsolutePath(), e);
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
											outputfile.println("<Status>" + "Valid" + "</Status>");
											shortSummary.println("<Status>" + "Valid" + "</Status>");
											validPdfa++;
										} else {
											int errorslen = 0;
											outputfile.println("<Status>" + "Invalid" + "</Status>");
											shortSummary.println("<Status>" + "Invalid" + "</Status>");
											invalidPdfa++;

											for (ValidationError error : result.getErrorsList()) {
												errorslen++;

												String errorCode = error.getErrorCode().toString();
												outputfile.println("<Code>" + error.getErrorCode() + "</Code>");
												String errorDetails = utilities.fileStringUtilities.reduceXmlEscapors(error.getDetails());

												if (errorCode.startsWith("1")) {
													outputfile.println("<Details Category=\"SyntaxError\">" + errorDetails + "</Details>");
													syntaxError++;
												}

												if (errorCode.startsWith("2")) {
													outputfile.println("<Details Category=\"GraphicError\">" + errorDetails + "</Details>");
													graphicError++;
												}

												if (errorCode.startsWith("3")) {
													outputfile.println("<Details Category=\"FontError\">" + errorDetails + "</Details>");
													fontError++;
												}

												if (errorCode.startsWith("4")) {
													outputfile.println("<Details Category=\"TransparencyError\">" + errorDetails + "</Details>");
													transparencyError++;
												}
												if (errorCode.startsWith("5")) {
													outputfile.println("<Details Category=\"AnnotationError\">" + errorDetails + "</Details>");
													annotationError++;
												}

												if (errorCode.startsWith("6")) {
													outputfile.println("<Details Category=\"ActionError\">" + errorDetails + "</Details>");
													actionError++;
												}

												if (errorCode.startsWith("7")) {
													outputfile.println("<Details Category=\"MetadataError\">" + errorDetails + "</Details>");
													metadataError++;
												}

											}
											outputfile.println("<SyntaxErrors>" + syntaxError + "</SyntaxErrors>");
											outputfile.println("<GraphicErrors>" + graphicError + "</GraphicErrors>");
											outputfile.println("<FontErrors>" + fontError + "</FontErrors>");
											outputfile.println("<TransparencyErrors>" + transparencyError + "</TransparencyErrors>");
											outputfile.println("<AnnotationErrors>" + annotationError + "</AnnotationErrors>");
											outputfile.println("<ActionErrors>" + actionError + "</ActionErrors>");
											outputfile.println("<MetadataErrors>" + metadataError + "</MetadataErrors>");

											shortSummary.println("<ErrorsCount>" + errorslen + "</ErrorsCount>");

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

			shortSummary.println("<Summary>");
			shortSummary.println("<ExaminedPdfAFiles>" + examinedPdfa + "</ExaminedPdfAFiles>");
			shortSummary.println("<ValidPdfAFiles>" + validPdfa + "</ValidPdfAFiles>");
			shortSummary.println("<InvalidPdfAFiles>" + invalidPdfa + "</InvalidPdfAFiles>");
			shortSummary.println("<CausedErrorPdfAFiles>" + causedErrorPdfa + "</CausedErrorPdfAFiles>");
			shortSummary.println("</Summary>");

			outputfile.println("</PdfBoxValidation>");
			shortSummary.println("</PdfBoxValidationSummary>");
			shortSummary.close();
			outputfile.close();
		} catch (FileNotFoundException e) {
			logger.error("Error analyzing " + e);
			JOptionPane.showMessageDialog(null, e, "error message", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void getsomeMetadata(PDDocumentInformation info) throws IOException {
		try {
			Calendar creationYear = info.getCreationDate();

			Date creationYearDate = creationYear.getTime();

			int len = creationYearDate.toString().length();

			String year = creationYearDate.toString().substring(len - 4, len);
			String creationSoftware = utilities.fileStringUtilities.reduceXmlEscapors(info.getProducer());

			shortSummary.println("<CreationYear>" + year + "</CreationYear>");
			shortSummary.println("<CreationSoftware>" + creationSoftware + "</CreationSoftware>");
			outputfile.println("<CreationYear>" + year + "</CreationYear>");
			outputfile.println("<CreationSoftware>" + creationSoftware + "</CreationSoftware>");
		} catch (Exception e) {
			shortSummary.println("<CreationYear>" + "</CreationYear>");
			shortSummary.println("<CreationSoftware>" + "</CreationSoftware>");
			outputfile.println("<CreationYear>" + "</CreationYear>");
			outputfile.println("<CreationSoftware>" + "</CreationSoftware>");
		}

	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}
}