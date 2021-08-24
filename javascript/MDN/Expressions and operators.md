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

### void

```js
// 표현식을 평가할 때 값을 반환하지 않도록 할 때 사용한다.
void (expression)
void expression
```

### in

* 지정한 속성이 해당 객체에 존재하면 true 를 반환

```js
// 배열
var trees = ["redwood", "bay", "cedar", "oak", "maple"];
0 in trees;        // true 반환
3 in trees;        // true 반환
6 in trees;        // false 반환
"bay" in trees;    // false 반환 (인덱스에 위치한 값이 아니라
                   // 인덱스 자체를 지정해야 함)
"length" in trees; // true 반환 (length는 Array의 속성임)

// 내장 객체
"PI" in Math;          // true 반환
var myString = new String("coral");
"length" in myString;  // true 반환

// 사용자 정의 객체
var mycar = { make: "Honda", model: "Accord", year: 1998 };
"make" in mycar;  // true 반환
"model" in mycar; // true 반환
```

### instanceof

* 지정한 객체가 지정한 타입이면 true 를 반환

```js
someObject instanceof Function
// 객체 <-> 비교할 타입
```

### this

* 일반적으로 this 는 메서드의 호출 객체를 참조한다.

### new

* 사용자 정의 객체 타입이나 내장 객체 타입의 인스턴스를 생성할 수 있다.
> var objectName = new objectType([param1, param2, ..., paramN]);

### super

* 객체의 부모가 가진 함수를 호출할 때 사용한다.

```js
super([arguments]); // 부모 생성자 호출
super.functionOnParent([arguments]);
```
