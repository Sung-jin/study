# 인터페이스

* 인터페이스는 객체의 사용 방법을 정의한 타입이라 할 수 있다.
* java8 의 lambda 는 함수적 인터페이스의 구현 객체를 생성하여 동작하도록 되어 있다.
* 인터페이스를 사용하면 객체의 교환성을 높여주기 때문에 다형성을 구현하는데 매우 중요한 역할을 한다.
    * 개발 코드와 객체가 서로 통신하는 접점 역할을 한다.
    * 개발 코드가 인터페이스의 메소드 호출 -> 인터페이스가 객체의 메소드를 호출 -> 객체가 응답 -> 인터페이스가 응답 하는 형태로 진행이 되며, 이렇게 구성됨으로써 개발 코드는 객체의 내부 주소를 알 필요 없이 인터페이스의 메소드만 알면 된다.
    * 인터페이스를 사용하면 개발 코드를 수정하지 않고 인터페이스를 변경하여 인터페이스를 사용하는 모든 개발 코드가 동일한 입력과 기능, 출력을 보장시켜줄 수 있다.
    * 또한, 인터페이스를 사용하는 객체에 따라서 실행 로직과 응답값이 다르게 동작 할 수 있다.
* 인터페이스는 class 키워드 대신 interface 키워드로 선언한다.
* 인터페이스는 상수와 메소드만 가질 수 있다.
* java8 이후부터는 디폴트 메소드와 정적 메소드를 선언할 수 있다.
* 인터페이스에 선언 가능한 종류
    1. Constant Field - 인터페이스에는 필드를 선언 할 수 없지만, 상수값은 고정 된 값이므로 선언하여 사용할 수 있다.
    2. Abstract Method - 메소드명과 매개 변수만 존재하는 추상 메소드를 선언할 수 있으며, 인터페이스를 사용하는 객체는 반드시 추상 메소드의 구현을 해야 한다.
    3. Defualt Method - 인터페이스를 상속한 구현 객체가 가지고 있는 인스턴스 메소드이다.
    4. Static Method - 인터페이스를 상속한 구현 객체가 없더라도 인터페이스만으로도 호출이 가능한 메소드이다.

* 인터페이스를 통해 호출된 메소드는 최종적으로 객체에서 실행되기 때문에 인터페이스에 구현부가 없으며, 리턴 타입과 메소드명과 매개변수만 기술되고 로직이 없다. -> Abstract Method
* 인터페이스는 다시 인터페이스를 상속받을 수 있다.

## 인터페이스 구현

* 인터페이스를 상속받아 추상 메소드의 로직을 만드는 것을 구현 (implement) 라고 한다.
    * 구현을 하면 인터페이스의 디폴트 메소드를 Override 할 수 있다.
* 인터페이스 변수는 참조 타입이며, 구현 객체가 대입될 경우 구현 객체의 번지수를 저장한다.
* 익명 구현 객체를 통해서도 인터페이스를 구현할 수 있다.
    * 익명 구현 객체로 인터페이스를 구현하였다면, 컴파일 시 (구현한 파일 이름)$1....n.class 형태로 익명 구현 한 수 만큼 클래스 파일이 생성된다.
* 다수의 인터페이스를 구현할 수 있다.
* 인터페이스 객체에 인터페이스를 구현한 클래스로 생성할 수 있으며, 그렇게 생성한 객체는 클래스에서 구현한 메소드로 실행이 된다.

## 타입 변환과 다형성

* 다형성은 하나의 타입에 대입되는 객체에 따라서 실행 결과가 다양한 형태로 나오는 성질을 말한다.
* 인터페이스에서 인터페이스 타입에 구현한 어떤 인터페이스 타입을 대입하냐에 따라 동작이 달라진다.
* 상속 - 같은 종류의 하위 클래스를 만드는 기술 | 인터페이스 - 사용 방법이 동일한 클래스를 만드는 기술
* 인터페이스를 이용해서 메소드를 호출하도록 구현하였다면, 코드의 변경은 없지만 구현 객체의 변동함에 따라 실행 결과가 다양해지며 이것이 인터페이스의 다향성이다.
    * 자동 타입 변환이 되기 때문에 위와 같은 경우가 가능하다.
    * 인터페이스를 구현한 객체는 그 인터페이스의 객체가 될 수 있다.
    * 필드에 인터페이스 값으로써 다향성을 구현할 수 있지만, 메소드의 매개 변수로 설정하여 메소드의 다양성을 추구하는 경우가 많다.

