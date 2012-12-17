package ie.ais.elect.xml.validation;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class UnMarshallFile {

	private static String SCHEMA_VALDIATION_LOCATION = "schema/ieXML.xsd";
	private static final Logger logger = Logger.getLogger(UnMarshallFile.class.getName());
	
	private static Schema gettingSchema(String schemaPath) throws SAXException
	{
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//        File schemaFile  = new File(SCHEMA_VALDIATION_LOCATION);
        File schemaFile  = new File(schemaPath);
        return schemaFactory.newSchema(schemaFile);
	}

	
//	public static IeXMLDocument xmlToObject(File inputFile, String schemaPath) throws JAXBException, SAXException {
//		 IeXMLDocument obj = new IeXMLDocument();
//		 logger.debug("unmarshalling Message "+ IeXMLDocument.class.getSimpleName());
//		 JAXBContext jaxbContext = JAXBContext.newInstance(IeXMLDocument.class);
//         Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//	     unmarshaller.setSchema(gettingSchema(schemaPath));
//	     obj = (IeXMLDocument) unmarshaller.unmarshal(inputFile);
//		 return obj;
//	}

//    protected Unmarshaller createUnmarshaller() throws JAXBException {
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//        unmarshaller.setSchema(schema);
//        return unmarshaller;
//    }

}
