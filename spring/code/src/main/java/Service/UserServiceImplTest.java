package Service;

import DAO.UserDaoJdbc;
import DTO.User;
import Domain.Level;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

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
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private MailSender mailSender;

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
    @DirtiesContext
    public void upgradeLevels() {
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
        // 유저 레벨 업그레이드 확인 테스트

        List<String> request = mockMailSender.getRequests();

        assertThat(request.size(), is(2));
        assertThat(request.get(0), is(users.get(1).getEmail()));
        assertThat(request.get(1), is(users.get(3).getEmail()));
        // 메일 전송 기능 확인 테스트
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
    public void upgradeAllOrNothing() {
        TestUserServiceImpl testUserServiceImpl = new TestUserServiceImpl(users.get(3).getId());
        testUserServiceImpl.setUserDao(this.userDao);
        testUserServiceImpl.setMailSender(this.mailSender);

        UserServiceTx txUserService = new UserServiceTx();
        txUserService.setTransactionManager(transactionManager);
        txUserService.setUserService(testUserServiceImpl);

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
