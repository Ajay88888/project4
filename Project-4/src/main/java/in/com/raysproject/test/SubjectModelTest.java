package in.com.raysproject.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import in.com.raysproject.bean.SubjectBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.SubjectModel;

public class SubjectModelTest {
	
public static SubjectModel model=new SubjectModel();
	
	public static void main(String[] args) throws Exception {

//	testAdd();							//done
//		testDelete();					//done
//		testUpdate();				//done
//		testFindByName();		//done
//		testFindByPK();   			//done
		testSearch();   				//done
//		testList();    
	}

	public static void testAdd() throws Exception{
		try{
			SubjectBean bean=new SubjectBean();
			
			bean.setId(4);
			bean.setSubjectName("finance");
			bean.setDescription("Bachelor");
			bean.setCourseName("Bcom");
			bean.setCourseId(3);
			bean.setSubjectId(104);
			bean.setCreatedBy("user");
			bean.setModifiedBy("user");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk=model.add(bean);
			System.out.println("add tested successfully");
			SubjectBean addedBean=model.findbypk(pk);
			if(addedBean==null){
				System.out.println("fail to add");
			}
		}catch(ApplicationException e){
				e.printStackTrace();
			}
	}
	public static void testDelete(){
		try{
			SubjectBean bean=new SubjectBean();
//			long pk=9L;
			bean.setId(3);
			model.delete(bean);
			System.out.println("Test Delete success");
			SubjectBean deletedBean=model.findbypk(3);
			
			if(deletedBean!=null){
				System.out.println("Test Delete fail");
			}
			
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	public static void testUpdate(){
		try{
			SubjectBean bean=model.findbypk(2L);
			bean.setSubjectName("maths");
			model.update(bean);
			System.out.println("Test Update success");
			SubjectBean updateBean=model.findbypk(2L);
			if(!"maths".equals(updateBean.getSubjectName())){
				System.out.println("Test Update fail");
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DuplicateRecordException e){
			e.printStackTrace();
		}
	}
	
	public static void testFindByName() {
		try{
			SubjectBean bean=model.findByName("maths");
			if(bean==null){
				System.out.println("Test Find By Name Fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	public static void testFindByPK(){
		try{
			SubjectBean bean=new SubjectBean();
			long pk=2;
			bean=model.findbypk(pk);
			if(bean==null){
				System.out.println("Test Find By Pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			
		
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	public static void testSearch() {
		try {
		SubjectBean bean = new SubjectBean();
		List list = new ArrayList();
		bean.setSubjectName("account");
		list = model.search(bean, 0, 0);
		if (list.size() < 0) {
		System.out.println("Test Search fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
		bean = (SubjectBean) it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		}
		} catch (ApplicationException e) {
		e.printStackTrace();
		}
		}

		public static void testList() {

		try {
		SubjectBean bean = new SubjectBean();
		List list = new ArrayList();
		list = model.list(1, 10);
		if (list.size() < 0) {
		System.out.println("Test list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
		bean = (SubjectBean) it.next();
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
