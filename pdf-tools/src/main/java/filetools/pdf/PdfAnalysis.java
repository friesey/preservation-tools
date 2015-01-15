package filetools.pdf;

// TODO: next time, the package name should start with a small character, this is the convention

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class PdfAnalysis {

	/*******************************************************
	 * Variables and objects used within the whole package
	 ********************************************************/
	public static BufferedReader PdfHeaderTest;

//	static Logger logger = LoggerFactory.getLogger(PdfAnalysis.class);

	/*********************************************************
	 * Methods used within the whole package
	 *
	 ********************************************************/

	/****************************************************************************
	 * Analysis PDF-Objects
	 * 
	 * @param file
	 * @return: nothings, puts out the information in a file
	 * @throws IOException
	 */
	public static void analysePdfObjects(File file) throws IOException {

		PrintWriter pdfboxanalysis = new PrintWriter(new FileWriter("D://pdfboxanalysis.txt"));
		pdfboxanalysis.println(file.toString());

		PDDocument pdf = PDDocument.load(file);
		PDDocumentInformation info = pdf.getDocumentInformation();
		COSDictionary dict = info.getDictionary();
		Collection<COSBase> l = dict.getValues();

		COSArray mediaBox = (COSArray) dict.getDictionaryObject("MediaBox");
		System.out.println("MediaBox: " + mediaBox);

		COSDictionary trailer = pdf.getDocument().getTrailer();
		System.out.println("Trailer:" + trailer);

		if (pdf.isEncrypted()) { //this actually works easily
			System.out.println("Encrypted");
		}

		for (Object o : l) {
			// System.out.println(o.toString());
			pdfboxanalysis.println(o.toString());
		}

		PDDocumentCatalog cat = pdf.getDocumentCatalog();

		@SuppressWarnings("unchecked")
		List<PDPage> lp = cat.getAllPages();
		pdfboxanalysis.println("# Pages: " + lp.size());
		PDPage page = lp.get(4);
		pdfboxanalysis.println("Page: " + page);
		pdfboxanalysis.println("\tCropBox: " + page.getCropBox());
		pdfboxanalysis.println("\tMediaBox: " + page.getMediaBox());
		pdfboxanalysis.println("\tResources: " + page.getResources());
		pdfboxanalysis.println("\tRotation: " + page.getRotation());
		pdfboxanalysis.println("\tArtBox: " + page.getArtBox());
		pdfboxanalysis.println("\tBleedBox: " + page.getBleedBox());
		pdfboxanalysis.println("\tContents: " + page.getContents());
		pdfboxanalysis.println("\tTrimBox: " + page.getTrimBox());
		List<PDAnnotation> la = page.getAnnotations();
		pdfboxanalysis.println("\t# Annotations: " + la.size());

		pdfboxanalysis.close();
	}

	/*********************************************************
	 * Checks if a PDF is ok to work with %PDF Header, Broken PDF & Encryption
	 * 
	 * @param file
	 * @return: boolean true or false
	 * @throws IOException
	 */

	public static boolean testPdfOk(File file) throws IOException {

		if (filetools.GenericFileAnalysis.testFileHeaderPdf(file) == true) {
			/*
			 * if (preservetools.files.GenericFileAnalysis.checkFileSize(file))
			 * {
			 */
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
		} /*
		 * else { System.out.println("Pdf too big to be examined"); return
		 * false; } }
		 */else {
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
		//	logger.error("Error analyzing " + e);
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
		//	logger.error("Error analyzing " + e);
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
		try {
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
		} catch (Exception e) {
			return null;
		}
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
