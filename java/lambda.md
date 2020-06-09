# 람다식 (Lambda)

* Java 8 부터 함수적 프로그래밍을 지원하기 위해 Lambda Expressions(람다식) 을 지원한다.
* 람다식은 anonymouse function(익명 함수) 를 생성하기 위한 식으로 객체 지향 언어보다는 함수 지향 언어에 가깝다.
* 람다식의 형태는 매개 변수를 가진 코드 블록이지만, 런타임 시에는 익명 구현 객체를 생성한다.

> 람다식 -> 매개 변수를 가진 코드 블록 -> 익명 구현 객체

* Thread 파트에서 스레드의 run 구현 객체를 람다식으로 표현하면 다음과 같다.

```JAVA
Runnable runnable = new Runnable() {
    public void run() {...};
}

Runnable runnable = () -> {...};
// {...}; 이 람다식을 통해 구현한 익명 함수 객체이다.
// 람다식은 (매개변수) -> {실행코드}; 형태로 작성된다.
// 런타임시에 정의된 람다식은 인터페이스의 익명 구현 객체로 생성된다.
```

## 람다식 기본 문법

> (타입 매개변수, ...) -> {실행코드};

* 타입 매개변수들은 실행코드의 실행하기 위한 인자값을 제공하는 역할을 한다.
    * 매개 변수 타입은 런타임 시에 대입되는 값에 따라 자동으로 인식될 수 있기 때문에 람다식에서는 일반적으로 매개 변수의 타입을 언급하지 않는다.
    * 변수 이름은 자유이며, 실행코드에서 매개 변수를 이용하여 로직을 실행한다.
* 매개변수가 1개이면 () 를 제외하여 작성할 수 있다.
* 매개변수가 없다면 () 만 사용한다.
* 람다식의 결과를 사용하여 값을 받고 싶다면 return 을 사용하면 된다.
    * {실행코드} 의 로직이 return 만 존재한다면 {} 와 return 을 제외하여 표현할 수 있다.

```JAVA
(arg1, arg2) -> { print(`arg1 : ${arg1}, arg2 : ${arg2}`) }
arg -> { print(`arg : ${arg}`); }
() -> { print("hello world"); }
(a, b) -> { return a + b; }
(a, b) -> a + b
```

## 타겟 타입과 함수적 인터페이스

* 자바는 메소드를 단독으로 선언할 수 없고 항상 클래스의 구성 멤버로 선언하기 때문에, 람다식은 단순히 메소드를 선언하는 것이 아니라 이 메소드를 가지고 있는 객체를 생성한다.
    * 인터페이스의 변수에 람다식을 대입하면, 그 인터페이스의 익명 구현 객체를 생성하여 대입하게 된다.
        * 인터페이스는 직접 객체화화 할 수 없기 때문에 구현 클래스가 필요하다.
        * 구현 클래스가 필요한데, 여기에 람다식을 사용하면 그 인터페이스의 구현 클래스를 익명구현 클래스로 생성하고 객체화를 한다.
        * 인터페이스의 종류에 따라 대입하는 람다식의 작성 방법은 달라진다. 이를 람다식의 타겟 타입이라고 한다.

### FunctionalInterface (함수적 인터페이스)

* 모든 인터페이스를 람다식의 타겟 대입으로 사용할 수 없다.
    * 람다식은 하나의 메소드를 정의하기 때문에 두 개 이상의 추상 메소드가 선언된 인터페이스는 람다식을 이용해서 구현 객체를 생성할 수 없다.
    * 즉, 하나의 추상 메소드가 선언된 인터페이스만 람다식의 타겟 타입이 될 수 있는데, 이러한 인터페이스를 함수적 인터페이스라고 한다.
    * @FunctionalInterface 어노테이션을 사용하면, 컴파일러가 체킹할 수 있게 할 수 있다.
        * 즉, 2개 이상의 추상 메소드가 선언되면 컴파일 오류가 발생된다.
        * 위 어노테이션이 없더라도 하나의 추상 메소드만 존재한다면 함수적 인터페이스이다.

```JAVA
@FunctionalInterface
public interface FunctionalInterfaceTest {
    public void method1();
    public void method2();
}
// 컴파일 에러
```

#### 함수적 인터페이스의 메소드에 따른 람다식 작성

