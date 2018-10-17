# 연산자

##  산술 연산자

|<cneter>Operator</center>|<center>Description</center>|<center>Example</center>|
|---|---|---|
|<center>+</center>|<center>덧셈</center>|<center>c = a + b</center>|
|<center>-</center>|<center>뺄셈</center>|<center>c = a - b</center>|
|<center>*</center>|<center>곱셈</center>|<center>c = a * b</center>|
|<center>/</center>|<center>나눗셈</center>|<center>c = a / b</center>|
|<center>%</center>|<center>나머지</center>|<center>c = a % b</center>|
|<center>++</center>|<center>증가</center>|<center>a++ / ++a</center>|
|<center>--</center>|<center>감소</center>|<center>a-- / --a</center>|
|<center>=</center>|<center>대입</center>|<center>a = b</center>|
|<center>+=</center>|<center>뒤에 값과 앞에 값을 더한 후 앞에 변수에 대입</center>|<center>a += b</center>|
|<center>-=</center>|<center>뒤에 값과 앞에 값을 뺀 후 앞에 변수에 대입</center>|<center>a -= b</center>|
|<center>*=</center>|<center>뒤에 값과 앞에 값을 곱한 후 앞에 변수에 대입</center>|<center>a *= b</center>|
|<center>/=</center>|<center>뒤에 값과 앞에 값을 나눈 후 앞에 변수에 대입</center>|<center>a /= b</center>|
|<center>%=</center>|<center>뒤에 값과 앞에 값을 나머지 연산 후 앞에 변수에 대입</center>|<center>a %= b</center>|
|<center>==</center>|<center>동등비교 / 형변환 후 비교</center>|<center>a == b</center>|
|<center>===</center>|<center>일치비교 / 타입까지 일치 확인</center>|<center>a === b</center>|
|<center>!=</center>|<center>부등비교</center>|<center>a != b</center>|
|<center>!==</center>|<center>불일치 비교</center>|<center>a !== b</center>|
|<center>></center>|<center>관계비교</center>|<center>a > b</center>|
|<center><</center>|<center>관계비교</center>|<center>a < b</center>|
|<center>>=</center>|<center>관계비교</center>|<center>a >= b</center>|
|<center><=</center>|<center>관계비교</center>|<center>a <= b</center>|
|<center>?</center>|<center>삼항 연산자</center>|<center>a = b ? 'true' : 'false'</center>|
|<center>||</center>|<center>or</center>|<center>if(조건1 || 조건2 (다중가능)){둘중 하나라도 true}</center>|
|<center>&&</center>|<center>and</center>|<center>if(조건1 || 조건2 (다중가능)){둘다 true}</center>|
|<center>!</center>|<center>not</center>|<center>!true = false / false! = true</center>|
|<center>typeof</center>|<center>데이터 타입을 문자열로 반환</center>|<center>typeof 1 (number)</center>|
|<center>!!</center>|<center>피연산자를 불린값으로 변환</center>|<center>!!1 = true / !!0 = false</center>|

##  제어문

블록 구문 - 구문들의 집합으로 중관호로 범위를 정함
> if{} / for{} / while{}에서 {}안에 있는 것들을 블록 구문이라 함

조건문 - 주어진 조건식이 true / false에 따라 실행되어질 구문들의 집함
> if...else / switch
if{조건식}{
  조건식이 참이면 여기 실행
}else{
  조건식이 거짓이면 여기 실행
}
switch(조건식){
  case:조건식1
    조건식1이면 여기 실행
  case:조건식2
    조건식2이면 여기 실행
  case:조건식3
    조건식3이면 여기 실행
  default:
    다 아니면 여기 실행
  //case는 몇개든 가능
}

반복문 - 조건식이 거짓일 때 까지 코드 블록을 실행
> for / while / do while
for([초기문];[조건식];[증감식]){
  구문
}

