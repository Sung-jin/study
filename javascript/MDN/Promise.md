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
