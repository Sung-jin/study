package model.http;

import model.http.header.RequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.net.URI;
import java.util.ArrayList;

public class HttpParser {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private HttpMethod method;
    private URI uri;
    private HttpVersion httpVersion;
    private RequestHeader requestHeader = new RequestHeader();

    public HttpParser(ArrayList<String> headers) {
        String[] requestLine = headers.remove(0).split(" ");
        method = HttpMethod.valueOf(requestLine[0]);
        uri = URI.create(requestLine[1]);
        httpVersion = HttpVersion.getVersionByString(requestLine[2]);

        for(String header: headers) {
            requestHeader.addHeader(header);
        }
    }

//    private header
//
//    public HttpParser(ArrayList<String> headers) {
//        List<String> methods = Stream.of(HttpMethod.values())
//                .map(Enum::name)
//                .collect(Collectors.toList());
//        for (String header: headers) {
//            String[] splitHeader = header.split(" ");
//
//            if (methods.contains(splitHeader[0])) {
//                String[] pathWithQueryString = splitHeader[1].split("\\?");
//
//                method = HttpMethod.valueOf(splitHeader[0]);
//                requestEndPoint = pathWithQueryString[0];
//                httpVersion = splitHeader[2];
//
//                if (pathWithQueryString.length == 2) requestParams = parseQueryString(pathWithQueryString[1]);
//            } else {
//                this.headers.add(parseHeader(header));
//            }
//        }
//    }
//
//    public HttpMethod getMethod() {
//        return method;
//    }
//
//    public Map<String, String> getRequestParams() {
//        return requestParams;
//    }
//
//    public String getHttpVersion() {
//        return httpVersion;
//    }
//
//    public String getContentType() {
//        return findValue(CONTENT_TYPE);
//    }
//
//    public String getConnection() {
//        return findValue(KEEP_ALIVE);
//    }
//
//    public String getCookie() {
//        return findValue(COOKIE);
//    }
//
//    public int getContentLength() {
//        return Integer.parseInt(Objects.requireNonNull(findValue(CONTENT_LENGTH)));
//    }
//
//    private String findValue(String key) {
//        try {
//            return headers
//                    .stream()
//                    .filter(header -> header.getKey().equals(key))
//                    .findFirst()
//                    .orElseThrow(NoSuchElementException::new)
//                    .getValue();
//        } catch (NoSuchElementException e) {
//            log.error(e.getMessage());
//        }
//        return null;
//    }
}
