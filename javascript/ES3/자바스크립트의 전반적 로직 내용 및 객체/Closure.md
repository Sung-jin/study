# 클로저

* 클로저는 함수를 일급 객체로 취급하는 함수형 프로그래밍 언어에서 사용되는 중요한 특성이다
* 클로저는 함수와 그 함수가 선언됐을 때의 **렉시컬 환경과의 조합** 이다

```JavaScript
function outerFunc() {
  var x = 10;
  var innerFunc = function () { console.log(x); };
  innerFunc();
}

outerFunc(); // 10
```

* innerFun가 outerFunc의 내부에 선언된 내부함수이므로 innerFunc는 자신이 속한 렉시컬 스코프를 참조할 수 있다
  * innerFunc가 호출되면 자신의 실행 컨텍스트가 실행 컨텍스트 스택에 쌓이고 변수 객체와 스코프 체인, this에 바인딩할 객체가 결정된다
  * 스코프 체인은 전역 스코프를 가리키는 전역 객체와 outerFunc의 스코프를 가리키는 outerFunc의 활성 객체와 자신의 스코프를 가리키는 활성 객체를 순차적으로 바인딩한다
  * 스코프 체인이 바인딩한 객체가 바로 렉시컬 스코프의 실체다
* 상위 스코프에 접글할 수 있는 것은 렉시컬 스코프의 레퍼런스를 차례대로 저장하고 있는 실행 컨텍스트의 스코프 체인을 자바스크립트 엔진이 검색하였기에 가능하다
  * innerFunc 스코프 내에서 변수 x를 검색하고 실패한다
  * innerFunc 함수를 포함하는 외부 함수 outerFunc의 스코프에서 변수 x를 검색하고 성공한다

```JavaScript
function outerFunc() {
  var x = 10;
  var innerFunc = function () { console.log(x); };
  return innerFunc;
}

/**
 *  함수 outerFunc를 호출하면 내부 함수 innerFunc가 반환된다.
 *  그리고 함수 outerFunc의 실행 컨텍스트는 소멸한다.
 */
var inner = outerFunc();
inner(); // 10
```

* outerFunc는 내부함수 innerFunc를 반환하고 콜스텍에서 제거되었다
* 콜스텍에서 제거되었으므로 outerFunc의 x는 더이상 유효하지 않게 되어 x에 접근할 방법은 없어보인다
* 하지만 x의 값인 10이 출력되고, 특별한 일이 일어나고 있는 듯 하다
* 이처럼 자신을 포함하고 있는 외부함수보다 내부함수가 더 오래 유지되는 경우, 외부 함수 밖에서 내부함수가 호출되더라도 외부함수의 지역 변수에 접근할 수 있는 것을 **클로저** 라고 부른다

>MDN의 클로저 정의
“A closure is the combination of a function and the lexical environment within which that function was declared.”<br>
클로저는 함수와 그 함수가 선언됐을 때의 렉시컬 환경(Lexical environment)과의 조합이다.

* 함수 - 반환된 내부함수
* 그 함수가 선언될 때의 렉시컬 환경 - 내부 함수가 선언됐을 때의 스코프
* 즉, 클로저는 반환된 내부함수가 자신이 선언됐을 때의 환경인 스코프를 기억하여 자신이 선언됐을 때의 환경 밖에서 호출되어도 그 환경에 접근할 수 있는 함수를 말한다
* 간단히 말해 클로저는 자신이 생성될 때의 환경을 기억하는 함수
* 위에서 x와 같이 클로저에 의해 참조되는 외부함수의 변수를 자유변수라고 한다
* 클로저라는 이름은 자유변수에 함수가 닫혀있다라는 의미로 의역하면 자유변수에 엮여있는 함수라는 뜻이다
* 실행 컨텍스트의 관점으로는 내부함수가 유효한 상태에서 외부함수가 종료하여 외부함수의 실행 컨텍스트가 반환되어도, 외부함수 실행 컨텍스트 내의 활성객체는 내부함수에 의해 참조되는 한 유효하여 내부함수가 스코프 체인을 통해 참조할 수 있는 것을 의미한다
* 외부함수가 이미 반환되었어도 외부함수 내의 변수는 이를 필요로 하는 내부함수가 하나 이상 존재하는 경우 계속 유지된다
* 이때 내부함수가 외부함수에 있는 변수의 복사본이 아니라 실제 변수에 접근하다는 것을 주의하여야 한다

