## 요구사항

* 회원 기능
    * 회원 등록
    * 회원 조회
* 상품 기능
    * 상품 등록
    * 상품 수정
    * 상품 조회
* 주문 기능
    * 상품 주문
    * 주문 내역 조회
    * 주문 취소
* 기타 요구사항
    * 상품의 종류는 도서, 음반, 영화
    * 상품을 카테고리로 구분할 수 있다.
    * 상품 주문 시 배송 정보를 입력할 수 있다.

## 도메인 설계

![](../images/10.erd.png)

![](../images/10.uml.png)

* 회원/주문/상품의 관계
    * 회원은 여러 상품을 주문 할 수 있다.
    * 한번 주문할 때 여러 상품을 선택할 수 있다.
        * 주문-상품 -> n:m
        * n:m 을 주문-주문상품-상품(물품) 으로 1:n n:1 관계로 변경하였다.
* 상품 분류
    * 상품은 도서/음반/영화로 구분된다.
    * '상품' 이라는 공통 속성을 사용하므로 상속 구조로 표현하였다.
* 회원
    * 주문한 상품과 임베디드 타입인 Address 를 가진다.
* 주문
    * 한번에 여러 상품을 주문할 수 있다. (1:n)
    * 주문 상태는 열거형으로 사용하였다.
* 주문상품
    * 주문한 상품에 대한 정보를 가진다.
* 상품
    * 상품에 대한 저보를 가진다.
    * 상품을 주문하면 재고수량 (stockQuantity) 가 줄어든다.
    * DTYPE 을 통해 각 상품의 종류를 구분한다.
* 배송
    * 주문시 배송 정보를 생성하며, 주문과 배송은 1:1 관계이다.
* 카테고리
    * 상품과 n:m 관계를 가진다.

### Repository

* 대부분 데이터 접근 계층은 CRUD (등록/조회/수정/삭제) 를 반복해서 개발한다.
* 반복되는 문제를 해결하려면 제네릭과 상속을 적절히 사용해서 공통 부분을 처리하는 부모 클래스를 만들어서 처리할 수 있다.
    * 이러한 방식을 GenericDAO 라고 한다.
    * 하지만 이 방법은 공통 기능을 구현한 부모 클래스에 너무 종속되고 구현 클래스 상속이 가지는 단점에 노출된다.
* 스프링 데이터 JPA 가 이러한 반복되는 CRUD 문제를 해결해준다.
    * Repository 를 개발할 떄 인터패ㅔ이스만 작성하면 실행 시점에 스프링 데이터 JPA 가 구현 객체를동적으로 생성해서 주입해준다.
    * 데이터 접근 계층을 개발할 때 구현 클래스 없이 인터페이스만 작성해도 개발을 완료할 수 있다.
    
```java
public interface EntityRepository extends JpaRepository<Entity, Long> {
    // JpaRepository<Entity, 식별자 타입>
    // 기본적인 CRUD (save, findOne, findAll ...) 은 JpaRepository 에서 제공한다.
    Entity findByField(String field);
    // findByField 와 같이 직접 작성한 공통으로 처리할 수 없는 메소드는
    // 스프링 데이터 JPA 가 메소드 이름을 분석해서 JPQL 을 실행한다.
    // select e from Entity e where field = :field
}
```

### 쿼리 메소드 기능

* 스프링 데이터 JPA 가 제공하는 쿼리 메소드 기능
    1. 메소드 이름으로 쿼리 생성
    2. 메소드 이름으로 JPA NamedQuery 호출
    3. @Query 어노테이션을 사용해서 Repository Interface 에 쿼리 직접 정의
    
#### 메소드 이름으로 쿼리 생성

```java
public interface MemberRepository extends Repository<Member, Long> {
    List<Member> findByEmailAndName(String email, String name);
}
// 메소드 이름을 분석하여 쿼리를 생성한다.
// SELECT m FROM Member m WHERE m.email = ?1 AND m.name = ?2
```

