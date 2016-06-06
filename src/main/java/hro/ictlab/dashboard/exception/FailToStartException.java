package hro.ictlab.dashboard.exception;

public class FailToStartException extends Exception {

    public FailToStartException() {
        super();
    }

    public FailToStartException(String message) {
        super(message);
    }

    public FailToStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailToStartException(Throwable cause) {
        super(cause);
    }

    protected FailToStartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
