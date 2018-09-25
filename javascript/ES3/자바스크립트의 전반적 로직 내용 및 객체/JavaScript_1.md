# JavaScript 1

자바 스크립트를 사용하면 정적 HTML을 동적으로 조작할 수 있다

자바 스크립트로 HTML 요소에 접근하고 조작을 할 수 있다
> 버튼 클릭, 웹페이지 로딩 등등

HTML파일 내부에 <script></script>태그로 자바스크립트를 사용 할 수 있고, <script src="파일이름.js"></script>와 같이 외부 자바스크립트 파일을 받아올 수 있다

[자바스크립트는 브라우저에서 HTML, css와 함께 실행된다](https://d2.naver.com/helloworld/59361)
> 브라우저의 주요 기능은 client가 웹페이지를 서버에 요청하고 응답을 받아 브라우저에 표시

HTML파서는 script 태그를 만나면 DOM 생성 프로세스를 중지하고 자바스크립트 엔진에 제어 권한을 넘긴다
> 자바 스크립트 코드를 상단에 올릴 시 렌더가 완료되지 않고 스크립트가 실생될 수 있다. -> alert등과 같은게 먼저 자바스크립트로 인해 실행된다면 렌더가 완료되기전에 알람이 표시되고 렌더는 멈춘다<br>
고로  대부분 HTML구조는 헤드에 css를, 바디 하단에 script를 넣는다<br>
이를 방지하고자 HTML5에서는 script 태그에 asnyc와 defer 어트리뷰트가 추가되었다<br>
> asnyc - 웹페이지 파싱과 외부 스크립트 파일의 다운로드가 동시에 진행, 스크립트는 다운로드 완료 직후 실행 / IE9이상<br>
defer - 웹페이지 파싱과 외부 스크립트 파일의 다운로드가 동시에 진행, 스크립트는 웹페이지 파싱 완료 직후 실행 / IE9이하에서는 정상 작동 안할 수 있음

![javascript_execution](../../images/javascript_execution.jpg)

자바스크립도 다른 c, python, java 등등과 같이 for / if / function / while 등등등 사용할 수 있다!

```JavaScript
5 * 10 / 'Hello' + ' ' + 'world'
//이런 것들을 표현식이라고 한다. 이런걸 표현식이라 하는구나.. 그냥 썼었던 것들도 의미가 있음
//표현식은 하나의 값으로 평가될 수 있는 구문
```

자바스크립트에서 변수는 var라는 키워드를 사용한다.
> 이 var가 모든 변수 타입을 넣을 수 있지만 그렇다고 자바스크립트에 String, int 등등이 없는 것은 아님<br>
자바스크립트의 자료형에는 Boolean, null, undefined, Number, String, Symbol(ES6), Object가 존재<br>
null은 값이 비어있다 / undefined는 할당되지 않았다<br>
또한 var없이 그냥 변수명 사용하면 전역변수가 되고 var를 사용하면 그 scope안에서 참조할 수 있는 값이 된다 -> function scope<br>
var는 hoisting, 같은 변수이름 재선언 등등 다 허용되는 등 코드가 복잡해지면 꼬일 수 있다<br>
이래서 es6에서 let, const라는 block-scope 키워드가 생김<br>
let과 const는 변수 재선언 불가능 및 const는 재할당 불가<br>
또한 const는 변수 선언과 동시에 초기화해야 한다. 상수라고 생각하면 쉬울 듯<br>

변수는 3단계에 걸쳐 생성
* 선언 단계
> 변수 객체에 변수를 등록한다
이 변수 객체는 스코프가 참조하는 대상이 된다

* 초기화 단계
> 변수 객체에 등록된 변수를 메모리에 할당
여기에서 변수는 undefined로 초기화

* 할당 단계
> undefined로 초기화된 변수에 실제값을 할당

[Execution Context](https://poiemaweb.com/js-execution-context)

var는 선언과 초기화 단계가 한번에 이루어진다

let은 선언 단계와 초기화 단계가 분리되어 진행

scope에 변수가 등록되고 변수는 메모리에 공간을 확보한 후 undefined로 초기화

따라서 변수 선언문 이전에 변수에 접근하여도 Variable Object에 변수가 존재하기 때문에 에러가 발생하지 않는다

이러한 현상을 Variable Hoisting이라 한다

```javascript
console.log(foo); // undefined
//자바스크립트는 모든 선언문은 hoisting이 되기 때문에 referenceError가 아닌 undefined가 출력
//hoisting이란 var 선언이 해당 scope에서 선두로 옮겨진 것처럼 동작하는 것을 말한다
var foo = 123;
//foo의 선언이 호이스팅되어 console.log(foo) 앞에 var foo;가 옮겨진다
//변수 객체에 등록되고 undefined로 초기화까지만 되고 할당 단계는 이 var foo = 123; 이부분에서 진행되므로
//console.log(foo);에서는 undefined가 출력
console.log(foo); // 123
{
  var foo = 456;
}
console.log(foo); // 456

console.log(bar); // Error: Uncaught ReferenceError: bar is not defined
let bar = 123;
//let 키워드는 선언 단계와 초기화 단계로 분리되어 진행되기 때문에 초기화 이전에 참조하려하면 참조 에러가 발생
//즉, 스코프의 시작 지점부터 초기화 시작 지점까지는 변수를 참조할 수 없다
//스코프의 시작 지점부터 초기화 시작 지점까지의 구간을 일시적 사각지대(Temporal Dead Zone; TDZ)

let foobar = 1; //전역 변수
{
  console.log(foobar); // ReferenceError: foobar is not defined
  let foobar = 2; //지역 변수
}
//ES6의 선언문도 여전히 호이스팅이 발생하기 때문에 참조 에러가 발생
//let은 블록 레벨 스코프를 가지므로 코드 블록 내에서 선언된 변수 foobar는 지역 변수
//지역 변수인 foobar도 해당 스코프에서 호이스팅되고 코드 블록의 선두부터 초기화가 이루어지는 지점까지 TDZ에 빠지고 전역 변수인 foobar의 값이 출력되지 않고 참조 에러가 발생
//결론 : let이 호이스팅이 안되는게 아니고 var는 선언과 초기화가 한번에, let은 선언과 초기화가 나눠지는 차이가 있어서 다르게 나타날 뿐이다
```

이 변수라는 건 주소를 기억하는 저장소!

변수에 값을 할당한다는 의미는 '그 주소에 이 값이 있다'라는 의미

C-family 언어는 Static Typing 언어이므로 변수 선언시 자료형을 지정하여서 그 자료형에 맞는 크기의 메모리 영역을 확보

자바스크립트는 Dynamic Typing 언어이므로 Type annotation이 필요없다(loosely typed, 느슨한 타입 언어)
> 값이 할당되는 과정에서 자동으로 변수의 자료형이 타입 추을 통해 결정된다<br>
즉, 같은 변수에 여러 자료형의 값을 할당할 수 있다

```javascript
var foo;

console.log(typeof foo);  // undefined

foo = null;
console.log(typeof foo);  // object

foo = {};
console.log(typeof foo);  // object

foo = 3;
console.log(typeof foo);  // number

foo = 3.14;
console.log(typeof foo);  // number

foo = 'Hi';
console.log(typeof foo);  // string

foo = true;
console.log(typeof foo);  // boolean
```

기본 자료형의 경우 변경 불가능한 값이며 값에 의한 전달이며 메모리의 스택 영역에 고정된 메모리 영역을 점유하고 저장된다

* Boolean
  * 논리적인 요소를 타나냄
  * true / false
  * null, undefined, 0은 false
* null
  * null은 null만 가질 수 있다
  * **null != Null / NULL**
  * null은 기본자료형, 객체형 변수에 값이 없다는 것을 의도적으로 명시!
  > 변수와 메모리 어드레스의 참조 정보를 제거하는 것을 의미하며 자바스크립트 엔진은 참조가 없어진 메모리 영역에 대해 가비지 콜렉션을 수행

  * (typeof null)은 object가 나오는데, 이는 설계상의 오류
  * null임을 확인하려면 typeof가 아닌 '==='을 사용해야 한다
  > [==는 동등 연산자, ===는 일치 연산자 / ===이 훨 강하게 비교한다](http://guswnsxodlf.github.io/javascript-equal-operator)

* undefined
  * 선언 이후 값을 할당하지 않은 변수

* Number
  * C의 경우 int, long, float, double과 같이 다양한 숫자 자료형이 존재
  * ESCMAScript 표준에 따르면 숫자 자료형은 배정밀도 64비트 부동소수점형 사이에서 하나만 존재
  * 추가적으로 +/- Infinity(음/양 무한), NaN(not-a-number) 존재
  > 1 / 0 = Infinity, -1 / 0 = -Infinity, 1 * 'string' = NaN

* String
  * 텍스트 데이터를 나타낼 때 사용
  * 0개 이상의 유니코드 문자들의 집합
  * 문자열은 ''/"" 안에 넣어서 생성한다
  * 문자열은 유사 배열이다

```JavaScript
var str = 'string';
console.log(str[0], str[1], str[2], str[3], str[4], str[5]); //string
//인덱스로 접근할 수 있다

str[0] = 'S';
console.log(str);//string <- s가 S로 변경되지 않았다.
//기본 자료형은 변경 불가능한 값이고 생성된 문자열은 read only
//기존 변수에 다른 문자열을 '재할당'은 가능하다
//이는 기존 문자열을 변경하는 것이 아닌 새로운 문자열을 새롭게 할당하는 것이다
//즉, 기존 문자열은 메모리에 계속 남아있게 된다
```

* Symbol
  * ES6에서 추가된 데이터 타입
  * 애플리케이션 전체에서 유일하며 변경 불가능한 기본 자료형
  * 주로 객체의 프로퍼티 키를 생성할 때 사용
  * Symbol은 전역에서 유일하기 때문에 Symbol 값을 키로 갖는 프로퍼티는 다른 어떠한 프로퍼티와 충돌하지 않는다

```javascript
var key = Symbol('key');

var obj = {};
obj[key] = 'value';
console.log(obj[key]); //value
```

* Object
  * 데이터와 그 데이터에 관련한 동작을 모두 포함할 수 있는 개념적 존재
  * 이름과 값을 가지는 데이터를 의미하는 프로퍼티와 동작을 의미하는 메소드를 포함할 수 있는 독립적 주체
  * 자바스크립트는 객체 기반의 스크립트 언어
  * 기본자료형을 제외한 나머지 값(배열, 함수, 정규표현식 ...)은 모두 객체
  * 객체는 참조에 의한 전달이며 메모리 힙 영역에 저장 (기본자료형은 값에 의한 전달이며 스택에 저장)

변수는 메모리 상의 주소를 기억하는 저장소이며, 메모리 주소에 접근하기 위해 사람이 이해할 수 있는 언어로 지정된 식별자 이다

변수명을 정하는 규칙이 존재
* 반드시 영문자와 \_, $로 시작되어야 한다.
* 이후에는 숫자도 사용 가능

할당되지 않은 변수 접근은 undefined / 미선언 변수는 ReferenceError

~~var는 중복 선언 가능~~

~~var 키워드 생략시 전역변수 (이를 암묵적 전역변수 implicit global)~~
>안하는게 좋다

var는 함수 레벨 스코프 / let, const는 블록 레벨 스코프
* 함수 레벨 스코프는 함수 내에서 선언된 변수는 함수 내에서만 유효하며 함수 외부에서는 참조할 수 없다
> 즉, 함수 내부에서 선언한 변수는 지역 변수이며 함수 외부에서 선언한 변수는 모두 전역 변수이다

* 블록레벨 스코프는 코드 블록 내에서 선언된 변수는 코드 블록 내에서만 유효하며 코드 블록 외부에서는 참조할 수 없다

var 키워드의 문제점
* var는 전역 변수의 남발이 되고 for loop 초기화식에서 사용한 변수를 for loop 외부와 전역에서 참조할 수 있다
* var 키워드 생략 허용이 되어 의도하지 않은 변수의 전역화가 된다
* 중복 선언이 허용되어 의도하지 않은 변수값 변경이 생긴다
* 변수 호이스팅에 의해 변수를 선언하기 전에 참조가 가능해진다
