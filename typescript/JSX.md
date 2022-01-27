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

### 속성 타입 검사

* 속성 타입 검사를 위해 요소 속성 타입을 경정해야 한다
  * 이는 내장 요소와 값-기반 요소 간에 약간 다른 점이 있다
  * 내장 요소의 경우, 요소 속성 타입은 `JSX.IntrinsicElements` 의 프로퍼티 타입과 동일하다
* 값-기반 요소
  * 요소 인스턴스 타입의 프로퍼티 타입에 따라 결정된다
  * 사용할 프로퍼티는 `JSX.ElementAttributesProperty` 에 따라 결정된다
  * 이는 단일 프로퍼티로 선언되어야 한다
  * 이후 해당 프로퍼티 이름을 사용한다
  * ts 2.8 부터 `JSX.ElementAttributesProperty` 가 제공되지 않으면, 클래스 요소의 생성자 또는 함수형 컴포넌트의 첫 번째 매개변수 타입을 대신 사용할 수 있다
* 요소 속성 타입은 JSX 에서 속성 타입을 확인하는데 사용되며, 선택적 혹은 필수적인 프로퍼티들이 지원된다

```typescript jsx
declare namespace JSX {
    interface IntrinsicElements {
        foo: { bar?: boolean }
    }
}

<foo bar />
// foo 의 요소 속성 타입은 '{bar?: boolean}'

declare namespace JSX {
    interface ElementAttributesProperty {
        props; // 사용할 프로퍼티 이름을 지정
  }
}

class MyComponent {
    // 요소 인스턴스 타입의 프로퍼티를 지정
    props: {
        foo?: string;
    }
}

<MyComponent foo="bar" />
// MyComponent 의 요소 속성 타입은 '{foo?: string}'

declare namespace JSX {
    interface IntrinsicElements {
        foo: { requiredProp: string; optionalProp?: number }
    }
}

<foo requiredProp="bar" />; // 성공
<foo requiredProp="bar" optionalProp={0} />; // 성공
<foo />; // requiredProp 누락 오류
<foo requiredProp={0} />; // requiredProp 타입 오류
<foo requiredProp="bar" unkownProp />; // 없는 prop 오류
<foo requiredProp="bar" unkown-prop />; // unkown-prop 은 유효한 식별자가 아니여서 성공

var props = {requiredProp: "bar"};
var errorProps = {}
<foo {...props} />; // 이와 같이 스프레드 연산자로도 가능
<foo {...errorProps} />; // 기존의 타입 체크와 동일하게 체크하며, 해당 형태는 오류가 발생
```

* 속성 이름이 유효한 js 식별자가 아닌 경우, 해당 이름을 요소 속성 타입에서 찾을 수 없어도 오류로 간주하지 않는다
* 또한 `JSX.IntrinsicAttributes` 인터페이스는 일반적으로 컴포넌트의 props 나 인수로 사용되지 않는 JSX 프레임워크를 위한 추가적인 프로퍼티를 지정할 수 있다
  * React 의 `Key` 등
* `JSX.IntrinsicAttributes<T>` 제네릭 타입을 사용하여 함수형 컴포넌트를 제외한 클래스형 컴포넌트에 대해 동일한 종류의 추가 속성을 지정할 수 있다
  * 해당 유형은 제네릭의 매개변수는 클래스 인스턴스 타입에 해당한다

### 자식 타입 검사

* ts 2.3 부터 자식의 타입 검사를 도입했다
* 자식은 자식 JSX 표현식을 속성에 삽입하는 요소 속성 타입의 특수한 프로퍼티이다
* ts 는 `JSX.ElementAttributesProperty` 를 사용해 props 를 결정하는 것과 유사하게 `JSX.ElementChildrenAttribute` 를 사용해 해당 props 내의 자식의 이름을 결정한다
  * `JSX.ElementChildrenAttribute` 는 단일 프로퍼티로 선언되어야 한다
  
```typescript jsx
interface PropsType {
    children: JSX.Element
    name: string
}

class Component extends React.Component<PropsType, {}> {
    render() {
        return (
            <h2>
              {this.props.children}
            </h2>
        )
    }
}

// 성공
<Component name="foo">
    <h1>Hello World</h1>
</Component>

// 오류 : 자식은 JSX.Element의 배열이 아닌 JSX.Element 타입이어야 한다
<Component name="bar">
    <h1>Hello World</h1>
    <h2>Hello World</h2>
</Component>

// 오류 : 자식은 JSX.Element의 배열이나 문자열이 아닌 JSX.Element 타입이이어야 한다
<Component name="baz">
    <h1>Hello</h1>
    World
</Component>
```

### JSX 결과 타입

* 기본적으로 JSX 표현식의 결과물은 `any` 타입이지만, `JSX.Element` 인터페이스를 수정하여 특정한 타입을 지정할 수 있다
* 그러나 이 인터페이스에서 JSX 의 요소, 속성, 자식에 대한 정보를 검색할 수 없다
  * 해당 인터페이스는 블랙박스이다

### 표현식 포함하기

* JSX 는 `{}` 로 표현식을 감싸 태그 사이에 표현식 사용을 허용한다

```typescript jsx
var a = <div>
    {["foo", "bar"].map(value => <span>value</span>)}
</div>
```

### 리액트와 통합하기

* 리액트에서 JSX 를 사용하기 위해선 React 타이핑을 사용해야 한다
  * 이는 리액트를 사용할 수 있도록 JSX 네임스페이스를 적절하게 정의한다

```typescript jsx
interface Props {
    foo: string;
}

class MyComponent extends React.Component<Props, {}> {
    render() {
        return <span>{this.props.foo}</span>
    }
}

<MyComponent foo="bar" />; // 성공
<MyComponent foo={0} />; // 오류
```

### 팩토리 함수

* `jsx: react` 컴파일러 옵션에서 사용하는 팩토리 함수는 설정이 가능하다
* `jsxFactory` 명령 줄 옵션을 사용하거나 인라인 `@jsx` 주석을 사용하여 파일별로 설정할 수 있다
* `jsxFactory` 에 `createElement` 를 설정한다면, `div/>` 는 `React.createElement('div')` 대신 `createElement('div')` 으로 생성된다
* 선택된 팩토리는 전역 네임스페이스로 돌아가기 전에 `JSX` 네임스페이스(타입 검사를 위한 정보)에도 영향을 미친다

```typescript jsx
import preact = require('preact');
/* @jsx preact.h */
const x = <div />;

// 이는 다음과 같이 생성된다
const preact = require('preact');
const x = preact.h('div', null);
```
