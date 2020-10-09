# 상속에 의한 패러다임 불일치

* 객체는 상속이 가능하다.

![inheritance model](../../../images/1.2.1%20inheritance%20model.png)

* 데이터베이스 모델에서는 슈퍼타입/서브타입 관계를 사용하면 객체 상속과 가장 유사한 형태로 설계가 가능하다.
* 아래 그림과 같이 dtype 컬럼을 사용해서 어떤 자식 테이블과 관계가 있는지 정의할 수 있다.

![table model](../../../images/1.2.1%20table%20model.png)

```java
abstract class Item {
    Long id;
    String name;
    int price;
}

class Album extends item {
    String artist;
}

class Movie extends Item {
    String director;
    String actor;
}

class Book extends Item {
    String author;
    String isbn;
}
```

```sql
INSERT INTO Item ...;
Insert INTO Album or Movie or Book ...;
```

* 위 형태에서 Album, Movie, Book 을 저장하려면 item 을 저장을 먼저 하거나 이전에 존재하던 item 을 셋팅하고 저장해야 한다.
* jdbc API 에서 저장하려면 객체에서 부모와 자식의 객체를 추출하고 각자 insert sql 을 작성하고 dtype 도 지정해야 한다.
* 자식을 조회하는 경우에도 item 을 join 하고 그 결과를 통해 객체를 생성해야 한다.
* 즉, 위 과정들이 모두 패러다임의 불일치를 해결하기 위해 소모되는 비용이다.

## JPA 와 상속

* 패러다임의 불일치 문제를 개발자 대신 해결해준다.
* 자바의 컬렉션 객체에 저장하듯 JPA 에게 객체를 저장하면 된다.

```
jpa.persist(album);

-> 해당 메소드를 실행하면 다음 sql 들이 실행된다.
INSERT INTO Item ...
INSERT INTO album ...

Long albumId = 1;
Album album = jpa.find(Album.class, albumId);

-> 해당 메소드는 두 테이블을 조인해서 필요한 데이터를 조회하고 그 결과를 반환한다.
SELECT i.*, a.* FROM Item i JOIN Album a on i.id = a.item_id
```
