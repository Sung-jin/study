package com.example.understandingspringcoreprinciple.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // MemberRepository 라는 인터페이스를 의존한다
    // 위 인터페이스를 MemberMemberRepository 라는 구현체로 할당하면서 의존한다
    // 즉, MemberServiceImpl 은 추상화와 구현체 모두 의존하고 있다 -> DIP 에 위반한다

    private final MemberRepository memberRepository;

    @Autowired
    // 기존에 별도로 의존관계 주입을 AppConfig 를 통하 였으나, AutoAppConfig 에는 의존관계 주입에 대한 내용이 없다
    // 즉, @Autowired 라는 어노테이션을 통해 의존관계 주입을 할 수 있다
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
