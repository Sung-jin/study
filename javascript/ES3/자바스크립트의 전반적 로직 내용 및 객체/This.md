# This

![this1](../../images/this1.png)

* 자바스크립트의 함수는 호출될 때, 매개변수 외에 arguments 객체와 this를 암묵적으로 전달 받는다
* **자바스크립트의 this는 java와 같은 익숙한 언어의 개념과 다르다**
* java에서의 this는 인스턴스 자신인 self를 가리키는 참조변수이다
* 주로 매개변수와 객체 자신이 가지고 있는 멤버변수명이 같을 경우 이를 구분하기 위해 사용된다
* **자바스크립트의 this는 해당 함수 호출 방식에 따라 바인딩되는 객체가 달라진다**

```Java
public Class foo {
  private String bar;

  public foo(String bar){
    this.bar = bar;
    //여기에서 this는 자기자신인 foo를 가르킨다
  }
}
```

--------------------------------------------------------------------------------

##  함수 호출 방식과 this 바인딩

* 자바스크립트의 함수 호출 방식에 의해 this에 바인딩 될 객체가 동적으로 결정된다
* 즉, 함수를 선언할 때가 아닌 함수를 호출할 때 함수가 어떻게 호출되었는지에 따라 this에 바인딩할 객체가 동적으로 결정된다
  * 함수의 상위 스코프를 결정하는 방식인 렉시컬 스코프는 함수를 선언할 때 결정된다

```javascript
var foo = function () {
  console.dir(this);
};

// 1. 함수 호출 패턴(Function Invocation Pattern)
foo(); // window
// window.foo();

// 2. 메소드 호출 패턴(Method Invocation Pattern)
var obj = { foo: foo };
obj.foo(); // obj

// 3. 생성자 호출 패턴(Constructor Invocation Pattern)
var instance = new foo(); // instance

// 4. apply 호출 패턴(Apply Invocation Pattern)
var bar = { name: 'bar' };
foo.call(bar);   // bar
foo.apply(bar);  // bar
foo.bind(bar)(); // bar
```

--------------------------------------------------------------------------------

##  함수 호출 패턴

* 전역객체는 모든 객체의 유일한 최상위 객체를 의미하며 일반적으로 Browser-side에서는 window, Server-side에서는 global 객체를 의미한다
* 전역객체는 전역 스코프를 갖는 전역변수를 프로퍼티로 소유한다
* 글로벌 영역에 선언한 함수는 전역객체의 프로퍼티로 접근할 수 있는 전역 변수의 메소드이다

```JavaScript
var foo = 'Global'
console.log(foo;)
console.log(window.foo);
console.log(foo === window.foo);  //true
```

* this는 기본적으로 전역객체에 바인딩된다
* 전역함수 뿐 아니라 내부함수의 경우도 this는 외부함수가 아닌 전역객체에 바인딩된다
* 또한 메소드의 내부함수일 경우에도 this는 전역객체에 바인딩된다
* 콜백함수의 경우에도 this는 전역객체에 바인딩된다
* 내부함수는 일반 함수, 메소드, 콜백함수 어디에서 선언되었든 this는 전역객체를 바인딩한다

```JavaScript
function foo() {
  console.log("foo's this: ",  this);  // window
  function bar() {
    console.log("bar's this: ", this); // window
  }
  bar();
}
foo();

var value = 1;

var obj = {
  value: 100,
  foo: function() {
    console.log("foo's this: ",  this);  // obj
    console.log("foo's this.value: ",  this.value); // 100
    function bar() {
      console.log("bar's this: ",  this); // window
      console.log("bar's this.value: ", this.value); // 1
    }
    bar();
  },
  foobar: funcion(){
    setTimeout(function() {
      console.log("callback's this: ",  this);  // window
      console.log("callback's this.value: ",  this.value); // 1
    }, 100);
  }
};

obj.foo();
obj.foobar();
```

* 내부함수의 this가 전역객체를 참조하는 것을 회피하는 방법도 있다

