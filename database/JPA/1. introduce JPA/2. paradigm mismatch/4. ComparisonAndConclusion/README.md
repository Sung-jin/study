## 비교

| 구분 | 비교방법 | 비고 |
| ---- | ---- | --- |
| 데이터베이스 | 기본 키의 값과 row 를 통해 비교 | |
| 객체 | 동일성 비교와 동등성 비교 | - 동일성 비교 : == 비교이며, 주소를 통한 비교이다. <br/><br/> - 동등성 비교 : equals() 메소드를 통해 객체 내부의 값을 비교 |

```java
Long id = 1;
Item item1 = itemDao.getItem(id);
Item item2 = itemDao.getItem(id);

item1 == item2; // false
```

* 위와 같이 db 상으로 같은 데이터이지만, 객체 측면에서는 서로 다른 인스턴트이기 때문에 (new 를 통해 생성) 동일성 비교는 false 가 리턴된다.
* 위와 같은 패러다임 불일치 해결을 위해 데이터베이스의 같은 데이터를 비교할 때 같은 인스턴스를 반환하도록 구현하는 것은 어렵다.

```java
Long id = 1;
Item item1 = jpa.find(Item.class, id);
Item item2 = jpa.find(Item.class, id);

item1 == item2; // true
```

* 하지만, jpa 의 경우에는 같은 데이터베이스의 값을 같은 트랜잭션 안에서 다른 객체에 저장하더라도 같은 객체가 조회됨을 보장한다.

## 정리!

* 객체 모델 <-> 관계형 데이터베이스 모델은 지향하는 패러다임이 서로 다르다.
    * 다른 패러다임을 해결하는데 개발자가 모든 부담을 가지게 되면 이러한 차이점을 해소하는데 더 많은 시간과 노력이 발생한다.
* 또한, 문제를 해결하더라도 모델링이 변하거나 모델링이 복잡해지면 패러다임의 차이는 더욱 커지게 되고 기존의 코드를 모두 수정하고 해결하는데 큰 노력이 추가적으로 들게된다.
* 결국에는 객체 모델링에서 데이터 중심의 모델로 변하게 될 것이다.
* JPA 는 이러한 패러다임 불일치 문제를 해결해주고 정교한 객체 모델링을 유지할 수 있게 도와준다.
 
