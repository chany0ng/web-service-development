package db.project.exceptions;

import lombok.Getter;

@Getter
public class MapException extends RuntimeException{
    private ErrorCode errorCode;

    public MapException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
