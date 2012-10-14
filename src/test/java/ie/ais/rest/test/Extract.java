package ie.ais.rest.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.zehon.exception.FileTransferException;
import com.zehon.sftp.SFTPClient;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.OPAQUE;
import oracle.xdb.XMLType;


public class Extract extends BaseDAO {

		public void archiveExtractFiles(List<String> fileNames, boolean isArchiveDirectory) {
			try {
				SFTPClient sftpClient = getSftpClient();
				logger.info("Archiving extract files from : "+getSourceFolder());
				String destinationDirectory = null;
				//extending method for destination directory parameter
				//to make it reusable for both archiving and error storing
				if(isArchiveDirectory)
					destinationDirectory = getDestinationFolder();
				else
					destinationDirectory = getErrorFolder();
				
				if (sftpClient.folderExists(getSourceFolder())) {
					for(String filename : fileNames) {
						if(sftpClient.fileExists(getSourceFolder(), filename)) {
							logger.info("Archiving :"+filename+" to : "+destinationDirectory);
							sftpClient.moveFile(filename, getSourceFolder(), filename+".proc", destinationDirectory);
							}
						else
							logger.info("Filename does not exist in source folder : "+filename);
					}
				} else {
					logger.info("Source folder does not exist - no archiving will take place");
				}
			} catch (FileTransferException e) {
				// TODO Auto-generated catch block
				logger.info("Exception Thrown attempting connection to file system");
				e.printStackTrace();
			}

		}
	}
