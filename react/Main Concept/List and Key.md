## 리스트와 Key

### 여러개의 컴포넌트 렌더링 하기

```jsx
const numbers = [1, 2, 3, 4, 5];
const listItems = numbers.map((number) => {
    <li>{number}</li>
});

ReactDOM.render(
    <ul>{listItems}</ul>,
    document.getElementById('root')
);

// 위와 같이 사용하면 5개의 숫자를 가진 리스틀 보여준다.
```

### 기본 리스트 컴포넌트

```jsx
function NumberList(props) {
    const numbers = props.numbers;
    const listItems = numbers.map((number) =>
        <li>{number}</li>
        
        /*
        <li key={number.toString()}>{number}</li>
        위와 같이 각 엘리먼트끼리 다른 key 를 가지도록 설정하면 경고는 해제된다.
        */
    );

    return (
        <ul>{listItems}</ul>
    );
}

const numbers = [1, 2, 3, 4, 5];
ReactDOM.render(
    <NumberList numbers={numbers} />,
    document.getElementById('root')
);

// 위와 같이 사용하면 key 를 넣어야 한다는 경고가 출력된다.
// key 는 엘리먼트 리스트를 만들 때 포함해야 하는 특수한 문자열 어트리뷰트이다.
```

### Key

* Key 는 React 가 어떤 항목을 변경, 추가, 삭제할지 식별하는 것을 도와준다.
* key 는 엘리먼트에 안정적인 고유성을 부여하기 위해 배열 내부의 엘리먼트에 지정해야 한다.
* Key 를 선택하는 가장 좋은 방법은 리스트의 다른 항목들 사이에서 고유하게 식별할 수 있는 문자열을 사용하는 것이다.
    * 대부분 데이터의 id 값을 이용한다.
    * id 값같은 데이터가 없다면 index 로도 key 를 구분할 수 있지만, 항목의 순서가 변할경우 권장하지 않는다.
        * 성능이 저하되거나 컴포넌트의 state 와 관련된 문제가 발생할 수 있다.
        * [Robin Porkny 가 작성한 인덱스를 key 로 사용할 경우 부정적인 영향에 대한 상세 설명](https://robinpokorny.medium.com/index-as-a-key-is-an-anti-pattern-e0349aece318)
    * 리스트에 key 를 지정하지 않으면 react 는 기본적으로 index 로 key 를 지정한다.

```jsx
const todoItems = todos.map((todo) =>
    <li key={todo.id}>
        {todo.text}
    </li>
);
const todoItems = todos.map((todo, index) =>
    <li key={todo.id}>
    {/*<li key={index}>*/}
    {/*id 와 같은 구분자가 없다면 index 로 key 데이터로 사용할 수 있다.*/}
        {todo.text}
    </li>
)
```

### Key 로 컴포넌트 추출하기

* 키는 주변 배열의 context 에만 의미가 있다.

```jsx
function ListItem(props) {
    return (
        <li>
        {/* li key={value.toString()}>*/}
        {/*여기에 key 를 지정할 필요가 없다.*/}
            {props.value}
        </li>
    )
}

function NumberList(props) {
    const numbers = props.numbers;
    const listItems = numbers.map((number) =>
        // <ListItem value={number} />
        <ListItem key={number.toString()} value={number} />
        // 여기에 key 를 지정해줘야 한다.
    )
    
    return (
        <ul>
            {listItems}
        </ul>
    )
}
```

### Key 는 형제 사이에서만 고유한 값이어야 한다.

* Key 는 배열 안의 형제 사이에서 고유하고, 전체 범위에서 고유할 필요는 없다.
    * 즉, 두개의 배열을 만들때 동일한 key 를 사용할 수 있다.

```jsx
function SomeFuncComponent(props) {
    const someChild1 = (
        <ul>
            {props.someFoo.map((foo) =>
                <li key={val.id}>
                    {val}
                </li>
            )}
        </ul>
    );

    const someChild2 = (
        <ul>
            {props.someBar.map((bar) =>
                <li key={val.id}>
                    {val}
                </li>
            )}
        </ul>
    );
    
    return (
        <div>
            {someChild1}
            {someChild2}
        </div>
    );
}
```

* 참고로 key 값은 prop 으로 전달하지 않는다.
    * key 값으로 넘긴 값이 필요하다면 다른 prop 으로 명시적으로 전달해주면 된다.
    
### JSX 에 map() 포함시키기

```jsx
function NumberList(props) {
    const numbers = props.numbers;
      // const listItems = numbers.map((number) =>
      //   <ListItem key={number.toString()}
      //             value={number} />
      // );
    return (
        // <ul>
        //     {listItems}
        // </ul>
        <ul>
            {numbers.map((number) =>
                <ListItem key={number.toString()}
                          value={number} />
            )}
        </ul>
        // 위와 같이 jsx 중괄호 안에 모든 표현식을 포함시킬 수 있다.
        // 이를 이용하여 map() 함수의 결과를 인라인으로 처리할 수 있다.
    );
}
```
