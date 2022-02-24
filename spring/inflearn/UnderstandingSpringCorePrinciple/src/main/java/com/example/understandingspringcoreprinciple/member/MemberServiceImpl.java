package com.example.understandingspringcoreprinciple.member;

public class MemberServiceImpl implements MemberService {
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // MemberRepository 라는 인터페이스를 의존한다
    // 위 인터페이스를 MemberMemberRepository 라는 구현체로 할당하면서 의존한다
    // 즉, MemberServiceImpl 은 추상화와 구현체 모두 의존하고 있다 -> DIP 에 위반한다

    private final MemberRepository memberRepository;

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
}
