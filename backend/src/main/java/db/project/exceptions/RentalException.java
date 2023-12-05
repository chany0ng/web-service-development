package db.project.exceptions;

import lombok.Getter;

@Getter
public class RentalException extends RuntimeException{
    private ErrorCode errorCode;

    public RentalException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
