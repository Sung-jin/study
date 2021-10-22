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

### 나머지 매개변수

* 함수가 최정적으로 얼마나 많은 매개변수를 취할지 모를 때 사용할 수 있다
* js 에서 모든 함수 내부에 위치한 arguments 라는 변수를 사용해 직접 인자를 가지고 작업할 수 있다
  * ts 에서는 해당 인자를 하나의 변수로 모을 수 있다

```typescript
function buildName(firstName: string, ...restOfName: string[]) {
    return firstName + ' ' + restOfName.join(' ');
}
// (x: type, ...: type[]) => returnType 과 같이 타입으로도 표현이 가능하다

const res = buildName('foo', 'bar', 'fuz', 'baz'); // foo bar fuz baz
```

### this 와 화살표 함수

* js 에서 this 는 함수가 호출될 때 정해지는 변수이다

```typescript
const obj = {
    val: 'foo',
    someFunc: function() {
        return function() {
            return this.val;
        }
    }
}

const someFunc = obj.someFunc();
const res = someFunc(); // error!
// someFunc 에 할당된 obj 의 someFunc 에서 바인딩된 this 가 obj 이 아닌 window 가 할당되었기 때문이다
// 최상위 레벨에서의 비-메서드 문법 호출은 this 를 window 로 한다 (strict mode 에서는 window 가 아닌 undfined 가 된다)
// 화살표 함수를 사용한다면, 생성된 쪽이 this 가 바인딩 된다

const changedObj = {
  val: 'foo',
  someFunc: () => {
    return function() {
      return this.val;
    }
  }
}
const changeSomedFunc = obj.someFunc();
const changedRes = changeSomedFunc(); // foo
```

### 콜백에서의 this 매개변수

* 콜백을 실행할 때, this 는 undefined 가 된다
  * this 매개변수를 지정하여 콜백 오류를 막는데 사용할 수 있다
  
```typescript
interface UIElement {
    addClickListener(onclick: (this: void, e: Event) => void): void
    // addClickListenr 의 콜백인 onclick 에 this 를 void 로 지정함으로써
    // 콜백의 this 는 void 임을 명시해줄 수 있다
};

class Handler {
  info: string;
  onClickGood = (e: Event) => { this.info = e.message }
}
let h = new Handler();
uiElement.addClickListener(h.onClickBad);
// 화살표 함수가 외부의 this 를 사용하기 때문에 가능하다
```

### 오버로드

* js 는 동적인 언어이다
* 하나의 함수가 전달된 인자의 형태에 따라 다른 타입의 객체들을 반환할 수 있다
* 하지만, 이러면 어떠한 타입을 받아서 어떠한 타입을 리턴하는 알 수 없다
  * 이때 오버로드를 이용할 수 있다
  * 오버로드 목록은 컴파일러가 함수 호출들을 해결할 때 사용한다
* 컴파일러가 오버로드된 여러 함수둘 중 알맞은 타입 검사를 할 때는 js 와 비슷한 프로세스를 따른다
  1. 오버로드 목록에서 첫 번째 오버로드를 진행하면서 제공된 매개변수를 사용하여 함수를 호출하려고 시도
  1. 일치 시 해당 오버로드를 알맞은 오버로드로 선택하여 작업 수행
  1. 불일치 시 다음 오버로드로 시도를 반복
* 컴파일러가 오버로드로 타입 체크하는 방식 때문에 가장 구체적인 것부터 오버로드 리스팅을 하는 것이 일반적이다

```typescript
function someFunc(x): any {
    if (typeof x === 'object') {
        // some object logic
    } else if (typeof x === 'string') {
        // some string logic
    }
    ...
}
// 위의 로직을 오버로드를 통해 다음과 같이 구현할 수 있다

function someFunc(x: object): string {
    // some object logic
    ...
}
function someFunc(x: string): object {
    // some string logic
    ...
}
```
