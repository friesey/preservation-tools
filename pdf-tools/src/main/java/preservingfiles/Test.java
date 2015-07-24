package preservingfiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Test {

	static String examinedFolder;
	String pathwriter;
	static PrintWriter xmlSimpleWriter;

	static int MAXIMUM_SIZE_PDF = 15500;

	// TODO: If a PDF is bigger than a certain size, do not do certain tests,
	// that will fail the Heap Space. The same is true for the number of pages.
	// It is too difficult to determine exactly where the borders are, that is
	// why I have chosen something arbitrary which puts the program on the save
	// side.

	public static void main(String args[]) throws IOException {

		changecolor();

		JOptionPane.showMessageDialog(null, "Please choose the folder with files to analyse.", "File Analysis", JOptionPane.QUESTION_MESSAGE);
		examinedFolder = utilities.BrowserDialogs.chooseFolder();

		if (examinedFolder != null) {

			JFrame f = new JFrame();
			JButton but = new JButton("... Program is running ... ");
			f.add(but, BorderLayout.PAGE_END);
			f.pack();
			f.setVisible(true);

			xmlSimpleWriter = new PrintWriter(new FileWriter(examinedFolder + "\\FileAnalysis.xml"));

			String xsltLocation = (examinedFolder + "\\Analyst.xsl");
			output.XslStyleSheets.fileAnalysis(xsltLocation);
			startingXmlOutput();

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
			ArrayList<ZbwFile> findings = new ArrayList<ZbwFile>();

			// TODO: neue arraylist aus jhovefileobjekten

			xmlSimpleWriter.println("<FileAnalysisSummary>");

			// To handle one file after the other
			for (int i = 0; i < files.size(); i++) {

				ZbwFile testfile = new ZbwFile();
				testfile.fileName = testfile.getName(files.get(i).toString());

				testfile.path = files.get(i).toString();
				testfile.zbwFile = testfile.toFile(testfile.path);
				testfile.mimetype = testfile.getFileMimeType(testfile.zbwFile);

				testfile.checksumMD5 = testfile.getMD5Checksum(testfile.zbwFile);
				testfile.size = testfile.getSizeinKB(testfile.toFile(testfile.path));
				testfile.mimetype = testfile.getFileMimeType(testfile.toFile(testfile.path));
				testfile.fileExtension = testfile.getFileExtension(testfile.path);
	

				findings.add(testfile);
			}

			for (int i = 0; i < findings.size(); i++) {
				xmlSimpleWriter.println("<File>");
				xmlSimpleWriter.println("<FileName><![CDATA[" + findings.get(i).fileName + "]]></FileName>");
				xmlSimpleWriter.println("<MD5Checksum>" + findings.get(i).checksumMD5 + "</MD5Checksum>");
				xmlSimpleWriter.println("<FileSizeKB>" + findings.get(i).size + "</FileSizeKB>");
				xmlSimpleWriter.println("<Mimetype>" + findings.get(i).mimetype + "</Mimetype>");
				xmlSimpleWriter.println("<FileExtension>" + findings.get(i).fileExtension + "</FileExtension>");
				
				
				
				
			

				if (findings.get(i).mimetype != null) {
					if (findings.get(i).mimetype.equals("application/pdf")) {
						ZbwFilePdf testfilePdf = new ZbwFilePdf();
						System.out.println(findings.get(i).fileName);
						System.out.println(findings.get(i).size);
						if (findings.get(i).size < MAXIMUM_SIZE_PDF) {
							
						//	testfilePdf.pdfFile = toPDDocument(testfilePdf);
							//hier PDDocument pruefen, ob es encrypted ist. Das sollte funktionieren.

					
						}

						
					}
				}
				
				xmlSimpleWriter.println("</File>");
				
			}
			xmlSimpleWriter.println("</FileAnalysisSummary>");
			xmlSimpleWriter.close();
			f.dispose();
		}
	}

	private static void startingXmlOutput() {
		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		xmlSimpleWriter.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		xmlSimpleWriter.println("<?xml-stylesheet type=\"text/xsl\" href=\"Analyst.xsl\"?>");
	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}

}
