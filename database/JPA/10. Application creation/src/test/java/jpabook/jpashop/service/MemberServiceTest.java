package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
// RunWith 에 SpringJUnit4ClassRunner.class 를 지정하면, 테스트가 스프링 컨테이너에서 실행된다.
// 즉, 스프링 프레임워크가 제공하는 @Autowired 같은 기능들을 사용할 수 있다.
@ContextConfiguration(locations = "classpath:appConfig.xml")
// 테스트 케이스를 실행할 떄 사용할 스프링 설정 정보를 지정한다.
@Transactional
// 테스트를 진행하면, 테스트에 사용된 데이터가 데이터베이스에 저장된다.
// 저장이 된 데이터로 인해 테스트가 실패할 수도 있다.
// 그리고 테스트용 데이터인데 데이터베이스에 남아있는건 맞지 않다.
// @Transactional 을 테스트에서 사용하면, 테스트마다 트랜잭션을 시작/종료를 하고, 종료시 강제로 롤백한다.
// 테스트에서 @Transactional 어노테이션을 사용하는 곳은 보통 비즈니스 로직이 있는 서비스 계층에서 사용한다.
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    /*
    Given/When/Then 의 경우 특별한 기능을 하지 않지만, 테스트를 이해하기 쉽게 도와준다.
    Given - 테스트할 상황을 설정
    When - 테스트 대상을 실행
    Then - 결과를 검증
     */

    @Test
    public void 회원가입() throws Exception {

        //Given
        Member member = new Member();
        member.setName("kim");

        //When
        Long saveId = memberService.join(member);

        //Then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    // expected 에 예외를 지정하면, 해당 테스트에서 지정한 예외가 발생하면 테스트가 성공한 것으로 된다.
    public void 중복_회원_예외() throws Exception {

        //Given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //When
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다.

        //Then
        fail("예외가 발생해야 한다.");
    }


}
