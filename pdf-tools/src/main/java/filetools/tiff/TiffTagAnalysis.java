package filetools.tiff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class TiffTagAnalysis {

	static String examinedFolder;
	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		try {

			examinedFolder = utilities.FolderBrowserDialog.chooseFolder();

			if (examinedFolder != null) {

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder),
						new ArrayList<File>());

				for (int i = 0; i < files.size(); i++) {
					System.out.println(files.get(i).getCanonicalPath());

					String tiffExtension = "TIF";

					String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath());
					if (extension.equals(tiffExtension)) {
						
						if (filetools.GenericFileAnalysis.testFileHeaderTiff(files.get(i))) {
							
							
							System.out.println ( files.get(i) + "is a tiff-file");
							
							
						}
						else {
							System.out.println ("This file purports to be a tiff-file. It has a .TIF-extension, but no lacks the magic number.");
							
							
						}
					}

				}

			}
		} catch (FileNotFoundException e) {
		}

	}
}
