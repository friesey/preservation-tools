package filetools.pdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;

public class PdftoXmlConversion {

	public static String examinedFolder;
	static String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"PdfToXmlConversionStyleSheet.xsl\"?>";
	static int begin;

	static int crit0begin;
	static int crit1begin;
	static int crit2begin;
	static int crit3begin;
	static int crit4begin;
	static int crit5begin;
	static int crit6begin;
	static int crit7begin;
	static int crit8begin;
	static int crit9begin;
	static int crit10begin;
	static int crit11begin;
	static int crit12begin;
	static int crit13begin;
	static int crit14begin;
	static int crit15begin;
	static int crit16begin;

	public static void main(String args[]) throws IOException {
		// try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();
			if (examinedFolder != null) {

				output.XslStyleSheets.PdfToXmlConversionStyleSheet(); // using
																		// the
																		// same
																		// XSLT
																		// Stylesheet
																		// for
																		// all
																		// of
																		// the
																		// XML
																		// Files

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
				for (int i = 0; i < files.size(); i++) {
					String mimetype = filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
					if (mimetype.equals("application/pdf")) {
						extractPdfContenttoXmlFile(files.get(i));
					}
				}
			}
		} /*catch (Exception e) {
			System.out.println(e);
		}
	}*/

	public static void extractPdfContenttoXmlFile(File file) throws IOException {
		// TODO Auto-generated method stub

		String filename = file.getName().replace(".pdf", "");

		PrintWriter pdftoXmlWriter = new PrintWriter(new FileWriter(examinedFolder + "//" + filename + ".xml"));
		String xmlVersion = "xml version='1.0'";
		String xmlEncoding = "encoding='ISO-8859-1'";
		pdftoXmlWriter.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
		pdftoXmlWriter.println(xsltStyleSheet);

		pdftoXmlWriter.println("<DataSeal>");

		// PdfReader reader = new PdfReader(file.toString());

		String [] lines = PdfAnalysis.extractsPdfLines(file.toString());
		
/*		List<String> listlines = new ArrayList<String>(lines.length);
		Collections.addAll(listlines, lines);*/

		for (int i = 0; i < lines.length; i++){

			
			if (lines[i].contains("Repository:")) {
				String[] parts = lines[i].split(":");
				pdftoXmlWriter.println("<Repository>" + parts[1] + "</Repository>");

				//
			}
			if (lines[i].contains("Seal Acquiry Date:")) {
				String[] parts = lines[i].split(":");
				pdftoXmlWriter.println("<SealAcquiryDate>" + parts[1] + "</SealAcquiryDate>");
			}

			if (lines[i].equals("Assessment")) {
				begin = i;				
			}			
			
		
			if (lines[i].contains("0. Repository Context")) { //or begins with?
				crit0begin = i;
			}
			
			if (lines[i].contains("1. The data producer deposits")) {
				crit1begin = i;
			}			

			if (lines[i].contains("2. The data producer provides")) {
				crit2begin = i;
			}
			
			if (lines[i].contains("3. The data producer provides the")) {
				crit3begin = i;
			}
			
			if (lines[i].contains("4. The data repository has an explicit ")) {
				crit4begin = i;
			}
			
			if (lines[i].contains("5. The data repository uses due")) {
				crit5begin = i;
			}
			
			if (lines[i].contains("6. The data repository applies")) {
				crit6begin = i;
			}
			
			if (lines[i].contains("7. The data repository has a")) {
				crit7begin = i;
			}
			
			if (lines[i].contains("8. Archiving takes place according")) {
				crit8begin = i;
			}
			
			if (lines[i].contains("9. The data repository assumes")) {
				crit9begin = i;
			}
			
			if (lines[i].contains("10. The data repository enables the users to")) {
				crit10begin = i;
			}
			
			if (lines[i].contains("11. The data repository ensures the")) {
				crit11begin = i;
			}
			
			if (lines[i].contains("12. The data repository ensures the")) {
				crit12begin = i;
			}
			
			if (lines[i].contains("13. The technical infrastructure")) {
				crit13begin = i;
			}
			
			if (lines[i].contains("14. The data consumer complies")) {
				crit14begin = i;
			}
			
			if (lines[i].contains("15. The data consumer conforms")) {
				crit15begin = i;
			}
			
			if (lines[i].contains("16. The data consumer")) {
				crit16begin = i;
			}
		}	
		
		pdftoXmlWriter.println("<Criterium0>");		
		int i = crit0begin;
		while (i < crit1begin) {
		//	if ((!lines[i].equals(" ")) && (!lines[i].contains("info@datasealofapproval"))) {
				
			pdftoXmlWriter.println(lines[i]);
		
			i++;
		//	}
		}		
		pdftoXmlWriter.println("</Criterium0>"); //maybe Criterium = 0 
		pdftoXmlWriter.println("<Criterium1>");	
		i = crit1begin;
		while (i < crit2begin) {
				
			pdftoXmlWriter.println(lines[i]);
	
			i++;
		//	}
		}		
		pdftoXmlWriter.println("</Criterium1>");
		pdftoXmlWriter.println("<Criterium2>");	
		
		i = crit2begin;
		while (i < crit3begin) {				
			pdftoXmlWriter.println(lines[i]);
	
			i++;
		//	}
		}		
		pdftoXmlWriter.println("</Criterium2>");
		pdftoXmlWriter.println("<Criterium3>");	
		i = crit3begin;
		while (i < crit4begin) {				
			pdftoXmlWriter.println(lines[i]);
		
			i++;
		//	}
		}		
		pdftoXmlWriter.println("</Criterium3>");
		
		pdftoXmlWriter.println("<Criterium4>");			
		i = crit4begin;
		while (i < crit5begin) {				
			pdftoXmlWriter.println(lines[i]);
		
			i++;
		//	}
		}		
		pdftoXmlWriter.println("</Criterium4>");
		
		
		pdftoXmlWriter.println("<Criterium5>");			
		i = crit5begin;
		while (i < crit6begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium5>");
		
		pdftoXmlWriter.println("<Criterium6>");			
		i = crit6begin;
		while (i < crit7begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium6>");
		
		pdftoXmlWriter.println("<Criterium7>");			
		i = crit7begin;
		while (i < crit8begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium7>");
		
		
		pdftoXmlWriter.println("<Criterium8>");			
		i = crit8begin;
		while (i < crit9begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium8>");
		
		pdftoXmlWriter.println("<Criterium9>");			
		i = crit9begin;
		while (i < crit10begin) {	
			System.out.println ("Crit 9 begin " + crit9begin);
			System.out.println ("Crit 10 begin " + crit10begin);
			System.out.println("i " + i);
			System.out.println("Lines length " + lines.length);
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium9>");
		
		pdftoXmlWriter.println("<Criterium10>");			
		i = crit10begin;
		while (i < crit11begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium10>");
		
		pdftoXmlWriter.println("<Criterium11>");			
		i = crit11begin;
		while (i < crit12begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium11>");
		
		pdftoXmlWriter.println("<Criterium12>");			
		i = crit12begin;
		while (i < crit13begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium12>");
		
		pdftoXmlWriter.println("<Criterium13>");			
		i = crit13begin;
		while (i < crit14begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium13>");
		
		
		pdftoXmlWriter.println("<Criterium14>");			
		i = crit14begin;
		while (i < crit15begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium14>");
		
		
		pdftoXmlWriter.println("<Criterium15>");			
		i = crit15begin;
		while (i < crit16begin) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium15>");
		
		pdftoXmlWriter.println("<Criterium16>");			
		i = crit16begin;
		while (i < lines.length) {				
			pdftoXmlWriter.println(lines[i]);		
			i++;		
		}		
		pdftoXmlWriter.println("</Criterium16>");

/*			if ((i > begin) && (!lines[i].equals(" ")) && (!lines[i].contains("info@datasealofapproval"))) {

				// do not copy empty lines,
				// do not copy lines in the head or food and
				// TODO do not copy page numbers
				// do not copy the stuff before the chapter 0

				if (i == crit0begin){
					pdftoXmlWriter.println("<Criterium0>");
				}
				
				if (i == crit1begin-1){
					pdftoXmlWriter.println("</Criterium0>");
				}
				
				pdftoXmlWriter.println(lines[i]);
			}
*/
	

		pdftoXmlWriter.println("</DataSeal>");
		pdftoXmlWriter.close();

	}
}
