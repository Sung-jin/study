package model.http;

import model.http.header.RequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HttpParser {
    private RequestHeader requestHeader = new RequestHeader();

    public HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        ArrayList<String> headers = new ArrayList<>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if (line.equals("")) break;
            headers.add(line);
        }

        String[] requestLine = headers.remove(0).split(" ");
        HttpMethod method = HttpMethod.valueOf(requestLine[0]);
        URI uri = URI.create(requestLine[1]);
        HttpVersion httpVersion = HttpVersion.getVersionByString(requestLine[2]);

        for(String header: headers) {
            requestHeader.addHeader(header);
        }

        HttpRequest httpRequest = new HttpRequest(method, uri, httpVersion, this.requestHeader);
        if (method != HttpMethod.GET) {
            httpRequest.setBody(br);
        }

        return httpRequest;
    }
}
