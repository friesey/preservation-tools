package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class SearchforStringinjavaFiles {
	
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
											
														 if ((extension.equals("txt")) || (extension.equals("java"))) {
									if (files.get(i).length() != 0)/*
																	 * important
																	 * because
																	 * otherwise
																	 * not yet
																	 * closed
																	 * outpufile
																	 * causes
																	 * neverending
																	 * story
																	 */{
										// TODO: There is a big performance
										// problem with too large Txt-Files, e.
										// g. more than 500 KB or a certain no.
										// of lines.
										BufferedReader txtreader = new BufferedReader(new FileReader(files.get(i)));
										String line;
										while (null != (line = txtreader.readLine())) {
											if (line.contains(searchedString)) {
												stringfound++;
												outputfile.println();
												outputfile.println(files.get(i));
												outputfile.println(line);
												outputfile.println();
											}
										}
										txtreader.close();
									}
								}

								else {
									System.out.println(files.get(i).toString());
								}
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

			if (stringfound == 0) {
				outputfile.println("The searched String was not found");
			}
			outputfile.println("The searched String was found in " + stringfound + " paragraphs");
			outputfile.close();
			}
		 catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
}


