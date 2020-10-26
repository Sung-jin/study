# 데이터베이스 스키마 자동 생성

* JPA 는 데이터베이스 스키마를 자동으로 생성하는 기능을 지원한다.
* 매핑정보와 데이터베이스 방언을 사용해서 데이터베이스 스키마를 생성한다.
    * 자동 생성되는 DDL 은 지정한 데이터베이스 방언에 따라 달라진다.

```xml
<property name="hibernate.hbm2ddl.auto" value="create"/>
<property name="hibernate.show_sql" value="true"/>
```

```yaml
spring.jpa:
  hibernate.ddl-auto: create
  show-sql: true
```

* auto ddl 옵션 설명

| 옵션 | 설명 |
| ---- | ---- |
| create | 기존 테이블을 삭제하고 새로 생성한다. <br/> Drop + Create |
| create-drop | create 속성에 추가로 애플리케이션을 종료할 때 생성한 DDL 을 제거한다. <br/> Drop + Create + Drop |
| update | 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 변경 사항만 수정한다. |
| validate | 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 차이가 있으면 경고가 발생하고 실행하지 않는다. <br/> 해당 설정은 DDL 을 수정하지 않는다. |
| none | 해당 옵션은 유효하지 않은 값이다. <br/> 위의 값들 외에는 모두 유효하지 않을 값이며, 유효하지 않은 값일 경우 auto ddl 을 하지 않는다. |

* auto ddl 주의점
    * DDL 을 수정하는 옵션은 절대 운영 서버에서 사용하면 안된다.
        * 운영중인 데이터베이스의 테이블이나 컬럼을 삭제할 수 있기 때문이다.
        * 개발 서버나 개발 단계에서 사용해야 한다.
* JPA 2.1 부터 스키마 자동 생성 기능을 표준으로 지원한다.
    * 하지만, update, validation 옵션은 지원하지 않는다.

```xml
<property name="javax.persistence.schema-generation.database.action" value="..."/>

<!-- 지원 옵션 : none, create, drop-and-create, drop --> 
```

* 이름 매핑 전략
    * 자바는 관례상 camelCase (testColumn)
    * 데이터베이스는 관례상 단어와 단어 사이에 _ 을 사용한다. (test_Column)
    * @Column(name="test_table")
    * hibernate.ejb.naming_strategy 속성을 사용하여 이름 매핑 전략을 변경할 수 있다.
        * 직접 구현할 수 있지만, org.hibernate.cfg.ImprovedNamingStrategy 클래스를 통해 설정할 수 있다.
        * 위 클래스가 생략되면 자동으로 camelCase -> under_score 으로 매핑한다.

```xml
<property name="hibernate.ejb.naming_strategy" value="org.hibernate.cfg.ImprovedNamingStrategy"/>
```

# DDL 생성 기능

```java
@Cloumn(nullable = false, length = 10)
private String value;

// 자동으로 DDL 이 생성될 때, value 라는 컬럼이 nullable 이 false 와 길이 10 인 컬럼이 생성된다.

@Entity
@Table(name = "entity", uniqueConstraints = {@UniqueConstraint (
    name = "ENTITY_VALUE1_VALUE2_UNIQUE",
    columnName = {"value1", "value2"}
)})
public class Entity {
    @Id
    private long id;
    
    private String value1;

    private String value2;
}

// ALTER TABLE entity ADD CONSTRAINT ENTITY_VALUE1_VALUE2_UNIQUE UNIQUE(value1, value2)
```

* 위와 같이 nullable, length, uniqueConstraint 등과 같은 속성들은 단지 DDL 을 자동 생성할 때만 사용되고 JPA 의 실행 로직에는 영향을 주지 않는다.
* 스키마 자동 생성 기능을 사용하지 않고 직접 DDL 을 만든다면 사용할 이유가 없다.
* 하지만 기능적인 측면 외에, 해당 어노테이션을 통해 데이터베이스의 제약조건을 손쉽게 파악할 수 있는 장점이 있다.
