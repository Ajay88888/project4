package in.com.raysproject.ctl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.com.raysproject.bean.BaseBean;
import in.com.raysproject.bean.TimetableBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.model.CourseModel;
import in.com.raysproject.model.SubjectModel;
import in.com.raysproject.model.TimetableModel;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;


/**
 * Timetabel functionality controller.to perform search and show list operation
 * @author Ajay
 *
 */

@WebServlet(name="TimetableListCtl",urlPatterns={"/ctl/TimetableListCtl"})
public class TimetableListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(TimetableListCtl.class);
	
	protected void preload(HttpServletRequest request) {
		CourseModel model=new CourseModel();
		SubjectModel smodel=new SubjectModel();
		try{
			List list=model.list();
			List list1=smodel.list();
			request.setAttribute("subjectList", list1);
			request.setAttribute("courseList", list);
		}catch(Exception e){
			log.error(e);
		}
	}
	protected BaseBean populateBean(HttpServletRequest request){
		 
		System.out.println("done");
		 log.debug("TimetableCtl Method populatebean Started");
		 TimetableBean bean=new TimetableBean();
		 
		 bean.setId(DataUtility.getLong(request.getParameter("id")));
//		 bean.setCourseName(request.getParameter("courseName"));
		 bean.setCourseId(DataUtility.getInt(request.getParameter("courseId")));
		 bean.setSubjectId(DataUtility.getInt(request.getParameter("subjectId")));
		 bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		 bean.setExamTime(DataUtility.getString(request.getParameter("examId")));
		 bean.setSemester(DataUtility.getString(request.getParameter("semesterId")));
		 
		 populateDTO(bean,request);
		 log.debug("TimetableListCtl Method populatebean Ended");
		 return bean;
		 
	 }
	
	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	
		System.out.println("doget method");
		log.debug("Subjectlist do get start");
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		TimetableBean bean=(TimetableBean)populateBean(request);
		TimetableModel model=new TimetableModel();
		List list;
		List next;
		
		try{
			list=model.search(bean,pageNo,pageSize);
			next=model.search(bean, pageNo + 1, pageSize);
			
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

			
		}catch(ApplicationException e){
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Timetablelist do get end");
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		
		log.debug("Time table ctl doPost Start");
		System.out.println("Hello inside post");
		List list = null;
		List next = null;
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		TimetableBean bean = (TimetableBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		System.out.println("jmmmmm"+op);
		
		// get the selected checkbox ids array for delete list
		
		String[] ids = request.getParameterValues("ids");
		
		TimetableModel model = new TimetableModel();
		
		System.out.println("qqqqqqq");
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					TimetableBean deletebean = new TimetableBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
				 
			}
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;
			} 
			bean = (TimetableBean)populateBean(request);
			System.out.println("in dopost of timetablelist"+bean.getCourseId()+"ww"+bean.getSubjectId());
			list = model.search(bean, pageNo, pageSize);
			
			ServletUtility.setBean(bean, request);

			next = model.search(bean, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Time table ctl doGet End");
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_LIST_VIEW;
	}

	
}
