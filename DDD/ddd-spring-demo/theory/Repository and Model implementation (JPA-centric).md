### 모듈 위치

![img.png](image/4_repository1.png)

* 위와 같이 레파지토리를 구현한 클래스는 인프라스트럭처 영역에 속한다

### 레파지토리 기본 기능 구현

* 레파지토리의 기본 기능은 다음과 같다
    1. 아이디로 애그리거트 조회
    1. 애그리거트 저장
* 인터페이스는 애그리거트 루트를 기준으로 작성한다
    * 주문 애그리거트는 Order 라는 루트 엔티티와 OrderLine, Orderer, ShippingInfo 등 다양한 객체를 포함한다
    * Order 가 루트이므로, Order 를 기준으로 레파지토리를 작성한다

```java
public interface OrderRepository {
    public Order findById(OrderNo no);
    public void save(Order order);
    
    public List<Order> findByOrdererId(String ordererId, int startRow, int size);
    // id 가 아닌 다른 형태로 조회 하는 등의 별도 쿼리를 작성할 수 있다
    
    public void delete(Order order);
}

pakcage com.example.order.infra;

@Repository
public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Order findById(OrderNo id) {
        return entityManager.find(Order.class, id); }
    
    @Override
    public void save(ORder order) {
        entityManager.save(order);
    }
    
    @Override
    public List<Order> findByOrdererId(String ordererId, int startRow, int fetchSize) {
        TypedQuery<Order> query = entityManager.createQuery(
            "select o from Order o " +
            "..."
        );
        query.setParameter(...);
        
        return query.getResultList();
    }
    // 위와 같이 쿼리를 직접 장성하여 요청할 수 있다
    
    @Override
    public void remove(Order order) {
        entityManager.remove(order);
        // 삭제 처리
    }
}

public class ChangeOrderService {
    @Transactional
    public void changeShippingInfo(OrderNo no, ShippingInfo newShippingInfo) {
        Order order = orderRepository.findById(no);
        order.changeShhippingInfo(newShippingInfo);
        // 해당 메서드는 스프링 프레임워크 트랜잭션 관리 기능을 통해 트랜잭션 범위에서 실행된다
        // 메서드 실행이 긑나면 트랜잭션을 커밋하면서, 해당 태른잭션 범위 안에서 변경된 객체의 데이터를
        // JPA 는 확인한 후 UPDATE 쿼리를 실행한다
    }
}
```

### 매핑 구현

#### 엔티티와 벨류 기본 매핑 구현

* 애그리거트와 JPA 매핑을 위한 기본 규칙은 다음과 같다
  * 애그리거트 루트는 엔티티이므로 @Entity 로 매핑 설정을 한다
  * 한 테이블에 엔티티와 벨류 데이터가 같이 있다면, 벨류는 @Embeddable 로 매핑 설정을하고 벨류 타입 프로퍼티는 @Embedded 로 매핑 설정을 한다

![img.png](image/4_repository2.png)

* 루트 엔티티는 Order 이며, Orderer 와 ShippingInfo 는 벨류이고, Address 와 Receiver 는 ShippingInfo 에 포함된다

#### 기본 생성자

* 엔티티와 벨류의 생성자는 객체를 생성할 때 필요한 것을 전달받는다
  * Receiver 벨류 타입과 같이 생성 시점에 수취인 이름과 연락처를 생성자 파라미터로 전달받고 더이상 수정 할 필요가 없는 불변 객체이므로, setter 는 제공되지 않는다
* JPA 의 @Entity 와 @embeddable 로 클래스를 매핑하려면 기본 생성자를 제공해야 한다
  * JPA 프로바이더는 DB 에서 데이터를 읽어와 매핑된 객체를 생성할 때 기본 생성자를 사용해서 객체를 생성한다
  * 이러한 기술적인 제약으로 Receiver 와 같은 불변 타입은 기본 생성자가 필요 없어도 기본 생성자를 추가해줘야 한다
    * kotlin 에서는 타입을 val 로 한 생성자로 정의하면 no argument constructor + no setter 도 존재 같이 커버된다
    * 참고로 하이버네이트는 클래스를 상속한 프록시 객체를 이용해서 지연 로딩을 구연하고, 프록시 클래스에서 상위 클래스의 기본 생성자를 호출할 수 있으므로 지연 로딩 대상이 되는 @Entity 와 @Embeddable 의 기본 생성자는 private 이 아닌 protected 이여야 한다
  
