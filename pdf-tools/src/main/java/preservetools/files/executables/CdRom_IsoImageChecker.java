package preservetools.files.executables;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class CdRom_IsoImageChecker {

	static String examinedCdRom;
	
	static String outputFolder;
	
	static String mimetype;
	
	static String extension;


	public static void main(String args[]) throws IOException {
		
		JOptionPane.showMessageDialog(null,"CD ROM Dialog","Please choose CD ROM Folder", JOptionPane.PLAIN_MESSAGE);

		examinedCdRom = preservetools.utilities.FolderBrowserDialog
				.chooseFolder();
		
		JOptionPane.showMessageDialog(null,"Output Folder","Please choose Folder where Outputfile will be created", JOptionPane.PLAIN_MESSAGE);

		
		outputFolder = preservetools.utilities.FolderBrowserDialog
				.chooseFolder();
				
		PrintWriter outputfile = new PrintWriter(new FileWriter(outputFolder + "//"
				+ "CdRomExecutableAnalysis.txt"));

		if (examinedCdRom != null) {

			ArrayList<File> files = preservetools.utilities.ListsFiles
					.getPaths(new File(examinedCdRom), new ArrayList<File>());

			for (int i = 0; i < files.size(); i++) {
				
				mimetype =  preservetools.files.GenericFileAnalysis.getFileExtension(files.get(i));
				
				extension = FilenameUtils.getExtension(files.get(i).toString());	
				
				outputfile.println("Mimetype: " + mimetype);
				outputfile.println("File-Extension: " + extension);		
				outputfile.println();

			}
		}
		
		outputfile.close();
	}
}
