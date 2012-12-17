package ie.ais.elect.xml.validation;

import java.io.File;

import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * The Class XMLSchemaHelper.
 * @author Sladjan Kuzmanovic 10 Jun 2010 
 */
public class XMLSchemaHelper {
	
	
	private static final Logger logger = Logger
			.getLogger(XMLSchemaHelper.class);
	
	/**
	 * Sets the schema and marshaller return validator.
	 *
	 * @param schemaPath the schema path
	 * @param marshaller the marshaller
	 * @return the validator
	 * @throws SAXException the sAX exception
	 * @throws JAXBException the jAXB exception
	 */
	public static Validator setSchemaAndMarshallerReturnValidator(String schemaPath , Marshaller marshaller) throws SAXException, JAXBException {

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = null;
		URL schemaUrl = ClassLoader.getSystemClassLoader().getResource(schemaPath);
		try {
			schema = schemaFactory.newSchema(schemaUrl);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		// if can not load schema from resources then load from hard coded path	
		if (schema == null) {
			try {
				schema = schemaFactory.newSchema(new File(schemaPath));
				logger.debug("schema instantiated successfully");
			} catch (SAXException e) {
				logger.error(e.getMessage());
				throw new SAXException("Can't Find schema file");
			}
		}
		logger.debug("setSchema() method to identify the Schema object::");
		// marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new
		// NamespacePrefixmapperImpl());
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setSchema(schema);
		// marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
		// "file:///Z:"+XMLSchemaHelper.HOME_SERVICES_XSD);
		logger.debug("Set Schema finished ...");
		return schema.newValidator();
	}

	public static Schema getSchemaFromClassPath(String schemaPath) throws SAXException {

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = null;
		ClassLoader classLoader =  ClassLoader.getSystemClassLoader();
		logger.debug("Getting resource from classloader:"+ classLoader);
		URL schemaUrl = classLoader.getSystemResource(schemaPath);
		try {
			logger.debug("schemaUrl"+ schemaUrl);
			logger.debug("schemaUrl"+ schemaUrl.getPath());
			schema = schemaFactory.newSchema(schemaUrl);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (schema == null) {
			try {
				schema = schemaFactory.newSchema(new File(schemaPath));
				logger.debug("schema instantiated successfully");
			} catch (SAXException e) {
				logger.error(e.getMessage());
				throw new SAXException("Can't Find schema file");
			}
		}
		logger.debug("Return Schema");
		return schema;
	}

	public static Schema getSchema(String schemaPath) throws SAXException {

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = null;
		ClassLoader classLoader =  ClassLoader.getSystemClassLoader();
		logger.debug("Getting resource from classloader:"+ classLoader);
		URL schemaUrl = classLoader.getResource(schemaPath);
		try {
			logger.debug("schemaUrl"+ schemaUrl);
			logger.debug("schemaUrl"+ schemaUrl.getPath());
			schema = schemaFactory.newSchema(schemaUrl);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return schema;
	}

	
}
