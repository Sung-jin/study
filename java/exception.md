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
* 로직상에서 throw new Exception() 과 같이 예외를 발생시킬 수도 있다.

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

## 예외 처리

* 예외 처리는 try-catch-finally 로 처리할 수 있다.
    * try block 에는 예외가 발생 할 수 있는 로직이 존재한다.
    * catch block 에는 예외가 발생했을 경우 실행 될 로직이 존재한다.
        * catch block 의 경우 다중으로 예외 처리가 가능하다.
        * 상위 예외 클래스가 하위 예외 클래스의 catch block 보다 위에 존재한다면, 하위 예외 클래스 catch block 에 도달하지 못하고 상위 예외 클래스에 의해 예외가 처리된다.
        * Java 7 부터 하나의 catch 를 통해서 여러개의 예외를 처리할 수 있다.
            * try{...}catch(Exception1 e1 | Exception2 e2 ....)
    * finally block 은 옵션이며 정상 실행이 되든, 예외가 발생하든 반드시 실행되는 로직이 존재한다.
        * 일반적으로 DB connection 을 해제 시켜주는 로직과 같이 자원 할당을 해제시키는 로직이 들어가는 경우가 많다.
    * Java 7 부터 try-with-resources 를 사용하여 예외 발생 여부와 상관없이 사용한 모든 리소스 객체 (입출력 스트림, 서버 소켓, 소켓, 채널...) 의 close() 메소드를 호출하여 리소스를 해제하는 기능이 추가되었다.
* try-catch block 을 통해 바로 예외를 처리할 수 있지만, 예외가 발생하였을 경우 호출한 곳에서 처리를 하도록 떠넘길 수 있다.
* throws 키워드를 메소드에 추가하여 발생할 예외를 적어주면 된다.
    * ',' 를 통해 나열할 수 있다.

```JAVA
public void willThrowException() throws Excetpion1, Exception2 {...}

public static void main(String[] args) {
    try {
        String NPE = null;
        print(NPE); // throw NPE
    } catch(NullPointerException e) {
        print("NPE 발생!! : {}", e);
    } finally {
        print("무조건 실행!");
    }
    // NPE 발생!! : ~~~~
    // 무조건 실행!
    try (FileInputStream fis = new FileInputSTream("test.txt")) {
        ...
    } catch(IOException e) {
        ...
    }
    // try-with-resources 를 사용한 try-catch block
    // 성공, 실패 여부의 상관없이 fis.close(); 가 실행되며 리소스를 닫아준다.

    try {
        willThorwException();
    } catch(Exception1 e) {...}
    catch(Exception2 e) {...}
    catch(Exception e) {...}
    finally {...}
}
```

## 사용자 정의 예외 클래스

* 개발자가 직접 정의한 예외이며, 실행 예외와 일반 예외 모두 지정이 가능하다.
    * 일반 예외로 만들 경우 Exception 클래스를 상속 받으면 된다.
    * 실행 예외로 선얼할 경우 RuntimeExcpetion 을 상속받으면 된다.
* 사용자 지정 예외는 **Excetpion** 으로 끝나는게 좋다.
* 필드, 메소드 선언, 생성자 모두 정의 할 수 있지만 생성자 선언만 하는경우가 대부분이다.
* 생성자를 정의할 때 대부분 2개를 선언한다.
    * 매개 변수가 없는 기본 타입
    * String 타입을 받아서 메세지로 처리하는 타입
        * 이렇게 받은 메세지는 상위 예외 클래스로 전달하며, catch block 에서 호출하여 예외 메시지를 출력하는데 사용된다.

## 예외 정보 얻기

* 모든 예외는 Exception 클래스를 상속받으며, getMessage() 와 printStackTrace() 를 모든 예외 클래스에서 사용할 수 있다.
* 위의 메소드를 통해서 예외의 메시지와 예외 발생 코드를 추적할 수 있다.