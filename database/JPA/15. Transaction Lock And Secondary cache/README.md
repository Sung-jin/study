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
