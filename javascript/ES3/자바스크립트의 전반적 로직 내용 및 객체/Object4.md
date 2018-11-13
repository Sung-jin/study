# 빌트인 객체

자바스크립트는 'Native object', 'Host object', 'User-defined object'와 같은 3개의 객체로 분류할 수 있다

* Native object
  * Object, String, Number, Boolean, Symbol, Function, Array, RegExp, Date, Math Promise...
* Host object
  * Global object - window, global(node.js)
  * DOM - Document, Event, HTMLElement, HTMLCollection, NodeList...
  * BOM - Location, Histroy, Navigator, Screen
  * Ajax - XMLHttpRequesFile, Canvas, Geolocation, Drag&Drop...

##  네이티브 객체

* ECMAScript 명세에 정의된 객체를 말하며 애플리케이션 전역의 공통 기능을 제공한다
* 네이티브 객체는 애플리케이션의 환경과 관계없이 언제나 사용할 수 있다
* Object, String, Number... 와 같은 객체 생성에 관계가 있는 함수 객체와 메소드로 구성된다

* Object
  * Object() 생성자 함수는 객체를 생성한다
  * 생성자 인수값이 null/undefinde이면 빈 객체를 반환한다
  * 그 이외의 경우 생성자 함수의 인수값에 따라 강제 형변환된 객체가 반환된다
  * 이때 반환된 객체의 [[Prototype]] 프로퍼티에 바인딩된 객체는 Object.prototype이 아니다
  * 객체를 생성할 경우 특수한 상황이 아니라면 객체리터럴 방식을 사용하는 것이 일반적이다

```JavaScript
var obj = new Object();
//생성자 함수를 이용한 객체 반환

var o = {};
//객체 리터럴 방식
```

* Function
  * 자바스크립트의 모든 함수는 Function 객체이며, 다른 모든 객체처럼 new 연산자를 이용하여 생성할 수 있다

* Boolean
  * Boolean은 원시타입 boolean을 위한 레퍼객체이다
  * Boolean 객체는 true / false를 포함하고 있는 객체이다

