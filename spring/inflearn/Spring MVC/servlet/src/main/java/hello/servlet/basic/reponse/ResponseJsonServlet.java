package hello.servlet.basic.reponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Content-Type: application/json
        // application/json 스펙상 utf-8 을 사용하도록 정의되어 있으므로, application/json;charset=utf-8 이라고 전달하는 것은 의미가 없다
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        // 응답으로 json 을 응답하기 위해서는 application/json 을 지정해야 하고, 기본적으로 characterSet 을 지정해줘야 한다
        // 또한 객체를 json 으로 응답을 주기 위해 Jackson 라이브러리의 objectMapper.writeValueAsString() 을 사용하여 JSON 문자로 변경할 수 있다

        HelloData helloData = new HelloData();
        helloData.setUsername("hello");
        helloData.setAge(20);

        // {"username": "hello", "age": 20}
        String result = objectMapper.writeValueAsString(helloData);
        resp.getWriter().write(result);
    }
}
