### 교차타입

* 여러 타입을 하나로 결합하는 것을 교차타입이라 한다
* 기존 타입을 합쳐 필요한 모든 기능을 가진 하나의 타입을 얻을 수 있다

```typescript
function extend<First, Second>(first: First, second: Second): First & Second {
    const result: Partial<First & Second> = {};
    for (const prop in first) {
        if (first.hasOwnProperty(prop)) {
            (result as First)[prop] = first[prop];
        }
    }
    for (const prop in second) {
        if (second.hasOwnProperty(prop)) {
            (result as Second)[prop] = second[prop];
        }
    }
    return result as First & Second;
}

class Person {
    constructor(public name: string) { }
}

interface Loggable {
    log(name: string): void;
}

class ConsoleLogger implements Loggable {
    log(name) {
        console.log(`Hello, I'm ${name}.`);
    }
}

const jim = extend(new Person('Jim'), ConsoleLogger.prototype);
jim.log(jim.name);
```

### 유니언 타입

* 유니언 타입은 값이 여러 타입 중 하나임을 설명한다
* 유니언 타입을 값으로 가지고 있으면, 유니언에 있는 모든 타입에 공통인 멤버만 접근이 가능하다

```typescript
function padLeft(value: string, padding: any) {
    // padding 은 any 로 선언되어 있고, 아래 if 분기에서 타입 체크를 진행한다
    // 즉, padLeft(..., true) 와 같이 number/string 이 아닌 타입도 들어갈 수 있다
    if (typeof padding === 'number') { ... }
    if (typeof padding === 'string') { ... }
}

function padLeft(value: string, padding: number|string) { ... }
// 위와 같이 padding 에 number/string 만 허용된다는
// 유니언 타입을 표시하여 제한할 수 있다
let indentedString = padLeft("Hello world", true); // 컴파일 중에 오류

// ------------------------------------------------------------
interface Bird {
  fly();
  layEggs();
}

interface Fish {
  swim();
  layEggs();
}

function getSmallPet(): Fish | Bird { ... }

let pet = getSmallPet();
pet.layEggs(); // 성공
pet.swim();    // 오류
```

### 타입 가드와 차별 타입

* 유니언 타입은 값의 타입이 겹쳐질 수 있는 상황을 모델링하는데 유용하다
* 유니언 타입의 모든 구성 성분을 가지고 있다고 보장되는 멤버에만 접근 할 수 있다

```typescript
const pet = getSmallPet();

if (pet.swim) {
    pet.swim();
}
else if (pet.fly) {
    pet.fly();
}
// 이렇게 각 프로퍼티들에 접근하는 것은 오류를 발생

if ((pet as Fish).swim) {
    (pet as Fish).swim();
} else if ((pet as Bird).fly) {
    (pet as Bird).fly();
}
// 위와 같이 타입 단언을 사용해야 오류가 발생하지 않는다
```

### 사용자-정의 타입 가드

* 검사를 실시했을 때, 분기별에서 해당 타입을 알 수 있는 방법에는 `타입 가드` 라는 것이 있다
  * 타입 가드는 스코프 안에서의 타입을 보장하는 런타임 검사를 수행한다는 표현식이다

#### 타입 서술어 사용

```typescript
function isFish(pet: Fish | Bird): pet is Fish {
    return (pet as Fish).swim !== undefined;
    // pet is Fish 가 타입 서술어이다
    // xxx is Fish 에서 xxx 는 반드시 현재 함수 시그니처의 매개변수 이름이어야 한다
    // 해당 예제에서는 Fish 또는 Bird 이어야 한다
}

if (isFish(pet)) {
    pet.swim();
    // isFish 라는 함수를 통해서 pet 변수는 해당 스콥에서
    // Fish 타입임을 알린다
} else {
    pet.fly();
}
```

#### in 연산자 사용

* `in` 연산자는 타입을 좁히는 표현으로 작용한다
* `n in x` 라는 표현에서 n 은 문자열 또는 문자열 리터럴 타입이고, x 는 유니언 타입이다

```typescript
function move(pet: Fish|Bird) {
    if ('swim' in pet) {
        // 해당 스콥에서 swim 이라는 프로퍼티가 pet 에 존재하므로,
        // Fish 라는 타입으로 좁혀지고
        return pet.swim();
    } else {
        // if 에서 벗어날 경우, swim 이 없는 타입으로 좁혀진다
        return pet.fly();
    }
}

