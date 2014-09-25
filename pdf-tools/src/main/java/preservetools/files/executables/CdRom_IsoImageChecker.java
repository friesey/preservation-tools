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

	public static void main(String args[]) throws IOException {

		JOptionPane.showMessageDialog(null, "CD ROM Dialog",
				"Please choose CD ROM Folder", JOptionPane.PLAIN_MESSAGE);

		examinedCdRom = preservetools.utilities.FolderBrowserDialog
				.chooseFolder();

		JOptionPane.showMessageDialog(null, "Output Folder",
				"Please choose Folder where Outputfile will be created",
				JOptionPane.PLAIN_MESSAGE);

		outputFolder = preservetools.utilities.FolderBrowserDialog
				.chooseFolder();

		PrintWriter outputfile = new PrintWriter(new FileWriter(outputFolder
				+ "//" + "CdRomExecutableAnalysis.txt"));

		if (examinedCdRom != null) {

			ArrayList<File> files = preservetools.utilities.ListsFiles
					.getPaths(new File(examinedCdRom), new ArrayList<File>());

			// TODO: Über einen Ordner mit mehreren CD ROMs laufen lassen und
			// pro CD ROM entscheiden?

			// TODO: Falls ein Imaging notwendig wird, gleich von hier aus
			// veranlassen? Ansonsten nur kopieren?

			// TODO: Dateiendungen recherchieren, die auf Executables hindeuten
			// wie .jar , .exe, .bat usw.

			// TODO: Den Adobe-Reader nicht mit archivieren und auch bei der
			// Imaging-Entscheidung stets aussparen

			for (int i = 0; i < files.size(); i++) {

				mimetype = preservetools.files.GenericFileAnalysis
						.getFileExtension(files.get(i));

				extension = FilenameUtils.getExtension(files.get(i).toString());

				outputfile.println("Mimetype: " + mimetype);
				outputfile.println("File-Extension: " + extension);
				outputfile.println();

				if (!mimetype.contains("text") || !mimetype.contains("image")) {
					// not mimetype text or image would be an executable, would
					// it?

					// TODO: it is also possible to go the other way to image
					// when Formats are unknown.

					if ((extension.contains(".bat") || extension
							.contains(".jar")) || extension.contains(".exe")) {

						// TODO: Which other extensions are possible?
						outputfile.println("This CD ROM has to be imaged");
						return;
						/*
						 * it is not necessary to examine other files. One
						 * executable is justification enough to have to create
						 * and Image.
						 */

					}

				}

			}
		}

		outputfile.close();
	}
}
