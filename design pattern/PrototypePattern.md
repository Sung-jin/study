## Prototype Pattern

* 기존 인스턴스를 복제하여 새로운 인스턴스를 만드는 방법이며, 기존의 객체를 응용하여 새로운 객체를 만들때 사용한다
    * 객체를 생성하는 시간과 자원이 많이 들고, 비슷한 객체가 이미 있는 경우에 활용된다
* 기존 객체를 복사한 후, 필요한 부분만 수정 또는 메서드로 제공하는 등의 방법으로 기존의 객체를 재활용한다
* 이러한 패턴을 위해서 Java 에서 제공하는 `clone()` 을 활용한다

### 생성 패턴

* 프로토타입 패턴은 기존의 인스턴스를 활용하여 새로운 인스턴스를 생성하는 패턴이며, 이를 생성 패턴이라 한다
* 생성 패턴은 객체를 생성, 합성하는 방법이나 객체의 표현 방법을 시스템과 분리해준다
* 생성 패턴은 다음 두가지의 사항이 있다
    1. 생성 패턴은 시스템이 어떤 Concrete Class 를 사용하는지에 대한 정보를 캡슐화한다
    2. 생성 패턴은 이들 클래스의 인스턴스들이 어떻게 만들고 어떻게 결합하는지에 대한 부분을 완전히 가려준다

### 예제

```java
public class BigObject implements Cloneable {
    private List<BigData> data;
    
    ...
  
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new BigObject(
                this.data.stream().map(value -> value.deepCopy())
                        .collect(Collectors.toList())
        );
    }
}

public static void main(String[] args) {
    try {
        BigObject bigObject = httpCall("url");
        bigObject.doSomething();
        
        BigObject deepCopiedObject = bigObject.clone();
        deepCopiedObject.doOtherSomething();
    } catch (CloneNotSupportedException e) { // checked exception }
}
```

## 개인적인 느낌

* 많은 프로토타입 패턴에서 예제로 DB 조회 결과 객체를 활용한 예제였으나, JPA 부터 관련된 ORM 등 DB 객체를 담당해주는 좋은 프레임워크가 많아서 와닿지가 않았다
* 그나마 http call 이나 배치성 데이터 등의 데이터의 경우 재활용할 일이 있다면 사용할만 하지만, 최근 좋아진 컴퓨팅 파워랑, 객체의 scope block 안에서 다시 재활용 되는 케이스는 거의 없을 것 같아서 활용성은 낮아 보인다
