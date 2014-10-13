package preservetools.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import preservetools.files.executables.CdRom_IsoImageChecker;

public class ListsFiles {
	/**
	 * lists all files and directories in given directory
	 * 
	 * @param
	 * @return: ArrayList<File> of all found files and subfolders
	 * @throws IOException
	 * 
	 */
	public static ArrayList<File> getPaths(File file, ArrayList<File> list)
			throws IOException {
		if (file == null || list == null || !file.isDirectory())
			return null;
		File[] fileArr = file.listFiles();
		for (File f : fileArr) {
			// TODO If a folder is chosen that cannot be searched/read, e. g.
			// C:/, the tool runs into issues

			// TODO: This cannot be done in this generic method. It's only for
			// the Cdom_IsoImageChecker
			/*
			 * if (f.isDirectory()) { if (!f.getName().toLowerCase().contains
			 * ("adobereader")) { getPaths(f, list); }
			 * 
			 * else { System.out.println ("Adobe Reader Software Folder:" +
			 * f.toString().toLowerCase()); }
			 * 
			 * }
			 */
			if (!f.isDirectory()) { // adds only non-directories (=files) to the
									// ArrayList of Files

				String extension = preservetools.files.GenericFileAnalysis
						.getFileExtension(f);

				if (extension != null) {

					// TODO: What about .tar-files etc?
					if (extension.equals("zip")) {
						try {
							ZipFile zf = new ZipFile(f.toString());
							Enumeration<? extends ZipEntry> e = zf.entries();
							ZipEntry ze;
							while (e.hasMoreElements()) {
								ze = e.nextElement();

								if (!ze.isDirectory()) {
									File CompFile = new File(ze.toString());
									list.add(CompFile);
								}

							}
							zf.close();
						}

						catch (Exception e) {
							// TODO: After this exception is caused, the adding
							// of the rest of the files in the zip folder stops
							CdRom_IsoImageChecker.filesExecutable.println(f
									+ " causes an Exception" + e);
						}
					}

					else {

						list.add(f);
					}
				}

				else {
					System.out
							.println("Extension of file could not be extracted "
									+ f);
					list.add(f);
				}
			}
		}

		return list;
	}
}
