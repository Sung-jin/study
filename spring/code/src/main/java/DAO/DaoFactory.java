//package DAO;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.SimpleDriverDataSource;
//
//import javax.sql.DataSource;
//
///*
//* 애플리케이션을 구성하는 컴포넌트의 구조와 관계를 정의한 설계도와 같은 역할
//* 어떤 오브젝트가 어떤 오브젝트를 사용할지를 정해놓은 코드
//* */
//
//@Configuration
//public class DaoFactory {
//
//    @Bean
//    public UserDao userDao() {
//        UserDao userDao = new UserDao();
//        userDao.setConnectionMaker(dataSource());
//        return userDao;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//
//        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
//        dataSource.setUrl("jdbc:mysql://localhost/study?characterEncoding=UTF-8&serverTimezone=UTC");
//        dataSource.setUsername("root");
//        dataSource.setPassword("1234");
//
//        return dataSource;
//    }
//}
