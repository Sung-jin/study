package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sql.sqlservice.jaxb.SqlType;
import sql.sqlservice.jaxb.Sqlmap;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class OxmTest {
    @Autowired
    Unmarshaller unmarshaller;

    @Test
    public unmarshallSqlMap() throws XmlMappingException, IOException {
        Source xmlSource = new StreamSource(
                getClass().getResourceAsStream("sqlmap.xml")
        );
        Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);

        List<SqlType> sqlList = sqlmap.getSql();
        assertEquals(sqlList.size(), 3);
        assertEquals(sqlList.get(0).getKey(), "add");
        assertEquals(sqlList.get(1).getKey(), "get");
        assertEquals(sqlList.get(2).getKey(), "delete");
    }
}
