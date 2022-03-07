package com.example.understandingspringcoreprinciple.web;

import com.example.understandingspringcoreprinciple.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
//    private final MyLogger myLogger;
    // myLogger 의 scope 은 request 이며, request 는 요청이 오는 시점에 생성된다
    // 하지만 LogDemoController 는 스프링이 실행 되면서 의존 관계 주입이 되어야 한다 (@RequiredArgsConstructor)
    // 즉, 없는 빈을 이용해서 생성하려고 하면서 에러가 발생한다
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;
    // proxyMode = ScopedProxyMode.TARGET_CLASS 로 인해 가짜 프록시 클래스를 만들어서 해당 객체를 주입한다

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }
}
