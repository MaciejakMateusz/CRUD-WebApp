package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exceptions.UserNotFoundException;
import pl.coderslab.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Queries {

    /**
     * ----- ENCRYPTING -----
     **/

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * ----- CREATE -----
     **/

    public User create(User user) {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setString(4, user.getCreationDate());
            statement.setString(5, user.getLastEdited());
            statement.setBoolean(6, user.isAdmin());
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
        String creationDate = resultSet.getString("creationDate");
        String lastEdited = resultSet.getString("lastEdited");
        boolean isAdmin = resultSet.getBoolean("isAdmin");
        return new User(id, email, username, password, creationDate, lastEdited, isAdmin);
    }

    /**
     * ----- READ -----
     **/

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

    public boolean isFoundById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // returns boolean (true = user is found)
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User readByEmail(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_EMAIL_QUERY)
        ) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createUser(resultSet);
                } else {
                    throw new UserNotFoundException(email);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFoundByEmail(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_EMAIL_QUERY)
        ) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> matchingUsers(String partialEmail) {
        List<User> matchingUsers = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_MATCHING_EMAIL)
        ) {
            statement.setString(1, "%" + partialEmail + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = createUser(resultSet);
                matchingUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matchingUsers;
    }

    public boolean isFoundByUsername(String username) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_USERNAME_QUERY)
        ) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> getTenRecords(int recordsPerPage, int offset) {
        ArrayList<User> users = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(READ_TEN_QUERY)) {
            statement.setInt(1, recordsPerPage);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
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

    public ArrayList<User> findAllAdmins() {
        try (PreparedStatement statement = getConnection().prepareStatement(READ_ADMINS_QUERY);
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

    /**
     * ----- UPDATE -----
     **/

    public void update(User user) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)
        ) {
            User userFromDB = read(user.getId());

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            if (userFromDB.getPassword().equals(user.getPassword())) {
                statement.setString(3, user.getPassword());
            } else {
                statement.setString(3, hashPassword(user.getPassword()));
            }
            statement.setString(4, user.getLastEdited());
            statement.setBoolean(5, user.isAdmin());
            statement.setInt(6, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ----- DELETE -----
     **/

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

    /**
     * ----- UTILS -----
     **/

    public int totalUsers() {
        try (PreparedStatement statement = getConnection().prepareStatement(TOTAL_USERS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total_users");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}