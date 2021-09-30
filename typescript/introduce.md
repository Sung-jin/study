### 타입 추론

* 변수를 생성함과 동시에 특정 값을 할당하면, 할당된 값 타입으로 해당 변수의 타입이 정해진다.

```typescript
const foo = "hello ts";
// ts 는 foo 가 string 임을 추론하여 해당 변수를 해당 타입으로 사용한다.
```

### 타입 정의하기

* ts 는 변수에 추론 타입을 지정할 수 있다.

```typescript
const foo = {
    name: 'some name',
    id: 0,
};
// name 은 string, id 는 numaber

interface foo {
    name: string,
    id: number,
}
// foo 라는 타입에는 name/id 가 존재하고, 각각 string/number 타입을 가진다고 정의한다.

const bar: foo = {
    name: 'some name',
    id: 0,
    // wrongType: 'wrong!!',
    // @errors: 2322
}
// bar 라는 변수는 foo 라는 타입의 형태를 가진다고 정의하였으므로,
// 해당 값을 가지게 된다.
// 그 외의 값을 가지게 된다면 ts 는 경고한다.

function someFunc(): foo {
    // 리턴 값을 타입으로 명시하여, 해당 타입의 데이터가 리턴됨을 명시할 수 있다.
    ...
}
```

#### ts 에서 사용할 수 있는 타입

* js 에서 사용되는 타입 (boolean, bigint, null, number, string, symbol, object, undefined) 외에 다음과 같은 타입을 사용할 수 있다.
    1. any - 어떠한 타입이든 허용
    2. unknown - 이 타입을 사용하는 사람이 무엇을 선언했는가를 확인해야 함
    3. never - 해당 타입은 발생될 수 없다
    4. void - undefined 를 리턴하거나 리턴 값이 없는 함수

### 유니언 (Unions)

* 여러 타입 중 하나일 수 있음을 선언한다.

```typescript
type MyBool = true | false;
// MyBool 은 ture 또는 false 가 가능함을 명시.
// 결국 boolean (구조적 타입 시스템의 프로퍼티) 이 된다.

type StringUnion = 'Foo' | 'Bar' | 'Fuz';
type NumberUnion = 1 | 2 | 3;
// string/number 등의 리터럴집합을 설정할 때 사용할 수 있다.
function wrap(obj: string|string[]): string[] {
    // 또한, 여러 타입의 값을 명시할 때 사용할 수 있다.
    if (typeof obj === 'string') return [obj];
    // 타입을 체크화는 순간, 해당 값은 해당 타입으로서 사용된다.
    // 즉, 위의 if block 이 true 가 되는 순간 obj 는 string 으로서 사용된다.
    // 고로, string 에서 사용될 수 있는 함수 등을 사용할 수 있게 된다.
    else obj;
}
```

### 교집합

```typescript
type Combinded = { a: number } & { b: string };     // { a: number, b: string }
type Conflicting = { a: number } & { a: number };   // { Conflicting.a: number & string }
```

#### 타입을 체크하는 조건자

```typescript
// 아래의 형태로 타입 체크하면, 해당하는 값은 해당되는 타입으로 지정된다.
typeof obj === 'string'     // string
typeof obj === 'number'     // number
typeof obj === 'bigint'     // bigint
typeof obj === 'boolean'    // boolean
typeof obj === 'symbol'     // symbol
typeof obj === 'undefinded' // undefinded
typeof obj === 'function'   // function
Array.isArray(obj)          // array
typeof obj === 'object'     // object
```

#### 유니언 타입

```typescript
declare function pad(s: string, n: number, direction: 'left'|'right'): string;
pad('hi', 10, 'left');
// ok

let s = 'right';
pad('hi', 10, s);
// @errors: 2345
// s 에 당장 할당된 값은 left|right 중 right 이지만, let 이므로 변경 가능하다.
// 즉, 변경이 가능한 변수를 지정된 유니언 타입에 대입했으므로 타입 에러가 발생한다.

let s2: 'left'|'right' = 'right';
pad('hi', 10, s2);
// s2 는 가변변수이지만, 타입을 지정하였기 때문에 에러가 발생하지 않는다.
```

