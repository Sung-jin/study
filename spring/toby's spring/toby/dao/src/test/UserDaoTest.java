package test;

import dao.ConnectionMaker;
import dao.SimpleConnectionMaker;
import dao.UserDao;
import domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ConnectionMaker connectionMaker = new SimpleConnectionMaker();
        UserDao dao = new UserDao(connectionMaker);
        // connection 필요에 따라 ConnectionMaker 를 구현한 클래스를 변경만 하면 언제든 확장가능하게 사용할 수 있다

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
