package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        return new LocalDBConnectionMaker().makeConnection();
        // 위와 같이 DI 를 활용한다면, 개발/운영 등의 환경을 분리하여 손쉽에 전역으로 적용할 수 있다
        // 이를 통해 환경별 DB 연결을 DAO 레벨이 아닌 주입해주는 코드 하나 변경으로 모두 적용시킬수 있다
    }

    public static class LocalDBConnectionMaker implements ConnectionMaker {
        @Override
        public Connection makeConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:13306/toby", "toby", "1q2w3e4r");
        }
    }

    public static class ProductionDBConnection implements ConnectionMaker {
        @Override
        public Connection makeConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://production-domain:3306/toby", "toby", "1q2w3e4r");
        }
    }
}
