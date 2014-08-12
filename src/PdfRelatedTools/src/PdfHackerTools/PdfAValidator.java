package PdfHackerTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class PdfAValidator {

	static String t;

	public static void main(String args[]) throws IOException {

		try {

			t = PdfUtilities.ChooseFolder();		

			// checks PDFA-Header and only runs Validations against these files

			// can be either: &PDF-1.1, 1.2, 1.3 or 1.4

			// Metadata key in Catalog dic - how to detect easily?

			// todo: checks against PDF/A compliance via PDFBox
			// see here: https://pdfbox.apache.org/cookbook/pdfavalidation.html

			if (t != null) {

				ArrayList<File> files = PdfUtilities.getPaths(new File(t),
						new ArrayList<File>());
				if (files == null)
					return;

				for (int i = 0; i < files.size(); i++) {
					if (!files.get(i).isDirectory() && files.get(i) != null) {
						System.out.println(i + 1);

						try {

							System.out.println(files.get(i).getCanonicalPath());
							
							if(!PdfUtilities.PdfSizeChecker (files.get(i))) {
								
							}
							

							

						}
						/*
						 * PDDocument.load(file); It'll fail with an Exception
						 * if the PDF is corrupted etc. If it succeeds you can
						 * also check if the PDF is encrypted using
						 * .isEncrypted()
						 */

						catch (IOException e) {
							System.out.print(e);
						}

					}

				}

			}

		} catch (FileNotFoundException e) {
		}
	}
}