#### 필드 접근 방식 사용

* JPA 는 필드와 메서드의 두 가지 방식으로 매핑을 처리할 수 있다
  * 메서드 방식을 사용하기 위한 프로퍼티인 get/set 메서드를 구현해야 한다
  * 엔티티에 프로퍼티를 위한 공개 get/set 메서드를 추가하면 도메인의 의도가 사라지고 객체가 아닌 데이터 기반으로 엔티티를 구현할 가능성이 높아진다
  * 특히, set 은 외부에서 객체의 값을 변경할 수 있으므로, set 메서드 대신 메서드의 의도가 잘 나타내는 기능을 제공해야 한다
* JPA 구현체인 하이버네이트는 @Access 를 이용해서 명시적으로 접근 방식을 지정하지 않으면 @Id 나 @EmbeddedId 가 어디에 위치했느냐에 따라 접근 방식을 결정한다
  * id 어노테이션이 필드에 위치하면 필드 접근자 방식을, get 메서드에 존재하면 메서드 접근 방식을 선택한다
  
```java
@Entity
@Access(AccessType.PROPERTY)
public class Order {
    ...
    
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    public OrderState getState() {
        return state;
    }
    
    public void setState(OrderState state) {
        this.state = state;
    }
    // 위와 같이 공개 setter 는 외부에서 마음대로 값을 변경 할 수 있다
  
    public void cancel() {
        this.state = CANCEL;
    }
    // 위와 같이 별도의 의도가 드러나는 메서드를 제공해야 한다
}
```
  
#### AttributeConverter 를 이용한 벨류 매핑 처리

* int, long, String, LocalDate 와 같은 타입은 DB 테이블의 한 개 컬럼과 매핑된다
* 벨류 타입의 프로퍼티를 한 개 컬럼에 매핑해야 될 때도 존재한다

```java
// JPA 2.0 이전 버전에서는 컬럼과 매핑하기 위한 프로퍼티를 따로 추가하고 get/set 메서드에서 실제 벨류 타입과 변환 처리를 해야 했다

// JPA 2.1 에서는 DB 컬럼과 벨류 사이의 변환 코드를 모델에 구현하지 않고 AttributeConverter 를 사용해서 변환을 처리할 수 있다
public interface AttributeConverter<X, Y> {
    // X 는 타입 파라미터의 벨류 타입이며, Y 는 DB 타입이다
    public Y convertToDatabaseColumn (X attribute);
    // 벨류 타입을 DB 컬럼 값으로 변환하는 기능을 구현
    public X convertToEntityAttribute (Y dbData);
    // DB 컬럼 값을 벨류로 변환하는 기능을 구현
}
// 위와 같이 인터페이로 벨류 타입과 컬럼 데이터 간의 변환 처리를 위한 기능을 정의하고 있다

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Money money) {
        return momeny != null ? null : money.getValue();
    }
    
    @Override
    public Money convertToEntityAttribute(Integer value) {
      return momeny != null ? null : new Money(value);
    }
}

@Entity
public class Order {
    ...
  
    @Column(name = "total_amounts")
    @Convert(converter = MoneyConverter.class)
    // 위와 같이 적용 할 converter 를 지정하면, 해당 컨버터로 DB 와 값 타입 간에 컨버팅이 진행된다
    private Money totalAmounts;
}
```

#### 벨류 컬렉션 - 별도 테이블 매핑

* Order 엔티티는 한개 이상의 OrderLine 을 가질 수 있다
  * 이렇게 여러개의 벨류 타입을 가지는 경우에는 컬렉션을 이용해서 나타낼 수 있으며, 별도 테이블에 보관한다
  * 벨류 컬렉션을 저장하는 `ORDER_LINE` 테이블은 외부키를 이용해서 엔티티에 해당하는 `PURCHASE_ORDER` 테이블을 참조한다
  * 이 외부키는 커렉션이 속할 엔티티를 의미하고, List 타입의 컬렉션은 인덱스 값이 필요하므로 `ORDER_LINE` 테이블에는 인덱스 값을 저장하기 위한 컬럼(id) 가 존재한다
  * 벨류 컬렉션은 별도 테이블로 매핑할 때는 @ElementCollection 과 @CollectionTable 을 함께 사용한다
  
