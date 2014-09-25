package preservetools.files.executables;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class CdRom_IsoImageChecker {

	static String examinedCdRom;
	
	static String mimetype;
	
	static String extension;

	public static void main(String args[]) throws IOException {

		examinedCdRom = preservetools.utilities.FolderBrowserDialog
				.chooseFolder();

		if (examinedCdRom != null) {

			ArrayList<File> files = preservetools.utilities.ListsFiles
					.getPaths(new File(examinedCdRom), new ArrayList<File>());

			for (int i = 0; i < files.size(); i++) {
				
				mimetype =  preservetools.files.GenericFileAnalysis.getFileExtension(files.get(i));
				
				extension = FilenameUtils.getExtension(files.get(i).toString());	
				
				System.out.println("Mimetype: " + mimetype + "   File-Extension: " + extension);						

			}
		}
	}
}