```

#### typeof 타입 가드

```typescript
function padLeft(value: string, padding: string | number) {
    if (typeof padding === 'number') {
        // typeof 키워드를 통해 변수를 특정 타입으로 명시할 수 있다
        // typeof 를 통해 추론할 수 있는 타입으로는 number/string/boolean/symbol 이 존재한다
        // 그 외으 타입을 비교하는 것을 막지는 않지만, 타입 가드의 표현식으로 인지되지 않는다
        return Array(padding + 1).join(" ") + value;
    }
    if (typeof padding === 'string') {
         return padding + value;
    }
    throw new Error(`Expected string or number, got '${padding}'.`);
}
```

#### instanceof 타입 가드

* `instanceof` 의 오른쪽은 생성자 함수여야 하며, ts 는 다음과 같은 형태로 타입을 추론한다
  1. 함수의 `prototype` 프로퍼티 타입이 any 가 아닌 경우
  1. 타입의 생성자 시그니처에서 반환된 유니언 타입인 경우

```typescript
interface Foo {
  log(val): string
}

class Fuz implements Foo {
  log(val) {
    console.log(`val: ${val}`);
    return val;
  }
}

class Foobar implements Foo {
  constructor(private prefix: string) {}
  log(val) {
    return `${this.prefix} ${val}`;
  }
}

function getRandomFoo() {
  return Math.random() < 0.5 ?
          new fuz() :
          new Foobar('value');
}

const foo: Foo = getRandomFoo();

if (foo instanceof Fuz) {}
if (foo instanceof Foobar) {}
// 각 분기의 블록 스코프에는 해당되는 타입으로 취급된다
```

#### Nullable type

* ts 에서 기본적으로 타입 검사시 `null`/`undefined` 를 아무것에나 할당할 수 있다고 간주한다
  * 이러한 문제는 `--strictNullChecks` 플래그를 통해 변수를 선언할 때 자동으로 `null`/`undefined` 을 포함하지 않게 할 수 있다
* `null`/`undefined` 를 유니언 타입을 사용하여 명시적으로 포함할 수 있다

```typescript
let foo = 'hello';
foo = null; // null 은 string 에 할당 할 수 없다

let bar: string|null = 'world';
bar = null; // ok
bar = undefined; // undefined 는 string|null 에 할당 할 수 없다
```

#### 선택적 매개변수와 프로퍼티

* `--stringNullChecks` 를 적용하고 선택적 매개변수를 사용하면 `undefined` 가 자동으로 타입에 추가된다

```typescript
function f(x?: number) {}
f(1); // ok
f(undefined); // ok
f(null); // error

class C {
    a?: number;
}
const c = new C();
c.a = 1; // ok
c.a = undefined; // ok
c.a = null; // error
```

#### 타입 가드와 타입 단언

```typescript
function f(a: string|null) {
    return a || 'default';
    // a 에 값이 없으면 뒤에 있는 값이 기본값으로 리턴된다
}

function f2(name: string|null) {
    return name!.length;
    // ! 연산자를 통해서 null 과 undefined 를 수동으로 제거할 수 있다
}
```

#### 타입 별칭

* 타입의 새로운 이름을 만들때 타입 별칭을 사용할 수 있다
  * 하지만, 실제로 새로운 타입을 생성하는 것은 아니다
* 타입 별칭은 제네릭이 될 수 있다
* 타입 별칭을 선언의 오른족 이외에 사용하는 것은 불가능하다

```typescript
type Name = string;
type NameResolver = () => string;
type NameOrResolver = Name | NameResolver;
function getName(n: NameOrResolver): Name {
    return typeof n === 'string' ? n : n();
}

