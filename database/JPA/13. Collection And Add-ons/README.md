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