```JavaScript
var value = 1;

var obj = {
  value: 100,
  foo: function() {
    var that = this;  // Workaround : this === obj

    console.log("foo's this: ",  this);  // obj
    console.log("foo's this.value: ",  this.value); // 100
    function bar() {
      console.log("bar's this: ",  this); // window
      console.log("bar's this.value: ", this.value); // 1

      console.log("bar's that: ",  that); // obj
      console.log("bar's that.value: ", that.value); // 100
    }
    bar();
    //that에 this를 바인딩
    function foobar(a, b) {
      console.log("bar's this: ",  this); // obj
      console.log("bar's this.value: ", this.value); // 100
      console.log("bar's arguments: ", arguments);
    }
    foobar.apply(obj, [1, 2]);
    foobar.call(obj, 1, 2);
    foobar.bind(obj)(1, 2);
  }
  //this를 명시적으로 바인딩할 수 있는 apply, call, bind 메소드 사용
};

obj.foo();
```

--------------------------------------------------------------------------------

##  메소드 호출 패턴

* 함수가 객체의 프로퍼티 값이면 메소드 호출 패턴으로 호출된다
* 메소드 내부의 this는 해당 메소드를 소유한 객체인 해당 메소드를 호출한 객체에 바인딩 된다
* 프로토타입 객체도 메소드를 가질 수 있다
* 프로토타입 객체 메소드 내부에서 사용된 this도 일반 메소드 방식과 마찬가지로 해당 메소드를 호출한 객체에 바인딩된다

```JavaScript
var obj1 = {
  name: 'Lee',
  sayName: function() {
    console.log(this.name);
  }
}

var obj2 = {
  name: 'Kim'
}

obj2.sayName = obj1.sayName;

obj1.sayName();
obj2.sayName();
//obj1의 sayName()의 this는 obj1이다
//obj2의 sayName()의 this는 obj2이다
//즉, 각각의 name에 해당되는 Lee와 Kim을 불러와 로그에 찍는다

function Person(name) {
  this.name = name;
}

Person.prototype.getName = function() {
  return this.name;
}

var me = new Person('Lee');
console.log(me.getName());

Person.prototype.name = 'Kim';
console.log(Person.prototype.getName());
//me는 Person()에 name을 Lee로 생성한 객체이다
//Person의 프로토타입의 name에 Kim을 넣었으므로 Person의 프로토타입의 getName()을 하면 Kim을 호출한다
//me 객체의 getName()은 me의 name에 Lee를 할당하였으므로 Lee가 로그에 찍힌다
```

--------------------------------------------------------------------------------

##  생성자 호출 패턴

* 자바스크립트의 생성자 함수는 객체를 생성하는 역할을 한다
* 자바와 같은 객체지향 언어의 생성자 함수와는 다르게 형식이 정해져 있는 것이 아닌, 기존 함수에 new 연산자를 붙여서 호출하면 해당 함수는 생성자 함수로 동작하게 된다
* 즉, 생성자 함수가 아닌 일반 함수에 new 연산자를 붙여 호출하면 생성자 함수처럼 동작할 수 있다
* 혼란을 방지하고자 생성자 함수명은 첫문자를 대문자로 기술하여 혼란을 방지하려는 노력을 한다

##  생성자 함수 동작 방식

* new 연산자와 생성자 함수를 호출
  1.  빈 객체 생성 및 this 바인딩
    * 생성자 함수의 코드가 실행되기 전 빈 객체가 생성된다
    * 빈 객체가 생성자 함수가 새로 생성하는 객체이다
    * 생성자 함수 내에서 사용되는 this는 이 빈 객체를 가리킨다
    * 생성된 빈 객체는 생성자 함수의 prototype 프로퍼티가 가리키는 객체를 자신의 프로토타입 객체로 설정한다

  2.  this를 통한 프로퍼티 생성
    * 생성된 빈 객체에 this를 사용하여 동적으로 프로퍼티나 메소드를 생성할 수 있다
    * this는 새로 생성된 객체를 가리키므로 this를 통한 생성한 프로퍼티와 메소드는 새로 생성된 객체에 추가된다

  3.  생성된 객체 반환
    * 반환문이 없는 경우, this에 바인딩된 새로 생성한 객체가 반환된다
    * 명시적으로 this를 반환하여도 this에 바인딩된 새로 생성한 객체가 반환된다
    * 반환문이 this가 아닌 다른 객체를 명시적으로 반환하는 경우, this가 아닌 해당 객체가 반환된다
    * 이때 this를 반환하지 않은 함수는 생성자 함수로서의 역할을 수행하지 못한다
    * 즉, 생성자 함수는 반환문을 명시적으로 사용하지 않는다

