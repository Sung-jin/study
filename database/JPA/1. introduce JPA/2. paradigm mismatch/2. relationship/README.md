## 연관관계

* 객체는 참조를 사용해서 다른 객체와 연관관계를 가지고 참조를 통해 연관된 다른 객체를 조회한다.
* 테이블은 외래키를 사용해서 다른 테이블과 연관관계를 가지고 조인을 사용해서 연관된 테이블을 조회한다.

![table model](../../../images/1.2.2%20table%20model%202.png)

* 위 테이블 그림의 item 과 album 을 이용하기로 하자.

```java
class Item {
    Long id;
    String name;
    int price;
}

class Album {
    Long id;
    String artist;
    Long item;
}
```

```mysql
SELECT i.*, a.* FROm item i LEFT JOIN album a on a.item = i.id;
-- album 을 통해서 item 에 join 을 걸어서 item 에 접근할 수 있다.
```

* erd 그대로 클래스를 만들 경우 테이블의 컬럼을 그대로 사용하기 때문에 db 에 조회, 저장은 편리하다.
* 하지만, rdb 의 경우에는 join 을 통해서 외래 키 값을 그대로 보관하여 해당 테이블에 접근하기가 편리하지만 객체는 연관된 객체의 참조를 보관해야 참조가 편하다.
* 즉, 객체지향적인 방법을 이용하기 위해서는 참조를 사용해야 한다.

```java
class Album {
    Long id;
    String artist;
    Item item;

    Item getItem() {
        return item;
    }
}

...
Item item = album.getItem();
```

* 객체지향 모델링을 사용하면 객체를 테이블에 저장하거나 조회하기가 쉽지 않다.
* 즉, rdb 와 객체지향 모델링 사이에 개발자가 중간에서 변환 역할을 해줘야 한다.

```java
Long albumId = album.getId();
String artist = album.getString();
Long ItemId = album.getItem().getId();

String sql = "INSERT INTO album....."
// 저장

String sql = "SELECT i.*, a.* FROM item i LEFT JOIN a on a.item = i.id WHERE ...";
...
Long itemId = resultSet.get("ItemId");
...
Item item = new Item(itemId, itemName, itemPreice);
Album album = new Album(albumId, artistName, item);
// 조회
```

* 위와 같이 패러다임 불일치를 해결하려고 소모하는 비용은 크며, 이를 자바 컬렉션에 회원 객체를 저장한다면 이런 비용이 전혀 들지 않는다.

## JPA 와 연관관계

```java
member.setTeam(team); // 회원과 팀의 연관관계를 설정한다.
jpa.persist(member);  // 회원과 연관관계를 함께 저장한다.
// 저장
Member member = jpa.find(Member.class, id);
Team team = member.getTeam();
```

* JPA 를 이용하면 연관관계 패러다임 불일치 문제를 해결해 준다.
