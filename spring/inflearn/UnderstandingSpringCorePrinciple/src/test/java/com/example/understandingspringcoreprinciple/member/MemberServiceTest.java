package com.example.understandingspringcoreprinciple.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService = new MemberServiceImpl();

    @Test
    void join() {
        // given
        Member member = new Member(1L, "MemberA", Grade.VIP);

        // when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
    // 수동으로 하나하나 확인 할 필요 없이 테스트 프레임워크를 통해 쉽게 테스트를 할 수 있다
    // 스프링과 같은 컨테이너 없이 순수 자바로 테스트를 하는 것을 단위 테스트라고 한다
}
