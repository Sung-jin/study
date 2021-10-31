### 클래스

* ECMA2015 에서 객체 지향적 클래스 기반의 접근 방식을 사용해서 앱을 만들 수 있다
* ts 는 js 의 버전과 상관없이 이러한 기법들을 사용할 수 있게 해주고, 기존의 js 로 컴파일하여 주요 브라우저와 플랫폼에서 동일하게 동작하게 한다

```typescript
class Greeter {
    // greeter 라는 클래스를 정의한다
    greeting: string;
    // 프로퍼티
  
    constructor(message: string) {
        // 생성자
        this.greeting = message;
    }
    greet() {
        // 메서드
        return `hello ${this.greeting}`;
        // 클래스 안의 멤버를 참조할 때 this 로 접근한다
    }
}

const greeter = new Greeter('world');
// new 를 통해 클래스의 인스턴스를 생성할 때 생성자를 호출하여 새로운 객체를 만든다
```

### 상속

* 클래는 기초 클래스로부터 프로퍼티, 메서드를 상속받는다

```typescript
class Animal {
    name: string;
    constructor(theName: string) { this.name = theName; }
    move(distanceInMeters: number = 0) {
        console.log(`Animal moved ${distanceInMeters}m.`);
    }
}

class Dog extends Animal {
    bark() {
        console.log('Woof! Woof!');
    }
    // dog 클래스는 annimal 을 상속받앗으므로,
    // bark(), move() 를 모두 가진 인스턴스를 생성할 수 있다
}

class Horse extends Animal {
    constructor(name: string) { super(name); }
    // 생성자 함수에서 기초 클래스의 기본 생성자를 실행하기 위해서 super() 를 호출해야 한다
    // 특히 생성자 내에서 this 에 있는 푸ㅡ로퍼티에 접근하기 위해서는 super 를 먼저 호출해야 한다
    move(distanceInMeters = 45) {
        console.log("Galloping...");
        super.move(distanceInMeters);
        // overwrite 를 하여 원본 클래스의 move 를 다시 재정의 할 수 있다
    }
}

const dog = new Dog();
let tom: Animal = new Horse("Tommy the Palomino");
```
