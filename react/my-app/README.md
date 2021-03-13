### React 란?

* 사용자 인터페이스를 구축하기 위한 선언적이고 효율적이며 유연한 javascript 라이브러리
* 컴포넌트라는 고립된 코드의 파편을 이용하여 복잡한 UI 를 구성한다.
* React 에는 여러 종류의 컴포넌트를 가진다. 

```javascript
class someComponent extends React.Component {
    render() {
        relturn (
            <div className="some-class">
                {this.props.someValue}
            </div>
        )
    }
}
```

* 위와 같은 형태의 구현을 컴포넌트라고 한다.
* someComponent 는 react 컴포넌트 클래스 또는 react 컴포넌트 타입이다.
* 개별 컴포넌트는 props 라는 매개변수를 받아오고 render 함수를 통해 표시할 뷰 계층 구조를 반환한다.
    * render 는 랜더링할 내용을 경량화한 react 엘리먼트를 반환한다.
    * React 구조가 아닌 JSX 라는 툭수한 문법으로 더 쉽게 작성할 수 있다.
    * `<div />` 구문은 빌드하는 시점에 React.createElement('div') 로 변환된다.
    * [createElement()](https://ko.reactjs.org/docs/react-api.html#createelement)
    * React 컴포넌트는 캡슐화되어 독립적으로 동작할 수 있고, 이러한 컴포넌트를 조합하여 또 다른 컴포넌트를 만들수 있다.
    
```javascript
return React.createElement('div', {className: 'some-class'}, this.props.someValue);
```

#### JSX

* JSX 내부의 중괄호 안에 어떤 javascript 표현식도 사용할 수 있다.
* React 엘리먼트는 JavaScript 객체이며 변수에 저장하거나 프로그램 여기저기에 전달할 수 있다.
 
#### 불변성

* 원본 객체를 직접 수정하지 않고, 복사본을 만들어서 수정하고 적용할 때 불변성이 적용된다.

```javascript
const originalArray = [1,2,3,4,5];

...

const copyArray = originalArray.splice();
copyArray[1] = 20;
originalArray = copyArray;
```

* 원본을 직접 수정하지 않고 카피본을 만들어서 불변성을 이용하는 이유
    1. 복잡한 특징들을 단순하게 만듦
        * undo 기능을 구현하기에는 직접 원본을 수정하는 것 보다는 수정본을 이용해서 수정하는 것이 더 편하다.
    2. 변화를 감지함
        * 객체를 직접 수정하는 경우에는 복제가 가능한 객체에서 변화를 감지하는 것은 어렵다.
    3. react 에서 다시 렌더링하는 시기를 결정함
        * react 에서 다시 렌더링하는 시기를 결정하는데, 이는 react 에서 순수 컴포넌트를 만드는데 도움을 준다.
        * 변하지 않는 데이터는 변경이 이루어졌는지 쉽게 판단할 수 있고, 이를 바탕으로 컴포넌트가 다시 렌더링할지를 결정할 수 있다.
* [성능 최적화하기](https://ko.reactjs.org/docs/optimizing-performance.html#examples)
