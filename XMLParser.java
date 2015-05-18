package versioncontrol;

import java.io.File;
import java.io.IOException;
//import java.util.LinkedList;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLParser{
	/*private static Document dom;
	
	public static Document getDocument(){
		return dom;
	}
	
	public XMLParser() {
	}
	
	public static void parseFile(File file, Queue<VTU> myVTUs){
		parseXMLFile(file);
		parseDocument(myVTUs);
	}
	
	
	private static void parseXMLFile(File file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(file);
			dom.getDocumentElement().normalize();
		} catch(ParserConfigurationException XMLpce){
			XMLpce.printStackTrace();
		} catch(SAXException XMLse){
			XMLse.printStackTrace();
		}catch(IOException XMLioe){
			XMLioe.printStackTrace();
		}
	}
	
	private static void parseDocument(Queue<VTU> myVTUs){
		Element rootXMLElmnt = dom.getDocumentElement();
		String action = rootXMLElmnt.getFirstChild().getNodeValue();
		String tableName = rootXMLElmnt.getFirstChild().getNextSibling().getNodeName();
		NodeList XMLnl = rootXMLElmnt.getElementsByTagName(tableName);
		
		if(XMLnl != null && XMLnl.getLength() >0){
			for(int i= 1; i<XMLnl.getLength(); i++){
				Element genericTable = (Element)XMLnl.item(i);
				String coloumnName = genericTable.getFirstChild().getNodeName();
				VTU value = new VTU(action,getValue(genericTable,tableName),tableName,coloumnName);
				myVTUs.add((value));
			}
		}
	}
	

	private static String getValue(Element genericTable,String tagName){
		String valueToUpdate = null;
		NodeList nl = genericTable.getElementsByTagName(tagName);
		if(nl != null && nl.getLength()>0){
			Element el = (Element)nl.item(0);
			valueToUpdate = el.getFirstChild().getNodeValue();
		}
		return valueToUpdate;
	}*/

}
