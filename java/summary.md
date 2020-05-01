# JAVA 개요

* Java Runtime Environment 가 설치되어 있다면 어떤 운영체제든 실행 가능하다.
    * 이식성이 높다.
* 객체 지향 프로그래밍(Object Oriented Programming) 이다.
* 함수형 프로그래밍을 지원한다.
    * JAVA8 부터 람다식을 지원한다.
* 메모리를 자동 관리한다.
    * c++ 에서 생성된 객체를 제거하기 위해서 코드로 할당 해제를 시켜줘야 한다.
    * 자바는 자동으로 메모리에 할당하고, GC를 통해서 사용하지 않는 객체를 할당 해제한다.
* JAVA SE(Standard Edition)
    * 자바 가상 기계(JVM)를 비롯한 자바 개발에 필수적인 도구와 라이브러리 API를 정의.
    * 클라이언트/서버 프로그램에 상관없이 JAVA SE 구현체인 JDK를 설치해야 한다.
* JAVA EE(Enterprise Edition)
    * 분산 환경에서 서버용 애플리케이션을 개발하기 위한 도구 및 라이브러리 API를 정의.
    * Servlet/JSP | EJB(Enterprise Java Bean) | XML 웹 서비스 등이 존재.
* 멀티 스레드, 동적 로딩, 오픈소스 라이브러리 등을 제공한다.

# JVM

* Java 프로그램을 해석하고 실행 할 수 있는 가상의 운영체제를 Java Virtual Machine(JVM) 이라 한다.
* 운영체제마다 프로그램을 실행하고 관리하는 방법이 다르기 때문에 Java를 실행 할 수 있는 가상 운영체제를 중간에 둬서 자바 프로그램이 어떤 환경이든 똑같이 동작할 수 있도록 설계되어 있다.
* 자바 바이트 코드는 모든 JVM에서 동일한 실행 결과를 보장하지만, JVM은 운영체제에 종속적이다.

![JVM](http://files.itworld.co.kr/archive/image/2018/09/jw_jvm_overview_3x2_1200x800-100758586-large(1).jpg)
<br/>출처 [IT WORLD](!http://www.itworld.co.kr/news/110837)

* 이미지처럼 애플리케이션과 JVM 사이에서 동작되며, JVM이 각 운영체제별로 종속적이다.
* 개발자가 작성하는 자바 파일은 .JAVA 이고, 이를 javac를 통해 컴파일 하면 .class 확장자인 바이트 코드 파일이 된다.
* 생성된 .class 파일인 바이트 코드가 JVM에 의해 해석되고 실행되어 진다.

# 주석
| //        | /* <br/> */     | /** <br/> */      |
|-----------|-----------|---------------|
| 한줄 주석 | 범위 주석 | 도큐먼트 주석 |