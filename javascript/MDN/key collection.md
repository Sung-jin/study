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
