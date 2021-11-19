package in.com.raysproject.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import in.com.raysproject.bean.UserBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DatabaseException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.exception.RecordNotFoundException;
import in.com.raysproject.util.EmailBuilder;
import in.com.raysproject.util.EmailMessage;
import in.com.raysproject.util.EmailUtility;
import in.com.raysproject.util.JDBCDataSource;

/**
 * JDBC Implement of user model
 * @author Ajay
 *
 */

public class UserModel {
	private static Logger log = Logger.getLogger(UserModel.class);

	private long Roleid;

	public long Roleid() {
		return Roleid;
	}

	public void setRoleid(long Roleid) {
		this.Roleid = Roleid;
	}

	public long add(UserBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("Model add started");
		Connection conn = null;
		long pk = 0;
		UserBean existbean = findByLogin(bean.getLogin());

		if (existbean != null) {
			throw new DuplicateRecordException("Login id already exist");

		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO st_user VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getFirstName());
			ps.setString(3, bean.getLastName());
			ps.setString(4, bean.getLogin());
			ps.setString(5, bean.getPassword());
			ps.setString(6, bean.getConfirmPassword());
			ps.setDate(7, new Date(bean.getDob().getTime()));
			ps.setString(8, bean.getMobileNo());
			ps.setLong(9, bean.getRoleId());
			ps.setString(10, bean.getGender());
			ps.setString(11, bean.getCreatedBy());
			ps.setString(12, bean.getModifiedBy());
			ps.setTimestamp(13, bean.getCreatedDatetime());
			ps.setTimestamp(14, bean.getModifiedDatetime());
			
			ps.executeUpdate();
			conn.commit();
			System.out.println("row affacted -->");
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:add roll back Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in add user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add end");
		return pk;

	}

	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		UserBean beanExist = findByLogin(bean.getLogin());
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("Login Id already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_user SET First_Name=?,Last_Name=?, login=?, password=?, Confirm_Password=?, dob=?, Mobile_No=?, Role_Id=?, Gender=?, created_By=?, modified_By=?, created_Datetime=?, modified_Datetime=? WHERE id=?");

