package in.com.raysproject.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import in.com.raysproject.bean.CourseBean;
import in.com.raysproject.bean.MarksheetBean;
import in.com.raysproject.bean.SubjectBean;
import in.com.raysproject.bean.TimetableBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DatabaseException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.util.JDBCDataSource;


/**
 * JDBC Implements of timetable
 * @author Ajay
 *
 */

public class TimetableModel {
	private static Logger log = Logger.getLogger(TimetableModel.class);

	public long add(TimetableBean bean) throws Exception {
		log.debug("Model add Started");
		Connection conn = null;
		long pk = 0;
		java.util.Date d = bean.getExamDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);
		
		CourseModel Cmodel = new CourseModel();
		CourseBean Cbean = null;
		Cbean = Cmodel.findbypk(bean.getCourseId());
		bean.setCourseName(Cbean.getCourseName());
		
		
		System.out.println("in add method course name and id set");

		SubjectModel Smodel = new SubjectModel();
		SubjectBean Sbean = Smodel.findbypk(bean.getSubjectId());
		bean.setSubjectName(Sbean.getSubjectName());
		
		System.out.println("in add method subject name and id set");

		TimetableBean duplicateSubjectName = findByName(bean.getSubjectName());

		if (duplicateSubjectName != null) {
			throw new DuplicateRecordException("SubjectName Already Exists");

		}

