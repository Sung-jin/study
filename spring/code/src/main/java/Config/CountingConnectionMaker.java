package Config;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements SimpleConnectionMaker {
    private int counter = 0;
    private SimpleConnectionMaker realSimpleConnectionMaker;

    public CountingConnectionMaker(SimpleConnectionMaker realSimpleConnectionMaker) {
        this.realSimpleConnectionMaker = realSimpleConnectionMaker;
    }

    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return realSimpleConnectionMaker.makeNewConnection();
    }

    public int getCounter() {
        return this.counter;
    }
}
