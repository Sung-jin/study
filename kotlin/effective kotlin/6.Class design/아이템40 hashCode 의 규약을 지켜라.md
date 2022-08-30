## hashCode 의 규약을 지켜라

* hashCode 함수는 해시 테이블을 구축할 떄 사용된다

### 해시 테이블

* 각 요소에 숫자를 할당하는 함수가 필요하며, 이를 해시 함수라고 한다
    * 해시 함수는 같은 요소라면 항상 같은 숫자를 리턴한다
* 해시 함수는 빠르고 충돌이 적으면 좋다
* 해시 함수는 각각의 요소에 특정한 숫자를 할당하고, 이를 기반으로 요소를 다른 버킷에 넣는다
    * 같은 요소라면 같은 숫자를 리턴하므로 같은 요소는 항상 동일한 버킷에 넣게 된다
    * 버킷은 배열처럼 구현된다
    * 즉, 요소를 찾을 때 버킷을 해시 함수로 찾은 후, 필터링 된 값들 중 찾는 요소만 찾으면 되므로 List/Set 보다 빠르게 찾을 수 있다

### 가변성과 관련된 문제

* 요소가 추가될 때만 해시 코드를 계산한다
    * 이는 요소가 변경되면 해시 코드는 계산되지 않고, 버킷 재배치도 이루어지지 않는다
    * `LinkedhashSet`/`LinkedHashMap` 의 키는 한 번 추가한 요소를 변경할 수 없다
    
```kotlin
data class FullName(
    var name: String,
    var surname: String
)

val person = FullName("Maja", "Markiewicz")
val s = mutableSetOf<FullName>()
s.add(person)
person.surname = "Moskala"

print(person in s)  // false
```

* 위와 같은 이슈로 인해 해시 등의 자료구조에서는 mutable 객체가 사용되지 않는다
* Set/Map 의 키로 mutable 요소를 사용하면 안되며, 사용하더라도 요소를 변경하면 안된다
* 따라서 immutable 객체를 많이 사용한다

### hashCode 규약

* 어떤 객체를 변경하지 않았다면 hashCode 는 여러번 호출해도 그 결과는 항상 같아야 한다
* equals 메서드의 실행 결과로 두 객체가 같다고 나온다면, hashCode 메서드의 호출 결과도 같아야 한다
* 성능적인 측면에서 hashCode 는 최대한 요소를 넓게 퍼뜨리는 것이 좋다
* 즉, hashCode/equals 와 같이 일관성 있는 동작을 해야 한다
    * 그렇지 않으면 컬렉션 내부에 요소가 들어 있는지 제대로 확인하지 못하는 문제가 발생할 수 있다
    * 따라서 equals 구현을 오버라이드할 때 hashCode 도 같이 오버라이드 하는 것이 좋다

```kotlin
class FullName(
    var name: String,
    var surname: String
) {
    override fun equals(other: Any?): Boolean =
        other is FullName && other.name == name && other.surname == surname
}

val s = mutableSetOf<FullName>()
s.add(FullName("Marcin", "Moskala"))

val p = FullName("Marcin", "Moskala")

print(p in s) // false
```

### hashCode 구현하기

* 일반적으로 data 한정자를 붙이면 equals 와 hashCode 를 적당히 정의해주므로 이를 직접 정의할 일은 거의 없다
* 단, equals 를 따로 정의했다면, hashCode 도 함께 정의해줘야 한다
* 반대로 equals 를 따로 정의하지 않았다면, hashCode 는 따로 정의하지 않는 것이 좋다
* **equals 로 같은 요소라고 판단되었다면, hashCode 도 반드시 같은 값을 리턴해야 한다**
* hashCode 는 기본적으로 equals 에서 비교에 사용되는 프로퍼티를 기반으로 해시 코드를 만들어야 한다
    * 일반적으로 모든 해시 코드의 값을 더하며, 더하는 과정마다 이전까지의 결과에 31을 곱한 뒤 더해준다
    * 31은 필수가 아니지만 관례적으로 사용된다
