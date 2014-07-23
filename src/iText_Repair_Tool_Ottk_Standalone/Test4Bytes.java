package iText_Repair_Tool_Ottk_Standalone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class Test4Bytes {
	
	static String t;
	static BufferedReader PdfHeaderTest;
	
	public static void main (String args[]) throws IOException {		
		
		// GUI Folder Browser Dialog
		JFileChooser j = new JFileChooser();
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		j.showOpenDialog(j);
		t = j.getSelectedFile().getPath();	
		
		
		ArrayList<File> files = getPaths(new File(t),
	                new ArrayList<File>()); 
		if (files == null) return;
		
		try {
			for (int i = 0; i < files.size(); i++)
				if (!files.get(i).isDirectory()) {				
				System.out.println(files.get(i).getCanonicalPath());		
				if (FileHeaderTest (files.get(i))== true) {
					 System.out.println (files.get(i).getName() + " is a PDF file and has a PDF header");
				}					
				else {
					 System.out.println (files.get(i).getName() + " PDF Header is missing.");
				}
			}
		}
		catch (IOException e) {
			System.out.print(e);
		}
		
		PdfHeaderTest.close();
		
	}
	
			// tests the FileHeader
	private static boolean FileHeaderTest(File file) throws IOException {
		PdfHeaderTest = new BufferedReader (new FileReader(file));	  
	 
		String FileHeader = PdfHeaderTest.readLine();	 
		// System.out.println (FileHeader);
				 
		 if (FileHeader.contains("%PDF")) {			
			return true;
		 }	 			
		 else {
			 return false;
		 }		 	 
		}

	//lists all files and directories in given directory
    private static ArrayList<File> getPaths(File file, ArrayList<File> list) {
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
}
