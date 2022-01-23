## 소개

* ts 는 공통 타입 변환을 용이하게 하기 위해 몇갖지 유틸리티 타입을 제공한다
* 해당 유틸리티들은 전역으로 사용이 가능하다

### Partial<T>

* T 의 모든 프로퍼티를 선택적으로 만드는 타입을 구성한다 
* 주어진 타입의 모든 하위 타입 집합을 나타내는 타입을 반환한다

```typescript
interface SomeThing {
    foo: string;
    bar: string;
}

function update(someThing: SomeThing, fieldToUpdates: Partial<SomeThing>) {
    return {...someThing, ...fieldToUpdates};
}

const someThing = {
    foo: '1',
    bar: '2',
}

const someThing2 = update(someThing, {
    foo: '3',
});
// someThing2 = {foo: '3', bar: '2'}
```

### Readonly<T>

* T 의 모든 프로퍼티를 **읽기 전용**으로 설정한 타입을 구성한다
  * 해당 타입의 프로퍼티는 재할당할 수 없다
* 런타임에 실패할 할당 표현식을 나타낼 때 유용하다
  * `function freeze<T>(obj: T): Readonly<T>;`

```typescript
interface SomeThing {
    foo: 'bar',
}

const ssomeThing: Readonly<SomeThing> = {
    foo: 'fuz',
}

someThing.foo = 'baz'; // 오류
```

### Record<K, T>

* 타입 T 의 프로퍼티의 집합 K 로 타입을 구성한다
* 타입의 프로퍼티들을 다른 타입에 매핑시키는데 사용할 수 있다

```typescript
interface SomeThing {
    foo: string;
}

type SomeThingBig = 'bar' | 'fuz' | 'baz';

const someThingBig: Record<SomeThingBig, SomeThing> = {
    bar: {foo: '1'},
    fuz: {foo: '1'},
    baz: {foo: '1'},
}

```

### Pick<T, K>

* T 에서 프로퍼티 K 의 집합을 선택해 타입을 구성한다

```typescript
interface SomeThingBig {
    foo: string;
    bar: string;
    fuz: string;
    baz: string;
}

type SomeThing = Pick<SomeThingBig, 'foo', 'bar'>;

const someThing: SomeThing = {
    foo: 'fuz',
    bar: 'baz'
}
```

### Omit<T, K>

* T 에서 모든 프로퍼티를 선택한 다음 K 를 제거한 타입을 구성한다

```typescript
interface SomeThingBig {
    foo: string;
    bar: string;
    fuz: string;
    baz: string
}

type SomeThing = Omit<SomeThingBig, 'fuz'>;

const someThing: SomeThing = {
    foo: '1',
    bar: '2',
    baz: '3',
}
```

### Exclude<T, U>

* T 에서 U 에 할당할 수 있는 모든 속성을 제외한 타입을 구성한다

```typescript
type T0 = Exclude<'a'|'b'|'c'|'e'|'f', 'a'|'c'|'f'>; // 'b'|'e'
type T1 = Exclude<string|number|(() => void), Function>; // string|number
```

### Extract<T, U>

* T 에서 U 에 할당 할 수 있는 모든 속성을 추출하여 타입을 구성한다

```typescript
type T0 = Extract<'a'|'b'|'c'|'d'|'e', 'a'|'c'|'f'>; // 'a'|'c'
type T1 = Extract<string|number|(() => void), Function>; // () => void
```

### NonNullable<T>

* T 에서 `null`/`undefined` 를 제외한 타입을 구성한다

```typescript
type T0 = NonNullable<string|number|null|undefined>; // string|number
```

### Parameters<T>

* 함수 타입 T 의 매개변수 타입들의 튜플 타입을 구성한다

```typescript
declare function f1(arg: { a: number, b: string }): void
type T0 = Parameters<() => string>; // []
type T1 = Parameters<(s: string) => string>; // [string]
type T2 = Parameters<typeof f1>; // [{a: number, b: string}]
type T3 = Parameters<any>; // unkown[]
type T4 = Parameters<never>; // never
type T5 = Parameters<string>; // 오류
type T6 = Parameters<Function>; // 오류
```