#### 벨류 컬렉션 - 한 개 컬럼 매핑

* 벨류 컬렉션을 별도 테이블이 아닌 한 개 컬럼에 저장할 수 있다
  * AttributeConvert 를 사용하면 벨류 컬렉션을 한 개 컬럼에 쉽게 매핑할 수 있다
  * 단, AttributeConvert 를 사용하려면 벨류 컬렉션을 표현하는 새로운 벨류 타입을 추가해야 한다
  
```java
public class EmailSet {
    private Set<Email> emails = new HashSet<>();
    
    private EmailSet() {}
    private EmailSet(Set<Email> emails) { this.emails.addAll(emails); }
  
    public Set<Email> getEmails() {
        return Collections.unmodifiableSet(this.emails);
    }
}

@Converter
public class EmailSetConverter implements attributeConverter<EmailSet, String> {
    @Override
    public String convertToDatabaseColumn(EmailSet attribute) {
          return attribute == null ? null :
                  attribute.getEmails()
                          .stream()
                          .map(Email::toString)
                          .collect(Collectors.joining(","));
    } 
    
    @Override
    public EmailSet convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        else {
            String[] emails = dbData.split(",");
            Set<Email> emailSet = Arrays
                    .stream(emails)
                    .map(value -> new Email(value)).collect(toSet());
            return new EmailSet(emailSet);
        }
    }
}

public class SomeEmails {
    @cColumn(name = "emails")
    @Convert(convert = EmailSetConverter.class)
    private EmailSet emails;
}
```

#### 벨류를 이용한 아이디 매핑

* 식별자는 문자열이나 숫자와 같은 기본 타입이기 때문에 주로 String/Long 타입을 이용해서 식별자를 매핑한다
* 기본 타입으로 식별자를 설정할 수 있으나, 식별자라는 의미를 부각시키기 위해 별도 식별자 벨류 타입을 만들어서 설정할 수 있다ㅏㅏㅏㅏ
  * 벨류 타입을 식별자로 매핑하면 @Id 대신 @EmbeddedId 어노테이션을 이용해야 한다
  * 벨류 타입으로 식별자를 구현하면 식별자에 기능을 추가할 수 있다
  
```java
@Entity
public class Order {
    @EmbeddedId
    // 벨류 타입 식별자는 @EmbeddedId 을 사용해야 한다
    private OrderNo number;
}

@Embeddable
public class OrderNo implements Serializable {
    // JPA 에서 식별자는 Serializable 타입이어야 하므로 식별자로 사용될 벨류 타입은 해당 인터페이스를 상속받아야 한다
    @Column(name = "order_number")
    private String number;
    
    public boolean is2ndGeneration() {
        return number.startWith("N");
    }
    // 식별자에 대한 별도의 기능을 추가할 수 있다
}
```

#### 별도 테이블에 저장하는 벨류 매핑

* 애그리거트에서 루트 엔티티를 뺀 나머지 구성요소는 대부분 벨류이다
  * 별도 테이블에 저장한다고 해서 엔티티가 아니다
* 하나의 에그리거트에서 루트 엔티티를 제외한 객체가 벨류가 아닌 엔티티가 맞다면, 다른 애그리거트인지 확인해야 한다
  * 자신만의 독자적인 라이프사이클을 가진다면 다른 애그리거트일 가능성이 높다
  * 예를들어서 상품과, 상품 리뷰이다 (각자 별도로 생성/수정이 되고, 변경 주체도 다르다)
* 애그리거트에 속한 객체가 벨류인지 엔티티인지 구분하는 방법은 고유 식별자를 가지는지 여부를 확인하는 것이다
  * 식별자를 찾을 때 매핑되는 테이블의 식별자를 애그리거트 구성요소의 식별자와 동일한 것으로 착각하면 안된다
  * 이는 별도 테이블로 저장되면서 PK 가 있다고 해서 테이블과 매핑되는 애그리거트 구성요소가 고유 식별자를 가지는 것은 아니다
  
