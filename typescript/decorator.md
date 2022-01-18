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

#### 메서드 데코레이터

* 메서드 데코레이터는 메서드 선언 직전에 선언된다
* 데코레이터는 메서드의 프로퍼티 설명자에 적용되며 메서드 정의를 관찰, 수정 또는 대체하는데 사용할 수 있다
* 메서드 데코레이터는 선언 파일, 오버로드 또는 기타 주변 컨텍스트에서 사용할 수 없다
* 메서드 데코레이터의 표현식은 다음과 같은 인수와 함께 함수로 호출된다
  1. 정적 멤버에 대한 클래스의 생성자 함수 또는 인스턴스 멤버에 대한 클래스의 프로토타입
  1. 멤버의 이름
  1. 멤버의 프로퍼티 설명자 (ES5 이전일 경우 프로퍼티 설명자는 undefined 이다)
* 메서드 데코레이터가 값을 반환한다면, 메서드의 프로퍼티 설명자로 사용된다
  * ES5 이전일 경우 반환 값은 무시된다

```typescript
function enumerable(value: boolean) {
    return function (target: any, propertyKey: string, descriptor: PropertyDescriptor) {
        descriptor.enumerable = value;
    };
    // 해당 데코레이터가 호출되면 설명자의 enumerable 프로퍼티를 수정한다
}

class Greeter {
    greeting: string;
    constructor(message: string) {
        this.greeting = message;
    }

    @enumerable(false)
    greet() {
        return "Hello, " + this.greeting;
    }
}
```

#### 접근자 데코레이터

* 접근자 데코레이터는 접근자 선언 바로 전에 선언된다
* 접근자 데코레이터는 접근자의 프로퍼티 설명자에 적용되며 접근자의 정의를 관찰, 수정 또는 교체하는데 사용할 수 있다
* 접근자 데코레이터는 선언 파일이나 다른 주변 컨텍스트에서 사용할 수 없다
* ts 는 단일 멤버에 대한 get/set 접근자를 데코레이팅을 할 수 없다
* 접근자 데코레이터의 표현식은 런타임에 다음 세가지 인수와 함게 함수로 호출된다
  1. 정적 멤버에 대한 클래스의 생성자 함수 또는 인스턴스 멤버에 대한 클래스의 프로토타입
  1. 멤버의 이름
  1. 멤버의 프로퍼티 설명자 (ES5 이전일 경우 프로퍼티 설명자는 undefined 이다)
* 메서드 데코레이터가 값을 반환한다면, 메서드의 프로퍼티 설명자로 사용된다
  * ES5 이전일 경우 반환 값은 무시된다
  
```typescript
function configurable(value: boolean) {
    return function (target: any, propertyKey: string, descriptor: PropertyDescriptor) {
        descriptor.configurable = value;
    };
}

class Point {
    private _x: number;
    private _y: number;
    constructor(x: number, y: number) {
        this._x = x;
        this._y = y;
    }
  
    @configurable(false)
    get x() { return this._x; }
  
    @configurable(false)
    get y() { return this._y; }
}
```

#### 프로퍼티 데코레이터

* 프로퍼티 데코레이터는 프로퍼티 선언 바로 전에 선언된다
* 프로퍼티 데코레이터는 선언 파일이나 다른 주변 컨텍스트에서 사용할 수 없다
* 프로퍼티 데코레이터의 표현 식은 런타임에 다음과 같은 인수와 함께 함수로 호출된다
  1. 정적 멤버에 대한 클래스의 생성자 함수 또는 인스턴스 멤버에 대한 클래스의 프로토타입
  1. 멤버의 이름

```typescript
import "reflect-metadata";

const formatMetadataKey = Symbol("format");

function format(formatString: string) {
    return Reflect.metadata(formatMetadataKey, formatString);
}

function getFormat(target: any, propertyKey: string) {
    return Reflect.getMetadata(formatMetadataKey, target, propertyKey);
}

class Greeter {
    @format("Hello, %s")
    // 데코레이터 팩토리
    // 해당 데코러에티가 호출되면 reflect-metadata 라이브러리의 Reflect.metadat 함수를 사용하여 프로퍼티에 대한 메타데이터 항목을 추가한다
    greeting: string;

    constructor(message: string) {
        this.greeting = message;
    }
    greet() {
        let formatString = getFormat(this, "greeting");
        return formatString.replace("%s", this.greeting);
    }
}
```

