package Service;

import DAO.UserDao;
import DAO.UserDaoJdbc;
import DTO.User;
import Domain.Level;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserServiceImplTest extends UserServiceImpl {

    @Autowired
    private UserDaoJdbc userDao;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private ApplicationContext context;

    private List<User> users;

    @Before
    public void  setUp() {
        users = Arrays.asList(
                new User("abc", "비실이", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "abc@example.com"),
                new User("foo", "이슬이", "qwer", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "foo@example.com"),
                new User("hey", "퉁퉁이", "abcd", Level.SILVER, 60, MIN_RECOOMEND_FOR_GOLD - 1, "hey@example.com"),
                new User("xyz", "노진구", "aaaa", Level.SILVER, 60, MIN_RECOOMEND_FOR_GOLD, "xyz@example.com"),
                new User("bar", "도라에몽", "bbbb", Level.GOLD, 100, Integer.MAX_VALUE, "bar@example.com")
        );
    }

    @Test
    public void upgradeLevels() {
//        UserServiceImpl userService = new UserServiceImpl();
//
//        UserDao mockUserDao = mock(UserDao.class);
//        when(mockUserDao.getAll()).thenReturn(this.users);
//        userService.setUserDao(mockUserDao);
//
//        MailSender mockMailSender = mock(MailSender.class);
//        userService.setMailSender(mockMailSender);
//
//        userService.upgradeLevels();
//
//        verify(mockUserDao, time(2)).update(any(User.class));
//        verify(mockUserDao, time(2)).update(any(User.class));
//        verify(mockUserDao).update(users.get(1));
//        assertThat(users.get(1).getLevel(), is(Level.SILVER));
//        verify(mockUserDao).update(users.get(3));
//        assertThat(users.get(3).getLevel(), is(Level.GOLD));
//
//        ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
//
//        verify(mockMailSender, time(2)).send(mailMessageArg.capture());
//        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
//        assertThat(users.get(1).getLevel(), is(Level.SILVER));
//        mockito를 이용하여 테스트하는 예제

        UserServiceImpl userService = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userService.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        userService.setMailSender(mockMailSender);

        userService.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated.size(), is(2));
        checkUserAndLevel(updated.get(0), "foo", Level.SILVER);
        checkUserAndLevel(updated.get(1), "xyz", Level.GOLD);

        List<String> request = mockMailSender.getRequests();
        assertThat(request.size(), is(2));
        assertThat(request.get(0), is(users.get(1).getEmail()));
        assertThat(request.get(1), is(users.get(3).getEmail()));
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userServiceImpl.add(userWithLevel);
        userServiceImpl.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(userWithoutLevel.getLevel()));
    }

    @Test
    @DirtiesContext
    public void upgradeAllOrNothing() throws Exception {
        TestUserServiceImpl testUserServiceImpl = new TestUserServiceImpl(users.get(3).getId());
        testUserServiceImpl.setUserDao(this.userDao);
        testUserServiceImpl.setMailSender(this.mailSender);

        TxProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", TxProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(testUserServiceImpl);
        UserService txUserService = (UserService) txProxyFactoryBean.getObject();
        // 프록시 팩토리 빈을 적용한 방법

//        TransactionHandler txHandler = new TransactionHandler();
//        txHandler.setTarget(testUserServiceImpl);
//        txHandler.setTransactionManager(transactionManager);
//        txHandler.setPattern("upgradeLevels");
//
//        UserService txUserService = (UserService) Proxy.newProxyInstance(
//                getClass().getClassLoader(),
//                new Class[] { UserService.class },
//                txHandler
//        );
//        proxy를 구현한 방법

//        UserServiceTx txUserService = new UserServiceTx();
//        txUserService.setTransactionManager(transactionManager);
//        txUserService.setUserService(testUserServiceImpl);
//        proxy로 구현하기 전, 모두 구현 한 클래스를 이용한 방법

        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        try {
            txUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) {}

        checkLevelUpgraded(users.get(1), false);
    }

    private void checkLevelUpgraded(User user, Boolean upgraded) {
        User userUpdate = userDao.get(user.getId());

        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId(), is(expectedId));
        assertThat(updated.getLevel(), is(expectedLevel));
    }

    static class TestUserServiceImpl extends UserServiceImpl {
        private String id;

        private TestUserServiceImpl(String id) {
            this.id = id;
        }

        @Override
        public void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {

    }

    static class MockUserDao implements UserDao {
        private List<User> users;
        private List<User> updated = new ArrayList<User>();

        private MockUserDao(List<User> users) {
            this.users = users;
        }

        List<User> getUpdated() {
            return this.updated;
        }

        public List<User> getAll() {
            return this.users;
        }

        public void update(User user) {
            updated.add(user);
        }

        public void add(User user) { throw new UnsupportedOperationException(); }
        public void deleteAll() { throw new UnsupportedOperationException(); }
        public User get(String id) { throw new UnsupportedOperationException(); }
        public int getCount() { throw new UnsupportedOperationException(); }
        // 사용되지 않는 메소드
    }

    static class MockMailSender implements MailSender {
        private List<String> requests = new ArrayList<String>();

        private List<String> getRequests() {
            return requests;
        }

        public void send(SimpleMailMessage simpleMailMessage) throws MailException {
            requests.add(simpleMailMessage.getTo()[0]);
        }

        public void send(SimpleMailMessage[] simpleMailMessages) throws MailException {
        }
    }
}