type Container<T> = { value: T };
// 타입 제네릭
type Tree<T> = {
    value: T;
    left: Tree<T>;
    right: Tree<T>;
}
// 프로퍼티 안에서 자신을 참조가 가능하다
```

#### 인터페이스 vs 타입 별칭

* 인터페이스는 어디에서나 사용할 수 있는 새로운 이름을 만들 수 있으나, 타입 별칭은 새로운 이름을 만들지 못한다
* ts 이전 버전에서 타입 별칭은 extend 하거나 implement 가 불가능하였으나, 2.7 이후 버전에서는 타입 별칭은 교차 타입을 생성함으로써 extend 할 수 있게 되었다
  * `type Cat = Animal & { purrs: true }`
* 소프트웨어의 이상적인 특징은 확장에 개발되어 있기 때문에 타입 별칭보다는 인터페이스를 사용하는게 좋다
* 인터페이스로 어떠한 형태를 표현할 수 없고 유니언이나 튜플타입을 사용해야 한다면, 일반적으로 타입 별칭을 사용한다

```typescript
type Alias = { num: number }
interface Interface {
    num: number;
}
declare function aliased(arg: Alias): Alias;
declare function interfaced(arg: Interface): Interface;
// 해당 함수의 리턴값이 ide 에서 aliased 는 객체를, interfaced 는 Interface 를 반환한다고 한다
```

#### 문자열 리터럴 타입

* 문자열 리터럴 타입은 문자열에 값을 정확하게 지정할 수 있다
* 문자열 리터럴 타입은 유니언 타입, 타입 가드, 타입 별칭과 잘 결합된다

```typescript
type Easing = "ease-in" | "ease-out" | "ease-in-out";
class UIElement {
    animate(dx: number, dy: number, easing: Easing) {
        if (easing === "ease-in") { ... }
        else if (easing === "ease-out") { ... }
        else if (easing === "ease-in-out") { ... }
    }
}

let button = new UIElement();
button.animate(0, 0, "ease-in");
button.animate(0, 0, "uneasy"); // error. uneasy 는 Easing 에 정의되어 있지 않음
// Argument of type '"uneasy"' is not assignable to parameter of type '"ease-in" | "ease-out" | "ease-in-out"'

// 문자열 리터럴 타입은 오버로드를 구별하기 위해 같은 방법으로 사용할 수 있다
function createElement(tagName: "img"): HTMLImageElement;
function createElement(tagName: "input"): HTMLInputElement;
// ... 더 많은 오버로드 ...
function createElement(tagName: string): Element {
    // ... 이곳에 코드를 ...
}
```

#### 숫자 리터럴 타입

```typescript
function dice(): 1|2|3|4|5|6 {
    // ...
}
// 위와 같이 ts 에는 숫자 리터럴 타입을 가지고 있지만
// 숫자 리터럴 타입을 명시적으로 작성되는 경우는 거의 없다

function foo(x: number) {
    if (x !== 1 || x !== 2) {}
    // !== 연산자는 1,2 타입에 적용할 수 없다
    // 즉, x 는 2와 비교될 때, 반드시 1 이어야 하지만 위의 검사가 유효하지 않는 비교를 의미한다
}
```

#### 판별 유니언

* 싱글톤 타입, 유니언 타입, 타입 가드, 타입 별칭을 합칠 수 있다
* 판별 유니언은 함수형 프로그래밍에서 유용하다
* 다음과 같은 요소가 존재한다
  1. 공통 싱글톤 타입 프로퍼티를 갖는 타입 - 판별식
  1. 해당 타입들의 유니언을 갖는 타입 별칭 - 유니언
  1. 공통 프로퍼티의 타입 가드
  
```typescript
interface Square {
    kind: 'square';
    size: number;
}
interface Rectangle {
    kind: 'rectangle';
    width: number;
    height: number;
}
interface Circle {
    kind: 'circle';
    radius: number;
}
type Shape = Square | Rectangle | Circle;
// 판별 유니언 선언

