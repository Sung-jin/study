### 열거형

* 열거형으로 이름이 있는 상수들의 집합을 정의할 수 있다
* ts 는 숫자와 문자열기반 열거형을 제공한다
* 숫자 열거형은 계산된 멤버와 상수 멤버를 섞어서 사용할 수 있다
  * 초기화 되지 않은 열거형이 먼저 나오거나, 숫자 상수 혹은 다른 상수 열거형 멤버와 함께 초기화된 숫자 열거형 이후 나와야 한다

#### 숫자 열거형

```typescript
enum Direction {
    Up = 3, Down, Left, Right
    // 1 로 초기화된 열거형을 선언
    // 그 뒤에 따로 값을 초기화 해주지 않으면 그 이후의 순차적인 값으로 할당된다
    // Up: 3, Down: 4, Left: 5, Right: 6
    // 초기 값을 정의하지 않으면 0 부터 순차적으로 증가된 값으로 할당된다
}

function move(direction: Direction) { ... }
// 위와 같이 열거형으로 타입을 지정할 수 있다

enum E {
    A = getSomeValue(),
    B, // error, 앞에 나온 A가 계산된 멤버이므로 초기화가 필요
}
```

#### 문자열 열거형

* 문자열 열거형에서 각 멤버들은 문자열 리터럴 또는 다른 문자열 열거형의 멤버로 상수 초기화를 해야 한다
* 문자열 열거형은 숫자 열거형처럼 자동증가 기능은 없지만, 직렬화를 잘 한다
  * 숫자 열거형을 이용해서 디버깅하고 있고, 해당 값을 읽어야 한다면 해당 값이 불확실한 경우가 있다
  * 이는 숫자만으로는 어떠한 의미인지 유의미한 정보를 제공해주지 않기 때문이다

```typescript
enum Direction {
    Up = "UP",
    Down = "DOWN",
    Left = "LEFT",
    Right = "RIGHT",
}
```

#### 이종 열거형

* 기술적으로 숫자와 문자를 섞어서 사용할 수 있으나, 이렇게 사용할 필요는 사실 거의 없다

```typescript
enum Heterogeneous {
    No = 0, Yes = "YES"
}
```

### 계산된 멤버와 상수 멤버

* 열거형의 첫 데이터의 초기값이 없는 경우, 0 으로 할당된다
* 별도의 값을 지정하지 않으면, 이전 값에서 1 씩 증가한 값을 상수로 가진다
* 열거형 멤버는 상수 열거형 표현식으로 초기화 된다
  * 상수 열거형 표현식은 컴파일 시 ts 가 알아낼 수 있는 표현식의 일부이다
* 상수 열거형 표현식
  1. 리터럴 열거형 표현식 (기본적으로 문자 리터럴 또는 숫자 리터럴)
  1. 이전에 정의된 다른 상수 열거형 멤버에 대한 참조
  1. 괄호로 묶인 상수 열거형 표현식 
  1. 상수 열거형 표현식에 단항 연산자를 사용한 경우
  1. 상수 열거형 표현식을 이중 연산자의 피연산자로 사용한 경우
* 상수 열거형 표현식 값이 NaN 이거나 Infinity 이면 컴파일 시점에 오류가 발생한다

```typescript
enum FileAccess {
    None,
    Read = 1 << 1,
    Write = 1 << 2,
    ReadWrite = Read | Write,
    Computed = 'some value'.length
}
```

### 유니언 열거형과 열거형 멤버 타입

* 리터럴 열거형 멤버 리터럴 열거형 멤버는 초기화 값이 존재하지 않거나, 다음과 같은 값으로 초기화 되는 멤버이다
  * 문자 리터럴 ('foo', 'bar'...)
  * 숫자 리터럴 (1, 2, 3, -1, -2, -3...)
* 열거형 멤버를 타입처럼 사용할 수 있다
* 열거형 타입 자체가 효율적으로 각각의 열거형 멤버의 유니언이 된다

