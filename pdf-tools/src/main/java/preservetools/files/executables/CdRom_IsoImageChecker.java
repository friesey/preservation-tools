package preservetools.files.executables;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class CdRom_IsoImageChecker {

	static String examinedCdRom;

	static String outputFolder;

	static String mimetype;

	static String extension;

	static int FOLDERMAX = 15; /*
								 * if there are more than FOLDERMAX folders, an
								 * ISO Image has to be created
								 */

	static int FILEMAX = 1000; /*
								 * if there are more than FILEMAX files, an ISO
								 * Image has to be created
								 */

	/*
	 * description: MIME Type: text MIME Type: image MIME Type: audio MIME Type:
	 * video MIME Type: application MIME Type: multipart MIME Type: message
	 */

	public static void main(String args[])  {
		
		try {

		boolean isonecessary = false;

		JOptionPane.showMessageDialog(null, "CD ROM Dialog",
				"Please choose CD ROM Folder", JOptionPane.QUESTION_MESSAGE);

		examinedCdRom = preservetools.utilities.FolderBrowserDialog
				.chooseFolder();

		JOptionPane.showMessageDialog(null, "Output Folder",
				"Please choose Folder where Outputfile will be created",
				JOptionPane.QUESTION_MESSAGE);

		outputFolder = preservetools.utilities.FolderBrowserDialog
				.chooseFolder();

		String CdRomName = preservetools.files.GenericFileAnalysis
				.getCdRomFolderName(examinedCdRom);
	
		//if the CD ROM drive is chosen, there are problems with the name of the folder
		if (CdRomName.contains(":")) {
			
			System.out.println ("Test");
			CdRomName = JOptionPane.showInputDialog(null,"Please type name of CD ROM",
                    "CD ROM name unknown",
                    JOptionPane.PLAIN_MESSAGE);
		}
		
		@SuppressWarnings("resource")
		PrintWriter outputfile = new PrintWriter(new FileWriter(outputFolder
				+ "//" +  "CdRomExecutableAnalysis_"  + CdRomName + ".txt"));

		PrintWriter filesExecutable = new PrintWriter(new FileWriter(
				outputFolder + "//" + "potentiallyExecutableFiles_" + CdRomName + ".txt"));
		
		

			if (examinedCdRom != null) {

				ArrayList<File> files = preservetools.utilities.ListsFiles
						.getPaths(new File(examinedCdRom),
								new ArrayList<File>());




				for (int i = 0; i < files.size(); i++) {

					mimetype = preservetools.files.GenericFileAnalysis
							.getFileExtension(files.get(i));

					extension = FilenameUtils.getExtension(files.get(i)
							.toString()).toLowerCase();

					outputfile.println(files.get(i).toString());
					outputfile.println("Mimetype: " + mimetype);
					outputfile.println("File-Extension: " + extension);
					outputfile.println();

					if (preservetools.files.GenericFileAnalysis
							.testIfMimeMightBeExecutable(mimetype)) {

						if (preservetools.files.GenericFileAnalysis
								.testIfExtensionCanbeExecutable(extension)) {

							filesExecutable
									.println("IsoImage recommended because of file:  "
											+ files.get(i).toString());
							filesExecutable.println("Mimetype: " + mimetype);
							filesExecutable.println();
							
							//TODO: Count files which are potentially executable

							isonecessary = true;
							
							// TODO: If ISO Image recommended, create one and copy files. Else only copy files.
													
							// TODO Adobe Reader Software does not count.
						}
					}
				}

				if (isonecessary = true) {
					JOptionPane.showMessageDialog(null,
							"One or more files are potentially executable",
							"Create Iso Image", JOptionPane.PLAIN_MESSAGE);
				}
				
				//else
			}
			filesExecutable.close();
			outputfile.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(),"Error Message", 
					JOptionPane.ERROR_MESSAGE);

		}
	}
}
