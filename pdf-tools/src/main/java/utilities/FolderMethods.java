package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import com.google.common.io.Files;

public class FolderMethods {

	public static void main(String args[]) throws IOException {

		String folder = createnewFolder();
		System.out.println(folder);
	}

	// general access point to folder handling

	// TODO: generate Folder as an object which always needs a name for the
	// string and a question for the Message Dialog

	public static String createnewFolder() throws FileNotFoundException {
		try {
			String newfolder = null;
			String pathtonewfolder = null;
			String name = null;
			//
			// JOptionPane.showMessageDialog(null, "CD ROM Dialog",
			// "Please choose CD ROM Folder", JOptionPane.QUESTION_MESSAGE);
			// examinedCdRom = preservetools.utilities.FolderBrowserDialog
			// .chooseFolder();

			JOptionPane.showMessageDialog(null, "Please choose parent directory for the new folder", "Choose Parent Folder", JOptionPane.PLAIN_MESSAGE);

			String parentdirectory = utilities.BrowserDialogs.chooseFolder();
			name = JOptionPane.showInputDialog(null, "Please choose name for the new folder", "Enter String Mask", JOptionPane.PLAIN_MESSAGE);

			pathtonewfolder = parentdirectory + "//" + name;

			File directory = new File(pathtonewfolder);
			if (directory.exists()) {
				JOptionPane.showMessageDialog(null, "This directory already exists and will not be created.", "Warning", JOptionPane.WARNING_MESSAGE);
			} else {
				directory.mkdirs();
			}

			newfolder = directory.toString();
			return newfolder;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.PLAIN_MESSAGE);
			return null;
		}
	}

	public static void archiveFilesToFolder(File file) {

/*		String parentDir = file.getParentFile().toString();			
		String newFolder = parentDir + "//someText";			
		String filename = file.getName() // + Extension
		
	

		String moveTargetPathStr = newFolder + 	"//" + filename;				
		File targetFile = new File (moveTargetPathStr);		
		
		Files.move(file, targetFile);	*/

	}

	public static void createnewFolderfromString(String string) {
		// TODO Auto-generated method stub
		try {
			String pathtonewfolder = utilities.TestClass.folderforzips + "//" + string;
			File directory = new File(pathtonewfolder);
			if (!directory.exists()) {
				directory.mkdirs();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.PLAIN_MESSAGE);

		}
	}
}
