package utilities;

import java.io.File;
import java.util.ArrayList;

public class ListsFiles {
	/**
	 * lists all files and directories in given directory
	 *
	 * @param
	 * @return: ArrayList<File> of all found files and subfolders
	 *
	 */
	public static ArrayList<File> getPaths(File file, ArrayList<File> list) {
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
}