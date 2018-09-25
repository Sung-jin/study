# JavaScript 5

어떤 특정 작업을 수행하기 위해 필요한 일련의 구문들을 그룹화한 개념을 함수라 한다

미리 작성된 함수를 재사용 할 수 있다 - 코드의 재사용

함수의 일반적 기능은 특정 작업을 수행하는 구문들의 집합을 정의하고 필요시 호출하여 필요한 값 또는 결과를 얻는 것

이 외에도 객체 생성, 객체의 메소드 지정, 정보의 구성 및 은닉, 클로저, 모듈화 등등 기능을 수행할 수 있다

함수는 구문의 집합으로 모듈화의 근간이 된다 -> 프로그래밍 기술은 요구사항의 집합을 자료구조와 함수의 집합을 변환하는 것이 일반적이다

함수 또한 객체이며 입급객체, First-class object라 한다

함수는 호출할 수 있다는 다른 객체와 다른 특징을 가지고 있으며, 함수도 객체이므로 변수나 객체, 배열 등에 저장할 수 있고 다른 함수로 전달되는 인자와 함수의 반환값이 될 수 있다

##  함수정의
* 함수 선언문
  * function 키워드와 이하의 내용으로 구성
  * 함수명, 매개변수 목록, 함수 몸체
```JavaScript
function 함수명(매개변수 목록...){
  함수 몸체
};

//하지만 이 변수명만 가지고는 외부 코드에서 접근이 불가능하다
//위와 같이 선언된 함수 선언문은 자바 스크립트 엔진에 의해 아래와 같이 변경이 되어 함수 이름으로 코드 외부에서 접근이 가능한 것이다

var 함수명 = function 함수명(매개변수 목록...){
  함수몸체
};
//결국 함수 선언문도 함수 표현식과 동일하게 함수 리터럴 방식으로 정의되는 것이다
```
  * 함수 선언문에서 함수명은 생략 불가능, 자신을 재귀적 호출하거나 자바스크립트 디버거가 해당 함수를 구분할 수 있는 식별자
  * 매개변수 목록은 0개 이상의 목록으로 다른 언어와 다르게 매개변수의 자료형을 기술하지 않는다
  * 함수의 몸체는 실제 함수가 호출되었을 때 실해오디는 구문들의 집합
  * 함수 몸체에서 return을 통해 결과값을 반환할 수도 있다

* 함수 표현식
  * 함수는 일급 객체
  > 무명의 리터럴로 표현이 가능<br>
  변수나 자료구조에 저장할 수 있다<br>
  함수의 바라미터로 전달할 수 있다<br>
  반환값으로 사용할 수 있다<br>

  * 일급 객체라는 것을 이용하여 함수 리터럴 방식으로 함수를 정의하고 변수에 할당
```javascript
var 변수명 = function(매개변수 목록...){
  함수 몸체
}
//이렇게 함수 표현식으로 표현하면 변수명을 생략할 수 있으며, 이러한 함수를 익명 함수라고 한다

var foo = function multiply(a, b){
  return a * b;
};
//가명 함수 표현식
var bar = function(a, b){
  return a * b;
};
//익명 함수 표현식

console.log(multiply(10, 5)); //Uncaught ReferenceError: multiply is not defined

var foobar = bar;
console.log(foobar(10, 5)); //50
//bar와 foobar는 동일한 익명 함수의 참조값을 가진다
```

* Function 생성자 함수
  * 함수 선언문, 표현식 모두 함수 리터럴 방식으로 사용(선언문은 자바스크립트 엔진이 기명 함수 표현식으로 변환하면서)
  * 이러한 두가지 함수 선언의 경우 리터럴 방식으로 함수를 정의하는데 이는 **내장 함수 Function 생성자 함수로 생성하는 것을 단순화시킨 축약법**
  * Function 생성자 함수는 Function.protorype.constructor 프로퍼티로 접근할 수 있다

```javascript
new Function(arg1, arg2, ... argN, functionBody)
//위는 Function 생성자 함수로 함수를 생성하는 문법

var 변수명 = new Function('매개변수1', '매개변수2', ..., '매개변수N', '함수몸체');
//위에 생성한 multiply 함수를 위의 new Function으로 생성하면
var multiply = new Function('num1', 'num2', 'return num1 * num2');
```

##  함수 호이스팅
```javascript
var res = square(5);

function square(number){
  return number * number;
}
//함수 호이스팅
---------------------------------------------------------------
var res2 = square2(5);  // TypeError: square is not a function

var square = function(number){
  return number * number;
}
```
위와 같이 함수 선언문의 경우 함수 선언 위치와 상관없이 함수 호출이 가능하다

**자바스크립트는 EB6의 let, const를 포함하여 모든 선언(var, let, const, function, function*, class)을 호이스팅 한다!**

함수 선언무으로 정의된 함수는 자바스크립트 엔진이 스크립트가 로딩되는 시점에 초기화하고 이를 variable object에 저장한다

**함수 선언, 초기화, 할당이 한번에 이루어 진다!** 고로 함수 선언문으로 정의된 함수는 선언 위치에 상관없이 소스 어느곳이든 호출이 가능하다