```JavaScript
function Person(name) {
  // 생성자 함수 코드 실행 전 -------- 1
  this.name = name;     // --------- 2
  // 생성된 함수 반환  -------------- 3
}

var me = new Person('Lee');
console.log(me.name);
```

##  객체 리터럴 방식과 생성자 함수 방식의 차이

* 객체 리터럴 방식과 생성자 함수 방식의 차이는 프로토타입 객체([[Prototype]])에 있다
  * 객체 리터럴 방식의 경우, 생성된 객체의 프로토타입 객체는 Object.prototype 이다
  * 생성자 함수 방식의 경우, 생성된 객체의 프로토타입 객체는 Person.prototype 이다

```JavaScript
// 객체 리터럴 방식
var foo = {
  name: 'foo',
  gender: 'male'
}

console.dir(foo);

// 생성자 함수 방식
function Person(name, gender) {
  this.name = name;
  this.gender = gender;
}

var me  = new Person('Kim', 'male');
console.dir(me);
```

##  생성자 함수에 new 연산자를 붙이지 않고 호출할 경우

* 일반함수와 생성자 함수에 특별한 형식적 차이는 없으며 함수에 new 연산자를 붙여서 호출하면 해당 함수는 생성자 함수로 동작한다
* 객체 생성 목적으로 만든 생성자 함수를 new 없이 호출하거나 일반함수에 new를 붙여 호출하면 오류가 발생할 수 있다
* 일반 함수와 생성자 함수 호출 시 this 바인딩 방식이 다르기 때문
* 일반 함수의 this는 전역객체에 바인딩, new 연산자와 함께 생성자 함수를 호출하면 this는 생성자 함수가 암묵적으로 생성한 빈 객체에 바인딩된다

```JavaScript
function Person(name) {
  // new없이 호출하는 경우, 전역객체에 name 프로퍼티를 추가
  this.name = name;
};

// 일반 함수로서 호출되었기 때문에 객체를 암묵적으로 생성하여 반환하지 않는다.
// 일반 함수의 this는 전역객체를 가리킨다.
var me = Person('Lee');
//new와 함께 생성자 함수를 호출해야 암묵적으로 반환하던 this가 있는데, new가 없으므로 undefined를 반환

console.log(me); // undefined
console.log(window.name); // Lee
```

* 일반 함수와 생성자 함수에 특별한 형식적 차이가 없기 때문에 일반적으로 생성자 함수는 첫문자를 대문자로 기술하여 혼란을 방지하고자 한다
* 그래도 실수는 발생할 수 있다
* 이러한 위험성을 회피하기 위해 Scope-Safe Constructor라는 패턴이 있다
  * 대부분의 라이브러리에서 광범위하게 사용된다
* 빌트인 생성자(Object, Regex, Array 등)는 new 연산자와 함께 호출되었는지를 확인한 후 적절한 값을 반환한다

```JavaScript
// Scope-Safe Constructor Pattern
function A(arg) {
  // 생성자 함수가 new 연산자와 함께 호출되면 함수의 선두에서 빈객체를 생성하고 this에 바인딩한다.

  /*
  this가 호출된 함수(arguments.callee, 본 예제의 경우 A)의 인스턴스가 아니면 new 연산자를 사용하지 않은 것이므로 이 경우 new와 함께 생성자 함수를 호출하여 인스턴스를 반환한다.
  arguments.callee는 호출된 함수의 이름을 나타낸다. 이 예제의 경우 A로 표기하여도 문제없이 동작하지만 특정함수의 이름과 의존성을 없애기 위해서 arguments.callee를 사용하는 것이 좋다.
  */
  if (!(this instanceof arguments.callee)) {
    return new arguments.callee(arg);
  }

  // 프로퍼티 생성과 값의 할당
  this.value = arg ? arg : 0;
}

var a = new A(100);
var b = A(10);

console.log(a.value);
console.log(b.value);
//쉽게 말하자면 new 키워드 없이 생성하면 함수 내부적으로 확인한 후, new 키워드를 추가해서 다시 호출하는 방식이다
```