function area(s: Shape) {
    switch (s.kind) {
        case 'square': return s.size * s.size;
        case 'rectangle': return s.height * s.width;
        case 'circle': return Math.PI * s.radius ** 2;
    }
}
```

#### 엄격한 검사

```typescript
type Shape = Square | Rectangle | Circle | Triangle;

function assertNever(x: never): never {
    throw new Error('Unexpected object: ' + x);
}

function area(s: Shape): number {
    switch (s.kind) {
        case 'square': return s.size * s.size;
        case 'rectangle': return s.height * s.width;
        case 'circle': return Math.PI * s.radius ** 2;
    }
    // Triangle 에 대한 처리를 하지 않아서 에러가 발생한다
    // --strictNullChecks 를 설정하여 강제로 에러를 없앨 수 있다
    // 하지만, 위의 옵션을 키게 되면 해당 함수의 반환 타입은 number|undefined 가 되면서
    // 명시적으로 number 타입을 가지고 있는 곳에 사용하게 되면 에러가 발생된다
    // 또는 컴파일러가 완전함을 검사하기 위해 사용하는 never 타입을 사용할 수 있다
    // default: return assertNever(s); 와 같이 사용하여 빠진 케이스가 있다면 여기서 오류 발생시킬 수 있다
}
```

### 다형성 this 타입

* 다형성 `this` 타입은 포함하는 클래스나 인터페이스의 하위 타입을 나타낸다
  * F-bounded polymorphism 이라고 부른다

```typescript
class BasicCalculator {
    public constructor(protected value: number = 0) { }
    public currentValue(): number {
        return this.value;
    }
    public add(operand: number): this {
        this.value += operand;
        return this;
    }
    public multiply(operand: number): this {
        this.value *= operand;
        return this;
    }
    ...
}
// this 를 반환하는 함수를 사용하면, 자기 자신을 반환하기 때문에 계속 자신의 메소드를 사용할 수 있다

class ScientificCalculator extends BasicCalculator {
    public constructor(value = 0) {
        super(value);
    }
    public sin() {
        this.value = Math.sin(this.value);
        return this;
    }
    ...
}
// 또한 this 타입을 사용하였기에 이를 extend 할 수 있다
// 해당 클래스로 생성된 객체는 sin 함수 외에 상속받은 모든 객체의 this 는
// ScientificCalculator 클래스로 생성된 객체이다

const result = new ScientificCalculator(2)
        .multiply(5)
        .sin()
        .add(1)
        .currentValue();
```

### 인덱스 타입

* 동적인 프로퍼티 이름을 사용하는 코드를 컴ㅍ파일러가 검사할 수 있다
* `keyof T` 는 인덱스 타입 쿼리 연산자이다
  * any 타입인 T 에 대해서 keyof T 는 알려지고 공개된 프로퍼티 이름들의 유니언이다
  * `keyof {id: number, name: string}` 의 경우 `id | name` 이 된다
  * keyof 를 사용하면 알려진 프로퍼티가 추가될 때 마다 자동으로 업데이트 된다
  * 미리 프로퍼티 이름을 알 수 없을 때, 제네릭 컨텍스트에 keyof 를 사용할 수 있다

```typescript
function pluck<T, K extends keyof T>(o: T, propertyNames: K[]): T[K][] {
    return propertyNames.map(n => o[n]);
}

interface Car {
    manufacturer: string;
    model: string;
    year: number;
}
let taxi: Car = {
    manufacturer: 'Toyota',
    model: 'Camry',
    year: 2014
};

let makeAndModel: string[] = pluck(taxi, ['manufacturer', 'model']);
// manufacturer/model 의 경우 string 이므로 타이핑된 문자열 배열로 만들 수 있다
// 컴파일러는 manufacturer/model 가 Car 의 프로퍼티인지 검사한다

let modelYear = pluck(taxi, ['model', 'year'])
// 한 타입이 아닌 프로퍼티를 추출할 경우 해당 타입들을 가지는 타입이 추출된다
// 위의 예시에서는 (string|number)[] 가 추출된다

function getProperty<T, K extends keyof T>(o: T, propertyName: K): T[K] {
    return o[propertyName];
    // o[propertyName]는 T[K] 타입이다
}

