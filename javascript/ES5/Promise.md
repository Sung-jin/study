# Promise

* ES5
```JavaScript
function isGreater(a, b, callback){
  var greater = false;
  if(a > b){
    greater = true;
  }
  callback(greater);
}

isGreater(1, 2, function(result){
  if(result){
    console.log('greater');
  }else {
    console.log('smaller');
  }
});
```

* ES6
```JavaScript
const isGreater = (a, b) =>{
  return new Promise ((resolve, reject)=>{
    if(a > b){
      resolve(true);
    }else{
      reject(false);
    }
  });
}

isGreater(1, 2)
  .then(result =>{
    console.log('greater');
  })
  .catch(result =>{
    console.log('smaller');
  });
```

* ES5에서는 어찌되었든 결과를 넣어서 콜백에 넣어주는 형태이다
  * 위의 예시는 큰지, 작은지에 대한 정의지만 데이터베이스와 연동했다고 가정하자
  * 데이터베이스에 연동 후, 응답(res)가 있을 경우 작업을 처리하거나 리턴해준다
  * 실패시, 실패에 따른 작업을 해준 뒤, 응답을 해준다

* ES6에서도 동작 방식은 비슷하다
  * Promise를 응답하며, 성공(resolve)와 실패(reject)를 반환해준다.
  * 데이터베이스에 연동 후, 응답(res)가 있을 경우 작업을 처리하거나 리턴해준다
  * 모든 작업이 끝이나고 성공했을 경우 resolve(res) 와 같이 resolve에 성공에 대한 응답을 넣는다
  * 실패시, reject(err) 와 같이 실패에 대한 응답을 넣는다
  * resolve로 응답이 성공한 promise의 경우 .then에 해당되는 작업을 진행한다
  * reject로 응답한 promise의 경우 .catch에 들어가 그 블록 안에 해당되는 작업을 진행한다
