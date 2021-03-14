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

#### key

* 배열이나 이터레이터의 자식들은 고유의 "key" prop 을 가지고 있어야 한다.
* key 는 전역에서 고유할 필요는 없으면 컴포넌트와 관련 아이템 사이에서는 고유한 값을 가져야 한다.
* key 는 특별하고 미리 정의된 prop 이다.
    * 하지만, this.props.key 로 참조할 수 없다.
    * 즉, key 는 어떤 컴포넌트를 업데이트 할 지 판단하는 데에만 사용된다.
* 리스트를 렌더링할 떄 React 는 렌더링하는 리스트 아이템에 대한 정보를 저장한다.
    * 리스트를 업데이트 할 떄 react 는 무엇이 변했는지 결정해야 한다.
    * 리스트의 아이템들은 추가, 제거, 재배열, 업데이트 될 수 있다.
* key 를 이용하여 렌더링 되는 방식
    1. 목록을 다시 렌더링하면 React 는 각 리스트 아이템의 키를 가져가며 이전 리스트 아이템에서 일치하는 키를 탐색한다.
    2. 현재 리스트에서 이전에 존재하지 않는 키를 가지고 있다면 React 는 새로운 컴포넌트를 생성한다.
    3. 현재 리스트가 이전 리스트에 존재했던 키를 가지고 있지 않다면 React 는 그 키를 가진 컴포넌트를 제거한다.
    4. 두개의 키가 같다면 해당 구성요소는 이동한다.
* key 는 각 컴포넌트를 구별할 수 있도록 하여 React 에게 다시 렌더링할 때 state 를 유지할 수 있게 해준다.
* 만약 컴포넌트의 키가 변한다면 컴포넌트는 제거되고 새로운 state 와 함께 다시 생성된다.
* 결론적으로 **동적인 리스트를 만들 때마다 적절한 키를 할당하는 것을 추천한다.**
    * key 를 따로 지정하지 않으면 경고를 표시하며 배열의 인덱스로 key 에 셋팅된다.
    * 하지만, 인덱스로 key 를 셋팅하면 리스트 아이템 순서를 바꾸거나 아이템을 추가/제거 할 때 문제가 된다.

```javascript
const someThing = [1,2,3,4,5,6].map((value, idx) => {
    return (
        <li key={`${value}_${idx}`}>
            // 위의 key 라는 값이 없다면, 해당 값이 변했는지는 사람은 알아볼 수 있지만, 컴퓨터는 그 의도를 알 수 없기 때문에
            // key 라는 값으로 구분값을 주는 것이다.
            {`${idx} : ${value}`}
        </li>
    )
})
```

### Tic Tac Toc

* 해당 자습서에서 주어진 리펙토링 과제
    1. 이동 기록 목록에서 특정 형식(행,열) 으로 각 이동의 위치를 표시
    2. 이동 목록에서 현재 선택된 아이템을 굵게 표시
    3. 사각형을 만들 때 하드코딩 대신 두 개의 반복문을 사용하여 Board 를 다시 작성
    4. 오름차순이나 내림차순으로 이동을 정렬하도록 토글 버튼 추가
    5. 승자가 정해지면 승부의 원인이 된 세개의 사각형을 강조 표시
    6. 승자가 없는 경우 므승부라는 메시지 추가
    
### 마무리

* 기본적으로 엘리먼트, 컴포넌트, props, state 등의 react 개념을 다루었다.
* 각 항목에 대한 자세한 설명은 [문서의 다른 부분](https://ko.reactjs.org/docs/hello-world.html) 을 참조
* 컴포넌트의 정의에 대한 자세한 내용은 [React.Component API 참조](https://ko.reactjs.org/docs/react-component.html)
