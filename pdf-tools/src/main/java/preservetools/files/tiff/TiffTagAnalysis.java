package preservetools.files.tiff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TiffTagAnalysis {

	static String examinedFolder;
	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		try {

			examinedFolder = preservetools.utilities.FolderBrowserDialog.chooseFolder();
			
			 if (examinedFolder != null) {
				 
				 ArrayList<File> files = preservetools.utilities.ListsFiles.getPaths(new File(examinedFolder),
						 new ArrayList<File>());
				 
				 for (int i = 0; i < files.size(); i++) {
					 System.out.println(files.get(i).getCanonicalPath());					 
					 
				 }				 
				 
			 }
			

		} catch (FileNotFoundException e) {
		}

	}

}
