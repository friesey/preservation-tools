package filetools.pdf;

public class PdfTestClass {
	
	public static void main(String args[]) throws Exception {
		
		PdfObject testPdf = new PdfObject();		
		String newPath = utilities.BrowserDialogs.chooseFile();		
		testPdf.setPath(newPath);
		testPdf.setName(testPdf.path);
		
		System.out.println(testPdf.getPath());
		System.out.println(testPdf.getName());
		
		
	}

}
