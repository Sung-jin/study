## 컴포넌트에서 다른 컴포넌트 담기

* 특정 컴포넌트의 자식 엘리먼트가 들어올지 예상할 수 없고, 이러한 컴포넌트는 특수한 children prop 을 사용하여 자식 엘리먼트를 그대로 전달하는 것이 좋다.

```jsx
function FancyBorder(props) {
    return (
        <div className={`SomeClassName-${props.classValue}`}>
            {props.children}
        </div>
    );
}

function WellcomeDialog() {
    return (
        <FancyBorder classValue="someValue">
            <p>자식 컴포넌트에</p>
            <p>여기에 해당하는 내용이</p>
            <p>전달되어 children 에 표현됩니다.</p>
        </FancyBorder>
    );
}

function SplitPane(props) {
    return (
        <div>
            <div className="left">
                {props.left}
            </div>
            <div className="right">
                {props.right}
            </div>
            {/*slot 과 비슷하게 동작한다.*/}
            {/*React 에서 prop 으로 전달할 수 있는 것에는 제한이 없다.*/}
        </div>
    );
}

function App() {
    return (
        <SplitPane
            left={
                <SomeComponent1/>
            }
            right={
                <SomeComponent2/>
            }
            {/*해당 컴포넌트들이 전달되어 left, right 에 대체되어 표시된다.*/}
        />
    );
}
```

### 특수화

```jsx
function Dialog(props) {
    return (
        <FancyBorder color="blue">
            <h1 className="Dialog-title">
                {props.title}
            </h1>
            <p className="Dialog-message">
                {props.message}
            </p>
            {props.children}
        </FancyBorder>
    );
}

class SignUpDialog extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleSignUp = this.handleSignUp.bind(this);
        this.state = {login: ''};
    }

    render() {
        return (
            <Dialog title="Mars Exploration Program"
                    message="How should we refer to you?">
                <input value={this.state.login}
                       onChange={this.handleChange} />
                <button onClick={this.handleSignUp}>
                    Sign Me Up!
                </button>
            </Dialog>
        );
    }

    handleChange(e) {
        this.setState({login: e.target.value});
    }

    handleSignUp() {
        alert(`Welcome aboard, ${this.state.login}!`);
    }
}

// SignUpDialog 는 Dialog 에 특수한 경우이다.
// 즉, Dialog 에 대해 어떤 특수한 (해당 예제에서는 회원 가입과 관련된 Dialog) 를 고려하는 형태이다.
// React 에서는 이 역시 합성을 통해 해결할 수 있다.
// 더 구체적인 컴포넌트가 일반적인 컴포넌트를 렌더링하고 Props 를 통해 내용을 구성한다.
```

### 상속은?

* props 와 합성은 명시적이고 안전한 바업븡로 컴포넌트의 모양과 동작을 커스터마이징하는데 필요한 모든 유연성을 제공한다.
    * 컴포넌트가 원시 타입의 값, React 엘리먼트 혹은 함수 등 어떠한 props 도 받을 수 있다.
* UI 가 아닌 기능을 여러 컴포넌트에서 재사용하기를 원한다면, 별도의 js 모듈로 분리하는 것이 좋다.
    * import 를 이용해서 다른 컴포넌트에서 함수/ 객체/ 클래스 등을 사용할 수 있다.
