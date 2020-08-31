package model.http;

import model.http.header.RequestField;
import model.http.header.RequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static util.IOUtils.readData;

public class HttpRequest {
    private HttpMethod method;
    private URI uri;
    private HttpVersion httpVersion;
    private RequestHeader requestHeader;
    private Map<String, ArrayList<String>> query = new HashMap<>();
    private Map<String, ArrayList<String>> body = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();

    public HttpRequest(HttpMethod method, URI uri, HttpVersion httpVersion, RequestHeader header) {
        this.method = method;
        this.uri = uri;
        this.httpVersion = httpVersion;
        this.requestHeader = header;

        if (hasKeyInHeader(RequestField.COOKIE.getKey())) {
            Arrays.stream(header
                    .getHeaderValue(RequestField.COOKIE)
                    .split(";(?=\\s)"))
                    .forEach(splitCookie -> {
                        String[] keyValue = splitCookie.split("=");
                        cookies.put(keyValue[0], keyValue[1]);
                    });
        }
    }

    public void setBody(BufferedReader br) throws IOException {
//        우선은 get / post 의 url 인코딩 형태만..
        this.body = readData(br, Integer.parseInt(getHeaderValue(RequestField.CONTENT_LENGTH)));
    }

    public Set<String> getQueryKeySet() {
        return this.query.keySet();
    }

    public ArrayList<String> getQueryValues(String key) {
        return query.get(key);
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

    public String getHeaderValue(RequestField key) {
        return requestHeader.getHeaderValue(key);
    }

    public String getHeaderValue(String customKey) {
        return requestHeader.getCustomHeaderValue(customKey);
    }

    public String getKeyValueHeader(RequestField field) { return field.getKey() + ": " + getHeaderValue(field); }

    public String getKeyValueCustomHeader(String key) { return key + ": " + getHeaderValue(key); }

    public String getCookieValue(String key) {
        return cookies.get(key);
    }

    public Boolean hasKeyInHeader(String key) {
        return requestHeader
                .getAllKeyStream()
                .anyMatch(key::equals);
    }
}
