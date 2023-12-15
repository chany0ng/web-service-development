package db.project.exceptions;

import lombok.Getter;

@Getter
public class BoardCommentException extends RuntimeException{
    private ErrorCode errorCode;

    public BoardCommentException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
