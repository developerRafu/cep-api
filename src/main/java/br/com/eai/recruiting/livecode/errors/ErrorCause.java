package br.com.eai.recruiting.livecode.errors;

public enum ErrorCause {
    REQUEST("Invalid request"),
    ZIP_CODE("Invalid zip code"),
    REPEATED_ZIP_CODE("Repeated zip code");
    private final String message;

    ErrorCause(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
