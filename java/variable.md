# 변수

* 값을 저장할 수 있는 메모리의 공간을 의미.
* 변수를 지정할 때 만드는 이름은 그 값을 찾을때, 사용할 때 이용되는 메모리 주소에 붙어진 이름이다.
* 변수이름 규칙
    1. 첫 번째 글자는 문자이거나 '$', '_' 이어야 하며 숫자이면 안된다.
    2. 대소문자 구분된다.
    3. 관례적으로 첫 문자는 소문자로 시작하고 다음 단어가 시작될 때는 대문자로 시작한다.
    4. 문자 길이의 제한은 없다.
    5. 자바 예약어는 사용 할 수 없다.
* 자바 예약어
    1. 기본 데이터 타입 - boolean, byte, char, short, int, long, float, double
    2. 접근 지정자 - private, protected, public
    3. 클래스 관련 - class, abstract, interface, extends, implements, enum
    4. 객체와 관련 - new, instanceof, this, super, null
    5. 메소드와 관련 - void, return
    6. 제어문과 관련 - if, else. switch, case, default, for, do, whilte, break, continue
    7. 논라깂 - true, false
    8. 예외 처리 - try, catch, finally, throw, throws
    9. 기타 - transient, volatile, package, import, synchronized, native, final, static, strictfp, assert

```
transient : Serialize 과정 중 제외하고 싶은 경우 사용하는 키워드

volatile : 변수를 메인 메모리에 저장을 명시하는 키워드 | 멀티 스레드 환경에서 volatile 키워드가 없는 변수의 경우 CPU Cache 값에서 가져오기 때문에 스레드마다 변수 값 불일치 문제가 발 생 할 수 있다.

native : 자바가 아닌 다른 언어로 구현한 후 자바에서 사용할 때 이용하는 키워드 | 구현할 때 Java Native Interface 를 사용한다.

strictfp : Strict Floating Point 의 준말로 플랫폼에 따라 부동 소수점 연산이 다를 수 있는데 strictfp 키워드를 사용한다면 플랫폼에 상관없이 JVM의 부동소수점 연산을 사용하게 되어 일관성을 보장한다.

assert expression1;
assert expression1: expression2;
위와 같이 사용 할 수 있으며 첫번째 표현식이 거짓이면 AssertionError 발생이되며, expression2 가 있을경우 예외 메시지로 보여지게 된다.
```

* 소스 코드내에서 변수 초기화에 직접 입력된 값을 literal 이라 하며, 값을 한번 저장하고 변경이 될 수 없는 변수를 constant 라고 한다.
* {} 블록 내에서 사용되는 변수를 local variable 이라 한다.
    * 로컬 변수는 메소드 실행이 끝나면 메모리에서 자동으로 없어진다.
    * 변수는 선언된 블록 내에서만 사용이 가능하다.

# 데이터 타입

* primitive 타입

| 종류 | 타입    | 메모리 크기 |
|------|---------|-------------|
| 정수 | byte    | 1byte       |
|      | char    | 2byte       |
|      | short   | 2byte       |
|      | int     | 4byte       |
|      | long    | 8byte       |
| 실수 | float   | 4byte       |
|      | double  | 8byte       |
| 논리 | boolean | 1byte       |

* \- 2^n ~ 2^n - 1 의 범위를 가진다. (n은 byte * 8에 해당되는 숫자)
* String
    * String은 기본 타입이 아니다.
    * String 변수를 생성 할 때, String 객체가 생성되고 그 객체는 주소를 참조하게 된다. 즉, String 변수는 참조 변수이다.
* float
    * (기호) (가수) x 10^(지수)
    * 가수는 0 =< m < 1 범위의 실수
    * 1.2345 = 0.12345 x 10^1 로 표현되며 가수는 0.12345, 지수는 1 이다.
    * 기호 1bit, 지수 8bit, 가수 23bit = 32bit (4byte)
    * double 은 기호 1bit, 지수 11bit, 가수 52bit 로 더 정확한 소수점을 표현 할 수 있다.

# 타입 변환

* 지정된 타입을 다른 타입으로 변경하는 것을 말한다.
* 묵시적(자동) 타입 변환과 명시적(강제) 타입 변환이 있다.
* 묵시적 타입 변환(Promotion)
    * 프로그램 실행 도중 자동적으로 변환하는 것을 말한다.
    * 작은 크기를 가지는 타입이 큰 크기를 가지는 변수로 변경될 때 발생한다.
    * 단, byte -> char 로는 강제 변환은 되지만 자동 변환시킬 수 없다. (byte는 음수가 저장될 수 있지만, char는 음수가 저장 될 수 없다.)
    * 연산에 의해서 자동 변환될 수 있다.

```java
byte byteValue = 10;
int intValue = byteValue;

int intValue = 200;
double doubleValue = intValue; // 200.0

char charValue = 'A';
int intValue = charValue; // 65

///////////////////////////////////////

int intValue = 10;
double doubleValue = 5.5;
double result = intValue + doubleValue; // 15.5
int result2 = intValue + (int)doubleValue; // 15
```

* 명시적 타입 변환(Casting)
    * 명시적 타입 변환의 경우 큰 타입에서 작은 타입으로 변환할 수 있다.
    * ()를 이용하여 변환 하고자 하는 타입으로 변경할 수 있다.
    * 큰 타입에서 작은 타입으로 옮길 경우 데이터 손실이 발생한다.
        * ex) 4byte의 int를 1byte의 byte로 옮길 경우 int를 1btye로 4개 쪼개고 가장 끝에 있는 1btye가 저장된다.
    * char 타입으로 변환하기 위해서는 강제 변환을 해야 한다.
    * 실수 -> 정수로 변환하기 위해서는 강제 변환을 해야 한다.


```java
int intValue = 103029770;
// 00000110 00100100 00011100 00001010
byte byteValue = (byte) intValue; // 10

double doubleValue = 3.14;
int intValue = (int) doubleValue; // 정수 부분인 3만 저장
```

* 정수와 실수 변환시 주의해야 할 점

```java
int num1 = 123456780;
int num2 = 123456780;

float num3 = num2;
num = (int) num3;

int result = num1 - num2;

print result // -4
// 123456780 은 23 비트(가수)로 표현할 수 없기 때문에 근사치로 변환된다.
```

* byte, short, int, long, float, double 타입에 MAX_VALUE, MIN_VALUE 상수가 존재한다.