```JAVA
public interface A {
    public static final int CONSTANT_VAL1 = 0;
    int CONSTANT_VAL2 = 1;
    // public static final 키워드를 생략하더라도 컴파일 시 자동으로 붙여준다.

    void add(int a, int b)
    // public abstract 를 생략하더라도 컴파일 시 자동으로 붙여준다.

    public abstract void minus(int a, int b)

    default void greeting() {
        print('Hello');
    }
    // public 속성을 가지며, 생략되더라도 컴파일 시 자동으로 붙는다.

    static void greetingStatic() {
        print('Hi there~!');
    }
    // public 속성을 가지며, 생략되더라도 컴파일 시 자동으로 붙는다.
}

public interface Foo {
    void mul(int a, int b)
    void div(int a, int b)
}

public interface Quux extends A, Foo { ... }
// Quux 를 구현한 클래스는 반드시 A 와 Foo 의 추상 메소드의 구현부가 있어야 한다.

public class B implements A {
    public void add(int a, int b) { print("B add : {}", a + b) }
    // 인터페이스는 기본적으로 public 제한자를 가지고 있다.
    // 상속받은 클래스는 구현하는 메소드는 public 보다 낮은 접근 제한자로 구현할 수 없다.
    public void minus(int a, int b) { print("B minus : {}", a - b) }
    public void anotherFunc() { print("B's new Func!") }
}

public class BB implements A {
    public void add(int a, int b) { print("BB add : {}", a + b) }
    public void minus(int a, int b) { print("BB minus : {}", a - b) }
}

public abstract class C implements A {
    public void add(int a, int b) { print("C add : {}", a + b) }
    // public void minus(int a, int b) { print(a - b) }
    // minus 에 대한 구현부가 없으므로 인터페이스를 상속받은 추상 클래스로 구현해야 한다.
}

public class Bar implements A, Foo {
    public void add(int a, int b) { print("Bar add : {}", a + b) }
    public void minus(int a, int b) { print("Bar minus : {}", a - b) }
    public void mul(int a, int b) { print("Bar mul : {}", a * b) }
    public void div(int a, int b) { print("Bar div : {}", a / b) }

    @Override
    public void greeting() {
        print('Hello World!');
    }
}
// 다중 인터페이스 상속

public class Baz {
    A one = B();
    A two = BB();

    void add(int a, int b) {
        one.add(a, b);
        two.add(a, b);
    }
}

public class Qux {
    void add(A a, int a, int b) {
        a.add(a, b);
    }
}
...

public static void main(String[] args) {
    B b = new B();
    //b 에 구현 객체의 주소가 저장된다.

    A a = new A() {
        public void add(int a, int b) { print(a + b) }
        public void minus(int a, int b) { print(a - b) }
    };
    // 인터페이스의 추상 메소드를 내부에서 구현해서 익명 구현 객체 형태로 구현할 수 있다.

    A fooBar = null;

    fooBar = new B();
    foobar.add(1, 1); // B add : 2
    foobar.greeting(); // Hello
    forBar.anotherFunc(); // 불가능
    (B)forBar.anotherFunc(); // B's new Func!

    fooBar - new Bar();
    foobar.add(1, 1); // Bar add : 2
    foobar.greeting(); // Hello World!

    Baz baz = new Baz();
    baz.add(1, 1); // B add : 2 \n BB add : 2
    baz.two = new B();
    baz.add(1, 1); // B add : 2 \n B add : 2

    Qux qux = new Qux();
    qux.add(new B(), 1, 1); // B add : 2
    qux.add(new BB(), 1, 1); // BB add : 2
}
```