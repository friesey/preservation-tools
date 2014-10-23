package externalToolAnalysis;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FitsAnalysis {

	static String fitsPath;
	static String examinedFile;
	static String examinedFolder;
	static Logger logger = LoggerFactory.getLogger(FitsAnalysis.class);

	public static void main(String args[]) throws IOException,
			InterruptedException {

		examinedFile = utilities.FileBrowserDialog.chooseFile();
		// examinedFolder =
		// preservetools.utilities.FolderBrowserDialog.chooseFolder();

		// -h : print this message
		// -i <arg> : input file or directory
		// -o <arg> : output file
		// -r : process directories recursively when -i is a directory
		// -v : print version information
		// -x: convert FITS output to a standard metadata schema
		// -xc : output using a standard metadata schema and include FITS xml

		try {
			final File fitsFile = new File(
					"C://Users//Friese Yvonne//FITS//fits-0.8.0//fits.bat");

			if (fitsFile.exists()) {

				final File outputFile = new File(
						String.format("C://PresToolsTest//OutputFits.txt"));

				// String argument = ("-i " + examinedFile +
				// "-o C://PresToolsTest//OutputFits.txt");

				final String argument = " -v";

				// String argument = (" -v");

				final ProcessBuilder runFits = new ProcessBuilder(
						fitsFile.getAbsolutePath(), argument);

				runFits.redirectErrorStream(true);
				runFits.redirectOutput(outputFile);

		//		runFits.environment().put("message","Example of process builder");

				final Process process = runFits.start();
				final int exitStatus = process.waitFor();
				System.out.println("Processed finished with status: "
						+ exitStatus);

			}

			else {
				System.out.println("File could not be found.");
			}
		}

		catch (IOException e) {
			logger.error("Error analyzing " + e);
		}

	}

}
