package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@Controller 의 반환값이 String 이면 뷰 이름으로 인식되고, 해당 이름의 뷰를 찾고 뷰가 렌더링 된다
@RestController 의 경우 뷰를 찾는게 아닌 HTTP 메시지 바디에 바로 입력을 한다
 */
@Slf4j
@RestController
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass());
    // @Slf4j 를 선언하면 위 로그 설정을 하지 않아도 로그를 사용할 수 있다

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

//        log.trace("trace my log = " + name);
        // 자바의 경우 위와 같이 String 을 더하면, 각각 해당되는 원본 값을 별도로 가지면서 ('trace my log = ', 'Spring')
        // 더해진 'trace my log = Spring' 이 별도로 할당되어 쓸모없는 리소스가 사용된다
        // 하지만 log level 이 trace 보다 높으면  ("xxx = {}", value) 의 경우에는 'xxx = {}' 를 가지고 있다가 사용되는 그 시점에 치환이 될 뿐
        // 변수에 대해서 실제 리소스가 사용되지 않는다

        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);
        // trace > debug > info > warn > error

        return "ok";
    }
}