let name: string = getProperty(taxi, 'manufacturer');
let year: number = getProperty(taxi, 'year');
// T[K] 를 반환하기 때문에 컴파일러는 실제 키의 타입을 인스턴스화한다
// 즉, getProperty 의 반환 타입은 요청한 프로퍼티에 따라 달라진다
```

#### 인덱스 타입과 인덱스 시그니처

* 인덱스 시그니처 매개변수 타입은 'string' 또는 'number' 이어야 한다
* 문자열 인덱스 시그니처 타입일 경우 `keyof T` 는 `string | number` 가 된다
  * `obj[42]` 나 `obj['42']` 에 접근할 수 있기 떄문
* `T[string]` 은 인덱스 시그니처의 타입이다

```typescript
interface Dictionary<T> {
    [key: string]: T;
}
let keys: keyof Dictionary[number]; // string | number
let value: Dictionary<number>['foo']; // number

interface NumberKeyDictionary<T> {
    [key: number]: T;
}
let numberKeys: keyof NumberKeyDictionary[number]; // number
// let numberValue: NumberKeyDictionary<number>['foo']; // error
let numberValue: NumberKeyDictionary<number>[42]; // number
```

### 매핑 타입

* ts 에서는 매핑 타입을 기반으로 새로운 타입을 만드는 방법을 제공한다
* 모든 프로퍼티를 readonly 또는 선택적으로 만들 수 있다

```typescript
type Readonly<T> = {
    readonly [P in keyof T]: T[P];
}
type Partial<T> = {
    [P in keyof T]?: T[P];
}

type FooPartial = Partial<Foo>;
type ReadonlyFoo = Readonly<Foo>;
// 위와 같이 사용할 수 있다

type PartialWithSomeKey<T> = {
    [P in keyof T]?: T[P];
} & { someKey: string }
// 위와 같이 교차 타입을 활용하면 기존의 타입에
// 특정 키가 추가된 타입을 선언할 수 있다
type PartialWithSomeKey<T> = {
    [P in keyof T]?: T[P];
    someKey: string;
    // 하지만 이렇게 사용하면 오류가 발생한다
}

type Keys = 'option1' | 'options2';
type Flags = { [K in Keys]: boolean };
type Flags = {
    option1: boolean;
    option2: boolean;
}
// 위의 타입과 같은 결과가 발생한다

type Pick<T, K extends keyof T> = {
    [P in K]: T[P];
}
type Record<K extends keyof any, T> = {
    [P in K]: T;
}
// 참고로 ts 표준 라이브러리에 위의 Readonly<T> 와 Partial<T> 및
// Pick, Record 이 포함되어 있다
// 참고로 Record 는 동형이 아니다
```

#### 매핑 타입의 추론

* 언래핑 추론은 동형 매핑된 타입에만 동작한다
  * 동형이 아니면 언래핑 함수에 명시적인 타입 매개변수를 주어야 한다

```typescript
type Proxy<T> = {
    get(): T;
    set(value: T): void;
}
type Proxify<T> = {
    [P in keyof T]: Proxy<T[P]>;
}
function proxify<T>(o: T): Proxify<T> {
    // ... 프록시 래핑 ...
}
let proxyProps = proxify(props);

function unproxify<T>(t: Proxify<T>): T {
    let result = {} as T;
    for (const k in t) {
        result[k] = t[k].get();
    }
    return result;
}
let unproxyProps = unproxify(proxyProps);
```

### 조건부 타입

* `T extends U ? X : Y` 와 같은 형태로 조건부 타입을 선언할 수 있다
  * 위 예제는 T 가 U 에 할당될 수 있으면 타입은 X 가 되고, 아니면 Y 가 된다
  * 즉, X 와 Y 로 결정되거나 지연될지, 타입 시스템이 T 가 항상 U 에 할당할 수 있는지에 대해 충분한 정보를 가지고 있는지 여부로 결정된다

```typescript
declare function f<T extends boolean>(x: T): T extends true ? string : number;
let x = f(Math.random() < 0.5)
// 타입은 string | number 로 즉시 결정된다
```

#### 분산 조건부 타입

* 검사된 타입이 벗겨진 타입 매개변수인 조건부 타입을 분산 조건부 타입 이라고 한다
  * T 에 대한 타입 인수 A|B|C 를 사용하여 조건부 타입을 사용할 경우
    * `(A extends U ? X : Y) | (B extends U ? X : Y) | (C extends U ? X : Y)`

```typescript
type TypeName<T> = 
        T extends string ? "string" :
        T extends number ? "number" :
        T extends boolean ? "boolean" :
        T extends undefined ? "undefined" :
        T extends Function ? "function" :
        "object";

