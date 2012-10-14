package ie.ais.rest.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.zehon.sftp.SFTPClient;

public abstract class BaseDAO {

		protected static Logger logger = Logger.getLogger(BaseDAO.class
				.getName());

		private DataSource dataSource;
		private Connection connection;
		private SFTPClient sftpClient;
		
		private String servicesJNDIName;
		private String sftpHost;
		private String sftpUser;
		private String sftpPassword;
		
		private String sourceFolder;
		private String destinationFolder;
		private String errorFolder;
		

		public DataSource getDataSource() {
			Context ic = null;
			Hashtable<String, String> ht = new Hashtable<String, String>();
			ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			try {
				ic = new InitialContext(ht);
				dataSource = (DataSource) ic.lookup(servicesJNDIName);
				logger.info("Data Source lookup successful");
			} catch (NamingException e) {
				logger.info("Data Source not found");
				e.printStackTrace();
			} catch (Exception e) {
				logger.info("Exception thrown registering datasource");
				e.printStackTrace();
			}
			return dataSource;
		}

		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		public Connection getConnection() throws Exception {
			logger.info("Returning JDBC Connection");
			if (connection == null || connection.isClosed())
				connection = getDataSource().getConnection();

			return connection;
		}

		public void closeConnection() {
			logger.info("Closing JDBC Connection");
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.info("Exception thrown closing JDBC Connection");
					e.printStackTrace();
				}
		}
		
		public SFTPClient getSftpClient() {
			logger.info("Getting handle on sftpClient.");
			if (sftpClient == null) {
				logger.info("Attempting connection to sftphost :"+sftpHost);
				logger.info("User to sftphost :"+sftpUser);
				sftpClient = new SFTPClient(sftpHost, sftpUser, sftpPassword);
			}
			return sftpClient;
		}
		
		public String getservicesJNDIName() {
			return servicesJNDIName;
		}

		public void setservicesJNDIName(String servicesJNDIName) {
			this.servicesJNDIName = servicesJNDIName;
		}

		public String getSftpHost() {
			return sftpHost;
		}

		public void setSftpHost(String sftpHost) {
			this.sftpHost = sftpHost;
		}

		public String getSftpUser() {
			return sftpUser;
		}

		public void setSftpUser(String sftpUser) {
			this.sftpUser = sftpUser;
		}

		public String getSftpPassword() {
			return sftpPassword;
		}

		public void setSftpPassword(String sftpPassword) {
			this.sftpPassword = sftpPassword;
		}

		public String getSourceFolder() {
			return sourceFolder;
		}

		public void setSourceFolder(String sourceFolder) {
			this.sourceFolder = sourceFolder;
		}

		public String getDestinationFolder() {
			return destinationFolder;
		}

		public String getErrorFolder() {
			return errorFolder;
		}

		public void setDestinationFolder(String destinationFolder) {
			this.destinationFolder = destinationFolder;
		}

		public void setSftpClient(SFTPClient sftpClient) {
			this.sftpClient = sftpClient;
		}

		public void setErrorFolder(String errorFolder) {
			this.errorFolder = errorFolder;
		}

	}
