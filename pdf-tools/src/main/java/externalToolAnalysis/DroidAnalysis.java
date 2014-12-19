package externalToolAnalysis;

import java.io.IOException;

import javax.swing.JOptionPane;

public class DroidAnalysis {

	static String droidPath;

	public static void main(String args[]) throws IOException {

		droidPath = "D://Benutzer//Friese Yvonne//Documents//DROID 6.1.3//droid-ui-6.1.3.jar";
		// TODO: make this field editable for other users with other paths or
		// just FileBrowserDialog

		try {
			ProcessBuilder runsDroid = new ProcessBuilder("java", "-Xmx1024m", "-Xms1024m", "-DTOOLS_DIR=/home/IM/work/dist", "-Daoi=whole", "-jar", droidPath);

			runsDroid.start();

		}

		catch (IOException e) {
			JOptionPane.showMessageDialog(null, e, "error message", JOptionPane.ERROR_MESSAGE);
		}
	}
}
