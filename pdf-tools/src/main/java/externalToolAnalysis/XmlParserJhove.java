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

		String xmlfile = utilities.FileBrowserDialog.chooseFile();

		parseXmlFile(xmlfile);
	}

	public static void parseXmlFile(String xmlfile) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlfile);

			PrintWriter xmlsummary = new PrintWriter(new FileWriter((RunJhoveApp.folder + "//" + "JhoveExaminationSummary" + ".xml")));

			String xmlVersion = "xml version='1.0'";
			String xmlEncoding = "encoding='ISO-8859-1'";
			String xmlxslStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"JhoveCustomized.xsl\"?>";

			xmlsummary.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			xmlsummary.println(xmlxslStyleSheet);
			xmlsummary.println("<JhoveFindingsSummary>");

			output.XslStyleSheetJhoveCustomized.JhoveCustomizedXsl();

			ArrayList<String> errormessages = new ArrayList<String>();

			doc.getDocumentElement().normalize();

			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("item");

			System.out.println("Examined PDF files: " + nList.getLength()); // this
																			// is
																			// the
																			// right
																			// length

			for (int temp = 0; temp < nList.getLength(); temp++) {
				int lenmessages = 0;

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					xmlsummary.println("<PdfFile>");

					xmlsummary.println("<FileName>" + eElement.getElementsByTagName("filename").item(0).getTextContent() + "</FileName>");
					xmlsummary.println("<Format>" + eElement.getElementsByTagName("format").item(0).getTextContent() + "</Format>");
					xmlsummary.println("<Status>" + eElement.getElementsByTagName("status").item(0).getTextContent() + "</Status>");

					String status = eElement.getElementsByTagName("status").item(0).getTextContent();
					if ((status.contains("Not")) || (status.contains("not"))) {
						System.out.println(eElement.getElementsByTagName("filename").item(0).getTextContent());

						lenmessages = eElement.getElementsByTagName("message").getLength();
						System.out.println(lenmessages);
						xmlsummary.println("<JhoveMessages>" + lenmessages + "</JhoveMessages>");

						for (int temp3 = 0; temp3 < lenmessages; temp3++) {

							String error = eElement.getElementsByTagName("message").item(temp3).getTextContent();
							xmlsummary.println("<Message>" + error + "</Message>");

							errormessages.add(error);
						}

					}
					xmlsummary.println("</PdfFile>");
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

			System.out.println("Sample consists of " + errormessages.size() + " different JHOVE error messages");

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
				System.out.println((i + 1) + ": " + temp1 + " x " + errormessages.get(i));
			}

			xmlsummary.println("</JhoveFindingsSummary>");
			xmlsummary.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
