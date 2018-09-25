# JavaScript 7
```JavaScript
function sum(a, b){
  return a + b;
}

sum('x', 'y');  //xy
//의도한 바는 number타입 2개를 넣어서 더하는 함수일 텐데 자바스크립트는 argument에 제한이 없어서 예측하기가 힘들다
```
이와 같이 자바스크립트는 동적 타입 언어이기 때문에 argument와 return 값에 대한 타입체크가 없다면 모든 형태가 가능하다

* typeof
  * 피연산자의 데이터 타입을 문자열로 반환한다

| typeof(확인할 포멧) |         result         |
|:-------------------:|:----------------------:|
|          ''         |         string         |
|          1          |         number         |
|         NaN         |         number         |
|         true        |         boolean        |
|          []         |         object         |
|          {}         |         object         |
|     new String()    |         object         |
|      new Date()     |         object         |
|   /test/directory   |         object         |
|    function (){}    |        function        |
|      undefined      |        undefined       |
|         null        |   object(설계적 결함)  |
|      undeclared     | undefined(설계적 결함) |

null을 제외한 원시 타입을 체크할 때는 typeof로 확인 가능하지만 그 외에 것들은 대부분 object이므로 확인하기 힘들다

* Object.prototype.toString
  * 객체를 나타내는 문자열을 반환한다

```javascript
Object.prototype.toString.call('');             // [object String]
Object.prototype.toString.call(new String());   // [object String]
Object.prototype.toString.call(1);              // [object Number]
Object.prototype.toString.call(new Number());   // [object Number]
Object.prototype.toString.call(NaN);            // [object Number]
Object.prototype.toString.call(Infinity);       // [object Number]
Object.prototype.toString.call(true);           // [object Boolean]
Object.prototype.toString.call(undefined);      // [object Undefined]
Object.prototype.toString.call();               // [object Undefined]
Object.prototype.toString.call(null);           // [object Null]
Object.prototype.toString.call([]);             // [object Array]
Object.prototype.toString.call({});             // [object Object]
Object.prototype.toString.call(new Date());     // [object Date]
Object.prototype.toString.call(Math);           // [object Math]
Object.prototype.toString.call(/test/i);        // [object RegExp]
Object.prototype.toString.call(function () {}); // [object Function]
Object.prototype.toString.call(document);       // [object HTMLDocument]
Object.prototype.toString.call(argument);       // [object Arguments]
Object.prototype.toString.call(undeclared);     // ReferenceError

function getType(target){
  return Object.prototype.toString.call(target).slice(8, -1);
}
//위의 성질을 이용하여 앞에 object 뒤에 있는 타입이 확인하고자 하는 데이터의 타입이다
//즉, '[object ' 이후부터 마지막 ']'이전까지의 글자가 그 데이터의 타입이므로 그것을 리턴해준다

function sum(a, b) {
  // a와 b가 number 타입인지 체크
  if (getType(a) !== 'Number' || getType(b) !== 'Number') {
    throw new TypeError('파라미터에 number 타입이 아닌 값이 할당되었습니다.');
  }
  return a + b;
}

console.log(sum(10, 20));   // 30
console.log(sum('10', 20)); // TypeError
//이를 이용하여 sum에 대한 함수에서 타입 체크를 해준다
```

* instanceof
  * Object.prototype.toString을 사용한다면 객체의 종류까지 식별은 가능하지만 객체의 상속 관계까지 체크는 할 수 없다

```JavaScript
// HTMLElement를 상속받은 모든 DOM 요소에 css 프로퍼티를 추가하고 값을 할당한다.
function css(elem, prop, val) {
  // type checking...
  elem.style[prop] = val;
}

css({}, 'color', 'red');
// TypeError: Cannot set property 'color' of undefined
//css 함수의 첫번째 argument는 반드시 HTMLElement를 상속받은 DOM요소를 전달해야 한다
//즉, HTMLElement를 상속받은 객체인 DOM요소인지 확인해야 한다
```

  * instanceof는 피연산자인 객체가 우항에 명시한 타입의 인스턴스인지 여부를 알려준다
  * 타입이란 constructor를 말하며 프로토타입 체인에 존재하는 모든 constructor를 검색하여 일치하는 constructor가 있다면 true를 반환한다

```JavaScript
function isElement(target) {
  return !!(target && target instanceof HTMLElement);
  // 또는 `nodeType`을 사용할 수도 있다.
  // return !!(target && target.nodeType === 1);
}

// HTMLElement를 상속받은 모든 DOM 요소에 css 프로퍼티를 추가하고 값을 할당한다.
function css(elem, prop, val) {
  // type checking
  if (!(isElement(elem) && getType(prop) === 'String' && getType(val) === 'String')) {
    throw new TypeError('매개변수의 타입이 맞지 않습니다.');
  }
  elem.style[prop] = val;
}
//instanceof를 사용하여 타겟이 HTMLElement에 상속받은 객체인지 확인하고 css 함수를 사용한다
```

# 유사배열
* 배열 체크를 위해서는 Array.isArray()를 사용한다
* 유사 배열 객체는 length 프로퍼티를 가지며, grguments, HTMLCollection, NodeList 등이 있다
* length를 이용하여 순회할 수 있고 call, apply 함수를 사용하여 배열의 메소드를 사용할 수 있다

```JavaScript
console.log(Array.isArray([]));    // true
console.log(Array.isArray({}));    // false
console.log(Array.isArray('123')); // false
//배열 객체 확인

const isArrayLike = function (collection) {
  // 배열 인덱스: 32bit 정수(2의 32제곱 - 1)
  // 유사 배열 인덱스: 자바스크립트로 표현할 수 있는 양의 정수(2의 53제곱 - 1)
  const MAX_ARRAY_INDEX = Math.pow(2, 53) - 1;
  // 빈문자열은 유사배열이다. undefined == null => true
  const length = collection == null ? undefined : collection.length;
  return typeof length === 'number' && length >= 0 && length <= MAX_ARRAY_INDEX;
  //유사 배열만 찾을 수 없는게 유사배열의 최대 맥시멈 값이 2의 53 -1 인거지 무조건 length가 2의 53 -1이 되는건 아니니까
  //배열과 유사배열 두가지를 찾는데 사용할 수 있지 이거만 가지고는 유사배열만 찾을 순 없다
};

console.log(isArrayLike([]));   //true
console.log(isArrayLike('abc'));   //true
console.log(isArrayLike(123));  //false
//유사 배열 확인
```
