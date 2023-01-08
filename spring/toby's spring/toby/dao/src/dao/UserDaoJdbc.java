package dao;

import domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc implements UserDao {
//    private ConnectionMaker simpleConnectionMaker;
//    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        return user;
    };

//    public void setConnectionMaker(ConnectionMaker connectionMaker) {
//        this.simpleConnectionMaker = connectionMaker;
//    }

//    public UserDao(ConnectionMaker connectionMaker) {
//        simpleConnectionMaker = connectionMaker;
//    }

    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        // 수동 DI
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

//    private JdbcContext jdbcContext;

//    public void setJdbcContext(JdbcContext jdbcContext) {
//        this.jdbcContext = jdbcContext;
//    }

    public void add(User user) {
//        StatementStrategy st = new AddStatement(user);
//        jdbcContextWithStatementStrategy(st);

//        jdbcContextWithStatementStrategy(c -> {
//        jdbcContext.workWithStatementStrategy(c -> {
//            PreparedStatement ps = c.prepareStatement("insert into users(id, name, password)");
//            ps.setString(1, user.getId());
//            ps.setString(2, user.getName());
//            ps.setString(3, user.getPassword());
//
//            return ps;
//        });
        // 익명 클래스를 통해 바로 전략을 생성하여 셋팅할 수 있다
//        jdbcContext.executeSql("insert into users(id, name, password)", user.getId(), user.getName(), user.getPassword());
        jdbcTemplate.update("insert into users(id, name, password)", user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) {
//        Connection c = simpleConnectionMaker.makeConnection();
//        Connection c = dataSource.getConnection();
//
//        PreparedStatement ps = c.prepareStatement("select * from user where id = ?");
//        ps.setString(1, id);
//
//        ResultSet rs = ps.executeQuery();
//
//        User user = null;
//        if (rs.next()) {
//            user = new User();
//            user.setId(rs.getString("id"));
//            user.setName(rs.getString("name"));
//            user.setPassword(rs.getString("password"));
//        }
//
//        rs.close();
//        ps.close();
//        c.close();
//
//        if (user == null) throw new EmptyResultDataAccessException();
//
//        return user;

        return jdbcTemplate.queryForObject("select * from user where id = ?", new Object[] {id}, userMapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id", userMapper);
    }

    public void deleteAll() {
//        StatementStrategy st = new DeleteAllStrategy();
//        jdbcContextWithStatementStrategy(st);

//        jdbcContextWithStatementStrategy(c -> c.prepareStatement("delete from users"));
//        jdbcContext.workWithStatementStrategy(c -> c.prepareStatement("delete from users"));
//        executeSql("delete from users");
//        jdbcContext.executeSql("delete from users");
        jdbcTemplate.update("delete from users");
    }

//    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
//        Connection c = null;
//        PreparedStatement ps = null;
//
//        try {
//            c = dataSource.getConnection();
//            ps = stmt.makePreparedStatement(c);
//            ps.execute();
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            if (ps != null) try { ps.close(); } catch (SQLException e) {}
//            if (c != null) try { c.close(); } catch (SQLException e) {}
//        }
//    }

    public int getCount() {
//        Connection c = dataSource.getConnection();
//
//        PreparedStatement ps = c.prepareStatement("select count(*) from users");
//
//        ResultSet rs = ps.executeQuery();
//        rs.next();
//        int count = rs.getInt(1);
//
//        rs.close();
//        ps.close();
//        c.close();
//
//        return count;
//        return jdbcTemplate.query("select count(*) from users", rs -> {
//            rs.next();
//            return rs.getInt(1);
//        });
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

//    private void executeSql(final String query) throws SQLException {
//        this.jdbcContext.workWithStatementStrategy(c -> c.prepareStatement(query));
//    }
}
