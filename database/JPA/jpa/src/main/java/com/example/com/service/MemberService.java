package com.example.com.service;

import com.example.com.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@Slf4j
@Service
public class MemberService {
    @Autowired
    private EntityManager entityManager;
    // 해당 엔티티 매니저를 통해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있다.
    // 데이터소스를 유지하면서 데이터베이스와 통신한다.
    // 엔티티 매니저는 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드간에 공유하거나 재사용하면 안 된다.

    private EntityTransaction tx = entityManager.getTransaction();
    // JPA 를 사용하기 위해서는 항상 트랜잭션 안에서 데이터를 변경해야 하며, 그러지 않을 경우 예외가 발생한다.

    public Member getMemberById(Long id) {
        Member member = entityManager.find(Member.class, id);
        entityManager.close();
        return member;
    }

    public List<Member> getAllMember() {
        List<Member> members = entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
        entityManager.close();
        return members;
    }

    public Boolean saveMember(Member member) {
        try {
            tx.begin();
            entityManager.persist(member);
            tx.commit();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            tx.rollback();

            return false;
        } finally {
            entityManager.close();
        }
    }

    public Boolean removeMember(Member member) {
        try {
            tx.begin();
            entityManager.remove(member);
            tx.commit();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            tx.rollback();

            return false;
        } finally {
            entityManager.close();
        }
    }
}
