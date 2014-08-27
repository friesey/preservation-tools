package pdfHackerTools;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Test;

public class PdfUtilitiesTest {
	public static final File ISATOR_MANUAL;
	static {
		try {
			ISATOR_MANUAL = PdfUtilitiesTest.getResourceAsFile("pdfHackerTools/Isartor_test_suite_manual.pdf");
		} catch (URISyntaxException excep) {
			throw new AssertionError("Couldn't load test data.");
		}
	}

	@Test
	public final void testCheckPdfSizeFile() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCheckPdfSizeString() {
		fail("Not yet implemented"); // TODO
	}

	public final static File getResourceAsFile(String resName)
			throws URISyntaxException {
		return new File(ClassLoader.getSystemResource(resName).toURI());
	}

}
