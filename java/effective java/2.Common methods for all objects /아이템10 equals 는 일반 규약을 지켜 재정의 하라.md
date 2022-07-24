## equals 는 일반 규약을 지켜 재정의하라

* equals 메서드 재정의는 쉬워보이나 잘못 정의하면 문제가 발생할 가능성이 높다
    * 문제를 회피하는 가장 쉬운 길은 재정의하지 않는 방법이다
    * 재정의하지 않으면 클래스의 인스턴스는 오직 자기 자신과만 같게 된다
* 아래와 같은 상황에 해당한다면 재정의하지 않는 것이 최선이다
    1. 각 인스턴스가 본질적으로 고유한 경우
        * 값을 표현하는게 아닌 동작하는 개체를 표현하는 클래스가 여기에 해당한다
        * `Thread` 가 좋은 예이며, Object 의 equals 메서드는 이러한 클래스에 딱 맞게 구현되어 있다
    2. 인스턴스의 논리적 동치성(logical equality) 을 검사할 일이 없는 경우
        * 정규식의 `Pattern` 은 equals 를 재정의해서 두 `Pattern` 인스턴스가 같은 정규표현식을 나타내는지 논리적 동치성을 검사하는 방법도 있다
        * 하지만 설계자는 클라이언트가 이 방식을 원하지 않거나 필요하지 않다고 느낄 수 있으며, 이때는 기본 equals 만으로 해결된다
    3. 상위 클래스에서 재정의한 equals 가 하위 클래스에도 딱 들어맞는 경우
        * `Set` 구현체는 `AbstractSet` 이 구현한 equals 를 상속받아 쓰고, `List` 는 `AbstractList`, `Map` 은 `AbstractMap` 으로부터 상속받아 사용한다
    4. 클래스가 private 이거나 package-private 이고 equals 메서드를 호출할 일이 없는 경우

### equals 를 재정의해야 하는 경우

* 객체 식별성(두 객체가 물리적으로 같은가)이 아닌 논리적 동치성을 확인해야 하며, 상위 클래스의 equals 가 논리적 동치성을 비교하도록 재정의되어 있지 않은 경우이다
* 주로 값 클래스들이 해당한다
    * 값 클래스는 Integer, String 과 같이 값을 표현하는 클래스를 말한다
    * 논리적 동치성을 확인하도록 재정의하면, 해당 인스턴스는 값을 비교하길 원하는 프로그래머의 기대에 부응하며, `Map`/`Set` 의 원소로 사용할 수 있게 된다
* 값 클래스이지만, 같은 인스턴스가 둘 이상 만들어지지 않음을 보장하는 싱글톤이라면 equals 를 재정의하지 않아도 된다

### equals 메서드 재정의시 지켜야할 일반 규약

1. 반사성(reflexivity): null 이 아닌 모든 참조 값 x 에 대해 `x.equals(x)` 는 true 이다
    * 객체는 자기 자신과 같아야 한다는 의미이다
    * 해당 규약을 어기면 컬렉션에서 존재하는 객체로 contains 메소드를 호출하면 false 가 리턴된다
2. 대칭성(symmetry): null 이 아닌 모든 참조 값 x/y 에 대해 `x.equals(y)` 가 true 이면 `y.equals(x)` 도 true 이어야 한다
    * 두 객체는 서로에 대한 동치 여부에 똑같이 답해야 한다는 의미이다
3. 추이성(transitivity): null 이 아닌 모든 참조 값 x/y/z 에 대해 `x.equals(y)` 가 true 이고 `y.equals(z)` 도 true 이면 `x.equals(z)` 는 true 이어야 한다
    * 첫번째 객체와 두번째 객체가 같고, 두번째 객체와 세번째 객체가 같다면, 첫번째와 세번째 객체는 같아야 한다
    * 추이성이 지켜지지 않으면 무한 재귀에 빠질 위험성이 존재한다
