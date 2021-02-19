package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오전 1:08
 */
@Repository
// 해당 어노테이션이 있으면, <context:component-scan> 에 의해 스프링 빈으로 자동 등록된다.
// 또한, JPA 전용 예외를 스프링이 추상화한 예외로 변환해준다.
// ex) (결과가 없을 때 발생하는 예외) NoResultException -> EmptyResultDataAccessException
public class MemberRepository {

    @PersistenceContext
    // 스프링이나 J2EE 컨테이너를 사용하면 컨테이너가 엔티티 매니저를 관리하고 제공한다.
    // 순수 자바 환경에서는 엔티티 매니저 팩토리에서 엔티티 매니저를 직접 생성해서 사용해야 한다.
    // 엔티티 매니저 주입
    // @PersistenceUnit 을 사용하면 엔티티 매니저 팩토리를 주입받을 수 있다.
    EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
