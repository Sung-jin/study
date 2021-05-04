## 2차 캐시

* 애플리케이션 -> 데이터베이스에 접근하는 시간 비용 >>> 애플리케이션 내부 메모리 접근 시간
* 영속성 컨텍스트 내부에서 보관하는 엔티티들을 1차 캐시라고 한다.
    * 1차 캐시의 경우, 트랜잭션을 시작하고 종료할 때까지만 유효하며, OSIV 를 사용하더라도 한계가 존재한다.
    * 즉, 애플리케이션 전체를 봤을 떄 데이터베이스 접근 횟수를 획기적으로 줄이지 못한다.
* 대부분의 JPA 구현체들은 애플리케이션 범위의 캐시를 지원하며, 이를 공유 캐시 또는 2차 캐시라고 한다.

![](../images/15.secon%20level%20cache.png)

* 위 그림처럼 2차 캐시를 적용한다면, 영속성 컨텍스트에 찾는 엔티티가 없을 경우 2차 캐시에서 조회를 하고, 2차 캐시에도 없다면 DB 에서 조회한다.

### 1차 캐시

* 영속성 컨텍스트 내부에 1차 캐시가 존재한다.
    * 엔티티 매니저로 조회/변경 시 모든 엔티티는 1차 캐시에 저장된다.
    * 트랜잭션을 커밋하거나 플러시를 호출하면 1차 캐시에 있는 엔티티의 변경 내역을 데이터베이스에 동기화한다.
* 1차 캐시는 끄고 켤 수 있는 옵션이 아니고, 영속성 컨텍스트 자체가 사실상 1차 캐시이다.
* 1차 캐시의 동작 방식
    1. 엔티티를 조회시 1차 캐시에 해당 엔티티가 없을 경우 데이터베이스에서 조회한다.
    2. 조회한 엔티티는 1차 캐시에 보관한다.
    3. 이후 같은 데이터를 조회하면 1차 캐시에 엔티티가 존재하므로, 데이터베이스에 조회하지 않고 1차 캐시에 있는 엔티티를 반환한다.
* 1차 캐시의 특징
    1. 1차 캐시는 같은 엔티티가 있으면, 해당 엔티티를 반환하며 1차 캐시는 객체 동일성 (a == b) 를 보장한다.
    2. 1차 캐시는 기본적으로 영속성 컨텍스트 범위의 캐시ㅏ이다.
        * 컨테이너 환경에서는 트랜잭션 범위의 캐시
        * OSIV 를 적용하면 요청 범위의 캐시

### 2차 캐시

* 애플리케이션에서 공유하는 캐시를 공유 캐시라고 하며, 일반적으로 2차 캐시라고 부른다.
* 2차 캐시는 애플리케이션을 종료할 때까지 캐시가 유지된다.
    * 분산 캐시나 클러스터링 캐시는 애플리케이션보다 더 오래 유지될 수 있다.
* 2차 캐시를 적용하였다면, 엔티티 매니저를 통해 데이터를 조회할 때 2차 캐시에서 찾고 없으면 데이터베이스에서 조회한다.
    * 즉, 2차 캐시를 적절하게 활용한다면 데이터베이스 조회 횟수를 줄일 수 있다.
* 2차 캐시의 동작 방식
    1. 영속성 컨텍스트는 엔티티가 필요하면 2차 캐시를 조회한다.
    2. 2차 캐시에 엔티티가 없으면 데이터베이스를 조회해서 결과를 2차 캐시에 보관한다.
    3. 2차 캐시는 자신이 보관하는 엔티티를 복사해서 반환한다.
    4. 2차 캐시에 저장되어 있는 엔티티를 조회하면 복사본을 만들어 반환한다.
* 2차 캐시는 동시성을 극대화하기 위해 캐시한 객체를 직접 반환하지 않고 복사본을 만들어서 반환한다.
    * 객체 자체를 반환한다면, 여러 곳에서 동시에 수정하면서 문제가 발생 할 수 있기 때문이다.
    * 객체에 락을 걸어서 해결할 수 있지만, 동시성이 떨어지게 된다.
    * 락에 비하면 객체 복사의 비용이 훨씬 저렴하다.
* 2차 캐시의 특징
    * 2차 캐시는 영속성 유닛 범위의 캐시이다.
    * 2차 캐시는 조회한 객체를 그대로 반환하는 것이 아니라 복사본을 만들어서 반환한다.
    * 2차 캐시는 데이터베이스 기본 키를 기준으로 캐시하지만 영속성 컨텍스트가 다르면 객체 동일성 (a == b) 를 보장하지 않는다.
    
## JPA 2차 캐시 기능

* JPA 2.0 에서 2차 캐시 표준을 정의하였다.
    * 이전에는 JPA 구현체 2차 캐시 기능을 각자 지원했다.

