package db.project.exceptions;

import lombok.Getter;

@Getter
public class TicketException extends RuntimeException{
    private ErrorCode errorCode;

    public TicketException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