* Number
  * 원시 타입 number를 다룰 때 유용한 프로퍼티와 메소드를 제공하는 레퍼객체이다
  * 변수 또는 객체의 프로퍼티가 숫자를 값으로 가지고 있다면 Number 객체의 별도 생성없이 Number 객체의 프로퍼티와 메소드를 사용할 수 있다
  * **원시 타입이 래퍼 객체의 메소드를 사용할 수 있는 이유는 원시 타입으로 프로퍼티나 메소드를 호출할 때 원시 타입과 연관된 래퍼 객체로 일시적으로 변환되어 프로토타입 객체를 공유하게 되기 때문이다**
  * Number Constructor
    * Number 객체는 Number() 생성자 함수를 통해 생성할 수 있다
    * 인자가 숫자로 변환될 수 없다면 NaN을 반환한다
    * Number() 생성자 함수를 new 연산자를 붙이지 않아 생성자로 사용하지 않으면 Number 객체를 반환하지 않고 원시 타입 숫자를 반환하며, 이때 형 변환이 발생할 수 있다
    * 일반적으로는 숫자를 사용할 때 원시 타입 숫자를 사용한다
  * Number Property
    * 정적 프로퍼티로 Number 객체를 생성할 필요없이 Number.propertyName 형태로 사용한다
    * Number.EPSILON(ES6)
      * 자바스크립트에서 표현할 수 있는 가장 작은 수
      * 임의의 수와 그 수보다 큰 수 중 가장 작은 수와의 차이와 같다
      * 약 2.2204460492503130808472633361816E-16 또는 2-52이다.
      * 부동소수점 산술 연상 비교는 정확한 값을 기대하기 어렵다
      * 정수는 2진법으로 오차없이 저장이 가능하지만 부동소수점을 표현하는 IEEE754은 2진법으로 변환시 무한소수가 되어 미세한 오차가 발생하는 구조적 한계를 가진다
      * 즉, 부동소수점의 비교는 Number.EPSILON을 사용하여 비교 기능을 갖는 함수를 작성해야 한다
    * Number.MAX_VALUE
      * 자바스크립트에서 사용 가능한 가장 큰 숫자를 반환
      * 1.7976931348623157e+308
      * MAX_VALUE보다 큰 숫자는 Infinity 이다
    * Number.MIN_VALUE
      * 자바스크립트에서 사용 가능한 가장 작은 숫자를 반환
      * 5e-324
      * MIN_VALUE는 0에 가장 가까운 양수 값
      * MIN_VALUE보다 작은 숫자는 0으로 반환된다
    * Number.POSITIVE_INFINITY/NEGATIVE_INFINITY
      * 각각 양/음의 Infinity를 반환한다
    * Number.NaN
      * 숫자가 아님을 나타내는 숫자값이다
  * Number Method
    * Number.isFinite(testValue:number):boolean - ES6
      * 매개변수에 전달된 값이 정상적인 유한수인지 검사하여 그 결과를 Boolean으로 반환한다
      * Number.isFinite()는 전역 함수 isFinite()와 차이가 있다
      * 전역함수는 인수를 숫자로 변환하여 검사를 수행
      * Number.isFinite()는 인수를 변환하지 않는다
      * 즉, 숫자가 아닌 인수가 주어지면 항상 false가 된다
    * Number.isInteger(testValue:number):boolean - ES6
      * 매개변수에 전달된 값이 정수인지 검사하여 그 결과를 Boolean으로 반환하며 검사전에 인수를 숫자로 변환하지 않는다
    * Number.isNaN(testValue:number):boolean - ES6
      * 매개변수에 전달된 값이 NaN인지를 검사하여 그 결과를 Boolean으로 반환한다
      * Number.isNaN()은 전역 함수 isNaN()와 차이가 있다
      * 전역함수는 인수를 숫자로 변환하여 수행
      * Number.isNaN()은 인수를 변환하지 않는다
      * 즉, 숫자가 아닌 인수가 주어지면 항상 false가 된다
    * Number.isSafeInteger(testValue:number):boolean - ES6
      * 매개변수에 전달된 값이 안전한 정수값인지 검사하여 그 결과를 Boolean으로 반환한다
      * 안전한 정수값은 -(253 - 1)와 253 - 1 사이의 정수값이며, 검사전에 인수를 숫자로 변환하지 않는다
    * Number.prototype.toExponential(fractionDigits?:number):string - ES3
      * 대상을 지수 표기법으로 변환하여 문자열로 반환한다
      * 지수 표기법이란 매우 큰 숫자를 표기할 때 주로 사용하여 Exponent 앞에 있는 숫자에 10의 n승이 곱하는 형식으로 수를 나타내는 방식이다
      * 1234 = 1.234e+3
    * Number.prototype.toFixed(fractionDigits?:number):string - ES3
      * 매개변수로 지정된 소숫점자리를 반올림하여 문자열로 반환한다
    * Number.prototype.toPrecision(precision?:number):string - ES3
      * 매개변수로 지정된 전체 자릿수까지 유효하도록 나머지 자릿수를 반올림하여 문자열로 반환한다
      * 지정된 전체 자릿수로 표현할 수 없는 경우 지수 표기법으로 결과를 반환한다
    * Number.prototype.toString(radix?:number):string - ES1
      * 숫자를 문자여롤 변환하여 반환한다
    * Number.prototype.valueOf():number - ES1
      * Number 객체의 원시 타입 값을 반환한다

```JavaScript
var x = 123;
var y = new Number(123);

console.log(x == y);  // true
console.log(x === y); // false

console.log(typeof x); // number
console.log(typeof y); // object

//Number.EPSILON을 이용한 부동소수점 비교
console.log(0.1 + 0.2);        // 0.30000000000000004
console.log(0.1 + 0.2 == 0.3); // false!!!

function isEqual(a, b){
  // Math.abs는 절댓값을 반환한다.
  // 즉 a와 b의 차이가 JavaScript에서 표현할 수 있는 가장 작은 수인 Number.EPSILON보다 작으면 같은 수로 인정할 수 있다.
  return Math.abs(a - b) < Number.EPSILON;
}

console.log(isEqual(0.1 + 0.2, 0.3)); //true
```

* Math
  * 수학 상수와 함수를 위한 프로퍼티와 메소드를 제공하는 빌트인 객체이다
  * 별도의 생성자가 없는 정적 프로퍼티와 메소드이다
  * Math.PI
    * PI 값을 반환한다
    * 3.141592653589793
  * Math Method
    * Math.abs(x:number):number - ES1
    * 반드시 0 또는 양수이어야하는 절대값을 반환한다
  * Math.round(x:number):number - ES1
    * 숫자를 가장 인접한 정수로 올림/내림한다
  * Math.sqrt(x:number):number - ES1
    * 양의 제곱근을 반환한다
