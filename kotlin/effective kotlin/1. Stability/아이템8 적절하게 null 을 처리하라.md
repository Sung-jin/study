## 적절하게 null 을 처리하라

* null 의 의미는 다양하다
    * 값이 부족하다
    * 프로퍼티의 값이 설정되지 않았거나 제거되었다
    * 함수의 반환값의 null 은 메서드의 실행 결과가 반환될 수 없을때
* null 은 사용하는곳 마다 의미가 달라지고 nullable 인 값은 해당 값을 사용할때 처리를 해야 하기 때문에 명확한 의미를 가지는 것이 좋다

### nullable 타입 처리

* `?.` 스마트 캐스팅, Elvis 연산자 등을 활용하여 안전하게 처리
* 오류를 throw
* 함수 또는 프로퍼티를 리펙토링하여 nullable 타입이 나오지 않게 변경

#### ?./스마트 캐스팅/Elvis 를 활용한 nullable 타입 처리

```kotlin
something?.foo()                                        // 안전 호출
if (something != null) somthing.foo()                   // 스마트 캐스팅
something?.someValue?.bar ?: throw Error("some error")  // Elvis 를 활용
something?.someValue?.bar ?: return
something?.someValue?.bar ?: "default"
```

* 위와 같이 많은 객체가 nullable 과 관련된 처리를 지원한다
```kotlin
Collection<T>?.orEmpty()
// 위와 같이 컬렉션이 null 일 경우 빈 List<T> 컬렉션을 반환하는 메서드가 존재한다
```

* 스마트 캐스팅은 코틀린의 규약 기능을 지원하며, 이를 사용하면 스마트 캐스팅할 수 있다
```kotlin
println("What is your name?")
val name = readLine()
if (!name.isNullOrBlank()) {
    println("Hello ${name.toUpperCase()}")
}

val news: List<News>? = getNews()
if (!news.isNullOrEmpty()) {
    news.forEach { notifyUser(it) }
}
```

#### 방어적 프로그래밍

* 모든 가능성을 null 일때 출력하지 않는다와 같이 올바른 방식으로 처리하는 것
* 코드가 프로덕션 환경으로 들어갔을 때 발생할 수 있는 수많은 것들로부터 프로그램을 방어하여 안정성을 높이는 방법을 나타내는 포괄적인 용어이다
* 상황을 처리할 수 있는 올바른 방법이 있을때 좋다

#### 공격적 프로그래밍

* 모든 상황을 안전하게 처리하는 것은 불가능하며, 예상하지 못한 상황이 발상하였을 때 이러한 문제를 개발자에게 알려서 수정하게 만드는 방법
* `require`/`check`/`assert` 등과 같은 기능이 공격적 프로그래밍을 위한 도구이다

##### 방어적/공격적 프로그래밍

* 네이밍만 봐서는 서로 상반되는 내용같아 보이지만, 결국 모두 안전한 코드를 위해 필요하다
* 두가지 모두를 이해하고 적절하게 사용할 수 있어야 한다

### 오류 throw

```kotlin
if (printer != null) printer.print()
```

* 위와 같은 코드가 존재한다면, `printer` 가 null 임을 예상하지 못하였다면 `print()` 가 호출되지 않아 예상하지 못하게 동작할 수 있으며, 이는 오류를 찾기 어렵게 만든다
* 상황에 따라서 오류가 발생되는 부분을 예외를 throw 함으로써 개발자에게 알려주는 방법도 존재한다
* 오류를 강제로 발생시킬 때는 `throw`/`!!`/`requireNotNull`/`checkNotNull` 등과 같은 것을 활용할 수 있다

```kotlin
fun process(user: User) {
    requireNotNull(user.name)
    val context = checkNotNull(context)
    val something = user.foo ?: throw Error("error")
    user.someValues.forEach { value
        print(value!!.nullable)
    }
}
```

### not-null assertion(!!) 과 관련된 문제

* nullable 을 처리하는 가장 간단한 방법은 not-null assertion(`!!`) 을 사용하는 것이다
* `!!` 의 경우 Java 에서 nullable 을 처리할 때 발생할 수 있는 문제가 똑같이 발생한다
    * null 이 아님을 강제로 `!!` 를 사용하여 값을 사용하였으나, 실제로는 null 일 경우 NPE 가 발생하게 된다
* `!!` 는 사용하기 쉬우나 어떠한 설명도 없는 제네릭 예외가 발생하며, 쉽게 남용하는 문제가 존재한다
* 결론적으로 `!!` 는 nullable 이나 100% null 이 아닌 경우에만 사용되어야 한다
    * 하지만 현재는 아니지만, 미래에는 확답할 수 없다
    ```kotlin
    fun largestOf(a: Int, b: Int, c: Int, d: Int): Int = listOf(a, b, c, d).max()!!
  
    // 변경 후
    fun largestOf(vararg nums: Int): Int = nums.max()!!
    largestOf() // NPE
    ```
