package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;

public class TestClass {

	public static String folderforzips;
	public static String newzipfolder;

	public static void main(String args[]) throws Exception {

		// String folder = utilities.BrowserDialogs.chooseFolder();
		String folder = "C://FileSample//zipSample"; //hard coded
		if (folder != null) {
			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
			folderforzips = utilities.FolderMethods.createnewFolder();
			for (int i = 0; i < files.size(); i++) {
				String extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();
				if (extension.equals("zip")) {
					
				String dirname = files.get(i).getName();
				int len = dirname.length()-4;
		
				newzipfolder  = dirname.substring(0,len);
				
				utilities.FolderMethods.createnewFolderfromString(newzipfolder );
				

					ZipFile zf = new ZipFile(files.get(i));
					for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();) {
						try {
							ZipEntry entry = e.nextElement();
							utilities.ListsFiles.ziptofile (entry, zf);
							// ziptofile(entry, zf);
						} catch (Exception exc) {
							System.out.println(exc);
						}

					}
				}
			}
		}
	}

	private static void ziptofile(ZipEntry entry, ZipFile zf) throws IOException {
		
		//This method works!
		
		byte[] buffer = new byte[ 0xFFFF ];
		InputStream in = zf.getInputStream(entry);
		
		String extension = FilenameUtils.getExtension(entry.toString()).toLowerCase();
		
		File newfile = new File ("D://Test//test." + extension);
		OutputStream os = new FileOutputStream (newfile);
        for ( int len; (len = in.read(buffer)) != -1; ) 
            os.write( buffer, 0, len );       
	in.close();
	os.close();
	}
}