## mutable 컬렉션 사용을 고려하라

* immutable 컬렉션보다 mutable 컬렉션이 좋은 점은 성능적인 측면에서 더 빠르다는 점이다
    * immutable 컬렉션에 요소를 추가하려면 새로운 컬렉션을 만들면서, 요소를 추가해야 한다
    
```kotlin
operator fun <T> Iterable<T>.plus(element: T): List<T> {
    if (this is Collection) return this.plus(element)
    val result = ArrayList<T>()
    result.addAll(this)
    result.add(element)
    return result
}
```

* 위와 같이 컬렉션을 복제하는 처리는 비용이 굉장히 많이 드는 처리이며, 이러한 복제 처리를 하지 않는 mutable 컬렉션이 성능적 관점에서 더 좋다
    * 하지만 안전성 측면에서는 immutable 컬렉션이 더 좋다
* 일반적인 지역 변수는 동기화와 캡슐화 등의 문제에 해당되지 않으므로, 지역 변수로 사용할 때는 mutable 컬렉션을 활용하는 것이 더 합리적이라고 할 수 있다

## 정리

* 가변 컬렉션은 일반적으로 추가 처리가 빠르다
* immutable 컬렉션은 컬렉션 변경과 관련된 처리를 더 세부적으로 조정이 가능하다
* 일반적으로 지역 스코프에서는 이러한 세부적인 조정이 필요하지 않으므로, 가변 컬렉션을 사용하는 것이 좋다
    * 특히 utils 에서는 요소 삽입이 자주 발생할 수 있기 때문에, mutable 컬렉션을 사용하는 것이 좋다
