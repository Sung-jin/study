import DAO.DaoFactory;
import DAO.UserDAO;
import DTO.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDAO aDAO = context.getBean("aUserDao", UserDAO.class);

        User aUser = new User();
        aUser.setId("fonnie");
        aUser.setName("오성진");
        aUser.setPassword("1234");

        aDAO.add(aUser);

        System.out.println("a 설정 DB에 등록성공");

        User aUser2 = aDAO.get(aUser.getId());
        System.out.println(aUser2.getName());
        System.out.println(aUser2.getPassword());
    }
}
