package test;

import dao.DaoFactory;
import dao.UserDao;
import dao.UserDaoJdbc;
import domain.User;
import error.EmptyResultDataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserDaoTest {
    public static void main(String[] args) throws SQLException {
        UserDaoJdbc dao = new AnnotationConfigApplicationContext(DaoFactory.class).getBean("userDao", UserDaoJdbc.class);

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

    @Autowired
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
//        dao = new UserDaoJdbc();
//        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost:13306", "test", "1q2w3e4r", true);
//        dao.setDataSource(dataSource);
        // 필요 상황에 따라 스프링 콘텍스트를 통한 DI 없이 필요한 정보를 직접 추가하여 테스트를 진행해도 된다

        user1 = new User("id1", "foo", "1234");
        user2 = new User("id2", "bar", "1234");
        user3 = new User("id3", "fuz", "1234");
        // 반복되는 부분을 텍스트 픽스처로 처리하면 모든 테스트에서 편리하게 공통으로 사용할 수 있다
    }

    @Test
    public void addAndGet() throws SQLException {
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.add(user1);
        dao.add(user2);
        assertEquals(dao.getCount(), 2);

        User user = dao.get(user1.getId());
        assertEquals(user.getName(), user1.getName());
        assertEquals(user.getPassword(), user1.getPassword());
        // 위와 같이 테스트 프레임워크를 통해 간편하게 테스트를 위한 코드를 작성할 수 있다
    }

    @Test
    public void count() throws SQLException {
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.add(user1);
        assertEquals(dao.getCount(), 1);

        dao.add(user2);
        assertEquals(dao.getCount(), 2);

        dao.add(user3);
        assertEquals(dao.getCount(), 3);
    }

    @Test
    public void getUserFailUser() throws SQLException {
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        assertThrows(EmptyResultDataAccessException.class, () -> dao.get("unknown_id"));
    }

    @Test
    public void getAll() {
        dao.deleteAll();

        List<User> users = dao.getAll();
        assertEquals(users.size(), 0);

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertEquals(users1.size(), 1);
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertEquals(users2.size(), 1);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertEquals(users3.size(), 1);
        checkSameUser(user1, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }

    @Test
    public void duplicateKey() {
        dao.deleteAll();

        dao.add(user1);
        assertThrows(DataAccessException.class, () -> dao.add(user1));
    }

    private void checkSameUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getPassword(), user2.getPassword());
    }
}
