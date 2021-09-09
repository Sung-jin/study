### 생성자 함수 사용하기

* 다음과 같은 방법으로 객체를 생성할 수 있다.
  1. 생성자 함수를 작성하여 객체 타입을 정의
     * 객체 타입 이름의 첫 글자는 대문자를 사용하는 것이 관례이다.
  2. new 를 이용하여 객체 인스턴스를 생성
  
```js
function Car(make, model, year, owner) {
    // primitive 뿐 아니라 모든 객체를 속성으로 사용할 수 있다.
    this.make = make;
    this.model = model;
    this.yaer = year;
    this.owner = owner;
    // 객체의 속성에 할당하기 위해서는 this 를 통해 접근한다.
}

function Person(name, age, sex) {
    this.name = name;
    this.age = age;
    this.sex = sex;
}

var who = new Person("owen", 10, "male")
var myCar = new Car("foo", "bar", "fuz", who);
```

#### Object.create

* 객체 생성자 함수가 없어도 객체를 생성할 수 있다.

```js
var Food = {
    type: "noddle",
    displayType: function() {
        console.log(this.type);
    }
}

var ramen = Object.create(Food);
remen.displayType(); // noddle
```

### 상속

* js 에서 모든 객체들은 최소한 하나의 다른 객체로부터 상속을 받는다.
* 상속을 제공하는 객체를 프로토타입이라고 한다.
    * 상속되는 속성들은 prototype 이라는 생성자 객체에서 찾을 수 있다.

#### 객체의 프로퍼티 정의

* prototype 프로퍼티를 사용하여 미리 정의된 객체 타입에 속성을 추가할 수 있다.
    * 이렇게 추가된 프로퍼티는 해당 객체 타입의 모든 인스턴스가 가지게 된다.

```js
function SomeObject() {}
function someFunc() { console.log('execute!') }

SomeObject.prototype.someProperty = null;
SomeObject.prototype.someFunc = someFunc; // 메소드도 추가할 수 있다.

var obj = new SomeObject();
Object.getPrototypeOf(obj).hasOwnProperty('someProperty'); // true
```

### 객체 참조를 위한 this

* 메소드 내부에서 this 키워드를 사용하면 해당 메서드를 포함한 객체를 가리킨다.

```
function validate(obj, lowval, hival) {
  if ((obj.value < lowval) || (obj.value > hival))
    alert("Invalid Value!");
}

<input type="text" name="age" size="3" onChange="validate(this, 18, 99)">
// 위 onChange 에서 this 는 input 자신의 요소이다.
```

### getters, setters

* getter - 특정 속성의 값을 받아 오기 위한 메서드
* setter - 특정 속성의 값을 설정하기 위한 메서드
* 객체를 생성 한 이후 `Object.defineProperties` 메서드를 사용해서 getter/setter 를 나중에 추가할 수 있다.

```js
var o = {
    a: 7,
    get b() { return this.a + 1; },
    // getter 는 인자가 없어야 한다.
    set c(x) { this.a = x }
    // setter 는 하나의 인자만 있어야 한다.
}
// getter, setter 를 정의할 때 get/set 접두사만 추가하면 된다.
console.log(o.a); // 7
console.log(o.b); // 7
o.c = 20;
console.log(o.a); // 20

------------------------------------

var o2 = { a: 0 };
Object.defineProperties(o2, {
    'b': { get: function () { a } },
    'c': { set: function (x) { this.a = x } }
});
// 객체를 정의 한 이후 defineProperties 를 통해 나중에 getter/setter 를 추가할 수 있다.
```

### 프로퍼티 삭제

* `delete` 연산자를 통해 객체의 특정 프로퍼티를 제거할 수 있다.
* var 키워드로 선안하지 않은 전역 변수도 `delete` 연산자로 삭제할 수 있다.

```js
var o = { foo: 'bar' };
console.log(o.foo); // bar
delete o.foo;
console.log(o.foo); // undefined

g = 'fuz';
console.log(g); // fuz
delete g;
console.log(g); // Uncaught ReferenceError: g is not defined
```

### 객체간의 비교

* js 에서 객체들은 레퍼런스 타입이다.
* 즉, 두 객체의 모든 속성 값들이 동일하더라도 두 객체는 동일하다고 비교 (equal) 될 수 없다.

```js
var o1 = { foo: 'fuz' };
var o2 = { foo: 'fuz' };

console.log(o1 == o2); // false
console.log(o1 === o2); // false
```
