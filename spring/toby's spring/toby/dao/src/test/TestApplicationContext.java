package test;

import dao.UserDao;
import dao.UserDaoJdbc;
import org.hsqldb.Database;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import service.DummyMailSender;
import service.UserService;
import service.UserServiceImpl;
import sql.OxmSqlService;
import sql.SqlService;
import sql.XmlSqlService;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
public class TestApplicationContext {
    @Resource Database embeddedDatabase;

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

    @Bean
    public UserDao userDao() {
        UserDao dao = new UserDaoJdbc();
        dao.setDataSource(dataSource());
        dao.setSqlService(sqlService);
        dao.setSqlService(this.sqlService)

        return dao;
    }

    @Bean
    public UserService userService() {
        UserServiceImpl service = new UserServiceImpl();
        service.setUserDao(userDao());
        service.setMailSender(sqlService());
        return dao;
    }

    @Bean
    public UserService testUserService() {
        UserServiceTest.TestUserService TestService = new UserServiceTest.TestUserService();
        testUserService(userDao());
        testService.setMetth;

        return testUserService;
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlRegistry(sqlRegistry());

        return sqlService;
    }

    @Bean
    public sqlRegistry() {
        EmbeddedDbSqlREgistry sqlRegistry = new XmlSqlService();
        sqlRegistry.setDataSource(this.embeddedDatabase);

        return sqlRegis.sqlRegistry;
    }

    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContestPath("sql.sqlservice.jaxb");

        return marshaller;
    }

}