#### 매개변수 데코레이터

* 매개변수 데코레이터는 매개변수 선언 직전에 선언된다
* 클래스 생성자 또는 메서드 선언의 함수에 적용된다
* 선언 파일, 오버로드 또는 다른 주변 컨텍스트 (선언 클래스) 에서 사용할 수 없다
* 매개변수 데코레이터는 매개변수가 메서드에서 선언되었을 때에만 관찰하는데 사용할 수 있다
* 매개변수 데코레이터의 반환 값은 무시된다
* 런타임시 다음 세개의 인수와 함께 매개변수 데코레이터 표현식이 호출된다
  1. 정적 멤버에 대한 클래스의 생성자 함수 또는 인스턴스 멤버에 대한 클래스의 프로토타입
  1. 멤버의 이름
  3. 함수의 매개 변수 목록에 있는 매개 변수의 서수 색인

```typescript
import 'reflect-metadata';
// reflect-metadata 라이브러리를 이용

function required(target: Object, propertyKey: string|symbol, parameterIndex: number) {
    const existingRequiredParameters: number[] = Reflect.getOwnMetadata(requiredMetadataKey, target, propertyKey) || [];
    existingRequiredParameters.push(parameterIndex);
    Reflect.defineMetadata(requiredMetadataKey, existingRequiredParameters, target, propertyKey);
}
// 해당 데코레이터를 이용하여 매개변수를 표시하는 메타데이터 항목을 추가한다

function validate(target: any, propertyName: string, descriptor: TypedPropertyDescriptor<Function>) {
    let method = descriptor.value;
    descriptor.value = function () {
        const requiredParameters: number[] = Reflect.getOwnMetadata(requiredMetadataKey, target, propertyName);
        if (requiredParameters) {
            for (let parameterIndex of requiredParameters) {
                if (parameterIndex >= arguments.length || arguments[parameterIndex] === undefined) {
                    throw new Error("Missing required argument.");
                }
            }
        }
    
        return method.apply(this, arguments);
      }
}
// 해당 데코레이터는 원래 메서드를 호출하기 전에 인수 유효성 검증

class Greeter {
    greeting: string;
  
    constructor(message: string) {
        this.greeting = message;
    }
  
    @validate
    greet(@required name: string) {
        return "Hello " + name + ", " + this.greeting;
    }
}
```

#### 메타데이터

* 실험적 메타데이터 API 에 대한 폴리필을 추가하는 reflect-metadata 라이브러리를 사용할 수 있다
  * 해당 라이브러리는 ECMAScript 표준의 일부가 아니다
* `npm i reflect-metadata --save` 명령어를 통해 설치가 가능하다
* ts 에는 데코레이터가 있는 선언에 대해 특정 타입의 메타 데이터를 내보내는 실험적인 지원을 포함한다
  * tsconfig.json 에서 `emitDecoratorMetadata` 컴파일러 옵션을 설정해야 한다

```typescript
import 'reflect-metadata';

// function validate<T>(target: any, propertyKey: string, descriptor: TypedPropertyDescriptor<T>) {
//     const set = descriptor.set;
//     descriptor.set = function (value: T) {
//         const type = Reflect.getMetadata('design:type', target, propertyKey);
//         if (!(value instanceof type)) {
//             throw new TypeError('Invalid type.');
//         }
//         set.call(target, value);
//     }
// }

class Point {
    x: number;
    y: number;
}

class Line {
    private _p0: Point;
    private _p1: Point;
  
    @validate
    @Reflect.metadata('design:type', Point)
    set p0(value: Point) { this._p0 = value; }
    get p0() { return this._p0; }
  
    @validate
    @Reflect.metadata('design:type', Point)
    // 해당 데코레이터를 사용하면 디자인-타입 정보를 주입한다
    set p1(value: Point) { this._p1 = value; }
    get p1() { return this._p1; }
}
```
