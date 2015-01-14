package output;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CsvOutput {

	public static String folderCsvData;

	static String SEPARATOR = ";";
	static String NEWLINE = "\n";
	static String MISSING_VALUE = "";

	// assuming all files in folder are from the same entity. otherwise more
	// than one csv file would be needed

	public static void main(String args[]) throws IOException {

		try {
			folderCsvData = utilities.BrowserDialogs.chooseFolder();
			if (folderCsvData != null) {

				JFrame f = new JFrame();
				JButton but = new JButton("... Program is running ... ");
				f.add(but, BorderLayout.PAGE_END);
				f.pack();
				f.setVisible(true);

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folderCsvData), new ArrayList<File>());
				PrintWriter outputCsv = new PrintWriter(new FileWriter(folderCsvData + "//" + "outputCsv.csv"));

				// the first line is always known, Heading Line

				String objectType = "Object Type";
				String alternativeTitle = "Alternative Title";
				String preservationType = "Preservation Type";
				String usageType = "Usage Type";
				String revisionNumber = "Revision Number";
				String fileMimeType = "File Mime Type";
				String fileName = "File Name";
				String fileLabel = "File Label";

				outputCsv.println(objectType + SEPARATOR + alternativeTitle + SEPARATOR + preservationType + SEPARATOR + usageType + SEPARATOR + revisionNumber + SEPARATOR + fileMimeType + SEPARATOR + fileName + SEPARATOR + fileLabel);

				for (int i = 0; i < files.size(); i++) {

				}
				outputCsv.close();
				f.dispose();
			}

		} catch (Exception e) {
		}
	}
}