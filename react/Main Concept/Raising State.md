## State 끌어올리기

```jsx
const scaleNames = {
    c: 'Celsius',
    f: 'Fahrenheit'
};

function BoilingVerdict(props) {
    if (props.calsius >= 100) {
        return <p>물이 끓습니다.</p>
    } else {
        return <p>물이 끓지않고 있습니다.</p>
    }
}

class TemperatureInput extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(e) {
        // this.setState({temperature: e.target.value});
        this.props.onTemperatureChange(e.target.value);
        // 부모로부터 공유되는 값을 변경할 수 있는 함수를 전달받아 사용한다.
    }

    render() {
        // const temperature = this.state.temperature;
        // 기존에는 temperature 의 값이 TemperatureInput 에서 관리되기 때문에,
        // 다른 컴포넌트에서 변경된 값에 의해 동기화 될 수 없었다.
        const temperature = this.props.temperature;
        // 부모로부터 전달받는 형태로 변경하면, 해당 값은 읽기 전용이 된다.
        // 그렇기 때문에 temperature 의 값을 제어할 수 없게된다.
        // 이러한 문제를 React 에서 제어가 가능한 방식으로 해결한다.
        // 즉, 공유되는 값을 제어할 수 있는 함수를 부모로부터 전달받아 사용하는 형태로
        // 해당 값을 사용하는 모든 컴포넌트가 동기화 되어 관리될 수 있다.
        const scale = this.props.scale;
        return (
            <fieldset>
                <legend>Enter temperature in {scaleNames[scale]}:</legend>
                <input value={temperature}
                       onChange={this.handleChange} />
            </fieldset>
        );
    }
}

class Calculator extends React.Component {
    constructor(props) {
        super(props);
        this.handleCelsiusChange = this.handleCelsiusChange.bind(this);
        this.handleFahrenheitChange = this.handleFahrenheitChange.bind(this);
        this.state = {temperature: '', scale: 'c'};
    }

    handleCelsiusChange(temperature) {
        this.setState({scale: 'c', temperature});
    }

    handleFahrenheitChange(temperature) {
        this.setState({scale: 'f', temperature});
    }

    render() {
        const scale = this.state.scale;
        const temperature = this.state.temperature;
        const celsius = scale === 'f' ? tryConvert(temperature, toCelsius) : temperature;
        const fahrenheit = scale === 'c' ? tryConvert(temperature, toFahrenheit) : temperature;

        return (
            <div>
                <TemperatureInput
                    scale="c"
                    temperature={celsius}
                    onTemperatureChange={this.handleCelsiusChange} />
                <TemperatureInput
                    scale="f"
                    temperature={fahrenheit}
                    onTemperatureChange={this.handleFahrenheitChange} />
                <BoilingVerdict
                    celsius={parseFloat(celsius)} />
            </div>
        );
    }
}

function toCelsius(fahrenheit) {
    return (fahrenheit - 32) * 5 / 9;
}
function toFahrenheit(celsius) {
    return (celsius * 9 / 5) + 32;
}
function tryConvert(temperature, convert) {
    const input = parseFloat(temperature);
    if (Number.isNaN(input)) {
        return '';
    }
    const output = convert(input);
    const rounded = Math.round(output * 1000) / 1000;
    return rounded.toString();
}
// 온도 변환 기능
```

### 위 코드에 의해 발생하는 일

1. React DOM `<input>` 의 onChange 에 지정된 함수를 호출한다.
   * TemperatureInput 의 handleChange 메서드
2. TemperatureInput 컴포넌트의 handleChange 메소드는 새로 입력된 값과 함께 this.props.onTemperatureChange() 를 호출한다.
   * onTemperatureChange 는 부모로부터 전달받은 함수이다.
   * 컴포넌트에 전달한 함수를 섭씨, 화씨 종류에 따라 다르게 전달하였다.
3. 특정 컴포넌트에 input 의 데이터를 변경하면 전달된 함수가 실행되면서, 해당되는 setState 가 작동하여 공유되는 부모의 temperature, scale 의 데이터가 변경된다.
   * setState 가 작동하면서 React 에게 다시 렌더링하도록 요청한다.
4. React 는 UI 가 어떻게 보여야 하는지 알아내기 위해 Calculator 컴포넌트의 render 메서드를 호출한다.
5. React 는 Calculator 가 전달하내 props 와 함께 각 TemperatureInput 컴포넌트의 render 메서드를 호출한다.
6. React 는 BoilingVerdict 컴포넌트에게 온도를 props 로 건네면서 그컴포넌트의 render 메서드로출한다.
7. React DOM 은 물의 끓는 여부와 올바른 입력값을 일치시키는 작업과 함께 DOM 을 갱신한다.
    * 값을 변경한 입력 필드는 현재 입력값을 그대로 받고, 다른 입력 필드는 변환된 온도 값으로 갱신된다.
    
### 결론

* React 안에서 변경이 일어나는 데이터에 대해서는 하나의 **진리의 원천** 을 하나만 두어야 한다.
* 보통 state 는 렌더링에 그 값을 필요로 하는 컴포넌트에 먼저 추가된다.
    * 하지만, 다른 컴포넌트도 해당 데이터가 필요하다면 가장 가까운 공통 조상으로 **끌어올리면** 된다.
    * 즉, 다른 컴포넌트 간에 존재하는 state 를 동기화하는 노력보다는 **하향식 데이터 흐름** 을 추천한다.
* state 끌어올리기는 양방향 바인딩 접근 방식보다 더 많은 보일러 플레이트 코드를 유발하지만, 버그를 찾고 격리하는데 더 쉽게 만드는 장점이 있다.
    * state 는 특정 컴포넌트 안에 존재하고, 그 컴포넌트가 자신의 state 를 스스로 변경할 수 있으므로 버그가 존재할 수 있는 범위가 크게 줄어든다.
    * 사용자의 입력을 거부하거나 변형하는 자체 로직도 구현이 가능하다.
* 어떠한 값이 props/state 로 부터 계산될 수 있다면, 그 값을 state 에 두어서는 안된다.
