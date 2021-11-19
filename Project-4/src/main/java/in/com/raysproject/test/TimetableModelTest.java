package in.com.raysproject.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.raysproject.bean.TimetableBean;
import in.com.raysproject.bean.TimetableBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.TimetableModel;

public class TimetableModelTest {
	public static TimetableModel model = new TimetableModel();

	public static void main(String[] args) throws Exception {
		// add();			
		 update();
		// delete();
		// findbypk();
		//	findByName();
		// nextpk();
		// search();
		// list();
	}

	public static void add() throws Exception {	
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = simpleDateFormat.parse("02/04/1999");
		TimetableBean bean = new TimetableBean();
		bean.setId(0);
		bean.setCourseId(3);
		bean.setCourseName("");
		bean.setSubjectId(3);
		bean.setSubjectName("");
		bean.setSemester("2");
		bean.setExamTime("1:00 - 3:00");
		bean.setExamDate(date);
		bean.setCreatedBy("user");
		bean.setModifiedBy("user");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk =model.add(bean);
		TimetableBean addedBean =model.findbypk(pk);
		if(addedBean==null){
			System.out.println("fail to add");
			}
			}catch(ApplicationException e){
			e.printStackTrace();
			}
		}

	private static void list() throws Exception {
		try {
		TimetableBean bean = new TimetableBean();
		List list = new ArrayList();
		list = model.list(0, 0);
		if (list.size() < 0) {
			System.out.println("Test list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (TimetableBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getCourseName());
		}
		}catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void search() throws Exception {
		try {
		TimetableBean bean = new TimetableBean();
		List list = new ArrayList();
		bean.setCourseName("ma");
		// bean.setId(2);
		list = model.search(bean, 1, 10);
		if (list.size() < 0) {
			System.out.println("Test Serach fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (TimetableBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getCourseName());
		}
		} catch (ApplicationException e) {
		e.printStackTrace();
		}
		}

	private static void nextpk() throws Exception {
		long pk = model.nextPK();
		System.out.println("Next PK->" + pk);
	}

	private static void findbypk() throws Exception {
		try {
		TimetableBean bean = new TimetableBean();
		long pk =1L;
		bean=model.findbypk(1);
		if(bean==null){
			System.out.println("Test Find By Pk fail");
		}
		
		System.out.println("DATA -->" + bean.toString());
	}catch (ApplicationException e) {
		e.printStackTrace();
	}
}

	public static void findByName() {
		try{
			TimetableBean bean=model.findByName("Maths");
			if(bean==null){
				System.out.println("Test Find By Name Fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getSemester());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
			
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	
	
	public static void update() throws Exception {
		try {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = simpleDateFormat.parse("08/05/2020");
		TimetableBean bean = model.findbypk(2L);
		bean.setCourseId(3);
		bean.setCourseName("");
		bean.setSubjectId(3);
		bean.setSubjectName("");
		bean.setSemester("3");
		bean.setExamTime("2:00 - 5:00");
		bean.setExamDate(date);
		bean.setId(1);
		model.update(bean);
		TimetableBean updateBean=model.findbypk(2L);
		if(!"Maths".equals(updateBean.getSubjectName())){
			System.out.println("Test Update fail");
		}
	}catch(ApplicationException e){
		e.printStackTrace();
	}catch(DuplicateRecordException e){
		e.printStackTrace();
	}
}
	
	public static void delete() throws Exception {
		TimetableBean bean = new TimetableBean();
		try {
		
		bean.setId(1);
		model.delete(bean);
		TimetableBean deletedBean = model.findbypk(1);
		if (deletedBean != null) {
		System.out.println("Test Delete fail");
		}

		}catch (ApplicationException e){
		e.printStackTrace();
		}
	}
}