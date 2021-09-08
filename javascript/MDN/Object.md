### 생성자 함수 사용하기

* 다음과 같은 방법으로 객체를 생성할 수 있다.
  1. 생성자 함수를 작성하여 객체 타입을 정의
     * 객체 타입 이름의 첫 글자는 대문자를 사용하는 것이 관례이다.
  2. new 를 이용하여 객체 인스턴스를 생성
  
```js
function Car(make, model, year, owner) {
    // primitive 뿐 아니라 모든 객체를 속성으로 사용할 수 있다.
    this.make = make;
    this.model = model;
    this.yaer = year;
    this.owner = owner;
    // 객체의 속성에 할당하기 위해서는 this 를 통해 접근한다.
}

function Person(name, age, sex) {
    this.name = name;
    this.age = age;
    this.sex = sex;
}

var who = new Person("owen", 10, "male")
var myCar = new Car("foo", "bar", "fuz", who);
```

#### Object.create

* 객체 생성자 함수가 없어도 객체를 생성할 수 있다.

```js
var Food = {
    type: "noddle",
    displayType: function() {
        console.log(this.type);
    }
}

var ramen = Object.create(Food);
remen.displayType(); // noddle
```