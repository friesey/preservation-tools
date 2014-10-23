package preservetools.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class SearchCertainFormat {

	static String examinedFolder;

	public static void main(String args[]) throws IOException {

		try {
			JOptionPane.showMessageDialog(null, "CD ROM Dialog",
					"Please choose Folder",
					JOptionPane.QUESTION_MESSAGE);
			examinedFolder = preservetools.utilities.FolderBrowserDialog
					.chooseFolder();

			if (examinedFolder != null) {

				ArrayList<File> files = preservetools.utilities.ListsFiles
						.getPaths(new File(examinedFolder),
								new ArrayList<File>());

				for (int i = 0; i < files.size(); i++) {
					
					String extension = FilenameUtils.getExtension(
							files.get(i).toString()).toLowerCase();
					
					String mimetype = filetools.GenericFileAnalysis
							.getFileMimeType(files.get(i));
					
					if (extension != null) {
						if (extension.equals("wav")) {
							System.out.println(files.get(i));
						}
					}
					
					if (mimetype != null) {
						if (mimetype.contains("audio")) {
							System.out.println(files.get(i));
						}
					}
					
					else {
					//	System.out.println ("another extension");
					}

				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
