package in.com.raysproject.ctl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.com.raysproject.bean.BaseBean;
import in.com.raysproject.bean.StudentBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.model.CollegeModel;
import in.com.raysproject.model.StudentModel;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;

/**
 * student functionality controller,to perform list and search operation
 * @author Ajay
 *
 */

@WebServlet(name="StudentListCtl",urlPatterns={"/ctl/StudentListCtl"})
public class StudentListCtl extends BaseCtl {

	private static Logger log=Logger.getLogger(StudentListCtl.class);

	protected void preload(HttpServletRequest request) {
		CollegeModel model=new CollegeModel();
		try {
			List list=model.list();
			request.setAttribute("collegeList", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		StudentBean bean=new StudentBean();
		//System.out.println("?????????????????????????????++++ StudentListCtl populate fname"+bean.getFirstName());
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		System.out.println("?????????????????????????????++++ StudentListCtl populate firstname"+bean.getFirstName());
		
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmailId(DataUtility.getString(request.getParameter("email")));
		
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
		System.out.println("??????????????????????????? populate collegeId" + " " + bean.getCollegeId());
		
		bean.setCollegeName(DataUtility.getString(request.getParameter("collegeName")));
		
		populateDTO(bean, request);
		return bean;
	}
	
	/**
     * Contains Display logics
     */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		log.debug("StudentListCtl Method do get Started");
		List list;
		List next;
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		/*int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize=DataUtility.getInt(request.getParameter("pageSize"));*/
		
		/*pageNo=(pageNo==0)?1:pageNo;
		pageSize=(pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;*/
		
		StudentBean bean=(StudentBean)populateBean(request);
		StudentModel model=new StudentModel();
		try {
			list = model.search(bean, pageNo, pageSize);
			//ServletUtility.setBean(bean, request);
			next = model.search(bean, pageNo+1, pageSize);
			/*} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
			}*/

			if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
			}if (next == null || next.size() == 0) {
			request.setAttribute("nextListSize", 0);

			} else {
			request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("StudentListCtl doGet End");
	}

	/**
     * Contains Submit logics
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		log.debug("StudentListCtl Method doPost Started");
		
		List list;
		List next;
		
		int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
		String op=DataUtility.getString(request.getParameter("operation"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		StudentBean bean=(StudentBean)populateBean(request);
		StudentModel model=new StudentModel();
		
		String[] ids=request.getParameterValues("ids");
		
		try{
			if(OP_SEARCH.equalsIgnoreCase(op)||OP_NEXT.equalsIgnoreCase(op)||OP_PREVIOUS.equalsIgnoreCase(op)){
				
				if(OP_SEARCH.equalsIgnoreCase(op)){
					pageNo=1;
				}else if(OP_NEXT.equalsIgnoreCase(op)){
					pageNo++;	
				}else if(OP_PREVIOUS.equalsIgnoreCase(op)&&pageNo>1){
					pageNo--;
				}
			}else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.STUDENT_CTL, request,
                        response);
                return;
            } else if (OP_RESET.equalsIgnoreCase(op)) {

    			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
    			return;
    		}else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;
			}
			else if(OP_NEXT.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
				return;
			}else if(OP_DELETE.equalsIgnoreCase(op)){
				
				System.out.println("11111111111111111111111111111 StudentList Delete");
				pageNo=1;
			if(ids!=null&&ids.length>0){
				StudentBean deletebean=new StudentBean();
				for(String id:ids){
					deletebean.setId(DataUtility.getInt(id));
					model.delete(deletebean);
					ServletUtility.setSuccessMessage("Data Delete Successfully", request);
				}
			}else{
				ServletUtility.setErrorMessage("Select at leat one record", request);
			}
		}
		bean = (StudentBean) populateBean(request);	
		list=model.search(bean, pageNo, pageSize);
		ServletUtility.setBean(bean, request);
		next = model.search(bean, pageNo+1, pageSize);
        ServletUtility.setList(list, request);
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)){
			ServletUtility.setErrorMessage("No record found", request);
		}if(next==null||next.size()==0){
			request.setAttribute("nextListSize", 0);
			
		}else{
			request.setAttribute("nextListSize", next.size());
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request,response);
	
	}catch(ApplicationException e){
		log.error(e);
	
		ServletUtility.handleException(e, request, response);
	return;
	}

		log.debug("StudentListCtl doPost end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_LIST_VIEW;
	}

}
