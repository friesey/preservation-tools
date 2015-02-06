package filetools.pdf;

import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class PdfTwinTesterBulk {

	// TODO: To test whole folders, find a way to know which PDF files to
	// compare

	static String OrgPdf;
	static String MigPdf;
	static long filesizeOrg;
	static long filesizeMig;

	static String folder;

	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {
		
		JOptionPane.showMessageDialog(null, "Please choose the folder with PDF Files to compare.", "Enter String Mask", JOptionPane.QUESTION_MESSAGE);
		folder = utilities.BrowserDialogs.chooseFolder();

		if (folder != null) {
		
		
		}
		else {
			System.out.println("Please choose two files.");
		}
	}

}
