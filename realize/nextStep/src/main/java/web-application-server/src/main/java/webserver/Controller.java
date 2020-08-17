package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final String rootDir = "./webapp";

    public void getResponseByEndPoint(DataOutputStream dos, String path) {
        try {
            Path requestFilePath = Paths.get(rootDir + path);
            List<Path> htmlPaths = getAllHtmlFiles();

            byte[] body = {};
            if (htmlPaths.contains(requestFilePath)) {
                body = Files.readAllBytes(requestFilePath);
                response200Header(dos, body.length);
            } else {
                response404Header(dos);
            }

            responseBody(dos, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Path> getAllHtmlFiles() throws IOException {
        return Files.walk(Paths.get(rootDir))
                .filter(file -> file.toString().contains(".html"))
                .collect(Collectors.toList());
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + 0 + "\r\n");
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
}
