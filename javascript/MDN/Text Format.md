### 문자열 리터럴과 개체

* 문자열 기본 데이터 형식의 래퍼
* js 에서 문자열 리터럴 `('문자열')` 을 임시 문자열 개체로 변환하고, 메서드를 호출한 이후 임시 문자열 개체를 삭제한다.
* String 개체를 사용할 필요가 없는 한, 직관에 반하는 행동을 할 수 있기 때문에 리터럴을 사용하는게 좋다.
* 문자열 리터럴은 배열과 같은 객체이므로, 인덱스로 특정 문자에 접근은 할 수 있지만 인덱스로 변경은 불가능하다.

```js
var s = new String('foo');
console.log(s); // String {"foo"}
typeof s; // 'object'

var s1 = '2 + 2';
var s2 = new String('2 + 2');
eval(s1); // 4
eval(s2); // '2 + 2'

console.log(s1[2]); // +
s1[2] = '-';        // 원본 객체에 영향은 없다.
console.log(s1[2]); // +
```

### 문자열 개체의 메서드

> var s = '2 + 2';

| 메소드 | 설명 |
| ---- | ---- |
| charAt, charCodeAt, codePointAt | 문자열에서 지정된 위치에 문자나 문자 코드를 반환 |
| indexOf, lastIndexOf | 문자열에서 지정된 부분의 문자열 인덱스나, 지정된 부분 마지막 위치를 반환 |
| startsWith, endsWith, includes | 문자열 시작 / 끝 / 지정된 문자열이 존재하는지 여부를 반환 |
| concat | 두 문자열의 텍스트를 결합하고 새로운 문자열을 반환 |
| fromCharCode, fromCodePoint | 유니코드 값의 지정된 시퀀스로부터 문자열을 구축 <br/> 문자열 인스턴스가 아닌 문자열 클래스 메서드 |
| split | 부분 문자열로 문자열을 분리하여 문자열 배열로 문자열 개체를 분할 |
| slice | 문자열의 한 부분을 추출하고 새 문자열을 반환 |
| substring, substr | 어느 시작 및 종료 인덱스 또는 시작 인덱스 및 길이를 지정하여 문자열의 지정된 일부를 반환 |
| match, replace, search | 정규 표현식 작업에 사용 |
| toLowerCase, toUpperCase | 모든 소문자/대문자 문자열로 변환하여 반환 |
| normalize | 호출 문자열 값의 유니 코드 표준화 양식을 반환 |
| repeat | 주어진 횟수를 반복하는 개체 요소로 이루어진 문자열을 반환 |
| trim | 문자열의 시작과 끝의 공백을 제거 |

### 다중 선 템플릿 문자열

* 포함 식을 허용하는 문자열 리터럴
* \`\` 으로 감싸서 선언할 수 있다.
    * \`${expression}\` 형태로 문자열 안에 표현식을 사용할 수 있다.
    * 또한 엔터로 개행을 표현할 수 있다.

```js
var s1 = `foo
bar`;
console.log(s1);
// foo
// bar
var s2 = `2 + 2 = ${2 + 2}`;
cosnole.log(s2); // 2 + 2 = 4
```