4. 일관성(consistency): null 이 아닌 모든 참조 값 x/y/z 에 대해 `x.equals(y)` 를 반복해서 호출하면 항상 같은 결과가 나와야 한다
    * 두 객체가 수정되지 않고 같다면 앞으로도 영원히 같아야 한다
    * 가변 객체의 경우 비교 시점에 따라 서로 다를 수 있으며, 불변 객체는 한번 다르면 끝까지 달라야 한다
    * 불변이든 아니든 equals 의 판단에 신뢰할 수 없는 자원이 끼어들면 안된다
        * 이러한 제약을 어기면 일관성 조건을 만족시키기가 아주 어렵다
        * 예를들어서 java.net.URL 의 equals 는 URL 과 매핑된 호스트의 IP 주소를 이용하여 비교하는데, 호스트 이름을 IP 주소로 바꾸려면 네트워크를 통해야 하고, 그 결과가 항상 같다고 보장할 수 없다
        * 이러한 문제를 피하기 위해서는 equals 는 항시 메모리에 존재하는 객체만을 사용한 결정적 걔산만 수행해야 한다
5. null 아님: null 이 아닌 모든 참조 값 x 에 대해 `x.equals(null)` 은 false 이다

* 컬렉션 클래스들을 포함하여 수많은 클래스는 전달받은 객체가 equals 규약을 지킨다고 가정하고 동작한다
* Object 명세에서 말하는 동치관계란 집합을 서로 같은 원소들로 이루어진 부분집합으로 나누는 연산이다
    * 이러한 부분집합을 동치류(equivalence class) 라고 한다
* equals 메서드가 쓸모있기 위해서는 모든 원소가 같은 동치류에 속한 어떤 원소와도 서로 교환할 수 있어야 한다

#### 대칭성

```java
public final class CaseInsensitiveString {
    private final String s;
    
    public CaseInsensitiveString(String s) {
        this.s = Object.requireNonNull(s);
    }
    
    @Override
    public boolean equals(Object o) {
        ...
        if (o instanceof String) {
            return s.equalsIgnoreCase((string) o);
        }
        ...
    }
}

CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
String s = "polish";

cls.equals(s); // true
s.equals(cis); // false

List<CaseInsensitiveString> list = new ArrayLuist<>();
list.add(cis);
list.contains(s);
// 이는 jdk 버전 등과 같은 요건으로 어떻게 동작할지 예상할 수 없다

----------------------------------------------------------------

@Override
public boolean equals(Object o) {
    // Point 클래스의 equals
    ...
    Point p = (Point) o;
    return p.x == x && p.y == y;
}

@Override
public boolean equals(Object o) {
    // ColorPoint 의 equals
    if (!(o instanceof ColorPoint)) return false;
    return super.equals(0) && ((ColorPoint) o).color == color;
    // 이는 Point 를 ColorPoint 와 비교한 결과와 그 두개를 바꿔서 비교한 결과가 다를 수 있다
    // Point 의 equals 는 색상을 무시하고 ColorPoint 의 equals 는 입력 매개변수의 클래스 종류가 다르기 때문에 매번 false 를 반환한다
}

Point p = new Point(1, 2);
ColorPoint cp = new ColorPoint(1, 2, Color.RED);

p.equals(cp); // true
cp.equals(p); // false
```

#### 추이성

