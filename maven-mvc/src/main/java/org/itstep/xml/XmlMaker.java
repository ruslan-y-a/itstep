package org.itstep.xml;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlMaker {
	private Element rootElement;
	private Document doc;
	private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder builder;
	
	public XmlMaker(String url, String element) throws ParserConfigurationException {  //"https://javadevblog.com/language", "Languages"
		 builder = factory.newDocumentBuilder();
		 doc = builder.newDocument();
		 rootElement=doc.createElementNS(url, element);
		 doc.appendChild(rootElement);
	 }
	
	public Element getRootElement() {return rootElement;}

	public void saveXml(String sFile) throws TransformerException {  //"/Users/prologistic/languages.xml"
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(); 
		DOMSource source = new DOMSource(doc);
		StreamResult file = new StreamResult(new File(sFile));
        transformer.transform(source, file);        
	}
	
	 //node.setAttribute("id", id);
	public static Node addNewChild(Document doc, Element element, String name, String value) {		
        Element node = doc.createElement(name);
        element.appendChild(node);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
	public void addNewChild(Element element, String name, String value) {		
        Element node = doc.createElement(name);
        element.appendChild(node);
        node.appendChild(doc.createTextNode(value));        
    }
	public void addNewChildren(Element element, Map<String,String> namevalue) {		        
		namevalue.forEach((x,y) -> {
			Element node = doc.createElement(x);
	        element.appendChild(node);
	        node.appendChild(doc.createTextNode(y));
		});		
    }
	public void addNewChildren(Map<String,String> namevalue) {		        
		namevalue.forEach((x,y) -> {
			Element node = doc.createElement(x);
			rootElement.appendChild(node);
	        node.appendChild(doc.createTextNode(y));
		});		
    }
	public void addNewChildren(String subName, Map<String,String> namevalue) {		        
		Element nodeMain = doc.createElement(subName);
		rootElement.appendChild(nodeMain);
		namevalue.forEach((x,y) -> {
			Element node = doc.createElement(x);	
			nodeMain.appendChild(node);
	        node.appendChild(doc.createTextNode(y));
		});		
    }
	public static void addNodeValue(Document doc, Element element, String value) {        
        element.appendChild(doc.createTextNode(value));}
	 
	
}
