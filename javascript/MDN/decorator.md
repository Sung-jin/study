## 소개

* 데코레이터는 클래스 선언과 멤버에 어노테이션과 메타-프로그래밍 구문을 추가할 수 있는 방법을 제공한다
* 데코레이터는 js 에 대한 2단계 제안이며 ts 의 실험적 기능으로 이용 가능하다
  * 실험적 지원을 활성화
    * `tsconfig.json` 에서 `experimentDecorators` 컴파일 옵션을 활성화
    * `tsc --target ES5 --experimentalDecorators` 으로 명령줄 실행
  
### 데코레이터

* 클래스 선언, 메서드, 접근자, 프로퍼티 또는 매개변수에 첨부할 수 있는 특수한 종류의 선언
* `@expression` 형식을 사용하며, expression 은 데코레이팅 된 선언에 대한 정보와 함께 런타임에 호출되는 함수이어야 한다

```typescript
function foo(target) {}

@foo
// 위와 같이 @foo 를 사용하기 위해서는 foo 라는 함수를 작성할 수 있다
```

#### 데코레이터 팩토리

* 데코레이터 팩토리를 사용하면 데코레이터가 선언에 적용되는 방식을 원하는 형태로 변경할 수 있다
* 데코레이터 팩토리는 데코레이터가 런타임에 호출할 표현식을 반환하는 함수이다

```typescript
function color(value: string) { // 데코레이터 팩토리
    return function(target) {
        // 데코레이터
    }
}
```

#### 데코레이터 합성

* 여러 데코레이터를 사용할 때 다음과 같은 단계로 수행된다
  1. 각 데코레이터의 표현은 위에서 아래로 평가된다
  1. 다음 결과는 아래에서 위로 함수로 호출된다
* 수학의 합성 합수와 유사하게 동작한다

```typescript
function f() {
    
    console.log("f(): evaluated");
    return function (target, propertyKey: string, descriptor: PropertyDescriptor) {
        console.log("f(): called");
    }
}

function g() {
    console.log("g(): evaluated");
    return function (target, propertyKey: string, descriptor: PropertyDescriptor) {
        console.log("g(): called");
    }
}

class C {
    @f()
    @g()
    method() {}
}

/*
f(): evaluated
g(): evaluated
g(): called
f(): called
 */
```

#### 데코레이터 평가

* 클래스에서 다양한 선언에 데코레이터를 적용하는 방법은 다음과 같다
  1. 메서드, 접근자 또는 프로퍼티 데코레이터가 다음에 오는 매개 변수 데코레이터는 각 인스턴스 멤버에 적용된다
  1. 메서드, 접근자 또는 프로퍼티 데코레이터가 다음에 오는 매개 변수 데코레이터는 각 정적 멤버에 적용된다
  1. 매개 변수 데코레이터는 생성자에 적용된다
  1. 클래스 데코레이터는 클래스에 적용된다

#### 클래스 데코레이터

* 클래스 데코레이터는 클래스 선언 직전에 선언된다
* 클래스 데코레이터는 클래스 생성자에 적용되며 클래스 정의를 관찬, 수정 또는 교체하는 데 사용할 수 있다
* 클래스 데코레이터는 선언 파일이나 다른 주변 컨텍스트에서 사용할 수 없다
* 클래스 데코레이터의 표현식은 데코레이팅 된 클래스의 생성자를 유일한 인수로 런타임에 함수로 호출된다
* 클래스 데코레이터가 값을 반환하면 클래스가 선언을 제공하는 생성자 함수로 바꾼다

```typescript
function sealed(constructor: Function) {
    Object.seal(constructor);
    Object.seal(constructor.prototype);
}

@sealed
class Greeter {
    greeting: string;
    constructor(message: string) {
        this.greeting = message;
    }
    greet() {
        return "Hello, " + this.greeting;
    }
}
// @sealed 가 실행되면 생성자와 프로토 타입을 모두 감싼다

function classDecorator<T extends {new(...args:any[]):{}}>(constructor:T) {
    return class extends constructor {
        newProperty = "new property";
        hello = "override";
    }
}

@classDecorator
class Greeter {
    property = "property";
    hello: string;
    constructor(m: string) {
        this.hello = m;
    }
}

console.log(new Greeter("world"));
/*
생성자를 재정의

{
  "property": "property",
  "hello": "override",
  "newProperty": "new property"
}
 */
```