```java
public class Point {
    private final int x;
    private final int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o) {
        ...
        Point p = (Point) o;
        return p.x == x && p.y == y;
    }
}

public class ColorPoint extends Point {
    private final Color color;
    
    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }
    
    // equals 를 재정의하지 않으면 상속한 equals 가 동작하게 된다
    // 이는 색상 정보에 대해서는 무시하고 equals 가 동작하게 된다
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;
        if (!(o instanceof ColorPoint)) return o.equals(this);
        
        return super.equals(o) && ((ColorPoint) o).color == color;
        // 대칭성은 지켜주지만 추이성은 깨진다
    }
}

ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
Point p2 = new Point(1, 2);
ColorPoint p3 = new ColoΩPoint(1, 2, Color.BLUE);

p1.equals(p2); // true
p2.equals(p3); // true
p1.equals(p3); // false

/**
 * Point 의 또다른 하위 클래스를 생성하고, equals 도 같은 방식으로 구현한다음
 * 서로 다른 Point 의 하위 클래스끼리 equals 를 호출하면 StackOverflowError 가 발생한다
 * 
 * 이러한 문제는 모든 객체 지향 언어의 동치관계에서 발생하는 근본적인 문제이다
 * 객체 지향적 추상화의 이점을 포기하지 않는 이상 구체 클래스를 확장해 새로운 값을 추가하면서 equals 규약을 만족시킬 방법은 존재하지 않는다
 */
```

* 리스코프 치환 원칙 위배
    * 리스코프 치환 원칙은 어떤 타입에 있어서 중요한 속성이라면 해당 하위 타입에서도 마찬가지로 중요하다
    * 즉, 해당 타입의 모든 메서드가 하위 타입에서도 똑같이 잘 동작해야 한다
    * 이는 Point 의 하위 클래스는 정의상 여전히 Point 이므로 어디서든 Point 로써 활용될 수 있어야 한다는 것을 의미한다

```java
@Override
public boolean equals(OBject o) {
    if (o == null | o.getClass() != getClass()) return false;
    Point p = (Point) o;
    return p.x == x && p.y == y;
}
/**
 * 이는 같은 구현 클래스의 객체와 비교할때만 true 가 반환한다
 * 하지만 Point 의 하위 클래스는 정의상 여전히 Point 이므로 어디서든 Point 로써 활용될 수 있어야하지만, 이러한 방식의 equals 는 활용할 수 없다
 */

private static final Set<Point> unitCircle = Set.of(
    new Point(1, 0), new Point(0, 1),
    new Point(-1, 0), new Point(0, -1)
);

public static boolean onUnitCircle(Point p) {
    return unitCircle.contains(p);
}
// 반지름이 1인 단위 원 안에 있는지 판별하는 메소드

public class CounterPoint extends Point {
    private static final AtomicInteger counter = new AtomicInteger();
    
    public CounterPoint(int x, int y) {
        super(x, y);
        counter.incrementAndGet();
    }
    
    public static int numberCreated() {
        return counter.get();
    }
}
/**
 * CounterPoint 인스턴스를 onUnitCircle 메서드에 전달하면, Point 클래스의 equals 를 getClass 로 작성하였기 때문에
 * onUnitCircle CounterPoint 인스턴스의 x/y 의 어떠한 값과는 상관없이 false 를 반환한다
 * 
 * 이는 거의 대부분의 컬렉션 구현체에서 주어진 원소를 담고 있는지 확인하는 방법이 equals 메서드를 활용하는데,
 * CounterPoint 의 어떠한 인스턴스는 어떤 Point 와 같을 수 없기 때문에 false 가 리턴된다
 * 이는 getClass 로 비교가 아닌 instanceof 로 비료를 했다면 onUnitCircle 메서드가 정상적으로 동작했을 것이다
 */
```

* 구체 클래스의 하위 클래스에서 값을 추가할 방법은 없지만 우회할 방법은 존재한다
    * 상속 대신 컴포지션을 사용하면 된다
    * 자바 라이브러리에는 대표적으로 `java.sql.Timestamp` 가 `java.util.Date` 를 확장한 후 nanoseconds 필드를 추가하였다
    * Timestamp 의 euqlas 는 대칭성을 위배하며, Date 객체와 하나의 컬렉션에 넣거나 서로 섞어 사용하면 엉뚱하게 동작할 수 있다

