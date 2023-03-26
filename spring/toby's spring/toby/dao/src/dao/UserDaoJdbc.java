package dao;

import domain.Level;
import domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private Map<String, String> sqlMap;

    public void setSqlAdd(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    private RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));

        return user;
    };

    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        // 수동 DI
    }

    public void add(User user) {
        jdbcTemplate.update(
                this.sqlMap.get("add"),
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend()
        );
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject(this.sqlMap.get("get"), new Object[] {id}, userMapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query(this.sqlMap.get("getAll"), userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update(this.sqlMap.get("deleteAll"));
    }

    public int getCount() {
        return jdbcTemplate.queryForObject(this.sqlMap.get("getCount"), Integer.class);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                this.sqlMap.get("update"),
                user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId()
        );
    }
}
