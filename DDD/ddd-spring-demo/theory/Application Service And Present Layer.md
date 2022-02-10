### 표현 영역과 응용 영역

* 응용 영역과 표현 영역이 사용자와 도메인을 연결해 주는 매개체 역할을 한다
  * 표현 영역은 사용자의 요청을 해석한다
  * 응용 서비스는 실제 사용자가 원하는 기능을 제공하는 영역이다
* 표현 영역은 사용자와의 상호작용을 처리하기 때문에 응용 서비스는 표현 영역에 의존하지 않는다

### 응용 서비스의 역할

* 응용 서비스는 사용자가 요청한 기능을 실행한다
  * 응용 서비스는 사용자의 요청을 처리하기 위해 레파지토리부터 도메인 객체를 구하고, 도메인 객체를 사용한다
* 응용 서비스의 주요 역할은 도메인 객체를 사용해서 사용자의 요청을 처리하는 것이다
* 응용 서비스는 주로 도메인 객체 간의 흐름을 제어하기하는 단순한 형태를 가진다

```java
public Result doSomeFunc(SomeReq req) {
    // 1. 레파지토리에서 애그리거트를 구한다
    SomeAgg agg = someAggRepository.findById(req.getId());
    
    // 2. 애그리거트의 도메인 기능을 실행한다
    agg.doFunc(req.getValue());
    
    // 3. 결과를 리턴한다
    return createSuccessResult(agg);
}

public Result doSomeCreation(CreateSomeReq req) {
    // 1. 데이터 중복 등 유효검사를 한다
    checkValid(req);
    
    // 2. 애그리거트를 생성한다
    SomeAgg newAgg = createSome(req);
    
    // 3. 레파지토리에 애그리거트를 저장한다
    someAggRepository.save(newAgg);
    
    // 4. 결과를 리턴한다
    return createSuccessResult;
}
```

* 위와 같은 형태보다 복잡할 경우, 대부분 응용 서비스에서 도메인 로직의 일부를 구현하고 있을 가능성이 높다
* 도메인 객체 간의 실행 흐름을 제어하는 것과 더불어 응용 서비스의 주된 역할 중 하나는 트랜잭션 처리이다
  * 응용 서비스는 도메인의 상태 변경을 트랜잭션으로 처리해야 한다
  
```java
public void blockMemeters(String[] blockingIds) {
    if (blockingIds == null || blockingIds.length == 0) return;
    List<Member> members = memberRepository.findByIds(blockingIds);
    for(Member mem: Members) {
        mem.block();
    }
    // 위와 같이 for loop 을 실행하는 도중 중간에 예외가 발생할 경우,
    // 일부 데이터만 적용이 되기 때문에 일관성이 깨지게 되므로
    // 모든 객체는 롤백되어야만 한다
    // 즉, 해당 scope 은 트랜잭션으로 관리되어야 한다
}
```

#### 도메인 로직 넣지 않기

```java
public class Member {
    public void changePassword(String oldPw, String newPw) {
        if (!matchPassword(oldPw)) throw new badPasswordException();
        setPassword(newPW);
    }
    
    public boolean matchPassword(String pwd) {
        return passwordEncoder.matches(pwd);
    }
    
    private void setPassword(String newPassword) {
        if (isEmpty(newPw)) throw new IllegalArgumentException("no new password");
        this.password = newPassword;
    }
}
// 위와 같이 멤버의 패스워드 변경과 validation 은 도메인의 핵심 로직이며,
// 해당 기능을 응용 서비스에서 구현하면 안된다

public class ChangePasswordService {
    public void changePassword(String memberId, String oldPw, String newPW) {
        Member member = memberRepository.findById(memberId);
        checkMember();
        member.changePassword(oldPw, newPw);
        // 패스워드 유효검사는 도메인의 기능이므로, 위와 같이 응용 서비스에서 해당 기능을 사용만 하면 된다
      
        /*
        if (!passwordEncoder.matches(oldPw, member.getPassword()) throw new BadPasswordException();
        ...
        위와 같이 도메인의 기능을 응용 서비스에서 구현하면 안된다
         */
    }
}
```

* 도메인 로직을 도메인 영역과 응용 서비스에 분산해서 구현하면 코드 품질에 문제가 발생한다
  1. 코드의 응집성이 떨어진다
    * 도메인 데이터와 해당 데이터를 조작하는 도메인 로직이 한 영역에 위치하지 않기 때문에, 도메인 로직을 파악하는데 여러 영역을 분석해야 한다
  1. 여러 응용 서비스에서 동일한 도메인 로직을 구현할 가능성이 높아진다
    * 응용 서비스 영역에 별도 보조 클래스를 만들어서 해당 클래스를 사용할 수 있지만, 응집도가 낮아지는건 해결하지 못한다
* 좋은 소프트웨어는 응집도가 높고 확장 결합도는 낮아야 한다