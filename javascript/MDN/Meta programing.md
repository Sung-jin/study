### 메타 프로그래밍

* ECMAScript 2015 부터 속성 조회/할당/열거/함수 호출 등의 사용자 지정 동작을 가로채고 정의할 수 있도록 Proxy/Reflect 개체에 대한 지원을 얻을 수 있다.
* js 는 Proxy/Reflect 개체의 도움으로 메타 수준에서 프로그래밍을 할 수 있다.

#### Proxies

* ECMAScript 6 에서 소개되었다.
* 특정 작업을 가로막는 것과 상숑자 정의 행위를 시행하는 것을 허용한다.
  * 속성 접근, 할당, 순회, 열거, 함수 호출 등의 기본적인 동작의 새로운 행동을 정의할 때 사용한다.
* 용어
  * handler - trap 들을 가지고 있는 placeholder 객체
  * traps - 프로퍼티에 접근할 수 있는 메소드
  * target - proxy 가 가상화하는 실제 객체. proxy 를 위한 backend 저장소로 사용된다.
* new Proxy(target, handler)
  * target - proxy 와 함께 감싸진 target 객체 
  * handler - 프로퍼티들이 function 인 객체이며, 동작이 수행될 때 handler 는 proxy 의 행동을 정의한다.
* 메서드
  * Proxy.revocable() : 폐기할 수 있는 Proxy 객체를 생성

```js
var handler = {
    get: function(target, name) {
        return name in target ? target[name] : 42;
    }
};

var p = new Proxy({}, handler);
p.a = 1;
console.log(p.a, p.b); // 1, 42

var obj = {};
obj.a = 1;
console.log(obj.a, obj.b); // 1 undefined
```

#### 예제

```js
// get handler
var getHandler = {
    get: function(target, name) {
        return name in target ?
                target[name] : 37;
    }
    // 키에 해당되는 값이 존재한다면 해당 값을, 아니면 37 을 리턴하는 핸들러
}
var gp = new Proxy({}, getHandler);
p.a = 1;

console.log(p.a); // 1
console.log(p.b); // 37

// validation
var validator = {
    set: function(obj, prop, value) {
        if (prop === 'age') {
            if (!Number.isInteger(value)) {
                throw new TypeError('The age is not an integer');
            }
            if (value > 200) {
                throw new RangeError('The age seems invalid');
            }
        }
        
        obj[prop] = value;
    }
};

var person = new Proxy({}, validator);

person.age = 100; // ok
person.age = 'ten'; // throw TypeError
person.age = 300; // throw RangeError

// 생성자 확장
function extend(sub, base) {
    var descriptor = Object.getOwnPropertyDescriptor(
        base.prototype, 'constructor'
    );
    base.prototype = Object.create(sup.prototype);
    var handler = {
        construct: function(target, args) {
            var obj = Object.create(base.prototype);
            this.apply(taret, obj, args);
            return obj;
        },
      apply: function(target, that, args) {
            sup.apply(that, args);
            base.apply(that, args);
      }
    };
    var proxy = new Proxy(base, handler);
    descriptor.value = proxy;
    Object.defineProperty(base.prototype, 'constructor', descriptor);
    return proxy;
}

var Person = function(name) {
    this.name = name;
}
var Boy = extend(Person, function(name, age) {
    this.age = age;
});

Boy.prototype.sex = 'M';

var Perter = new Boy('Peter', 13);
console.log(Peter.sex);     // M
console.log(Peter.name);    // Peter
console.log(Peter.age);     // 13

// DOM nodes 조작
let view = new Proxy({
  selected: null
}, {
    set: function(obj, prop, newVal) {
        let oldVal = obj[prop];
        
        if (prop === 'selected') {
            if (oldVal) oldVal.setAttribute('some-selected', false);
            if (newVal) newVal.setAttribute('some-selected', true);
        }
        obj[prop] = newVal;
    }
});

let i1 = view.selected = document.getElementById('some-item-1');
console.log(li.getAttribute('some-selected')); // true

let i2 = view.selected = document.getElementById('some-item-2');
console.log(i1.getAttribute('aria-selected')); // false
console.log(i2.getAttribute('aria-selected')); // true
```

