package db.project.exceptions;

import lombok.Getter;

@Getter
public class SurchargeException extends RuntimeException{
    private ErrorCode errorCode;

    public SurchargeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
