package webserver;

import model.http.HttpParser;
import model.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private HttpParser httpParser = new HttpParser();

    private Controller controller = new Controller();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = httpParser.parse(in);

            controller.response(dos, httpRequest);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