type T10 = TypeName<string|(() => void)>; // "string" | "function"
type T12 = TypeName<string | string[] | undefined>;  // "string" | "object" | "undefined"
type T11 = TypeName<string[] | number[]>; // "object"
// 분산 조건부 타입에 의해, 조건부 타입 내의 T 에 대한 참조는 유니언 타입의 개별 성분으로 결정된다

type BoxedValue<T> = { value: T };
type BoxedArray<T> = { array: T[] };
type Boxed<T> = T extends any[] ? BoxedArray<T[number]> : BoxedValue<T>;

type T20 = Boxed<string>;  // BoxedValue<string>;
type T21 = Boxed<number[]>;  // BoxedArray<number>;
type T22 = Boxed<string | number[]>;  // BoxedValue<string> | BoxedArray<number>;
// T 가 분기 안에서 추가 제약조건 any[] 를 가지고 T[number] 로 배열의 요소 타입을 참조할 수 있다

type Diff<T, U> = T extends U ? never : T;  // U에 할당할 수 있는 타입을 T에서 제거
type Filter<T, U> = T extends U ? T : never;  // U에 할당할 수 없는 타입을 T에서 제거

type T30 = Diff<"a" | "b" | "c" | "d", "a" | "c" | "f">;  // "b" | "d"
type T31 = Filter<"a" | "b" | "c" | "d", "a" | "c" | "f">;  // "a" | "c"
type T32 = Diff<string | number | (() => void), Function>;  // string | number
type T33 = Filter<string | number | (() => void), Function>;  // () => void

type NonNullable<T> = Diff<T, null | undefined>;  // T에서 null과 undefined를 제거

type T34 = NonNullable<string | number | undefined>;  // string | number
type T35 = NonNullable<string | string[] | null | undefined>;  // string | string[]

function f1<T>(x: T, y: NonNullable<T>) {
    x = y;  // 성공
    y = x;  // 오류
}

function f2<T extends string | undefined>(x: T, y: NonNullable<T>) {
  x = y;  // 성공
  y = x;  // 오류
  let s1: string = x;  // 오류
  let s2: string = y;  // 성공
}
// 다음과 같이 조건부 타입의 분산 프로퍼티는 유니언 타입을 필터링하는데 사용할 수 있다

type FunctionPropertyNames<T> = { [K in keyof T]: T[K] extends Function ? K : never }[keyof T];
type FunctionProperties<T> = Pick<T, FunctionPropertyNames<T>>;

type NonFunctionPropertyNames<T> = { [K in keyof T]: T[K] extends Function ? never : K }[keyof T];
type NonFunctionProperties<T> = Pick<T, NonFunctionPropertyNames<T>>;

interface Part {
    id: number;
    name: string;
    subparts: Part[];
    updatePart(newName: string): void;
}

type T40 = FunctionPropertyNames<Part>;  // "updatePart"
type T41 = NonFunctionPropertyNames<Part>;  // "id" | "name" | "subparts"
type T42 = FunctionProperties<Part>;  // { updatePart(newName: string): void }
type T43 = NonFunctionProperties<Part>;  // { id: number, name: string, subparts: Part[] }
// 조건부 타입을 이용한 매핑 타입

type ElementType<T> = T extends any[] ? ElementType<T[number]> : T; // 오류
// 유니언과 교차 타입과 유사하게 조건부 타입은 재귀적으로 자기 자신을 참조할 수 없다
```
