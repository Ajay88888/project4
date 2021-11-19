package in.com.raysproject.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import in.com.raysproject.bean.CollegeBean;
import in.com.raysproject.bean.CourseBean;
import in.com.raysproject.bean.FacultyBean;
import in.com.raysproject.bean.SubjectBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DatabaseException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.util.JDBCDataSource;

/**
 * JDBC Implements of faculty model
 * @author Ajay
 *
 */

public class FacultyModel {
	private static Logger log = Logger.getLogger(FacultyModel.class);

	public long add(FacultyBean fbean) throws ApplicationException, DuplicateRecordException {
	
		Connection conn = null;
		
		long pk = 0;
		CollegeModel model = new CollegeModel();
		CollegeBean bean = model.findbypk(fbean.getCollegeId());
		String CollegeName = bean.getName();
		fbean.setCollegeName(CollegeName);
		
		CourseModel model1 = new CourseModel();
		CourseBean bean1 = model1.findbypk(fbean.getCourseId());
		String CourseName = bean1.getCourseName();
		fbean.setCourseName(CourseName);
		
		SubjectModel model2 = new SubjectModel();
		SubjectBean bean2 = model2.findbypk(fbean.getSubjectId());
		String SubjectName = bean2.getSubjectName();
		fbean.setSubjectName(SubjectName);
		
		FacultyBean duplicateRole = findbyName(fbean.getFirstName());
		// Check if create Faculty already exist
		if (duplicateRole != null) {
			throw new DuplicateRecordException("Faculty already exists");
		}
		java.util.Date d = fbean.getDateOfJoining();
		long l = d.getTime();
	//	String i =(String)d.getTime();
		java.sql.Date date = new java.sql.Date(l);
		System.out.println(date);
		try {
		
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("insert into st_faculty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, fbean.getFirstName());
			ps.setString(3, fbean.getLastName());
			ps.setString(4, fbean.getGender());
			ps.setString(5, fbean.getLoginId());
			ps.setDate(6, new Date(fbean.getDateOfJoining().getTime()));
			ps.setString(7, fbean.getQualification());
			ps.setString(8, fbean.getMobileNo());
			ps.setInt(9, fbean.getCollegeId());
			ps.setString(10, fbean.getCollegeName());
			ps.setInt(11, fbean.getCourseId());
			ps.setString(12, fbean.getCourseName());
			ps.setInt(13, fbean.getSubjectId());
			ps.setString(14, fbean.getSubjectName());
			ps.setString(15, fbean.getCreatedBy());
			ps.setString(16, fbean.getModifiedBy());
			ps.setTimestamp(17, fbean.getCreatedDatetime());
			ps.setTimestamp(18, fbean.getModifiedDatetime());
			int a = ps.executeUpdate();
			System.out.println("insert data" + a);
			ps.close();
			conn.commit();

			System.out.println("row affected -->" + a);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;

	}

	public void update(FacultyBean fbean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		System.out.println("update method start");
		Connection conn = null;

		FacultyBean beanexist = findbyName(fbean.getFirstName());

		System.out.println("update method bean exist");

		CollegeModel model = new CollegeModel();
		CollegeBean bean = model.findbypk(fbean.getCollegeId());
		String CollegeName = bean.getName();
		fbean.setCollegeName(CollegeName);
		
		CourseModel model1 = new CourseModel();
		CourseBean bean1 = model1.findbypk(fbean.getCourseId());
		String CourseName = bean1.getCourseName();
		fbean.setCourseName(CourseName);
		
		SubjectModel model2 = new SubjectModel();
		SubjectBean bean2 = model2.findbypk(fbean.getSubjectId());
		String SubjectName = bean2.getSubjectName();
		fbean.setSubjectName(SubjectName);

		System.out.println("update method get subject name");
		
		java.util.Date d = fbean.getDateOfJoining();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);

		System.out.println("update method get date");
		
		if (beanexist != null && beanexist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Faculty is already exist");
		}
		System.out.println("update method no duplicacy found");
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_faculty SET FirstName=?, LastName=?, gender=?, login_Id=?, date_of_joining=?, qualification=?, mobile_no=?, college_Id=?, collegename=?, course_Id=?, coursename=?, subject_Id=?, subjectname=?, created_By=?, modified_By=?, created_Datetime=?, modified_Datetime=? WHERE Id=?");

			ps.setString(1, fbean.getFirstName());
			ps.setString(2, fbean.getLastName());
			ps.setString(3, fbean.getGender());
			ps.setString(4, fbean.getLoginId());
			ps.setDate(5, new Date(fbean.getDateOfJoining().getTime()));
			ps.setString(6, fbean.getQualification());
			ps.setString(7, fbean.getMobileNo());
			ps.setInt(8, fbean.getCollegeId());
			ps.setString(9, fbean.getCollegeName());
			ps.setInt(10, fbean.getCourseId());
			ps.setString(11, fbean.getCourseName());
			ps.setLong(12, fbean.getSubjectId());
			ps.setString(13, fbean.getSubjectName());
			ps.setString(14, fbean.getCreatedBy());
			ps.setString(15, fbean.getModifiedBy());
			ps.setTimestamp(16, fbean.getCreatedDatetime());
			ps.setTimestamp(17, fbean.getModifiedDatetime());
			ps.setLong(18, fbean.getId());
			System.out.println("update method set all parameter");
		int res =	ps.executeUpdate();
			System.out.println("update method execute query done"+res);
			conn.commit();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);

			try {
				System.out.println("update method rollback");
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in updating Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");

	}

	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("model delete started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_faculty where id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("deleted");
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error("DataBase Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete end");

	}

	public long nextPK() throws DatabaseException {
		log.debug("model nextPK started");

		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_faculty");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("DataBase Exception", e);
			throw new DatabaseException("Exception: Exception in getting pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK end");
		return pk + 1;
	}

	public FacultyBean findbypk(long pk) throws ApplicationException {
		log.debug("model find by pk started");
		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_faculty WHERE Id = ?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginId(rs.getString(5));
				bean.setDateOfJoining(rs.getDate(6));
				bean.setQualification(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getInt(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getInt(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getInt(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
			
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting Faculty by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByPK End");
		return bean;
	}

	public List search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_faculty WHERE 1=1");
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
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND Gender like '" + bean.getGender() + "%'");
				}
			if (bean.getLoginId() != null && bean.getLoginId().length() > 0) {
				sql.append(" AND login_Id like '" + bean.getLoginId() + "%'");
				}
			
			if (bean.getDateOfJoining() != null &&(((CharSequence) bean.getDateOfJoining()).length()>0 )) {
				sql.append(" AND Date_Of_Joining like '" + bean.getDateOfJoining() + "%'");
				}
			if (bean.getQualification() != null && bean.getQualification().length() > 0) {
				sql.append(" AND Qualification like '" + bean.getQualification() + "%'");
				}
			
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND Mobile_No like '" + bean.getMobileNo() + "%'");
				}
			if (bean.getCollegeId() != 0) {
				sql.append(" AND College_Id like '" + bean.getCollegeId() + "%'");
				}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND College_Name like '" + bean.getCollegeName() + "%'");
				}
			if (bean.getCourseId() != 0 ) {
				sql.append(" AND Course_ID like '" + bean.getCourseId() + "%'");
				}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND Course_Name like '" + bean.getCourseName() + "%'");
				}
			if (bean.getSubjectId() != 0 ) {
				sql.append(" AND Subject_Id like '" + bean.getSubjectId() + "%'");
				}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND Subject_Name like '" + bean.getSubjectName() + "%'");
				}
		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
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
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginId(rs.getString(5));
				bean.setDateOfJoining(rs.getDate(6));
			//	bean.setDate(7, new Date(rs.getDateOfJoining().getTime()));
				bean.setQualification(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getInt(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getInt(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getInt(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_faculty");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FacultyBean bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginId(rs.getString(5));
				bean.setDateOfJoining(rs.getDate(6));
				bean.setQualification(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getInt(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getInt(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getInt(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
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

	public FacultyBean findbyName(String firstname) throws ApplicationException {
		log.debug("Model FindByName Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM st_faculty WHERE firstname =?");
		Connection conn = null;
		FacultyBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			// ps.setString(1, string);
			ps.setString(1, firstname);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting Faculty by Name");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByName Ended");
		return bean;
	}
}