#### 캐시 모드 설정

```java
@Cacheable
// @Cacheable(true or false)
// true 가 기본값이다.
@Entity
public class Entity {
    ...
}
```

```xml
<persistence-unit name="test">
    <shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
    <!-- persistence.xml 에 설정하여 영속성 유닛 단위에 캐시 적용 방식을 지정할 수 있다. -->
</persistence-unit>
```

* SharedCacheMode 캐시 모드 설정

| 캐시 모드 | 설명 |
| ---- | ---- |
| ALL | 모든 엔티티를 캐시한다. |
| NONE | 캐시를 사용하지 않는다. |
| ENABLE_SELECTIVE | Cacheable(true) 로 설정된 엔티티만 캐시를 적용한다. |
| DISABLE_SELECTIVE | 모든 엔티티를 캐시하지만, Cacheable(false) 로 설정된 엔티티는 캐시하지 않는다. |
| UNSPECIFIED | JPA 구현체가 정의한 설정을 따른다. |

### 캐시 조회, 저장 방식 설정

* 캐시를 무시하고 데이터베이스를 직접 조회하거나 캐시를 갱신하려면 캐시 조회 모드와 캐시 보관 모드를 사용하면 된다.
* 프로퍼티
    * javax.persistence.cache.retrieveMode : 캐시 조회 모드 프로퍼티 이름
    * javax.persistence.cache.storeMode : 캐시 보관 모드 프로퍼티 이름
* 옵션
    * javax.persistence.CacheRetrieveMode : 캐시 조회 모드 설정 옵션
    * javax.persistence.CacheStoreMode : 캐시 보관 설정 모드

```java
public enum CacheRetrieveMode {
    USE,    // 기본값이며, 캐시에서 조회한다.
    BYPASS  // 캐시를 무시하고 데이터베이스에 직접 접근한다.
}

public enum CacheStoreMOde {
    USE,    // 기본값이며, 조회한 데이터를 캐시에 저장한다. 
            // 조회한 데이터가 이미 캐시에 있다면, 해당 데이터를 최신 데이터로 갱신하지 않는다.
            // 트랜잭션을 커밋하면 등록 수정한 엔티티도 캐시에 저장한다.
    BYPASS, // 캐시에 저장하지 않는다.
    REFRESH // USE 전략 + 데이터베이스에 조회한 엔티티를 최신 상태로 다시 갱신한다.
}

em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
em.setProperty("javax.persistence.cache.storeMode", CacheMode.BYPASS);
// EntityManager 단위로 설정이 가능하다.

Map<String, Object> param = new hashMap<String, Object>();
param.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
param.put("javax.persistence.cache.storeMode", CacheMode.BYPASS);
em.find(Entity.class, id, param);
// find()/refresh 단위로 설정이 가능하다.

em.createQuery("select e from Entity e where ...")
    .setParameter(...)
    .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
    .setHint("javax.persistence.cache.storeMode", CacheMode.BYPASS)
    .getSingleResult();
// zQuery.setHint (TypeQuery 포함) 에 사용할 수 있다.
```

### JPA 캐시 관리 API

```java
Cache cache = emf.getCache();
boolean contains = cache.contains(Entity.class, id);
// 위와 같이 EntityManagerFactory 에서 캐시를 관리하는 인터페이스를 제공받을 수 있다.

puyblic interface Cache {
    public boolean contains(Class cls, Object primaryKey);
    // 해당 엔티티가 캐시에 있는지 여부 확인
    
    public void evict(Class cls, Object primaryKey);
    // 해당 엔티티중 특정 식별자를 가진 엔티티를 캐시에서 제거
    
    public void evict(Class cls);
    // 해당 엔티티 전체를 캐시에서 제거
    
    public void evictAll();
    // 모든 캐시 데이터 제거
    
    public <T> T unwrap(Class<T> cls);
    // JPA Cache 구현체 조회
}
```

## 하이버네이트와 EHCACHE 적용

* 하이버네이트가 지원하는 캐시
    1. 엔티티 캐시 - 엔티티 단위로 캐시하며, 식별자로 엔티티를 조회하거나 컬렉션이 아닌 연관된 엔티티를 로딩할 때 사용한다.
    2. 컬렉션 캐시 - 엔티티와 연관된 컬렉션을 캐시하며, 컬렉션이 엔티티를 담고 있으면 식별자 값만 캐시한다.
    3. 쿼리 캐시 - 쿼리와 파라미터 정보를 키로 사용해서 캐시하며, 결과가 엔티티면 식별자 값만 캐시한다.
* JPA 표준에는 엔티티 캐시만 정의되어 있다.

### 환경설정

