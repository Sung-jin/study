package com.example.understandingspringcoreprinciple;

import com.example.understandingspringcoreprinciple.member.Grade;
import com.example.understandingspringcoreprinciple.member.Member;
import com.example.understandingspringcoreprinciple.member.MemberService;
import com.example.understandingspringcoreprinciple.member.MemberServiceImpl;

public class MemberApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);

        memberService.join(member);

        Member findMember = memberService.findMember(1L);

        System.out.println("new Member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
