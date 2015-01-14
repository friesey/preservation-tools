package utilities;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;

public class CsvOutput {

	public static String folderCsvData;

	static String SEPARATOR = ";";
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
				String outputpath = folderCsvData + "//" + "outputCsv.csv"; //should be the content folder, now it's just the same folder
				PrintWriter outputCsv = new PrintWriter(new FileWriter(outputpath));

				// the first line is always known, Heading Line

				String objectType = "Object Type";
				String title = "Title";
				String alternativeTitle = "Alternative Title";
				String preservationType = "Preservation Type";
				String usageType = "Usage Type";
				String revisionNumber = "Revision Number";
				String fileMimeType = "File Mime Type";
				String fileName = "File Name";
				String fileLabel = "File Label";

				outputCsv.println(objectType + SEPARATOR + title + SEPARATOR + alternativeTitle + SEPARATOR + preservationType + SEPARATOR + usageType + SEPARATOR + revisionNumber + SEPARATOR + fileMimeType + SEPARATOR + fileName + SEPARATOR + fileLabel);

				for (int i = 0; i < files.size(); i++) {
					
					//the outputfile should be omitted
					
					// String extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();
					
					if (!files.get(i).toString().contains ("outputCsv")) {

					objectType = "FILE"; // other possibilities: SIP, IE,
											// REPRESENTATION
					title = getTitle(files.get(i));
					alternativeTitle = getalternativeTitle(files.get(i));
					preservationType = "PRESERVATION MASTER";
					usageType = "VIEW";
					revisionNumber = "1";
					fileMimeType = getMimeType(files.get(i));
					fileName = getfileName(files.get(i));
					fileLabel = getFileLabel(files.get(i));

					outputCsv.println(objectType + SEPARATOR + title + SEPARATOR + alternativeTitle + SEPARATOR + preservationType + SEPARATOR + usageType + SEPARATOR + revisionNumber + SEPARATOR + fileMimeType + SEPARATOR + fileName + SEPARATOR + fileLabel);
				}
				}
				outputCsv.close();
				f.dispose();
			}

		} catch (Exception e) {
		}
	}

	private static String getFileLabel(File file) {
		// this is the path. Is it the path that is needed?
		try {
			String fileLabel = file.getParent().toString();
			return fileLabel;
		} catch (Exception e) {

		}
		return MISSING_VALUE;
	}

	private static String getfileName(File file) {		
		try {
			String filename = file.getName();
			return filename;
		} catch (Exception e) {

		}
		return MISSING_VALUE;
	}

	private static String getMimeType(File file) {
			try {
			String filemime = Files.probeContentType(file.toPath());
			
			if (filemime == null){
				filemime = "mimetype/unknown"; //TODO: check which solution is in Rosetta Mets if Mimetype is not known.
			}
			return filemime;
		} catch (Exception e) {

		}
		return MISSING_VALUE;
	}

	private static String getTitle(File file) {
		// I am assuming that one folder is one IE and the name of the folder is
		// the name of the IE
		try {
			String titleofIE = file.getParent().toString();
			System.out.println(titleofIE);
			String[] temp = titleofIE.split(Pattern.quote("\\"));
			int len = temp.length;
			titleofIE = temp[len - 1];
			return titleofIE;
		} catch (Exception e) {

		}
		return MISSING_VALUE;
	}

	private static String getalternativeTitle(File file) {
		try {
			// usually there would not be an alternative title
		} catch (Exception e) {

		}
		return MISSING_VALUE;
	}
}