```xml
<!--pom.xml-->
<dependency>
    ...
    <artifactId>hibernate-ehcache</artifactId>
    ...
</dependency>

...

<!--ehcache.xml-->
<!--EHCACHE 는 ehcache.xml 을 설정 파일로 사용한다.-->
<!--설정값 doc : https://www.ehcache.org/documentation/-->
<ehcache>
    <defaultCache
        maxElementsInMemory="1000"
        eternal="false"
        timeToIdleSeconds="1200"
        timeToLiveSeconds="1200"
        diskExpiryThreadIntervalSeconds="1200"
        memoryStoreEvictionPolicy="LRU"
        />
</ehcache>

...

<!--persistence.xml-->
<!--하이버네이트 캐시 사용정보 설정-->
<persistence-unit name="test">
    <shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
    <properties>
        <property name="hibernate.cache.use_second_level_cache" value="true"/>
        <!--2차 캐시 활성화-->
        <!--엔티티 캐시와 컬렉션 캐시를 사용할 수 있다.-->
        <property name="hibernate.cache.use_query_cache" value="true"/>
        <!--쿼리 캐시를 활성화-->
        <property name="hibernate.cache.region.factory_class" value="org.hibernate.cache.ehcache.EhCacheRegionFactory"/>
        <!--2차 캐시를 처리할 클래스를 지정한다.-->
        <property name="hibernate.cache.generate_statistics" value="true"/>
        <!--하이버네이트가 여러 통계정보를 출력해주는데 캐시 적용 여부를 확인할 수 있다.-->
        <!--성능에 영향을 주므로, 개발 환경에서만 적용하는게 좋다.-->
    </properties>
    ...
</persistence-unit>
```

### 엔티티 캐시와 컬렉션 캐시

```java
@Cacheable
// 해당 어노테이션을 통해 엔티티를 캐시를 적용한다.
@Cache(usage = CacheConcureencyStrategy.READ_WRITE)
// 해당 어노테이션은 하이버네이트 전용이다.
@Entity
public class Parent {
    ...
    
    @Cache(usage = CacheConcureencyStrategy.READ_WRITE)
    // 클래스 레벨의 @Cache 와 같으며, 컬렉션 캐시를 적용할 때 사용할 수 있다.
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children = new ArrayList<Child>();
    
    ...
}
```

@Cache

| 속성 | 설명 |
| ---- | ---- |
| usage | CacheConcurrencyStrategy 를 사용해서 캐시 동시성 전략을 설정 |
| region | 캐시 지역 설정 |
| include | 연관 객체를 캐시에 포함할지 선택한다. <br/> all/non-lazy 를 선택할 수 있다 |

* CacheConcurrencyStrategy

| 속성 | 설명 |
| ---- | ---- |
| NONE | 캐시 설정 X |
| READ_ONLY | 읽기 전용으로 설정 <br/> 등록/삭제는 가능하지만 삭제는 불가능하다. |
| NONSTRICT_READ_WRITE | 엄격하지 않은 쓰기 전략 <br/> 동시에 같은 엔티티를 수정하면 데이터 일관성이 깨질 수 있다. <br/> EHCACHE 는 데이터를 수정하면 캐시 데이터를 무효화한다 |
| READ_WRITE | 읽기 쓰기가 가능하고, READ COMMITTED 정도의 격리 수준을 보장한다. <br/> EHCACHE 는 데이터를 수정하면 캐시 데이터도 같이 수정한다. |
| TRANSACTIONAL | 컨테이너 관리 환경에서 사용할 수 있다. <br/> 설정에 따라 REPEATABLE READ 격리 수준을 보장받을 수 있다. |

* 캐시 동시성 전략 지원 여부
    * ConcurrentHashMap 은 개발시에만 사용해야 한다.
    
| Cache | read-only | nonstrict-read-write | read-write | transactional |
| ---- | ---- | ---- | ---- | ----- |
| ConcurrentHashMap | O | O | O |  |
| EHCache | O | O | O | O |
| Infinispan | O | | | O |

### 캐시 영역

* 캐시를 적용시, 캐시 영역에 저장된다.
* 엔티티 캐시 영역은 기본값으로 [패키지명 + 클래스명] 을 사용한다.
* 컬렉션 캐시 영역은 엔티티 캐시 영역 이름에 ㅏ캐시한 컬렉션의 필드 명이 추가된다.
* @Cache(region = "someCacheName"...) 과 같이 캐시 영역을 직접 지정할 수 있다.
* 캐시 영역을 위한 접두사를 설정하려면 persistence.xml 설정에 hibernate.cache.region_prefix 를 사용해도 된다.
* 캐시 영역별 세부 설정이 가능하다.
    * ehcache.xml 에서 설정이 가능하다.