### ConstructorParameters<T>

* 생성자 함수 타입의 모든 매개변수 타입을 추출한다
* 모든 매개변수 타입을 가지는 튜플 타입을 생성한다
  * T 가 함수가 아닌 경우 never

```typescript
type T0 = ConstructorParameters<ErrorConstructor>;  // [(string | undefined)?]
type T1 = ConstructorParameters<FunctionConstructor>;  // string[]
type T2 = ConstructorParameters<RegExpConstructor>;  // [string, (string | undefined)?]
```

### ReturnType<T>

* 함수 T 의 반환 타입으로 구성된 타입을 만든다

```typescript
declare function f1(): { a: number, b: string };

type T0 = ReturnType<() => string>; // string
type T1 = REturnType<(<T>() => T)>; // {}
type T2 = ReturnType<(<T extends U, U extends number[]>() => T)>; // number[]
type T3 = ReturnType<typeof T1>; // {a: number, b: string}
type T4 = ReturnType<any>; // any
type T5 = ReturnType<never>; // any
type T6 = ReturnType<string>; // 오류
type T6 = ReturnType<Function>; // 오류
```

### InstanceType<T>

* 생성자 함수 타입 T 의 인스턴스 타입으로 구성된 타입을 만든다

```typescript
class C {
    x = 0;
    y = 0;
}

type T0 = InstanceType<typeof C>; // C
type T1 = InstanceType<any>; // any
type T2 = InstanceType<never>; // any
type T3 = InstanceType<string>; // 오류
type T4 = InstanceType<Function>; // 오류
```

### Required<T>

* T 의 모든 프로퍼티가 필수로 설정된 타입을 구성한다

```typescript
interface Foo {
    fuz?: number;
    baz?: string;
}

const foo: Required<Foo> = { fuz: 0 }; // 오류 baz 값이 없음
```

### ThisParameterType

* 함수 타입의 this 매개변수의 타입 또는 함수 타입에 this 매개변수가 없을 경우 unknown 을 추출한다
* `--strictFunctionTypes` 가 활성화 되어야 올바르게 동작한다

```typescript
function toHex(this: Number) {
    return this.toString(16);
}

function numberToString(n: ThisParameterType<typeof toHex>) {
    return toHex.apply(n);
}
```

### OmitThisParameter

* 함수 타입에서 this 매개변수를 제거한다
* `--strictFunctionTypes` 가 활성화 되어야 올바르게 동작한다

```typescript
function toHex(this: Number) {
    return this.toString(16);
}

const fiveToHex: OmitThisParameter<typeof toHex> = toHex.bind(1000);

console.log(fiveToHex()); // 3e8
```

### ThisType<T>

* 변형된 타입을 반환하지 않고, 문맥적 this 타입에 표시하는 역할을 한다
* `--noImplicitThis` 가 활성화 해야 한다
* `thisType<T>` 마커 인터페이스는 단지 `lib.d.ts` 에 선언된 빈 인터페이스이다
  * 객체 리터럴의 문맥적 타입으로 인식되는 것을 넘어, 그 인터페이스는 빈 인터페이스처럼 동작한다

```typescript
type ObjectDescriptor<D, M> = {
    data?: D;
    methods?: M & ThisType<D & M>;
    // 메서드 안의 this 타입은 D & M 타입이다
}

function makeObject<D, M>(desc: ObjectDescriptor<D, M>): D & M {
    const data: object = desc.data || {};
    const methods: object = desc.methods || {};
    return {...data, ...methods}  as D & M;
}

const obj = makeObject({
  data: {x: 0, y: 0},
  methods: {
      moveBy(dx: number, dy: number) {
          this.x += dx;
          this.y += dy;
          // 강하게 타입이 정해진 this
      }
  }
});
```
