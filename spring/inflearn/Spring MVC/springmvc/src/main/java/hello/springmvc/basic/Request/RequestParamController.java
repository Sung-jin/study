package hello.springmvc.basic.Request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age = {}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody // view 조회를 하지 않고 String 을 응답할 수 있다
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ) {
        log.info("username = {}, age = {}", memberName, memberAge);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
            // 파라미터 이름과 같은 변수이면 별도 지정하지 않아도 알아서 매핑된다
    ) {
        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        // 요청 파라미터와 이름이 같은 단순 타입(string, int, Integer...) 이면 @requestParam 없이도 매핑이 가능하다
        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            // null 과 "" 은 다르다. 즉, "xxx?username=" 형태로 호출하면 정상 호출이 된다
            @RequestParam(required = false) int age
            // required 는 false 이지만, int (기본형) 에 null 이 들어갈 수 없어서 500 에러가 발생한다
            // @RequestParam(required = false) Integer age
            // 와 같이 선언해야 정상적으로 동작한다
    ) {
        // required 의 기본값은 true
        // age 에 대한 파라미터값은 없어도 문제가 없지만, username 에 대한 파라미터가 없으면
        // 400 BadRequest 를 리턴한다
        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            // null 뿐만 아니라 "xxx?username=" 로 호출하여도 (빈 문자) defaultValue 가 들어간다
            @RequestParam(required = false, defaultValue = "-1") int age
    ) {
        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        // 파라미터의 값이 1개 초과일 경우 multiValueMap 으로 사용해야 한다
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        // @ModelAttribute 를 사용하면, 해당 객체를 생성하고, 파라미터의 이름으로 해당 객체(여기서는 HelloData)의 해당되는 프로퍼티를 찾는다
        // 해당되는 프로퍼티의 setter 를 호출하여 해당 값으로 바인딩한다
        // 하지만, 이런 바딩인되는 작업이 실패하게 되면 바인딩 오류(BindException)가 발생한다
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("userData = {}", helloData);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        // @ModelAttribute 도 생략이 가능하다
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("userData = {}", helloData);

        return "ok";
    }

    /*
    파라미터 매핑 어노테이션을 생략하였을 때,
    String, int, Integer... 등과 같은 단순한 타입은 @RequestParam
    나머지는 @ModelAttribute (argument resolver 로 지정해둔 타입을 제외) 로 매핑이 된다
     */
}
