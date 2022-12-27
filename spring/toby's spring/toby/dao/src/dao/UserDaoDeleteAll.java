package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDao {

//    @Override
//    protected PreparedStatement makeStatement(Connection c) throws SQLException {
//        return c.prepareStatement("delete from users");
//    }
//    상속을 통해 확장하여 OCP 를 지킬 순 있찌만, DAO 로직마다 상속을 통해 새로운 클래스를 만들어야 한다는 단점이 존재한다
//    또한 확장구조가 이미 클래스를 설계하는 시점에 고정되어 버린다
}
