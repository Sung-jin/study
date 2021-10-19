* ts 의 핵심 원칙 중 하나는 타입 검사가 값의 형태에 초점을 맞추고 있다는 점이다
* duck typing 또는 structural subtyping 이라고 한다

### 인터페이스

* 특정한 형태의 타입을 명시하여 해당 값이 최소한 interface 값 안에 있는 값이 존재한다는 타입 체크를 할 때 사용한다.
* 즉, interface 를 선언하고 해당 형태의 값임을 명시하면 컴파일러는 최소한 interface 의 형태를 가지고 있는지 타입 검사를 한다
  * 꼭 완전 같을 필요 없이, interface 의 모든 값을 가지고만 있으면 되고, 그 외에 다른 필드가 존재해도 된다
  * 하지만 선택적 프로퍼티로 명시한다면 해당 필드는 존재하지 않아도 된다

```typescript
function printLabel(labeledObj: { label: string }) {
    // 해당 함수의 인자값인 labeledObj 에 대한 타입 검사를 실행한다.
    // 여기에서는 label 이라는 string 키를 가진 인수값을 인자로 받는다
    console.log(labeledObj.label);
}

const myObj = {size: 10, label: 'some label'};
printLabel(myObj); // some label
// 실제로 넘겨준 인자값에는 label 이라는 키 외에 size 라는 number 필드가 존재하지만,
// label 이라는 string 값을 가지고 있기 때문에 컴파일러에서 타입 체크를 통과하게 된다
// 컴파일러는 최소한 필요한 프로퍼티가 있는지 검사한다

//-------------------------------------------------
interface LabeledValue {
    label: string;
}

function printLabel(labeledObj: LabeledValue) {
    // 필요한 곳에 타입을 바로 지정할 수 있지만,
    // interface 로 형태로 지정하고 해당 타입으로 지정할 수 있다
    console.log(labeledObj.label);
}

//-------------------------------------------------
interface OptionalPropery {
    id?: number;
    name: string;
    phone?: number;
}
// id, phone 의 경우 값이 있을 수 있고 undefined 일 수 있다.

function infoPerson(person: OptionalPropery) {
    if (!person.id) {
      console.log('new person');
      id = 100;
    }
    
    if (person.phone) console.log(`${person.name}'s phone : ${person.phone}`);
    else console.log(`name : ${person.name}`);
}

const foo: OptionalPropery = {
    name: 'foo1'
};
const bar: OptionalPropery = {
    id: 1,
    name: 'bar1'
};
const fuz: OptionalPropery = {
    id: 2,
    name: 'fuz1',
    phone: 1000000000
}
const foobar: OptionalPropery = {
    val: 'foobar1'
}
// foo, bar, fuz 모두 가능한 타입이다
// foobar 의 경우 필수 필드인 name 이 없으므로 컴파일에서 에러가 발생한다
// 또한 OptionalPropery 의 name 필드만 존재한다면 다른 어떠한 필드가 와도 가능하다
```

### Readonly properties

* 객체가 처음 생성될 때 지정된 값을 변경 할 수 없도록 할 때 읽기전용 프로퍼티(readonly)를 사용한다
* 또한 `ReadonlyArray<T>` 타입의 읽기전용 배열이 ts 에 존재하며, 처음 초기화된 값이 변경되지 않음을 보장한다
* const, readonly 둘다 읽기전용에 사용된다
  * const 의 경우 변수에 사용된다
  * readonly 의 경우 프로퍼티에 사용된다

```typescript
interface Point {
    readonly x: number;
    readonly y: number;
}

let p1: Point = { x: 10, y: 20 };
p1.x = 5; // 오류

let a: number[] = [];
let ro: ReadonlyArray<number> = [1,2,3,4];

ro[0] = 12;         // error
ro.push(5);         // error
ro.length = 100;    // error
a = ro; // error

a = ro as number[];
// 타입 단언으로 readonly array 를 일반 배열에 대입할 수 있다
```

### 초과 프로퍼티 검사

```typescript
interface SquareConfig {
    color?: string;
    width?: number;
}

function createSquare(config: SquareConfig): { color: string; area: number } {
    // ...
}

// error: Object literal may only specify known properties, but 'colour' does not exist in type 'SquareConfig'. Did you mean to write 'color'?
let mySquare = createSquare({ colour: "red", width: 100 });
// color 가 아닌 colour 로 전달되었다
// color 에 대한 프로퍼티는 없고 colour 프로퍼티가 있지만, 해당 프로퍼티는 중요하지 않다
// ts 의 경우 이럴때 에러가 발생한다
// 초과 프로퍼티 검사를 피하는 방법으로는 타입 단언을 사용하면 피할 수 있다
let mySquare = createSquare({ width: 100, opacity: 0.5 } as SquareConfig);

// 또는 문자열 인덱스 서명을 추가할 수 있다
interface SquareConfig {
  color?: string;
  width?: number;
  [propName: string]: any;
  // string 타입의 키를 가지는 어떠한 것도 올 수 있다
}

