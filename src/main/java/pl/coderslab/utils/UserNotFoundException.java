package pl.coderslab.utils;

public class UserNotFoundException extends RuntimeException {

    private static final String MSG_ID = "USER for given id: %s not found";
    private static final String MSG_STRING_DATA = "USER for given data: %s not found";

    public UserNotFoundException(long id) {
        super(String.format(MSG_ID, id));
    }
    public UserNotFoundException(String column) {
        super(String.format(MSG_STRING_DATA, column));
    }

}