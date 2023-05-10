package pl.coderslab.utils;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exceptions.UserNotFoundException;
import pl.coderslab.users.User;
import pl.coderslab.users.UserShow;

import java.sql.*;
import java.util.ArrayList;

public class UserDao extends Queries {

    // --- ENCRYPTING ---

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // --- CREATE ---

    public User create(User user) {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static User createUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        return new User(id, email, username, password);
    }

    // --- READ ---

    public User read(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createUser(resultSet);
                } else {
                    throw new UserNotFoundException(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User readEmail(String email) {
        return getUser(email, READ_EMAIL_QUERY);
    }

    public User readUsername(String username) {
        return getUser(username, READ_USERNAME_QUERY);
    }

    private User getUser(String username, String readUsernameQuery) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(readUsernameQuery)
        ) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createUser(resultSet);
                } else {
                    throw new UserNotFoundException(username);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> findAll() {
        try (PreparedStatement statement = getConnection().prepareStatement(READ_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = createUser(resultSet);
                addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addToArray(User u, ArrayList<User> users) {
        users.add(u);
    }

    // --- UPDATE ---

    public void update(User user) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)
        ) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // --- DELETE ---

    public void delete(int id) {
        try (PreparedStatement statement = getConnection()
                .prepareStatement(REMOVE_USER_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Successfully deleted entry");
    }


}