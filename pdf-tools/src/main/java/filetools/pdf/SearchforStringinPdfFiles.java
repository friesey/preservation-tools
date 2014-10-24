package filetools.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class SearchforStringinPdfFiles {

	static String folder;

	static String searchedString;

	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {
		
		String extension;
	
		System.out.println("Test");

		try {			
			
			folder = utilities.FolderBrowserDialog.chooseFolder();
			
			PrintWriter outputfile = new PrintWriter(new FileWriter(folder + "//"
					+ "SearchForString" + ".txt"));
			

			searchedString = JOptionPane.showInputDialog(null, "Please enter String that should be searched in the PDF Files", "Enter String Mask", JOptionPane.PLAIN_MESSAGE);

			if (searchedString != null) {

				if (folder != null) {

					ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
					if (files != null) {

						// TODO: All the PDF-ExceptionHandling is missing here
						// for lazy & hurry reasons

						for (int i = 0; i < files.size(); i++) {
							
							extension = FilenameUtils.getExtension(files.get(i).toString())
									.toLowerCase();

							if (extension.equals ("pdf")) {

								String[] linesPdf = PdfAnalysis.extractsPdfLines(files.get(i).toString());

								int lenlines = linesPdf.length;

								for (int j = 0; j < lenlines; j++) {
									if ((linesPdf[j]).contains(searchedString)) {
										outputfile.println();
										outputfile.println(files.get(i));
										outputfile.println(linesPdf[j]);
										//TODO: I wonder why the lines are so high. Is this calculating cumulative?
										outputfile.println("Line: " + j);
										outputfile.println();
									}
								}
							}
							
							//TODO: Add other file formats, e. g. MS Word to search for string there, too

							else {
							//	System.out.println(files.get(i) + " is not a PDF file");
							}
						}
					}

				}
			}
			
			outputfile.close();
			System.out.println("File was closed");
		}

		catch (FileNotFoundException e) {
			System.out.println(e);

		}
	}
}