package DAO;

import Config.CountingConnectionMaker;
import Config.SimpleConnectionMaker;
import Config.studyConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
//    @Bean
//    public UserDAO studyUserDao() {
//        return new UserDAO(simpleConnectionMaker());
//    }

    @Bean
    public UserDAO studyUserDao() {
        UserDAO userDAO = new UserDAO();
        userDAO.setSimpleConnectionMaker(simpleConnectionMaker());
        return userDAO;
    }

    @Bean
    public SimpleConnectionMaker simpleConnectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public SimpleConnectionMaker realConnectionMaker() {
        return new studyConnectionMaker();
    }
}
