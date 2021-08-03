## 함수

* js 에서 기본적인 구성 블록 중 하나
* 작업을 수행하거나 값을 계싼하는 문장 집합 같은 자바 스크립트 절차
* 함수를 사용하기 위해서는 함수를 호출하는 범위 내에 함수를 정의해야 한다.

### 함수 선언

* 구성
    1. 함수의 이름
    1. 괄호 안에서 쉼표로 분리된 함수의 매개변수
    1. 중괄호 안의 함수를 정의하는 js 표현

```js
// statement
function sum(a, b) {
    return a + b;
}

// function expression
const factorial = function fac(n) { return n < 2 ? 1 : n * fac(n - 1) };
// 함수 표현식에는 함수 이름을 지정할 수 있고, 함수 내에서 자신을 참조하는데 사용 할 수 있다.
// 또한, 다른 함수의 매개변수로도 전달 할 수 있다.
function map(fn, a) {
    const result = [];
    for (let i = 0; i != a.length; i++) result[i] = fn(a[i]);
    
    return result;
}
```

* 기본 자료형 (boolean, number, string ...) 으로 전달된 매개변수를 함수 블록안에서 변경되더라도 전역적, 함수를 호출한 부분애 적용되지 않는다.
* 하지만, 기본 자료형이 아닌 값은 함수 내에서 변경이 되면 함수 외부에도 해당 값이 적용된다.

```js
function someFunction(obj) {
   obj.foo = 'change'; 
}

const someObj = {foo: 'before'};

console.log(someObj.foo); // before

someFunction(someObj);

console.log(someObj.foo); // change
```

### 함수 호출

* 함수는 호출될 때 범위 내에 있어야 한다.
  * state 함수는 호이스팅 될 수 있다. (반대로 함수 표현식은 호이스팅 되지 않는다.)

```js
console.log(square(5)); // ok
function suqare(n) { return n * n };

////////////////////

console.log(square); // undefined
console.log(square(5)); // TypeError
square = function (n) { return n * n };
```

### 함수의 범위

* 함수 내에 정의된 변수는 함수의 범위에서만 정의되어 있으므로, 함수 외부에서는 접근이 불가능하다.
* A 함수가 정의된 범위 내에 정의된 B 함수에서는 A 함수의 변수에 접근이 가능하다.

### 범위와 함수 스택

* 함수는 자신을 참조하고 호출할 수 있다. (재귀)
  1. 함수의 이름으로 호출
  1. arguments.callee
      * ES5 은 엄격 모드 (strict mode) 에서 arguments.callee()의 사용을 금한다.
  1. 함수를 참조하는 범위 내 변수
* 재귀적 알고리즘은 비 재귀적인 알고리즘으로 변환 할 수 있다.

### 중첩된 함수와 클로저

* 함수내에 함수를 끼워 넣을 수 있다.
  * 중첩 된 내부 함수는 그것을 포함하는 외부 함수와 별개이다.
  * 또한, 해당 함수는 클로저를 형성한다.
  * **클로저** 는 그 변수를 결합하는 환경을 자유롭게 변수와 함께 가질 수 있는 표현(전형적인 함수)이다.
* 즉, 내부 함수는 외부 함수의 명령문에서만 액세스할 수 있다.
* 내부 함수는 클로저를 형성한다.
  * 외부 함수는 내부 함수의 인수와 변수를 사용할 수 없지만, 내부 함수는 외부 함수의 인수와 변수를 사용할 수 있다.

```js
function outside(x) {
    function inside(y) {
        return x + y;
    }
    return inside;
};

fn_inside = outside(3);
result = fn_inside(5); // return 8

result = outside(3)(5); // return 8;
```

### 다중 중첩 함수

* 함수는 다중 중첩될 수 있다.
  * A -> B -> C ... 형태로 중첩이 가능하며, A 안의 B,C 는 모두 클로저를 형성한다.
  * 위와 같은 형태라면 B 는 A 에 접근할 수 있고, C 는 B 에 접근할 수 있다.
* 이러한 클로저는 다중 범위를 포함할 수 있다.
  * 또한, 재귀적으로 그것을 포함하는 함수의 범위를 포함하며, 이를 `범위 체이닝` 이라고 한다.

```js
function A(x) {
    function B(y) {
        function C(z) {
            console.log(x + y + z);
        }
        C(3);
    }
    B(2);
}
A(1); // 6

/*
1. B 는 A 를 포함하는 클로저를 형성
2. C 는 B 를 포함하는 클로저를 형성
3. B 의 클로저는 A 를 포함, C 의 클로저도 A 를 포함하기 때문에, C 에서 B,A 의 인수와 변수에 접근이 가능하다.
즉, C 는 A 와 B 의 범위 체이닝을 한다.
하지만, 역은 아니다. -> A -> B/C, B -> C 에 접근은 불가능하다.
*/
```

### 이름 충돌

* 클로저의 범위에서 두개의 인수 또는 변수의 이름이 같은 경우, 더 안쪽 범위가 우선순위를 가진다. (이름 충돌)
  * 가장 외부는 우선순위가 가장 낮고 가장 안쪽이 더 큰 우선순위를 가지며, 이를 범위 체인이라 한다.
  
```js
function outside() {
    var x = 10;
    function inside(x) {
        return x;
    }
    return inside;
}
result = outside()(20); // 20
```

### 클로저

* js 는 함수의 중첩을 허용하고, 내부함수가 외부 함수에서 정의된 모든 변수와 함수들에 접근이 가능하다.
  * 그러나 외부함수는 내부함수 안에 정의된 변수와 함수에 접근할 수 없다.
  * 즉, 내부 함수의 변수에 대한 일종의 캡슐화를 제공한다.
  * 또한 내부 함수가 외부 함수의 변수에 접근할 수 있으므로, 내부 함수가 외부 함수의 수명을 초과하여 생존할 경우 외부 함수의 변수나 함수는 외부함수의 실행 기간보다 길 수 있다.
* 클로저는 내부 함수가 어떻게든 외부 함수 범위 밖의 모든 범위에서 사용 가능해지면 생성된다.

```js
var pet = function(name) {
    var age;
    
    return {
        getName: function() {
            return name;
        },
        setName: function(newName) {
            name = newName;
        },
        getAge: function() {
            return age;
        },
        setAge: function(newAge) {
            if (typeof newAge === 'number') age = newAge;
        }
    };
}

myPet = pet("poodle");

myPet.getName(); // poodle 을 리턴
myPet.setAge(10);
myPet.getAge(); // 10 을 리턴
myPet.setName("dachshund");
myPet.getName(); // dachshund 을 리턴

/*
getAge 를 통해 내부 변수에 접근할 수 있고, setAge 를 통해 내부 변수를 셋팅할 수 있다.
또한, myPet.age 와 같이 접근은 불가능하다. 

위와같이 설정을 하면 age 라는 내부 변수는 지속적이고 캡슐화된 데이터로서 보유된다.
 */

var createPet = function(name) {
    return {
        setName: function(name) {
            name = name;
            // 위와 같이 내부 함수가 외부 함수 범위에 존재하는 변수 중 같은 이름으로 정의하면
            // 다시는 외부 함수 범위의 변수에 접근할 방법이 없다.
        }
    }
}
```
