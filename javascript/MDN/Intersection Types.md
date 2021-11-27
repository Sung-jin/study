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
