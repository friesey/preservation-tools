package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class SearchCertainFormat {

	static String examinedFolder;

	static String searchedextension;

	public static void main(String args[]) throws IOException {

		try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();

			if (examinedFolder != null) {
				searchedextension = JOptionPane.showInputDialog(null, "Please enter File Extension that should be searched in folder", "Enter File Extension", JOptionPane.PLAIN_MESSAGE);
				searchedextension = searchedextension.toLowerCase();
				if ((searchedextension == null) || (searchedextension.length() == 0)) {
					JOptionPane.showMessageDialog(null, "You have not typed in any text", "Misbehaviour. Program stopped.", JOptionPane.PLAIN_MESSAGE);
				}

				else {
					ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

					for (int i = 0; i < files.size(); i++) {
						String extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();
						// String mimetype =
						// filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
						if (extension.equals(searchedextension)) {
							System.out.println(files.get(i).toString());
							// TODO: What to do with the found files? Copy?
							// Move? Print out somewhere?
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
