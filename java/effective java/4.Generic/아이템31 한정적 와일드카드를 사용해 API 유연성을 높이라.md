## 한정적 와일드카드를 사용해 API 유연성을 높이라

* 매개변수화 타입은 불공변이다
    * List<String> 은 List<Object> 의 하위 타입이 아니다 (List<String> 은 List<Object> 가 하는 일을 수행 못하므로 리스코프 치환 원칙에 어긋난다)

```java
public class Stack<E> {
    ...
    public void pushAll(Iterable<E> src) {
        for (E e: src) {
            push(e);
        }
    }
}

Stack<Number> numberStack = new Stack<>();
Iterazble<Integer> integers = ...;
numberStack.pushAll(integers);
// Integer 는 Number 의 하위 타입이므로 문제가 될 것 같지 않으나,
// 매개변수화 티입은 불공변이기 때문에 에러가 발생한다
```

### 한정적 와일드카드

```java
public void pushAll(Iterable<? extends E> src) {
    for (E e: src) {
        push(e);
    }
}
```

* 위와 같이 한정적 와일드카드를 활용하면 정상 동작한다
    * 위의 한정적 와일드카드는 E 의 Iterable 이 아닌, E 의 하위 타입의 Iterable 을 의미한다
    * 하위 타입에는 자기 자신도 포함된다
    
```java
public void popAll(Collection<? super E> dst) {
    while (!isEmpty()) {
        dst.add(pop());
    }
}

Stack<Number> numberStack = new Stack<>();
Collection<Object> objects = ...;
numberStack.popAll(objects);
```

* 반대로 위와 같이 활용하면, E 의 Collection 이 아닌 E 의 상위 타입의 Collection 이라는 의미의 한정적 와일드카드를 선언할 수 있다
* 유연성을 극대화하려면 원소의 생상자나 소비자용 입력 매개변수에 와일드카드 타입을 사용해야 한다
    * 생산자와 소비자 역할을 동시에 한다면 와일드카드 타입을 사용해도 좋은게 없다
    * `생산자(producer)-extends`/`소비자(consumer)-super`
* 참고로 반환 타입에 한정적 와일드카드 타입을 사용할 경우, 클라이언트 코드에서도 와일드카드 타입을 사용 해야 하므로, 반환 타입에는 와일드카드 한정자를 사용하면 안된다
* 클래스 사용자가 와일드카드 타입을 신경 써야 한다면, 해당 API 는 문제가 있을 가능성이 크다
    * 와일드카드 타입이 알아서 매개변수를 받고 거절을 하기 때문이다
* 메서드 선언에 타입 매개변수가 한 번만 나오면 와일드 카드로 대체하라
    * 비한정적 타입 매개변수라면 비한정적 와일드카듸로, 한정적 타입 매개변수라면 한정적 와일드카드로 변경하면 된다
    
## 정리

* 복잡하더라도 와일드카드 타입을 적요하면 API 가 훨씬 유연해진다
* 널리 사용될 라이브러리를 작성한다면, 반드시 와일드카드 타입을 적절히 사용해줘야 한다
* `생산자(producer)-extends`/`소비자(consumer)-super`
* Comparable/Comparator 는 모두 소비자이다
