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

### public

* 프로그램 내에서 선언된 멤버들에 자유롭게 접근할 수 있다
* public 을 붙이지 않더라도 기본적으로 public 으로 동작한다
    * 즉, ts 에서 기본적으로 각 멤버는 public 이다

```typescript
class Foo {
    public bar: string;
    // 명시적으로 public 을 작성해서 명시적으로 표현할 수 있다
    fuz: string;
    // public 키워드가 없더라도 기본적으로 public 으로 동작한다
}
```

### ECMAScript 비공개 필드

* ts 3.8 에서 비공개 필드를 위한 js 의 새로운 문법을 지원한다
* 해당 문법은 js 런타임에 내장되어 있으며, [ts 3.8 릴리즈](https://devblogs.microsoft.com/typescript/announcing-typescript-3-8-beta/#type-only-imports-exports) 에서 비공개 필드에 대해 자세히 나와있다

```typescript
class Foo {
    #bar: string;
    constructor(baz: string) { this.#bar = baz; }
}

const foo = new Foo();
foo.#bar; // #bar 는 비공개 식별자이기 때문에 Foo 클래스 외부에서 접근이 불가능하다
```

### ts 의 private

* 멤버를 포함하는 클래스 외부에서 특정 멤버에 접근하지 못하도록 private 을 표시하는 방법이 있다
* 두개의 다른 타입을 비교할 때, 모든 멤버의 타입이 호환된다면 해당 타입들 자체가 호환 가능하다고 한다
    * 하지만 private, protected 멤버가 있는 타입들을 비교할 때는 타입을 다르게 처리한다
    * 호환된다고 판단되는 두개의 타입 중 한쪽에서 private/protected 멤버를 가지고 있다면, 다른 한쪽도 동일한 선언에 private/protected 멤버를 가지고 있어야 한다

```typescript
class Foo {
    private name: string;

    constructor(val) {
        this.name = val;
    }
}

class Bar {
    private name: string;

    constructor(val) {
        this.name = val;
    }
}

class EBar extends Bar {
    constructor(val) { super('bar'); }
}

let foo = new Foo();
let bar = new Bar();
let eBar = new EBar();

foo.name; // bar 는 private 으로 설정되어 있어서 Foo 클래스 외부에서 접근이 불가능하다bar;

foo = bar;  // foo 와 bar 는 name: string 이라는 같은 형태를 가지기 때문에 호환이 가능하다
foo = eBar; // error! foo 와 eBar 는 호환될 수 없다
```

### protected

* protected 로 선언된 멤버를 파생된 클래스 내에서 접근할 수 있다는 점을 제외하고는 private 지정자와 매우 유사하게 동작한다
* 생성자도 protected 로 선언될 수 있으며, 이는 클래스를 포함하는 클래스 외부에서 인스턴스화 할 수 없지만 확장할 수 있음을 의미한다

```typescript
class Foo {
    protected name: string;

    constructor(val) {
        this.name = val;
    }
}

class Bar extends Foo {
    private baz: string;

    protected constructor(name: string, baz: string) {
        super(name);
        this.baz = baz;
    }
}

class Fuz extends Bar {
    constructor(name: string, baz: string) { super(name, baz); }
}

const bar = new Bar();  // Bar 의 생성자는 protected 이므로 생성할 수 없다
const fuz = new Fuz();
fuz.name; // 오류
```

### 읽기전용 지정자

* `readonly` 키워드를 사용하면 해당 프로퍼티가 읽기전용이 된다
* 읽기전용 프로퍼티는 선언 또는 생성자에서 초기화해야 한다

```typescript
class Foo {
    readonly bar: string;
    readonly fuz = 0;

    constructor(val, private readonly baz: string) {
        // baz 의 경우 매개변수 프로퍼티에 readonly 키워드를 사용했고,
        // baz 라는 readonly 변수가 없지만, 생성자에 baz 에 위치한 데이터는
        // 객체 생성 후 baz 라는 readonly 변수로 생성된다
        // 또한 private/protected/public 을 사용할 수 있다
        this.bar = val;
    }
}

const foo = new Foo('val', 'readonly baz');
foo.bar = 'change'; // error
```

### 접근자

* ts 객체의 멤버에 대한 접근을 가로치는 방식으로 getters/setters 를 지원한다
* 접근자를 사용하려면 ECMAScript 5 이상이어야 한다
* ㅎet/set 이 없는 접근자는 자동으로 readonly 로 유추된다

```typescript
class Employee {
    private _fullName: string;
    private fullNameMaxLength = 10;

    get fullName(): string {
        return this._fullName;
    }

    set fullName(newName: string) {
        if (newName && newName.length > this.fullNameMaxLength) {
            throw new Error("fullName has a max length of " + this.fullNameMaxLength);
        }

        this._fullName = newName;
    }
}

const employee = new Employee();

employee.fullName = "Bob Smith";
console.log(employee.fullName);

empolyee.fullName = 'foooooooooo'; // error fullName has a max length of 10
```

### 전역 프로퍼티

* 인스턴스가 아닌 클래스 자체에 전역 멤버를 생성할 수 있다
* `static` 키워드를 사용하면 `클래스이름.전역프로퍼티` 형태로 접근할 수 있다

```typescript
class Foo {
    static val = 0;
  
    constructor() {
        console.log(Foo.val);
        Foo.val += 1;
    }
}

new Foo(); // 0
new Foo(); // 1
new Foo(); // 2
```

### 추상 클래스

* 추상클래스로 직접 인스턴스화 할 수 없다
* 멤버에 대한 구현 세부 정보를 포함할 수 있다
* abstract 키워드는 추상 클래스뿐 아니라 추상 메서드를 정의하는데 사용할 수 있다
* 추상 클래스 내에 추상으로 표시된 메서드는 구현을 포함하지 않고, 반드시 파생된 클래스에서 구현되어야 한다
* 추상 메서드와 인터페이스 메서드는 비슷한 문법을 공유하지만, 추상 메서드는 반드시 abstract 키워드를 포함해야 하고, 선택적으로 접근 지정자를 포함할 수 있다

```typescript
abstract class Animal {
    abstract makeSound(): void;
    
    move(): void {
        console.log('move');
    }
}

class Dog extends Animal {
    constructor() {
        super();
    }
    
    makeSound() {
        console.log('Bow-wow');
    }
    
    eat() {
        console.log('eat something...')
    }
}

const dog = new Dog();
dog.makeSound(); // Bow-wow
dog.eat();       // eat something...
```
