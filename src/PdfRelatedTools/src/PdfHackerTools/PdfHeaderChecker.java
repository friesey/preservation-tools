package PdfHackerTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PdfHeaderChecker {
	
	static String t;	
	
	public static void main (String args[]) throws IOException {		
		
		t= PdfUtilities.ChooseFolder();	
				
		ArrayList<File> files = PdfUtilities.getPaths(new File(t), new ArrayList<File>()); 
		if (files == null) return;
		
		try {
			for (int i = 0; i < files.size(); i++)
				if (!files.get(i).isDirectory()) {				
				System.out.println(files.get(i).getCanonicalPath());		
				if (PdfUtilities.FileHeaderTest (files.get(i))== true) {
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
		PdfUtilities.PdfHeaderTest.close();		
	}
}
