package versioncontrol;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XMLBuilder{
	/*static File xmlMessage = null;
	static ArrayList<VTU> VTUs;

	private static Document addNodes(Document doc, ArrayList<VTU> nodes) {
		Node root;
		if (doc.hasChildNodes()!=true){
			doc.appendChild(doc.createElement("belief.msg"));
			root = doc.getDocumentElement();
			Node action = root.appendChild(doc.createElement("action"));
			action.appendChild(doc.createTextNode(nodes.get(0).getAction()));
		}
		for(VTU node : nodes){
			root = doc.getDocumentElement();
			Node tableName = doc.createElement(node.getTableName());
			Node coloumnName = tableName.appendChild(doc.createElement(node.getColoumnName()));
			coloumnName.appendChild(doc.createTextNode(node.getValue()));
			root.appendChild(tableName);
		}
		
		return doc;
	}

	public static File buildMessage(String path){
		try {
			DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document dom = domBuilder.newDocument();
			addNodes(dom,VTUs);
			Transformer xmlTransformer = TransformerFactory.newInstance().newTransformer();
			xmlTransformer.transform(new DOMSource(dom), new StreamResult(new File(path)));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e){
			e.printStackTrace();
		} catch (TransformerException e){
			e.printStackTrace();
		}
		return null;
		
	}*/

}
