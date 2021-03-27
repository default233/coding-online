package com.chen.biz.exception;

/**
 * @author danger
 * @date 2021/3/25
 */
public class BadArgumentException extends CustomException{
    public BadArgumentException() {
        super();
    }

    public BadArgumentException(String message) {
        super(message);
    }

    public BadArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadArgumentException(Throwable cause) {
        super(cause);
    }

    public BadArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

