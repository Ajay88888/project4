package in.com.raysproject.ctl;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.com.raysproject.bean.BaseBean;
import in.com.raysproject.bean.RoleBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.RoleModel;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.DataValidator;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;


/**
 * role functionality controller.to perform add,delete ,update operation
 * @author Ajay
 *
 */

@WebServlet (name = "RoleCtl", urlPatterns={"/ctl/RoleCtl"})
public class RoleCtl extends BaseCtl{
	private static final long serialVersionUID=1L;

	private static Logger log=Logger.getLogger(RoleCtl.class);
	protected boolean validate(HttpServletRequest request){
		log.debug("RoleCtl Method validate Started");
		
		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("name"))){
			request.setAttribute("name",PropertyReader.getValue("error.require","Name"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("description"))){
			request.setAttribute("description",PropertyReader.getValue("error.require","Description"));
			pass=false;
		}
		 log.debug("RoleCtl Method validate Ended");
		 return pass;
	}
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		log.debug("RoleCtl method populateBean started");
		
		RoleBean bean=new RoleBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(bean,request);
		log.debug("RoleCtl Method populatebean Ended");
		return bean;
	}
	
	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		log.debug("RoleCtl Method doGet Started");
		
		System.out.println("in do get");
		
		String op=DataUtility.getString(request.getParameter("operation"));
		
		RoleModel model=new RoleModel();
		
		long id=DataUtility.getLong(request.getParameter("id"));
		if(id>0 || op!=null){
			RoleBean bean;
			try{
				bean=model.findbypk(id);
				ServletUtility.setBean(bean,request);
			}catch(ApplicationException e){
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("RoleCtl Method doGetEnded");
	}
	
	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		log.debug("RoleCtl Method doGet Started");
		
		System.out.println("to do post");
	
	String op=DataUtility.getString(request.getParameter("operation"));
	
	RoleModel model=new RoleModel();
	long id=DataUtility.getLong(request.getParameter("id"));
	
	 
		if (OP_SAVE.equalsIgnoreCase(op)|| OP_UPDATE.equalsIgnoreCase(op)) {

	            RoleBean bean = (RoleBean) populateBean(request);

	            try {
	                if (id > 0) {
	                    model.update(bean);
	                    ServletUtility.setSuccessMessage("Data is successfully Update", request);
	                } else {
	                   try {
	                	     long pk = model.add(bean);
//	                         bean.setId(pk);
	                   }catch (ApplicationException e) {
							log.error(e);
							ServletUtility.handleException(e, request, response);
							return;
						} catch (DuplicateRecordException e) {
							ServletUtility.setBean(bean, request);
							ServletUtility.setErrorMessage("Role already exists", request);
						}
	                   
	                
	                }

	                ServletUtility.setBean(bean, request);
	                ServletUtility.setSuccessMessage("Data is successfully saved", request);

	            } catch (ApplicationException e) {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            } catch (DuplicateRecordException e) {
	                ServletUtility.setBean(bean, request);
	                ServletUtility.setErrorMessage("Role already exists", request);
	            } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        }
			
		else if (OP_CANCEL.equalsIgnoreCase(op)) {

	            ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
	            return;

	        }else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
				return;

			}
		
	        ServletUtility.forward(getView(), request, response);

	        log.debug("RoleCtl Method doPOst Ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_VIEW;
	}
}
	

