package in.com.raysproject.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import in.com.raysproject.bean.CollegeBean;
import in.com.raysproject.bean.StudentBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DatabaseException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.util.JDBCDataSource;

/**
 * JDBC Implement of student model
 * @author Ajay
 *
 */

public class StudentModel {

	private static Logger log = Logger.getLogger(StudentModel.class);

	public long add(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model studentbean add started");

		Connection conn = null;
		CollegeModel cModel = new CollegeModel();
		CollegeBean collegeBean = cModel.findbypk(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());
		StudentBean duplicateName = findByEmailId(bean.getEmailId());
		long pk = 0;
		if (duplicateName!= null) {
			throw new DuplicateRecordException("Email already exist");
		}
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement("INSERT INTO st_student VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
				pk=nextPK();
				ps.setLong(1, pk);
				ps.setString(2, bean.getFirstName());
				ps.setString(3, bean.getLastName());
				ps.setDate(4, new Date(bean.getDob().getTime()));
			//	ps.setDate(6,new java.sql.Date(bean.getDob().getTime()));
				ps.setString(5, bean.getMobileNo());
				ps.setString(6, bean.getEmailId());
				ps.setLong(7, bean.getCollegeId());
				ps.setString(8, bean.getCollegeName());
				ps.setString(9, bean.getCreatedBy());
				ps.setString(10, bean.getModifiedBy());
				ps.setTimestamp(11, bean.getCreatedDatetime());
				ps.setTimestamp(12, bean.getModifiedDatetime());
				ps.executeUpdate();
				conn.commit();
				System.out.println("row affected -->");
				ps.close();
				// conn.close();
			} catch (Exception e) {
				log.error("Database Exception..", e);
				e.printStackTrace();
				try {
					conn.rollback();

				} catch (Exception ex) {
					ex.printStackTrace();
					throw new ApplicationException("Exception: add rollback exception " + ex.getMessage());
				}
				throw new ApplicationException("Exception:Exception in add student");

			} finally {
				JDBCDataSource.closeConnection(conn);

			}
			log.debug("Model add End");
			return pk;
		}

	public void update(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		StudentBean beanExist = findByEmailId(bean.getEmailId());
		System.out.println("update method beaExxist check");
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			System.out.println("duplicate record mfound");
			throw new DuplicateRecordException("Email Id is already exist");

		}
		CollegeModel cModel = new CollegeModel();
		CollegeBean collegeBean = cModel.findbypk(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());
		System.out.println("College name" + collegeBean.getName());
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_student SET FirstName=?, LastName=?, Dob=?, Mobile_No=?, Email=?, College_Id=?, College_Name=?, created_By=?, modified_By=?, created_Datetime=?, modified_Datetime=? WHERE Id=?");
			ps.setString(1, bean.getFirstName());
			ps.setString(2, bean.getLastName());
			ps.setDate(3, new Date(bean.getDob().getTime()));
			ps.setString(4, bean.getMobileNo());
			ps.setString(5, bean.getEmailId());
			ps.setLong(6, bean.getCollegeId());
			ps.setString(7, bean.getCollegeName());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			ps.setLong(12, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("UPDATED");
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error("DatabaseException..", e);
			try {
				System.out.println("transaction rollback");
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exception: delete rollback excption" + ex.getMessage());

			}
			throw new ApplicationException("Exception in update student");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model update end");

	}

	public void delete(StudentBean bean) throws ApplicationException {
		log.debug("model delete started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_student WHERE id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("DELETED");
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error("Database exception..", e);
			try {
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exception: Delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in delete Student");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete end");

	}

	public StudentBean findByEmailId(String Email) throws ApplicationException {
		log.debug("model findByEmailId started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_student WHERE EMAIL=?");
		Connection conn = null;
		StudentBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, Email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setMobileNo(rs.getString(5));
				bean.setEmailId(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception.. ", e);
			throw new ApplicationException("Exception: Exception in getting User by Email");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Email End");
		return bean;
	}

	public StudentBean findbypk(long pk) throws ApplicationException {
		log.debug("model find By Pk started");
		StringBuffer sql=new StringBuffer("Select * from st_student where id=?");
		StudentBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				bean = new StudentBean();
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setMobileNo(rs.getString(5));
				bean.setEmailId(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				bean.setId(rs.getLong(1));
			}
			System.out.println("row affacted -->");
			ps.close();
			conn.close();

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception:Exception in getting User by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Pk end");
		return bean;
	}

	public long nextPK() throws DatabaseException {
		log.debug("model next pk started");
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_student");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception: Exception in getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	public List search(StudentBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_student WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id =" + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRSTNAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LASTNAME like '" + bean.getLastName() + "%'");
			}
			
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO like '" + bean.getMobileNo() + "%'");
			}
			if (bean.getEmailId() != null && bean.getEmailId().length() > 0) {
				sql.append(" AND EMAIL like '" + bean.getEmailId() + "%'");
			}
			if (bean.getCollegeId() != 0) {
				sql.append(" AND College_Id like '" + bean.getCollegeId() + "%'");
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND COLLEGE_NAME '" + bean.getCollegeName());
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
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				//bean.setDob(new java.util.Date(rs.getDate(4).getTime()));
				bean.setDob(rs.getDate(4));
				bean.setMobileNo(rs.getString(5));
				bean.setEmailId(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (

		Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Serach End");
		return list;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_student");
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
			StudentBean bean = new StudentBean();
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setMobileNo(rs.getString(5));
			bean.setEmailId(rs.getString(6));
			bean.setCollegeId(rs.getLong(7));
			bean.setCollegeName(rs.getString(8));
			bean.setCreatedBy(rs.getString(9));
			bean.setModifiedBy(rs.getString(10));
			bean.setCreatedDatetime(rs.getTimestamp(11));
			bean.setModifiedDatetime(rs.getTimestamp(12));
			list.add(bean);
		}
		rs.close();
		 }catch(Exception e){
			   log.error("Database Exception..", e);
		       throw new ApplicationException("Exception : Exception in getting list of Student");
			}finally{
				JDBCDataSource.closeConnection(conn);
			}
			  log.debug("Model list End");
		        return list;
	}
}
