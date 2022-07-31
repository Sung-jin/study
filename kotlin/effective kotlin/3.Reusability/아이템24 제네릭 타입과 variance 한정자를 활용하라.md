## 제네릭 타입과 variance 한정자를 활용하라

* `class Cup<T>` 와 같은 제네릭 클래스가 존재한다면, T 는 variance 한정자(out 또는 in) 이 없으므로 기본적으로 invariant(불공변성) 이다
    * invariant 는 제네릭 타입으로 만들어지는 타입들이 서로 관련성이 없다는 의미이다
    * `Cup<Int>`, `Cup<String>`, `Cup<Something>` ... 모두 어떠한 관련성도 가지지 않는다
* 제네릭 한정자에 어떠한 연관성을 원한다면 out 또는 in 라는 variance 한정자를 붙여야 한다
    * out: 타입 파라미터를 covariant(공변성) 로 만든다
        * 공변성은 A 가 B 의 서브타입일 때, `Cup<A>` 가 `Cup<B>` 의 서브타입이라는 의미이다
        ```kotlin
        clas Cup<out T>
        open class Dog
        class Puppy: Dog()
        
        fun main(args: Array<String>) {
            val b: Cup<Dog> = cup<Puppy>() // ok
            val a: Cup<Puppy> = Cup<Dog>() // 오류
            val anys: Cup<Any> = Cup<Int>() // ok
            val nothings: Cup<Nothing> = Cuop<Int>() // 오류
        }
        ```
    * in: 타입 파라미터를 contravariant(반변성) 으로 만든다
        * A 가 B 의 서브타입일 때, `Cup<A>` 가 `Cup<B>` 의 슈퍼타입이라는 의미이다
        ```kotlin
        class Cup<in T>
        open class Dog
        class Puppy(): Dog()
        
        fun main(args: Array<String>) {
            val b: Cup<Dog> = cup<Puppy>() // 오류
            val a: Cup<Puppy> = Cup<Dog>() // ok
            val anys: Cup<Any> = Cup<Int>() // 오류
            val nothings: Cup<Nothing> = Cuop<Int>() // ok
        }
        ```
      
### 함수 타입

* 함수 타입은 파라미터 유형과 리턴 타입에 따라서 서로 어떤 관계를 가진다
    * 코틀린 함수 타입의 모든 파라미터 타입은 contravariant 이고, 모든 리턴 타입은 covariant 이다
    * 함수 타입을 사용할 때는 자동으로 variance 한정자가 사용된다
    * 코틀린에서 자주 사용되는 것으로는 covariant 를 가진 List 가 존재한다
        * 이는 variance 한정자가 붙지 않은 MutableList 와는 다르다

```kotlin
fun something(transition: (Int) -> Any) {}

/**
 * 위와 같이 (Int) -> Any 타입의 함수는
 * (Int) -> Number / (Number) -> Any / (Number) -> Number / (Number) -> In
 * 등 모두 동작한다
 */
```

### variance 한정자의 안전성

* 자바 배열은 covariant 이며, 이는 배열을 기반으로 제네릭 연산자는 정렬 함수 등을 만들기 위해서라는 이야기가 존재한다
* 하지만 자바의 배열이 covariant 속성을 가지고 때문에 큰 문제가 발생한다

```java
Integer[] numbers = {1, 4, 2, 1};
Object[] objects = numbers;
objects[2] = "B"; // 런타임 오류 - ArrayStoreException

// 위와 같이 numbers 를 Object[] 로 캐스팅해도 구조 내부에서 사용되고 있는 실질적인 타입이 바뀌는 것이 아니다
// 즉, 위와 같이 Object[] 로 캐스팅해고 String 타입의 값을 할당하면 오류가 발생한다
```

* 위와 같은 결함을 코틀린이 해결하기 위해 `IntArray`, `CharArray` 등 Array 를 invariant 로 만들었다
    * `Array<Int>` -> `Array<Any>` 등으로 변경할 수 없다
    
```kotlin
open class Dog
class Puppy: Dog()
class Hound: Dog()

fun takeDog(dog: Dog) {}

takeDog(Dog())
takeDog(Puippy())
takeDog(Hound())

// 어떤 서브타입이라도 전달할 수 있고, 아규먼트를 전달할 때 암묵적으로 업캐스팅할 수 있다
```

* 위의 예제는 covariant 하지 않고, covariant 타입 파라미터(out 한정자)가 in 한정자 위치에 있다면, covariant 와 업캐스팅을 연결해서 우리가 원한느 타입 아무거나 전달할 수 있다
* 즉, value 가 매우 구체적인 타입이라 안전하지 않으므로, value 를 Dog 타입으로 지정할 경우 String 을 넣을 수 없다

