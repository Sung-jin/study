# 데이터 타입

* 데이터 타입은 크게 2가지로 나뉜다.
    1. primitive type (원시 타입) - 변수를 선언하고 그 변수에 저장되는 것들
        * 정수 타입
        * 실수 타입
        * 논리 타입
    2. reference type (참조 타입) - 선언된 변수는 객체 주소를 가르키며, 그 주소에 데이터가 존재되며 생성 된 객체는 heap 영역에 존재한다.
        * 배열 타입
        * 열거 타입
        * 클래스
        * String

# Memory Area

![Memory Area](https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.imgur.com%2FpAh5gIZ.png&i mgrefurl=https%3A%2F%2Fminwan1.github.io%2F2018%2F06%2F06%2F2018-06-06-Java%2CJVM%2F&tbnid=WK6PFCE2fHCL_M&vet=12ahUKEwig-53krabpAhUEc5QKHXreB3EQMygLegUIARDoAQ..i&docid=DD7xv7xVvbT9YM&w=739&h=678&q=java%20runtime%20area&ved=2ahUKEwig-53krabpAhUEc5QKHXreB3EQMygLegUIARDoAQ)
<br/>출처 [세바의 코딩교실](https://programmer-seva.tistory.com/72)

## Method Area

* JVM 이 실행할 때 생성되고 모든 스레드가 공유하는 영역
* .class 파일을 클래스 로더를 통해 분류하여 저장한다.
    * runtime constant pool
    * field data
    * method data
    * method code
    * constructor

## Heap Area

* 객체와 배열이 생성되는 영역
* 생성 된 객체와 배열은 JVM 스택 영역의 변수나 다른 객체의 필드에서 참조한다.
* 이러한 객체를 참조하는 변수 또는 필드가 없다면 쓰레기 객체가 되는데, 이를 Garbage Collector 를 JVM이 자동으로 실행하여 정리한다.
    * 자바 코드로 객체를 직접 제거시키는 방법이 제공되지 않는다.

## JVM Stack Area

* JVM Stack Area 는 스레드마다 하나씩 존재하며, 스레드가 시작될 때 할당된다.
* 따로 스레드를 추가하지 않는다면 main JVM Stack 만 존재한다.
    * 메소드를 호출할 때마다 frame 을 push 하고 해당 메소드가 종료되면 pop 동작을 수행한다.
    * 위와 같이 동작해서 예외가 발생하면 printStackTrace 메소드를 통해 예외가 발생한 시점의 JVM Stack 을 출력하며, 하나의 라인이 하나의 frame 이다.
* frame 내부에는 로컬 변수 스택이 존재하며 기본 타입 변수와 참조변수가 push, pop 동작을 한다.
    * 변수가 초기화 되는 시점에 push 된다.
    * 변수는 선언된 블록 안에서만 스택에 존재하고 블록을 벗어나면 스택에서 제거가 된다.
    * 즉, if, for, while 등의 block 안에 선언된 블록은 그 블록의 scope 을 가지게 된다.
    * 참조 변수는 stack 에 생성되지만, 그 참조가 가지고 있는 값은 heap area 에 생성되고 stack 에 생성된 변수는 heap area 의 주소를 가르킨다.

```java
String str = "Hello World!";
str = null;

// GC 가 heap area 의 Hello World! 객체를 메모리에서 제거한다.
```

## ==, !=

* primitive type 의 == != 연산은 값을 비교한다.
* reference type 의 == != 연산은 동일한 객체를 참조하는지(주소) 비교한다.

## NullPointerException

* reference type 의 경우 null 을 참조하는 변수를 만들 수 있고, 이로인해 NPE 문제가 발생한다.

## String

* 동일한 문자열을 참조할 경우 그 변수들은 모두 같은 객체를 참조하게 된다.
* 같은 문자열을 참조하게 하더라도 new 연산자를 통해서 각자 다른 객체를 참조하게 할 수 있다.

```java
String str1 = "Hello World!";
String str2 = "Hello World!";
String str3 = new String("Hello World!");

str1 == str2 // true
str1 == str3, str2 == str3 // false
str1.equal(str2) str1.eqaul(str3) // true
str2.equal(str1) str2.eqaul(str3) // true
str3.equal(str1) str3.eqaul(str2) // true
```

## Array

* 배열을 복사할 때 for loop 또는 System.arraycopy() 메소드를 이용 할 수 있다.

```java
System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length);

src - 원본 배열
srcPos - 원본 배열의 복사를 시작할 항목의 시작 인덱스
dest - 복사가 될 새로운 배열
destPos - 복사가 될 배열의 시작 인덱스
length - 복사할 개수
```

## Enum

* 한정된 값만을 가지는 데이터 타입
* enum 클래스는 관례적으로 시작 단어를 대문자로 한다.
* enum const 는 관례적으로 모두 대문자이며 단어 사이는 '_' 를 통해 연결한다.
* enum 과 enum const 는 reference type 이다.

```java
public enum Week {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

print Week.MONDAY.name(); // MONDAY
print Week.MONDAY.ordinal() // 0
print Week.MONDAY.compareTo(Week.WEDNESDAY) // 2
print Week.ValueOf("MONDAY") // Week.MONDAY
for-print Week.values() // Week.MONDAY Week.TUESDAY ... Week.SUNDAY
```