```JAVA
@FunctionalInterface
public interface FooFuntionalInterfaceTest {
    public void method();
}

FooFunctionalInterfaceTest fooFunctionalInterfaceTest = () -> { print("Hello World~") };
fooFunctionalInterfaceTest.method(); // Hello World~

FooFunctionalInterfaceTest fooFunctionalInterfaceTest = () -> print("GoodBye World~");
fooFunctionalInterfaceTest.method(); // GoodBye World~

FooFunctionalInterfaceTest fooFunctionalInterfaceTest = () -> {
        print("Foo");
        print("Bar");
    };
fooFunctionalInterfaceTest.method(); // Foo Bar
// 매개 변수와리턴값이 없는 람다식

//////////////////////////////////////////////////////////////////////////////////////////

@FunctionalInterface
public interface BarFuntionalInterfaceTest {
    public void method(String str);
}

BarFuntionalInterfaceTest barFunctionalInterfaceTest = (name) -> { print(`${name} hello~`) };
barFunctionalInterfaceTest.method("Foo"); // Foo hello~

BarFuntionalInterfaceTest barFunctionalInterfaceTest = () -> print(`${name} hello!`);
barFunctionalInterfaceTest.method("Bar"); // Foo hello!

BarFuntionalInterfaceTest barFunctionalInterfaceTest = ("FooBar") -> {
        print(`${name} hello~`)
        print(`${name} hi~`)
    };
barFunctionalInterfaceTest.method(); // FooBar hello~ FooBar hi~
// 매개 변수가 있는 람다식

@FunctionalInterface
public interface FooBarFuntionalInterfaceTest {
    public int method(int a, int b);
}

FooBarFuntionalInterfaceTest foobarFunctionalInterfaceTest = (a, b) -> { return a + b; };
foobarFunctionalInterfaceTest.method(1, 1); // 2

FooBarFuntionalInterfaceTest foobarFunctionalInterfaceTest = (a, b) -> return a + b;
foobarFunctionalInterfaceTest.method(3, 4); // 7

FooBarFuntionalInterfaceTest foobarFunctionalInterfaceTest = (a, b) -> {
        a++;
        b++;
        return a + b;
    };
foobarFunctionalInterfaceTest.method(10, 20); // 32
// 리턴값이 있는 람다식

// 실행로직 블록을 같은 형태의 매개변수로 가지는 다른 메소드로 넘겨줘도 가능하다.

public int sum(int x, int y) { return x + y; }

FooBarFuntionalInterfaceTest foobarFunctionalInterfaceTest = (a, b) -> sum(a, b);
foobarFunctionalInterfaceTest.method(100, 200); // 300
```

## 클래스 멤버와 로컬 변수 사용

* 람다식의 실행 블록에는 클래스의 멤버 및 로컬 변수를 사용할 수 있다.
    * 클래스의 멤버는 제약 사항 없이 사용 가능
    * 로컬 변수는 제약 사향 존재

### 클래스 멤버 사용

* **람다식의 실행 블록에 클래스의 멤버를 사용할 때, this 는 익명객체가 아닌 람다식을 실행한 객체의 참조이다.**

```JAVA
@FunctionalInterface
public interface FuntionalInterfaceTest {
    public void method();
}

public class Outer {
    public int field = 10;

    class Inner {
        int field = 20;

        void method() {
            int field = 30;
            FuntionalInterfaceTest functionalInterfaceTest = () -> print(this.field);
            functionalInterfaceTest.method();
        }
    }
}

public static void main(String args[]) {
    Outer outer = new Outer();
    Outer.Inner inner = outer.new Inner();

    inner.method();
    // method() 내부의 field 가 아닌, method() 를 호출한 Inner 클래스의 field 가 참조된다.
    // 즉, 람다식의 실행 블록에 this 는 그 람다식을 실행한 객체가 된다.
}
```

### 로컬 변수 사용

* 람다식에서의 바깥 클래스의 필드나 메소드는 제한 없이 사용할 수 있으나, 메소드의 매개 변수 또는 로컬 변수를 사용하면 이 두 변수는 final 특성을 가져야 한다.
* 즉, 매개 변수 또는 로컬 변수를 람다식에서 읽는 것은 허용되지만, 람다식 내부 또는 외부에서 변경할 수 없다.

```JAVA
@FunctionalInterface
public interface FuntionalInterfaceTest {
    public void method();
}

public class Foo {
    void method(int arg) {  // arg 는 final 특성을 가짐
        int localVar = 40;  // localVar 는 final 특성을 가짐
        // arg, localVar 는 둘다 수정이 불가능하다.

        FuntionalInterfaceTest functionalInterfaceTest = () -> {
            print(arg);
            print(localVar);
        }
        functionalInterfaceTest.method();
    }
}

public static void main(String args[]) {
    Foo foo = new Foo();

    foo.method(100);
    // 100 , 40
}
```

