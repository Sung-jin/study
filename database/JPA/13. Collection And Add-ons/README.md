## 컬렉션

* JPA 에서 기본으로 제공하는 Collection, List, Set, Map 컬렉션을 지원한다.
    * @OneToMany, @ManyToMany 를 사용해서 1:N, N:M 관계를 매핑할 때 사용할 수 있다.
    * @ElementCollection 을 사용하여 값 타입을 하나 이상 보관할 때 사용할 수 있다.

### 자바 컬렉션의 특징

* Collection - 자바가 제공하는 최상위 컬렉션 / 하이버네이트는 중복을 허용하고 순서를 보장하지 않는다고 가정한다.
* Set - 중복을 허용하지 않는 컬렉션 / 순서를 보장하지 않는다.
* List - 순서가 있는 컬렉션이며, 순서를 보장하고 중복을 허용한다.
* Map - Key/Value 구조로 되어있는 특수한 컬렉션

### JPA 와 컬렉션

* 하이버네이트는 엔티티를 영속 상태로 만들 때 컬렉션 필드를 하이버네이트에서 준비한 컬렉션으로 감싸서 사용한다.
    * 하이버네이트는 컬렉션을 효율적으로 관리하기 위해 엔티티를 영속 상태로 만들 때 원본 컬렉션을 감싸고 있는 내장 컬렉션을 생성해서 이 내장 컬렉션을 사용하도록 참조를 변경한다.
    * 이러한 내장 컬렉션은 원본 컬렉션을 감싸고 있어서 래퍼 컬렉션이라고도 불린다.
    * 하이버네이트는 이러한 특징 때문에 사용할 때 즉시 초기화해서 사용하는 것을 권장한다.
        * Collection<Child> children = new ArrayList<Child>();

```java
@Entity
public class Entity {
    ...
    
    @OneToMany
    private Collection<Child> children = new ArrayList<Child>();
    
    ...
}

...
        
Entity entity = new Entity();
print(entity.getChildren().getClass());
// class java.util.ArrayList

em.persist(entity);
print(entity.getChildren().getClass());
// class org.hibernate.collection.internal.PersistentBag

```

* 래퍼 컬렉션 

| 컬렉션 인터페이스 | 내장 컬렉션 | 중복 허용 | 순서 보관 |
| ---- | ---- | ---- | ---- |
| Collection, List | PersistenceBag | O | X |
| Set | PersistenceSet | X | X |
| List + @OrderColumn | O | O |

#### Collection, List

* 위 컬렉션은 ArrayList 로 초기화하면 된다.
* 중복을 허용하므로, 해당 컬렉션에 객체를 추가할 때 (add) 항상 true 가 리턴된다.
* 컬렉션에 엔티티를 추가해도, 중복된 엔티티를 비교하지 않기 때문에 지연 로딩된 컬렉션을 초기화 하지 않는다.

#### Set

* 중복을 허용하지 않는 컬렉션이며, HashSet 으로 초기화하면 된다.
* Set 은 중복을 허용하지 않으므로, add() 할 때마다 equals() 메소드로 같은 객체가 있는지 비교를 한다.
    * 중복된 객체가 없다면 true 를, 있어서 추가 실패를 하면 false 를 리턴한다.
    * HashSet 은 해시 알고리즘을 사용하므로 hashCode() 도 사용한다.
* 중복을 허용하지 않기 때문에 중복 엔티티를 비교하는데, 지연 로딩된 엔티티가 Set 에 저장된다면 컬렉션을 초기화 한다.

#### List + @OrderColumn

* @OrderColumn 을 추가하면 순서를 보장하는 컬렉션이 된다.
    * 즉, 데이터베이스에 순서 값을 저장해서 조회할 때 사용한다.
* 순서가 존재하는 컬렉션은 데이터베이스에 순서 값도 함께 관리한다.

```java
@OneToMany(mappedBy = "entity")
@OrderCOlumn(name = "SomeOrderName")
private List<Child> children = new ArrayList<Child>();
// Child 테이블에 SomeOrderName 이라는 컬럼 이름에 Child 의 순서가 저장된다.

...

Parent parent = new Parent(...);
em.persist(parent);

Child child1 = new Child(...);
child1.setParent(parent);
parent.addChild(child1);
// SomeOrderName 0
em.persist(child1);

Child child2 = new Child(...);
child2.setParent(parent);
parent.addChild(child2);
// SomeOrderName 1
em.persist(child2);

Child child3 = new Child(...);
child3.setParent(parent);
parent.addChild(child3);
// SomeOrderName 2
em.persist(child3);
```

##### @OrderColumn 의 단점

* @OrderColumn 은 Parent 에서 매핑하므로, child 는 해당 index 값을 모른다.
    * 그래서 child 를 insert 할 때 position 값이 저장되지 않는다.
    * position 값은 parent.child 의 index 값이므로 update SQL 이 추가로 발생한다.