#### 판별 유니언

```typescript
type Shape = { kind: "circle"; radius: number }
    | { kind: "square"; x: number }
    | { kind: "triangle"; x: number; y: number };

function area(s: Shape) {
    if (s.kind === 'circle') return Math.PI * s.radius * s.radius;
    else if (s.kind === 'square') return s.x * s.x;
    else return (s.x * s.y) / 2;
    // 모든 타입에 대해서 커버되었기에, 각 분기마다 타입이 정해지고, 해당 타입으로 변수가 사용된다.
    // 또한, 모두 커버가 되었고 area 의 리턴값은 결론적으로 number 가 되므로, area() => number 형태가 된다.
    // 하지만, 만약 모든 타입이 커버되지 않았다면, area() => number|undefined 가 된다.
}

function height(s: Shape) {
    if (s.kind === 'circle') return 2 * s.radius;
    else return s.x;
    // else 에 지정된 타입은 if 에 걸린 타입을 제외한 square/triagle 이 모두 가능하게 되며,
    // square/triagle 에 모두 x 가 존재하므로 사용할 수 있다.
    // 이와 같이 변수에 여러 타입이 매핑되더라도 공통된 값은 사용할 수 있다고 추론된다.
}
```

### 제네릭 Generics

* 타입에 변수를 제공하는 방법
* 배열이 일반적인 예시이며, 제네릭이 없는 배열은 어떤 것이든 포함이 가능한 배열이된다.

```typescript
type StringArray = Array<string>;
type NumberArray = Array<number>;
type ObjectArray = Array<{name : string}|SomeType>;

interface Backpack<Type> {
    add: (obj: Type) => void;
    get: () => Type;
}

declare const backpack: Backpack<string>;

const object = backpack.get();
// string

backpack.add(10);
// backpack 에 type 은 string 으로 정의하였으므로, 에러가 발생한다.
// @errors: 2345
```

### 구조적 타입 시스템

* ts 의 핵심 원칙 중 하나는 타입 검사가 값이 있는 형태에 집중한다.
    * 이는 '덕 타이핑' 또는 '구조적 타이핑' 이라고 불린다.

```typescript
interface Point {
    x: number;
    y: number;
}

function print(p: Point) {
    console.log(`${p.x}, ${p.y}`);
}

const point = {x: 1, y: 2};
print(point);
// point 에 Point 라는 타입임을 명시하지 않았지만,
// 같은 형태를 가지고 있으므로, 같은 타입으로 간주된다.
// -> 구조적 타입 시스템

const point2 = {x: 3, y: 4, z: 5};
print(point2);
// '3, 4'
// Point 에는 x, y 만 있으므로 일치된 객체만 사용된다.

class VirtualPoint {
    x: number;
    y: number;

    constructor(x: number, y: number) {this.x = x; this.y = y;}
}

const virtualPoint = new VirtualPoint(6, 7);
print(virtualPoint);
// '6, 7'
// 구조적으로 클래스와 객체가 형태를 따르는 방법에는 차이가 없다.
// 필요한 속성이 모두 존재한다면, ts 는 구현 세부 정보에 관계없이 일치하게 본다.
```

### 삭제된 구조적 타입

* ts 에서 객체는 정확히 단일 타입이 아니다.
* 인터페이스를 만족하는 객체를 생성할 때, 둘 사이의 선언적 관계가 없더라도 해당 인터페이스가 예상되는 곳에 해당 객체를 사용할 수 있다.
* ts 의 타입 시스템은 명목이 아닌 구조적이므로, 타입간의 관계는 특정 관계로 선언되었는지가 아닌 포함된 프로퍼티에 의해 결정된다.
    * 런타임시에는 지정한 타입은 어떠한 형태로도 존재하지 않는다.
    * 집합으로서의 타입 개념으로 보고, 집합의 멤버로 확인할 뿐이다.

