# JavaScript 3

##  객체

* 자바스크립트는 객체기반의 스크립트 언어
* 자바스크립트에서 기본자료형을 제외한 나머지 모든 것들은 객체이다
* 객체는 데이터와 그 데이터에 관련되는 동작을 모두 포함할 수 있는 개념적 존재
* 프로퍼티와 동작을 나타내는 메소드로 구성된 집합, 구성 요소들의 순서를 보장하지 않는다
* 자바스크립트는 **프로토타입** 인 객체의 프로퍼티와 메소드를 상속받을 수 있다

1. 프로퍼티
* 이름과 값의 쌍을 프로퍼티라고 한다
* 프로퍼티는 프로퍼티 이름과 프로퍼티 값으로 구성
* 프로퍼티 이름 - 빈 문자열을 포함하는 모든 문자열
* 프로퍼티 값 - 모든 값
* 프로퍼티 이름이 중복될 시 나중에 선언된 프로퍼티로 덮어 쓴다

2. 메소드
* 객체에 제한되어 있는 함수를 의미
* 프로퍼티가 함수일 경우, 일반 함수와 구분하기 위해 메소드라 한다

3. 객체 생성
* ES6 이전 자바스크립트에서는 클래스라는 개념이 없었으며 별도의 객체 생성 방법이 존재
* ES6에서 클래스가 생김으로 새로운 객체지향 모델을 제공하는 것이 아닌 class도 함수이다
* 객체 리터럴
  * 일반적인 자바스크립트의 객체 생성 방식
  * '{}'를 통해 객체를 생성
  * {프로퍼티 이름 : 프로퍼티 값}과 같은 형태로 기술하면 해당 프로퍼티가 추가된 객체를 생성할 수 있다
  * {}안에 아무것도 없다면 빈 객체를 생성한다
```javascript
var emptyObject = {};
console.log(typeof emptyObject); // object

var person = {
  name: 'Lee',
  gender: 'male',
  sayHello: function () {
    console.log('Hi! My name is ' + this.name);
  }
};

console.log(typeof person); // object
console.log(person); // { name: 'Lee', gender: 'male', sayHello: [Function: sayHello] }

person.sayHello(); // Hi! My name is Lee
```
* Object() 생성자 함수
  * new와 함께 빈 객체를 생성할 수 있다
  * 객체가 소유하고 있찌 않은 프로퍼티에 값을 할당하면 그 프로퍼티가 추가된다
```javascript
// 빈 객체의 생성
var person = new Object();
// 프로퍼티 추가
person.name = 'Lee';
person.gender = 'male';
person.sayHello = function () {
  console.log('Hi! My name is ' + this.name);
};

console.log(typeof person); // object
console.log(person); // { name: 'Lee', gender: 'male', sayHello: [Function] }

person.sayHello(); // Hi! My name is Lee
```
* 객체 리터럴로 생성해도 자바 스크립트 내부적으로 Object() 생성자 함수를 사용하여 객체를 생성한다
* 생성자 함수를 사용하면 클래스 생성자를 통해 생성하는 것처럼 간편하게 생성할 수 있다
```javascript
var person1 = {
  name: 'Lee',
  gender: 'male',
  sayHello: function () {
    console.log('Hi! My name is ' + this.name);
  }
};

var person2 = {
  name: 'Kim',
  gender: 'female',
  sayHello: function () {
    console.log('Hi! My name is ' + this.name);
  }
};
//////////////////////////////////////////////////////////
// 생성자 함수
function Person(name, gender) {
  this.name = name;
  this.gender = gender;
  this.sayHello = function(){
    console.log('Hi! My name is ' + this.name);
  };
}

// 인스턴스의 생성
var person1 = new Person('Lee', 'male');
var person2 = new Person('Kim', 'female');

//위와 아래의 결과는 같다
//////////////////////////////////////////////////////////
function Person(name, gender) {
  var married = true;         // private
  this.name = name;           // public
  this.gender = gender;       // public
  this.sayHello = function(){ // public
    console.log('Hi! My name is ' + this.name);
  };
}

var person = new Person('Lee', 'male');

console.log(typeof person); // object
console.log(person); // Person { name: 'Lee', gender: 'male', sayHello: [Function] }

console.log(person.gender);  // 'male'
console.log(person.married); // undefined
//생성자 함수는 일반적으로 대문자로 시작하는데, 이는 생성자 함수는 일반 함수에서 new 연산자를 붙이면 사용 할 수 있기 때문에 혼란을 방지하기 위한 방지책이다
//소문자로 해도 동작은 하나 코드의 가독성이 떨어진다
//this는 생성자 함수가 생성할 인스턴스를 가리키며, this를 통해 연결된 프로퍼티와 메소드는 외부에서 참조가 가능하다
//생성자 내부에서 선언된 변수는 외부에서 참조 불가능하다
```

