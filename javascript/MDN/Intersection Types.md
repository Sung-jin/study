### 교차타입

* 여러 타입을 하나로 결합하는 것을 교차타입이라 한다
* 기존 타입을 합쳐 필요한 모든 기능을 가진 하나의 타입을 얻을 수 있다

```typescript
function extend<First, Second>(first: First, second: Second): First & Second {
    const result: Partial<First & Second> = {};
    for (const prop in first) {
        if (first.hasOwnProperty(prop)) {
            (result as First)[prop] = first[prop];
        }
    }
    for (const prop in second) {
        if (second.hasOwnProperty(prop)) {
            (result as Second)[prop] = second[prop];
        }
    }
    return result as First & Second;
}

class Person {
    constructor(public name: string) { }
}

interface Loggable {
    log(name: string): void;
}

class ConsoleLogger implements Loggable {
    log(name) {
        console.log(`Hello, I'm ${name}.`);
    }
}

const jim = extend(new Person('Jim'), ConsoleLogger.prototype);
jim.log(jim.name);
```

### 유니언 타입

* 유니언 타입은 값이 여러 타입 중 하나임을 설명한다
* 유니언 타입을 값으로 가지고 있으면, 유니언에 있는 모든 타입에 공통인 멤버만 접근이 가능하다

```typescript
function padLeft(value: string, padding: any) {
    // padding 은 any 로 선언되어 있고, 아래 if 분기에서 타입 체크를 진행한다
    // 즉, padLeft(..., true) 와 같이 number/string 이 아닌 타입도 들어갈 수 있다
    if (typeof padding === 'number') { ... }
    if (typeof padding === 'string') { ... }
}

function padLeft(value: string, padding: number|string) { ... }
// 위와 같이 padding 에 number/string 만 허용된다는
// 유니언 타입을 표시하여 제한할 수 있다
let indentedString = padLeft("Hello world", true); // 컴파일 중에 오류

// ------------------------------------------------------------
interface Bird {
  fly();
  layEggs();
}

interface Fish {
  swim();
  layEggs();
}

function getSmallPet(): Fish | Bird { ... }

let pet = getSmallPet();
pet.layEggs(); // 성공
pet.swim();    // 오류
```

### 타입 가드와 차별 타입

* 유니언 타입은 값의 타입이 겹쳐질 수 있는 상황을 모델링하는데 유용하다
* 유니언 타입의 모든 구성 성분을 가지고 있다고 보장되는 멤버에만 접근 할 수 있다

```typescript
const pet = getSmallPet();

if (pet.swim) {
    pet.swim();
}
else if (pet.fly) {
    pet.fly();
}
// 이렇게 각 프로퍼티들에 접근하는 것은 오류를 발생

if ((pet as Fish).swim) {
    (pet as Fish).swim();
} else if ((pet as Bird).fly) {
    (pet as Bird).fly();
}
// 위와 같이 타입 단언을 사용해야 오류가 발생하지 않는다
```

### 사용자-정의 타입 가드

* 검사를 실시했을 때, 분기별에서 해당 타입을 알 수 있는 방법에는 `타입 가드` 라는 것이 있다
  * 타입 가드는 스코프 안에서의 타입을 보장하는 런타임 검사를 수행한다는 표현식이다

#### 타입 서술어 사용

```typescript
function isFish(pet: Fish | Bird): pet is Fish {
    return (pet as Fish).swim !== undefined;
    // pet is Fish 가 타입 서술어이다
    // xxx is Fish 에서 xxx 는 반드시 현재 함수 시그니처의 매개변수 이름이어야 한다
    // 해당 예제에서는 Fish 또는 Bird 이어야 한다
}

if (isFish(pet)) {
    pet.swim();
    // isFish 라는 함수를 통해서 pet 변수는 해당 스콥에서
    // Fish 타입임을 알린다
} else {
    pet.fly();
}
```

#### in 연산자 사용

* `in` 연산자는 타입을 좁히는 표현으로 작용한다
* `n in x` 라는 표현에서 n 은 문자열 또는 문자열 리터럴 타입이고, x 는 유니언 타입이다

```typescript
function move(pet: Fish|Bird) {
    if ('swim' in pet) {
        // 해당 스콥에서 swim 이라는 프로퍼티가 pet 에 존재하므로,
        // Fish 라는 타입으로 좁혀지고
        return pet.swim();
    } else {
        // if 에서 벗어날 경우, swim 이 없는 타입으로 좁혀진다
        return pet.fly();
    }
}

```

#### typeof 타입 가드

```typescript
function padLeft(value: string, padding: string | number) {
    if (typeof padding === 'number') {
        // typeof 키워드를 통해 변수를 특정 타입으로 명시할 수 있다
        // typeof 를 통해 추론할 수 있는 타입으로는 number/string/boolean/symbol 이 존재한다
        // 그 외으 타입을 비교하는 것을 막지는 않지만, 타입 가드의 표현식으로 인지되지 않는다
        return Array(padding + 1).join(" ") + value;
    }
    if (typeof padding === 'string') {
         return padding + value;
    }
    throw new Error(`Expected string or number, got '${padding}'.`);
}
```

#### instanceof 타입 가드

* `instanceof` 의 오른쪽은 생성자 함수여야 하며, ts 는 다음과 같은 형태로 타입을 추론한다
  1. 함수의 `prototype` 프로퍼티 타입이 any 가 아닌 경우
  1. 타입의 생성자 시그니처에서 반환된 유니언 타입인 경우
  
```typescript

```