# 객체지향 프로그래밍

* Java, C++, Python, PHP 등은 객체지향 프로그래밍을 지원한다
* '객체지향'의 개념은 명확한 정의가 없는 것이 특징이다
* 객체지향 프로그래밍은 실세계에 존재하고 인지하고 있는 객체(Object)를 소프트웨어 세계에서 표현하기 위해 객체의 핵심적인 개념 또는 기능만을 추출하는 추상화를 통해 모델링하려는 프로그래밍 패러다임을 말한다
* 즉, 주변의 실세계에서 사물을 인지하는 방식을 프로그래밍에 접목하려는 사상을 의미한다
* 절차지향은 함수들의 집합 혹은 단순한 컴퓨터의 명령어들의 목록을 의미한다

#  클래스 기반 vs 프로토타입 기반

## 클래스 기반 언어

* Java, C++, Python, PHP 등은 클래스로 객체의 자료구조와 기능을 정의하고 생성자를 통해 인스턴스를 생성한다
* 클래스란 같은 종류의 집단에 속하는 속성과 행위를 정의한 것으로 객체지향 프로그램의 기본적인 사용자 정의 데이터형이라고 할 수 있다
* 즉, 클래스는 객체 생성에 사용되는 패턴일 뿐이며 new 연산자를 통한 인스턴스화 과정이 필요하다
* 모든 인스턴스는 오직 클래스에서 정의된 범위 내에서만 작동하며 런타임에 그 구조를 변경할 수 없다.
* 이러한 특성은 정확성, 안정성, 예측성 측면에서 프로토타입 기반 언어보다 조금 더 나은 결과를 보장한다

## 프로토타입 기반 언어

* 자바스크립트는 멀티 패러다임 언어이다
  * 명령형
  * 함수형
  * 프로토타입 기반 객체지향 언어
* 클래스가 없어서 객체지향이 아니라고 생각할 수 있지만, 프로토타입 기반의 객체지향 언어이다
* 자바스크립트에서 클래스가 없으며, 별도의 객체 생성 방법이 존재한다
  * 객체 리터럴
  * Object() 생성자 함수
  * 생성자 함수

```JavaScript
// 객체 리터럴
var obj1 = {};
obj1.name = 'Lee';

// Object() 생성자 함수
var obj2 = new Object();
obj2.name = 'Lee';

// 생성자 함수
function F() {}
var obj3 = new F();
obj3.name = 'Lee';
```

* 자바스크립트는 이미 생성된 인스턴스의 자료구조와 기능을 동적으로 변경할 수 있다는 특징이 있다
* 객체지향의 상속, 캡슐화 등의 개념은 프로토타입 체인과 클로저 등으로 구현할 수 있다
* 자바스크립트에서는 클래스, 생성자, 메소드 모두 함수로 구현이 가능하다
* ES6에서 클래스가 도입되었다
  * ES6의 클래스는 프로토타입 기반 객체지향 프로그래밍보다 클래스 기반 언어에 익숙한 프로그래머가 빠르게 학습할 수 있는 단순한 문법을 제시한다
  * 하지만 ES6에서 클래스가 새로운 객체지향 모델을 제공하는 것이 아니며 클래스도 함수이며 기존 프로토타입 기반 패턴의 Syntacitc sugar이다

# 생성자 함수와 인스터스의 생성

* 자바스크립트에서 생성자 함수와 new 연산자를 통해 인스턴스를 생성할 수 있다

```JavaScript
// 생성자 함수(Constructor)
function Person(name) {
  // 프로퍼티
  this.name = name;

  // 메소드
  this.setName = function (name) {
    this.name = name;
  };

  // 메소드
  this.getName = function () {
    return this.name;
  };
}

// 인스턴스의 생성
var me = new Person('Lee');
console.log(me.getName()); // Lee

// 메소드 호출
me.setName('Kim');
console.log(me.getName()); // Kim

var user1 = new Person('Kim');
var user2 = new Person('Oh');

console.log(user1.getName()); // Kim
console.log(user2.getName()); // Oh
```

* 위와 같이 함수로 클래스를 정의하고 new 키워드와 함께 생성자로써 사용할 수 있다
* 또한, 정의된 하나의 생성자 함수로 여러개의 인스턴스를 생성할 수 있으며 각각의 인스턴스에 메소드와 프로퍼티가 생성되어 각자 소유하게 된다
* 하지만, 이러한 방식의 인스턴스 생성은 메모리 낭비이며 인스턴스가 많거나 메소드가 크거나 많다면 무시할 수 없다

# 프로퍼타입 체인과 메소드 정의

