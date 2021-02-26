package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오전 1:08
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    /*
    기존의 save(), findOne(), findAll() 의 경우 상속받은 JpaRepository 가 제공한다.
    findByName 의 경우 메소드 이름을 분석하여 적절한 쿼리를 실행시켜 준다.
     */
    List<Member> findByName(String name);
}
