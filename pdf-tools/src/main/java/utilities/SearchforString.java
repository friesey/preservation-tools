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

import filetools.pdf.PdfAnalysis;

public class SearchforString {

	static String fileorfolder;
	static String searchedString;
	static PrintWriter outputfile;
	static int stringfound;
	static String extension;

	static int MAXIMAL_HITS = 50;

	public static void main(String args[]) throws IOException {
		stringfound = 0;
		try {
			fileorfolder = utilities.BrowserDialogs.chooseFileOrFolder();
			if (fileorfolder != null) {
				searchedString = JOptionPane.showInputDialog(null, "Please enter String that should be searched in the file or folder", "Enter String Mask", JOptionPane.PLAIN_MESSAGE);
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
						outputfile.println("The searched String was not found");
					} else if (stringfound == MAXIMAL_HITS) {
						JOptionPane.showMessageDialog(null, "The searched String was found at least " + MAXIMAL_HITS + " times. Search stopped", "Findings", JOptionPane.PLAIN_MESSAGE);
						outputfile.println("The searched String was found at least " + MAXIMAL_HITS + " times. Search stopped");
					} else {
						JOptionPane.showMessageDialog(null, "The searched String was found " + stringfound + " times", "Findings", JOptionPane.PLAIN_MESSAGE);
						outputfile.println("The searched String was found in " + stringfound + " paragraphs");
					}
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
		outputfile = new PrintWriter(new FileWriter(finalString + "SearchForString" + ".txt"));
		String filename = FilenameUtils.getBaseName(file);
		if (!filename.startsWith("~")) {
			extension = FilenameUtils.getExtension(file.toString()).toLowerCase();
			if ((extension.equals("txt")) || (extension.equals("java")) || (extension.equals("yml"))) {
				if (fileorfolder.length() != 0)
				/**
				 * important because otherwise not yet closed outpufile causes
				 * neverending story
				 */
				{
					// TODO: There is a big performance
					// problem with too large Txt-Files, e.
					// g. more than 500 KB or a certain no.
					// of lines.
					File examinedfile = new File(file);
					searchforStringinSimpleFiles(examinedfile);
				} else if (extension.equals("pdf")) {
					File pdffile = new File(fileorfolder);
					searchforStringinPdfFiles(pdffile);
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Search for String is not implemented yet for this kind of file format.", "Findings", JOptionPane.PLAIN_MESSAGE);
				return; // TODO: this is not very elegant and
						// has to be improved
			}
		}
	}

	public static void searchStringinFolder(String folder) throws IOException {
		outputfile = new PrintWriter(new FileWriter(folder + "//" + "SearchForString" + ".txt"));
		ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
		if (files != null) {
			for (int i = 0; i < files.size(); i++) {
				String filename = FilenameUtils.getBaseName(files.get(i).toString());
				if (!filename.startsWith("~")) {
					extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();
					if ((extension.equals("txt")) || (extension.equals("java")) || (extension.equals("yml"))) {
						// TODO: add more extensions that can be
						// searched by a simple BufferedReader
						searchforStringinSimpleFiles(files.get(i));
					} else if (extension.equals("pdf")) {
						searchforStringinPdfFiles(files.get(i));
					}
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
		if (filetools.pdf.PdfAnalysis.testPdfOk(file)) {
			String[] linesPdf = PdfAnalysis.extractsPdfLines(file.toString());
			if (linesPdf != null) {
				int lenlines = linesPdf.length;
				for (int j = 0; (j < lenlines && (stringfound < MAXIMAL_HITS)); j++) {
					if ((linesPdf[j]).contains(searchedString)) {
						stringfound++;
						outputfile.println();
						outputfile.println(file.toString());
						outputfile.println(linesPdf[j]);
						outputfile.println();
					}
				}
			}
		}
	}

	public static void searchforStringinSimpleFiles(File file) throws IOException {
		if (file.length() != 0)
		/**
		 * important because otherwise not yet closed outputfile causes
		 * neverending story
		 */
		{
			// TODO: There is a big performance
			// problem with too large Txt-Files,
			// e.
			// g. more than 500 KB or a certain
			// no.
			// of lines.
			BufferedReader txtreader = new BufferedReader(new FileReader(file));
			String line;
			while (null != (line = txtreader.readLine()) && (stringfound < MAXIMAL_HITS)) {
				if (line.contains(searchedString)) {
					stringfound++;
					outputfile.println();
					outputfile.println(file);
					outputfile.println(line);
					outputfile.println();
				}
			}
			txtreader.close();
		}
	}
}
