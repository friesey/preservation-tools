package externalToolAnalysis;

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

			doc.getDocumentElement().normalize();

			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("item");
			// If I choose "repInfo", "filename" is not part of the node, but I
			// want that information

			System.out.println("Examined PF files: " + nList.getLength()); // this is the right length

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				// System.out.println("\nCurrent Element :" +
				// nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("File Name : " + eElement.getElementsByTagName("filename").item(0).getTextContent());
					System.out.println("Format : " + eElement.getElementsByTagName("format").item(0).getTextContent());
					System.out.println("Findings : " + eElement.getElementsByTagName("status").item(0).getTextContent());

					String status = eElement.getElementsByTagName("status").item(0).getTextContent();
					if ((status.contains("Not")) || (status.contains("not"))) {
						System.out.println("Message : " + eElement.getElementsByTagName("message").item(0).getTextContent());
					}
					
					System.out.println();
					System.out.println();

				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