```java
@Entity
@SecondaryTable(
        name = "article_content",
        // 저장할 테이블을 지정
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id")
        // 벨류 테이블에서 엔티티 테이블로 조인할 때 사용할 컬럼을 지정
)
public class Article {
    @Id
    private Long id;
    private STring title;
    
    @AttributeOverrides({
            @AttributeOverride(name = "content", column = @Column(table = "article_content")),
            @AttributeOverride(name = "contentType", column = @Column(table = "article_content"))
            // 해당 벨류 데이터가 저장된 테이블 이름을 지정
    })
    private ArticleContent content;
}

Article article = entityManager.find(Article.class, 1L);
// 다음과 같이 조회할 때 @SecondaryTable 로 매핑된 article_content 테이블을 조인하여 조회한다
```

#### 벨류 컬렉션을 @Entity 로 매핑하기

* JPA 는 @Embeddable 타입의 클래스 상속 매핑을 지원하지 않는다
  * 상속 구조를 가지는 벨류 타입을 사용하려면 @Embeddable 대신 @Entity 를 이용한 상속 매핑으로 처리해야 한다
  
```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "image_type")
public abstract class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @COlumn(name = "image_id")
    private Long id;
    
    ...
  
    public abstract String getURL();
    public abstract boolean hasThumbnail();
    public abstract String getThumbnailURL();
}

@Entity
@DiscriminatorValue("II")
public class InternalImage extends Image { ... }

@Entity
@DiscriminatorValue("EI")
public class ExternalImage extends Image { ... }

@Entity
public class Product {
    @EmbeddedId
    private ProductId id;
    ...
  
    @OneToMany(casecade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
    // 위와 같이 벨류 타입이지만, @Entity 로 지정하고 상속받은 객체를 사용할 수 있다
    
    ...
  
    public void changeImages(List<Image> newImages) {
        images.clear();
        // clear() 를 실행할 때, 프록시 객체이면 해당 객체를 찾기 위한 객체 수 만큼의 select 와
        // 실제 객체를 하나하나 삭제하는 delete from xxx ... 가 객체 수 만큼 실행된다
        // 연관된 객체의 수가 많다면 성능에 문제가 발생할 수 있다
        
        // @Embeddable 타입에 대한 컬렉션의 clear() 메서드 호출 시 컬렉션에 속한 객체를 로딩하지 않고 한번의 delete 쿼리로 삭제 처리를 수행할 수 있다
        // 애그리거트의 특성을 유지하면서 해당 문제를 해소하려면 결국 상속을 포기하고 @Embeddable 로 매핑된 단일 클래스로 구현해야 한다
        // 이경우 다형을 포기하고 if-else 로 구현해야 하는 경우가 발생한다
        images.addAll(newImages);
    }
}
```

#### ID 참조와 조인 테이블을 이용한 단방향 M:N 매핑

* 애그리거트 간 집한 연관은 성능상의 이유로 피해야 하지만, 필요로 하다면 단방향 집합 연관을 적용할 수 있다

```java
public class Product {
    @ElementCollection
    @CollectionTAble(name = "product_category", joinColumns = @JoinColumn(name = "product_id"))
    private Set<CategoryId> categoryIds;
}
```

* 위와 같이 Product 에서 Category 로의 단방향 M:N 연관을 ID 참조 방식으로 구현이 가능하다
  * @ElementCollection 을 사용하였기 때문에, 해당 데이터가 삭제되면 조인 테이블의 데이터도 같이 삭제된다
  
### 애그리거트 로딩 전략

* 애그리거트의 루트가 로딩될 때, 해당 객체와 객체에 속한 모든 객체가 완전한 상태이여야 한다
  * 조회 시점에 애그리거트를 완전한 상태가 되도록 하려면 애그리거트 루트에서 연관 매핑의 조회 방식을 즉시 로딩(FetchType.EAGER) 으로 설정하면된다
  * 즉시 로딩으로 설정하면 애그리거트 루트를 구할 때 연관된 구성요소를 DB 에서 함께 읽어온다
