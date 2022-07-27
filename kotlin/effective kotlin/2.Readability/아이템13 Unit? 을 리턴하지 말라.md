## Unit? 을 리턴하지 말라

* Unit? 은 Unit 또는 null 이라는 값을 가질 수 있으며, 이는 Boolean 의 true/false 처럼 사용을 할 순 있다

```kotlin
fun keyIsCorrect(key: String): Boolean = ...
if (!keyIsCorrect(key)) return

---
fun verifyKey(key: String): Unit? = ...
verifyKey(key) ?: return
// 위의 verifyKey 의 구현을 봐야 Unit? 임을 알고, 사용처만 보면 알수가 없고 오해의 소재가 많이 있다
```

* 위와 같이 사용은 할 수 있으나, 읽을때는 Unit? 으로 boolean 을 표현한 것은 오해의 소재가 있으며 예측하지 어려운 오류를 만들 수 있다
* 그리고 Boolean 이 존재하는데 위와 같이 Unit? 을 사용할 이유는 없으며 Unit? 을 리턴하거나 이를 기반으로 연산하지 않는 것이 좋다
