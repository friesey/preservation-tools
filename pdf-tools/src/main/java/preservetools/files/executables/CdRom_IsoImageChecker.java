package preservetools.files.executables;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CdRom_IsoImageChecker {

	static String examinedCdRom;

	public static void main(String args[]) throws FileNotFoundException {

		examinedCdRom = preservetools.utilities.FolderBrowserDialog
				.chooseFolder();

		if (examinedCdRom != null) {

			ArrayList<File> files = preservetools.utilities.ListsFiles
					.getPaths(new File(examinedCdRom), new ArrayList<File>());

			for (int i = 0; i < files.size(); i++) {

			}
		}
	}
}
