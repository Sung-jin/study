### 함수 타입

* ts 는 반환문을 보고 반환타입을 파악할 수 있으므로 반환타입을 생략할 수 있다
* 매개변수의 타입들이 올바르게 나열되어 있다면 함수 타입에 이름을 붙이더라도 유효한 타입으로 간주한다
* 화살표 표기를 써서 반환타입을 지정할 수 있다 (함수 타입을 지정할 때 반드시 지정해줘야 한다)
    * 함수가 값을 반환하지 않는다면 `Void` 를 써서 표시해야 한다
* 함수 타입 작성
    * `(arg:type, ...) => type`
* ts 컴파일러가 방정식의 한쪽에만 타입이 있더라도 타입을 추론할 수 있다
    * `contextual typing` 이라 한다

```typescript
// 함수의 타입 추론
let myAdd = function(x: number, y: number): number { return  x + y; };
// myAdd는 전체 함수 타입을 가진다

let myAdd: (baseValue: number, increment: number) => number =
        function(x, y) { return x + y; };
// 매개변수 x 와 y는 number 타입을 가진다
```

### 선택적 매개변수와 기본 매개변수

* ts 에서는 모든 매개변수가 함수에 필요하다고 가정한다
    * null/undefined 를 줄 수 없다는 의미는 아니다
* 함수가 호출될 때 컴파일러는 각 매개변수에 대해 사용자가 값을 제공했는지 검사한다
* 컴파일러는 매개변수들이 함수로 전달될 유일한 매개변수라고 가정한다
    * 즉, 함수에 주어진 인자의 수는 함수가 기대하는 매개변수의 수와 일치해야 한다
    * js 의 경우 모든 매개변수는 선택적이고, 선언된 함수의 매개변수보다 적은 매개변수가 전달되면 나머지 매개변수는 undefined 가 된다
    * 만약 선택적 매개변수를 ts 에서 선언하고 싶다면 `?` 를 매개변수명 뒤에 붙여주면 된다
    * 또는 undefined 일 경우 할당될 매개변수의 값을 정해 놓을 수 있다 (`기본-초기화 매개변수` 라고 한다)

```typescript
function buildName(firstName: string, lastName: string) { return firstName + ' ' + lastName };

const res1 = buildName('foo');                  // error 너무 적은 매개변수
const res2 = buildName('foo', 'bar');           // ok
const res3 = buildName('foo', 'bar', 'fuz');    // error 너무 많은 매개변수
////////////////////////////////////

function buildNameWithOptional1(firstName: string, lastName?: string) {
    if (lastName) return firstName + ' ' + lastName;
    else firstName;
}

function buildNameWithOptional2(firstName: string, lastName = 'bar') {
    // 두번째 매개변수에 값이 할당되지 않으면 bar 가 할당된다
    return firstName + ' ' + lastName;
}

const res1 = buildNameWithOptional1('foo');                  // ok
const res2 = buildNameWithOptional1('foo', 'bar');           // ok
const res3 = buildNameWithOptional1('foo', 'bar', 'fuz');    // error 너무 많은 매개변수

// buildNameWithOptional1, buildNameWithOptional2 는 아래와 같은 공통된 타입을 공유한다
// (firstName: string, lastName?: string) => string

function buildNameWithOptional3(firstName = 'foo', lastName: string) {
    return firstName + ' ' + lastName;
}

const res = buildNameWithOptional3(undefined, 'bar');
// 뒤에서부터 기본-초기화 매개변수를 사용하면 부족한 부분이 거기에 해당되지만,
// 앞에서 부터 사용되면 해당 위치에 undefined 를 넣어줘야 동작한다
// 그런데 굳이 이렇게 할 필요가..
```
