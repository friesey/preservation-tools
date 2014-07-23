package iText_Repair_Tool_Ottk_Standalone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;

import org.apache.pdfbox.pdmodel.PDDocument;
import com.itextpdf.text.pdf.PdfReader;

public class CreationSoftwareDetective {
	
	static String t;
	static int  ProducerID;
	static ArrayList<String> ProducerType;
	static BufferedReader PdfHeaderTest;
	static PdfReader reader;
	static PrintWriter outputfile;

public static void main (String args[]) throws IOException {
	
	// GUI Folder Browser Dialog
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			j.showOpenDialog(j);
			t = j.getSelectedFile().getPath();
			ProducerID = 0;
			ProducerType = new ArrayList<String>();
			
			ArrayList<File> files = getPaths(new File(t),
		                new ArrayList<File>()); 
			if (files == null) return;
			
			String extension;
			
			outputfile = new PrintWriter(new FileWriter(t + "//"+"CreationSoftwareDetective.txt"));
			
			try {
				for (int i = 0; i < files.size(); i++)
					
					if (files.get(i) != null) {	 //maybe not necessary
					//prints out only files and not the subdirectories as well
					if (!files.get(i).isDirectory()) {								
					
						//null pointer exception
						
					extension = Files.probeContentType(files.get(i).toPath());			
					if (extension.equals("application/pdf")) {
						if (FileHeaderTest(files.get(i)) == true){								
						
				try {
						PDDocument testfile = PDDocument.load(files.get(i));						
						if (EncryptionTest(testfile) == false) {				
						{
							reader = new PdfReader(files.get(i).toString());								
						    GetProducer(reader, files.get(i).getName());	
						    reader.close();
						    
						}				
						
						}
						testfile.close(); 
						}	
						
				catch (IOException e) {
							
							outputfile.println (files.get(i) + " is so damaged it cannot be parsed: " + e);
						}			
															
					}
					}				
					}
			}
			}
					
			catch (IOException e){
					e.printStackTrace();
			}       				
			// redundante Einträge entfernen			
			HashMap<String, String> hmTemp = new HashMap<String, String>();
			for(String item : ProducerType) {
			    hmTemp.put(item, item);
			}
			ProducerType.clear();
			ProducerType.addAll(hmTemp.keySet());
			Collections.sort(ProducerType);			
			
			
			
			for(String item : ProducerType) {			
				outputfile.println(item);
			}		
			
			// in case no PDF-files are found, the outputfile comes out empty. Is this intended?
			outputfile.close();
			
		}	

		private static boolean EncryptionTest(PDDocument file) throws IOException {			
					
		//	 PDDocumentInformation info = PDDocument.load(file).getDocumentInformation();	
	
			
			if (file.isEncrypted() == true) {				
				outputfile.println (file + " is encrypted");
				return true;
			}
			else {						
				return false;			
				
			}
		}

		// tests the FileHeader
		private static boolean FileHeaderTest(File file) throws IOException {
	
			PdfHeaderTest = new BufferedReader (new FileReader(file));	  
						
			String FileHeader = PdfHeaderTest.readLine();	 			 
			if (FileHeader.contains("%PDF")) {			
				return true;
			}	 			
			else {
				return false;
				}		 	 
			}
	
		@SuppressWarnings("rawtypes")
		private static ArrayList<String> GetProducer(PdfReader reader, String name) {
			 Map info = reader.getInfo();
			 if (info.get("Producer")!=null) {
				// System.out.println(info.get("Producer"));			 
				 ProducerType.add(info.get("Producer").toString());	
					}						 		 		
			 return ProducerType;
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
