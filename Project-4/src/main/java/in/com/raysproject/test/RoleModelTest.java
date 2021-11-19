package in.com.raysproject.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import in.com.raysproject.bean.RoleBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.RoleModel;

public class RoleModelTest {
	public static RoleModel model = new RoleModel();

	public static void main(String[] args) throws Exception {
//		add(); 						//done
//		update(); 					//done
//		delete();					//done
		 findbypk();				//done
//		search();					//dones
//		findByName();			//done
//		list();							//done
	}

	public static void add() throws SQLException {
		try {
			RoleBean bean = new RoleBean();
			bean.setId(9);
			bean.setName("piyush");
			bean.setDescription("from hp");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("leader");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			RoleBean addedbean=model.findbypk(pk);
			if(addedbean==null){
				System.out.println("Test add fail");
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DuplicateRecordException e){
			e.printStackTrace();
		}
		
	}

	public static void update() throws Exception {
		
		try {
		RoleBean bean = model.findbypk(9L);
		bean.setName("karan");
		bean.setDescription("uk from");
		bean.setCreatedBy("me");
		bean.setModifiedBy("user");
		model.update(bean);
		  RoleBean updatedbean = model.findbypk(9L);
          if (!"karan".equals(updatedbean.getName())) {
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
			RoleBean bean = new RoleBean();
			long pk = 9L;
			bean.setId(pk);
			model.delete(bean);
			RoleBean deletedbean = model.findbypk(pk);
		  if (deletedbean != null) {
              System.out.println("Test Delete fail");
          }
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	private static void findbypk() throws Exception {
		try {
			RoleBean bean = new RoleBean();
			long pk = 1;
			RoleBean rolebean = model.findbypk(pk);
			if (bean == null) {
			System.out.println("Test Find By PK fail");
			}
			/*System.out.println(rolebean.getId());*/
			 System.out.println("DATA -->" + rolebean.toString());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
 
	  
	    
	public static void search() throws Exception {
		try {
		RoleBean bean = new RoleBean();
		List<RoleBean> list = new ArrayList<RoleBean>();
		list = model.search(bean, 0, 0);
		 if (list.size() < 0) {
             System.out.println("Test Serach fail");
         }
		Iterator<RoleBean> it = list.iterator();
		while (it.hasNext()) {
			RoleBean roleBean = (RoleBean) it.next();
			System.out.println(roleBean.getName());
			System.out.println(roleBean.getDescription());
		  }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

	
	public static void list() {

    try {
        RoleBean bean = new RoleBean();
        List list = new ArrayList();
        list = model.list(1, 10);
        if (list.size() < 0) {
            System.out.println("Test list fail");
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            bean = (RoleBean) it.next();
            System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getDescription());
        }

    } catch (ApplicationException e) {
        e.printStackTrace();
    }
}

	 public static void findByName() {
	        try {
	            RoleBean bean = new RoleBean();
	            bean = model.findByName("admin");
	            if (bean == null) {
	                System.out.println("Test Find By PK fail");
	            }
	            System.out.println(bean.getId());
	            System.out.println(bean.getName());
	            System.out.println(bean.getDescription());
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	   }
}