* 즉시 로딩 방식으로 설정하면 애그리거트 루트를 로딩하는 시점에 애그리거트에 속한 모든 객체를 함께 로딩할 수 있지만, 다음과 같은 문제가 발생할 수 있다
  * 

```java
public class Product {
    ...
  
    @OneToMany(
            casecade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Image> images = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Option> options = new ArrayList<>();
}

entityManager.find(Product.class, 1L);

/*
위와 같이 설정된 애그리거트를 조회하면 다음과 같은 쿼리가 발생한다

select
    p.product_id, ... img.product_id, img.xxx, ... opt.product_id, opt.xxx ...
from
    product p
    left outer join image img on p.product_id = img.product_id
    left outer join product_option opt on p.product_id = opt.product_id
where p.product_id = ?

해당 쿼리는 카타시안 조인을 사용하는데, 이는 쿼리 결과에 중복을 발생시킨다
product 의 image 가 2개, option 이 2개라면, 해당 쿼리의 결과로 구해지는 행의 수는 4개이다
즉, product 의 정보는 4번 중복되고, image 와 option 의 테이블은 2번 중복된다

조회 성능 때문에 즉시 로딩 방식을 사용하지만, 이러한 경우에는 즉시 로딩 방식 때문에 조회 성능이 나빠지는 문제가 발생한다
 */
```

* 애그리거트는 개념적으로 하나여야 한다
  * 하지만, 루트 엔티티를 로딩하는 시점에 애그리거트에 속한 객체를 모두 로딩해야 하는 것은 아니다
  * 애그리거트가 완전해야 하는 이유는 상태를 변경하는 기능을 실행할 때와 표현 영역에서 애그리거트의 상태 정보를 보여줄 때 필요하기 때문이다
    * 표현 영역에서의 필요성은 조회 전용 기능을 구현하는 방식을 사용하는 좋은 방법이 존재한다
    * 상태 변경 기능을 실행하기 위해 조회 시점에 즉시 로딩을 이용해서 애그리거트를 오나전한 상태로 로딩할 필요가 없는데, JPA 는 트랜잭션 범위 내에서 지연 로딩을 허용하기 때문에 상태를 변경하는 시점에 필요한 구성 요소만 로딩해도 문제가 되지 않는다
    * 일반적인 애플리케이션은 상태를 변경하는 기능보다 조회하는 기능을 실행하는 빈도가 훨씬 높다
    * 이러한 이유로 애그리거트 내의 모든 연관을 즉시 로딩으로 설정할 필요는 없으며, 애그리거트에 맞게 적절하게 설정하면 된다
  
### 애그리거트의 영속성 전파

* 애그리거트가 완전한 상태여야 한다는 의미는 조회뿐 아니라 저장/삭제할 때도 하나로 처리해야 함을 의미한다
  * 저장 메서드는 애그리거트 루트만 저장하면 안되고 애그리거트에 속한 객체를 저장해야 한다
  * 삭제 메서드는 애그리거트 루트뿐만 아니라 애그리거트에 속한 모든 객체를 삭제해야 한다
* @Embeddable 매핑 타입의 경우 함께 저장되고 삭제되므로 `cascade` 속성을 추가로 설정하지 않아도 된다
* @Entity 타입에 대한 매핑은 `cascade` 속성을 사용해서 저장과 삭제 시에 함께 처리되도록 설정해야 한다
  * @OneToOne, @OneToMany 의 경우 `cascade` 속서으이 기본값이 없으므로, 별도로 설정해줘야 한다

> @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)

### 식별자 생성 기능

* 식별자 생성 방식
  1. 사용자가 직접 지정 (직접 입력한 이메일 등)
  1. 도메인 로직으로 생성
    * 생성 규칙이 존재한다면, 해당 규칙으로 식별자 생성
    * 주문번호를 고객 id 와 타임스탬프로 구성하는 등의 방법이 존재함
  1. DB 를 이용한 일련번호 사용
    * @GeneratedValue 등과 같은 JPA 기능 이용
