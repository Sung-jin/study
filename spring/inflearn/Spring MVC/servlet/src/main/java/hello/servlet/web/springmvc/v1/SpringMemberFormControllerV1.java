package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
// 해당 어노테이션이 존재하면 자동으로 스프링 빈으로 등록된다 (내부에 @Component 가 존재하여 컴포넌트 스캔의 대상이 된다)
// RequestMappingHandlerMapping 에서 매핑 정보로 사용된다 (@Controller, @RequestMapping 가 class 레벨에 있는 경우)
// -> @Component @RequestMapping
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