```javascript
var array = ['one', 'two', 'three', 'four'];

for (var i = 0; i < array.length; i++) {
  // console.log(array[i]);
  console.log('[' + i + '] = ' + array[i]);
}

// for-in
for (var index in array) {
  console.log('[' + index + '] = ' + array[index]);
}

// foreach
array.forEach(function (element, index, arr) {
  console.log('[' + index + '] = ' + element);
});

// for-of (ES6)
for (const element of array) {
  console.log(element);
}
// array.entries(): 배열의 key/value의 쌍을 반환하는 iterator를 반환
for (const [index, value] of array.entries()) {
  console.log('[' + index + '] = ' + value);
}
//모두 결과는
//[0] = one
//[1] = two
//[2] = three
//[3] = four
```

>while(조건식){
  구문
}
do{
  구문
}while(조건식)
do while은 무조건 구문을 1번은 실행한다
break는 코드 블럭을 탈출, continue는 continue 이후 구문의 실행을 스킵하고 다시 반복문의 조건식으로 이동한다

# 형변환

1. 암묵적 타입 강제 변환

자바스크립트는 암묵적 타입 강제 변환을 통해 조건식을 평가한다. 다시 말해, 조건식에 주어진 값이 Boolean 값이 아니더라도 암묵적 타입 강제 변환을 통해 Boolean 값으로 변환하여 평가한다.

자바스크립트는 문맥을 고려하여 타입을 암묵적으로 강제 변환하여 작업을 수행한다.
```javascript
if (true) {
  console.log('1');
}
if (1) {
  console.log('2');
}
if ('str') {
  console.log('3');
}
if (null) {
  console.log('4');
}
var x = '';
if (x) {
  console.log('5');
}
if (!x) {
  console.log('6');
}
//1, 2, 3, 6은 출력
console.log('1' > 0);            // true
console.log(1 + '2');            // '12'
console.log(2 - '1');            // 1
console.log('10' == 10);         // true
console.log('10' === 10);        // false
console.log(undefined == null);  // true
console.log(undefined === null); // false

var num = 2;
var str = '1';
// Bad
console.log(num - str);
// Good
console.log(num - parseInt(str));
//출력은 같지만 강제 변환되어 작업을 수행하므로 가독성이 떨어질 수 있다
```

2. [타입 변환 테이블](https://poiemaweb.com/js-type-coercion#2-%ED%83%80%EC%9E%85-%EB%B3%80%ED%99%98-%ED%85%8C%EC%9D%B4%EB%B8%94-type-conversion-table)

3. 타입 변환

래퍼 객체 생성자 함수를 new 연산자 없이 호출하면 타입을 변경한 값을 생성할 수 있다
```javascript
var x = false;

// 변수 x의 값을 숫자 타입으로 변환
console.log('Number : ' + Number(x));  // 0
// 변수 x의 값을 문자열 타입으로 변환
console.log('String : ' + String(x));  // 'false'
// 변수 x의 값을 불리언 타입으로 변환
console.log('Boolean: ' + Boolean(x)); // false

console.log(+10);     // 10(number)
console.log(+'10');   // 10(number)
console.log(+true);   // 1(number)
console.log(+false);  // 0(number)
console.log(+null);   // 0(number)
console.log(+undefined); // NaN
console.log(+NaN);    // NaN

var val = '123';
console.log(typeof val + ': ' + val); // string: 123

// sting -> number
val = +val; // "+": 단항 연산자(unary operator)
// val = val * 1;
// val = parseInt(val);
// val = Number(val);
console.log(typeof val + ': ' + val); // number: 123

// number -> sting
val = val + '';
// val = String(val);
// val = val.toString();
console.log(typeof val + ': ' + val); // string: 123
```

4. ture / false

false인 값
* false
* undefined
* null
* 0
* NaN
* ''

이외의 값들은 모두 true
```javascript
//주의!!
var b = new Boolean(false);
if (b) // true
```
