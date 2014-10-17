package preservetools.files.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.xml.sax.SAXException;

public class AudioFilesConversion {

	static String audioFolder;

	public static void main(String args[]) throws IOException, SAXException,
			TikaException {

		// try {

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

					if ((extension.equals("mp3")) || (extension.equals("m4a"))
							|| (extension.equals("wma"))) {

						InputStream inputfile = new FileInputStream(
								files.get(i));

						System.out.println();
						System.out.println(files.get(i).toString());

						if (extension.equals("mp3")) {

							Metadata metadata = preservetools.files.MetadataExtraction
									.getMetadatafromMP3(inputfile);
						}

					}

					// File outputfile = new
					// File(preservetools.files.executables.CdRom_IsoImageChecker.archivFolder+
					// "\\" + tracktitle + ".wav");

					// AudioConverter con = new AudioConverter
					// (AudioStream stream, AudioFormat output);

					/* *
					 * long framelength = 275219; *
					 * 
					 * System.out.println("Size: " + metadata.size());
					 * //Long.parseLong(metadata.get("xmpDM:duration"));
					 * 
					 * 
					 * float sampleRate = Float.parseFloat(metadata
					 * .get("xmpDM:audioSampleRate")); int sampleSizeInBits =
					 * 16; int channels = Integer.parseInt(metadata
					 * .get("channels")); boolean signed = true; boolean
					 * bigEndian = true;
					 * 
					 * AudioFormat format = new AudioFormat(sampleRate,
					 * sampleSizeInBits, channels, signed, bigEndian);
					 * 
					 * @SuppressWarnings("resource") AudioInputStream
					 * audiostream = new AudioInputStream( inputfile, format,
					 * framelength);
					 * 
					 * AudioFormat audioformattest = audiostream .getFormat();
					 * System.out.println(audioformattest);
					 * 
					 * File outputfile = new File(
					 * preservetools.files.executables
					 * .CdRom_IsoImageChecker.archivFolder + "\\" + tracktitle +
					 * ".wav");
					 * 
					 * AudioSystem.write(audiostream, AudioFileFormat.Type.WAVE,
					 * outputfile);
					 */
				}

				else {
					// System.out.println("This file has not been converted: "
					// + files.get(i).toString());

				}
			}
		}
	}
	/*
	 * } catch (Exception e) { System.out.println(e); }
	 */
}
