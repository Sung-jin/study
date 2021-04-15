## 트랜잭션과 락

### 트랜잭션과 격리 수준

* ACID
    * Atomicity(원자성) 
        * 트랜잭션 내에서 실행한 작업들은 마치 하나의 작업인 것처럼 모두 성공하든가 모두 실패해야 한다.
    * Consistency(일관성) 
        * 모든 트랜잭션은 일관성 있는 데이터베이스 상태를 유지해야 한다.
        * 데이터베이스에서 정한 무결성 제약 조건을 항상 만족해야 한다.
    * Isolation(격리성)
        * 동시에 실행되는 트랜잭션들이 서로에게 영향을 미치지 않아야 한다.
        * 동시에 같은 데이터리르 수정하지 못해야 한다.
        * 격리성은 동시성과 관련된 성능 이슈로 인해 격리 수준을 선택할 수 있다.
    * Durability(지속성)
        * 트랜잭션을 성공적으로 끝내면 그 결과가 항상 기록되어야 한다.
        * 중간에 문제가 발생하여도, 로그 등을 사용해서 성공한 트랜잭션 내용을 복구해야 한다.
* 트랜잭션의 격리 수준 4단계 (ANSI 표준)
    1. READ UNCOMMITTED (커밋되지 않은 읽기)
    2. READ COMMITTED (커밋된 읽기)
    3. REPEATABLE READ (반복 가능한 읽기)
    4. SERIALIZABLE (직렬화 가능)

* 격리 수준에 따른 문제점

| 격리 수준 | DIRTY READ | NON_REPEATABLE READ | PHANTOM READ |
| ---- | ---- | ---- | ---- |
| READ UNCOMMITTED | O | O | O |
| READ COMMITTED |  | O | O |
| REPEATABLE READ |  |  | O |
| SERIALIZABLE |  |  |  |

* READ UNCOMMITTED
    * 커밋하지 않은 데이터를 읽을 수 있다.
    * 트랜잭션 1 이 데이터를 수정하고 있고, 커밋 전인 데이터가 존재할 때, 트랜잭션 2 가 수정 중인 데이터를 조회할 수 있다.
        * 이를 Dirty Read 라고 한다.
    * 위 상황에서 트랜잭션 1 이 rollback 을 하면, 트랜잭션 1 과 트랜잭션 2 의 같은 데이터에 대해 정합성에 문제가 발생한다.
    * Dirty Read 를 허용하는 격리 수준을 READ UNCOMMITTED 라고 한다.
* READ COMMITTED
    * 커밋한 데이터만 읽을 수 있다.
    * 트랜잭션 1 이 A 라는 데이터를 조회중이고, 트랜잭션 2 가 A 를 수정하고 커밋하면 트랜잭션 1 이 A 를 다시 조회하면 수정된 데이터가 조회된다.
    * 이러한 현상처럼 반복해서 같은 데이터를 읽을 수 없는 상태를 NON-REPEATABLE READ 라고 한다.
    * Dirty Read 는 허용하지 않지만, NON-REPEATABLE READ 를 허용하는 격리 수준을 READ COMMITTED 라고 한다.
* REPEATABLE READ
    * 한번 조회한 데이터를 반복해서 조회해도 같은 데이터가 조회된다.
    * 트랜잭션 1 이 특정 조건의 쿼리를 조회하고, 트랜잭션 2 가 특정 조건에 해당되는 새로운 데이터를 추가하고 커밋하면, 트랜잭션 1 이 다시 같은 조건으로 조회하면 데이터가 추가되어 조회된다.
    * 위 처럼 반복 조회 시 결과 집합이 달라지는 것을 PHANTOM READ 라고 한다.
    * PHANTOM READ 는 허용하지만, NON-REPEATABLE READ 는 허용하지 않는 격리 수준을 REPEATABLE READ 라고 한다.
* SERIALIZABLE
    * 가장 엄격한 수준의 트랜잭션 격리 수준이다.
    * 해당 격리 수준은 동시성 처리 성능이 급격히 떨어질 수 있다.
* 애플리케이션 대부분 동시성 처리가 중요하므로, READ COMMITTED 를 기본으로 사용한다.

### 낙관적 락과 비관적 락

