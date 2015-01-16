package filetools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class XmlToCsv {
	
	public static String examinedFolder;
	
	public static void main(String args[]) throws IOException {
		
		examinedFolder = utilities.BrowserDialogs.chooseFolder();
		if (examinedFolder != null) {
			
			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
			
			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					
				}
				
				
			}
			
			
		}
	}

}
