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

### 배열 객체의 메서드

* concat() - 두개의 배열을 합쳐 **새로운 배열**을 반환

```javascript
var arr1 = [1,2,3,4,5];
var arr2 = arr1.concat([6,7,8,9,10]);
console.log(arr2); // [1,2,3,4,5,6,7,8,9,10]
```

* join(delimiter = ',') - 배열의 모든 요소를 주어진 구분자로 연결된 하나의 문자열로 반환
    * , (콤마) 가 기본값
    
```javascript
var arr = [1,2,3,4,5];
console.log(arr.join()); // 1,2,3,4,5
console.log(arr.join('|')); // 1|2|3|4|5
```

* push() - 한개 이상의 요소를 배열의 마지막에 추가하고, 추가한 요소를 포함한 길이를 반환

```javascript
var arr = [1,2,3,4,5];
console.log(arr.push(...[6,7,8]));  // 8
console.log(arr.push(6,7,8));       // 8
console.log(arr); // [1,2,3,4,5,6,7,8]
```

* pop() - 배열의 마지막 요소를 제거하고 그 제거된 요소를 반환

```javascript
var arr = [1,2,3,4,5];
console.log(arr.pop()); // 5
console.log(arr); // [1,2,3,4]
```

* shift() - 배열의 첫번째 요소를 제거하고 그 제거된 요소를 반환

```javascript
var arr = [1,2,3,4,5];
console.log(arr.shift()); // 1
console.log(arr); // [2,3,4,5]
```

* unshift() - 한개 이상의 요소를 배열의 앞쪽에 추가하고 추가한 요소를 포함한 길이를 반환

```javascript
var arr = [1,2,3,4,5];
console.log(arr.unshift(...[6,7,8]));  // 8
console.log(arr.unshift(6,7,8));       // 8
console.log(arr); // [6,7,8,1,2,3,4,5]
```

* slice(startIdx, endIdx) - 배열의 특정 부분을 추출하여 그 추출된 부분을 포함하는 새로운 배열을 반환
    * endIdx 에 해당되는 요소는 포함하지 않는다.
    
```javascript
var arr = [1,2,3,4,5];
console.log(arr.slice(1, 4));   // [2,3,4]
console.log(arr.slice(1));      // [2,3,4,5]
```

* splice(idx, removeCount, ...addElements) - 주어진 인덱스를 포함하여 removeCount 수 만큼 지우고, 주어진 요소로 해당 부분을 변경
    * 지워진 요소가 반환된다

```javascript
var arr = [1,2,3,4,5];
console.log(arr.splice(1, 2, ...[6,7,8]));  // [1,6,7,8,4,5];
var arr = [1,2,3,4,5];
console.log(arr.splice(1, 2, 6, 7, 8));     // [1,6,7,8,4,5];
```

* reverse() - 인덱스가 뒤집어진 배열에 대한 참조값이 반환
    * 즉, 반환된 값을 변경하면 원본값도 같이 변경된다.

```javascript
var arr = [1,2,3,4,5];
var arr2 = arr.reverse();
arr2[0] = 10;
console.log(arr2);  // [10,2,3,4,5]
console.log(arr);   // [10,2,3,4,5]
```

* sort() - 넘겨준 콜백 함수로 정렬을 하고 참조값을 반환
    * 콜백 함수는 sort 할 때 사용되는 반복 함수
    * 콜백 함수는 배열의 요소가 두개인 인수로 호출된다.
    * 왼쪽이 앞서면 양수, 오른쪽이 앞서면 음수, 같으면 0 을 반환하는 콜백 함수를 넘겨주면 된다.

```javascript
var arr = new Array('Wind', 'Rain', 'Fire');
var sortFn = function(a, b) {
    return a[a.length - 1] == b[b.length - 1] ? 0 :
        a[a.length - 1] < b[b.length - 1] ? -1 : 1;
}
// 마지막 문자로 정렬
arr.sort(sortFn);
arr.sort((a, b) => {
    return a[a.length - 1] == b[b.length - 1] ? 0 :
            a[a.length - 1] < b[b.length - 1] ? -1 : 1;
    // 익명 함수로도 콜백 함수를 사용할 수 있다. 
})
```

* indexOf(searchElement [, fromIndex]) - searchElement 를 검색하고 첫 번째 일치 항목의 인덱스를 반환
    * 찾는 요소가 없을 경우 -1
    * fromIndex 가 있을 경우, 해당 인덱스부터 검색

```javascript
var arr = [1,2,3,4,5,1,2,3,4,5];
console.log(arr.indexOf(4)); // 3
console.log(arr.indexOf(4, 5)); // 8
console.log(arr.indexOf(6)); //-1
```

* lastIndexOf(searchElement [, fromIndex]) - searchElement 를 뒤에서부터 검색하고 첫 번째 일치 항목의 인덱스를 반환
    * 찾는 요소가 없을 경우 -1
    * fromIndex 가 있을 경우, 해당 인덱스부터 검색

```javascript
var a = ['a', 'b', 'c', 'd', 'a', 'b', 'b', 'b'];
console.log(a.lastIndexOf('b')); // 7
console.log(a.lastIndexOf('b', 2)); // 1
```

* forEach(callback[, thisObject]) - 배열의 모든 요소에 대해 반복적으로 주어진 callback 을 실행

```javascript
var a = [1,2,3,4,5];
a.forEach(function(val) {console.log(val)});    // 1 2 3 4 5 
a.forEach((val) => console.log(val));           // 1 2 3 4 5
```

* map(callback[, thisObject]) - 배열의 모든 요소를 콜백함수로 실행하고, 해당 결과를 반환

```javascript
var a = [1,2,3,4,5];
var a2 = a.map(function(val) { return val * 2 });
var a2 = a.map((val) => val * 2);
// [2,4,6,8,10]
```

* filter(callback[, thisObject]) - 배열의 모든 요소에 대해 콜백 함수가 true 인 요소만 새로운 배열 반환

```javascript
var a = [1,2,3,4,5];
var a2 = a.map(function(val) { return val % 2 === 0 });
var a2 = a.map((val) => val % 2 === 0);
// [2,4]
``` 

* every(callback [, thisObject]) - 배열의 모든 항목에 대해 true 일 경우 true 를 반환한다.

```javascript
var arr1 = [1,2,3,4,5];
var arr2 = [1,2,3,4,'5'];

console.log(arr1.every(val => typeof val === 'number'));    // true
console.log(arr2.every(val => typeof val === 'number'));    // false
```

* some(callback [, thisObject]) - 배열의 모든 항목에 대해 한개라도 true 일 경우 true 를 반환한다.

```javascript
var arr1 = [1,2,3,4,5];
var arr2 = [1,2,3,4,'5'];

console.log(arr1.some(val => typeof val === 'string'));    // false
console.log(arr2.some(val => typeof val === 'string'));    // true
```

* reduce(callback [, thisObject]) - 배열내의 요소를 계산하여 새로운 객체로 변경한다.
    * 결과, 현재 요소, 현재 인덱스, 원본 배열 순으로 인자값이 들어간다.

```javascript
var arr = [1,2,3,4,5];
console.log(arr.reduce((res, val) => res + val, 0)); // 15
```

* reduceRight(callback[, initalvalue]) - reduce() 와 유사하게 동작하지만, 배열의 마지막 요소부터 시작된다.
