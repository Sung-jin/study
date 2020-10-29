## 매핑 어노테이션

* JPA 에 다양한 매핑 어노테이션
    1. 객체와 테이블 매핑 - @Entity, @Table
    2. 기본 키 매핑 - @ID
    3. 필드와 컬럼 매핑 - @Column
    4. 연관관계 매핑 - @ManyToOne, @JoinColumn

### @Entity

* JPA 를 사용해서 테이블과 매핑할 클래스는 @Entity 어노테이션을 필수로 붙어야 한다.
* 해당 어노테이션이 있는 클래스는 JPA 가 관리하는 것으로, 엔티티라 부른다.
* 엔티티 어노테이션 적용 시 주의사항
    1. 기본 생성자는 필수이다. (파라미터가 없는 생성자)
        * JPA 가 엔티티 객체를 생성할 때 기본 생성자를 사용하므로 반드시 필요하다.
        * 자바는 클래스 내부에 생성자가 없다면 파라미터가 없는 기본 생성자를 자동으로 생성하지만, 생성자를 따로 만들면 자동으로 생성되지 않는다.
        * 즉, 생성자를 따로 지정한 엔티티 클래스의 경우에는 직접 생성자가 없는 기본 생성자도 같이 선언해줘야 한다. 
    2. final, enum, interface, inner 클래스에는 사용할 수 없다.
    3. 저장할 필드에 final 을 사용하면 안 된다.

| 속성 | 기능 | 기본값 |
| ---- | ---- | --- |
| name | JPA 에서 사용할 엔티티 이름을 지정한다. <br/> 보통 기본값으로 클래스 이름을 사용한다. <br/> 다른 패키지에 같은 이름의 엔티티 클래스가 있다면, 이름을 지정하여 중복을 방지해야 한다. | 클래스 이름 |

### @Table

* 엔티티와 매핑할 테이블을 지정하는 어노테이션이다.
* 생략하면 매핑한 엔티티 이름을 테이블 이름으로 사용한다.

| 속성 | 기능 | 기본값 |
| ---- | ---- | ---- |
| name | 매핑할 테이블 이름 | 엔티티 이름 |
| catalog | catalog 기능이 있는 데이터베이스에서 catalog 를 매핑한다. | |
| schema | schema 기능이 있는 데이터베이스에서 schema 를 매핑한다. | |
| uniqueConstraints (DDL) | DDL 생성 시에 유니크 제약조건을 만든다. <br/> 2개 이상의 복합 유니크 제약조건을 만들 수 있다. <br/> 참고로 이 기능은 스키마 자동 생성 기능을 사용해서 DDL 을 만들 때만 사용한다. |

### @Id

* Primary Key 매핑
* 하지만 데이터베이스 종류마다 할당해주는 방법이 다르다.
* JPA 는 이러한 문제를 해결하는 방법으로 다음과 같다.
    1. IDENTITY - 기본 키 생성을 데이터베이스에 위임한다.
    2. SEQUENCE - 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.
    3. TABLE - 키 생성 테이블을 사용한다.
* 기본 키만 할당하려면 @Id 만 사용하고, 자동 생성 전략을 사용하려면 @Id 에 @GeneratedValue 에 위의 설정값을 추가하여 사용하면 된다.
* @GeneratedValue 전략을 사용하려면 persistence.xml 에 다음과 같은 설정 추가가 필요하다.

```xml
<property name="hibernate.id.new_generator_mappings" value="true" />
```

#### 기본 키 직접 할당

* @Id 에 적용 가능한 자바 타입
    1. 자바 기본형
    2. 자바 래퍼형
        * String
        * java.util.Date
        * java.sql.Date
        * BigDecimal
        * BigInteger
* 기본 키 직접 할당 전략은 em.persist(entity) 로 엔티티를 저장할 때 직접 id 변수에 데이터를 지정한 뒤 영속화 하는 경우이다.
    * 기본 키 직접 할당 전략에서 직접 키를 지정하지 않고 저장하면 PersistenceException 예외가 발생한다.
    
#### IDENTITY 전략

* 기본 키 생성을 데이터베이스에 위임한다.
* 주로 MySQL, PostgreSQL, SQL Server, DB2 에서 사용된다.

```mysql
CREATE TABLE entity (
    id long NOT NULL AUTO_INCREMENT PRIMARY KEY,
    data varchar(255)
);

INSERT INTO entity(data) value ('test');
# 자동으로 id 의 값이 순서대로 채워져서 저장된다.
```

```java
@Entity
@Data
public class entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 해당 어노테이션과 IDENTITY 속성을 주면 em.persist() 할 때 데이터베이스에서 자동으로 값을 할당하여 저장한다.
    private long id;

    private String data;
}

public Entity save() {
    Entity entity = new Entity();
    entity.setData("hello world!");

    return em.persist(entity);
}
// 위와같이 id 를 지정하지 않더라도, em.persist() 를 통해 데이터베이스에 저장할 때 데이터베이스에서 id 를 자동으로 채워준다.
// 또한, 저렇게 리턴해주면 자동으로 저장된 id 가 들어간 엔티티가 리턴된다.
```

