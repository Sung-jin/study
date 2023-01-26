package proxy;

import java.lang.reflect.Proxy;

public class HelloProxy {

    public static Hello create() {
        return (Hello) Proxy.newProxyInstance(
                HelloProxy.class.getClassLoader(),      // 클래스 로더
                new Class[] { Hello.class },            // 다이나믹 프록시가 구현할 인터페이스
                new UppercaseHandler(new HelloTarget()) // 부가기능과 위임 관련 코드를 담고 있는 InvocationHandler 구현체
        );
    }
}
