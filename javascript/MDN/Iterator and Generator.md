### 반복자

* 시퀀스를 정의하고 종료시의 반환값을 잠재적으로 정의하는 객체
* (value, done) 이라는 두개의 속성을 반환하는 next() 메소드를 사용하여 Iterator protocol 을 구현한다.
  * 마지막 값을 산출하고 next() 를 호출하면 `{done: true}` 가 반환된다.
* 반복자의 next() 메소드는 일반적으로 한 번씩만 할 수 있다.

### Generator functions

* 반복자를 만들 때 반복자 내부에 명시적으로 상태를 유지할 필요가 있을 수 있다.
  * Generator 함수가 이에 대한 강력한 대안을 제공한다.
  * 생성자 함수는 `function*` 문법을 통해 작성된다.
* 생성자 함수가 최초로 호출될 때, 함수 내부의 어떠한 코드도 실행되지 않고 대신 생성자라고 불리는 반복자 타입을 반환한다.
* 생성자의 next 메소드를 호출해서 어떤 값이 소비되면, 생성자 함수는 yield 키워드를 만날 때까지 실행된다.
* 생성자 함수는 원하는 만큼 호출될 수 있고, 매번 새로운 생성자를 반환한다.
  * 하지만 생성자는 단 한번만 순회될 수 있다.

```js
function* makeRangeIterator(start = 0, end = Infinity, step = 1) {
    let n = 0;
    for (let i = start; i < end; i += step) {
        n++;
        yield i;
    }
    return n;
}
```

### Iterables

* 객체는 값이 for..of 구조 내에서 반복되는 것 같은 그 반복 동작을 정의하는 경우 iterable 하다.
  * String, Array, TypedArray, Map, Set 은 기본 반복 동작이 존재한다.
* iterable 하기 위해서 객체는 @@iterator 메서드를 구현해야 한다.
  * 객체(프로토타입 체인에 등장하는 객체 중 하나)가 Symbol.iterator 키를 갖는 속성이 있어야 함을 뜻한다.
* iterable 은 한번 또는 여러번 반복가능하다.
  * Generator 와 같은 iterable 은 관습적으로 자신의 @@iterator 메소드로부터 this 를 반환한다.
  * 여러번 반복 가능한 iterable 은 메소드가 호출되는 매 회 새로운 iterator 를 반드시 반환해야 한다.
  
#### 사용자 정의 iterable

```js
var myIterable = {
    *[Symbol.iterator]() {
        yield 1;
        yield 2;
        yield 3;
  }
}

for (let value of myIterable) {
    console.log(value); // 1 2 3
}
console.log([...myIterable]); // [1,2,3]

```

#### iterable 을 기대하는 구문

```js
for (let value of ['a','b','c']) console.log(value);
[...'abc']; // ['a','b','c']
function* gen() {
    yield* ['a', 'b', 'c']
}
gen.next() // {value: 'a', done: false}
[a,b,c] = new Set(['a','b','c']);
```

### Generator 심화

* 생성자 함수는 요청에 따라 산출된 값을 계산하고, 계산하기 힘든 수열 또는 무한 수열이라도 효율적으로 나타내게 한다.
* next() 메서드는 또환 생성기의 내부 상태를 수정하는데 쓰일 값을 받는다.
  * next() 에 전달되는 값은 생성기가 중단된 마지막 yield 식의 결과로 처리된다.
  
```js
function* fibonacci() {
    var fn1 = 0;
    var fn2 = 1;
    while (true) {
        var current = fn1;
        fn1 = fn2;
        fn2 = current + fn1;
        var reset = yield current;
        if (reset) {
            fn1 = 0;
            fn2 - 1;
        }
    }
}

var sequence = fibonacci();
console.log(sequence.next().value);     // 0
console.log(sequence.next().value);     // 1
console.log(sequence.next().value);     // 1
console.log(sequence.next().value);     // 2
console.log(sequence.next().value);     // 3
console.log(sequence.next().value);     // 5
console.log(sequence.next().value);     // 8
console.log(sequence.next(true).value); // 0
console.log(sequence.next().value);     // 1
console.log(sequence.next().value);     // 1
console.log(sequence.next().value);     // 2
```

* 추가적으로 throw() 메서드를 호출하고 throw 해야 하는 예외 값을 전달하여 생성기가 예외를 던질 수 있다.
  * 이 예외는 현재 일시 중단된 yield 대신 throw value 문인 것처럼 생성기의 현재 일시 중단된 컨텍스트에서 throw 된다.
* catch 를 통해 생성기 내에 발생된 예외를 잡아내지 않으면 next() 에 대한 후속 호출은 `done: true` 이 된다.
* Generator 에는 주어진 값을 반환하고 제너레이터 자체를 완료하는 return(value) 메서드가 존재한다.