```typescript
enum ShapeKind {
    Circle,
    Square,
}

interface Circle {
    kind: ShapeKind.Circle;
    ...
}

interface Square {
    kind: ShapeKind.Square;
    ...
}

let c: Circle = {
    kind: ShapeKind.Square, // type error
    ...
}

function f(shape: ShapeKind) {
    if (shape !== ShapeKind.Circle || shape !== ShapeKind.Square) {
        // error
        // shape 은 Circle/Square 둘 중 하나이기 때문에 무조건 true 를 반환한다
        ...
    }
}
```

### 런타임에서의 열거형

```typescript
enum E {X, Y, Z}
// 해당 열거형은

function f(obj: { X: number }) {
    obj.X;
}

f(E);
// E 는 X 라는 숫자 프로퍼티를 가지고 있기 때문에 동작한다
```

### 컴파일 시점의 열거형

* 열거형에 keyof 키워드는 일반적인 객체에서 기대하는 동작과 다르게 동작한다
* keyof typeof 를 사용하면 모든 열거형의 키를 문자열로 나타내는 타입을 가져온다

```typescript
enum LogLevel {
    ERROR, WARN, INFO, DEBUG
}

type LogLevelStrings = keyof typeof LogLevel;
// type LogLevelString = 'ERROR' | 'WARN' | 'INFO' | 'DEBUG';

function printImportant(key: LogLevelStrings, message: string) {
    const num = LogLevel[key];
    if (num <= LogLevel.WARN) {
        console.log('Log level key is: ', key);
        console.log('Log level value is: ', num);
        console.log('Log level message is: ', message);
    }
}
printImportant('ERROR', 'This is a message');
```

### 역 매핑

* 열거형 값에서 열거형 이름으로 역 매핑을 받을 수 있다

```typescript
enum Enum { A }
const a = Enum.A;
const nameOfA = Enum[a];

// ts 로 컴파일
var Enum;
(function (Enum) {
    Enum[Enum['A'] = 0] = 'A';
})(Enum || (Enum = {}));
var a = Enum.A;
var nameOfA = Enum[a]; // 'A'
// name -> value 의 열거형 정방향 매핑과
// value -> name 으로의 역방향 매핑 두 정보를
// 모두 저장하는 객체로 컴파일 된다
// 다른 열거형 멤버 참조는 항상 프로퍼티 접근으로 노출되며, 인라인되지 않는다
// 문자열 열거형은 역 매핑을 생성하지 않는다
```

### const 열거형

* 열거형의 요구사항이 엄격해져야 할 때, const 열거형으로 사용할 수 있다
  * 열거형 값에 접근할 때 간점 참조에 대한 비용을 피하기 위해 const 열거형을 사용할 수 있다
  * const 열거형은 const 지정자를 열거형에 붙여 정의한다
* const 열거형은 상수 열거형 표현식만 사용될 수 있다
  * 일반적인 열거형과 달리 컴파일 과정에서 완전히 제거된다
  * const 열거형은 사용하는 공간에 인라인된다
  
```typescript
const enum Enum {
    A = 1,
    B = A * 2
}

const enum Directions {
    Up,
    Down,
    Left,
    Right
}

const directions = [Directions.Up, Directions.Down, Directions.Left, Directions.Right];
// 컴파일 결과
// var directions = [0 /* Up */, 1 /* Down */, 2 /* Left */, 3 /* Right */];
```

### Ambient 열거형

* 이미 존재하는 열거형 타입의 모습을 묘사하기 위해 사용한다
* ambient vs non-ambient 열거형의 가장 큰 차이점
  * 일반적인 열거형에서 초기화되지 않은 멤버가 상수로 간주하는 멤버 뒤에 있다면 해당 멤버도 상수로 간주한다
  * const 가 아닌 ambient 열거형에서 초기화되지 않은 멤버는 항상 계산된 멤버로 간주한다

```typescript
declare enum Enum {
    A = 1,
    B,
    C = 2
}
```
