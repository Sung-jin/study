# Async / Await

##  [참고 - 이벤트 루프와 비동기 프로그래밍의 부상, async/await을 이용한 코딩 팁 다섯가지](https://engineering.huiseoul.com/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%9E%91%EB%8F%99%ED%95%98%EB%8A%94%EA%B0%80-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EB%A3%A8%ED%94%84%EC%99%80-%EB%B9%84%EB%8F%99%EA%B8%B0-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D%EC%9D%98-%EB%B6%80%EC%83%81-async-await%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%BD%94%EB%94%A9-%ED%8C%81-%EB%8B%A4%EC%84%AF-%EA%B0%80%EC%A7%80-df65ffb4e7e)

* 비동기 코드를 동기 코드 처럼 동작하게 할 때 사용한다
* [async 함수에는 await식이 포함될 수 있다](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Statements/async_function#Description)
* 이 식은 async 함수의 실행을 일시 중지하고 전달 된 Promise의 해결을 기다린 다음 async 함수의 실행을 다시 시작하고 완료후 값을 반환한다

```JavaScript
...
db.query('쿼리', function(err, res) =>{
  if(err){
    return res.err(err);
  }

  ... res 처리 후 연속 작업

  db.query('쿼리2', function(err, res) =>{
    if(err){
      return res.err(err);
    }

    ... 콜백 반복
  });
});

//이와 같이 콜백, 콜백, 콜백, 콜백으로 작성 할 순 있다
//할 순 있다, 가독성이 떨어져서 그렇지
//콜백 형태로 할 수도 있고, then으로도 할 수 있다

const query1 = (인자1, 인자2...) =>{
  return new Promise ((resolve, reject)=>{
    ...처리 후
    resolve(res);
    if(err){
      reject(err);
    }
  });
}

const query2 = (인자1, 인자2...) =>{
  return new Promise ((resolve, reject)=>{
    ...처리 후
    resolve(res);
    if(err){
      reject(err);
    }
  });
}

query1(인자1, 인자2..)
  .then(res =>{
    ...응답에 대한 처리
    return query2(인자1, 인자2...)
      .then(res =>{
        ...코드 처리
      })
      .catch(err =>{
        return res.err(err);
      });
  })
  .catch(err =>{
    return res.err(err);
  });
```

* 비동기 코드의 작업이 끝나고 다음 작업으로 수행 할 것을 콜백 함수로 만들어서 사용할 수 있다
* 예약을 한다고 생각하면 된다
* 한 / 두개면 알아보기 쉽다
* 하지만, 4번만 넘어가도 알아보기 힘들어...
* 콜백 지옥 끝나는 부분은 **});** 이게 넘쳐나...

```JavaScript
(async function(){
  try{
    var firstRes = await db.query('쿼리');
    ... res 처리 후 연속 작업

    var secondRes = await db.query('쿼리2');
    ... 반복
  }catch(err){
    return res.err(err);
  }
})
```

* 같은 방식으로 동작한다
* 콜백 하나하나에 await을 붙여서 풀어쓴 것이다
* 다만, await은 async로 정의된 함수의 내부에서만 사용될 수 있다
* 모든 async 함수는 암묵적으로 promise를 반환하고, promise가 함수로부터 반환할 값을 resolve한다

# Error Stack

```JavaScript
const query = () =>{
  return callPromise()
    .then(() => callPromise())
    .then(() => callPromise())
    .then(() => callPromise())
    .then(() => callPromise())
    .then(() =>{
      throw new Error("opps!");
    })
}

query()
  .catch(err =>{
    console.log(err);
    //Error : oops! at callPromise.then.then.then.then.then ~~~
  });
```

* 에러에 대한 내용을 라인넘버를 통해 추론해야 한다
* 에러 함수에 대한 정보는 그저 callPromise 뿐

```JavaScript
const query = async () =>{
  await callPromise();
  await callPromise();
  await callPromise();
  await callPromise();
  await callPromise();
  throw new Error("oops!");
}

query()
  .catch(err =>{
    console.log(err);
    //Error : oops! at query
  });
```
