package preservingfiles;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Test {

	static String examinedFolder;
	String pathwriter;

	public static void main(String args[]) throws IOException {

		changecolor();

		JOptionPane.showMessageDialog(null, "Please choose the folder with files to analyse.", "File Analysation", JOptionPane.QUESTION_MESSAGE);
		examinedFolder = utilities.BrowserDialogs.chooseFolder();

		if (examinedFolder != null) {
			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

			ArrayList<ZbwFile> findings = new ArrayList<ZbwFile>();

			// TODO: neue arraylist aus jhovefileobjekten

			// To handle one file after the other
			for (int i = 0; i < files.size(); i++) {

				ZbwFile testfile = new ZbwFile();
				testfile.fileName = testfile.getName(files.get(i).toString());

				testfile.path = "C:\\FileSample\\text\\PDFs\\100PdfFiles\\605.pdf";
				testfile.mimetype = testfile.getFileMimeType(testfile.toFile(testfile.path));
				testfile.checksumMD5 = testfile.getMD5Checksum(testfile.toFile(testfile.path));
				testfile.size = testfile.getSizeinKB(testfile.toFile(testfile.path));
				testfile.mimetype = testfile.getFileMimeType(testfile.toFile(testfile.path));
				testfile.fileExtension = testfile.getFileExtension(testfile.path);		

				findings.add(testfile);
			}

			for (int i = 0; i < findings.size(); i++) {
				System.out.println(findings.get(i).fileName);
			}
		}
	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}

}
