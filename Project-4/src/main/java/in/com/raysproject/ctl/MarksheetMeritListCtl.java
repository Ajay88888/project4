package in.com.raysproject.ctl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.com.raysproject.bean.BaseBean;
import in.com.raysproject.bean.MarksheetBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.model.MarksheetModel;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;

/**
 *  marksheetmerit list functionlity controller to show merit list student
 * @author Ajay
 *
 */

@WebServlet(name="MarksheetMeritListCtl",urlPatterns={"/ctl/MarksheetMeritListCtl"})
public class MarksheetMeritListCtl extends BaseCtl {

	private static Logger log=Logger.getLogger(MarksheetMeritListCtl.class);
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		System.out.println("populatebean of marksheet merit list");
		
		log.debug("Marksheet merit list populate bean start");
		MarksheetBean bean=new MarksheetBean();
		System.out.println("populated");
		return bean;
	}

	/**
     * Contains Display logics
     */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("MarksheetMeritListCtl doGet Start");
		System.out.println("doGet");
		
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		MarksheetBean bean=(MarksheetBean)populateBean(request);
		
//		String op=DataUtility.getString(request.getParameter("operation"));
//		long id = DataUtility.getLong(request.getParameter("id"));
		
		MarksheetModel model=new MarksheetModel();
		System.out.println("before try");
		List list=null;
		List next = null;
		try{
			
			
			list=model.getMeritList(pageNo, pageSize);
	        ServletUtility.setBean(bean, request);
	        if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			System.out.println("before view");
			ServletUtility.forward(ORSView.MARKSHEET_MERIT_LIST_VIEW, request, response);  //getView()
			System.out.println("after view");
		}catch(Exception e){
			log.error(e);
			ServletUtility.handleException(e, request, response);
			e.printStackTrace();
		}
		log.debug("MarksheeetMeritListCtl doGet End");
	}

	/**
     * Contains Submit logics
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("MarksheetMeritListCtl doGet Start");
		System.out.println("doPost");
		List list=null;
		
		int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo=(pageNo==0)?1:pageNo;
		pageSize=(pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
		
		MarksheetBean bean=(MarksheetBean)populateBean(request);
		
		String op=DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		
		MarksheetModel model=new MarksheetModel();
		
		try{
			if(OP_BACK.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
				return;
			}
			list=model.getMeritList(pageNo, pageSize);
			ServletUtility.setList(list, request);
			
			if(list==null||list.size()==0){
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(ORSView.MARKSHEET_MERIT_LIST_VIEW, request, response);
		}catch(ApplicationException e){
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("MarksheetMeritListCtl doPOst End");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;	
	}

}
