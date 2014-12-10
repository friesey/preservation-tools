package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

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
			if (directory.exists()){
				JOptionPane.showMessageDialog(null, "This directory already exists and will not be created.", "Warning", JOptionPane.PLAIN_MESSAGE);
			}
			else {
		       directory.mkdirs();
		    }
			
			newfolder = directory.toString();
			return newfolder;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.PLAIN_MESSAGE);
			return null;
		}
	}

	public class archiveFilesToFolder {

	}
}
