package it.sayservice.services.feratel.script;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

public class ScriptBody {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public Document getXMLDocument(String path) throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream xmlStream = loader.getResourceAsStream(path);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document document = docBuilder.parse(xmlStream);
		return document;
	}

}
