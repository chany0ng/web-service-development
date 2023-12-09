package db.project.exceptions;

import lombok.Getter;

@Getter
public class LocationException extends RuntimeException{
    private ErrorCode errorCode;

    public LocationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
