package filetools.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.xml.sax.SAXException;

public class AudioFilesConversion {

	static String audioFolder;

	static Metadata metadata;

	static String filename;

	static float duration;

	static float samplerate;

	static int channel;

	static int bits;

	public static void main(String args[]) throws IOException, SAXException, TikaException {

		try {

			JOptionPane.showMessageDialog(null, "CD ROM Dialog", "Please choose Folder with Audio Files", JOptionPane.QUESTION_MESSAGE);
			audioFolder = preservetools.utilities.FolderBrowserDialog.chooseFolder();

			JOptionPane.showMessageDialog(null, "CD ROM Dialog", "Please choose where your files will be archived", JOptionPane.QUESTION_MESSAGE);
			filetools.executables.CdRom_IsoImageChecker.archivFolder = preservetools.utilities.FolderBrowserDialog.chooseFolder();

			if (audioFolder != null && filetools.executables.CdRom_IsoImageChecker.archivFolder != null) {

				ArrayList<File> files = preservetools.utilities.ListsFiles.getPaths(new File(audioFolder), new ArrayList<File>());

				// TODO: Hardcoded only for testing purposes
					
				filetools.MetadataExtraction.metadataOutput = new PrintWriter(new FileWriter("D:\\MetadataFolder\\audiotest.txt"));

				for (int i = 0; i < files.size(); i++) {

					String extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();

					if (extension != null) {

						InputStream inputfile = new FileInputStream(files.get(i));

						System.out.println();
						System.out.println(files.get(i).toString());

						if (extension.equals("mp3") || extension.equals("m4a") || extension.equals("wma") || extension.equals("wav") || extension.equals("cda")) {

							if (extension.equals("mp3")) {
								metadata = filetools.MetadataExtraction.getMetadatafromMP3(inputfile);
							}

							else if (extension.equals("m4a")) {
								metadata = filetools.MetadataExtraction.getMetadatafromMP4(inputfile);
							}

							else if (extension.equals("wma") || extension.equals("wav") || extension.equals("cda")) {
								metadata = filetools.MetadataExtraction.getMetadatafromAudio(inputfile);
							}

							String tracktitle = metadata.get("title");
							String artist = metadata.get("xmpDM:artist");
							String compression = metadata.get("xmpDM:audioCompressor");
							String durationStr = metadata.get("xmpDM:duration");
							String sampleRateStr = metadata.get("xmpDM:audioSampleRate");
							String channelStr = metadata.get("channels");
							String encodingStr = metadata.get("encoding");
							String bitsStr = metadata.get("bits");
							String format = metadata.get("Content-Type");

							if (durationStr != null) {
								duration = Float.parseFloat(durationStr);
							}
							if (sampleRateStr != null) {
								samplerate = Float.parseFloat(sampleRateStr);
							}
							if (channelStr != null) {
								channel = Integer.parseInt(channelStr);
							}

							if (bitsStr != null) {
								bits = Integer.parseInt(channelStr);
							}

							filename = FilenameUtils.getBaseName(files.get(i).toString());

							filetools.MetadataExtraction.metadataOutput.println();
							filetools.MetadataExtraction.metadataOutput.println();
							filetools.MetadataExtraction.metadataOutput.println("Dateiname:" + (filename));
							filetools.MetadataExtraction.metadataOutput.println("No. of MD Elements: " + metadata.size());
							filetools.MetadataExtraction.metadataOutput.println("Titel: " + tracktitle);
							filetools.MetadataExtraction.metadataOutput.println("Verfasser: " + artist);
							filetools.MetadataExtraction.metadataOutput.println("Dauer " + durationStr);
							filetools.MetadataExtraction.metadataOutput.println("Sample-Rate: " + sampleRateStr);
							filetools.MetadataExtraction.metadataOutput.println("Channel (1 for mono, 2 for stereo): " + channelStr);
							filetools.MetadataExtraction.metadataOutput.println("Audio Compression: " + compression);
							filetools.MetadataExtraction.metadataOutput.println("Encoding: " + (encodingStr));
							filetools.MetadataExtraction.metadataOutput.println("Sample Size: " + (bitsStr));
							filetools.MetadataExtraction.metadataOutput.println("Content-Type: " + (format));
							filetools.MetadataExtraction.metadataOutput.println();

							String[] metadataNames = metadata.names();
							for (String name : metadataNames) {
								filetools.MetadataExtraction.metadataOutput.println(name + ": " + metadata.get(name));
							}

							if (extension.equals("wav")) {

								convertWavFile(files.get(i));
							}
						}

						else {
							System.out.println("File was ot analyzed: " + files.get(i).toString());
						}

					}
				}
				filetools.MetadataExtraction.metadataOutput.close();
			}
		}

		catch (Exception exc) {
			System.out.println(exc);
		}
	}

	@SuppressWarnings("static-access")
	private static void convertWavFile(File file) {

		File outputfile = new File(filetools.executables.CdRom_IsoImageChecker.archivFolder + "\\" + filename + ".wav");

		int totalFramesRead = 0;
		File fileIn = new File(file.toString());

		try {

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);

			int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
			System.out.println("Bytes per Frame: " + bytesPerFrame);

			if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
				// some audio formats may have unspecified frame size
				// in that case we may read any amount of bytes
				bytesPerFrame = 1;
			}

			// Set an arbitrary buffer size of 1024 frames.
			int numBytes = 1024 * bytesPerFrame;

			byte[] audioBytes = new byte[numBytes];

			try {
				int numBytesRead = 0;
				int numFramesRead = 0;

				// Try to read numBytes bytes from the file.

				while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {

					// Calculate the number of frames actually read.
					numFramesRead = numBytesRead / bytesPerFrame;
					totalFramesRead += numFramesRead;
					// Here, do something useful with the audio data that's
					// now in the audioBytes array...

					AudioFileFormat.Type filetype = new AudioFileFormat.Type(file.toString(), audioFolder);

					AudioFileFormat format = new AudioFileFormat(filetype, audioInputStream.getFormat(), numFramesRead);

					AudioSystem.write(audioInputStream, format.getType(), outputfile);

				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
