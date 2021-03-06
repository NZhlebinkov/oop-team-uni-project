package nl.tudelft.oopp.api.models;

public class ServerResponseAlert {
    private String message;
    private String alertType;

    /**
     * A Response object sent after a login attempt by a user.
     *
     * @param message   the message that should be sent back.
     * @param alertType the type of alert that should be used.
     */
    public ServerResponseAlert(String message, String alertType) {
        this.message = message;
        this.alertType = alertType;
    }

    public ServerResponseAlert() {}

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlertType() {
        return this.alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

}
