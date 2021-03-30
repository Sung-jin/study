## 이벤트 처리하기

* React 엘리먼트에서 이벤트를 처리하는 방식은 DOM 엘리먼트에서 이벤트를 처리하는 방식과 매우 유사하다.
* 다른 문법 차이
    1. React 의 이벤트는 소문자 대신 camelCase 를 사용한다.
    2. JSX 를 사용하여 문자열이 아닌 함수로 이벤트 핸들러를 전달한다.
    3. React 에서는 false 를 반환해도 기본 동작을 방지할 수 없다.
        * preventDefault 를 명시적으로 호출해야 기본 동작을 방지할 수 있다.

```jsx
<button href="#" onclick="console.log('some log'); return false;">
    btn
</button>
// HTML

function someFunctionComponent() {
    function someFunction(e) {
        // e 는 합성 이벤트이다.
        // React 는 W3C 명세에 따라 합성 이벤트를 정의하기 때문에 브라우저 호환성에 대한 걱정은 없다.
        // React 이벤트는 브라우저 고유 이벤트와 정확히 동일하게 동작하지 않는다.
        e.preventDefault();
        console.log('some log');
    }
    
    return (
        <a href="#" onClick={someFunction(e)}>
            btn
        </a>
    )
}
// React

class Toggle extends React.Component {
    construcotr(props) {
        super(props);
        this.state = {isToggle: true};
        
        this.handleClick = this.handleClick.bind(this);
        // 콜백에서 'this' 가 작동하려면 바인딩을 해줘야 한다.
        // javascript 에서 클래스 메서드는 기본적으로 바인딩 되어 있지 않기때문에,
        // this.handleClick 을 바인딩하지 않고 onClick 에 전달하였다면, 함수가 실제로 호출될 때
        // this 는 undefinded 가 된다.
        // 일반적으로 this.handleClick 과 같이 () 를 사용하지 않고 메서드를 참조할 경우,
        // 해당 메서드를 바인딩해줘야 한다.
    }
    
    handleClick() {
        this.setState(state => ({
            isToggle: !state.isToggle
        }));
    }
    
    handleClick = () => {
        this.setState(state => ({
            isToggle: !state.isToggle
        }));
    }
    // ...bind(this) 를 하지 않는 방법중 퍼블릭 클래스 필드 문법이 존재한다.
    // 하지만, 해당 기능은 실험적인 문법이다.
    
    render() {
        return (
            <button onClick={this.handleClick}>
                {/*<button onClick{() => this.handleClick()}> 처럼 화살표 함수를 사용한다면,*/}
                {/*bind(this) 를 하지 않아도 된다.*/}
                {/*하지만 위 문법의 문제점은 해당 컴포넌트가 렌더링될 때마다 다른 콜백이 생성된다.*/}
                {/*대부분 문제가 되지 않지만, 콜백이 하위 컴포넌트에 props 로 전달된다면 그 컴포넌트들은 추가로 다시 렌더링을 수행할 수 있다.*/}
                {/*이러한 종류의 성능 문제를 피하고자, 생성자 안에서 바인딩하거나 클래스 필드 문법을 사용하는 것을 권장한다.*/}
                {this.state.isToggle ? 'ON' : 'OFF'}
            </button>
        );
    }
}

ReactDOM.render(
    <Toggle />,
    document.getElementById('root')
);
// ES6 클래스를 사용하여 컴포넌트를 정의할 때, 일반적인 패턴은 이벤트 핸들러를 클래스의 메서드로 만드는 것이다.
```

### 이벤트 핸들러에 인자 전달하기

* 루프 내부에서 이벤트 핸들러에 추가적인 매개변수를 전달하는 것은 일반적이다.

```jsx
<button onClick={(e) => this.deleteRow(id, e)}>Delete Row</button>
<button onClick={this.deleteRow.bind(this, id)}>Delete Row</button>
```

* 위 두줄은 동등하지만, 화살표 함수와 Function.prototype.bind 를 사용하는 차이점이 있다.
* 두 경우 모두 React 이벤트를 나타내는 e 인자가 id 라는 객체 뒤에 존재하게 된다.
    * 화살표 함수를 사용하면 명시적으로 인자를 전달해야 하지만, bind 를 사용할 경우 추가 인자가 자동으로 전달된다.
