package webserver;

import model.http.HttpMethod;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.HttpStatusCode;
import model.http.header.RequestField;
import model.http.header.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
                    .filter(it -> it.toString().endsWith(".html"))
                    .map(it -> it
                            .toString()
                            .substring(rootDir.length())
                    )
                    .collect(Collectors.toSet());

            allJsEndpoint = Files
                    .walk(Paths.get(rootDir))
                    .filter(it -> it.toString().endsWith(".js"))
                    .map(it -> it
                            .toString()
                            .substring(rootDir.length())
                    )
                    .collect(Collectors.toSet());

            allCssEndpoint = Files
                    .walk(Paths.get(rootDir))
                    .filter(it -> it.toString().endsWith(".css"))
                    .map(it -> it
                            .toString()
                            .substring(rootDir.length())
                    )
                    .collect(Collectors.toSet());
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }

//    private HttpResponseHelper httpResponseHelper = new HttpResponseHelper();

    public void response(DataOutputStream dos, HttpRequest httpRequest) {
        try {
            ResponseHeader responseHeader = new ResponseHeader();
            String originalPath = httpRequest.getUri().getPath();
            String requestPath = originalPath.contains(".") ?
                    originalPath.substring(0, originalPath.lastIndexOf("/") + 1) : originalPath;

            if (isRequestStaticFile(originalPath, httpRequest)) {
                String test = originalPath.substring(0, originalPath.lastIndexOf("/")) + "/index.html";
                Optional<String> result = getRequestFileTypeSet(
                        originalPath.contains(".") ? originalPath.substring(originalPath.lastIndexOf(".") + 1) : ""
                ).stream().filter(it ->
                        it.equals(originalPath.substring(0, originalPath.lastIndexOf("/")) + "/index.html")
                                || it.equals(originalPath)
                ).findFirst();

                responseHeader.addHeader(httpRequest.getKeyValueHeader(RequestField.CONTENT_TYPE));
                responseHeader.addHeader(httpRequest.getKeyValueHeader(RequestField.CONTENT_LENGTH));
                responseHeader.addHeader(httpRequest.getKeyValueHeader(RequestField.CONNECTION));

                HttpResponse httpResponse = new HttpResponse(
                        httpRequest.getMethod(), httpRequest.getHttpVersion(), responseHeader
                );

                httpResponse.responseHeader(dos, HttpStatusCode.OK);

                if (result.isPresent()) {
                    responseBody(dos, getByteRequestFile(result.get()));
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

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

    private byte[] getByteRequestFile(String requestPath) throws IOException {
        Path requestFilePath = Paths.get(rootDir + requestPath);

        return Files.readAllBytes(requestFilePath);
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    private Boolean isRequestStaticFile(String path, HttpRequest httpRequest) {
        String extension = path.substring(path.lastIndexOf(".") + 1);


        return Arrays.asList("html", "js", "css").contains(extension) ||
                (httpRequest.getMethod() == HttpMethod.GET && httpRequest.getQueryKeySet().size() == 0);
    }

    private Set<String> getRequestFileTypeSet(String extension) {
        switch (extension) {
            case "js": {
                return allJsEndpoint;
            }
            case "css": {
                return allCssEndpoint;
            }
            default: {
                return allHtmlEndpoint;
            }
        }
    }

    private boolean hasContain(List<String> compareList, String target) {
        return compareList.contains(target);
    }
}
