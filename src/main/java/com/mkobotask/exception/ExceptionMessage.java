package com.mkobotask.exception;

public enum ExceptionMessage {

    USER_ALREADY_EXISTS("User already exists!"),
    USER_NOT_AUTHOURIZED("User not authourized"),
    MISSING_REQUIRED_FIELD("Missing required fields. Please check documentation for required field"),
    RECORD_ALREADY_EXISTS("Record already exist"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Record with provided Id not found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATED_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    INCORRECT_EMAIL_OR_PASSWORD("Incorrect email or password"),
    USER_NOT_FOUND("User not found"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
