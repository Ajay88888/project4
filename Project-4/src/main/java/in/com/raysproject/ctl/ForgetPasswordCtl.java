package in.com.raysproject.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.com.raysproject.bean.BaseBean;
import in.com.raysproject.bean.UserBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.RecordNotFoundException;
import in.com.raysproject.model.UserModel;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.DataValidator;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;


/**
 * forget password ctl.To perform password send in email
 * @author Ajay
 *
 */
@WebServlet(name="ForgetPasswordCtl",urlPatterns={"/ForgetPasswordCtl"})
public class ForgetPasswordCtl extends BaseCtl{

	public static Logger log=Logger.getLogger(ForgetPasswordCtl.class);
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ForgetPasswordCtl Method Validate Started");
		
		boolean pass=true;
		String login=request.getParameter("login");
		
		if(DataValidator.isNull(login)){
			request.setAttribute("login",PropertyReader.getValue("error.require","Email Id"));
			pass=false;
		}else if(!DataValidator.isEmail(login)){
			request.setAttribute("login",PropertyReader.getValue("error.email","login"));
			pass=false;
		}
		log.debug("ForgetPasswordCtl Method validate Ended");
		
		return pass;
	}


	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ForgetPasswordCtl method populatedBean Started");
		
		UserBean bean=new UserBean();
		
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		
		log.debug("ForgetPasswordCtl method populate bean Ended");
		
		return  bean;
	}

	/**
	 * Display Concept are there
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("ForgetPasswordCtl method doGet Started");
		
		ServletUtility.forward(getView(), request, response);
	}
	
	/**
	 * Submit Concepts
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		log.debug("ForgetPasswordCtl method doPost Started");
		
		String op=DataUtility.getString(request.getParameter("operation"));
		
		UserBean bean=(UserBean)populateBean(request);
		
		UserModel model=new UserModel();
		
		if(OP_GO.equalsIgnoreCase(op)){
			
			try{
				 model.forgetPassword(bean.getLogin());
				ServletUtility.setSuccessMessage("Password has been sent to your Email Id", request);
			}catch(RecordNotFoundException e){
				ServletUtility.setErrorMessage(e.getMessage(), request);
				log.error(e);
			}catch(ApplicationException e){
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbb");
				log.error(e.getMessage());
				ServletUtility.handleException(e, request, response);
				return;
			}
			ServletUtility.forward(getView(), request, response);
		}
		log.debug("ForgetPasswordCtl Method doPost End");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FORGET_PASSWORD_VIEW;
	}
}
