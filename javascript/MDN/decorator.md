## 소개

* 데코레이터는 클래스 선언과 멤버에 어노테이션과 메타-프로그래밍 구문을 추가할 수 있는 방법을 제공한다
* 데코레이터는 js 에 대한 2단계 제안이며 ts 의 실험적 기능으로 이용 가능하다
  * 실험적 지원을 활성화
    * `tsconfig.json` 에서 `experimentDecorators` 컴파일 옵션을 활성화
    * `tsc --target ES5 --experimentalDecorators` 으로 명령줄 실행
  
### 데코레이터

* 클래스 선언, 메서드, 접근자, 프로퍼티 또는 매개변수에 첨부할 수 있는 특수한 종류의 선언
* `@expression` 형식을 사용하며, expression 은 데코레이팅 된 선언에 대한 정보와 함께 런타임에 호출되는 함수이어야 한다

```typescript
function foo(target) {}

@foo
// 위와 같이 @foo 를 사용하기 위해서는 foo 라는 함수를 작성할 수 있다
```