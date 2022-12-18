package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    // DAO 를 활용한다면, 해당 의존성을 사용하는 모든 곳에 적용할 부가기능을 추가하기가 쉽다
    private int counter = 0;
    private final ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return counter;
    }
}
