# 데이터 타입

* 데이터 타입은 크게 2가지로 나뉜다.
    1. primitive type (원시 타입) - 변수를 선언하고 그 변수에 저장되는 것들
        * 정수 타입
        * 실수 타입
        * 논리 타입
    2. reference type (참조 타입) - 선언된 변수는 객체 주소를 가르키며, 그 주소에 데이터가 존재되며 생성 된 객체는 heap 영역에 존재한다.
        * 배열 타입
        * 열거 타입
        * 클래스
        * String

# Memory Area

![Memory Area](https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.imgur.com%2FpAh5gIZ.png&imgrefurl=https%3A%2F%2Fminwan1.github.io%2F2018%2F06%2F06%2F2018-06-06-Java%2CJVM%2F&tbnid=WK6PFCE2fHCL_M&vet=12ahUKEwig-53krabpAhUEc5QKHXreB3EQMygLegUIARDoAQ..i&docid=DD7xv7xVvbT9YM&w=739&h=678&q=java%20runtime%20area&ved=2ahUKEwig-53krabpAhUEc5QKHXreB3EQMygLegUIARDoAQ)
<br/>출처 [세바의 코딩교실](https://programmer-seva.tistory.com/72)

## 메소드 영역

* .class 파일을 클래스 로더를 통해 분류하여 저장한다.
    * runtime constant pool
    * field data
    * method data
    * method code
    * constructor