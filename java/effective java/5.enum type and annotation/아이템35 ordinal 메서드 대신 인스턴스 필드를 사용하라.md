## ordinal 메서드 대신 인스턴스 필드를 사용하라

* 열거 타입 상수는 하나의 정숫값에 대응되며, 모든 열거 타입은 해당 상수가 그 열거 타입에서 몇 번째 위치인지 반환하는 ordinal 메서드를 제공한다

```java
public enum Ensemble {
    SOLE, DUET, TRIO, QUARTET, QUINTET,
    SEXTET, SEPTET, OCTET, NONET, DECTET;
    
    public int numberOfMusicians() { return ordinal() + 1; }
}
```

* 위와 같이 ordinal 을 이용하여 상수를 표현할 수 있으나, 상수의 위치를 변경하는 순간 `numberOfMusicians` 를 사용하는 모든 곳은 문제가 발생한다
    * OCTET 와 같이 8명이서 연주하는 복 4중주 double quartet 는 추가할 수 없다
* 또한 이미 사용중인 정수와 값이 같은 상수를 추가할 방법이 없으며, 중간 값을 비울 수 없다
    * 위의 예에서는 12 명이 연주하는 triple quartet 가 있으나, 11 명이 연주하는 이름은 없다
    
### 해결방안

* 열거 타입 상수에 연결된 값은 ordinal 메서드로 얻지말고 인스턴스 필드에 저장하면 된다

```java
public enum Ensemble {
    SOLE(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
    SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),
    NONET(9), DECTET(10), TRIPLE_QUARTET(12);
    
    private final int numberOfMusicians;
    Ensemble(int size) { this.numberOfMusicians = size; }
    public int getNumberOfMusicians() { return numberOfMusicians; }
}
```

## 정리

* Enum 의 API 문서의 ordinal 에는 '대부분 프로그래머는 해당 메서드를 쓸 일이 없다' 라고 작성되어 있다
* ordinal 은 EnumSet, EnumMap 과 같은 열거 타입기반의 범용 자료구조에 사용될 목적으로 설계되어있다
* 즉, 이러한 용도가 아니라면 ordinal 메서드는 절대 사용하지 말아야 한다
