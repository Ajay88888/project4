package in.com.raysproject.exception;

/**
 * ApplicationException is propogated from Service classes when an business
 * logic exception occurered.
 * @author Ajay
 *
 */

public class ApplicationException extends Exception {
	   public ApplicationException(String msg) {
	        super(msg);
	    }
	}