```java
public class ColorPoint {
    private final Point point;
    // Point 를 상속하는 대신 Point 를 private 필드로 정의하고
    private final Color color;
    
    public ColorPoint(int x, int y, Color color) {
        point = new Point(x. y);
        this.color = Object.requireNonNull(color);
    }
    
    // ColorPoint 의 point 의 뷰를 반환하는 메서드를 추가
    public Point asPoint() {
        return point;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint)) return false;
        ColorPoint cp = (ColorPoint) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }
}
```

#### null 아님

* 모든 객체가 null 과 같지 않아야 한다는 의미이다

```java
@Override
public boolean equals(Object o) {
    if (o == null) return false;
    // 명시적 null 검사는 필요하지 않다
    if (!(o intanceof SomeType)) return false;
    // instanceof 연산자로 전달받은 객체가 null 이 아닌지도 같이 확인하면서 비교를 진행할 수 있다
    // 타입을 확인하지 않고 비교를 하면 잘못된 타입이 주어졌을 때 ClassCaseException 이 발생하면서 일반 규약을 위배하게 된다
    // instanceof 연산자는 첫번째 피연산자가 null 이면 false 를 반환하므로 명시적으로 null 체크를 할 필요가 없다
}
```

### equals 메서드 구현 방법

1. `==` 연산자를 사용해 입력이 자기 자신의 참조인지 확인한다
    * 이는 자기 자신이면 true 를 반환하며, 단순한 성능 최적화용이고 비교 작업이 복잡한 상황일 때 값어치를 한다
2. `instanceof` 연산자로 입력이 올바른 타입인지 확인한다
    * 비교하려는 인스턴스의 클래스가 비교되는 대상을 구현한 특정 인터페이스가 될 수 있고, 어떤 인터페이스는 자신을 구현한 클래스끼리도 비교할 수 있도록 equals 규약을 수정하기도 한다
    * 인터페이스를 구현한 클래스라면 equals 에서 해당 인터페이스를 사용해야 한다
    * Set, List, Map, Map.entry 드으이 컬렉션 인터페이스들이 여기에 해당한다
3. 입력이 올바른 타입으로 형변환한다
    * `instanceof` 검사를 했다면 올바른 타입으로 형변환은 성공한다
4. 입력 객체와 자기 자신의 대응되는 핵심 필드들이 모두 일치하는지 하나씩 검사한다
    * 모든 필드가 일치하면 true, 하나라도 다르면 false 를 반환한다
    * 2단계에서 인터페이스를 사용하였다면, 입력이 필드 값을 가져올 때도 인터페이스의 메서드를 사용해야 한다
    * 타입이 클래스라면 접근 권한에 따라 해당 필드에 직접 접근할 수도 있다
    
### 비교 연산자 사용

* float/double 을 제외한 기본 타입 필드는 == 연산자로 비교한다
* 참조 타입 필드는 각각의 equals 메서드로, float/double 필드는 각각 정적 메서드인 `Float.compare(float, float)`/`Double.compare(double, double)` 로 비교한다
* float/double 을 특별 취급하는 이유는 `Float.NaN`/`-0.0f` 등과 같은 특수한 부동소수 값등을 다뤄야 하기 때문이다
    * Float.equals/Double.equals 를 사용할 수 있지만, 이는 오토박싱을 수반할 수 있어서 성능상 좋지 않다
* 배열 필드는 원소 각각을 앞서 지침대로 비교한다
    * 배열의 모든 원소가 핵심필드라면 Arrays.equals 메서드들 중 하나를 사용하면 된다
* null 을 정상 값으로 취급하는 참조 타입 필드도 존재하며, 이는 정적 메서드인 `Objects.equals(Object, Object)` 메서드를 사용하여 NullPointerException 을 방지해야 한다
* 비교하기가 아주 복잡한 필드를 가진 클래스는 해당 필드의 표준형을 저장해둔 후 표준형끼리 비교하면 훨씬 경제적이다
    * 이 기법은 불변 클래스에 적합하다
    * 가변 객체라면 값이 변경될때 마다 표준형을 최신 상태로 갱신해줘야 한다
