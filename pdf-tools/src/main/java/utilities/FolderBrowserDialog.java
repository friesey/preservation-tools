package utilities;

import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

public class FolderBrowserDialog {
	
	/**
	 * Chooses the folder which is examined via a simple folder browser Dialog
	 * 
	 * @param Does
	 *            not need any to begin with.
	 * @return: string for folder path
	 */

	public static String chooseFolder() throws FileNotFoundException {
		JFileChooser j = new JFileChooser();
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		j.showOpenDialog(j);
		if (j.getSelectedFile() == null) {
			System.out.println("No folder was chosen");
		} else {
			String folder = j.getSelectedFile().getPath();
			return folder;
		}
		return null;
	}

}

//TODO: search zip file