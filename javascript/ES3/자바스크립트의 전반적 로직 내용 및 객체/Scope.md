# Scope

##  스코프
* 스코프란 유효범위라고 생각하면 됨

```JavaScript
var foo = 'global'

function bar(){
  var foo = 'function scope'
}

foo();          //function scope
console.log(x); //global
//foo가 중복되어 있는데 참조되는게 다르다
```

* 스코프는 참조 대상 식별자를 찾아내기 위한 규칙으로 자바스트립트는 이 규칙대로 식별자를 찾는다
* 변수는 전역, if/for/while/try/catch 코드 블록이나 함수 내에 선언하며 코드 블록이나 함수는 중첩될 수 있다
* 식별자는 자신이 어디에서 선언됐는지에 의해 자신이 유효한 범위를 갖는다
* 위에서 global 값을 가진 foo는 어디에서든 참조할 수 있다
* 함수 안에 foo는 함수 코드 블록 내부에서만 참조할 수 있다
* 자바스크립트는 함수 레벨 기반의 렉시컬 스코프를 따른다
* ES6에서 let, const 키워드가 생겨서 블록 레벨 스코프를 지원한다
*

##  스코프의 구분
* 전역 스코프는 코드 어디에서든지 참조가능 -> 전역 변수
* 지역 스코프는 코드 블록이 만든 스코프로 함수 자신과 하위 함수에서만 참조할 수 있다 -> 지역 변수
* 변수는 선언 위치에 의해 스코프를 가진다

##  자바스크립트의 스코프 특징
* C-family language는 블록 레벨 스코프를 따른다
* 자바스크립트는 함수 레벨 스코프를 따른다
  * 함수 코드 블록 내에서 선언된 변수는 함수 코드 블록 내에서만 유효하고 함수 외부에서는 참조할 수 없다
  * ES6 let, const 키워드는 블록 레벨 스코프를 사용할 수 있다

```JavaScript
var x = 0;
{
  var x = 1;
  console.log(x); // 1
}
console.log(x);   // 1

let y = 0;
{
  let y = 1;
  console.log(y); // 1
}
console.log(y);   // 0
```

##  전역 스코프
* 자바스크립트에서 전역 변수는 전역 객체(Global Object)는 window의 프로퍼티이다
* C의 경우 main함수가 시작점이며, 전역 변수를 선언하기 위해서는 main함수 밖에 선언한다
* 자바스크립트는 특별한 시작점이 없어서 글로벌 영역에 변수나 함수를 선언하기 쉽다
  * 그러므로 전역 변수를 남발하게 하는 문제를 야기시킨다.
  * 전역 변수의 사용은 변수 이름이 중복될 수 있고, 의도치 않는 상태 변화로 코드를 예측하기 어렵게 만드므로 사용을 억제해야 한다

##  Non block-level scope
```JavaScript
if (true) {
  var x = 5;
}
console.log(x);
//x는 if블록안에 선언되었지만, 자바스크립트는 블록레벨 스코프를 사용하지 않으므로
//함수 밖에서 선언된 변수는 코드 블록내에서 선언되었다 할지라도 모두 전역 스코프를 가진다
//즉, x는 전역 변수이다
```

##  Function-level scope
```JavaScript
var a = 10;     // 전역변수

(function () {
  var b = 20;   // 지역변수
})();

console.log(a); // 10
console.log(b); // "b" is not defined

var x = 'global';

function foo() {
  var x = 'local';
  console.log(x);   // local

  function bar() {  // 내부함수
    console.log(x); // local
  }

  bar();
}
foo();
console.log(x); // global
//내부함수 bar()에서 실행 컨텍스트에 의해 x는 바로 위 함수에 있는 x가 출력된다

var foobar = function(){
  var a = 3, b = 5;
  var barfoo = function(){
    var b = 7, c = 11;
// 이 시점에서 a는 3, b는 7, c는 11
    a += b + c;
// 이 시점에서 a는 21, b는 7, c는 11
  };
// 이 시점에서 a는 3, b는 5, c는 not defined
  barfoo();
// 이 시점에서 a는 21, b는 5
};
```
* 중첩스코프의 경우 가장 인접한 지역을 우선 참조한다
* 전역 영역에서는 전역변수만 참조 가능
* 함수 내 지역 영역에서는 전역과 지역 변수 모두 참조 가능
* 내부함수는 자신을 포함하고 있는 외부함수의 변수까지 접근 가능
* 즉, 내부함수는 자신을 포함하는 함수와 전역 변수의 값을 변경할 수 있으며, 또한 함수도 전역변수의 값을 변경할 수 있다

