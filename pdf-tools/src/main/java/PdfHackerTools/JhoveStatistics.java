package PdfHackerTools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JhoveStatistics {
	
	public static void main(String args[]) throws IOException {
		
		String JhoveFindings = PdfUtilities.ChooseFile();

		FileInputStream inputStream = new FileInputStream(
				JhoveFindings);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		String line = null;
		
		int wellformed = 0;
		int malformed = 0;
		int invalid = 0;

		ArrayList<String> lines = new ArrayList<String>();

		StringBuilder responseData = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			responseData.append(line);
			lines.add(line);
		}

		int len= lines.size();		
		System.out.println (len);
		
		for (int i = 0; i <len; i++){
			// System.out.println(lines.get(i));
			if (lines.get(i).contains("Well-Formed and valid")) {
				wellformed++;
			}			
			else if (lines.get(i).contains("not valid")) {
				invalid++;
			}	
			else if (lines.get(i).contains("malformed")) {
				malformed++;
			}
		}
		System.out.println ("PDF files Well-Formed and valid: " + wellformed);
		System.out.println ("PDF files malformed: " + malformed);
		System.out.println ("PDF files invalid: " + invalid);
		reader.close();

	}

}
