package filetools.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class DeletionSpaces {

	static String examinedFolder;

	public static void main(String args[]) throws IOException {

		try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();
			if (examinedFolder != null) {

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
				if (files == null)
					return;
				String mimetype;
				PdfReader reader;

				for (int i = 0; i < files.size(); i++) {
					System.out.println(files.get(i).getCanonicalPath());
					mimetype = filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
					if (mimetype != null) {
						if (mimetype.equals("application/pdf")) {						
								reader = new PdfReader(files.get(i).toString());
								if (!reader.isEncrypted()) {
									createVersionWithLesserSpaces(reader,files.get(i));
								}
						}
					}
				}
			}
		} catch (Exception e) {

		}
	}

	private static void createVersionWithLesserSpaces(PdfReader reader, File file) {
		
		String filename = file.getName();
		System.out.println (filename);
		
/*		Document document = new Document();

		PdfCopy copy = new PdfCopy(document, new FileOutputStream(examinedFolder + "//" + "Mig_iText" + filename));

		copy.setPdfVersion(PdfWriter.PDF_VERSION_1_5);
		document.open();
		int n = reader.getNumberOfPages();
		for (int i = 0; i < n;) {
			copy.addPage(copy.getImportedPage(reader, ++i));
		}
		copy.freeReader(reader);
		document.close();*/
		
	}
}
