package db.project.exceptions;

import lombok.Getter;

@Getter
public class NoticeException extends RuntimeException{
    private ErrorCode errorCode;

    public NoticeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
