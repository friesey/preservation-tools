package preservingfiles;

import java.io.IOException;

import com.itextpdf.text.pdf.PdfEncryptor;
import com.itextpdf.text.pdf.PdfReader;

public class ZbwFilePdfEncryption extends ZbwFilePdf {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean printPossible;
	boolean degradedprintPossible;
	boolean modifyPossible;
	boolean modifyAnnotationsPossible;
	boolean copyPossible;
	boolean assemblyPossible;
	boolean fillinPossible;
	boolean screenreadPossible;

	public static void testPermissions(String pdf) throws IOException {

		PdfReader reader = new PdfReader(pdf); //Invalid PdfException. I do not know why yet.
		int permissions = reader.getPermissions();
		
		String info = PdfEncryptor.getPermissionsVerbose(permissions);
		
		System.out.println(info);
		

	}

}
