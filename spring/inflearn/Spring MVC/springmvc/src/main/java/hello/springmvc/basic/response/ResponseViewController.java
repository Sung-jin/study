package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

    // @ResponseBody 해당 annotation 이 있으면, 뷰를 찾지 않고 텍스트를 응답한다
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");

        return "response/hello";
    }

    // 반환 값이 void 이고, @Controller 를 사용하고, HttpServletResponse/OutputStream(Writer) 와 같은
    // HTTP 메시지 바디를 처리하는 파라미터가 없으면
    // 경로의 리소스를 참고하여 논리 뷰 이름으로 사용한다
    // 명확하지 않기 때문에 추천하지 않는다
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
