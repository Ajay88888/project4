package in.com.raysproject.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import in.com.raysproject.bean.CollegeBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DatabaseException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.util.JDBCDataSource;


/**
 * JDBC implements of college model
 * @author Ajay
 *
 */
public class CollegeModel {
	private static Logger log = Logger.getLogger(CollegeModel.class);

	public long add(CollegeBean bean) throws Exception {
		log.debug("Model add started");
		System.out.println("Add");
		long pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			System.out.println("addd 1");
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO st_college VALUES(?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			// ps.setLong(1, bean.getId());
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getAddress());
			ps.setString(4, bean.getState());
			ps.setString(5, bean.getCity());
			ps.setString(6, bean.getPhoneNo());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			System.out.println("row affected -->" + bean.toString());
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add end");
		return pk;
	}

	/*
	 * public long add(CollegeBean bean) throws ApplicationException,
	 * DuplicateRecordException{
	 * 
	 * // log.debug("model add has started"); CollegeBean duplicaterecord
	 * =findByName(bean.getName()); if(duplicaterecord!=null){ throw new
	 * DuplicateRecordException("name in collegebean already exits"); }
	 * 
	 * Connection conn =null; long pk=0; try{ conn =
	 * JDBCDataSource.getConnection(); conn.setAutoCommit(false);
	 * PreparedStatement ps = conn.prepareStatement(
	 * "insert into st_college values(?,?,?,?,?,?,?,?,?,?)"); pk=nextPK();
	 * ps.setLong(1, pk); ps.setString(2,bean.getName()); ps.setString(3,
	 * bean.getAddress()); ps.setString(4, bean.getState()); ps.setString(5,
	 * bean.getCity()); ps.setString(6, bean.getPhoneNo()); ps.setString(7,
	 * bean.getCreatedBy()); ps.setString(8, bean.getModifiedBy()); //
	 * ps.setTimestamp(9, bean.getCreateDateTime()); // ps.setTimestamp(10,
	 * bean.getModifiedDateTime());
	 * 
	 * ps.executeUpdate(); conn.commit(); System.out.println("inserted");
	 * ps.close(); }catch(Exception e){ try{ conn.rollback(); } catch(Exception
	 * ex){ ex.printStackTrace(); } // log.error("DatabaseException",e); //
	 * throw new ApplicationException(e.getMessage()); } finally {
	 * JDBCDataSource.closeConnection(conn); } // log.debug(
	 * "model add has ended"); return pk;
	 * 
	 * }
	 */

	public void update(CollegeBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("Model update started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_college SET Name=?, Address=?, State=?, City=?, Phone_No=?, created_By=?, modified_By=?, created_Datetime=?, modified_Datetime=?  WHERE id=?");
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getState());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getPhoneNo());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDatetime());
			ps.setTimestamp(9, bean.getModifiedDatetime());
			ps.setLong(10, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("row affected -->" + bean.toString());
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:delete rollback exception" + ex.getMessage());

			}
			e.printStackTrace();
			throw new ApplicationException("Exception in updating college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public void delete(CollegeBean bean) throws ApplicationException {
		log.debug("Model Delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_college WHERE Id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("deleted -->");
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:Delete RollBack Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception:Exception in delete college");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Model delete end");

	}

	public long nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_college");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception:Exception is getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model nextPK End");
		return pk + 1;
	}

	public CollegeBean findbypk(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("select * from st_college where id=?");

		CollegeBean bean = new CollegeBean();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			System.out.println("row affacted -->");
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception:Exception in getting College by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByPK End");
		return bean;

	}

	public CollegeBean findbyName(String name) throws Exception {
		log.debug("Model FindByName Started ");

		StringBuffer sql = new StringBuffer("Select * from st_college where name=?");
		Connection conn = null;
		CollegeBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting College by Name");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByName Ended");
		return bean;
	}

	public List search(CollegeBean bean, int pageNo, int pageSize) throws Exception {
		log.debug("Model Search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_college WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id =" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			System.out.println("Sql-->" + sql.toString());
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like'" + bean.getAddress() + "%'");
			}
			if (bean.getState() != null && bean.getState().length() > 0) {
				sql.append(" AND STATE like '" + bean.getState() + "%'");
			}
			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" AND CITY like '" + bean.getCity() + "%'");
			}
			if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
				sql.append(" AND PHONE_NO = " + bean.getPhoneNo());
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception:Exception in search college");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search End");
		return list;
	}

	public List search(CollegeBean bean) throws Exception {
		return search(bean, 0, 0);
	}

	public List list() throws Exception {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		log.debug("Model list Started");
		
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_college");
		
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CollegeBean bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting list of user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list end");
		return list;

	}
}
