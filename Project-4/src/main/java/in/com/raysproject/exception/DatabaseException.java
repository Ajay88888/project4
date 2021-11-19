package in.com.raysproject.exception;


/**
 * DatabaseException is prpogated by DAO classes when an unhandled Database
 * exception occurred
 * @author Ajay
 *
 */

public class DatabaseException extends Exception {
    public DatabaseException(String msg) {
        super(msg);
    }
}

