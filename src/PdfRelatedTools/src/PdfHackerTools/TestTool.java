package PdfHackerTools;

import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class TestTool {	
	
	public static void main(String args[]) throws IOException {
	
	String Testfile  = PdfUtilities.ChooseFile();
	String ExtractedText = null;
	
	StringBuffer buff = new StringBuffer();    

         PdfReader reader = new PdfReader(Testfile);         
         PdfReaderContentParser parser = new PdfReaderContentParser(reader);
         TextExtractionStrategy strategy;
         
         for (int i = 1; i <= reader.getNumberOfPages(); i++) {
             strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
             ExtractedText = strategy.getResultantText().toString();
             buff.append(ExtractedText + "\n");
         }        
    
         
         String[] LinesArray;
         
         LinesArray= buff.toString().split("\n");
         
     	for (int j = 0; j < LinesArray.length; j++) {
     		System.out.println ("Line: " + j);
			System.out.println (LinesArray[j]);
	}
         
         reader.close();       
   
 } 
}