* JPA 의 영속성 컨텍스트 (1차 캐시) 를 적절히 활용하면 데이터베이스가 READ COMMITTED 격리 수준이어도, REPEATABLE READ 격리 수준이 가능해진다.
    * 단, 스칼라를 직접 조회하면 해당 값은 영속성 컨텍스트에 관리받지 못하므로 REPEATABLE READ 수준이 될 수 없다.
* 낙관적 락
    * 트랜잭션 대부분은 충돌이 발생하지 않는다고 낙관적으로 가정하는 방법
    * 데이터베이스가 제공하는 락 기능을 사용하는 것이 아닌, JPA 의 버전 관리 기능을 사용한다.
    * 즉, 애플리케이션이 제공하는 락이다.
    * ㄴ트랜잭션을 커밋하기 전까지는 트랜잭션의 충돌을 알 수 없다는 특징이 있다.
* 비관적 락
    * 트랜잭션의 충돌이 발생한다고 가정하고, 우선 락을 걸고 작업하는 방법
    * 데이터베이스의 락 기능을 사용한다.
    * 대표적으로 **select for update** 구문이 존재한다.
        * 특정 세션이 데이터에 접근하여 수정을 완료할 때 까지, 다른 세션이 접근할 수 없다.

### 두번의 갱신 분실 문제

* 조회된 같은 데이터에 대해서 A 가 먼저 수정한 후, B 가 수정하면 A 의 수정내용은 B 의 내용으로 덮어씌워져 결국 B 의 수정사항만 남는 현상
* 두번의 갱신 분실 문제 해결방안
    1. 마지막 커밋만 인정하기
        * A 의 내용은 무시하고 마지막 커밋한 B 의 내용만 인정한다.
    2. 최초 커밋만 인정하기
        * A 의 내용이 이미 수정으 완료하였으므로, B 의 수정 완료 시 오류가 발생한다.
    3. 충돌하는 갱신 내용 병합하기
        * A, B 의 내용의 수정사항을 병합한다.
    
### @Version

* JPA 의 낙관적 락을 사용하려면 @Version 어노테이션을 사용해서 버전 관리 기능을 추가해야 한다.
* @Version 적용 가능한 타입
    1. Long (long)
    2. Integer (int)
    3. Short (short)
    4. Timestamp
    
```java
@Entity
public class Board {
    ...
    
    @Version
    private Integer version;
    // 버전 관리 기능을 사용하기 위해서는 엔티티에 버전 관리용 필드를 하나 추가하고
    // @Version 어노테이션을 붙여야 한다.
    // 엔티티를 수정할 때 마다 버전이 하나씩 자동으로 증가한다.
    // 엔티티 수정할 때 조회 시점의 버전과 수정 시점의 버전이 다르면 예외가 발생한다.
}

...

// 트랜잭션 1, version = 1
Board board1 = em.find(Board.class, 1L);

...

// 트랜잭션 2, version = 1
Board board2 = em.find(Board.class, 1L);
board2.setTitle("some change value");

save(board2);
// version 이 2로 증가.
tx.commit();
...

// 트랜잭션 1
board1.save("some change value2");

save(board1);
tx.commit();
// 예외 발생 -> 데이터베이스 version = 2, 엔티티 version = 1
```

* @Version 을 사용하면, **최초 커밋만 인정하기** 가 적용된다.

#### 버전 정보 비교 방법

* 엔티티를 수정하고 트랜잭션을 커밋하면, 영속성 컨텍스트를 flush 하면서 UPDATE 쿼리를 실행하고, @Version 을 사용한다면 검색 조건에 엔티티의 버전 정보를 추가한다.

```
UPDATE Board
Set Title=?,
    Version=? /*기존 버전 + 1*/
WHERE Id=?
AND Version=? /*기존 버전*/
```

* where 조건에 기존 버전을 조건을 추가하여, 업데이트가 성공되면 성공으로 취급하고, 업데이트 row 가 없다면 JPA 가 예외를 발생시킨다.
* 즉, 버전은 엔티티의 값을 변경하면 증가한다.
* @Version 에 해당하는 필드는 JPA 가 직접 관리하므로, 벌크 연산을 제외하고는 개발자가 임의로 수정하면 안된다.
