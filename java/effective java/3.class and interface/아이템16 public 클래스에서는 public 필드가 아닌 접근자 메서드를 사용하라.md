## public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라

```java
class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
```

* 위와 같이 패키지 바깥에서 접근할 수 있는 클래스일때 접근자를 제공함으로써 클래스 내부 표현 방식을 언제든 바꿀 수 있는 유연성을 얻을 수 있다
* 데이터 필드를 직접 접근할 수 없게 함으로써 캡슐화의 이점을 얻을 수 있다
    * `setXXX` 와 같은 메서드도 객체의 변경 흐름을 추적하기 힘들게 만들고, 불변성을 깨지게 만드는 요소임으로 지양하는 것이 좋다
    * 내부 상태의 변경이 필요하다면 해당 변경을 담당하는 별도의 메서드를 작성하여 제공해야 한다
* package-private 클래스 또는 private 중첩 클래스일때는 데이터 필드를 public 으로 해도 상관없다
    * 접근할 수 있는 곳이 내부로 한정되어 있고, 필드 변경시 내부에서만 변경하면 된다
* public 클래스의 필드가 불변일때 직접 노출할 때의 단점을 조금 줄어든다
    * 하지만 API 를 변경하지 않고 표현을 변경할 수 없으며, 필드를 읽을 때 부수 작업을 수행할 수 없다
    
```java
public final class Time{
    ...
    
    public final int hour;
    public final int minute;
    
    public Time(int hour, int minute) { ... }
}
// 위와 같이 hour/minute 가 불변값이고, 외부로 노출되어도 변경되지 않을 수 있으나,
// public getMinute() { return minute + "뿐" } 과 같이 부수적인 작업을 할 수 없으며
// 필드명이 모호하면 해당 값만으로 어떤 값을 표현한건지 알 수 없을 가능성이 존재한다
```

## 정리

* public 클래스는 절대 가변 필드를 직접 노출해서는 안된다
* 불변 필드는 노출해도 덜 위험하지만, 덜 위험할 뿐 직접 노출은 지양해야 한다
* package-private 클래스 / private 중첩 클래스는 필드를 직접 노출하는 편이 좋을 때가 존재하며, 직접 노출해도 크게 문제가 없다
