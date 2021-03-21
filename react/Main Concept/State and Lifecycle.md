## State 와 생명주기

* [컴포넌트 API 레퍼런스](https://ko.reactjs.org/docs/react-component.html)

```typescript jsx
function Clock(props) {
    return (
        <div>
            <h1>Hello, world!</h1>
            <h2>It is {props.date.toLocaleTimeString()}.</h2>
        </div>
    );
}

function tick() {
    ReactDOM.render(
        <Clock date={{new Date()}} />,
        document.getElementById('root')
    );
}

setInterval(tick, 1000);
```

* 위와 같은 clock 컴포넌트는 clock 컴포넌트 자체에서 타이머를 설정하고 UI 업데이트를 하는 것은 clock 의 구현 세부사항이 되어야 한다.

```typescript jsx
ReactDOM.render(
    <Clock />,
    document.getElementById('root')
)
```

* 위와 같이 한 번만 코드를 작성하고 clock 이 스스로 업데이트하도록 구현하고자 한다.
* 위와 같이 구현하려면, clock 컴포넌트에 "state" 를 추가해야 한다.
    * state 는 props 와 유사하지만, 비공개이며 해당 컴포넌트에 의해 완전히 제어된다.
    
### 함수 컴포넌트 -> 클래스 컴포넌트

* 다음과 같은 단계로 함수 컴포넌트 -> 클래스 컴포넌트 로 변경할 수 있다.
    1. React.Component 를 확장하는 동일한 이름의 ES6 class 를 생성한다.
    2. render() 라고 불리는 빈 메서드를 추가한다.
    3. 함수의 내용을 render() 메서드 안으로 옮긴다.
    4. render() 내용 안에 있는 props 를 this.props 로 변경한다.
    5. 남아있는 빈 함수 선언을 삭제한다.

```typescript jsx
class Clock extends React.Component {
    render() {
        return (
            <div>
                <h1>Hello, world!</h1>
                <h2>It is {this.props.date.toLocaleTimeString()}.</h2>
            </div>
        );
    }
    // render() 메서드는 업데이트가 발생할 때마다 호출된다.
    // 같은 DOM 노드로 <Clock /> 을 렌더링하는 경우 Clock 클래스의 단일 인스턴스만 사용한다.
    // 단일 인스턴스만 사용된다는 것은 로컬 state 와 생명주기 메서드와 같은 부가적인 기능을 사용할 수 있게 해준다.
}

// props -> state 로 변경
class Clock extends React.Component {
    constructor(props) {
        super(props);
        // super(props) 를 통해 props 를 전달한다.
        // 클래스 컴포넌트는 항상 props 로 기본 constructor 를 호출해줘야 한다.
        this.state = {date: new Date()};
    }
    // 초기 this.state 를 지정하는 class constructor 추가

    componentDidMount() {
        // 해당 컴포넌트가 마운트 될 때 실행 (컴포넌트 출력물이 DOM 에 렌더링 된 후 실행된다.)
        this.timerID = setInterval(
            () => this.tick(),
            1000
        );
        // 데이터 흐름 안에 포함되지 않는 어떤 항목을 보관할 필요가 있다면, this.props / this.state 가 아닌
        // 클래스에 수동으로 부가적인 필드를 추가해도 된다.
    }

    componentWillUnmount() {
        // 해당 컴포넌트가 언마운트 될 때 실행
        clearInterval(this.timerID);
    }
    // componentDidMount, componentWillUnmount 와 같은 것들을 "생명주기 메서드" 라고 한다.

    tick() {
        this.setState({
            date: new Date()
        });
    }

    render() {
        return (
            <div>
                <h1>Hello, world!</h1>
                <h2>It is {this.state.date.toLocaleTimeString()}.</h2>
                {/*기존에 props 를 state 로 변경한다.*/}
            </div>
        );
    }
    // render() 메서드는 업데이트가 발생할 때마다 호출된다.
    // 같은 DOM 노드로 <Clock /> 을 렌더링하는 경우 Clock 클래스의 단일 인스턴스만 사용한다.
    // 단일 인스턴스만 사용된다는 것은 로컬 state 와 생명주기 메서드와 같은 부가적인 기능을 사용할 수 있게 해준다.
}

ReactDOM.render(
    <Clock />,
    // 기존의 props 로 넘겨주던 date 를 제거한다.
    document.getElementById('root')
);
```

* 위와 같은 clock 클래스 컴포넌트의 동작 순서
    1. `<Clock />` 이 ReactDOM.render() 로 전달되었을 때 React 는 Clock 컴포넌트의 constructor 를 호출한다.
       Clock 이 현재 시각을 표시하기 위한 현재 시각이 포함된 객체인 this.state 를 초기화 한다.
    2. React 는 Clock 컴포넌트의 render() 메서드를 호출한다.
       render() 를 통해 React 는 화면에 표시할 내용을 알게되고, React 는 Clock 의 렌더링 출력값을 일치시키기 위해 DOM 을 업데이트 한다.
    3. Clock 출력값이 DOM 에 삽입되면, React 는 componentDidMount() 생명주기 메서드를 호출한다.
    4. 브라우저는 componentDidMount() 에 의해 생성된 setInterval 을 통해 매초 tick() 메서드를 호출한다.
       tick() 메서드 안에는 setState() 가 존재하고, setState() 때문에 React 는 state 가 변경됨을 알고 화면에 표시된 내용을 업데이트하기 위해 render() 메서드를 다시 호출한다.
       this.state.date 가 달라진 것을 확인하고 업데이트 된 데이터를 React 가 DOM 업데이트를 한다.
    5. Clock 컴포넌트가 DOM 으로부터 한번이라도 삭제된 적이 있다면, React 는 타이머를 멈추기 위해 componentWillUnmount() 생명주기 메서드를 호출한다.
    
### State 를 올바르게 사용하기

* 직접 State 를 수정하면 안된다.

```typescript jsx
this.state.someValue = 'some change';
// 위와 같이 직접 변경하면 컴포넌트를 다시 렌더링하지 않는다.

this.setState({someValue: 'some change'});
// 위와 같이 setState 를 이용해야 한다.
// this.state 를 지정할 수 있는 유일한 공간은 constructor 뿐이다.
``` 

* State 업데이트는 비동기적일 수 있다.
    * React 는 성능을 위해 여러 setState() 호출을 단일 업데이트로 한꺼번에 처리할 수 있다.
    * this.props 와 this.state 가 비동기적으로 업데이트 될 수 있기 떄문에 다음 state 를 계산할 때 해당 값에 의존해서는 안된다.

```typescript jsx
this.setState({
    counter: this.state.counter + this.props.increment,
})
// 객체보다는 함수를 사용하는 다른 형태의 setState() 를 사용한다.

this.setState((state, props) => {
    // 이전 state 를 첫 번째 인자로 받고, 업데이트가 적용된 시점의 props 를 두번째 인자를 받는 setState
    counter: state.counter + props.increment
});
```

* State 업데이트는 병합된다.
    * setState() 를 호출할 때 React 는 제공한 객체를 현재 state 로 병합한다.

```typescript jsx
constructor(props) {
    super(props);
    this.state = {
        posts: [],
        comments: []
    }
}

componentDidMount() {
    fetchPosts().then(response => {
        this.setState({
            posts: response.posts
        });
    });

    fetchComents().then(response => {
        this.setState({
            comments: response.comments
        })
    })
}

// 병합은 얕게 이루어지기 때문에 this.setState({comments}) 는 this.state.posts 에 영향을 주지 않지만,
// this.state.comments 는 완전히 대체도니다.
```

### 데이터는 아래로 흐른다.

* 부모 컴포넌트나 자식 컴포넌트 모두 특정 컴포넌트가 유상태인지, 무상태인지 알 수 없고 그들이 함수나 클래스로 정의되었는지에 대해서 관심을 가질 필요가 없다.
    * 그래서 state 는 종종 로컬 또는 캡슐화라고 불린다.
    * state 가 소유하고 설정한 컴포넌트 이외에는 어떠한 컴포넌트에도 접근할 수 없다.
* 컴포넌트는 자신의 state 를 자식 컴포넌트에 props 로 전달할 수 있다.

```typescript jsx
<SomeCompoent someValue={this.state.someValue} />
```

* SomeComponent 는 someValue 를 자신의 props 로 받고, 이 값은 SomeComponent 입장에서 전달한 부모의 props 의 값인지 state 의 값인지 알 수 없다.
    * 이러한 데이터 흐름이기에 **top-down(하양식)** 또는 **단방향식** 데이터 흐름이라고 한다.
    * 모든 state 는 항상 특정한 컴포넌트가 소유하고 있으며, 그 state 로부터 파생된 UI 또는 데이터는 오직 트리구조에서 자신의 **아래**에 있는 컴포넌트에만 영향을 미친다.
* 이러한 구조이기 때문에 같은 컴포넌트를 여러개 사용하더라도, 각 컴포넌트는 독립적으로 동작한다.
    * 각자 독립적으로 동작하기 때문에, 독립적으로 업데이트를 한다.
