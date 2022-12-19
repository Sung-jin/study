package test;

import dao.UserDao;
import domain.User;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoXmlTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        GenericXmlApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("dataSource.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("id");
        user.setName("osj");
        user.setPassword("1q2w3e4r");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user.getId() + " 조회 성공");
    }
}
