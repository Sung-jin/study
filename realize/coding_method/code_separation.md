# 코드 분리

팀으로써 프로젝트를 진행할 때, 그 프로젝트를 여러명이서 진행한다

이는 당연한 사실이지만 혼자서 프로젝트를 진행하는 것과 달리 나 의외의 사람이 내가 작성한 코드를 금방 이해하고 진행할 수 있게 하는 구조가 당연히 좋다

즉, 가독성! 가독성은 몇번 말해도 중요하다

```JavaScript
const dbPool = '디비정보들'
const foo = new Array();

...

function a(){
  ...
  dbPool.connect();
  dbPool.query(...)
    .then(result =>{
      ...
      val1 = result[0].a;
      val2 = result[1].b;
      ...
    })
    .catch(err =>{
      console.error(err);
    });

  const result = va1 === val2;
  foo.push({'blabla' : result ? '111' : '222'});
  ...
}

function b(){
  ...
  dbPool.connect();
  dbPool.query(...)
    .then(result =>{
      ...
      val1 = result[0].a;
      val2 = result[1].b;
      ...
    })
    .catch(err =>{
      console.error(err);
    });

  const result = va1 === val2;
  foo.push({'blabla' : result ? '111' : '222'});
  ...
}
```

위와 같이 나는 평소에 기능별로 나눠져 있지만, 나눠져 있는 기능 중 반복되는 기능들은 네이티브 코드 안에 다 삽입했었다

이를 아래와 같이 한다면?

```JavaScript
const dbPool = '디비정보들'
const foo = new Array();

...

function a(){
  ...
  repeatCode(val1, val2);
  ...
}

function b(){
  ...
  repeatCode(val1, val2);
  ...
}

function repeatCode(val1, val2){
  ...
  dbPool.connect();
  dbPool.query(...)
    .then(result =>{
      ...
      val1 = result[0].a;
      val2 = result[1].b;
      ...
    })
    .catch(err =>{
      console.error(err);
    });

  const result = va1 === val2;
  foo.push({'blabla' : result ? '111' : '222'});
  ...
}
```

조금 더 가독성이 높아지고 기능별로 나눠지게 되었다

어찌보면 당연히 했어야 하는 것이다

나는 지금껏 일차적으로는 기능을 나누긴 했었다

예를들어, api에서 엔드포인트 별 기능을 다르게 해야 하니 엔드포인트별 기능을 나누긴 했다

기능은 다르더라도 디비에서 데이터를 가져온다던가, 가져온 데이터에서 필요한 부분만 추출한다던가 등등 엔드포인트별 반복되는 기능은 존재할 것이다

나는 이렇게 반복되는 부분을 네이티브 코드 안에 반복해서 썼었다

-----------------------

##  Result!

기능에 대한 코드를 지역 함수가 아닌 분리된 함수로 만드는 습관을 가지자

분리된 함수로 만들면 관리나 사용성이 편리해진다

또한, 나눈 기능은 비슷한 기능(디비관련 기능 등등)끼리 모아두고 따로 파일형태로 관리를 합시다

~~느낌은 알겠는데, 이 습관이 내것이 되는데는 기존 하던방식이 있었기에 얼마나 걸릴지...~~
