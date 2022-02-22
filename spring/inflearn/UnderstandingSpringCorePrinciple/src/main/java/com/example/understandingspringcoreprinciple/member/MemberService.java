package com.example.understandingspringcoreprinciple.member;

public interface MemberService {
    void join(Member member);
    Member findMember(Long memberId);
}
