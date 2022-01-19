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
