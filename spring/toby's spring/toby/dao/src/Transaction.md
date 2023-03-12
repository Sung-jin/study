## 트랜잭션 전파

* PROPAGATION_REQUIRED
  * 진행 중인 트랜잭션이 없으면 새로 시작하고, 이미 시작된 틀내잭션이 있으면 참여한다
  * 이는 다양한 방식으로 결합하여 하나의 트랜잭션으로 구성하기 쉽다
* PROPAGATION_REQUIRES_NEW
  * 항상 새로운 트랜잭션을 시작
  * 이는 앞에서 시작된 트랜잭션이 있어도 새로운 트랜잭션을 만들어 독자적으로 동작하게 한다
  * 독립적인 트랜잭션이 보장되어야 하는 코드에 적용할 수 있다
* PROPAGATION_NOT_SUPPORTED
  * 트랜잭션 없이 동작하도록 할 수 있다
  * 즉, 진행중인 트랜잭션이 있더라도 무시한다
  * 이는 특정 메서드에서만 트랜잭션을 제외하고 싶을 경우, 클래스 또는 전체에 트랜잭션을 걸고, 제외한 부분만 사용하는 등으로 활용이 가능하다

## 격리 수준

* 동시에 여러 트랜잭션이 어떠한 수준의 격리수준으로 동시에 진행시킬 수 있다
  * 격리 수준은 DB 에 설정되어 있으며, JDBC 드라이버나 DataSource 등에서 재설정이 가능하다
  * DefaultTransactionDefinition 에 설정된 격리 수준은 `ISOLATION_DEFAULT` 이다

## 제한시간

* 트랜잭션을 수행하는 제한시간을 설정할 수 있다
* DefaultTransactionDefinition 의 기본 설정은 제한시간이 없다
  * 제한시간은 트랜잭션을 직접 시작할 수 있는 `PROPAGATION_REQUIRRED`/`PROPAGATION_REQUIRES_NEW` 와 함께 사용해야만 의미가 있다

## 읽기전용

* 트랜잭션 내에서 데이터를 조작하는 시도를 막을 수 있으며, 데이터 액세스 기술에 따라 성능이 향상될 수 있다
* TransactionDefinition 타입 오브젝트를 사용하면 네가지 속성을 이용하여 트랜잭션의 동작방식을 제어할 수 있다

## 트랜잭션 정의 수정

* TransactionDefinition 오브젝트를 생성하고 사용하는 코드는 트랜잭션 경계설정 기능을 가진 TransactionAdvice 이다
* 기본 설정인 DefaultTransactionDefinition 대신 외부에서 정의된 TransactionDefinition 오브젝트를 DI 받아서 사용하도록 만들면 기본 정의를 변경할 수 있다
  * 빈을 통해 TransactionDefinition 타입을 정의하면, 원하는 속성을 지정할 수 있으나, TransactionAdvice 를 사용하는 모든 트랜잭션의 속성이 변경된다

## 트랜잭션 인터셉터와 트랜잭션 속성

### TransactionInterceptor

* `PlatformTransactionManager`/`Properties` 타입의 두 가지 프로퍼틸르 가지고 있다
  * Properties 는 transactionAttributes 로, 트랜잭션 속성을 정의한 프로퍼티이다
  * transactionAttributes 는 TransactionDefinition 네 가지 기본 항목에 rollbackOn() 메소드를 하나더 가지고 있는 인터페이스로 정의된다
  * 이러한 transactionAttributes 를 이용하면 트랜잭션 부가기능의 동작 방식을 모두 제어할 수 있다

### 메소드 이름 패턴을 이용한 트랜잭션 속성 지정

* Properties 타입의 transactionAttributes 프로퍼티는 메소드 패턴과 트랜잭션 속성을 키와 값으로 가지는 컬렉션이며, 다음과 같은 문자열로 정의할 수 있다
  * `PROPAGATION_NAME, ISOLATION_NAME, readOnly, timeout_NNNN, -Exception1, +Exception2`
  * PROPAGATION_NAME: 트랜잭션 전파 방식이며, 필수항목이고, `PROPAGATION_` 으로 시작한다
  * ISOLATION_NAME: 격리수준을 나타내며, `ISOLATION`_ 으로 시작하고 생략이 가능하며, 디폴트는 결리 수준으로 지정된다
  * readOnly: 읽기전용 항목이며, 생략이 가능하고, 기본은 읽기전용이 아니다
  * timeout_NNNN: 제한 시간이며, `timeout_` 으로 시작하고 초 단위 시간을 뒤에 붙이며, 생략 가능하다
  * -Exception1: 체크 예외 중 롤백 대상으로 추가할 것을 넣으며, 한개 이상 등록할 수 있다
  * +Exception2: 런타임 예외지만 롤백시키지 않을 예외들을 넣을 수 있으며, 한개 이상 등록할 수 있다

```xml
<bean id="transactionAdvice" class="...TransactionInterceptor">
  <property name="transactionManager" ref="transactionManager" />
  <property name="transactionAttributes">
    <props>
      <prop key="get*">PROPAGATION_REQUIRED,readonly,timeout_30</prop>
      <prop key="upgrade*">PROPAGATION_REQUIRES_NEW,ISOLATION_SERIALIZABLE</prop>
      <prop key="*">PROPAGATION_REQUIRED</prop>
    </props>
  </property>
</bean>
```

* 위와 같이 메소드 이름 패턴과 문자열로 된 트랜잭션 속성을 정의할 수 있다
* readOnly 와 timeout 속성은 트랜잭션이 처음 시작된 곳이 아니라면 적용되지 않는다
