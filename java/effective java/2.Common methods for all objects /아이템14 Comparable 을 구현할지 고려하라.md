## Comparable 을 구현할지 고려하라

* `Comparable` 인터페이스의 `compareTo` 메서드는 Object 메서드가 아니다
* `compareTo` 는 동치성 비교 + 순서 비교가 가능하고 제네릭하다
* Comparable 을 구현했다는 의미는 해당 클래스의 인스턴스들에는 자연적인 순서가 있음을 뜻한다
    * 객체들의 배열을 `sort` 함수로 정렬할 수 있다
* Comparable 을 구현하면, 해당 인터페이스를 사용하는 수많은 제네릭 알고리즘과 컬렉션에서 활용할 수 있다
* 알파벳, 숫자, 연대 등과 같이 순서가 명확한 값 클래스를 작성한다면 반드시 Comparable 인터페이스를 구현하는게 좋다

### Comparable 일반 규약

* 해당 객체와 주어진 객체의 순서를 비교한다
* 해당 객체가 주어진 객체보다 작으면 음의 정수, 같으면 0, 크면 양의 정수를 반환한다
* 객체와 비교할 수 없는 타입의 객체가 주어지면 ClassCastException 이 발생한다
* Comparable 을 구현한 클래스는 모든 x,y 에 대해 sgn(x.compareTo(y)) == -sgn(y.compareTo(x)) 이어야 한다
* Comparable 을 구현한 클래스는 추이성을 보장해야 한다
    * x.compareTo(y) > 0 && y.compareTo(z) > 0 이면 x.compareTo(z) > 0
* Comparable 을 구현한 클래스느 모든 z 에 대해 x.compareTo(y) == 0 이면 sgn(x.compareTo(z)) == sgn(y.compareTo(z)) 이다
* (x.compareTo(y) == 0) == (x.equals(y)) 는 권고사항이다
    * 이를 지키지 않는 모든 클래스는 해당 사실을 명시해야 한다
    * 이는 compareTo 메서드로 수행한 동치성 테스트의 결과가 equals 와 같아야 한다는 의미이다
    * 이를 지키면 compareTo 의 결과와 equals 의 결과가 일관된다
    * `BigDecimal("1.0)`/`BigDecimal("1.00)` 을 HashSet 과 TreeSet 에 넣으면 HashSet 은 원소 2개를 가지고 TressSet 은 1개의 원소만 가지게 된다
    
#### 일반 규약 상세

* Comparable 일반 규약은 결국엔 equals 규약과 똑같이 반사성/대칭성/추이성을 충족해야 하며, 주의사항도 같다
* 기존 클래스를 확장한 구체 클래스에서 새로운 값 컴포넌트를 추가하였다면, compareTo 규약을 지킬 방법이 없다
    * 우회방법은 확장 대신 독립된 클래스를 만들고, 해당 클래스에 원래 클래스의 인스턴스를 가리키는 필드를 두면 된다
    * 그리고 내부 인스턴스를 반환하는 뷰 메서드를 제공하면 된다
    * 이를 통해 바깥 클래스에 원하는 compareTo 메서드를 구현해 넣을 수 있게 된다

#### 주의사항

* compareTo 메서드에서 정수 기본 타입 필드를 비교할 때는 관계 연산자인 `<`/`>` 를, 실수 기본 타입 필드를 비교할 때는 정적 메서드인 `Double.compare`/`Float.compare` 를 사용하도록 권고 했었다
* 이는 자바7 부터 박싱된 기본 타입 클래스들에 새로 추가된 정적 메서드인 compare 이용할 수 있게 되면서 이전 방식은 거추장스럽고 오류를 유발하는 방식이 되었다

#### 기본 필드가 여러개인 객체 비교

* 클래스의 핵심 필드가 여러개일때 먼저 비교하는 순서에 따라 결과가 달라진다
* 이는 핵심 필드부터 순서대로 비교를 하고, 비교 결과가 0 인 경우에 다음 대상을 비교하는 식으로 진행해야 한다

```java
private static final Comparator<PhoneNumber> COMPARATOR = comparingInt((PhoneNumber pn) -> pn.areaCode)
        .thenComparingInt(pn -> pn.prefix)
        .thenComparingInt(pn -> pn.lineNum);

public int compareTo(PhoneNumber pn) {
    return COMPARATOR.compare(this, pn);
}

// 자바 8의 메서드 연쇄 방식으로 비교자를 생성할 수 있다
// 하지만 비교할 대상을 하나하나 직접 비교하는 것보다 성능적인 측면에서 떨어진다
```

#### 값의 차를 기준으로 비교

```java
static Comparator<Object> hashCodeOrder = new Comparator<>() {
    public int compare(Object o1, Object o2) {
        return o1.hashCode() - o2.ghashCode();
    }
};
// 이는 정수 오버플로를 일으키거나 IEEE754 부동소수점 계산 방식에 따른 오류가 발생할 수 있다

static Comparator<Object> hashCodeOrder = new Comparator<>() {
    public int compare(Object o1, Object o2) {
        return Integer.compare(o1.hashCode() - o2.ghashCode());
    }
};
static Comparator<Object> hashCodeOrder = Comparator.comparingInt( o-> o.hashCode());
// 대신 위와 같은 형태로 비교를 할 수 있다
```

## 정리

* 순서를 고려해야 하는 값 클래스를 작성한다면 꼭 Comparable 인터페이스를 구현해야 한다
    * 이를 통해 인스턴스들을 쉽게 정렬/검색/비교 기능을 제공하는 컬렉션에서 정상 동작하도록 해야 한다
* compareTo 메서드에서 필드의 값을 비교할 때는 `<`/`>` 연산자는 쓰지 말아야 한다
    * 대신 박싱된 기본 타입 클래스가 제공하는 정적 compare 메서드나 Comparator 인터페이스가 제공하는 비교자 생성 메서드를 사용해야 한다
