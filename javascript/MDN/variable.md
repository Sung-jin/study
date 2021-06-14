## undefined

* 지정된 초기값이 없을 경우 undefined 값을 가진다.
    * var, let 을 통해 변수를 선언하였으나, 해당 변수가 초기화 되지 않았을 경우 undefined 가 할당된다.
* undefined 는 boolean 문맥에서 false 로 동작한다.
    * `if(undfined) console.log('값이 할당되어 있음');`
* undefined 는 number 문맥에서 NaN 으로 변환된다.
    * `undefined + 1; // 해당 결과는 NaN`
    * `null` 은 number 문맥에서 0 으로 동작한다.

## 변수 범위

* 어떤 함수 밖에 변수를 선언하면, 해당 함수에서 해당 변수에 접근할 수 있다.
    * 가장 외부에 선언된 변수를 **전역 변수** 라고 한다.
* 특정 함수 등의 범위안에 선언된 변수를 지역 변수라고 하며, 해당 범위 내부에서만 접근이 가능하다.
* ES6 이전의 js 에는 block scope 가 없다.
    * block 내에 선언된 변수는 그 블록 내에 존재하는 함수 (혹은 전역 범위) 에 지역적이다.

```js
// before ES6

if (true) {
    var x = 5;
}

console.log(x); // 5
// x 의 범위가 if 가 아닌 x 가 선언된 함수나 전역 문백이기 때문에
// 위와 같은 결과가 발생한다.
```

* ES6 의 let 으로 선언하면, 해당 현상은 발생하지 않는다.

```js
if (true) {
    let y = 5;
}

console.log(y); // ReferenceError: y is not defined
```

## 변수 호이스팅

* 나중에 선언된 변수를 참조할 수 있는 것을 **호이스팅(hoisting)** 이라 한다.
    * js 변수가 어떤 의미에서 끌어올려지거나 함수나 문의 최상단으로 올려지는 것을 말한다.
    * 끌어올려진 변수는 (사용/참조 후에 선언 및 초기화 포함) undefined 값을 반환한다.
* 함수는 함수 선언만 상단으로 호이스팅되며, 함수 표현식을 호이스팅이 되지 않는다.

```js
foo(); // foo

function foo() {
    console.log('foo');
}

bar(); // TypeError: bar is not a function

var bar = function() {
    console.log('bar')
}
```

## 전역 변수

* 전역 객체는 global 객체의 속성이다.
* 웹에서는 global 은 window 이다.
    * `window.foo = 'something';` 과 같이 전역 변수를 선언 및 접근할 수 있다.
* 전역 변수에 선언이 되면, 다른 창에서 해당 값에 접근할 수 있다.
    * A window/iframe 에서 전역 변수로 선언한 foo 를 B window/iframe 에서 접근을 할 수 있다.

## 상수

* const 키워드를 통해 읽기 전용 상수를 만들 수 있다.
    * 상수는 스크립트가 실행중에 대입을 통해 값을 변경하거나, 재선언이 불가능하다.
    * 상수는 선언과 동시에 값을 초기화 해야 한다.
* 상수도 let 과 같은 block scope 을 가진다.
* 상수로 할당된 객체/배열의 속성은 보호되지 않는다.

```js
const someObj = {foo: 'foo'};
someobj.foo = 'bar'; // ok

const someArr = ['bar'];
someArr[0] = 'other';
someArr.push('new push');
```

## 데이터 형

* ECMAScript 표준은 다음과 같은 데이터 형을 정의한다.
    1. Boolean
       * true/false
    2. null
       * js 는 대소문자를 구분하므로, null/Null/NULL 등과 모두 다르다.
    3. undefined
       * 값이 저장되어 있지 않은 최상위 속성
    4. Number
       * 정수 또는 실수형 숫자
    5. String
       * 문자열
    6. Symbol
       * ES6 에 도입 된, 인스턴스가 고유하고 불변인 데이터 형
    7. Object

## 자료형 변환

* js 는 정형 언어이다.
    * 즉, 변수 선언 시 데이터 형을 지정할 필요가 없다.
    * 데이터 형이 스크립트 실행 도중 필요에 의해 자동으로 변환된다.
* js 에서 + 는 다음과 같이 동작한다.
    * 숫자와 문자열 사이의 + 는 숫자가 문자열로 변하고 문자열과 합쳐진다.
    * 숫자와 숫자 사이의 + 는 숫자가 계산된다.
    * `+` 가 아닌 다른 연산자의 경우, 숫자를 문자로 변경하지는 않는다.
* 문자를 숫자로 변경할 때 다음과 같은 방법을 사용할 수 있다.
    * parseInt() - 오직 정수만 반환 / parseFloat()
    * 또는 + 라는 연산자를 앞에 붙여서 숫자로 변환할 수 있다.