* index 값이기 때문에 리스트의 데이터가 변경된다면 해당되는 인덱스로 업데이트하는 SQL 이 영향받은 객체 수 만큼 동작한다.
* 또한, 시스템상이 아닌 리스트의 중간 데이터를 데이터베이스에서 삭제를 하면 해당 리스트를 조회하는 시점에 중간 인덱스가 null 이 온다.
    * 0, 1, 2, 3 순서를 가진 리스트 데이터 중 1 을 삭제하면 0, 2, 3 이 되고 해당 리스트를 조회하면 [0, null, 2, 3] 이 조회된다.
    * 위와 같은 상황에서 순환 조회를 하면 NullPointException 이 발생한다.

#### @OrderBy

* 데이터베이스의 Order By 를 이용하여 컬렉션을 정렬한다.
* 모든 컬렉션에 사용할 수 있다.

```java
...

@OneToMany(mappdedBy = "entity")
@OrderBy("field desc, id desc")
private Set<Child> children = new HashSet<Child>();

// 위와 같이 엔티티를 셋팅하고, 위 엔티티를 조회하게 되면
// select e.* from ... order by e.field desc, e.id desc;
// 와 같이 order by 뒤의 조건이 붙어서 조회가 된다.
```

* 참고로 하이버네이트는 Set 에 @OrderBy 를 사용하면, 순서를 보장하기 위해 내부에서 LinkedHashSet 을 사용한다.

### @Converter

* 컨버터를 사용하면 엔티티의 데이터를 변환하여 데이터베이스에 저장할 수 있다.

```mysql
CEATE TABLE MEMBER (
    id bigint,
    field varchar(255),
    ...
)
```

```java
@Converter(converter = BooleanToYNConverter.class)
private boolean someField;

// 또는

@Converter(converter = BooleanToYNConverter.class, attribute = "someField")
// 컨버터를 적용할 필드를 명시해주면 클래스 레벨에서도 적용가능하다.
public class Entity {
    ...
}

...

// @Converter(autoApply = true)
// 위와 같이 설정하면 모든 Boolean 타입에 대해 자동으로 컨버터가 적용된다.
@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {
    // 컨버터를 사용하기 위해서는 AttributeConverter 를 구현해야 한다.
    // <JPA 에 사용할 타입, 데이터베이스에 저장된 타입>
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }
    
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
```

### 리스너

* JPA 리스너를 사용하면 엔티티의 생명주기에 따른 이벤트를 처리할 수 있다.

![](../images/13.life%20cycle%20with%20listener.png)

* PostLoad
    * 엔티티가 영속성 컨텍스트에 조회된 직후 또는 refresh 를 호출한 후 (2차 캐시에 저장되어 있어도 호출된다.)
* PrePersist
    * persist() 메소드를 호출해서 엔티티를 영속성 컨텍스트에 관리하기 직전에 호출된다.
    * 식별자 생성 전략을 사용한 경우 엔티티에 식별자는 아직 존재하지 않는다.
    * 새로운 인스턴스를 merge 할 때도 수행된다.
* PreUpdate
    * flush 나 commit 을 호출해서 엔티티를 데이터베이스에 수정하기 직전에 호출된다.
* PreRemove
    * remove() 메소드를 호출해서 엔티티를 영속성 컨텍스트에서 삭제하기 직전에 호출된다.
    * 삭제 명령어로 영속성 전이가 일어날 때도 호출된다.
    * orphanRemoval 에 대해서는 flush/commit 시에 호출된다.
* PostPersist
    * flush 나 commit 을 호출해서 엔티티를 데이터베이스에 저장한 직후에 호출된다.
    * 식별자가 항상 존재한다.
* PostUpdate
    * flush 나 commit 을 호출해서 엔티티를 데이터에비스에 수정한 직후에 호출된다.
* PostRemove
    * flush 나 commit 을 호출해서 엔티티를 데이터베이스에 삭제한 직후에 호출된다.

#### 이벤트 적용 위치

1. 엔티티에 직접 적용

```java
@Entity
public class Entity {
    ...
    
    @PrePersist
    public void prePersist() {
        ...
    }
    
    @PostPersist
    @PostLoad
    @PreRemove
    @PostRemove
    ...
}

// 엔티티에 직접 적용할 때에는 어노테이션으로 지정하면, 해당하는 시점에 해당 메소드가 실행된다.
```

2. 별도의 리스너 등록

```java
@Entity
@EntityListeners(SomeListener.class)
public class Entity {...}

...

public class SomeListener {
    @PrePersist
    public void prePersist(Object obj) {...}
    // 특정 타입이 확실하면 특정 타입을 받을 수 있다.
    // 또한, 반환 타입은 void 로 설정해야 한다.
    
    ...
}
```

3. 기본 리스너

* 모든 엔티티의 이벤트를 처리하려면 META-INF/orm.xml 에 기본 리스너로 등록하면 된다.

```xml
<entity-mappings ...>
    ...
    <entity-listener class="some.listener.path.class" />
</entity-mappings>
```

* 여러 리스너를 등록하였을 때, 이벤트 호출 순서
    1. 기본 리스너
    2. 부모 클래스 리스너
    3. 리스너
    4. 엔티티
