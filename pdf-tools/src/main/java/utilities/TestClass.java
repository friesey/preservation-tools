package utilities;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class TestClass {

	public static String folderforzips;

	public static void main(String args[]) throws Exception {

		String folder = utilities.BrowserDialogs.chooseFolder();
		if (folder != null) {
			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

			folderforzips = utilities.FolderMethods.createnewFolder();

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();
				if (extension.equals("zip")) {
					System.out.println(files.get(i).toString() + " recognised zip");
					ArrayList<File> arrzips = new ArrayList<File>();
					utilities.ListsFiles.unpackzip(files.get(i));
				}
			}
		}
	}
}