함수 표현식의 경우 함수 호이스팅이 아니라 변수 호이스팅이 발생, 변수 호이스팅은 변수 생성 및 초기화까지 일어나므로 아직까지 할당이 안됬으므로 'undefined'가 변수에 들어가 있다

즉, 실제값의 할당은 할당문에서 이뤄지는데 아직 함수 내용이 할당이 안됬으므로 함수가 아니라고 나오는 것

**함수 표현식은 함수 선언문과 달리 스크립트 로딩 시점에 VO에 함수를 할당하지 않고 runtime에 해석되고 실행됨**

자바스크립트의 권위자인 더글러스 크락포드는 함수 표현식만을 사용할 것을 권고 -> 함수 호이스팅은 함수를 호출 전 선언한다는 규칙을 무시하므로 코드의 구조를 엉성하게 만들 수 있다고 지적

**함수 선언문으로 함수를 정의하면 대규모 애플리케이션의 경우 인터프리터가 너무 많은 코드를 변수 객체에 저장하므로 애플리케이션의 응답속도는 현저히 떨어질 수 있다**

이렇게 한가지를 더 배웠네!! 평소에 함수 선언문으로 ~~편해서~~ 그리고 습관되서 사용했는데, 함수 표현식으로 바꿔야 겠다

##  First-class object 일급 객체

일급 객체란 생성/대입/연산/인자 또는 반환값으로서의 전달 등 프로그래밍 언어의 기본적 조작을 제한없이 사용할 수 있는 대상

함수와 객체를 구분짓는 특징은 호출할 수 있다는 점

```JavaScript
// 1. 무명의 리터럴로 표현이 가능하다.
var increase = function(num) {
  return num + 1;
};

var decrease = function(num){
  return num - 1;
};
// 2. 변수나 데이터 구조안에 담을 수 있다.
var obj = {
  increase: increase,
  decrease: decrease
};

// 3. 함수의 파라미터로 전달 할 수 있다.
function calc(func, num){
  return func(num);
}

console.log(calc(increase, 1)); //2
console.log(calc(decrease, 1)); //0

// 3. 반환값(return value)으로 사용할 수 있다.
function calc(mode){
  var funcs = {
    plus:  function(left, right){ return left + right; },
    minus: function(left, right){ return left - right; }
  };
  return funcs[mode];
}
console.log(calc('plus')(2,1));   //3
console.log(calc('minus')(2,1));  //1
```

##  매개변수 - 인자
* parameter와 argument
  * parameter는 함수 내에서 변수와 동일하게 메모리 공간을 확보
  * 함수에 전달된 argument는 매개변수에 할당
  * argument를 전달하지 않을 시 undefined로 초기화가 된다
* Call-by-value
  * 원시 타입 argument는 값에 의한 호출로 동작된다
  * 함수 호출시 원시 타입 argument는 parameter에 값을 복사하여 함수로 전달한다
  * 함수 내에 parameter를 통해 값이 변경되어도 원시 타입 값은 값이 변하지 않는다
* Call-by-reference
  * 객체형 argument는 참조에 의한 호출로 동작된다
  * 객체를 함수에 전달할 때는 parameter를 복사하지 않고 이 값을 참조한다
  * 참조하므로 함수 내부에서 값을 변경할 시 함수가 끝이 나고 전달한 parameter도 값이 변경되어 있다
* Impure function / Pure function
  * 함수 내부에서 변경되어도 외부에서는 변경되지 않는 함수를 Pure function
  * 그 반대로 함수 내부에서 변경되면 외부도 영향을 받는 것을 Impure function이라 한다

```javascript
var foo = function (p1, p2) {
  console.log(p1, p2);
};

foo(1); // 1 undefined

//Call-by-value
function bar(primitive) {
  primitive += 1;
  return primitive;
}

var x = 0;

console.log(bar(x)); // 1
console.log(x);      // 0
//함수 외부의 값이 변경되지 않는다

//Call-by-reference
function changeVal(primitive, obj) {
  primitive += 100;
  obj.name = 'Kim';
  obj.gender = 'female';
}

var num = 100;
var obj = {
  name: 'Lee',
  gender: 'male'
};

console.log(num); // 100
console.log(obj); // Object {name: 'Lee', gender: 'male'}

changeVal(num, obj);

console.log(num); // 100
console.log(obj); // Object {name: 'Kim', gender: 'female'}
//객체는 함수에 parameter로 전달이 되면 Call-by-reference로 전달된다
//즉, 함수 내부에서 값이 변경된다면 외부에도 같이 변경되어진다
```

## 반환값
* return 키워드로 함수에서 반환값을 반환할 수 있다
* return으로 함수를 호출한 caller에게 값을 반환할 수 있다
* 배열 등을 이용하여 한번에 여러개의 값을 리턴할 수 있다
* 함수는 반환을 생략할 수 있으며, 생략하면 자동으로 undefined를 반환한다
* return을 만나면 함수의 실행을 종료하고 호출한 코드로 되돌아 간다
* 즉, 함수안에 return 이후 코드가 더 존재하더라도 함수는 종료가 된다
