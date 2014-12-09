package utilities;

import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileBrowserDialog {

	/**
	 * Chooses the file which is examined via a simple folder browser Dialog
	 *
	 * @param Does
	 *            not need any to begin with.
	 * @return: string for file path
	 */
	public static String chooseFile() throws FileNotFoundException {
		JFileChooser j = new JFileChooser();
		j.setFileSelectionMode(JFileChooser.FILES_ONLY);
		j.showOpenDialog(j);
		if (j.getSelectedFile() == null) {
			JOptionPane.showMessageDialog(null, "No file was chosen");
		} else {
			String file = j.getSelectedFile().getPath();
			return file;
		}
		return null;
	}

}
