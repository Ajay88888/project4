package in.com.raysproject.exception;

/**
 * DuplicateRecordException thrown when a duplicate record occurred
 * @author Ajay
 *
 */


public class DuplicateRecordException extends Exception {
    public DuplicateRecordException(String msg) {
        super(msg);
    }
}