```JavaScript
Math.sqrt(9);   //3
Math.sqrt(-9);  //NaN
```
  * Math.floor(x:number):number - ES1
    * 지정된 숫자를 자신보다 작은, 가장 가까운 정수로 내림한다
    * 즉, 소수점 이하의 값을 제거한 정수를 얻는다
```JavaScript
Math.floor(1.9);   //1
Math.floor(-1.9);  //-2
```
  * Math.random():number - ES1
    * 0이상 1미만 사이의 임의의 숫자를 반환한다
  * Math.pow(x:number, y:number):number - ES1
    * 첫번째 인수를 밑, 두번째 인수를 지수로하여 거듭제곱을 반환한다
  * Math.max(...values:number[]):number - ES1
    * 인수 중 가장 큰 수를 반환한다
  * Math.min(...values:number[]):number - ES1
    * 인수 중 가장 작은 수를 반환한다

* Date
  * 날짜와 시간(년, 월, 일, 시, 분, 초, 밀리초)을 위한 메소드를 제공하는 빌트인 객체이다
  * 내부적으로 Date 객체는 숫자값을 갖는다
  * 1970년 1월 1일 00:00(UTC)을 기점으로 현재 시간까지의 밀리초를 나타낸다
  * 현재의 날짜와 시간은 자바스크립트 코드가 동작한 시스템의 시계에 의해 결정된다
  * 시스템 시계의 설정에 따라 서로 다른 값을 가질 수 있다
  * Date Constructor
    * Date 생성자를 사용하여 날짜와 시간을 가지는 인스턴스를 생성한다
    * 생성된 인스턴스는 기본적으로 현재 날짜와 시간을 나타내는 값을 가진다
    * 다른 날짜와 시간을 다루고 싶은 경우 생성자의 인수에 해당 날짜와 시간 정보를 명시적으로 지정한다
    * Date() 생성자 함수를 new 연산자없이 사용하면 Date 객체를 반환하지 않고 결과값을 문자열로 반환한다
  * new Date()
    * 매개변수가 없는 경우 현재 날짜와 시간을 가지는 인스턴스를 반환한다
  * new Date(milliseconds)
    * 매개변수에 밀리초를 전달하면 1970년 1월 1일 00:00(UTC)을 기점으로 전달된 밀리초만큼 경과한 날짜와 시간을 가지는 인스턴스를 반환한다
    * 1s = 1,000ms / 1m = 60,000ms / 1h = 3,600,000ms / 1d = 86,400,000ms
  * new Date(dateString)
    * 매개변수에 날짜와 시간을 나타내는 문자열을 전달하면 지정된 날짜와 시간을 가지는 인스턴스를 반환한다
    * 이때 함수에 전달된 문자열은 parse() 메소드에 의해 인식 가능한 형식이어야 한다
  * new Date(year, month[, day, hour, minute, second, millisecond])
    * 매개변수에 년, 월, 일, 시, 분, 초, 밀리초를 의미하는 숫자를 전달하면 지정된 날짜와 시간을 가지는 인스턴스를 반환한다
    * 이때 년, 월을 반드시 지정하여야 한다
    * 지정하지 않은 옵션은 0 또는 1로 초기화 된다
  * Date Method
    * Date.now()
      * 1970년 1월 1일 00:00:00(UTC)을 기점으로 현재 시간까지 경과한 밀리초를 숫자로 반환한다
    * Date.parse()
      * 1970년 1월 1일 00:00:00(UTC)을 기점으로 매개변수로 전달된 지정 시간까지의 밀리초를 숫자로 반환한다
    * Date.UTC()
      * 1970년 1월 1일 00:00:00(UTC)을 기점으로 매개변수로 전달된 지정 시간까지의 밀리초를 숫자로 반환한다
    * Date.prototype.getFullYear()
      * 해당 연도를 나타내는 4자리 숫자를 반환한다
    * Date.prototype.setFullYear()
      * 해당 연도를 나타내는 4자리 숫자를 설정한다
      * 연도 이외 월, 일도 설정할 수 있다
      * setFullYear(yearValue[, monthValue[, dayValue]])
    * Date.prototype.getMonth()
      * 해당 월을 나타내는 0~11의 정수를 반환한다
    * Date.prototype.setMonth()
      * 해당 월을 나타내는 0~11의 정수를 설정한다
      * 월 이외 일도 설정할 수 있다
    * Date.prototype.getDate()
      * 해당 날짜(1~31)를 나타내는 정수를 반환한다
    * Date.prototype.setDate()
      * 해당 날짜를 나타내는 정수를 설정한다
    * Date.prototype.getDay()
      * 해당 요일을 나타내는 정수를 반환한다
      * 0: 일요일 / 1: 월요일 / ... / 6: 토요일
    * Date.prototype.getHours()
      * 해당 시간(0~23)을 나타내는 정수를 반환한다
    * Date.prototype.setHours()
      * 해당 시간을 나타내는 정수를 설정한다
      * 시간 이외 분, 초, 밀리초도 설정할 수 있다
      * setHours(hoursValue[, minutesValue[, secondsValue[, msValue]]])
    * Date.prototype.getMinutes()
      * 해당 분(0~59)을 나타내는 정수를 반환한다
    * Date.prototype.setMinutes()
      * 해당 분을 나타내는 정수를 설정한다
      * 분 이외 초, 밀리초도 설정할 수 있다
    * Date.prototype.getSeconds()
      * 해당 초(0~59)를 나타내는 정수를 반환한다
    * Date.prototype.setSeconds()
      * 해당 초를 나타내는 정수를 설정한다
      * 초 이외 밀리초도 설정할 수 있다
    * Date.prototype.getMilliseconds()
      * 해당 밀리초(0~999)를 나타내는 정수를 반환한다
    * Date.prototype.setMilliseconds()
      * 해당 밀리초를 나타내는 정수를 설정한다
    * Date.prototype.getTime()
      * 1970년 1월 1일 00:00:00(UTC)를 기점으로 현재 시간까지 경과된 밀리초를 반환한다
    * Date.prototype.setTime()
      * 1970년 1월 1일 00:00:00(UTC)를 기점으로 현재 시간까지 경과된 밀리초를 설정한다
    * Date.prototype.getTimezoneOffset()
      * UTC와 지정 로케일 시간과의 차이를 분단위로 반환한다
      * 우리나라 KST는 UTC와 9시간 차이가 난다
      * 즉, UTC = KST - 9h
    * Date.prototype.toDateString()
      * 사람이 읽을 수 있는 형식의 문자열로 날짜를 반환한다
    * Date.prototype.toTimeString()
      * 사람이 읽을 수 있는 형식의 문자열로 시간을 반환한다