```kotlin
class Box<out T> {
    private var value: T? = null

    // 코틀린에서 사용할 수 없는 코드    
    fun set(value: T) {
        this.value = value
    }
}

val puppyBox = Box<Puppy>()
val dogBox: Box<Dog> = puppyBox
dogBox.set(Hound())

val dogHouse = Box<Dog>()
val box: Box<Any> = dogHouse
box.set("Some string")
box.set(42)

/**
 * 캐스팅 후에 실질적인 객체가 그대로 유지되고, 타이핑 시스템에서만 다르게 처리되기 때문에 위와 같은 상황은 안전하지 않다
 * 그래서 코틀린은 public in 한정자 위치에 covariant 타입 파라미터(out 한정자) 가 오는 것을 금지하여 이러한 상황을 막는다
 */

class Box<out T> {
    var value: T? = null // 오류
    
    fun set(value: T) { // 오류
        this.value = value
    }
}
```

* 가시성을 private 로 제한하면, 오류가 발생하지 않는다
    * 객체 내부에서는 업캐스트 객체에 covariant(out 한정자) 를 사용할 수 없기 때문이다
    
```kotlin
class Box<out T> {
    private var value: T? = null
        
    private set(value: T) {
        this.value = value
    }
}
```

* covariant 는 public out 한정자 위치에서도 안전하므로 따로 제한되지 않는다
* 이러한 안정성의 이유롷 생성되거나 노출되는 타입에만 covariant 를 사용하는 것이다
* covariant 인 List<T> 가 좋은 예로 있으며, 이는 함수 파라미터가 List<Any?> 로 예측된다면, 별도의 변환 없이 모든 종류를 파라미터로 전달할 수 있다
    * 다만 MutableList<T> 에서 T 는 in 한정자 위치에서 사용되며, 안전하지 않으므로 invariant 이다
    
```kotlin
fun append(list: MutableList<Any>) {
    list.add(42)
}

val strs = mutableListOf<String>("A", "B", "C")
append(strs) // 코틀린에서 사용할 수 없는 코드
```

* 다른 예로 Response 가 존재하며, Response 를 사용하면 다양한 이득을 얻을 수 있다
* variance 한정자 덕분에 아래 내용은 모두 참이 된다
    - Response<T> 라면 T 의 모든 서브타입이 허용된다
    - Response<T1, T2> 라면 T1/T2 의 모든 서브타입이 허용된다
    - Failure<T> 라면, T 의 모든 서브타입 Failure 가 허용된다
    - covariant 와 Nothing 타입으로 인해 Failure 는 오류 타입을 지정하지 않아도 되고, Success 는 잠재적인 값을 지정하지 않아도 된다
    ```kotlin
    sealed class Response<out R, out E>
    class Failure<out E>(val error: E): Response<Nothing, E>()
    class Success<out R>(val value: R): Response<R, Nothing>()
    ```
* covariant 와 public in 위치와 같은 문제는 contravariant 타입 파라미터와 public out 위치에서도 발생한다
    * out 위치는 암묵적인 업캐스팅을 허용한다
    ```kotlin
    open class Car
    interface Boat
    class Amphibious: Car(), Boat
    
    fun getAmphibious(): Amphibious = Amphibious()
    
    val car: Car = getAmphibious()
    val boat: Boar = getAmphibious()
    ```
* 이는 contravariant 에 맞는 동작이 아니다
```kotlin
class Box<in T>(
    // 코틀린에서 사용할 수 없는 코드
    val value: T
)

val garage: Box<Car> = Box(Car())
val amphibiousSpot: Box<Amphibious> = garage
val boat: Boat = garage.value // Car 를 위한 공간이다

val noSpot: Box<Nothing> = Box<Car>(Car())
val boat: Nothing = noSpot.value
// 아무것도 만들 수 없다
```

* 이러한 상황을 막기 위해, 코틀린은 contravariant 타입 파라미터를 public out 한정자 위치에 사용하는 것을 금지하고 있다
```kotlin
class Box<in T> {
    var value: T? = null // 오류
    
    fun set(value: T) {
        this.value = value
    }
    
    fun get(): T = value // 오류
        ?: error("Value not set")
}
```

* 이번에도 요소(예제에서 value)가 private 인 경우 아무런 문제가 발생하지 않는다
* 이런 형태로 타입 파라미터에 contravariant 를 사용한다

### variance 한정자의 위치