# 클로저의 활용

* 클로저는 자바스크립트의 강력한 기능이지만 성능적인 면과 자원적인 면에서 손해를 볼 수 있다

```HTML
<script>
var count = document.getElementById('count');
var counter = 0;//1번 함수일 때만 존재하는 라인

function increaser() {
  var counter = 0;
  return ++counter;
}
// ..1
function increaser() {
  var counter = 0;
  return ++counter;
}
// ..2
var increaser = (function () {
  var counter = 0;
  return function () {
    return ++counter;
  };
}());
// ..3
document.getElementById('inclease').onclick = function () {
  count.innerHTML = increaser();
};
</script>
```
* 1번
  * increaser 함수는 전역 변수 counter의 값을 사용한다
  * 전역이므로 누구나 접근할 수 있어 값이 변경될 수 있다
  * 즉, increaser 함수가 호출되기 직전에 전역변수 counter는 반드시 0이여야 한다
* 2번
  * 전역변수를 지역변수로 변경하여 의도치 않은 상태 변경을 방지하였다
  * 하지만 increaser가 호출될 때마다 지역변수 counter에 0이 할당되어 항상 1이 표시된다
* 3번
  * 변수 increaser에는 즉시실행함수가 호출되어 그 결과 함수가 할당된다
  * 즉, increaser를 호출하면 변수 increaser에 담긴 함수가 호출된다
  * 즉시실행함수는 한번만 실행되므로 increaser가 호출될 때마다 변수 counter가 재차 초기화될 일은 없을 것이다
  * increaser에 담겨있는 함수는 외부 함수의 변수 counter에 접근할 수 있고 변수 counter는 자신을 참조하는 함수가 소멸될 때까지 유지된다
  * increaser에 담긴 함수가 **클로저** 이다
  * 변수 counter는 외부에서 직접 접근할 수 없는 pirvate 변수이므로 전역 변수를 사용했을 때와 같이 의도되지 않은 변경을 걱정하지 않아도 된다

```JavaScript
// 함수를 인자로 전달받고 함수를 반환하는 고차 함수
function makeCounter(predicate) {
  // 자유 변수
  var num = 0;
  // 클로저
  return function () {
    num = predicate(num);
    return num;
  };
}

// 보조 함수
function increase(n) {
  return ++n;
}

// 보조 함수
function decrease(n) {
  return --n;
}

const increaser = makeCounter(increase);
console.log(increaser()); // 1
console.log(increaser()); // 2

const decreaser = makeCounter(decrease);
console.log(decreaser()); // -1
console.log(decreaser()); // -2
//클로저를 사용하는 예시
```

##  자주 발생하는 실수

```JavaScript
var arr = [];

for (var i = 0; i < 5; i++) {
  arr[i] = function () {
    return i;
  };
}

for (var j = 0; j < arr.length; j++) {
  console.log(arr[j]());
}
// arr에 순차적으로 0, 1, 2, 3, 4를 반환할 것 같지만
// 변수 i는 외부함수의 변수가 아닌 전역 변수이기 때문에 모두 '5'가 들어간다

for (var i = 0; i < 5; i++){
  arr[i] = (function (id) {
    return function () {
      return id;
    };
  }(i));
}
// 배열 arr에 즉시실행함수에 의해 함수가 반환된다
// 즉시실행함수는 i를 인자로 전달받고 매개변수 id에 할당한 후 내부 함수를 반환하고 life-cycle이 종료되며 id는 자유변수가 된다
// 배열 arr에 할당된 함수는 id를 반환하며 id는 상위 스코프의 자유변수이므로 그 값이 유지된다
// 이는 자바스크립트의 함수 레벨 스코프 특성으로 인해 for 루프의 초기문에서 사용된 변수의 스코프가 전역이 되기 때문에 발생하는 현상이다

for (let i = 0; i < 5; i++) {
  arr[i] = function () {
    return i;
  };
}
//ES6의 let을 사용한다면 전역으로 변하지 않기 때문에 생각대로 초기화 된다
```
