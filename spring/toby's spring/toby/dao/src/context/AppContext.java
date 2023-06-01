package context;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import service.DummyMailSender;
import service.UserService;
import sql.SqlServiceContext;
import test.UserServiceTest;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "dao")
@Import(SqlServiceContext.class)
public class AppContext {

    @Autowired
    UserDao userDao;

    /**
     * db 연결 및 트랜잭션
     */

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:13306/testdb");
        dataSource.setUsername("test");
        dataSource.setPassword("1q2w3e4r");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    /**
     * 애플리케이션 로직 테스트
     */

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = snew JavaMailSenderImpl();
        mailSender.setHost("some.host");

        return mailSender;
    }

    @Configuration
    @Profile("production")
    public static class ProductionAppContext {
        @Bean
        public MailSender mailSender() {
            JavaMailSenderImpl mailSender = snew JavaMailSenderImpl();
            mailSender.setHost("some.host");

            return mailSender;
        }
    }

    @Configuration
    @Profile("test")
    public static class TestAppContext {
        @Autowired
        UserDao userdao;

        @Bean
        public UserService testUserService() {
            return new UserServiceTest.TestUserService();
        }

        @Bean
        public MailSender mailSender() {
            return new DummyMailSender();
        }
    }
}
