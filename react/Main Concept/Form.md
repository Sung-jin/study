## 폼

* HTML 폼 엘리먼트는 폼 엘리먼트 자체가 내부 상태를 가진다.
* 폼은 React 의 다른 DOM 엘리먼트와 다르게 동작한다.

```html
<form>
    <label>
        Name:
        <input type="text" name="name"/>
    </label>
    <input type="submit" value="submit"/>
</form>
```

* 위와 같은 폼을 React 에서 그대로 사용할 수 있다.
* 하지만 javascript 함수로 폼의 제출을 처리하고 사용자가 폼에 입력한 데이터에 접근하도록 하는 것이 편리하다.
    * 이를 위한 표준 방식은 **제어 컴포넌트** 라고 불리는 기술을 이용한다.
    
### 제어 컴포넌트

* HTML 의 input, textarea, select 등과 같은 폼 엘리먼트들은 사용자의 입력을 기반으로 자신의 state 를 관리하고 업데이트 한다.
* React 에서는 변경할 수 있는 state 가 일반적으로 컴포넌트의 state 속성에 유지되며 setState() 에 의해 업데이트된다.
* React state 를 신뢰 가능한 단일 출처로 만들어 두 요소를 결합할 수 있다.
    * 결합을 하면 해당 렌더링 되는 React 컴포넌트는 폼에 발생하는 사용자 입력값을 제어한다.
    * 이러한 방식으로 React 에 의해 제어되는 입력 폼 엘리먼트를 **제어 컴포넌트** 라고 한다.

```jsx
class NameForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: '',
            textarea: '초기 값을 지정해 줄 수 있으며, 초기값이 화면에 바로 표시된다.',
            select: 'one',
        };
        
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    handleChange(event) {
        this.setState({value: event.target.value});
    }
    
    ...
    
    handleSubmit(event) {
        alert(`submit name : ${this.state.value}`);
        ...
        event.preventDefault();
    }
    
    return() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    name :
                    <input type="text" value={this.state.value} onChange={this.handleChange} />
                    {/*onChange 에 의해 폼에 입력되는 데이터가 state.value 에 기록된다.*/}
                    {/*value 어트리뷰트는 폼 엘리먼트에 설정되므로 표시되는 값은 항상 staet.value 가 되고, React state 는 신뢰 가능한 단일 출처가 된다.*/}
                    <textarea value={this.state.textarea} onChange={this.handleChangeTextarea} />
                    <select value={this.state.select} onChange={this.handleChangeSelect}>
                        <option value="one">One</option>
                        <option value="two">Two</option>
                        ...
                    </select>
                    {/*input, textarea, select 등과 같은 폼 엘리먼트를 value 에 state 값과 매핑하여 사용할 수 있다.*/}
                    <input type="file"/>
                    {/*file api 를 이용하여 javascript 로 파일을 처리할 수 있다.*/}
                    {/*값이 읽기 전용이고, 그래서 React 에서 비제어 컴포넌트이다.*/}
                </label>
                <imput tyope="submit" value="Submit" />
            </form>
        );
    }
}
```

### 다중 입력 제어하기

* 여러 input 엘리먼트를 제어해야 할 때, 각 엘리먼트에 name 어트리뷰트를 추가하고 event.target.name 값을 통해 핸들러가 어떤 작업을 하는지 선택할 수 있게 한다.

```jsx
class Reservation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isGoing: true,
            numberOfGuests: 2
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        
        this.setState({
            [name]: value
            // ES6 의 computed property name 구문
        });
        // setState 는 자동적으로 현재 state 에 일부 state 를 병합하기 때문에
        // 바뀐 부분에 대해서만 호출할 수 있다.
    }

    render() {
        return (
            <form>
                <label>
                    Is going:
                    <input name="isGoing" 
                           type="checkbox" 
                           checked={this.state.isGoing} 
                           onChange={this.handleInputChange} />
                    </label>
                    <br />
                    <label>
                    Number of guests:
                    <input name="numberOfGuests" 
                           type="number" 
                           value={this.state.numberOfGuests} 
                           onChange={this.handleInputChange} />
                </label>
            </form>
        );
    }
}
```

### 제어되는 Input Null 값

* 제어 컴포넌트에 value prop 을 지정하면 의도하지 않으면 사용자가 변경할 수 없다.
* value 를 설정하였어도, 수정이 가능하다면 undefined 나 null 로 설정이 가능하다.

```jsx
ReactDOM.render(<input value="hi" />, mountNode);

setTimeout(function() {
  ReactDOM.render(<input value={null} />, mountNode);
}, 1000);
```

### 제어 컴포넌트 대안

* 입력 폼을 구현하기 위한 대체 기술인 [비제어 컴포넌트](https://ko.reactjs.org/docs/uncontrolled-components.html) 가 존재한다.

### 완전한 해결책

* 유효성 검사, 방문한 필드 추적 및 폼 제출 처리와 같은 완벽한 해결을 원한다면 [Formik](https://formik.org/) 이 대중적인 선택이다.
