package preservetools.files;

import java.io.File;
import java.util.ArrayList;

import javax.sound.*;

public class AudioCD {

	public static void extractAudioFiles(ArrayList<File> files) {

		// it is already known that .cda-files are included

		// extract wav files
		// copy them to some folder
		
		for (int i = 0; i < files.size(); i++) {
			
			try {
		
		// getAudioFileFormat (files.get(i));
		
		}
		
			catch (Exception e) {
				System.out.println ("Audio CD Error: " + e);
			}

	}
}
}