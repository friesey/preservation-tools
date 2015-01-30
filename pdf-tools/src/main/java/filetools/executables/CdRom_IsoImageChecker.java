package filetools.executables;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CdRom_IsoImageChecker {

	public static PrintWriter filesExecutable;
	public static PrintWriter outputfile;
	static String examinedCdRom;
	static String outputFolder;
	public static String archivFolder;
	static String mimetype;
	static String extension;
	static int filescount;
	static int filecheck;
	static int FOLDERMAX = 15; /*
								 * if there are more than FOLDERMAX folders, an
								 * ISO Image has to be created
								 */

	static int FILEMAX = 1000; /*
								 * if there are more than FILEMAX files, an ISO
								 * Image has to be created
								 */

	static boolean isonecessary;

	/*
	 * description: MIME Type: text MIME Type: image MIME Type: audio MIME Type:
	 * video MIME Type: application MIME Type: multipart MIME Type: message
	 */

	// TODO: How to end program if it runs too long?

	public static void main(String args[]) throws IOException, NoSuchAlgorithmException {
		output.XmlOutput.createsXMLHeader();
		// try {
		// measures time
		double starttime = System.currentTimeMillis();
		filecheck = 0;
		isonecessary = false;

		JOptionPane.showMessageDialog(null, "CD ROM Dialog", "Please choose CD ROM Folder", JOptionPane.QUESTION_MESSAGE);
		examinedCdRom = utilities.BrowserDialogs.chooseFolder();

		JOptionPane.showMessageDialog(null, "Output Folder", "Please choose Folder where Outputfile will be created", JOptionPane.QUESTION_MESSAGE);
		outputFolder = utilities.BrowserDialogs.chooseFolder();

		if (examinedCdRom != null && outputFolder != null) {
			String CdRomName = filetools.GenericFileAnalysis.getCdRomFolderName(examinedCdRom);

			if (CdRomName.contains(":")) {
				CdRomName = JOptionPane.showInputDialog(null, "Please type name of CD ROM", "CD ROM name unknown", JOptionPane.PLAIN_MESSAGE);
			}

			JFrame f = new JFrame();
			JButton but = new JButton("... Program is running ... ");
			f.add(but, BorderLayout.PAGE_END);
			f.pack();
			f.setVisible(true);

			outputfile = new PrintWriter(new FileWriter(outputFolder + "//" + "CdRomExecutableAnalysis_" + CdRomName + ".txt"));
			filesExecutable = new PrintWriter(new FileWriter(outputFolder + "//" + "potentiallyExecutableFiles_" + CdRomName + ".txt"));
			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedCdRom), new ArrayList<File>());

			output.XmlOutput.xmlSimpleWriter.println("<CdRomFolderTest>");

			outputfile.println("No. of files are in the folder or CD: " + files.size());
			outputfile.println();
			filescount = files.size();

			for (int i = 0; i < files.size(); i++) {
				extension = utilities.fileStringUtilities.getExtension(files.get(i).toString());
				extension = extension.toLowerCase();
				if (extension != null) {
					if (extension.equals("cda")) {
						// TODO: Could be tested earlier to save time
						filetools.audio.AudioCD.extractAudioFiles(files);
						// has to stop afterwards. Should not do it as often as
						// cda files are found
						// break is brute force
						break;
					} else if (extension.equals("zip")) {

						outputfile.println("CD / Folder consists of the following zipped Folder :  " + files.get(i).toString());
						outputfile.println();
						isonecessary = true;

						ArrayList<File> arrzips = new ArrayList<File>();
						// arrzips =
						// utilities.ListsFiles.getpathsfromzip(files.get(i));

						for (int j = 0; j < arrzips.size(); j++) {
							System.out.println(arrzips.get(j));
							// TODO: Zifiles zu files auspacken oder mit Strings
							// der Zip-Entries arbeiten
							ckeckifFileIsExecutable(arrzips.get(j));
						}

					} else {
						ckeckifFileIsExecutable(files.get(i));
					}
				}
			}
			f.dispose();
			if (isonecessary == true) {
				JOptionPane.showMessageDialog(null, "One or more files are potentially executable", "Create Iso Image", JOptionPane.PLAIN_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "None of the files is potentially executable", "No Iso Image necessary", JOptionPane.PLAIN_MESSAGE);
				filesExecutable.println("None of the files is potentially executable");
			}
			double endtime = System.currentTimeMillis();
			double runtime = endtime - starttime;
			outputfile.println("Time needed to operate: " + runtime + " Milliseconds");
			outputfile.println("Checksum generated and compared: " + filecheck);
			filesExecutable.close();
			outputfile.close();
			output.XmlOutput.xmlSimpleWriter.println("</CdRomFolderTest>");
			output.XmlOutput.xmlSimpleWriter.close();

			System.out.println("Checksum generated and compared: " + filecheck);
			System.out.println("No. of files: " + filescount);

			double runtimeSec = runtime / 1000;

			if (runtime > 1000) {

				if (runtimeSec > 60) {
					double runtimeMin = runtimeSec / 60;
					System.out.println("Time needed to operate: " + runtimeMin + " Seconds");
				} else {
					System.out.println("Time needed to operate: " + runtimeSec + " Seconds");
				}
			}

			else {
				System.out.println("Time needed to operate: " + runtime + " Milliseconds");
			}
		} else {
			System.out.println("Please choose a folder!");
		}
		/*
		 * } catch (Exception e) { // System.out.println (e);
		 * JOptionPane.showMessageDialog(null, e.toString(), "Error Message",
		 * JOptionPane.ERROR_MESSAGE); }
		 */

	}

	public static void ckeckifFileIsExecutable(File file) throws IOException, NoSuchAlgorithmException {
		mimetype = filetools.GenericFileAnalysis.getFileMimeType(file);
		outputfile.println(file.toString());
		outputfile.println("Mimetype: " + mimetype);
		outputfile.println("File-Extension: " + extension);
		outputfile.println();
		if (filetools.GenericFileAnalysis.testIfMimeMightBeExecutable(mimetype)) {
			if (filetools.GenericFileAnalysis.testIfExtensionCanbeExecutable(extension)) {
				filecheck++;
				if (filetools.ChecksumChecker.testIfChecksumisPdfReaderSoftware(file)) {

					filesExecutable.println((file.toString()) + " contains AdobeAcrobatReader MD5 checksum");
				} else {

					filesExecutable.println("IsoImage recommended because of file:  " + file.toString());
					filesExecutable.println("Mimetype: " + mimetype);
					filesExecutable.println();

					// TODO: Count files which are potentially
					// executable

					isonecessary = true;

					// TODO: If ISO Image recommended, create one
					// and
					// copy files. Else only copy files.

				}
			}
		}
	}
}