```typescript
interface Point {
    x: number;
    y: number;
}

interface Name {
    name: string;
}

function printPoint(point: Point) {
    console.log(`x: ${point.x}, y: ${point.y}`);
}

function printName(val: Name) {
    console.log(`name: ${val.name}`);
}

const object = {
    x: 10,
    y: 20,
    name: 'foo',
}

printPoint(object); // x: 10, y: 20
printName(object); // name: foo
```

### 구조적 타입화의 결과

```typescript
class Empty {}

function fn(arg: Empty) {
    // do something
}

fn({foo: 123});
// 전달된 {foo: 123} 자체는 Empty 와 동일하지는 않지만
// Empty 의 구조를 확인하여 유효성 검사를 한다.
// 즉, 해당되는 타입에 모든 프로퍼티가 있다면 유효하다고 판단합니다.

class Car {
    drive() {
        // some logic
    }
}

class Golfer {
    drive() {
        // some other logic
    }
}

const w: Car = new Golfer();
// 위와 같은 형태는 오류가 아니다.
// 그 이유는 Car 와 Golfer 의 클래스 구조가 동일하기 때문이다.
// 이러한 형태는 혼란을 줄 수 있지만, 상관없는 클래스가 동일한 경우는 일반적이지 않은 형태이다.
```

### 반영 (Reflection)

```cs
static void PrintType<T>() {
    Console.writeLine(typeof(T).Name);
}
```

* ts 타입 시스템이 완벽히 지워졌으므로, 제네릭 타입 인자의 인스턴스화와 같은 정보는 런타임에 사용할 수 없다.
* js 에는 `typeof`/`instanceof` 와 같은 제한된 원시요소가 있지만, 연산자 타입이 지워진 코드의 출력에 존재한다.
    * ex) `typeof (new Car())` 는 `Car` 또는 `"Car"` 가 아닌 `"object"` 이다

### readonly 와 const

* js 에서 변수는 수정 가능함이 기본이지만, 참조가 수정 불가능함을 선언하기 위해 const 로 변수를 선언할 수 있다.
    * 참조 대상은 여전히 수정이 가능하다.
* ts 에서는 readonly 제어자를 사용하여 해당 변수는 변경 불가능하다고 명시할 수 있다.
* 매핑된 타입인 `Readonly<T>` 는 모든 프로퍼티를 readonly 로 만든다.
* 부작용을 일으키는 메서드를 제거하고 배열 인덱스에 대한 변경을 방지하는 특정 `ReadonlyArray<T>` 타입과, 이 타입에 대한 특수 구문이 있다.
* 배열과 객체 리터럴에서 동작하는 const-assertion 만 사용할 수 있다.

```typescript
const a = [1,2,3];
a.push(4);
a[0] = 100;
--------------------------------------------------

interface Rx {
    readonly x: number;
}
let rx: Rx = { x: 1 };
rx.x = 12;
// error
--------------------------------------------------

interface X {
    x: number;
}
let rx2: X = { x: 1 };
let rx3: Readonly<X> = { x: 1 };
rx2.x = 12;
// ok
rx3.x = 12;
// error
--------------------------------------------------

let foo: ReadonlyArray<number> = [1,2,3];
let bar: readonly number[] = [1,2,3];
foo.push(102); // error
bar[0] = 100; // error
--------------------------------------------------

let fuz = [1,2,3] as const;
fuz.push(100); // error
fuz[0] = 100; // error
```

#### 내장타입

* js 7개의 내장 타입

| 타입 | 설명 |
| ---- | ---- |
| Number | 배정밀도 IEEE 754 부동소수점 |
| String | 수정 불가능한 UTF-16 문자엻 |
| Boolean | true, false |
| Symbol | 보통 키로 사용하는 고유 값 |
| Null | 단위 타입과 동등 |
| Undefined | 단위 타입과 동등 |
| Object | 레코드와 유사 |
