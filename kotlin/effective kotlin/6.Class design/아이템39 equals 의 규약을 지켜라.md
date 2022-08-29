## equals 의 규약을 지켜라

* 코틀린의 Any 에는 다음과 같은 잘 설정된 규약을 가진 메서드가 존재한다
    * equals
    * hashCode
    * to String
* 이러한 메서드들의 규약은 주석과 문서에 잘 설명되어 있다
* Java 부터 정의되어 있던 메서드이며, 코틀린에서 중요한 위치를 차지하고 있으며, 수많은 객체와 함수들이 이러한 규약에 의존하고 있다
    * 즉, 규약을 위반하면 일부 객체 또는 기능이 제대로 동작하지 않을 수 있다
    
### 동등성

* 구조적 동등성(structural equality)
    * equals 메서드와 이를 기반으로 만들어진 `==`/`!=` 연산자로 확인하는 동등성이다
    * a 가 nullable 이 아니라면 a == b 는 a.equals(b) 로 변환되고, a 가 nullable 이라면 a?.equals(b) ?: (b === null) 로 변환된다
* 레퍼런스적 동등성(referential equality)
    * `===`/`!==` 연산자로 확인하는 동등성
    * 두 피연산자가 같은 객체를 가르키면 true 를 반환한다
* equals 는 Any 에 구현되어 잇으므로, 모든 객체에서 사용할 수 있지만, 다른 타입의 두 객체를 비교하는 것은 허용하지 않는다
```kotlin
open class Animal
class Book
Animal() == Book()
Animal() === Book()
// ==/=== 연산자를 사용이 불가능하다

class Cat: Animal()
Animal() == Cat()
Animal() === Cat()
// 같은 타입 또는 상속 관계인 경우 비교가 가능하다
```

### equals 가 필요한 이유

* Any 에 구현되어 있는 기본 equals 메서드는 두 인스턴스가 완전히 같은 객체인지를 비교한다
    * 이는 모든 객체는 디폰트로 유일한 객체라는 것을 의미한다
    * 이러한 동작은 데이터베이스 연결, 레파지토리, 스레드 등의 활동 요소를 활용할 때 굉장히 유용하다
* data 한정자를 붙여서 데이터 클래스로 정의하면 주소가 아닌 내부 값이 같을 경우를 비교한다
    * 이는 내부에 어떤 값을 가지고 있는지가 중요하므로 위와 같이 내부 값의 같은지 여부로 변경된다
    * 즉, 일반적으로 데이터 모델을 표현할 때는 data 한정자를 붙인다

```kotlin
class Name(val name: String)
val name1 = Name("A")
val name2 = Name("A")
val name1Ref = name1

name1 == name1      // true
name1 == name2      // false
name1 == name1Ref   // true

data class FullName(val name: String, val surname: String)
val name1 = FullName("A", "B")
val name2 = FullName("A", "B")
val name3 = FullName("A", "C")

name1 == name1  // true
name1 == name2  // true
name1 == name3  // false
```

* 기본 생성자에 선언되지 않은 프로퍼티는 copy 로 복사되지 않는다
* data 한정자를 기반으로 동등성의 동작을 조잘할 수 있으므로, 일반적으로 코틀린에서는 equals 를 직접 구현할 필요가 없다
    * 상황에 따라서 equals 를 직접 구현해야 하는 경우가 있을 수 있다
* 결론적으로 equals 를 직접 구현해야 하는 경우는 다음과 같다
    * 기본적으로 제공되는 동작과 다른 동작을 해야 하는 경우
    * 일부 프로퍼티만으로 비교해야 하는 경우
    * data 한정자를 붙이는 것을 원하지 않거나, ㄱ비교해야 하는 프로퍼티가 기본 생성자에 없는 경우
  
### equals 규약

* 어떤 다른 객체가 이 객체와 같은지 확인할 때 사용한다
* 구현은 다음과 같은 요구사항을 충족해야 한다
    * 반사적(reflexive) 동작
        * x 가 null 이 아닌 값이라면, x.equals(x) 는 true 를 리턴해야 한다
    * 대칭적(symmetric) 동작
        * x 와 y 가 null 이 아닌 값이라면, x.equals(y) 는 y.equals(x) 와 같은 결과를 출력해야 한다
    * 연속적(transitive) 동작
        * x, y, z 가 null 이 아닌 값이고, x.equals(y) 와 y.equals(z) 가 true 라면, x.equals(z) 도 true 이어야 한다
    * 일관적(consistent) 동작
        * x, y 가 null 이 아닌 값이라면, x.equals(y) 는 여러번 실행하더라도(비교에 사용되는 프로퍼티를 변경하지 않은 경우) 항상 같은 결과를 리턴해야 한다
    * null 과 관련된 동작
        * x 가 null 이 아닌 값이라면, x.equals(null) 은 항상 false 를 리턴해야 한다
  
#### 반사적 동작

```kotlin
class Time(
    val millisArg: Long = -1L,
    val isNow: Boolean = false
) {
    val millis: Long get() =
        if (isNow) System.currentTimeMillis()
        else millisArg
  
    override fun equals(other: Any?): Booolean =
        other is Time && millis == other.millis
}

val now = Time(isNow = true)
now == now  // true/false 둘다 존재할 수 있다
List(100000) { now }.all { it == now }  // 대부분 false 이다
```

