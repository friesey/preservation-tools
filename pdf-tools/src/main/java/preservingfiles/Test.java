package preservingfiles;

import java.io.File;

public class Test {

	public static void main(String args[]) {

		ZbwFile testfile = new ZbwFile();
		testfile.path = "C:\\FileSample\\text\\PDFs\\100PdfFiles\\605.pdf";
		System.out.println("Filename: " + testfile.getName(testfile.path));		
		System.out.println("MD5 Checksum: " + testfile.getMD5Checksum(testfile.toFile(testfile.path)));
		System.out.println("Filesize: " + testfile.getSizeinKB(testfile.toFile(testfile.path)) + " Kilobytes");
		
		
	}

}
