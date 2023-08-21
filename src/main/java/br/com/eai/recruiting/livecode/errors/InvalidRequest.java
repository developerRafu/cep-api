package br.com.eai.recruiting.livecode.errors;

public class InvalidRequest extends RuntimeException {

    private final ErrorCause errorCause;
    public static InvalidRequest instance = new InvalidRequest(ErrorCause.REQUEST);
    public static InvalidRequest invalidRequest = new InvalidRequest(ErrorCause.REQUEST);
    public static InvalidRequest invalidZipCodes = new InvalidRequest(ErrorCause.REPEATED_ZIP_CODE);

    public InvalidRequest(ErrorCause errorCause) {
        this.errorCause = errorCause;
    }

    public ErrorCause getErrorCause() {
        return errorCause;
    }
}
