package db.project.exceptions;

import lombok.Getter;

@Getter
public class UserException extends  RuntimeException{
    private ErrorCode errorCode;

    public UserException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
