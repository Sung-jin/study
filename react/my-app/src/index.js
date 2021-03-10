import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

class Square extends React.Component {
    constructor(props) {
        super(props);
        // javascript 클래스에서 하위 클래스의 생성자를 정의할 떄 항상 super 를 호출해야 한다.
        // 모든 react 컴포넌트 클래스는 생성자를 가질 때 super(props) 호출 구문부터 작성해야 한다.
        this.state = {
            value: null,
        };
    }
    // 해당 컴포넌트의 어떠한 값을 기억할 때 state 를 사용한다.
    render() {
        return (
            // <button className="square" onClick={() => alert('click')}>
            <button
                className="square"
                onClick={() => this.setState({value: 'X'})}
            >
                {/*state 의 값을 변경할 때 this.setState 를 사용한다.*/}
                {/*setState 를 호출하면 React 는 자동으로 컴포넌트 내부의 자식 컴포넌트 역시 업데이트를 진행한다.*/}
                {/*{this.props.value}*/}
                {this.state.value}
            </button>
        );
    }
    // 부모에게 전달받은 props 의 value 를 사용한다.
}
// 스퀘어 컴포넌트

class Board extends React.Component {
    renderSquare(i) {
        return <Square value={i} />;
    }
    // 부모인 board 가 자식 컴포넌트인 square 에 value 라는 props 를 전달

    render() {
        const status = 'Next player: X';

        return (
            <div>
            <div className="status">{status}</div>
            <div className="board-row">
                {this.renderSquare(0)}
                {this.renderSquare(1)}
                {this.renderSquare(2)}
            </div>
            <div className="board-row">
                {this.renderSquare(3)}
                {this.renderSquare(4)}
                {this.renderSquare(5)}
            </div>
            <div className="board-row">
                {this.renderSquare(6)}
                {this.renderSquare(7)}
                {this.renderSquare(8)}
            </div>
        </div>
    );
    }
}
// 보드 컴포넌트

class Game extends React.Component {
    render() {
        return (
            <div className="game">
                <div className="game-board">
                    <Board />
                </div>
                <div className="game-info">
                    <div>{/* status */}</div>
                    <ol>{/* TODO */}</ol>
                </div>
            </div>
    );
    }
}
// 게임 컴포넌트

// ========================================

ReactDOM.render(
<Game />,
    document.getElementById('root')
);
