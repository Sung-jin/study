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
