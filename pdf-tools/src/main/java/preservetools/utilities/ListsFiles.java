package preservetools.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ListsFiles {
	/**
	 * lists all files and directories in given directory
	 * 
	 * @param
	 * @return: ArrayList<File> of all found files and subfolders
	 * @throws IOException 
	 * 
	 */
	public static ArrayList<File> getPaths(File file, ArrayList<File> list) throws IOException {
		if (file == null || list == null || !file.isDirectory())
			return null;
		File[] fileArr = file.listFiles();
		for (File f : fileArr) {
			// TODO If a folder is chosen that cannot be searched/read, e. g.
			// C:/, the tool runs into issues
			if (f.isDirectory()) {
				getPaths(f, list);
			}
			if (!f.isDirectory()){ //adds only non-directories (=files) to the ArrayList of Files
				String extension = preservetools.files.GenericFileAnalysis.getFileExtension(f);
				
				if (extension.equals("zip")){
					 ZipFile zf = new ZipFile (f.toString());
					 Enumeration<? extends ZipEntry> e = zf.entries(); 
					 ZipEntry ze; 
					 while(e.hasMoreElements()){
			                ze = e.nextElement();
			               // list.add(ze.getName());
			                System.out.println(ze.getName()); 				
					 }
					 zf.close();
				}
				else {
			list.add(f);}					
		}		
	}
		return list;
	}
}

