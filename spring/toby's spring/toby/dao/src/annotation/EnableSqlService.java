package annotation;

import org.springframework.context.annotation.Import;
import sql.SqlServiceContext;

@Import(SqlServiceContext.class)
public @interface EnableSqlService {
}
