## 성능 최적화

### 즉시 로딩과 N+1

```java
@Entity
public class Member {
    ...
    
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<Order>();
    
    ...
}

@Entity
public class Order {
    ...
    
    @ManyToOne
    private Member member;
    
    ...
}

...

em.find(Member.class, 1L);

/*
select m.*, o.*
from Member m
outer join orders o on m.id = o.member

위와 같은 관계에서 Member 를 조회하면 위의 쿼리가 실행되면서,
order 의 데이터들을 join 을 통해 함께 조회한다.
*/

List<Member> members = em.createQueryt("select m from Member m", Member.class).getResultList();

/*
select * from Member

JPQL 을 통해 조회를 하면, 지연 로딩에 대한 설정은 신경쓰지 않고 JPQL 의 SQL 만 이용하여 생성한다.
그 후, SQL 의 실행 결과인 멤버 엔티티에서 order 의 값들은 즉시 로딩이고,
해당 값이 있다면 즉시 로딩이기에 다음과 같은 쿼리가 해당하는 수 만큼 order 를 조회한다.

select * from orders where member = 1;
select * from orders where member = 2;
select * from orders where member = 3;
...
*/
```

* 위와 같이 기준 엔티티를 조회하고, 해당 데이터의 즉시 로딩이 설정된 필드들의 수 만큼 다시 쿼리를 날리는 현상을 N+1 문제라고 한다.

### 지연 로딩과 N+1

```java
// 즉시 로딩 N+1 에서 모든 조건은 같고 Member 의 FetchType.EAGER -> LAZY 로만 변경한다.
@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
private List<Order> orders = new ArrayList<Order>();
```

* 위와 같이 설정하고, member 를 조회하면 LAZY 이기 때문에 member 를 조회하는 시점에 order 에 대해서 N+1 현상은 발생하지 않는다.
* 하지만 해당 필드를 사용할 때, 결국 초기화를 하는 과정에서 N+1 현상이 발생할 수 있다.
    * .get(0)/.size() 등과 같은 로직은 하나의 SQL 로 처리할 수 있다.
    * 하지만, 컬렉션에 대해 모든 조회를 통한 로직이 들어있다면, 사용하는 곳 마다 SQL 이 발생하고, 결론적으로 N+1 과 같은 현상이 발생한다.

```java
for (Member member: members) {
    print(member.getOrders().size());
    // for loop 의 실행마다 해당 order 를 조회하는 SQL 이 발생한다.
}
```

### 패치조인 사용

* N+1 문제를 해결하는 가장 일반적인 방법은 페치 조인을 사용하는 것이다.

```
select m from Member m join fetch m.orders

select m.*, o.* from Member m
inner join orders o on m.id = o.member
```

### 하이버네이트 @BatchSize

* 하이버네이트가 제공하는 어노태이션
* 연관된 엔티티를 조회할 떄 지정한 size 만큼 SQL 의 IN 절을 사용해서 조회한다.
    * size 보다 큰 값이 조회된다면, 그 수 만큼 나눠서 조회한다.
    * ex) 조회 결과 10, size 5 이면 2번 조회한다.
* 또한, LAZY 로 설정한 경우, size 수 만큼 미리 조회되어 있다가 그보다 큰 값의 데이터가 사용될 때 in 을 통해 다음 범위만큼 조회한다.
* `<property name="hibernate.default_batch_fetch_size" value="5"/>` 와 같이 설정할 수 있다.

```java
@Entity
public class Member {
    ...
    
    @org.hibernate.annotations.BatchSize(size = 5)
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<Order>();
    
    ...
}

/*
select * from Orders
where member in (
    ?, ?, ?, ?, ?
)
 */
```

### 하이버네이트 @Fetch(FetchMode.SUBSELECT)

* 해당 어노테이션을 사용하면 연관된 데이터를 조회할 때 서브 쿼리를 사용해서 N+1 문제를 해결한다.

```java
@Entity
public class Member {
    ...
    
    @org.hibernate.annotations.Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<Order>();
    
    ...
}

...

List<Member> members = em.createQueryt("select m from Member m where m.id > 10", Member.class).getResultList();

/*
select o from Orders o
where o.member in (
    select m.id from Member m
    where m.id > 10
)
 */
```

### N+1 정리

* 즉시 로딩과 지연 로딩 중 추천하는 방법은 지연 로딩만 사용하는 것이다.
    * 즉시 로딩의 경우 N+1 문제 뿐 아니라 비즈니스 로직에서 필요하지 않는 엔티티를 모두 로딩하는 상황이 자주 발생하게 된다.
    * 또한, 즉시 로딩이 연속으로 발생해서 전혀 예상하지 못한 SQL 이 실행되는 등의 이슈로 성능 최적화가 어려워질 수 있다.
    * 결론적으로 모두 지연 로딩으로 설정하고, 즉시 로딩이 필요한 곳에는 패치 조인을 사용하는게 좋다.
