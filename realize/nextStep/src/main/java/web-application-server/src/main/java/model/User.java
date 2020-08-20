package model;

import java.util.ArrayList;
import java.util.Map;

public class User {
    private String userId = "";
    private String password = "";
    private String name = "";
    private String email = "";

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User(Map<String, ArrayList<String>> body) {
        if (body.get("userId") != null) this.userId = body.get("userId").get(0);
        if (body.get("password") != null) this.password = body.get("password").get(0);
        if (body.get("name") != null) this.name = body.get("name").get(0);
        if (body.get("email") != null) this.email = body.get("email").get(0);
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
