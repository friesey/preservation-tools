package iText_Repair_Tool_Ottk_Standalone;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFileChooser;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class Main_Standalone {
	
	static String t;
	
	public static void main (String args[]) {
		
		// GUI Folder Browser Dialog
				JFileChooser j = new JFileChooser();
				j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				j.showOpenDialog(j);
				t = j.getSelectedFile().getPath();
				
				ArrayList<File> files = getPaths(new File(t),
			                new ArrayList<File>()); 
				if (files == null) return;
				String extension;
				PdfReader reader;
				try {
					for (int i = 0; i < files.size(); i++)
						//prints out only files and not the subdirectories as well
						if (!files.get(i).isDirectory()) {
						
						System.out.println(files.get(i).getCanonicalPath());
						extension = Files.probeContentType(files.get(i).toPath());
						// System.out.printf (extension + "\n");
						if (extension.equals("application/pdf")) {
							System.out.println(files.get(i));							
							try						
							{
								reader = new PdfReader(files.get(i).toString());								
								if (!reader.isEncrypted())									
								{							
									RepairWithItext(reader, files.get(i).getName());
								}
							}
							
							catch(Exception e)
							{
								System.out.println(e);
							}
						}
						}
						
				} catch (IOException e){
					e.printStackTrace();
				}         			
					}		
	
			 static void RepairWithItext(PdfReader reader, String filename) throws DocumentException, IOException {
				 
				 Map info = reader.getInfo();
				 Document document = new Document();
				 
				 PdfCopy copy = new PdfCopy(document, new FileOutputStream (t + "//" + "Mig_iText" + filename));
				 copy.setPDFXConformance(PdfWriter.PDFA1B);
		            if (info.get("Title")!=null) document.addTitle((String)info.get("Title"));
		            if (info.get("Author")!=null)document.addAuthor((String)info.get("Author"));
		            if (info.get("Keywords")!=null)document.addKeywords((String)info.get("Keywords")); 
		            copy.createXmpMetadata();
		            document.open();
		            int n = reader.getNumberOfPages();
		            for (int i = 0; i < n;) {
		                copy.addPage(copy.getImportedPage(reader, ++i));
		            }		            
		            copy.freeReader(reader);
		            document.close();
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


