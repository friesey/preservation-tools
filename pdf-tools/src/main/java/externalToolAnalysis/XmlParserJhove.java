package externalToolAnalysis;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class XmlParserJhove {

	public static void main(String args[]) throws Exception {

		JOptionPane.showMessageDialog(null, "Please choose the XML File to analyse", "XmlParsing", JOptionPane.QUESTION_MESSAGE);

		String xmlfile = utilities.BrowserDialogs.chooseFile();

		parseXmlFile(xmlfile);
	}

	public static void parseXmlFile(String xmlfile) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlfile);

			PrintWriter xmlsummary = new PrintWriter(new FileWriter((JhoveValidator.folder + "//" + "JhoveExaminationSummary" + ".xml")));

			String xmlVersion = "xml version='1.0'";
			String xmlEncoding = "encoding='ISO-8859-1'";
			String xmlxslStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"JhoveCustomized.xsl\"?>";

			xmlsummary.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			xmlsummary.println(xmlxslStyleSheet);
			xmlsummary.println("<JhoveFindingsSummary>");

			output.XslStyleSheets.JhoveCustomizedXsl();

			ArrayList<String> errormessages = new ArrayList<String>();

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					xmlsummary.println("<PdfFile>"); //TODO: should be changed to File, but as well in XSLT
					String testutf8 = eElement.getElementsByTagName("filename").item(0).getTextContent();

					if (testutf8.contains("&")) {
						String sub = JhoveValidator.normaliseToUtf8(testutf8);
						xmlsummary.println("<FileName>" + sub + "</FileName>");
					} else {
						xmlsummary.println("<FileName>" + eElement.getElementsByTagName("filename").item(0).getTextContent() + "</FileName>");
					}
					xmlsummary.println("<Status>" + eElement.getElementsByTagName("status").item(0).getTextContent() + "</Status>");

					String status = eElement.getElementsByTagName("status").item(0).getTextContent();
					if ((status.contains("Not")) || (status.contains("not"))) {
						System.out.println(eElement.getElementsByTagName("filename").item(0).getTextContent());

						int lenmessages = eElement.getElementsByTagName("message").getLength();
						xmlsummary.println("<JhoveMessages>" + lenmessages + "</JhoveMessages>");

						for (int temp3 = 0; temp3 < lenmessages; temp3++) {

							String error = eElement.getElementsByTagName("message").item(temp3).getTextContent();
							int writtenmessage = temp3 + 1;

							xmlsummary.println("<Message" + writtenmessage + ">" + error + "</Message" + writtenmessage + ">");
							errormessages.add(error);
						}
					}
					xmlsummary.println("</PdfFile>"); //TODO: should be changed to File, but as well in XSLT
				}
			}

			Collections.sort(errormessages);

			int i;

			// copy ErrorList because later the no. of entries of each
			// element will be counted

			ArrayList<String> originerrors = new ArrayList<String>();
			for (i = 0; i < errormessages.size(); i++) { // There might be a
															// pre-defined
															// function for this
				originerrors.add(errormessages.get(i));
			}
			// get rid of redundant entries
			i = 0;
			while (i < errormessages.size() - 1) {
				if (errormessages.get(i).equals(errormessages.get(i + 1))) {
					errormessages.remove(i);
				} else {
					i++;
				}
			}
			xmlsummary.println("<SampleSummary>");
			xmlsummary.println("<ExaminedPdfFiles>" + nList.getLength() + "</ExaminedPdfFiles>");
			xmlsummary.println("<DifferentJhoveMessages>" + errormessages.size() + "</DifferentJhoveMessages>");
			// how often does each JHOVE error occur?
			int j = 0;
			int temp1;
			for (i = 0; i < errormessages.size(); i++) {
				temp1 = 0;
				for (j = 0; j < originerrors.size(); j++) {
					if (errormessages.get(i).equals(originerrors.get(j))) {
						temp1++;
					}
				}
				xmlsummary.println("<JhoveMessage>");
				xmlsummary.println("<MessageText>" + errormessages.get(i) + "</MessageText>");
				xmlsummary.println("<Occurance>" + temp1 + "</Occurance>");
				xmlsummary.println("</JhoveMessage>");
			}
			xmlsummary.println("</SampleSummary>");
			xmlsummary.println("</JhoveFindingsSummary>");
			xmlsummary.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "error message", JOptionPane.ERROR_MESSAGE);
		}
	}
}