```js
var foo = 'bar';
foo = 123;
// 정형 언어이므로, 오류가 발생하지 않는다.
----------------

var x = 10;
var y = 20;
console.log(x + y); // 30
----------------

var z = 'hi';
console.log(x + z); // 10hi
console.log("30" - x); // 20
----------------

console.log(parseFloat("1.1") + parseInt("2")); // 3.1
console.log(+"1.1" + (+"3.1")); // 4.2
```

## 리터럴

* js 에서 값을 나타내기 위해 리터럴을 사용한다.
* 이는 스크립트에 부여한 고정값으로, 변수가 아니다.

### 배열 리터럴

* 0 개 이상의 expression (식) 목록
* 배열 리터럴을 사용해서 배열을 만들 때, 그 요소로 지정된 값으로 초기화되고 그 길이는 지정된 인수의 갯수로 설정된다.
* 배열 리터럴은 일종의 객체 initializer 이다.
* 배열 리터럴은 배열 객체이다.
* 배열 리터럴에 쉼표를 추가하면 `undefined` 가 할당된다.
    * `const foo = ['bar', , 'fuz'];` = `['bar', undefined, 'fuz']`
    * 가장 마지막에 ',' 로 끝날 경우 해당 쉼표는 무시된다.

### 불리언 리터럴

* 불리언 형은 true/false 의 리터럴 값을 가진다.
* Boolean 객체는 원시 불린 데이터 (true/false) 를 감싸는 wrapper 이다.

### 정수 리터럴

* 정수는 10진수, 16진수, 8진수, 2진수로 표혈될 수 있다.

```js
0, 10, 123, -321 // 10 진수
015, 0001, -0o77 // 8 진수
0x1234, -0xF1AB // 16 진수
0b11, 0b0011, -0b11 // 2 진수
```

### 부동 소수점 리터럴

* 부동 소수점의 구문은 다음과 같다.
    * `[(+|-)][digits][.digits][(E|e)[(+|-)]digits]`
    * +/- 가 달릴 수 있는 10진 정수 및 소수점 (.), 지수(e) 가 포함될 수 있다.

### 객체 리터럴

* 중괄호 ({}) 로 묶인 0개 이상의 객체의 속성명-값 목록이다
* 값을 할당할 때, 값을 바로 할당하거나 특정 함수의 결과값을 할당하거나 기존 변수의 값으로 할당 할 수 있다.
* 또한, 객체 리터럴 내부에 객체를 중첩가능하다.
* 객체 속성명은 빈 문자열 포함 어떤 문자열도 가능하다.
    * 속성명이 유효한 js 식별자나 숫자가 아닌 경우 따음표로 묶어야하며, 해당 속성명은 . 으로 속성에 접근할 수 없다.
    * 이러한 식별자는 배열에 접근하는 형태로 [] 안에 해당 키값으로 접근이 가능하다.

```js
const foo = 'bar';
function something(val) {
    if (val === 'fuz') return val;
    else return 'foobar';
}

var someValue = {
    key1: 'hello',
    key2: foo,
    key3: something('fuz'),
    key4: {value1: '123', value2: '456'},
    '!': '이것도 가능'
};

console.log(someValue["!"]); // 이것도 가능
```

### 향상된 객체 리터럴

* ES2015 에서 객체 리터럴은 구성에서 프로토타입 설정 할당을 위한 단축 표기, 메서드 정의, super 클래스 호출 및 식으로 동적인 속성명 계산을 지원하기 위해 확장되었다.

```js
var obj = {
    // __proto__
    __proto__: theProtoObj,
    // handler: hanlder 의 단축 표기
    handler,
    toString() {
        return super.toString();
    },
    // Computed (dynamic) property names
    [ 'prop_' + (() => 42)() ]: 42
    // prop_42: 42
}
```

### 정규식 리터럴

* 슬래시 (/) 사이에 감싸인 패턴
* `var regex = /ab+c/`;

### 문자열 리터럴

* 큰 따음표(") 또는 작은 따음표(') 로 묶인 0 개 이상의 문자
* 문자열 리터럴 값은 문자열 객체의 모든 메서드를 호출할 수 있다.
* js 는 자동으로 문자열 리터럴을 임시 문자열 객체로 변환, 메서드를 호출하고 나서 임시 문자열 객체를 폐기한다.
* `console.log('some value'.length); //10`
* ES2015 에서 템플릿 리터럴도 사용할 수 있다.

```js
const foo = 'bar';
const someValue = `
hello world!
hello ${foo}!
`;

/*
hello world
hello bar!
 */
```
