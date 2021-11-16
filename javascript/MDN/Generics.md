### 제네릭

* 단일 타입이 아닌 다양한 타입을 지정
  * 이러한 제네릭을 이용한다면 다양하게 동작하는 컴포넌트를 작성할 수 있음
  * 여러 타입의 컴포넌트나 자신만의 타입을 사용할 수 있음

```typescript
function someThing(some: string) {
    // 특정한 타입을 지정하면, 해당 타입으로 동작한다
    return some;
}

function someThing(some: any) {
    // any 로 지정하면 어떤 타입이든 받아들이는 점에서 제네릭이지만,
    // 실제로 함수가 반환할 때 어떤 타입인지에 대한 정보는 잃는다
    return some;
}

function someThing(some: T): T {
    // 제네릭을 사용하면, 어떠한 타입이든 받을 수 있으며,
    // 해당 타입을 리턴한다고 명시함으로써, 인자타입에 따라 결과물의 타입이 정해진다
    return some;
}

const res1 = someThing<string>('some value');
// <string> 과 같이 타입 인수를 포함하여 제네릭 T 에 대한 정보를 넘길 수 있다
// 명시적인 타입을 지정함으로써, 복잡한 코드에서 해당 타입을 명시적으로 보여줄 수 있다
const res2 = someThing('some value');
// 가장 일반적인 방법이며, 타입 인수 추론을 통해 제네릭 T 의 값을 추론하여 해당 값으로 결과를 리턴한다
```

### 제네릭 타입 변수 작업

* 제네릭을 사용하게 되면, 컴파일러가 함수 본문에 제네릭 타입화된 매개변수를 쓰도록 강요한다
  * 이러한 매개변수들은 실제로 any 나 모든 타입이 될 수 있는 것처럼 취급할 수 있게 된다
  
```typescript
function someThing<T>(arg: T): T {
    console.log(arg.length);
    // eeror, 이러한 상황에서 T 는 any 이므로 length 가 없다 
    return arg;
}

function someThing<T>(arg: T[]): T[] {
    console.log(arg.length);
    // 이때 T 는 배열이므로, length 를 사용할 수 있다
    return arg;
}
```