| 키워드 | 예 | JPQL |
| ---- | ---- | ---- |
| And | findByField1AndField2 | ... where a.field1 = ?1 and a.field2 = ?2 |
| Or | findByField1OrField2 | ... where a.field1 = ?1 or a.field2 = ?2 |
| Is,Equals | findByField, findByFieldIs, findByFieldEquals | ... where a.field1 = ?1 |
| Between | findByFieldBetween | ... where a.field between 1? and 2? |
| LessThan | findByFieldLessThan | ... where a.field < ?1 |
| LessThanEqual <br/> GreaterThan | findByFieldLessThanEqual <br/> findByFieldGreaterThan | ... where a.field <= ?1 <br/> ... where a.field > ?1 |
| GreaterThanEqual | findByFieldGreaterThaneEqual | ... where a.field >= ?1 |
| After | findByFieldDateAfter | ... where a.field > ?1 |
| Before | findByFieldDateBefore | ... where a.field < ?1 |
| IsNull | findByFieldIsNull | ... where a.field is null |
| IsNotNull, NotNull | findByField(Is)NotNull | ... where a.field not null |
| Like | findByFieldLike | ... where a.field like ?1 |
| NotLike | findByFieldNotLike | ... where a.field not like ?1 |
| StartingWith | findByFieldStartWith | ... where a.field like ?1 </br> field% |
| EndingWith | findByFieldEndingWith | ... where a.field like ?1 </br> %field |
| Containing | findFieldContaining | ... where a.field like ?1 </br> %field% |
| OrderBy | findByField1OrderByField2Desc | ... where a.field1 = ?1 order by a.field2 desc |
| Not | findByFieldNot | ... where a.field <> ?1 |
| In | findByFieldIn(Collection fields) | ... where a.field in ?1 |
| NotIn | findByFieldNotIn(Collection fields) | ... where a.field not in ?1 |
| TRUE | findByFieldTrue() | ... where a.field = true |
| FALSE | findByFieldFalse() | ... where a.field = false |
| IgnoreCase | findByFieldIgnoreCase | ... where UPPER(a.field) = UPPER(?1) |

* 스프링 데이터 JPA 공식 문서가 제공하는 쿼리 생성 기능
* 해당 스프링 데이터 JPA 에 연동된 필드의 엔티티가 변경되면 같이 변경해줘야 한다.
    * 엔티티와 매핑되지 않으면 컴파일 에러가 발생한다.

#### 반환 타입

* 반환 타입으로는 컬렉션 또는 반환 타입을 지정할 수 있다.

```java
List<Member> findByName(String name);   // 컬렉션
Member findByEmail(String email);       // 반환 값 지정
```

* 위 설정에서 조회 결과가 없을 경우
    * 컬렉션 -> 빈 컬렉션
    * 반환 값 지정 -> null
* 단건으로 지정한 메소드를 호출하면 스프링 데이터 JPA 내부에서 JPQL 의 Query.getSingleResult() 메소드를 호출한다.
    * 참고로 getSingleResult() 메소드의 결과가 없으면 NoResultException 예외가 발생하나, 스프링 데이터 JPA 는 해당 예외를 무시하고 null 을 리턴해준다.

#### 페이징과 정렬

* 페이징과 정렬 기능을 위한 파라미터
    1. org.springframework.data.domain.Sort : 정렬 기능
    2. org.springframework.data.domain.pageable : 페이징 기능 (내부 sort 포함)

```java
// count 쿼리 사용
Page<Membmer> findByName(STring name, Pageable pageable);

// count 쿼리 X
List<Membmer> findByName(STring name, Pageable pageable);

Page<Membmer> findByName(STring name, Sort sort);

...

PageRequest pageRequest = new PageRequest(0, 10, new Sort(Direction.DESC, "name"));
// Pagable 은 인터페이스이고, 해당 인터페이스를 구현한 PageRequest 를 사용할 수 있다.
// 해당 클래스에서 제공하는 메소드는 여러가지가 존재한다.
// 페이지는 0부터 시작된다.

Page<Member> result = memberRepository.findByNameStartingWith("김", pageRequest);

List<Member> members = result.getContent(); // 조회된 데이터
int toalPages = result.getTotalPages();     // 전체 페이지 수
boolean hasNextPage = result.hasNextPage(); // 다음 페이지 존재 여부
```

#### Lock

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
List<Member> findByNAme(String name);
// LOCK 어노테이션을 사용하면, 해당 쿼리를 사용할 때 락을 사용한다.
```

### 명세

* 명세를 이해하기 위한 핵심 단어는 술어(predicate) 이다.
    * 술어 - 단순히 참 or 거짓으로 평가된다.
* AND/OR 연산자로 조합할 수 있다.
* 데이터를 검색하기 위한 제약 조건 하나하나를 술어라 할 수 있다.
* 스프링 데이터 JPA 의 술어는 org.springframework.data.jpa.domain.Specification 클래스로 정의되어 있다.
    * Specification 은 composite pattern 으로 구성되어 있어 여러 Specification 을 조합할 수 있다.
    * 즉, 다양한 검색조건을 조립해서 새로운 검색조건을 쉽게 만들 수 있다.
* 명세 기능을 사용하기 위해서는 org.springframework.data.jpa.repository.JpaSpecificationExecutor 인터페이스를 상속받으면 된다.

```java
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {}

