package webserver;

import model.http.HttpMethod;
import model.http.HttpParser;
import model.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static HttpParser httpParser = HttpParser.getInstance();
//    TODO - 객체 생성하는 형태로 변경해야 함. -> 스레드이므로.

    private Controller controller = new Controller();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
//        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
//                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            ArrayList<String> headers = new ArrayList<>();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (line.equals("")) break;
                headers.add(line);
            }

            HttpRequest httpRequest = httpParser.parse(headers);

            if (httpRequest.getMethod() != HttpMethod.GET) {
                httpRequest.setBody(br);
            }

            controller.response(dos, httpRequest);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
