### Map

* ES6 에서 소개된 값들을 매핑하기 위한 새로운 데이터 구조
* 키와 값을 매핑시켜서 저장하며, 저장된 순서대로 각 요소들을 반복적으로 접근할 수 있다.

```js
var someMap = new Map();

someMap.set('key1', 'value1');
someMap.set('key2', 'value2');
someMap.set('key3', 'value3');

for(var [key, value] of someMap) {
    console.log(`${key} : ${value}`);
}
```

### Object vs Map

| Object | Map |
| ---- | ---- |
| Object 의 key 는 string | Map 의 key 는 모든 값을 가질 수 있다 |
| Object 의 크기는 수동으로 계산해야 한다. | Map 의 크기는 size 로 얻을 수 있다 |

### WeakMap object

* object 만을 키로 허용하고 임의의 값을 허용하는 key-value 형태의 요소 집합
* 키의 객체가 가지고 있는 참조가 더이상 존재하지 않을 경우 GC 의 수거대상이 되는 약한 참조를 의미한다
* WeakMap API 는 Map API 와 동일하다
* 단, WeakMap 의 keys 는 열거형태가 아니다
    * 즉, 키 목록을 제공하는 메서드가 없다
    * 키 목록을 제공한다면 GC 의 상태, 결과에 따라 키 목록이 변하게 되고, 이는 비 결정성을 야기한다
* WeakMap 을 사용하는 한가지 경우는 객체의 사적인 정보를 저장하기 위해서또는 상세 구현 내용을 숨기기 위해서이다.

```js
// https://fitzgeraldnick.com/2014/01/13/hiding-implementation-details-with-e6-weakmaps.html
const privates = new WeakMap();
function Public() {
    const me = {
        // private data
    }
    privartes.set(this, me);
}

Public.prototype.method = function () {
    const me = privates.get(this);
    // me 를 통해 private 데이터 작업
}

module.exports = Public;
```

### Set

* 입력된 순서에 따라 저장된 요소를 반복 처리를 할 수 있고, 중복된 값을 허용하지 않는 값들의 집합
* Array.from 또는 spread operator 를 통해 Set 객체로 배열을 만들 수 있다.
* Set 생성자는 배열을 인자로 받을 수 있다.

```js
var set = new Set();

set.add(1);
set.add(1);
set.add(2);
set.add(3);
set.add(1);

for (let item of set) console.log(item); // 1 2 3

var setByArray = new Set([1,1,2,3,1,1]);
for (let item of setByArray) console.log(item); // 1 2 3

var arrayBySet1 = Array.from(set);
var arrayBySet2 = [...set];

console.log(arrayBySet1); // [1,2,3]
console.log(arrayBySet2); // [1,2,3]
```

### array vs set

* indexOf 를 통해 배열에 특정 요소가 존재하는지 확인하는 것은 느리다.
* set 은 특정 데이터를 삭제하는 기능이 제공된다.
* NaN 은 indexOf 로 찾을 수 없다.
* set 은 중복을 확인 할 필요가 없다.

### WeakSet

* WeakSet 내의 요소 열거 형태는 없다.
* set 과 다른 점
  * WeakSet 에는 객체만 저장할 수 있다.
  * WeakSet 은 약한 참조를 가진다.
    * 즉, WeakSet 에 저장된 객체에 대한 참조가 없어지면 GC 대상이 된다.
    * 따라서 현재 저장되어 있는 객체에 대한 목록은 없으며 WeakSet 은 열거형이 아니다.
