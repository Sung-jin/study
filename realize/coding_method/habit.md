# 평소 코딩 습관

평소에 if문으로 분기를 많이 사용했었다

```JavaScript
const foo = {'1' : true, '2' : false};
const bar = new Array();
//const는 상수를 의미하지만 Object가 변수에 할당될 때는
//Object 자체가 할당되는게 아니라 Object의 주소가 할당된다
//{} === {} -> false!!!
//위의 예를 보면 왼쪽 {}의 주소와 오른쪽 {}의 주소는 당연히 다르기 때문에
//false가 뜨는 것이다
//즉, 'const 오브젝트변수'를 하면 오브젝트변수에는 오브젝트 주소가 들어있고,
//오브젝트변수에서 값을 변경하면, 이 주소값이 변하는게 아니라 주소 안에 들어있는 값이 변경이 되는 것이다!
//이렇게 하나를 또 배웠다 ㅎ_ㅎ
//https://hyunseob.github.io/2016/11/21/misunderstanding-about-const/

for(key in foo){
  if(foo[key]){
    bar.push({key : 'Yes!'});
  }else{
    bar.push({key : 'No!'});
  }
}
```

위와 같이 조건에 따라 Yes! 또는 No를 배열에 넣는 단순한 코드이다

이를 아래와 같은 방법으로도 표현할 수 있다

```JavaScript
const foo = {'1' : true, '2' : false};
const bar = new Array();

for(key in foo){
  const result = foo[key];
  bar.push({key: result ? 'Yes!' : 'No!'});
}
```

if 분기를 이용해서 true, false를 확인하고 그에 따라 값을 할당 할 수 있지만, 위와 같이 표현할 수 있다

성능상에 차이는 크게 없지만 더 직관적이고 가독성이 높아진다

평소 첫번째와 같이 코드를 작성하곤 했는데, 이러한 고정관념이 저러한 방법도 있구나를 느낀 좋은 경험이었다
