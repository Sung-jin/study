### DefaultMessageCodesResolver 의 기본 메시지 생성 규칙

```
-- 객체 오류

1. code + "." + object name
2. code

ex) 오류 코드: reuqired, object name: item
1. required.item
2. required
```

```
--- 필드 오류

1. code + "." + object name + "." + field
2. code + "." + field
3. code + "." + field type
4. code

ex) 오류 코드: typeMismatch, object name "user", field "age", field type: int
1. "typeMismatch.user.age"
2. "typeMismatch.age"
3. "typeMismatch.int"
4. "typeMismatch"
```

### Bean Validation

* 특정 구현체가 아닌 Bean Validation 2.0(JSR-380) 이라는 기술 표준
* 검증 어노테이션과 여러 인터페이스의 모음이다
* Bean Validation 을 구현한 기술중에 일반적으로 사용하는 구현체는 하이버네이트 Validator 이다
    * 이는 ORM 과 아무 상관이 없다
  
#### 검증 순서

1. `@ModelAttribute` 각각의 필드에 타입 변환 시도
    * 성공하면 다음 작업을, 실패하면 `typeMissmatch` 로 `FieldError` 추가
2. Validator 적용

##### 바인딩에 성공한 필드만 Bean Validation 적용

* BeanValidation 은 바딩인에 실패한 필드에서 적용되지 않는다
* `@ModelAttribute` -> 각각의 필드 타입 변환 시도 -> 변환에 성공한 필드만 Bean Validation 적용

#### BeanValidation 메시지 찾는 순서

1. 생성된 메시지 코드 순서대로 `messageSource` 에서 찾기
2. 어노테이션의 `message` 속성 사용 -> `@NotBlank(message = "공백 {0}")`
3. 라이브러리가 제공하는 기본 값 사용 -> 공백일 수 없습니다.

### Form 전송 객체 분리

* `Groups` 는 등록시 폼과 해당 도메인 객체가 딱 맞지 않기 때문에 잘 사용하지 않는다
    * 회원가입 시 약관 정보 등과 같이 유저에 대한 도메인 객체 뿐 아니라 부가적인 데이터가 넘어오는 경우가 많다
* 이를 위해 대부분 해당 도메인 객체 자체보다 해당 컨트롤러에서 필요한 정보만 가지고 있는 형태의 객체를 전달받고, 해당 도메인의 객체로 변환하여 사용한다

#### 폼 데이터 전달에 Item 도메인 객체 사용

* `HTML Form -> Item -> Controller -> Repository`
    * 장점: Item 도메인 객체를 컨트롤러, 레파지토리 까지 직접 전달하여 중간에 Item 을 만드는 과정이 없어서 간단하다
    * 단점: 간단한 경우에만 적용할 수 있으며, 수정시 검증이 중복될 수 있고, 상황에 따라 검증이 달라야 한다면 groups 를 사용해야 한다

#### 폼 데이터 전달을 위한 별도의 객체 사용

* `HTML Form -> ItemSaveForm -> Controller -> Item 컨버팅 -> Repository`
    * 장점: 전송하는 폼 데이터가 복잡해도 해당 객체에 맞춘 별도의 폼 객체를 사용하여 데이터를 전달 받을 수 있으며, 객체가 다르므로 검증이 중복되지 않는다
    * 단점: 폼 데이터를 기반으로 컨트롤러에서 Item 객체를 생성하는 변환 과정이 추가된다

### @ModelAttribute vs @RequestBody

* HTTP 요청 파라미터를 처리하는 `@ModelAttribute` 는 각각의 필드 단위로 세밀하게 적용된다
    * 특정 필드 타입이 맞지 않는 오류가 발생해도 나머지 필드는 정상 처리할 수 있다
* `HttpMessageConverter` 는 `@ModelAttribute` 와 다르게 각각의 필드 단위로 적용되는 것이 아니라, 전체 객체 단위로 적용된다
    * 메시지 컨버터의 작동이 성공해서 해당 객체가 만들어져야 `@Valid/@Validated` 가 적용된다
* `@ModelAttribute`: 필드단위로 정교하게 바인딩이 적용되며, 특정 필드가 바인딩 되지 않아도 나머지 필드는 정상 바인딩이 되고, Validator 를 사용한 검증도 적용할 수 있다
* `@RequestBody`: `HttpMessageConverter` 단계에서 JSON 객체로 변경하지 못하면 이후 단계 자체가 진행되지 않고 예외가 발생하므로, 컨트롤러도 호출되지 않고 Validator 도 적용할 수 없다
