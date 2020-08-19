package webserver;

import model.http.HttpRequest;
import model.http.HttpResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class Controller {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String rootDir = "./webapp";
    private static List<String> htmlEndpoint;
    static {
        try {
            htmlEndpoint = Files
                    .walk(Paths.get(rootDir))
                    .map(it -> it.toString().substring(rootDir.length()))
                    .collect(Collectors.toList());
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }

    private HttpResponseHelper httpResponseHelper = new HttpResponseHelper();

    public void response(DataOutputStream dos, HttpRequest httpRequest) {
        boolean test = htmlEndpoint.contains(httpRequest.getRequestEndPoint());
        if (htmlEndpoint.contains(httpRequest.getRequestEndPoint())) {
            httpResponseHelper.getResponseByHtml(dos, httpRequest);
        } else {
            switch(httpRequest.getRequestEndPoint()) {
                case "/user/create" : {
                    UserService userService = new UserService();
                    userService.setUser(httpRequest);
                    httpResponseHelper.response302Header(dos, "/index.html");
                }
                default: {
                    httpResponseHelper.response404Header(dos);
                }
            }
        }
    }
}
