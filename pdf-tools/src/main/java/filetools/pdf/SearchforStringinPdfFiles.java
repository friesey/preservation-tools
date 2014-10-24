package filetools.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class SearchforStringinPdfFiles {

	static String folder;

	static String searchedString;

	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		String extension;

		System.out.println("Test");

		int stringfound = 0;

		try {

			folder = utilities.FolderBrowserDialog.chooseFolder();

			PrintWriter outputfile = new PrintWriter(new FileWriter(folder + "//" + "SearchForString" + ".txt"));

			searchedString = JOptionPane.showInputDialog(null, "Please enter String that should be searched in the PDF Files", "Enter String Mask", JOptionPane.PLAIN_MESSAGE);

			if (searchedString != null) {

				if (folder != null) {

					ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
					if (files != null) {

						// TODO: All the PDF-ExceptionHandling is missing here
						// for lazy & hurry reasons

						for (int i = 0; i < files.size(); i++) {

							extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();

							if (extension.equals("pdf")) {

								String[] linesPdf = PdfAnalysis.extractsPdfLines(files.get(i).toString());

								int lenlines = linesPdf.length;

								for (int j = 0; j < lenlines; j++) {
									if ((linesPdf[j]).contains(searchedString)) {
										stringfound++;
										outputfile.println();
										outputfile.println(files.get(i));
										outputfile.println(linesPdf[j]);
										outputfile.println();
									}
								}
							}

							if ((extension.equals("doc")) || (extension.equals("docx"))) {
								InputStream wordstream = new FileInputStream(files.get(i));
								XWPFDocument wordfile = new XWPFDocument(wordstream);

								List<XWPFParagraph> allparagraphs = wordfile.getParagraphs();
								int parCount = allparagraphs.size();
								System.out.println(parCount);

								for (int n = 0; n < parCount; n++) {
									String line = allparagraphs.get(n).getParagraphText();

									if (line.contains(searchedString)) {
										stringfound++;
										outputfile.println();
										outputfile.println(files.get(i));
										outputfile.println("Searched String found in line/paragraph :" + n);
										outputfile.println(allparagraphs.get(n));
										outputfile.println();
									}
								}
							}

							// TODO: Add other file formats, e. g. MS Word to
							// search for string there, too

							else {
								// System.out.println(files.get(i) +
								// " is not a PDF file");
							}
						}
					}

				}
			}
			if (stringfound == 0) {
				outputfile.println("The searched String was not found");
			}
			outputfile.println("The searched String was found " + stringfound + " times");
			outputfile.close();
			System.out.println("File was closed");
		}

		catch (FileNotFoundException e) {
			System.out.println(e);

		}
	}
}