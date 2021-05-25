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

### 제네릭 Generics

* 타입에 변수를 제공하는 방법
* 배열이 일반적인 예시이며, 제네릭이 없는 배열은 어떤 것이든 포함이 가능한 배열이된다.

```typescript
type StringArray = Array<string>;
type NumberArray = Array<number>;
type ObjectArray = Array<{name : string}|SomeType>;

interface Backpack<Type> {
    add: (obj: Type) => void;
    kget: () => Type;
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