* String
  * String 객체는 원시 타입인 문자열을 다룰 때 유용한 프로퍼티와 메소드를 제공하는 레퍼객체이다
  * 변수 또는 객체 프로퍼티가 문자열을 값으로 가지고 있다면 String 객체의 별도 생성없이 String 객체의 프로퍼티와 메소드를 사용할 수 있다
  * **원시 타입이 래퍼 객체의 메소드를 사용할 수 있는 이유는 원시 타입으로 프로퍼티나 메소드를 호출할 때 원시 타입과 연관된 래퍼 객체로 일시적으로 변환되어 프로토타입 객체를 공유하게 되기 때문이다**
  * String Constructor
    * String() 생성자 함수를 통해 String 객체를 생성할 수 있으며, 전달된 인자는 모두 문자열로 변환된다
    * new 연산자를 사용하지 않고 String() 생성자 함수를 호출하면 String 객체가 아닌 문자열 리터럴을 반환하며 형 변환이 발생할 수 있다
    * 일반적으로 문자열을 사용할 때는 원시 타입 문자열을 사용한다
  * String Property
    * String.length
      * 문자열 내의 문자 갯수를 반환한다
      * length 프로퍼티를 소유하고 있으므로 String 객체는 유사 배열 객체이다
  * String Method
    * 문자열은 변경 불가능한 값이기 때문에, String 객체의 모든 메소드는 언제나 새로운 문자열을 반환한다
    * String.prototype.charAt(pos:number):string - ES1
      * 매개변수로 전달한 index 번호에 해당하는 위치의 문자를 반환한다
      * index 번호는 0~문자열-1 사이의 정수
      * 지정한 index가 범위를 벗어날 경우 빈문자열을 반환한다
    * String.prototype.concat(...string:string[]):string - ES3
      * 매개변수로 전달된 1개 이상의 문자열과 연결하여 새로운 문자열을 반환한다
      * concat 메소드를 사용하는 것보다는 +, += 할당 연산자를 사용하는 것이 성능상 유리하다
    * String.prototype.indexOf(searchString:string, fromIndex=0):number - ES1
      * 매개변수로 전달된 문자 또는 문자열을 대상 문자열에서 검색하여 처음 발견된 곳의 index를 반환한다
      * 발견하지 못할 경우 -1을 반환한다
    * String.prototype.lastIndexOf(searchString:string, fromIndex=this.length-1):number - ES1
      * 매개변수로 전달된 문자 또는 문자열을 대상 문자열에서 검색하여 마지막으로 발견된 곳의 index를 반환한다
      * 발견하지 못할 경우 -1을 반환한다
    * String.prototype.replace(searchValue:string|RegExp, replaceValue:string):string - ES3
      * 첫번째 인자에 전달된 문자열 또는 정규표현식을 대상 문자열에서 검색하여 두번째 인자에 전달된 문자열로 대체한다
      * **원본 문자열은 변경되지 않고 결과가 반영된 새로운 문자열을 반환한다**
      * 검색된 문자열이 복수 존재할 경우 첫번째로 검색된 문자열만 대체된다
    * String.prototype.prototype.split(separator:string|RegExp, limit?:number):string[] - ES3
      * 첫번째 인자에 전달된 문자열 또는 정규표현식을 대상 문자열에서 검색하여 문자열을 구분한 후 분리된 각 문자열로 이루어진 배열을 반환한다
      * **원본 문자열은 변경되지 않는다**
      * 인수가 없는 경우 대상 문자열 전체를 단일 요소로 하는 배열을 반환한다
    * String.prototype.substring(start:number, end=this.length):string - ES3
      * 첫번째 인자에 전달된 index에 해당하는 문자부터 두번째 인자에 전달된 index에 해당하는 문자의 바로 이전 문자까지를 모두 반환한다
      * 이때 첫번째 인수 < 두번째 인수의 관계가 성립한다
      * 첫번째 인수 > 두번째 인수인 경우 두 인수는 교환
      * 두번째 인수가 생략될 경우 해당 문자열의 끝까지 반환
      * 인수 < 0 | NaN일 경우 0으로 취급
      * 인수 > 문자열의 길이일 경우 인수는 문자열의 길이로 취급된다
    * String.prototype.toLowerCase():string - ES1
      * 대상 문자열의 모든 문자를 소문자로 변경한다
    * String.prototype.toUpperCase():string - ES1
      * 대상 문자열의 모든 문자를 대문자로 변경한다
    * String.prototype.trim():string - ES5
      * 대상 문자열 양쪽 끝에 있는 공백 문자를 제거한 문자열을 반환한다
      * **해당 문자열 자신은 변경되지 않는다**

