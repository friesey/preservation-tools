package filetools.pdf;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfTwinTest {

	static String OrgPdf;
	static String MigPdf;
	static long filesizeOrg;
	static long filesizeMig;

	static String folder;

	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		System.out
				.println("Please select the folder for outputfile 'PdfTwinTest.txt'");

		folder = utilities.BrowserDialogs.chooseFolder();

		if (folder != null) {

			outputfile = new PrintWriter(new FileWriter(folder
					+ "\\PdfTwinTester.txt"));
			outputfile.println("Pdf Twin Test");

			OrgPdf = utilities.BrowserDialogs.chooseFile();
			System.out.println(OrgPdf);

			MigPdf = utilities.BrowserDialogs.chooseFile();
			System.out.println(MigPdf);

			outputfile.println("Original File: " + OrgPdf);
			outputfile.println("Migrated File: " + MigPdf);

			if (OrgPdf != null && MigPdf != null) {

				// the real program starts here, everything else are just
				// prerequisites

				filesizeOrg = OrgPdf.length();
				filesizeMig = MigPdf.length();

				if (filesizeOrg < 16000000 && filesizeMig < 16000000) {

					if (filetools.GenericFileAnalysis.testFileHeaderPdf(OrgPdf) == true
							&& filetools.GenericFileAnalysis.testFileHeaderPdf(MigPdf) == true) {

						PDDocument testfileOrg = PDDocument.load(OrgPdf);
						PDDocument testfileMig = PDDocument.load(MigPdf);

						if (testfileOrg.isEncrypted()
								|| testfileMig.isEncrypted()) {

							System.out
									.println("One or both of the Pdf-files are encrypted.");
						} else {

							if (PdfAnalysis.checkBrokenPdf(OrgPdf) == false
									&& PdfAnalysis.checkBrokenPdf(MigPdf) == false) {
								String[] linesOrg = PdfAnalysis
										.extractsPdfLines(OrgPdf);
								String[] linesMig = PdfAnalysis
										.extractsPdfLines(MigPdf);

								int differences = 0;

								int lenOrg = linesOrg.length;
								int lenMig = linesMig.length;

								outputfile.println(OrgPdf + " has " + lenOrg
										+ " lines.");
								outputfile.println(OrgPdf + " has " + lenMig
										+ " lines.");

								if (lenOrg != lenMig) {
									if (lenOrg > lenMig) {
										outputfile
												.println("The migrated Pdf has"
														+ (lenOrg - lenMig)
														+ " lines less.");
									} else {
										outputfile
												.println("The migrated PDf has"
														+ (lenMig - lenOrg)
														+ " lines more.");
									}
									outputfile.println();
								}

								if ((lenOrg > lenMig || lenOrg == lenMig)) {

									for (int j = 0; j < lenMig; j++) {

										if (!(linesOrg[j]).equals(linesMig[j])) {
											outputfile.println();
											outputfile
													.println("Differs in line: "
															+ (j + 1));
											outputfile.println();
											outputfile.println("Original : "
													+ linesOrg[j]);
											outputfile.println("Migration: "
													+ linesMig[j]);
											outputfile.println();
											differences++;
										}
									}
									if (differences == 0) {
										outputfile
												.println("Both PDF-Files are alike.");
									}

									else {
										outputfile.println(differences
												+ " lines have differences.");
									}
								}

								else /* (lenMig > lenOrg) */{
									for (int j = 0; j < lenOrg; j++) {

										// happens twice, maybe create a method?

										if (!(linesOrg[j]).equals(linesMig[j])) {
											outputfile.println();
											outputfile
													.println("Differs in line: "
															+ (j + 1));
											outputfile.println();
											outputfile.println("Original : "
													+ linesOrg[j]);
											outputfile.println("Migration: "
													+ linesMig[j]);
											outputfile.println();
											differences++;
										}
									}
									if (differences == 0) {
										outputfile
												.println("Both PDF-Files are alike.");
									}

									else {
										outputfile.println(differences
												+ " lines have differences.");
									}
								}
							} else {
								System.out.println("Program closed.");
							}
						}
					}

					else {
						System.out
								.println("One of the files is lacking a PdfHeader.");
						System.out
								.println("Please choose two proper Pdf-files");
					}

				} else {
					System.out
							.println("One of the Files or both are too big to be examined:");
					System.out.println(OrgPdf + " Filesize: " + filesizeOrg);
					System.out.println(MigPdf + " Filesize: " + filesizeMig);
				}
			}

			else {
				System.out.println("Please choose two files.");
			}

			outputfile.close();
		}
	}
}
