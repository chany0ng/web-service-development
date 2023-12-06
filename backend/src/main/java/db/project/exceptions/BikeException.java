package db.project.exceptions;

import lombok.Getter;

@Getter
public class BikeException extends RuntimeException{
    private ErrorCode errorCode;

    public BikeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
