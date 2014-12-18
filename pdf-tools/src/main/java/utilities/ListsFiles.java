package utilities;

import java.io.File;
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

	public static ArrayList<File> getpathsfromzip(File zipfile) throws ZipException, IOException {
		ArrayList<File> arrzips = new ArrayList<File>();
		ZipFile zf = new ZipFile(zipfile);
		for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();) {
			try {
				ZipEntry entry = e.nextElement();

				File entrytofile = ziptofile(entry, zf);
				arrzips.add(entrytofile);
			
			} catch (Exception exc) {
				System.out.println(exc);
			}
		}
		zf.close();
		// TODO Auto-generated method stub
		return arrzips;
	}
	
	public static ArrayList<File> unpackzip(File zipfile) throws ZipException, IOException {
		ArrayList<File> arrzips = new ArrayList<File>();
		ZipFile zf = new ZipFile(zipfile);
		for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements();) {
			try {
				ZipEntry entry = e.nextElement();
				File entrytofile = ziptofile(entry, zf);
				arrzips.add(entrytofile);
			
			} catch (Exception exc) {
				System.out.println(exc);
			}
		}
		zf.close();
		// TODO Auto-generated method stub
		return arrzips;
	}

	private static File ziptofile(ZipEntry entry, ZipFile zf) throws IOException {
		final InputStream zipStream = zf.getInputStream(entry);
		byte[] buffer = new byte[zipStream.available()];
		zipStream.read(buffer);

		/* gets directory of the zip file to know where to create the tmp file */
		String[] parts = zf.getName().split("//");
		int len = parts.length;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < len - 1; i++) {
			builder.append(parts[i]);
			builder.append("//");
		}

		String path = builder.toString();
		System.out.println(path);
		File targetFile = new File(path + "targetFile.tmp");
		/* does not work, gives the targetFile.tmp files back to main */

		OutputStream outStream = new FileOutputStream(targetFile);
		outStream.write(buffer);
		outStream.close();
		return targetFile;
	}

	public static ArrayList<File> unpackjar(File jarfile) {
		ArrayList<File> arrjars = new ArrayList<File>();
		// TODO Auto-generated method stub
		return arrjars;
	}

	// tar can be unpacked via http://tutego.de/go/tarcvs
}