package filetools.pdf;

import java.io.IOException;

public class PdfinXmlConversion {

	public static String examinedFolder;

	public static void main(String args[]) throws IOException {
		try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();
			if (examinedFolder != null) {

			}
		} catch (Exception e) {

		}
	}
}
