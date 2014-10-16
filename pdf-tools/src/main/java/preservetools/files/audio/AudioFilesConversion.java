package preservetools.files.audio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class AudioFilesConversion {

	static String audioFolder;

	public static void main(String args[]) throws IOException {

		try {

			JOptionPane.showMessageDialog(null, "CD ROM Dialog",
					"Please choose Folder with Audio Files",
					JOptionPane.QUESTION_MESSAGE);
			audioFolder = preservetools.utilities.FolderBrowserDialog
					.chooseFolder();

			JOptionPane.showMessageDialog(null, "CD ROM Dialog",
					"Please choose where your files will be archived",
					JOptionPane.QUESTION_MESSAGE);
			preservetools.files.executables.CdRom_IsoImageChecker.archivFolder = preservetools.utilities.FolderBrowserDialog
					.chooseFolder();

			if (audioFolder != null
					&& preservetools.files.executables.CdRom_IsoImageChecker.archivFolder != null) {

				ArrayList<File> files = preservetools.utilities.ListsFiles
						.getPaths(new File(audioFolder), new ArrayList<File>());

				for (int i = 0; i < files.size(); i++) {

					String extension = FilenameUtils.getExtension(
							files.get(i).toString()).toLowerCase();

					if (extension != null) {

						if (extension.equals("m4a")) {

							// AudioInputStream audioInputStream =
							// AudioSystem.getAudioInputStream(files.get(i));
							//
							// System.out.println(audioInputStream.toString());
							//
							// AudioFileFormat audiofileformatwave = new
							// AudioFileFormat(
							// AudioFileFormat.Type.WAVE,
							// audioInputStream.getFormat(),
							// (int) audioInputStream.getFrameLength());
							//
							// JOptionPane.showMessageDialog(null,
							// "CD ROM Dialog",
							// "Please choose where your files will be archived",
							// JOptionPane.QUESTION_MESSAGE);
							// preservetools.files.executables.CdRom_IsoImageChecker.archivFolder
							// = preservetools.utilities.FolderBrowserDialog
							// .chooseFolder();
							//
							// AudioFileFormat.Type fileType =
							// audiofileformatwave.getType();
							// File outputfile = new File(
							// preservetools.files.executables.CdRom_IsoImageChecker.archivFolder
							// + tracktitle);
							// AudioSystem.write(audioInputStream, fileType,
							// outputfile);
							//
							//

						}

					}
				}
			}
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}
}
