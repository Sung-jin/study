## React 시작하기

* [자습서: React 시작하기](https://ko.reactjs.org/tutorial/tutorial.html) 을 참고하여 기초 학습을 진행한다.

### 목표

* 대화형 틱택토 게임 만들기
* [최종 결과물](https://codepen.io/gaearon/pen/gWWZgR?editors=0010)

### 필요지식

* HTML
* JavaScript
    * [JavaScript 학습 추천](https://developer.mozilla.org/ko/docs/Web/JavaScript/A_re-introduction_to_JavaScript)
    * 해당 자습서에서는 화살표 함수, 클래스, let, const 및 Babel REPL 을 사용하여 ES6 코드가 어떻게 컴파일 되는지 확인한다.

### 자습서 환경설정

1. 브라우저에 코드 작성
2. 로컬 환경에서 코드 작성 ✔
    * CLI 를 통해 프로젝트 초기 셋팅을 진행한다.

```
npx create-react-app my-app

# 해당 프로젝트의 파일을 사용하지 않고, 구조만 사용한다.

cd my-app/src

rm -f *

cat <<EOF >./index.css
body {
  font: 14px "Century Gothic", Futura, sans-serif;
  margin: 20px;
}

ol, ul {
  padding-left: 30px;
}

.board-row:after {
  clear: both;
  content: "";
  display: table;
}

.status {
  margin-bottom: 10px;
}

.square {
  background: #fff;
  border: 1px solid #999;
  float: left;
  font-size: 24px;
  font-weight: bold;
  line-height: 34px;
  height: 34px;
  margin-right: -1px;
  margin-top: -1px;
  padding: 0;
  text-align: center;
  width: 34px;
}

.square:focus {
  outline: none;
}

.kbd-navigation .square:focus {
  background: #ddd;
}

.game {
  display: flex;
  flex-direction: row;
}

.game-info {
  margin-left: 20px;
}
EOF

# index.css

cat <<EOF >./index.js
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

class Square extends React.Component {
  render() {
    return (
      <button className="square">
        {/* TODO */}
      </button>
    );
  }
}

class Board extends React.Component {
  renderSquare(i) {
    return <Square />;
  }

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

// ========================================

ReactDOM.render(
  <Game />,
  document.getElementById('root')
);

EOF

# index.js

npm start
```
