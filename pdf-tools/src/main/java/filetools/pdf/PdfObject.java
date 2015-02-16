package filetools.pdf;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import com.itextpdf.text.pdf.PdfReader;

public class PdfObject extends filetools.FileObject{
	// org.slf4j.Logger logger = LoggerFactory.getLogger(PdfObject.class);
	String path;
	String name;
	File pdfFile;

	boolean isEncrypted;
	boolean isPdfA;
	String xmpMetadata;
	PdfReader reader;
	// constructor for the PdfObject
	public int id;
	public static int count = 0;

	public PdfObject(String path) {
		id = count;
		count++;
	}

	public boolean isEncrypted(File file) throws IOException {
		// TODO: might test type of encryption via iText
		PDDocument pdf = PDDocument.load(file);
		if (pdf.isEncrypted()) {
			isEncrypted = true;
		} else {
			isEncrypted = false;
		}
		pdf.close();
		// logger.error (e.toString());
		return isEncrypted;
	}

	public boolean isPdfA(String path) throws IOException {
		try {
			reader = new PdfReader(path);
			if (reader.getMetadata() != null) {
				xmpMetadata = new String(reader.getMetadata());
				if (xmpMetadata.contains("pdfaid:conformance")) {
					isPdfA = true;
				} else {
					isPdfA = false;
				}
			} else {
				isPdfA = false;
			}
			return isPdfA;
		} catch (Exception e) {
			isPdfA = false;
			return isPdfA;
		}
	}
	// encryption

	// number of lines
	// pdf ok
	// pdf valid (but do not use jhove here)
	// pdf version?
	// get xmp metadata
	// what is available under pdfAnalysis
}