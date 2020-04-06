package nl.tudelft.oopp.api.models;

public class UserAuthResponse {
    private String message;
    private String alertType;
    private User user;

    public UserAuthResponse() {
    }

    /**
     * A Response object sent after a login attempt by a user.
     *
     * @param message   the message that should be sent back.
     * @param alertType the type of alert that should be used.
     * @param user    the user that was generated by the database.
     */
    public UserAuthResponse(String message, String alertType, User user) {
        this.message = message;
        this.alertType = alertType;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
