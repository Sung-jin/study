# 엘리먼트 렌더링

* 화면에 표시할 내용을 기술하는 것을 엘리먼트라고 한다.

```jsx
const element = <h1>Hello, world</h1>;
```

* React 엘리먼트는 일반 객체이며, 브라우저 DOM 엘리먼트보다 쉽게 생성할 수 있다.
* React DOM 은 React 엘리먼트와 일치하도록 DOM 을 업데이트 한다.

## DOM 에 엘리먼트 렌더링하기

```html
<div id="root"></div>
```

* 위 엘리먼트에 들어가는 모든 엘리먼트를 React DOM 에서 관리하기 때문에 이것을 root DOM 노드라고 한다.
* React 로 구현된 애플리케이션은 일반적으로 하나의 root DOM 노드가 존재한다.
    * 하지만 React 를 기존 앱에 통합하려는 경우 원하는 수 만큼 독립된 root DOM 노드가 있을 수 있다.
* React 엘리먼트를 루트 DOM 노드에 렌더링하려면 둘 다 ReactDOM.render() 로 전달하면 된다.

```jsx
const element = <h1>Hello, world</h1>;
ReacDOM.render(element, document.getElementByID('root'));
```

## 렌더링 된 엘리먼트 업데이트하기

* React 엘리먼트는 불변객체이다.
    * 엘리먼트를 생성한 이후에는 해당 엘리먼트의 자식이나 속성을 변경할 수 없다.
    * 엘리먼트는 특정 시점의 UI 를 보여준다.
* 즉, 지금까지의 내용으로는 UI 를 업데이트하기 위한 유일한 방법은 새로운 엘리먼트를 생성하고 이를 ReactDOM.render() 로 전달하는 것이다.

```jsx
function tick() {
    const element = (
        <div>
            <h1>Hell, world!</h1>
            <h2>IOt is {new Date().toLocaleDateString()}.</h2>
        </div>
    );
    ReactDOM.render(element, document.getElementById('root'));
}

setInterval(tick, 1000);
// 1 초마다 ui 가 위에 정의된 내용으로 변경된다.
```

## 변경된 부분만 업데이트하기

* React DOM 은 해당 엘리먼트와 그 자식 엘리먼트를 이전의 엘리먼트와 비교하고 DOM 을 원하는 상태로 만드는데, 필요한 경우에만 DOM 을 업데이트 한다.
* 즉, 위의 예제에서 변경되는 부분은 `<h2>IOt is {new Date().toLocaleDateString()}.</h2>` 에 해당되는 노드만 변경이 이뤄진다.