/*
public interface JpaSpecificationExecutor<T> {
    T findOne(Specification<T> spec);
    List<T> findAll(Specification<T> spec);
    Page<T> findAll(Specification<T> spec, Pageable pageable);
    List<T> findAll(Specification<T> spec, Sort sort);
    long count(Specification<T> spec);
}

JpaSpecificationExecutor 는 Specification 을 받아서 검색 조건으로 사용한다.
*/

...

import some dependencies...

public class OrderSpec {
    public static Specification<Order> memberName(final String memberName) {
        return new Specification<Order>() {
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (StringUtils.isEmpty(memberNAme)) return null;
                
                Join<Order, Member> m = root.join("member", JoinType.INNER);
                // 회원과 조인
                return builder.equal(m.get("name"), memberName);
            }
        };
    }
    
    public static Specification<Order> isOrderStatus() {...}
    
    // 명세를 정의하려면 Specification 인터페이스를 구현하면 된다.
    // 명세를 정의할 때 toPredicate(...) 메소드만 구현하면 된다.
    // 해당 메소드는 JPA Criteria 의 Root, CriteriaQuery, CriteriaBuilder
    // 클래스가 모두 파라미터로 주어진다.
    // 해당 파라미터를 활용해서 적절한 검색 조건을 반환하면 된다.
}

...

import static org.springframework.data.jpa.domain.Specifications.*;
// Specification 안에 where(), and(), or(), not() 메소드를 제공한다.
import static ...spec.OrderSpec.*;

public List<Order> findOrders(String name) {
    List<Order> result = orderRepository.findAll(
            where(memberName(name)).and(isOrderStatus())
            // memberName(), isOrderStatus() 라는 명세를 and 로 조합해서 검색 조건으로 사용한다.
    );
    
    return result;
}
```

### 사용자 정의 리포지토리 구현

```java
public interface CustomRepository {
    public List<Entity> findEntityCustom();
}

...

public class EntityRepositoryImpl implements CustomRepository {
    @Override
    public List<Entity> findEntityCustom() {
        ...
        // 사용자 정의 구현
    }
}

...

public interface EntityRepository extends JpaRepository<Entity, Long>, CustomRepository {}

// 위와 같이 커스텀 repository 클래스를 선언하고, 구현한 클래스를 repository 인터페이스 이름 + Impl 로 생성한다.
// Impl 외 다른 이름을 붙이고 싶으면 별도의 설정을 해주면 된다.
```

### Web 확장

#### 설정

* 스프링 데이터가 제공하는 Web 확장 기능을 활성화 하기 위해서는 org.springframework.data.web.config.SpringDataWebConfiguration 을 스프링 빈으로 등록하면 된다.
* JavaConfig (java 클래스로 설정) 를 사용하면 @EnableSpringDataWebSupport 어노테이션을 통해서 설정할 수 있다.

```java
@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebAppConfig {...}
```

* 설정 후에 도메인 클래스 컨버터, 페이징, 정렬을 위한 HandlerMethodArgumentResolver 가 스프링 빈에 등록된다.

#### 도메인 클래스 컨버터

* HTTP 파라미터로 넘어온 엔티티의 아이디로 엔티티 객체를 찾아서 바인딩 해준다.


```java
@Controller
public class EntityController {
    @RequestMapping("...")
    public String someFunction(@RequestParam("id") Entity entity, Model model) {
        // 도메인 클래스 컨버터 설정을 진행하면 id 를 받지만, 해당 id 에 해당되는 엔티티를 바로 바인딩되어 사용할 수 있게 해준다.
        // 참고로 도메인 클래스 컨버터는 해당 엔티티와 관련된 repository 를 사용해서 엔티티를 ㅊ자는다.
        model.addAtrribute("entity", entity);
        return "entity/SomeHtml";
    }
}
```

#### 페이징과 정렬

* 페이징 기능    PageableHandlerMethodArgumentResolver
* 정렬 기능     SortHandlerMethodArgumentResolver

```java
@RequestMapping(...)
public String list(Pageable pageable, Model model) {
    Page<Entity> page = entityService.findEntities(pageable);
    model.addAtrribute("entities", page.getContent());
    return "entity/someHtml";
}

/*
Pageable 에 매핑되는 요청 파라미터

page    : 현재 페이지 / 0 부터 시작
size    : 한 페이지에 노출할 데이터 건수
sort    : 정렬 조건을 정의

entities?page=0&size=10&sort=field,desc
와 같이 파라미터로 위 속성을 넘겨주면 해당 값으로 pageable 객체에 매핑되고
해당 pageable 정보를 바탕으로 쿼리를 진행한다.

pageable 의 기본값은 page = 0, size = 20 이다.
기본값 변경은 @PageableDefault 어노테이션을 사용해서 변경할 수 있다.
 */
```