* variance 한정자는 크게 두 위치에 사용할 수 있다
    1. 선언 부분
        * 일반적으로 사용하는 위치이다
        * 클래스와 인터페이스 선언에 한정자가 적용되고, 클래스와 인터페이스가 사용되는 모든 곳에 영향을 준다
        ```kotlin
        // 선언 쪽의 variance 한정자
        class Box<out T>(val value: T)
        val boxStr: Box<String> = Box("Str")
        val boxAny: Box<Any> = boxStr
        ```
    2. 클래스와 인터페이스를 활용하는 위치
        * 특정한 변수에만 variance 한정자가 적용된다
        ```kotlin
        class Box<T>(val value: T)
        val boxStr: Box<String> = Box("Str")
        // 사용하는 쪽의 variance 한정자
        val boxAny: Box<out Any> = boxStr
        ```
* 모든 인스턴스에 variance 한정자를 적용하면 안 되고, 특정 인스턴스에만 적용해야 할 경우 이러한 코드를 사용한다
    * MutableList 는 in 한정자를 포함하면, 요소를 리턴할 수 없으므로 in 한정자를 붙이지 않는다
* 단일 파라미터 타입에 in 한정자를 붙여서 contravariant 를 가지게 하는 것은 가능하며, 이를 통해 여러가지 타입을 받아들이게 할 수 있다
```kotlin
interface Dog
interface Cutie
data class Puppy(val name: String): Dog, Cutie
data class Hound(val name: String): Dog
data class Cat(val name: String): Cutie

fun fillWithPuppies(list: MutableList<in Puppy>) {
    list.add(Puppy("Jim"))
    list.add(Puppy("Beam"))
}

val dogs = mutableListOf<Dog>(Hound("Pluto"))
fillWithPuppies(dogs)
println(dogs)
// [Hound(name=Pluto), Puppy(name=Jim), Puppy(name=Beam)]

val animals = mutableListOf<Cutie>(Cat("Felix"))
fillWithPuppies(animals)
println(animals)
// [Cat(name=Felix), Puppy(name=Jim), Puppy(name=Beam)]
```

* 참고로 variance 한정자를 사용하면, 위치가 제한될 수 있다
    * MutableList<out T> 가 있다면, get 으로 요소를 추출하였을 떄 T 타입이 나올 것이다
    * 하지만 Set 은 Nothing 타입의 아규먼트가 전달될 거라 예상되므로 사용할 수 없다
        * 모든 타입의 서브타입을 가진 리스트(Nothing 리스트) 가 존재할 가능성이 있기 때문이다
    * MutableList<in T> 를 사용할 경우 get/set 모두 사용이 가능하지만, get 을 사용하면 전달되는 자료형은 Any? 가 된다
        * 모든 타입의 슈퍼타입을 가진 리스트(Any 리스트) 가 존재할 가능성이 있기 때문이다

## 정리

* 코틀린은 타입 아규먼트의 관계에 제약을 걸 수 있는 굉장히 강력한 제네릭 기능을 제공한다
* 이러한 기능으로 제네릭 객체를 연산할 때 굉장히 다양한 지원을 받을 수 있다
* 코틀린에는 다음과 같은 타입 한정자가 존재한다
    - 타입 파라미터의 기본적인 variance 동작은 invariant 이다
        * 만약 `Cup<T>` 라고 한다면, 타입 파라미터 T 는 invariant 이다
        * A 가 B 의 서브타입일 때, `Cup<A>` 와 `Cup<B>` 는 아무런 관계를 가지지 않는다
    - out 한정자는 타입 파라미터를 covariant 하게 만든다
        * 만약 `Cup<T>` 라고 하면, 타입 파라미터 T 는 covariant 이다
        * A 가 B 의 서브타입일 때, `Cup<A>` 는 `Cup<B>` 의 서브타입이 된다
        * covariant 타입은 out 위치에 사용할 수 있다
    - in 한정자는 타입 파라미터를 contravariant 하게 만든다
        * 만약 `Cup<T>` 라고 하면, 타입 파라미터 T 는 contravariant 이다
        * A 가 B 의 서브타입일 때, `Cup<B>` 는 `Cup<A>` 의 슈퍼타입이 된다
        * contravariant 타입은 in 위치에 사용할 수 있다
* 코틀린에서는
    * List 와 Set 의 타입 파라미터는 covariant(out 한정자) 이다
        * List<Any> 가 예상되는 모든 곳에 전달할 수 있다
        * 또한 Map 에서 값의 타입을 나타내는 타입 파라미터는 covariant(out 한정자) 이다
        * Array, MutableList, MutableSet, MutableMap 의 타입 파라미터는 invariant(한정자 지정 없음) 이다
    * 함수 타입의 파라미터 타입은 contravariant(in 한정자) 이며, 리턴 타입은 contravariant(out 한정자) 이다
    * 리턴만 되는 타입에는 covariant(out 한정자) 를 사용한다
    * 허용만 되는 타입에는 contravariant(in 한정자) 를 사용한다
