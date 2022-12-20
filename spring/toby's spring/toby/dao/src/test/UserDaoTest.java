package test;

import dao.DaoFactory;
import dao.UserDao;
import domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao dao = new AnnotationConfigApplicationContext(DaoFactory.class).getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("id");
        user.setName("osj");
        user.setPassword("1q2w3e4r");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        if (!user.getName().equals(user2.getName())) System.out.println("테스트 실패 (name)");
        else if (!user.getPassword().equals(user2.getPassword())) System.out.println("테스트 실패 (password)");
        else System.out.println("조회 테스트 성공 ");
        // 위와 같이 단순히 특정 실패에 대해 로직으로 정상 여부를 테스트할 수 있다
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        UserDao dao = new AnnotationConfigApplicationContext(DaoFactory.class).getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("id");
        user.setName("osj");
        user.setPassword("1q2w3e4r");
        dao.add(user);

        User user2 = dao.get(user.getId());

        assertEquals(user2.getName(), user.getName());
        assertEquals(user2.getPassword(), user.getPassword());
        // 위와 같이 테스트 프레임워크를 통해 간편하게 테스트를 위한 코드를 작성할 수 있다
    }
}
