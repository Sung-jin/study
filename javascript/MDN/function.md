## 함수

* js 에서 기본적인 구성 블록 중 하나
* 작업을 수행하거나 값을 계싼하는 문장 집합 같은 자바 스크립트 절차
* 함수를 사용하기 위해서는 함수를 호출하는 범위 내에 함수를 정의해야 한다.

### 함수 선언

* 구성
    1. 함수의 이름
    1. 괄호 안에서 쉼표로 분리된 함수의 매개변수
    1. 중괄호 안의 함수를 정의하는 js 표현

```js
// statement
function sum(a, b) {
    return a + b;
}

// function expression
const factorial = function fac(n) { return n < 2 ? 1 : n * fac(n - 1) };
// 함수 표현식에는 함수 이름을 지정할 수 있고, 함수 내에서 자신을 참조하는데 사용 할 수 있다.
// 또한, 다른 함수의 매개변수로도 전달 할 수 있다.
function map(fn, a) {
    const result = [];
    for (let i = 0; i != a.length; i++) result[i] = fn(a[i]);
    
    return result;
}
```

* 기본 자료형 (boolean, number, string ...) 으로 전달된 매개변수를 함수 블록안에서 변경되더라도 전역적, 함수를 호출한 부분애 적용되지 않는다.
* 하지만, 기본 자료형이 아닌 값은 함수 내에서 변경이 되면 함수 외부에도 해당 값이 적용된다.

```js
function someFunction(obj) {
   obj.foo = 'change'; 
}

const someObj = {foo: 'before'};

console.log(someObj.foo); // before

someFunction(someObj);

console.log(someObj.foo); // change
```
