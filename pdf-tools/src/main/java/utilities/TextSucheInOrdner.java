package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class TextSucheInOrdner {

	public static String fileorfolder;
	static String searchedString;
	static PrintWriter outputfile;
	static int stringfound;
	static String extension;

	static int MAXIMAL_HITS = 50;

	// implemented non-context-sensitive for methods
	// searchforStringinSimpleFiles and searchforStringinPdfFiles

	public static void main(String args[]) throws IOException {				
		stringfound = 0;		
		try {
			fileorfolder = utilities.BrowserDialogs.chooseFileOrFolder();
			if (fileorfolder != null) {
				searchedString = JOptionPane.showInputDialog(null, "Bitte geben Sie den gesuchten Text ein. Gross- und Kleinschreibung wird nicht beachtet", "Eingabemaske", JOptionPane.PLAIN_MESSAGE);
				if ((searchedString == null) || (searchedString.length() == 0)) {
					JOptionPane.showMessageDialog(null, "You have not typed in any text", "Misbehaviour. Program stopped.", JOptionPane.PLAIN_MESSAGE);
				} else {
					if (new File(fileorfolder).isDirectory()) {
						searchStringinFolder(fileorfolder);
					} else if (new File(fileorfolder).isFile()) {
						searchStringinFile(fileorfolder);
					}
					if (stringfound == 0) {
						JOptionPane.showMessageDialog(null, "The searched String was not found", "Findings", JOptionPane.PLAIN_MESSAGE);
						outputfile.println("<StringWasNotFound>");
						outputfile.println("<OccuranceSearchedTest>" + stringfound + "</OccuranceSearchedTest>");
						outputfile.println("</StringWasNotFound>");
					} else if (stringfound == MAXIMAL_HITS) {
						JOptionPane.showMessageDialog(null, "The searched String was found at least " + MAXIMAL_HITS + " times. Search stopped", "Findings", JOptionPane.PLAIN_MESSAGE);
						outputfile.println("<SearchedStopped>Der gesuchte Text ist mehr als 50 Mal in den gesuchten Dateien vorhanden</SearchedStopped>");
					} else {
						JOptionPane.showMessageDialog(null, "The searched String was found " + stringfound + " times", "Findings", JOptionPane.PLAIN_MESSAGE);
						outputfile.println("<OccuranceSearchedTest>" + stringfound + "</OccuranceSearchedTest>");
					}

					outputfile.println("</Textsuche>");
					outputfile.close();
				}
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "An exception occured " + e);
		}
	}

	public static void searchStringinFile(String file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		String[] parts = file.split(Pattern.quote("\\"));
		for (int i = 0; i < parts.length - 1; i++) {
			stringBuilder.append(parts[i]);
			stringBuilder.append("//");
		}
		String finalString = stringBuilder.toString();
		outputfile = new PrintWriter(new FileWriter(finalString + "ErgebnisTextSuche" + ".xml"));

		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"TextSucheTagStyle.xsl\"?>";

		String xsltLocation = finalString + "TextSucheTagStyle.xsl";

		output.XslStyleSheets.TextSucheCustomizedXsl(xsltLocation);

		outputfile.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		outputfile.println(xsltStyleSheet);
		outputfile.println("<Textsuche>");
		outputfile.println("<Datei>");
		// outputfile.println("<Durchsucht =\"file\">" + fileorfolder +
		// "</Durchsucht>");
		outputfile.println("<Durchsucht>" + fileorfolder + "</Durchsucht>");
		outputfile.println("<Art>File</Art>");
		outputfile.println("<AnzahlDateien>" + "1" + "</AnzahlDateien>");
		outputfile.println("<GesuchterText>" + searchedString + "</GesuchterText>");

		String filename = FilenameUtils.getBaseName(file);
		if (!filename.startsWith("~")) {
			extension = FilenameUtils.getExtension(file.toString()).toLowerCase();
			System.out.println(extension);
			if ((extension.equals("txt")) || (extension.equals("java")) || (extension.equals("yml"))) {
				if (fileorfolder.length() != 0)
				/**
			 * neverending story
				 */
				{
					// TODO: There is a big performance
					// problem with too large Txt-Files, e.
					// g. more than 500 KB or a certain no.
					// of lines.
					File examinedfile = new File(file);
					searchforStringinSimpleFiles(examinedfile);
				}
			}

			else if (extension.equals("pdf")) {
				File pdffile = new File(fileorfolder);
				searchforStringinPdfFiles(pdffile);
			}

			else {
				JOptionPane.showMessageDialog(null, "Search for String is not implemented yet for this kind of file format.", "Findings", JOptionPane.PLAIN_MESSAGE);
				return; // TODO: this is not very elegant and
						// has to be improved
			}
		}

		outputfile.println("</Datei>");
	}

	public static void searchStringinFolder(String folder) throws IOException {

		outputfile = new PrintWriter(new FileWriter(folder + "//" + "ErgebnisTextSuche" + ".xml"));

		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"TextSucheTagStyle.xsl\"?>";

		String xsltLocation = folder + "//" + "TextSucheTagStyle.xsl";
		output.XslStyleSheets.TextSucheCustomizedXsl(xsltLocation);

		outputfile.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		outputfile.println(xsltStyleSheet);
		outputfile.println("<Textsuche>");
		// outputfile.println("<Durchsucht = \"folder\">" + fileorfolder +
		// "</Durchsucht>");
		outputfile.println("<Durchsucht>" + fileorfolder + "</Durchsucht>");
		outputfile.println("<Art>Folder</Art>");

		ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
		int anzahlDateien = files.size();
		outputfile.println("<AnzahlDateien>" + anzahlDateien + "</AnzahlDateien>");
		outputfile.println("<GesuchterText>" + searchedString + "</GesuchterText>");

		if (files != null) {
			for (int i = 0; i < files.size(); i++) {
				String filename = FilenameUtils.getBaseName(files.get(i).toString());
				if (!filename.startsWith("~")) {
					outputfile.println("<Datei>");
					extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();
					if ((extension.equals("txt")) || (extension.equals("java")) || (extension.equals("yml"))) {
						// TODO: add more extensions that can be
						// searched by a simple BufferedReader
						searchforStringinSimpleFiles(files.get(i));
					} else if (extension.equals("pdf")) {
						searchforStringinPdfFiles(files.get(i));
					} else {
						outputfile.println("<FileExtension>" + extension + "</FileExtension>");
						outputfile.println("<Dateiname><![CDATA[" + files.get(i).getName() + "]]></Dateiname>");
						outputfile.println("<Suchergebnis>" + "nicht durchsucht" + "</Suchergebnis>");
					}

					outputfile.println("</Datei>");
				}
			}
		}
	}

	// TODO: Add other file formats, e. g. MS Word to
	// search for string there, too

	// else if "doc"

	// else if "xls"

	// else if "xlsx"

	// else if "ppt"

	// else if "xml"

	// else if "html"

	// else if "pptx"

	public static void searchforStringinPdfFiles(File file) throws IOException {

		outputfile.println("<Dateiname><![CDATA[" + (file.getName()) + "]]></Dateiname>");

		int trefferinDatei;

		if (filetools.pdf.PdfAnalysis.testPdfOk(file)) {
			try {
				PdfReader reader = new PdfReader(file.toString());
				int pagesPdf = reader.getNumberOfPages();
				StringBuffer buff = new StringBuffer();
				String ExtractedText = null;
				PdfReaderContentParser parser = new PdfReaderContentParser(reader);
				TextExtractionStrategy strategy;

				trefferinDatei = 0;
				for (int i = 1; i <= pagesPdf; i++) {
					strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
					ExtractedText = strategy.getResultantText().toString();
					buff.append(ExtractedText + "\n");
					String[] LinesArray = buff.toString().split("\n");
					int linesPdf = LinesArray.length;

					for (int j = 0; (j < linesPdf && (stringfound < MAXIMAL_HITS)); j++) {
						String paragraph = LinesArray[j].toLowerCase();
						String searchStringlowerCase = searchedString.toLowerCase();
						if (paragraph.contains(searchStringlowerCase)) {
							trefferinDatei++;
							stringfound++;
							outputfile.println("<Seitenzahl>" + i + "</Seitenzahl>");
							outputfile.println("<GanzeZeile>" + (LinesArray[j]) + "</GanzeZeile>");
						}
					}

				}
				outputfile.println("<TextinDatei>" + trefferinDatei + "</TextinDatei>");
				outputfile.println("<Suchergebnis>" + trefferinDatei + " x " + "</Suchergebnis>");

				reader.close();

			} catch (Exception e) {
				outputfile.println("<Fehlermeldung>" + e + "</Fehlermeldung>");
			}
		}

	}

	public static void searchforStringinSimpleFiles(File file) throws IOException {
		if (file.length() != 0)
			/**
			 * important because otherwise not yet closed outputfile causes
			 * neverending story
			 */

			outputfile.println("<Dateiname>" + (file.getName()) + "</Dateiname>");
		{
			// TODO: There is a big performance
			// problem with too large Txt-Files,
			// e.
			// g. more than 500 KB or a certain
			// no.
			// of lines.
			BufferedReader txtreader = new BufferedReader(new FileReader(file));
			String line;
			int trefferinDatei = 0;
			while (null != (line = txtreader.readLine()) && (stringfound < MAXIMAL_HITS)) {
				String linelowercase = line.toLowerCase();
				String searchStringlowerCase = searchedString.toLowerCase();
				if (linelowercase.contains(searchStringlowerCase)) {
					trefferinDatei++;
					stringfound++;
					outputfile.println("<GanzeZeile>" + (line) + "</GanzeZeile>");
				}
			}
			outputfile.println("<TextinDatei>" + trefferinDatei + "</TextinDatei>");
			outputfile.println("<Suchergebnis>" + trefferinDatei + " x gefunden" + "</Suchergebnis>");
			txtreader.close();
		}
	}
}
