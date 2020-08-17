package model.http;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequest {
    private final static String contentTypeIndication = "Accept:";

    private HttpMethod method;
    private String requestEndPoint;
    private String httpVersion;
    private String contentType;
    // TODO - valid content type 으로 변환할 수 있는 방법 찾아봐야 함.

    public HttpRequest(ArrayList<String> headers) {
        List<String> methods = Stream.of(HttpMethod.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        for (String header: headers) {
            String[] splitHeader = header.split(" ");
            if (methods.contains(splitHeader[0])) {
                method = HttpMethod.valueOf(splitHeader[0]);
                requestEndPoint = splitHeader[1];
                httpVersion = splitHeader[2];
            } else if (splitHeader[0].equals(contentTypeIndication)) {
                contentType = splitHeader[1];
            }
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getRequestEndPoint() {
        return requestEndPoint;
    }

    public String getContentType() {
        return contentType;
    }
}
