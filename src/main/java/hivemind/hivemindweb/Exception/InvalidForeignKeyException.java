package hivemind.hivemindweb.Exception;

import java.sql.SQLException;

public class InvalidForeignKeyException extends SQLException {
    private static final String SQL_STATE = "23503";
    private static final int ERROR_CODE = 23503;
    // SQLState and ErrorCode for PostgreSQL foreign key violations
    // 23503 = standard code for "foreign key constraint violation"


    //Update later for must meths for DEBUG
    public InvalidForeignKeyException(String message){
        super(message,SQL_STATE, ERROR_CODE);
    }

    public InvalidForeignKeyException(String message, Throwable cause) {
        super(message, SQL_STATE, ERROR_CODE, cause);
    }

    @Override
    public String toString() {
        return String.format(
            "InvalidForeignKeyException{message='%s', sqlState='%s', errorCode=%d}",
            getMessage(), getSQLState(), getErrorCode()
        );
    }
}
