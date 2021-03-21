# Hello World

```jsx
ReactDom.render(
    <h1>Hello, world!</h1>,
    documentr.getElementById('root')
);

// 가장 단순한 React 예시
```

## JSX

```jsx
const element = <h1>Hello, world!</h1>;
```

* 위 문법은 문자열도, HTML 도 아니다.
* JSX 이며, JavaScript 를 확장한 문법이다.
* JSX 는 React **엘리먼트**를 생성한다.
* React 에서 이벤트가 처리되는 방식, 시간에 따라 state 가 변하는 방식, 화면에 표시하기 위해 데이터가 준비되는 방식 등 렌더링 로직이 본질적으로 다른 UI 로직과 연결된다는 사실을 받아들인다.
* React 는 별도의 파일에 마크업과 로직을 넣어 기술을 인위적으로 분리하지 않고, 다 포함한 **컴포넌트** 라고 부르는 느슨하게 연결된 유닛으로 관리한다.
* React 에서 JSX 사용은 필수가 아니지만, JS 코드 안에서 UI 관련 작업이 보기가 더 편하고, React 가 도움이 되는 에러 및 경고 메시지를 표시해준다.

### JSX 에 표현식 포함하기

```jsx
function formatName(user) {
    return `${user.firstName} user.lastName`;
}
const user = {
    firstName: 'Sung-jin',
    lastName: 'Oh'
}
const element = (
    <h1>
        Hellop, {formatName(user)}
    </h1>
);
// jsx 의 중괄호 안에는 JS 표현식을 넣을 수 있다.
// ex) {2 + 2}    {someThing.someValue}    {someFunc(someObj)}   ...
    
ReactDom.render(
    element,
    document.getElementById('root')
);
```

* 위와 같이 jsx 로 구현하였어도, 컴파일이 끝나면 jsx 표현식이 정규 js 함수 호출이 되고 js 객체로 인식된다.
* 죽, jsx 를 if 구문 및 for loop 안에 사용하고, 변수에 할당하고, 인자로 받아들이고, 함수로부터 반환하는 모든 것을 할 수 있다.

```jsx
function getGreeting(user) {
    if (user) {
        return (
            <h1>hello, {formatName(user)}!</h1>
        );
    }
    return <h1>Hello, Stranger.</h1>;
}
```

### JSX 속성 정의

```jsx
const element1 = <div tabIndex="0"></div>;
const element2 = <img src={someObj.someUrl}></img>;
```

* 위와 같이 속성에 따옴표로 문자열 리터럴을 정의할 수 있다.
* 또한, 중괄호를 사용해서 어트리뷰트에 js 표현식을 삽입할 수 있다.
* 참고로 js 표현식을 삽입할 때, 중괄호 주변에 따옴표를 입력하면 안된다.
    * 따옴표 또는 중괄호 표현식 둘 중 하나만 사용해야 한다.
* JSX 는 HTML 보다 js 에 가깝기 때문에, React DOM 은 HTML 어트리뷰트 이름 대신 camelCase 명명 규칙을 사용한다.
    * ex) class => className, tabindex => tabIndex

### JSX 로 자식 정의

```jsx
const element = <img src={someObj.someUrl}/>;
// 태그가 비어있는 경우 /> 로 닫아줘어야 한다.
const element = (
    <div>
        <h1>Hello!</h1>
        <h2>Hi!</h2>
    </div>
);
// JSX 태그는 자식을 포함할 수 있다.
```

### JSX 는 주입 공격을 방지한다.

```jsx
const title = response.potentiallyMaliciousInput;
// 이것은 안전하다.
const element = <h1>{title}</h1>;
```

* 기본적으로 React DOM 은 JSX 에 삽입된 모든 값을 렌더링하기 전에 이스케이프를 하므로, 애플리케이션에서 명시적으로 작성되지 않은 내용은 주입되지 않는다.
* 모든 항목은 렌더링 되기 전에 문자열로 변환된다.
* 이러한 특성으로 XSS 공격을 방지할 수 있다.

### JSX 는 객체를 표현한다.

```jsx
const element = (
    <h1 className="greeting">
        Hello, world!
    </h1>
);

const element = React.createElement(
    'h1',
    {className: 'greeting'},
    'Hello, world!'
);

// 위의 2가지 표현 방식은 동일하다.
// 추가적으로 createElement 는 버그가 없는 코드를 작성하는 데 도움이 되도록 몇가지 검사를 수행하고, 아래와 같은 객체를 생성한다.

const element = {
    type: 'h1',
    props: {
        className: 'greeting',
        children: 'Hello, world!'
    }
};
// 해당 객체는 단순화 한 구조이다.
// 또한, 이러한 객체를 React 엘리먼트라고 하며, 화면에서 보고 싶은 것을 나타내는 포현이다.
// React 는 이러한 객체를 읽어서 DOM 을 구성하고 최신 상태로 유지하는 데 사용한다.
```


