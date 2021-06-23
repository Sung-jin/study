## Block 문

* ECMAScript2015 이전에는 블록 범위를 가지고 있지 않다.
    * 즉, Block 내에서 선언한 변수는 블록을 넘어 변수가 위치한 함수 혹은 스크립트에 영향을 끼친다.
* ECMAScript2015 의 `let`, `const` 로 변수 선언 시 블록 범위를 제안할 수 있다.

```js
var x = 1;
{
    var x = 2;
}
console.log(x); // 2
// C, Java 등에서는 1 을 출력한다.

----------------

let y = 1;

{
    let y = 2;
    console.log(y); // 2
}

console.log(y); // 1
```

## 거짓으로 취급하는 값 (if 분기에서)

* false
* undefined
* null
* 0
* NaN
* empty string ("")

### throw

* 예외를 사용할 때 throw 문을 사용한다.

`throw expression;`

* expression 에는 어떠한 것도 사용할 수 있다.

```js
throw "Error";
throw 10;
throw true;
throw {toString: () => "I'm an object!"}

function UserException(message) {
    this.message = message;
    this.name = "UserException";
}

UserException.prototype.toString = function () {
    return `${this.name} : ${this.message}`;
}

try {
    throw new UserException("Some Error Message");   
} catch (e) {
    console.log(e.toString()); // UserException : Some Error Message
    // try 블록에서 예외가 발생 시 catch 블록이 사용된다.
} finally {
    // 해당 블록은 try 가 정상적으로 동작을 하든, 예외가 발생하여 catch 블록이 동작하든
    // try - catch 블록이 완료되고 실행된다.
}
```

* try - catch 문을 중첩할 수 있다.
* try - catch 에 catch 가 없다면 반드시 finally 블록이 있어야 한다.
* [nested try-block]()

## Error 객체 도구화 하기

* 오류의 종류에 따라 name, message 속성을 사용할 수 있다.
* name 속성은 오류의 일반 클래스 (DOMException / Error ...) 를 제공하고, message 는 error 객체를 문자열로 바꿀 수 있는 것보다 더 간결한 메세지를 제공한다.

```js
function doSomethingError() {
    if (someCustomError()) throw (new Error('Some Message'));
    else jsError();
}

...

try {
    doSomethingError();
} catch (e) {
    console.log(e.name); // 'Error'
    console.log(e.message); // 'Some Message' or jsError message
}
```
