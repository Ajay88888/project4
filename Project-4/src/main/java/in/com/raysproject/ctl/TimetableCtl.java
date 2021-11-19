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
import in.com.raysproject.exception.DuplicateRecordException;
import in.com.raysproject.model.CourseModel;
import in.com.raysproject.model.SubjectModel;
import in.com.raysproject.model.TimetableModel;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.DataValidator;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;


/**
 * Timetable functionality controller. to perform add,delete and update
 * operation
 * @author Ajay
 *
 */
@WebServlet (name = "TimetableCtl", urlPatterns = { "/ctl/TimetableCtl" })
public class TimetableCtl extends BaseCtl{

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(TimetableCtl.class);
	
	protected void preload(HttpServletRequest request) {
		CourseModel model = new CourseModel();
		SubjectModel model2 = new SubjectModel();
		try {
			List list = model.list();
			List list1 = model2.list();
			
			request.setAttribute("courseList", list);
			System.out.println("SUCCESSFULLY PRINT COURSELIST" +list);
			
			request.setAttribute("subjectList", list1);
			System.out.println("SUCCESSFULLY PRINT SUBJECTLIST" + list1);
		}catch(ApplicationException e) {
			log.error(e);
			e.printStackTrace();
		}
		
	}
	
	protected boolean validate(HttpServletRequest request) {
		System.out.println("ENTER IN VALIDATE MATHOD");
		log.debug("TimetableCtl Method validate Started");
		 boolean pass = true;
		 
		 String examDate = request.getParameter("examDate");
		
			if (DataValidator.isNull(request.getParameter("courseId"))) {
				request.setAttribute("courseId", PropertyReader.getValue("error.require", "course Name"));
				pass = false;
				System.out.println("SUCCESSFULLY GET COURSE NAME");
			}
			if (DataValidator.isNull(request.getParameter("subjectId"))) {
				request.setAttribute("subjectId", PropertyReader.getValue("error.require", "subject Name"));
				pass = false;
				System.out.println("SUCCESSFULLY GET SUBJECT NAME"+request.getParameter("subjectId"));
			}
			if (DataValidator.isNull(request.getParameter("semesterId"))) {
				request.setAttribute("semesterId", PropertyReader.getValue("error.require", "semester"));
				pass = false;
				System.out.println("SUCCESSFULY GET SEMESTER"+request.getParameter("semesterId"));
			}
			if (DataValidator.isNull(examDate)) {
				request.setAttribute("examDate", PropertyReader.getValue("error.require", "Exam Date"));
				pass = false;
			} else if (!DataValidator.isDate(examDate)) {
				request.setAttribute("examDate", PropertyReader.getValue("error.date", "Exam Date"));
				pass = false;
				System.out.println("SUCCESSFULLY GET EXAM DATE"+examDate);
			}
			if (DataValidator.isNull(request.getParameter("examId"))) {
				request.setAttribute("examId", PropertyReader.getValue("error.require", "exam Time"));
				pass = false;
				System.out.println("SUCCESSFULLY GET EXAM TIME"+request.getParameter("examId"));
			}

		log.debug("TimetableCtl Method validate Ended");
		return pass;
	}
	
	protected BaseBean populateBean(HttpServletRequest request) {
	
		log.debug("TimetableCtl Method populatebean Started");
		TimetableBean bean = new TimetableBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSubjectName(DataUtility.getString(request.getParameter("subName")));
		//bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		bean.setSemester(DataUtility.getString(request.getParameter("semesterId")));
		bean.setCourseId(DataUtility.getInt(request.getParameter("courseId")));
		bean.setSubjectId(DataUtility.getInt(request.getParameter("subjectId")));
		bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		bean.setExamTime(DataUtility.getString(request.getParameter("examId")));
		//bean.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(bean, request);
		log.debug("TimeTableCtl Method populatebean Ended");
		return bean;
	}
	
	/**
	 * Display Logics inside this method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException{
	
		log.debug("TimeTablectl do get started");
		String op = DataUtility.getString(request.getParameter("operation"));
	
		TimetableModel model = new TimetableModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (id > 0 || op != null) {
			TimetableBean bean;
			try {
				bean = model.findbypk(id);
				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;

			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("Timetablectl do get end");
	}
	
	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		log.debug("TimetableCtl Method doPost Started");
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		// get model
		TimetableModel model = new TimetableModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (OP_SAVE.equalsIgnoreCase(op)) {
			System.out.println("successfully entered in save method");
			TimetableBean bean = (TimetableBean) populateBean(request);

			TimetableBean bean1;
			TimetableBean bean2;
			TimetableBean bean3;
			
			try {
				bean1 = model.checkByCourseName(bean.getCourseId(), bean.getExamDate());

				bean2 = model.checkBySubjectName(bean.getCourseId(), bean.getSubjectId(), bean.getExamDate());

				bean3 = model.checkBysemester(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
						bean.getExamDate());
				
				if (bean1 == null && bean2 == null && bean3 == null) {

					long pk = model.add(bean);
					bean.setId(pk);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				} else {
					bean = (TimetableBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Exam already exist!", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Exam already exist!", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
				System.out.println("111111111");
			TimetableBean bean = (TimetableBean) populateBean(request);
			System.out.println("1111112222");
			TimetableBean bean4;

			try {

				bean4 = model.checkByExamTime(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
						bean.getExamDate(), bean.getExamTime());
				System.out.println("111133333");

				if (id > 0 && bean4 == null) {
					System.out.println("11114444");
					model.update(bean);
					System.out.println("1111555555");
					ServletUtility.setBean(bean, request);
					System.out.println("1116666666");
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
				} else {
					System.out.println("11177777");
					bean = (TimetableBean) populateBean(request);
					System.out.println("111188888");
					ServletUtility.setBean(bean, request);
					System.out.println("1111999999");
					ServletUtility.setErrorMessage("Exam already exist!", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				System.out.println("111112111");
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println("11111133311");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Exam already exist!", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			TimetableBean bean = (TimetableBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("TimetableCtl Method doPost Ended");
		
	}
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_VIEW;
	}

}
