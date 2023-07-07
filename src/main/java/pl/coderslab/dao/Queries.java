package pl.coderslab.dao;

public class Queries extends DbUtil {

    protected static final String CREATE_USER_QUERY =
            "INSERT INTO users(email, username, password, creationDate, lastEdited, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";
    protected static final String READ_ID_QUERY =
            "SELECT * FROM users WHERE id = ?";
    protected static final String READ_EMAIL_QUERY =
            "SELECT * FROM users WHERE email = ?";
    protected static final String FIND_MATCHING_EMAIL =
            "SELECT * FROM users WHERE users.email LIKE ? ORDER BY id LIMIT 50 OFFSET 0";
    protected static final String READ_USERNAME_QUERY =
            "SELECT * FROM users WHERE username = ?";
    protected static final String REMOVE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    protected static final String UPDATE_USER_QUERY =
            "UPDATE users SET email = ?, username = ?, password = ?, lastEdited = ?, isAdmin = ? WHERE id = ?";
    protected static final String READ_TEN_QUERY =
            "SELECT * FROM users WHERE isAdmin = 0 ORDER BY id LIMIT ? OFFSET ?";
    protected static final String READ_ADMINS_QUERY =
            "SELECT * FROM users WHERE isAdmin = 1";
    protected static final String TOTAL_USERS_QUERY =
            "SELECT COUNT(*) AS total_users FROM users WHERE isAdmin = 0";
}
