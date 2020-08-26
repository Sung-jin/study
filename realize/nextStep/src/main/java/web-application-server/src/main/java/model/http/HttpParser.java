package model.http;

import model.http.header.RequestHeader;

import java.net.URI;
import java.util.ArrayList;

public class HttpParser {
    static HttpParser instance;

    private HttpMethod method;
    private URI uri;
    private HttpVersion httpVersion;
    private RequestHeader requestHeader = new RequestHeader();

    private HttpParser() {}

    public static HttpParser getInstance() {
        if (instance == null) instance = new HttpParser();
        return instance;
    }

    public HttpRequest parse(ArrayList<String> headers) {
        String[] requestLine = headers.remove(0).split(" ");
        method = HttpMethod.valueOf(requestLine[0]);
        uri = URI.create(requestLine[1]);
        httpVersion = HttpVersion.getVersionByString(requestLine[2]);

        for(String header: headers) {
            requestHeader.addHeader(header);
        }

        return new HttpRequest(this.method, this.uri, this.httpVersion, this.requestHeader);
    }
}
