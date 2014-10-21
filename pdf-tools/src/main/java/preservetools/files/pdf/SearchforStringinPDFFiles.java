package preservetools.files.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SearchforStringinPDFFiles {
	
	static String folder;

	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {
		try {
		
		folder = preservetools.utilities.FolderBrowserDialog.chooseFolder();
		
		if (folder != null) {
			
	
				ArrayList<File> files = preservetools.utilities.ListsFiles
						.getPaths(new File(folder),
								new ArrayList<File>());
				if (files == null) {
					
				}
					
					
		}
		}

			catch (FileNotFoundException e) {
				System.out.println(e);

			}
}
}