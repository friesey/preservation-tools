package filetools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class XmlToCsv {

	static String SEPARATOR = ";";
	static String MISSING_VALUE = "";

	public static String examinedFolder;

	public static void main(String args[]) throws IOException {

		examinedFolder = utilities.BrowserDialogs.chooseFolder();
		if (examinedFolder != null) {

			BufferedReader xmlreader;

			PrintWriter outputCsv = new PrintWriter(new FileWriter(examinedFolder + "//" + "outputCsv.csv"));

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

			String repository = "Repository";
			String criterium = "Criterium";
			String answer = "Answer";
			String links = "Links & References";

			outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + answer + SEPARATOR + links);

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {

					xmlreader = new BufferedReader(new FileReader(files.get(i)));

					String line;
					ArrayList<String> lineslist = new ArrayList<String>();
					criterium = "1";

					while (null != (line = xmlreader.readLine())) {
						lineslist.add(line);
					}

					for (int j = 0; j < lineslist.size(); j++) {
						String temp;
						if (lineslist.get(j).contains("<Repository>")) {
							
							temp = lineslist.get(j).replace("<Repository>", "");
							temp = temp.replace("</Repository>", "");
							repository = temp;
						}

						// if (lineslist.get(j).contains("<Criterium Number=\"1\">")) {
							
							if (lineslist.get(j).contains("Applicant Entry")) {
						
							StringBuffer buffer1 = new StringBuffer();
							while (!lineslist.get(j).equals("</Criterium>")) {
								++j;								
								buffer1.append(lineslist.get(j));
							}
							answer = buffer1.toString();
						}

					}
						outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + answer + SEPARATOR + links);
				}
				
			}

			outputCsv.close();
		}
	}
}
