package in.com.raysproject.exception;

/**
 *  RecordNotFoundException thrown when a record not found occurred
 * @author Ajay
 *
 */

public class RecordNotFoundException extends Exception {
	public RecordNotFoundException(String msg) {
        super(msg);

	}
}
