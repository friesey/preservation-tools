package filetools.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfChecker {

	static String examinedFolder;

	static int PdfHeader;
	static int NoPdfHeader;
	static int PdfA;
	static int PdfStandard;
	static int PdfEncrypted;
	static int PdfTooBig;
	static int i;

	static long filesize;

	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException{

		 try {
		 
		 examinedFolder = preservetools.utilities.FolderBrowserDialog.chooseFolder();
		 
		 // TODO: Create an XML Writer		
		 
		 if (examinedFolder != null) {
			
			ArrayList<File> files = preservetools.utilities.ListsFiles.getPaths(new File(examinedFolder),
					new ArrayList<File>());
			if (files == null)
				return;

			PdfHeader = 0;
			NoPdfHeader = 0;
			PdfA = 0;
			PdfStandard = 0;
			PdfEncrypted = 0;
			PdfTooBig = 0;
			filesize = 0;

			for (i = 0; i < files.size(); i++) {
				if (files.get(i) != null) {
					System.out.println(i + 1);
					
					try {
						System.out.println(files.get(i).getCanonicalPath());

						//is this necessary as the %PDF Test follows anyway?
						String extension = FilenameUtils.getExtension(files
								.get(i).getCanonicalPath());

						if (extension.equals("pdf")) {

							if(!filetools.GenericFileAnalysis.checkFileSize(files.get(i))) {							
								
								if (filetools.GenericFileAnalysis.testFileHeaderPdf(files.get(i)) == true) {

									System.out
											.println(files.get(i).getName()
													+ " is a PDF file and has a PDF header");
									PdfHeader++;

									PDDocument testfile = PDDocument.load(files
											.get(i));

									if (testfile.isEncrypted()) {

										System.out.println("Pdf is encrypted: "
												+ files.get(i));
										PdfEncrypted++;
										
										// TODO: Add Encryption Checker
										
									//	if (!PdfEncryptor.isCopyAllowed(testfile.toString()))
										
										
									}

									else {

										String PdfType = PdfAnalysis
												.checkIfPdfA(files.get(i));

										System.out.println("Pdf Type: "
												+ PdfType);

										if (PdfType.contains("PDF/A")) {
											PdfA++;
										} else {
											PdfStandard++; // this included
															// files with
															// "%PDF-header that have no XMP Metadata"
										}

										PdfAnalysis.PdfHeaderTest.close();
									}
								}

								else {
									System.out.println(files.get(i).getName()
											+ " PDF Header is missing.");
									NoPdfHeader++;
								}
							}
							
							else {
								PdfTooBig++;
							}
						}

						else {
							System.out.println("Extension is not .pdf");
							NoPdfHeader++;
						}
					}

					catch (IOException e) {
						System.out.print(e);
					}
				}
			}

			System.out.println();
			System.out.println();

			// System.out.println("Files examined: 	" + i); // does not always
			// work
			System.out.println("PDF Header missing: 	" + NoPdfHeader);
			System.out.println("PDF Header: 		" + PdfHeader);
			System.out.println("PDF/A-files:		" + PdfA);
			System.out.println("PDF Standard files: 	" + PdfStandard);
			System.out.println("PDF Encrypted files: 	" + PdfEncrypted);
			System.out.println("PDF files too big:	" + PdfTooBig);			
		 }
		 }
		catch (FileNotFoundException e ) {
			
		}
	}
}
