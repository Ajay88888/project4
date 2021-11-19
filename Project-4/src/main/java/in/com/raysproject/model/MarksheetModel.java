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
import in.com.raysproject.bean.MarksheetBean;
import in.com.raysproject.bean.StudentBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DatabaseException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.util.JDBCDataSource;


/**
 * JDBC Implements of marksheet model
 * 
 * @author Ajay
 *
 */

public class MarksheetModel {
	Logger log = Logger.getLogger(MarksheetModel.class);

	public long nextPK() throws DatabaseException {
		log.debug("Model nextPK started");

		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_marksheet");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Exception in Marksheet getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK end");
		return pk + 1;
	}

	public long add(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add started");
		Connection conn = null;
		StudentModel sModel = new StudentModel();
		StudentBean studentbean = sModel.findbypk(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + "" + studentbean.getLastName());
		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());
		long pk = 0;

		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Roll Number already exist");
		}
		try {
			System.out.println("add method");
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setLong(2, bean.getStudentId());
			ps.setString(3, bean.getName());
			ps.setInt(4, bean.getPhysics());
			ps.setInt(5, bean.getChemistry());
			ps.setInt(6, bean.getMaths());
			ps.setString(7, bean.getRollNo());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			System.out.println("row affected -->");
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception in add marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add end");
		return pk;
	}


	public void update(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Started");
		Connection conn = null;
		MarksheetBean beanExist = findByRollNo(bean.getRollNo());
		StudentModel sModel = new StudentModel();
		StudentBean studentbean = sModel.findbypk(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + "" + studentbean.getLastName());
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_marksheet SET student_id=?, name=?, physics=?, chemistry=?, maths=?, roll_no=?, created_By=?, modified_By=?,created_Datetime=?,modified_Datetime=? WHERE Id=?");

			ps.setLong(1, bean.getStudentId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getPhysics());
			ps.setInt(4, bean.getChemistry());
			ps.setInt(5, bean.getMaths());
			ps.setString(6, bean.getRollNo());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			ps.setLong(11, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("UPDATED");
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("update rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model update end");
	}

	
	public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
		log.debug("model find by roll no started");
		System.out.println("marksheet model  method findByRollNo started");
		StringBuffer sql = new StringBuffer("select * from st_marksheet where roll_no=?");
		MarksheetBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, rollNo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setStudentId(rs.getLong(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				bean.setRollNo(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("exception in getting marksheet by rollno");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findByRollNo end");
		return bean;
	}

	public void delete(MarksheetBean bean) throws ApplicationException {
		log.debug("model delete started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_marksheet where id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("DELETED");
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				log.error(ex);
				throw new ApplicationException("Delete rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("exception in delete marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete end");
	}

	public MarksheetBean findbypk(long pk) throws ApplicationException {
		log.debug("model findbypk started");
		StringBuffer sql = new StringBuffer("select *from st_marksheet where id=?");
		
		MarksheetBean bean = null;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setStudentId(rs.getLong(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				bean.setRollNo(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				bean.setId(rs.getLong(1));
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception in getting marksheet  by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model findByPK end");
		/*
		 * System.out.println("row affacted -->"); ps.close(); conn.close();
		 */
		return bean;
	}

	public List search(MarksheetBean bean) throws Exception {
		System.out.println("marksheet model Method search 0 Started");
		return search(bean, 0, 0);
	}

	public List search(MarksheetBean marksheetBean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		System.out.println("<<<>>> Search(1) method (rollNo)= " + marksheetBean.getRollNo());
		StringBuffer sql = new StringBuffer("SELECT * FROM st_marksheet WHERE 1=1");
		if (marksheetBean != null) {
			if (marksheetBean.getId() > 0) {
				sql.append(" AND id = " + marksheetBean.getId());
			}
			if ((marksheetBean.getRollNo() != null) && (marksheetBean.getRollNo().length() > 0)) {

				sql.append(" AND ROLL_NO like '" + marksheetBean.getRollNo() + "%'");
			}
			
			if (marksheetBean.getName() != null && marksheetBean.getName().length() > 0) {
				sql.append(" AND name like '" + marksheetBean.getName() + "%'");
			}
		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			MarksheetBean markBean;
			while (rs.next()) {
				markBean = new MarksheetBean();
				markBean.setId(rs.getLong(1));
				markBean.setStudentId(rs.getLong(2));
				markBean.setName(rs.getString(3));
				markBean.setPhysics(rs.getInt(4));
				markBean.setChemistry(rs.getInt(5));
				markBean.setMaths(rs.getInt(6));
				markBean.setRollNo(rs.getString(7));
				markBean.setCreatedBy(rs.getString(8));
				markBean.setModifiedBy(rs.getString(9));
				markBean.setCreatedDatetime(rs.getTimestamp(10));
				markBean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(markBean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model serch end");
		return list;
	}

	public List list() throws ApplicationException {
		System.out.println("marksheet model Method List(0) Started");
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		System.out.println("marksheet model Method List(1) Started");
		log.debug("model list started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_marksheet ");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setStudentId(rs.getLong(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				bean.setRollNo(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				bean.setId(rs.getLong(1));				
				
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception in getting list of marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;
	}

	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		log.debug("model meritlist started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"select id, roll_no, name, physics, chemistry, maths,(physics+chemistry+maths)as total from st_marksheet order by total desc");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new ApplicationException("Exception in getting Meritlist of marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Meritlist End");
		return list;
	}
}