package preservetools.files.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
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

						if (extension.equals("mp3")) {						
		
							String tracktitle = "track"+i;
													
							InputStream inputfile = new FileInputStream(
									files.get(i));

							long length = 1014; 

							float sampleRate = 8000;
							int sampleSizeInBits = 8;
							int channels = 1;
							boolean signed = true;
							boolean bigEndian = true;

							AudioFormat format = new AudioFormat(sampleRate,
									sampleSizeInBits, channels, signed,
									bigEndian);

							@SuppressWarnings("resource")
							AudioInputStream audiostream = new AudioInputStream(
									inputfile, format, length);

							long test = audiostream.getFrameLength();
							System.out.println(test);
							
							AudioFormat audioformattest = audiostream.getFormat();							
							System.out.println(audioformattest);							
										 							 
							File outputfile = new File(
							 preservetools.files.executables.CdRom_IsoImageChecker.archivFolder
							 + "\\" + tracktitle);
							
							 AudioSystem.write(audiostream, AudioFileFormat.Type.WAVE,
							 outputfile);						
							

						}

						else {
							System.out
									.println("This file has not been converted: "
											+ files.get(i).toString());
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
