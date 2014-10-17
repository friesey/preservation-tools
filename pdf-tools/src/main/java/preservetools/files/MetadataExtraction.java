package preservetools.files;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MetadataExtraction {

	public static PrintWriter metadataOutput;

	public static Metadata getMetadatafromMP3(InputStream inputfile)
			throws IOException, SAXException, TikaException {
		// TODO Auto-generated method stub

		DefaultHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(inputfile, handler, metadata, parseCtx);

		outputsMetadata(metadata);

		return metadata;

	}

	public static void outputsMetadata(Metadata metadata) throws IOException {

		String tracktitle = metadata.get("title");
		String artist = metadata.get("xmpDM:artist");
		String duration = metadata.get("xmpDM:duration");
		String sampleRate = metadata.get("xmpDM:audioSampleRate");
		String channel = metadata.get("channels");
		String compression = metadata.get("xmpDM:audioCompressor");

		metadataOutput = new PrintWriter(new FileWriter("D:\\"
				+ "MetadataFolder\\" + tracktitle + ".txt"));

		metadataOutput.println("Titel: " + tracktitle);
		metadataOutput.println("Verfasser: " + artist);
		metadataOutput.println("Dauer " + duration);
		metadataOutput.println("Sample-Rate: " + sampleRate);
		metadataOutput
				.println("Channel (1 für mono, 2 für stereo): " + channel);
		metadataOutput.println("Audio Compression: " + compression);
		metadataOutput.println();

		metadataOutput.println("No. of Metadata Elements: " + metadata.size());
		String[] metadataNames = metadata.names();
		for (String name : metadataNames) {
			metadataOutput.println(name + ": " + metadata.get(name));
			
			metadataOutput.close();

		}
	}
}
