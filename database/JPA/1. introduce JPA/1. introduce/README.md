# JPA 소개

* problem 1,2,3 의 예제를 통해서 JDBC API 를 사용할 경우 문제점을 간단히 살펴봤다.
    1. 진정한 의미의 계층 분할이 어렵다.
    2. 엔티티를 신뢰할 수 없다.
    3. SQL 에 의존적인 개발을 피하기 어렵다.
* problem 1,2,3 에서의 문제점을 JPA 에서 해결하는 방식
    * 객체를 데이터베이스에 저장하고 관리할 때, 개발자가 직접 SQL 을 작성하지 않고 JPA 가 제공하는 API 를 사용할 수 있다.
    * 즉, JPA 가 적잘한 SQL 을 생성해서 데이터베이스에 전달한다.

```
// 저장기능
jpa.persist(member);

// 조회 기능
String memberId = "hello";
Member member = jpa.find(Member.class, memberId);

// 수정 기능
Member member = jpa.find(Member.class, memberId);
member.setName("수정");

// 연관된 객체 조회
Member member = jpa.find(Member.class, memberId);
Team team = member.getTeam();

// problem 1,2,3 에서 SQL 쿼리를 하나하나 다 작성하고, DAO 를 만들고 엔티티가 변할 때 마다 해당되는 모든 코드를 변경해야 했지만
// JPA 를 통해 위와 같은 간단한 코드로 모두 해결된다.
```

* 조회
    * persist 를 통해서 객체를 데이터베이스에 저장한다.
    * 해당 메소드는 JPA 가 객체와 매핑정보를 보고 적절한 Insert SQL 을 생성해서 데이터베이스에 전달한다.
* 조회
    * find 메소드를 통해 객체 하나를 데이터베이스에서 조회한다.
    * 객체와 매핑정보를 보고 적절한 Select SQL 을 생성하여 데이터베이스에서 해당되는 데이터를 받아서 객체로 반환한다.
* 수정
    * 별도의 수정 메소드는 존재하지 않는다.
    * jpa 를 통해 가져온 객체 데이터를 수정하고 트랜젝션이 커밋 될 때 최종적으로 업데이트 된 데이터와 처음 데이터를 비교하여 적절한 Update SQL 을 생성해서 데이터베이스에 전달한다.
* 연관된 객체 조회
    * jpa 를 통해 가져온 객체의 연관된 객체를 사용하는 시점에 해당되는 객체를 불러오는 Select SQL 을 생성하여 데이터베이스에 전달한다.
