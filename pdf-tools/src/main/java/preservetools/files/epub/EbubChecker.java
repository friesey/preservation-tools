package preservetools.files.epub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class EbubChecker {

	// TODO: Check if epub file

	public static void main(String args[]) throws FileNotFoundException {

		String examinedFolder = preservetools.utilities.FolderBrowserDialog
				.chooseFolder();
		String extension;

		if (examinedFolder != null) {

			ArrayList<File> files = preservetools.utilities.ListsFiles
					.getPaths(new File(examinedFolder), new ArrayList<File>());

			try {
				for (int i = 0; i < files.size(); i++) {
					
					System.out.println(files.get(i).getCanonicalPath());
					
					extension =  preservetools.files.GenericFileAnalysis.getFileExtension(files.get(i));	

					if (extension.equals("application/epub")) {

					}

				}
			}

			catch (IOException e) {
				e.printStackTrace();

			}

		}

		else {
			System.out.println("No folder was chosen");
		}

	}
}
