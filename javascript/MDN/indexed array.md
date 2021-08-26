### 배열 객체

* 배열은 이름과 인덱스로 참조되는 정렬된 값의 집합이다.
* js 에서는 명시적인 배열 데이터 형식을 가지고 있지 않다.
    * 그러나 미리 정의된 배열 객체를 사용할 수 있고 배열 객체의 메서드를 개발하는 어플리케이션에서 사용되는 배열에 사용할 수 있다.

```js
// 배열 생성 방식
var arr = new Array(element0, element1, ..., elementN);
var arr = Array(element0, element1, ..., elementN);
var arr = [element0, element1, ..., elementN];
// 배열 생성되 초기화

var arr = new Array(arrayLength);
var arr = Array(arrayLength);
var arr = [];
arr.length = arrayLength;
// arrayLength 의 크기만큼 빈 배열이 생성됨

var arr = [10];         // 숫자 10 1개만 가지는 배열 생성
var arr = Array(10);    // 10 개의 빈 배열이 생성 됨

var arr = Array(9.3);   // RangeError: Invalid array length
```

### 배열의 길이

* js 의 배열은 배열에 포함된 요소들을 배열의 인덱스 값을 속성 이름으로 사용해서 표준 객체의 속성처럼 저장한다.
* 길이 속성은 항상 마지막 요소의 인덱스에 1을 더한 값이 된다.

```js
var foo = [];
foo[30] = 'bar';
console.log(foo.length); //31

var fuz = ['one', 'two', 'three'];
console.log(fuz.length); //3

fuz.length = 2;
console.log(fuz); // ["one", "two"]
fuz.length = 0;
console.log(fuz); // []
fuz.length = 3;
console.log(fuz); // [empty × 3]
```
