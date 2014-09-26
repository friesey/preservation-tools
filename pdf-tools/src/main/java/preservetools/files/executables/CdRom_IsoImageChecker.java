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

	public static void main(String args[]) throws IOException {

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

		@SuppressWarnings("resource")
		PrintWriter outputfile = new PrintWriter(new FileWriter(outputFolder
				+ "//" +  "CdRomExecutableAnalysis_" + CdRomName + ".txt"));

		PrintWriter filesExecutable = new PrintWriter(new FileWriter(
				outputFolder + "//" + "potentiallyExecutableFiles_" + CdRomName + ".txt"));
		try {

			if (examinedCdRom != null) {

				ArrayList<File> files = preservetools.utilities.ListsFiles
						.getPaths(new File(examinedCdRom),
								new ArrayList<File>());

				// TODO: ueber einen Ordner mit mehreren CD ROMs laufen lassen
				// und
				// pro CD ROM entscheiden

				// TODO: Falls ein Imaging notwendig wird, gleich von hier aus
				// veranlassen. Ansonsten nur kopieren

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
						}
					}
				}

				if (isonecessary = true) {
					JOptionPane.showMessageDialog(null,
							"One or more files are potentially executable",
							"Create Iso Image", JOptionPane.PLAIN_MESSAGE);
				}
			}
			filesExecutable.close();
			outputfile.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error Message", e.toString(),
					JOptionPane.ERROR_MESSAGE);

		}
	}
}
