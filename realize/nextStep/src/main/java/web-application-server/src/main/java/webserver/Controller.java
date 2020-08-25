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
import java.util.*;
import java.util.stream.Collectors;

import static util.HttpRequestUtils.parseCookies;

class Controller {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String rootDir = "./webapp";
    private static Set<String> allHtmlEndpoint;
    private static Set<String> allJsEndpoint;
    private static Set<String> allCssEndpoint;
    static {
        try {
            allHtmlEndpoint = Files
                    .walk(Paths.get(rootDir))
                    .filter(it -> it.endsWith(".html"))
                    .map(it -> it
                            .toString()
                            .substring(rootDir.length(), it.toString().lastIndexOf("/"))
                    )
                    .collect(Collectors.toSet());

            allJsEndpoint = Files
                    .walk(Paths.get(rootDir))
                    .filter(it -> it.endsWith(".js"))
                    .map(it -> it
                            .toString()
                            .substring(rootDir.length())
                    )
                    .collect(Collectors.toSet());

            allCssEndpoint = Files
                    .walk(Paths.get(rootDir))
                    .filter(it -> it.endsWith(".css"))
                    .map(it -> it
                            .toString()
                            .substring(rootDir.length())
                    )
                    .collect(Collectors.toSet());
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }

    private HttpResponseHelper httpResponseHelper = new HttpResponseHelper();

    public void response(DataOutputStream dos, HttpRequest httpRequest) {
//        Set<String> requestFileTypeSet = getRequestFileTypeSet(
//                httpRequest.requestEndPoint.substring(
//                        httpRequest.requestEndPoint.lastIndexOf(".")
//                )
//        );
//
//        if (requestFileTypeSet.contains())
//
//        if (allStaticEndpoint.contains(httpRequest.requestEndPoint)) {
//            if (hasContain(Arrays.asList("/user/list", "/user/list.html"), httpRequest.requestEndPoint)) {
//                Map<String, String> cookies = parseCookies(httpRequest.getCookie());
//                if (Boolean.parseBoolean(cookies.get("login"))) {
//                    httpRequest.requestEndPoint = "/user/list.html";
//                    httpResponseHelper.getResponseByHtml(dos, httpRequest);
//                } else {
//                    httpResponseHelper.response401Header(dos, "/index.html");
//                }
//            } else {
//                httpResponseHelper.getResponseByHtml(dos, httpRequest);
//            }
//        } else {
//            switch(httpRequest.requestEndPoint) {
//                case "/user/create" : {
//                    UserService userService = new UserService();
//                    userService.setUser(httpRequest);
//                    userService.joinUser(userService.getUser());
//
//                    httpResponseHelper.response302Header(dos, "/index.html");
//                    break;
//                }
//                case "/user/login" : {
//                    UserService userService = new UserService();
//                    userService.setUser(httpRequest);
//                    boolean loginResult = userService.loginUser(userService.getUser());
//
//                    if (!loginResult) log.error("로그인 실패!");
//
//                    httpResponseHelper.responseLogin302Header(dos, "/index.html", loginResult);
//                    break;
//                }
//                default: {
//                    httpResponseHelper.response404Header(dos);
//                }
//            }
//        }
    }

    private Set<String> getRequestFileTypeSet(String extension) {
        switch (extension) {
            case "html" : {
                return allHtmlEndpoint;
            }
            case "js" : {
                return allJsEndpoint;
            }
            case "css" : {
                return allCssEndpoint;
            }
            default: {
                return Collections.emptySet();
            }
        }
    }

    private boolean hasContain(List<String> compareList, String target) {
        return compareList.contains(target);
    }
}
