package Config;

import DTO.User;

import java.sql.*;

public class SimpleConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");;
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/study?characterEncoding=UTF-8&serverTimezone=UTC", "root", "1234");
        return c;
    }
}
