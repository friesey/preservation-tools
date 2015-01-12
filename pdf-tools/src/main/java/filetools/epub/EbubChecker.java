package filetools.epub;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EbubChecker {

	// TODO: Check if epub file

	public static void main(String args[]) throws IOException {

		String examinedFolder = utilities.BrowserDialogs
				.chooseFolder();
		String extension;

		if (examinedFolder != null) {

			ArrayList<File> files = utilities.ListsFiles
					.getPaths(new File(examinedFolder), new ArrayList<File>());

			try {
				for (int i = 0; i < files.size(); i++) {
					
					System.out.println(files.get(i).getCanonicalPath());
					
					extension =  filetools.GenericFileAnalysis.getFileMimeType(files.get(i));	

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
