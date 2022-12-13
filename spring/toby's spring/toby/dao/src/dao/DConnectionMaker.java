package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // TODO - 해당 클래스만의 connection 생성 로직
        return null;
    }
}
