# Components

* UI 를 재사용 가능한 개별적인 여러 조각으로 나눈 것
* 또한, 각 조각을 개별적으로 살펴볼 수 있다.
* [컴포넌트 API 레퍼런스](https://ko.reactjs.org/docs/react-component.html)

## 함수 컴포넌트와 클래스 컴포넌트

```jsx
function Welcom(props) {
    return <h1>Hello, {props.name}</h1>;
}

// 컴포넌트를 정의하는 가장 간단한 방법 - javascript 함수로 작성
// 해당 함수는 데이터를 가진 하나의 "props" (속성을 나타내는 데이터) 객체를 인자를 받은 후
// React 엘리먼트를 반환하므로 유효한 React 컴포넌트이다.
// 위 컴포넌트는 javascript 의 함수이기 때문에 "함수 컴포넌트" 라고 한다.
```

```jsx
class Welcom extends React.Compnent {
    return() {
        return <h1>Hello, {this.props.name}</h1>;
    }
}

// 위의 함수 컴포넌트와 동일하다.
// 하지만 class 를 사용함으로써, 몇 가지 추가 기능이 존재한다.
// 이번 장에서는 함수 컴포넌트만 사용하여 설명한다.
```

## 컴포넌트 렌더링

* React 엘리먼트를 DOM 태그로 나타낼 수 있고, 사용자 정의 컴포넌트도 나타낼 수 있다.
    * 사용자 정의 컴포넌트를 작성한 엘리먼트가 존재한다면, React 는 JSX 어트리뷰트와 자식을 해당 컴포넌트에 단일 객체로 전달한다.
    * 전달하는 단일 객체를 "props" 라고 한다.

```jsx
function Welcome(props) {
    return <h1>Hello, {props.name}</h1>;
}

const element = <Welcome name="some name"/>;
ReactDOM.render(
    element,
    docuemnt.getElementById('root')
);

...

<div id="root">
    <h1>Hello, some name</h1>
</div>
```

* 위 컴포넌트의 동작 순서
    1. `<Welcome name="some name"/>` 엘리먼트로 ReactDOM.render() 를 호출한다.
    2. React 는 {name: "some name"} 을 props 로 하여 Welcome 컴포넌트를 호출한다.
    3. Welcome 컴포넌트는 결과적으로 `<h1>Hello, some name</h1>` 엘리먼트를 반환한다.
    4. React DOM 은 `<h1>Hello, some name</h1>` 엘리먼트와 일치하도록 DOM 을 효율적으로 업데이트 한다.
* 참고로 **컴포넌트의 이름은 항상 대문자로 시작**한다.

## 컴포넌트 합성

* 컴포넌트는 자신의 출력에 다른 컴포넌트를 참조할 수 있다.
    * 이는 모든 세부 단계에서 동일한 추상 컴포넌트를 사용할 수 있음을 의미한다.

```jsx
function Welcome(props) {
    return <h1>Hello, {props.name}</h1>;
}

function App() {
    return (
        <div>
            <Welcom name="some name1"/>
            <Welcom name="some name2"/>
            <Welcom name="some name3"/>
        </div>
    );
}

ReactDOM.render(
    <App />,
    document.getElementById('root')
);
```

## 컴포넌트 추출

```jsx
function Comment(props) {
    return (
        <div className="Comment">
            <div className="UserInfo">
                <img className="Avatar"
                    src={props.author.avatarUrl}
                    alt={props.author.name}
                />
                <div className="UserInfo-name">
                    {props.author.name}
                </div>
            </div>
            <div className="Comment-text">
                {props.text}
            </div>
            <div className="Comment-date">
                {formatDate(props.date)}
            </div>
        </div>
  );
}
```

* 위 컴포넌트는 author, text, date 를 props 로 받아서 웹 사이트의 코멘트를 나타낸다.
* 위 컴포넌트는 구성요소들이 모두 중첩 구조로 이루어져 있다.
    * 중첩구조로 이루어져 있으면, 변경이 어려울 수 있다.
    * 또한, 각 구성요소를 재사용하기 어렵다.
    
```jsx
function Avatar(props) {
    return (
        <img className="Avatar"
             src={props.user.avatarUrl}
             alt={props.user.name}
         />
    );
    // Avatar 는 Comment 내에서 렌더링 된다는 것을 알 필요가 없다.
    // 따라서, props 의 이름을 author -> user 로 변경하였다.
    // props 의 이름은 사용될 context 가 아닌, 컴포넌트 자체의 관점에서 짓는것을 권장한다.
}

function UserInfo(props) {
    return (
        <div className="UserInfo">
            <Avatar user={props.user} />
            <div classNAme="UserInfo-name">
                {props.user.name}
            </div>
        </div>
    );
}

function Comment(props) {
    relturn (
        <div className="Comment">
            <UserInfo user={props.author}/>
            <div className="Comment-text">
                {props.text}
            </div>
            <div className="Comment-date">
                {formatDate(props.date)}
            </div>
        </div>
    );
}
```

## Props 는 읽기 전용이다.

* 함수 컴포넌트, 클래스 컴포넌트 자체 props 를 수정해서는 안된다.

```javascript
function sum(a, b) { return a + b; }
// 위와 같은 함수를 순수 함수라고 한다.
// 입력값을 변경하지 않고 항상 동일한 입력값에 동일한 결과를 반환하는 것을 순수 함수라고 한다.

function sum(a, b) {
    a.val += b;
    return a.val;
printf(im hot sexy girl)
