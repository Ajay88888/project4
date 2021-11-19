package in.com.raysproject.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import in.com.raysproject.exception.ApplicationException;


/**
 * JDBC DataSource is a Data Connection Pool
 * @author Ajay
 *
 */

public final class JDBCDataSource {
	private static JDBCDataSource datasource;
	private ComboPooledDataSource cpds = null;
	private static ResourceBundle rb = ResourceBundle.getBundle("system");
	
	private JDBCDataSource() {}

	public static JDBCDataSource getInstance() throws Exception {
		if (datasource == null) {
			datasource = new JDBCDataSource();
			datasource.cpds = new ComboPooledDataSource();
			
//			try {
				datasource.cpds.setDriverClass(PropertyReader.getValue("driver"));
			/*	System.out.println("url-->"+PropertyReader.getValue("url"));
				System.out.println("url-->"+PropertyReader.getValue("driver"));*/
			/* } catch (Exception e) {
	                e.printStackTrace();
	            }*/
				datasource.cpds.setJdbcUrl(PropertyReader.getValue("url"));
				datasource.cpds.setUser(PropertyReader.getValue("user"));
				datasource.cpds.setPassword(PropertyReader.getValue("pwd"));
				datasource.cpds.setInitialPoolSize(DataUtility.getInt(PropertyReader.getValue("initialPoolSize")));
				datasource.cpds.setAcquireIncrement(DataUtility.getInt(PropertyReader.getValue("acquireIncrement")));
				datasource.cpds.setMaxPoolSize(DataUtility.getInt(PropertyReader.getValue("maxPoolSize")));
				datasource.cpds.setMaxIdleTime(DataUtility.getInt(PropertyReader.getValue("timeout")));
				datasource.cpds.setMinPoolSize(DataUtility.getInt(PropertyReader.getValue("minPoolSize")));
	}
		return datasource;
	}
	public static Connection getConnection() throws Exception {
		return getInstance().cpds.getConnection();
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void trnRollback(Connection connection) throws ApplicationException {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
				throw new ApplicationException(ex.toString());
			}
		}
	}
}
