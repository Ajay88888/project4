package in.com.raysproject.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import in.com.raysproject.bean.DropdownListBean;
import in.com.raysproject.exception.DatabaseException;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.JDBCDataSource;


/**
 * Base model 
 * @author Ajay
 *
 */

public abstract class BaseModel implements Serializable, DropdownListBean, Comparable<BaseModel> {
	
	private static Logger log = Logger.getLogger(BaseModel.class);

	protected Long id;
	protected String createdBy;
	protected String modifiedBy;
	protected Timestamp createdDatetime;
	protected Timestamp modifiedDatetime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	public int compareTo(BaseModel next) {
		return (int) (id - next.getId());
	}

	public long nextpk() throws Exception {
		log.debug("Model nextPK Started");
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM " + getTableName());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception:Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPk End");
		return pk + 1;
	}

	public abstract String getTableName();

	public void updateCreatedInfo() throws Exception {
		log.debug("Model update started" + createdBy);
		Connection conn = null;

		String sql = "UPDATE " + getTableName() + " SET CREATED_BY=?, CREATED_DATETIME=? WHERE ID=?";
		log.debug(sql);
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, createdBy);
			ps.setTimestamp(2, DataUtility.getCurrentTimestamp());
			ps.setLong(3, id);
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
		} catch (SQLException e) {
			log.error("Database Exception..", e);
			JDBCDataSource.trnRollback(conn);
			throw new in.com.raysproject.exception.ApplicationException(e.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update end");
	}

	public void updateModifiedInfo() throws Exception {
		log.debug("Model Update Started");
		Connection conn = null;
		String sql = "UPDATE " + getTableName() + " SET MODIFIED_BY=?, MODIFIED_DATETIME=? WHERE ID=?";

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, modifiedBy);
			ps.setTimestamp(2, DataUtility.getCurrentTimestamp());
			ps.setLong(3, id);
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
		} catch (SQLException e) {
			log.error("Database Exception..", e);
			JDBCDataSource.trnRollback(conn);

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	protected <T extends BaseModel> T populateModel(T model, ResultSet rs) throws SQLException {
		model.setId(rs.getLong("ID"));
		model.setCreatedBy(rs.getString("CREATED_BY"));
		model.setModifiedBy(rs.getString("MODIFIED_BY"));
		model.setCreatedDatetime(rs.getTimestamp("CREATED_DATETIME"));
		model.setModifiedDatetime(rs.getTimestamp("MODIFIED_DATETIME"));
		return model;
	}
}