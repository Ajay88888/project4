package in.com.raysproject.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import in.com.raysproject.bean.RoleBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DatabaseException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.util.JDBCDataSource;


/**
 * JDBC Implement of role model
 * @author Ajay
 *
 */

public class RoleModel {
	private static Logger log = Logger.getLogger(RoleModel.class);

	public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException, SQLException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = (int) nextPK();
			// System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getDescription());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDatetime());
			ps.setTimestamp(7, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			System.out.println("row affected -->");
			ps.close();
			// conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception:Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model and End");
		return pk;
	}

	public void update(RoleBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("model update started");
		Connection conn = null;
		RoleBean duplicaterole = findByName(bean.getName());
		if (duplicaterole != null && duplicaterole.getId() != bean.getId()) {
			throw new DuplicateRecordException("Role already exsits");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_role SET name=?, description=?, created_by=?, modified_by=?, created_Datetime=?, modified_Datetime=? WHERE Id=?");
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getDescription());
			ps.setString(3, bean.getCreatedBy());
			ps.setString(4, bean.getModifiedBy());
			ps.setTimestamp(5, bean.getCreatedDatetime());
			ps.setTimestamp(6, bean.getModifiedDatetime());
			ps.setLong(7, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("row affected -->");
			ps.close();
			conn.close();

		} catch (Exception e) {
			log.error("Database exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:delete rollback exception" + ex.getMessage());

			}e.printStackTrace();
			throw new ApplicationException("Exception in updating Role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public void delete(RoleBean bean) throws ApplicationException {
		log.debug("model delete started");

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pe = conn.prepareStatement("DELETE FROM st_role WHERE id = ?");
			pe.setLong(1, bean.getId());
			pe.executeUpdate();
			conn.commit();
			System.out.println("DELETED");
			pe.close();
			conn.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception: Delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception:Exception in delete Role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete ended");
	}

	public RoleBean findByName(String name) throws ApplicationException {
		log.debug("Model findBy EmailId Started");

		StringBuffer sql = new StringBuffer("select * from st_role where name=?");

		RoleBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception.. ", e);
			throw new ApplicationException("Exception:Exception in getting User by emailId");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy EmailId End");
		return bean;
	}
	
	public RoleBean findbypk(long pk) throws ApplicationException {
		log.debug("Model findByPk started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_role WHERE ID=?");
		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
			/*System.out.println("searched successfully");*/
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception.. ", e);
			throw new ApplicationException("Exception : Exception in getting User by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPk End");

		return bean;
	}

	public long nextPK() throws DatabaseException {
		log.debug("Model nextPk started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_role");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("DataBase Exception", e);
			try {
				throw new ApplicationException("Exception: Exception in getting pk");
			} catch (ApplicationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK end");
		return pk + 1;
	}

	public List search(RoleBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_role WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id =" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND Name like ' " + bean.getName() + " '%");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like ' " + bean.getDescription() + " '%");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		// ArrayList<RoleBean> list = new ArrayList<RoleBean>();
		ArrayList list = new ArrayList();
		Connection conn = null;
		// RoleBean bean1;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception.. ", e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search Role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		return list;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_role");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				RoleBean bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception.. ", e);
			throw new ApplicationException("Exception : Exception in getting list of Role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;

	}
}