package sql;

import error.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

public class EmbeddedDbSqlRegistry implements UpdatableSqlRegistry {
    SimpleJdbcTemplate jdbc;
    TransactionTemplate transactionTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbc = new SimpleJdbcTemplate(dataSource);
        this.transactionTemplate = new TransactionTemplate(
                new DataSourceTransactionManager(dataSource)
        );
    }

    @Override
    public void registerSql(String key, String sql) {
        jdbc.update("insert into sqlmap(key_, sql_) values(?,?)", key, sql);
    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        try {
            return jdbc.queryForObject("select sql_ from sqlmap where key+ = ?", String.class, key);
        } catch(EmptyResultDataAccessException e) {
            throw new SqlNotFoundException(key + "에 해당하는 SQL 을 찾을 수 없습니다.", e);
        }
    }

    @Override
    public void updateSql(String key, String sql) throws SqlUpdateFailureException {
        int affected = jdbc.update("update sqlmap set sql_ = ? where key_ = ?", sql, key);

        if (affected == 0) throw new SqlUpdateFailureException(key + "에 해당하는 SQL 을 찾을 수 없습니다.");
    }

    @Override
    public void updateSql(final Map<String, String> sqlmap) throws SqlUpdateFailureException {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            for(Map.Entry<String, String> entry: sqlmap.entrySet()) {
                updateSql(entry.getKey(), entry.getValue());
            }
        });
    }
}
