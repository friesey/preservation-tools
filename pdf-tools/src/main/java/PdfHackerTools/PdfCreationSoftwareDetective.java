package PdfHackerTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import com.itextpdf.text.pdf.PdfReader;

public class PdfCreationSoftwareDetective {
	static String t;
	static int ProducerID;
	static ArrayList<String> ProducerType;
	static BufferedReader PdfHeaderTest;
	static PdfReader reader;
	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {
		try {
			t = PdfUtilities.ChooseFolder();
			if (t != null) {
				ProducerID = 0;
				ProducerType = new ArrayList<String>();
				ArrayList<File> files = PdfUtilities.getPaths(new File(t),
						new ArrayList<File>());
				if (files == null)
					return;
				String extension;
				outputfile = new PrintWriter(new FileWriter(t + "//"
						+ "CreationSoftwareDetective.txt"));
				for (int i = 0; i < files.size(); i++) {
					if (files.get(i) != null) { // maybe not necessary
						// prints out only files and not the subdirectories as
						// well
						if (!files.get(i).isDirectory()) {
							// null pointer exception
							extension = Files.probeContentType(files.get(i)
									.toPath());
							if (extension != null) {
								if (extension.equals("application/pdf")) {
									if (!PdfUtilities.PdfSizeChecker(files
											.get(i))) {
										if (PdfUtilities.FileHeaderTest(files
												.get(i)) == true) {
											System.out.println(files.get(i));
											try {
												PDDocument testfile = PDDocument
														.load(files.get(i));
												if (PdfUtilities
														.EncryptionTest(testfile) == false) {
													{
														if (PdfUtilities
																.brokenPdfChecker(files
																		.get(i)
																		.toString()) == false) {
															reader = new PdfReader(
																	files.get(i)
																			.toString());
															Map info = reader
																	.getInfo();
															if (info.get("Producer") != null) {
																System.out
																		.println(info
																				.get("Producer")
																				.toString());
																ProducerType
																		.add(info
																				.get("Producer")
																				.toString());
															}
															reader.close();
														}
													}
												}

												testfile.close();
											} catch (IOException e) {
												outputfile
														.println(files.get(i)
																+ " is so damaged it cannot be parsed: "
																+ e);
											}
										} else {
											System.out
													.println(files.get(i)
															.getName()
															+ " PDF Header is missing.");
										}
									}
								}
							}
						}

					}
				}
				// get rid of redundant entries
				Collections.sort(ProducerType);
				int i;

				for (i = 0; i < ProducerType.size() - 1;) {
					if (ProducerType.get(i).equals(ProducerType.get(i + 1))) {
						ProducerType.remove(i);
					} else {
						i++;
					}
				}
				for (String item : ProducerType) {
					outputfile.println(item);
				}
				// in case no PDF-files are found, the outputfile comes
				// out empty.
				// Is this intended?
				outputfile.close();
			}
		} catch (FileNotFoundException e) {
		}
	}
}