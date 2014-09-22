package preservetools.files.pdf;

// TODO: next time, the package name should start with a small character, this is the convention

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class PdfAnalysis {

	/*******************************************************
	 * Variables and objects used within the whole package
	 ********************************************************/
	public static BufferedReader PdfHeaderTest;

	static Logger logger = LoggerFactory.getLogger(PdfAnalysis.class);

	/*********************************************************
	 * Methods used within the whole package
	 *
	 ********************************************************/

	/****************************************************************************
	 * Checks if a PDF is ok to work with %PDF Header, Broken PDF & Encryption
	 * 
	 * @param file            
	 * @return: boolean true or false
	 * @throws IOException
	 */

	public static boolean testPdfOk(File file) throws IOException {

		if (preservetools.files.GenericFileAnalysis.testFileHeaderPdf(file)) {
			if (!preservetools.files.GenericFileAnalysis.checkFileSize(file)) {
				PDDocument testfile = PDDocument.load(file);
				if (!testfile.isEncrypted()) {
					if (!checkBrokenPdf(file.toString())) {
						return true;
					} else {
						System.out.println("Broken Pdf");
						return false;
					}
				} else {
					System.out.println("Is encrypted");
					return false;
				}
			} else {
				System.out.println("Pdf too big to be examined");
				return false;
			}
		} else {
			System.out.println("No PDF Header");
			return false;
		}
	}

	/**
	 * Determines which PDF version it is. Can also detect PDF/A.
	 * 
	 * @param File
	 *            (should be PDF)
	 * @return: String PDF Version TODO: occasionally throws WARN about log4j
	 *          that I cannot understand or get rid of.
	 * @throws IOException
	 */

	public static String checkIfPdfA(File file) throws IOException {
		String pdfType = "No XMP Metadata";
		String XmpMetadata;
		PdfReader reader;
		try {
			reader = new PdfReader(file.toString());
			// There is no PDF/A compliance before PDF 1.4
			if (reader.getPdfVersion() > 3) {
				if (reader.getMetadata() != null) {
					XmpMetadata = new String(reader.getMetadata()); // nullpointerException
					reader.close();
					if (XmpMetadata.contains("pdfaid:conformance")) {
						pdfType = "PDF/A";
					} else {
						pdfType = "PDF 1.4 or higher";
					}
				}
			} else {
				pdfType = "PDF 1.0 - 1.3";
			}
			return pdfType;
		} catch (java.lang.NullPointerException e) {
			System.out.println(e);
			pdfType = "PDF cannot be read by PdfReader";
			logger.error("Error analyzing " + e);
			return pdfType;
		}
	}

	/**
	 * Checks if a Pdf is too broken to be examined.
	 * 
	 * @param File
	 *            (should be PDF)
	 * @return: boolean
	 * @throws IOException
	 */

	// TODO: This function does not work, e. g. for encrypted files and should
	// not be used until it is fixed.
	public static boolean checkBrokenPdf(String file) throws IOException {

		boolean brokenPdf;
		try {
			PdfReader reader = new PdfReader(file);
			reader.getMetadata();
			// TODO: One day this function could test more and be more clever.
			brokenPdf = false;
			return brokenPdf;
		} catch (Exception e) {
			System.out.println("Broken: " + file);
			brokenPdf = true;
			logger.error("Error analyzing " + e);
			return brokenPdf;
		}
	}

	/**
	 * Simple Encryption Test without reader, because encryption causes lots of
	 * exceptions.
	 * 
	 * @param PDDocument
	 *            (should be PDF)
	 * @return: boolean
	 * @throws IOException
	 */

	public static boolean testsEncryption(PDDocument file) throws IOException {
		// PDDocumentInformation info =
		// PDDocument.load(file).getDocumentInformation();
		if (file.isEncrypted() == true) {
			System.out.println(file + " is encrypted");
			return true;
		} else {
			return false;
		}
	}

	public static String[] extractsPdfLines(String PdfFile) throws IOException {
		StringBuffer buff = new StringBuffer();
		String ExtractedText = null;
		PdfReader reader = new PdfReader(PdfFile);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			ExtractedText = strategy.getResultantText().toString();
			buff.append(ExtractedText + "\n");
		}

		String[] LinesArray;

		LinesArray = buff.toString().split("\n");
		reader.close();
		return LinesArray;
	}

	/**
	 * Checks the size of the Pdf-file, because some big Pdf Files might cause
	 * exceptions. *
	 * 
	 * @param file
	 *            (should be Pdf)
	 * @return: boolean
	 * @throws
	 */

	/*
	 * I think this method is so complicated because of the test that was build.
	 * Maybe change method in GenericFileAnalysis eventually to embedd those
	 * kinds of tests, too.
	 * 
	 * public static boolean checkPdfSize(File file) { boolean toobig =
	 * isFileTooLong(file, DEFAULT_MAX_FILE_LENGTH); if (toobig) { System.out
	 * .println("File is bigger than 16 MB and therefore cannot be measured"); }
	 * return toobig; }
	 * 
	 * public static boolean checkPdfSize(String filePath) { File toCheck = new
	 * File (filePath); return checkPdfSize(toCheck); }
	 * 
	 * public static boolean isFileTooLong(File toCheck, long maxLength) {
	 * return (toCheck.length() > maxLength); }
	 */
}