* 위의 생성자 함수를 통한 인스턴스 생성에 따른 메모리 낭비를 해결하는 방법이다
* 모든 객체는 프로토타입이라는 다른 객체를 가리키는 내부 링크를 가지고 있다
* 즉, 프로토타입을 통해 직접 객체를 연결할 수 있는데 이를 프로토타입 체인이라 한다
* 프로토타입을 이용하여 생성자 함수 내부의 메소드를 생성자 함수의 prototype 프로퍼티가 가리키는 프로토타입 객체로 이동시키면 생성자 함수에 의해 생성된 모든 인스턴스는 프로토타입 체인을 통해 프로토타입 객체의 메소드를 참조할 수 있다

```JavaScript
function Person(name) {
  this.name = name;
}

// 프로토타입 객체에 메소드 정의
Person.prototype.setName = function (name) {
  this.name = name;
};

// 프로토타입 객체에 메소드 정의
Person.prototype.getName = function () {
  return this.name;
};

var me  = new Person('Lee');
var you = new Person('Kim');
var him = new Person('choi');

console.log(Person.prototype);
// Person { setName: [Function], getName: [Function] }

console.log(me);  // Person { name: 'Lee' }
console.log(you); // Person { name: 'Kim' }
console.log(him); // Person { name: 'choi' }
```

