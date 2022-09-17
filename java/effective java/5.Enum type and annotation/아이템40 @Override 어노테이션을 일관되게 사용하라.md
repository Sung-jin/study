## @Override 어노테이션을 일관되게 상용하라

* @Override 어노테이션을 사용한다는 의미는 상위 타입의 메서드를 재정의했음을 뜻한다
* @Override 어노테이션을 일관성 있게 사용하면 여러가지 버그들을 에방할 수 있다

```java
public class Bigram {
    private final char first;
    private final char second;
    
    public Bigram(char first, char second) {
        this.first = first;
        this.second = second;
    }
    
    public boolean equals(Bigram b) {
        return b.first == first && b.second == second;
    }
    
    public int hashCode() {
        return 31 * first + second;
    }
    
    public static void main(String[] args) {
        Set<Bigram> s = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                s.add(new Bigram(ch, ch));
            }
            System.out.println(s.size()); // 260
            // Set 을 활용하였으므로 26 을 예상하였으나, 260 이 출력이 된 이유는
            // equals/hashCode 를 재정의한 것이 아닌 overloading 을 하면서
            // equals 로 중복여부를 체크할 때, '==' 연산인 식별성만 확인하면서
            // 서로다른 객체로 인식하면서 260 이 출력이 되었다
        }
    }
}
```

* @Override 어노테이션을 활용하면 컴파일에서 잘못 선언한 부분을 체크할 수 있다

```java
@Override
public boolean equals(Bigram b) {
    // 컴파일 에러가 발생하며 인자 타입이 Bigram 이 아닌, Object 로 해야 한다
    ...
}
```

* 상위 클래스의 메서드를 재정의하려면 모든 메서드에 @Override 어노테이션을 달아야만 한다
* 단, 구체 클래스에서 상위 클래스의 추상 메서드를 재정의할 때는 @Override 를 달지 않아도 된다
    * 컴파일에서 구현하지 않은 메서드를 알려주기는 하지만, 통일성을 위해 @Override 를 추가하는 것이 좋다
* @Override 는 클래스뿐 아니라 인터페이스의 메서드를 재정의할 때도 사용할 수 있다

## 정리

* 재정의한 모든 메서드에 @Override 어노테이션을 의식적으로 달면 잘못 재정의한 부분을 컴파일에서 알 수 있다
* 단, 구체 클래스에서 상위 클래스의 추상 메서드를 재정의한 경우에는 추가하지 않아도 되나, 통일성을 위해 다는 것이 좋다
