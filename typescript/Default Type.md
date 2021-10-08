### Boolean

* 참/거짓을 표현하는 기본적인 데이터 타입

```typescript
const isFalse: boolean = false;
const isTrue: boolean = true;
```

### Number

* js/ts 의 모든 숫자는 부동 소수 값이다
* number 라는 타입으로 표현되며, ts 는 16진수/10진수/2진수/8진수 리터럴도 지원된다

```typescript
const decimal: number = 10;
const hex: number = 0x000a;
const binary: number = 0b0110;
const octal: number = 0o012;
```

### 문자열

* `'`/`"` 으로 묶여진 데이터를 문자열로 표현하며, string 이라는 타입으로 표현한다.
* 또한 ` 으로 감싸서 템플릿 문자열로도 표현할 수 있다.

```typescript
const color: string = 'red';
const text: string = `hello
world!
my favorite color is ${color}
`;
```

### 배열

* 배열 요소를 나타내는 타입 뒤에 [] 를 붙여서 표현한다.

```typescript
const numberList: number[] = [1,2,3,4,5];
```

### 튜플

* 요소의 타입과 개수가 고정된 배열을 표현할 때 사용한다
    * 모든 요소가 같을 필요는 없다
    
```typescript
let x: [string, number];
x = ['foo', 1]; // ok
x = [1, 'foo']; // error

x[0].substring(1); //ok
x[1].substring(1); // error

x[2] = 'bar'; // error
```

### 열거 (enum)

* enum 은 0 부터 시작하여 멤버들의 번호를 매긴다.
    * 멤버 중 하나의 값을 수동으로 설정하여 번호를 변경할 수 있다.
    * 또는 모든 멤버를 수동으로 설정할 수 있다.
* enum 타입의 index 로 접근하면, 해당되는 key string 을 알 수 있다.

```typescript
enum Color {Red, Green, Blue};
const r: Color = Color.Red;

//////////////////////////////////////////////////
enum Color {Red = 1, Green, Blue};
// 1 부터 시작하여 red = 1, green = 2, blue = 3 으로 설정된다.
enum Color {Red = 1, Green = 4, Blue = 3};

//////////////////////////////////////////////////
enum Color {Red = 1, Green = 4, Blue = 3};
const colorName: string = Colr[4];
console.log(colorName); // Green
```

### Any

* 타이에 대한 검사를 하지 않는 모든 값을 수용할 때 사용할 수 있다.

```typescript
let anyVal: any = 'foo';
anyVal = 1;
anyVal = [1, '2'];
anyVal = {foo: 'bar'};
// any 타입으로 지정하였으므로, 모두 가능하다.

anyVal.push(1);
anyVal.subString(1);
// any 타입이므로 검사에 걸리지 않는다.
```

### Void

* 어떠한 타입도 존재할 수 없음을 나타내며, any 와 반대이다
* void 는 보통 함수의 반환 값이 없을 때 반환 타입을 표현하기 위해 쓰인다
* void 를 타입 변수를 선언할 경우 null/undefined 만 할당할 수 있다.

```typescript
function helloWorld(): void {
    alert('hello!');
}
```

### Null / Undefined

* null/undefined 로 타입을 지정할 경우 자신의 값으로만 할당할 수 있다.

### never

* 절대 발생할 수 없는 타입을 나타낸다
* 함수 표현식이나 화살표 함수 표현식에서 항상 오류를 발생시키거나 절대 반환하지 않는 반환 타입으로 쓰인다
* 변수 또는 타입 가드에 의해 아무 타입도 얻지 못할 경우 never 타입을 얻게 될 수 있다
* never 는 모든 타입에 할당 가능한 하위 타입이다
    * 하지만 어떤 타입도 never 에 할당할 수 있거나 하위 타입이 아니다
    * any 도 할당할 수 없다
    
```typescript
function error(message: string): never {
    throw new Error(message);
}
// never를 반환하는 함수는 함수의 마지막에 도달할 수 없다.

function fail() {
    return error("Something failed");
}
// 반환 타입이 never로 추론된다.

function infiniteLoop(): never {
    while (true) {
    }
}
// never를 반환하는 함수는 함수의 마지막에 도달할 수 없다.
```

### Object

* number/string/boolean/bigint/symbol/null/undefined 가 아닌 나머지 타입일 경우 object 로 타입이 지정된다

```typescript
declare function create(o: object | null): void;

create({ prop: 0 });    // ok
create(null);           // ok

create(42);             // error
create("string");       // error
create(false);          // error
create(undefined);      // error
```

### 타입 단언

* 타입 단언은 다른 언어의 타입 변현(형 변환)과 유사하지만, 다른 특별한 검사를 하거나 데이터를 재구성하지는 않는다
* 이는 런타임에 영향을 미치지 않으며, 온전히 컴파일러만 이를 사용한다
* 타입 단언은 `angle-bracket`/`as` 문법이 존재한다.
    * JSX 와 함께 사용할 경우 as 스타일의 단언만 허용된다

```typescript
// angle-bracket
let someValue: any = 'string value';
let strLength: number = (<string>someValue).length;
// <string> 으로 해당 값은 string 이라고 타입 단언을 할 수 있다

// as
let someValue: any = 'string value';
let strLength: number = (someValue as string).length;
```

