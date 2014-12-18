package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.*;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

public class ListsFiles {
	/**
	 * lists all files and directories in given directory
	 *
	 * @param
	 * @return: ArrayList<File> of all found files and subfolders
	 *
	 */
	public static ArrayList<File> getPaths(File file, ArrayList<File> list) {

		try {
			if (file == null || list == null || !file.isDirectory())
				return null;
			File[] fileArr = file.listFiles();
			for (File f : fileArr) {
				// TODO If a folder is chosen that cannot be searched/read, e.
				// g.
				// C:/, the tool runs into issues
				if (!f.isDirectory()) {
					// should not add directories to the ArrayList of files
					list.add(f);

				} else {
					getPaths(f, list);
				}
			}
			return list;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Most likely you tried to choose a folder like \"C:\" but you do not have the rights to read files there.", "Info Message", JOptionPane.PLAIN_MESSAGE);
			return null;
		}
	}

	public static void unpackzip(File zipfile) throws ZipException, IOException {
		ZipFile zf = new ZipFile(zipfile);
		for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();) {
			try {
				ZipEntry entry = e.nextElement();
				ziptofile(entry, zf);
			} catch (Exception exc) {
				System.out.println(exc);
			}
		}
		zf.close();
	}

	private static void ziptofile(ZipEntry entry, ZipFile zf) throws IOException {

		String onlyname = entry.toString();
		String fullpath = zf.getName();		

		InputStream in = zf.getInputStream(entry);
		GZIPInputStream zipStream = new GZIPInputStream(in); //does not work
		byte[] buffer = new byte[zipStream.available()];
		zipStream.read(buffer);

		String[] parts = entry.getName().split("/");

		if (entry.isDirectory()) {
			// TODO: Create a new folder with the name in the folderforzips
			for (int l = 0; l < parts.length; l++) {
				if (!parts[l].contains(".")) {
					utilities.FolderMethods.createnewFolderfromString(parts[l]);
					// TODO: Does not see subfolders and that's why the next
					// part does not work
				}
			}
		}

		System.out.println(utilities.TestClass.folderforzips);
		System.out.println(parts[parts.length - 1]);
		// File targetFile = new File(utilities.TestClass.folderforzips + "//" +
		// entry.getName()); /*does not work because of the subfolders*/
		File targetFile = new File(utilities.TestClass.folderforzips + "//" + parts[parts.length - 1]); // but
																										// these
																										// files
																										// are
																										// all
																										// broken

		OutputStream outStream = new FileOutputStream(targetFile);

		for (int length; (length = zipStream.read(buffer)) != -1;) {
			outStream.write(buffer, 0, length);
		}

		if (outStream != null) {
			outStream.close();
		}

		if (zipStream != null) {
			zipStream.close();
		}

	}

	public static ArrayList<File> unpackjar(File jarfile) {
		ArrayList<File> arrjars = new ArrayList<File>();
		// TODO Auto-generated method stub
		return arrjars;
	}

	// tar can be unpacked via http://tutego.de/go/tarcvs
}