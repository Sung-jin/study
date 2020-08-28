package model.http;

import model.http.header.RequestField;
import model.http.header.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private HttpMethod method;
    private HttpVersion httpVersion;
    private ResponseHeader responseHeader;
    private Map<String, ArrayList<String>> body = new HashMap<>();

    public HttpResponse(HttpMethod method, HttpVersion httpVersion, ResponseHeader header) {
        this.method = method;
        this.httpVersion = httpVersion;
        this.responseHeader = header;
    }

    public void responseHeader(DataOutputStream dos, HttpStatusCode code, ResponseHeader responseHeader) {
        try {
            dos.writeBytes(httpVersion.getText() + " " + getStatusCode(code) + " \r\n");
            dos.writeBytes(responseHeader.getAllKeyValueHeader());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getStatusCode(HttpStatusCode code) {
        return code.getCode() + " " + code.getDesc();
    }
}
