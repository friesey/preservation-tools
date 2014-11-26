package externalToolAnalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.io.FilenameUtils;

public class JhoveStatistics {

	public static void main(String args[]) throws IOException {

		String JhoveFindings = utilities.FileBrowserDialog.chooseFile();

		if (JhoveFindings != null) {

			boolean jhove = isJhoveOutput(JhoveFindings);

			// TODO: Add Outputtextfile

			if (jhove == true) {

				JhoveOutputAnalysis(JhoveFindings);
			}

			else {
				System.out.println("Chosen File is not a JHOVE output file.");
			}
		}
	}

	public static void JhoveOutputAnalysis(String jhoveFindings) throws IOException {
		FileInputStream inputStream = new FileInputStream(jhoveFindings);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		String name = FilenameUtils.getFullPathNoEndSeparator(jhoveFindings);
	
		PrintWriter output = new PrintWriter(new FileWriter(name + "Statistics.txt"));

		String line = null;

		int wellformed = 0;
		int malformed = 0;
		int invalid = 0;

		ArrayList<String> errorlist = new ArrayList<String>();

		ArrayList<String> lines = new ArrayList<String>();

		StringBuilder responseData = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			responseData.append(line);
			lines.add(line);
		}

		int len = lines.size();
		output.println(len);

		for (int i = 0; i < len; i++) {
			if (lines.get(i).contains("Well-Formed and valid")) {
				wellformed++;
			} else if (lines.get(i).contains("not valid")) {
				invalid++;
			} else if (lines.get(i).contains("Not well-formed")) {
				malformed++;
			} else if ((lines.get(i).contains("error")) && (lines.get(i).contains("message"))) {
				errorlist.add(lines.get(i));
				//TODO: nur was zwischen den eckigen Klammern steht
			}

		}
		output.println("Files Well-Formed and valid: " + wellformed);
		output.println("Files malformed: " + malformed);
		output.println("Files invalid: " + invalid);
		reader.close();

		Collections.sort(errorlist);

		int i;

		// copy ErrorList because later the no. of entries of each
		// element will be counted

		ArrayList<String> originerrors = new ArrayList<String>();
		for (i = 0; i < errorlist.size(); i++) { // There might be a
													// pre-defined
													// function for this
			originerrors.add(errorlist.get(i));
		}

		// get rid of redundant entries
		i = 0;
		while (i < errorlist.size() - 1) {
			if (errorlist.get(i).equals(errorlist.get(i + 1))) {
				errorlist.remove(i);
			} else {
				i++;
			}
		}

		output.println("Sample consists of " + errorlist.size() + " different JHOVE error messages");

		// how often does each JHOVE error occur?
		int j = 0;
		int temp;

		for (i = 0; i < errorlist.size(); i++) {
			temp = 0;
			for (j = 0; j < originerrors.size(); j++) {
				if (errorlist.get(i).equals(originerrors.get(j))) {
					temp++;
				}
			}
			output.println((i + 1) + ": " + temp + " x " + errorlist.get(i));
		}

		output.close();

	}

	@SuppressWarnings("resource")
	private static boolean isJhoveOutput(String JhoveFindings) throws IOException {
		BufferedReader JhoveHeader = new BufferedReader(new FileReader(JhoveFindings));
		String FileHeader = JhoveHeader.readLine();
		if (FileHeader != null) {
			if (FileHeader.contains("JhoveView")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
