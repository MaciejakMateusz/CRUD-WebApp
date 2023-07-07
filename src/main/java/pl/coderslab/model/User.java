package pl.coderslab.model;

import java.util.Objects;

public class User {
    private int id;
    private String email, userName, password, creationDate, lastEdited;
    private boolean isAdmin = false;

    public User() {}

    public User(int id, String email, String userName, String password, String creationDate, String lastEdited, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.creationDate = creationDate;
        this.lastEdited = lastEdited;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(String lastEdited) {
        this.lastEdited = lastEdited;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(email, user.email) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(creationDate, user.creationDate) && Objects.equals(lastEdited, user.lastEdited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, userName, password, creationDate, lastEdited, isAdmin);
    }
}
