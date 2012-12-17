package ie.ais.elect.xml.validation;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.bind.v2.WellKnownNamespace;

/**
 * Simple JAXB Wrapper.
 *
 * @param <T> the generic type
 * @author Vineet Manohar
 */
public class JaxbWrapper<T> {
    
	/** The Constant NAMESPACE_PREFIX_MAPPER. */
	private static final String NAMESPACE_PREFIX_MAPPER = "com.sun.xml.bind.namespacePrefixMapper";

//	/** The DBCHANGELO g_ namespace. */
//	private static String DBCHANGELOG_NAMESPACE = "http://www.liquibase.org/xml/ns/dbchangelog";
//
//	/** The DBCHANGELO g_ schema. */
//	private static String DBCHANGELOG_SCHEMA = DBCHANGELOG_NAMESPACE + "/dbchangelog-2.0.xsd";

	/** The jaxb context. */
    private JAXBContext jaxbContext = null;

    /** The schema. */
    private Schema schema;

    /** The Constant logger. */
	private static final Logger logger = Logger.getLogger(JaxbWrapper.class);
	
    /**
     * Instantiates a new jaxb wrapper.
     *
     * @param schemaUrl the schema url
     * @param clazz the clazz
     * @throws JAXBException the jAXB exception
     * @throws SAXException the sAX exception
     */
    public JaxbWrapper(URL schemaUrl, Class<?>... clazz) throws JAXBException, SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        this.schema = schemaFactory.newSchema(schemaUrl);
        this.jaxbContext = JAXBContext.newInstance(clazz);
    }

    /**
     * Instantiates a new jaxb wrapper.
     *
     * @param schemaFile the schema file
     * @param clazz the clazz
     * @throws JAXBException the jAXB exception
     * @throws SAXException the sAX exception
     */
    public JaxbWrapper(File schemaFile, Class<?>... clazz) throws JAXBException, SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        this.schema = schemaFactory.newSchema(schemaFile);
        this.jaxbContext = JAXBContext.newInstance(clazz);
    }

    /**
     * Converts the given object to XML.
     *
     * @param t the t
     * @return the string
     * @throws JAXBException the jAXB exception
     * @author Vineet Manohar
     */
    public String objectToXml(T t) throws JAXBException {
        StringWriter writer = new StringWriter();
        objectToXml(t, writer);
        return writer.toString();
    }

    /**
     * Converts the given object to XML.
     *
     * @param t the object to convert to xml
     * @param writer the output will be written to this writer
     * @throws JAXBException the jAXB exception
     * @author Vineet Manohar
     */
    public void objectToXml(T t, Writer writer) throws JAXBException {
        createMarshaller().marshal(t, writer);
    }

    /**
     * Converts the given object to XML.
     *
     * @param t the object to convert to xml
     * @param outputStream the output will be written to this output stream
     * @throws JAXBException the jAXB exception
     * @author Vineet Manohar
     */
    public void objectToXml(T t, OutputStream outputStream) throws JAXBException {
        createMarshaller().marshal(t, outputStream);
    }

    /**
     * Converts the given object to XML.
     *
     * @param t the object to convert to xml
     * @param file the output will be written to this file
     * @throws JAXBException the jAXB exception
     * @author Vineet Manohar
     */
    public void objectToXml(T t, File file) throws JAXBException {
        createMarshaller().marshal(t, file);
    }

    /**
     * validates the object against the schema, throws an Exception if
     * validation fails.
     *
     * @param t the object to validate
     * @throws JAXBException when schema validation fails
     * @author Vineet Manohar
     */
    public void validate(T t) throws JAXBException {
        createMarshaller().marshal(t, new StringWriter());
    }

    /**
     * Validate.
     *
     * @param t the t
     * @param nameSpace the name space
     * @param NAMESPACE 
     * @param SCHEMA 
     * @throws JAXBException the jAXB exception
     */
    public void validate(T t, String nameSpace, String NAMESPACE, String SCHEMA) throws JAXBException {
        createNSMarshaller(nameSpace, NAMESPACE, SCHEMA).marshal(t, new StringWriter());
    }

    
    /**
     * converts xml form to a java object.
     *
     * @param is InputStream which points to a valid XML content
     * @return the Java object representing the xml
     * @throws JAXBException if jaxb unmarshalling fails. Common reason is schema
     * incompatibility
     * @author Vineet Manohar
     */
    @SuppressWarnings("unchecked")
    public T xmlToObject(InputStream is) throws JAXBException {
        return (T) createUnmarshaller().unmarshal(is);
    }

    /**
     * converts xml form to a java object.
     *
     * @param xml a valid XML string
     * @return the Java object representing the xml
     * @throws JAXBException if jaxb unmarshalling fails. Common reason is schema
     * incompatibility
     * @author Vineet Manohar
     */
    @SuppressWarnings("unchecked")
    public T xmlToObject(String xml) throws JAXBException {
        StringReader stringReader = new StringReader(xml);
        return (T) createUnmarshaller().unmarshal(stringReader);
    }

    /**
     * Creates a marshaller. Clients generally don't need to call this method,
     * hence the method is protected. If you want use features not already
     * exposed, you can subclass and call this method.
     *
     * @return the marshaller
     * @throws JAXBException the jAXB exception
     * @throws PropertyException the property exception
     * @author Vineet Manohar
     */
    protected Marshaller createMarshaller() throws JAXBException, PropertyException {
    	logger.debug("createMarshaller, SCHEMA:: " + schema.toString());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setSchema(schema);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
        return marshaller;
    }

    protected Marshaller createNSMarshaller(String nameSpace, String NAMESPACE, String SCHEMA) throws JAXBException, PropertyException {
    	logger.debug("createMarshaller, SCHEMA:: " + schema.toString());
        JAXBContext jaxbContext2 = JAXBContext.newInstance(nameSpace); 
		Marshaller marshaller = jaxbContext2 .createMarshaller();
        marshaller.setSchema(schema);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, NAMESPACE + " " + SCHEMA);
		marshaller.setProperty(NAMESPACE_PREFIX_MAPPER, new NamespacePrefixMapper() {
			
			@Override
			public String[] getPreDeclaredNamespaceUris() {
				return new String[] { WellKnownNamespace.XML_SCHEMA_INSTANCE };
			}

			@Override
			public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
				if (namespaceUri.equals(WellKnownNamespace.XML_SCHEMA_INSTANCE))
					return "xsi";
				if (namespaceUri.equals(WellKnownNamespace.XML_SCHEMA))
					return "xs";
				if (namespaceUri.equals(WellKnownNamespace.XML_MIME_URI))
					return "xmime";
				return suggestion;

			}
		});
		
        return marshaller;
    }

    /**
     * Creates a marshaller. Clients generally don't need to call this method,
     * hence the method is protected. If you want use features not already
     * exposed, you can subclass and call this method.
     *
     * @return the unmarshaller
     * @throws JAXBException the jAXB exception
     * @author Vineet Manohar
     */
    protected Unmarshaller createUnmarshaller() throws JAXBException {
    	logger.debug("createUnmarshaller, SCHEMA:: " + schema.toString());
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
        return unmarshaller;
    }
}