##  렉시컬 스코프
```JavaScript
var x = 1;
function foo() {
  var x = 10;
  bar();
}
function bar() {
  console.log(x);
}
foo(); // 1
bar(); // 1
```
* foo()를 통해 x를 10으로 변경시켰다 하더라도 bar()에서 x는 전역변수인 1을 출력한다
* 이는 foo()에서 x를 변경하더라도 함수를 처음 선언하는 순간 자신에게 가장 가까운 곳에 있는 변수를 계속 참조한다
* 즉. foo()안에서 bar()를 하였다 하더라도 bar()라는 함수를 선언한 시점에서 전역 변수인 x를 가리키게 된다
* **렉시컬 스코프는 함수를 어디서 호출하는지가 아니라 어디에 선언하였는지에 따라 결정된다**

##  암묵적 전역 변수
```JavaScript
function foo() {
  x = 10;
}
console.log(x); // 10
```

* foo 함수 내에 선언되지 않은 x에 값을 할당
* foo에 변수 x가 없으므로 컨텍스트에서 x를 찾는다
* 전역에도 x가 존재하지 않으므로 ReferenceError를 출력할 것 같지만 전역 컨텍스트의 this가 가리키는 전역객체에 프로퍼티 x를 동적 생성한다
* 즉, x는 전역 변수가 된다 -> 암묵적 전역 변수
* 이러한 의도와 상관없이 동작하는 암묵적 전역 변수는 오류 발생의 원인이 될 가능성이 크므로 var 키워드를 사용하여야 한다

##  변수 이름의 중복
```JavaScript
// x.js
function foo (){
  i = 0;
}
// y.js
for(var i = 0; i < 5; i++){
  foo();
  console.log(i);
}
```
* x.js와 y.js를 한곳에 로드하게 된다면 변수 i는 중복된다
* x.js의 i는 var 키워드를 사용하지 않았으므로 암묵적으로 전역 변수가 된다
* y.js의 변수 i는 전역변수이다
* 자바스크립트는 중복이 허용되므로 에러는 발생시키지 않고 무한 반복 상태에 빠진다
* 전역변수의 무분별한 사용은 위험하다 -> 전역변수를 반드시 사용하여야 할 이유가 없다면 지역변수를 사용하여야 한다

##  최소한의 전역변수 사용
* 더글라스 크락포드의 제안 - 애플리케이션에서 전역변수 사용을 위해 전역변수 객체 하나를 만들어 사용하는 것

```JavaScript
var MYAPP = {};
MYAPP.student = {
  name: 'Lee',
  gender: 'male'
};
console.log(MYAPP.student.name);
//MYAPP이라는 전역변수 사용을 위한 변수 객체를 생성
//MYAPP안에는 전역변수들이 모여있고 함수에서 같은 이름으로 선언하더라도 MYAPP.~~와 같이 전역변수를 따로 명시하지 않으면 전역변수에 접근하지 않는다
```

##  즉시실행함수를 이용한 전역변수 사용 억제
* IIFE를 사용하여 전역변수 사용을 억제할 수 있다
* 이 방법은 전역변수를 만들지 않으므로 라이브러리 등에 자주 사용된다
* 즉시 실행 함수는 즉시 실행되고 그 후 전역에서 바로 사라진다

```JavaScript
(function () {
  var MYAPP = {};
  MYAPP.student = {
    name: 'Lee',
    gender: 'male'
  };
  console.log(MYAPP.student.name);  //Lee
}());
console.log(MYAPP.student.name);    //ReferenceError: MYAPP is not defined
```
