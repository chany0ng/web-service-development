package db.project.exceptions;

import lombok.Getter;

@Getter
public class BreakdownReportException extends RuntimeException{
    private ErrorCode errorCode;

    public BreakdownReportException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
