### 리터럴

* 리터럴 타입은 집합 타입의 보다 구체적인 하위 타입이다
  * `'foo'`는 string 이만, string 은 `'foo'` 가 아니다

### 리터럴 타입 좁히기

* var, lat 으로 변수를 선언핧 경우 해당 변수의 값이 변결 될 가능성이 있음을 컴파일러에게 알린다
* const 로 변수를 선언하면 ts 에게 해당 객체는 절대 변경되지 않음을 알린다
* 문자열과 같은 무한한 수의 잠재적 케이스를 유한한 수의 잠재적 케이스로 줄여나가는 것을 타입 좁히기 (narrowing) 이라 한다

```typescript
let foo = 'foo';
// let 으로 변수 선언 시, 해당 변수는 변경이 가능하므로 무한한 수의 잠재적 케이스를 가진다
const bar = 'bar';
// const 로 변수 선언 시, 해당 변수는 변경이 불가능하므로 유한한 수의 잠재적 케이스를 가진다
// const 로 변수 선언 시 ts 는 bar 가 문자열이 아닌 'bar' 로 타입을 정한다
// bar 는 'bar' 라는 문자로 타입을 좁혔다 (narrowing)
```

### 문자열 리터럴 타입

* 문자열 리터럴 타입은 유니언 타입, 타입 가드, 타입 별칭과 잘 결합된다
* 해당 기능을 함께 사용하여 enum 과 비슷한 형태를 갖출 수 있다

```typescript
type Easing = 'ease-in' | 'ease-out' | 'ease-in-out';

class UIElement {
    animate(dx: number, dy: number, easing: Easing) {
        if (easing === 'ease-in') {
            ...
        } else if (easing === 'ease-out') {}
        else if (easing === 'ease-in-out') {}
    }
}

let button = new UIElement();
button.animate(0, 0, "uneasy");
// uneasy 의 경우 Easing 에 없으므로 오류가 발생한다
```

### 숫자 릴터럴 타입

```typescript
function rollDice(): 1|2|3|4|5|6 {
    return (Math.floor(Math.random() ^ 6) + 1) as 1|2|3|4|5|6;
}
// 문자열 리터럴 처럼 숫자도 리터럴 타입을 지정할 수 있다
```
