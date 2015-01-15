package filetools.pdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;

public class PdfInformationExtractionDSA {
	public static String examinedFolder;
	static PrintWriter informationExtraction;
	static String SEPARATOR = ";";
	static String MISSING_VALUE = "";
	static PrintWriter outputCsv;

	public static void main(String args[]) throws IOException {
		try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();
			if (examinedFolder != null) {
				informationExtraction = new PrintWriter(new FileWriter(examinedFolder + "//" + "InformationExtract" + ".xml"));
				String xmlVersion = "xml version='1.0'";
				String xmlEncoding = "encoding='ISO-8859-1'";
				String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"InformationExtractionXsl.xsl\"?>";
				output.XslStyleSheets.InformationExtractionXsl();
				informationExtraction.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
				informationExtraction.println(xsltStyleSheet);
				informationExtraction.println("<Information>");
				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
				if (files == null)
					return;
				String mimetype;
				PdfReader reader;
				for (int i = 0; i < files.size(); i++) {
					mimetype = filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
					if (mimetype != null) {
						if (mimetype.equals("application/pdf")) {
							reader = new PdfReader(files.get(i).toString());
							if (!reader.isEncrypted()) {
								informationExtraction.println("<File>");
								informationExtraction.println("<FileName>" + files.get(i).getName().toString() + "</FileName>");
								int pages = reader.getNumberOfPages();
								informationExtraction.println("<PdfPages>" + pages + "</PdfPages>");
								createXml(files.get(i));
								informationExtraction.println("</File>");
							}
						}
					}
				}
				informationExtraction.println("</Information>");
				informationExtraction.close();

				for (int i = 0; i < files.size(); i++) {
					mimetype = filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
					if (mimetype != null) {
						if (mimetype.equals("application/pdf")) {
							System.out.println(files.get(i).toString());
							createTableforC1(files.get(i));
						}
					}
				}

			}
		} catch (Exception e) {
		}
	}

	public static void createXml(File file) throws DocumentException, IOException {
		Document document = new Document();
		document.open();
		String[] lines = PdfAnalysis.extractsPdfLines(file.toString());
		// stringBuilder.append();
		// stringBuilder.toString();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].contains("Repository:")) {
				String[] parts = lines[i].split(":");
				informationExtraction.println("<Repository>" + parts[1] + "</Repository>");
				//
			}
			if (lines[i].contains("Seal Acquiry Date:")) {
				String[] parts = lines[i].split(":");
				informationExtraction.println("<SealAcquiryDate>" + parts[1] + "</SealAcquiryDate>");
				return;
			}
			// createTableforC1(lines, repository);
			// System.out.println (lines[i]);
		}
	}

	private static void createTableforC1(File file) throws IOException {

		String filename = file.getName().toString();
		int len = (filename.length() - 4);
		filename = filename.substring(0, len);

		File directory = new File(examinedFolder + "//" + filename);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		outputCsv = new PrintWriter(new FileWriter(directory.toString() + "//" + "Criterium1.csv"));
		String[] lines = PdfAnalysis.extractsPdfLines(file.toString());

		StringBuilder stringBuilder = new StringBuilder();

		String repositoryName = "Repository Name";
		String implementation = "Implementation";
		String criterium1 = "Criterium 1";

		outputCsv.println(repositoryName + SEPARATOR + implementation + SEPARATOR + criterium1);

		for (int j = 0; j < lines.length; j++) {
			if ((!lines[j].contains("Minimum Required ") && (lines[j].contains("Statement of Compliance")))) {
				implementation = (lines[j + 1]);
				repositoryName = filename;
				criterium1 = "answer";
				outputCsv.println(repositoryName + SEPARATOR + implementation + SEPARATOR + criterium1);
				outputCsv.close();
				return;
			}
			System.out.println("Test");
			/*
			 * if (lines[j].contains("Applicant Entry")) { do { j++;
			 * stringBuilder.append(lines[j]); } while
			 * (!lines[j].contains("Applicant Entry"));
			 */
		}

		// outputCsv.println (repository + SEPARATOR +
		// stringBuilder.toString());

	}

}