--------------------------------------------------------------------------------

##  apply 호출 패턴

* 자바스크립트 엔진의 암묵적 this 바인딩 이외에 this를 특정 객체에 명시적으로 바인딩하는 방법도 제공된다
* Function.protorype.apply, Function.protorype.call 메소드를 통해 가능하다
  * 이 2개 모두 Function.protorype 객체의 메소드이다
* apply() 메소드를 호출하는 주체는 함수이며, apply() 메소드는 this를 특정 객체에 바인딩할 뿐 본질적인 기능은 함수 호출이다

```JavaScript
var Person = function (name) {
  this.name = name;
};

var foo = {};

// apply 메소드는 생성자함수 Person을 호출한다. 이때 this에 객체 foo를 바인딩한다.
Person.apply(foo, ['name']);
//foo: 함수 내부의 this에 바인딩할 객체
//['name']: 함수에 전달할 argument의 배열
//['name']에 들어갈 매개변수는 argument의 배열 형태이다

console.log(foo); // { name: 'name' }
```

* apply() 메소드의 대표적인 용도는 arguments 객체와 같은 유사 배열 객체에 배열 메소드를 사용하는 경우이다
* arguments 객체는 배열이 아니기 때문에 slice() 같은 배열의 메소드를 사용할 수 없으나 apply()를 이용하면 가능하다

```JavaScript
function convertArgsToArray() {
  console.log(arguments);

  // arguments 객체를 배열로 변환
  // slice: 배열의 특정 부분에 대한 복사본을 생성한다.
  var arr = Array.prototype.slice.apply(arguments); // arguments.slice
  // var arr = [].slice.apply(arguments);

  console.log(arr);
  return arr;
}

convertArgsToArray(1, 2, 3);
//Array.prototype.slice.apply(arguments) -> Array.prototype.slice() 메소드를 호출하는데, this는 arguments 객체로 바인딩 하라
//즉, arguments.slice()와 같은 형태로 호출하라는 것이된다
```

* call() 메소드의 경우 apply()와 기능은 같지만 apply()의 두번째 인자에서 배열 형태로 넘긴 것을 각각 하나의 인자로 넘긴다
* apply()와 call() 메소드는 콜백 함수의 this를 위해서 사용되기도 한다

```JavaScript
function Person(name) {
  this.name = name;
}

Person.prototype.doSomething = function(callback) {
  if(typeof callback == 'function') {
    // --------- 1
    callback();
  }
};

function foo() {
  console.log(this.name); // --------- 2
}

var p = new Person('Lee');
p.doSomething(foo);  // undefined
```

* 1의 시점에서 this는 Person 객체이다
* 2의 시점에서 this는 전역 객체 window를 가리킨다
* 콜백함수를 호출하는 외부 함수 내부의 this와 콜백함수 내부의 this가 상이하기 때문에 문맥상 문제가 발생한다
* 즉, 콜백함수 내부의 this를 콜백함수 호출하는 함수 내부의 this와 일치시켜 주어야 하는 번거로움이 발생한다

```JavaScript
Person.prototype.doSomething = function (callback) {
  if (typeof callback == 'function') {
    callback.call(this);
    //call(this) 추가해줘서 callback의 this를 콜백함수를 호출하는 외부 함수의 this로 묶어주는 역할을 추가해준다
  }
};
```

* ES5에서 추가된 Function.prototype.bind를 사용하는 방법도 가능하다
* Function.prototype.bind는 함수에 인자로 전달한 this가 바인딩된 새로운 함수를 리턴한다
* 즉, bind는 apply와 call 메소드와 같이 함수를 실행하지 않기 때문에 명시적으로 함수를 호출할 필요가 없다

```JavaScript
Person.prototype.doSomething = function (callback) {
  if (typeof callback == 'function') {
    // callback.call(this);
    // this가 바인딩된 새로운 함수를 호출
    callback.bind(this)();
  }
};
```
