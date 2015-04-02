package preservingfiles;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.pdf.PdfReader;

public class ZbwFilePdf extends ZbwFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PDDocument pdfFile;
	boolean isEncrypted;
	boolean isPdfA;

	public static PDDocument toPDDocument(File file) throws IOException {
		try {
			PDDocument pdfFile = PDDocument.load(file);
			return pdfFile;
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isEncrypted(PDDocument pdfFile) {
		if (pdfFile.isEncrypted()) {
			return true;
		} else {
			return false;
		}
	}

	// TODO: int getPdfVersion

	public static boolean isPdfA(String pdfString) {
		System.out.println(pdfString);
		try {
			PdfReader reader = new PdfReader(pdfString);
			if (reader.getMetadata() != null) {
				String xmpMetadata = new String(reader.getMetadata()); // nullpointerException
				reader.close();
				if (xmpMetadata.contains("pdfaid:conformance")) {
					return true;
				} else {
					return false;
				}
			}

		} catch (Exception e) {
			System.out.println("An Exception occured while testing if PDF/A: " + e);
		}
		return false;
	}

}
