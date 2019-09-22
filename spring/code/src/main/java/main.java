import Config.AConnectionMaker;
import Config.BConnectionMaker;
import Config.SimpleConnectionMaker;
import DAO.UserDAO;
import DTO.User;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        SimpleConnectionMaker aConnection = new AConnectionMaker();
        SimpleConnectionMaker bConnection = new BConnectionMaker();

        UserDAO aDAO = new UserDAO(aConnection);
        UserDAO bDAO = new UserDAO(bConnection);

        User aUser = new User();
        aUser.setId("fonnie");
        aUser.setName("오성진");
        aUser.setPassword("1234");

        aDAO.add(aUser);

        System.out.println("a 설정 DB에 등록성공");

        User aUser2 = aDAO.get(aUser.getId());
        System.out.println(aUser2.getName());
        System.out.println(aUser2.getPassword());

        User bUser = new User();
        bUser.setId("fonnie");
        bUser.setName("오성진");
        bUser.setPassword("1234");

        bDAO.add(bUser);

        System.out.println("b 설정 DB에 등록성공");

        User bUser2 = aDAO.get(aUser.getId());
        System.out.println(bUser2.getName());
        System.out.println(bUser2.getPassword());
    }
}
