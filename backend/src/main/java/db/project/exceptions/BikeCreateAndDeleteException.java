package db.project.exceptions;

import lombok.Getter;

@Getter
public class BikeCreateAndDeleteException extends RuntimeException{
    private ErrorCode errorCode;

    public BikeCreateAndDeleteException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