[![prototype](https://poiemaweb.com/js-object-oriented-programming#4-%ED%94%84%EB%A1%9C%ED%86%A0%ED%83%80%EC%9E%85-%EC%B2%B4%EC%9D%B8%EA%B3%BC-%EB%A9%94%EC%86%8C%EB%93%9C%EC%9D%98-%EC%A0%95%EC%9D%98)](../../images/prototype.png)

* 위의 사진처럼 Person 생성자 함수의 prototype 프로퍼티에 메소드를 정의한 후, 생성 된 객체는 프로토타입 체인에 의해 메소드를 참조할 수 있게 된다
* 즉, 프로토타입 객체는 상속할 것들의 저장되는 장소이다
* 위의 예시를 더글라스 크락포드가 제안한 프로토타입 객체에 메소드를 추가하는 방식으로 만들면 아래와 같다

```JavaScript
/**
 * 모든 생성자 함수의 프로토타입은 Function.prototype이다. 따라서 모든 생성자 함수는 Function.prototype.method()에 접근할 수 있다.
 * @method Function.prototype.method
 * @param ({string}) (name) - (메소드 이름)
 * @param ({function}) (func) - (추가할 메소드 본체)
 */
Function.prototype.method = function (name, func) {
  // 생성자함수의 프로토타입에 동일한 이름의 메소드가 없으면 생성자함수의 프로토타입에 메소드를 추가
  // this: 생성자함수
  if (!this.prototype[name]) {
    this.prototype[name] = func;
  }
};

/**
 * 생성자 함수
 */
function Person(name) {
  this.name = name;
}

/**
 * 생성자함수 Person의 프로토타입에 메소드 setName을 추가
 */
Person.method('setName', function (name) {
  this.name = name;
});

/**
 * 생성자함수 Person의 프로토타입에 메소드 getName을 추가
 */
Person.method('getName', function () {
  return this.name;
});

var me  = new Person('Lee');
var you = new Person('Kim');
var him = new Person('choi');

console.log(Person.prototype);
// Person { setName: [Function], getName: [Function] }

console.log(me);  // Person { name: 'Lee' }
console.log(you); // Person { name: 'Kim' }
console.log(him); // Person { name: 'choi' }
```

# 상속

* 클래스 기반 언어에서 상속 또는 확장은 코드 재사용의 관점에서 매우 유용하다
* 새로 정의할 클래스가 기존의 클래스와 매우 유사하다면 상속을 통해 다른 점만 구현하면 된다
* 클래스 기반 언어에서 객체는 클래스의 인스턴스이며 클래스는 다른 클래스로 상속될 수 있다
* 자바스크립트는 기본적으로 프로토타입을 통해 상속을 구현한다
* 즉, 프로토타입을 통해 **객체가 다른 객체로 직접 상속** 된다는 의미이다
* 자바스크립트의 상속 구현 방식은 두가지로 구분할 수 있다
  * 의사 클래스 패턴 상속
  * 프로토타입 패턴 상속

##  의사 클래스 패턴 상속

* 의사 클래스 패턴은 자식 생성자 함수의 prototype 프로퍼티를 부모 생성자 함수의 인스턴스로 교체하여 상속을 구현하는 방법이다
* 부모와 자식 모두 생성자 함수를 정의하여야 한다

```JavaScript
// 부모 생성자 함수
var Parent = (function () {
  // Constructor
  function Parent(name) {
    this.name = name;
  }

  // method
  Parent.prototype.sayHi = function () {
    console.log('Hi! ' + this.name);
  };

  // return constructor
  return Parent;
}());

// 자식 생성자 함수
var Child = (function () {
  // Constructor
  function Child(name) {
    this.name = name;
  }

  // 자식 생성자 함수의 프로토타입 객체를 부모 생성자 함수의 인스턴스로 교체.
  Child.prototype = new Parent(); // ②

  // 메소드 오버라이드
  Child.prototype.sayHi = function () {
    console.log('안녕하세요! ' + this.name);
  };

  // sayBye 메소드는 Parent 생성자함수의 인스턴스에 위치된다
  Child.prototype.sayBye = function () {
    console.log('안녕히가세요! ' + this.name);
  };

  // return constructor
  return Child;
}());

var child = new Child('child'); // ①
console.log(child);  // Parent { name: 'child' }

console.log(Child.prototype); // Parent { name: undefined, sayHi: [Function], sayBye: [Function] }

child.sayHi();  // 안녕하세요! child
child.sayBye(); // 안녕히가세요! child

console.log(child instanceof Parent); // true
console.log(child instanceof Child);  // true
```

* ①에서 Child 생성자 함수가 생성한 인스턴스의 프로토타입 객체는 Parent 생성자 함수로 생성한 ②이다
* Parent 생성자 함수가 생성한 인스턴스의 프로토타입 객체는 Parent.prototype이다
* child는 프로토타입 체인에 의해 Parent 생성자 함수가 생성한 인스턴스와 Parent.prototype의 모든 프로퍼티에 접근할 수 있게 되었다
* 의사 클래스 패턴 상속이지만, 내부적으로 프로토타입을 사용하는 것은 변함이 없다
* 의사 클래스 패턴으로 구현을 한다면 구동상에 문제는 없지만 아래의 문제를 가지게 된다
  * new 연산자를 통해 인스턴스를 생성
    * 이는 자바스크립트 프로토타입 본질에 모순된다
    * 프로토타입 본성에 맞게 객체에서 다른 객체로 직접 상속하는 방법을 갖는 대신 생성자 함수와 new 연산자를 통해 객체를 생성하는 불필요한 간접적인 단계가 있다
    * 생성자 함수의 사용은 new 연산자를 포함하는 것을 잊게 되면 this는 새로운 객체와 바인딩되지 않고 전역객체에 바인딩된다
  * 생성자 링크의 파괴
    * child 객체의 프로토타입 객체는 Parent 생성자 함수가 생성한 new Parent() 객체이다
    * 프로토타입 객체는 내부 프로퍼티로 constructor를 가지며 이는 생성자 함수를 가리킨다
    * 의사 클래스 패턴 상속은 프로토타입 객체를 인스턴스로 교체하는 과정에서 constructor의 연결이 깨진다
    * child 객체를 생성한 것은 Child 생성자 함수이지만 child.constructor의 출력은 Parent 생성자 함수를 나타낸다
    * 이는 child 객체의 프로토타입 객체인 new Parent() 객체는 constructor가 없기 때문에 프로토타입 체인에 의해 Parent.prototype의 constructor를 참조했기 때문이다
  * 객체 리터럴
    * 의사 클래스 패턴 상속은 기본적으로 생성자 함수를 사용하기 때문에 객체리터럴 패턴으로 생성한 객체의 상속에는 적합하지 않다
    * 이는 객체리터럴 패턴으로 생성한 객체의 생성자 함수는 Object()이고 이를 변경할 방법이 없기 때문이다

##  프로토타입 패턴 상속

* 프로토타입 패턴 상속은 Object.create 함수를 사용하여 객체에서 다른 객체로 직접 상속을 구현하는 방식이다
* 프로토타입 패턴 상속은 의사 클래스 패턴의 단점인 new 연산자로 인한 생성자 링크 파괴가 없어 객체리터럴에도 사용할 수 있다

```JavaScript
//생성자 함수를 사용한 프로토타입 패턴 상속
// 부모 생성자 함수
var Parent = (function () {
  // Constructor
  function Parent(name) {
    this.name = name;
  }

  // method
  Parent.prototype.sayHi = function () {
    console.log('Hi! ' + this.name);
  };

  // return constructor
  return Parent;
}());

// create 함수의 인수는 프로토타입이다.
var child = Object.create(Parent.prototype);
child.name = 'child';

child.sayHi();  // Hi! child

console.log(child instanceof Parent); // true


//객체리터럴 패턴으로 생성한 객체에도 프로토타입 패턴 상속이 가능하다
var parent = {
  name: 'parent',
  sayHi: function() {
    console.log('Hi! ' + this.name);
  }
};

// create 함수의 인자는 객체이다.
var child2 = Object.create(parent);
child.name = 'child';

// var child = Object.create(parent, {name: {value: 'child'}});

parent.sayHi(); // Hi! parent
child2.sayHi();  // Hi! child

console.log(parent.isPrototypeOf(child2)); // true
````

* Object.create 함수는 매개변수에 프로토타입으로 설정할 객체 또는 인스턴스를 전달하고 이를 상속하는 새로운 객체를 생성한다
* Object.create 함수는 표준에 늦게 추가되어 IE9 이상에서 정상적으로 작동된다
  * 즉, 크로스 브라우징에 주의해야 한다

```JavaScript
// Object.create 함수의 폴리필
if (!Object.create) {
  Object.create = function (o) {
    function F() {}  // 1
    F.prototype = o; // 2
    return new F();  // 3
  };
}
```

* Polyfill은 특정 기능이 지원되지 않는 브라우저를 위해 사용할 수 있는 코드 조각이나 플러그인을 뜻한다
* 위의 코드에서는 프로토타입 패턴 상속의 핵심을 담고 있다
  * 비어있는 생성자 함수 F를 생성한다
  * 생성자 함수 F의 prototype 프로퍼티에 매개변수로 전달받은 객체를 할당한다
  * 생성자 함수 F를 생성자로 하여 새로운 객체를 생성하고 반환한다

# 캡슐화와 모듈 패턴

* Java의 경우 클래스를 정의하고 public 또는 private 등으로 한정할 수 있다
* 위와 같이 외부에 공개하거나 비공개하여 정보 은닉을 하는 것을 말한다
* 자바스크립트는 public, private 등으 키워드는 제공하지 않지만, 정보 은닉이 불가능한 것은 아니다

```JavaScript
var Person = function(arg) {
  var name = arg ? arg : ''; // ①

  this.getName = function() {
    return name;
  };

  this.setName = function(arg) {
    name = arg;
  };
}

var me = new Person('Lee');

var name = me.getName();

console.log(name);  // Lee

me.setName('Kim');
name = me.getName();

console.log(name);  // Kim
```
* 자바스크립트는 function-level scope를 제공하므로 함수 내의 변수는 외부에서 참조할 수 없으므로 ①의 name 변수는 private 변수가 된다
* var 대신 this를 사용하면 public 변수가 되며, 이때 new 키워드로 객체를 생성하지 않으면 this는 생성된 객체에 바인딩되지 않고 전역객체에 연결된다
* getName과 setName은 클로저로서 private 변수에 접근할 수 있고, 이것이 기본적인 정보 은닉의 방법이다

```JavaScript
var person = function (personInfo) {
  var o = personInfo;

  return {
    getPersonInfo: function() {
      return o;
    }
  };
};
```

* 처음 코드에서 변경을 위와 같이 시킬 수 있다
* person 함수는 객체를 반환하며, 이 객체 내의 메소드 getName과 setName은 클로저로서 private 변수 name에 접근할 수 있다
* 이를 모듈 패턴이라 하며 캡슐화와 정보 은닉을 제공한다
* 하지만, private 멤버가 객체나 배열일 경우 반환된 해당 멤버의 변경이 가능하다

```JavaScript
var me = person({ name: 'Lee', gender: 'male' });

var myInfo = me.getPersonInfo();
console.log('myInfo: ', myInfo);
// myInfo:  { name: 'Lee', gender: 'male' }

myInfo.name = 'Kim';

myInfo = me.getPersonInfo();
console.log('myInfo: ', myInfo);
// myInfo:  { name: 'Kim', gender: 'male' }
```

* 위와 같이 객체를 반환하는 경우 반환값은 얕은 복사로 private 멤버의 참조값을 반환하게 된다
* private 멤버의 참조값을 반환하므로 private 멤버의 값을 변경할 수 있다
* 이를 회피하기 위해서는 객체를 그대로 반환하지 않고 반환해야 할 객체의 정보를 새로운 객체에 담아 반환해야 한다
* 객체 전체가 그대로 반환되어야 하는 경우에는 깊은 복사로 본사본을 만들어 반환해야 한다
* 위의 모듈 패턴은 생성자 함수가 아니며 단순히 메소드를 담은 객체를 반환한다
* 반환된 객체는 객체 리터럴 방식으로 생성된 객체로 함수 person의 프로토타입에 접근할 수 없다
* 즉, 반환된 객체가 함수 person의 프로토타입에 접근할 수 없다는 것은 person을 부모 객체로 상속할 수 없다는 것을 의미한다
* 함수 person을 부모 객체로 상속할 수 없다는 것은 함수 person이 반환하는 객체에 모든 메소드를 포함시켜야한다는 것을 의미한다
* 이러한 문제를 해결하기 위해 객체를 반환하는 것이 아닌 함수를 반환해야 한다

```JavaScript
var Person = function() {
  var name;

  var F = function(arg) { name = arg ? arg : ''; };

  F.prototype = {
    getName: function() {
      return name;
    },
    setName: function(arg) {
      name = arg;
    }
  };

  return F;
}();

var me = new Person('Lee');

console.log(Person.prototype === me.__proto__);

console.log(me.getName());
me.setName('Kim')
console.log(me.getName());
```
