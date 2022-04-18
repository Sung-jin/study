## Escape

* HTML 문서는 `<`/`>` 와 같은 특수 문자를 기반으로 정의되므로, 뷰 템플릿으로 HTML 에 사용할 때 이러한 특수문자를 조심해서 사용해야 한다
    * `<b>Hello</b>` 를 텍스트로 치환하면 bold 처리가 되지 않고 `&lt;b&gt;Hello&lt;/b&gt;` 로 표시가 된다
    * `&lt;`/`&gt;` 등과 같은 것을 HTML 엔티티라고 한다
        * HTML 엔티티는 HTML 에서 태그로 표시되는 특수 기호 등과 같은 것들을 텍스트로 표현할 수 있는 방법이다
        * 특수문자를 HTML 엔티티로 변경하는 것을 `Escape` 이라고 한다
* 타임리프가 제공하는 `th:text`/`[[...]]` 와 같은 문법은 기본적으로 `Escape` 를 제공한다
* `Escape` 처리를 하지 않고 그대로 태그로 사용 하는 것을 `UnEscape` 라고 한다

### thymeleaf 리터럴

* 리터럴은 소스 코드상에 고정된 값을 의미한다
* 타임리프의 리터럴은 다음과 같은 값이 있다
    1. 문자: `'hello'`
    1. 숫자: `10`
    1. 불린: `true`/`false`
    1. null: `null`
* 타임리프에서 문자 리터럴은 항상 `'` 로 감싸야 한다
    * 공백없이 쭉 이어진다면 하나의 의미있는 토큰으로 인지해서 따음표를 생략할 수 있다
    * `A-Z`/`a-z`/`0-9`/`[]`/`.`/`-`/`_`
    * `<span th:text="hello">` 는 허용하나, `<span th:text="hello world">` 는 허용하지 않는다
  
### thymeleaf 반복 상태 유지 기능

* `<tr th:each"user, userStat : ${users}">` 와 같은 형태로 작성하면, `user` 에는 해당 loop 의 값이 매핑되고 `userStat` 에는 현해 loop 의 상태의 값이 매핑된다
    * 두번째 파라미터는 생략이 가능하며, 생략시 loop 의 `변수명 + Stat` 을 붙여서 사용하면 된다
* `index`: 0 부터 시작하는 값
* `count`: 1 부터 시작하는 값
* `size`: 전체 사이즈
* `event`/`odd`: 홀수/짝수 여부 (`boolean`)
* `first`/`last`: 처음/마지막 여부 (`boolean`)
* `current`: 현재 객체

### if/unless/switch

* `if`/`unless`: 조건에 맞지 않으면 태그 자체를 렌더링 하지 않는다
* `switch`: `case` 에 해당되는 부분이 출려고딘다

### 블록

* `<th:block>` 은 HTML 태그가 아닌 타임리프의 유일한 자체 태그