package preservetools.externalToolAnalysis;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunFits {

	static String fitsPath;
	static String examinedFile;
	static String examinedFolder;
	static Logger logger = LoggerFactory.getLogger(FitsAnalysis.class);

	public static void main(String args[]) throws IOException,
			InterruptedException {

		try {

			examinedFile = preservetools.utilities.FileBrowserDialog
					.chooseFile();
			// examinedFolder =
			// preservetools.utilities.FolderBrowserDialog.chooseFolder();

			// -h : print this message
			// -i <arg> : input file or directory
			// -o <arg> : output file
			// -r : process directories recursively when -i is a directory
			// -v : print version information
			// -x: convert FITS output to a standard metadata schema
			// -xc : output using a standard metadata schema and include FITS
			// xml

			File outputFile = new File("C://PresToolsTest//OutputFits.txt");			
			String fitsPath = "C://Users//Friese Yvonne//FITS//fits-0.8.0//fits.bat";			
			Process process = Runtime.getRuntime().exec(fitsPath);		
			
		}

		catch (IOException e) {
			logger.error("Error analyzing " + e);
		}

	}

}
