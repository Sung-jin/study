package model.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpResponseHelper {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String rootDir = "./webapp";

    public void getResponseByHtml(DataOutputStream dos, HttpRequest httpRequest) {
        try {
            Path requestFilePath = Paths.get(rootDir + httpRequest.requestEndPoint);

            byte[] body = Files.readAllBytes(requestFilePath);
            response200Header(dos, body.length, httpRequest);

            responseBody(dos, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response200Header(DataOutputStream dos, int contentLength, HttpRequest httpRequest) {
        try {
            dos.writeBytes("HTTP/1.1 " + getStatusCode(HttpStatusCode.OK) + " \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
//            dos.writeBytes("Content-Type: " + httpRequest.getContentType() + "\r\n");
            dos.writeBytes("Content-Length: " + contentLength + "\r\n");
            dos.writeBytes("Connection: " + httpRequest.getConnection() + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 " + getStatusCode(HttpStatusCode.REDIRECT) + " \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void responseLogin302Header(DataOutputStream dos, String redirectUrl, boolean success) {
        try {
            dos.writeBytes("HTTP/1.1 " + getStatusCode(HttpStatusCode.REDIRECT) + " \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("Set-Cookie: login=" + success + "; Path=/ \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void response401Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 " + getStatusCode(HttpStatusCode.UNAUTHORIZED) + " \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 " + getStatusCode(HttpStatusCode.NOT_FOUND) + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getStatusCode(HttpStatusCode code) {
        return code.getCode() + " " + code.getDesc();
    }
}
