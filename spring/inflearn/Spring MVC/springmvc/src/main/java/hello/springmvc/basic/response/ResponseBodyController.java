package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
// @ResponseBody 를 클래스 단위로 설정이 가능하다
// @Controller + @ResponseBody = @RestController
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
        // HttpServletResponse 객체를 통해 HTTP 메시지 바디에 직접 OK 응답 메시지를 전달
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
        // ResponseEntity 에 HTTP 메시지의 헤더, 바디 정보와 응답 코드를 설정해서 리턴할 수 있다
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
        // @ResponseBody 를 사용하면 view 를 사용하지 않고 HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할 수 있다
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
        // HTTP 메시지 컨버터를 통해 JSON 형식으로 변환되어 반환된다
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
        // ResponseEntity 는 HTTP 응답 코드를 설정할 수 있지만, @ResponseBody 를 사용하면 이런 것을 설정하기 까다롭다
        // @responseStatus 어노테이션을 사용하여 응답 코드를 설정할 수 있다
    }
}
