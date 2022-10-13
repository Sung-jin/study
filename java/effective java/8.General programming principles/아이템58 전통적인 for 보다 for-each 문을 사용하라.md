## 전통적인 for 보다 for-each 문을 사용하라

```java
for (Iterator<Element> i = c.iterator(); i.hasNext();) {
    ...
}

for (int i = 0; i < a.length; i++) {
    ...
}
```

* 위와 같은 관용구들은 while 보다는 좋지만, 반복자와 인덱스 변수는 거의 사용되지 않으나 선언되고 있다
* 또한 잘못된 변수를 사용하더라도 컴파일러가 잡아주는 보장도 없다
* 컬렉션 또는 배열에 따라 코드 형태가 상당히 달라진다

```java
enum Foo { ... }
enum Bar { ... }

Collection<Foo> foos = Arrays.asList(Foo.values());
Collection<Bar> bars = Arrays.asList(Bar.values());

List<Something> res = new ArrayList<>();

for (Iterator<Foo> f = foos.iterator(); f.hasNext();)
    for (Iterator<Bar> b = bars.iterator(); b.hasNext();)
        res.add(new Something(f.next(), b.next()));

// 위와 같이 b 에 해당하는 loop 에서 f 에 대한 next() 가 여러번 불릴 수 있기 때문에
// noSuchElementException 이 발생할 확률이 높다
```

### for-each

* 위와 같은 상황을 for-each 를 사용함으로써 모두 해결이 된다
    * 정식 이름은 '향상된 for 문' 이다
* 반복자와 인덱스를 사용하지 않아 코드가 깔끔해지고 오류가 날 일도 없어진다
* 하나의 관용구로 컬렉션과 배열을 모두 처리할 수 있어서 어떠한 컨테이너를 다루던 신경쓰지 않아도 된다
* for-each 는 Iterable 인터페이스를 구현한 객체라면 무엇이든 순회할 수 있다
    * Iterable 을 구현하기는 어렵지만, 원소들의 묶음을 표현하는 타입을 작성해야 한다면 Iterable 을 구현하는 것을 고려하는게 좋다

```java
for (Element e: elements) {
    ...
}
```

* 위의 문제되는 코드는 for-each 로 간단하고 심플하게 처리가 가능하다

```java
for (Foo f: foos)
    for (Bar b: bars)
        res.add(new Something(f.next(), b.next()));
```

### for-each 를 사용할 수 없는 상황

* 파괴적인 필터링
    * 컬렉션을 순회하면서 선택된 원소를 제거해야 할 때 반복자의 remove 메서드를 호출해야 한다
    * Java 8 의 Collection 에 removeIf 메서드를 이용하여 컬렉션을 명시적으로 순회하는 일을 피할 수 있다
* 변형
    * 리스트나 배열을 순회하면서 해당 원소의 값 일부 또는 전체를 교체해야 하는 경우, 리스트의 반복자나 배열의 인덱스를 사용해야 한다
* 병렬 반복
    * 여러 컬렉션을 병렬로 순회해야 한다면 각각의 반복자와 인덱스 변수를 사용하여 엄격하고 명시적으로 제어해야 한다
* 위와 같은 상황에서는 일반적인 for 문을 사용하되 위의 for 관용구에서 발생하는 문제를 경계해야 한다

## 정리

* 전통적인 for 문과 비교하였을 때 for-each 문은 명료하고 유연하고 버그를 예방해 주면서 성능저하도 없다
* 가능한 모든 곳에서 for 문이 아닌 for-each 문을 사용하는게 좋다
