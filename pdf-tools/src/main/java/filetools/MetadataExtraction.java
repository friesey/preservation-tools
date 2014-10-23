package filetools;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.audio.AudioParser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.parser.mp4.MP4Parser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MetadataExtraction {

	public static PrintWriter metadataOutput;

	public static Metadata getMetadatafromMP3(InputStream inputfile)
			throws IOException, SAXException, TikaException {


		DefaultHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(inputfile, handler, metadata, parseCtx);

		return metadata;

	}
	
	public static Metadata getMetadatafromMP4(InputStream inputfile) throws IOException, SAXException, TikaException {
	
		DefaultHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new MP4Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(inputfile, handler, metadata, parseCtx);

		return metadata;
	}
	

	public static Metadata getMetadatafromAudio(InputStream inputfile) throws IOException, SAXException, TikaException {
		
		DefaultHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new AudioParser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(inputfile, handler, metadata, parseCtx);		

		return metadata;
	}

	public static void outputsMetadata(Metadata metadata) throws IOException {

		String tracktitle = metadata.get("title");
		String artist = metadata.get("xmpDM:artist");
		String compression = metadata.get("xmpDM:audioCompressor");
		String durationStr = metadata.get("xmpDM:duration");
		String sampleRateStr = metadata.get("xmpDM:audioSampleRate");
		String channelStr = metadata.get("channels");

		if (durationStr != null) {
			float duration = Float.parseFloat(durationStr);
		}
		if (sampleRateStr != null) {
			float samplerate = Float.parseFloat(sampleRateStr);
		}
		if (channelStr != null) {
			int channel = Integer.parseInt(channelStr);
		}

		// TODO: Hardcoded only for testing purposes

		metadataOutput = new PrintWriter(new FileWriter(
				"D:\\MetadataFolder\\audiotest.txt"));

		metadataOutput.println("Titel: " + tracktitle);
		metadataOutput.println("Verfasser: " + artist);
		metadataOutput.println("Dauer " + durationStr);
		metadataOutput.println("Sample-Rate: " + sampleRateStr);
		metadataOutput.println("Channel (1 für mono, 2 für stereo): "
				+ channelStr);
		metadataOutput.println("Audio Compression: " + compression);
		metadataOutput.println();

		metadataOutput.println("No. of Metadata Elements: " + metadata.size());
		String[] metadataNames = metadata.names();
		for (String name : metadataNames) {
			metadataOutput.println(name + ": " + metadata.get(name));
		}

	}



}
