## 예외 처리

* JPA 표준 예외들은 javax.persistence.PersistenceException 의 자식 클래스이고, RuntimeException 의 자식이다.
    * 즉, 모든 JPA 예외는 언체크 예외이다.

### JPA 표준 예외

* 트랙잭션 롤백을 표시하는 예외
   * 이러한 예외가 발생하면, 트랜잭션을 강제로 커밋해도 트랜잭션이 커밋되지 않고, RollbackException 이 발생한다.
    
| 예외 | 설명 |
| ---- | ---- |
| EntityExistsException | EntityManager.persist(..) 호출 시 이미 같은 엔티티가 존재할 경우 발생 |
| EntityNotFoundException | EntityManager.getReference(...) 를 호출 후 해당 엔티티를 사용하였으나, 엔티티가 존재하지 않으면 발생 <br/> refresh(..), lock(..) 에서 발생할 수 있다. |
| OptimisticLockException | 낙관적 락 충돌 시 발생 |
| PessimisticLockException | 비관적 락 충돌 시 발생 |
| RollbackException | EntityTransaction.commit() 실패 시 발생 <br/> 롤백이 표시되어 있는 트랜잭션 커밋 시에도 발생 |
| TransactionRequiredException | 트랜잭션이 필요할 때 트랜잭션이 없으면 발생 <br/> 트랜잭션 없이 엔티티를 변경할 때 주로 발생 |

* 트랜잭션 롤백을 표시하지 않는 예외
    * 개발자가 트랜잭션을 커밋할지 롤백할지 판단해서 처리할 수 있다.
    
| 예외 | 설명 |
| ---- | ---- |
| NoResultException | Query.getSingleResult() 호출 시 결과가 없을 때 발생 |
| NonUniqueResultException | Query.getSingleResult() 호출 시 결과가 둘 이상일 때 발생 |
| LockTimeoutException | 비관적 락에서 시간 초과 시 발생 |
| QueryTimeoutException | 쿼리 실행 시간 초과 시 발생 |

### 스프링 프레임워크의 JPA 에외 변환

* 서비스 계층에서 JPA 예외를 직접 사용하면 JPA 에 의존하게 된다.
    * 스프링 프레임워크는 이러한 문제를 해결하기 위해 데이터 접근 계층에 대한 예외를 추상화하여 개발자에게 제공한다.
* EntityExistsException -> DataIntegrityViolationException
* EntityNotFoundException -> JpaObjectRetrievalFailureException
* OptimisticLockException -> JpaOptimisticLockingFailureException
* PessimisticLockException -> PessimisticLockingFailureException
* RollbackException -> kkkTransactionSystemException
* TransactionRequiredException -> InvalidDataAccessApiUsageException
* NoResultException -> EmptyResultDataAccessException
* NonUniqueResultException -> IncorrectResultSizeDataAccessException
* LockTimeoutException -> CannotAcquireLockException
* QueryTimeoutException -> kQueryTimeoutException
* PersistenceException -> JpaSystemException
* java.lang.IllegalStateException -> InvalidDataAccessApiUsageException
* java.lang.IllegalArgumentException -> InvalidDataAccessApiUsageException

### 스프링 프레임워크에 JPA 얘외 변환 적용

* JPA 예외를 스프링 프레임워크의 추상화된 예외로 변환할 때는 PersistenceExceptionTranslationPostProcessor 를 스프링 빈으로 등록하면 된다.
    * @Repository 어노테이션을 사용한 곳에 예외 변환 AOP 를 적용하면 스프링 프레임워크의 추상화된 예외로 변환된다.

```xml
<bean class="...PersistenceExceptionTranslationPostProcessor" />
```

```java
@Bean
public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
    return new PersistenceExceptionTranslationPostProcessor();
}

...

@Reposiroy
public class NoResultExceptionTestRepository {
    ...
    
    public Entity findMember() {
        return em.createQuery("select e from Entity e")
                .getSingleResult();
        // 해당 쿼리에 조회된 결과가 없다면 kNoResultException 이 발생한다.
        // 하지만, 해당 예외가 findMember 를 바쪄나갈 때 APO 에 의해 EmptyResultDataAccessException 예외로 변환해서 반환한다.
    }

    public Entity findMember2() throws javax.persistence.NoResultException {
        // AOP 에 의해 변환된 예외가 아닌, JPA 예외를 받고 싶다면 throws 에 해당하는 예외를 명시하면 된다.
        return em.createQuery("select e from Entity e")
                .getSingleResult();
    }
}
```

### 트랜잭션 롤백 시 주의사항

* 트랜잭션을 롤백하는 것은 데이터베이스의 반영사항만 롤백하는 것이다.
    * 즉, 자바 객체까지 원상복구 되지 않는다.
    * 엔티티가 수정된 상태로 롤백이 된다면, 영속성 컨텍스트에는 수정된 엔티티가 그대로 존재하게 된다.
    * 따라서 롤백된 엔티티를 그대로 사용하는 것은 위험하다.
* 해결방법
    * 새로운 영속성 컨텍스트를 생성
    * EntityManager.clear() 를 통해 초기화 후 사용
* 이러한 이슈로 인해 스프링 프레임워크가 해결하는 방식
    * 기본 전략인 영속성 컨텍스트 전략은 문제가 발생하면 AOP 종료 시점에 트랜잭션을 롤백하면서 영속성 컨텍스트도 종료한다.
    * OSIV 등과 같이 영속성 컨텍스트의 범위를 트랜잭션 범위보다 넓게 사용 할 경우에 문제가 발생한다.
        * 이럴때는 트랜잭션 롤백시 영속성 컨텍스트를 초기화 한다.
    * [더 자세한 내용](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/support/AbstractPlatformTransactionManager.html#doRollback-org.springframework.transaction.support.DefaultTransactionStatus-)
