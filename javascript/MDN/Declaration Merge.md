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
