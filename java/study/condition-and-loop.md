# 조건문

- 조건문의 종류로는 if, switch 가 존재한다.
- 조건문 결과가 true 일 경우 그 안의 코드블럭을 실행한다.
- 모든 조건이 false이고 else가 존재하거나 defualt 가 존재할 시 그 블럭을 실행한다.
- switch 의 경우 break;가 없다면 조건에 만족하는 case문 아래의 모든 블럭을 실행하게 된다.

```java
if (condition1) {
    // something...
} else if (condition2) {
    // something...
} else {
    // something...
}

switch(condition) {
    case condition1 : // something...
    case condition2 : // something...
    case condition3 : // something...
    default : //something
}
```

# 반복문

- 반복문의 종류로는 for, while, do-while 이 존재한다.
  - forEach, foreach, map, for in 등 더 다양하고 사용 방법이 조금 다른 loop 도 존재한다.
  - 이는 lambda 를 다룰 때 겸사겸사 자세히 다루겠다.
- 반복문의 조건만큼 loop 이 동작하며 코드 블럭을 그 횟수만큼 실행한다.
- do while의 경우 조건 상관없이 코드 블럭을 한번 실행 한 후 조건에 따라 loop 이 동작된다.

```java
for (int i = 0; i < 100; i++) {
    // something...
    // something을 100번 반복하여 실행한다.
}

while (condition) {
    // something...
    // something 을 condition 이 false 가 될 때 까지 반복하여 실행한다.
}

do {
    // something...
    // something 을 1번 실행하고 condition이 false일 때 까지 something을 반복한다.
} while (condition);
```

# loop 제어문

**break**

- loop 코드 블럭을 실행도중 break 를 만나면 그 코드 블럭의 loop 을 종료한다.

**continue**

- loop 코드 블럭을 실행도중 continue 를 만나면 현재 실행중인 loop 을 continue 시점에서 끝내고 loop 조건을 확인한다.

```java
for (int i = 0; i < 100; i++) {
    // something...
    if (condition) break; //condition 이 true 이면 현재 시점에서 for 문을 종료한다.
}

for (int i = 0; i < 100; i++) {
    // block 1
    // something...

    for (int j = 0; j < 100; j++) {
        // block 2
        // something...
        if (condition) break; //condition 이 true 이면 block 2를 종료하고 block 1 의 나머지 loop 을 실행한다.
    }
}

for (int i = 0; i < 100; i++) {
    // something...
    if (condition) continue; //condition 이 true 이면 현재 시점에서 loop 의 조건 확인으로 이동한다.
}

// while 도 for 문처럼 block 을 기준으로 break 와 continue 가 동작한다.
```
