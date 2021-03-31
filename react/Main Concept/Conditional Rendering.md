## 조건부 렌더링

* React 에서 원하는 동작을 캡슐화하여 컴포넌트를 만들 수 있고, 애플리케이션의 상태에 따라 특정 컴포넌트만 렌더링할 수 있다.
* React 의 조건부 렌더링은 if, 조건부 연산자와 같은 javascript 연산자를 통해 현재 상태에 맞는 UI 가 업데이트가 된다.

```jsx
function SomeComponent1(props) {
    return <div>one</div>;
}

function SomeComponent2(props) {
    return <div>two</div>;
}

function someConditionalComponent(props) {
    const condition = props.condition;
    if (condition) {
        return <SomeComponent1 />;
    } else {
        return <SomeComponent2 />;
    }
}

ReactDOM.render(
    <someConditionalComponent condition={true} />,
    // <someConditionalComponent condition={false} />,
    document.getElementById('root')
);
```

### 엘리먼트 변수

* 한 컴포넌트의 일부를 조건부로 렌더링 할 수 있다.

```jsx
function LogoutButton(props) {
    return (
        <button onClick={props.onClick}>
            Login
        </button>
    );
}

function LoginButton(props) {
    return (
        <button onClick={props.onClick}>
            Logout
        </button>
    );
}

class LoginControl extends React.Component {
    constructor(props) {
        super(props);
        this.handleLoginClick = this.handleLoginClick.bind(this);
        this.handleLogoutClick = this.handleLogoutClick.bind(this);
        this.state = {isLogin: false};
    }

    handleLoginClick() {
        this.setState({isLogin: true});
    }

    handleLogoutClick() {
        this.setState({isLogin: false});
    }

    render() {
        const isLogin = this.state.isLogin;
        let button;
        if (isLogin) {
            button = <LogoutButton onClick={this.handleLogoutClick} />;
        } else {
            button = <LoginButton onClick={this.handleLoginClick} />;
        }

        return (
            <div>
                {button}
            </div>
        );

        /*return (
            <div>
                {isLogin}
                {isLogin ?  <LogoutButton onClick={this.handleLogoutClick} /> : <LoginButton onClick={this.handleLoginClick} />}
                이런 형태로 조건부 렌더링이 가능하다.
                이는 condition ? true : false 를 이용한 렌더링이다.
            </div>
        );*/

    }
}

ReactDOM.render(
    <LoginControl />,
    document.getElementById('root')
);
```

### 논리 && 연산자로 if 를 인라인으로 표현하기

```jsx
function Mailbox(props) {
    const unreadMessages = props.unreadMessages;
    return (
        <div>
            <div>Hello!</div>
            {unreadMessages.length > 0 &&
                <h2>
                    You have {unreadMessages.length} unread messages.
                </h2>
            }
        {/* javascript 에서 true && expression 은 항상 expression 으로 평가되고, false && expression 은 expression 이 무시된다. */}
        </div>
    );
}

const messages = ['some message 1', 'some message 2', 'some message 3'];
ReactDOM.render(
    <Mailbox unreadMessages={messages} />,
    document.getElementById('root')
);

...

// 아래와 같은 사용은 주의해야 한다.

return() {
    const count = 0;
    return (
        <div>
            { count && <h1>Messages: {count}</h1>}
        {/* 0 은 false 이므로, 뒤의 expression 이 무시되고 0 만 렌더링 된다. */}
        </div>
    );
}
``` 

### 컴포넌트가 렌더링하는 것을 막기

* 다른 컴포넌트에 의해 렌더링될 때 컴포넌트 자체를 숨길 수 있다.
    * 이때는 렌더링 결과를 출력하는 대신 null 을 반환하면 된다.

```jsx
function WarningBanner(props) {
    if (!porps.warn) {
        return null;
    }
    // 위와 같이 null 을 리턴하면 해당 컴포넌트는 렌더링되지 않는다.
    // 참고로 컴포넌트의 render 메소드로부터 null 을 반환하는 것은 생명주기 메서드 호출에 영향을 주지 않는다.
    // 그 예로 componentDidUpdate 는 계속 호출된다.

    return (
        <div className="warning">
            Warning!
        </div>
    );
}

class Page extends React.Component {
    constructor(props) {
        super(props);
        this.state = {isWarning: ture};
        this.handleToggleClick = this.handleToggleClick.bind(this);
    }
    
    handleToggleClick() {
        this.setState(state => ({
            isWarning: !state.isWarning
        }));
    }
    
    return() {
        return (
            <div>
                <WarningBanner warn={this.state.isWarning} />
                <button onClick={this.handleToggleClick}>
                    {this.state.isWarning ? 'Hide' : 'Show'}
                </button>
            </div>
        );
    }
}

ReactDOM.render(
    <Page />,
    document.getElementById('root')
);
```
