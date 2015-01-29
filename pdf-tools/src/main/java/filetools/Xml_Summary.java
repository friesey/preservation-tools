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

public class Xml_Summary {

	static PrintWriter summaryXml;

	static String repository = "Repository";
	static int criterium;
	static String answer = "Answer";
	static String links = "Links & References";
	static String compliance = "Statement of Compliance";

	static BufferedReader xmlreader;

	public static String examinedFile;

	public static void main(String args[]) throws IOException {

		examinedFile = utilities.BrowserDialogs.chooseFile();
		if (examinedFile != null) {

			

			summaryXml = new PrintWriter(new FileWriter("D://enhance.xml"));
			addDataSealDate(examinedFile);

			summaryXml.close();
		}
		/*
		 * } catch (Exception e) { System.out.println(e); }
		 */
	}

	public static void addDataSealDate(String examinedFile2) throws IOException {
		xmlreader = new BufferedReader(new FileReader(examinedFile2));
		String line = xmlreader.readLine();
		ArrayList<String> lineslist = new ArrayList<String>();
		

		while (line != null) {
			line = xmlreader.readLine();
			lineslist.add(line);
		}
		
	
		int len = lineslist.size();
		
		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		summaryXml.println("<?" + xmlVersion + " " + xmlEncoding + "?>");

		for (int j = 0; j < len-1; j++) {		
			summaryXml.println(lineslist.get(j));		
			if(lineslist.get(j).contains("<Repository> Tübingen CLARIN-D Repository</Repository>")){
				summaryXml.println ("<SealAcquiryDate>Apr. 16, 2013</SealAcquiryDate>");
			}
		}
	}
	

		/*		while (line != null) {
			line = xmlreader.readLine();
			if (line.contains("<Repository>3TU.Datacentrum</Repository>")){
			summaryXml.println(line);
			summaryXml.println ("<SealAcquiryDate>Jan. 28, 2013</SealAcquiryDate>");
			}
			else{
			summaryXml.println(line);
			}			
	}*/

}


