package in.com.raysproject.ctl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.com.raysproject.bean.BaseBean;
import in.com.raysproject.bean.RoleBean;
import in.com.raysproject.bean.UserBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.UserModel;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.DataValidator;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;

/**
 * User registration functionality Controller. Performs operation for User
 * 
 * @author Ajay
 *
 */

@WebServlet(name="UserRegistrationCtl",urlPatterns={"/UserRegistrationCtl"})
public class UserRegistrationCtl extends BaseCtl {

public static final String OP_SIGN_UP="SignUp";
	
	private static Logger log=Logger.getLogger(UserRegistrationCtl.class);
	
	protected boolean validate(HttpServletRequest request){
		
		System.out.println("in validate method");
		log.debug("UserRegistrationCtl Method Validate Started");
		boolean pass=true;
		String login=request.getParameter("login");
		String dob=request.getParameter("dob");
		
		if(DataValidator.isNull(request.getParameter("firstName"))){
			request.setAttribute("firstName",PropertyReader.getValue("error.require","First Name"));
			pass=false;	
		}else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "please enter correct Name");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("lastName"))){
			request.setAttribute("lastName",PropertyReader.getValue("error.require","Last Name"));
			pass=false;
			
		}else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "please enter correct Name");
			pass = false;
		}
		if(DataValidator.isNull(login)){
			request.setAttribute("login",PropertyReader.getValue("error.require","Login Id"));
			pass=false;
		}else if(!DataValidator.isEmail(login)){
			request.setAttribute("login",PropertyReader.getValue("error.require","Login"));
			pass=false;
			
		}if(DataValidator.isNull(request.getParameter("password"))){
			request.setAttribute("password",PropertyReader.getValue("error.require","Password"));
			pass=false;
		}else if (!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password", "Password should be 8 to 12 characters");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Password Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("confirmPassword"))){
			request.setAttribute("confirmPassword",PropertyReader.getValue("error.require","Confirm Password"));
			pass=false;
			
		}
		if(DataValidator.isNull(request.getParameter("gender"))){
			request.setAttribute("gender",PropertyReader.getValue("error.require","Gender"));
			pass=false;
			
		}if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobile"))) {
			request.setAttribute("mobile", "Please Enter Valid Mobile Number");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("dob"))){
			request.setAttribute("dob",PropertyReader.getValue("error.require","Date Of Birth"));
			pass=false;
			
		}
		else if(!DataValidator.isDate(dob)){
			request.setAttribute("dob",PropertyReader.getValue("error.date","Date of Birth"));
			pass=false;
		}else if (!DataValidator.isValidAge(dob)) {
			request.setAttribute("dob", "Age Must be greater then 18 year");
			pass = false;
		}
		if(!request.getParameter("password").equals(request.getParameter("confirmPassword"))&&!"".equals(request.getParameter("confirmPassword"))){
			ServletUtility.setErrorMessage("Confirm password not matched", request);
			pass=false;
		
		}
			log.debug("UserRegistratioCtl Method validate Ended");
			return pass;
	}
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		log.debug("UserRegistrationCtl Method populatebean Started");
		System.out.println("in populate method");
		UserBean bean=new UserBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRoleId(RoleBean.STUDENT);
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		
		populateDTO(bean,request);
		log.debug("UserRegistrationCtl Method populatebean Ended");
		return bean;
	}
	
	/**
	 * Display concept of user registration
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		log.debug("UserRegistrationCtl Method doGet Started");
		System.out.println("doGet method  of User Registrationctl");
		ServletUtility.forward(getView(),request,response);
	}
	
	/**
	 * Submit concept of user registration
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		System.out.println("in post method ");
		log.debug("UserRegistrationCtl Method doPost Started");
		
		String op=DataUtility.getString(request.getParameter("operation"));
				
		UserModel model=new UserModel();
		
//		long id=DataUtility.getLong(request.getParameter("id"));
		
		if(OP_SIGN_UP.equalsIgnoreCase(op)){
		
			UserBean bean=(UserBean)populateBean(request);
			
			try{
			
				long pk=model.registerUser(bean);
				bean.setId(pk);
				System.out.println("in post method OP_sign_Up");
				/*request.getSession().setAttribute("UserBean",bean);*/
				
				ServletUtility.setSuccessMessage("Registration done successfully", request);
				
//				ServletUtility.forward(ORSView.LOGIN_CTL, request, response);
//				ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
						
					}catch(DuplicateRecordException e){
						log.error(e);
						System.out.println("in post method DuplicateRecordException e");
						ServletUtility.setBean(bean, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
//						ServletUtility.forward(getView(), request, response);
					}catch (ApplicationException e) {
						log.error(e);
						System.out.println("in post method applicationException e");
						ServletUtility.handleException(e, request, response);
						return;
					}
//					ServletUtility.setSuccessMessage("Registration done successfully", request);
//					ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
//					ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
					System.out.println("in post method of if block....");
					
				}else if (OP_RESET.equalsIgnoreCase(op)) {
					
					System.out.println("in post method OP_ReSet........");
					ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
					return;
				}
		
				ServletUtility.forward(getView(), request, response);
				log.debug("UserRegistrationCtl Method doPost Ended");
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		System.out.println("getview of User Registration ctl");
		return ORSView.USER_REGISTRATION_VIEW;
	}

}