* 어떤 필드를 먼저 비교하느냐에 따라 equals 의 성능이 좌우하기도 한다
    * 최상의 성능을 바란다면 다를 가능성이 더 크거나 비교하는 비용이 싼 필드를 먼저 비교해야 한다
* 동기화용 락 필드 같이 객체의 논리적 상태와 관련 없는 필드는 절대로 비교해서는 안된다
* 핵심 필드로부터 계산해낼 수 있는 파생 필드는 굳이 비교할 필요는 없으나, 파생 필드를 비교하는 쪽이 더 빠를 경우도 존재한다
    * 파생 필드가 객체 전체의 상태를 대표하는 상황에서 그러하다
    
### equals 를 구현 후

* 구현 후 `대칭적인가?/추이성이 있는가?/일관적인가?` 를 확인해봐야 한다
    * 이는 단위 테스트를 작성하여 확인이 가능하다
    * 반사성과 null 아님도 만족해야 하지만, 이 둘은 문제되는 경우가 거의 없다
    
```java
// 이상의 비법에 따라 작성한 PhoneNumber 클래스용 equals 메서드
public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;
    
    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "지역코드");
        this.prefix = rangeCheck(prefix, 999, "프리픽스");
        this.lineNum = rangeCheck(lineNum, 999, "가입자 번호");
    }
    
    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max) throw new IllegalArgumentException(arg + ": " + val);
        
        return (short) val;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == this.areaCode;
    }
}
```

### equals 정의시 주의사항

* equals 를 재정의할 때는 반드시 hashCode 도 재정의해야 한다
* 너무 복잡하게 해결하려 들지 말아야 한다
    * 필드들의 동치성만 검사해도 equals 규약을 어렵지 않게 지킬 수 있다
* 일반적으로 별칭은 비교하지 않는게 좋다
* Object 외의 타입을 매개변수로 받는 equals 메서드를 선언하지 않아야 한다
    * 이는 재정의가 아닌 다중정의를 한것이며, 기본 equals 를 나둔 상태에서 다중정의를 하였어도 이러한 equals 는 문제가 될 수 있다
    * 하위 클래스에서 @Override 애노테이션이 긍정 오류를 내게 하며, 보안 측면에서도 잘못된 정보를 준다
```java
public boolean equals(Something o) {
    ...
    // 다중 정의된 equals
}

@Override
public boolean equals(Something o) {
    ...
    // @Override 를 하게되면 메서드의 타입이 다르므로 컴파일 단계에서 문제를 파악할 수 있게 된다
}
```

### AutoValue 프레임워크

* equals 와 hashcode 를 재정의할 때 테스트하는 일은 지루하고 이를 테스트하는 코드도 항상 비슷할 것이다
* 이러한 작업을 대신해주는 오픈소스가 AutoValue 프레임 워크이다
* 클래스에 어노테이션 하나만 추가하면 AutoValue 가 이러한 메서드들을 알아서 작성해주며, 직접 작성하는 것과 근복적으로 똑같은 코드를 만들어준다
* 대다수의 IDE 도 같은 기능을 제공하지만 생성된 코드가 AutoValue 만큼 깔끔하거나 읽기 좋지는 않다
* 또한 IDE 는 클래스가 수정된 것을 자동으로 알아내지 못하므로 테스트 코드를 작성해둬야 한다
    * 하지만 이러한 단점이 있더라도 직접 작성하는 것보다는 IDE 에 맡겨서 부주의한 실수를 방지할 수 있는것이 좋다
    
## 정리

* 꼭 필요한 경우가 아니라면 equals 를 재정의하지 말자
    * 많은 경우에 Object 의 equals 가 원하는 비교를 정확히 수행해준다
* 재정의할 때는 해당 클래스의 핵심 필드를 모두 빠짐없이 다섯가지 규약을 확실히 지키며 비교해야 한다
