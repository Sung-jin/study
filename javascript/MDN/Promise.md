## Promise

* 비동기 작업의 최종 완료 또는 실패를 나타내는 객체
* 함수에 콜백을 전달하는 대신에 콜백을 첨부하는 방식의 객체

### 보장

* 콜백은 자바스크립트 Event Loop 이 현재 실행중인 콜 스택을 완료하기 이전에는 절대 호출되지 않는다.
  * 비동기 작업이 성공하거나 실패한 뒤 then 을 이용하여 추가한 콜백의 경우에도 같다.
* then() 을 여러번 사용하여 여러개의 콜백을 추가할 수 있으며, 각각의 콜백은 주어진 순서대로 하나하나 실행된다.

### Chaining

* 순차적으로 각각의 작업이 전 단계 비동기 작업이 성공 한 이후에 해당 결과로 다음 작업을 해야할 때 promise chain 을 이용할 수 있다.
* then() 은 새로운 promise 를 반환한다.
  * then 이 사용되는 순서대로 해당 단계를 실행한다.
* 중간 작업 중 실패가 되면 chaining 마지막으로는 catch 에 전달한 콜백이 실행된다.
  * 실패된 순서와 상관없이 어떤곳이든 실패하면 catch block 으로 이동하여 해당 콜백을 실행한다.
* es8 (ECMAScript 2017) 에서 async/await 구문을 사용할 수 있다.


```js
const promise = somePromiseFunc();
const promise2 = promise.then(successCallback, failureCallback);
// or
const promise = somePromiseFunc().thne(successCallback, failureCallback);
// 위와같이 then block 으로 계속 결과에 대한 chain 을 사용할 수 있다.
---------------------

// 이전에는 콜백 피라미드로 작업되었다.
someThing1(function(result) {
    somethiing2(function(result2) {
        someThine3(function(result3) {
            console.log(result, result2, result3);
        });
    });
});

// 위와 같은 콜백 지옥이 then 을사용하면 아래와 같이 작업이 가능하다.
someThing1().then(function (result) {
    return someThing2(result);
}).then(function (result) {
    return someThing3(result);
}).then(function(result) {
    console.log(result);
}).catch(failureCallback);

// 또는 async/await 으로 하나하나 실행 할 수 있다.
async function someThings() {
    try {
        const result1 = await someThing1();
        const result2 = await someThing2();
        const result3 = await someThing3();   
    } catch (e) {
        console.error(e);
        // 1,2,3 함수가 순서대로 실행이 되며, 시도 중 실패하면
        // 해당 함수에서 중지되고 catch block 이 실행된다.
    }  
}
```


### Reject

*  Promise.reject(reason) 메서드는 주어진 이유(reason)로 거부된 Promise 객체를 반환한다.
* Promise 가 reject 될 때는 두 가지 이벤트 중 하나가 전역 범위에 발생한다.
  1. rejectionhandled - executor 의 reject 함수에 의해 reject 가 처리된 후 promise 가 reject 될 때 발생
  2. unhandledrejection - promise 가 reject 되었지만 사용할 수 있는 reject 핸들러가 없을 때 발생
* PromiseRejectionEvent 에 멤버 변수인 promise 와 reason 속성이 있다.
  * promise : reject 된 promise
  * reason : promise 가 reject 된 이유를 알려주는 속성
* reject 를 통해서 promise 에 대한 예외 처리 및 디버깅에 사용할 수 있다.
* reject 는 전역적이기 때문에, 모든 에러는 발생한 지점에 상관없이 동일한 핸들러로 전달된다.

```js
window.addEventListener('rejectionhandled', event => {
    // reject 가 처리된 promise 가 reject 를 발생할 때, 전역적으로 발생하므로 여기 이벤트가 동작한다.
});
window.addEventListener('unhandledrejection', event => {
    // reject 가 처리되지 않은 promise 가 reject 를 발생할 때, 전역적으로 발생하므로 여기 이벤트가 동작한다.
});
```

### 오래된 콜백 API 를 사용하여 Promise 만들기

