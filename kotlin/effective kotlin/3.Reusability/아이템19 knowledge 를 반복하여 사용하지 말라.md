## knowledge 를 반복하여 사용하지 말라

* '실용주의 프로그래머' 라는 책에서 **Don't Repeat Yourself** 라는 규칙을 **DRY 규칙** 이라고 표현하듯, knowledge 를 반복하여 사용하지 않는것이 좋다ㅏ
* DRY 말고 WET 안티패턴, SST 등과 같이 비슷한 형태의 많은 형태로 이야기가 되고 있고 많은 오용 또는 남용되고 있다
* 이러한 규칙을 확실하게 적용하려면 해당 이야기가 언제 왜 나오는지 이해가 필요하고 간단한 이론을 알아야 한다

### knowledge

* 프로그래밍에서 knowledge 는 넓은 의미로 '의도적인 정보' 를 의미한다
* knowledge 는 코드 또는 데이터로 표현이 가능하다
    * 기본 동작을 하도록 코드와 데이터를 부족하게 만들어서 표현할 수 있다
    * 상속시 특정 메소드를 오버라이드하지 않게 강제함으로써 해당 메소드는 슈퍼 클래스와 동일하게 동작하기를 원한다는 의미를 전달할 수 있다
    * 알고리즘 작동 방식, UI 형태, 원하는 결과 등이 모두 knowledge 이다
* 프로그래밍에서 중요한 knowledge
    1. 로직: 프로그램이 어떠한 식으로 동작하는지와 프로그램이 어떻게 보이는지
    2. 공통 알고리즘: 원하는 동작을 하기 위한 알고리즘

### 모든 것은 변화한다

* 언어나 플랫폼, 라이브러리 등 모든 것들은 처음과 현재를 비교한다면 항상 변화하고 있다
* 변화는 예상하지 못한 곳에서 일어고 있으며, 프로젝트의 knowledge 도 계쏙해서 변화한다
    * 프로젝트의 knowledge 가 변화하는 이유로는 사용자의 요구/습관을 더 많이 파악하거나 디자인 표준이 변하거나 플랫폼/라이브러리/도구 등의 변화 등 여러 이유가 존재한다
* 변화할 때 가장 큰 적은 knowledge 가 반복되어 있는 부분이다
    * 프로그램 내부에서 여러 부분에 반복되어 있는 코드를 변경하기 위해서 반복되는 부분을 모두 찾고 모두 변경할 수 있다
        * 이는 변경 과정에서 일부 변경을 누락하거나 일부를 잘못 변경할 가능성이 매우크고, 많이 귀찮고 쓸데없는 짓으로 느껴진다
* knowledge 가 반복되면 변경이 부담스럽고 위험한 행동이 되고 확장성을 막는다

### 언제 코드를 반복해도 될까?

* 추출을 통해서 knowledge 반복을 줄이면 안 되는 상황은, knowledge 의 반복처럼 보이나 실질적으로 knowledge 가 다른것을 나타날때가 있다
    * 두개의 어플리케이션이 존재하고, 빌드 도구 설정등이 비슷하다고 하여 추출하여 knowledge 를 줄일 수 있으나, 본질적으로 두개의 프로젝트는 다른 것이고 하나의 프로젝트 설정을 변경하면 다른 프로젝트에도 영향이 가게 된다
* 신중하지 못한 추출은 변경을 더 어렵게 만들수 있고, 구성을 읽을 때도 더 어려울 수 있다
* '함께 변경될 가능성이 높은가? 따로 변경될 가능성이 높은가?' 라는 질문을 통해 반복되는, 같은 knowledge 인지 판단할 수 있는 근거가 될 수 있다
    * 코드를 추출하는 이유는 변경을 쉽게 만들기 위함이기 때문에 위와 같은 조건으로 추출 대상을 선정할 수 있다
* 비즈니스 규칙이 다른곳에서 왔는지 확인하여 해당 규칙은 독립적으로 변경될 가능성이 높다고 판단할 근거가 될 수 있다
* 잘못된 코드 추출로부터 보호할 수 있는 규칙으로 단일 책임 원칙(Single Responsibility Principle,SRP) 가 존재한다

