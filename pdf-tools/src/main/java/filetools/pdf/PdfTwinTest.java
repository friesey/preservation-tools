package filetools.pdf;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfTwinTest {

	static String OrgPdf;
	static String MigPdf;
	static long filesizeOrg;
	static long filesizeMig;

	static int levenstheinfullPdf;
	static int sumlevenstheinlines;

	static String folder;

	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		JOptionPane.showMessageDialog(null, "Please choose the folder for the output xml file.", "Enter String Mask", JOptionPane.QUESTION_MESSAGE);
		folder = utilities.BrowserDialogs.chooseFolder();

		if (folder != null) {
			sumlevenstheinlines = 0;
			createXMLandXSLT(); // this creates the XML output and the XSLT
								// Stylesheet in the chosen folder

			outputfile.println("<PdfTwinTest>");

			OrgPdf = utilities.BrowserDialogs.chooseFile();
			MigPdf = utilities.BrowserDialogs.chooseFile();

			boolean orgok = analysePdfok(OrgPdf);
			boolean migok = analysePdfok(MigPdf);

			if (orgok == true && migok == true) {
				String[] linesOrg = PdfAnalysis.extractsPdfLines(OrgPdf);
				String[] linesMig = PdfAnalysis.extractsPdfLines(MigPdf);
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
						outputfile.println("<OriginalLine><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(linesOrg[j]) + "]]></OriginalLine>"); // TODO:
						// cannot
						// display
						// cyrillic
						// stuff
						outputfile.println("<MigrationLine><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(linesMig[j]) + "]]></MigrationLine>");
						differences++;

						int levenshtein = calcLevenshtein(linesOrg[j], linesMig[j]);
						if (levenshtein == 1) {
							findSpaceinLine(linesOrg[j], linesMig[j]);
						}
						sumlevenstheinlines = sumlevenstheinlines + levenshtein;
						outputfile.println("<LevenshteinDistance>" + levenshtein + "</LevenshteinDistance>");
						outputfile.println("</Details>");
					}
				}
				if (differences == 0) {
					outputfile.println("<ComparedItem>");
					outputfile.println("<FileOrg>" + utilities.fileStringUtilities.getFileName(OrgPdf) + "</FileOrg>");
					outputfile.println("<LinesLengthOrg>" + lenOrg + "</LinesLengthOrg>");
					outputfile.println("<FileMig>" + utilities.fileStringUtilities.getFileName(MigPdf) + "</FileMig>");
					outputfile.println("<LinesLengthMig>" + lenMig + "</LinesLengthMig>");
					outputfile.println("<PdfTwins>" + "true" + "</PdfTwins>");
					outputfile.println("</ComparedItem>");
				}

				else {
					outputfile.println("<ComparedItem>");
					outputfile.println("<FileOrg>" + utilities.fileStringUtilities.getFileName(OrgPdf) + "</FileOrg>");
					outputfile.println("<LinesLengthOrg>" + lenOrg + "</LinesLengthOrg>");
					outputfile.println("<FileMig>" + utilities.fileStringUtilities.getFileName(MigPdf) + "</FileMig>");
					outputfile.println("<LinesLengthMig>" + lenMig + "</LinesLengthMig>");
					outputfile.println("<PdfTwins>" + "false" + "</PdfTwins>");
					outputfile.println("<DifferentLines>" + differences + "</DifferentLines>");

					String wholeStringPdfOrg = PdfAnalysis.extractsPdfLinestoString(OrgPdf);
					String wholeStringPdfMig = PdfAnalysis.extractsPdfLinestoString(MigPdf);

					int levenshteinlines = calcLevenshtein(wholeStringPdfOrg, wholeStringPdfMig);
					outputfile.println("<LevenshteinPdf>" + levenshteinlines + "</LevenshteinPdf>");
					levenstheinfullPdf = levenshteinlines;

					if (levenstheinfullPdf != sumlevenstheinlines) {
						outputfile.println("<LineIrregularities>" + "very likely" + "</LineIrregularities>");
					} else {
						outputfile.println("<LineIrregularities>" + "not likely" + "</LineIrregularities>");
					}
					outputfile.println("</ComparedItem>");
				}
			}
		}
		outputfile.println("</PdfTwinTest>");
		outputfile.close();
	}

	private static void findSpaceinLine(String orgline, String migline) {
		// TODO Auto-generated method stub
		String[] orgArr = orgline.split(" ");
		String[] migArr = migline.split(" ");

		for (int n = 0; n < orgArr.length; n++) {
			if (!orgArr[n].equals(migArr[n])) {
				outputfile.println("<DifferentWordOrg><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(orgArr[n]) + "]]></DifferentWordOrg>");
				if (orgArr.length < migArr.length) {
					outputfile.println("<DifferentWordMig><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(migArr[n]) + " " + utilities.fileStringUtilities.reduceNULvalues(migArr[n + 1]) + "]]></DifferentWordMig>");
				} else {
					outputfile.println("<DifferentWordMig><![CDATA[" + utilities.fileStringUtilities.reduceNULvalues(migArr[n]) + "]]></DifferentWordMig>");
				}
				break;
			}
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
		JOptionPane.showMessageDialog(null, "PDF will not be examined due to invalidity, encryption or file size", pdf, JOptionPane.INFORMATION_MESSAGE);
		return false;
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
