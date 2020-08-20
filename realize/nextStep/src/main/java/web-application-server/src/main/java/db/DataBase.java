package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;

public class DataBase {
    private static Map<String, User> joinUsers = Maps.newHashMap();
    private static Map<String, User> loginUsers = Maps.newHashMap();

    public static void addUser(User user) {
        joinUsers.put(user.getUserId(), user);
    }

    public static void login(User user) {
        loginUsers.put(user.getUserId(), user);
    }

    public static User findLoginUserById(String userId) {
        return loginUsers.get(userId);
    }

    public static Collection<User> findAll() {
        return joinUsers.values();
    }
}
