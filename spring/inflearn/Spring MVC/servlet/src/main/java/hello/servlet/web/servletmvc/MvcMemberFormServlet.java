package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        dispatcher.forward(req, resp);
        // 다른 서블릿이나 JSP 로 이동할 수 있는 기능
        // 서버 내부에서 다시 호출이 발생한다
        // WEB-INF 하위의 자원들은 외부에서 직접 호출해도 호출되지 않는다
        // 이는 WAS 등의 서버에서의 룰이고, 서버 내부에서 호출이 가능하다
    }
}