* setTimeout() 함수와 같은 일부 API 는 success, failure 콜백을 전달하는 방식이 존재한다.
* 예전 스타일의 콜백과 Promise 를 합칠경우, 실패하거나 프로그래밍 오류가 있을 때 아무것도 잡아내지 않는다.
* 이러한 형태의 API 를 Promise 로 감싸서 이러한 이슈를 해결할 수 있다.

```js
const wait = ms => new Promise(resolve => setTimeout(resolve, ms));
wait(10000).then(() => something()).catch(failureCallback);
```

### 구성

* Promise.resolve()/.reject() - resolve/reject 를 직접 생성하기 위한 바로가기
* Promise.all()/,race() - 비동기 작업을 병렬로 실행하기 위한 두가지 구성 도구

```js
const result = someThing.resolve('some result');
result.then(res => console.log(res)); // some result

const result2 = someThing2.reject('some error');
result2.then(() => {}).catch(err => console.error(err)); // some error

Promise.all([somePromise1(), somePromise2(), somePromise3()]).map(res => {
    console.log(res); // 1,2,3 순차적으로 해당 응답값이 출력
});
[somePromise1(), somePromise2(), somePromise3()].forEach(async someThing => {
    await someThing();
    // ESMAScript 2017 에서 async/await 을 사용하여 순차적 구성을 수행할 수 있다.
})

const promise1 = new Promise((resolve, reject) => {
  setTimeout(resolve, 500, 'one');
});

const promise2 = new Promise((resolve, reject) => {
  setTimeout(resolve, 100, 'two');
});

Promise.race([promise1, promise2]).then((value) => {
  console.log(value);
  // Both resolve, but promise2 is faster
  // so fater promise2 result is bind to value
});
```

### timing

* then() 에 전달 된 함수는 이미 resolved 된 promise 라도 동기적으로 호출되지 않는다.
* 즉시 실행되는 대신 전달 된 함수는 마이크로 태스크 대기열에 저장된다.
  * js 이벤트 루프의 현재 실행이 끝날 때 대기열이 빌때 나중에 실행된다.

```js
Promise.resolve().then(() => console.log(2));
console.log(1);
// 1 2

const wait = ms => new Promise(resolve => setTimeout(resolve, ms));
wait().then(() => console.log(1));
Promise.resolve().then(() => console.log(2)).then(() => console.log(3));
console.log(4);

// 4 2 3 1
```

### nesting

* 간단한 promise 체인은 중첩이 부주의 한 구성의 결과 일 수 있으므로 중첩하지 않고 평평하게 유지하는 것이 가장 좋다.
* 중첩 된 catch 는 중첩 된 범위 외부의 체인에있는 오류가 아닌 범위 및 그 이하의 오류만 잡는다.

```js
doSomethingCritical()
.then(res => doSomthingOptional(res)
        .then(optionalRes => doSomethingExtraNice(optionalRes))
        .catch(e => {})
        // 해당 catch 는 doSomthingOptional, doSomethingExtraNice 에서 발생한 오류를
        // catch 한 후 moreCriticalStuff 를 다시 실행한다.
).then(() => moreCriticalStuff())
.catch(e => console.error(e));
// moreCriticalStuff 의 실패는 해당 catch 에서만 포착된다.
```

### promise chain 의 일반적인 실수

```js
doSomething().then(function (res) {
    doSomthingElse(res)
            // 해당 함수에서 별도의 결과에 대한 return 값이 없다.
            .then(newRes => doThirdThing(newRes));
}).then(() => doFourthThing());
// 즉, doSomthingElse 은 doThirdThing, doFourthThing 과의 체인이 끊어져 있으므로
// 각각 독립적인 체인이 경쟁하게 된다.
// doSomthingElse 이 doThirdThing, doFourthThing 이 완료될 때 까지 기다리지 않고 병렬로 실행하게 된다.
// 또한 각각의 promise 가 불필요하게 중첩되어 있다.
// 추가적으로 catch 로 체인을 종료하지 않았다.

doSomething()
.then(function (res) {
    return doSomethingElse(res);
})
.then(newResult => doThirdThing(newResult))
.then(() => doFourthThing())
.catch(err => console.err(err));
```