* 기본 전략
    * @OneToOne, @ManyToOne : 즉시 로딩
    * @OneToMany, @ManyToMany : 지연 로딩

## 읽기 전용 쿼리의 성능 최적화

* 영속성 컨텍스트에 관리되면 여러 기능들이 많지만, 그러한 혜택들이 발현되기 위해서는 더 많은 메모리가 사용된다.
    * 1차 캐시
    * 변경 감지
* 이러한 기능들은 읽기에만 사용될 엔티티들에는 적용될 필요가 없다.
    * 즉, 읽기 전용으로 엔티티를 조회하면 그만큼 메모리 사용량을 최적화할 수 있다.

### 읽기 전용 쿼리

* 스칼라 타입으로 조회
    * 스칼라 타입은 영속성 컨텍스트가 결과를 관리하지 않는다.
    * select e.id, e.field1, e.field2 ... from Entity e
* 읽기 전용 쿼리 힌트 사용
    * 하이버네이트 전용 힌트인 org.hibernate.readOnly 를 사용하면 읽기 전용으로 조회할 수 있다.
    * 단, 읽기 전용으로 조회하면 스냅샷이 없으므로 엔티티를 수정해도 데이터베이스에 적용되지 않는다.

```java
TypeQuery<Entity> query = em.createQuery("select e from Entity e", Entity.class);
query.setHint("org.hibernate.readOnly", true);
```

* 읽기 전용 트랜잭션 사용
    * 스프링 프레임워크의 트랜잭션 어노테이션에 읽기 전용 모드로 설정한다.
    * @Transactional(readOnly = true)
    * 위 옵션을 true 로 설정하면 스프링 프레임워크가 하이버네이트 세션의 플러시 모드를 MANUAL 로 설정한다.
    * MANUAL 로 설정되면, 강제로 플러시를 호출하지 않는한 플러시가 일어나지 않는다.
    * 즉, 트랜잭션을 커밋해도 영속성 컨텍스트를 플러시 하지 않게되고, 변경 내용은 적용되지 않는다.
* 트랜잭션 밖에서 읽기
    * 트랜잭션 범위 외에서 엔티티를 조회하면, 영속성 컨텍스트에 의해 관리 받지 않으므로 읽기 전용으로 조회된다.
    
```java
@Transactional(readOnly = true)
// 읽기 전용 트랜잭션
// 플러시를 작동하지 않도록 설정
public List<Entity> find() {
    return em.createQuery("select e from Entity e", Entity.class)
        .setHint("org.hibernate.readOnly", true)
        // 읽기 전용 쿼리 힌트
        // 엔티티를 읽기 전용으로 조회
        .getResultList();
}
```

## 배치 처리

* 수백만 건의 데이터를 배치처리를 할 때, 일반적인 방식으로 엔티티를 조회하면 영속성 컨텍스트에 아주 많은 엔티티가 쌓이면서 메모리 부족 현상이 발생할 수 있다.
* 배치 처리는 적절한 단위로 영속성 컨텍스트를 초기화해야 한다.
* 2차 캐시를 사용한다면, 2차 캐시에 엔티티를 보관하지 않도록 주의해야 한다.

### JPA 등록 배치

* 수많은 데이터를 한 번에 등록할 경우, 영속성 컨텍스트에 엔티티가 계속 쌓이지 않게 해야 한다.
    * 일정 단위마다 영속성 컨텍스트의 엔티티를 데이터베이스에 플러시하고 영속성 컨텍스트를 초기화해야 한다.

```java
EntityManager em = entityMangerFactory.createEntityManager();
EntityTransaction tx = em.getTransaction();

tx.begin();

for (int i = 0; i < 100000; i++) {
    Entity entity = new Entity(i);
    em.persist(entity);
    
    if (i % 100s == 0) {
        em.flush();
        em.clear();
        // 100 건마다 플러시와 영속성 컨텍스트 초기화
    }
}

tx.commit();
em.close():
```

### JPA 페이징 처리

```java
tx.begin();

int pageSize = 100;
for(int i = 0; i < 100; i++) {
    List<Entity> entities = em.createQuery("select e from Entity e", Entity.class)
                            .setFirstResult(i * pageSize)
                            .setMaxResults(pageSize)
                            .getResultList();
    
    for (Entity entity: entities) {
        entity.setField(i);
        // 비지니스 로직 실행
    }
    
    em.flush();
    em.clear();
    // 페이징 단위를 100개로 조회하여 처리
}
tx.commit();
em.close();
```

* JPA 는 JDBC 커서를 지원하지 않고, 커서를 사용하려면 하이버네이트 세션을 사용해야 한다.

### 하이버네이트 scroll 사용

