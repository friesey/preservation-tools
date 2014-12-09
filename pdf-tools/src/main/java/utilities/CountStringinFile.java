package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class CountStringinFile {

	static String file;
	static String searchedString;
	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		String extension;
		int stringfound = 0;

		try {

			JOptionPane.showMessageDialog(null, "Please choose the file (\"txt\"; \"java\" or \"yml\") you want to examine with the File Browser Dialog", "User Input", JOptionPane.PLAIN_MESSAGE);

			file = utilities.FileBrowserDialog.chooseFile();

			searchedString = JOptionPane.showInputDialog(null, "Please enter Text that should be searched in Files", "Enter Text Mask", JOptionPane.PLAIN_MESSAGE);

			if (searchedString != null) {

				if (file != null) {

					String filename = FilenameUtils.getBaseName(file.toString());

					if (!filename.startsWith("~")) {
						extension = FilenameUtils.getExtension(file.toString()).toLowerCase();

						if ((extension.equals("txt")) || (extension.equals("java")) || (extension.equals("yml"))) {
							if (file.length() != 0)
							/**
							 * important because otherwise not yet closed
							 * outpufile causes neverending story
							 */
							{
								// TODO: There is a big performance
								// problem with too large Txt-Files, e.
								// g. more than 500 KB or a certain no.
								// of lines.
								BufferedReader txtreader = new BufferedReader(new FileReader(file));
								String line;
								while (null != (line = txtreader.readLine())) {
									if (line.contains(searchedString)) {
										stringfound++;
									}
								}
								txtreader.close();
							}
						}

						else {
							JOptionPane.showMessageDialog(null, "Not a txt or java File", "Findings", JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			} else {
				// System.out.println(files.get(i) +
				// " is not a PDF file");
			}

			if (stringfound == 0) {
				JOptionPane.showMessageDialog(null, "The searched String was not found", "Findings", JOptionPane.PLAIN_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "The searched String was found " + stringfound + " times", "Findings", JOptionPane.PLAIN_MESSAGE);
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "An exception occured " + e);
		}
	}
}
