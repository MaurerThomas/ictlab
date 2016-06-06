package hro.ictlab.dashboard.exception;

public class FailToConnectException extends Exception {

    public FailToConnectException() {
        super();
    }

    public FailToConnectException(String message) {
        super(message);
    }

    public FailToConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailToConnectException(Throwable cause) {
        super(cause);
    }

    protected FailToConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
