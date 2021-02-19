package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오전 1:07
 */
@Service
// <context:component-scan> 에 의해 스프링 빈으로 등록된다.
@Transactional
// 해당 어노테이션이 붙어있는 클래스나 메소드에 대해서 트랜잭션을 적용한다.
// 외부에서 호출할 때, 트랜잭션을 시작하고 종료하면 트랙잭션을 커밋한다.
// 트랜잭션 도중 예외가 발생하면 트랜잭션 롤백을 진행한다.
// 참고로 Unchecked 예외만 롤백한다.
// checked 예외가 발생해도 롤백하고 싶다면, @Transactional(rollbackFor = Exception.class) 형태로 지정해준다.
public class MemberService {

    @Autowired
    // 스프링 컨테이너가 적절한 스프링 빈을 주입해준다.
    MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        // 중복 검증이 있지만, 멀티 스레드 환경에서는 문제가 발생할 수 있다.
        // 해당 이슈는 회원명 컬럼에 유니크 제약을 거는 등의 방법으로 해결을 할 수는 있다.
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
