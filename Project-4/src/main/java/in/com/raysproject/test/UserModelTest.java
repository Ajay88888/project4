package in.com.raysproject.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import in.com.raysproject.bean.UserBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.exception.RecordNotFoundException;
import in.com.raysproject.model.UserModel;

public class UserModelTest {

	public static UserModel model = new UserModel();

	public static void main(String[] args) throws Exception {
		 //add(); 				//done
		// update(); 			//done
		// delete(); 				//done
		// findbypk();
		// nextpk();
		// search();				//done
		 list();
		// FindByLogin();
		// RegisterUser();
		// getRoles();
		// authenticate();
		// changePassword();
		// forgetPassword();
		// resetPassword();

	}
	
	public static void add() throws ParseException, DuplicateRecordException {
		try {
			UserBean bean = new UserBean();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

			Date date = simpleDateFormat.parse("05/07/1998");

			bean.setId(10);
			bean.setFirstName("brijesh");
			bean.setLastName("raam");
			bean.setLogin("brijesh12@gmail.com");
			bean.setPassword("ak@12");
			bean.setConfirmPassword("ak@12");
			bean.setDob(date);
			bean.setMobileNo("7878454588");
			bean.setRoleId(4L);
			bean.setGender("male");
			bean.setCreatedBy("shhh");
			bean.setModifiedBy("piy");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			UserBean addedbean = model.findbypk(pk);
			System.out.println("Test add success");
			if (addedbean == null) {
				System.out.println("Test add fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void update() throws Exception {
		try {
			//UserBean bean = model.findbypk(8);
			UserBean bean =  new UserBean();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = simpleDateFormat.parse("08/08/1998");

			bean.setFirstName("digmabar");
			bean.setLastName("hemsworth");
			bean.setLogin("digabar@gmail.com");
			bean.setPassword("ch45@12");
			bean.setConfirmPassword("ch45@12");
			bean.setDob(date);
			bean.setMobileNo("8784654588");
			bean.setRoleId(4L);
			bean.setGender("male");
			bean.setCreatedBy("ritesh");
			bean.setModifiedBy("ansh");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			bean.setId(8);
			
			UserBean updatebean = model.findbypk(8L);
			model.update(bean);
			if ("firstname".equals(updatebean.getLogin())) {
				System.out.println("test update fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}


	private static void forgetPassword() throws Exception {
		try {
			boolean b = model.forgetPassword("ayushparmar1230@gmail.com");

			System.out.println("Suucess : Test Forget Password Success"+b);

		} catch (RecordNotFoundException s) {
			s.printStackTrace();
		} catch (ApplicationException s) {
			s.printStackTrace();
		}
	}

	private static void changePassword() throws Exception {
		UserBean bean = model.findByLogin("ranjitchoudhary20@gmail.com");
		String oldPassword = bean.getPassword();
		bean.setId(15l);
		bean.setPassword("88");
		bean.setConfirmPassword("kk");
		String newPassword = bean.getPassword();
		try {
			model.changePassword(15L, oldPassword, newPassword);
			System.out.println("password has been change successfully");
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void authenticate() throws Exception {
		UserBean bean = new UserBean();
		bean.setLogin("kumar@g.com");
		bean.setPassword("rr");
		bean = model.authenticate(bean.getLogin(), bean.getPassword());
		if (bean != null) {
			System.out.println("Successfully login");

		} else {
			System.out.println("Invalied login Id & password");
		}

	}

	private static void getRoles() throws Exception {
		UserBean bean = new UserBean();
		List list = new ArrayList();
		bean.setRoleId(2L);
		list = model.getRoles(bean);
		if (list.size() < 0) {
			System.out.println("Test Get Roles fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (UserBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getConfirmPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
		
		}
	}

	private static void RegisterUser() throws Exception {
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		bean.setId(4);
		bean.setFirstName("vaibhav");
		bean.setLastName("jain");
		bean.setLogin("vaibhvath10u@gmail.com");
		bean.setPassword("aswe23");
		bean.setConfirmPassword("aswe23");
		bean.setDob(sdf.parse("11/20/2015"));
		bean.setMobileNo("9895969497");
		bean.setGender("Male");
		bean.setRoleId(2);
		bean.setCreatedBy("chris");
		bean.setModifiedBy("sunilsahu");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.registerUser(bean);
		System.out.println("Successfully register");
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLogin());
		System.out.println(bean.getLastName());
		System.out.println(bean.getDob());
		UserBean registerbean = model.findbypk(pk);
		if (registerbean != null) {
			System.out.println("Test registation fail");
		}
	}

	private static void FindByLogin() throws Exception {
		UserBean bean = new UserBean();
		bean = model.findByLogin("ayushparmar1230@gmail.com");
		if (bean == null) {
			System.out.println("Test Find By PK fail");
		}
		/*System.out.println(bean.getId());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLogin());
		System.out.println(bean.getPassword());
		System.out.println(bean.getDob());
		System.out.println(bean.getRoleId());
		System.out.println(bean.getGender());*/
		System.out.println("DATA -->" + bean.toString());
	}

	private static void list() throws Exception {
		UserBean bean = new UserBean();
		List list = new ArrayList();
		list = model.list(0, 0);
		if (list.size() < 0) {
			System.out.println("Test list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (UserBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
			System.out.println("DATA -->" + bean.toString());
			
		}

	}

	private static void search() throws Exception {
		UserBean bean = new UserBean();
		List list = new ArrayList();
		bean.setFirstName("chris");
		// bean.setId(1);
		list = model.search(bean, 0, 0);
		if (list.size() < 0) {
			System.out.println("Test Serach fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (UserBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
		}
	}

	private static void nextpk() throws Exception {
		long pk = model.nextPK();
		System.out.println("Next PK->" + pk);

	}

	

	
	public static void delete() throws Exception {
		try {
			UserBean bean = new UserBean();

			bean.setId(5);
			model.delete(bean);
			System.out.println("Test delete success" + bean.getId());
			UserBean deletedbean = model.findbypk(5);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void findbypk() throws Exception {
		try {
			UserBean bean = model.findbypk(4);
		if (bean == null)
		System.out.println("test findbypk fail");
		
		System.out.println(bean.getId());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLogin());
		System.out.println(bean.getPassword());
		System.out.println(bean.getConfirmPassword());
		System.out.println(bean.getDob());
		System.out.println(bean.getRoleId());
		System.out.println(bean.getGender());
		
		System.out.println("DATA -->" + bean.toString());

		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	}
