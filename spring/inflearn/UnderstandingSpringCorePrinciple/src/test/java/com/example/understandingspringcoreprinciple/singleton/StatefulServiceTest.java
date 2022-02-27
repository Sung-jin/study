package com.example.understandingspringcoreprinciple.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // thread A: A 사용자
        statefulService1.order("userA", 10000);
        // thread B: B 사용자
        statefulService2.order("userB", 20000);

        // thread A: A 사용자 주문 금액 조회
        int priceA = statefulService1.getPrice();
        System.out.println("price = " + priceA);

        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }
    // 다른 변수(다른 스레드)에서 각자 메서드를 실행하였지만,
    // 공유되는 필드를 사용하므로, 해당 필드는 덮어씌워진다
    // 이러한 공유 필드는 문제의 원인을 찾기도 어렵고, 확인하기도 어렵다

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}