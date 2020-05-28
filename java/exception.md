# Exception (예외)

* 로직에 의한 에러나 사용자의 잘못 된 조작으로 인해 에러가 발생할 수 있으며, 이때 예외가 발생한다.
* 예외가 발생하면 프로그램은 비정상으로 종료가 되며, 이러한 예외를 처리할 수 있는 방법을 자바에서 제공한다.
* 예외에는 2가지 종류가 존재한다.
    1. Exception (일반 예외)
        * 컴파일러 체크 예외
        * 컴파일 과정에서 예외 처리 코드가 필요한지 확인을 하면서, 예외 처리가 되어 있지 않으면 컴파일 에러가 발생한다.
    2. Runtime Exception (실행 예외)
        * 컴파일 과정에 검사하지 않은 예외를 Runtime Exception 이라고 한다.
* 일반 예외와 실행 예외의 구별하는 방법
    * Runtime Exception 은 Exception 클래스를 상속받은 RuntimeException 클래스를 상속한 예외들을 말한다.
    * 일반 예외는 Exception 을 상속받았지만, Runtime Exception 을 상속받지 않은 예외나 그런 예외를 상속 받은 예외를 말한다.
    * JVM 은 RuntimeException 클래스의 상속 여부를 확인하여 일반 예외와 실행 예외를 판단한다.

## 예외 클래스

* 자바에서는 예외를 클래스로 관리한다.
* JVM 은 프로그램을 실행하는 도중에 예외가 발생하면 해당 예외 클래스로 객체를 생성하고, 해당 객체를 예외 처리 코드에서 사용할 수 있도록 해준다.
* 모든 예외 클래스는 java.lang.Exception 클래스를 상속받는다.


## RuntimeException

### NPE (Null Point Exception)

* ~~악명높은 자바의 NPE~~
* null 을 가지는 객체를 참조할 때 발생하는 예외이다.
* 객체가 없는데, 그 객체를 사용하려 했기 때문에 발생하는 오류이다.

```JAVA
public static void main(String[] args) {
    String NPE = null;
    print(NPE); // throw NPE
}
```

### ArrayIndexOutOfBoundsException

* 배열의 인덱스 범위를 초과하여 사용할 경우 발생하는 예외이다.
* loop 에서 발생되는 경우가 많다.
* 인덱스의 크기를 검사하는 등의 방법으로 해결할 수 있다.

```JAVA
public static void main(String[] args) {
    int[] foo = {1, 2};
    print(foo[2]); // throw ArrayIndexOutOfBoundsException
}
```

### NumberFormatException

* 숫자가 아닌 문자열을 숫자로 변경할 때 발생하는 예외이다.

```JAVA
public static void main(String[] args) {
    String foo = abc;
    print(Integer.parse(foo)); // throw NumberFormatException
    print(Double.parse(foo)); // throw NumberFormatException
}
```

### ClassCastException

* 타입 변환은 상위 클래스와 하위 클래스 간에 발생하며, 구현 클래스와 인터페이스 간에도 발생한다.
* 위와 같은 형태가 아니라면 타입 변환이 불가능하며, 강제 변환으로 맞지 않는 타입 변환을 시도하면 ClassCastException 이 발생한다.

```JAVA
public abstract class Animal {...}
public abstract class Dog extends {...}
public abstract class Cat extends {...}

public interface RemoteControl {...}
public class Television implements RemoteControl {...}
public class Audio implements RemoteControl {...}

public static void main(String[] args) {
    Animal animal = new Dog();
    Dog dog = (Dog)animal;
    RemoteControl rc = new Television();
    Television tv = (Television)rc;
    // OK

    Animal animal = new Dog();
    Cat cat = (Cat)animal;
    RemoteControl rc = new Television();
    Audio audio = (Audio)rc;
    // ClassCastException

    // instanceOf 를 이용하여 타입을 확인하고 형변환을 시도하는게 좋다.
}
```