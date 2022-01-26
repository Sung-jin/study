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

```typescript jsx
var foo = <foo>bar;
```

* 위 코드는 변수 `bar` 가 `foo` 타입이라는 것을 단언한다
* ts 는 꺾쇠 괄호를 사용해 타입을 단언하기 때문에, JSX 특정 구문이 문법 해석에 문제가 될 수 있다
    * 결과적으로 `.tsx` 파일에서 화살 괄호를 통한 타입 단언을 허용하지 않는다
* 위와 같이 화살 괄호를 통한 타입 단언이 허용하지 않으므로, `as` 라는 타입 단언을 활용해야 한다
    * `.ts` 와 `.tsx` 모두 사용할 수 있다

```typescript jsx
var foo = bar as foo;
```

### 타입 검사

* JSX 표현식 `<expr />` 에서 `expr` 은 환경에 내장된 요소 (`div`/`span`...) 혹은 사용자가 만든 컴포넌트를 참조한다
  * 리액트에서 내장 요소는 `React.createElement("div")` 와 같은 문자열로 생성되지만, 사용자 컴포넌트는 `React.createElement("MyComponent")` 의 형태가 아니다
  * JSX 요소에 전달되는 속성의 타입은 다르게 조회되어야 한다

#### 내장 요소

* 내장 요소는 항상 소문자로 시작하고 값-기반 요소는 항상 대문자로 시작한다
* 내장요소는 특수 인터페이스 `JSX.IntrinsicElements` 에서 조회된다
  * 해당 인터페이스가 지정되지 않으면 그대로 진행되어 내장 요소 타입은 검사되지 않는다
  * 해당 인터페이스가 있을 경우, 내장 요소의 이름은 `JSX.IntrinsicEklements` 인터페이스의 프로퍼티로 조회된다

```typescript jsx
declare namespace JSX {
    interface IntrinsicElements {
        foo: any
        // [elementNAme: string]: any;
        // 위와 같이 지정하면 catch-all 문자열 인덱서를 지정한다
    }
}

<foo />; // 성공
<bar />; // IntrinsicElements 에 지정되지 않았기 때문에 오류가 발생


```

#### 값 기반 요소

* 값-기반 요소는 해당 스코프에 있는 식별자로 간단하게 조회된다
* 값-기반 요소 정의하는 방법
  1. 함수형 컴포넌트 (FC)
  1. 클래스형 컴포넌트
* 각 정의는 JSX 표현식에서 서로 구별할 수 없으므로, ts 는 과부하 해결을 사용하여 먼저 함수형 컴포넌트 표현식으로 해석하고, 실패하면 클래스형 컴포넌트로 해석하고, 다시 실패하면 오류를 보고한다

```typescript jsx
import MyComponent from "./myComponent";

<MyComponent />; // 성공
<OtherComponent />; // 실패
```

##### 함수형 컴포넌트 (Function Component)

* 컴포넌트 첫번째 인수가 `props` 객체인 js 함수로 정의된다
* ts 는 컴포넌트의 반환 타입이 `JSX.Element` 에 할당 가능하도록 요구한다
* 함수형 컴포넌트는 이전에 무상태 함수형 컴포넌트(SFC) 로 알려져 있엇으나, 최근 버전의 리액트에서는 더이상 함수형 컴포넌트를 무상태로 취급하지 않으며, `SFC` 타입과 그 별칭인 `StatelessComponent` 는 더이상 상용되지 않는다

```typescript jsx
interface ClickableProps {
    children: JSX.Element[] | JSX.Element
}

interface HomeProps extends ClickableProps {
    home: JSX.Element;
}

interface SideProps extends ClickableProps {
    side: JSX.Element | string;
}

function MainButton(prop: HomeProps): JSX.Element;
function MainButton(prop: SideProps): JSX.Element {
    ...
}
// 함수형 컴포넌트는 js 함수이므로 함수 오버로드 또한 사용이 가능하다

function ComponentFoo(prop: HomeProps) {
    return <MainButton home={prop.home} />;
}
const Button = (prop: {value: string}, context: {color: string}) => <button>
```

##### 클래스형 컴포넌트 

* `<Expr />` 에서 요소 클래스 타입은 `Expr` 의 타입이며, 위의 `MyComponent` 가 ES6 클래스이면 해당 클래스 타입은 클래스 생성자이고 전역이다
  * `MyComponent` 가 팩토리 함수라면, 클래스 타입은 해당 함수이다
* 클래스 타입이 결정되면 인스턴스 타입은 클래스 타입의 생성자 혹은 호출 시그니처에 의한 반환 타입을 결합하여 결정된다
* ES6 클래스의 경우 인스턴스 타입은 해당 클래스의 인스턴스 타입이 되고, 팩토리 함수의 경우 해당 함수로부터 반환된 값의 타입이 된다
* 요소 인스턴스 타입은 `JSX.ElementClass` 에 할당 가능해야 하며, 그렇지 않을 경우 오류가 발생한다
  * 기본적으로 `JSX.ElementClass` 는 `{}` 이지만, 적절한 인터페이스에 적합한 타입으로만 JSX 를 사용하도록 제한할 수 있다

```typescript jsx
class MyComponent {
    render() {}
}

// 생성자 시그니처 사용
var myComponent = new MyComponent();
// 요소 클래스 타입 => MyComponent
// 요소 인스턴스 타입 => { render: () => void }

function MyFactoryFunction() {
    return {
        render: () => {
            
        }
    }
}

// 호출 시그니처 사용
var myComponent = MyFactoryFunction();
// 요소 클래스 타입 => MyFactoryFunction
// 요소 인스턴스 타입 => { render: () => void }
```
