package filetools.pdf;

public class PdfTestClass {

	public static void main(String args[]) throws Exception {

		PdfObject testPdf = new PdfObject();
		String newPath = utilities.BrowserDialogs.chooseFile();
		testPdf.setPath(newPath);
		testPdf.pdfFile = testPdf.toFile(newPath);

		System.out.println(testPdf.getPath());
		System.out.println(testPdf.getName(testPdf.path));
		System.out.println(testPdf.getMD5Checksum(testPdf.pdfFile));
		// System.out.println(testPdf.isEncrypted(testPdf.pdfFile)); //TODO: get rid of all the log4j Logging in the Console
		testPdf.isPdfA = testPdf.isPdfA(testPdf.path);

		if (testPdf.isPdfA == true) {
			System.out.println(testPdf.name + " is a Pdf/A file");
		}
	}

}
