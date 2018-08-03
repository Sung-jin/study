function add(a, b, callback){
  var result = a + b;
  callback(result);//결과를 콜백 함수 인자값으로
}

add(10, 10, function(result)/*콜백으로 호출 되는 익명 함수*/{
  console.log('파라미터로 전달 된 콜백 함수 호출됨.');
  console.log('더하기 (10, 10)의 결과 : %d',result);
});

///////////////////////////////////////////////////////////////

function add(a, b, callback){
  var result = a + b;
  callback(result);

  var count = 0;//add 함수에서 계속 접근 가능. 초기화가 되는게 아님.
  var history = function(){
    return count + ' : ' + a + ' + ' + b + ' = ' + result;
  };
  return history;
}

var add_history = add(10, 10, function(result){
  console.log('파라미터로 전달 된 콜백 함수 호출됨.');
  console.log('더하기 (10, 10)의 결과 : %d',result);
});

console.log('결과 값으로 받은 함수 실행 결과 : ' + add_history());
console.log('결과 값으로 받은 함수 실행 결과 : ' + add_history());
console.log('결과 값으로 받은 함수 실행 결과 : ' + add_history());
