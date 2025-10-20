package hivemind.hivemindweb.Exception;

public class InvalidPrimaryKeyException extends RuntimeException {

    // PostgreSQL codes for primary key / constraint violations
    private static final String SQL_STATE = "23505"; // 23505 = unique constraint violation
    private static final int ERROR_CODE = 23505;

    private final String sqlState;
    private final int errorCode;

    public InvalidPrimaryKeyException(String message) {
        super(message);
        this.sqlState = SQL_STATE;
        this.errorCode = ERROR_CODE;
    }

    public InvalidPrimaryKeyException(String message, Throwable cause) {
        super(message, cause);
        this.sqlState = SQL_STATE;
        this.errorCode = ERROR_CODE;
    }

    public String getSQLState() {
        return sqlState;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return String.format(
            "InvalidPrimaryKeyException{message='%s', sqlState='%s', errorCode=%d}",
            getMessage(), sqlState, errorCode
        );
    }
}