* RegExp
  * 문자열에서 특정 내용을 찾거나 대체 또는 발췌하는데 사용한다
  * '시작 패턴 종료기호 플래그'와 같이 표현된다
  * /regexp/i
  * 플래그
    * i(Ignore Case) - 대소문자를 구별하지 않고 검색한다
    * g(Global) - 문자열 내의 모든 패턴을 검색한다
    * m(Multi Line) - 문자열의 행이 바뀌더라도 검색을 계속한다
    * 플래그는 옵션이므로 선택적으로 사용한다
    * 플래그가 없을 경우 매칭 대상이 1개 이상이더라도 첫번째 매칭 대상만 검색하고 종료한다
  * 패턴
    * 찾고자 하는 대상을 문자열로 지정한다
    * 문자열의 따옴표는 생략하며, 따옴표를 포함하면 따옴표까지 검색한다
    * 패턴은 특별한 의미를 가지는 메타문자 또는 기호로 표현할 수 있다
    * '.'은 임의의 문자 한 개를 의미한다
    * 패턴에 문자 또는 문자열을 지정하면 일치하는 문자 또는 문자열을 추출한다
    * 앞선 패턴을 최소 한번 반복하려면 앞선 패턴 뒤에 '+'를 붙인다
    * '|'를 사용하면 or의 의미를 가진다
    * '[]'내의 문자는 or로 동작한다
    * '[]'내에서 '-'를 사용하면 범위를 지정할 수 있다
    * '/[\\d]+/g'는 모든 숫자를 판별하는 패턴이다
    * '/[\\w]+/g'는 모든 알파벳과 숫자를 판별하는 패턴이다
  * 자주 쓰이는 정규표현식

