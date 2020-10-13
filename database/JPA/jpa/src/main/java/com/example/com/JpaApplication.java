package com.example.com;

import com.example.com.entity.Member;
import com.example.com.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@EntityScan
@SpringBootApplication
public class JpaApplication {
//    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
//    static MemberService memberService = new MemberService();
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
    static EntityManager em = emf.createEntityManager();
    static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);

        Member member = new Member();
        member.setName("오성진");
        member.setAge(27);

//        memberService.saveMember(member);
        try {
            tx.begin();
            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

}
