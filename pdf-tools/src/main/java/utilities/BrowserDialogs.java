package utilities;

import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class BrowserDialogs {

	/**
	 * Chooses the folder which is examined via a simple folder browser Dialog
	 * 
	 * @param Does
	 *            not need any to begin with.
	 * @return: string for folder path
	 */
	// TODO: search zip file

	public static String chooseFolder() throws FileNotFoundException {
		try {
		JOptionPane.showMessageDialog(null, "Folder Browser Dialog", "Please choose Folder", JOptionPane.QUESTION_MESSAGE);
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
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Most likely you tried to choose a folder like \"C:\" but you do not have the rights to read files there.", "Info Message", JOptionPane.PLAIN_MESSAGE);
		return null;
	}
	}

	/**
	 * Chooses the file which is examined via a simple folder browser Dialog
	 *
	 * @param Does
	 *            not need any to begin with.
	 * @return: string for file path
	 */
	public static String chooseFile() throws FileNotFoundException {
		try {
			JOptionPane.showMessageDialog(null, "File Browser Dialog", "Please choose Folder", JOptionPane.QUESTION_MESSAGE);
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Most likely you tried to choose a folder like \"C:\" but you do not have the rights to read files there.", "Info Message", JOptionPane.PLAIN_MESSAGE);
			return null;
		}
	}

	public static String chooseFileOrFolder() throws FileNotFoundException {

		try {
			JOptionPane.showMessageDialog(null, "Folder or File Browser Dialog", "Please choose Folder", JOptionPane.QUESTION_MESSAGE);
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			j.showOpenDialog(j);
			if (j.getSelectedFile() == null) {
				JOptionPane.showMessageDialog(null, "No file or folder was chosen", "Info Message", JOptionPane.PLAIN_MESSAGE);
				return null;
			} else {
				String folder = j.getSelectedFile().getPath();
				return folder;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Most likely you tried to choose a folder like \"C:\" but you do not have the rights to read files there.", "Info Message", JOptionPane.PLAIN_MESSAGE);
			return null;
		}
	}

}
