
package filetools.pdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PdfEcnryptionDetailChecker {

	static String examinedFolder;
	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {
		
		JOptionPane.showMessageDialog(null, "Please choose the folder with PDF/A files to validate.", "PDFBox Validation", JOptionPane.QUESTION_MESSAGE);
		examinedFolder = utilities.BrowserDialogs.chooseFolder();
		
		outputfile = new PrintWriter(new FileWriter(examinedFolder + "//" + "PdfEncryptionDetailChecker.xml"));
		
		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"PdfEncryptionDetailChecker.xsl\"?>";
		
		String xsltLocation = examinedFolder + "//" + "PdfEncryptionDetailChecker.xsl";
		
		output.XslStyleSheets.PdfEncryptionXsl(xsltLocation); //TODO
		
		outputfile.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		outputfile.println(xsltStyleSheet);
		outputfile.println("<PdfEncryptionCheck>");
		
		if (examinedFolder != null) {

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

			for (int i = 0; i < files.size(); i++) {
				outputfile.println("<File>");
				outputfile.println("<FileName>" + files.get(i).toString() + "</FileName>");
				
		//causes output problems
			
			
			outputfile.println("</File>");
			
			}
		}
		
		outputfile.println("</PdfEncryptionCheck>");
	}
}