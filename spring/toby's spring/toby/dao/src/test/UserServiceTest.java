package test;


import dao.UserDao;
import domain.Level;
import domain.User;
import error.TestUserServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import proxy.TransactionHandler;
import service.TxProxyFactoryBean;
import service.UserService;
import service.UserServiceImpl;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static service.UserServiceImpl.MIN_LOG_COUNT_FOR_SILVER;
import static service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserService testUserService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserDao userDao;

    @Autowired
    DataSource dataSource;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    MailSender mailSender;

    @Autowired
    ApplicationContext context;

    List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("foo", "A", "p1", Level.BASIC, MIN_LOG_COUNT_FOR_SILVER - 1, 0),
                new User("bar", "B", "p2", Level.BASIC, MIN_LOG_COUNT_FOR_SILVER, 0),
                new User("fuz", "C", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
                new User("baz", "D", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
                new User("foobar", "E", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void upgradeLevels() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userService.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertEquals(updated.size(), 2);
        checkUserAndLevel(updated.get(0), "bar", Level.SILVER);
        checkUserAndLevel(updated.get(1), "baz", Level.GOLD);

        List<String> request = mockMailSender.getRequests();
        assertEquals(request.size(), 2);
        assertEquals(request.get(0), updated.get(1).getEmail());
        assertEquals(request.get(1), updated.get(3).getEmail());
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertEquals(userWithLevelRead.getLevel(), users.get(0).getLevel());
        assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
    }

//    @Test
//    @DirtiesContext
//    public void upgradeAllOrNothing() throws Exception {
//        TestUserService testUserService = new TestUserService(users.get(3).getId());
//        testUserService.setUserDao(userDao);
//        testUserService.setMailSender(mailSender);
//
////        UserServiceTx txUserService = new UserServiceTx();
////        txUserService.setTransactionManager(transactionManager);
////        txUserService.setUserService(testUserService);
////        TransactionHandler txHandler = new TransactionHandler();
////        txHandler.setTarget(testUserService);
////        txHandler.setTransactionManager(transactionManager);
////        txHandler.setPattern("upgradeLevels");
////        UserService txUserService = (UserService) Proxy.newProxyInstance(
////                getClass().getClassLoader(),
////                new Class[] { UserService.class },
////                txHandler
////        );
//
////        TxProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", TxProxyFactoryBean.class);
//        ProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", ProxyFactoryBean.class);
//        txProxyFactoryBean.setTarget(testUserService);
//        UserService txUserService = (UserService) txProxyFactoryBean.getObject();
//
//        userDao.deleteAll();
//        for (User user: users) userDao.add(user);
//
//        try {
//            txUserService.upgradeLevels();
//            fail("TestUserServiceException expected");
//        } catch(TestUserServiceException e) {}
//
//        checkLevel(users.get(1), false);
//    }

    @Test
    public void upgradeAllOrNothing() {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        try {
            this.testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) {}

        checkLevel(users.get(1), false);
    }

    @Test
    public void readOnlyTransactionAttribute() {
        assertThrows(TransientDataAccessResourceException.class, () -> testUserService.getAll());
    }

    @Test
    public void transactionSync() {
        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        TransactionStatus txStatus = new transactionManager.getTransaction(txDefinition);
        // 트랜잭션 매니저에게 트랜잭션을 요청함으로써, 아래의 각 REQUIRED 속성의 트랜잭션이 요청된 트랜잭션에 전파된다
        // txDefinition.setReadOnly(true); 이와 같이 읽기 전용 트랜잭션으로 할 경우, deleteAll, add 와 같이 쓰기작업에서 예외가 발생하게 된다
        // userDao.deleteAll(); JdbcTemplate 을 통해 이미 시작된 트랜잭션이 있다면, 자동으로 참여하고, 위와 같이 읽기 전용에 참여하게 될 경우 예외가 발생하게 된다

//        userService.deleteAll();

        userService.add(users.get(0));
        userService.add(users.get(1));
        // 위와 같은 기존의 트랜잭션이 없다면, 각각의 메소드마다 트랜잭션이 설정되어 있으므로, 총 3번의 트랜잭션이 만들어진다

        assertEquals(userDao.getCount(), 2);

        transactionManager.rollback(txStatus);

        assertEquals(userDao.getCount(), 0);
    }

    private void checkLevel(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
        } else {
            assertEquals(userUpdate.getLevel(), user.getLevel());
        }
    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertEquals(updated.getId(), expectedId);
        assertEquals(updated.getLevel(), expectedLevel);
    }

    static class TestUserService extends UserServiceImpl {
        private String id = "fuz";

        public TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }

        public List<User> getAll() {
            for (User user: super.getAll()) {
                super.update(user);
            }
            return null;
        }
    }

    static class MockMailSender implements MailSender {
        private List<String> requests = new ArrayList<>();

        public List<String> getRequests() {
            return this.requests;
        }

        public void send(SimpleMailMesssage mailMessage) throws MailException {
            requests.add(mailMessage.getTo()[0]);
        }

        public void send(SimpleMailMessage[] mailMessage) throws MailException {}
    }

    static class MockUserDao implements UserDao {
        private List<User> users;
        private List<User> updated = new ArrayList<>();

        private MockUserDao(List<User> users) {
            this.users = users;
        }

        public List<User> getUpdated() {
            return updated;
        }

        public List<User> getAll() {
            return users;
        }

        public void update(User user) {
            updated.add(user);
        }

        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }
    }
}
