package ru.cheapestway.rest.models;

public class MessageWS {
    private String userID;
    private String message;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageWS(String userID, String message) {
        this.userID = userID;
        this.message = message;
    }

    public MessageWS() {
    }

    @Override
    public String toString() {
        return "MessageWS{" +
                "userID='" + userID + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
