package preservetools.externalToolAnalysis;

import java.io.IOException;

public class FitsAnalysis {

	static String fitsPath;

	public static void main(String args[]) throws IOException,
			InterruptedException {

		fitsPath = "C://Users//Friese Yvonne//FITS//fits-0.8.0";

		// -h : print this message
		// -i <arg> : input file or directory
		// -o <arg> : output file
		// -r : process directories recursively when -i is a directory
		// -v : print version information
		// -x: convert FITS output to a standard metadata schema
		// -xc : output using a standard metadata schema and include FITS xml
		
		ProcessBuilder runsFits = new ProcessBuilder("java", "-Xmx1024m", "-Xms1024m",
				"-DTOOLS_DIR=/home/IM/work/dist", "-Daoi=whole", "-jar", fitsPath);
		
		Process p = runsFits.start();
		
		

	}

}
