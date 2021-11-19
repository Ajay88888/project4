package in.com.raysproject.ctl;

import java.io.IOException;

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
import in.com.raysproject.util.DataValidator;
import in.com.raysproject.util.PropertyReader;
import in.com.raysproject.util.ServletUtility;


/**
 * get marksheet functionality ctl.to perform  get marksheet opeation
 * @author Ajay
 *
 */

@WebServlet(name="GetMarksheetCtl",urlPatterns={"/ctl/GetMarksheetCtl"})
public class GetMarksheetCtl extends BaseCtl {

	private static Logger log=Logger.getLogger(GetMarksheetCtl.class);
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("validate method of getMarksheetCtl");
		log.debug("GetMarksheetCtl method validate Started");
		
		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("rollNo"))){
			request.setAttribute("rollNo",PropertyReader.getValue("error.require", "Roll Number"));
			pass=false;
		}
		
		log.debug("GetMarksheetCtl method validate End");
		
		return pass;
	}
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		System.out.println("populate method of getMarksheetCtl");
		log.debug("GetMarksheetCtl method populatedBean Started");
		
		MarksheetBean bean=new MarksheetBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		
		log.debug("GetMarksheetCtl method populatedbean End");
		return bean;

	}

	/**
	 * Concept of Display method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("doget method of getMarksheetCtl");
		log.debug("GetMarksheetCtl method doGet Started");
		
		ServletUtility.forward(getView(), request, response);
		
	}
	
	/**
     * Concept of submit method
     *
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost method of getMarksheetCtl");
		log.debug("GetMarksheetCtl method doPost Started");
		
		String op=DataUtility.getString(request.getParameter("operation"));
		
		
		MarksheetBean bean=(MarksheetBean) populateBean(request);
		
		MarksheetModel model=new MarksheetModel();
		
//		long id=DataUtility.getLong(request.getParameter("id"));
		if(OP_GO.equalsIgnoreCase(op)){
			
			try{
				bean=model.findByRollNo(bean.getRollNo());
				if(bean!=null){
					ServletUtility.setBean(bean, request);
				}else{
					ServletUtility.setErrorMessage("RollNo Does Not exist", request);
					
				}
			}catch(ApplicationException e){
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetCtl Method doGet End");
	
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.GET_MARKSHEET_VIEW;
	}

}
