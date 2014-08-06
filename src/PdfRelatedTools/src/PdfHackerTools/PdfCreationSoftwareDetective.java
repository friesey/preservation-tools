package PdfHackerTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.pdf.PdfReader;

public class PdfCreationSoftwareDetective {

	static String t;
	static int ProducerID;
	static ArrayList<String> ProducerType;
	static BufferedReader PdfHeaderTest;
	static PdfReader reader;
	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		t = PdfUtilities.ChooseFolder();

		ProducerID = 0;
		ProducerType = new ArrayList<String>();

		ArrayList<File> files = PdfUtilities.getPaths(new File(t),
				new ArrayList<File>());
		if (files == null)
			return;

		String extension;

		outputfile = new PrintWriter(new FileWriter(t + "//"
				+ "CreationSoftwareDetective.txt"));

		try {
			for (int i = 0; i < files.size(); i++)

				if (files.get(i) != null) { // maybe not necessary
					// prints out only files and not the subdirectories as well
					if (!files.get(i).isDirectory()) {

						// null pointer exception

						extension = Files.probeContentType(files.get(i)
								.toPath());
						if (extension.equals("application/pdf")) {
							if (PdfUtilities.FileHeaderTest(files.get(i)) == true) {

								try {
									PDDocument testfile = PDDocument.load(files
											.get(i));
									if (PdfUtilities.EncryptionTest(testfile) == false) {
										{
											reader = new PdfReader(files.get(i)
													.toString());
											GetProducer(reader);
											reader.close();

										}

									}
									testfile.close();
								}

								catch (IOException e) {

									outputfile
											.println(files.get(i)
													+ " is so damaged it cannot be parsed: "
													+ e);
								}

							}
						}
					}
				}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		// redundante Einträge entfernen
		HashMap<String, String> hmTemp = new HashMap<String, String>();
		for (String item : ProducerType) {
			hmTemp.put(item, item);
		}
		ProducerType.clear();
		ProducerType.addAll(hmTemp.keySet());
		Collections.sort(ProducerType);

		for (String item : ProducerType) {
			outputfile.println(item);
		}

		// in case no PDF-files are found, the outputfile comes out empty. Is
		// this intended?
		outputfile.close();

	}

	/**
	 * Reads the Producer (Software which has created the PDF-file) from the XMP
	 * Metadata
	 * 
	 * @param Takes
	 *            in the PdfReader
	 * @return: Producer Type in an ArrayList<String> etc
	 */
	@SuppressWarnings("rawtypes")
	private static ArrayList<String> GetProducer(PdfReader reader) {
		Map info = reader.getInfo();
		if (info.get("Producer") != null) {
			ProducerType.add(info.get("Producer").toString());
		}
		return ProducerType;
	}
}