```JavaScript
// 'http'로 시작하는지 검사
var regexp = /^http/;
// 'html'로 끝나는지 검사
var regexp = /html$/;
// 모두 숫자인지 검사
var regexp = /^\d+$/;
// 1개 이상의 공백으로 시작하는지 검사
var regexp = /^[\s]+/;
// 알파벳 대소문자 또는 숫자로 시작하고 끝나며 4 ~10자리인지 검사
var regexp = /^[A-Za-z0-9]{4,10}$/
// 메일 주소 형식에 맞는지 검사
var regexp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
// 핸드폰 번호 형식에 맞는지 검사
var regexr = /^\d{3}-\d{3,4}-\d{4}$/;
//특수 문자 포함 여부를 검사
var regexr = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi
```

  * RegExp Constructor
    * RegExp 객체를 생성하기 위해서 리터럴 방식과 RegExp 생성자 함수를 사용할 수 있으며, 일반적인 방법은 리터럴 방식이다
    * new RegExp(pattern[, flags])
    * pattern - 정규표현식의 텍스트 / flags - 정규표현식의 플래그
  * RegExp Method
    * RegExp.prototype.exec(target:string):RegExpExecArray|null - ES3
      * 문자열 검색하여 매칭 결과를 반환한다
      * 반환값은 배열 또는 null이다
      * exec 메소드는 g 플래그를 지정하여도 첫번째 매칭 결과만을 반환한다
    * RegExp.prototype.test(target:string):boolean - ES3
      * 문자열을 검색하여 매칭 결과를 반환한다
      * 반환값은 true 또는 false이다

* Array
  * 배열 리터럴
    * 0개 이상으 기밧을 쉼표로 구분하여 대괄호로 묶는다
    * 첫 인덱스는 '0'으로 읽을 수 있다
    * 존재하지 않는 요소에 접근하면 undefined를 반환한다
    * 배열 리터럴과 객체 리터럴의 근본적인 차이는 배열의 프로토타입은 Array.prototype, 객체의 프로토타입은 Object.prototype이다
    * 자바스크립트 배열은 어떤 데이터 타입의 조합이라도 포함할 수 있다
  * Array() 생성자 함수
    * 배열 리터럴 방식은 내장 함수 Array() 생성자 함수로 배열을 생성하는 것을 단순화 한 것이다
    * Array() 생성자 함수는 Array.prototype.constructor 프로퍼티로 접근할 수 있다
    * Array() 생성자 함수는 매개변수의 갯수에 따라 다르게 동작한다
      * 매개변수가 1개이고 숫자인 경우 매개변수로 전달된 숫자를 length 값으로 가지는 빈 배열을 생성한다
      * 그 외의 경우 매개변수로 전달된 값들을 요소로 가지는 배열을 생성한다
  * 배열 요소의 추가와 삭제
    * 배열 요소의 추가
      * 순서에 맞게 값을 할당할 필요는 없고 필요한 인덱스 위치에 값을 할당한다
      * 값이 할당되지 않은 인덱스 위치의 요소는 생성되지 않는다
      * 단, 존재하지 않는 요소를 참조하면 undefined가 반환된다
      * 배열의 길이는 최종 인덱스 위치의 기준으로 산정된다
    * 배열 요소의 삭제
      * 배열은 객체이기 때문에 배열의 요소를 삭제하기 위해 delete 연산자를 사용할 수 있다
      * 이때 length는 변환이 없다
      * 해당 요소를 완전히 삭제하여 length에도 반영하기 위해서는 Array.prototype.splice 메소드를 사용하여야 한다
    * 배열 요소의 열거
      * 배열은 객체이므로 for in 문을 사용할 수 있다
      * 배열은 객체이기 때문에 프로퍼티를 가질 수 있다
      * for in 문을 사용하면 불필요한 프로퍼티까지 출력될 수 있고 요소들의 순서를 보장하지 않으므로 배열을 열거하는데 적합하지 않다
      * 즉, 배열 요소의 열거에는 forEach 메소드 또는 for 문을 사용하는 것이 좋다
  * Array Property
    * Array.length
      * 요소의 개수(배열의 길이)를 나타낸다
      * 배열 인덱스는 32비트 양의 정수로 처리된다
      * 232 - 1(4,294,967,296 - 1) 미만이다
      * 배열 요소의 개수와 length 프로퍼티의 값이 반드시 일치하는 것은 아니다
        * 배열에 요소의 개수와 length 프로퍼티의 값이 일치하지 않는 배열을 희소 배열이라 한다
        * 희소 배열은 배열의 요소가 연속적이지 않은 배열을 의미한다
        * 희소 배열이 아닌 일반 배열은 배열의 요소 개수와 length 프로퍼티의 값이 언제나 일치하지만 희소 배열은 배열의 요소 개수보다 length 프로퍼티의 값이 언제나 크다
        * 희소 배열은 일반 배열보다 느리며 메모리를 낭비한다
      * 현재 length 프로퍼티 값보다 더 큰 인덱스로 요소를 추가하면 새로운 요소를 추가할 수 있도록 자동으로 length 프로퍼티의 값이 늘어난다
      * length 프로퍼티의 값을 명시적으로 변경할 수 있으며, 현재보다 작은 값으로 변경한다면 변경된 값보다 크거나 같은 인덱스에 해당하는 요소는 모두 삭제가 된다
  * ArrayMethod
    * Array.isArray(arg:any):boolean - ES5
      * 객체가 배열이면 true, 배열이 아니면 false를 반환한다
    * Array.prototype.indexOf(searchElement:T, fromIndex?:number):number - ES5 | 원본 배열을 변경하지 않음
      * indexOf 메소드의 인자로 지정된 요소를 배열에서 검색하여 인덱스를 반환한다
      * 중복되는 요소가 있는 경우 첫번째 인덱스만 반환한다
      * 해당하는 요소가 없는 경우 -1을 반환한다
    * Array.prototype.concat(...items:Array<T[] | T):T[] - ES3 | 원본 배열을 변경하지 않음
      * concat 메소드의 인수로 넘어온 배열 또는 값을 자신의 본사본에 요소로 추가하고 반환한다
    * Array.prototype.join(separator?:string):string - ES1 | 원본 배열을 변경하지 않음
      * 배열 요소 전체를 연결하여 생성한 문자열을 반환한다
      * 구분자는 생략 가능하며 기본 구분자는 ',' 이다
      * Array.prototype.join() 메소드는 + 연산자보다 빠르다
    * Array.prototype.pop():T | undefined - ES3 | 원본 배열을 변경
      * 배열에서 마지막 요소를 제거하고 제거한 요소를 반환한다
      * 만약 빈 배열일 경우 undefined를 반환한다
    * Array.prototype.push(...items:t[]):number - ES3 | 원본 배열을 변경
      * 인자로 전달된 항목을 배열의 마지막에 추가한다
      * concat 메소드와 다르게 인자로 전달된 항목을 마지막 요소로 추가한다
      * 반환값은 배열의 새로운 length 값이다
    * Array.prototype.reverse():this - ES1 | 원본 배열을 변경
      * 배열 요소의 순서를 반대로 변경한다
      * 반환값은 변경된 배열이다

