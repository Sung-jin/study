## ordinal 인덱싱 대신 EnumMap 을 사용하라

```java
class Plant {
    enum LifeCycle { ANNUAL, PERENNIAL, BIENNIAL }
    
    final STring name;
    final LifeCycle lifeCycle;
    
    Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }
    
    ...
}

Set<Plant>[] platsByLifeCycle =
        (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
for(int i = 0; i < plantsByLifeCycle.length; i++) {
    platsByLifeCycle[i] = new HashSet<>();
}
for (Plant p: garden) {
    platsByLifeCycle[p.lifeCycle.ordinal()].add(p);
}
```

* 배열은 제네릭과 호환되지 않으므로 비검사 형변환을 수행해야 하고 깔끔히 컴파일되지 않는다
* 배열은 각 인덱스의 의미를 모르니 출력 결과에 직접 레이블을 달아야 한다
* 정수는 열거 타입과 달리 타입 안전하지 않으므로 정확한 정숫값을 사용한다는 것을 직접 보증해야 한다

### EnumMap

* 열거 타입을 키로 사용하도록 설계한 아주 빠른 Map 이다

```java
Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle =
    new EnumMap<>(Plant.LifeCycle.class);
for (Plant.LifeCycle lc: Plant.LifeCycle.values()) {
    plantsByLifeCycle.put(lc, new HashSet<>());
}
for (Plant p: garden) {
    platsByLifeCycle.get(p.liceCycle).add(p);
}
```

* EnumMap 을 활용하면 형변환 없이 사용할 수 있다
    * 맵의 키인 열거 타입이 그 자체로 출력용 문자열을 제공하므로 레이블을 달 필요가 없다
* 배열 인덱스를 계산하는 과정에서 오류가 날 가능성이 없어진다
    * 내부 구현 방식을 안으로 숨겨서 Map 타입 안전성과 배열의 성능을 모두 얻어냈다
* EnumMap 의 생성자가 받는 키 타입의 Class 객체는 한정적 타입 토큰으로, 런타임 제네릭 타입 정보를 제공한다

### 배열들의 배열의 인덱스에 ordinal 사용

```java
public enum Phase {
    SOLID, LIQUID, GAS;
    
    public enum Transition {
        MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;
        
        private static final Transitionp[][] TRANSITIONS = {
                { null, MELT, SUBLIME },
                { FREEZE, null, BOIL },
                { DEPOSITY, CONDENSE, null }
        };
        
        public static Transition from(Phase from, Phase to) {
            return TRANSITIONS[from.ordianl()][to.ordinal()];
        }
    }
}
```

* 컴파일러는 ordinal 과 배열 인덱스의 관계를 알 수 없으므로, Phase 나 Phase.Transition 열거 타입을 수정하면서 TRANSITIONS 를 함께 수정하지 않거나 잘못 수정하면 런타임 오류가 발생한다
* 이도 EnumMap 을 활용하여 표현하는 형태가 훨씬 낫다

```java
public enum Phase {
    ...;
    
    public enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID)...;
        
        private final Phase from;
        private final Phase to;
        
        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }
        
        private static final Map<Phase, Map<Phase, Transition>> m =
                Stream.of(values()).collect(
                        groupingBy(
                                t -> t.from,
                                () -> new EnumMap<>(Phase.class),
                                toMap(t -> t.to, t -> t, (x, y) -> y, () -> new EnumMap<>(Phase.class))
                        )
                );
        
        public static Transition from(Phase from, Phase to) {
            return m.get(from).get(to);
        }
    }
}
```

* 위와 같이 EnumMap 을 활용한 경우, 신규 상태(예를들어 PLASMA) 를 추가할 경우
    * 배열로 만든 코드에서는 새로운 상수 1개, Phase,Transition 에 2개, 원소 9개 짜리 배열들의 배열을 원소 16개로 변경해야 한다
    * EnumMap 을 활용한 코드에서는 상태 목록에 PLASMA 를 추가하고, 전이 목록 (IONIZE(GAS, PLASMA) / DEIONIZE(PLASMA, GAS)) 만 추가하면 된다

## 정리

* 배열의 인덱스를 얻기 위해 ordinal 을 사용하는 것은 좋지 않으므로 EnumMap 을 사용해야 한다
