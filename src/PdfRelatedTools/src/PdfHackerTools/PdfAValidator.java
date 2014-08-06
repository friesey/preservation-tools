package PdfHackerTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PdfAValidator {

	static String t;

	public static void main(String args[]) throws IOException {

		t = PdfUtilities.ChooseFolder();

		// checks PDFA-Header and only runs Validations against these files

		// can be either: &PDF-1.1, 1.2, 1.3 or 1.4

		// Metadata key in Catalog dic - how to detect easily?

		// todo: checks against PDF/A compliance via PDFBox
		// see here: https://pdfbox.apache.org/cookbook/pdfavalidation.html

		ArrayList<File> files = PdfUtilities.getPaths(new File(t),
				new ArrayList<File>());
		if (files == null)
			return;

		/*
		 * PDDocument.load(file); It'll fail with an Exception if the PDF is
		 * corrupted etc. If it succeeds you can also check if the PDF is
		 * encrypted using .isEncrypted()
		 */

	}

}
