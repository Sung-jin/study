package model.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils.Pair;
import webserver.RequestHandler;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.HttpRequestUtils.parseHeader;
import static util.HttpRequestUtils.parseQueryString;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final static String CONTENT_TYPE = "Accept";
    private final static String KEEP_ALIVE = "Connection";
    private final static String CONTENT_LENGTH = "Content-Length";
    private final static String COOKIE = "Cookie";

    private HttpMethod method;
    public String requestEndPoint;
    private Map<String, String> requestParams;
    private String httpVersion;
    private ArrayList<Pair> headers = new ArrayList<>();
    public Map<String, ArrayList<String>> body = new HashMap<>();
//    private String contentType;
//    // TODO - valid content type 으로 변환할 수 있는 방법 찾아봐야 함.
//    private String connection;
//    private int contentLength;

    public HttpRequest(ArrayList<String> headers) {
        List<String> methods = Stream.of(HttpMethod.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        for (String header: headers) {
            String[] splitHeader = header.split(" ");

            if (methods.contains(splitHeader[0])) {
                String[] pathWithQueryString = splitHeader[1].split("\\?");

                method = HttpMethod.valueOf(splitHeader[0]);
                requestEndPoint = pathWithQueryString[0];
                httpVersion = splitHeader[2];

                if (pathWithQueryString.length == 2) requestParams = parseQueryString(pathWithQueryString[1]);
            } else {
                this.headers.add(parseHeader(header));
            }
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getContentType() {
        return findValue(CONTENT_TYPE);
    }

    public String getConnection() {
        return findValue(KEEP_ALIVE);
    }

    public String getCookie() {
        return findValue(COOKIE);
    }

    public int getContentLength() {
        return Integer.parseInt(Objects.requireNonNull(findValue(CONTENT_LENGTH)));
    }

    private String findValue(String key) {
        try {
            return headers
                    .stream()
                    .filter(header -> header.getKey().equals(key))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new)
                    .getValue();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
