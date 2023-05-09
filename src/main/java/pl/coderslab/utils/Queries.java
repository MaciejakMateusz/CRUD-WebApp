package pl.coderslab.utils;

public class Queries extends DbUtil {

    protected static final String CREATE_USER_QUERY =
            "INSERT INTO users(email, username, password) VALUES (?, ?, ?)";
    protected static final String READ_ID_QUERY =
            "SELECT * FROM users WHERE id = ?";
    protected static final String READ_EMAIL_QUERY =
            "SELECT * FROM users WHERE email = ?";
    protected static final String READ_USERNAME_QUERY =
            "SELECT * FROM users WHERE username = ?";
    protected static final String REMOVE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    protected static final String UPDATE_USER_QUERY =
            "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
    protected static final String READ_ALL_QUERY = "SELECT * FROM users";
}