* Error
  * Error 생성자는 error 객체를 생성한다
  * error 객체의 인스턴스는 런타임 에러가 발생하였을 때 throw 된다
  * EvalError / InternalError / RangeError / ReferenceError / SyntaxError / TypeError / URIError 등의 Error에 관련한 객체가 있다

* Symbol
  * ES6에서 추가된 유일하고 변경 불가능한 원시 타입으로 Symbol 객체는 원시 타입 Symbol 값을 생성한다

* 원시 타입과 래퍼객체
  * 네이티브 객체는 각자의 프로퍼티와 메소드를 가진다
  * 정적 프로퍼티, 메소드는 해당 인스턴스를 생성하지 않아도 사용할 숭 씨고 prototype에 속해있는 메소드는 해당 prototype을 상속받은 인스턴스강 씨어야만 사용할 수 있다
  * 하지만, 원시 타입 값에 대해 표준 빌트인 객체의 메소드를 호출하면 정상적으로 작동한다
  * 이는 원시 타입 값에 대해 표준 빌트인 객체의 메소드를 호출할 때, 원시 타입 값은 연관된 객체로 일시 변환되기 때문이다
  * 그리고 메소드 호출이 종료되면 객체로 변환된 원시 타입 값은 다시 원시 타입 값으로 복귀한다
  * Wrapper 객체는 String, Number, Boolean이 있다

```JavaScript
var str = 'Hello world!';
var res = str.toUpperCase();
console.log(res); // 'HELLO WORLD!'

var num = 1.5;
console.log(num.toFixed()); // 2
```

##  호스트 객체

* 브라우저 환경에서 제공하는 호스트 환경에 정의된 객체를 말한다
* 브라우저에서 동작하는 환경과 브라우저 외부에서 동작하는 환경의 자바스크립트(Node.js)는 다른 호스트 객체를 사용할 수 있다
* 브라우저에서 동작하는 환경의 호스트 객체는 전역 객체인 window, BOM, DOM 및 XMLHttpRequest 객체 등을 제공한다

* 전역 객체 - Global Object
  * 모든 객체의 유일한 최상위 객체를 의미
  * Browser-side : window / Server-side(Node.js) : global 객체를 의미
* Browser Object Model(BOM)
  * 브라우저 객체 모델은 브라우저 탭 또는 브라우저 창의 모델을 생성한다
  * 최상위 객체는 window 객체로 현재 브라우저 창 또는 탭을 표현하는 객체이다
  * 이 객체의 자식 객체들은 브라우저의 다른 기능들을 표현한다
  * 이 객체들은 Standard Built-in Objects가 구성된 후에 구성된다
