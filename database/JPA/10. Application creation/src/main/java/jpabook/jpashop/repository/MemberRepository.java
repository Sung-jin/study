package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오전 1:08
 */

/*
스프링 데이터 JPA 설정하기 전
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
*/

/*
public interface JpaRepository<T, ID extends Serializable> extends pagingAndSortingRepository<T, ID> {

}
// 스프링 데이터 JPA 는 간단한 CRUD 기능을 공통으로 처리하는 JpaRepository 인터페이스를 제공한다.
// 위 인터페이스를 상속받고, 제네릭에 엔티티 클래스와 엔티티 클래스의 식별자 타입을 지정하면 사용할 수 있다.
*/

public interface MemberRepository extends JpaRepository<Member, Long> {}
/*
주요 메소드

save(S) - 새로운 엔티티는 저장하고 이미 있는 엔티티는 수정한다.
          엔티티에 식별자 값이 없으면(null) 새로운 엔티티로 판단해서 EntityManager.persist() 를 호출한다.
          식별자 값이 있으면 이미 있는 엔티티로 판단해서 EntityManager.merge() 를 호출한다.
          필요하다면 스프링 데이터 JPA 의 기능을 확장해서 신규 엔티티 판단 전략을 변경할 수 있다.
delete(T) - 엔티티 하나를 삭제한다. 내부에서 EntityManager.remove() 를 호출한다.
findOne(ID) - 엔티티 하나를 조회한다. 내부에서 EntityManager.find() 를 호출한다.
getOne(ID) - 엔티티를 프록시로 조회한다. 내부에서 EntityManger.getReference() 을 호출한다.
findAll(...) - 모든 엔티티를 조회한다. sort/pageable 조건을 파라미터로 제공할 수 있다.

JpaRepository 공통 인터페이스를 사용하면 일반적인 CRUD 를 해결할 수 있다.
*/
