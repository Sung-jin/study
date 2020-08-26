package model.http;

import model.http.header.RequestField;
import model.http.header.RequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static util.IOUtils.readData;

public class HttpRequest {
    private HttpMethod method;
    private URI uri;
    private HttpVersion httpVersion;
    private RequestHeader requestHeader;
    private Map<String, ArrayList<String>> body = new HashMap<>();

    public HttpRequest(HttpMethod method, URI uri, HttpVersion httpVersion, RequestHeader header) {
        this.method = method;
        this.uri = uri;
        this.httpVersion = httpVersion;
        this.requestHeader = header;
    }

    public void setBody(BufferedReader br) throws IOException {
//        우선은 get / post 의 url 인코딩 형태만..
        this.body = readData(br, Integer.parseInt(getHeaderValue(RequestField.CONTENT_LENGTH)));
    }

    public Set<String> getBodyKeySet() {
        return this.body.keySet();
    }

    public ArrayList<String> getBodyValues(String key) {
        return body.get(key);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public String fullUri() {
        return uri.toString();
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public String getHeaderValue(String customKey) {
        return requestHeader.getCustomHeaderValue(customKey);
    }

    public String getHeaderValue(RequestField key) {
        return requestHeader.getHeaderValue(key);
    }
}