* 엔티티가 영속 상태가 되려면 식별자가 필요한데, IDENTITY 전략은 엔티티를 데이터베이스에 저장해야 식별자를 구할 수 있으므로 em.persist() 를 호출하는 즉시 insert sql 이 데이터베이스에 전달된다.
* 따라서, 트랜재션을 지원하는 쓰기 지연이 동작하지 않는다.

#### SEQUENCE 전략

* 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트이다.
* Oracle, PostgreSQL, DB2, H2 데이터베이스에서 사용할 수 있다.

```oracle
CREATE TABLE entity (
    id long NOT NULL PRIMARY KEY,
    data varchar(255)
);

CREATE SEQUENCE ENTITY_SEQ START WITH 1 INCREMENT BY 1;
```

```java
@Data
@Entity
@SequenceGenerator(
    name = "ENTITY_SEQ_GENERATOR",
    sequenceName = "ENTITY_SEQ",
    initialValue = 1,
    allocationSize = 1
)
// ENTITY_SEQ_GENERATOR 라는 시퀀스 생성기를 등록
public class entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTITY_SEQ_GENERATOR")
    // SEQUENCE 전략으로 설정하고, ENTITY_SEQ_GENERATOR 에 등록한 시퀀스 생성기를 선택한다.
    // id 식별자 값은 ENTITY_SEQ_GENERATOR 시퀀스가 할당한다.
    // IDENTITY 와 다르게 em.persist() 를 호출할 때 먼저 데이터베이스 시퀀스를 사용해서 식별자를 조회한다.
    // 조회한 식별자를 엔티티에 할당한 후에 엔티티를 영속성 컨텍스트에 저장한다.
    // 이후 트랜잭션을 커밋해서 플러시가 일어나면 엔티티를 데이터베이스에 저장한다.
    // 즉, IDENTITY 는 저장 후 id 를 조회해서 엔티티의 식별자에 할당하지만 SEQUENCE 전략은 식별자를 조회한 뒤 셋팅하고 데이터베이스에 저장한다.
    // @SequenceGenerator -> @GeneratedValue 옆에 사용해도 된다.
    private long id;

    private String data;
}
```

* @SequenceGenerator 속성

| 속성 | 기능 | 기본값 |
| ---- | ---- | ---- |
| name | 식별자 생성기 이름 | 필수 |
| sequenceName | 데이터베이스에 등록되어 있는 시퀀스 이름 | hibernate_sequence |
| initialValue | DDL 생성 시에만 사용됨. <br/> 시퀀스 DDL 을 생성할 때 처음 시작하는 수를 지정 | 1 |
| allocationSize | 시퀀스 한 번 호출에 증가하는 수 (성능 최적화에 사용) | 50 |
| catalog, schema | 데이터베이스 catalog, schema 이름 | |

> CREATE SEQUENCE [sequenceName] <br/>
> START WITh [initialValue] INCREMENT BY [allocationSize]

#### Table 전략

* 키 생성 전용 테이블을 하나 만들고 해당 테이블에 이름과 값으로 사용할 컬럼을 만들어 데이터베이스 시퀀스를 흉내내는 전략이다.
* 모든 데이터베이스에서 사용이 가능하다.

```sql
CREATE TABLE sequence_table (
    sequence_name varchar(255) not null,
--  시퀀스 이름 
    next_val bigint,
--  시퀀스 값
    primary key key ( sequence_name )
)
```

```java
@Data
@Entity
@TableGenerator(
    name = "ENTITY_SEQ_GENERATOR",
    // 테이블 키 생성기
    table = "sequence_table",
    // 키 생성용 테이블 매핑
    pkColumnValue = "entity_seq",
    allocationSize = 1
)
// ENTITY_SEQ_GENERATOR 라는 시퀀스 생성기를 등록
public class entity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ENTITY_SEQ_GENERATOR")
    // TABLE 전략으로 설정하고, ENTITY_SEQ_GENERATOR 에 등록한 시퀀스 생성기를 선택한다.
    private long id;

    private String data;
}
```

* Sequence 전략과 시퀀스 사용 여부 차이만 제외하고 전략과 내부 동작방식이 같다.
* pkColumnValue 에 해당되는 값이 테이블의 sequence_name 컬럼에 생성되고 해당 테이블에 매핑된다.
* 테이블이 생성될 때 마다 next_val 컬럼의 값이 증가한다.
* @TableGenerator 속성

