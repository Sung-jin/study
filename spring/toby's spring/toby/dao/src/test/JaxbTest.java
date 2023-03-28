package test;

import org.junit.jupiter.api.Test;
import sql.sqlservice.jaxb.SqlType;
import sql.sqlservice.jaxb.Sqlmap;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JaxbTest {

    @Test
    public void readSqlmap() throws JAXBException, IOException {
        String contextPath = Sqlmap.class.getPackage().getName();
        JAXBContext context = JAXBContext.newInstance(contextPath);
        Umarshaller unmarshaller = context.createUnmarshaller();

        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(
                getClass().getResourceAsStream("sqlmap.xml")
        );
        List<SqlType> sqlList = sqlmap.getSql();

        assertEquals(sqlList.size(), 3);
        assertEquals(sqlList.get(0).getKey(), "add");
        assertEquals(sqlList.get(0).getValue(), "insert");
        assertEquals(sqlList.get(1).getKey(), "get");
        assertEquals(sqlList.get(1).getValue(), "select");
        assertEquals(sqlList.get(2).getKey(), "delete");
        assertEquals(sqlList.get(2).getValue(), "delete");
    }
}
