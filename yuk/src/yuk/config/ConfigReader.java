package yuk.config;

// Java stuff
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
// Xml Parser
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// Logs based on agent message file
public class ConfigReader{
    private String		filePath;
    private IConfigReader	iConfig;
    
	// Constructor
	public ConfigReader(String file, IConfigReader c){
		filePath = file;
		iConfig = c;
	}
	
	// Reads a config file and sends simple, nested results to caller
	public String parse() throws Exception{
		BufferedReader fi = null;	
		InputStream inputStream ;
		try {
			inputStream = new FileInputStream(filePath);
		} catch (Exception e) {
			inputStream =  ConfigReader.class.getClassLoader().getResourceAsStream(filePath);
		}
		if (isUnicode())
			fi = new BufferedReader(new InputStreamReader(inputStream,"Unicode"));
		else
			fi = new BufferedReader(new InputStreamReader(inputStream));
		ConfigParser cp = new ConfigParser(iConfig);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(new InputSource(fi), cp);
		if (fi != null) 
			fi.close();
		if(inputStream != null)
			inputStream.close();
		return "";
	}

  	protected boolean isUnicode() {
		boolean isUnicode = true;
	  	BufferedReader fi = null;
		InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(filePath);
		ConfigParser cp = new ConfigParser(iConfig);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// factory.setValidating(false);
		try {
			SAXParser parser = factory.newSAXParser();
			fi = new BufferedReader(new InputStreamReader(inputStream, "Unicode"));
			parser.parse(new InputSource(fi), cp);
			
		} catch (Exception e) {
			isUnicode = false;
		} 
		try {
			if (fi != null)
			fi.close();
		} catch (IOException e) {
			//do not thing
		}
		return isUnicode;
	 }
	 	
	// Inner class to get XML pieces
	class ConfigParser extends DefaultHandler {
		private IConfigReader iConfig;

		// Constructor
		public ConfigParser(IConfigReader c) {
			iConfig = c;
		}

		// Routines called from parser - serially
		public void startDocument() throws SAXException {
			
		}

		public void endDocument() throws SAXException {

		}

		public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
			Hashtable attrs = new Hashtable();
			for (int i = 0; i < attributes.getLength(); i++) {
				attrs.put(attributes.getQName(i).toUpperCase(),	attributes.getValue(i));
			}
			iConfig.startParms(qName.toUpperCase(), attrs);
		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
			iConfig.endParms(qName.toUpperCase());
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			
		}
	}

	
}
