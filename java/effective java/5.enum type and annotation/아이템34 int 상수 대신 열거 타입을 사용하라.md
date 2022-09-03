## int 상수 대신 열거 타입을 사용하라

* 정수 열거 패턴은 열거 타입을 지원하기 전에 정수 상수를 한 묶음 선언하여 활용하는 것을 말한다
* 정수 열거 패턴은 다음과 같은 단점이 존재한다
    * 타입 안전을 보장할 방법이 없으며 표현력도 좋지 않다
    * 정수 열거 패턴을 위한 별도 이름공간을 지원하지 않기 때문에 접두어를 사용하여 이름 충돌을 방지해야 한다
    * 정수 열거를 활용하면 프로그램이 깨지기 쉽다
    * 문자열로 출력하기가 다소 까다롭다
    * 정수 열거 그룹에 속한 모든 상수를 순회하는 방법도 마땅하지 않으며, 상수가 몇개인지 알 수 없다
* 문자열을 이용하여 상수를 사용하는 문자열 열거 패턴도 존재한다
    * 하지만 이는 상수의 의미를 출력할 수 있다는 점이 좋으나, 문자열 상수의 이름 대신 문자열 값을 그대로 하드코딩하게 만든다
    * 문자열에 오타가 있어도 컴파일러가 확인할 수 없으므로, 런타임 버그가 생길 가능성이 높다
    * 문자열 비교에 따른 성능 저하가 존재한다
    
### 열거 타입

```java
public enum Fruit { APPLE, ORANGE }
```

* 위와 같은 단점들을 해결해주는 대안이며, 다양한 것을 표현을 할 수 있다
    * 완전한 형태의 클래스이며, 다른 언어의 열거 타입보다 훨씬 강력하다
* 클래스이며, 상수 하나당 자신의 인스턴스를 하나씩 만들어 public static final 필드로 공개한다
    * 열거 타입은 밖에서 접근할 수 있는 생성자를 제공하지 않으므로 사실상 final 이다
    * 이는 클라이언트가 인스턴스를 직접 생성하거나 확장할 수 없으므로 열거 타입 선언으로 만들어진 인스턴스들은 딱 하나만 존재됨을 보장한다
* 열거 타입은 컴파일타임 타입 안전성을 제공한다
    * 전달 받은 참조가 null 이 아닌 열거 타입이라면, 해당 열거 타입 중 내부에 선언된 값임을 보장한다
* 열거 타입에 새로운 상수를 추가하거나 순서를 변경하여도 다시 컴파일 하지 않아도 된다
    * 필드의 이름만 공개되므로, 상수 값이 클라이언트로 컴파일되어 각인되지 않기 때문이다
* 열거 타입의 toString 메서드는 출력하기에 적합한 문자열을 준다
* 열거 타입에 임의의 메서드나 필드를 추가할 수 있으며, 임의의 인터페이스를 구현하게 할 수도 있다

```java
public enum Planet {
    MERCURY (3.302e+23, 2.439e6),
    VENUS   (4.869e+24, 6.052e6)
    ...;
    
    private final double mass;
    private final double radius;
    private final double surfaceGravity;
    // 열거 타입 내부에 필드를 추가할 수 있다
    
    private static final double G = 6.67300E-11;
    // 열거 타입 내부에서 사용될 상수를 정의할 수 있다
    
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
        // 생성자를 통해 열거 타입 상수 각각의 특정 데이터와 연결하여 인스턴스 필드를 초기화할 수 있다
    }
    
    public double mass() { return mass; }
    public double radius() { return radius; }
    public double surfaceGravity() { return surfaceGravity; }
    
    public double surfaceWeight(double mass) {
        return mass * surfaceGravity;
    }
    // 열거 타입 내부에 메서드를 선언할 수 있다
}
```

* 열거 타입은 근본적으로 불변이므로 모든 필드가 final 이어야 한다
    * public 으로 선언해도 괜찮으나, private 으로 하고 public 접근자 메서드로 제공하는게 좋다
* 열거 타입은 자신 안에 정의된 상수들의 값을 배열에 담아 반환하는 정적 메서드인 `values` 를 제공하며, 선언된 순서대로 저장된다
* 각 열거 타입 값의 toString 메서드는 상수 이름을 문자열로 반환한다
* 열거 타입을 선언한 클래스 혹은 해당 패키지에서만 유용한 기능은 private/package-private 메서드로 구현한다
    * 이를 통해 열거 타입 상수는 자신을 선언한 클래스 혹은 패키지에서만 사용할 수 있는 기능을 담게 된다
