package utilities;

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
				String title = "Title"
;				String alternativeTitle = "Alternative Title";
				String preservationType = "Preservation Type";
				String usageType = "Usage Type";
				String revisionNumber = "Revision Number";
				String fileMimeType = "File Mime Type";
				String fileName = "File Name";
				String fileLabel = "File Label";

				outputCsv.println(objectType + SEPARATOR + title + SEPARATOR + alternativeTitle + SEPARATOR + preservationType + SEPARATOR + usageType + SEPARATOR + revisionNumber + SEPARATOR + fileMimeType + SEPARATOR + fileName + SEPARATOR + fileLabel);

				for (int i = 0; i < files.size(); i++) {

					objectType = "FILE"; //other possibilities: SIP, IE, REPRESENTATION
					title = getTitle(files.get(i));
					alternativeTitle = getalternativeTitle(files.get(i));
					preservationType = "PRESERVATION MASTER";
					usageType = "VIEW";
					revisionNumber = "1";
					fileMimeType = getMimeType(files.get(i));
					fileName = getfileName(files.get(i));
					fileLabel = getFileLabel (files.get(i));

					outputCsv.println(objectType + SEPARATOR + title + SEPARATOR + alternativeTitle + SEPARATOR + preservationType + SEPARATOR + usageType + SEPARATOR + revisionNumber + SEPARATOR + fileMimeType + SEPARATOR + fileName + SEPARATOR + fileLabel);
				}
				outputCsv.close();
				f.dispose();
			}

		} catch (Exception e) {
		}
	}

	private static String getFileLabel(File file) {
		// TODO Auto-generated method stub
		try {
			
		}
		catch (Exception e) {
	
		}
		return MISSING_VALUE;
	}

	private static String getfileName(File file) {
		// TODO Auto-generated method stub
		try {
			
		}
		catch (Exception e) {
	
		}
		return MISSING_VALUE;
	}

	private static String getMimeType(File file) {
		// TODO Auto-generated method stub
		try {
			
		}
		catch (Exception e) {
	
		}
		return MISSING_VALUE;
	}

	private static String getTitle(File file) {
		// TODO Auto-generated method stub
		try {
			
		}
		catch (Exception e) {
	
		}
		return MISSING_VALUE;
	}

	private static String getalternativeTitle(File file) {
		// TODO Auto-generated method stub
		try {
			
		}
		catch (Exception e) {
	
		}
		return MISSING_VALUE;
	}
}