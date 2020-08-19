package service;

import model.User;
import model.http.HttpRequest;

public class UserService {
    private User user;

    public void setUser(HttpRequest httpRequest) {
        this.user = new User(httpRequest.body);
    }
}
