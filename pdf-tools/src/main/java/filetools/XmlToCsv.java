package filetools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class XmlToCsv {

	static String SEPARATOR = ";";
	static String MISSING_VALUE = "";
	static PrintWriter outputCsv;

	static String repository = "Repository";
	static int criterium;
	static String answer = "Answer";
	static String links = "Links & References";

	static BufferedReader xmlreader;

	public static String examinedFolder;

	public static void main(String args[]) throws IOException {

		examinedFolder = utilities.BrowserDialogs.chooseFolder();
		if (examinedFolder != null) {

			outputCsv = new PrintWriter(new FileWriter(examinedFolder + "//" + "outputCsv.csv"));

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

			outputCsv.println(repository + SEPARATOR + "criterium" + SEPARATOR + answer + SEPARATOR + links);

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium1(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium2(files.get(i));
				}
			}

			outputCsv.close();
		}
	}

	public static void getcriterium1(File file) throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 1;
		String temp;
		// repository = file.getName();

		while (line != null) {
			line = xmlreader.readLine();
			lineslist.add(line);
		}
		for (int j = 0; j < lineslist.size(); j++) {

			if (lineslist.get(j).contains("<Repository>")) {
				temp = lineslist.get(j).replace("<Repository>", "");
				temp = temp.replace("</Repository>", "");
				repository = temp;
			}

			if (lineslist.get(j).contains("<Criterium Number=\"1\">")) {
				System.out.println(lineslist.get(j));
				StringBuffer buffer1 = new StringBuffer();
				while (!lineslist.get(j).equals("</Criterium>")) {
					++j;
					buffer1.append(lineslist.get(j));
				}
				temp = buffer1.toString().replace("Statement of Compliance:", "");
				temp = temp.replace("</Criterium>", "");
				temp = temp.replace("Applicant Entry", "");
				temp = temp.replace("Minimum Required Statement of Compliance:", "");
				temp = temp.replace("Minimum Required", "");
				temp = temp.replace("Self-assessment statement:", "");
				temp = temp.replace("1. The data producer deposits the data in a data repository with sufficientinformation for others to assess the quality of the dataand compliance with disciplinary and ethical norms.", "");
				answer = temp;

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
	}

	public static void getcriterium2(File file) throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 2;
		String temp;
		// repository = file.getName();

		while (line != null) {
			line = xmlreader.readLine();
			lineslist.add(line);
		}
		for (int j = 0; j < lineslist.size(); j++) {

			if (lineslist.get(j).contains("<Repository>")) {
				temp = lineslist.get(j).replace("<Repository>", "");
				temp = temp.replace("</Repository>", "");
				repository = temp;
			}

			if (lineslist.get(j).contains("<Criterium Number=\"2\">")) {
				System.out.println(lineslist.get(j));
				StringBuffer buffer1 = new StringBuffer();
				while (!lineslist.get(j).equals("</Criterium>")) {
					++j;
					buffer1.append(lineslist.get(j));
				}
				temp = buffer1.toString().replace("Statement of Compliance:", "");
				temp = temp.replace("</Criterium>", "");
				temp = temp.replace("Applicant Entry", "");
				temp = temp.replace("Minimum Required Statement of Compliance:", "");
				temp = temp.replace("Self-assessment statement:", "");
				answer = temp;

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
	}
}
