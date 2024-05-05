package dev.vk.jfc.app.storage.appstorage.rest;

import java.util.function.Supplier;

public class JfcException extends RuntimeException {
    public JfcException() {
    }

    public JfcException(String message) {
        super(message);
    }

    public JfcException(String message, Throwable cause) {
        super(message, cause);
    }

    public JfcException(Throwable cause) {
        super(cause);
    }

    public JfcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
