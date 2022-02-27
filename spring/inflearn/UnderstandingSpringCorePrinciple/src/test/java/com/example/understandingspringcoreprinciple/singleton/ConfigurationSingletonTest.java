package com.example.understandingspringcoreprinciple.singleton;

import com.example.understandingspringcoreprinciple.AppConfig;
import com.example.understandingspringcoreprinciple.member.MemberRepository;
import com.example.understandingspringcoreprinciple.member.MemberServiceImpl;
import com.example.understandingspringcoreprinciple.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        assertThat(memberRepository1).isSameAs(memberRepository2);
        assertThat(memberRepository).isSameAs(memberRepository1);
        assertThat(memberRepository).isSameAs(memberRepository2);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        // bean = class path.to.AppConfig$$EnhancerBySpringCGLIB$$91cc1543
        // 순수한 클래스라면 bean = class path.to.AppConfig 으로 출력되어야 한다
        // 이는 바이트코드 조작 라이브러리를 사용해서 해당 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한다
        // (CGLIB 는 원본의 자식 타입이므로, AppConfig 타입으로 조회가 가능하다)
        // 이러한 형태로 상속을 받고, 빈을 등록할 때 기존에 등록된 빈을 호출하면 등록된 빈을 리턴하는 형태로 동작할 것이다
        // 이러한 작업 덕분에 싱글톤이 보장된다
    }
}
