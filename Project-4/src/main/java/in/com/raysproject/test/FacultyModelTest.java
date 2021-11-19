package in.com.raysproject.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.raysproject.bean.CollegeBean;
import in.com.raysproject.bean.FacultyBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.FacultyModel;

public class FacultyModelTest {
	public static FacultyModel model = new FacultyModel();

	public static void main(String[] args) throws Exception {
		//add();				//done
		 update();			//done
		// delete();
		// findbypk();		//done
		// search();			//done
		// list();					//done
		// findbyName();

	}

public static void add() throws DuplicateRecordException, ParseException {
		
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = simpleDateFormat.parse("04/09/1995");
		FacultyBean bean = new FacultyBean();
		bean.setId(14);
		bean.setFirstName("satish");
		bean.setLastName("jain");
		bean.setGender("Male");
		bean.setLoginId("satish09@gmail.com");
		bean.setDateOfJoining(date);
		bean.setQualification("blockchain tech");
		bean.setMobileNo("9845123097");
		bean.setCollegeId(8);
		bean.setCollegeName("ips");
		bean.setCourseId(6);
		bean.setCourseName("Mcom");
		bean.setSubjectId(6);
		bean.setSubjectName("engineering in physics");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("user");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk=model.add(bean);
		System.out.println("add tested ");
		FacultyBean addedBean=model.findbypk(pk);
		System.out.println(addedBean);
		if(addedBean==null){
		System.out.println("fail to add");
		}
		}catch(ApplicationException e){
		e.printStackTrace();
		}
		}
	

public static void update() throws Exception {
	try {
		FacultyBean bean=model.findbypk(2L);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	Date date = simpleDateFormat.parse("05/03/2000");
	bean.setFirstName("krishna");
	bean.setLastName("jain");
	bean.setGender("Male");
	bean.setLoginId("krushna119@gmail.com");
	bean.setDateOfJoining(date);
	bean.setQualification("mca clear");
	bean.setMobileNo("9579560978");
	bean.setCollegeId(2);
	bean.setCollegeName("Acropolis");
	bean.setCourseId(5);
	bean.setCourseName("mba");
	bean.setSubjectId(3);
	bean.setSubjectName("bca");
	bean.setCreatedBy("admin");
	bean.setModifiedBy("user");
	bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
	bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	//bean.setId(2);
	
	model.update(bean);
	FacultyBean updateBean=model.findbypk(2L);
	if(!"krishna".equals(updateBean.getFirstName())){
		System.out.println("Test Update fail");
	}
}catch(ApplicationException e){
	e.printStackTrace();
}catch(DuplicateRecordException e){
	e.printStackTrace();
}
}


public static void delete() throws Exception {
	FacultyBean bean = new FacultyBean();
	try {
	bean.setId(1);
	model.delete(bean);
	System.out.println("Test delets success");
	FacultyBean deletedBean = model.findbypk(1);
	if (deletedBean != null) {
	System.out.println("Test Delete fail");
	}

	}catch (ApplicationException e){
	e.printStackTrace();
	}
}

public static void findbypk() throws Exception {
	try {
	FacultyBean bean = new FacultyBean();
	long pk;
	bean=model.findbypk(1);
	/*FacultyBean facultyBean = model.findbypk(bean);*/
	System.out.println("DATA -->" + bean.toString());
	}catch(ApplicationException e){
		e.printStackTrace();
	}
}

public static void search() throws Exception {
	try {
		
	FacultyBean bean = new FacultyBean();
	List list = new ArrayList();
	//bean.setFirstName("sumit");
	// bean.setLastName("malhara");
	bean.setLoginId("madhur10@gmail.com");
	bean.setCollegeName("Lovely P.U.");
	list = model.search(bean, 0, 0);
	if (list.size() < 0) {
		System.out.println("Test Serach fail");
	}
	Iterator it = list.iterator();
	while (it.hasNext()) {
		bean = (FacultyBean) it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getGender());
		System.out.println(bean.getLoginId());
		System.out.println(bean.getDateOfJoining());
		System.out.println(bean.getQualification());
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getCollegeId());
		System.out.println(bean.getCollegeName());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getSubjectId());
		System.out.println(bean.getSubjectName());
		
	}
	} catch (ApplicationException e) {
	e.printStackTrace();
		}
	}


public static void list() throws Exception {
	try {
	FacultyBean bean = new FacultyBean();
	List list = new ArrayList();
	list = model.list(0, 10);
	if (list.size() < 0) {
		System.out.println("Test list fail");
	}
	Iterator it = list.iterator();
	while (it.hasNext()) {
		bean = (FacultyBean) it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
	}
	} catch (ApplicationException e) {
		e.printStackTrace();
		}
}

	private static void findbyName() throws Exception {
		try {
			
		FacultyBean bean = new FacultyBean();
		bean = model.findbyName("sumit");
		if (bean == null) {
			System.out.println("TEST FIND BY NAME FAIL");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
	}catch (ApplicationException e)  {
		e.printStackTrace();
	}
	}

	

	/*private static void nextpk() throws Exception {
		long pk = model.nextPK();
		System.out.println("Next PK->" + pk);
	}*/

	

}
