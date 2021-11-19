package in.com.raysproject.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import in.com.raysproject.bean.BaseBean;
import in.com.raysproject.bean.FacultyBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.CollegeModel;
import in.com.raysproject.model.CourseModel;
import in.com.raysproject.model.FacultyModel;
import in.com.raysproject.model.SubjectModel;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.DataValidator;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;


/**
 * faculty functionality ctl.To perform add,delete and update operation
 * @author Ajay
 *
 */

@WebServlet(name = "FacultyCtl", urlPatterns={"/ctl/FacultyCtl"})
public class FacultyCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(FacultyCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		try {
			CollegeModel model = new CollegeModel();
			CourseModel model1 = new CourseModel();
			SubjectModel model2 = new SubjectModel();

			List l = model.list();
			List li = model1.list();
			List list = model2.list();
			request.setAttribute("collegeList", l);
			request.setAttribute("courseList", li);
			request.setAttribute("subjectList", list);
			System.out.println("............" + l + ".." + li + ".." + list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("FacultyCtl Method validate Started");

		boolean pass=true;
		String email = request.getParameter("email");
		
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
			System.out.println("1" +pass);
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "please enter correct Name");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
			System.out.println("2" +pass);
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "please enter correct Name");
			pass = false;
		}
		
			if (DataValidator.isNull(request.getParameter("gender"))) {
				request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
				pass = false;
				System.out.println("3" +pass);
			}
			
		if (DataValidator.isNull(request.getParameter("dateOfJoining"))) {
			request.setAttribute("dateOfJoining", PropertyReader.getValue("error.require", "date Of Joining"));
			pass = false;
			System.out.println("6" +pass);
			System.out.println(request.getParameter("dateOfJoining"));
		} else if (!DataValidator.isDate(request.getParameter("dateOfJoining"))) {
			request.setAttribute("dateOfJoining", PropertyReader.getValue("error.date", "date Of Joining"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("qualification"))) {
			request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
			pass = false;
			System.out.println("7" +pass);
		}
		
		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
			System.out.println("8" +pass);
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.Email", "LoginId"));
		}	
		
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "mobile No"));
			pass = false;
			System.out.println("8" +pass);
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "collegeId"));
			pass = false;
			System.out.println("9" +pass);
		}
				
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "course Name"));
			pass = false;
			System.out.println("11" +pass);
		}
		

		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "subject Name"));
			pass = false;
			System.out.println("13" +pass);
		}
				
		log.debug("FacultyCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("FacultyCtl Method populatebean Started");
		FacultyBean bean = new FacultyBean();

			bean.setId(DataUtility.getLong(request.getParameter("id")));
			System.out.println("successfully get id 11111");
			
			bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
			System.out.println("successfully get firstname 22222");
			
			bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
			System.out.println("successfully get Lastname 33333");
			
			bean.setGender(DataUtility.getString(request.getParameter("gender")));
			System.out.println("successfully get gender 44444");
			
			bean.setLoginId(DataUtility.getString(request.getParameter("login")));
			System.out.println("successfully get Login id 55555");
			
			String date = request.getParameter("dateOfJoining");
			System.out.println(request.getParameter("dateOfJoining"));
			System.out.println("date is :"+date);
			
			bean.setDateOfJoining(DataUtility.getDate(request.getParameter("dateOfJoining")));
			bean.setQualification(DataUtility.getString(request.getParameter("qualification")));
			System.out.println("successfully get qualification 66666");
			
			bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
			System.out.println("successfully get mobileno 77777");
			
			bean.setCollegeId(DataUtility.getInt(request.getParameter("collegeId")));
			System.out.println("successfully get ciollege id 88888");
			
			bean.setCollegeName(DataUtility.getString(request.getParameter("collegeName")));
			System.out.println("successfully get collegeName 99999");
			
			bean.setCourseId(DataUtility.getInt(request.getParameter("courseId")));
			System.out.println("successfully get course id 10,10");
			
			bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
			System.out.println("successfully get course name 20,20");
			
			bean.setSubjectId(DataUtility.getInt(request.getParameter("subjectId")));
			System.out.println("successfully get subject id 30,30");
			
			bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
			System.out.println("successfully get subject name 40,40");
			
			populateDTO(bean, request);
			log.debug("FacultyCtl Method populatebean Ended");
			return bean;
	}


	/**
	 * Display Logics inside this method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("FacultyCtl do get Start");

		String op=DataUtility.getString(request.getParameter("operation"));
		
		FacultyModel model=new FacultyModel();
		
		long id=DataUtility.getLong(request.getParameter("id"));
		
		if(id>0||op!=null){
			FacultyBean bean;
			 try{
				 bean=model.findbypk(id);
				 ServletUtility.setBean(bean, request);
				 System.out.println("id=========="+bean.getId());
				 
			 }catch(ApplicationException e){
				 e.printStackTrace();
				 log.error(e);
				 ServletUtility.handleException(e, request,response);
				 return;
				 
			 }
		 }
		 ServletUtility.forward(getView(), request, response);
		 log.debug("Faculty Ctl do get end");
	 }	

	/**
	 * Submit logic inside it
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("FacultyCtl Method doPost Started");
		 System.out.println("in dommpost of facultyctl");
		 String op=DataUtility.getString(request.getParameter("operation"));
		 
		 FacultyModel model=new FacultyModel();
		 long id=DataUtility.getLong(request.getParameter("id"));
		 if(OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)){
			 FacultyBean bean=(FacultyBean)populateBean(request);
			 
			 try{
				 if(id>0){
					 System.out.println("We are in Update "+bean.getId());
					 model.update(bean);
					 bean.setId(id);
					// ServletUtility.setBean(bean, request);
					 
					 ServletUtility.setSuccessMessage("Data Successfully Update", request);
					
					// ServletUtility.forward(getView(), request, response);

				 }else{
					 long pk;
					 try {
						 System.out.println("in dopost of faculty ctl update method"+bean);
						 pk=model.add(bean);
							ServletUtility.setBean(bean, request);
						 System.out.println("in dopost of faculty ctl add method"+pk);
						 bean=null;
						 
						ServletUtility.setSuccessMessage("Data Successfully saved",request);
					
						
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setBean(bean, request);
						ServletUtility.setErrorMessage("Faculty already exists", request);
					}					 
					 
				 }
			 }catch(ApplicationException e){
				 e.printStackTrace();
				 log.error(e);
				 ServletUtility.handleException(e, request, response);
				 return;
			 }catch(DuplicateRecordException e){
				 ServletUtility.setBean(bean, request);
				 ServletUtility.getErrorMessage("Faculty Id already exists",request);
				 
			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else if(OP_DELETE.equalsIgnoreCase(op)){
			 
			 FacultyBean bean=(FacultyBean)populateBean(request);
			 try{
				 model.delete(bean);
				 ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				 return;
				 
			 }catch(ApplicationException e){
				 log.error(e);
				 e.printStackTrace();
				 ServletUtility.handleException(e, request, response);
				 return;
			 }
			 
		 }else if(OP_CANCEL.equalsIgnoreCase(op)){
			 
			 ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			 return;
		 }else if(OP_RESET.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;
				
			}
		 ServletUtility.forward(getView(), request, response);
		 log.debug("FacultyCtl Method doPost Ended");
	 }


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_VIEW;
	}

}
