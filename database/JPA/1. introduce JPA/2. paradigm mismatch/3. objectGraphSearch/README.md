## 객체 그래프 탐색

![table model](../../../images/1.3.1%20obejct%20relationship.png)

* 객체에서 연관된 다른 객체를 참조를 사용해서 찾는것을 객체 그래프 탐색이라 한다.

```java
member.getOrder().getOrderItem().item()...;
// 위 그림에서 어떤 객체를 기준으로 하든, 해당 객체에 연관관계를 가진 모든 객체에 참조를 통해 접근할 수 있다.

memberDao.getOrder().getOrderItem()...;
// 위 탐색의 경우에는 가능하다는 보장이 없다.
// Dao 에 의해 가져와진 연관관계 데이터까지만 접근이 가능하다.
// 즉, SQL 을 다루게 되면 실행되는 SQL 에 따라 객체 그래프를 어디까지 탐색할 수 있는지 정해지게된다.
// 비즈니스 로직에서 함부로 객체 그래프를 탐색할 수 없게 되기 때문에 이는 큰 제약사항이다.
// 객체 그래프를 어디까지 탐색이 가능한지 확인하기 위해서는 DAO 를 열여서 SQL 을 직접 확인해야만 한다.
// 이는 SQL 에 의존적인 개발이면서 엔티티가 SQL 에 논리적으로 종속되어 발생하는 문제이다.
// 이를 해결하기 위해서 모든 연관관계 데이터들을 메모리에 올려두는것도 현실성이 없다.
// 결국에는 연관관계마다 join 을 하는 메소드들을 계쏙 만들게 되어 버린다.

memberDao.getMember();
memberDao.getMemberWithTeam();
memberDao.getWithOrder();
memberDao.getWithOrderWithOrderItem();
...
```

## JPA 와 객체 그래프 탐색

* JPA 를 사용하게 되면 객체 그래프를 마음껏 탐색할 수 있다.
* JPA 는 연관된 객체를 사용하는 시점에 적절한 SELECT SQL 을 실행한다.
    * 해당 기능은 실제 객체를 사용하는 시점까지 데이터베이스 조회를 미루기 때문에 **지연 로딩**이라 한다.
    * JPA 는 연관된 객체를 즉시 함께 조회할지 아니면 실제 사용되는 시점에 지연해서 조회할지를 설정을 통해 정의할 수 있다.
        * 해당 설정은 FetchType.EAGER (즉시) / FetchType.LAZY(지연) 을 통해 설정이 가능하다.
        * @ManyToOne/@OneToOne 의 기본값을 EAGER 이다.
        * @OneToMany/@ManyToMany 의 기본값을 LAZY 이다.

```java
Member member = jpa.find(Member.class, id);
// member select sql 이 실행됨.

Order order = member.getOrder();
order.getOrderName();
// order 객체를 사용하는 시점에 order 를 조회하는 select sql 이 실행됨.
``` 
