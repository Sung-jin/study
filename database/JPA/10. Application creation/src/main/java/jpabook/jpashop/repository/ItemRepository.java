package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오후 9:48
 */

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
            // 위 형태는 저장될 때 @GeneratedValue 가 id 에 있기 때문에, 식별자 값이 자동으로 생성되기에 정상적으로 사용가능하다.
            // id 필드에 @GeneratedValue 가 없다면, 저장되는 시점에 식별자가 없어서 예외가 발생한다.
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}
