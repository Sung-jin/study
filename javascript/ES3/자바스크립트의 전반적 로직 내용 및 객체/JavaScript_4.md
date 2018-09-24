# JavaScript 4

##  Immutability - 변경불가성
객체가 생성된 이후 그 상태를 변경할 수 없는 디자인 패턴이며 함수형 프로그래밍의 핵심 원리

객체는 참조 형태로 전달하고 받는다

객체의 참조를 가지고 있는 어떤 장소에서 객체를 변경하면 참조를 공유하는 모든 장소에서 영향을 받는다

이러한 변화를 의도한 것이 아니라면 변경 사실을 통지하고 대처하는 대응이 필요하다

이러한 의도하지 않은 객체의 변경이 발생하는 원인의 대다수는 **레퍼런스를 참조한 다른 객체에서 객체를 변경** 하기 때문이다

이러한 문제의 해결 방법으로 객체를 불변객체로 만들어 프로퍼티의 변경을 방지하며 변경이 필요할 경우 참조가 아닌 객체의 방어적 복사를 통해 새로운 객체를 생성한 후 변경한다

불변 객체를 사용하면 복제나 비교를 위한 조작을 단순화할 수 있고 성능향상 개선에 도움이 되지만 변경 가능한 데이터를 많이 가지고 있다면 오히려 부적절하다

ES6에서는 불변 데이터 패턴을 쉽게 구현할 수 있는 새로운 기능이 추가되었다

##  immutable value 와 mutable value
* JavaScript의 기본 자료형은 immutable value
* 기본 자료형을 제외한 모든 객체는 mutable value

```javascript
var str = 'hello';
//메모리에 문자열 hello가 생성되고 str은 메모리에 생성된 hello를 가리킨다
str = 'world';
//hello를 수정하는 것이 아닌 새로운 문자열 world를 메모리에 생성하고 str은 이 world를 가리킨다
//hello와 world는 모두 메모리에 존재한다

var arr = [];
console.log(arr.length);//0

var v2 = arr.push(2);
console.log(arr.length);//1
//arr은 객체이다 즉, 변경 가능하다
//arr은 push로 arr자체가 업데이트가 되고, 업데이트 된 객체를 v2에 들어가게 된다
//객체는 직접 대상 배열을 변경한다

var user = {
  name: 'Lee'
};

var myName = user.name;
var user2 = user;

console.log(user.name);//Lee
console.log(user2.name);//Lee

user.name = 'Kim';
console.log(myName);//Lee

console.log(user.name);//Kim
console.log(user2.name);//Kim
//myName은 String으로 변경 불가능한 값이다
//즉, 객체에서 할당을 받았더라도 메모리에 user.name의 값인 'Lee'를 생성한 후 myName은 이 주소를 가리킨다
//user와 user2은 객체이다
//즉, 변경 가능한 값이며 user2 = user를 하였으므로 같은 객체 주소를 가리킨다
//user.name = 'Kim'으로 변경할 시 객체가 변경이되고 이를 가리키는 user와 user2는 같은 객체를 가리키므로 둘다 값이 변경이 된다
```

##  불변 데이터 패턴
의도하지 않은 객체의 변경이 발생하는 원인의 대다수는 **레퍼런스를 참조한 다른 객체에서 객체를 변경** 하기 때문

이를 해결하기 위해 객체를 불변객체로 만들어 프로퍼티의 변경을 방지하며 객체의 변경이 필요한 경우, 참조가 아닌 객체의 방어적 복사를 통해 새로운 객체를 생성한 후 변경한다

* 객체의 방어적 복사(Defensive copy) - Object.assign
  * Object.assign(target, ...sources)
  * 소스 객체를 타깃 객체로 프로퍼티를 복사
  * 소스 객체의 프로퍼티 중 타겟 객체의 프로퍼티와 동일한 프로퍼티는 소스 객체의 프로퍼티로 덮어쓰기 된다
  * ES6에서 추가된 메소드이며 IE에서는 지원하지 않는다

