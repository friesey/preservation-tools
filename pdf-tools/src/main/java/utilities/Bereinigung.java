package utilities;

import java.io.File;
import com.google.common.io.Files;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Bereinigung {

	static String examinedFolder;

	public static void main(String args[]) throws Exception {

		JOptionPane.showMessageDialog(null, "Please choose a Folder", "Bereinigung", JOptionPane.QUESTION_MESSAGE);
		examinedFolder = utilities.BrowserDialogs.chooseFolder();
		if (examinedFolder != null) {
			
			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
			
			for (int i = 0; i < files.size(); i++) {
			
			
			String editString = files.get(i).getAbsolutePath().toString();				
			
		//		String editedString = editString.replace(".tif", "");
			
		String editedString = editString + ".tif";
							
			
			
			new File(files.get(i).toString()).renameTo(new File(editedString));
			
				
			}
		}
	}
}
