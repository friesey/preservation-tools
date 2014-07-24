package PdfHackerTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PdfUtilities {
	
	/**
	 * Variables and objects used within the whole package
	 */
	
	static BufferedReader PdfHeaderTest;	
	
	
	/**
	 * Methods used within the whole package
	 */
	
	/**  
	 * lists all files and directories in given directory
	 * @param
	 * @return: ArrayList<File> of all found files and subfolders
	 * 
	 */	
    static ArrayList<File> getPaths(File file, ArrayList<File> list) {
        if (file == null || list == null || !file.isDirectory())
            return null;
        File[] fileArr = file.listFiles();
        for (File f : fileArr) {
            if (f.isDirectory()) {
                getPaths(f, list);
            }
            list.add(f);
        }
        return list; 	
    }

    
	/**  
	 * Tests if the first line of the file contains the proper PDF-Header "%PDF"
	 * @param Creates a PdfHeaderTest-Pdf-Reader and reads the first line of the PDF-file like an editor would do.
	 * @return: boolean false = no PDF-Header; true = first line contains PDF-Header
	*/	
    	static boolean FileHeaderTest(File file) throws IOException {
    		PdfHeaderTest = new BufferedReader (new FileReader(file));	 
    		String FileHeader = PdfHeaderTest.readLine();	 	
		 
    		if (FileHeader.contains("%PDF")) {			
    			return true;
    		}	 			
    		else {
    			return false;
    			}		 	 
    		}    
}
