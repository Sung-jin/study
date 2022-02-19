## Mixin

전통적인 객체지향 계층과 함께 재사용 가능한 컴포넌트로 부터 클래스를 빌드하는 또 다른 일반적인 방법으로 간단한 부분클래스를 결합하여 빌드할 수 있다

### 예제

```typescript
function applyMixins(derivedCtor: any, baseCtors: any[]) {
    baseCtors.forEach(baseCtor => {
        Object.getOwnPropertyNames(baseCtor.prototype).forEach(name => {
            Object.defineProperty(derivedCtor.prototype, name, Object.getOwnPropertyDescriptor(baseCtor.prototype, name));
        });
    });
}
/*
mixin 을 수행 할 도우미 함수
각 mixin 의 속성이 실행되고, mixin 의 대상으로 복사되어 독립형 속성을 구현한다
 */

class Disposable {
    isDisposed: boolean;

    dispose() {
        this.isDisposed = true;
    }
}

class Activatable {
    isActive: boolean;

    activate() {
        this.isActive = true;
    }

    deactivate() {
        this.isActive = false;
    }
}
/*
mixin 을 수행하는 Disposable, Activatable 클래스
각 클래스는 부분적인 기능에 집중되어 있다
 */

class SmartObject {
    constructor() {
        setInterval(() => console.log(`${this.isActive} : ${this.isDisposed}`), 500);
    }
    
    interact() {
        this.activate();
    }
}
interface SmartObject extends Disposable, Activatable {}
/*
SmartObject 클래스에서 Disposable/Activatable 을 확장하는 대신 SmartObject 인터페이스에서 확장한다
Declaration merging 으로 인해 SmartObject 인터페이스가 SmartObject 클래스에 혼합된다

클래스를 인터페이스로 취급하고, Disposable/Activatable 뒤에있는 유형만 구현이 아닌 SmartObject 유형으로 혼합한다
이것은 클래스 구현에서 mixin 을 제공해야 한다는 것을 의미한다
 */

applyMixins(SmartObject, [Disposable, Activata ble]);
// mixin 적용

let smartObj = new SmartObject();
setTimeout(() => smartObj.interact(), 1000);
```