		System.out.println("duplicacy check");
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_timetable values(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setInt(2, bean.getCourseId());
			ps.setString(3, bean.getCourseName());
			ps.setInt(4, bean.getSubjectId());
			ps.setString(5, bean.getSubjectName());
			ps.setString(6, bean.getSemester());
			ps.setString(7, bean.getExamTime());
			ps.setDate(8, new Date(bean.getExamDate().getTime()));
			ps.setString(9, bean.getCreatedBy());
			ps.setString(10, bean.getModifiedBy());
			ps.setTimestamp(11, bean.getCreatedDatetime());
			ps.setTimestamp(12, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			System.out.println("row affected -->");
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("connection roll back in add method");
				throw new ApplicationException("Exception:add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;

	}

	public void update(TimetableBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("model update started");
		Connection conn = null;
		java.util.Date d = bean.getExamDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);
		
		CourseModel Cmodel = new CourseModel();
		CourseBean Cbean = null;
		Cbean = Cmodel.findbypk(bean.getCourseId());
		bean.setCourseName(Cbean.getCourseName());
		
		TimetableBean beanExist = findByName(bean.getSubjectName());
		
		SubjectModel Smodel = new SubjectModel();
		SubjectBean Sbean = Smodel.findbypk(bean.getSubjectId());
		bean.setSubjectName(Sbean.getSubjectName());
		
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("SubjectName is already exist");

		}
		System.out.println("update 3");
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_timetable SET course_id=?, course_name=?, subject_id=?, subject_name=?, semester=?, exam_time=?, exam_date=?, created_By=?, modified_By=?, created_Datetime=?, modified_Datetime=? WHERE Id=?");
			ps.setInt(1, bean.getCourseId());
			ps.setString(2, bean.getCourseName());
			ps.setInt(3, bean.getSubjectId());
			ps.setString(4, bean.getSubjectName());
			ps.setString(5, bean.getSemester());
			ps.setString(6, bean.getExamTime());
			ps.setDate(7, new Date(bean.getExamDate().getTime()));
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
			e.printStackTrace();
			log.error("Database Exception..", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("update 9");
				throw new ApplicationException("Exception:delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in updating Timetable");
		} finally {
			System.out.println("update 10");
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");

	}

	public TimetableBean findByName(String subname) throws ApplicationException {
		log.debug("Model FindByName Started ");
		StringBuffer sql = new StringBuffer("Select * from st_timetable where subject_name=?");

		TimetableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
	//		stmt.setString(1, subjectName);
			stmt.setString(1, "subjectName");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getInt(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getInt(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(7));
				bean.setExamTime(rs.getString(8));
				bean.setExamDate(rs.getDate(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));

			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception:Exception in getting Timetable by SubjectName");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByName Ended");
		return bean;
	}

	public void delete(TimetableBean bean) throws ApplicationException {
		log.debug("Model Delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_timetable where id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("DELETED");
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:Delete RollBack Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception:Exception in delete TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Model delete end");

	}

	public TimetableBean findbypk(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("select * from st_timetable where id=?");
		TimetableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());

			stmt.setLong(1, pk);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getInt(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getInt(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamTime(rs.getString(7));
				bean.setExamDate(new java.util.Date(rs.getDate(8).getTime()));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception:Exception in getting Timetable by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByPK End");
		return bean;
	}

	public long nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_timetable");
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

	public List search(TimetableBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id =" + bean.getId());
			}
			if (bean.getCourseId() != 0 && bean.getCourseId() > 0) {
				System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzz    "+bean.getCourseId());
				sql.append(" AND COURSE_ID = " + bean.getCourseId() );
				
				}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND course_name like '" + bean.getCourseName() + "%'");
			}
			if (bean.getSubjectId() != 0 && bean.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID like '" + bean.getSubjectId() + "%'");
				}
				if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND SUBJECT_NAME like '" + bean.getSubjectName() + "%'");
				}
				if(bean.getSemester() !=null && bean.getSemester().length() >0) {
					sql.append(" AND SEMESTER LIKE '" + bean.getSemester() + "%'");
				}
				if (bean.getExamTime() != null && bean.getExamTime().length() > 0) {
					sql.append(" AND Exam_Time like '" + bean.getExamTime() + "%'");
					}	
			if ((bean.getExamDate() != null) && (bean.getExamDate().getDate() > 0)) {
				Date date = new Date(bean.getExamDate().getTime());
	             System.out.println(">>>>"+date);
				sql.append(" AND EXAM_DATE like '" + date+ "%'");
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
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getInt(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getInt(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamTime(rs.getString(7));
				bean.setExamDate(new java.util.Date(rs.getDate(8).getTime()));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	public List search(TimetableBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable");
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
				TimetableBean bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getInt(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getInt(4));
				bean.setSubjectName(rs.getNString(5));
				bean.setSemester(rs.getNString(6));
				bean.setExamTime(rs.getString(7));
				bean.setExamDate(new java.util.Date(rs.getDate(8).getTime()));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

	public static TimetableBean checkByCourseName(long CourseId, java.util.Date ExamDate) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimetableBean tbean = null;
		System.out.println("jjjj" + CourseId + ",,," + ExamDate);
		Date Exdate = new Date(ExamDate.getTime());

		StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable WHERE COURSE_ID=? " + "AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setDate(2, Exdate);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimetableBean();
				tbean.setId(rs.getLong(1));
				tbean.setCourseId(rs.getInt(2));
				tbean.setCourseName(rs.getString(3));
				tbean.setSubjectId(rs.getInt(4));
				tbean.setSubjectName(rs.getString(5));
				tbean.setSemester(rs.getString(6));
				tbean.setExamTime(rs.getString(7));
				tbean.setExamDate(rs.getDate(8));
				tbean.setCreatedBy(rs.getString(9));
				tbean.setModifiedBy(rs.getString(10));
				tbean.setCreatedDatetime(rs.getTimestamp(11));
				tbean.setModifiedDatetime(rs.getTimestamp(12));

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timetable model checkByCourseName..." + e.getMessage());
		}
		return tbean;
	}

	public static TimetableBean checkBySubjectName(long CourseId, long SubjectId, java.util.Date ExamDAte)
			throws ApplicationException {
		System.out.println("jjjj" + CourseId + "kj" + SubjectId + ",,," + ExamDAte);
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimetableBean tbean = null;
		Date ExDate = new Date(ExamDAte.getTime());
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM st_timetable WHERE COURSE_ID=? AND SUBJECT_ID=? AND" + " EXAM_DATE=?");

		try { 
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setDate(3, ExDate);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimetableBean();
				tbean.setId(rs.getLong(1));
				tbean.setCourseId(rs.getInt(2));
				tbean.setCourseName(rs.getString(3));
				tbean.setSubjectId(rs.getInt(4));
				tbean.setSubjectName(rs.getString(5));
				tbean.setSemester(rs.getString(6));
				tbean.setExamTime(rs.getString(7));
				tbean.setExamDate(rs.getDate(8));
				tbean.setCreatedBy(rs.getString(9));
				tbean.setModifiedBy(rs.getString(10));
				tbean.setCreatedDatetime(rs.getTimestamp(11));
				tbean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timetable model checkBySubjectName..." + e.getMessage());
		}
		return tbean;
	}

	public static TimetableBean checkBysemester(long CourseId, long SubjectId, String semester, java.util.Date ExamDAte)
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimetableBean tbean = null;
		System.out.println("jjkkk" + CourseId + "jjj" + SubjectId + "kk" + semester + "kk" + ExamDAte);
		Date ExDate = new Date(ExamDAte.getTime());

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM st_timetable WHERE COURSE_ID=? AND SUBJECT_ID=? AND " + " SEMESTER=? AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, ExDate);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimetableBean();
				tbean.setId(rs.getLong(1));
				tbean.setCourseId(rs.getInt(2));
				tbean.setCourseName(rs.getString(3));
				tbean.setSubjectId(rs.getInt(4));
				tbean.setSubjectName(rs.getString(5));
				tbean.setSemester(rs.getString(6));
				tbean.setExamTime(rs.getString(7));
				tbean.setExamDate(rs.getDate(8));
				tbean.setCreatedBy(rs.getString(9));
				tbean.setModifiedBy(rs.getString(10));
				tbean.setCreatedDatetime(rs.getTimestamp(11));
				tbean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timetable model checkBySubjectName..." + e.getMessage());
		}
		return tbean;
	}

	public static TimetableBean checkByExamTime(long CourseId, long SubjectId, String semester, java.util.Date ExamDAte,
			String ExamTime) throws ApplicationException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		TimetableBean tbean = null;
		Date ExDate = new Date(ExamDAte.getTime());
		StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable WHERE COURSE_ID=? AND SUBJECT_ID=? AND"
				+ " SEMESTER=? AND EXAM_DATE=? AND EXAM_TIME=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, ExDate);
			ps.setString(5, ExamTime);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimetableBean();
				tbean.setId(rs.getLong(1));
				tbean.setCourseId(rs.getInt(2));
				tbean.setCourseName(rs.getString(3));
				tbean.setSubjectId(rs.getInt(4));
				tbean.setSubjectName(rs.getString(5));
				tbean.setSemester(rs.getString(6));
				tbean.setExamTime(rs.getString(7));
				tbean.setExamDate(rs.getDate(8));
				tbean.setCreatedBy(rs.getString(9));
				tbean.setModifiedBy(rs.getString(10));
				tbean.setCreatedDatetime(rs.getTimestamp(11));
				tbean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timetable model checkByexamTime..." + e.getMessage());
		}
		return tbean;
	}
}
