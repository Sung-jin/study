import DAO.UserDAO;
import DTO.User;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDAO dao = new UserDAO();

        User user = new User();
        user.setId("fonnie");
        user.setName("오성진");
        user.setPassword("1234");

        dao.add(user);

        System.out.println("등록성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
    }
}
