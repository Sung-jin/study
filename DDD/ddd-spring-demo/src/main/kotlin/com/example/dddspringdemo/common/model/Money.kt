package com.example.dddspringdemo.common.model

data class Money (
    val value: Int
    // value 의 타입이 val 이고, 별도의 setter 가 없으므로
    // Money 는 불변타입이다
    // 이러한 불변타입으로 구현하는 이유는 보다 안전한 코드를 작성할 수 있기 때문이다 (참조의 의한 변경 등의 예상치 못한 변경을 방지하고 원본 데이터가 변경되지 않음을 보장한다)
) {
    fun add(money: Money): Money {
        return Money(this.value + money.value)
        // 불변 객체로 동작하게 하기 위해, 내부 값을 변경할 경우에는 새로운 객체를 리턴하도록
        // 위와 같이 구현해야 한다
    }

    fun multiply(multiplier: Int): Money {
        return Money(this.value * multiplier)
    }
}
// 타입이 1개여도 '돈' 이라는 의미의 벨류 타입을 만들어서 사용할 수 있다
// 필드 정의만 해도 좋지만, 해당 타입에 계산등의 로직을 추가할 수도 있다

// 위와 같이 정의를 하면 기존에는 돈 amount, price.. 등과 같은 이름에 Int 타입이었지만,
// 어떠한 이름이더라도 Money 라는 필드를 가지게 됨으로써 어떠한 동작을 하는지 예상을 할 수 있다