| 속성 | 기능 | 기본값 |
| ---- | ---- | ---- |
| name | 식별자 생성기 이름 | 필수 |
| table | 키생성 테이블명 | hibernate_sequences |
| pkColumnName | 시퀀스 컬럼명 | sequence_name |
| valueColumnName | 시퀀스 값 컬렴명 | next_val |
| pkColumnValue | 키로 사용할 값 이름 | 엔티티 이름 |
| initialValue | 초기 값, 마지막으로 생성된 값이 기준 | 0 |
| allocationSize | 시퀀스 한 번 호출에 증가하는 수 <br/> (성능 최적화에 사용) | 50 |
| catalog, schema | 데이터베이스 catalog, schema 이름 | |
| uniqueConstraints(DDL) | 유니크 제약 조건을 지정할 수 있다 | |

#### auto 전략

* GenerationType.AUTO 는 선택한 데이터베이스 방언에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택한다.
* @GeneratedValue.strategy 의 기본값은 AUTO 이다.
* 데이터베이스를 사용하여도 코드를 수정할 필요가 없다.
* 스키마 자동 생성 기능을 사용하고, SEQUENCE, TABLE 전략이 선택되면 sequence 용 테이블이 자동으로 생성된다.

#### 기본 키 매핑 정리

* 영속성 컨텕스트는 엔티티를 식별자 값으로 구분하므로 엔티티를 영속 상태로 만들려면 식별자 값이 반드시 있어야 한다.
* em.persist() 를 호출한 직후에 발생하는 일은 다음과 같다.
    * 직접 할당
        * 영속화 하기 전에 직접 객체에 식별자 값을 할당해야 하며, 없으면 예외가 발생한다.
    * SEQUENCE
        * 데이터베이스 시퀀스에서 식별자 값을 획득한 후 영속성 컨텍스트에 저장한다.
    * TABLE
        * 데이터베이스 시퀀스 생성용 테이블에서 식별자 값을 획득한 후 영속성 컨텍스트에 저장한다.
    * IDENTITY
        * 데이터베이스에 엔티티를 저장해서 식별자 값을 획득한 후 영속성 컨텍스트에 저장한다.
        * 테이블에 데이터를 저장해야 식별자 값을 획득할 수 있다.
* 권장하는 식별자 전략
    1. null 값은 허용하지 않는다.
    2. 유일해야 한다.
    3. 변해서는 안된다.
* 테이블의 기본 키를 선택하는 전략
    1. 자연키 (natural key) - 비즈니스에 의미가 있는 키이며, 주민등록번호/이메일/전화번호 등과 같은 데이터
    2. 대리키 (surrogate key) - 비즈니스와 관련 없는 임의로 만들어진 키이며, 대체키로도 불린다.

### @Enumerated

* 자바의 enum 과 매핑하는 어노테이션
* 해당 어노테이션에 속성으로 value 가 있으며 아래와 같다.
    1. EnumType.ORDINAL
        * 기본값이다.
        * enum 의 순서대로 0부터 순차적으로 오르는 값으로 데이터베이스에 저장한다.
        * 장점 : 데이터베이스에 저장되는 데이터 크기가 작아진다.
        * 단점 : 이미 저장된 enum 의 순서를 변경할 수 없다.
    2. EnumType.STRING
        * enum 의 해당되는 값으로 데이터베이스에 저장한다.
        * 장점 : 저장된 enum 의 순서가 변경되거나 값이 변경되어도 안전하다.
        * 단점 : 데이터베이스에 저장되는 데이터 크기가 ORDINAL 에 비해 크다.
        
```java
enum userType() {
    ADMIN, USER, GUEST
}

// ORDINAL 일 경우, ADMIN 은 0/USER 는 1/GUEST 는 2 로 데이터베이스에 저장된다.
// STRING 일 경우 해당되는 문자 그대로 저장된다.
```

### @Temporal

* 날짜 타입을 매핑할 때 사용한다.
* 해당 어노테이션에 속성으로 value 가 있으며, 필수로 값을 지정해줘야 하며 아래와 같은 설정 값이 존재한.
    1. TemporalType.DATE
        * 날짜, 데이터베이스 date 타입과 매핑
        * 예) 2020-12-31다
    2. TemporalType.TIME
        * 시간, 데이터베이스 time 타입과 매핑
        * 예) 23:59:59
    3. TemporalType.TIMESTAMP
        * 날짜와 시간, 데이터베이스 timestamp 타입과 매핑
        * 예) 2020-12-31 23:59:59
* 해당 어노테이션을 생략할 경우, 자바의 Date 와 가장 유사한 timestamp 로 정의된다.
* timestamp 대신에 datetime 을 예약어로 사용하는 데이터베이스도 존재하지만, 데이터베이스 방언 덕분에 애플리케이션 코드는 변경하지 않아도 된다.
* 데이터베이스 방언에 따라 생성되는 DDL
    * datetime: MySQL
    * timestamp: H2, oracle, PostgresSQL

### @Lob

* 데이터베이스의 BLOB 과 CLOB 타입과 매핑된다.
* 별도의 속성은 없지만 매핑하는 필드 타입이 문자면 CLOB 으로, 나머지는 BLOB 으로 매핑한다.

```java
@Lob
String content;

@Lob
byte[] image;

// 각각 CLOB, BLOB 이 매핑된다.
```

