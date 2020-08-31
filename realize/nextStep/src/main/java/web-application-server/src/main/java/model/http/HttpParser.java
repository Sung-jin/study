package model.http;

import model.http.header.RequestHeader;

import java.net.URI;
import java.util.ArrayList;

public class HttpParser {
    private RequestHeader requestHeader = new RequestHeader();

    public HttpRequest parse(ArrayList<String> headers) {
        String[] requestLine = headers.remove(0).split(" ");
        HttpMethod method = HttpMethod.valueOf(requestLine[0]);
        URI uri = URI.create(requestLine[1]);
        HttpVersion httpVersion = HttpVersion.getVersionByString(requestLine[2]);

        for(String header: headers) {
            requestHeader.addHeader(header);
        }

        return new HttpRequest(method, uri, httpVersion, this.requestHeader);
    }
}