* 위와 같이 실행할 때마다 결과가 달라질 수 있으며, 이는 일관적 동작을 위반한다
* 이러한 사항을 위반하면 컬렉션 내부에 해당 객체 포함여부를 확인하는 contains 메서드에 문제가 발생한다

```kotlin
sealed class Time
data class TimePoint(val millis: Long): Time()
object Now: Time()
// 위와 같이 '객체가 현재 시간을 나타내는가를 확인하고, 현재 시간을 나타내지 않느다면 같은 타임스탬프를 가지고 있는가?'
// 로 동등성을 확인하면 된다
```

#### 대칭적 동작

* 일반적으로 다른 타입과 동등성을 확인하려 할 때 위반되는 경우가 많다

```kotlin
class Complex(
    val real: Double,
    val imaginary: Double
) {
    override fun equals(other: Any?): Boolean {
        if (other is Double) {
            return imaginary == 0.0 && real == other
        }
      return other is Complex && real == other.real && imaginary == other.imaginary
    }
}

Complex(1.0, 0.0).equals(1.0)   // true
1.0.equals(Complex(1.0, 0.0))   // false
```

* 동등성 비교가 대칭적으로 동작하지 못하면, 두 객체간의 비교를 신뢰할 수 없게 된다
* 이러한 오류는 디버깅 중 찾기 정말 어렵다
* 결론적으로 다른 클래스는 동등하지 않게 만들어 버리는 것이 좋다

#### 연속적 동작

* 타입이 다른 경우에 가장 크게 문제가 된다

```kotlin
open class Date(
    val year: Int,
    val month: Int,
    val day: Int
) {
    override fun equals(o: Any?): Boolean = when (o) {
        is DateTime -> this == o.date
        is Date -> o.day == day && o.month == month && o.year == year
        else -> false
    }
}

class DateTime(
    val date: Date,
    val hour: Int,
    val minute: Int,
    val second: Int
): Date(date.yaer, date.month, date.day) {
    override fun equals(o: Any?): Boolean = when (o) {
        is DateTime -> o.date == date && o.hour == hour && o.minute == minute && o.second == second
        is Date -> date == o
        else -> false
    }
}
```

* 위의 구현은 DateTime 과 Date 를 비교할 때 보다 DateTime 과 DateTime 을 비교할 때 더 많은 프로퍼티를 확인한다는 문제점이 존재한다
    * 이는 날짜가 같지만 시간이 다른 DateTime 두 객체를 비교하면 false 가 나오지만, 날짜가 같은 Date 를 비교하면 true 가 나온다

```kotlin
val o1 = DateTime(Date(1992, 10, 20), 12, 30, 0)
val o2 = Date(1992, 10, 20)
val o3 = DateTime(Date(1992, 10, 20), 14, 45, 30)

o1 == o2    // true
o2 == 03    // true
o1 == o3    // false
```

* DateTime 과 Date 는 상속 관계를 가지므로, 같은 객체끼리만 비교하게 만드는 방법은 좋지 않은 방법이다
    * 이는 리스코프 치환 원칙을 위반한다
    * 즉, 상속 대신 컴포지션을 사용하고 두 객체를 비교하지 못하는 만드는 것이 좋다
  
#### 일관적 동작

* immutable 객체라면 비교 결과는 언제나 같아야 한다
    * 즉, equals 는 반드시 비교 대상이 되는 두 객체에만 의존하는 순수 함수이어야 한다
* null 과는 같을 수 없다
* 일관적 동작을 위반하는 대표적인 예로는 `java.net.URL.equals` 가 존재한다

### URL 과 관련된 equals 문제

* `java.net.URL` 객체 2개를 비교할 때 동일한 IP 주소로 해석될 때 true, 아닐 때는 false 가 리턴된다
* 이는 네트워크 상태에 따라서 달라진다

```kotlin
fun main() {
    val enWiki = URL("https://en.wikipedia.org/")
    val wiki = URL("https://wikipedia.org/")
    println(enWiki == wiki)
}
// 네트워크 상태에 따라서 true/false 둘다 출력이 된다
// 즉, 동등성이 네트워크에 의존한다
```

* 위와 같은 설계의 문제점은 다음과 같다
    * 동작이 일관되지 않다
    * 일반적으로 equals/hashCode 처리는 빠를 것이라 예상하지만, 네트워크 처리는 굉장히 느리다
    * 동일한 IP 를 가진다고 동일한 콘텐츠를 나타내는 것은 아니므로, 동작 자체에 문제가 있다

## 정리

* 특별한 이유가 없는 이상 직접 equals 를 구현하는 것은 좋지 않다
* 기본적으로 제공되는 것을 그대로 사용하거나, 데이터 클래스로 만들어서 사용하는 것이 좋다
* 직접 구현을 한다면, 반사적/대칭적/연속적/일관적 동작을 꼭 확인해야 한다
    * 또한, 이러한 클래스는 final 로 만드는 것이 좋다
    * 상속을 통해 서브 클래스에서 equals 가 작동하는 방식을 변경하면 안되기 때문이다
