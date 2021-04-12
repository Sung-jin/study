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