4. 프로퍼티에 접근

* 프로퍼티 이름은 기본적으로 문자열로 작성
* 문자열 이외에도 숫자로 작성할 수 있지만 암묵적으로 형변환되어 문자열이 된다
* 프로퍼티 이름은 '' / ""로 묶어주어야 하지만 사용 가능한 유효한 이름일 경우 생략해도 가능하다
* 유효하지 않은 이름일 경우 반드시 '' / ""으로 묶어줘야 한다
* 프로퍼티 값이 함수인 경우 이를 메소드라 한다
* 객체의 프로퍼티에 접근하는 방법은 마침표, 표기법, 대괄호 표기법이 있다
* 객체가 소유하고 있는 프로퍼티에 새로운 값을 할당하면 프로퍼티 값은 갱신된다
* 객체가 소유하고 있지 않은 프로퍼티에 값을 할당하면 해당 프로퍼티를 추가하고 값을 할당한다
* delete 연산자를 사용하여 객체의 프로퍼티를 삭제할 수 있다
* for-in을 통해 객체에 포함된 모든 프로퍼티에 대한 루프를 수행할 수 있다
```javascript
var person = {
  'first-name': 'Ung-mo',
  'last-name': 'Lee',
  gender: 'male',
  1: 10
};

console.log(person.first-name);    // NaN: undefined-undefined
console.log(person[first-name]);   // ReferenceError: first is not defined
console.log(person['first-name']); // 'Ung-mo'

console.log(person.gender);    // 'male'
console.log(person[gender]);   // ReferenceError: gender is not defined
console.log(person['gender']); // 'male'

console.log(person['1']); // 10
console.log(person[1]);   // 10 : person[1] -> person['1']
console.log(person.1);    // SyntaxError

console.log(person.age); // undefined

person['first-name'] = 'Kim';
console.log(person['first-name'] ); // 'Kim'
//프로퍼티 값 갱신

person.age = 20;
console.log(person.age); // 20
//프로퍼티 값 동적 생성

delete person.gender;
console.log(person.gender); // undefined
//프로퍼티 삭제

for (var prop in person) {
  console.log(prop + ': ' + person[prop]);
}

//first-name: Kim
//last-name: Lee
//age: 20
```

5. Pass-by-reference

* object를 객체형/참조형이라 한다
* 객체의 모든 연산이 실제값이 아닌 참조값으로 처리된다
* 기본 자료형은 값이 정해지면 변경할 수 없지만 객체는 프로퍼티를 변경, 추가, 삭제가 가능하다
* 객체는 동적으로 변화할 수 있으므로 런타임에 메모리 공간을 확보하고 메모리의 힙 영역에 저장된다
* 기본자료형은 값으로 복사되어 전달되는데, 이를 pass-by-value라 한다
```JavaScript
// Pass-by-reference
var foo = {
  val: 10
}

var bar = foo;
//같은 참조값(address)를 가진다
console.log(foo.val, bar.val); // 10 10
console.log(foo === bar);      // true

bar.val = 20;
//같은 참조값을 가지므로 한곳에서 값을 변경하면 참조하는 곳이 같으므로 둘다 같이 변한다
console.log(foo.val, bar.val); // 20 20
console.log(foo === bar);      // true

var baz = {
  val: 20
}
//새로운 객체를 생성하고, 프로퍼티를 초기화 한다
//이때 생성되는 20은 위에서 bar와 foo에서 참조하는 20이 아닌 새로 생성된 20을 참조한다
console.log(bar.val, baz.val); // 20 20
console.log(bar === baz);      // false
//값은 같지만 둘이 참조하는 값이 다르므로 false
```

6. 객체의 분류

* Built-in Objcet(내장 객체) : 웹페이지 등을 표현하기 위한 공통의 기능을 제공
* 내장 객체는 Standard Built-in Object, Native Object로 구분할 수 있으며 Native Object는 Browser Object Model과 Document Object Model로 구분할 수 있다
* 사용자가 생성한 객체를 Host Object라고 한다
