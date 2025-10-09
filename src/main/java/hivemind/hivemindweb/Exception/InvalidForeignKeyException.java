package hivemind.hivemindweb.Exception;

import java.sql.SQLException;

public class InvalidForeignKeyException extends SQLException {
    //Update later for must meths for DEBUG
    public InvalidForeignKeyException(String message){
        super(message);
    }

    public InvalidForeignKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
