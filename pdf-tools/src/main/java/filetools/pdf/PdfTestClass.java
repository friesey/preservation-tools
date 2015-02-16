package filetools.pdf;

public class PdfTestClass {

	public static void main(String args[]) throws Exception {

		PdfObject testPdf = new PdfObject();
		String newPath = utilities.BrowserDialogs.chooseFile();
		testPdf.setPath(newPath);
		testPdf.pdfFile = testPdf.toFile(newPath);

		System.out.println("<PdfAnalysis>");
		try {
			System.out.println("<FilePath>" + testPdf.getPath() + "</FilePath>");
			System.out.println("<FileName>" + testPdf.getName(testPdf.path) + "</FileName>");
			System.out.println("<MD5Checksum>" + testPdf.getMD5Checksum(testPdf.pdfFile) + "</MD5Checksum>");
			// System.out.println(testPdf.isEncrypted(testPdf.pdfFile)); //TODO:
			// get rid of all the log4j Logging in the Console
			testPdf.isPdfA = testPdf.isPdfA(testPdf.path);
			System.out.println("<PdfA>" + testPdf.isPdfA + "</PdfA>");
		}

		catch (Exception e) {

			String parts[] = e.toString().split(":");

			System.out.println("<Exception>" + parts[0] + "</Exception>");
			String text;
			StringBuilder build = new StringBuilder();

			for (int i = 1; i < parts.length; i++) {
				build.append(parts[i]);
			}

			text = build.toString();

			System.out.println("<ExceptionText>" + text + "</ExceptionText>");
		}

		System.out.println("</PdfAnalysis>");

	}

}