```javascript
var user = {name : 'Lee'};
var user1 = user;

const obj = {a : 1};
const copy = Object.assign({}, obj);

console.log(copy);          //{a : 1}
console.log(obj == copy);   //false
console.log(user = user1);  //true

const o1 = { a: 1 };
const o2 = { b: 2 };
const o3 = { c: 3 };

const merge1 = Object.assign(o1, o2, o3);

console.log(merge1); // { a: 1, b: 2, c: 3 }
console.log(o1);     // { a: 1, b: 2, c: 3 }, 타겟 객체가 변경된다!

const o4 = { a: 1 };
const o5 = { b: 2 };
const o6 = { c: 3 };

const merge2 = Object.assign({}, o4, o5, o6);

console.log(merge2); // { a: 1, b: 2, c: 3 }
console.log(o4);     // { a: 1 }
//타겟 객체의 변경을 원하지 않고 그저 복사 또는 병합만을 원하면 Object.assign({}, sources);와 같이 {}로 빈 객체를 만들어 주면 된다

const user2 = {name : 'Lee'};
const user3 = Object.assign({}, user2);

user2.name = 'Kim'

console.log(user2.name);  //Kim
console.log(user3.name);  //Lee
//Object.assign을 통해 겍체를 복사하여 생성하면 같은 주소를 공유하지 않으므로 한쪽 객체의 값을 변경하더라도 다른 객체에는 영향을 끼치지 않는다
//user2는 const로 선언되어 재할당 할 수 없지만 객체의 프로퍼티는 보호되지 않는다 즉, 객체의 내용은 변경할 수 있다
```

* 불변객체화를 통한 객체 변경 방지 - Object.freeze

```javascript
const user2 = {
  name : 'Lee'
  address: {
    city : 'Seoul'
  }
};
const user3 = Object.assign({}, user2, {name : 'Kim'});

console.log(user2.name);  //Lee
console.log(user3.name);  //Kim

Object.freeze(user2);

user2.name = 'Kim'; //무시된다

console.log(user2); //{name : 'Lee', address: { city : 'Seoul'}}
console.log(Object.isFrozen(user2));  //true

user2.address.city = 'Busan'; //변경됨
console.log(user);  //{name : 'Lee', address: { city : 'Busan'}}

function deepFreeze(obj){
  const props = Object.getOwnPropertyNames(obj);

  props.forEach((name) =>{
    const prop = obj[name];
    if(typeof prop === 'object' && prop !== null){
      deepFreeze(prop);
    }
  });
  return Object.freeze(obj);
}
//재귀호출 방식으로 내부의 모든 객체를 하나하나 싹다 프로즌시켜서 변경 불가능한 객체로 만들어 준다
//내부의 객체는 변경 불가능은 안되므로 하나하나 들어가면서 객체가 존재할 시 그 객체를 다시 프로즌 시키는 함수에 인자값으로 재귀호출하여
//반복처리를 통해 모든 객체를 Object.freeze를 시킨다

deepFreeze(user2);

user.name = 'Kim' //무시된다
user.address.city = 'Seoul' //무시된다

console.log(user2); ////{name : 'Lee', address: { city : 'Busan'}}
```

* Immutable.js
  * Object.assign과 Object.freeze를 이용하여 불변 객체를 만드는 방법은 번거롭고 성능상 이슈가 있어 사용하지 않는것이 좋다
  * Immutable.js는 facebook에서 제공하는 라이브러리이다
  * List, Stack, Map, OrderedMap, Set, OrderedSet, Record와 같은 영구 불변 데이터 구조를 제공

```JavaScript
const { Map } = require('immutable')
const map1 = Map({a : 1, b : 2, c : 3})
const map2 = map1.set('b', 50)
map1.get('b') //2
map2.get('b') //50
//map1.set()의 결과를 반영한 새로운 객체를 반영하기 때문에 map1의 객체는 변경되지 않았다
```