let squareOptions = { colour: "red" };
let mySquare = createSquare(squareOptions);
// color, width 라는 공통 프로퍼티는 하나 이상 존재해야 한다
// 하지만 위와 같이 하나라도 존재하지 않으므로 에러가 발생한다
```

### 함수 타입

* 인터페이스는 객체를 기술하는 것 외에 함수 ㅌ타입을 설명할 수 있다
* 인터페이스에 호출 서명을 전달하여 함수 타입을 기술할 수 있다
  * 매개변수 목록과 반환 타입만 주어진 함수 선언과 비슷하다
  * 매개변수는 이름과 타입이 모두 필요하다

```typescript
interface SearchFunc {
  (source: string, subString: string): boolean;
}

let mySearch: SearchFunc
mySearch = function (src: string, sub: string) {
    let booleanVal: boolean = false;
    // 정의된 함수의 이름과 꼭 같을 필요는 없으며, 타입 형태만 같으면 된다
    ...
  
    return booleanVal;
    // return "some String";
    // SearchFunc 의 리턴 타입은 boolean 이기 때문에 에러가 발생한다
    // error: Type '(src: string, sub: string) => string' is not assignable to type 'SearchFunc'.
    // Type 'string' is not assignable to type 'boolean'.
}
```

### 인덕서블 타입

* 타입을 인덱스로 기술할 수 있다
* 해당 반환 유형과 함께 객체를 인덱싱하는 데 사용할 수 있는 타입을 기술하는 인덱스 시그니처를 가지고 있다
* 두 타입의 인덱서를 모두 지원하는 것은 가능하지만, 숫자 인덱서에서 반환된 타입은 반드시 문자열 인덱서에서 반환된 타입의 하위 타입이어야 한다
  * number 로 인덱싱될 때, js 는 실제로 객체를 인덱싱하기 전에 string 으로 변환하기 때문이다
* 인덱스의 할당을 막기 위해서 인덱스 시그니처를 읽기 전용으로 만들 수 있다

```typescript
interface StringArray {
    [index: number]: string;
}

let myArray: StringArray;
myArray = ['foo', 'bar'];

interface ReadonlyStringArray {
    readonly [index: number]: string;
}

const myReadonlyArray: ReadonlyStringArray = ['foo', 'bar'];
myReadonlyArray[1] = 'fuz'; // error
```

### 인터페이스 구현하기

```typescript
interface ClockInterface {
  currentTime: Date;
  setTime(d: Date): void;
}

class Clock implements ClockInterface {
  // 인페이스는 클래스의 public, private 모두보다는 public 을 기술한다
  currentTime: Date = new Date();
  setTime(d: Date) {
      this.currentTime = d;
  }

  constructor(h: nnumber, m: number) {}
}
```

### 인터페이스 확장하기

* 인터페이스도 확장이 가능하다
* 한 인터페이스의 멤버를 다른 인터페이스에 복사하는 것을 가능하게 해준다
* 또한 여러 인터페이스를 확장할 수 있고, 모든 인터페이스의 조합을 만들어낼 수 있다

```typescript
interface Shape {
    color: string;
}

interface PenStroke {
    penWidth: number;
}

interface Square extends Shape, PenStroke {
    sideLength: number;
}

let square = {} as Square;
square.color = 'blue';
square.sideLength = 10;
square.penWidth = 5.0;
```

### 하이브리드 타입

* js 에서는 존재하는 다양한 타입들을 기술할 수 있다
* js 의 동적이고 유연한 특성 때문에 몇몇 타입의 조합으로 동작하는 객체를 마주할 수 있다

```typescript
interface Counter {
  (start: number): string;
  intervale: number;
  reset(): void;
}

function getCounter(): Count {
    let counter = (function (start: number) {}) as Counter;
    counter.intervale = 123;
    counter.reset = function () {};
    return counter;
}

const c = getCOunter();
c(10);
c.reset();
c.interval = 5.0;
```

### 클래스를 확장한 인터페이스

* 인터페이스 타입이 클래스 타입을 확장하면 클래스의 멤버는 상속받지만 구현은 상속받지 않는다
  * 인터페이스가 구현을 제공하지 않고, 클래스의 멤버 모두를 선언한 것과 같다
* 인터페이스는 기초 클래스의 private, protected 멤버도 상속 받는다
  * 인터페이스가 private/protected 멤버를 포함한 클래스를 확장할 수 있다는 뜻
  * 인터페이스 타입은 그 클래스나 하위 클래스에 의해서만 구현될 수 있다
* 특정 프로퍼티를 가진 하위클래스에서만 코드가 동작하도록 지정하는데 유용하다

```typescript
class Control {
    private state: any;
}

interface SelectableControl extends Control {
    // private state 프로퍼티를 포함하여 Control 의 모든 멤버를 가지고 있다
    // state 의 경우 private 멤버이므로 SelectableControl 를 구현하는 것은 Control 의 자식에만 가능하다
    select(): void;
}

class Button extends Control implements SelectableControl {
    select() { }
}
class TextBox extends Control {
    select() { }
}
// Button 과 TextBox 는 Control 을 상속받기 때문에
// Control 의 모든 멤버를 가지고 있다

class Image implements SelectableControl {
    private state: any;
    select() { }
}
class Location {}
```
