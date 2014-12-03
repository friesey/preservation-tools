package utilities;

import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
			JOptionPane.showMessageDialog(null, "No folder was chosen", "Info Message", JOptionPane.PLAIN_MESSAGE);
			return null;
		} else {
			String folder = j.getSelectedFile().getPath();
			return folder;
		}
	}
}

// TODO: search zip file