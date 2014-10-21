package preservetools.files.audio;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JOptionPane;

public class AudioCD {

	public static void extractAudioFiles(ArrayList<File> files) {
		
		/*

		// it is already known that .cda-files are included
		// extract wav files
		// copy them to some folder
		
		int test = 0;

		for (int i = 0; i < files.size(); i++) {
			test++;
		
			System.out.println (test);

			try {
				int len = files.get(i).toString().length();

				String tracktitle = files.get(i).toString()
						.substring(3, len - 4);

				System.out.println(tracktitle);

				// TODO: As cda Files are nor really Audio-Files, an
				// AudioInputStream cannot be generated without a ripper. Will
				// look for a usable ripper soon.

				// AudioInputStream audioInputStream =
				// AudioSystem.getAudioInputStream(files.get(i));
				//
				// System.out.println(audioInputStream.toString());
				//
				// AudioFileFormat audiofileformatwave = new AudioFileFormat(
				// AudioFileFormat.Type.WAVE,
				// audioInputStream.getFormat(),
				// (int) audioInputStream.getFrameLength());
				//
				// JOptionPane.showMessageDialog(null, "CD ROM Dialog",
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
				// AudioSystem.write(audioInputStream, fileType, outputfile);
				//
				//
			}

			catch (Exception e) {
				System.out.println("Audio CD Error: " + e);
			}
		}
		*/
	}
}
