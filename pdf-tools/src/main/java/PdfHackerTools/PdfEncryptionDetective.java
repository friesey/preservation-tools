package PdfHackerTools;

import java.io.FileNotFoundException;

public class PdfEncryptionDetective {

	static String t;

	public static void main(String args[]) throws FileNotFoundException {

		try {

			t = PdfUtilities.ChooseFolder();

			if (t != null) {

				// TODO: check for encryption via PDFBox and/or iText and
				// outputs in XML

				// mind the XSLT

			}
		}

		catch (FileNotFoundException e) {
		}

	}

}
