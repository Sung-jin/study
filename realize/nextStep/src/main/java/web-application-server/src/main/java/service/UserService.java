package service;

import model.User;
import model.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.List;

import static db.DataBase.*;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private User user;

    public void setUser(HttpRequest httpRequest) {
        this.user = new User(
                getFirstElementOrEmpty(httpRequest.getBodyValues("userId")),
                getFirstElementOrEmpty(httpRequest.getBodyValues("password")),
                getFirstElementOrEmpty(httpRequest.getBodyValues("name")),
                getFirstElementOrEmpty(httpRequest.getBodyValues("email"))
        );
    }

    public void joinUser(User user) {
        addUser(user);
        // TODO - 중복 회원가입 (id 필터로) 방지 기능 추가
    }

    public boolean loginUser(User user) {
        return findAll().stream().anyMatch(it ->
            it.getUserId().equals(user.getUserId()) && it.getPassword().equals(user.getPassword())
        );
    }

    public User getUser() {
        return user;
    }

    private String getFirstElementOrEmpty(List<String> element) {
        return element != null && element.size() > 0 ? element.get(0) : "";
    }
}
