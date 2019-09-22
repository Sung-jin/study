package Config;

import DTO.User;

import java.sql.*;

public interface SimpleConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException;
}
