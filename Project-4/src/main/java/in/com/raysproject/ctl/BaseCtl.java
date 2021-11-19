package in.com.raysproject.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.com.raysproject.bean.BaseBean;
import in.com.raysproject.bean.UserBean;
import in.com.raysproject.util.DataUtility;
import in.com.raysproject.util.DataValidator;
import in.com.raysproject.util.ServletUtility;

/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 * @author Ajay
 *
 */


public abstract class BaseCtl extends HttpServlet {
	
	public static final String OP_SAVE = "Save";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_LOG_OUT = "Logout";
	
	public static final String OP_RESET="Reset";
	public static final String OP_UPDATE="Update";
	public static final String OP_CHANGE_MY_PROFILE = "MyProfile";

	public static final String MSG_SUCCESS = "success";
	public static final String MSG_ERROR = "error";

	protected boolean validate(HttpServletRequest request) {
		System.out.println("validate method in BaseCtl");
		return true;
	}

	protected void preload(HttpServletRequest request) {
		System.out.println("basectl preload method");
	}

	protected BaseBean populateBean(HttpServletRequest request) {
		return null;
	}

	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {
		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;
		
		UserBean userbean = (UserBean) request.getSession().getAttribute("user");
		
		if (userbean == null) {
			createdBy = "root";
			modifiedBy = "root";
			
		} else {
			modifiedBy = userbean.getLogin();
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}

		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);
		long cdt = DataUtility.getLong(request.getParameter("createdDateTime"));
		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}
		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());
		return dto;

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("1");
		preload(request);
		System.out.println("2");
		String op = DataUtility.getString(request.getParameter("operation"));
		/*System.out.println("BaseCtl service op " + op);*/
		System.out.println("3");
		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op)&&!OP_RESET.equalsIgnoreCase(op)) {
			System.out.println("V");
			if (!validate(request)) {
				BaseBean bean = (BaseBean) populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}
		System.out.println("4");
		super.service(request, response);
		System.out.println("5");
	}

	protected abstract String getView();

}
