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
	static String compliance = "Statement of Compliance";

	static BufferedReader xmlreader;

	public static String examinedFolder;

	public static void main(String args[]) throws IOException {

		examinedFolder = utilities.BrowserDialogs.chooseFolder();
		if (examinedFolder != null) {

			outputCsv = new PrintWriter(new FileWriter(examinedFolder + "//" + "outputCsv.csv"));

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

			outputCsv.println(repository + SEPARATOR + "criterium" + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);

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
			
			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium3(files.get(i));
				}
			}
			
			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium4(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium5(files.get(i));
				}
			}
			
			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium6(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium7(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium8(files.get(i));
				}
			}
			
			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium9(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium10(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium11(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium12(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium13(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium14(files.get(i));
				}
			}
			
			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium15(files.get(i));
				}
			}

			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
				if (extension.equals("xml")) {
					getcriterium16(files.get(i));
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
				temp = temp.replace("1. The data producer deposits the data in a data repository with sufficient information forothers to assess the quality of the data, and compliance with disciplinary and ethicalnorms.", "");
				temp = temp.replace("1. The data producer deposits the research data in a data repository with sufficientinformation for others to assess the scientific and scholarly quality of the research dataand compliance with disciplinary and ethical norms.", "");
				temp = temp.replace("1. The data producer deposits the data in a data repository with sufficient information for others to assess thequality of the data, and compliance with disciplinary and ethical norms.", "");
				temp = temp.replace("This guideline cannot be outsourced.", "");
				int len = temp.length();
				temp = temp.substring(52, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
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
				temp = temp.replace("2. The data producer provides the data in formats recommended by the data repository.Minimum Required 3. In progress: We are in the implementation phase.", "");
				temp = temp.replace("2. The data producer provides the research data in formats recommended by the datarepository.Minimum Required 3. In progress: We are in the implementation phase.This guideline cannot be outsourced.", "");
				temp = temp.replace("2. The data producer provides the research data in formats recommended by the data repositoryMinimum Required Statement of Compliance3. In progress: We are in the implementation phase.:", "");
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}	
	}
	public static void getcriterium3(File file) throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 3;
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

			if (lineslist.get(j).contains("<Criterium Number=\"3\">")) {
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
				temp = temp.replace("This guideline cannot be outsourced.", "");
				temp = temp.replace("3. The data producer provides the research data together with the metadata requested bythe data repository. ", "");
				int len = temp.length();			
		
				
				temp = temp.substring(90, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium4(File file)throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 4;
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

			if (lineslist.get(j).contains("<Criterium Number=\"4\">")) {
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
				temp = temp.replace("This guideline cannot be outsourced.", "");
				temp = temp.replace("4. The data repository has an explicit mission in the area of digital archiving andpromulgates it. ", "");

			int len = temp.length();
				temp = temp.substring(90, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium5(File file)throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 5;
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

			if (lineslist.get(j).contains("<Criterium Number=\"5\">")) {
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
				temp = temp.replace("This guideline cannot be outsourced.", "");
				
				temp = temp.replace("5. The data repository uses due diligence to ensure compliance with legal regulations andcontracts including, when applicable, regulations governing the protection of humansubjects. ", "");

				int len = temp.length();
				temp = temp.substring(90, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium6(File file)throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 6;
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

			if (lineslist.get(j).contains("<Criterium Number=\"6\">")) {
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
				temp = temp.replace("This guideline cannot be outsourced.", "");
				temp = temp.replace("6. The data repository applies documented processes and procedures for managing datastorage. ", "");

				int len = temp.length();
				temp = temp.substring(90, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium7(File file) throws IOException{
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 7;
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

			if (lineslist.get(j).contains("<Criterium Number=\"7\">")) {
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
				temp = temp.replace("7. The data repository has a plan for long-term preservation of its digital assets. ", "");

				int len = temp.length();
				temp = temp.substring(51, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	public static void getcriterium8(File file) throws IOException{
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 8;
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

			if (lineslist.get(j).contains("<Criterium Number=\"8\">")) {
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
				temp = temp.replace("This guideline can be outsourced.", "");
				
				temp = temp.replace("8. Archiving takes place according to explicit work flows across the data life cycle. ", "");

			int len = temp.length();
				temp = temp.substring(52, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium9(File file)throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 9;
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

			if (lineslist.get(j).contains("<Criterium Number=\"9\">")) {
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
				
				temp = temp.replace("9. The data repository assumes responsibility from the data producers for access andavailability of the digital objects. ", "");

				int len = temp.length();
				temp = temp.substring(90, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium10(File file)throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 10;
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

			if (lineslist.get(j).contains("<Criterium Number=\"10\">")) {
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
				temp = temp.replace("10. The data repository enables the users to utilize the research data and refer to them. ", "");

			int len = temp.length();
				temp = temp.substring(52, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}							
				else if
				(temp.startsWith("2")){
					compliance = "2. Theoretical";
				}		
				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;
			}
		}		
	}
	
	public static void getcriterium11(File file) throws IOException{
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 11;
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

			if (lineslist.get(j).contains("<Criterium Number=\"11\">")) {
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
				temp = temp.replace("11. The data repository ensures the integrity of the digital objects and the metadata. ", "");

			int len = temp.length();
				temp = temp.substring(52, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium12(File file) throws IOException{
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 12;
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

			if (lineslist.get(j).contains("<Criterium Number=\"12\">")) {
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
				temp = temp.replace("12. The data repository ensures the authenticity of the digital objects and the metadata. ", "");

			int len = temp.length();
				temp = temp.substring(52, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
	}
	public static void getcriterium13(File file) throws IOException{
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 13;
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

			if (lineslist.get(j).contains("<Criterium Number=\"13\">")) {
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
				temp = temp.replace("13. The technical infrastructure explicitly supports the tasks and functions described ininternationally accepted archival standards like OAIS. ", "");
				
				int len = temp.length();
				temp = temp.substring(52, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium14(File file)throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 14;
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

			if (lineslist.get(j).contains("<Criterium Number=\"14\">")) {
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
				temp = temp.replace("14. The data consumer complies with access regulations set by the data repository. ", "");

			int len = temp.length();
				temp = temp.substring(90, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium15(File file)throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 15;
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

			if (lineslist.get(j).contains("<Criterium Number=\"15\">")) {
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
				temp = temp.replace("This guideline cannot be outsourced.", "");
				temp = temp.replace("15. The data consumer conforms to and agrees with any codes of conduct that aregenerally accepted in higher education and scientific research for the exchange andproper use of knowledge and information.", "");
				temp = temp.replace("15. The data consumer conforms to and agrees with any codes of conduct that aregenerally accepted in the relevant sector for the exchange and proper use of knowledgeand information. ", "");
				temp = temp.replace("15. The data consumer conforms to and agrees with any codes of conduct that are generally accepted in highereducation and scientific research for the exchange and proper use of knowledge and information. ", "");
				
				int len = temp.length();
				temp = temp.substring(90, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
	
	public static void getcriterium16(File file)throws IOException {
		xmlreader = new BufferedReader(new FileReader(file));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		criterium = 16;
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

			if (lineslist.get(j).contains("<Criterium Number=\"16\">")) {
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
				temp = temp.replace("This guideline cannot be outsourced.", "");
				temp = temp.replace("16. The data consumer respects the applicable licenses of the data repository regardingthe use of the research data. ", "");
				temp = temp.replace("16. The data consumer respects the applicable licenses of the data repository regardingthe use of the research data.", "");
				temp = temp.replace("16. The data consumer respects the applicable licences of the data repository regardingthe use of the data.", "");
				temp = temp.replace("16. Data consumer respects the applicable licenses of the data repository regarding the use of the data.", "");
												 
				int len = temp.length();
				temp = temp.substring(90, len);
				if (temp.startsWith("3")) {
					compliance = "3. In progress";
				}
				else if (temp.startsWith("4")){
					compliance = "4. Implemented";
				}				
				temp = temp.replace("4. Implemented: This guideline has been fully implemented for the needs of our repository.", "");
				temp = temp.replace("3. In progress: We are in the implementation phase.", "");
				answer = temp;
				

				outputCsv.println(repository + SEPARATOR + criterium + SEPARATOR + compliance + SEPARATOR + answer + SEPARATOR + links);
				return;

			}
		}
		
	}
}
