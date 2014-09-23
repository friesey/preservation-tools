package preservetools.files.pdf;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.itextpdf.text.pdf.PdfReader;

public class XmpMetadataReader {

	public static void main(String args[]) throws IOException {

		String pdfFile = preservetools.utilities.FileBrowserDialog.chooseFile();

		String xmpMetadata;
		PdfReader reader;

		@SuppressWarnings("resource")
		PrintWriter outputfile = new PrintWriter(new FileWriter("C://PresToolsTest//log.xml"));
		// TODO: check if it is really a PDF before creating reader
		try {
			reader = new PdfReader(pdfFile);
			if (reader.getMetadata() != null) {
				xmpMetadata = new String(reader.getMetadata());
				
				/*
				ArrayList<String> xmpMetadataArr = new ArrayList<String>();
				@SuppressWarnings("resource")
				Scanner input = new Scanner(xmpMetadata);
				input.useDelimiter(">");
				
				while (input.hasNext()){
				xmpMetadataArr.add(input.next());
				}

				for (int i = 0; i < xmpMetadataArr.size(); i++) {
					outputfile.println(xmpMetadataArr.get(i));
				}
				*/
				outputfile.print (xmpMetadata);
				
				reader.close();
				outputfile.close();
			}

			else {
				System.out.println("No XMP Metadata found");
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
