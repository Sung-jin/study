## Introduction

* JSX 는 내장형 XML 과 같은 구문이다
    * 변환의 의미는 구현에 따라 다르지만 유효한 js 로 변환되어야 한다
* ts 는 임베딩, 타입 검사, JSX 를 js 로 직접 컴파일하는 것을 지원한다

### 기본 사용방법

* JSX 를 사용하기 위한 처리
    1. 파일 이름을 `.tsx` 확장자로 지정
    1. `jsx` 옵션 활성화
* ts 는 `preserve`/`react`/`react-native` 라는 세 가지의 JSX 모드와 함께 제공된다
    * 해당 모드들은 방출 단계에서만 영향을 미치며, 타입 검사에는 영향을 받지 않는다
    * `preserve` 모드는 Babel 과 같은 다른 변환 단계에 사용하도록 결과물의 일부를 유지하며, 결과물은 `.jsx` 확장자를 가진다
    * `react` 모드는 `React.createElement` 를 생성하여, 사용하기 전에 JSX 변환이 필요하지 않으며, 결과물은 `.js` 확장자를 가지게 된다
    * `react-native` 모드는 JSX 를 유지한다는 점은 `preserve` 모드와 동일하지만, 결과물은 `.js` 확장자를 가지게 된다

| 모드 | 입력 | 결과 | 결과 파일 확장자 |
| ---- | ---- | ---- | ---- |
| `reserve` | `<div/>` | `<div/>` | `.jsx` |
| `react` | `<div/>` | `React.createElement("div")` | `.js` |
| `react-native` | `<div/>` | `<div/>` | `.js` |

### as 연산자

```typescript
var foo = <foo>bar;
```

* 위 코드는 변수 `bar` 가 `foo` 타입이라는 것을 단언한다
* ts 는 꺾쇠 괄호를 사용해 타입을 단언하기 때문에, JSX 특정 구문이 문법 해석에 문제가 될 수 있다
    * 결과적으로 `.tsx` 파일에서 화살 괄호를 통한 타입 단언을 허용하지 않는다
* 위와 같이 화살 괄호를 통한 타입 단언이 허용하지 않으므로, `as` 라는 타입 단언을 활용해야 한다
    * `.ts` 와 `.tsx` 모두 사용할 수 있다

```typescript
var foo = bar as foo;
```