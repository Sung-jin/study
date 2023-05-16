package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import sql.EmbeddedDbSqlRegistry;
import sql.SqlUpdateFailureException;
import sql.UpdatableSqlRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    EmbeddedDatabase db;

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:schema.sql")
                .build();

        EmbeddedDbSqlRegistry embeddedDbSqlREgistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlREgistry.setDataSource(db);

        return embeddedDbSqlREgistry;
    }

    @AfterEach
    public void tearDown() {
        db.shutdown();
    }

    @Test
    public void transactionalUpdate() {
        checkFindResult("SQL1", "SQL2", "SQL3");

        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY4", "Modified4"); // 해당 값은 존재하지 않으므로 예외가 발생하여 트랜잭션이 동작해야 한다

        try {
            sqlRegistry.updateSql(sqlmap);
            fail();
        } catch(SqlUpdateFailureException e) { }

        checkFindResult("SQL1", "SQL2", "SQL3"); // 트랜잭션 롤백에 의해 변경된 KEY1 의 값이 변경되지 않음을 확인한다
    }
}
