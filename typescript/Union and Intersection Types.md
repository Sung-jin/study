## 유니언 타입

```typescript
declare function padLeft(value: string, padding: any): string;
// 위와 같이 padding 이라는 변수의 타입을 any 로 지정하여 모든 타입이 가능하다
declare function padLeft(value: string, padding: number|string): string;
// | 으로 특정 타입들을 지정할 수 있다
// 위와 같이 사용할 때 유니언 타입이라 하고, 해당 변수는 해당 유니언 타입들 중 하나라고 판단한다
```

### 공통 필드를 가지는 유니언

* 유니언 타입인 값이 있으면, 유니언에 있는 모든 타입에 공통인 멤버들에만 접근할 수 있다

```typescript
interface Bird {
  fly(): void;
  layEggs(): void;
}

interface Fish {
  swim(): void;
  layEggs(): void;
}

declare function getSmallPet(): Fish | Bird;

let pet = getSmallPet();
pet.layEggs();
// Bird/Fish 둘다 layEggs 라는 메서드를 공통으로 가지고 있기 때문에,
// getSmallPet 이라는 함수를 통해 리턴받은 Fish|Bird 의 공통 메서드인
// layEggs 는 사용이 가능하나, 해당 함수만으로는 어떠한 타입인지 알 수 없으므로
// fly, swim 은 런타임시에 오류가 발생이 가능하기 때문에 ts 에서 에러가 발생한다

// 두 개의 잠재적인 타입 중 하나에서만 사용할 수 있다
pet.swim(); // @errors: 2339
```

### 유니언 구별하기

* ts 가 현재 가능한 타입 추론의 범위를 좁혀 나가게 해줄 수 있는 리터럴 타입을 갖는 단일 필드를 사용할 수 있다

```typescript
type NetworkLoadingState = {
  state: "loading";
};

type NetworkFailedState = {
  state: "failed";
  code: number;
};

type NetworkSuccessState = {
  state: "success";
  response: {
    title: string;
    duration: number;
    summary: string;
  };
};

type NetworkState = | NetworkLoadingState | NetworkFailedState | NetworkSuccessState;

// NetworkState 라는 타입에 state 라는 공통된 변수가 존재한다
// 또한 state 는 각각 'loading', 'failed', 'success' 라는 값으로 정해져 있다
// 즉, state 의 값이 특정 값임을 확인하면, 해당 블록 스콥에서는 해당 타입으로 인식한다

function networkStatus(state: NetworkState): string {
    // 해당 시점에서 state 는 어떤 타입인지 알 수 없다
    switch (state.state) {
        case "loading":
            return "Downloading...";
            // state 가 loading 이므로, NetworkLoadingState 라는 타입이 된다
        case "failed":
            // 여기서 타입은 NetworkFailedState일 것이며,
            // 따라서 `code` 필드에 접근할 수 있다
            return `Error ${state.code} downloading`;
        case "success":
            return `Downloaded ${state.response.title} - ${state.response.summary}`;
            // 여기서 타입은 NetworkSuccessState 것이며,
            // response 라는 필드를 접근할 수 있다
    }
}
```

## 교차 타입

* 여러 타입을 하나로 결합한 것이 교차 타입이다
* 기존 타입을 합쳐 필요한 기능을 모두 가진 단일 타입을 얻을 수 있다

```typescript
interface ErrorHandling {
    success: boolean;
    error?: { message: string };
}

interface ArtworksData {
    artworks: { title: string }[];
}

interface ArtistsData {
    artists: { name: string }[];
}

type ArtworksResponse = ArtworksData & ErrorHandling;
// ArtworksResponse 은 artists 와 success,error 가 존재한다
type ArtistsResponse = ArtistsData & ErrorHandling;
// ArtistsResponse 은 artworks 와 success,error 가 존재한다

const handleArtistsResponse = (response: ArtistsResponse) => {
    if (response.error) {
        console.error(response.error.message);
        return;
    }

    console.log(response.artists);
};
```

### 교차를 통한 믹스인

```typescript
class Person {
    constructor(public name: string) {}
}

interface Loggable {
    log(name: string): void;
}

class ConsoleLogger implements Loggable {
    log(name: string) {
        console.log(`Hello, I'm ${name}.`);
    }
}

// 두 객체를 받아 하나로 합친다
function extend<First extends {}, Second extends {}>(
    first: First,
    second: Second
): First & Second {
    const result: Partial<First & Second> = {};
    for (const prop in first) {
        if (first.hasOwnProperty(prop)) {
            (result as First)[prop] = first[prop];
        }
    }
    for (const prop in second) {
        if (second.hasOwnProperty(prop)) {
            (result as Second)[prop] = second[prop];
        }
    }
    return result as First & Second;
}

const jim = extend(new Person("Jim"), ConsoleLogger.prototype);
jim.log(jim.name);
```
