## Iterables

* `Symbol.iterator` 프로퍼티에 대한 구현을 가지고 있다면 이터러블로 간주한다
  * 해당 함수는 반복할 값 목록을 반환한다
* `Map`/`Set`/`String`/`Int32Array`/`Unit32Array` 등과 같은 일부 내장 타입에는 `Symbol.iterator` 가 구현되어 있다

#### for..of

* `Symbol.iterator` 프로퍼티를 호출하여 이터러블 객체를 반복한다

```typescript
const arr = [1, '2', false, {}];

for (let entry of someArray) {
    console.log(entry); // 1, '2', false, {}
}
```

### for..of vs for..in

* `for..of`
  * 반복되는 객체의 숫자 프로퍼티 값 목록을 반환한다
  * 이터러블 객체의 값에 주로 관심이 있다
* `for..in`
  * 반복되는 객체의 키 목록을 반환한다
  * 모든 객체에서 작동한다

```typescript
const arr = [1,2,3,4];

for(let i in arr) console.log(i); // '0', '1', '2', '3'
for(let i of arr) console.log(i); // 1, 2, 3, 4
```

## Code generation

* ES5, ES3 호환 엔진을 대상으로 하는 경우, 반복자 Array 유형의 값만 허용한다
  * 이러한 배열이 아닌 값이 `Symbol.iterator` 프로퍼티를 구현하더라도 배열이 아닌 값에서 `for..of` 루프를 사용하면 오류가 발생한다
* 컴파일러는 `for..of` 루프에 대한 간단한 `for` 루프를 생성한다

```typescript
const numbers = [1,2,3];
for (let num of numbers) {
    console.log(num);
}
// 위 for..of 코드는
for (var _i = 0; _i < numbers.length; _i++) {
    var num = numbers[_i];
    console.log(num);
}
// 위와 같이 생성된다
```

* ECMAScript 2015 호환 엔진을 타겟팅하는 경우, 컴파일러는 엔진의 내장 반복자 구현을 대상으로 하는 `for..of` 루프를 생성한다