### 단일 책임 원칙

* 단일 책임 원칙이란, 클래스를 변경하는 이유는 단 한 가지이어야 한다라는 의미이다
* 단일 책임 원칙이라는 용어를 만든 로버트 C. 마틴이 다음과 같은 비유로 단일 책임 원칠을 설명한다
    * 두 액터가 같은 클래스를 변경하는 일은 없어야 한다
    * 액터란 변화를 만들어 내는 존재를 의미하며, 서로의 업무와 분야에 대해서 잘 모르는 개발자로 비유된다
    * 이러한 개발자들이 같은 코드를 변경하는 것은 굉장히 위험한 일이다
    
```kotlin
class Student {
    fun isPassing(): Boolean =
        calculatePointsFromPassedCourses() > 15
    // 장학금 관련 부서에서 만든 프로퍼티
    
    fun qualifiesForScholarship(): Boolean =
        calculatePointsFromPassedCourses() > 30
    // 인증 관련 부서에서 만든 프로퍼티
    
    private fun calculatePointsFromPassedCourses(): Int {
        // 위의 각 부서에서 만든 프로퍼티에서 공통으로 사용되는 메소드
    }
}

/**
 * 위와 같이 각 부서에서 사용하는 프로퍼티와 공통 메소드가 존재하는 상황에서 두 부서가 아닌 다른 개발자가
 * 장학금과 같련된 프로퍼티의 조건을 변경해야 한다면 calculatePointsFromPassedCourses 에서
 * qualifiesForScholarship 프로퍼티의 값을 수정하고 있음을 확인하고
 * 요구사항에 맞게 변경을 진행하고, calculatePointsFromPassedCourses 의 값을 사용하는
 * isPassing 도 관련된 값이라고 생각하여 같이 수정을 하는 이슈가 발생할 수 있다
 */
```

* 일반반적으로 private 메소드는 두가지 이상의 역할을 하지 않는 관습 때문에, 해당 함수가 자신이 해야 하는 일 이외의 책임을 가지고 있을 거라고 예측하지 못하고 수정을 할 수 있다
* 결론적으로 위의 예제에서는 처음부터 책임에 따라서 다른 클래스로 구분하여 만들어야 했다
    * StudentIsPassingValidator: 인증 통과 여부를 확인하는 클래스
    * StudentQualifiesForScholarShipValidator: 장학금을 받을 수 있는 포인트를 가지고 있는지 여부를 확인하는 클래스
* 또는 코틀린 확장 함수를 활용하여 구분이 가능하다
```kotlin
class Student {
    fun calculatePointsFromPassedCourses(): Int {}
    // 단, 외부에서 접근을 해야 하므로 public 으로 선언되어야 한다
    // 또한 위의 메소드는 공통 부분이므로, 이를 함수로 수정해서는 안되는 규약을 정해야 한다
}

// scholarship 모듈
fun Student.qualifiesForScholarship(): Boolean {}

// accreditations 모듈
fun Student.isPassing(): Boolean {}
```

* 단일 책임 원칙은 다음과 같은 사실을 알려준다
    1. 서로 다른곳에서 사용하는 knowledge 는 독립적으로 변경할 가능성이 많으며, 비슷한 처리를 하더라도 완전히 다른 knowledge 로 취급하는 것이 좋다
    2. 다른 knowledge 는 분리해 두는 것이 좋으며, 그렇지 않으면 재사용해서는 안되는 부분을 재사용하려는 유횩이 발생할 수 있다
  
## 정리

* 모든 것은 변화하며, 공통 knowledge 가 있다면 이를 추출하여 변화에 대비해야 한다
* 여러 요소에 비슷한 부분이 존재하고, 이를 변경할 때는 실수가 발생할 수 있다
    * 따라서 이러한 부분은 추출하여 공통으로 만드는 것이 좋다
    * 추가적으로 의도하지 않은 수정을 피하거나 다른곳에서 조작하는 부분이 있다면, 분리하여 사용하는 것이 좋다
* 많은 개발자는 DRY 원칙을 엄격하게 지키려고 비슷해 보이는 코드는 모두 추출하려는 경향이 있지만, 극단적인 것은 언제나 정답은 아니다
