import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

// class Square extends React.Component {
//     // constructor(props) {
//     //     super(props);
//     //     // javascript 클래스에서 하위 클래스의 생성자를 정의할 떄 항상 super 를 호출해야 한다.
//     //     // 모든 react 컴포넌트 클래스는 생성자를 가질 때 super(props) 호출 구문부터 작성해야 한다.
//     //     this.state = {
//     //         value: null,
//     //     };
//     // }
//     // 해당 컴포넌트의 어떠한 값을 기억할 때 state 를 사용한다.
//
//     // 게임의 상태를 square 의 부모인 board 에서 관리하기 때문에 해당 컴포넌트에서 상태를 관리할 필요가 없어진다.
//     // 이러한 컴포넌트를 Board 컴포넌트에 '제어되는 컴포넌트' 라고 한다.
//     render() {
//         return (
//             // <button className="square" onClick={() => alert('click')}>
//             <button
//                 className="square"
//                 onClick={() => this.props.onClick()}
//             >
//                 {/*state 의 값을 변경할 때 this.setState 를 사용한다.*/}
//                 {/*setState 를 호출하면 React 는 자동으로 컴포넌트 내부의 자식 컴포넌트 역시 업데이트를 진행한다.*/}
//                 {this.props.value}
//                 {/*{this.state.value}*/}
//             </button>
//         );
//     }
//     // 부모에게 전달받은 props 의 value 를 사용한다.
// }
// // 스퀘어 컴포넌트

function Square(props) {
    return (
        <button className="square" onClick={props.onClick}>
            {/*this.props -> props 로 변경됨.*/}
            {props.value}
        </button>
    );
}
// state 가 없이 컴포넌트를 작성할 때 함수 컴포넌트로 작성할 수 있다.
// 함수 컴포넌트는 props 를 전달받아서 렌더링할 대상을 반환하는 함수이다.

class Board extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            squares: Array(9).fill(null),
            xIsNext: true,
        };
    }

    renderSquare(i) {
        return <Square
            value={this.state.squares[i]}
            onClick={() => this.handleClick(i)}
        />;
    }
    // 부모인 board 가 자식 컴포넌트인 square 에 value 라는 props 를 전달

    handleClick(i) {
        const squares = this.state.squares.slice();
        if (calculateWinner(squares) || squares[i]) {
            return;
            // 이미 클릭된 곳일 경우 해당 이벤트는 무시
        }
        // .slice 를 통해 기존 배열을 수정하지 않고 복사본을 생성해서 수정을 진행했다.
        // 이는 불변성을 지키기 위해서이다.
        squares[i] = this.state.xIsNext ? 'X' : 'O';
        this.setState({
            squares: squares,
            xIsNext: !this.state.xIsNext,
        });
    }

    render() {
        const winner = calculateWinner(this.state.squares);
        let status;
        if (winner) {
            status = `Winner: ${winner}`;
        } else {
            status = `Next player : ${this.state.xIsNext ? 'X' : 'O'}`;
        }

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

function calculateWinner(squares) {
    const lines = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6],
    ];
    for (let i = 0; i < lines.length; i++) {
        const [a, b, c] = lines[i];
        if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
            return squares[a];
        }
    }
    return null;
}

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
