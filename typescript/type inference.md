### 기본

```typescript
let x = 3;
// x 라는변수는 number 로 추론된다
// 이러한 추론은 변수와 멤버 초기화하고 매개변수의 기본값을 설정하며, 함수의 반환 타입을 결정할때 발생한다
```

### 최적 공통 타입

* 여러 표현식에서 타입 추론이 발생할 때, 해당 표현식의 타입을 사용하여 **최적 공통 타입**을 계산한다

```typescript
let x = [0, 1, null];
// x 타입을 추론하려면 각 배열 요소들의 타입을 고려해야 한다
// 위 예제에서는 number 와 null 이라는 후보가 존재한다
// 이러한 후보들 중 모든 후보 타입을 포함할 수 있는 타입을 선택한다

let zoo = [new Rhino(), new Elephant(), new Snake()];
// 가장 이상적인 타입 추론은 Animal[] 타입으로 추론되길 원하지만,
// 배열 데이터 중 Animal 타입의 객체가 없으므로 Animal 배열 요소 타입으로 추론하지 않는다
let zoo: Animal[] = [new Rhino(), new Elephant(), new Snake()];
// 위와 같이 모든 후보 타입을 포함하는 상위 타입을 별도로 표시해줘야 한다
```

### 문맥상 타이핑

* ts 에서는 경우에 따라 다른 방향에서도 타입을 추론한다
    * 이를 **문맥상 타이핑**이라고 한다
    * 문맥상 타이핑은 표현식의 타입이 해당 위치에 의해 암시될 때 발생한다
* 문맥상 타이핑은 아래와 같은 경우에 적용된다
    * 일반적인 경우
    * 함수 호출 인수
    * 할당의 오른쪽
    * 타입 단언
    * 객체/배열 리터럴의 멤버
    * 반환문
* 문맥상 타입은 최적 공통 타입의 후보 타입으로도 사용된다

```typescript
window.onmousedown = function(mouseEvent) {
    // window.onmousedown 함수 타입을 사용하여 할당 오른쪽에 함수 표현식의 타입을 추론한다
    // 즉, window.onmousedown 의 매개변수에 해당되는 mouseEvent 라는 변수를 추론하여
    // 아래 메소드/필드의 여부를 체크하여 검사할 수 있다
    console.log(mouseEvent.button);     // 성공
    console.log(mouseEvent.kangaroo);   // 오류
    // 문맥상 타이핑을 통해 mouseEvent 에 'button' 은 있으나, 'kangaroo' 은 없음을 추론한다
}

window.onscroll = function(uiEvent) {
    // 마찬가지로 uiEvent 는 window.onscroll 의 문맥상 타이핑을 통해 타입을 추론하여
    // uiEvent 는 UIEvent 임을 추론하여, 해당 변수에 button 이라는 필드가 없음을 알고
    // 오류를 발생시킨다
    // function(uiEvent: any) 와 같은 형태로 문맥상 타이핑에 의한 추론이 아닌 별도의 타입을 지정할 수 있다
    console.log(uiEvent.button); // 오류
}
```