* 또한, @EcludeDefaultListeners, @ExcludeSuperclassListeners 와 같은 세밀한 설정에 관한 리스너도 존재한다.

### 엔티티 그래프

* 글로벌 fetch 옵션은 애플리케이션 전체에 영향을 주고 변경할 수 없다는 단점이 존재한다.
    * 그래서 일반적으로 글로벌 fetch 옵션은 FetchType.LAZY 를 사용하고 JPQL 에서 fetch join 을 한다.
    * 하지만, fetch join 의 경우 같은 JPQL 이 중복해서 작성되는 경우가 많을 수 있다.
* 엔티티 그래프 기능은 엔티티 조회시점에 연관된 엔티티들을 함께 조회하는 기능이다.

#### Named 엔티티 그래프

```java
@NamedEntityGraph(name = "Order.withMember", attributeNodes = {
        @NamedAttributeNode("member")
})
// @NamedEntityGraph 를 통해 엔티티 그래프를 정의한다.
// name 은 엔티티 그래프의 이름을 지정한다.
// attributeNodes 는 함께 조회할 속성을 선택한다.
@Entity
public class Order {
    ...
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;
}
// 위와 같은 설정이면 Member 는 LAZY 로 설정하였지만, 엔티티 그래프에서 함께 조회할 속성으로
// Member 를 선택했으므로 이 엔티티 그래프를 사용하면
// Order 를 조회할 때 Member 도 함께 조회된다.
```

#### em.find() 에서 엔티티 그래프 사용

```java
EntityGraph graph = em.getEntityGraph("Order.withMember");

Map hints = new HashMap();
hints.put("javax.persistence.fetchgraph", graph);

Order order = em.find(Order.class, orderId, hints);
```

* Named 엔티티 그래프를 사용하려면 정의한 엔티티 그래프를 em.getEntityGraph 를 통해서 찾아오면 된다.

#### subgraph

* Order -> OrderItem -> Item 과 같이 연관된 테이블들을 조회할 때 subgraph 를 사용하면 된다.

```java
@NamedEntityGraph(name = "Order.withAll", attributeNodes = {
        @NamedAttributeNode("member"),
        @NamedAttributeNode(value = "OrderItems", subgraph = "orderItems")
    },
    subgraphs = @NamedEntityGraph(name = "orderItems", attributeNodes = {
        @NamedAttributeNode("item")
    })
)
@Entity
public class Order {
    ...
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;
    
    @OneToMany(mappedBy = "order", cscade = CascadeType.ALL)
    private List<OrderItme> orderItems = new ArrayList<OrderItem>();
}

@Entity
public class OrderItem {
    ...
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}
```

* Order.withAll 이라는 Named 엔티티 그래프를 정의
* 해당 엔티티 그래프는 Order -> Member, Order -> OrderItem, OrderItem -> Item 의 객체 그래프를 함께 조회한다.
    * 이때 OrderItem -> Item 의 경우 Order 의 객체 그래프가 아니므로 subgraph 로 정의했다.

```java
Map hints = new HashSet();
hints.put("javax.persistence.fetchgraph", em.getEntityGraph("Order.withAll"));
```

```
select o.*, m.*, oi.*, i.*
from Order o
inner join Member m on o.member = m.id
left outer join OrderItem oi on o.order = oi.id
left outer join Item i on oi.item = i.id
where o.id = ?
```

#### JPQL 에서 엔티티 그래프 사용

* em.find() 와 동일하게 힌트만 추가하면 된다.

```java
List<Order> result = em.createQuery("select o from order o where o.id = :orderId", Order.class)
                        .setParameter("orderId", orderId)
                        .setHint("javax.persistence.fetchgraph", em.getEntityGraph("Order.withAll")
                        .getResultList();
```

#### 동적 엔티티 그래프

* 동적 엔티티 그래프를 구성하려면 createEntityGraph() 메소드를 사용하면 된다.
    * public <T> EntityGraph<T> createEntityGraph(Class<T> rootType);
    
```java
EnttiyGraph<Order> graph = em.createEntityGraph(Order.class)
graph.addAtrributeNodes("member");
Subgraph<OrderItem> orderItems = graph.addSubgraph("orderItems");
orderItems.addAtrributeNodes("item");

Map hints = new HashMap();
hints.put("javax.persistence.fetchgraph", graph);

Order order = em.find(Order.class, orderId, hints);
// graph.addSubgraph("orderItems") 메소드를 통해 서브 그래프를 만들었고,
// 해당 서브 그래프는 item 속성을 포함하도록 했다.
```

#### 엔티티 그래프 정리

* ROOT 에서 시작
    * 엔티티 그래프는 항상 조회하는 엔티티의 ROOT 에서 시작해야 한다.
* 이미 로딩된 엔티티
    * 영속성 컨텍스트에 해당 엔티티가 이미 로딩되어 있으면 엔티티 그래프가 적용되지 않는다.
    * 하지만 아직 초기화 되지 않은 프록시에는 엔티티 그래프가 적용된다.
