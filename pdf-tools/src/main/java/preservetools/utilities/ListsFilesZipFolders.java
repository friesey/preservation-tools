package preservetools.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import preservetools.files.executables.CdRom_IsoImageChecker;

public class ListsFilesZipFolders {

	public static ArrayList<File> unzipFolder(File zipfile,
			ArrayList<File> ziplist) {
		try {

			ZipFile zf = new ZipFile(zipfile.toString());
			Enumeration<? extends ZipEntry> e = zf.entries();
			ZipEntry ze;
			while (e.hasMoreElements()) {
				ze = e.nextElement();

				if (!ze.isDirectory()) {
					File CompFile = new File(ze.toString());
					ziplist.add(CompFile);
				}

				else {
					ArrayList<File> filesinZipFolder = preservetools.utilities.ListsFiles
							.getPaths(new File(ze.toString()),
									new ArrayList<File>());

					for (int i = 0; i < filesinZipFolder.size(); i++) {
						ziplist.add(filesinZipFolder.get(i));
					}
				}
				zf.close();
			}
		} catch (Exception e) {

			// TODO: After this exception is caused, the adding
			// of the rest of the files in the zip folder stops

			CdRom_IsoImageChecker.filesExecutable.println(zipfile
					+ " causes an Exception" + e);

		}
		return ziplist;

	}
}
