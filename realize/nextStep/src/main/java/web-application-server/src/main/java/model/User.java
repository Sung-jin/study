package model;

public class User {
    private String userId = "";
    private String password = "";
    private String name = "";
    private String email = "";

    public User(String userId, String password, String name, String email) {
        if (userId != null) this.userId = userId;
        if (password != null) this.password = password;
        if (name != null) this.name = name;
        if (email != null) this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
