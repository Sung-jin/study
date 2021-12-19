## 선언 병합

* 컴파일러가 같은 이름으로 선언된 두개의 개별적인 선언을 하나의 정의로 병합하는 것을 뜻한다
* 병합된 정의는 원래 두 선언 각각의 기능을 모두 가진다
* 두개의 선언만 합쳐질 필요 없이 그 이상이 합쳐질 수 있다

### 기본 사용법

* ts 에서의 선언은 네임스페이스/타입/값 중 한 종류 이상의 엔티티를 생성한다
  * 네임스페이스-생성 선언은 점으로 구분된 표기법을 사용하여 접근할 이름을 가진 네임스페이스를 생성한다
  * 타입-생성 선언은 선언된 형태로 표시되며 지정된 이름에 바인딩 된 타입을 생성한다
  * 값-생선 선언은 출력된 js 에서 확인할 수 있는 값을 생성한다
  
| 선언 타입 | 네임스페이스 | 타입 | 값 |
| ---- | ---- | ---- | ---- |
| 네임스페이스 | X |  | X |
| 클래스 | | X | X |
| 열거형 | | X | X |
| 인터페이스 | | X | |
| 타입 별칭 | | X | |
| 함수 | | | X | 
| 변수 | | | X |

### 인터페이스 병합

* 가장 읿반적인 선언 병합 유형이 인터페이스 병합이다
* 각 인터페이스의 비-함수 멤버는 고유해야 한다
  * 고유하지 않다면 모두 같은 타이업어야 한다
* 인터페이스가 같은 이름이지만 다른 타입을 가진 비-함수 멤버가 존재할 경우 컴파일 오류가 발생한다
* 함수 멤버의 경우 이름이 같을 경우 동일한 함수에 대한 오버로드하는 것으로 처리한다
  * 나중에 선언된 인터페이스가 더 높은 우선순위를 가진다

```typescript
interface Box {
    hegith: number;
    width: number;
    clone(foo: string): number;
}

interface Box {
    scale: number;
    clone(bar: number): string;
    clone(foo: string): string;
}

/*
머지된 결과
interfcae Box {
    height: number;
    widht: number;
    scale: number;
    clone(bar: number): string;
    clone(foo: string): string;
}
 */

/*
inteface Box {
    height: string;
}
// 위와 같이 선언을 하면 컴파일 에러가 발생한다
 */

let box: Box = {height: 5, width: 6, scale: 10};
```

### 네임스페이스 병합

* 네임스페이스에 선언된 내보낸 인터페이스로부터 타입 정의가 병합되며, 내부에 병합된 인터페이스 정의들이 있는 단일 네임스페이스를 생성한다
* 네임스페이스 값을 병합하기 위해 각 선언 위치에서 이미 지정된 이름의 네임스페이스가 있는 경우, 기존 네임스페이스에 두번째 네임스페이스의 내보낸 멤버를 첫번째에 추가하여 값을 확장한다
* 네임스페이스를 병합할 때 `export` 되지 않은 멤버는 원래의 네임스페이스에서만 볼 수 있다

````typescript
namespace Animals {
    // let haveMuscles = true;
    export class Zebra {}
}

namespace Animals {
    export interface Legged { numberOfLegs: number; }
    export class Dog {}
    /*
    export function doAnimalsHaveMuscles() {
        return haveMuscles;
        // 해당 변수는 선언된 범위에서 접근할 수 없다
    }
     */
}

/*
병합 결과
namespace Animals {
    export interface Legged { numberOfLegs: number; }
    export class Dog {}
    export class Zebra {}
}
 */
````

### 클래스, 함수, 열거형과 네임스페이스 병합

* 네임스페이스는 다른 타입의 선언과 병합할 수 있을 정도로 유연하다
  * 이를 위해서는 네임 스페이스의 선언은 병합할 선언을 따라야 한다

#### 네임 스페이스와 클래스 병합

```typescript
function buildLabel(name: string): string {
    return buildLabel.prefix + name + buildLabel.suffix;
    // buildLabel 이라는 네임스페이스에서 prefix/suffix 이 export 되어야만 접근이 가능하다
    // 최종 결과물은 다른 클래스 내에서 관리되는 클래스이다
    // 또한 네임스페이스를 사용하여 기존 클래스에 더 많은 정적 멤버를 추가할 수 있다
}

namespace buildLabel {
    export let suffix = "";
    export let prefix = "Hello, ";
}

console.log(buildLabel("Sam Smith"));

enum Color {
    red = 1,
    green = 2,
    blue = 4
}

namespace Color {
    export function mixColor(colorName: string) {
        if (colorName == "yellow") {
            return Color.red + Color.green;
        }
        else if (colorName == "white") { } ...
    }
    // 정적 멤버의 열거형을 확장할 수 있다
}
```

#### 허용되지 않는 병합

* ts 에서 모든 병합이 허용되지 않는다
* 클래스는 다른 클래스 혹은 변수와 병합할 수 없다
  * 클래스를 병합을 대처하는 방법으로는 믹스인을 활용할 수 있다

#### 모듈 보강

* js 는 모듈 병합을 지원하지 않지만, 기존 객체를 가져와서 업데이트 패치를 할 수 있다
* 모듈 보강의 경우 두 가지 제한사항이 존재한다
  1. 보강에 새로운 최상위 선언은 할 수 없다
    * 기존 선언에 대한 패치만 가능하다
  1. default export 는 보강할 수 없으며, 이름을 가지는 export 만 보강할 수 있다

```typescript
// observable.ts
export class Observable<T> {}

// some.ts
import { Observable } from '/some/path';
Observable.prototype.map = function (f) {...}
// 이는 ts 에서 동작은 잘 하나, 컴파일러는 Observable.prototype.map 에 대해 알 수 없다
// 이는 모듈 보강을 통해 컴파일러에게 정보를 알려줄 수 있다

declare module '/some/path' {
    interface Observable<T> {
        map<U>(f: (x: T) => U): Observable<U>;
    }
}
Observable.prototype.map = function (f) {...}

// consumer.ts
import { Observable } from "/some/path";
import "/some/map";
let o: Observable<number>;
o.map(x => x.toFixed());
```

#### 전역 보강

* 모듈 내부에서 전역 범위에 선언을 추가할 수 있다
* 전역 보강 또한 기존의 모듈 보강과 같은 제한사항을 가진다

```typescript
// observable.ts
export class Observable<T> {}

declare global {
    interface Array<T> {
        toObservable(): Observable<T>;
    }
}

Array.prototype.toObservable = function () {}
```