			ps.setString(1, bean.getFirstName());
			ps.setString(2, bean.getLastName());
			ps.setString(3, bean.getLogin());
			ps.setString(4, bean.getPassword());
			ps.setString(5, bean.getConfirmPassword());
			ps.setDate(6, new Date(bean.getDob().getTime()));
			ps.setString(7, bean.getMobileNo());
			ps.setLong(8, bean.getRoleId());
			ps.setString(9, bean.getGender());
			ps.setString(10, bean.getCreatedBy());
			ps.setString(11, bean.getModifiedBy());
			ps.setTimestamp(12, bean.getCreatedDatetime());
			ps.setTimestamp(13, bean.getModifiedDatetime());
			ps.setLong(14, bean.getId());
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
				throw new ApplicationException("Exception:Delete rollback Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in updating user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update end");

	}

	public void delete(UserBean bean) throws ApplicationException {
		log.debug("Model delete started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_user WHERE id = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("DELETED");
			ps.close();
			conn.close();
		} catch (Exception e) {
			log.error("Database Exception ", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:Delete rollback Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception:Exception in delete user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete end");

	}

	public UserBean findbypk(long pk) throws ApplicationException {

		log.debug("Model findByPK started");

		StringBuffer sql = new StringBuffer("SELECT * FROM st_user WHERE id = ?");
		Connection conn = null;
		UserBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));

			}
			rs.close();
			System.out.println("row affacted -->" + bean.toString());
			ps.close();
			conn.close();
		} catch (Exception e) {

			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	public long nextPK() throws DatabaseException {
		log.debug("Model nextPK started");
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_user ");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("DatabaseException", e);
			throw new DatabaseException("Exception: Exception in getting pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public List search(UserBean bean) throws ApplicationException {
		try {
			return search(bean, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_user WHERE 1=1");
		System.out.println("usermodel search method sql" + sql );
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id =" + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			if (bean.getConfirmPassword() != null && bean.getConfirmPassword().length() > 0) {
				sql.append(" AND CONFIRM PASSWORD like '" + bean.getConfirmPassword() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getGender());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
			}
			if (bean.getRoleId() > 0) {
				sql.append(" AND ROLE_ID = " + bean.getRoleId());
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + bean.getGender() + "%'");
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" Limit " + pageNo + "," + pageSize);
			}
			System.out.println(sql);
			ArrayList list = new ArrayList();
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					bean = new UserBean();
					bean.setId(rs.getLong(1));
					bean.setFirstName(rs.getString(2));
					bean.setLastName(rs.getString(3));
					bean.setLogin(rs.getString(4));
					bean.setPassword(rs.getString(5));
					bean.setConfirmPassword(rs.getString(6));
					bean.setDob(rs.getDate(7));
					bean.setMobileNo(rs.getString(8));
					bean.setRoleId(rs.getLong(9));
					bean.setGender(rs.getString(10));
					bean.setCreatedBy(rs.getString(11));
					bean.setModifiedBy(rs.getString(12));
					bean.setCreatedDatetime(rs.getTimestamp(13));
					bean.setModifiedDatetime(rs.getTimestamp(14));

					list.add(bean);
				}
				rs.close();
			} catch (Exception e) {
				log.error("Database Exception..", e);
				throw new ApplicationException("Exception : Exception in getting User by pk");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

			log.debug("Model search End");
			return list;
		}
		return null;
	}

	public List list() throws ApplicationException {
		try {
			return list(0, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_user");

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

				UserBean bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
	}

	public UserBean findByLogin(String login) throws ApplicationException {

		log.debug("Model findByLogin Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM st_user WHERE LOGIN=?");

		UserBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception", e);
			throw new ApplicationException("Exception:Exception in getting User by login");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return bean;
	}

	public List getRoles(UserBean bean) throws ApplicationException {
		log.debug("Model get Roles Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM st_user WHERE ROLE_ID=?");
		Connection conn = null;
		List list = new ArrayList();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, bean.getRoleId());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model get roles End");
		return list;
	}

	public long registerUser(UserBean bean) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");
		long pk = add(bean);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successful for ORS Project SunilOS");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		return pk;
	}

	public static boolean resetPassword(UserBean bean) {
		return false;
	}


	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {

		UserBean userData = new UserBean();
		System.out.println("Running till system");
		userData = findByLogin(login);
		System.out.println(userData.getId());
		boolean flag = false;

		if (userData == null) {
			throw new RecordNotFoundException("Email ID does not exists !");

		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(login);
		msg.setSubject("SUNRAYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		flag = true;

		return flag;
	}

	public UserBean authenticate(String login, String password) throws ApplicationException {

		log.debug("Model authenticate Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM st_user WHERE LOGIN = ? AND PASSWORD = ?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
				

			}
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("Exception:Exception in getting role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model authenticate End");
		return bean;
	}

	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {

		log.debug("model changePassword Started");
		boolean flag = false;
		UserBean beanExist = null;

		beanExist = findbypk(id);
		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
			beanExist.setPassword(newPassword);
			try {
				update(beanExist);
			} catch (DuplicateRecordException e) {
				log.error(e);
				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFirstName());
		map.put("lastName", beanExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(beanExist.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		log.debug("Model changePassword End");
		return flag;
	}
	}
/*
	public UserBean updateAccess(UserBean bean) throws ApplicationException {
		return null;
	}
}*/

	/*public boolean lock(String login) throws RecordNotFoundException, ApplicationException {

		log.debug("Service lock Started");
		boolean flag = false;
		UserBean beanExist = null;
		try {
			beanExist = findByLogin(login);
			if (beanExist != null) {
			
				update(beanExist);
				flag = true;
			} else {
				throw new RecordNotFoundException("LoginId not exist");
			}
		} catch (DuplicateRecordException e) {
			log.error("Application Exception..", e);
			throw new ApplicationException("Database Exception");
		}
		return flag;
	}
}*/