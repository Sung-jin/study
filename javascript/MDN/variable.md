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

