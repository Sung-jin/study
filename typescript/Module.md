## 소개

* ECMAScript 2015 부터 js 에 모듈 개념이 생겼다
* 모듈은 전역 스코프가 아닌 자체 스코프 내에서 실행된다
  * 모듈 내에서 선언된 변수, 함수, 클래스 등은 `export` 양식 중 하나를 사용하여 명시적으로 `export` 하지 않는 한 모듈 외부에서 보이지 않는다
  * 다른 모듈에서 export 한 변수, 함수, 클래스, 인터페이스 등을 사용하기 위해서 `import` 양식 중 하나를 사용하여 `import` 해야 한다
* 모듈은 선언형이며, 모듈 간의 관계는 파일 수준의 `imports`/`exports` 관점에서 지정된다
* 모듈은 모듈 로더를 사용하여 다른 모듈을 `import` 한다
  * 런타임 시 모듈 로더는 모듈을 실행하기 전에 모듈의 모든 의존을 찾고 실행해야 한다
* ECMAScript 2015 와 마찬가지로 ts 최상위 수준의 `import` 혹은 `export` 가 포함된 모든 파일을 모듈로 간주한다
  * 최상위 수준의 `import` 혹은 `export` 선언이 없는 파일은 전역 스코프에서 사용할 수 있는 스크립트로 처리된다
  
### Export

* `export` 키워드를 추가하여 변수/함수/클래스/타입 별칭/인터페이스를 `export` 할 수 있다

```typescript
// StringValidator.ts
export interface StringValidator {
    isAcceptable(s: string): boolean;
}
// 위와 같이 export 를 하면,

// ZipCodeValidator.ts
import { StringValidator } from './StringValidator';
// import 를 통해서 사용할 수 있다

export const numberRegexp = /^[0-9]+$/;

export class ZipCodeValidator implements StringValidator {
    isAcceptable(s: string) {
        return s.length === 5 && numberRegexp.test(s);
    }
}
```

### Re-export

* 모듈은 다른 모듈을 확장하고 일부 기능을 부분적으로 노출할 수 있다
* 선택적으로 하나의 모듈은 하나 혹은 여러 개의 모듈을 감쌀 수 있고, `export * from "module"` 구문을 사용해 `export` 하는 것을 모두 결합 할 수 있다

```typescript
export class ParseIntBasedZipCodeValidator {
    isAcceptable(s: string) {
        return s.length === 5 && parseInt(s).toString() === s;
    }
}

export { ZipCodeVAlidator as RegExpBasedZipCodeValidator } from './ZipCiodeValidator';
// 기존 validator 의 이름을 변경 후 export

export * from './StringValidator';
export * from './ZipCodeValidator';
export * from './ParseIntBasedZipCodeValidator';
// 위와 같이 export 를 묶어서 할 수 있다
```

### import

* `export` 를 한 양식 중 하나를 `import` 할 수 있다

```typescript
import { ZipCodeValidator } from './ZipCodeValidator';
let myValidator = new ZipCodeValidator();
// 모듈에서 단일 export 를 import 를 할 수 있다
import { ZipCodeValidator as ZCV } from './ZipCodeValidator';
let myValidator2 = new ZCV();
// 이름을 수정해서 import 가 가능하다
```

### 전체 모듈을 단일 변수로 import 후 모듈 exports 접근에 사용하기

```typescript
import * as validator from './ZipCodeValidator';
let myValidator = new validator.ZipCodeValidator();
```

### 부수효과만을 위해 모듈 import 하기

* 권장되지 않지만, 일부 모듈은 다른 모듈에서 사용할 수 있도록 일부 전역 상태로 설정한다
* 이러한 모듈은 어떤 `exports` 도 없거나, 사용자가 `exports` 에 관심이 없다

```typescript
import "./my-module.js";
```

### 타입 import 하기

* ts 3.8 에서 `import` 혹은 `import type` 을 사용하여 타입을 `import` 할 수 있다
* `import type` 은 항상 js 에서 제거되며, 바벨 같은 도구는 `isolatedModules` 컴파일러 플래그를 통해 코드에 더 나은 가정을 할 수 있다
 
```typescript
// 동일한 import 를 재사용하기
import { APIResponseType } from './api';

// 명시적으로 import type 사용

import type { APIResponseType } from './api';
```
