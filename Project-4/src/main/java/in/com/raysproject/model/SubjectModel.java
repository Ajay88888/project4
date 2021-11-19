package in.com.raysproject.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import in.com.raysproject.bean.CourseBean;
import in.com.raysproject.bean.SubjectBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DatabaseException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.util.JDBCDataSource;

/**
 * JDBC Implement of subject
 * @author Ajay
 *
 */


public class SubjectModel {
	private static Logger log = Logger.getLogger(CollegeModel.class);

	public long add(SubjectBean bean) throws Exception {
		log.debug("Model add Started");
		Connection conn = null;
		long pk = 1;
		CourseModel cmodel = new CourseModel();
		CourseBean cBean = cmodel.findbypk(bean.getCourseId());
		bean.setCourseName(cBean.getCourseName());

		SubjectBean duplicateSubjectName = findByName(bean.getSubjectName());

		if (duplicateSubjectName != null) {
			throw new DuplicateRecordException("Subject Name Already Exists");

		}
		try {
			conn = JDBCDataSource.getConnection();

			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getSubjectName());
			ps.setString(3, bean.getDescription());
			ps.setString(4, bean.getCourseName());
			ps.setLong(5, bean.getCourseId());
			ps.setLong(6, bean.getSubjectId());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit();
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
			throw new ApplicationException("Exception : Exception in add Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;

	}

	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("select max(id) from st_subject");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
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

	public void delete(SubjectBean bean) throws ApplicationException {
		log.debug("Model Delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_subject where id=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:Delete RollBack Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception:Exception in delete Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Model delete end");

	}

	public SubjectBean findByName(String subjectname) throws ApplicationException {
		log.debug("Model FindByName Started ");
		StringBuffer sql = new StringBuffer("Select * from st_subject where subject_name=?");

		SubjectBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, subjectname);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setCourseId(rs.getLong(5));
				bean.setSubjectId(rs.getLong(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting Subject by Name");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByName Ended");
		return bean;
	}

	public SubjectBean findbypk(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");

		StringBuffer sql = new StringBuffer("select * from st_subject where id=?");

		SubjectBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setCourseId(rs.getLong(5));
				bean.setSubjectId(rs.getLong(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting Subject by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByPK End");
		return bean;

	}

	public void update(SubjectBean bean) throws DuplicateRecordException, ApplicationException {

		log.debug("Model update started");

		Connection conn = null;
		CourseModel cmodel = new CourseModel();
		CourseBean cBean = cmodel.findbypk(bean.getCourseId());
		bean.setCourseName(cBean.getCourseName());

		SubjectBean beanExist = findByName(bean.getSubjectName());

		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Subject is already exist");

		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"Update st_subject set subject_name=?,description=?,Course_name=?,course_id=?,subject_id=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");

			ps.setString(1, bean.getSubjectName());
			ps.setString(2, bean.getDescription());
			ps.setString(3, bean.getCourseName());
			ps.setLong(4, bean.getCourseId());
			ps.setLong(5, bean.getSubjectId());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDatetime());
			ps.setTimestamp(9, bean.getModifiedDatetime());
			ps.setLong(10, bean.getId());

			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in updating Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");

	}

	public List search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_subject WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND SUBJECT_NAME like '" + bean.getSubjectName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND Description like '" + bean.getDescription() + "%'");
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND COURSE_NAME like '" + bean.getCourseName() + "%'");
			}
			if (bean.getCourseId() != 0 && bean.getCourseId() > 0) {
				sql.append(" AND COURSE_ID like '" + bean.getCourseId() + "%'");
			}
			if (bean.getSubjectId() != 0 && bean.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID like '" + bean.getSubjectId() + "%'");
			}
		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setCourseId(rs.getLong(5));
				bean.setSubjectId(rs.getLong(6));
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
			throw new ApplicationException("Exception : Exception in search course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	public List search(SubjectBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_subject");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				SubjectBean bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setCourseId(rs.getLong(5));
				bean.setSubjectId(rs.getLong(6));
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
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

}
