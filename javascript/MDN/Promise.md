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


