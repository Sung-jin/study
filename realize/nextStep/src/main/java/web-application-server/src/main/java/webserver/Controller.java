package webserver;

import controller.UserController;
import model.http.HttpMethod;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.HttpStatusCode;
import model.http.header.RequestField;
import model.http.header.ResponseField;
import model.http.header.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private static final String rootDir = "./webapp";
    private static Set<String> allStaticFiles;
    static {
        try {
            allStaticFiles = Files
                    .walk(Paths.get(rootDir))
                    .filter(it -> it.toString().substring(1).contains("."))
                    .map(it -> it
                            .toString()
                            .substring(rootDir.length())
                    )
                    .collect(Collectors.toSet());
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }

    public void response(DataOutputStream dos, HttpRequest httpRequest) {
        Optional<String> requestFileResult = Optional.empty();
        ResponseHeader responseHeader = new ResponseHeader();
        HttpStatusCode httpStatusCode;

        try {
            String originalPath = httpRequest.getUri().getPath();
            String requestPath = originalPath.contains(".") ?
                    originalPath.substring(0, originalPath.lastIndexOf("/") + 1) : originalPath;

            if (isNotAuthentication(httpRequest, requestPath)) {
                httpStatusCode = HttpStatusCode.UNAUTHORIZED;

                responseHeader.addHeader(
                        ResponseField.CONTENT_TYPE.getKey(),
                        httpRequest.getHeaderValue(RequestField.ACCEPT).replaceAll(",.*", "")
                );
                requestFileResult = allStaticFiles.stream().filter("/index.html"::equals).findFirst();
                log.error("로그인이 되어 있지 않습니다. 요청 path = {}", requestPath);
            } else if (isRequestStaticFile(originalPath, httpRequest)) {
                httpStatusCode = HttpStatusCode.OK;
                requestFileResult = allStaticFiles.stream().filter(it ->
                        it.equals(originalPath.substring(0, originalPath.lastIndexOf("/")) + "/index.html")
                                || it.equals(originalPath.substring(0, originalPath.lastIndexOf("/")))
                                || it.equals(originalPath.replaceAll("(.*(?=/css))|(.*(?=/js))|(.*(?=/fonts))", ""))
                                || it.equals(originalPath)
                ).findFirst();

                responseHeader.addHeader(
                        ResponseField.CONTENT_TYPE.getKey(),
                        httpRequest.getHeaderValue(RequestField.ACCEPT).replaceAll(",.*", "")
                );
                responseHeader.addHeader(httpRequest.getKeyValueHeader(RequestField.CONNECTION));
            } else {
                if (originalPath.startsWith("/user")) {
                    UserController userController = new UserController(requestPath, httpRequest, responseHeader);
                    httpStatusCode = userController.getHttpStatusCode();
                } else {
                    httpStatusCode = HttpStatusCode.NOT_FOUND;
                    log.error("없는 요청! Request Path : {}, Method : {}", originalPath, httpRequest.getMethod().name());
                }
            }

            HttpResponse httpResponse = new HttpResponse(
                    httpRequest.getMethod(), httpRequest.getHttpVersion(), responseHeader
            );
            httpResponse.responseHeader(dos, httpStatusCode);

            if (requestFileResult.isPresent()) {
                byte[] requestStaticFile = getRequestStaticFile(requestFileResult.get());
                responseHeader.addHeader(
                        ResponseField.CONTENT_LENGTH.getKey(),
                        String.valueOf(requestStaticFile.length)
                );

                dos.writeBytes("\r\n");
                dos.write(requestStaticFile, 0, requestStaticFile.length);
            }

            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private byte[] getRequestStaticFile(String requestPath) throws IOException {
        Path requestFilePath = Paths.get(rootDir + requestPath);
        return Files.readAllBytes(requestFilePath);
    }

    private Boolean isRequestStaticFile(String path, HttpRequest httpRequest) {
        String extension = path.substring(path.lastIndexOf(".") + 1);

        return Arrays.asList("html", "js", "css", "ico", "eot", "svg", "ttf", "woff", "woff2").contains(extension) ||
                (httpRequest.getMethod() == HttpMethod.GET && httpRequest.getQueryKeySet().size() == 0);
    }

    private boolean isNotAuthentication(HttpRequest httpRequest, String requestPath) {
        boolean isNotAuthentication = Arrays.asList("/user/list/").contains(requestPath);
        // 특정 패스에 대한 권한 체크
        // 특정 패스를 요청하였을 경우, 권한 체크가 필요하므로 속해있으면 false 로 초기화

        if (isNotAuthentication && httpRequest.hasKeyInHeader(RequestField.COOKIE.getKey())) {
            isNotAuthentication = !Boolean.valueOf(httpRequest.getCookieValue("login"));
        }

        return isNotAuthentication;
    }
}
