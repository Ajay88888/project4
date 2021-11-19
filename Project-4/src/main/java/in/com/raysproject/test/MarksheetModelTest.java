package in.com.raysproject.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.raysproject.bean.MarksheetBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.MarksheetModel;

public class MarksheetModelTest {
	public static MarksheetModel model = new MarksheetModel();

	public static void main(String[] args) throws Exception {
		// add();							
		// update();						//done
		 delete();
		// findbypk();					//done
		// nextpk();
		//search();						
		// list();
		//meritList();

	}

	private static void add() {

		try {
			MarksheetBean bean = new MarksheetBean();
			bean.setId(14);
			bean.setStudentId(15);
			bean.setName("deepak");
			bean.setPhysics(50);
			bean.setChemistry(35);
			bean.setMaths(68);
			bean.setRollNo("AK1767");
			bean.setCreatedBy("jfbr");
			bean.setModifiedBy("djkds");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);

			MarksheetBean addedBean = model.findbypk(pk);
			if (addedBean==null) {
				System.out.println("Test add fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();

		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
	private static void update() throws Exception {
		try {
		MarksheetBean bean = new MarksheetBean();
		bean.setId(3);
		bean.setRollNo("201");
		bean.setStudentId(7);
		//bean.setName("pushpendra");
		bean.setPhysics(40);
		bean.setChemistry(88);
		bean.setMaths(99);
		model.update(bean);
		MarksheetBean updateBean=model.findbypk(4L);
		System.out.println("Test update successfull");	
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DuplicateRecordException e){
			e.printStackTrace();
		}
	}

	
	public static void list() throws Exception {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			list = model.list(1, 6);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getStudentId());
				System.out.println(bean.getName());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void search() throws Exception {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			bean.setName("rahul");

			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getStudentId());
				System.out.println(bean.getName());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/*
	 * private static void nextpk() throws Exception { long pk = model.nextPK();
	 * System.out.println("Next PK->" + pk); }
	 */
	
	
	public static void findbypk() throws Exception {
		try {
		MarksheetBean bean = new MarksheetBean();
		long pk=2L;
		bean=model.findbypk(pk);
	
		if(bean==null){
			System.out.println("find by pk fail");
		}else System.out.println("DATA -->" + bean.toString());
		}catch(ApplicationException e){
			e.printStackTrace();
		}
		
	}


	
	private static void delete() throws Exception {
		try {
		MarksheetBean bean = new MarksheetBean();
		long pk=1L;
		bean.setId(pk);
		model.delete(bean);

		MarksheetBean deletedBean=model.findbypk(pk);
		if(deletedBean!=null){
			System.out.println("Test Delete Fail");
			
		}
		
	}catch(ApplicationException e){
		e.printStackTrace();
	}
}
public static void meritList(){
		
		try{
			MarksheetBean bean=new MarksheetBean();
			List list=new ArrayList();
			list=model.getMeritList(1, 4);
			if(list.size()<0){
				System.out.println("Test list fail");
			}
			Iterator it=list.iterator();
			while(it.hasNext()){
				bean= (MarksheetBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
				
		}	
			}catch(ApplicationException e){
				e.printStackTrace();
			}
	}
	}