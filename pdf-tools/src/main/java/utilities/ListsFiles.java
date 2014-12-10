package utilities;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ListsFiles {
	/**
	 * lists all files and directories in given directory
	 *
	 * @param
	 * @return: ArrayList<File> of all found files and subfolders
	 *
	 */
	public static ArrayList<File> getPaths(File file, ArrayList<File> list) {
		
		try {
		if (file == null || list == null || !file.isDirectory())
			return null;
		File[] fileArr = file.listFiles();
		for (File f : fileArr) {			
			// TODO If a folder is chosen that cannot be searched/read, e. g.
			// C:/, the tool runs into issues
			if (!f.isDirectory()) {				
				//should not add directories to the ArrayList of files
				list.add(f);
			} else {				
				getPaths(f, list);
			}
		}
		return list;
	}
		catch (Exception e) {		
			JOptionPane.showMessageDialog(null, "Most likely you tried to choose a folder like \"C:\" but you do not have the rights to read files there.", "Info Message", JOptionPane.PLAIN_MESSAGE);
		return null;		
	}
}
}