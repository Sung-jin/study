## 데이터 집합 표현에 data 한정자를 사용하라

* data 클래스를 사용하면 아래 함수들이 자동으로 생성된다
    * toString
        * 클래스의 이름과 기본 생성자 형태로 모든 프로퍼티와 값을 출력한다
    * equals, hashCode
        * equals 는 기본 생성자의 프로퍼티가 같은지 확인한다
    * copy
        * immutable 데이터 클래스를 만들떄 편리하다
        * 기본 생성자 프로퍼티가 같은 새로운 객체를 복제한다
        * 새로 만들어진 객체의 값은 이름 있는 아규먼트를 활용하여 변경이 가능하다
        * copy 메서드는 객체를 얕은 복사하지만, 객체가 immutable 이라면 상관이 없다
    * componentN (component1, component2 ...)
        * 위치를 기반으로 객체를 해제할 수 있다
        * 객체를 해체할 때는 데이터 클래스의 기본 생성자에 붙어 있는 프로퍼티 이름과 같은 이름을 사용하는게 좋다
            * 잘못 해체할 경우 IDE 에서 경고를 제공한다
    
### 튜플 대신 데이터 클래스 사용하기

* 튜플은 Serializable 을 기반으로 만들어지며, toString 을 사용할 수 있는 제네릭 데이터 클래스이다
* 튜플만 보고 어떤 타입을 나타내는지 예측할 수 없으며, 언제나 데이터 클래스를 사용하는 것이 더 좋기에 점차 없어졌다
    * 지금은 `Pair`/`Triple` 만 남아있다
* `Pair`/`Triple` 가 아직 남아있는 이유
    * 값에 간단하게 이름을 붙일 때
    * 표준 라이브러리에서 볼 수 있는 것처럼 미리 알 수 없는 aggregate 를 표현할 때
    * 이 외에는 무조건 데이ㅏ터 클래스를 사용하는 것이 좋다
    
```kotlin
fun String.parseName(): Pair<String, String>? {
    val indexOfLastSpace = this.trim().lastIndexOf(' ')
    if (indexOfLastSpace < 0) return null
    val firstName = this.take(indexOfLAstSpace)
    val lastName = this.drop(indexOfLastSpace)
    return Pair(firstName, lastName)
}
// 리턴값인 Pair<String, String> 이 전체 이름을 나타낸다는 것을 인지하기 어렵다
// 또한, 어떤 값이 성이고 이름인지 구분할 수 없다

data class FullName(
    val firstName: String,
    val lastName: String
)

fun String.parseName(): FullName? {
    ...
    return FullName(firstName, lastName)
}
// 전체 이름을 나타내는 FullName data 클래스를 생성하는 것만으로도
// 리턴값이 전체 이름을 나타낸다는 것을 알 수 있으며, 내부 값 순서에 상관없이 프로퍼티가 이름이 있으므로
// 각 프로퍼티가 어떤걸 의미하는지 알 수 있다
```

* 위와 같이 튜플보다 데이터 클래스를 사용하는 것으로 변경할 때 추가 비용은 거의 들지 않으며, 다음과 같이 명확한 점이 늘어난다
    1. 함수의 리턴 타입이 더 명확해진다
    2. 리턴 타입이 짧아지며, 전달하기 쉬워진다
    3. 사용자가 데이터 클래스에 적혀 있는 것과 다른 이름을 활용하여 변수를 해제하면 경고가 출력된다
* 위와 같은 클래스가 좁은 스코프를 가지게 하고 싶다면 일반적인 클래스와 같은 형태로 가시성에 제한을 걸면 된다
* 결론적으로 튜플보다 데이터 클래스를 활용하면 명확성과 가시성, 그외 내부 메소드 등 여러가지 장점이 존재한다
