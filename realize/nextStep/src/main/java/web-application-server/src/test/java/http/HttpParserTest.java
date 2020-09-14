package http;

import model.http.HttpMethod;
import model.http.HttpParser;
import model.http.HttpRequest;
import model.http.header.RequestField;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtilsTest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class HttpParserTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);
    private Path httpRequestDir = Paths.get("src", "test", "java", "resources");

    @Test
    public void testParseGet() throws Exception {
        InputStream in = new FileInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("Http_GET.txt")).getFile());
        HttpRequest httpRequest = new HttpParser().parse(in);

        assertEquals(HttpMethod.GET, httpRequest.getMethod());
        assertEquals("/user/create", httpRequest.getUri().getPath());
        assertEquals("keep-alive", httpRequest.getHeaderValue(RequestField.CONNECTION));
        assertEquals("foo", httpRequest.getQueryValues("userId"));
    }
}
