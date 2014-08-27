package pdfHackerTools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class PdfUtilitiesTest {
	public static final int MEGABYTE_SIZE = 1024 * 1024;
	public static final File ISATOR_MANUAL;
	static {
		try {
			ISATOR_MANUAL = PdfUtilitiesTest.getResourceAsFile("pdfHackerTools/Isartor_test_suite_manual.pdf");
		} catch (URISyntaxException excep) {
			throw new AssertionError("Couldn't load test data.");
		}
	}

	/**
	 * Ensure string method 
	 * @throws IOException 
	 */
	@Test
	public final void testCheckPdfSizeFileMethods() throws IOException {
		// make an 18MB test file, should be too large
		File largeTest = getTestTempFile(18);
		// Check that it's flagged too large
		assertTrue(PdfUtilities.checkPdfSize(largeTest));
		
		// Isator manual is smaller than 16MB so should be false
		assertFalse(PdfUtilities.checkPdfSize(ISATOR_MANUAL));
		largeTest.delete();
	}

	@Test
	public final void testCheckPdfSizeString() throws IOException {
		// make an 18MB test file, should be too large
		File largeTest = getTestTempFile(18);
		// Check that it's flagged too large
		assertTrue(PdfUtilities.checkPdfSize(largeTest.getAbsolutePath()));
		
		// Isator manual is smaller than 16MB so should be false
		assertFalse(PdfUtilities.checkPdfSize(ISATOR_MANUAL.getAbsolutePath()));
		largeTest.delete();
	}

	public final static File getResourceAsFile(String resName)
			throws URISyntaxException {
		return new File(ClassLoader.getSystemResource(resName).toURI());
	}

	public final static File getTestTempFile(int sizeInMegaBytes) throws IOException {
		byte[] buffer = new byte[MEGABYTE_SIZE];
		File temp = File.createTempFile("random", ".tmp");
		temp.deleteOnExit();
		FileOutputStream output = new FileOutputStream(temp);
		for (int iLoop = 0; iLoop < sizeInMegaBytes; iLoop ++) {
			Random rand = new Random();
			rand.nextBytes(buffer);
			IOUtils.write(buffer, output);
		}
		output.close();
		return temp;
	}
}