* 열거 타입의 정적 필드 중 열거 타입의 생성자에서 접근할 수 있는 것은 상수 변수뿐이다
    * 열거 타입 생성자가 실행되는 시점에 정적 필드들은 아직 초기화되기 전이라, 자기 자신을 추가하지 못하게 하는 제약이 꼭 필요하다
    * 열거 타입 생성자에서 같은 열거 타입의 다른 상수에도 접근할 수 없다

#### 열거 타입 값에 따른 기능 구현 (상수별 메서드 구현)

```java
public enum Operation {
    PLUS    { public double apply(double x, double y) { return x + y; } },
    MINUS   { public double apply(double x, double y) { return x - y; } },
    TIMES   { public double apply(double x, double y) { return x * y; } },
    DIVIDE  { public double apply(double x, double y) { return x / y; } };
    
    public abstract double apply(double x, double y);
}
```

* 위와 같이 추상 메서드를 선언하고, 각 상수별 클래스 몸체에서 각자 구현하여 상수별 메서드를 구현할 수 있다

#### 전략 열거 타입 패턴

* 상수별 메서드 구현에는 열거 타입 상수끼리 코드를 공유하기 어렵다는 단점이 존재한다

```java
enum PayrollDay {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
    SATURDAY, SUNDAY;
    
    private static final int MINS_PER_SHIFT = 8 * 60;
    
    int pay(int minutesWorked, int payRate) {
        int basePay = minutesWorked * payRate;
        
        int overtimePay;
        switch(this) {
            case SATURDAY: case SUNDAY:
                overtimePay = basePay / 2;
                break;
            default:
                overtimePay = minutesWorked <= MINS_PER_SHIFT ?
                        0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
        }
    }
}
```

* 위와 같이 구현하여 주중 오버타임과 주말에 대한 잔업 수당을 처리할 수 있으나, 휴가 등과 같은 새로운 열거 타입을 추가할 경우 여러 문제가 발생할 수 있다
* 상수를 추가할 때 잔업수당 '전략' 을 선택하도록 하는 형태로 구현하면 깔끔하다

```java
enum PayrollDay {
    MONDAY(WEEKDAY), TUESDAY(WEEKDAY), ... SATURDAY(WEEKEND) ...;
    
    private final payType payType;
    
    PayrollDay(payType payType) { this.payType = payType; }
    
    int pay(int minutesWorked, int payRate) {
        return payType.pay(minutesWorked, payRate);
    }
    
    enum PayType {
        WEEKDAY {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked <= MINS_PER_SHIFT ? 0 :
                        (minsWorked - MINS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked * payRate / 2;
            }
        };
        
        abstract int overtimePay(int mins, int payRate);
        private static final int MINS_PER_SHIFT = 8 * 60;
        
        int pay(int minsWorked, int payRate) {
            int basePay = minsWorked * payRate;
            return basePay + overtimePay(minsWorked, payRate);
        }
    }
}
```

* switch 를 이용하여 각 enum 의 특정 값들을 하나하나 처리하는 방식보다 복잡은 하지만, 유연하고 안전하게 구현할 수 있다
* 기존 열거 타입에 상수별 동작을 혼합하여 넣을 때는 switch 의 사용이 좋은 선택이 될 수 있다

```java
public static Operation inverse(Operation op) {
    switch(op) {
        case PLUS:  return Operation.MINUS;
        case MINUS: return Operation.PLUS;
        ...
    }
}
```

## 정리

* 필요한 원소를 컴파일타임에 다 알 수 있는 상수 집합이라면 항상 열거 타입을 사용하자
* 열거 타입에 정의된 상수 개수가 영원히 고정불변일 필요는 없다
* 열거 타입은 정수 상수보다 뛰어나고 읽기 쉽고 안전하고 강력하다
* 열거 타입을 활용하면, 각 상수를 특정 데이터와 연결짓거나 상수마다 다르게 동작하는 메서드를 구현할 수 있다
* 열거 타입 상수 일부가 같은 동작을 공유한다면, 전략 열거 타입 패턴을 사용하는게 좋다
    * 중복 코드와 상수 추가시 누락을 방지할 수 있다
