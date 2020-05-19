# 상속

* 객체지향 프로그래밍에서 상속을 통해 부모의 멤버를 자식이 사용할 수 있다.
* 이미 구현 된 클래스를 재사용하여 새로운 클래스를 만들기 때문에 코드의 중복을 줄여주는 **재사용성** 장점이 있다.
* 부모의 기능이 변하면 상속받은 모든 자식이 함께 적용되어 유지 보수 시간을 최소화 한다.
    * 같은 기능을 클래스마다 구현하였고, 그 기능을 변경해야 한다면 하나하나 찾아서 변경해야 하지만, 상속을 받은 자식이 부모의 정보가 변하면 알아서 그 속성이 함께 변경된다.
* 상속을 하더라도 다음과 같은 상황에서 제외된다.
    * 부모의 private 멤버를 사용 할 수 없다.
    * 서로 다른 패키지일 경우에 defualt 멤버를 사용 할 수 없다.

## 클래스 상속

* extends 키워드 뒤에 상속할 클래스를 추가하여 그 클래스를 상속받을 수 있다.
* Java 에서는 다중 상속은 불가능하다.
* 상속을 받은 클래스를 생성할 때, 상속한 부모 클래스의 객체가 생성되고 자식 클래스가 생성된다.
* 자식 클래스가 생성될 때, 그 생성자에 **super()** 가 없을 경우 컴파일러가 자동으로 생성자 첫 라인에 super() 가 추가된다.
    * 자동으로 super(); 가 붙을 수 있으므로 부모의 빈 매개변수를 가지는 생성자가 존재하거나, 자식 클래스에서 명시적으로 특정 부모의 생성자를 호출해야만 한다.
* super() 를 통해 자식이 상속한 부모 생성자를 호출한다.

```JAVA
public class A extends B {...}

// public class C extends D, E {...} <- 불가능
```

## Overriding (메소드 재정의, 오버라이딩)

* 상속한 부모 메소드를 자식 클래스가 다시 재정의하여 로직을 변경하여 사용할 수 있으며, 이를 overriding 이라 한다.
* overriding 시 다음과 같은 규칙이 있다.
    1. 부모의 메소드와 동일한 리턴 타입, 메소드 이름, 매개 변수 리스트를 가져야 한다.
    2. 더 강한 접근 제한자로 변경할 수 없다.
        * public -> protected, defualt, private
        * protected -> defualt, private
        * defualt -> private
    3. 새로운 예외를 throws 할 수 없다.
* 부모의 메소드를 overriding 한 경우, overriding 된 메소드가 호출되지만, super() 를 통해서 부모의 메소드를 호출할 수 있다.

## 클래스와 메소드의 final

* 클래스에 final 키워드를 붙일경우, 그 클래스를 더이상 상속할 수 없다.
* 메소드에 final 키워드를 붙일경우, 그 메소드는 더이상 overriding 을 할 수 없다.

```JAVA
public class A {
    protected int field1 = 0;
    protected void method1() { print("hello world!"); }

    protected A() {...}
}
...

public class B extends A {
    int field2 = 1;
    void method2() { print("hi world!"); }

    @Override
    public method2() { print("goodbye world!"); }

    public method3() { super.method1(); }

    b() {
        // super(); 가 없으면 컴파일러가 자동으로 생성한다.
        // 또한, 명시적으로 코드에 super() 를 추가하여 특정 부모의 생성자를 매개변수와 함께 호출할 수 있다.
        ...
    }
}
// B 클래스는 A 의 맴버 (field1, method1) 을 사용할 수 있다.

public class C extends A {
    ...
}
...

public static void main(String[] args) {
    B b = new B();

    print(b.field1);    // 0
    b.method2();        // goodbye world!
    b.method3();        // hello world!
}
```

## 다형성

* 같은 타입이지만 실행 결과가 다양한 객체를 이용할 수 있는 성질을 말한다.
* 하나의 타입에 여러 객체를 대입함으로써 다양한 기능을 이용할 수 있도록 해준다.
* 자식 클래스가 부모 클래스로 타입 변환을 허용하며, 이것을 이용하여 객체는 부품화가 가능하다.
* 자식은 부모의 성질을 받았기 때문에 부모와 동일하게 취급될 수 있고, 이때 자동 타입 변환(Promotion) 이 가능하다.
    * 부모 타입으로 자동 타입 변환이 일어난 경우, 부모의 필드와 메소드에만 접근이 가능해진다.
    * **단, overriding 되었을 경우 overriding 된 메소드가 호출된다**
