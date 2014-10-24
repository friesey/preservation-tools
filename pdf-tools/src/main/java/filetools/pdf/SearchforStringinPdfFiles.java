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
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class SearchforStringinPdfFiles {

	static String folder;
	static String searchedString;
	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		String extension;
		int stringfound = 0;

		try {

			folder = utilities.FolderBrowserDialog.chooseFolder();

			PrintWriter outputfile = new PrintWriter(new FileWriter(folder + "//" + "SearchForString" + ".txt"));

			searchedString = JOptionPane.showInputDialog(null, "Please enter String that should be searched in the PDF Files", "Enter String Mask", JOptionPane.PLAIN_MESSAGE);

			if (searchedString != null) {

				if (folder != null) {

					ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
					if (files != null) {

						for (int i = 0; i < files.size(); i++) {						
							
							String filename = FilenameUtils.getBaseName(files.get(i).toString());
									
							if (!filename.startsWith("~")) {

								extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();

								if (extension.equals("pdf")) {
									if (filetools.pdf.PdfAnalysis.testPdfOk(files.get(i))) {

										String[] linesPdf = PdfAnalysis.extractsPdfLines(files.get(i).toString());
										if (linesPdf != null) {
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
									}
								}
								else if (extension.equals("docx")) {
									InputStream wordstream = new FileInputStream(files.get(i));
									XWPFDocument wordfile = new XWPFDocument(wordstream);

									List<XWPFParagraph> allparagraphs = wordfile.getParagraphs();
									int parCount = allparagraphs.size();

									for (int n = 0; n < parCount; n++) {
										String line = allparagraphs.get(n).getParagraphText();

										if (line.contains(searchedString)) {
											stringfound++;
											outputfile.println();
											outputfile.println(files.get(i));
											outputfile.println("Searched String found in paragraph: " + n);
											outputfile.println(line);
											outputfile.println();
										}
									}
								}
								
								//commit
								
								// else if "doc"
								
								// else if "xls"
								
								// else if "xlsx"
								
								// else if "ppt"
								
								// else if "txt"
								
								// else if "xml"
								
								// else if "html"
								
								// else if "pptx"
								
								
								else {
									System.out.println(files.get(i).toString());
								}

								
								
								
							}
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

			if (stringfound == 0) {
				outputfile.println("The searched String was not found");
			}
			outputfile.println("The searched String was found in " + stringfound + " paragraphs");
			outputfile.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
}