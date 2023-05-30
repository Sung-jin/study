package test;

import dao.UserDao;
import domain.User;
import error.TestUserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.DummyMailSender;
import service.UserService;
import test.UserServiceTest.TestUserService;

@Configuration
public class TestAppContext {
    @Autowired
    UserDao userdao;

    @Bean
    public UserService testUserService() {
        return new TestUserService();
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }
}
