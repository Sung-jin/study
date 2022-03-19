### 트리플 슬래시

* 단일 XML 태그를 포함하는 한줄 주석
* 주석의 내용은 컴파일러 지시어로 사용된다
* 트리플 슬래시는 오직 포함된 파일의 상단에서만 유효하다
* 다른 트리플 슬래시 지시어를 포함한 주석 앞에만 위치할 수 있다
    * 문 혹은 선언 뒤에 나올경우, 보통의 한 줄 주석으로만 여겨지며 특별한 의미를 가지지 않는다

#### /// \<reference path="..." />

* 가장 일반적인 지시어이다
    * 파일간 의존성 선언으로 사용된다
* 트리플 슬래시 참조는 컴파일 프로세스에 추가적인 파일을 포함하도록 컴파일러에게 지시한다
* `--out`/`--outFile` 을 사용할 때 출력물을 정렬하기 위한 방법으로 사용된다
* 파일은 전처리 통과 후 입력과 동일한 순서로 출력 파일 위치에 생성된다

#### 입력 파일 전처리

* 컴파일러는 모든 트리플 슬래시 참조 지시어를 분석하기 위해 입력 파일에 대한 전처리를 수행한다
    * 해당 과정에서 추가 파일이 컴파일에 추가된다
* 이러한 과정은 root files 집합에서 시작한다
    * `tsconfig.json` 파일의 `files` 목록에 지정된 파일 이름이나, 커맨드 라인에 지정되어있다
    * 이러한 루트 파일들은 지정된 순서대로 전처리 된다
    * 파일이 목록에 추가되기 전 파일에 있는 모든 트리플 슬래시 참조가 처리되고 그 대상들이 포함된다
* 트리플 슬래시 참조는 파일에서 보이는 순서대로 깊이 우선으로 처리된다
* 루트가 없는 경우 트리플 슬래시 참조 경로는 이를 포함하고 있는 파일을 기준으로 처리된다

#### 오류

* 존재하지 않는 파일을 참조할 경우에는 오류이다
* 파일이 자기 자신에 대한 트리플 슬래시 참조를 가지는 것은 오류이다

#### --noResolve 사용하기

* 컴파일러 플래그 `--noResolve` 가 지정되면 트리플 슬래시 참조는 무시되며, 새 파일을 추가하거나 제공된 파일의 순서를 변경하지 않는다

### /// \<reference types="..." />

* 패키지의 의존선을 선언한다
* 패키지 이름을 처리하는 과정은 `import` 문에서 모듈 이름을 처리하는 과정과 유사하다
* 선언 파일에 `///<reference types="node" />` 를 포함하는 것은 해당 파일이 `@types/node/index.d.ts` 에 선언된 이름을 사용한다고 선언하며, 해당 패키지는 선언 파일과 함께 컴파일에 포함되어야 한다는 것을 의미한다
* 컴파일ㄹ 중 생성된 선언 파일의 경우 자동으로 `/// <reference types="..." />` 가 추가된다
* `.ts` 파일에서 `@type` 의 패키지의 의존성을 선언하려면 커맨드 라인에 `--types` 를 사용하거나, `tsconfig.json` 을 사용하면 된다

### /// \<reference lib="..." />

* 해당 지시어는 파일이 명시적으로 기존 내장 lib 파일을 포함하도록 한다
    * 내장 `lib` 파일은 `tsconfig.json` 의 `lib` 컴파일러 옵션과 같은 방식으로 참조된다
* DOM API 또는 Symbol 또는 Iterable 과 같은 내장 js 런타임 생성자와 같은 내장 타입에 의존하는 선언 파일 작성자에게는 트리플 슬래시 참조 lib 지시어를 사용하는 것을 권장한다

```typescript
/// <reference lib="es2017.string" />

"foo".padStart(4);
// 이는 --lib es2017.string 으로 컴파일 하는 것과 같다
```

### /// \<reference no-default-lib="true"/>

* 파일을 기본 라이브러리라고 표시하며, 컴파일러에게 기본 라이브러리를 컴파일에 포함시키지 않도록 지시한다
    * 이는 `--nolib` 옵션을 커맨드 라인에 넘기는 것과 비슷한 영향을 준다
* `--skipDefaultLibCheck` 를 넘겨주면 컴파일러가 `/// <reference no-default-lib="true" />` 을 가지는 파일만 검사하지 않는다

### /// \<amd-module />

* 기본적으로 AMD 모듈은 익명으로 생성되나, 이는 모듈로 만들어 내는 과정에 번들러와 같은 다른 툴을 사용할 경우 문제를 발생시킬 수 있다
* `amd-module` 지시어는 컴파일러에게 선택적으로 모듈의 이름을 넘길 수 있도록 해준다

```typescript
///<amd-module name="NamedModule"/>
export class C {
}
// 이는 AMD define 호출의 일부로 NamedModule 이름을 모듈에 할당하는 결과를 낳는다

define("NamedModule", ["require", "exports"], function (require, exports) {
    var C = (function () {
        function C() {
        }
        return C;
    })();
    exports.C = C;
});
```

### /// \<amd-dependency />

* 해당 지시어는 deprecated 가 되었으며, `import "moduleName"` 을 사용해야 한다
* 컴파일러에게 ts 가 아닌 모듈의 의존성이 결과 모듈의 require 호출에 추가되어야 한다고 알린다
* `amd-dependecy` 지시어는 선택적으로 `name` 프로퍼티를 가진다
    * 이로인해 amd-dependency 에 선택적으로 일므을 전달할 수 있다
  
```typescript
/// <amd-dependency path="legacy/moduleA" name="moduleA"/>
declare var moduleA:MyType
moduleA.callStuff()

// 생성된 js 코드는 아래와 같다
define(["require", "exports", "legacy/moduleA"], function (require, exports, moduleA) {
    moduleA.callStuff()
});
```
