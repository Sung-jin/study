package test;

import org.junit.jupiter.api.AfterEach;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import sql.EmbeddedDbSqlRegistry;
import sql.UpdatableSqlRegistry;

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
}
