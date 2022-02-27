package com.example.understandingspringcoreprinciple;

import com.example.understandingspringcoreprinciple.member.MemberRepository;
import com.example.understandingspringcoreprinciple.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // @Configuration 에도 @Component 가 있으며, 제외 설정이 없으면 충돌이 발생할 수 있음
        // 현재는 확인을 위해서 기존의 예제 코드를 남기기 위해 Configuration 을 제외함
)
// @Component 라고 붙은 모든 컴포넌트를 등록
public class AutoAppConfig {
    // 기존의 AppConfig 파일과 다르게 @Bean 이라는 별도 설정과 의존관계 설정에 대한 정보가 없다
    // 이러한 정보가 없기 때문에, 등록한 컴포넌트에서 의존관계 주입에 대한 설정을 해결해야 한다

    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
