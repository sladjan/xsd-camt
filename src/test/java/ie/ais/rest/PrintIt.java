package ie.ais.rest;



import ie.ais.elect.utils.PackageHelper;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class PrintIt {
	// BookRequest bookRequest = new BookRequest();
	// SiteAllocationRequest siteAllocationRequest = new
	// SiteAllocationRequest();
	// SiteVisitUpdateRequest updateRequest = new SiteVisitUpdateRequest();
	// ie.bge.homeservices.bo.jaxb.SiteVISIT siteVISIT = new SiteVISIT();
	// Premise premise = new Premise();
	private static String packageName = "ie.ais.elect.xml.";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrintIt printIt = new PrintIt();
//		printIt.getSetMethods(printIt, "sent", MIM602DailySummaryReconciliationCount.MessagesSent.class);
//		printIt.getSetMethods(printIt, "received", MIM602DailySummaryReconciliationCount.MessagesReceived.class);
//		printIt.getSetMethods(printIt, "msgHeader", MessageHeader.class);

		PackageHelper helper = new PackageHelper();
//		List<String> classes = helper.getClasses();
		List<String> classes = helper.getAllClasses();
		System.out.println("********: "+classes.size());
		int i = 0;
		//print all methods
		for (String string : classes ) {
			try {
				i++;
				if(i>50)
					break;
				Class claz = Class.forName(packageName + string);
				System.out.println("public void "+ string + " get" + string+"() {");
				System.out.println("\t"+string + " " + string.toLowerCase()+" = new "+ string+"();");
				printIt.getSetMethodsTake2(printIt, string.toLowerCase(), claz);
				System.out.println("\treturn "+ string.toLowerCase() + ";\n}");
				System.out.println("\n");
			} catch (Throwable e) {
				System.err.println(e);
			}
		}

		
//		for (String string : classes) {
//		try {
//			Class claz = Class.forName(packageName + string);
//			System.out.println("public static void validate"+"("+ string + " " + string.toLowerCase()+") throws JAXBException {");
//			System.out.println("\t JAXBContext jaxbContext = JAXBContext.newInstance("+string+".class);");
//			System.out.println("\t Marshaller marshaller = jaxbContext.createMarshaller();");
//			System.out.println("\t marshaller.setSchema(schema);");
//			System.out.println("\t marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));");
//			System.out.println("\t marshaller.marshal("+string.toLowerCase()+", new StringWriter());");
//			System.out.println("}\n");
//		} catch (Throwable e) {
//			System.err.println(e);
//		}
//	}
		
		
		//print all fields, does not work properly
//		for (String string : classes) {
//		try {
//			Class claz = Class.forName(packageName + string);
//			System.out.println(string);
//			printIt.getFields(printIt, claz);
//		} catch (Throwable e) {
//			System.err.println(e);
//		}
//	}
		
		
// 		getMethods(printIt, "ref");
// 		getSetMethods(printIt, "updateRequest", SiteVisitUpdateRequest.class);
//	 	getSetMethods(printIt, "cmAqspcGprn",CDxServiceAgreementMaintenanceService.class);
//		getMethods("logger.debug(response.getCDxServiceAgreementMaintenanceService().getCDxServiceAgreementMaintenanceDetails().",
//		CDxServiceAgreementMaintenanceDetails.class);
//		getAssert("assertEquals(","response.getCDxServiceAgreementMaintenanceService().getCDxServiceAgreementMaintenanceDetails().",
//				CDxServiceAgreementMaintenanceDetails.class);
//		getAssert("assertTrue(","calendar.",
//				XMLGregorianCalendar.class);
	}


	private static void getAssert(String pretext,String qualifyName,
			Class class1) {
		Method[] methods = class1.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("get")
					&& !methods[i].getName().startsWith("getClass")) {
				System.out.println(pretext + "\""+methods[i].getName() + "\" ,\"\" ,"+qualifyName+methods[i].getName()+"());");
			}
		}
	}

	private void getFields(PrintIt printIt, Class claz) {
		Field[] fields = claz.getFields();
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i].getName());
			System.out.println(fields[i].getType());
			System.out.println(fields[i].getGenericType());
			Annotation[] annots = fields[i].getDeclaredAnnotations();
			for (int j = 0; j < annots.length; j++) {
				System.out.println(annots[j].toString());
				System.out.println(annots[j].annotationType());
			}
			annots = fields[i].getAnnotations();
			for (int j = 0; j < annots.length; j++) {
				System.out.println(annots[j].toString());
				System.out.println(annots[j].annotationType());
			}
			
		}
	}
	private static void getMethods(String string,
			Class class1) {
		Method[] methods = class1.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("get")
					&& !methods[i].getName().startsWith("getClass")) {
				System.out.println(string + methods[i].getName() + "());");
			}
		}
	}

		
	private static void getSetMethodsTake2(PrintIt printIt, String objName,
			Class claz) {
		Method[] methods = claz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().startsWith("set")) {
				if (objName != null) {
//					System.out.println(method.getDefaultValue());
//					Class<?>[] types = method.getParameterTypes();
//					for (int j = 0; j < types.length; j++) {
//						System.out.println(types[j].getName());
//						System.out.println(types[j].getSimpleName());
//					}
//					System.out.println();
					System.out.println("\t"+objName + "." + method.getName()
							+ "(" + printOnlyFirstParamByType(method.getParameterTypes(), method.getName())
							+ ");");

				} else
					System.out.println("\t"+method.getName() + "();");
			}
		}
	}

	private static void getSetMethods(PrintIt printIt, String objName,
			Class claz) {
		Method[] methods = claz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("set")) {
				if (objName != null) {
					System.out.println(objName + "." + methods[i].getName()
							+ "(" + printParamsOld(methods[i].getParameterTypes())
							+ ");");

				} else
					System.out.println(methods[i].getName() + "();");
			}
		}
	}

	private static String printOnlyFirstParamByType(Class<?>[] parameterTypes, String methodName) {
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < parameterTypes.length; i++) {
			// System.out.println(parameterTypes[i].getSimpleName());
			String simpleName = parameterTypes[i].getSimpleName();
			stb.append("new " + simpleName + "(\""
					+ testSimpleNameValue(simpleName, methodName) + "\")");
		}
		return stb.toString();
	}

	private static String testSimpleNameValue(String simpleName, String methodName) {
		String returnName = null;
		if(simpleName.equalsIgnoreCase("String"))
			returnName = methodName.substring(3);
		else if(simpleName.equalsIgnoreCase("XMLGregorianCalendar"))
			returnName = "";
		else if(simpleName.equalsIgnoreCase("BigDecimal"))
			returnName = "123";
		else if(simpleName.equalsIgnoreCase("Integer"))
			returnName = "123";
		else if(simpleName.equalsIgnoreCase("int"))
			returnName = "123";
		else if(simpleName.equalsIgnoreCase("Boolean"))
			returnName = "false";
		else
			returnName = simpleName;
		return returnName;
	}

	private static String printParamsOld(Class<?>[] parameterTypes) {
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < parameterTypes.length; i++) {
			// System.out.println(parameterTypes[i].getSimpleName());
			stb.append("new " + parameterTypes[i].getSimpleName() + "(\" "
					+ parameterTypes[i].getSimpleName() + "\", ");
		}
		return stb.toString();
	}
	// private static void getMethods(PrintIt printIt, String objName) {
	// Method [] methods = printIt.bookRequest.getClass().getMethods();
	// for (int i = 0; i < methods.length; i++) {
	// if(methods[i].getName().startsWith("get") &&
	// !methods[i].getName().startsWith("getClass"))
	// {
	// if(objName!= null)
	// System.out.println(objName+"."+methods[i].getName()+"();");
	// else
	// System.out.println(methods[i].getName()+"();");
	// }
	// }
	// }

}
