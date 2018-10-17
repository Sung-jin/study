# 엄격 모드

```JavaScript
function foo(){
  x = 10;
}
console.log(x);
```

* foo에서 변수 x를 찾을 때, 자바스크립트 엔진은 변수 x가 어디에 선언 되었는지 스코프 체인을 통해 검색한다
* foo 함수의 컨텍스트에서 x를 검색하지만 함수 컨텍스트에는 변수 x에 대한 변수 선언이 없으므로 검색에 실패한다
* 함수 컨텍스트의 상위 컨텍스트에서 변수 x를 검색한다
* 반복하여 결국 전역 컨텍스트까지 확인하고, 전역에도 없으면 ReferenceError를 출력하지 않고 전역 컨텍스트의 this가 가리키는 전역 객체에 프로퍼티 x를 동적으로 생성한다
* 동적으로 전역 변수로 생성되는 변수를 암묵적 전역 변수라고 한다
* 암묵적 전역 변수는 오류 발생의 원인이 될 가능성이 크다
  * 변수를 선언할 때는 반드시 var 키워드를 사용하여야 한다
  * 실수, 무지로 인해 오류가 발생 할 수 있다
  * 잠재적인 오류를 발생시키기 어려운 개발 환경을 만드는 것을 지원해주는 ES5의 strict mode가 있다
* strict mode는 자바스크립트 언어의 문법을 보다 엄격히 적용하여 기존에는 무시되던 오류를 발생시킬 가능성이 높거나 자바스크립트 엔진의 최적화 작업에 문제를 일으킬 수 있는 코드에 대해 명시적인 에러를 발생시킨다
* ESLinkt와 같은 프로그램을 사용할 수 있다

--------------------------------------------------------------------------------

* strict mode를 적용하고자 하는 곳의 선두에 'use strict'; 디렉티브를 추가한다

```Javascript
'use strict';
//전역에 strict mode 적용
function foo(){
  x = 10; // ReferenceError: x is not defined
}
foo();
////////////////////////////////////////////////////////
function foo(){
  'use strict';
  //함수 단위에 strict mode 적용
  x = 10; // ReferenceError: x is not defined
}
foo();
////////////////////////////////////////////////////////
function foo(){
  x = 10;
  'use strict';
  //선두에 없으면 제대로 동작하지 않는다
}
foo();
```

* 전역에 strict mode를 적용할 경우 스크립트 단위로 적용된다
* strict mode 스크립트와 non-strict mode 스크립트를 혼용하는 것은 오류를 발생시킬 수 있다
* 외부 서드 파티 라이브러리를 사용하는 경우, 라이브러리가 non-strict mode일 경우도 존재하기 때문에 전역에 strict mode를 적용하는 것은 좋지 않다
* 차라리, 즉시 실행함수로 스크립트를 감싸서 스코프를 구분하고 즉시 실행함수의 선두에 strict mode를 적용해라

```JavaScript
(function () {
  // non-strict mode
  var lеt = 10; // OK

  function foo() {
    'use strict';

    let = 20; // SyntaxError: Unexpected strict mode reserved word
  }
  foo();
}());
```

* 위와 같이 함수 단위로 strict mode를 적용하는 것도 피하는게 좋다
* 어떤 함수는 strict mode를 적용하고, 어떤 함수는 strict mode를 적용하지 않는 것은 바람직하지 않다
* 그렇다고 모든 함수에 일일이 strict mode를 적용하는 것은 번거롭다
* 또한, strict mode가 적용된 함수가 참조할 함수 외부의 컨텍스트에 strict mode를 적용하지 않는다면 문제가 발생할 수 있다
* 즉, strict mode는 즉시 실행함수로 감싼 스크립트 단위로 적용하는 것이 바람직하다

--------------------------------------------------------------------------------

* strict mode를 적용했을 때의 에러가 발생하는 사례

```JavaScript
//암묵적 전역 변수
(function () {
  'use strict';

  x = 1;
  console.log(x); // ReferenceError: x is not defined
}());

//변수, 함수, 매개변수의 삭제
(function () {
  'use strict';

  var x = 1;
  delete x;
  // SyntaxError: Delete of an unqualified identifier in strict mode.

  function foo(a) {
    delete a;
    // SyntaxError: Delete of an unqualified identifier in strict mode.
  }
  delete foo;
  // SyntaxError: Delete of an unqualified identifier in strict mode.
}());

//매개변수 이름의 중복
(function () {
  'use strict';

  //SyntaxError: Duplicate parameter name not allowed in this context
  function foo(x, x) {
    return x + x;
  }
  console.log(foo(1, 2));
}());

//with 문의 사용
(function () {
  'use strict';

  // SyntaxError: Strict mode code may not include a with statement
  with({ x: 1 }) {
    console.log(x);
  }
}());

//일반 함수의 this
//일반 함수를 호출하면 this에 undefined가 바인딩 된다
//에러는 발생하지 않는다
(function () {
  'use strict';

  function foo() {
    console.log(this); // undefined
  }
  foo();

  function Foo() {
    console.log(this); // Foo
  }
  new Foo();
}());
```

* IE9 이하에서는 지원하지 않는다
