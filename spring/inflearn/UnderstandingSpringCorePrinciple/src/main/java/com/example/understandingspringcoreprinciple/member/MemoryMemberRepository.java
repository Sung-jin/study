package com.example.understandingspringcoreprinciple.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    // HashMap 은 동시성 이슈가 밣생할 수 있음
    // ConcurrentHashMap 을 사용할 수 있다

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
