package filetools.pdf;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.activation.FileDataSource;

import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;

public class PdfAValidator {

	static String examinedFolder;
	static PrintWriter outputfile;
	static PrintWriter ShortSummary;

	static Logger logger = LoggerFactory.getLogger(PdfAValidator.class);
	
	public static void main(String args[]) throws IOException {

		try {

			examinedFolder = utilities.FolderBrowserDialog.chooseFolder();

			// Generating two Outputfiles in the folder that is examined

			outputfile = new PrintWriter(new FileWriter(examinedFolder + "//"
					+ "PdfAValidation.txt"));

			ShortSummary = new PrintWriter(new FileWriter(examinedFolder + "//"
					+ "PdfAValidationShortSummary.txt"));

			if (examinedFolder != null) {

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder),
						new ArrayList<File>());

				for (int i = 0; i < files.size(); i++) {
					if (files.get(i) != null) {

						System.out.println(i + 1);
						outputfile.println(i + 1);
						ShortSummary.println(i + 1);

						try {
							System.out.println(files.get(i).getCanonicalPath());
							outputfile.println(files.get(i).getCanonicalPath());
							ShortSummary.println(files.get(i)
									.getCanonicalPath());

							if (PdfAnalysis.testPdfOk(files.get(i)))
							/*
							 * Test if the Pdf File is ok to be examined.
							 * Otherwise gives error in Console
							 */
							{
								String PdfType = PdfAnalysis.checkIfPdfA(files
										.get(i));
								if (PdfType.contains("PDF/A")) {
									/*
									 * the actual PdfAValidation starts here
									 */
									ValidationResult result = null;
									FileDataSource fd = new FileDataSource(
											files.get(i).toString());
									PreflightParser parser = new PreflightParser(
											fd);
									try {
										parser.parse();
										PreflightDocument document = parser
												.getPreflightDocument();
										try {
											document.validate();

											result = document.getResult();
											document.close();

										} catch (NullPointerException e) {
											/*
											 * TODO: Why can this generate a
											 * NullPointerException ?
											 */
											outputfile.println(e);
											ShortSummary.println(e);
											 logger.error("Error analyzing " + files
														.get(i).getAbsolutePath(), e);
										}
									} catch (SyntaxValidationException e) {
										/*
										 * the parse method throws a
										 * SyntaxValidationException if the PDF
										 * file can't be parsed.
										 */
										result = e.getResult();
										logger.error("Error analyzing " + files
												.get(i).getAbsolutePath(), e);
									}
									if (result != null) {
										if (result.isValid()) {
											outputfile
													.println("The file "
															+ files.get(i)
															+ " is a valid PDF/A-1b file");

											ShortSummary
													.println("The file "
															+ files.get(i)
															+ " is a valid PDF/A-1b file");
										} else {
											outputfile
													.println("The file "
															+ files.get(i)
															+ " is not valid, error(s) :");

											ShortSummary
													.println("The file "
															+ files.get(i)
															+ " is not valid, error(s) :");
											for (ValidationError error : result
													.getErrorsList()) {
												outputfile.println(error
														.getErrorCode()
														+ " : "
														+ error.getDetails());
											}
										}
									}
								} else {
									ShortSummary.println("No PDF/A file");
									outputfile.println("No PDF/A file");
								}
							}
						} catch (IOException e) {
							outputfile.print(e);
							 logger.error("Error analyzing " + files
										.get(i).getAbsolutePath(), e);
						}
					}
				}
			}
			ShortSummary.close();
			outputfile.close();
		} catch (FileNotFoundException e) {
			 logger.error("Error analyzing " + e);
		}
	}
}