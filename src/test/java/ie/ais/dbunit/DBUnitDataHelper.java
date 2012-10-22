/*
 * 
 */
package ie.ais.dbunit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;

import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.operation.DatabaseOperation;

public class DBUnitDataHelper {

	private static final Logger logger = Logger.getLogger(DBUnitDataHelper.class);
	private static final String URL = "jdbc:oracle:thin:@netbook1:1521:XE";
	private static final String user= "generic";
	private static final String pass = "generic";

	public static void main(String[] args){
		DBUnitDataHelper.inserNewData();
	}
	
	public static void getAllData() {
		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, user, pass);

			IDatabaseConnection connection;
			connection = new DatabaseConnection(conn);

			QueryDataSet partialDataSet = new QueryDataSet(connection);
			// Specify the SQL to run to retrieve the data
			//partialDataSet.addTable("SERVICE_CONFIG", " SELECT * FROM SERVICE_CONFIG ");
			// partialDataSet.addTable("address", " SELECT * FROM address WHERE addressid=1 ");

			String [] parameters = {"USER_DETAIL",
					"USER_ROLE",
					"SERVICE_CONFIG",
					"ACCOUNT",
					"ACCOUNT_SEGMENT",
					"AUDIT_DETAIL",
					"SESSION_DETAIL",
					"APP_PROPERTIES"};
			for (String tableName : parameters ) {
                partialDataSet.addTable(tableName);
            }


			// Specify the location of the flat file(XML)
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream("dataset-all.xml"));
//			FlatXmlWriter datasetWriter = new FlatXmlWriter( new FileOutputStream("dataset-all.xml"));
			// Export the data
//			datasetWriter.write(partialDataSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseUnitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getData() {
		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, user, pass);

			IDatabaseConnection connection;
			connection = new DatabaseConnection(conn);

			QueryDataSet partialDataSet = new QueryDataSet(connection);
			// Specify the SQL to run to retrieve the data
			partialDataSet.addTable("SERVICE_CONFIG", " SELECT * FROM SERVICE_CONFIG ");
			// partialDataSet.addTable("address", " SELECT * FROM address WHERE addressid=1 ");

			// Specify the location of the flat file(XML)
			FlatXmlWriter datasetWriter = new FlatXmlWriter( new FileOutputStream("dataset.xml"));

			// Export the data
			datasetWriter.write(partialDataSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseUnitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateDataUnlocking() {
		try {
			// Connect to the database
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL);
			IDatabaseConnection connection = new DatabaseConnection(conn);
			DatabaseOperation.UPDATE.execute(connection, new FlatXmlDataSet(
					new FileInputStream("dataset.xml")));
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			logger.error(exc.getMessage());
		}
	}

	public static void updateDataNewUser() {
		try {
			// Connect to the database
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, user, pass);
			IDatabaseConnection connection = new DatabaseConnection(conn);
			DatabaseOperation.UPDATE.execute(connection, new FlatXmlDataSet(
					new FileInputStream("dataset-user.xml")));
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			logger.error(exc.getMessage());
		}
	}

	public static void inserNewData() {
		try {
			// Connect to the database
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, user, pass);
			IDatabaseConnection connection = new DatabaseConnection(conn);
			DatabaseOperation.INSERT.execute(connection, new FlatXmlDataSet(
					new FileInputStream("dataset.xml")));
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			logger.error(exc.getMessage());
		}
	}
}