* Document Object Model(DOM)
  * 문서 객체 모델은 현재 웹페이지의 모델을 생성한다
  * 최상위 객체는 document 객체로 전체 문서를 표현한다
  * 이 객체의 자식 객체들은 문서의 다른 요소들을 표현한다
  * 이 객체들은 Standard Built-in Objects가 구성된 후에 구성된다

##  전역객체

* 모든 객체의 유일한 최상위 객체를 의미
* Browser-side : window / Server-side(Node.js) : global
* 전역 객체는 실행 컨텍스트에 컨트롤이 들어가기 이전에 생성이 되며 constructor가 없기 때문에 new 연산자를 이용하여 새롭게 생성할 수 없다
* 전역 객체는 전역 스코프를 가지게 된다
* 전역 객체의 자식 객체를 사용할 때 전역 객체의 기술은 생략할 수 있다
* 전역 객체는 전역 변수를 프로퍼티로 가지게 된다
* 글로벌 영역에 선언한 함수도 전역 객체의 프로퍼티로 접근할 수 있다
* Standard Built-in Objects도 전역 객체의 자식 객체이다

* 전역 프로퍼티
  * Infinity
    * 양/음의 무한대를 나타내는 숫자값 Infinity를 가진다
  * NaN
    * 숫자가 아님을 나타내는 숫자값
    * NaN 프로퍼티는 Number.NaN 프로퍼티와 같다
  * undefined
    * undefined 프로퍼티는 원시 타입 undefined를 값으로 가진다

* 전역 함수
  * eval()
    * 매개변수에 전달된 문자열 구문 또는 표현식을 평가 또는 실행한다
    * 사용자로 부터 입력받은 콘텐츠를 eval()로 실행하는 것은 보안에 매우 취약하다
    * eval()의 사용은 가급적으로 금지되어야 한다
  * isFinite()
    * 매개변수에 전달된 값이 정상적인 유한수인지 검사하여 그 결과를 Boolean으로 반환
    * 전달된 값이 숫자가 아닌 경우, 숫자로 변환한 후 검사를 수행
    * isFinite(null)은 null이 숫자 0으로 변환 후 수행하므로 true
  * isNaN
    * 매개변수에 전달된 값이 NaN인지 검사하여 그 결과를 Boolean으로 반환
  * parseFloat()
    * 매개변수에 전달된 문자열을 부동소수점 숫자로 변환하여 반환
    * 문자열의 첫 숫자만 반환되며 전후 공백은 무시된다
    * 첫문자를 숫자로 변환할 수 없다면 NaN을 반환한다
  * parseInt()
    * 매개변수에 전달된 문자열을 정수형 숫자로 해석하여 반환한다
    * parseInt(string, radix);
    * radix: 진법을 나타내는 기수(2 ~ 36, default: 10)
    * 첫번째 매개변수에 전달된 문자열의 첫번째 문자가 해당 지수의 숫자로 변환될 수 없다면 NaN을 반환
  * encodeURI()/decodeURI()
    * encodeURI()는 매개변수로 전달된 URI를 인코딩한다
    * 인코딩은 URI의 문자들을 이스케이프 처리하는 것
    * 알파벳, 0~9, - _ . ! ~ * ' ()는 이스케이프 처리에서 제외
    * decodeURI()는 매개변수로 전달된 URI을 디코딩한다
  * encodeURIComponent()/decodeURIComponent()
    * 매개변수로 전달된 URI의 구성요소를 인/디코딩한다
    * encodeURIComponent()는 인수를 쿼리스트링의 일부라고 간주하며 =, ?, &를 인코딩한다
    * encodeURI()는 인수를 URI 전체라고 간주하며 파라미터 구분자인 =, ?, &를 인코딩 하지 않는다

```JavaScript
var uriComp = '안녕&foo=bar&foobar';

// encodeURI / decodeURI
var enc = encodeURI(uriComp);
var dec = decodeURI(enc);
console.log(enc);
// %EC%95%88%EB%85%95&foo=bar&foobar
console.log(dec);
// 안녕&foo=bar&foobar

// encodeURIComponent / decodeURIComponent
enc = encodeURIComponent(uriComp);
dec = decodeURIComponent(enc);
console.log(enc);
// %EC%95%88%EB%85%95%26foo%3Dbar%26foobar
console.log(dec);
// 안녕&foo=bar&foobar
```
