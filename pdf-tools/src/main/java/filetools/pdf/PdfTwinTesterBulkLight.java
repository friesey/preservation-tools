package filetools.pdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class PdfTwinTesterBulkLight {

	// TODO: To test whole folders, find a way to know which PDF files to
	// compare

	static String orgPdf;
	static String migPdf;
	static long filesizeOrg;
	static long filesizeMig;

	static String folder;

	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		JOptionPane.showMessageDialog(null, "Please choose the folder with PDF Files to compare.", "Enter String Mask", JOptionPane.QUESTION_MESSAGE);
		folder = utilities.BrowserDialogs.chooseFolder();

		if (folder != null) {
			createXMLandXSLT();
			outputfile.println("<PdfTwinTest>");

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
			ArrayList<File> pdffiles = new ArrayList<File>();
			boolean pdfok;
			for (int i = 0; i < files.size(); i++) {

				String extension = utilities.fileStringUtilities.getExtension(files.get(i).toString()).toLowerCase();

				if (extension.equals("pdf")) {
					pdfok = PdfTwinTest.analysePdfok(files.get(i).toString());
					if (pdfok == true) {
						pdffiles.add(files.get(i));
					}
				}
			}

			// ZbwPdfObject[] pdfobjectArr = new ZbwPdfObject[pdffiles.size()];

			ArrayList<ZbwPdfObject> pdfObjectList = new ArrayList<ZbwPdfObject>();

			for (int i = 0; i < pdffiles.size(); i++) {

				ZbwPdfObject temp = new ZbwPdfObject();
				PDDocument pd = new PDDocument();
				pd = PDDocument.load(pdffiles.get(i));
				temp.metaInfo = pd.getDocumentInformation();
				temp.filename = utilities.fileStringUtilities.getFileName(pdffiles.get(i).toString());
				temp.creationDate = getsomeMetadata(temp.metaInfo);
				temp.title = temp.metaInfo.getTitle();
				temp.file = pdffiles.get(i);
				pdfObjectList.add(temp);
				// pdfobjectArr[i] = temp;
				pd.close();
			}

			ArrayList<ZbwPdfObject> copy = new ArrayList<ZbwPdfObject>();
			for (int i = 0; i < pdfObjectList.size(); i++) {
				copy.add(pdfObjectList.get(i));
			}

			for (int i = 0; i < pdfObjectList.size(); i++) {
				for (int j = 0; j < copy.size(); j++) {
					if ((pdfObjectList.get(i).title != null) && ((copy.get(j).title) != null)) {
						if (pdfObjectList.get(i).title.equals(copy.get(j).title)) {
							// PDF Dateien vergleichen
							if (!pdfObjectList.get(i).filename.equals(copy.get(j).filename)) {
								comparetwoPdfFiles(pdfObjectList.get(i).file, copy.get(j).file);
							}
						}
					}
				}
				copy.remove(pdfObjectList.get(i));
			}

			outputfile.println("</PdfTwinTest>");
			outputfile.close();
		}

		else {
			System.out.println("Please choose two files.");
		}
	}

	private static void createXMLandXSLT() throws IOException {
		outputfile = new PrintWriter(new FileWriter(folder + "\\PdfTwinTester.xml"));
		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"PdfTwinTestStyle.xsl\"?>";
		String xsltLocation = (folder + "//" + "PdfTwinTestStyle.xsl");
		output.XslStyleSheets.PdfTwinTestXsl(xsltLocation);
		outputfile.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		outputfile.println(xsltStyleSheet);
	}

	private static void comparetwoPdfFiles(File orgPdf, File migPdf) throws IOException {
		boolean orgok = analysePdfok(orgPdf.toString());
		boolean migok = analysePdfok(migPdf.toString());

		if (orgok == true && migok == true) {
			String[] linesOrg = PdfAnalysis.extractsPdfLines(orgPdf.toString());
			String[] linesMig = PdfAnalysis.extractsPdfLines(migPdf.toString());

			System.out.println("Original: " + orgPdf.toString());

			System.out.println("Migrated: " + migPdf.toString());

			int differences = 0;

			int lenOrg = linesOrg.length;
			int lenMig = linesMig.length;
			int arraysize; // to avoid out of array bound if one Pdf has
							// more lines than the other
			if ((lenOrg > lenMig || lenOrg == lenMig)) {
				arraysize = lenMig;
			} else {
				arraysize = lenOrg;
			}
			for (int j = 0; j < arraysize; j++) {
				if (!(linesOrg[j]).equals(linesMig[j])) {
					outputfile.println("<Details>");
					outputfile.println("<DifferentLineNumber>" + (j + 1) + "</DifferentLineNumber>");
				//	outputfile.println("<OriginalLine><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(linesOrg[j]) + "]]></OriginalLine>"); // TODO:
					/*
					 * cannot display cyrillic stuff
					 */
			//		outputfile.println("<MigrationLine><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(linesMig[j]) + "]]></MigrationLine>");
					differences++;

					/*
					 * int levenshtein = calcLevenshtein(linesOrg[j],
					 * linesMig[j]); if (levenshtein == 1) {
					 * findSpaceinLine(linesOrg[j], linesMig[j]); }
					 */
					outputfile.println("<LevenshteinDistance>" + "not calculated" + "</LevenshteinDistance>");
					outputfile.println("</Details>");
				}
			}
			if (differences == 0) {
				outputfile.println("<ComparedItem>");
				outputfile.println("<FileOrg><![CDATA[" + utilities.fileStringUtilities.getFileName(orgPdf) + "]]></FileOrg>");
				outputfile.println("<LinesLengthOrg>" + lenOrg + "</LinesLengthOrg>");
				outputfile.println("<FileMig><![CDATA[" + utilities.fileStringUtilities.getFileName(migPdf) + "]]></FileMig>");
				outputfile.println("<LinesLengthMig>" + lenMig + "</LinesLengthMig>");
				outputfile.println("<PdfTwins>" + "true" + "</PdfTwins>");
				outputfile.println("</ComparedItem>");
			}

			else {
				outputfile.println("<ComparedItem>");
				outputfile.println("<FileOrg><![CDATA[" + utilities.fileStringUtilities.getFileName(orgPdf) + "]]></FileOrg>");
				outputfile.println("<LinesLengthOrg>" + lenOrg + "</LinesLengthOrg>");
				outputfile.println("<FileMig><![CDATA[" + utilities.fileStringUtilities.getFileName(migPdf) + "]]></FileMig>");
				outputfile.println("<LinesLengthMig>" + lenMig + "</LinesLengthMig>");
				outputfile.println("<PdfTwins>" + "false" + "</PdfTwins>");
				outputfile.println("<DifferentLines>" + differences + "</DifferentLines>");

				String wholeStringPdfOrg = PdfAnalysis.extractsPdfLinestoString(orgPdf.toString());
				String wholeStringPdfMig = PdfAnalysis.extractsPdfLinestoString(migPdf.toString());

				// int levenshteinlines = calcLevenshtein(wholeStringPdfOrg,
				// wholeStringPdfMig);
				outputfile.println("<LevenshteinPdf>" + "not calculated" + "</LevenshteinPdf>");

				outputfile.println("<LineIrregularities>" + "not calculated" + "</LineIrregularities>");

				outputfile.println("</ComparedItem>");
			}
		}
	}

	private static Date getsomeMetadata(PDDocumentInformation info) throws IOException {
		try {
			Calendar creationYear = info.getCreationDate();
			Date creationDate = creationYear.getTime();
			return creationDate;
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean analysePdfok(String pdf) throws IOException {
		if (pdf != null) {
			filesizeOrg = pdf.length();
			if (filesizeOrg < 16000000) {
				if (filetools.GenericFileAnalysis.testFileHeaderPdf(pdf) == true) {
					PDDocument testfile = PDDocument.load(pdf);
					if (!testfile.isEncrypted()) {
						if (!PdfAnalysis.checkBrokenPdf(pdf)) {
							return true;
						}
					}
				}
			}
		}
		// JOptionPane.showMessageDialog(null,
		// "PDF will not be examined due to invalidity, encryption or file size",
		// pdf, JOptionPane.INFORMATION_MESSAGE);
		System.out.println("PDF will not be examined due to invalidity, encryption or file size");
		return false;
	}

	private static void findSpaceinLine(String orgline, String migline) {
		// TODO Auto-generated method stub
		String[] orgArr = orgline.split(" ");
		String[] migArr = migline.split(" ");

		for (int n = 0; n < orgArr.length; n++) {
			if (!orgArr[n].equals(migArr[n])) {
			/*	outputfile.println("<DifferentWordOrg><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(orgArr[n]) + "]]></DifferentWordOrg>");
				if (orgArr.length < migArr.length) {
					outputfile.println("<DifferentWordMig><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(migArr[n]) + " " + utilities.fileStringUtilities.reduceNULvalues(migArr[n + 1]) + "]]></DifferentWordMig>");
				} else {
					outputfile.println("<DifferentWordMig><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(migArr[n]) + "]]></DifferentWordMig>");
				}*/
				break;
			}
		}
	}

	private static int calcLevenshtein(String orgline, String migline) {
		orgline.toLowerCase();
		migline.toLowerCase();

		int[] costs = new int[migline.length() + 1];

		for (int j = 0; j < costs.length; j++)
			costs[j] = j;
		for (int i = 1; i <= orgline.length(); i++) {
			costs[0] = i;
			int nw = i - 1;
			for (int j = 1; j <= migline.length(); j++) {
				int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), orgline.charAt(i - 1) == migline.charAt(j - 1) ? nw : nw + 1);
				nw = costs[j];
				costs[j] = cj;
			}
		}
		return costs[migline.length()];
	}
}
