## 레이블 문

* 레이블은 프로그램에서 다른 곳으로 참조할 수 있도록 식별자로 문을 제공한다.
* 레이블 값은 예약어가 아닌 임의의 js 식별자일 수 있다.

```js
markLoop :
while(someBool) {
    doSomething();
}
```

## 레이블과 break/continue

* 레이블에 break, continue 를 사용하여 해당 레이블에 대한 동작을 실행시킬 수 있다.

```js
let x = 0;
let y = 0;
labelCancelLoops: while (true) {
    console.log(`outer loops: ${x}`);
    x += 1;
    y = 1;
    while (true) {
        console.log(`inner loops: ${y}`);
        y += 1;
        if (y === 10 && x === 10) {
            break labelCancelLoops;
        } else if (z === 10) break;
    }
}
// break 와 label
i = 0;
j = 0;

checkI:
    while (i < 4) {
        console.log(i);
        i += 1;
        checkJ:
            while (j > 4) {
                console.log(j);
                j -= 1;
                if ((j % 2) === 0) continue checkJ;
            }
            console.log(`${j} is odd.`);
    }
    console.log(`i = ${i}`);
    console.log(`j = ${j}`);
```

## for...in / for...of

```js
const obj = {
    foo: 1,
    bar: 2,
    fuz: 'hello'
};

function dumpProps(obj, objName) {
    let result = '';
    for (let i in obj) {
        // i 에 obj 의 값으로 반복한다.
        result += objName + `.${i} = ${obj[i]} <br/>`;
    }
    result += "<hr/>";
    return result;
}

console.log(obj, 'obj');
// obj.foo = 1 <br/>obj.bar = 2 <br/>obj.fuz = hello <br/><hr/>
```

* 배열에 for...in 을 사용할 수 있지만, for...in 은 숫자 인덱스에 추가하여 사용자 정의 속성의 이름을 반환한다.
    * 사용자 정의 속성 또는 메서드를 추가하는 등의 Array 객체를 수정한다면, 배열 요소 이외에도 사용자 정의 속성을 통해 for...in 문을 반복하게 된다.
    * 따라서 배열을 통해 반복할 때 숫자 인덱스와 전통적인 for 루프를 사용하는 것이 좋다.

## for...of

* 각각의 고유한 특성의 값을 실행할 명력과 함께 사용자 지정 반복 후크를 호출하여, 반복가능한 객체를 통해 반복하는 루프를 만든다.
    * 반복 가능한 객체 - 배열/Map/Set/인수 ...
    
```js
for (variable of object) {
    statement
}
```

### for...in / for...of 차이

```js
let arr = [3, 5, 7];
arr.foo = "hello";

for (let i in arr) {
   console.log(i); // 0 1 2 "foo"
}

for (let i of arr) {
   console.log(i); // 3 5 7
}
```
