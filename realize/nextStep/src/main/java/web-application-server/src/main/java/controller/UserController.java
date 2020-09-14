package controller;

import model.http.HttpRequest;
import model.http.HttpStatusCode;
import model.http.header.ResponseField;
import model.http.header.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.RequestHandler;

public class UserController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private static final String rootUrl = "/";

    private UserService userService = new UserService();

    private String requestPath;
    private HttpRequest httpRequest;
    private ResponseHeader responseHeader;
    private HttpStatusCode httpStatusCode;

    public UserController(String requestPath, HttpRequest httpRequest, ResponseHeader responseHeader) {
        this.requestPath = requestPath;
        this.httpRequest = httpRequest;
        this.responseHeader = responseHeader;

        execute();
    }

    private void execute() {
        switch (httpRequest.getMethod()) {
            case GET: {

            }
            case POST: {
                if ("/user/create".equals(requestPath)) {
                    userService.setUser(httpRequest);
                    userService.joinUser(userService.getUser());

                    httpStatusCode = HttpStatusCode.REDIRECT;
                    responseHeader.addHeader(ResponseField.LOCATION.getKey(), rootUrl);
                } else if ("/user/login".equals(requestPath)) {
                    userService.setUser(httpRequest);
                    boolean loginResult = userService.loginUser(userService.getUser());

                    if (!loginResult) log.error("로그인 실패!");

                    httpStatusCode = HttpStatusCode.REDIRECT;
                    responseHeader.addHeader(ResponseField.LOCATION.getKey(), rootUrl);
                    responseHeader.addHeader(ResponseField.SET_COOKIE.getKey(), "login=" + loginResult + "; Path=/");
                } else if ("/user/list".equals(requestPath)) {

                }
            }
        }
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
