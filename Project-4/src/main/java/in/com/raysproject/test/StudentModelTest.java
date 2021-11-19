package in.com.raysproject.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import in.com.raysproject.bean.StudentBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.StudentModel;

public class StudentModelTest {
	public static StudentModel model = new StudentModel();
	
	public static void main(String[] args) throws Exception {
				// add();
				// update();
				// delete();
				// findbypk();
				// nextpk();
				// findByEmailId();
				search();								//done
			//	list();										//done
		
				try {
					search();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// list();

			}

	public static void add() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

		StudentBean bean = new StudentBean();
		try {

			bean.setId(7);
			bean.setFirstName("vijay");
			bean.setLastName("jain");
			bean.setDob(sdf.parse("01/05/2000"));
			bean.setMobileNo("9858854896");
			bean.setEmailId("vijay12@gmail.com");
			bean.setCollegeId(13);
			bean.setCollegeName("");
			bean.setCreatedBy("ankush");
			bean.setModifiedBy("nishant");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			StudentBean addedbean = model.findbypk(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();

		} catch (DuplicateRecordException ex) {
			ex.printStackTrace();
		}
	}

	private static void list() {
		try {
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			list = model.list(0, 0);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmailId());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void search() throws Exception {
		try {
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			//bean.setFirstName("ankit");
			//bean.setLastName("malhara");
			bean.setCollegeName("NIT");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmailId());
				System.out.println(bean.getCollegeId());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void findByEmailId() throws Exception {
		try {
			StudentBean bean = new StudentBean();
			bean = model.findByEmailId("rahul@gmail.com");
			if (bean != null) {
				System.out.println("find by emailid failed");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getCollegeId());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void nextpk() throws Exception {
		long pk = model.nextPK();
		System.out.println("Next PK->" + pk);
	}

	private static void findbypk() throws Exception {
		StudentBean bean = new StudentBean();
		bean.setId(1);
		StudentBean Studentbean = model.findbypk(1l);
		System.out.println("DATA -->" + Studentbean.toString());
	}

	public static void update() throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = simpleDateFormat.parse("05/02/1998");
			StudentBean bean = model.findbypk(1L);
			bean.setFirstName("pushpendra");
			bean.setLastName("malhara");
			bean.setDob(date);
			bean.setMobileNo("15997896");
			bean.setEmailId("ankit@gmail.com");
			bean.setCollegeId(4878);
			bean.setCollegeName("mu");
			bean.setCreatedBy("akshat");
			bean.setModifiedBy("sunil sahu");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			bean.setId(6);
			model.update(bean);
			StudentBean updatebean = model.findbypk(1L);

			if (!"ankit".equals(updatebean.getFirstName())) {
				System.out.println("Test update fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException ex) {
			ex.printStackTrace();
		}
	}

	private static void delete() throws Exception {
		try {
			StudentBean bean = new StudentBean();
			long pk = 5L;
			bean.setId(pk);
			model.delete(bean);
			StudentBean deletedbean = model.findbypk(pk);
			if (deletedbean != null) {
				System.out.println("Test delete fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
}


		