* 하이버네이트 scroll 이라는 이름으로 JDBC 커서를 지원한다.

```java
Session session = em.unwrap(Session.class);
tx.begin();

ScrollableResults scroll = session.createQuery("select e from Entity e")
                                .setCacheMode(CacheMode.IGNORE) // 2차 캐시 기능 x
                                .scroll(ScrollMode.FORWARD_ONLY);

iunt count = 0;

while(scroll.next()) {
    // scroll 의 next() 메소드를 사용하면 다음 엔티티를 사용할 수 있게 된다.
    Entity e = (Entity) scroll.get(0);
    e.setField(e.getField() + 1000);
    
    count++;
    if (count % 100 == 0) {
        session.flsuh();
        session.clear();
    }
}
tx.commit();
session.close();
```

### 하이버네이트 무상태 세션 사용

* 무상태 세션은 영속성 컨텍스트와 2차 캐시를 사용하지 않는다.
* 엔티티를 수정하려면 무상태 세션이 제공하는 update() 메소드를 직접 호출해야 한다.

```java
SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
StatelessSession session = sessionFactory.openStatelessSession();
Transaction tx = session.beginTransaction();
ScrollableResults scroll = session.createQuery("select e from Entity e")
                                .scroll();

while(scroll.next()) {
    Entity e = (Entity) scroll.get(0);
    e.setField(e.getField() + 1000);
    session.update(e);
    // 직접 update 를 호출한다.
}
tx.commit();
session.close();
```

## ㄴ삐 znjfl glsxm tkdyd

* JPA 는 데이터베이스 SQL 힌트 기능을 제공하지 않는다.
* SQL 힌트를 직접 사용하려면 하이버네이트를 직접 사용해야 한다.
    * JPA 구현체에게 제공하는 힌트가 아니며, 데이터베이스 벤더에게 제공하는 힌트이다.
* SQL 힌트는 하이버네이트 쿼리가 제공하는 addQueryHint() 메소드를 사용한다.

```java
Session session = em.unwrap(Session.class);

List<Entity> results = session.createQuery("select e from Entity e")
                            .addQuerytHint("FULL (ENTITY") // SQL hint 추가
                            .list();

/*
select
    /*+ FULL (MEMBER) */ e.id, e.field ...
    이러한 힌트가 추가되어서 쿼리가 실행된다.
from
    Entity e
 */
```

* 오라클 외에 SQL 힌트를 제공하려면 각 방언에 org.hibernate.dialect.Dialect 에 있는 다음 메소드를 오버라이딩해서 기능을 구현해야 한다.

```java
public String getQueryHintString(String query, List<String> hints) {return query;}
```

## 트랜잭션을 지원하는 쓰기 지연과 성능 최적화

### 트랜잭션을 지원하는 쓰기 지연과 성능 최적화

```
INSERT(entity1);
INSERT(entity2);
INSERT(entity3);
...

commit();
```

* 위와 같이 여러건의 insert query 와 1번의 commit 을 호출한은 것 보다, 모든 insert query 를 모아서 한번에 보내는게 네트워크 비용 측면에서 유리하다.
* JDBC 가 제공하는 SQL 배치 기능을 사용하면 SQL 을 모아서 데이터베이스에 한번에 보낼 수 있다.
* 하지만 이러한 기능을 사용하기 위해서는 많은 부분이 수정이 필요하고, 기존 로직에 적용하기 힘들 수 있다.
    * 이러한 여러건의 데이터를 변경, 추가하는 경우에는 배치 전략을 사용할 수 있다.
    * 참고로 배치 전략은 구현체마다 조금씩 다르다.

```xml
<property name="hibernate.jdbc.batch_size" value="50"/>
```

* 위와 같이 설정하면, 최대 50개의 **같은 sql** 을 모아서 전송한다.
    * 중간에 다른 sql 이 실행되면, 이전에 모아뒀던 같은 sql 들을 실행하고, 다음 배치를 실행한다.
    
### 트랜잭션을 지원하는 쓰기 지연과 애플리케이션 확장성

* 트랜잭션을 커밋해서 영속성 컨텍스트를 플러시 하기 전까지는 데이터베이스에 데이터를 등록, 수정, 삭제하지 않는다.
    * 즉, 이 시점에는 데이터베이스 row 에 lock 을 걸지 않는다.
    * 이러한 점 때문에 데이터베이스 테이블 row 에 lock 이 걸리는 시간을 최소화 할 수 있다.
    
```
update(entity);
someFunc1();
someFunc2();
commit();
```

* JPA 를 사용하지 않고 직접 위의 상황을 SQL 로 사용한다면, update() 시에 업데이트를 진행하므로 데이터베이스 테이블 row 에 lock 이 걸린다.
* commit() 을 통해 lock 이 해제될 때 까지 someFunc1, someFunc2 에 계속 걸려있는다.
