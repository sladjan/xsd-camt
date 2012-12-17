package ie.ais.elect.xml.validation;

import ie.ais.xml.document.utils.JaxbWrapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * 
 * @author Sladjan Kuzmanovic 13 November 2010
 */

public class SchemaValidatorHelper <T>{

	private static final Logger logger = Logger.getLogger(SchemaValidatorHelper.class);
	private static final String pathToFile = "output/";
//	private static final String PACKAGE_NAME = "${package}.marketmsg.elect.xml";
//	public static final String QNAME_BGTHEADERSEG = "";
//	private static final String BGTHEADERSEG_WS ="BGTHeaderSeg";
//	private Class<?> clazz;

	public static final String TNS_NAME = "ns2";
	public static final String SCHEMA_VALDIATION_LOCATION_JUNIT_TEST = "pain.001.001.04.xsd";
	private String SCHEMA_VALDIATION_LOCATION = "wsdl/pain.001.001.04.xsd";
	

	public void validationGeneric(T clazz)
			throws JAXBException, SAXException, IOException {
		JaxbWrapper<T> jaxbwrap = new JaxbWrapper<T>(new File(SCHEMA_VALDIATION_LOCATION) , clazz.getClass());
//		URL resource = this.getClass().getResource(SCHEMA_VALDIATION_LOCATION);
//		JaxbWrapper<T> jaxbwrap = new JaxbWrapper<T>( resource , clazz.getClass());
		System.out.println("Validation " + clazz);
		jaxbwrap.validate(clazz);
		logger.debug("Schema Validation finished successfully");
	}
	
	public void writeToXMLGeneric(T clazz, Writer outputStream) throws JAXBException, SAXException,
			IOException {
		JaxbWrapper<T> jaxbwrap = new JaxbWrapper<T>(new File(SCHEMA_VALDIATION_LOCATION) , clazz.getClass());
//		URL resource = this.getClass().getResource(SCHEMA_VALDIATION_LOCATION);
//		JaxbWrapper<T> jaxbwrap = new JaxbWrapper<T>( resource , clazz.getClass());
		jaxbwrap.objectToXml(clazz, outputStream);
		logger.debug("Schema Validation finished successfully");
	}

	public void validationOfXMLGeneric(T clazz, InputStream inputStream)
	throws JAXBException, SAXException, IOException {
		JaxbWrapper<T> jaxbwrap = new JaxbWrapper<T>(new File(SCHEMA_VALDIATION_LOCATION) , clazz.getClass());
//		URL resource = this.getClass().getResource(SCHEMA_VALDIATION_LOCATION);
//		JaxbWrapper<T> jaxbwrap = new JaxbWrapper<T>( resource , clazz.getClass());
		clazz = jaxbwrap.xmlToObject(inputStream);
		jaxbwrap.validate(clazz);
		logger.debug("Schema Validation finished successfully");
	}

	public void validateAndWrite(T clazz, String fileName)
			throws JAXBException, SAXException, IOException {
		validationGeneric(clazz);
		Writer outputStream = getOutputStream(fileName);
		writeToXMLGeneric(clazz, outputStream);
	}

	public void writeValidMessageToFile(T clazz,
			String fileName) {
		try {
			validationGeneric(clazz);
			Writer outputStream = getOutputStream(fileName);
			writeToXMLGeneric(clazz, outputStream);
//			num++;
		} catch (JAXBException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
//			assertTrue("Validaton failed!!!", false);
		} catch (SAXException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	private Writer getOutputStream(String className) throws IOException {
		File file = new File(pathToFile + className+".xml");
		Writer outputStream = new FileWriter(file);
		return outputStream;
	}

}

