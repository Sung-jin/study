package jpabook.start;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
@Slf4j
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        // 엔티티 매니저 팩토리 생성
        // 해당 엔티티 매니저를 통해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있다.
        // 데이터소스를 유지하면서 데이터베이스와 통신한다.
        // 여러 스레드가 동시에 접근해도 안전하므로, 다른 스레드 간에 공유해도 된다.
        // JPA 는 엔티티 팩토리를 생성할 때 커넥션풀을 생성하는데, J2SE 환경에서 사용하는 방법이다.

        EntityManager em = emf.createEntityManager();
        // 엔티티 매니저 생성
        // 엔티티 매니저는 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드간에 공유하거나 재사용하면 안 된다.
        // 데이터베이스 연결이 꼭 필요한 시점까지 커넥션을 얻지 않는다.
        // 보통 트랜잭션을 시작할 때 커넥션을 획득한다.

        EntityTransaction tx = em.getTransaction();
        // 트랜잭션 기능 획득
        // JPA 를 사용하기 위해서는 항상 트랜잭션 안에서 데이터를 변경해야 하며, 그러지 않을 경우 예외가 발생한다.

        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public static void logic(EntityManager em) {

        Long id = 1L;
        Member member = new Member();
        member.setId(id);
        member.setName("오성진");
        member.setAge(2);

        //등록
        em.persist(member);

        //수정
        member.setAge(27);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        log.info("find member = {}, age = {}", findMember.getName(), findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
        log.info("members size : {}", members.size());
        // Java Persistence Query Language
        // SQL 을 추상화한 객체지향 쿼리 언어이다.
        // SQL 과 문법이 거의 유사하며 SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 등을 사용할 수 있다.
        // JPQL 은 엔티티 객체를 대상으로 쿼리하며, SQL 은 데이터베이스 테이블 대상으로 쿼리한다.

        //삭제
        em.remove(member);
    }
}
