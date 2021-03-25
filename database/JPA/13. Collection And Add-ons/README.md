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
