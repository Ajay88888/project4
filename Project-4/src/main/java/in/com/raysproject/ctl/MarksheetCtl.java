package in.com.raysproject.ctl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.com.raysproject.bean.BaseBean;
import in.com.raysproject.bean.MarksheetBean;
import in.com.raysproject.exception.ApplicationException;
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.MarksheetModel;
import in.com.raysproject.model.StudentModel;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.DataValidator;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;



/**
 * marksheeet functionality controller.to perform add,delete and update operation
 * @author Ajay
 *
 */

@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {


	private static Logger log = Logger.getLogger(MarksheetCtl.class);


	protected void preload(HttpServletRequest request) {
		System.out.println("preload method in MarksheetCtl");

		StudentModel model = new StudentModel();
		try {
			List l = model.list();
			request.setAttribute("studentList", l);
		} catch (ApplicationException e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	
	protected boolean validate(HttpServletRequest request) {
		System.out.println("validate method in MarksheetCtl");
		log.debug("MarksheetCtl Method Validate Started");

		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
			System.out.println("111111111111111111" + pass);
		} else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", "Please Enter Valid Roll No");
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
			System.out.println("111111111111111112" + pass);
		}
		if (DataValidator.isNull(request.getParameter("physics"))){
			request.setAttribute("physics", PropertyReader.getValue("error.require", "physics marks "));
			pass=false;
			System.out.println("1111111111PPP" + pass);
		}
		else if ( !DataValidator.isInteger(request.getParameter("physics"))) {
				request.setAttribute("physics", PropertyReader.getValue("error.integer", "marks"));
				pass = false;
				System.out.println("111111111111111113" + pass);
				}
				else if(DataUtility.getInt(request.getParameter("physics")) > 100 ) {
				request.setAttribute("physics", "marks can't be greater than 100");
				pass=false;
				}
				/*else if(DataUtility.getInt(request.getParameter("physics")) == 0) {
					request.setAttribute("physics", "marks can't be zero");
					pass=false;
				}*/
				else if(DataUtility.getInt(request.getParameter("physics")) < 0) {
					request.setAttribute("physics", "marks can't be less than 0");
					pass=false;
				}else if( !DataValidator.isInteger(request.getParameter("physics"))) {
					request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
				pass=false;
			}
		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "chemistry marks"));
			pass = false;
			System.out.println("111111111111111115" + pass);
		}
		else if(DataUtility.getInt(request.getParameter("chemistry")) > 100 ) {
			request.setAttribute("chemistry", "marks can't be greater than 100");
			pass=false;
			}
		/*else if(DataUtility.getInt(request.getParameter("chemistry")) == 0) {
			request.setAttribute("chemistry", "marks can't be zero");
			pass=false;
		}*/
		else if(DataUtility.getInt(request.getParameter("chemistry")) < 0) {
			request.setAttribute("chemistry", "marks can't be less than 0");
			pass=false;
			}else if( !DataValidator.isInteger(request.getParameter("chemistry"))) {
				request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
				pass=false;
		}
		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "maths marks "));
			pass = false;
			System.out.println("111111111111111114" + pass);
		}
		else if(DataUtility.getInt(request.getParameter("maths")) > 100 ) {
			request.setAttribute("maths", "marks can't be greater than 100");
			pass=false;
			}
			else if(DataUtility.getInt(request.getParameter("maths")) < 0) {
			request.setAttribute("maths", "marks can't be less than 0");
			pass=false;
			/*}else if(DataUtility.getInt(request.getParameter("maths")) == 0) {
				request.setAttribute("maths", "marks can't be zero");
				pass=false;*/
			}else if( !DataValidator.isInteger(request.getParameter("maths"))) {
				request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
				pass=false;
		}
		return pass;
	}
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("populatebean method in marksheetCtl");
		log.debug("MarksheetCtl Method populatebean Started");

		MarksheetBean bean = new MarksheetBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		
		System.out.println("populate " + bean.getStudentId());
		bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

		populateDTO(bean, request);
		System.out.println("populate done");
		log.debug("MarksheetCtl Method populatebean Ended");
		return bean;
	}

	/**
	 * Display Logics inside this method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("MarksheetCtl doGet Started");
		System.out.println("doget method in MarksheetCtl");

		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			MarksheetBean bean;
			try {
				bean = model.findbypk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug(" MarksheetCtl method doGet Ended");
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("MarksheetCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("-------------------------------------- dopost "+op);
		
		if(OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)){
			
			System.out.println("++++++++++++++++++++++ in do post (operation)="+op);
			
			MarksheetBean bean=(MarksheetBean) populateBean(request);
			System.out.println(bean.getId()+""+bean.getName()+""+bean.getStudentId());
			try{
				if(id>0){
					model.update(bean);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
					ServletUtility.setBean(bean, request);
				}else{
					
					long pk=model.add(bean);
					System.out.println("++++++++++++++++++++++ in do post (pk)="+pk);
//					bean.setId(pk);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
			}catch(ApplicationException e){
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}catch(DuplicateRecordException e){
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
			}
			}else if(OP_CANCEL.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			}else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
				return;
			}
			ServletUtility.forward(getView(), request, response);
			log.debug("MarksheetCtl Method doPost Ended");
			}

	@Override
	protected String getView() {
		System.out.println("marksheetctl veiw method");
		return ORSView.MARKSHEET_VIEW;
	}
}
