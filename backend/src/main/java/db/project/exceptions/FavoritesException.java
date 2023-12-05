package db.project.exceptions;

import lombok.Getter;

@Getter
public class FavoritesException extends RuntimeException{
    private ErrorCode errorCode;

    public FavoritesException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
