package PdfHackerTools;

public class PdfAValidator {
	
static String t;
	
	public static void main (String args[]) {
	
	t= PdfUtilities.ChooseFolder();	
	
	// checks PDFA-Header and only runs Validations against these files
	
	//todo: checks against PDF/A compliance via PDFBox
	// see here: https://pdfbox.apache.org/cookbook/pdfavalidation.html
	
	}

}
