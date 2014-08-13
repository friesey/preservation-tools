package PdfHackerTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.activation.FileDataSource;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;

public class PdfAValidator {

	static String t;
	static PrintWriter outputfile;
	static PrintWriter ShortSummary;

	public static void main(String args[]) throws IOException {

		try {

			t = PdfUtilities.ChooseFolder();
			
			outputfile = new PrintWriter(new FileWriter(t + "//"
					+ "PdfAValidation.txt"));
			
			ShortSummary = new PrintWriter(new FileWriter(t + "//"
					+ "PdfAValidationShortSummary.txt"));

			if (t != null) {

				ArrayList<File> files = PdfUtilities.getPaths(new File(t),
						new ArrayList<File>());
				if (files == null)
					return;

				for (int i = 0; i < files.size(); i++) {
					if (!files.get(i).isDirectory() && files.get(i) != null) {
						System.out.println(i + 1);
						outputfile.println(i + 1);
						ShortSummary.println(i + 1);

						try {

							System.out.println(files.get(i).getCanonicalPath());
							outputfile.println(files.get(i).getCanonicalPath());
							ShortSummary.println(files.get(i).getCanonicalPath());

							// can the PdfAValidator handle bigger files?
							if (!PdfUtilities.PdfSizeChecker(files.get(i))) {

								if (PdfUtilities.FileHeaderTest(files.get(i)) == true) {

									if (PdfUtilities.brokenPdfChecker(files
											.get(i).toString()) == false) {

										PDDocument testfile = PDDocument
												.load(files.get(i));

										if (!testfile.isEncrypted()) {

											String PdfType = PdfUtilities
													.PdfAChecker(files.get(i));
											if (PdfType.contains("PDF/A")) {
												// the actual PdfAValidation
												// starts here
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

														result = document
																.getResult();
														document.close();

													} catch (NullPointerException e) {
														outputfile.println(e);
														ShortSummary.println(e);
													}
												} catch (SyntaxValidationException e) {
													/*
													 * the parse method throws a
													 * SyntaxValidationException
													 * if the PDF file can't be
													 * parsed.
													 */
													result = e.getResult();
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
														
														ShortSummary.println("The file "
																+ files.get(i)
																+ " is not valid, error(s) :");														
														for (ValidationError error : result
																.getErrorsList()) {
															outputfile
																	.println(error
																			.getErrorCode()
																			+ " : "
																			+ error.getDetails());
														}
													}
												}
											}

											else {
												// outputfile.println(PdfType);
												ShortSummary.println("No PDF/A file");
												outputfile.println("No PDF/A file");
												ShortSummary.close();
												outputfile.close();
											}
										}
									}
								}
							}
						} catch (IOException e) {
							outputfile.print(e);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
		}
	}
}