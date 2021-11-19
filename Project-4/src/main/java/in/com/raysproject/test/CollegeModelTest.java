package in.com.raysproject.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import in.com.raysproject.bean.CollegeBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.CollegeModel;

public class CollegeModelTest {
	public static CollegeModel model = new CollegeModel();

	public static void main(String[] args) throws Exception {
		 //add(); 					//done
		// update();				//done
		// delete();				//done
		 findbypk();				//done
		// findbyName(); 		//done
		// list();  					//done
		//search();				//done
	}

	public static void add() throws Exception {
		try {
			CollegeBean bean = new CollegeBean();
			//bean.setId(17);
			bean.setName("ankush");
			bean.setAddress("high school");
			bean.setState("gj");
			bean.setCity("nmch");
			bean.setPhoneNo("987778462");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("aksh");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			System.out.println("add tested ");
			CollegeBean addedBean = model.findbypk(pk);
			if (addedBean == null) {
				System.out.println("fail to add");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void update() {
		try {
			CollegeBean bean = model.findbypk(5L);
			bean.setName("rahul");
			bean.setAddress("soutch tukoganj");
			bean.setState("up");
			bean.setCity("agra");
			bean.setPhoneNo("54545621");
			bean.setCreatedBy("ajay");
			bean.setModifiedBy("ankush");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			bean.setId(5);
			model.update(bean);
			System.out.println("Test Update success");
			CollegeBean updateBean = model.findbypk(5L);
			if (!"rahul".equals(updateBean.getName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void delete() throws Exception {
		try {
			
			CollegeBean bean = new CollegeBean();
			bean.setId(17);
			model.delete(bean);
			System.out.println("test delete success");
			CollegeBean deletedBean = model.findbypk(5);
			if (deletedBean != null) {
				System.out.println("test delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	


	public static void findbypk() {
		try {
			CollegeBean bean = new CollegeBean();
			// bean.setId(1);
			long pk = 5L;
			bean = model.findbypk(5);
			if (bean == null) {
				System.out.println("test Find By Pk fail");
			} else System.out.println("Data-->" + bean.toString());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void findbyName() throws Exception {
		try {
			CollegeBean bean = new CollegeBean();
			bean = model.findbyName("lucky");
			if (bean == null) {
				System.out.println("TEST FIND BY NAME FAIL");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void list() {
	try {
		
		CollegeBean bean = new CollegeBean();
		List list = new ArrayList();
			list = model.list(0, 0);
			if (list.size() < 0) {
				System.out.println("test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void search() throws Exception{
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			bean.setName("lucky");
		//	bean.setAddress("soutch tukoganj");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("TEST SEARCH FAIL");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}
