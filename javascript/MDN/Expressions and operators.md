### 구조 분해

```js
var foo = [1, 2, 3];

var one = foo[0];
var two = foo[1];
var three = foo[2];
// 구조 분해 x

var [one, two, three] = foo;
// 구조 분해 사용
```

### 논리 연산자

| 연산자 | 사용법 | 설명 |
| ---- | ---- | ---- |
| 논리 AND (&&) | expr1 && expr2 | expr 모두 true 여야 true |
| 논리 OR (<code>&#124;&#124;</code>) | expr1 || expr2 | expr 중 1개만 true 이면 true |
| 논리 NOT (!) | !expr | expr 이 true 면 false, false 면 true |

* false 로 변환할 수 있는 표현식
    1. null
    1. 0
    1. NaN
    1. ""
    1. undefined
* 참고로 널 병합 연산자를 사용할 수 있다. (??)
    * 왼쪽 피연산자가 null 또는 undefined 일 때 오른쪽 피연산자를 반환하고, 그렇지 않으면 왼쪽 피연산자를 반환한다.
    * falsy 값 ("", 0) 인 경우에는 오른쪽 값이 아닌 0/"" 이 반환된다.
        * 이때는 `||` 를 사용하면 "", 0 이 아닌 오른쪽 값이 할당된다.

```js
const foo = null ?? 'default';
console.log(foo); // default

const baz = 0 ?? 42;
console.log(baz); // 0

const bar = 0 || 42;
console.log(bar); // 42
```

### delete

* 객체의 속성을 삭제한다.
* delete 연산자로 속성을 성공적으로 제거한 후, 해당 속성에 접근하면 undefined 가 반환된다.
* delete 연산자의 경우 제거할 수 있는 경우 true, 제거할 수 없으면 false 가 반환된다.

```js
delete object.property;
delete object[propertyKey];
delete objectName[index];

delete Math.PI; // false 반환 -> 삭제할 수 없는 속성
```

### typeof

* 평가 전의 피연산자 타입을 나타낸다.

```js
var myFun = new Function("5 + 2");
var shape = "round";
var size = 1;
var foo = ['Apple', 'Mango', 'Orange'];
var today = new Date();

typeof myFun;           // "function"
typeof shape;           // "string"
typeof size;            // "number"
typeof foo;             // "object"
typeof today;           // "object"
typeof dontExist;       // "undefined"
typeof true;            // "boolean"
typeof null;            // "object"
typeof 62;              // "number" 반환
typeof 'some string';   // "string" 반환
```