* 한 부모에서 파생되어진 자식이 존재하더라도, 현재 자식의 부모가 같지 않다면 자동 타입 변환은 일어날 수 없다.
    * a -> b -> c | a -> d -> e
    * c = d or e 불가능
    * e = b or c 불가능
* 필드와 메소드의 매개변수도 다양성을 이용할 수 있다.
* instanceof 를 통해 객체의 타입을 확인할 수 있다.

```JAVA
public class Tire {
    ...
    protected move() { print ("move!"); }
    protected stop() { print ("stop!"); }
}

public class GoodTire extends tire {
    ...
    @Overriding
    move() { print("move move!!"); }

    acceleration() { print("more faster!"); }
}

public class NiceTire extends tire {
    @Overriding
    move() { print("nice move!!"); }
    ...
}

public class Car {
    public tire1;
    public tire2;
    public tire3;

    public move(Tire tire) {
        tire.move();
    }
    ...
}

public static void main(String[] args) {
    Tire goodTire = new GoodTire();
    Tire niceTire = new NiceTire();
    // 부모의 타입으로 변환할 수 있다.

    GoodTire goodTire2 = new GoodTire();
    Tire tire2 = goodTire2;
    tire2.move(); // move move!!
    tire2.stop(); // stop!
    // tire2.acceleration(); // 사용 불가능
    print(goodTire2 == tire2) // true
    // 자동 변환
    // goodTire2 와 tire2 는 같은 힙 영역에 존재하는 객체를 가르킨다.

    Car car = new Car();
    car.tire1 = Tire();
    car.tire2 = GoodTire();
    car.tier3 = NiceTire();
    // 필드의 다형성

    car.move(car.tire1); // move!
    car.move(car.tire2); // move move!
    car.move(car.tire3); // nice move!
    // 메소드 매개변수의 다형성

    print(car.tire1 instanceof Tire);       // true
    print(car.tire1 instanceof GoodTire);   // false
    print(car.tire1 instanceof NiceTire);   // false
    print(car.tire2 instanceof Tire);       // false
    print(car.tire2 instanceof GoodTire);   // true
    print(car.tire2 instanceof NiceTire);   // false
    print(car.tire3 instanceof Tire);       // false
    print(car.tire3 instanceof GoodTire);   // false
    print(car.tire3 instanceof NiceTire);   // false
}
```

## Abstract (추상)

* 강아지, 고양이 등은 모두 동물이라는 특성을 가지고 있다.
* 강아지, 고양이 등과 같이 구체적인 것이 아닌 동물과 같이 공통되는 특성을 가지고 있는 것을 추상이라 한다.
* 클래스도 위와같이 특성만 추출한 추상 클래스를 만들 수 있다.
* 추상 클래스가 부모가 되며 추상 클래스를 상속한 자식 클래스는 추상 클래스의 모든 특성 및 새로 정의하는 특성을 바탕으로 클래스를 작성할 수 있다.
* 추상 클래스 자체만으로 인스턴스를 생성시키지 못한다.
* 추상 클래스의 용도
    1. 상속받은 자식 클래스들의 공통된 필드와 메소드의 이름을 통일 할 목적
        * 여러 사람이 클래스를 작성하게 될 경우, 구현하는 사람마다 동일한 기능을 하는 필드와 메소드의 이름이 모두 다를 것이다.
        * 추상 클래스를 통해 상속받은 모든 클래스의 구조를 잡아줄 수 있다.
    2. 실체 클래스를 작성할 시간을 절약할 수 있다.
    3. 추상 메서드를 선언하여 상속받은 클래스들이 구현을 하도록 강제할 수 있다.
        * abstract 키워드를 붙여서 메서드를 선언하고 {} 가 없어야 한다.

```JAVA
public abstract class Animal {
    ...

    public void sleep() {
        print("zzZ");
    }

    public abstract sound();
}

public abstract class Dog extends {
    ...
    @Override
    public abstract sound() {
        print("Woof Woof!");
    }
}

public abstract class Cat extends {
    ...
    // Animal의 abstract sound 가 overriding 되지 않아 컴파일 에러가 발생한다.
}
```