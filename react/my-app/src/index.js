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
    // constructor(props) {
    //     super(props);
    //     this.state = {
    //         squares: Array(9).fill(null),
    //         xIsNext: true,
    //     };
    // }

    renderSquare(i) {
        return <Square
            value={this.props.squares[i]}
            onClick={() => this.props.onClick(i)}
        />;
    }
    // 부모인 board 가 자식 컴포넌트인 square 에 value 라는 props 를 전달

    render() {
        return (
            <div>
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
    constructor(props) {
        super(props);
        this.state = {
            history: [{
                squares: Array(9).fill(null),
            }],
            stepNumber: 0,
            xIsNext: true,
        }
    }
    handleClick(i) {
        const history = this.state.history.slice(0, this.state.stepNumber + 1);
        const current = history[this.state.stepNumber];
        const squares = current.squares.slice();
        if (calculateWinner(squares) || squares[i]) {
            return;
        }
        squares[i] = this.state.xIsNext ? 'X' : 'O';
        this.setState({
            history: history.concat([{
                squares: squares,
            }]),
            // concat 을 이용하여 기존 배열을 변경하지 않고 데이터를 변경할 수 있다.
            stepNumber: history.length,
            xIsNext: !this.state.xIsNext,
        });
    }
    jumpTo(step) {
        this.setState({
            stepNumber: step,
            xIsNext: (step % 2) === 0
        });
    }
    render() {
        const history = this.state.history;
        const current = history[history.length - 1];
        const winner = calculateWinner(current.squares);

        const moves = history.map((step, move) => {
            const desc = move ?
                `Go to move #${move}` :
                'Go to game start';
            return (
                <li key={move}>
                    <button onClick={() => this.jumpTo(move)}>{desc}</button>
                </li>
            )
            // 위와 같이 배열이나 이터레이터의 자식들은 고유의 "key" 라는 prop 을 가지고 있어야 한다.
        })

        let status;
        if (winner) {
            status = 'Winner: ' + winner;
        } else {
            status = 'Next player: ' + (this.state.xIsNext ? 'X' : 'O');
        }

        return (
            <div className="game">
                <div className="game-board">
                    <Board
                        squares={current.squares}
                        onClick={(i) => this.handleClick(i)}
                    />
                </div>
                <div className="game-info">
                    <div>{status}</div>
                    <ol>{moves}</ol>
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
