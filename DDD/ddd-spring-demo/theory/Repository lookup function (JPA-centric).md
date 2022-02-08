### 검색을 위한 스펙

* 다양한 검색 조건을 조합하여 조회를 해야 하는 경우가 발생한다
  * 모든 조회 조건마다 쿼리를 만들어서 사용하기에는 쿼리가 너무 많아지고, 100% 커버할 수 없다
  * 이때 스펙을 이용하면 다양한 검색 조건을 조합하여 쿼리할 수 있다

```java
// 스펙 인터페이스

public interface Specification<T> {
    public boolean isSatisfiedBy(T record);
    // record 가 검사 대상이 되는 애그리거트 객체이다
    // 검사 대상 객체가 조건을 충족하면 true 를 리턴한다
}

public class OrererSpec implements Specification<Order> {
    private String ordererId;
    
    public boolean isSatisfiedBy(Orderer record) {
        return record.getOrdererId().getMemer....;
        // 판별식
    }
    // 위와 같이 Specification 을 implements 하고 isSatisfiedBy 를 구현하면, 스펙을 사용할 때 사용된다
}

public class MemoryOrderRepository implements OrderRepository {
    public List<Order> findAll(Specification spec) {
        List<Order> allOrders = findAll();
        return allOrders.stream().filter(order -> spec.isSatisfiedBy(order)).collect(toList());
    }
}
// 레파지토리는 스펙을 전달받아서 애그리거트를 걸러내는 용도로 사용한다
// 해당 레파지토리는 메모리를 조회하는 레파지토리라고 가정한다

Specification<Order> orderSpec = new OrderSpec("id");
List<Order> orders = memoryOrderRepository.findAll(orderSpec);
// 위와 같이 메모리에서 id 라는 orderId 를 가진 모든 객체를 찾는다
```

### 스펙 조합

* 스펙의 장점은 조합이다
  * AND/OR 연산자로 조합해서 새로운 스펙을 만들 수 있다
  * 스펙들끼리 조합하여 더 복잡한 스펙을 만들 수 있다

```java
public class AndSpec<T> implements Specification<T> {
    private List<Specification<T>> specs;
    
    public AndSpecification(Specification<T> ...specs) {
        this.specs = Arrays.asList(specs);
    }
}

Specification<Order> orderSpec = new OrderSpec("id");
Specification<Order> orderDateSpec = new OrderDateSpec(fromDate, toDate);
AndSpec<T> spec = new AndSpec(orderSpec, orderDateSpec);
List<Order> orders = orderRepository.findAll(spec);
// 위와 같이 스펙끼리 조합하여 조회가 가능하다
```

## JPA 를 위한 스펙 구현

* JPA 는 다양한 검색 조건을 조합하기 위해 `CriteriaBuilder`/`Predicate` 를 사용한다

### JPA 스펙 구현

```java
// JPA 레파지토리를 위한 Specification 인터페이스
public interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaBuilder cb);
}

@StaticMetamodel(Order.class)
public abstract class Order_ {
    public static volatile SingularAttribute<Order, OrderNo> number;
    ...
}
// 위와 같이 Order_, Orderer_, MemberId_ 등은 JPA 의 정적 메타 모델로 정의하고,
// 아래와 같이 사용할 수 있다
// 정적 메타 모델 클래스는 대상 모델의 각 프로퍼티와 동일한 이름을 가지는 정적 필드를 정의한다

public class OrdererSpec implements Specification<Order> {
    private String ordererId;
    
    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaBuilder cb) {
        return cb.equal(root.get(Order_.orderer).get(Orderer_.memberId).get(MemberId_.id), ordererId);
        // 정적 메타 모델 대신 문자열로 프로퍼티를 지정할 수 있다
        // cb.equal(root.get("orderer).get("memberId")...
        // 문자열의 경우 오타 가능성이 있으며, 직접 실행 전까지 오타여부를 확인 못할 가능성이 높다
        // 또한, 객체가 변경될 때 체크가 불가능하며, IDE 의 코드 자동 완성 기능을 사용할 수 없는 등의 이슈가 존재한다
    }
}

Specification<Order> ordererSpec = new OrdererSpec("id");
List<Order> orders = orderRepository.findAll(ordererSpec);

// 아래와 같이 스펙 생성 기능을 별도 클래스로 모아서 사용할 수 있다

public class OrderSpecs {
    public static Specification<Order> orderer(String ordererId) {
        return (root, cb) -> cb.equal(
                root.get(Order_.orderer).get(Orderker_.memberId).get(MembmerId_.id), ordererId
        );
    }
    
    public static Specification<Order> between(Date from, Date to) {
        return (root, cb) -> cb.between(root.get(Order_.orderDate), from, to);
    }
}

Specification<Order> betweenSpec = OrderSpecs.between(fromTime, toTime);
```

### AND/OR 스펙 조합을 위한 구현

```java
public class AndSpecification<T> implements Specification<T> {
    ...
  
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder cb) {
        Predicate[] predicates = specs.stream()
              .map(spec -> spec.toPredicate(root, cb))
              .toArray(size -> new Predicate[size]);
        return cb.and(predicates);
    }
}
// AND 스펙 구현

public class OrSpecification<T> implements Specification<T> {
    ...

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder cb) {
        Predicate[] predicates = specs.stream()
              .map(spec -> spec.toPredicate(root, cb))
              .toArray(Predicate[]::new);
        return cb.or(predicates);
    } 
}
// OR 스펙 구현

Specification<Order> specs = new AndSpecification<Order>(
        OrderSpecs.orderer("id"), OrderSpecs.between(fromTime, toTime)
);
// 위와 같이 사용할 수 있다
```

### 스펙을 사용하는 JPA 레파지토리 구현

```java
public interface OrderRepository {
    public List<Order> findAll(Specification<Order> spec);
    ...
}

@Repository
public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManger entityManager;
    
    @Override
    public List<Order> findAll(Specification<Order> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        // 검색 대상이 되는 루트를 생성
        Predicate predicate = spec.toPredicate(root, cb);
        // 검색 조건에 대상과, criteriaBuilder 를 셋팅한다
        // predicate 를 이용하여 쿼리를 동적으로 생성 할 수 있다
        
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(
                cb.desc(root.get(Order_.number).get(OrderNo_.number))
        );
        // 위와 같이 특정 로직에 따라 필요한 조건을 넣어서 사용할 수 있다
        
        TypedQuery<Order> query = entityManager.createQuery(criteriaquery);
        
        return query.getResultList();
    }
}
```

#### 레파지토리 구현 기술 의존

* 도메인 모델은 구현 기술에 의존하지 않아야 하지만, JPA 용 Specification 인터페이스는 toPredicate() 메서드가 JPA 의 root 와 CriteriaBuilder 에 의존하고 있다
  * 결국 레파지토리 인터페이스는 JPA 에 의존하는 모양이 된다
* Specification 구현을 기술에 중립적인 형태로 구현하기 위해서는 많은 부분을 추상화해야 한다
  * 이는 많은 노력이 요구된다
  * 하지만 레파지토리 구현 기술을 변경하는 경우는 거의 드물기 때문에 이점이 거의 없다
* 한 애플리케이션에서 다양한 레파지토리 구현 기술을 사용하고, 각 레파지토리에 대해 동일한 스펙 인터페이스를 사용 해야 하는 경우에만 추상화해야 이점이 있다