* nullability 와 관련된 정보는 숨겨져 있어서 놓치기 쉽다
* nullable 변수를 선언해놓고 나중에 초기화 한 후 `!!` 를 통해 사용하는 것은 좋은 방법이 아니다
    * 이는 사용할 때 마다 `!!` 로 unpack 을 해야 하므로 반복적으로 사용하게 된다
    * 또한 이후에 의미있는 null 값을 가질 가능성 자체를 차단해 버린다
    ```kotlin
    class Something {
        private some: String? = null // nullable 하게 선언하고 null 로 초기화
        // private lateinit var some: String 와 같이 나중에 초기화가 된다는 의미의 lateinit 을 사용하는 형태가 좋다
        
        init {
            some = "value" // init block 에서 초기화
        }
  
        fun something() {
            print(some!!)
            // some 을 사용할때 !! 등과 같이 null 체크가 필요하다
        }
    }
    ```
* 결국 `!!` 는 미래 어느 싲머에는 언제든 NPE 가 발생할 수 있음을 알아야 하며, 사용을 피해야 한다
    * 코틀린 커뮤니티, Detekt 와 같은 정적 분석 도구 등에서 `!!` 는 아에 사용하지 않도록 정책을 가지고 있다
* `!!` 가 의미있는 경우는 거의 없으며, nullability 가 제대로 표현되지 않는 라이브러리를 사용할 정도에만 사용해야 한다

### 의미 없는 nullability 피하기

* nullability 는 결국에 처리를 위한 비용이 추가적으로 들게되며, 필요한 경우가 아니라면 nullability 자체를 피하는 것이 좋다
* null 은 중요한 메시지를 전달하는데 사용될 수 있으며, 다른 개발자가 보기에 의미가 없다면 null 을 사용하지 않는 것이 좋다
* 의미가 없는 nullability 가 잇다면, 이는 `!!` 를 사용하거나 의미 없는 null 처리 코드를 사용하는 개발자가 처리해야 한다
* nullability 를 피할수 있는 방법
    - 클래스에서 nullability 에 따라 여러 함수를 만들어 제공
        * 대표적으로 List<T> 의 `get`/`getOrNull` 이 존재한다
    - 어떤 값이 클래스 생성 이후에 확실하게 설정이 된다는 보장이 존재한다면 `lateinit` 프로퍼티와 notNull 델리게이트를 사용
    - 빈 컬렉션 대신 null 을 리턴하지 않아야 한다
        * `List<String>?` 와 `List<String?>` 의 의미는 완전히 다르다
        * 컬렉션에 요소가 없다는 의미로 null 을 리턴하지 말고 빈 컬렉션을 리턴해줘야 한다
    - nullable enum 과 None enum 값은 완전히 다른 의미이다
        * null enum 은 별도 처리가 필요하나, None enum 정의에 없으므로 필요한 경우에 사용하는 쪽에서 추가하여 활용할 수 있다는 의미이다
  
### lateinit 과 notNull 델리게이트

* 클래스가 클래스 생성 중에 초기화 할 수 없는 프로퍼티를 가지는 경우가 존재하며, 이는 사용하기 전에만 초기화가 되면 된다
* 이럴때 `lateinit` 키워드를 사용하여 해당 변수의 타입이 nullable 이 아니게 지정을 할 수 있다
    * 하지만 위에서 언급한 것처럼 사용하기 전에는 무조건 초기화가 되어 있어야 하며, 사용 시점에 초기화가 되어있지 않으면 예외가 발생한다
    * `lateinit` 은 `@BeforeEach` 와 같이 다른 함수들보다도 먼저 호출되는 함수에서 프로퍼티를 설정하는 경우에 사용할 수 있다
    * 프로퍼티를 사용할 때마다 nullable 에서 null 이 아닌 것으로 타입 변환하는 것은 바람직하지 않으며, `lateinit` 을 사용한다면 nullable 한 타입으로 지정하지 않아도 된다
        * 이는 `lateinit` 이 나중에 초기화 할 것임을 명시하는 한정자이기 때문이다
  
```kotlin
class UserControllerTest {
//    private lateinit var dat: UserDao? = null
    private lateinit var dat: UserDao
    
    @BeforeEach
    fun init() {
        dao = mockk()
    }
  
    ...
}
```

* `lateinit` 도 사용할 경우 비용이 발생한다

#### lateinit vs nullable

* `lateinit` 은 unpack 하지 않아도 된다
* 어떤 의미를 나타내기 위해서 null 을 사용하고 싶을 때 `laiteinit` 은 nullable 로 만들 수 있다
* 프로퍼티가 초기화 된 이후에는 초기화되지 않은 상태로 돌아갈 수 없다

#### lateinit 정리

* `lateinit` 은 프로퍼티를 처음 사용하기 전에 반드시 초기화 될 거라고 예상되는 상황에서 활용해야 한다
    * 이러한 상황으로는 라이프 사이클을 가지는 클래스처럼 메서드 호출에 명확한 순서가 있을 경우가 있다
* JVM 의 Int, Long, Double, Boolean 과 같은 기본 타입과 연결된 타입으로 프로퍼티를 초기화해야 하는 경우에는 `lateinit` 을 사용할 수 없다
    * 이때는 `lateinit` 보다는 조금 느리지만 Delegates.notNull 을 사용할 수 있다
  
```kotlin
var someBoolean: Boolean by Delegates.notNull()

...

someBoolean = false
```
