package dao;

import domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
//    private ConnectionMaker simpleConnectionMaker;
    private DataSource dataSource;

//    public void setConnectionMaker(ConnectionMaker connectionMaker) {
//        this.simpleConnectionMaker = connectionMaker;
//    }

//    public UserDao(ConnectionMaker connectionMaker) {
//        simpleConnectionMaker = connectionMaker;
//    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
    private static UserDao INSTANCE;

    모든 생성자는 private 으로..

    public static synchronized UserDao getInstance() {
        if (INSTANCE == null) INSTANCE = new UserDao(...);
        return INSTANCE;
    }
    싱글톤을 위해서는 모든 생성자(대부분 기본 생성자 하나만 정의 및 private 으로)가 private 이고
    단 하나의 인스턴스를 위한 static 인 자기 자신을 가지고, 해당 인스턴스로만 활용하는 형태이다
     */

    public void add(User user) throws ClassNotFoundException, SQLException {
//        Connection c = simpleConnectionMaker.makeConnection();
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("insert into user(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
//        Connection c = simpleConnectionMaker.makeConnection();
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from user where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}
