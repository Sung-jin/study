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
