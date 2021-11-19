package in.com.raysproject.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import in.com.raysproject.bean.CourseBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.CourseModel;

public class CourseModelTest {

	public static CourseModel model = new CourseModel();

	public static void main(String[] args) throws Exception {

		 //testAdd();							//done
		// testDelete();
		// testUpdate();						//done
		//testFindByName(); 				//done
		// testFindByPK(); 					//done
	//	 testSearch(); 						//done
		// testList(); 							//done
	}

	public static void testAdd() throws Exception {
		try {
			CourseBean bean = new CourseBean();

			bean.setId(3);
			bean.setCourseName("agriculture");
			bean.setDescription("Bachelor");
			bean.setDuration("4 year");
			bean.setCreatedBy("user2");
			bean.setModifiedBy("user2");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			System.out.println("successfully tested");
			CourseBean addedBean = model.findbypk(pk);
			if (addedBean == null) {
				System.out.println("fail to add");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		try {
			CourseBean bean = new CourseBean();
			// long pk=9L;
			bean.setId(2);
			model.delete(bean);
			System.out.println("Test Delete success");
			CourseBean deletedBean = model.findbypk(2);

			if (deletedBean != null) {
				System.out.println("Test Delete fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {
			CourseBean bean = model.findbypk(1L);
			bean.setCourseName("MCA");
			model.update(bean);
			System.out.println("Test Update success");
			CourseBean updateBean = model.findbypk(1L);
			if (!"MCA".equals(updateBean.getCourseName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByName() {
		try {
			CourseBean bean = model.findByName("MCA");
			if (bean == null) {
				System.out.println("Test Find By Name Fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 1L;
			bean = model.findbypk(1);
			if (bean == null) {
				System.out.println("Test Find By Pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			bean.setCourseName("Bcom");
		//	bean.setId(2);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {

		try {
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
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
