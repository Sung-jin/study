package test;


import dao.UserDao;
import domain.Level;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("foo", "A", "p1", Level.BASIC, 49, 0),
                new User("bar", "B", "p2", Level.BASIC, 50, 0),
                new User("fuz", "C", "p3", Level.SILVER, 60, 29),
                new User("baz", "D", "p4", Level.SILVER, 60, 30),
                new User("foobar", "E", "p5", Level.GOLD, 100, 100)
        );
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user: users) userDao.add(user);

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level level) {
        User userUpdate = userDao.get(user.getId());
        assertEquals(userUpdate.getLevel(), level);
    }
}
